/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.store.IndexOutput;

/**
 *
 * @author palla
 */
public class MyDirectory extends Directory{

    private File directory;


    /**
     *
     */
    public MyDirectory(String indirizzo) {

        this.directory = new File(indirizzo);

    }


    @Override
    public String[] listAll() throws IOException {

        String[] listaFile = directory.list() ;

        return listaFile;
    }

    @Override
    public boolean fileExists(String string) throws IOException {
        
        String[] lista = listAll();
        for(int i=0;i<lista.length;i++) {
            if (lista[i].contains(string))
                return true;
        }
        return false;
    }

    @Override
    public long fileModified(String string) throws IOException {
        return 124; //ovviamente da cambiare
    }

    @Override
    public void touchFile(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteFile(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public long fileLength(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IndexOutput createOutput(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IndexInput openInput(String string) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
