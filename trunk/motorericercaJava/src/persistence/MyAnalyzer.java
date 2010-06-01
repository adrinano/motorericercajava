/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
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

        result = new StandardFilter(result);
        result = new LowerCaseFilter(result);
        result = new StopFilter(true, result, getStopWords());

        return result;
    }

    private static Set<String> getStopWords(){
        Set<String> stringSet = new HashSet<String>();

        try {
            BufferedReader in = new BufferedReader(new FileReader("webapps/sherlockTux/WEB-INF/config/stopWord"));
            String str;
            while ((str = in.readLine()) != null) {
		stringSet.add(str);
            }
            in.close();
        } catch (IOException e) {
        }
        return stringSet;
    }

}
