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

/**
 *
 * @author palla
 */
public class HtmlFunction {

    public List<URL> getURL(URL url){

	List<URL> lst = new LinkedList<URL>();
        WebClient wc = new WebClient(BrowserVersion.FIREFOX_3);
        try {
            HtmlPage page = wc.getPage(url);
            List<HtmlAnchor> anchors = page.getAnchors();

            for (int i=0; i<anchors.size(); i++){
            File file =new File(anchors.get(i).getHrefAttribute());

            if(file.getName().endsWith(".pdf")&& !anchors.get(i).getHrefAttribute().contains("http")){
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

	//System.out.println("Summary: " + summary.trim());
	System.out.println("Title: " + info.getTitle());
	System.out.println("Author: " + info.getAuthor());
	System.out.println("Contents: " + contents.length());
	System.out.println("Keywords: " + info.getKeywords());
	System.out.println("Subject: " + info.getSubject());
	System.out.println("Creator: " + info.getCreator());
	System.out.println("Traped: " + info.getTrapped());
	System.out.println("pdf Path: " + url.toString());

        Date date = new Date(connection.getLastModified());
	System.out.println("Modification Date: " + info.getModificationDate());
	System.out.println("------------");
	pdf.close();
    }
    

}
