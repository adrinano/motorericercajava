/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package local;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import modello.ConcertoBean;
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
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class Shell {

    public static void main(String[] args) throws IOException, CorruptIndexException, ParseException{
        String sito = "http://www.liverock.it";
        String url = "/concerti.php";
        String path = "html_download";
        //HtmlFunction html= new HtmlFunction();
        //boolean create = html.getPage(path, url, 1);
        //html.htmlParser(sito,url);
        LinkedList<ConcertoBean> lista = new LinkedList<ConcertoBean>();

        ConcertoBean c1 = new ConcertoBean();
        ConcertoBean c2 = new ConcertoBean();
        ConcertoBean c3 = new ConcertoBean();
        ConcertoBean c4 = new ConcertoBean();

        c1.setArtista("io");
        c2.setArtista("me");
        c3.setArtista("te");
        c4.setArtista("egli");
        lista.add(c1);
        lista.add(c2);
        lista.add(c3);
        lista.add(c4);

        IndexFunction index = new IndexFunction();
        index.indicizza(lista, true);

        //File fileDir = new File(path);
        //System.out.println(fileDir.isDirectory());
        //File file = fileDir.listFiles()[0];

        //HtmlParser htmlparser = new HtmlParser();
        //htmlparser.Parser(file, 1);

        //index.addDoc(fileDir, true);

        // 3. search
        //index.search("content", "Lazio");

    }

}
