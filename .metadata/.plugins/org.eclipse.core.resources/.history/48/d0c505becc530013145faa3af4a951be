package com.mattyork.jarhndemo.test;

import com.mattyork.jarhn.HNObjects.HNPost;
import com.mattyork.jarhn.HNObjects.HNPost.PostType;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HNPostTestCase extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	
	public void testParseStandardPost() throws Throwable {

		String testPost = "<tr><td align=\"right\" valign=\"top\" class=\"title\">11.</td>"
				+ "<td><center><a id=\"up_6776753\" href=\"vote?for=6776753&amp;dir=up&amp;whence=%6e%65%77%73\">"
				+ "<div class=\"votearrow\" title=\"upvote\"></div></a>"
				+ "<span id=\"down_6776753\"></span></center></td><td class=\"title\">"
				+ "<a href=\"http://techcrunch.com/2013/11/21/source-microsoft-in-talks-to-buy-shoutcast-and-winamp-from-aol/\">"
				+ "Microsoft In Talks To Buy Shoutcast And Winamp From AOL</a><span class=\"comhead\">"
				+ " (techcrunch.com) </span></td></tr><tr><td colspan=\"2\"></td><td class=\"subtext\">"
				+ "<span id=\"score_6776753\">99 points</span> by <a href=\"user?id=Kopion\">Kopion</a>"
				+ " 6 hours ago  | <a href=\"item?id=6776753\">59 comments</a></td></tr>";

		// Create desired post
		HNPost expectedPost = new HNPost();
		expectedPost.Title = "Microsoft In Talks To Buy Shoutcast And Winamp From AOL";
		expectedPost.UrlString = "http://techcrunch.com/2013/11/21/source-microsoft-in-talks-to-buy-shoutcast-and-winamp-from-aol/";
		expectedPost.Points = 99;
		expectedPost.CommentCount = 59;
		expectedPost.PostId = "6776753";
		expectedPost.Type = PostType.PostTypeDefault;
		expectedPost.UrlDomain = "(techcrunch.com)";
		expectedPost.Username = "Kopion";

		// Parse actual post
		HNPost actualPost = HNPost.postFromHTML(testPost);

		// Compare posts
		compareTwoPosts(expectedPost, actualPost);
	}

	public void testParseAskHNWithoutCommentsOrExplicitTitle() throws Throwable {
		String testPost = "<tr><td align=\"right\" valign=\"top\" class=\"title\">1.</td>"
				+ "<td><center><a id=\"up_6778868\" href=\"vote?for=6778868&amp;dir=up&amp;whence=%61%73%6b\">"
				+ "<div class=\"votearrow\" title=\"upvote\"></div></a><span id=\"down_6778868\"></span>"
				+ "</center></td><td class=\"title\">"
				+ "<a href=\"item?id=6778868\">Chef, Puppet, Salt, Ansible. What do you use for server setup and deployment?</a></td>"
				+ "</tr><tr><td colspan=\"2\"></td><td class=\"subtext\">"
				+ "<span id=\"score_6778868\">6 points</span> by <a href=\"user?id=alfor\">alfor</a>"
				+ " 26 minutes ago  | <a href=\"item?id=6778868\">discuss</a></td></tr>";

		// Create desired post
		HNPost expectedPost = new HNPost();
		expectedPost.Title = "Chef, Puppet, Salt, Ansible. What do you use for server setup and deployment?";
		expectedPost.UrlString = "item?id=6778868";
		expectedPost.Points = 6;
		expectedPost.CommentCount = 0;
		expectedPost.PostId = "6778868";
		expectedPost.Type = PostType.PostTypeAskHN;
		expectedPost.UrlDomain = "";
		expectedPost.Username = "alfor";
		expectedPost.TimeCreatedString = "26 minutes ago";

		// Parse actual post
		HNPost actualPost = HNPost.postFromHTML(testPost);

		// Compare posts
		compareTwoPosts(expectedPost, actualPost);
	}
	
	public void testParseAskHNWithCommentsAndExplicitTitle() {
		String testPost = "<tr><td align=\"right\" valign=\"top\" class=\"title\">4.</td>" +
				"<td><center><a id=\"up_6778810\" href=\"vote?for=6778810&amp;dir=up&amp;whence=%61%73%6b\">" +
				"<div class=\"votearrow\" title=\"upvote\"></div></a><span id=\"down_6778810\"></span>" +
				"</center></td><td class=\"title\">" +
				"<a href=\"item?id=6778810\">Ask HN: Which Has Better Tech Scene - Baltimore Vs DC Vs VA?</a></td>" +
				"</tr><tr><td colspan=\"2\"></td><td class=\"subtext\">" +
				"<span id=\"score_6778810\">2 points</span> by <a href=\"user?id=davidsmith8900\">davidsmith8900</a>" +
				" 48 minutes ago  | <a href=\"item?id=6778810\">2 comments</a></td></tr>";

		// Create desired post
		HNPost expectedPost = new HNPost();
		expectedPost.Title = "Ask HN: Which Has Better Tech Scene - Baltimore Vs DC Vs VA?";
		expectedPost.UrlString = "item?id=6778810";
		expectedPost.Points = 2;
		expectedPost.CommentCount = 2;
		expectedPost.PostId = "6778810";
		expectedPost.Type = PostType.PostTypeAskHN;
		expectedPost.UrlDomain = "";
		expectedPost.Username = "davidsmith8900";
		expectedPost.TimeCreatedString = "48 minutes ago";

		// Parse actual post
		HNPost actualPost = HNPost.postFromHTML(testPost);

		// Compare posts
		compareTwoPosts(expectedPost, actualPost);
	}
	

	
	public void testParseJobsWithoutDomain() {
		
		String testPost = "<tr><td align=\"right\" valign=\"top\" class=\"title\">1.</td>" +
				"<td></td><td class=\"title\"><a href=\"item?id=6778635\">Balanced is hiring #8</a></td></tr>" +
				"<tr><td colspan=\"2\"></td><td class=\"subtext\">2 hours ago</td></tr>";
		
		// Create desired post
		HNPost expectedPost = new HNPost();
		expectedPost.Title = "Balanced is hiring #8";
		expectedPost.UrlString = "item?id=6778635";
		expectedPost.Points = 0;
		expectedPost.CommentCount = 0;
		expectedPost.PostId = "6778635";
		expectedPost.Type = PostType.PostTypeJobs;
		expectedPost.UrlDomain = "";
		expectedPost.Username = "";
		expectedPost.TimeCreatedString = "2 hours ago";

		// Parse actual post
		HNPost actualPost = HNPost.postFromHTML(testPost);

		// Compare posts
		compareTwoPosts(expectedPost, actualPost);
	}

	private void compareTwoPosts(HNPost expectedPost, HNPost actualPost) {
		Assert.assertEquals(expectedPost.Title, actualPost.Title);
		Assert.assertEquals(expectedPost.UrlString, actualPost.UrlString);
		Assert.assertEquals(expectedPost.Points, actualPost.Points);
		Assert.assertEquals(expectedPost.CommentCount, actualPost.CommentCount);
		Assert.assertEquals(expectedPost.PostId, actualPost.PostId);
		Assert.assertEquals(expectedPost.Type.ordinal(),
				actualPost.Type.ordinal());
		Assert.assertEquals(expectedPost.UrlDomain, actualPost.UrlDomain);
		Assert.assertEquals(expectedPost.Username, actualPost.Username);
	}
}
