/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import modello.DocumentoBean;
import modello.SitoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistence.HtmlFunction;
import persistence.IndexFunction;
import persistence.XMLFunction;


/**
 *
 * @author palla
 */
public class IndexThread implements Runnable{

    final String siteXML = "webapps/sherlockTux/WEB-INF/config/siteUrl.xml";
    private boolean inExecution;

    public IndexThread(){

        this.inExecution = false;
        
    }



    @Override
    public void run(){

        System.out.println("Run Thread start");
        inExecution = true;
        
        eseguiIndex();

        try{
            Thread.sleep(10000);
        }catch (InterruptedException ie){
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ie);
        }

        inExecution = false;
        System.out.println("Run Thread finish");
        
    }

    public void eseguiIndex(){
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

        XMLFunction xml = new XMLFunction();
        List<SitoBean> siteList = xml.getSiteList();
        LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();
        Iterator<SitoBean> iterator = siteList.iterator();
        while (iterator.hasNext()){
            try {
                SitoBean sb = iterator.next();
                System.out.println("Materia che indicizza: " + sb.getMateria());
                indexing(sb, index, html, listaDocumenti);
            } catch (IOException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean getInExecution (){
        return this.inExecution;
    }

    private static void indexing(SitoBean sb, IndexFunction index, HtmlFunction html, LinkedList<DocumentoBean> listaDocumenti) throws IOException, URISyntaxException {

        List<URL> listaPDF = html.getURLList(sb.getUrl(), ".pdf");
        for (int i = 0; i < listaPDF.size(); i++) {
            DocumentoBean pdf = html.getPDFinfo(sb, listaPDF.get(i));
            if(pdf!=null){
                listaDocumenti.add(pdf);
            }
        }

        List<URL> listaPPT = html.getURLList(sb.getUrl(), ".ppt");
        for (int i = 0; i < listaPPT.size(); i++) {
            listaDocumenti.add(html.getPPTinfo(sb, listaPPT.get(i)));
        }

        index.indicizza(listaDocumenti);

    }

}
