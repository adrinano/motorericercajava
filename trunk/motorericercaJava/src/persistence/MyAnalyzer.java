/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

/**
 *
 * @author palla
 */
public class MyAnalyzer extends Analyzer{

    @Override
    public TokenStream tokenStream(String string, Reader reader) {
        TokenStream result = new StandardTokenizer(Version.LUCENE_30, reader);

        return result;
    }

}
