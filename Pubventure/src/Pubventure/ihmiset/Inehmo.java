package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;
import Pubventure.enumit.KomentoEnum;
import java.util.Random;

/**
 *
 * Pelin ns. ihmisluokka. Tarjoaa konstruktorin hahmojen luomiseen, sekä metodit
 * hahmojen ominaisuuksien kyselyyn ja asettamiseen
 */
public class Inehmo {

    private String ulkomuoto;
    private InehmoEnum tyyppi;
    private boolean liikkuvuus;
    private int asenne;
    private Sijainti sijainti;
    private int humala;
    private int rakko;
    private Random arpoja = new Random();
    private String edellinenSuunta;

    public Inehmo(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuvuus) {
        this.sijainti = sijainti;
        this.ulkomuoto = ulkomuoto;
        this.tyyppi = tyyppi;
        this.liikkuvuus = liikkuvuus;
        
        this.humala = arpoja.nextInt(51);
        this.rakko = arpoja.nextInt(101);
        this.asenne = arpoja.nextInt(51);
    }

    /**
     * Luo satunnaisluvun
     * @param maksimi on maksimiarvo + 1 halutulle tulokselle
     * @return palauttaa arvotun kokonaisluvun
     */
    private int arvoLuku(int maksimi) {
        return arpoja.nextInt(maksimi);
    }

    //ehkä ei
//    public void liiku(SuuntaEnum suunta) {
//    }
    
    public Sijainti getSijainti() {
        return this.sijainti;
    }

    /**
     * 
     * @return palauttaa true, jos inehmo on enum-tyyppiä SANKARI
     */
    public boolean getSankaruus() {
        if (this.tyyppi == InehmoEnum.SANKARI) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getLiikkuvuus() {
        return this.liikkuvuus;
    }

    public InehmoEnum getTyyppi() {
        return this.tyyppi;
    }

    public String getUlkomuoto() {
        return this.ulkomuoto;
    }

    public int getAsenne() {
        return this.asenne;
    }

    public int getHumala() {
        return this.humala;
    }

    public int getRakko() {
        return this.rakko;
    }

    public String getEdellinenSuunta() {
        return this.edellinenSuunta;
    }

    //ei vielä käytössä
    public void setEdellinenSuunta(String suunta) {
        this.edellinenSuunta = suunta;
    }

    public void setHumala(int humala) {
        this.humala = humala;
    }

    public void setRakko(int rakko) {
        this.rakko = rakko;
    }

    public void setAsenne(int muutos) {
        this.asenne += muutos;
    }

    public void setUlkomuoto(String ulkomuoto) {
        this.ulkomuoto = ulkomuoto;
    }

    public void setLiikkuva(boolean liikkuva) {
        this.liikkuvuus = liikkuva;
    }

    public void setTyyppi(InehmoEnum tyyppi) {
        this.tyyppi = tyyppi;
    }

    //tämä ei vielä käytössä - muutettava!!
    public void setSijainti(Sijainti uusiSijainti) {
        this.sijainti = uusiSijainti;
    }
    
    
    // tarkastamatta
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        
//        if (obj.getClass() == Asiakas.class) {
//            return false;
//        }
//        
//        if (obj.getClass() == Sankari.class) {
//            return false;
//        }
//        
//        if (obj.getClass() == Portsari.class) {
//            return false;
//        }
//        
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Inehmo other = (Inehmo) obj;
//        if (this.sijainti.getX() != other.getSijainti().getX()) {
//            return false;
//        }
//        if (this.sijainti.getY() != other.getSijainti().getY()) {
//            return false;
//        }
//        return true;
//    }
//    
//    //tarkastamatta
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 37 * hash + this.sijainti.getX();
//        hash = 37 * hash + this.sijainti.getY();
//        return hash;
//    }
}
