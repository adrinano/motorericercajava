/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.util.Date;


/**
 *
 * @author palla
 */
public class DocumentoPptBean {


    private String applicazione;
    private String autore;
    private String commenti;
    private String keywords;
    private String ultimoAutore;
    private String numeroRevisione;
    private String oggetto;
    private String template;
    private String titolo;
    private Date dataCreazione;
    private Date dataEdit;
    private Date dataModifica;
    private String conteggioPagine;
    private String proprieta;
    private String TipoFile;



    public String getTipoFile() {
        return TipoFile;
    }

    public void setTipoFile(String TipoFile) {
        this.TipoFile = TipoFile;
    }

    public String getApplicazione() {
        return applicazione;
    }

    public void setApplicazione(String applicazione) {
        this.applicazione = applicazione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getCommenti() {
        return commenti;
    }

    public void setCommenti(String commenti) {
        this.commenti = commenti;
    }

    public String getConteggioPagine() {
        return conteggioPagine;
    }

    public void setConteggioPagine(String conteggioPagine) {
        this.conteggioPagine = conteggioPagine;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Date getDataEdit() {
        return dataEdit;
    }

    public void setDataEdit(Date dataEdit) {
        this.dataEdit = dataEdit;
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

    public String getNumeroRevisione() {
        return numeroRevisione;
    }

    public void setNumeroRevisione(String numeroRevisione) {
        this.numeroRevisione = numeroRevisione;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getProprieta() {
        return proprieta;
    }

    public void setProprieta(String proprieta) {
        this.proprieta = proprieta;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getUltimoAutore() {
        return ultimoAutore;
    }

    public void setUltimoAutore(String ultimoAutore) {
        this.ultimoAutore = ultimoAutore;
    }




}
