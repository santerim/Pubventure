package Pubventure.ihmiset;

import pubventure.sijainti.Sijainti;
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
     * Inehmon väliaikainen/toissijainen ulkonäkö
     */
    private String vaulkomuoto;
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
    /**
     * Määrittää josko inehmo on halukas puhumaan sankarin kanssa
     */
    private boolean halukasPuhumaan;
    /**
     * Pidetään kirjaa siitä missä oltiin viimeksi
     */
    private Sijainti edellinenSijainti;
    /**
     * Joskus käy niin, että kun kaksi ihmistä kohtaa...
     */
    private boolean vosu;
    /**
     * Toisin kuin jotkut väittävät, sukupuoli ei ole pelkkä sosiaalinen
     * konstruktio, ja siksi tällä ei ole setteriä.
     */
    private InehmoEnum sukupuoli;

    public Inehmo(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuvuus, InehmoEnum sukupuoli) {
        this.sijainti = sijainti;
        this.ulkomuoto = ulkomuoto;
        this.tyyppi = tyyppi;
        this.liikkuvuus = liikkuvuus;
        this.halukasPuhumaan = true;
        this.sukupuoli = sukupuoli;

        this.humala = (double) arpoja.nextInt(51);
        this.rakko = (double) arpoja.nextInt(76);
        this.asenne = (double) arpoja.nextInt(51);
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
        if (this.vaulkomuoto != null) {
            return this.vaulkomuoto;
        } else {
            return this.ulkomuoto;
        }
    }

    public double getAsenne() {
        return this.asenne;
    }
    
    public String getAsenneKuvaus() {
        if (this.asenne < 20) {
            return "Hän vaikuttaa vihamieliseltä.";
        } else if (this.asenne >= 20 && this.asenne < 40) {
            return "Hän vaikuttaa nyrpeältä.";
        } else if (this.asenne >= 40 && this.asenne < 60) {
            return "Hän vaikuttaa välinpitämättömältä";
        } else if (this.asenne >= 60 && this.asenne < 80) {
            return "Hän vaikuttaa ystävälliseltä";
        } else if (this.asenne >= 80 && this.asenne < 100) {
            return "Hän vaikuttaa pitävän seurastasi";
        } else if (this.asenne == 100) {
            if (this.sukupuoli.equals(InehmoEnum.MIES)) {
                return "Hän saattaisi hyvinkin tarjota oluen.";
            } else if (this.sukupuoli.equals(InehmoEnum.NAINEN)) {
                return "Hmm, tämä nainen taitaa haluta seuraasi.";
            }
        }
        return null;
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

    public boolean getHalukasPuhumaan() {
        return this.halukasPuhumaan;
    }

    public InehmoEnum getSukupuoli() {
        return this.sukupuoli;
    }

    public Sijainti getEdellinenSijainti() {
        return this.edellinenSijainti;
    }

    public boolean getVosuus() {
        return this.vosu;
    }

    public void setVosuus(boolean totuusarvo) {
        this.vosu = totuusarvo;
    }

    public void setEdellinenSijainti(Sijainti sijainti) {
        this.edellinenSijainti = sijainti;
    }

    public void setHalukasPuhumaan(boolean totuusarvo) {
        this.halukasPuhumaan = totuusarvo;
    }

    public void setSelite(String selite) {
        this.selite = selite;
    }

    public void setEdellinenSuunta(KomentoEnum suunta) {
        this.edellinenSuunta = suunta;
    }

    /**
     * Muutetaan humalaa niin että se pysyy välillä 0-100
     *
     * @param muutos on annettu muutos
     */
    public void setHumala(double muutos) {
        if (this.humala + muutos > 100) {
            this.humala = 100;
        } else if (this.humala + muutos < 0) {
            this.humala = 0;
        } else {
            this.humala = humala + muutos;
        }
    }

    public void setRakko(double uusiArvo) {
        this.rakko = uusiArvo;
    }
    
    /**
     * Muutetaan asennetta (tai itsetuntoa, mikäli kyseessä on sankari). Jos
     * muutos saisi arvon menemään yli 100:n tai alle 0:n, asetetaan arvo sen
     * sijaan maksimi- tai minimiarvoonsa
     *
     * @param muutos on joko positiivinen tai negatiivinen luku
     */
    public void setAsenne(double muutos) {
        if (this.asenne + muutos > 100) {
            this.asenne = 100;
        } else if (this.asenne + muutos < 0) {
            this.asenne = 0;
        } else {
            this.asenne = this.asenne + muutos;
        }

    }

    public void setUlkomuoto(String ulkomuoto) {
        this.ulkomuoto = ulkomuoto;
    }
    
    public void setVAUlkomuoto(String ulkomuoto) {
        this.vaulkomuoto = ulkomuoto;
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
