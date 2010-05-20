/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import javax.servlet.http.HttpServletRequest;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class AvviaRicerca {

    public boolean avviaAzione(HttpServletRequest request) throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {

        IndexThread it = new IndexThread();
        Timer t = new Timer(true);
        t.scheduleAtFixedRate(it, new Date (System.currentTimeMillis()), 10000);


        IndexFunction index = new IndexFunction();

        //ritorna false se la lista è piena
        return index.search(request.getParameter("search"));

    }

}
