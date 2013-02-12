
package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Tarjoilija extends Inehmo {

    public Tarjoilija(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva);
    }
    
}
