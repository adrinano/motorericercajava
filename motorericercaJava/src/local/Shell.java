/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package local;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.lucene.index.CorruptIndexException;
import java.util.Iterator;
import java.util.LinkedList;
import modello.DocumentoBean;
import org.xml.sax.SAXException;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class Shell {


    public static void main(String[] args) throws MalformedURLException, ParserConfigurationException, SAXException, IOException {
        try {
//            Singleton s = Singleton.getInstance();
//            s.checkUpdate();
//            SitoBean sito = new SitoBean();
//            sito.setMateria("Sistemi Operativi");
//            sito.setUrl(new URL("http://www.dia.uniroma3.it/~pizzonia/so/"));
//            sito.setPassword("null");
//            sito.setId(String.valueOf(System.currentTimeMillis()));
//            IndexThread it = new IndexThread();
//            it.eseguiIndex();
//            XMLFunction xml = new XMLFunction();
//            xml.addSiteToXML(sito);
//            xml.removeSite("1275997359810");
//            String pathname = "file/07_memory_management.pdf";
//
//            URL url = new URL("http://torlone.dia.uniroma3.it/calcolatori/materiale.html");
//            URL url2 = new URL("http://cialdea.dia.uniroma3.it/teaching/pf/materiale/slides/pdf/");
//            http://www.dia.uniroma3.it/~pizzonia/so/
//            URL url = new URL("http://www.dia.uniroma3.it/~atzeni/didattica/SINF/20092010/Programma.html");
//
//            HtmlFunction html = new HtmlFunction();
            IndexFunction index = new IndexFunction();
            LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();


            index.search("ram");
            listaDocumenti = index.getDocumentList();
            Iterator<DocumentoBean> iterator = listaDocumenti.iterator();
            while (iterator.hasNext()) {
                DocumentoBean doc = iterator.next();
                System.out.println("Titolo:     " + doc.getTitolo());
                System.out.println("Percorso:   " + doc.getPercorso());
            }
            
        } catch (CorruptIndexException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.lucene.queryParser.ParseException ex) {
            Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
        }
   }


}
