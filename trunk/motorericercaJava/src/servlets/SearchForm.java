/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import Helper.FormHelper;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modello.DocumentoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.store.LockObtainFailedException;
import persistence.HtmlFunction;
import persistence.IndexFunction;

/**
 *
 * @author palla
 */
public class SearchForm extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
        
        FormHelper form = new FormHelper(request);

        if (form.controllo()){
            HtmlFunction html = new HtmlFunction();
            IndexFunction index = new IndexFunction();
            LinkedList<DocumentoBean> listaDocumenti = new LinkedList<DocumentoBean>();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
		doPost(request, response);

    }

}
