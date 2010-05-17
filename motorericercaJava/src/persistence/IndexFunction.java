/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.DocumentoBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author palla
 */
public class IndexFunction{

    private Analyzer luceneAnalyzer;
    private FSDirectory indexDir;

    public IndexFunction() throws CorruptIndexException, LockObtainFailedException, IOException {
        //imposto la versione di Lucene
        this.luceneAnalyzer = new MyAnalyzer();
        //indexDir indica che Lucene crea l'index in memoria principale
        this.indexDir = FSDirectory.open(new File("config/index.writer"));
    }


    /**
     * indicizzazione del contenuto delle tabelle prese dal sito
     */
    public void indicizza(List<DocumentoBean> listDocumenti) throws CorruptIndexException, LockObtainFailedException, IOException{

        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, IndexWriter.MaxFieldLength.LIMITED);
        Iterator<DocumentoBean> iterator = listDocumenti.iterator();
        
        while(iterator.hasNext()){
            addDoc(iterator.next(), indexWriter);
            //System.out.println("Titolo ppt " + iterator.next().getTitolo());
        }
        indexWriter.optimize();
        indexWriter.close();
    }


    /**
     * Aggiunge il documento da indicizzare
     */
    private void addDoc(DocumentoBean documento, IndexWriter indexWriter) throws CorruptIndexException, LockObtainFailedException, IOException{

        //Add documents to the index
	try {
            if(documento != null){
                Document document = new Document();
                System.out.println("The document " + documento.getTitolo() + " is being indexed");

                if (documento.getTitolo()!=null){
                    document.add(new Field("title", documento.getTitolo(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("title", documento.getPercorso(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                
                document.add(new Field("content", documento.getContenuto(), Field.Store.YES, Field.Index.ANALYZED));
                document.add(new Field("path", documento.getPercorso(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                indexWriter.addDocument(document);
                System.out.println("Path document added: " + documento.getPercorso());
                System.out.println("------------");
            }

	}catch (Exception e) {
            System.out.println("Could not add: " + documento.getPercorso());
            Logger.getLogger(IndexFunction.class.getName()).log(Level.SEVERE, "Catch index!", e);
            System.out.println("------------");
	}finally {
            //indexWriter.optimize(10);
            //indexWriter.close();
	}
    }

    /**
     * 
     * @param field
     * @param querystr
     * @throws CorruptIndexException
     * @throws IOException
     * @throws ParseException
     */
    public LinkedList<DocumentoBean> search(String querystr) throws CorruptIndexException, IOException, ParseException {

        int hitsPerPage = 500;
        LinkedList<DocumentoBean> lst = new LinkedList();
        IndexSearcher searcher = new IndexSearcher(indexDir, true);

        //String sentence = JOptionPane.showInputDialog(querystr);
        QueryParser queryParser = new QueryParser(Version.LUCENE_30, "content", luceneAnalyzer);
        queryParser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = queryParser.parse(querystr);
        
        //TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        //searcher.search(query, collector);
        //ScoreDoc[] hits = collector.topDocs().scoreDocs;
        TopDocs topDocs = searcher.search(query,hitsPerPage);
        ScoreDoc[] hits = topDocs.scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits...");
        for(int i=0;i<hits.length;++i) {
            DocumentoBean db = new DocumentoBean();
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            //System.out.println((i + 1) + ". " + d.get("path"));

            db.setPercorso(d.get("path"));
            db.setContenuto(d.get("content"));
            db.setTitolo(d.get("title"));

            lst.add(db);
        }

        // searcher can only be closed when there
        // is no need to access the documents any more.
        searcher.close();

        return lst;
    }




}
