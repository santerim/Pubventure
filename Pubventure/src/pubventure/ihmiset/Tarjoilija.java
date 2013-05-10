
package pubventure.ihmiset;

import pubventure.Sijainti;
import pubventure.enumit.InehmoEnum;

/**
 *
 * @author Santeri
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