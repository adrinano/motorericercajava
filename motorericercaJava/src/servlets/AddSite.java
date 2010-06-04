/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import controllo.Action.AddSiteAction;
import controllo.Helper.FormHelper;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author palla
 */
public class AddSite extends HttpServlet{
    private String prossimaPagina = "/ControlPanel/errore.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException{
        FormHelper form = new FormHelper();
        
        if (form.controlloSito(request)){
            //aggiungi sito al file xml
            AddSiteAction asa = new AddSiteAction();

            try {
                if (asa.startAddSite(request)){
                    prossimaPagina = "/ControlPanel/index.jsp";
                }else{
                    prossimaPagina = "/ControlPanel/errore.jsp?err=errore nella AddSite&redir=ControlPanel/index.jsp";
                }

            } catch (ParserConfigurationException ex) {
                Logger.getLogger(AddSite.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(AddSite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ServletContext application  = getServletContext();
	RequestDispatcher rd =  application.getRequestDispatcher(prossimaPagina);
	rd.forward(request, response);
	return;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException, ServletException{
        doPost(request, response);
    }

}
