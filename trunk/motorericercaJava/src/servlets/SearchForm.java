/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servlets;

import controllo.Action.SearchAction;
import controllo.Helper.FormHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modello.DocumentoBean;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.store.LockObtainFailedException;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class SearchForm extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
        long startTime = System.currentTimeMillis();

        FormHelper form = new FormHelper();
        SearchAction ar = new SearchAction();

        LinkedList<DocumentoBean> lst;
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML>");
        out.println("<html lang=\"en\">");
        out.println("<head>");
        out.println("<title>Sherlock Tux - Results</title>");
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../style.css\" />");
        out.println("</head>");
        out.println("<body>\n <div id=\"result\">");
        out.println("<div id=\"formsearchonresults\"> <div id=\"miniform\">You asked me: <span id=\"text_result\"> " + request.getParameter("search") +"</span></div> ");
        if (form.controlloRicerca(request)){
            try {
                lst = ar.startSearch(request);
                long endTime = System.currentTimeMillis();
                System.out.println("Avvia Azione");
                if(lst.isEmpty()){
                    out.println("<div id=\"results_number\">" + lst.size() + " results in " + (endTime-startTime) +" milliseconds</div>");
                    out.println("</div> \n <hr /> \n <hr /> \n<div id=\"posts\">");
                    
                   
                }else{
                   //prossimaPagina = "/Results/results.jsp";
                    out.println("<div id=\"results_number\">" + lst.size() + " results in " + (endTime-startTime) +" milliseconds</div>");
                    out.println("</div> \n <hr /> \n <hr /> \n<div id=\"posts\">");

                    //risultato della ricerca                   
                    Iterator<DocumentoBean> iterator = lst.iterator();
                    while(iterator.hasNext()){
                        DocumentoBean doc = iterator.next();
                        out.println("<div class=\"post\">");
                        out.println("<h2 class=\"title\"><a href=\"" + doc.getPercorso() + "\" title=\" "+ doc.getTitolo() +" \">" + doc.getTitolo() + "</a></h2>");
                        out.println("<p class=\"meta\"><span class=\"date\"></p>");
                        out.println("<div class=\"entry\">");
						out.println("<ul>");
                                                        out.println("<li><strong>Score</strong>: "+ doc.getScore() + "</li>");
                                                        out.println("<li><strong>Corso</strong>: "+ doc.getMateria() + "</li>");
							out.println("<li><strong>Author</strong>: "+ doc.getAutore() + "</li>");
							out.println("<li><strong>Ultimo autore</strong>: "+ doc.getUltimoAutore() + "</li>");
							out.println("<li><strong>Keywords</strong>: " + doc.getKeywords() +"</li>");
							out.println("<li><strong>Subject</strong>: " + doc.getOggetto() +"</li>");
							out.println("<li><strong>Creator</strong>: " + doc.getApplicazione() + "</li>");
                                                        out.println("<li><strong>Contents</strong>: <div class=\"boxcode\"><pre>" + doc.getContenuto().substring(0, 400) + " ...</pre></div></li>");
						out.println("</ul>");
					out.println("<hr />");
					out.println("</div>");
				out.println("</div>");
                    }
                    
                }

                
            } catch (ParseException ex) {
                Logger.getLogger(SearchForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.println("</div>\n</div>\n</body>");
        out.println("</html>");
        
//        ServletContext application  = getServletContext();
//	RequestDispatcher rd =  application.getRequestDispatcher(prossimaPagina);
//	rd.forward(request, response);
	return;

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, CorruptIndexException, LockObtainFailedException, IOException {
		doPost(request, response);

    }

}
