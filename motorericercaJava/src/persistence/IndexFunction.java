/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modello.ComparatoreDocumentiBean;
import modello.DocumentoBean;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class IndexFunction{

    private Analyzer luceneAnalyzer;
    private FSDirectory indexDir;
    private LinkedList<DocumentoBean> lst;

    /**
     * 
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    public IndexFunction() throws CorruptIndexException, LockObtainFailedException, IOException {
        //imposto la versione di Lucene
        this.luceneAnalyzer = new MyAnalyzer();
        
        //indexDir indica che Lucene crea l'index su FileSystem
        this.indexDir = FSDirectory.open(new File("webapps/sherlockTux/WEB-INF/config/index.writer"));

        //lista dei socumenti
        this.lst = new LinkedList<DocumentoBean>();

    }


    /**
     *
     * @param listDocumenti
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    public void indicizza(List<DocumentoBean> listDocumenti) throws CorruptIndexException, LockObtainFailedException, IOException{
        long start = System.currentTimeMillis();

        //crea l'indexWriter il true determina che l'index venga riscritto
        //quando si chiede di indicizzare di modo da eliminare i doppioni
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);
        
        indexWriter.setUseCompoundFile(false);
        Iterator<DocumentoBean> iterator = listDocumenti.iterator();
        
        while(iterator.hasNext()){
            try{
                indexWriter.addDocument(addDoc(iterator.next()));
            }catch (Exception e){
                Logger.getLogger(IndexFunction.class.getName()).log(Level.SEVERE, "Catch index!", e);
            }
        }
        indexWriter.optimize();
        indexWriter.close();

        System.out.println("Time to index: " + (System.currentTimeMillis() - start));
    }

    /**
     * Update dell'indice
     * @param listDocumenti
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    public void update(List<DocumentoBean> listDocumenti) throws CorruptIndexException, LockObtainFailedException, IOException{
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true, IndexWriter.MaxFieldLength.LIMITED);
        indexWriter.setUseCompoundFile(false);
        Iterator<DocumentoBean> iterator = listDocumenti.iterator();

        while(iterator.hasNext()){
            indexWriter.updateDocument(new Term("content"), addDoc(iterator.next()), luceneAnalyzer);
        }
        indexWriter.optimize();
        indexWriter.close();
    }

    /**
     * Restituisce il documento Document da indicizzare
     * 
     * @param documento
     * @return
     * @throws CorruptIndexException
     * @throws LockObtainFailedException
     * @throws IOException
     */
    private Document addDoc(DocumentoBean documento) throws CorruptIndexException, LockObtainFailedException, IOException{
        Document document = new Document();
        //Add documents to the index
	try {
            if(documento != null){
                
                document.add(new Field("path", documento.getPercorso(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                document.add(new Field("materia", documento.getMateria(), Field.Store.YES, Field.Index.ANALYZED));

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
                    document.add(new Field("author", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
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
                    document.add(new Field("creationDate", Long.toString(documento.getDataCreazione().getTime()) , Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("creationDate", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getDataModifica()!=null){
                    document.add(new Field("editDate", Long.toString(documento.getDataModifica().getTime()), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("editDate", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                if(documento.getTipoFile()!=null){
                    document.add(new Field("typeFile", documento.getTipoFile(), Field.Store.YES, Field.Index.NOT_ANALYZED));
                }else{
                    document.add(new Field("typeFile", "Campo Nullo!", Field.Store.YES, Field.Index.NOT_ANALYZED));
                }

                //indexWriter.addDocument(document);
            }

	}catch (Exception e) {
            System.out.println("Could not add: " + documento.getTitolo());
            Logger.getLogger(IndexFunction.class.getName()).log(Level.SEVERE, "Errore nel creare il document...", e);
            System.out.println("------------");
	}
        return document;
    }

    /**
     * 
     * @param field
     * @param querystr
     * @throws CorruptIndexException
     * @throws IOException
     * @throws ParseException
     */
    public boolean search(String querystr) throws CorruptIndexException, IOException, ParseException {

        int hitsPerPage = 500;
        IndexSearcher searcher = new IndexSearcher(indexDir, true);

        //QueryParser queryParser = new QueryParser(Version.LUCENE_30, "content", luceneAnalyzer);
        //queryParser.setDefaultOperator(QueryParser.Operator.AND);
        //Query query = queryParser.parse(querystr);
        
        String[] fields = {"content","object", "author", "object", "key", "title", "lastAuthor", "materia"};
        BooleanClause.Occur[] flags = {BooleanClause.Occur.MUST, BooleanClause.Occur.SHOULD,
                            BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                            BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD,
                            BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
        
        Query query = MultiFieldQueryParser.parse(Version.LUCENE_30, querystr, fields, flags, luceneAnalyzer);
        TopDocs topDocs = searcher.search(query,hitsPerPage);     //senza il peso della query
        ScoreDoc[] hits = topDocs.scoreDocs;
        //FINE VECCHIA PROCEDURA

        // 4. display results
        lst.clear();
        System.out.println("Found " + hits.length + " hits...");
        for(int i=0;i<hits.length;++i) {
            DocumentoBean db = new DocumentoBean();
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);

            db.setPercorso(d.get("path"));
            db.setContenuto(d.get("content"));
            db.setTitolo(d.get("title"));
            db.setApplicazione(d.get("application"));
            db.setAutore(d.get("author"));
            db.setKeywords(d.get("key"));
            db.setTipoFile(d.get("typeFile"));
            db.setNumeroRevisione(d.get("revision"));
            db.setUltimoAutore(d.get("lastAuthor"));
            db.setOggetto(d.get("object"));
            db.setMateria(d.get("materia"));
            db.setScore(hits[i].score);

            //La data di creazione e la data di modifica sono oggetti di tipo Date
            //Se sono null non possono accettare una stringa per creare l'oggetto
            //Tocca controllare questa situazione per settare i campi nell'oggetto DocumentoBean
            if(d.get("creationDate").equals("Campo Nullo!")){
                db.setDataCreazione(null);
            }else{
                db.setDataCreazione(null);
            }
            if(d.get("editDate").equals("Campo Nullo!")){
                db.setDataModifica(null);
            }else{
                db.setDataModifica(null);
            }
            
            lst.add(db);

        }

        // searcher can only be closed when there
        // is no need to access the documents any more.
        searcher.close();
        return lst.isEmpty();
    }

    /**
     * Ritorna la lista dei documenti cercati
     * @return
     */
    public LinkedList<DocumentoBean> getDocumentList(){
        Collections.sort(lst, new ComparatoreDocumentiBean());
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

    
}
