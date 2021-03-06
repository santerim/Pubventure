
package pubventure.ymparisto;

import pubventure.Sijainti;
import pubventure.enumit.PubiobjektiEnum;

/**
 *
 * @author Santeri
 * 
 * Tämä luokka tarjoaa Pubi-luokan käyttämät ei-ihmisoliot.
 */
public class Pubiobjekti {

    /**
     * Se, että onko jokin pubiobjekti este vai ei, on oleellista törmäysten
     * selville saamisessa
     */
    private boolean onkoEste = false;
    
    /**
     * Pubiobjektilla on yleensä yksilöllinen ulkonäkö, joka tulostetaan
     * kenttää piirrettäessä
     */
    private String ulkonako;
    
    /**
     * Väliaikainen ulkonäkö pubiobjektille
     */
    private String vaulkonako;
    
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
    
    /**
     * Reitinetsintää varten tarvitsemme muuttujan, joka pitää sisällään
     * pubiobjektin "hinnan" sen yli liikkumiseen. Esteet saavat Integerin
     * maksimiarvon.
     */
    private int hidastearvo;
    
    /**
     * Jokaisella pubiobjektilla on myös sijainti
     */
    private Sijainti sijainti;
    
    /**
     * Reitinetsinnässä tarvittava f-arvo, joka on summa etäisyydestä alku-
     * ja loppupisteisiin
     */
    private int f;
    
    /**
     * Reitinetsinnässä tarvittava g-arvo, joka on "hinta" kulkemisesta
     * lähtöpisteeseen
     */
    private int g;
    
    /**
     * Reitinetsinnässä tarvittava h-arvo, joka on arvioitu etäisyys maaliin
     */
    private int h;
    
    /**
     * Reitinetsinnässä tarvittava tieto siitä, mistä Pubiobjektista tultiin
     * nykyiseen
     */
    private Pubiobjekti edellinen;
    
    private boolean avoimissa;
    
    private boolean tutkituissa;
    
    /**
     * Tämän tarkoitus on tuoda julki, josko tuoli, wc-pytty, tai tiski on
     * varattu.
     */
    private boolean varattu;

    /**
     * Luo uuden pubiobjektin kaikilla sille kuuluvilla määreillä
     * 
     * @param ulkonako on pelikentällä nähtävä ulkonäkö, mikäli muuttuja
     * vaulkonäkö on null
     * 
     * @param onkoEste määrittää josko tämän pubiobjektin päälle voi kulkea.
     * Esim. tuoli ei ole este, mutta seinä on.
     * 
     * @param tyyppi on pubiobjektin enum-tyyppi, esim. lattia, pöytä jne.
     * 
     * @param selite on merkkijono, joka poimitaan muodostettavaan lauseeseen,
     * mikäli pelaajahahmo (t)utkii tätä pubiobjektia
     * 
     * @param sijainti on pubiobjektin Sijainti-olio, joka nimensä mukaisesti
     * pitää kirjaa sen sijainnista
     * 
     * @param hidastearvo määrittää kuinka paljon kyseisen pubiobjektin päältä
     * kävely hidastaa menoa. Esim. tuolin yli kulkeminen on hitaampaa kuin
     * tyhjällä lattialla
     */
    public Pubiobjekti(String ulkonako, boolean onkoEste, PubiobjektiEnum tyyppi, String selite, Sijainti sijainti, int hidastearvo) {
        this.ulkonako = ulkonako;
        this.onkoEste = onkoEste;
        this.tyyppi = tyyppi;
        this.selite = selite;
        this.sijainti = sijainti;
        this.hidastearvo = hidastearvo;
    }
    
    /**
     * Tämä konstruktori on vain reitinhakualgoritmien testausta varten, koska
     * siinä on mielekästä pystyä luomaan pubiobjekti ilman pitkää litaniaa
     * tilanteessa tarpeettomia määreitä.
     */
    public Pubiobjekti() {
    }

    public String getUlkonako() {
        if (vaulkonako != null) {
            return this.vaulkonako;
        } else {
            return this.ulkonako;
        }
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
    
    public int getHidastearvo() {
        return this.hidastearvo;
    }
    
    public Sijainti getSijainti() {
        return this.sijainti;
    }
    
    public int getX() {
        return this.sijainti.getX();
    }
    
    public int getY() {
        return this.sijainti.getY();
    }
    
    public void setTyyppi(PubiobjektiEnum tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    public void setUlkonako(String ulkonako) {
        this.ulkonako = ulkonako;
    }

    public void setVAUlkonako(String ulkonako) {
        this.vaulkonako = ulkonako;
    }
    
    public void setEste(boolean onkoEste) {
        this.onkoEste = onkoEste;
    }
    
    public void setSelite(String selite) {
        this.selite = selite;
    }
    
    public void setHidastearvo(int arvo) {
        this.hidastearvo = arvo;
    }
    
    /**
     * HUOM! Jokaiselle Pubiobjektille annetaan sijainti luomisen yhteydessä,
     * joten tätä metodia ei pidä käyttää muuhun kuin testaukseen!
     * @param sijainti on uusi Sijainti-olio Pubiobjektille.
     * @see Sijainti
     */
    public void setSijainti(Sijainti sijainti) {
        this.sijainti = sijainti;
    }
    
    public int getH() {
        return this.h;
    }
    
    public int getG() {
        return this.g;
    }
    
    public int getF() {
        return this.f;
    }
    
    public void setG(int arvo) {
        this.g = arvo;
    }
    
    public void setH(int arvo) {
        this.h = arvo;
    }
    
    public void setF(int arvo) {
        this.f = arvo;
    }
    
    public void setEdellinen(Pubiobjekti edellinen) {
        this.edellinen = edellinen;
    }
    
    public Pubiobjekti getEdellinen() {
        return this.edellinen;
    }
    
    public boolean getAvoimissa() {
        return this.avoimissa;
    }
    
    public boolean getTutkituissa() {
        return this.tutkituissa;
    }
    
    public void setAvoimissa(boolean totuusarvo) {
        this.avoimissa = totuusarvo;
    }
    
    public void setTutkituissa(boolean totuusarvo) {
        this.tutkituissa = totuusarvo;
    }
    
    public void setVarattu(boolean arvo) {
        this.varattu = arvo;
    }
    
    public boolean getVarattu() {
        return this.varattu;
    }
    
    // TARKISTETTAVA!
    @Override
    public boolean equals(Object object) {
        Pubiobjekti vertailtava = (Pubiobjekti) object;
        if (this.sijainti.getX() == vertailtava.sijainti.getX() 
                && this.sijainti.getY() == vertailtava.sijainti.getY()) {
            return true;
        } else {
            return false;
        }
    }
}