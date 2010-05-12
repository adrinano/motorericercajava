/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.util.Date;

/**
 *
 * @author adrinano
 */
public class DocumentoPdfBean {

    private String titolo;
    private String autore;
    private String contenuto;
    private String keywords;
    private String oggetto;
    private String creatore;
    private String trapped;
    private String produttore;
    private String percorso;
    private Date dataCreazione;
    private Date dataModifica;    //quasi sempre null... per ora lo metto
    private int tipoFile;

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getCreatore() {
        return creatore;
    }

    public void setCreatore(String creatore) {
        this.creatore = creatore;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Date getDataModifica() {
        return dataModifica;
    }

    public void setDataModifica(Date dataModifica) {
        this.dataModifica = dataModifica;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTrapped() {
        return trapped;
    }

    public void setTrapped(String trapped) {
        this.trapped = trapped;
    }

    public int getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(int tipoFile) {
        this.tipoFile = tipoFile;
    }


}
