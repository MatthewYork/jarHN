package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        contentIndex = htmlString.indexOf("height=1 width=");
        if (contentIndex != -1) {
        	scanner.setScanIndex(contentIndex + "height=1 width=".length());
            newComment.Level = Integer.parseInt(scanner.scanToString(">"))/40;
            scanner.setScanIndex(0);
		}
        
        
        //Get Username
        contentIndex = htmlString.indexOf("<a href=\"user?id=");
        if (contentIndex != -1) {
        	scanner.setScanIndex(contentIndex + "<a href=\"user?id=".length());
        	user = scanner.scanToString("\">");
            if (user.length() == 0) {
    			newComment.Username = "[deleted]";
    		}
            else {
            	newComment.Username = user;
            }
            scanner.setScanIndex(0);
		}
        
        //Get Date/Time
        contentIndex = htmlString.indexOf(newComment.Username+"</a>");
        if (contentIndex != -1) {
        	 scanner.setScanIndex(contentIndex +newComment.Username.length()+"</a> ".length());
             newComment.TimeCreatedString = scanner.scanToString("  |");
             scanner.setScanIndex(0);
		}
        else if (htmlString.indexOf(newComment.Username+"</font></a>") != -1) {
        	scanner.setScanIndex(htmlString.indexOf(newComment.Username+"</font></a>") +newComment.Username.length()+"</font></a> ".length());
            newComment.TimeCreatedString = scanner.scanToString("  |");
            scanner.setScanIndex(0);
		}
        
        //Get Comment Text
        newComment.Text = "";
        ArrayList<Integer> commentIndexes = HNComment.findIndexes(htmlString, "<span class=\"comment\"><font color=", scanner);
        for (Integer index : commentIndexes) {
        	if (index != -1) {
            	scanner.setScanIndex(index + "<span class=\"comment\"><font color=#000000>".length());
            	text =  scanner.scanToString("</font>");
            	
            	if (newComment.Text.equals("")) {
            		newComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(text);
				}
            	else {
            		newComment.Text = newComment.Text +"\n\n"+HNUtilities.stringByReplacingHTMLEntitiesInText(text);
            	}
                scanner.setScanIndex(0);
    		}
		}
        
        //Get Comment Id
        contentIndex = htmlString.indexOf("<a href=\"reply?id=");
        if (contentIndex != -1) {
			scanner.setScanIndex(contentIndex + "<a href=\"reply?id=".length());
			newComment.CommentId = scanner.scanToString("&");
	        
			//Get Reply Url String
			scanner.setScanIndex(contentIndex + "<a href=\"".length());
	        newComment.ReplyURLString = scanner.scanToString("\">");
		}
        
        //Get Links
        newComment.Links = HNCommentLink.linksFromCommentText(newComment.Text);
        
        newComment.ParentID = "";
        
        return newComment;
	}
	
	private static ArrayList<Integer> findIndexes(String baseString, String matchString, OMScanner scanner){
		scanner.setScanIndex(0);
		ArrayList<Integer> matches = new ArrayList<Integer>();
		
		Integer contentIndex = 0;
		while (contentIndex != -1) {
			contentIndex = baseString.substring(scanner.getScanIndex(), baseString.length()-1).indexOf(matchString);
			if (contentIndex != -1) {
				matches.add(contentIndex);
				scanner.setScanIndex(contentIndex + matchString.length());
			}
		}
		
	    return matches;
	}
}
