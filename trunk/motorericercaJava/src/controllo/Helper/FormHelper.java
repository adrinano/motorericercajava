/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Helper;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class FormHelper {
    

    public FormHelper(){

    }

    /**
     * Controllo form di ricerca
     * @param request
     * @return
     */
    public boolean controlloRicerca (HttpServletRequest request){

        if (request.getParameter("search").equals("")||request.getParameter("search")==null){
            return false;
        }
        return true;
    }

    /**
     * Controllo dei campi per l'aggiunta dei siti da indicizzare
     * @param request
     * @return
     */
    public boolean controlloSito(HttpServletRequest request){

        if (request.getParameter("name").equals("")||request.getParameter("name")==null){
            return false;
        }

        if (request.getParameter("url").equals("")||request.getParameter("url")==null){
            return false;
        }

        return true;
    }

    /**
     * 
     * @param request
     * @return
     */
    public boolean controlloIndexNow(HttpServletRequest request){
        if (request.getParameter("index_action").equals("")||request.getParameter("index_action")==null)
            return false;

        return true;
    }

    /**
     * 
     * @param request
     * @return
     */
    public boolean controlloRemove(HttpServletRequest request){

        if (request.getParameter("remove").equals("0"))
            return false;

        return true;

    }
}
