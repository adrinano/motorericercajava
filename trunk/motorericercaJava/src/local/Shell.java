/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package local;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import modello.ComparatoreDocumentiBean;
import modello.DocumentoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import persistence.HtmlFunction;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class Shell {

    public static void main(String[] args) throws IOException, CorruptIndexException, ParseException, URISyntaxException{
        //String pathname = "file/07_memory_management.pdf";

        URL url1 = new URL("http://torlone.dia.uniroma3.it/calcolatori/materiale.html");
        URL url2 = new URL("http://www.dia.uniroma3.it/~pizzonia/so/");
        //URL url = new URL("http://www.dia.uniroma3.it/~atzeni/didattica/SINF/20092010/Programma.html");

        HtmlFunction html = new HtmlFunction();
        IndexFunction index = new IndexFunction();
        LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();

        esegui(url1, index, html, listaDocumenti);
        esegui(url2, index, html, listaDocumenti);

        listaDocumenti = index.search("RAID");

        Collections.sort(listaDocumenti, new ComparatoreDocumentiBean());
        Iterator<DocumentoBean> iterator = listaDocumenti.iterator();

        while(iterator.hasNext()){
            DocumentoBean doc = iterator.next();
            System.out.println("Titolo:     " + doc.getTitolo());
            System.out.println("Percorso:   " + doc.getPercorso());
        }
    }

    private static void esegui(URL url, IndexFunction index, HtmlFunction html, LinkedList<DocumentoBean> listaDocumenti) throws IOException, URISyntaxException {

        List<URL> lista = html.getURLList(url, ".pdf");
        System.out.println("isEmpty: " + lista.isEmpty() + "Elements number: " + lista.size());
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(i + " ");
            listaDocumenti.add(html.getPDFinfo(lista.get(i)));
        }
        System.out.print(" ");
        System.out.println("------------");
        System.out.println("------------");
        index.indicizza(listaDocumenti);
        //html.getPPTinfo(url);
        //File file = new File(pathname);
        //Document doc = new Document();
        //List<Fieldable> fields = doc.getFields();
    }


}
