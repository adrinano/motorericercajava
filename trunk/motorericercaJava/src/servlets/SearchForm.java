/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import controllo.Action.AvviaRicerca;
import controllo.Helper.FormHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

/**
 *
 * @author palla
 */
public class SearchForm extends HttpServlet{
    private String prossimaPagina;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
        
        FormHelper form = new FormHelper(request);
        AvviaRicerca ar = new AvviaRicerca();
        if (form.controllo()){
            try {
               if(!ar.avviaAzione(request)){
                   prossimaPagina="index.jsp";
               }else{
                   prossimaPagina = "reults.jsp";
               }
            } catch (ParseException ex) {
                Logger.getLogger(SearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ServletContext application  = getServletContext();
	RequestDispatcher rd =  application.getRequestDispatcher(prossimaPagina);
	rd.forward(request, response);
	return;

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
		doPost(request, response);

    }

}
