package pubventure.ihmiset;

import java.util.ArrayList;
import java.util.List;
import pubventure.Logiikka;
import pubventure.Sijainti;
import pubventure.enumit.AieEnum;
import pubventure.enumit.InehmoEnum;
import pubventure.enumit.PubiobjektiEnum;
import pubventure.reittialgot.Astar;
import pubventure.reittialgot.Dijkstra;
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
    /**
     * Jokainen inehmo on jotain enum-tyyppiä
     *
     * @see Pubventure.enumit.InehmoEnum
     */
    private InehmoEnum[] inehmoEnumit;
    private boolean inehmojenNakyvyys;
    private boolean asiakkaatLiikkuvat;
    private Logiikka log;
    private List<Sijainti> tuolit;
    private List<Sijainti> tiski;
    private List<Sijainti> miestenPytyt;
    private List<Sijainti> naistenPytyt;
    private Astar astar;
    private Dijkstra dijkstra;

    public InehmojenHallinnointi(Pubi pubi, int asiakkaita, ArrayList<Inehmo> inehmot, boolean asiakkaatLiikkuvat, Logiikka log) {
        this.pubi = pubi;
        this.asiakkaita = asiakkaita;
        this.inehmot = inehmot;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;
        this.log = log;
        this.inehmoEnumit = InehmoEnum.values();
        this.inehmojenNakyvyys = true;

        this.astar = new Astar(pubi);
        this.dijkstra = new Dijkstra(pubi);

        this.tuolit = pubi.etsiPubiobjektit(PubiobjektiEnum.TUOLI);
        this.tiski = pubi.etsiPubiobjektit(PubiobjektiEnum.TISKI);
        this.miestenPytyt = pubi.etsiPubiobjektit(PubiobjektiEnum.WCPYTTYM);
        this.naistenPytyt = pubi.etsiPubiobjektit(PubiobjektiEnum.WCPYTTYN);
    }

    private void paivitaAikeet() {
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getTyyppi() == InehmoEnum.ASIAKAS) {
                if (!inehmo.getTekeeJotain()) {
                    if (inehmo.getKusettaa()) {
                        inehmo.setAieEnum(AieEnum.KAYVESSASSA);
                        inehmo.setTekeeJotain(true);
                        continue;
                    } else if (inehmo.getJanottaa()) {
                        inehmo.setAieEnum(AieEnum.KAYTISKILLA);
                        inehmo.setTekeeJotain(true);
                        continue;
                    } else {
                        if (inehmo.getAieEnum() != AieEnum.ISTU) {
                            inehmo.setAieEnum(AieEnum.MENEISTUMAAN);
                            inehmo.setTekeeJotain(true);
                        }
                    }
                } else {
                    paivitaLiikkeet(inehmo);
                }
            }
        }
    }

    public void paivita() {
        paivitaArvot();
        paivitaAikeet();

    }

    private void paivitaLiikkeet(Inehmo inehmo) {
        if (!inehmo.getSeuraaReittia()) {
            switch (inehmo.getAieEnum()) {
                case KAYTISKILLA:
                    // etsitään reitti satunnaiseen kohtaan tiskillä
                    System.out.println("Tiskille");
                    inehmo.setReitti(astar.etsiReitti(
                            pubi.getObjekti(inehmo.getSijainti()),
                            pubi.getObjekti(tiski.get(pubi.arvoLuku(tiski.size())))));
                    inehmo.setSeuraaReittia(true);
                    break;
                case MENEISTUMAAN:
                    System.out.println("Istumaan");
                    // etsitään reitti takaisin sinne, mistä tultiinkin
                    inehmo.setReitti(astar.etsiReitti(
                            pubi.getObjekti(inehmo.getSijainti()),
                            inehmo.getPaikka()));
                    inehmo.setSeuraaReittia(true);
                    break;
            }
        } else {
            seuraaReittia(inehmo);
        }

    }

    private void seuraaReittia(Inehmo inehmo) {
        Sijainti seuraava;
        if (inehmo.getReitti()[0] != null) {
            seuraava = inehmo.getReitti()[0].getSijainti();

            if (!log.onkoSiinaJoku(seuraava)) {
                inehmo.setSijainti(seuraava);
                poistaReitistaEka(inehmo);
            }
        } else {
            inehmo.setSeuraaReittia(false);
            inehmo.setTekeeJotain(false);
            inehmo.setAieEnum(AieEnum.ISTU);
        }
    }

    private void poistaReitistaEka(Inehmo inehmo) {
        Pubiobjekti[] uusiReitti = new Pubiobjekti[50];
        System.arraycopy(inehmo.getReitti(), 1, uusiReitti, 0, inehmo.getReitti().length - 1);
        inehmo.setReitti(uusiReitti);
    }

    private void paivitaArvot() {
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getTyyppi() == InehmoEnum.ASIAKAS) {
                if (!inehmo.getTekeeJotain()) {
                    if (inehmo.getOdotus() >= 99) {
                        inehmo.setOdotus(0);
                        if (inehmo.getSijainti().equals(inehmo.getPaikka().getSijainti())) {
                            inehmo.setAieEnum(AieEnum.KAYTISKILLA);
                        } else {
                            inehmo.setAieEnum(AieEnum.MENEISTUMAAN);
                        }
                        inehmo.setTekeeJotain(true);
                    } else {
                        inehmo.setOdotus(inehmo.getOdotus() + 5);
                    }
                }
            }
        }
    }

    private void paivitaVatsaJaRakko(Inehmo inehmo) {
    }

    private void paivitaHumala(Inehmo inehmo) {
    }

    /**
     * Luo pubissa olevat ihmiset. Sankari asetetaan uloskäyntiin ikäänkuin
     * vasta paikalle saapuneeksi. Tarjoilija luodaan sellaisen pubiobjektin
     * kohdalle, joka on tarjoilijoita varten tarkoitettu. Asiakkaat luodaan
     * vapaalle lattia- alueelle.
     */
    public void luoHahmot() {
//        Sijainti uusiSijainti;

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
            // haetaan tuoli, ja luodaan asiakaat tuoleille, merkiten jokaisen
            // tuolille asettamisen jälkeen ko. tuoli varatuksi

            int nro = pubi.arvoLuku(tuolit.size());

            if (!pubi.getObjekti(tuolit.get(nro)).getVarattu()) {
                Inehmo uusiInehmo = luoInehmo(InehmoEnum.ASIAKAS, tuolit.get(nro));
                uusiInehmo.setPaikka(pubi.getObjekti(uusiInehmo.getSijainti()));
                uusiInehmo.setAieEnum(AieEnum.ISTU);
                inehmot.add(uusiInehmo);
                asiakkaitaJaljella--;

                pubi.getObjekti(tuolit.get(nro)).setVarattu(true);
            }



//            uusiSijainti = pubi.arvoSijainti();
//
//            //mikäli arvotuissa koordinaateissa ei ole estettä, tai mikäli se ei
//            //ole tarjoilijoille varattua aluetta, tai vastakkaisen sukupuolen
//            //vessaa, luodaan siihen uusi asiakas
//            if (!pubi.tormaako(uusiSijainti)) {
//                PubiobjektiEnum enumi = pubi.getObjekti(uusiSijainti).getTyyppi();
//                if (!enumi.equals(PubiobjektiEnum.TALUE)
//                        && !enumi.equals(PubiobjektiEnum.OVI)
//                        && !enumi.equals(PubiobjektiEnum.MVESSA)
//                        && !enumi.equals(PubiobjektiEnum.NVESSA)) {
//                    inehmot.add(luoInehmo(InehmoEnum.ASIAKAS, uusiSijainti));
//                    asiakkaitaJaljella--;
//                }
//            }
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

    public int getAsiakkaita() {
        return this.asiakkaita;
    }

    public ArrayList<Inehmo> getInehmot() {
        return this.inehmot;
    }
}
