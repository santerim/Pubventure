package pubventure.ihmiset;

import java.util.ArrayList;
import java.util.List;
import pubventure.Sijainti;
import pubventure.enumit.InehmoEnum;
import pubventure.enumit.PubiobjektiEnum;
import pubventure.ymparisto.Pubi;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Tämä luokka hoitaa pelihahmojen luomisen ja sijoittaa ne inehmot-
 * arraylistiin. Luokan tehtävänä on lähinnä toimia Pubi-luokan apuluokkana.
 * 
 * @see Pubi
 */
public class InehmojenHallinnointi {

    private Pubi pubi;
    private int asiakkaita;
    private ArrayList<Inehmo> inehmot;
    private Pubiobjekti[][] kentta;
    /**
     * Jokainen inehmo on jotain enum-tyyppiä
     *
     * @see Pubventure.enumit.InehmoEnum
     */
    private InehmoEnum[] inehmoEnumit;
    private boolean inehmojenNakyvyys;

    public InehmojenHallinnointi(Pubi pubi, int asiakkaita, ArrayList<Inehmo> inehmot, Pubiobjekti[][] kentta) {
        this.pubi = pubi;
        this.asiakkaita = asiakkaita;
        this.inehmot = inehmot;
        this.kentta = kentta;
        this.inehmoEnumit = InehmoEnum.values();
        this.inehmojenNakyvyys = true;
    }

    /**
     * Luo pubissa olevat ihmiset. Sankari asetetaan uloskäyntiin ikäänkuin
     * vasta paikalle saapuneeksi. Tarjoilija luodaan sellaisen pubiobjektin
     * kohdalle, joka on tarjoilijoita varten tarkoitettu. Asiakkaat luodaan
     * vapaalle lattia- alueelle.
     */
    public void luoHahmot() {
        Sijainti uusiSijainti;

        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        List<Sijainti> lista = pubi.etsiPubiobjektit(PubiobjektiEnum.ULOSKAYNTI);
        if (lista != null) {
            this.inehmot.add(luoInehmo(InehmoEnum.SANKARI, lista.get(0)));
        }

        //luodaan tarjoilija
        lista = pubi.etsiPubiobjektit(PubiobjektiEnum.TALUE);
        if (lista != null) {
            this.inehmot.add(luoInehmo(InehmoEnum.TARJOILIJA, lista.get(0)));
        }

        //luodaan portsarit
        lista = pubi.etsiPubiobjektit(PubiobjektiEnum.VALUE);
        if (lista != null) {
            for (int i = 0; i < 2; i++) {
                this.inehmot.add(luoInehmo(InehmoEnum.PORTSARI, lista.get(i)));
            }
        }

        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla toistensa
        // kanssa, tai minkään esteeksi määritellyn pubiobjektin kohdalla
        int asiakkaitaJaljella = this.asiakkaita;

        while (asiakkaitaJaljella > 0) {
            uusiSijainti = pubi.arvoSijainti();

            //mikäli arvotuissa koordinaateissa ei ole estettä, tai mikäli se ei
            //ole tarjoilijoille varattua aluetta, tai vastakkaisen sukupuolen
            //vessaa, luodaan siihen uusi asiakas
            if (!pubi.tormaako(uusiSijainti)) {
                PubiobjektiEnum enumi = pubi.getObjekti(uusiSijainti).getTyyppi();
                if (!enumi.equals(PubiobjektiEnum.TALUE)
                        && !enumi.equals(PubiobjektiEnum.OVI)
                        && !enumi.equals(PubiobjektiEnum.MVESSA)
                        && !enumi.equals(PubiobjektiEnum.NVESSA)) {
                    inehmot.add(luoInehmo(InehmoEnum.ASIAKAS, uusiSijainti));
                    asiakkaitaJaljella--;
                }
            }
        }
    }

    /**
     * Luo hahmon peliin annettujen parametrien mukaan
     *
     * @param tyyppi InehmoEnum-luokan enum
     * @param sijainti on uuden inehmon saama sijainti
     * @return palauttaa valmiin inehmon
     */
    public Inehmo luoInehmo(InehmoEnum tyyppi, Sijainti sijainti) {
        switch (tyyppi) {
            case SANKARI:
                return new Sankari(sijainti, "@", InehmoEnum.SANKARI, true, InehmoEnum.MIES);
            case ASIAKAS:
                return new Asiakas(sijainti, "a", InehmoEnum.ASIAKAS, true, arvoSukupuoli(), arvoIka());
            case PORTSARI:
                return new Portsari(sijainti, "P", InehmoEnum.PORTSARI, false, InehmoEnum.MIES);
            case TARJOILIJA:
                return new Tarjoilija(sijainti, "t", InehmoEnum.TARJOILIJA, true, arvoSukupuoli());
        }
        return null;
    }

    public InehmoEnum arvoIka() {
        int satunnainen = pubi.arvoLuku(3);
        return inehmoEnumit[satunnainen];
    }

    public InehmoEnum arvoSukupuoli() {
        int satunnainen = pubi.arvoLuku(2) + 7;
        return inehmoEnumit[satunnainen];
    }

    public void setInehmojenNakyvyys(boolean arvo) {
        this.inehmojenNakyvyys = arvo;
        if (arvo) {
            for (Inehmo inehmo : inehmot) {
                inehmo.setNakyvyys(true);
            }
        } else {
            for (Inehmo inehmo : inehmot) {
                if (inehmo.getTyyppi() != InehmoEnum.SANKARI) {
                    inehmo.setNakyvyys(false);
                }
            }
        }
    }

    public boolean getInehmojenNakyvyys() {
        return this.inehmojenNakyvyys;
    }
}
