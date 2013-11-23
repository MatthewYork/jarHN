package com.mattyork.jarhn;

import android.R.anim;
import android.R.integer;

public class HNUtilities {

	public static String stringByReplacingHTMLEntitiesInText(String text) {
		text = text.replace("<p>", "\n\n");
		text = text.replace("</p>", "");
		text = text.replace("<i>", "");
		text = text.replace("</i>", "");
		text = text.replace("&#38;", "&");
		text = text.replace("&#62;", ">");
		text = text.replace("&#x27;", "'");
		text = text.replace("&#x2F;", "/");
		text = text.replace("&quot;", "\"");
		text = text.replace("&#60;", "<");
		text = text.replace("&lt;", "<");
		text = text.replace("&gt;", ">");
		text = text.replace("&amp;", "&");
		text = text.replace("<pre><code>", "");
		text = text.replace("</code></pre>", "");

		// Replace <a> tags
		OMScanner scanner = new OMScanner(text);
		String goodText = "", runningString = text;
		int contentIndex = -1;

		int beginningContentIndex = text.indexOf("<a href=");
		int endContentIndex = text.indexOf("</a>");

		if (beginningContentIndex != -1 && endContentIndex != -1) {
			String linkSubString = text.substring(beginningContentIndex,
					endContentIndex);
			text = text.replace(" rel=\"nofollow\">", "");
			text = text.replace("<a href=", "");
			text = text.replace("</a>", "");
			text = text.replace("</font>", "");
			
			 int startQuoteIndex = linkSubString.indexOf("\""); 
			 int endQuoteIndex = linkSubString.lastIndexOf("\""); 
			 
			 if (startQuoteIndex != -1 && endQuoteIndex != -1) {
				linkSubString = linkSubString.substring(startQuoteIndex,
						endQuoteIndex);
				text = text.replace(linkSubString, "");
			}
			

		}

		return text;
	}
}
