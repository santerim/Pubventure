
package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Sankari extends Inehmo {

    private double rahat;
    private int juomat;
    private double juomatVatsassa;
    private boolean nousuhumala;
    private boolean rakkoTaynna;
    
    
    public Sankari(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva);
        this.rahat = 30;
    }
    
    /**
     * Tämän metodin tarkoitus on luoda tasaiseen tahtiin tapahtuvat muutokset
     * hahmon ominaisuuksissa. Esim juodun oluen on tarkoitus hiljalleen
     * nostaa humalatilaa jne.
     */
    public void paivitaMuuttujat() {
        if (juomatVatsassa > 0) {
            nousuhumala = true;
            juomatVatsassa = juomatVatsassa - 0.25;
            super.setHumala(0.5);
        } else {
            nousuhumala = false;
        }
        
        if (super.getRakko() < 99 && juomatVatsassa > 0) {
            super.setRakko(super.getRakko() + 0.5);
        } else if (super.getRakko() >= 99) {
            this.rakkoTaynna = true;
        }
        
        if (nousuhumala) {
            super.setAsenne(0.5);
        } else {
//            super.setHumalanMuutos(-1);
//            super.setAsenne(-1);
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
}
