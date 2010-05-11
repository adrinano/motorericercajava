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
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

    public List<URL> getURLList(URL url, String end){

	List<URL> lst = new LinkedList<URL>();
        WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
        try {
            HtmlPage page = wc.getPage(url);


            List<HtmlAnchor> anchors = page.getAnchors();

            for (int i=0; i<anchors.size(); i++){
            File file =new File(anchors.get(i).getHrefAttribute());

            if(file.getName().endsWith(end) && !anchors.get(i).getHrefAttribute().contains("http://")){
                lst.add(new URL(url.toString() + anchors.get(i).getHrefAttribute()));
                System.out.println("Si: " + url.toString()+ anchors.get(i).getHrefAttribute());
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

    public void getPPTinfo (URL url) throws IOException{
        URLConnection connection = url.openConnection();
        connection.connect();

        PowerPointExtractor pptext = new PowerPointExtractor(connection.getInputStream());

        SummaryInformation info = pptext.getSummaryInformation();
        System.out.println(info.getLastSaveDateTime().toString());
        //System.out.println(pptext.getText(true, true));

    }


    public void getPDFinfo(URL url) throws IOException {
        URLConnection connection = url.openConnection();
	connection.connect();
	PDDocument pdf = PDDocument.load(connection.getInputStream());

	//create a writer where to append the text content.
	StringWriter writer = new StringWriter();
	PDFTextStripper stripper = new PDFTextStripper();
	stripper.writeText( pdf, writer );

	String contents = writer.getBuffer().toString();
	//String summary = contents.substring( 0, contents.length() );

        PDDocumentInformation info = pdf.getDocumentInformation();

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
	System.out.println("------------");
	pdf.close();
    }


}
