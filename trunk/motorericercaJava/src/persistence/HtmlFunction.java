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
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import modello.DocumentoPdfBean;
import modello.DocumentoPptBean;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.usermodel.SlideShow;

/**
 *
 * @author palla
 */
public class HtmlFunction {

    static final int tipoPdf = 1;
    static final int tipoPpt = 0;

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

                lst.add(new URL( pathIniziale + anchors.get(i).getHrefAttribute()));
                System.out.println("Si: " + pathIniziale + anchors.get(i).getHrefAttribute());
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
     * @param url
     * @throws IOException
     */
    public DocumentoPptBean getPPTinfo (URL url) throws IOException{
        URLConnection connection = url.openConnection();
        connection.connect();

        DocumentoPptBean documentoPpt = new DocumentoPptBean();

        PowerPointExtractor pptext = new PowerPointExtractor(connection.getInputStream());

        SummaryInformation info = pptext.getSummaryInformation();
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
        System.out.println("informazioni ppt CreateTime: " +info.getCreateDateTime().getTime());
        System.out.println("informazioni ppt EditTime: " +info.getEditTime());
        System.out.println("informazioni ppt LastSave: " +info.getLastSaveDateTime().getTime());
        System.out.println("informazioni ppt PageCount: " +info.getPageCount());
        System.out.println("informazioni ppt Property: " +info.getProperties());
        //System.out.println(pptext.getText(true, true));

        return documentoPpt;

    }

    /**
     * Funzione che restituisce le informazioni dei file PDF (libreria pdfbox)
     * @param url
     * @throws IOException
     */
    public DocumentoPdfBean getPDFinfo(URL url) throws IOException {
        URLConnection connection = url.openConnection();
	connection.connect();

        DocumentoPdfBean documentoPdf = new DocumentoPdfBean();

	PDDocument pdf = PDDocument.load(connection.getInputStream());

	//create a writer where to append the text content.
	StringWriter writer = new StringWriter();
	PDFTextStripper stripper = new PDFTextStripper();
	stripper.writeText( pdf, writer );

	String contents = writer.getBuffer().toString();
	//String summary = contents.substring( 0, contents.length() );

        PDDocumentInformation info = pdf.getDocumentInformation();

        documentoPdf.setAutore(info.getAuthor());
        documentoPdf.setContenuto(contents.toString());
        documentoPdf.setCreatore(info.getCreator());
        documentoPdf.setDataCreazione(info.getCreationDate().getTime());
        documentoPdf.setDataModifica(info.getModificationDate().getTime());
        documentoPdf.setKeywords(info.getKeywords());
        documentoPdf.setOggetto(info.getSubject());
        documentoPdf.setPercorso(url.toString());
        documentoPdf.setProduttore(info.getProducer());
        documentoPdf.setTipoFile(tipoPdf);
        documentoPdf.setTitolo(info.getTitle());
        documentoPdf.setTrapped(info.getTrapped()); //ma a che ce serve????

        System.out.println("letto il documento:" +info.getTitle());

/*
	System.out.println("Title: " + info.getTitle());
	System.out.println("Author: " + info.getAuthor());
	System.out.println("Contents: " + contents.length());
	System.out.println("Keywords: " + info.getKeywords());
	System.out.println("Subject: " + info.getSubject());
	System.out.println("Creator: " + info.getCreator());
	System.out.println("Trapped: " + info.getTrapped());
        System.out.println("Producer: " + info.getProducer());
	System.out.println("pdf Path: " + url.toString());
        
        //La data di creazione di un pdf coincide con la data di modifica del documento
        System.out.println("Creation Date: " + info.getCreationDate().getTime());
*/
 System.out.println("------------");

	pdf.close();

        return documentoPdf;
    }

    private String eliminaFinale(String nodeName, String path){
        int dif = path.length() - nodeName.length();
        return path.substring(0, dif);
    }


}
