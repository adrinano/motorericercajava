/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import controllo.Action.IndexAction;
import controllo.Helper.FormHelper;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author palla
 */
public class IndexNow extends HttpServlet{
     private String prossimaPagina = "/ControlPanel/errore.jsp";
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FormHelper form = new FormHelper();
        if(form.controlloIndexNow(request)){
            IndexAction ia = new IndexAction();
            if (ia.startIndex(request)){
                prossimaPagina = "/ControlPanel/index.jsp";
            }else{
                prossimaPagina = "/ControlPanel/errore.jsp?err=errore nella AddSite&redir=ControlPanel/index.jsp";
            }
        }

        ServletContext application  = getServletContext();
	RequestDispatcher rd =  application.getRequestDispatcher(prossimaPagina);
	rd.forward(request, response);
	return;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

    }

}
