/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author palla
 */
public class FormHelper {
    private String form;

    public FormHelper(HttpServletRequest request)throws ServletException {

        this.form = request.getParameter("searchform");
    }

    public boolean controllo (){
        boolean verifica = true;

        if (form.equals("")||form==null){
            verifica = false;
        }
        return verifica;
    }
}
