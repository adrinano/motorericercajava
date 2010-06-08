/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controllo.Action;

import javax.servlet.http.HttpServletRequest;
import persistence.XMLFunction;

/**
 *
 * @author palla
 */
public class RemoveSiteAction {

    /**
     * 
     * @param request
     * @return
     */
    public boolean startRemoveSite(HttpServletRequest request){
        XMLFunction xml = new XMLFunction();
        return xml.removeSite(request.getParameter("remove"));
    }

}
