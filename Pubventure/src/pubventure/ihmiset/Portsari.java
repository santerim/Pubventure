
package pubventure.ihmiset;

import pubventure.Sijainti;
import pubventure.enumit.InehmoEnum;

/**
 *
 * @author Santeri
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Portsari extends Inehmo {

    public Portsari(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva, InehmoEnum sukupuoli) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, sukupuoli);
        super.setSelite("portsari");
        super.setUlkomuoto("<font color='#FF8C00'>P</font>");
    }
}
