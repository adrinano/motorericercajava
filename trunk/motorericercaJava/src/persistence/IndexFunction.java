/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.DocumentoBean;
import org.apache.lucene.analysis.Analyzer;
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
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

/**
 *
 * @author palla
 */
public class IndexFunction{

    private Analyzer luceneAnalyzer;
    private FSDirectory indexDir;
    private LinkedList<DocumentoBean> lst;

    public IndexFunction() throws CorruptIndexException, LockObtainFailedException, IOException {
        //imposto la versione di Lucene
        this.luceneAnalyzer = new MyAnalyzer();
        
        //indexDir indica che Lucene crea l'index su FileSystem
        this.indexDir = FSDirectory.open(new File("config/index.writer"));
        
        
        //lista dei socumenti
        this.lst = new LinkedList<DocumentoBean>();
    }


    /**
     * indicizzazione del contenuto delle tabelle prese dal sito
     */
    public void indicizza(List<DocumentoBean> listDocumenti) throws CorruptIndexException, LockObtainFailedException, IOException{

        //crea l'indexWriter. il true determina che l'index viene riscritto quando si chiede di indicizzare
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);
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

                document.add(new Field("path", documento.getPercorso(), Field.Store.YES, Field.Index.NOT_ANALYZED));

                if (documento.getTitolo()!=null){
                    document.add(new Field("title", documento.getTitolo(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("title", eliminaInizioPath(documento.getPercorso()), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if (documento.getContenuto()!=null){
                    document.add(new Field("content", documento.getContenuto(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("content", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                
                if(documento.getApplicazione()!=null){
                    document.add(new Field("application", documento.getApplicazione(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("application", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getAutore()!=null){
                    document.add(new Field("author", documento.getAutore(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("autho", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                
                if(documento.getKeywords()!=null){
                    document.add(new Field("key", documento.getKeywords(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("key", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                
                if(documento.getUltimoAutore()!=null){
                    document.add(new Field("lastAuthor", documento.getUltimoAutore(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("lastAuthor", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }
                
                if(documento.getNumeroRevisione()!=null){
                    document.add(new Field("revision", documento.getNumeroRevisione(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("revision", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getOggetto()!=null){
                    document.add(new Field("object", documento.getOggetto(), Field.Store.YES, Field.Index.ANALYZED));
                }else{
                    document.add(new Field("object", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getDataCreazione()!=null){
                    long d = documento.getDataCreazione().getTime();
                    document.add(new Field("creationDate", "" , Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("creationDate", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getDataModifica()!=null){
                    document.add(new Field("editDate", documento.getDataModifica().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("editDate", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getTipoFile()!=null){
                    document.add(new Field("typeFile", documento.getTipoFile(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("typeFile", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                indexWriter.addDocument(document);
                System.out.println("Path document added: " + documento.getPercorso());
                System.out.println("------------");
            }

	}catch (Exception e) {
            System.out.println("Could not add: " + documento.getPercorso());
            Logger.getLogger(IndexFunction.class.getName()).log(Level.SEVERE, "Catch index!", e);
            System.out.println("------------");
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
    public void search(String querystr) throws CorruptIndexException, IOException, ParseException {

        int hitsPerPage = 500;
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
        lst.clear();
        System.out.println("Found " + hits.length + " hits...");
        for(int i=0;i<hits.length;++i) {
            DocumentoBean db = new DocumentoBean();
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            //System.out.println((i + 1) + ". " + d.get("path"));

            db.setPercorso(d.get("path"));
            db.setContenuto(d.get("content"));
            db.setTitolo(d.get("title"));
            db.setApplicazione(d.get("application"));
            db.setAutore(d.get("author"));
            db.setDataCreazione(new Date (d.get("creationDate")));


            lst.add(db);
        }

        // searcher can only be closed when there
        // is no need to access the documents any more.
        searcher.close();
    }

    /**
     * Ritorna la lista dei documenti cercati
     * @return
     */
    public LinkedList<DocumentoBean> getDocumentList(){
        return lst;
    }

    /**
     * elimina la parte iniziale del path per avere il nome dil file
     * @param path
     * @return
     */
    private String eliminaInizioPath(String path){
        File fileName = null;
        try {
            URL url = new URL(path);
             fileName = new File(url.toString());
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(IndexFunction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName.getName();
    }

    /**
     * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and returns false.
     * @param dir
     * @return
     */
    private boolean deleteDir(File dir) {
	if (dir.isDirectory()) {
		String[] children = dir.list();
		for (int i=0; i<children.length; i++) {
			boolean success = deleteDir(new File(dir, children[i]));
			if (!success) {
				return false;
			}
		}
	}
	// The directory is now empty so delete it
	return dir.delete();
    }
}
