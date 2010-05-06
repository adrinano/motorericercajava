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
import java.util.ArrayList;
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
     * inizializzazione degli elementi della lista delle regioni
     * @return List
     */
    private List aggiungiRegioni() {
        List<String> listaReg = new ArrayList();
        listaReg.add("Lazio");
        listaReg.add("Svizzera");
        listaReg.add("Aosta");    //Valle d'Aosta
        listaReg.add("Piemonte");
        listaReg.add("Liguria");
        listaReg.add("Toscana");
        listaReg.add("Umbria");
        listaReg.add("Basilicata");
        listaReg.add("Marche");
        listaReg.add("Abruzzo");
        listaReg.add("Sardegna");
        listaReg.add("Puglia");
        listaReg.add("Lombardia");
        listaReg.add("Alto Adige"); //Trentino Alto Adige
        listaReg.add("Friuli Venezia Giulia");
        listaReg.add("Veneto");
        listaReg.add("Romagna"); //Emilia Romagna
        listaReg.add("Campania");
        listaReg.add("Calabria");
        listaReg.add("Molise");
        listaReg.add("Sicilia");
        return listaReg;
    }

    /**
     *
     * @param file
     * @param number
     */
    public void htmlParser(String sito, String uri) throws IOException{
        try {
            URL url = new URL(sito+uri);
            WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
            HtmlPage page = (HtmlPage) wc.getPage(url);
            List<HtmlAnchor> anchors = page.getAnchors();
            List<Integer> listaAncore = new ArrayList();    //lista delle ancore delle regioni
            List<String> listaRegioni = aggiungiRegioni();    //lista delle regioni

            for (int i=0; i<anchors.size(); i++){
                for (int r=0;r<listaRegioni.size();r++) {
                    if (anchors.get(i).getTextContent().contains(listaRegioni.get(r))){
                        //questi ci servono a vedere cosa prendiamo
                        System.out.println("Anchor " + i + " : " + anchors.get(i).getTextContent());
                        System.out.println(anchors.get(i).getHrefAttribute());
                        listaAncore.add(i);
                    }
                }
            }
            //System.out.println("dimensione lista: "+listaAncore.size());
            for (int j=0; j<listaAncore.size(); j++) {
                //questo ci serve a vedere cosa prendiamo
                System.out.println("indirizzo :"+ anchors.get(listaAncore.get(j)).openLinkInNewWindow().getWebResponse().getRequestSettings().getUrl());
            }


        } catch (MalformedURLException ex) {
            Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
