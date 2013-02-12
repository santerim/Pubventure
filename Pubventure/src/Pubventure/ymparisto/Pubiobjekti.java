package Pubventure.ymparisto;

import Pubventure.enumit.PubiobjektiEnum;

/**
 * 
 * Tämä luokka tarjoaa Pubi-luokan käyttämät ei-ihmisoliot.
 */
public class Pubiobjekti {

    private boolean onkoEste;
    private String ulkonako;
    private PubiobjektiEnum tyyppi;

//    public Pubiobjekti() {
//    }

    public Pubiobjekti(String ulkonako, boolean onkoEste, PubiobjektiEnum tyyppi) {
        this.ulkonako = ulkonako;
        this.onkoEste = onkoEste;
        this.tyyppi = tyyppi;
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
    
    public void setTyyppi(PubiobjektiEnum tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    public void setUlkonako(String ulkonako) {
        this.ulkonako = ulkonako;
    }

    public void setEste(boolean onkoEste) {
        this.onkoEste = onkoEste;
    }
}
