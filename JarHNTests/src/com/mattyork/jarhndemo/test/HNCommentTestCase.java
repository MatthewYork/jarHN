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

	public void testParseCommentTopLevel() throws Throwable {

		String testString = "height=1 width=0></td><td valign=top><center>"
				+ "<a id=up_6786641 href=\"vote?for=6786641&amp;dir=up&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%31%38%35\">"
				+ "<div class=\"votearrow\" title=\"upvote\"></div></a><span id=down_6786641></span></center></td>"
				+ "<td class=\"default\"><div style=\"margin-top:2px; margin-bottom:-10px; \">"
				+ "<span class=\"comhead\"><a href=\"user?id=mchannon\">mchannon</a> 2 hours ago  | "
				+ "<a href=\"item?id=6786641\">link</a></span></div><br><span class=\"comment\">"
				+ "<font color=#000000>The spelling with more legitimacy is Aluminum.<p>Sir Humphrey Davy, its discoverer, called it Aluminum when he published his work Chemical Philosophy in 1812.<p>Some anonymous dilettante (anonymous to this day!) thought, upon reviewing said book, it sounded better (more classical) with the extra I and syllable.<p>Everybody calls Aluminum&#x27;s oxide Alumina, not Aluminia.<p>Nobody else seems to feel the need to rename Platinum Platinium, or Molybdenum Molybdenium, or Tantalum Tantalium.<p>We Americans too often like to mock other cultures for being different, and our basis is usually wrong when we do.  Maybe mocking our pronunciation and spelling isn&#x27;t a habit you want to hold on to.</font></span><p><font size=1><u>"
				+ "<a href=\"reply?id=6786641&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%31%38%35\">reply</a></u></font></td></tr></table></td></tr>";

		HNComment expectedComment = new HNComment();
		expectedComment.Level = 0;
		expectedComment.CommentId = "6786641";
		expectedComment.Links = new ArrayList<HNCommentLink>();
		expectedComment.ParentID = "";
		expectedComment.ReplyURLString = "reply?id=6786641&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%31%38%35";
		expectedComment.Text = "The spelling with more legitimacy is Aluminum.<p>Sir Humphrey Davy, its discoverer, called it Aluminum when he published his work Chemical Philosophy in 1812.<p>Some anonymous dilettante (anonymous to this day!) thought, upon reviewing said book, it sounded better (more classical) with the extra I and syllable.<p>Everybody calls Aluminum&#x27;s oxide Alumina, not Aluminia.<p>Nobody else seems to feel the need to rename Platinum Platinium, or Molybdenum Molybdenium, or Tantalum Tantalium.<p>We Americans too often like to mock other cultures for being different, and our basis is usually wrong when we do.  Maybe mocking our pronunciation and spelling isn&#x27;t a habit you want to hold on to.";
		expectedComment.Text = HNUtilities
				.stringByReplacingHTMLEntitiesInText(expectedComment.Text);
		expectedComment.TimeCreatedString = "2 hours ago";
		expectedComment.Type = CommentType.CommentTypeDefault;
		expectedComment.Username = "mchannon";

		HNComment actualComment = HNComment.commentFromHTML(testString);

		compareComments(expectedComment, actualComment);
	}

	public void testParseCommentNested() throws Throwable {

		String testString = "height=1 width=80></td><td valign=top><center>" +
				"<a id=up_6786532 href=\"vote?for=6786532&amp;dir=up&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%32%33%39\">" +
				"<div class=\"votearrow\" title=\"upvote\"></div></a><span id=down_6786532></span></center></td>" +
				"<td class=\"default\"><div style=\"margin-top:2px; margin-bottom:-10px; \">" +
				"<span class=\"comhead\"><a href=\"user?id=tptacek\">tptacek</a> 3 hours ago  | " +
				"<a href=\"item?id=6786532\">link</a></span></div><br><span class=\"comment\">" +
				"<font color=#000000>Once we have dynamic pinning, we don&#x27;t need an elaborate distributed cryptographic ledger to solve this problem.</font></span><p><font size=1><u>" +
				"<a href=\"reply?id=6786532&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%32%33%39\">reply</a></u></font></td></tr></table></td></tr>";

		HNComment expectedComment = new HNComment();
		expectedComment.Level = 2;
		expectedComment.CommentId = "6786532";
		expectedComment.Links = new ArrayList<HNCommentLink>();
		expectedComment.ParentID = "";
		expectedComment.ReplyURLString = "reply?id=6786532&amp;whence=%69%74%65%6d%3f%69%64%3d%36%37%38%36%32%33%39";
		expectedComment.Text = "Once we have dynamic pinning, we don&#x27;t need an elaborate distributed cryptographic ledger to solve this problem.";
		expectedComment.Text = HNUtilities
				.stringByReplacingHTMLEntitiesInText(expectedComment.Text);
		expectedComment.TimeCreatedString = "3 hours ago";
		expectedComment.Type = CommentType.CommentTypeDefault;
		expectedComment.Username = "tptacek";

		HNComment actualComment = HNComment.commentFromHTML(testString);

		compareComments(expectedComment, actualComment);
	}

	private void compareComments(HNComment expectedComment,
			HNComment actualComment) {
		Assert.assertEquals(expectedComment.Level, actualComment.Level);
		Assert.assertEquals(expectedComment.CommentId, actualComment.CommentId);
		// Assert.assertEquals(expectedComment.Links, actualComment.Links);
		Assert.assertEquals(expectedComment.ParentID, actualComment.ParentID);
		Assert.assertEquals(expectedComment.ReplyURLString,
				actualComment.ReplyURLString);
		Log.i("Expected", expectedComment.Text);
		Log.i("Actual", actualComment.Text);
		Assert.assertEquals(expectedComment.Text, actualComment.Text);
		Assert.assertEquals(expectedComment.TimeCreatedString,
				actualComment.TimeCreatedString);
		Assert.assertEquals(expectedComment.Type, actualComment.Type);
		Assert.assertEquals(expectedComment.Username, actualComment.Username);
	}
}
