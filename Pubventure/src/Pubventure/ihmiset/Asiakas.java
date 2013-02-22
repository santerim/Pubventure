
package Pubventure.ihmiset;

import Pubventure.Sijainti;
import Pubventure.enumit.InehmoEnum;

/**
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Asiakas extends Inehmo {

    /**
     * Ikämuuttuja InehmoEnumina
     * @see Pubventure.enumit.InehmoEnum
     */
    private InehmoEnum ika;
    
    public Asiakas(Sijainti sijainti, String ulkomuoto, InehmoEnum tyyppi, boolean liikkuva, InehmoEnum sukupuoli, InehmoEnum ika) {
        super(sijainti, ulkomuoto, tyyppi, liikkuva, sukupuoli);

        this.ika = ika;
        
        /**
         * Korvataan oletusulkomuoto sukupuolen ja iän perusteella valitulla
         * ulkomuodolla. Nainen on "n" ja mies "m". Mitä nuorempi, sitä
         * vaaleampi väri.
         */
        if (super.getSukupuoli().equals(InehmoEnum.NAINEN)) {
            if (this.ika.equals(InehmoEnum.NUORI)) {
                setUlkonako("<font color='#FFB6C1'>n</font>");
                super.setSelite("nuori nainen");
            } else if (this.ika.equals(InehmoEnum.KESKIIKAINEN)) {
                setUlkonako("<font color='#F08080'>n</font>");
                super.setSelite("keski-ikäinen nainen");
            } else if (this.ika.equals(InehmoEnum.VANHA)) {
                setUlkonako("<font color='#CB0859'>n</font>");
                super.setSelite("vanha nainen");
            }
        }
        
        if (super.getSukupuoli().equals(InehmoEnum.MIES)) {
            if (this.ika.equals(InehmoEnum.NUORI)) {
                setUlkonako("<font color='#87CEFA'>m</font>");
                super.setSelite("nuori mies");
            } else if (this.ika.equals(InehmoEnum.KESKIIKAINEN)) {
                setUlkonako("<font color='#1E90FF'>m</font>");
                super.setSelite("keski-ikäinen mies");
            } else if (this.ika.equals(InehmoEnum.VANHA)) {
                setUlkonako("<font color='#1E90FF'>m</font>");
                super.setSelite("vanha mies");
            }
        }
        
        
    }
    
    private void setUlkonako(String ulkonako) {
        super.setUlkomuoto(ulkonako);
    }
    
    public InehmoEnum getIka() {
        return this.ika;
    }
}
