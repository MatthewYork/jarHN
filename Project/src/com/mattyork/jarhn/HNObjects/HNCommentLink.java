package com.mattyork.jarhn.HNObjects;

import java.sql.Struct;
import java.util.ArrayList;

import android.net.Uri;

public class HNCommentLink {
	public enum LinkType {
		LinkTypeDefault, LinkTypeHN
	}

	Uri Uri;
	HNRange UriRange;
	LinkType Type;
	
	/***
	 * Creates an ArrayList of link objects from a given comment string.
	 * @param commentString
	 * @return ArrayList<HNCommentLink>
	 */
	public static ArrayList<HNCommentLink> linksFromCommentText(
			String commentString) {
		return new ArrayList<HNCommentLink>();
	}
}
