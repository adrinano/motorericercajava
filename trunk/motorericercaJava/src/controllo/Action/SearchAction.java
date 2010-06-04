/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import modello.DocumentoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class SearchAction {

    public LinkedList<DocumentoBean> startSearch(HttpServletRequest request) throws CorruptIndexException, LockObtainFailedException, IOException, ParseException {
        
//        Singleton s = Singleton.getInstance();
//        s.checkUpdate();

        IndexFunction index = new IndexFunction();

        //ritorna false se la lista è piena
        index.search(request.getParameter("search"));

        return index.getDocumentList();

    }

}
