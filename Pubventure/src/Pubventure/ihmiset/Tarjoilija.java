
package Pubventure.ihmiset;

import Pubventure.Sijainti;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Tarjoilija extends Inehmo {

    public Tarjoilija(Sijainti sijainti, String ulkomuoto, String tyyppi, boolean liikkuva, int asennePelaajaan) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, asennePelaajaan);
    }
    
}
