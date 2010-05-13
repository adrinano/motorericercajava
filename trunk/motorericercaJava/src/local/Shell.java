/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package local;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import persistence.HtmlFunction;

/**
 *
 * @author palla
 */
public class Shell {

    public static void main(String[] args) throws IOException, CorruptIndexException, ParseException, URISyntaxException{
        String pathname = "file/07_memory_management.pdf";
	URL url = new URL("http://www.dia.uniroma3.it/~pizzonia/so/");
        //URL url = new URL("http://www.dia.uniroma3.it/~atzeni/didattica/SINF/20092010/Programma.html ");

        HtmlFunction html = new HtmlFunction();
	List<URL> lista = html.getURLList(url, ".pdf");

	System.out.println("isEmpty: " + lista.isEmpty());
	for (int i=0; i<lista.size(); i++){
		html.getPDFinfo(lista.get(i));
	}

        //html.getPPTinfo(url);

	//File file = new File(pathname);
	//Document doc = new Document();
	//List<Fieldable> fields = doc.getFields();

    }

}
