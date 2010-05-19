/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.DocumentoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;
import persistence.HtmlFunction;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class IndexThread extends TimerTask{

    public IndexThread(){
        
    }

    @Override
    public void run(){

        
        HtmlFunction html = new HtmlFunction();
        IndexFunction index = null;
        try {
            index = new IndexFunction();
        } catch (CorruptIndexException ex) {
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<URL> urlist = getURLList();
        LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();
        Iterator<URL> iterator = urlist.iterator();

        while (iterator.hasNext()){
            try {
                esegui(iterator.next(), index, html, listaDocumenti);
            } catch (IOException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private static void esegui(URL url, IndexFunction index, HtmlFunction html, LinkedList<DocumentoBean> listaDocumenti) throws IOException, URISyntaxException {

        List<URL> listaPDF = html.getURLList(url, ".pdf");
        for (int i = 0; i < listaPDF.size(); i++) {
            listaDocumenti.add(html.getPDFinfo(listaPDF.get(i)));
        }

        List<URL> listaPPT = html.getURLList(url, ".ppt");
        for (int i = 0; i < listaPPT.size(); i++) {
            listaDocumenti.add(html.getPPTinfo(listaPPT.get(i)));
        }

        index.indicizza(listaDocumenti);

    }

    private List<URL> getURLList() {
        List<URL> lst = new LinkedList();
        try {
            BufferedReader in = new BufferedReader(new FileReader("config/siteUrl"));
            String str;
            while ((str = in.readLine()) != null) {
                lst.add(new URL (str));
            }
            in.close();
        } catch (IOException e) {
        }

        return lst;
    }
}
