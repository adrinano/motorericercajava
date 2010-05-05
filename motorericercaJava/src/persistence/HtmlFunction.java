/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.IOException;

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

}
