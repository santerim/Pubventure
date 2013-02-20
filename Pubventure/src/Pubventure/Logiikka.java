package Pubventure;

import Pubventure.enumit.InehmoEnum;
import Pubventure.enumit.KomentoEnum;
import Pubventure.enumit.PubiobjektiEnum;
import Pubventure.gui.Kayttoliittyma;
import Pubventure.ihmiset.Inehmo;
import Pubventure.ihmiset.Sankari;
import Pubventure.ymparisto.Pubi;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;

/**
 *
 * Luokka toimii pääasiallisena ohjelman toiminnan ohjaajana. Se luo Pubi-luokan
 * ja suorittaa sen ne metodit, jotka luovat pelikentän, sekä luo swing-ikkunaa
 * hallitsevan Kayttoliittyma-luokan.
 */
public class Logiikka {

    private Pubi pubi;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
    private ArrayList<Inehmo> inehmot;
    private Random arpoja = new Random();
    private Kayttoliittyma kl;
    private KomentoEnum[] komennot;
    private Sankari sankari;

    public Logiikka(int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.siirtoja = siirtoja;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;
        this.komennot = KomentoEnum.values();

        this.pubi = new Pubi(asiakkaita);
        pubi.luoKentta();
        pubi.luoHahmot();

        this.inehmot = pubi.getInehmot();
        this.sankari = (Sankari) inehmot.get(0);
    }

    /**
     * Aloittaa pelin suorittamisen luomalla käyttöliittymän
     */
    public void aloita() {
        this.kl = new Kayttoliittyma(pubi, inehmot, sankari, this);
        setViestiKentanSisalto(KomentoEnum.LIIKE, "");
        SwingUtilities.invokeLater(kl);
    }

    /**
     * Viestikentän tavanomaisten selitetekstien muutokset Kayttoliittyma-
     * luokkaa hyödyntäen.
     *
     * @see
     * Pubventure.gui.Kayttoliittyma#setViestiKentanSisalto(java.lang.String)
     *
     * @param komento Enum, joka määrittää mikä teksti viestikenttään laitetaan
     */
    public void setViestiKentanSisalto(KomentoEnum komento, String syote) {
        switch (komento) {
            case ODOTUS:
                kl.setViestiKentanSisalto("Odotat hetken");
                break;
            case OHJE:
                kl.naytaOhjeet();
                break;
            case LIIKE:
                kl.setViestiKentanSisalto("Liiku, anna komento, tai paina &lt;Enter&gt; näyttääksesi ohjeet");
                break;
            case SUUNTA:
                kl.setViestiKentanSisalto("Anna suunta tai &lt;esc&gt; &#47; &lt;space&gt; peruaksesi");
                break;
            case PERU:
                setViestiKentanSisalto(KomentoEnum.LIIKE, "");
                break;
            case VIESTI:
                kl.setViestiKentanSisalto(syote);
        }
    }

    /**
     * Kutsuu komennon tyypin edellyttämää metodia, sekä piirtää kentän
     * uudestaan.
     *
     * @param komento on Nappaimistonkuuntelijalta Kayttoliittyman kautta tullut
     * komento
     */
    public void kasitteleKomento(KomentoEnum komento) {
        if (komento == KomentoEnum.POHJOINEN || komento == KomentoEnum.ITA
                || komento == KomentoEnum.ETELA || komento == KomentoEnum.LANSI
                || komento == KomentoEnum.ODOTUS) {
            setViestiKentanSisalto(KomentoEnum.LIIKE, "");
            kasitteleLiikekomento(komento, inehmot.get(0));
            
        }

        switch (komento) {
            // Tässä tapauksessa jäädään odottamaan, että käyttäjä antaa
            // kaksivaiheisen komennon jälkimmäisen osan, eli suunnan.
            // Viestikenttään päivitetään kehotus suunnan antamiseen.
            case SUUNTA:
                if (komento == KomentoEnum.SUUNTA) {
                    setViestiKentanSisalto(KomentoEnum.SUUNTA, "");
                }
                break;
            case ODOTUS:
                setViestiKentanSisalto(KomentoEnum.ODOTUS, "");
                break;
            case OHJE:
                setViestiKentanSisalto(KomentoEnum.OHJE, "");
                return;
            case JUO:
                if (sankari.getOnkoRakkoTaynna()) {
                    kl.setViestiKentanSisalto("Nyt kusettaa kyllä liikaa!");
                } else {
                    if (sankari.getJuomat() > 0) {
                        sankari.setJuomat(-1);
                        sankari.setJuomatVatsassa(1);
                        kl.kirjoitaPelaajanTiedot();
                        kl.setViestiKentanSisalto("Hörp.");
                    } else {
                        kl.setViestiKentanSisalto("Tuoppisi on tyhjä!");
                    }
                }

        }

        //liikutetaan muita kuin sankaria, mikäli ei odoteta jatkokomentoa
        if (!kl.getNappaimistonKuuntelija().getOdotetaanSuuntaKomentoa()) {
            liikutaHahmoja();
        }
        //päivitetään pelaajan tiedot
        kl.kirjoitaPelaajanTiedot();

        //päivitetään sankarin muuttujia, kuten humala, rakko jne
        sankari.paivitaMuuttujat();

        //päivitetään pelikentän ulkonäkö
        kl.piirraAlue();
    }

    /**
     * Mikäli kyseessä ei ole pelin sankari, ja mikäli inehmon attribuutti
     * sallii liikkumisen, sitä liikutetaan kutsutamalla liikekomennon
     * käsittelevää metodia.
     *
     * @see
     * Pubventure.Logiikka#kasitteleLiikekomento(Pubventure.enumit.KomentoEnum,
     * Pubventure.ihmiset.Inehmo)
     */
    public void liikutaHahmoja() {
        if (asiakkaatLiikkuvat) {
            for (Inehmo inehmo : inehmot) {
                int satunnaisluku = this.arpoja.nextInt(10);
                if (inehmo.getSankaruus() == false 
                        && inehmo.getLiikkuvuus() == true
                        && satunnaisluku > 7) {
                    kasitteleLiikekomento(arvoLiikesuunta(), inehmo);
                }
            }
        }
    }

    /**
     * Käsittelee liikekomennon. Mikäli halutussa liikkumissuunnassa ei ole
     * estettä, tarkistetaan josko siinä on joku inehmo. Mikäli kumpikaan
     * ehdoista ei täyty, muutetaan ko. inehmon sijaintia. Poikkeuksen tekee
     * komento-enum ODOTUS, joka ei muuta sijaintia mitenkään.
     *
     * @param komento on halutun liikkumissuunnan komentoenum
     */
    public void kasitteleLiikekomento(KomentoEnum komento, Inehmo inehmo) {
        if (komento != KomentoEnum.ODOTUS) {
            Sijainti sijaintiAnnetussaSuunnassa = annaSijaintiHalutussaSuunnassa(komento, inehmo);
            boolean suunnassaOnJoku = onkoSiinaJoku(sijaintiAnnetussaSuunnassa);
        }

        switch (komento) {
            case POHJOINEN:
                liikutaJaVaihdaPaikkojaJosKyseessaSankari(komento, inehmo);
                break;
            case LANSI:
                liikutaJaVaihdaPaikkojaJosKyseessaSankari(komento, inehmo);
                break;
            case ETELA:
                liikutaJaVaihdaPaikkojaJosKyseessaSankari(komento, inehmo);
                break;
            case ITA:
                liikutaJaVaihdaPaikkojaJosKyseessaSankari(komento, inehmo);
                break;
            case ODOTUS:
                break;
        }
    }

    /**
     * Metodi hoitaa liikkumisen varsinaisen logiikan niin sankarille kuin
     * kaikille ylipäätään liikkumaan pystyville. Ensin tutkitaan
     * törmättäisiinkö esteeseen. Mikäli ei, tutkitaan onko liikkumissuunnassa
     * joku. Jos on, ei tehdä mitään, paitsi jos kyseessä on sankari, jolloin
     * vaihdetaan sen ja tiellä olevan inehmon paikkoja.
     *
     * @param komento on annettu liikkumissuunta
     * @param inehmo on liikkumista yrittävä inehmo
     *
     * @see
     * Pubventure.Logiikka#kasitteleLiikekomento(Pubventure.enumit.KomentoEnum,
     * Pubventure.ihmiset.Inehmo)
     */
    private void liikutaJaVaihdaPaikkojaJosKyseessaSankari(KomentoEnum komento, Inehmo inehmo) {
        Sijainti sijaintiAnnetussaSuunnassa = annaSijaintiHalutussaSuunnassa(komento, inehmo);
        boolean suunnassaOnJoku = onkoSiinaJoku(sijaintiAnnetussaSuunnassa);

        if (!tormaakoEsteeseen(sijaintiAnnetussaSuunnassa)) {
            if (!suunnassaOnJoku) {
                inehmo.setSijainti(sijaintiAnnetussaSuunnassa);
            } else if (suunnassaOnJoku && inehmo.getSankaruus()) {
                Inehmo toinen = etsiInehmoAnnetussaSijainnissa(sijaintiAnnetussaSuunnassa);
                if (toinen.getLiikkuvuus()) {
                    Sijainti apuSijainti = new Sijainti(sankari.getSijainti().getX(), sankari.getSijainti().getY());
                    sankari.setSijainti(toinen.getSijainti());
                    toinen.setSijainti(apuSijainti);
                    setViestiKentanSisalto(KomentoEnum.VIESTI, "Ohitat vastaantulijan");
                }
            }
        }
    }

    /**
     * Etsitään kuka on annetussa sijainnissa
     *
     * @param sijainti on tutkittava sijainti
     * @return palauttaa true, mikäli inehmo löytyi
     */
    public Inehmo etsiInehmoAnnetussaSijainnissa(Sijainti sijainti) {
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSijainti().equals(sijainti)) {
                return inehmo;
            }
        }
        return null;
    }

    /**
     * Luodaan halutun suunnan koordinaatit omaava Sijainti-olio
     *
     * @param suunta on suunta mitä tutkitaan
     * @param inehmo on tutkinnan laukaiseva Inehmo-olio (ml. Sankari)
     * @return palauttaa uuden Sijainti-olion
     */
    public Sijainti annaSijaintiHalutussaSuunnassa(KomentoEnum suunta, Inehmo inehmo) {
        Sijainti sijaintiAnnetussaSuunnassa = null;
        switch (suunta) {
            case POHJOINEN:
                sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() - 1);
                break;
            case ITA:
                sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX() + 1, inehmo.getSijainti().getY());
                break;
            case ETELA:
                sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() + 1);
                break;
            case LANSI:
                sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX() - 1, inehmo.getSijainti().getY());
        }
        return sijaintiAnnetussaSuunnassa;
    }

    /**
     * Käsitellään kaksivaiheiset komennot
     *
     * @param komento on NappaimistonKuuntelijan Kayttoliittyma-luokan kautta
     * välittämä komento
     * @see
     * Pubventure.gui.NappaimistonKuuntelija#keyPressed(java.awt.event.KeyEvent)
     *
     * @param sijainti on Kayttoliittyma-luokan etsimä Sijainti
     * @see
     * Pubventure.gui.Kayttoliittyma#valitaKaksivaiheinenKomento(Pubventure.enumit.KomentoEnum,
     * Pubventure.enumit.KomentoEnum)
     */
    public void kasitteleKaksivaiheinenKomento(KomentoEnum komento, Sijainti sijainti) {
        switch (komento) {
            case OSTA:
                if (pubi.getObjekti(sijainti).getTyyppi() == PubiobjektiEnum.TISKI) {
                    if (sankari.setRahat(-5)) {
                        sankari.setJuomat(5);
                        kl.kirjoitaPelaajanTiedot();
                        setViestiKentanSisalto(KomentoEnum.VIESTI, "Ostit oluen!");
                    } else {
                        setViestiKentanSisalto(KomentoEnum.VIESTI, "Sinulla ei ole varaa!");
                    }

                } else {
                    setViestiKentanSisalto(KomentoEnum.VIESTI, "Et ole baaritiskillä.");
                }
                break;

            case LYO:
                setViestiKentanSisalto(KomentoEnum.VIESTI, "Nyt ei tunnu sopivalta hetkeltä alkaa tapella");
                break;
            case KUSE:
                if (pubi.getObjekti(sijainti).getTyyppi() == PubiobjektiEnum.PISUAARI
                        || pubi.getObjekti(sijainti).getTyyppi() == PubiobjektiEnum.WCPYTTY) {
                    if (sankari.getRakko() > 20) {
                        sankari.setRakko(0);
                        sankari.setRakkoTaynna(false);
                        kl.kirjoitaPelaajanTiedot();
                        setViestiKentanSisalto(KomentoEnum.VIESTI, "Aaahh...");
                    } else {
                        setViestiKentanSisalto(KomentoEnum.VIESTI, "Nyt ei vielä oikein irtoa.");
                    }
                }
                break;
            case PUMMI:
                Inehmo kohde = etsiInehmoAnnetussaSijainnissa(sijainti);
                if (kohde == null) {
                    setViestiKentanSisalto(KomentoEnum.VIESTI, "Ei siinä ole ketään.");
                } else if (kohde.getTyyppi().equals(InehmoEnum.PORTSARI)) {
                    setViestiKentanSisalto(KomentoEnum.VIESTI, "Taitaa olla huono idea.");
                } else {
                    if (kohde.getAsenne() > 90) {
                        if (sankari.setRahat(5)) {
                            setViestiKentanSisalto(KomentoEnum.VIESTI, "Sait pummittua vitosen!");
                            kl.kirjoitaPelaajanTiedot();
                        }
                    } else {
                        setViestiKentanSisalto(KomentoEnum.VIESTI, "Eipä onnistunut.<br>Kenties sinun pitäisi yrittää vaikuttaa häneen jotenkin?");
                    }
                }
                break;
            case PUHU:

                break;
            case TUTKI:
                String selite = "Siinä on ";
                if (onkoSiinaJoku(sijainti)) {
                    for (Inehmo inehmo : inehmot) {
                        if (inehmo.getSijainti().equals(sijainti)) {
                            selite += inehmo.getSelite();
                            break;
                        }
                    }
                    setViestiKentanSisalto(KomentoEnum.VIESTI, selite);
                } else {
                    setViestiKentanSisalto(KomentoEnum.VIESTI, selite + pubi.getObjekti(sijainti).getSelite());
                }
                break;
            case PERU:
                setViestiKentanSisalto(KomentoEnum.LIIKE, "");
                break;
            case VONKAA:
                break;
            case ANNA:
                break;
        }
    }

    /**
     * Testaa josko annetussa sijainnissa on joku hahmo
     *
     * @param sijainti
     * @return palauttaa true, mikäli sijainnissa on joku
     */
    public boolean onkoSiinaJoku(Sijainti sijainti) {
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSijainti().equals(sijainti)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Testaa josko annetussta sijainnista löytyvän pubiobjektin esteattribuutti
     * on true.
     *
     * @param sijainti on sijainti jonka esteellisyyttä tutkitaan
     * @return true tai false sen mukaan oliko koordinaateissa este vai ei
     */
    public boolean tormaakoEsteeseen(Sijainti sijainti) {
        if (pubi.getObjekti(sijainti).getEste()) {
            return true;
        }
        return false;
    }

    /**
     * Poimitaan satunnainen liikesuunta
     *
     * @return palauttaa arvotun suunnan
     */
    public KomentoEnum arvoLiikesuunta() {
        int satunnainen = arpoja.nextInt(4);
        return komennot[satunnainen];
    }

    public Sijainti getSankarinSijainti() {
        return pubi.getInehmot().get(0).getSijainti();
    }

    public Pubi getPubi() {
        return this.pubi;
    }

    public int getSiirtoja() {
        return this.siirtoja;
    }

    public boolean getAsiakkaatLiikkuvat() {
        return this.asiakkaatLiikkuvat;
    }

//    public void setAsiakkaita(int asiakkaita) {
//        this.asiakkaita = asiakkaita;
//    }
//    
    public void setSiirtoja(int siirtoja) {
        this.siirtoja = siirtoja;
    }

    public void setAsiakkaatLiikkuvat(boolean juuTaiEi) {
        this.asiakkaatLiikkuvat = juuTaiEi;
    }

    public Inehmo getInehmo(int luku) {
        return inehmot.get(luku);
    }

    public ArrayList<Inehmo> getInehmot() {
        return this.inehmot;
    }
}
