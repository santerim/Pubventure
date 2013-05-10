
package Pubventure.ihmiset;

import pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Tarjoilija extends Inehmo {
    
//    private InehmoEnum sukupuoli;

    public Tarjoilija(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva, InehmoEnum sukupuoli) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, sukupuoli);
//        this.sukupuoli = sukupuoli;
    }
    
}
