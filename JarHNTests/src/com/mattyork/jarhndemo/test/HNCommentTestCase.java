package com.mattyork.jarhndemo.test;

import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

import android.util.Log;

import com.mattyork.jarhn.HNUtilities;
import com.mattyork.jarhn.HNObjects.HNComment;
import com.mattyork.jarhn.HNObjects.HNComment.CommentType;
import com.mattyork.jarhn.HNObjects.HNCommentLink;

public class HNCommentTestCase extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParseComment() throws Throwable {
		
		String testString = "<tr><td><table border=\"0\"><tbody><tr><td>" +
				"<img src=\"s.gif\" height=\"1\" width=\"0\"></td><td valign=\"top\"><center>" +
				"<a id=\"up_6784229\" href=\"vote?for=6784229&amp;dir=up&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%33%36%31%37\">" +
				"<div class=\"votearrow\" title=\"upvote\"></div></a>" +
				"<span id=\"down_6784229\"></span></center></td><td class=\"default\">" +
				"<div style=\"margin-top:2px; margin-bottom:-10px; \"><span class=\"comhead\">" +
				"<a href=\"user?id=tks2103\">tks2103</a> 17 minutes ago  | <a href=\"item?id=6784229\">link</a>" +
				"</span></div><br><span class=\"comment\"><font color=\"#000000\">Turntable dunked all other music streaming sites in playlist quality for the simple reason that the intelligence behind song selection resided in humans rather than an algorithm.<p>It was a specific application of the general idea that social recommendations are more powerful than algorithmic recommendations. I don't know how valid the general idea is, but when it came to Turntable, it worked. People search the space of musical works much more effectively than algorithms.</p><p>And then Turntable did nothing with that power. They had high quality playlists being minted by the thousands on a daily basis, and I could not access any of them.</p><p>At least for a time, they were focused on live engagement. Unfortunately, it's just too hard to grow a music streaming product that demands each listener be present to click a button for every song. I loved the service, but I also have a job. I cannot be on the site all day long. At the risk of being presumptuous, I would guess that the product leaders were blinded by their devotion to live engagement.</p><p>Focusing instead on their brilliant, yet simple, solution to the problem of \"What song should go on next?\" would have been the right call. But they did not walk this path.</p><p>As for their new product, which predictably focuses more intently on the live engagement aspect, I am unconvinced. Turntable Live provides a subset of the functionality that Twitch.tv does, with less reach.</p><p>Best of luck to them. No doubt, they are an extremely talented team.</p></font><p><font color=\"#000000\">And I'm really, really going to miss Turntable. Kickass idea. (yeah yeah plug.dj, whatever)</font></p><p>" +
				"<font size=\"1\"><u><a href=\"reply?id=6784229&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%33%36%31%37\">reply</a></u></font>" +
				"</p></span></td></tr></tbody></table></td></tr>";
		
		HNComment expectedComment = new HNComment();
		expectedComment.Level = 0;
		expectedComment.CommentId = "6784229";
		expectedComment.Links = new ArrayList<HNCommentLink>();
		expectedComment.ParentID = "";
		expectedComment.ReplyURLString = "reply?id=6784229&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%33%36%31%37";
		expectedComment.Text = "Turntable dunked all other music streaming sites in playlist quality for the simple reason that the intelligence behind song selection resided in humans rather than an algorithm.<p>It was a specific application of the general idea that social recommendations are more powerful than algorithmic recommendations. I don't know how valid the general idea is, but when it came to Turntable, it worked. People search the space of musical works much more effectively than algorithms.</p><p>And then Turntable did nothing with that power. They had high quality playlists being minted by the thousands on a daily basis, and I could not access any of them.</p><p>At least for a time, they were focused on live engagement. Unfortunately, it's just too hard to grow a music streaming product that demands each listener be present to click a button for every song. I loved the service, but I also have a job. I cannot be on the site all day long. At the risk of being presumptuous, I would guess that the product leaders were blinded by their devotion to live engagement.</p><p>Focusing instead on their brilliant, yet simple, solution to the problem of \"What song should go on next?\" would have been the right call. But they did not walk this path.</p><p>As for their new product, which predictably focuses more intently on the live engagement aspect, I am unconvinced. Turntable Live provides a subset of the functionality that Twitch.tv does, with less reach.</p><p>Best of luck to them. No doubt, they are an extremely talented team.</p></font><p><font color=\"#000000\">And I'm really, really going to miss Turntable. Kickass idea. (yeah yeah plug.dj, whatever)";
		expectedComment.Text = HNUtilities.stringByReplacingHTMLEntitiesInText(expectedComment.Text);
		expectedComment.TimeCreatedString = "17 minutes ago";
		expectedComment.Type = CommentType.CommentTypeDefault;
		expectedComment.Username = "tks2103";
		
		HNComment actualComment = HNComment.commentFromHTML(testString);
		
		compareComments(expectedComment, actualComment);
	}

	
	private void compareComments(HNComment expectedComment, HNComment actualComment) {
		Assert.assertEquals(expectedComment.Level, actualComment.Level);
		Assert.assertEquals(expectedComment.CommentId, actualComment.CommentId);
		//Assert.assertEquals(expectedComment.Links, actualComment.Links);
		Assert.assertEquals(expectedComment.ParentID, actualComment.ParentID);
		Assert.assertEquals(expectedComment.ReplyURLString, actualComment.ReplyURLString);
		Log.i("Expected", expectedComment.Text);
		Log.i("Actual", actualComment.Text);
		Assert.assertEquals(expectedComment.Text, actualComment.Text);
		Assert.assertEquals(expectedComment.TimeCreatedString, actualComment.TimeCreatedString);
		Assert.assertEquals(expectedComment.Type, actualComment.Type);
		Assert.assertEquals(expectedComment.Username, actualComment.Username);
	}
}
