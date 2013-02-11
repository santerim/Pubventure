
package Pubventure.ihmiset;

import Pubventure.Sijainti;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Asiakas extends Inehmo {

    public Asiakas(Sijainti sijainti, String ulkomuoto, String tyyppi, boolean liikkuva, int asennePelaajaan) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, asennePelaajaan);
    }
    
}
