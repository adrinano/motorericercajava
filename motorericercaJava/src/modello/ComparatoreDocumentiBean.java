/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class ComparatoreDocumentiBean implements Comparator<DocumentoBean>{

    @Override
    public int compare(DocumentoBean o1, DocumentoBean o2) {
        String s1 = eliminaFinale(o1.getPercorso());
        String s2 = eliminaFinale(o2.getPercorso());
        return s2.hashCode() - s1.hashCode();
    }

    /**
     * 
     * @param path
     * @return
     */
    private String eliminaFinale(String path){
        int dif = 0;
        try {
            URL url = new URL(path);
            File fileName = new File(url.toString());            
            dif = path.length() - fileName.getName().length();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComparatoreDocumentiBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return path.substring(0, dif);
    }


}
