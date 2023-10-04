package org.example;

import org.example.Indexer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH = 2;
    Crawler(){
        urlSet = new HashSet<>();

    }

    public void getPgeTextsAndLinks(String url, int depth) {
        if (urlSet.contains(url)) {
            return;
        }
        if (urlSet.add(url)){
            System.out.println(url);
        }
        if (depth >=     MAX_DEPTH) {
            return;
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get(); // converts the url (html page to java object)
            //indexer work starts here

            Indexer indexer =  new Indexer(document, url);
            System.out.println(document.title());

            Elements availableLinkOnPage = document.select("a[href]");
            // in html <a>-anchor tag , this creates hyperlink
            //This line uses Jsoup's select method to find all the anchor tags (<a>) with
            // href attributes on the current web page. The result is stored
            // in the availableLinkOnPage variable, which is an instance of
            // Jsoup's Elements class. This collection contains all the anchor
            // tags found on the page.
            for (Element currentLink : availableLinkOnPage) {  //loop iterates over each <a>with an href attribute.
                getPgeTextsAndLinks(currentLink.attr("abs:href"), depth);
                //This extracts the value of the href attribute from the current <a> element and
                // ensure that the URL is an absolute URL and continues the crawling process for the linked page
            }
            //In summary, this portion of the code iterates through all the anchor tags on the current web page,
            // extracts their absolute URLs, and then recursively crawls the linked pages using the
            // getPgeTextsAndLinks method. This process allows the web crawler to explore multiple pages by
            // following the links on each page it visits.


        } catch (IOException ioException) {
            ioException.printStackTrace(); //This method prints information about the exception to the standard error stream,
            // which is typically displayed on the console.
        }
    }

    public static void main(String[] args) {
       Crawler crawler = new Crawler();
       crawler.getPgeTextsAndLinks("https://www.javatpoint.com", 0);
    }
}