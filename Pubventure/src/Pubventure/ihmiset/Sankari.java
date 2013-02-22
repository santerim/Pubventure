package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;
import java.util.Random;

/**
 *
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */
public class Sankari extends Inehmo {

    /**
     * Sankarin rahavarat
     */
    private double rahat;
    /**
     * Juomat (olut) desilitroina
     */
    private int juomat;
    /**
     * Käytetään sen selvittämiseen, josko humala (ja sen myötä itsetunto) on
     * nousussa, ja josko rakko täyttyy
     */
    private double juomatVatsassa;
    /**
     * Nousuhumalalla on merkitystä sen suhteen miten attribuutit muuttuvat
     */
    private boolean nousuhumala;
    /**
     * Jos rakko on aivan täynnä, ei tee mieli juoda lisää.
     */
    private boolean rakkoTaynna;
    /**
     * Satunnaisuutta saadaan arpomalla lukuja
     */
    private Random arpoja = new Random();
    /**
     * Itsetunto vaikuttaa joidenkin toimintojen onnistumismahdollisuuksiin
     */
    private double itsetunto;

    public Sankari(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva, InehmoEnum sukupuoli) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, sukupuoli);
        this.rahat = 10;
        this.itsetunto = arpoja.nextInt(50);
    }

    /**
     * Tämän metodin tarkoitus on luoda tasaiseen tahtiin tapahtuvat muutokset
     * hahmon ominaisuuksissa. Esim juodun oluen on tarkoitus hiljalleen nostaa
     * humalatilaa jne.
     */
    public void paivitaMuuttujat() {
        double rakonMuutos = 3.0;
        if (juomatVatsassa > 0) {
            nousuhumala = true;
            juomatVatsassa = juomatVatsassa - 0.25;
            super.setHumala(0.5);
            if (super.getRakko() + rakonMuutos < 100 && juomatVatsassa > 0) {
                super.setRakko(super.getRakko() + rakonMuutos);
            } else if (super.getRakko() + rakonMuutos > 100) {
                super.setRakko(100);
                this.rakkoTaynna = true;
            }
        } else {
            nousuhumala = false;
        }




        if (nousuhumala) {
            setAsenne(0.75);
        } else {
            super.setHumala(-0.15);
            setAsenne(-0.10);
        }
    }

    public double getRahat() {
        return this.rahat;
    }

    public int getJuomat() {
        return this.juomat;
    }

    public double getJuotuAlkoholi() {
        return this.juomatVatsassa;
    }

    public boolean getOnkoRakkoTaynna() {
        return this.rakkoTaynna;
    }

    public void setJuomatVatsassa(int muutos) {
        this.juomatVatsassa += muutos;
    }

    /**
     * Yrittää asettaa uuden rahalukeman.
     *
     * @param muutos on muutosarvo, voi olla negatiivinen
     * @return palauttaa true jos muutos onnistui, eli ei menty miinukselle
     */
    public boolean setRahat(double muutos) {
        if (rahat + muutos < 0) {
            return false;
        } else {
            rahat = rahat + muutos;
            return true;
        }
    }

    public void setJuomat(int juomat) {
        this.juomat += juomat;
    }

    public void setRakkoTaynna(boolean totuusarvo) {
        this.rakkoTaynna = totuusarvo;
    }

    @Override
    public double getAsenne() {
        return this.itsetunto;
    }

    @Override
    public void setAsenne(double muutos) {
        if (this.itsetunto + muutos > 100) {
            this.itsetunto = 100;
        } else if (this.itsetunto + muutos < 0) {
            this.itsetunto = 0;
        } else {
            this.itsetunto = this.itsetunto + muutos;
        }
    }
}
