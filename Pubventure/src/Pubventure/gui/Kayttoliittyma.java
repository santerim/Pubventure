package Pubventure.gui;

import Pubventure.Logiikka;
import Pubventure.Sijainti;
import Pubventure.enumit.KomentoEnum;
import Pubventure.ihmiset.Inehmo;
import Pubventure.ihmiset.Sankari;
import Pubventure.ymparisto.Pubi;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * Kayttoliittyma-luokka hoitaa swing-ikkunan luomisen ja hallinnoi sen
 * toimintaa.
 */
public class Kayttoliittyma implements Runnable {

    private Piirtaja piirtaja;
    private int leveys;
    private int korkeus;
    private Pubi pubi;
    private ArrayList<Inehmo> inehmot;
    private StringBuilder sb;
    private Logiikka logiikka;
    private Font fontti;
    private NappaimistonKuuntelija kuuntelija;
    private Sankari sankari;

    public Kayttoliittyma(Pubi pubi, ArrayList<Inehmo> inehmot, Sankari sankari, Logiikka log) {
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.logiikka = log;
        this.sankari = sankari;

        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();

        this.sb = new StringBuilder();

        this.piirtaja = new Piirtaja();
        this.kuuntelija = new NappaimistonKuuntelija(this);
        this.piirtaja.addKeyListener(kuuntelija);

        this.fontti = new Font("Courier new", Font.PLAIN, 14);
        this.piirtaja.kenttaLabel.setFont(fontti);
        this.piirtaja.kenttaLabel.setForeground(Color.WHITE);
        this.piirtaja.tietoLabel.setFont(fontti);
        this.piirtaja.tietoLabel.setForeground(Color.WHITE);
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
                        if (inehmo.getSijainti().equals(nykSijainti)) {
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
                + "Paina (o)sta, " /*(a)nna,*/ + "(l)yö, (k)use, (p)uhu"
                + "<br>(j)uo, (t)utki, p(u)mmi</table></html>");
        setTietoKentanSisalto("<html><table cellpadding='10'>"
                + "@ = pelaaja<br>"
                + "n = nainen<br>"
                + "m = mies<br>"
                + "t = tarjoilija<br>"
                + "E = uloskäynti<br>"
                + "o = ovi<br>"
                + "B = baaritiski<br>"
                + "w = vessa<br>"
                + "L = lavuaari<br>"
                + "# = pöytä<br>"
                + "¤ = tuoli</table></html>");
    }

    /**
     * Kirjoittaa tietokenttään pelaajan tiedot
     *
     * @see
     * Pubventure.gui.Kayttoliittyma#setTietoKentanSisalto(java.lang.String)
     */
    public void kirjoitaPelaajanTiedot() {
        setTietoKentanSisalto("<html><table cellpadding='10'><br>"
                + "Itsetunto: " + (int) sankari.getAsenne() + "<br>"
                + "Humala: " + (int) sankari.getHumala() + "<br>"
                + "Juomat: " + (int) sankari.getJuomat() + "<br>"
                + "Rakko: " + (int) sankari.getRakko() + "<br>"
                + "Rahaa: " + (int) sankari.getRahat() + "&euro;"
                + "</table></html>");
    }

    /**
     *
     * Välittää näppäinkomentojen syötteen eteenpäin Logiikka-luokalle
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
        switch (suunta) {
            case POHJOINEN:
                logiikka.kasitteleKaksivaiheinenKomento(komento, logiikka.annaSijaintiHalutussaSuunnassa(suunta, sankari));
                break;
            case ITA:
                logiikka.kasitteleKaksivaiheinenKomento(komento, logiikka.annaSijaintiHalutussaSuunnassa(suunta, sankari));
                break;
            case ETELA:
                logiikka.kasitteleKaksivaiheinenKomento(komento, logiikka.annaSijaintiHalutussaSuunnassa(suunta, sankari));
                break;
            case LANSI:
                logiikka.kasitteleKaksivaiheinenKomento(komento, logiikka.annaSijaintiHalutussaSuunnassa(suunta, sankari));
                break;
        }
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

    public void setTietoKentanSisalto(String sisalto) {
        piirtaja.tietoLabel.setText(sisalto);
    }

    public void setPeliKentanSisalto(String sisalto) {
        piirtaja.kenttaLabel.setText(sisalto);
    }
}
