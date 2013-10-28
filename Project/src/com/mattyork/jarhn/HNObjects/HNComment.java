package com.mattyork.jarhn.HNObjects;

import java.util.ArrayList;

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
	ArrayList<Object> Links;
	
	public static ArrayList<HNComment> parsedCommentsFromHTML(String htmlString, HNPost post) {
		
		return new ArrayList<HNComment>();
	}
}
