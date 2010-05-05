/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author palla
 */
public class HtmlFunction {

    /**
     * prendere la pagina web e restituirla di modo da indicizzarla con lucene
     */
    @SuppressWarnings("empty-statement")
    public boolean getPage(String path, String url, int number) throws IOException{

        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(url);
        File fileToSave = new File(path + File.separator +"page_" + number + ".html");

        if (fileToSave.exists()){
            fileToSave.delete();
        }

        page.save(fileToSave);


        return true;

    }

    /**
     *
     * @param file
     * @param number
     */
    public void htmlParser(String uri) throws IOException{
        try {
            URL url = new URL(uri);
            WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
            HtmlPage page = (HtmlPage) wc.getPage(url);
            List<HtmlAnchor> anchors = page.getAnchors();

            for (int i=0; i<anchors.size(); i++){
                if (anchors.get(i).getTextContent().contains("Lazio")){
                    System.out.println("Anchor " + i + " : " + anchors.get(i).getTextContent());
                    System.out.println(anchors.get(i).getHrefAttribute());
                }
            }


        } catch (MalformedURLException ex) {
            Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
