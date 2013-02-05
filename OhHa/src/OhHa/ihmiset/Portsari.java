package OhHa.ihmiset;

import OhHa.Sijainti;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Portsari extends Inehmo {

    public Portsari(Sijainti sijainti, String ulkomuoto, String tyyppi, boolean liikkuva, int asennePelaajaan) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, asennePelaajaan);
    }
}
