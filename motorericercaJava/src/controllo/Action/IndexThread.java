/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.DocumentoBean;
import modello.SitoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;
import persistence.HtmlFunction;
import persistence.IndexFunction;
import persistence.XMLFunction;


/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
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
        
        eseguiUpdate();

        try{
            Thread.sleep(10000);
        }catch (InterruptedException ie){
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ie);
        }

        inExecution = false;
        System.out.println("Run Thread finish");
        
    }

    public void eseguiUpdate(){
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
                System.out.println("Update della materia: " + sb.getMateria());
                index.update(getDocumentBeanList(sb, html, listaDocumenti));
            } catch (IOException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * esegue l'indicizzazione
     */
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
                index.indicizza(getDocumentBeanList(sb, html, listaDocumenti));
            } catch (IOException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @return
     */
    public boolean getInExecution (){
        return this.inExecution;
    }

    /**
     *Ritorna i documenti da indizizzare o con i quali fare l'update
     * @param sb
     * @param index
     * @param html
     * @param listaDocumenti
     * @throws IOException
     * @throws URISyntaxException
     */
    private static LinkedList<DocumentoBean> getDocumentBeanList(SitoBean sb, HtmlFunction html, LinkedList<DocumentoBean> listaDocumenti) throws IOException, URISyntaxException {

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

        //index.indicizza(listaDocumenti);
        return listaDocumenti;

    }

}
