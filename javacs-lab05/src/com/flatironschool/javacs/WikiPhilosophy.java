package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {
	
	
	final static List<String> visited = new ArrayList<String>();
	final static WikiFetcher wf = new WikiFetcher();

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 * 
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 * 
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
        // some example code to get you started

		String destination = "https://en.wikipedia.org/wiki/Philosophy";
		String source = "https://en.wikipedia.org/wiki/Java_(programming_language)";
		
		testConjecture(destination, source, 8);

        // the following throws an exception so the test fails
        // until you update the code
	}

	public static void testConjecture(String destination, String source, int limit) throws IOException {
		String url = source;
		for(int i = 0; i<limit; i++) {
			if(visited.contains(url)) {
				System.err.println("Exiting because Loop");
				return;
			}
			else {
				visited.add(url);
			}
			Element elt = getFirstValidLink(url);
			if(elt == null) {
				System.err.println("No valid Links");
				return;
			}

			System.out.println("**" + elt.text() + "**");
			url = elt.attr("abs:href");
			if(url.equals(destination)) {
				System.out.println("Found");
				break;
			}



		}
	}

	public static Element getFirstValidLink(String url) throws IOException {
		int num = 0;
		System.out.println("Fetching..." + num);
		Elements paragraphs = wf.fetchWikipedia(url);
		WikiParser wp = new WikiParser(paragraphs);
		Element elt = wp.findFirstLink();
		num ++ ;
		return elt;
	}
}
