import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Document site;
    public static List<String> linktext = new ArrayList<String>();
    public static List<String> link = new ArrayList<String>();
    public static List<String> ranked = new ArrayList<String>();
    public static List<String> subReddit = new ArrayList<String>();
    static String URL = "http://www.reddit.com";


    public static void main(String[] args) throws IOException {
        String URL;
        URL = "http://www.reddit.com";
        System.out.println();
        System.out.println(" -- Reddit Scrape! -- ");
        checkFrontPage();
        connect(URL);
        getContent(site);
        getRanks();
        getSubReddits();
        printData();
    }

    public static void checkFrontPage() throws IOException {

        if (URL == "http://www.reddit.com")
        {
            System.out.println("Front Page Detected.");
            connect(URL);
            getContent(site);
            getRanks();
            getSubReddits();
            printDataFrontPage();
        }
    }

    private static void getSubReddits() {
        Elements subReddits = site.getElementsByClass("tagline");
        {
        for (Element  subreddits : subReddits)
            {
                subReddit.add(subreddits.text());
            }
        }

    }

    private static void printData() {
        System.out.println("Printing Data now...\n");
        for (int i = 0; i < ranked.size(); i++)
        {
            System.out.print("Rank: " + ranked.get(i)+ "\n");
            System.out.println("--------");
            System.out.print("Title: " + linktext.get(i) + "\n");
            System.out.print("Link: " + "[" + link.get(i) + "]" + "\n");
            System.out.println("User/SubReddit: " +subReddit.get(i)+ "\n");
        }
        linktext.clear();
        link.clear();
        ranked.clear();
        subReddit.clear();
        choice();
    }

    private static void printDataFrontPage()
    {
        System.out.println("Printing Data now...\n");
        for (int i = 0; i < ranked.size(); i++)
        {
            System.out.print("Rank: " + ranked.get(i)+ "\n");
            System.out.println("--------");
            System.out.print("Title: " + linktext.get(9 + i) + "\n");
            System.out.print("Link: " + "[" + link.get(9 + i) + "]" + "\n");
            System.out.println("User/SubReddit: " +subReddit.get(9 + i)+ "\n");
        }
        linktext.clear();
        link.clear();
        ranked.clear();
        subReddit.clear();
        choice();
    }

    public static void connect(String URL) throws IOException {
        site = Jsoup.connect(URL).get();
        System.out.println("This is the URL: " + URL);
        System.out.println("This is the title: " + (site.title()) + "\n");
    }

    public static void getContent(Document site) {
        Elements title = site.getElementsByClass("title");
        if (title.hasAttr("a[href]")) ;
        {
            for (Element titles : title) {
                /**This is controlling empty titles**/
                if (titles.attr("href") != "") {
                    if (titles.attr("href").contains("/r/"))
                    {
                        String tempLink = titles.attr("href");
                        link.add("http://www.reddit.com" + tempLink);
                    }
                    else
                    {
                    link.add(titles.attr("href"));
                    }
                    linktext.add(titles.text());
                }
            }
        }
    }

    public static void getStatistics() {
        //System.out.print("Statisitcs!");
    }

    public static void getRanks() {
        Elements rank = site.getElementsByClass("rank");
        for (Element ranks : rank) {
            if (ranks.text().isEmpty()) {
                //This controls empty ranking issue
            } else {
                ranked.add(ranks.text());
            }
        }
    }

    public static void choice () {
        System.out.println("--------");
        System.out.println("");
        System.out.println("|----------------------|");
        System.out.println("| 1. Reload.           |");
        System.out.println("| 2. Change SubReddit. |");
        System.out.println("| 3. Exit.             |");
        System.out.println("|----------------------|");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String selection = null;
        try {
            selection = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your choice!");
            System.exit(1);
        }
        if (Integer.parseInt(selection) == 1)
        {
            System.out.println();
            System.out.println(" -- Reddit Scrape! -- ");
            try {
                connect(URL);
            } catch (IOException e) {
                e.printStackTrace();
                choice();
            }
            getContent(site);
            getRanks();
            getSubReddits();
            printData();
        }
        if (Integer.parseInt(selection) == 2)
        {
            System.out.println("Which subreddit Subreddit?");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
            String subreddit = null;
            try {
                subreddit = br2.readLine();
            } catch (IOException ioe) {
                System.out.println("IO error trying to read your subreddit!");
                System.exit(1);
            }
            String reddit = "http://www.reddit.com/r/";
            URL = null;
            URL = reddit + subreddit;
            if (subreddit.isEmpty())
            {
                URL = "http://www.reddit.com";
            }
            linktext.clear();
            link.clear();
            ranked.clear();
            subReddit.clear();
            try {
                connect(URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /** This is the URL tried **/
            //System.out.println("Tried : " + URL);
            getContent(site);
            getRanks();
            getSubReddits();
            printData();
        }

        if (Integer.parseInt(selection) == 3)
        {
           System.exit(0);
        }

        else
        {
            System.out.println("Enter a listed option.");
            choice();
        }
    }
}