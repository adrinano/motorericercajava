/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.UrlUtils;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.DocumentoBean;
import modello.SitoBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class HtmlFunction {


    /**
     * Funzione che restituisce la lista degli url dei file da indicizzare
     * @param url
     * @param end
     * @return
     */
    public List<URL> getURLList(URL url, String end) throws URISyntaxException{

	List<URL> lst = new LinkedList<URL>();
        WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
        try {
            HtmlPage page = wc.getPage(url);

            List<HtmlAnchor> anchors = page.getAnchors();

            for (int i=0; i<anchors.size(); i++){
            File file =new File(anchors.get(i).getHrefAttribute());

            if(file.getName().endsWith(end) && !anchors.get(i).getHrefAttribute().contains("http://")){
                String pathIniziale = url.toString();
                
                if (!pathIniziale.endsWith("/")){
                    File fileName = new File(url.toString());
                    pathIniziale = eliminaFinale( fileName.getName(), url.toString());
                }

                lst.add(new URL( UrlUtils.encodeAnchor(pathIniziale + anchors.get(i).getHrefAttribute())));
                //System.out.println("Si: " + pathIniziale + anchors.get(i).getHrefAttribute());
            }

        }

	} catch (FailingHttpStatusCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}


	return lst;

    }

    /**
     * Funzione che restituisce le informazioni dei file PPT (libreria poi)
     * 
     * @param sb
     * @param url
     * @return
     */
    public DocumentoBean getPPTinfo (SitoBean sb, URL url){

        DocumentoBean documentoPpt = new DocumentoBean();

        try {
            
            URLConnection connection = getConnection(sb, url);
            
            PowerPointExtractor pptext = new PowerPointExtractor(connection.getInputStream());
            SummaryInformation info = pptext.getSummaryInformation();
            documentoPpt.setApplicazione(info.getApplicationName());
            documentoPpt.setAutore(info.getAuthor());
            //documentoPpt.setCommenti(info.getComments());
            //documentoPpt.setConteggioPagine(info.getPageCount());
            if (info.getCreateDateTime() != null) {
                documentoPpt.setDataCreazione(info.getCreateDateTime());
            } else {
                documentoPpt.setDataCreazione(null);
            }
            
            if (info.getLastSaveDateTime() != null) {
                documentoPpt.setDataModifica(info.getLastSaveDateTime());
            } else {
                documentoPpt.setDataModifica(null);
            }
            //documentoPpt.setDataModifica(info.getLastSaveDateTime());
            documentoPpt.setKeywords(info.getKeywords());
            documentoPpt.setNumeroRevisione(info.getRevNumber());
            documentoPpt.setOggetto(info.getSubject());
            //documentoPpt.setTemplate(info.getTemplate());
            documentoPpt.setTipoFile("ppt");
            documentoPpt.setTitolo(info.getTitle());
            documentoPpt.setUltimoAutore(info.getLastAuthor());
            documentoPpt.setContenuto(pptext.getText(true, true, true, true));
            documentoPpt.setPercorso(url.toString());
            documentoPpt.setMateria(sb.getMateria());
            /*
            System.out.println(info.getLastSaveDateTime().toString());
            System.out.println("informazioni ppt AppName: " +info.getApplicationName());
            System.out.println("informazioni ppt Author: " +info.getAuthor());
            System.out.println("informazioni ppt Comment: " +info.getComments());
            System.out.println("informazioni ppt Keyword: " +info.getKeywords());
            System.out.println("informazioni ppt Lauthor: " +info.getLastAuthor());
            System.out.println("informazioni ppt RevNumber: " +info.getRevNumber());
            System.out.println("informazioni ppt Subject: " +info.getSubject());
            System.out.println("informazioni ppt Template: " +info.getTemplate());
            System.out.println("informazioni ppt Title: " +info.getTitle());
            System.out.println("informazioni ppt CreateTime: " +info.getCreateDateTime().toString());
            System.out.println("informazioni ppt EditTime: " +info.getEditTime());
            //System.out.println("informazioni prese da documentoPpt: " +documentoPpt.getDataModifica().toString());
            System.out.println("informazioni ppt LastSave: " +info.getLastSaveDateTime().getTime());
            System.out.println("informazioni ppt PageCount: " +info.getPageCount());
            System.out.println("---------------------");
            //System.out.println("informazioni ppt Property: " +info.getProperties());
            //System.out.println(pptext.getText(true, true));
             */
        } catch (IOException ex) {
            Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, "Catch ppt!", ex);
        }

        return documentoPpt;
    }

    /**
     * Funzione che restituisce le informazioni dei file PDF (libreria pdfbox)
     * @param url
     * @throws IOException
     */
    public DocumentoBean getPDFinfo(SitoBean sb, URL url){

        DocumentoBean documentoPdf = new DocumentoBean();
        try {
            System.out.println("get PDF info");
            System.out.println("pdf Path: " + url.toString());
            //se l'utenza è a null nel file xml allora esegue una normale connessione
            URLConnection connection = getConnection(sb, url);
            
            PDDocument pdf = PDDocument.load(connection.getInputStream());
            System.out.println("isEncrypted: " + pdf.isEncrypted());
            if(!pdf.isEncrypted()){
                //create a writer where to append the text content.
                StringWriter writer = new StringWriter();
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.writeText(pdf, writer);
                String contents = writer.getBuffer().toString();
                //String summary = contents.substring( 0, contents.length() );
                PDDocumentInformation info = pdf.getDocumentInformation();
                documentoPdf.setAutore(info.getAuthor());
                documentoPdf.setUltimoAutore(info.getAuthor()); //stessa Autore
                documentoPdf.setNumeroRevisione(null);
                documentoPdf.setContenuto(contents.toString());
                documentoPdf.setApplicazione(info.getCreator());
                if (info.getCreationDate() != null) {
                    documentoPdf.setDataCreazione(info.getCreationDate().getTime());
                } else {
                    documentoPdf.setDataCreazione(null);
                }
                //documentoPdf.setDataCreazione(info.getCreationDate().getTime());
                if (info.getModificationDate() != null) {
                    documentoPdf.setDataModifica(info.getModificationDate().getTime());
                } else {
                    documentoPdf.setDataModifica(null);
                }
                //documentoPdf.setDataModifica(info.getModificationDate().getTime());
                //documentoPdf.setDataEdit(info.getModificationDate().getTime());     //stesso di Modifica
                documentoPdf.setKeywords(info.getKeywords());
                documentoPdf.setOggetto(info.getSubject());
                documentoPdf.setPercorso(url.toString());
                //documentoPdf.setProduttore(info.getProducer());
                documentoPdf.setTipoFile("pdf");
                documentoPdf.setTitolo(info.getTitle());
                documentoPdf.setMateria(sb.getMateria());
                //documentoPdf.setTrapped(info.getTrapped()); //ma a che ce serve????
                //System.out.println("letto il documento:" +info.getTitle());
                /*
                System.out.println("Title: " + info.getTitle());
                System.out.println("Author: " + info.getAuthor());
                System.out.println("Contents: " + contents.length());
                System.out.println("Keywords: " + info.getKeywords());
                System.out.println("Subject: " + info.getSubject());
                System.out.println("Creator: " + info.getCreator());
                System.out.println("Trapped: " + info.getTrapped());
                System.out.println("Producer: " + info.getProducer());
                //La data di creazione di un pdf coincide con la data di modifica del documento
                System.out.println("Creation Date: " + info.getCreationDate().getTime());
                System.out.println("------------");
                 */
                pdf.close();
            }else{
                pdf.close();
                return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, "Catch di pdf!" + url.toString(), ex);
        }

        return documentoPdf;
    }

    /**
     * 
     * @param nodeName
     * @param path
     * @return
     */
    private String eliminaFinale(String nodeName, String path){
        int dif = path.length() - nodeName.length();
        return path.substring(0, dif);
    }

    /**
     * 
     * @param sb
     * @param url
     * @return
     */
    private URLConnection getConnection(SitoBean sb, URL url){
        URLConnection connection = null;

        if (sb.getPassword().equals("null")){
            try {
                connection = url.openConnection();
                connection.connect();
            } catch (IOException ex) {
                Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
            try {
                BASE64Encoder base64 = new BASE64Encoder();
                String encoding = base64.encode(sb.getPassword().getBytes());
                connection = url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + encoding);
            } catch (IOException ex) {
                Logger.getLogger(HtmlFunction.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        return connection;
    }


}
