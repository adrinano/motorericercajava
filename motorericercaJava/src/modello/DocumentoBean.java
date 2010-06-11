/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.util.Date;


/**
 *
 * @author Daniele Palladino
 * @author Adriano Bellia
 */
public class DocumentoBean {


    private String applicazione;
    private String autore;
    //private String commenti;
    private String keywords;
    private String ultimoAutore;
    private String numeroRevisione;
    private String oggetto;
    //private String template;
    private String titolo;
    private Date dataCreazione;
    //private Date dataEdit;              //servirebbe un long invece di un date... ma ci serve???
    private Date dataModifica;
    //private int conteggioPagine;
    //private String proprieta;
    private String TipoFile;
    private String contenuto;
    private String percorso;
    private float score;
    private String materia;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
    
    
    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

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

    /*public String getCommenti() {
    return commenti;
    }

    public void setCommenti(String commenti) {
    this.commenti = commenti;
    }

    public int getConteggioPagine() {
    return conteggioPagine;
    }

    public void setConteggioPagine(int conteggioPagine) {
    this.conteggioPagine = conteggioPagine;
    }*/

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
/*
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
*/
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
}
