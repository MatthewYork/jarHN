package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.integer;
import android.util.Log;

import com.mattyork.jarhn.HNManager;
import com.mattyork.jarhn.OMScanner;

public class HNPost {
	public enum PostType {
		PostTypeDefault, PostTypeAskHN, PostTypeJobs, PostTypeShowHN
	}

	public PostType Type;
	public String Username = "";
	public String UrlString = "";
	public String UrlDomain = "";
	public String Title = "";
	public int Points = 0;
	public int CommentCount = 0;
	public String PostId = "";
	public String TimeCreatedString = "";
	
	/***
	 * Creates an ArrayList of Posts from a given HTML stirng and valid FNID.
	 * @param htmlString
	 * @param fnid
	 * @return
	 */
	public static ArrayList<HNPost> parsedPostsFromHTML(String htmlString){
		
		// Set up
		List<String> htmlComponents =  Arrays.asList(htmlString.split("\\s*<tr><td align=right valign=top class=\"title\">\\s*"));
	    ArrayList<HNPost> postArray = new ArrayList<HNPost>();
	    
	    // Scan through components and build posts
	    for (int xx = 1; xx < htmlComponents.size(); xx++) {
	    	Log.i("asdf", xx+"");
			HNPost newPost = new HNPost();
			
			OMScanner scanner = new OMScanner(htmlComponents.get(xx));
			
			//Scan Url
			scanner.skipToString("<a href=\"");
			newPost.UrlString = scanner.scanToString("\">");
			newPost.UrlString = newPost.UrlString.replace("\" rel=\"nofollow", "");
			
			//Scan Title
			newPost.Title = scanner.scanToString("</a>");
			
			//Scan Points
			if (htmlComponents.get(xx).contains("id=score_")) {
				OMScanner pointsScanner = new OMScanner(htmlComponents.get(xx));
				pointsScanner.skipToString("<span id=score_");
				pointsScanner.skipToString(">");
				String pointString = pointsScanner.scanToString(" point");
				newPost.Points = Integer.parseInt(pointString);
			}
			
			//Scan Author
			if (htmlComponents.get(xx).contains("<a href=\"user?id=")) {
				scanner.skipToString("<a href=\"user?id=");
				scanner.skipToString(">");
				newPost.Username = scanner.scanToString("</a> ");
			}
			else {
				scanner.skipToString("</a> ");
			}
			
			if (htmlComponents.get(xx).contains("  |")) {
				//Scan Time Ago
				newPost.TimeCreatedString = scanner.scanToString("  |");
			}
			else {
				scanner.skipToString("<td class=\"subtext\">");
				newPost.TimeCreatedString = scanner.scanToString("</td>");
			}
			
			//Scan Number of Comments
			if (htmlComponents.get(xx).contains("  | <a href=\"item?id=")) {
				OMScanner idScanner = new OMScanner(htmlComponents.get(xx));
				idScanner.skipToString("  | <a href=\"item?id=");
				newPost.PostId = idScanner.scanToString("\">");
				
				//Scan over the comment string to get number of comments (0 if discuss)
				String commentString = scanner.scanToString("</a>");
				if (commentString.contains("discuss")) {
					newPost.CommentCount = 0;
				} else {
					OMScanner commentScanner = new OMScanner(commentString);
					commentScanner.skipToString(">");
					newPost.CommentCount = Integer.parseInt(idScanner.scanToString(" "));
				}
			}
			else if (htmlComponents.get(xx).contains("<a href=\"item?id=")) {
				OMScanner idScanner = new OMScanner(htmlComponents.get(xx));
				idScanner.skipToString("<a href=\"item?id=");
				newPost.PostId = idScanner.scanToString("\">");
			}
			else {
				scanner.skipToString("\">");
			}
			
			//Check if Jobs post
			if (newPost.PostId.length() == 0 && newPost.Points == 0) {
				newPost.Type = PostType.PostTypeJobs;
				if (!newPost.UrlString.contains("http")) {
					newPost.UrlString.replace("item?id=", "");
				}
			}
			//Check if AskHN
			else {
				if (!newPost.UrlString.contains("http") && newPost.PostId.length() > 0) {
					newPost.Type = PostType.PostTypeAskHN;
					//MAYBE UPDATE URL STRING HERE. CODE ISN"T CLEAR!
				}
				else {
					newPost.Type = PostType.PostTypeDefault;
				}
			}
			
			if (htmlComponents.get(xx).toLowerCase().contains("show hn")) {
				newPost.Type = PostType.PostTypeShowHN;
			}
		
			if (xx == htmlComponents.size() - 1) {
				scanner.skipToString("<td class=\"title\"><a href=\"");
				HNManager.getInstance().postFNID = scanner.scanToString("\"");
			}
	    
			postArray.add(newPost);
	    }
	    
		return postArray;
	}
}
