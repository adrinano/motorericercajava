/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package local;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;
import persistence.HtmlFunction;
import persistence.HtmlParser;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class Shell {

    public static void main(String[] args) throws IOException, CorruptIndexException, ParseException{
        String url = "http://www.liverock.it/concerti.php";
        String path = "html_download";

        HtmlFunction html= new HtmlFunction();
        //boolean create = html.getPage(path, url, 1);

        html.htmlParser(url);

        //IndexFunction index = new IndexFunction();
        File fileDir = new File(path);
        //System.out.println(fileDir.isDirectory());
        File file = fileDir.listFiles()[0];

        //HtmlParser htmlparser = new HtmlParser();
        //htmlparser.Parser(file, 1);

        //index.addDoc(fileDir, true);

        // 3. search
        //index.search("content", "Lazio");

    }

}
