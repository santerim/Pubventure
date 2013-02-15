package Pubventure;

import Pubventure.enumit.KomentoEnum;
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
//    private int asiakkaita;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
//    private Scanner lukija = new Scanner(System.in);
    private ArrayList<Inehmo> inehmot;
    private Random arpoja = new Random();
    private Kayttoliittyma kl;
    private KomentoEnum[] komennot;
    private Sankari sankari;

    public Logiikka(int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.komennot = KomentoEnum.values();
//        this.asiakkaita = asiakkaita;
        this.pubi = new Pubi(asiakkaita);
        pubi.luoKentta();
        pubi.luoHahmot();
        this.inehmot = pubi.getInehmot();
        this.sankari = (Sankari) inehmot.get(0);
//        System.out.println("Pubin leveys: " + pubi.getLeveys());
//        System.out.println("Pubin korkeus: " + pubi.getKorkeus());
        this.siirtoja = siirtoja;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;
    }

    /**
     * Aloittaa pelin suorittamisen luomalla käyttöliittymän
     */
    public void aloita() {
        this.kl = new Kayttoliittyma(pubi, inehmot, this);
        SwingUtilities.invokeLater(kl);
    }

    /**
     * Kutsuu komennon tyypin edellyttämää metodia, sekä piirtää kentän uudestaan.
     * @param komento 
     */
    public void kasitteleKomento(KomentoEnum komento) {
        if (komento == KomentoEnum.POHJOINEN || komento == KomentoEnum.ITA
                || komento == KomentoEnum.ETELA || komento == KomentoEnum.LANSI
                || komento == KomentoEnum.ODOTUS) {
            kasitteleLiikekomento(komento, inehmot.get(0));
            kl.setViestiKentanSisalto("  ");
        }
        
        // jos odotetaan, lisätään viestikenttään teksti "odotat"
        if (komento == KomentoEnum.ODOTUS) {
            kl.setViestiKentanSisalto("Odotat hetken.");
        }
        
        //tähän kaksivaiheisten komentojen IF
        if (komento == KomentoEnum.TEEJOTAIN) {
            kl.setViestiKentanSisalto("<html><table cellpadding='10'>"
                    + "Paina (o)sta, (a)nna, (l)yö, (k)use, (p)uhu"
                    + "<br>(j)uo, (t)utki, tai &lt;Esc&gt; peruaksesi</table></html>");
            kasitteleMuuKomento(komento);
        }
        
        //liikutetaan muita kuin sankaria
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSankaruus() == false) {
                kasitteleLiikekomento(arvoLiikesuunta(), inehmo);
            }
        }
        kl.piirraAlue();
    }
    
    /**
     * Käsittelee liikekomennon. Mikäli halutussa liikkumissuunnassa ei ole
     * estettä, muutetaan koordinaatteja vastaavasti. Mikäli suunnassa on este,
     * sijaintia ei muuteta, mutta yksi vuoro kuluu.
     * @param komento on haluttu liikkumissuunta
     */
    public void kasitteleLiikekomento(KomentoEnum komento, Inehmo inehmo) {
            switch (komento) {
                case POHJOINEN:
                    if (!tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() - 1)) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() - 1);
                    }
                    break;
                case LANSI:
                    if (!tormaako(inehmo.getSijainti().getX() - 1, inehmo.getSijainti().getY())) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() - 1);
                    }
                    break;
                case ETELA:
                    if (!tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() + 1)) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() + 1);
                    }
                    break;
                case ITA:
                    if (!tormaako(inehmo.getSijainti().getX() + 1, inehmo.getSijainti().getY())) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() + 1);
                    }
                    break;
                case ODOTUS:
                    break;
            }
    }

    /**
     * Käsitellään ei-liikekomennot
     * @param komento on Nappaimistonkuuntelijalta Piirtoalustan kautta välitetty
     * KomentoEnum
     */
    public void kasitteleMuuKomento(KomentoEnum komento) {
        switch (komento) {
            case OSTA:
                kl.setViestiKentanSisalto(" ");
                break;
            case ANNA:
                break;
            case LYO:
                break;
            case KUSE:
                break;
            case PUHU:
                break;
            case JUO:
                break;
            case TUTKI:
                break;
            case PERU:
                kl.setViestiKentanSisalto("");
                break;
        }
    }
    
    public void kirjoitaPelaajanTiedot() {
        kl.getPiirtaja().tietoLabel.setText("<html><table cellpadding='10'>"
                + "Itsetunto: " + sankari.getAsenne() + "<br>"
                + "Humala: " + sankari.getHumala() + "<br>"
                + "Rakko: " + sankari.getRakko() + "<br>"
                +"</table></html>");
    }
    
    /**
     * Testaa josko annetussa sijainnissa on joku hahmo
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
     * Testaa josko annetuissa koordinaateissa on este ja palauttaa true mikäli
     * näin on
     * @param x x-akselin koordinaatti
     * @param y y-akselin koordinaatti
     * @return true tai false sen mukaan oliko koordinaateissa este vai ei
     */
    public boolean tormaako(int x, int y) {
        Sijainti sijainti = new Sijainti(x, y);
        if (pubi.getObjekti(sijainti).getEste()) {
            return true;
        }
        if (onkoSiinaJoku(sijainti)) {
            return true;
        }
        return false;
    }

    /**
     * Poimitaan satunnainen liikesuunta
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
