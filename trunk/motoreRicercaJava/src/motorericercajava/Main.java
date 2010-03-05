/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package motorericercajava;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;


/**
 *
 * @author adrinano
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Palla è una pippa
    	// Adrinano pure....
        Analyzer a = new Analyzer() {

            @Override
            public TokenStream tokenStream(String string, Reader reader) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

}
