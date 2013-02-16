
package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Sankari extends Inehmo {

    private int rahat;
    private int juotavaa;
    
    public Sankari(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva);
        this.rahat = 50;
    }
    
    public int getRahat() {
        return this.rahat;
    }
    
    public boolean setRahat(int muutos) {
        if (rahat + muutos < 0) {
            return false;
        } else {
            rahat = rahat + muutos;
            return true;
        }
    }
}
