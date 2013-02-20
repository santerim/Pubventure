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

    /**
     * Miltä inehmo näyttää pelikentällä
     */
    private String ulkomuoto;
    
    /**
     * Mihin suuntaan oltiin viimeksi menossa
     */
    private KomentoEnum edellinenSuunta;
    
    /**
     * Teksti joka näkyy kun pelaajahahmo tutkii kohdetta
     */
    private String selite;
    
    /**
     * Olion tyyppi
     */
    private InehmoEnum tyyppi;
    
    /**
     * Käytetään määrittelemään josko inehmo pysyy paikallaan
     */
    private boolean liikkuvuus;
    
    /**
     * Asenne pelaajaa kohtaan. Pelaaja-inehmon ollessa kyseessä toimii
     * itsetunto-lukemana
     */
    private double asenne;
    
    /**
     * Humalan aste
     */
    private double humala;
    
    /**
     * Tarve käydä ns. heittämässä vettä
     */
    private double rakko;
    
    /**
     * Satunnaislukujen luoja
     */
    private Random arpoja = new Random();
    
    /**
     * Hahmon sijainnista kirjaa pitävä olio
     */
    private Sijainti sijainti;

    public Inehmo(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuvuus) {
        this.sijainti = sijainti;
        this.ulkomuoto = ulkomuoto;
        this.tyyppi = tyyppi;
        this.liikkuvuus = liikkuvuus;

        this.humala = (double) arpoja.nextInt(51);
        this.rakko = (double) arpoja.nextInt(76);
        this.asenne = (double) arpoja.nextInt(101);
    }

    /**
     * Luo satunnaisluvun
     *
     * @param maksimi on maksimiarvo + 1 halutulle tulokselle
     * @return palauttaa arvotun kokonaisluvun
     */
    private int arvoLuku(int maksimi) {
        return arpoja.nextInt(maksimi);
    }

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

    public double getAsenne() {
        return this.asenne;
    }

    public double getHumala() {
        return this.humala;
    }

    public double getRakko() {
        return this.rakko;
    }

    //ei vielä käytössä
    public KomentoEnum getEdellinenSuunta() {
        return this.edellinenSuunta;
    }

    public String getSelite() {
        return this.selite;
    }

    public void setSelite(String selite) {
        this.selite = selite;
    }

    //ei vielä käytössä
    public void setEdellinenSuunta(KomentoEnum suunta) {
        this.edellinenSuunta = suunta;
    }

    public void setHumala(double muutos) {
        if (this.humala + muutos < 101 && this.humala + muutos > -1) {
            this.humala = this.humala + muutos;
        }
    }

    public void setRakko(double uusiArvo) {
        this.rakko = uusiArvo;
    }

    /**
     * Muutetaan asennetta (tai itsetuntoa, mikäli kyseessä on sankari), mikäli
     * attribuutti pysyisi välillä 0...100
     * @param muutos 
     */
    public void setAsenne(double muutos) {
        if (this.asenne < 101 && muutos > 0) {
            this.asenne += muutos;
        } else if (muutos < 0 && this.asenne > 0) {
            this.asenne += muutos;
        } 
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

    public void setSijainti(Sijainti uusiSijainti) {
        this.sijainti = uusiSijainti;
    }
    
    // tarkastamatta...eikä kenties tulekaan käyttöön
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
//    //tarkastamatta, sama kuin yllä
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 37 * hash + this.sijainti.getX();
//        hash = 37 * hash + this.sijainti.getY();
//        return hash;
//    }
}
