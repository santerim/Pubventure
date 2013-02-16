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
            
            
            // tähän korjaus!!! loogisesti väärässä paikassa
            kasitteleMuuKomento(komento);
        }
        
        //liikutetaan muita kuin sankaria, mikäli ei odoteta jatkokomentoa
        if (!kl.getNappaimistonKuuntelija().getOdotetaanKomentoa()) {
            for (Inehmo inehmo : inehmot) {
            if (inehmo.getSankaruus() == false) {
                kasitteleLiikekomento(arvoLiikesuunta(), inehmo);
            }
        }
        }
        
        kl.piirraAlue();
    }
    
    /**
     * Käsittelee liikekomennon. Mikäli halutussa liikkumissuunnassa ei ole
     * estettä, tarkistetaan josko siinä on joku inehmo. Mikäli kumpikaan
     * ehdoista ei täyty, muutetaan ko. inehmon sijaintia.
     * Poikkeuksen tekee komento-enum ODOTUS, joka ei muuta sijaintia mitenkään.
     * 
     * @param komento on halutun liikkumissuunnan komentoenum
     */
    public void kasitteleLiikekomento(KomentoEnum komento, Inehmo inehmo) {
        Sijainti sijaintiAnnetussaSuunnassa = null;
            switch (komento) {
                case POHJOINEN:
                    sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() - 1);
                    if (!tormaakoEsteeseen(sijaintiAnnetussaSuunnassa)) {
                        if (!onkoSiinaJoku(sijaintiAnnetussaSuunnassa)) {
                            inehmo.setSijainti(sijaintiAnnetussaSuunnassa);
                        }
                    }
                    break;
                case LANSI:
                    sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX() - 1, inehmo.getSijainti().getY());
                    if (!tormaakoEsteeseen(sijaintiAnnetussaSuunnassa)) {
                        if (!onkoSiinaJoku(sijaintiAnnetussaSuunnassa)) {
                            inehmo.setSijainti(sijaintiAnnetussaSuunnassa);
                        }
                    }
                    break;
                case ETELA:
                    sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() + 1);
                    if (!tormaakoEsteeseen(sijaintiAnnetussaSuunnassa)) {
                        if (!onkoSiinaJoku(sijaintiAnnetussaSuunnassa)) {
                            inehmo.setSijainti(sijaintiAnnetussaSuunnassa);
                        }
                    }
                    break;
                case ITA:
                    sijaintiAnnetussaSuunnassa = new Sijainti(inehmo.getSijainti().getX() + 1, inehmo.getSijainti().getY());
                    if (!tormaakoEsteeseen(sijaintiAnnetussaSuunnassa)) {
                        if (!onkoSiinaJoku(sijaintiAnnetussaSuunnassa)) {
                            inehmo.setSijainti(sijaintiAnnetussaSuunnassa);
                        }
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
        kl.getNappaimistonKuuntelija().setOdotetaanKomentoa(false);
    }
    
    public void kirjoitaPelaajanTiedot() {
        kl.getPiirtaja().tietoLabel.setText("<html><table cellpadding='10'>"
                + "Itsetunto: " + sankari.getAsenne() + "<br>"
                + "Humala: " + sankari.getHumala() + "<br>"
                + "Rakko: " + sankari.getRakko() + "<br>"
                + "Rahaa: " + sankari.getRahat()
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
     * Testaa josko annetussta sijainnista löytyvän pubiobjektin
     * esteattribuutti on true.
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
