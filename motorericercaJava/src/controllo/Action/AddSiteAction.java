/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import com.gargoylesoftware.htmlunit.javascript.host.Document;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import modello.SitoBean;
import org.xml.sax.SAXException;
import persistence.XMLFunction;

/**
 *
 * @author palla
 */
public class AddSiteAction {

    public boolean startAddSite(HttpServletRequest request) throws ParserConfigurationException, MalformedURLException, SAXException, IOException{

        SitoBean sito = new SitoBean();

        sito.setMateria(request.getParameter("name"));
        sito.setUrl(new URL (request.getParameter("url")));

        if(request.getParameter("user").equals("")||request.getParameter("user")==null&&request.getParameter("password").equals("")||request.getParameter("password")==null){
            sito.setPassword("null");
        }else{
            sito.setPassword(request.getParameter("user") + ":" + request.getParameter("password"));
        }
        sito.setId(String.valueOf(System.currentTimeMillis()));

        XMLFunction xml = new XMLFunction();
        return xml.addSiteToXML(sito);
    }
}
