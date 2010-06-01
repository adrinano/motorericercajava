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


/**
 *
 * @author palla
 */
public class IndexThread implements Runnable{

    private boolean inExecution;

    public IndexThread(){

        this.inExecution = false;
        
    }



    @Override
    public void run(){

        System.out.println("Run Thread start");
        inExecution = true;
        
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

        List<SitoBean> siteList = getSitoBeanList();
        LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();
        Iterator<SitoBean> iterator = siteList.iterator();

        while (iterator.hasNext()){
            try {

                indexing(iterator.next(), index, html, listaDocumenti);
            } catch (IOException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try{
            Thread.sleep(10000);
        }catch (InterruptedException ie){
            Logger.getLogger(IndexThread.class.getName()).log(Level.SEVERE, null, ie);
        }

        inExecution = false;
        System.out.println("Run Thread finish");
        
    }

    public boolean getInExecution (){
        return this.inExecution;
    }

    private static void indexing(SitoBean sb, IndexFunction index, HtmlFunction html, LinkedList<DocumentoBean> listaDocumenti) throws IOException, URISyntaxException {

        List<URL> listaPDF = html.getURLList(sb.getUrl(), ".pdf");
        for (int i = 0; i < listaPDF.size(); i++) {
            listaDocumenti.add(html.getPDFinfo(sb, listaPDF.get(i)));
        }

        List<URL> listaPPT = html.getURLList(sb.getUrl(), ".ppt");
        for (int i = 0; i < listaPPT.size(); i++) {
            listaDocumenti.add(html.getPPTinfo(sb, listaPPT.get(i)));
        }

        index.indicizza(listaDocumenti);

    }

    private List<SitoBean> getSitoBeanList() {
        List<SitoBean> lst = new LinkedList();
        SitoBean sb = new SitoBean();

        try {
            File file = new File("config/siteUrl.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            NodeList nodeLst = doc.getElementsByTagName("corso");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;
                    NodeList NameElmntLst = fstElmnt.getElementsByTagName("name");
                    Element fstNmElmnt = (Element) NameElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    sb.setMateria(((Node) fstNm.item(0)).getNodeValue());
                    System.out.println("Name : "  + ((Node) fstNm.item(0)).getNodeValue());

                    NodeList urlElmntLst = fstElmnt.getElementsByTagName("url");
                    Element lstUrlElmnt = (Element) urlElmntLst.item(0);
                    NodeList lstUrl = lstUrlElmnt.getChildNodes();
                    sb.setUrl(new URL(((Node) lstUrl.item(0)).getNodeValue()));
                    System.out.println("Url : " + ((Node) lstUrl.item(0)).getNodeValue());

                    NodeList pswElmntLst = fstElmnt.getElementsByTagName("psw");
                    Element lstPswElmnt = (Element) pswElmntLst.item(0);
                    NodeList lstPsw = lstPswElmnt.getChildNodes();
                    sb.setPassword(((Node) lstPsw.item(0)).getNodeValue());
                    System.out.println("Psw : " + ((Node) lstPsw.item(0)).getNodeValue());

                    NodeList usrElmntLst = fstElmnt.getElementsByTagName("user");
                    Element lstUsrElmnt = (Element) usrElmntLst.item(0);
                    NodeList lstUsr = lstUsrElmnt.getChildNodes();
                    sb.setUser(((Node) lstUsr.item(0)).getNodeValue());
                    System.out.println("User : " + ((Node) lstUsr.item(0)).getNodeValue());

                    lst.add(sb);
                }

            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        

        return lst;
    }
}
