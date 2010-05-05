/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modello;

import java.net.URL;

/**
 *
 * @author palla
 */
public class ConcertoBean {

    private String artista;
    private String data;
    private String locale;
    private String citta;
    private String provincia;
    private String youtube;

    public ConcertoBean(String artista, String data, String locale, String citta, String provincia, String youtube) {
        this.artista = artista;
        this.data = data;
        this.locale = locale;
        this.citta = citta;
        this.provincia = provincia;
        this.youtube = youtube;
    }

    /**
     * @return the artista
     */
    public String getArtista() {
        return artista;
    }

    /**
     * @param artista the artista to set
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return the citta
     */
    public String getCitta() {
        return citta;
    }

    /**
     * @param citta the citta to set
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the youtube
     */
    public String getYoutube() {
        return youtube;
    }

    /**
     * @param youtube the youtube to set
     */
    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }








}
