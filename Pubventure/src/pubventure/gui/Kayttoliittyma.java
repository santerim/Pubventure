
package pubventure.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;
import pubventure.Logiikka;
import pubventure.Sijainti;
import pubventure.enumit.InehmoEnum;
import pubventure.enumit.KomentoEnum;
import pubventure.ihmiset.Asiakas;
import pubventure.ihmiset.Inehmo;
import pubventure.ihmiset.InehmojenHallinnointi;
import pubventure.ihmiset.Sankari;
import pubventure.ymparisto.Pubi;

/**
 *
 * @author Santeri
 *
 * Kayttoliittyma-luokka hoitaa swing-ikkunan luomisen ja hallinnoi sen
 * toimintaa.
 */
public class Kayttoliittyma implements Runnable {

    /**
     * Piirtaja-luokka on swingbuilderilla tehty ikkunointi
     *
     * @see Pubventure.gui.Piirtaja
     */
    private Piirtaja piirtaja;
    /**
     * Pubi-luokkaa tarvitaan pelikentän piirtämiseen
     *
     * @see Pubventure.ymparisto.Pubi
     */
    private Pubi pubi;
    /**
     * Pelikenttää piirrettäessä kulloinkin piirtovuorossa olevaan kohtaan
     * piirretään inehmon ulkonäkö pubiobjektin sijaan, mikäli kyseisessä
     * kohdassa sattuu olemaan joku
     */
    private ArrayList<Inehmo> inehmot;
    /**
     * Pelikenttä täytetään html-merkkijonolla, joka koostetaan ennen tulostusta
     * stringbuilderiin
     */
    private StringBuilder sb;
    /**
     * Logiikka-luokkaan pitää päästä käsiksi, jotta näppäimistönkuuntelijalta
     * voidaan välittää komennot eteenpäin
     *
     * @see Pubventure.gui.NappaimistonKuuntelija
     * @see Pubventure.Logiikka#kasitteleKomento(Pubventure.enumit.KomentoEnum)
     * @see
     * Pubventure.Logiikka#kasitteleKaksivaiheinenKomento(Pubventure.enumit.KomentoEnum,
     * Pubventure.Sijainti)
     */
    private Logiikka logiikka;
    /**
     * Pelikentän piirtämisessä käytetään ns. fixed width-fonttia, jotta kentän
     * layout saadaan tasaiseksi.
     */
    private Font fontti;
    /**
     * Näppäimistönkuuntelija sieppaa näppäinkomennot ja välittää ne tälle
     * luokalle. Se asetetaan konstruktorissa Piirtaja-luokan kuuntelijaksi.
     */
    private NappaimistonKuuntelija kuuntelija;
    /**
     * Sankaria tarvitaan tässäkin luokassa, jotta voimme kätevästi tulostaa
     * tietoja ruudulle.
     *
     * @see Pubventure.gui.Kayttoliittyma#kirjoitaPelaajanTiedot()
     */
    private Sankari sankari;
    
    private InehmojenHallinnointi ih;

    public Kayttoliittyma(Pubi pubi, ArrayList<Inehmo> inehmot, Sankari sankari, InehmojenHallinnointi ih, Logiikka log) {
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.logiikka = log;
        this.sankari = sankari;
        this.ih = ih;

        this.sb = new StringBuilder();

        this.piirtaja = new Piirtaja();
        this.kuuntelija = new NappaimistonKuuntelija(this);
        this.piirtaja.addKeyListener(kuuntelija);

        this.fontti = new Font("monospaced", Font.BOLD, 20);
        this.piirtaja.kenttaLabel.setFont(fontti);
        this.piirtaja.kenttaLabel.setForeground(Color.WHITE);
        this.piirtaja.tietoLabel.setFont(fontti);
        this.piirtaja.tietoLabel.setForeground(Color.WHITE);
        this.piirtaja.viestiLabel.setFont(fontti);
        this.piirtaja.viestiLabel.setForeground(Color.WHITE);
    }

    @Override
    public void run() {
        piirtaja.pack();
        piirtaja.setVisible(true);
        piirraAlue();
        kirjoitaPelaajanTiedot();
    }

    /**
     * Metodi hoitaa pelikentän piirtämisen käyttäen hyväkseen Pubi-luokan
     * tarjoamia metodeja. Kenttä tulostetaan html-koodina.
     *
     * @see Pubventure.ymparisto.Pubi
     */
    public void piirraAlue() {
        sb.setLength(0);
        sb.append("<html><b>");

        for (int i = 0; i < pubi.getKorkeus(); i++) {
            sb.append("&nbsp;");
            for (int j = 0; j < pubi.getLeveys(); j++) {
                Sijainti nykSijainti = new Sijainti(j, i);

                    if (pubi.tormaako(nykSijainti)) {
                        int inehmoLoydetty = 0;
                        for (Inehmo inehmo : inehmot) {
                            if (inehmo.getSijainti().equals(nykSijainti) && inehmo.getNakyvyys()) {
                                sb.append(inehmo.getUlkomuoto());
                                inehmoLoydetty = 1;
                                break;
                            } else {
                            }
                        }
                        if (inehmoLoydetty == 0) {
                            sb.append(pubi.getObjekti(nykSijainti).getUlkonako());
                        }
                    } else {
                        sb.append(pubi.getObjekti(nykSijainti).getUlkonako());
                    }


                if (j == pubi.getLeveys() - 1) {
                    sb.append("<br>");
                }
            }
        }
        sb.append("</html>");
        setPeliKentanSisalto(sb.toString());
    }

    /**
     * Näyttää ohjeet kun enter-näppäintä on painettu
     *
     */
    public void naytaOhjeet() {
        setViestiKentanSisalto("<html><table cellpadding='10'>"
                + "Paina (o)sta, " /*(a)nna, "(l)yö,*/ + "(k)use, (p)uhu "
                + "(j)uo, (t)utki, p(u)mmi, (v)onkaa<br>"
                + "tai käytä nuolinäppäimiä &#47; numpadia liikkuaksesi.</table></html>");
        setTietoKentanSisalto("<html><table cellpadding='10'>"
                + "@ = pelaaja<br>"
                + "<font color='#F08080'>n</font> = nainen<br>"
                + "<font color='#1E90FF'>m</font> = mies<br>"
                + "t = tarjoilija<br>"
                + "<font color='#2E8B57'>E</font> = uloskäynti<br>"
                + "<font color='#708090'>o</font> = ovi<br>"
                + "B = baaritiski<br>"
                + "w = vessa<br>"
                + "L = lavuaari<br>"
                + "# = pöytä<br>"
                + "<font color='#8B4513'>¤</font> = tuoli</table></html>");
    }

    /**
     * Kirjoittaa tietokenttään pelaajan tiedot
     *
     * @see
     * Pubventure.gui.Kayttoliittyma#setTietoKentanSisalto(java.lang.String)
     */
    public void kirjoitaPelaajanTiedot() {
        setTietoKentanSisalto("<html><table cellpadding='10'><br>"
                + "Rohkeus: " + (int) sankari.getAsenne() + "<br>"
                + "Humala: " + (int) sankari.getHumala() + "<br>"
                + "Juomat: " + (int) sankari.getJuomat() + " dl<br>"
                + "Rakko: " + (int) sankari.getRakko() + "<br>"
                + "Rahaa: " + (int) sankari.getRahat() + "&euro;"
                + "</table></html>");
    }

    /**
     * Hoitaa loppurutiinit
     */
    public void loppu() {
        setPeliKentanSisalto("");

        sb.setLength(0);
        sb.append("<html><table cellpadding='10'>Ja niin ilta päättyi.<br>");

        int pisteet = 0;
        pisteet += sankari.getRahat();

        boolean rahaaJaiEnemman;
        if (sankari.getRahat() > 10) {
            sb.append("Sinulle jäi enemmän rahaa kuin oli tullessa,<br>");
            rahaaJaiEnemman = true;
        } else {
            sb.append("Olet kokolailla rahaton, ");
            rahaaJaiEnemman = false;
        }

        if (sankari.getHumala() < 25) {
            if (rahaaJaiEnemman) {
                sb.append("mutta olet jokseenkin selvinpäin.<br>");
            } else {
                sb.append("ja selvinpäin.<br>Kuinka ikävää.<br>");
            }
        } else if (sankari.getHumala() >= 25 && sankari.getHumala() < 40) {
            if (rahaaJaiEnemman) {
                sb.append("ja olet vieläpä mukavassa laitamyötäisessä.<br>");
                pisteet += 33;
            } else {
                sb.append("mutta mukavassa laitamyötäisessä.<br>");
                pisteet += 33;
            }
        } else if (sankari.getHumala() >= 40
                && sankari.getHumala() < 85) {
            if (rahaaJaiEnemman) {
                sb.append("ja olet mukavassa humalassa.<br>");
                pisteet += 50;
            } else {
                sb.append("mutta silti mukavassa humalassa.<br>");
                pisteet += 50;
            }
        } else {
            if (rahaaJaiEnemman) {
                sb.append("ja olet aivan hirveässä kännissä.<br>");
                pisteet += 15;
            } else {
                sb.append("mutta silti aivan hirveässä kännissä.<br>");
                pisteet += 15;
            }
        }

        Asiakas vosu = (Asiakas) sankari.getVosu();
        int naisenPisteet = 0;
        if (vosu != null) {
            sb.append("Löysit myös itsellesi seuraa, nimittäin<br>");
            if (vosu.getIka().equals(InehmoEnum.NUORI)) {
                naisenPisteet += 100;
                sb.append("nuoren naisen.<br>");
            } else if (vosu.getIka().equals(InehmoEnum.KESKIIKAINEN)) {
                naisenPisteet += 66;
                sb.append("keski-ikäisen naisen.<br>");
            } else if (vosu.getIka().equals(InehmoEnum.NUORI)) {
                naisenPisteet += 33;
                sb.append("vanhan naisen.<br>Makuasioista ei voine kiistellä.");
            }
        }
        if (!rahaaJaiEnemman && naisenPisteet > 0) {
            sb.append("Sinulla ei kuitenkaan ollut varaa taksiin,<br>"
                    + "joten naisesi häipyi yöhön. Harmi.");

        } else if (rahaaJaiEnemman && naisenPisteet > 0) {
            sb.append("Otit taksin asunnollesi ja maistoit piparia.<br>");
            pisteet += naisenPisteet;
        }
        sb.append("Pisteet: ").append(pisteet).append("</table></html>");

        setPeliKentanSisalto(sb.toString());
        setViestiKentanSisalto("Paina &lt;esc&gt; lopettaaksesi.");
        kuuntelija.setLoppu(true);
    }

    /**
     *
     * Välittää näppäinkomentojen syötteen eteenpäin Logiikka-luokalle
     *
     * @see Pubventure.Logiikka#kasitteleKomento(Pubventure.enumit.KomentoEnum)
     */
    public void valitaKomento(KomentoEnum komento) {
        logiikka.kasitteleKomento(komento);
    }

    /**
     * Välittää kaksivaiheisten komentojen parametrit Logiikka-luokan metodille.
     * Kyseinen metodi vaatii parametreikseen KomentoEnumin, sekä Sijainnin.
     *
     * @see
     * Pubventure.Logiikka#annaSijaintiHalutussaSuunnassa(Pubventure.enumit.KomentoEnum,
     * Pubventure.ihmiset.Inehmo)
     * @see
     * Pubventure.Logiikka#kasitteleKaksivaiheinenKomento(Pubventure.enumit.KomentoEnum,
     * Pubventure.Sijainti)
     *
     * @param komento on NappaimistonKuuntelijalta saatu KomentoEnum
     *
     * @param suunta on NappaimistonKuuntelijalta saatu KomentoEnum, jonka
     * perusteella saadaan Logiikka-luokata annetussa suunnassa oleva
     * Sijainti-olio
     */
    public void valitaKaksivaiheinenKomento(KomentoEnum komento, KomentoEnum suunta) {
        logiikka.kasitteleKaksivaiheinenKomento(komento, logiikka.annaSijaintiHalutussaSuunnassa(suunta, sankari));
    }

    public Piirtaja getPiirtaja() {
        return this.piirtaja;
    }

    public JLabel getViestiKentta() {
        return piirtaja.viestiLabel;
    }

    public JLabel getTietoKentta() {
        return piirtaja.tietoLabel;
    }

    public JLabel getPeliKentta() {
        return piirtaja.kenttaLabel;
    }

    public NappaimistonKuuntelija getNappaimistonKuuntelija() {
        return this.kuuntelija;
    }

    public void setViestiKentanSisalto(String sisalto) {
        piirtaja.viestiLabel.setText("<html><table cellpadding='10'>"
                + sisalto
                + "</table></html>");
    }

    public void setViestiKentanSisalto(KomentoEnum komento, String syote) {
        switch (komento) {
            case ODOTUS:
                setViestiKentanSisalto("Odotat hetken");
                break;
            case OHJE:
                naytaOhjeet();
                break;
            case LIIKE:
                setViestiKentanSisalto("Liiku, anna komento, tai paina &lt;Enter&gt; näyttääksesi ohjeet");
                break;
            case SUUNTA:
                setViestiKentanSisalto("Anna suunta tai &lt;esc&gt; &#47; &lt;space&gt; peruaksesi");
                break;
            case PERU:
                setViestiKentanSisalto(KomentoEnum.LIIKE, "");
                break;
//            case VIESTI:
//                setViestiKentanSisalto(syote);
        }
    }

    public void setTietoKentanSisalto(String sisalto) {
        piirtaja.tietoLabel.setText(sisalto);
    }

    public void setPeliKentanSisalto(String sisalto) {
        piirtaja.kenttaLabel.setText(sisalto);
    }
    
    public void setInehmotNakymattomiksi() {
        ih.setInehmojenNakyvyys(false);
    }
    
    public void setInehmotNakyviksi() {
        ih.setInehmojenNakyvyys(true);
    }
}
