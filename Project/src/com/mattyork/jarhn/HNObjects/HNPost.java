package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.integer;

import com.mattyork.jarhn.HNManager;
import com.mattyork.jarhn.OMScanner;

public class HNPost {
	public enum PostType {
		PostTypeDefault, PostTypeAskHN, PostTypeJobs
	}

	public PostType Type;
	public String Username;
	public String UrlString;
	public String UrlDomain;
	public String Title;
	public int Points;
	public int CommentCount;
	public String PostId;
	public String TimeCreatedString;
	
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
			HNPost newPost = new HNPost();
			
			OMScanner scanner = new OMScanner(htmlComponents.get(xx));
			
			//Scan Url
			scanner.skipToString("<a href=\"");
			newPost.UrlString = scanner.scanToString("\">");
			
			//Scan Title
			newPost.Title = scanner.scanToString("</a>");
			
			//Scan Points
			scanner.skipToString("<span id=score_");
			scanner.skipToString(">");
			newPost.Points = Integer.parseInt(scanner.scanToString(" points"));
			
			//Scan Author
			scanner.skipToString("<a href=\"user?id=");
			scanner.skipToString(">");
			newPost.Username = scanner.scanToString("</a> ");
			
			//Scan Time Ago
			scanner.scanToString("  |");
			
			//Scan Number of Comments
			scanner.skipToString("<a href=\"item?id=");
			newPost.PostId = scanner.scanToString("\">");
			//Scan over the comment string to get number of comments (0 if discuss)
			String commentString = scanner.scanToString("</a>");
			if (commentString.equals("discuss")) {
				newPost.CommentCount = 0;
			} else {
				OMScanner commentScanner = new OMScanner(commentString);
				newPost.CommentCount = Integer.parseInt(commentScanner.scanToString(" "));
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
		
			if (xx == htmlComponents.size() - 1) {
				scanner.skipToString("<td class=\"title\"><a href=\"");
				HNManager.getInstance().postFNID = scanner.scanToString("\"");
			}
	    
			postArray.add(newPost);
	    }
	    
		return postArray;
	}
}
