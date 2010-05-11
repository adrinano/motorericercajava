/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import modello.DispenseBean;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.demo.HTMLDocument;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author palla
 */
public class IndexFunction{

    private StandardAnalyzer luceneAnalyzer;
    private Directory indexDir;

    public IndexFunction() {
        //imposto la versione di Lucene
        this.luceneAnalyzer = new StandardAnalyzer(Version.LUCENE_30);
        //indexDir indica che Lucene crea l'index in memoria principale
        this.indexDir = new RAMDirectory();

    }


    /**
     * indicizzazione del contenuto delle tabelle prese dal sito
     */
    public void indicizza(List<DispenseBean> listConcerti, boolean create) throws CorruptIndexException, LockObtainFailedException, IOException{

        IndexWriter indexWriter = new IndexWriter(indexDir,luceneAnalyzer,create, IndexWriter.MaxFieldLength.LIMITED);
        Iterator<DispenseBean> iterator = listConcerti.iterator();
        while(iterator.hasNext()){
            //addDoc(null, create);
            System.out.println("si " + iterator.next().getArtista());
        }
    }


    /**
     * Aggiunge il documento da indicizzare
     */
    private void addDoc(File fileDir, boolean create) throws CorruptIndexException, LockObtainFailedException, IOException{

        IndexWriter indexWriter = new IndexWriter(indexDir,luceneAnalyzer,create, IndexWriter.MaxFieldLength.LIMITED);

        File[] textFiles = fileDir.listFiles();
        System.out.println(fileDir.getName());
        System.out.println("Nome textFiles: " + textFiles[0].getName());

        //Add documents to the index
        for(int i = 0; i < textFiles.length; i++){
          if(textFiles[i].isFile() && textFiles[i].getName().endsWith(".html")){

              System.out.println(textFiles[i].getName().endsWith(".html"));

              Reader fileReader = null;

              try {
                  Document document = new Document();
                  System.out.println("File " + textFiles[i].getCanonicalPath() + " is being indexed");

                  fileReader = new FileReader(textFiles[i]);

                  String str = HTMLDocument.uid(fileDir);
                  //HTMLParser html = new HTMLParser(fileReader);
                  //html.ReInit(fileReader);
                  System.out.println(str);

                  document.add(new Field("content", str, Field.Store.YES, Field.Index.ANALYZED));
                  document.add(new Field("path", textFiles[i].getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                  indexWriter.addDocument(document);
                  System.out.println("Added: " + textFiles[i].getName());

              }catch (Exception e) {
                  System.out.println("Could not add: " + fileDir);
              }finally {
                  indexWriter.optimize();
                  indexWriter.close();
              }

          }
        }



    }

    public void search(String field, String querystr) throws CorruptIndexException, IOException, ParseException {

        int hitsPerPage = 20;

        IndexSearcher searcher = new IndexSearcher(indexDir, true);

        //String sentence = JOptionPane.showInputDialog(querystr);
        QueryParser queryParser = new QueryParser(Version.LUCENE_30, field, luceneAnalyzer);

        queryParser.setDefaultOperator(QueryParser.Operator.AND);

        Query query = queryParser.parse(querystr + " ");
        TopDocs topDocs = searcher.search(query,1000);
        //searcher.search(query, collector);
        //ScoreDoc[] hits = collector.topDocs().scoreDocs;
        ScoreDoc[] hits = topDocs.scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get(field) + " Field: " + field);
        }

        // searcher can only be closed when there
        // is no need to access the documents any more.
        searcher.close();
    }


}
