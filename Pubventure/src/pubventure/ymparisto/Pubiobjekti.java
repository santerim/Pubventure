package Pubventure.ymparisto;

import Pubventure.enumit.PubiobjektiEnum;

/**
 * 
 * Tämä luokka tarjoaa Pubi-luokan käyttämät ei-ihmisoliot.
 */
public class Pubiobjekti {

    /**
     * Se, että onko jokin pubiobjekti este vai ei, on oleellista törmäysten
     * selville saamisessa
     */
    private boolean onkoEste;
    
    /**
     * Pubiobjektilla on yleensä yksilöllinen ulkonäkö, joka tulostetaan
     * kenttää piirrettäessä
     */
    private String ulkonako;
    
    /**
     * Jokainen pubiobjekti on jotain enum-tyyppiä
     * @see PubiobjektiEnum
     */
    private PubiobjektiEnum tyyppi;
    
    /**
     * Pubiobjekteilla on myös merkkijonomuotoinen seliteteksti, jota voidaan
     * käyttää kun pelaaja katselee ympärilleen
     */
    private String selite;

    public Pubiobjekti(String ulkonako, boolean onkoEste, PubiobjektiEnum tyyppi, String selite) {
        this.ulkonako = ulkonako;
        this.onkoEste = onkoEste;
        this.tyyppi = tyyppi;
        this.selite = selite;
    }

    public String getUlkonako() {
        return this.ulkonako;
    }

    public boolean getEste() {
        return this.onkoEste;
    }

    public PubiobjektiEnum getTyyppi() {
        return this.tyyppi;
    }
    
    public String getSelite() {
        return this.selite;
    }
    
    public void setTyyppi(PubiobjektiEnum tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    public void setUlkonako(String ulkonako) {
        this.ulkonako = ulkonako;
    }

    public void setEste(boolean onkoEste) {
        this.onkoEste = onkoEste;
    }
    
    public void setSelite(String selite) {
        this.selite = selite;
    }
}
