package OhHa;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import OhHa.ihmiset.Sankari;
import OhHa.ihmiset.Tarjoilija;
import OhHa.ymparisto.Pubi;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import OhHa.gui.Kayttoliittyma;

public class Logiikka implements Runnable {

    private Pubi pubi;
    private int asiakkaita;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
    private Scanner lukija = new Scanner(System.in);
    
    private ArrayList<Inehmo> inehmot;
    private Random luku = new Random();
    
    private Kayttoliittyma kl;

    public Logiikka(int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.asiakkaita = asiakkaita;
        this.pubi = new Pubi(asiakkaita);
        pubi.luoKentta();
        pubi.luoOlennot();
        this.inehmot = pubi.getInehmot();
//        System.out.println("Pubin leveys: " + pubi.getLeveys());
//        System.out.println("Pubin korkeus: " + pubi.getKorkeus());
        
        this.siirtoja = siirtoja;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;

        
        
    }

    @Override
    public void run() {


        //käyttöliittymätestausta
        this.kl = new Kayttoliittyma(pubi, inehmot);
        kl.run();
        
        while (true) {
            System.out.println("Siirtoja: " + siirtoja);
            System.out.println("Asiakkaita: " + asiakkaita);
            System.out.println("");
            //tulostaOlennot();
            System.out.println("");
            tulostaPubi();
            System.out.println("");

            System.out.print("Anna komento> ");
            String komento = lukija.nextLine();
            kasitteleKomento(komento);

            if (asiakkaatLiikkuvat) {
                liikutaAsiakkaita();
            }

            // tehtävä uusiksi
            if (siirtoja == 0 && asiakkaita > 0) {
                System.out.println("\nHÄVISIT");
                break;
            } else if (asiakkaita == 0) {
                System.out.println("\nVOITIT");
                break;
            }
        }

    }

    public Sijainti getSankarinSijainti() {
        return pubi.getInehmot().get(0).getSijainti();
    }
    

    public void kasitteleKomento(String komento) {

        if (komento.isEmpty()) {
//            System.out.println("Odotat hetken");
        } else {
            char ekaKirjain = komento.charAt(0);
            switch (ekaKirjain) {
                case 'w':
                    if (!tormaako(getSankarinSijainti().getX(), getSankarinSijainti().getY() - 1)) {
                        if (getSankarinSijainti().getY() > 0) {
                            getSankarinSijainti().setY(getSankarinSijainti().getY() - 1);
                        }
                    }
                    break;
                case 'a':
                    if (!tormaako(getSankarinSijainti().getX() - 1, getSankarinSijainti().getY())) {
                        if (getSankarinSijainti().getX() > 0) {
                            getSankarinSijainti().setX(getSankarinSijainti().getX() - 1);
                        }
                    }
                    break;
                case 's':
                    if (!tormaako(getSankarinSijainti().getX(), getSankarinSijainti().getY() + 1)) {
                        if (getSankarinSijainti().getY() < pubi.getKorkeus() - 1) {
                            getSankarinSijainti().setY(getSankarinSijainti().getY() + 1);
                        }
                    }
                    break;
                case 'd':
                    if (!tormaako(getSankarinSijainti().getX() + 1, getSankarinSijainti().getY())) {
                        if (getSankarinSijainti().getX() < pubi.getLeveys() - 1) {
                            getSankarinSijainti().setX(getSankarinSijainti().getX() + 1);
                        }
                    }
                    break;
            }
        }
    }

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



    // MUUTETTAVA!
//    public void osuiko() {
//        for (int i = 1; i < inehmot.size(); i++) {
//            if (sankari.equals(inehmot.get(i))) {
//                inehmot.remove(i);
//                asiakkaita--;
//            }
//        }
//    }
    
    //MUUTETTAVA - lisättävä satunnaisuutta ja lopulta kohteeseen suunnistus
    public void liikutaAsiakkaita() {
        for (Inehmo inehmo : inehmot) {
            if (!inehmo.getSankaruus() && inehmo.getLiikkuvuus()) {
                String suunta = arvoLiikesuunta();
                switch (suunta) {
                    case "w":
                        //jos ei törmää, muutetaan inehmon sijaintia
                        if (inehmo.getSijainti().getY() > 0 && tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() - 1) == false) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() - 1);
                        }
                        break;
                    case "a":
                        if (inehmo.getSijainti().getX() > 0 && tormaako(inehmo.getSijainti().getX() - 1, inehmo.getSijainti().getY()) == false) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() - 1);
                        }
                        break;
                    case "s":
                        if (inehmo.getSijainti().getY() < pubi.getKorkeus() - 1 && tormaako(inehmo.getSijainti().getX(), inehmo.getSijainti().getY() + 1) == false) {
                            inehmo.getSijainti().setY(inehmo.getSijainti().getY() + 1);
                        }
                        break;
                    case "d":
                        if (inehmo.getSijainti().getX() < pubi.getLeveys() - 1 && tormaako(inehmo.getSijainti().getX() + 1, inehmo.getSijainti().getY()) == false) {
                            inehmo.getSijainti().setX(inehmo.getSijainti().getX() + 1);
                        }
                        break;
                }
            }
        }
    }

    public String arvoLiikesuunta() {
        String[] suunnat = {"w", "a", "s", "d"};
        int satunnainen = luku.nextInt(4);
        return suunnat[satunnainen];
    }

    public void tulostaInehmotJaSijainnit() {
        for (Inehmo inehmo : inehmot) {
            System.out.println(inehmo.getTyyppi() + " " + inehmo.getSijainti().getX() + " " + inehmo.getSijainti().getY());
        }
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
