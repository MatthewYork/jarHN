package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.R.integer;

import com.mattyork.jarhn.HNUtilities;
import com.mattyork.jarhn.OMScanner;
import com.mattyork.jarhn.HNObjects.HNPost.PostType;

public class HNComment {
	public enum CommentType {
		CommentTypeDefault, CommentTypeAskHN, CommentTypeJobs
	}

	public CommentType Type;
	public String Text;
	public String Username;
	public String CommentId;
	public String ParentID;
	public String TimeCreatedString;
	public String ReplyURLString;
	public int Level;
	public ArrayList<HNCommentLink> Links;
	
	public static ArrayList<HNComment> parsedCommentsFromHTML(String htmlString, HNPost post) {
		ArrayList<HNComment> comments = new ArrayList<HNComment>();
		List<String> htmlComponents = Arrays.asList(htmlString.split("\\s*<tr><td><table border=0><tr><td><img src=\"s.gif\"\\s*"));
		
		if (post.Type == PostType.PostTypeAskHN) {
			//Grab AskHN Post
			OMScanner scanner = new OMScanner(htmlComponents.get(0));
			String text, user, timeAgo;
			
			//Scan User
			scanner.skipToString("<a href=\"user?id=");
			user = scanner.scanToString("\">");
			
			//Scan Time Ago
			scanner.skipToString("</a> ");
			timeAgo = scanner.scanToString("ago");
			
			//Scan Ask text
			scanner.skipToString("</tr><tr><td></td><td>");
			text = scanner.scanToString("</td>");
			
			//Create special comment for it
			HNComment newComment = new HNComment();
			newComment.Level = 0;
			newComment.Username = user;
			newComment.TimeCreatedString = timeAgo;
			newComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(text);
			newComment.Links = HNCommentLink.linksFromCommentText(newComment.Text);
			newComment.Type = CommentType.CommentTypeAskHN;
			comments.add(newComment);
		}
		
		if (post.Type == PostType.PostTypeJobs) {
			//Grave Jobs Post
			OMScanner scanner = new OMScanner(htmlComponents.get(0));
			scanner.skipToString("</tr><tr><td></td><td>");
			String text = scanner.scanToString("</td>");
			
			//Create special comment for it
			HNComment newComment = new HNComment();
			newComment.Level = 0;
			newComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(text);
			newComment.Links = HNCommentLink.linksFromCommentText(newComment.Text);
			newComment.Type = CommentType.CommentTypeJobs;
			comments.add(newComment);
		}
		
		for (int xx = 1; xx < htmlComponents.size(); xx++) {
			
			//Parse comment
			HNComment newComment = HNComment.commentFromHTML(htmlComponents.get(xx));
			
	        //Add comment to array
	        comments.add(newComment);
		}
		
		return comments;
	}
	
	public static HNComment commentFromHTML(String htmlString){
		//Create master scanner
		OMScanner scanner = new OMScanner(htmlString);
		
		//Create empty comment
		HNComment newComment = new HNComment();
		newComment.Type = CommentType.CommentTypeDefault;
        String level = "";
        String user = "";
        String text = "";
        String reply = "";
        String commentId = "";
		
        int contentIndex = -1;
        
        //Get Comment Level
        contentIndex = htmlString.indexOf("height=\"1\" width=\"");
        if (contentIndex != -1) {
        	scanner.setScanIndex(contentIndex + "height=\"1\" width=\"".length());
            newComment.Level = Integer.parseInt(scanner.scanToString("\">"))/40;
            scanner.setScanIndex(0);
		}
        
        
        //Get Username
        scanner.skipToString("<a href=\"user?id=");
        user = scanner.scanToString("\">");
        if (user.length() == 0) {
			newComment.Username = "[deleted]";
		}
        else {
        	newComment.Username = user;
        }
        scanner.setScanIndex(0);
        
        //Get Date/Time
        scanner.skipToString(newComment.Username+"</a> ");
        newComment.TimeCreatedString = scanner.scanToString("  |");
        scanner.setScanIndex(0);
        
        //Get Comment Text
        contentIndex = htmlString.indexOf("<span class=\"comment\"><font color=\"#000000\">");
        if (contentIndex != -1) {
        	scanner.setScanIndex(contentIndex + "<span class=\"comment\"><font color=\"#000000\">".length());
        	text =  scanner.scanToString("</font></p><p>");
            newComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(text);
            scanner.setScanIndex(0);
		}
        
        
        //Get CommentId
        scanner.skipToString("reply?id=");
        newComment.CommentId = scanner.scanToString("&");
        scanner.setScanIndex(0);
        
        //Get Reply URL Addition
        contentIndex = htmlString.indexOf("<font size=\"1\"><u><a href=\"");
        if (contentIndex != -1) {
			scanner.setScanIndex(contentIndex +"<font size=\"1\"><u><a href=\"".length());
			newComment.ReplyURLString = scanner.scanToString("\">reply");
	        scanner.setScanIndex(0);
		}
        
        //Get Links
        newComment.Links = HNCommentLink.linksFromCommentText(newComment.Text);
        
        newComment.ParentID = "";
        
        return newComment;
	}
}
