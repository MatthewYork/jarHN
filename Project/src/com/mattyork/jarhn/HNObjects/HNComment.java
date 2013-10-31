package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mattyork.jarhn.HNUtilities;
import com.mattyork.jarhn.OMScanner;
import com.mattyork.jarhn.HNObjects.HNPost.PostType;

public class HNComment {
	public enum CommentType {
		CommentTypeDefault, CommentTypeAskHN, CommentTypeJobs
	}

	CommentType Type;
	String Text;
	String Username;
	String CommentId;
	String ParentID;
	String TimeCreatedString;
	String ReplyURLString;
	int Level;
	ArrayList<HNCommentLink> Links;
	
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
			OMScanner scanner = new OMScanner(htmlComponents.get(xx));
			HNComment newComment = new HNComment();
	        String level = "";
	        String user = "";
	        String text = "";
	        String reply = "";
	        String commentId = "";
			
	        //Get Comment Level
	        scanner.skipToString("height=1 width=");
	        newComment.Level = Integer.parseInt(scanner.scanToString(">"))/40;
	        
	        //Get Username
	        scanner.skipToString("<a href=\"user?id=");
	        user = scanner.scanToString("\">");
	        if (user.length() == 0) {
				newComment.Username = "[deleted]";
			}
	        else {
	        	newComment.Username = user;
	        }
	        
	        //Get Date/Time
	        scanner.skipToString("</a> ");
	        newComment.TimeCreatedString = scanner.scanToString(" |");
	        
	        //Get Comment Text
	        scanner.skipToString("<font color=");
	        scanner.skipToString(">");
	        text =  scanner.scanToString("</font>");
	        newComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(text);
	        
	        //Get CommentId
	        scanner.skipToString("reply?id=");
	        newComment.CommentId = scanner.scanToString("&");
	        
	        //Get Reply URL Addition
	        scanner.skipToString("<font size=1><u><a href=\"");
	        newComment.ReplyURLString = scanner.scanToString("\">reply");
	        
	        //Get Links
	        newComment.Links = HNCommentLink.linksFromCommentText(newComment.Text);
	        
	        //Add comment to array
	        comments.add(newComment);
		}
		
		return comments;
	}
}
