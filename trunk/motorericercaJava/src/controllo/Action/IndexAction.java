/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class IndexAction {

    /**
     * 
     * @param request
     * @return
     */
    public boolean startIndex(HttpServletRequest request){

        if(request.getParameter("index_action").equals("index_now")){
            IndexThread it = new IndexThread();
            it.eseguiIndex();
            return true;
        }

        if(request.getParameter("index_action").equals("index_thread")){
            Singleton s = Singleton.getInstance();
            s.setDelay(Double.parseDouble(request.getParameter("hours")));
            System.out.println("Delay: " + s.getDelay());
            s.checkUpdate();
            return true;
        }

        return false;
    }

}
