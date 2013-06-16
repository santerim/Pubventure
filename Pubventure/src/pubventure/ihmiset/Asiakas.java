
package pubventure.ihmiset;

import java.util.ArrayList;
import pubventure.Sijainti;
import pubventure.enumit.InehmoEnum;
import pubventure.enumit.PubiobjektiEnum;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Inehmo-luokan laajennus, jonka tarkoitus on...laajentaa sen toimintaa.
 */

public class Asiakas extends Inehmo {

    /**
     * Ikämuuttuja InehmoEnumina
     * @see Pubventure.enumit.InehmoEnum
     */
    private InehmoEnum ika;
    
    /**
     * Asiakkaan tuntemat henkilöt, tai sellaiset joiden seuraan tämä hakeutuu.
     */
    private ArrayList<Inehmo> tutut;
    
    /**
     * Kun tulee tarve siirtyä jonnekin, etsitään reitti johonkin tiettyyn
     * kohteeseen (baaritiski, vessa jne).
     */
    private Pubiobjekti kohde;
    
    
    
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
    
    public void setKohde(Pubiobjekti kohde) {
        this.kohde = kohde;
    }
    
    public Pubiobjekti getKohde() {
        return this.kohde;
    }
    
    public ArrayList<Inehmo> getTutut() {
        return this.tutut;
    }
    
    public void lisaaTuttu(Inehmo inehmo) {
        this.tutut.add(inehmo);
    }
    
    public void poistaTuttu(Inehmo inehmo) {
            this.tutut.remove(inehmo);
    }
}
