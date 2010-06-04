/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.util.Comparator;

/**
 *
 * @author palla
 */
public class ComparatoreSitiBean implements Comparator<SitoBean>{

    @Override
    public int compare(SitoBean o1, SitoBean o2) {
        String s1 = o1.getMateria();
        String s2 = o2.getMateria();
        return s2.hashCode() - s1.hashCode();
    }
}
