package com.mattyork.jarhn;

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
	    
	    //Replace <a> tags
	    OMScanner scanner = new OMScanner(text);
	    String goodText = "", runningString = "";
	    
	    while (scanner.hasNext()) {
			if (scanner.getScanString().substring(scanner.getScanIndex()).contains("<a href")) {
				goodText = scanner.scanToString("<a href=");
				runningString = runningString + goodText;
				scanner.skipToString("<a href=\"");
				goodText = scanner.scanToString("\"");
				runningString = runningString + goodText;
				scanner.skipToString("</a>");
			}
			else {
				runningString = runningString + scanner.getScanString().substring(scanner.getScanIndex());
				scanner.setScanIndex(text.length());
			}
		}
		
		return runningString;
	}
}
