package Pubventure;

import Pubventure.enumit.KomentoEnum;
import Pubventure.ihmiset.Inehmo;
import Pubventure.ymparisto.Pubi;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Pubventure.gui.Kayttoliittyma;
import Pubventure.gui.Piirtoalusta;
import javax.swing.SwingUtilities;

/**
 *
 * Luokka toimii pääasiallisena ohjelman toiminnan ohjaajana. Se luo Pubi-luokan
 * ja suorittaa sen ne metodit, jotka luovat pelikentän, sekä luo swing-ikkunaa
 * hallitsevan Kayttoliittyma-luokan.
 */
public class Logiikka {

    private Pubi pubi;
    private int asiakkaita;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
//    private Scanner lukija = new Scanner(System.in);
    private ArrayList<Inehmo> inehmot;
    private Random arpoja = new Random();
    private Kayttoliittyma kl;
    private KomentoEnum[] komennot;

    public Logiikka(int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.komennot = KomentoEnum.values();
        this.asiakkaita = asiakkaita;
        this.pubi = new Pubi(asiakkaita);
        pubi.luoKentta();
        pubi.luoHahmot();
        this.inehmot = pubi.getInehmot();
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

// alla debuggaus-koodia
//
//        while (true) {
//            System.out.println("Siirtoja: " + siirtoja);
//            System.out.println("Asiakkaita: " + asiakkaita);
//            System.out.println("");
//            //tulostaOlennot();
//            System.out.println("");
//            tulostaPubi();
//            System.out.println("");
//
//            System.out.print("Anna komento> ");
//            String komento = lukija.nextLine();
//            kasitteleKomento(komento);
//
//            if (asiakkaatLiikkuvat) {
//                liikutaAsiakkaita();
//            }

        // tehtävä uusiksi - ja siirrettävä
//            if (siirtoja == 0 && asiakkaita > 0) {
//                System.out.println("\nHÄVISIT");
//                break;
//            } else if (asiakkaita == 0) {
//                System.out.println("\nVOITIT");
//                break;
//            }
//        }

    }

    /**
     * Kutsuu komennon tyypin edellyttämää metodia, sekä piirtää kentän uudestaan.
     * @param komento 
     */
    public void kasitteleKomento(KomentoEnum komento) {
        if (komento == KomentoEnum.POHJOINEN || komento == KomentoEnum.ITA
                || komento == KomentoEnum.ETELA || komento == KomentoEnum.LANSI) {
            kasitteleLiikekomento(komento);
        }
    }
    
    public void liikutaHahmoa() {
        
    }
    
    /**
     * Käsittelee käyttöliittymän lähettämän liikekomennon. Mikäli halutussa
     * liikkumissuunnassa ei ole estettä, muutetaan koordinaatteja vastaavasti.
     * Mikäli suunnassa on este, sijaintia ei muuteta, mutta yksi vuoro kuluu.
     * @param komento on haluttu liikkumissuunta
     */
    public void kasitteleLiikekomento(KomentoEnum komento) {
            switch (komento) {
                case POHJOINEN:
                    if (!tormaako(getSankarinSijainti().getX(), getSankarinSijainti().getY() - 1)) {
                        if (getSankarinSijainti().getY() > 0) {
                            getSankarinSijainti().setY(getSankarinSijainti().getY() - 1);
                        }
                    }
                    break;
                case LANSI:
                    if (!tormaako(getSankarinSijainti().getX() - 1, getSankarinSijainti().getY())) {
                        if (getSankarinSijainti().getX() > 0) {
                            getSankarinSijainti().setX(getSankarinSijainti().getX() - 1);
                        }
                    }
                    break;
                case ETELA:
                    if (!tormaako(getSankarinSijainti().getX(), getSankarinSijainti().getY() + 1)) {
                        if (getSankarinSijainti().getY() < pubi.getKorkeus() - 1) {
                            getSankarinSijainti().setY(getSankarinSijainti().getY() + 1);
                        }
                    }
                    break;
                case ITA:
                    if (!tormaako(getSankarinSijainti().getX() + 1, getSankarinSijainti().getY())) {
                        if (getSankarinSijainti().getX() < pubi.getLeveys() - 1) {
                            getSankarinSijainti().setX(getSankarinSijainti().getX() + 1);
                        }
                    }
                    break;
                case ODOTUS:
                    break;
            }
        
        liikutaAsiakkaita();
        kl.getPiirtoalusta().piirraAlue();
    }

    /**
     * Käsitellään ei-liikekomennot
     * @param komento 
     */
    public void kasitteleMuuKomento(String komento) {
        
    }
    
    /**
     * Testaa josko annetuissa koordinaateissa on este ja palauttaa true mikäli
     * näin on
     * @param x x-akselin koordinaatti
     * @param y y-akselin koordinaatti
     * @return true tai false sen mukaan oliko koordinaateissa este vai ei
     */
    public boolean tormaako(int x, int y) {
        if (pubi.getObjekti(x, y).getEste()) {
            return true;
        }
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSijainti().getX() == x && inehmo.getSijainti().getY() == y) {
                return true;
            }

        }
        return false;
    }

    /**
     * debug-metodi pubin tulostamista varten
     */
    public void tulostaPubi() {
        for (int i = 0; i < pubi.getKorkeus(); i++) {
            for (int j = 0; j < pubi.getLeveys(); j++) {
                boolean kohdassaOnInehmo = false;
                for (Inehmo inehmo : inehmot) {
                    if (inehmo.getSijainti().getX() == j && inehmo.getSijainti().getY() == i) {
                        System.out.print(inehmo.getUlkomuoto());
                        kohdassaOnInehmo = true;
                        break;
                    }
                }
                if (!kohdassaOnInehmo) {
                    System.out.print(pubi.getObjekti(j, i).getUlkonako());
                }
            }
            System.out.println("");
        }
    }

    //MUUTETTAVA - lisättävä satunnaisuutta ja lopulta kohteeseen suunnistus
    /**
     * liikuttaa asiakkaita satunnaisesti, mikäli liikkumissuunnassa ei ole
     * estettä tai toista hahmoa
     */
    public void liikutaAsiakkaita() {
        for (Inehmo inehmo : inehmot) {
            if (!inehmo.getSankaruus() && inehmo.getLiikkuvuus()) {
                KomentoEnum suunta = arvoLiikesuunta();
                switch (suunta) {
                    case POHJOINEN:
                        //jos ei törmää, muutetaan inehmon sijaintia
                        if (inehmo.getSijainti().getY() > 0
                                && tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() - 1) == false) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() - 1);
                        }
                        break;
                    case LANSI:
                        if (inehmo.getSijainti().getX() > 0
                                && tormaako(inehmo.getSijainti().getX() - 1, inehmo.getSijainti().getY()) == false) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() - 1);
                        }
                        break;
                    case ETELA:
                        if (inehmo.getSijainti().getY() < pubi.getKorkeus() - 1
                                && tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() + 1) == false) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() + 1);
                        }
                        break;
                    case ITA:
                        if (inehmo.getSijainti().getX() < pubi.getLeveys() - 1
                                && tormaako(inehmo.getSijainti().getX() + 1, inehmo.getSijainti().getY()) == false) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() + 1);
                        }
                        break;
                    case ODOTUS:
                        break;
                }
            }
        }
    }

    /**
     * Otetaan satunnainen liikesuunta
     * @return palauttaa arvotun suunnan
     */
    public KomentoEnum arvoLiikesuunta() {
        int satunnainen = arpoja.nextInt(3);
        return komennot[satunnainen];
    }

    /**
     * debug-metodi hahmojen sijainnin tarkkailuun
     */
    public void tulostaInehmotJaSijainnit() {
        for (Inehmo inehmo : inehmot) {
            System.out.println(inehmo.getTyyppi() + " " + inehmo.getSijainti().getX() + " " + inehmo.getSijainti().getY());
        }
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

    public void setAsiakkaita(int asiakkaita) {
        this.asiakkaita = asiakkaita;
    }

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
