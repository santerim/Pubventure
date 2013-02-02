package OhHa;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import OhHa.ihmiset.Sankari;
import OhHa.ihmiset.Tarjoilija;
import OhHa.ymparisto.Pubi;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Logiikka {

    private Pubi pubi;
    private int asiakkaita;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
    private Scanner lukija = new Scanner(System.in);
    private Inehmo sankari;
    private ArrayList<Inehmo> inehmot = new ArrayList<>();
    private Random luku = new Random();

    public Logiikka(int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.pubi = new Pubi();
        pubi.luoKentta();
//        System.out.println("Pubin leveys: " + pubi.getLeveys());
//        System.out.println("Pubin korkeus: " + pubi.getKorkeus());
        this.asiakkaita = asiakkaita;
        this.siirtoja = siirtoja;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;

    }

    public void run() {
        luoOlennot();

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

    public void luoOlennot() {
        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        this.sankari = new Sankari(new Sijainti(13, 1), "@", "sankari", true);
        this.inehmot.add(sankari);

        //luodaan tarjoilija
        this.inehmot.add(new Tarjoilija(new Sijainti(5, 1), "t", "tarjoilija", false));

        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla
        // tai seinissä yms
        int asiakkaitaJaljella = this.asiakkaita;

        while (asiakkaitaJaljella > 0) {
            int uusiX = this.arvoX();
            int uusiY = this.arvoY();

            if (!tormaako(uusiX, uusiY)) {
                inehmot.add(new Asiakas(new Sijainti(uusiX, uusiY), "a", "asiakas", true));
                asiakkaitaJaljella--;
            }
        }
    }

    public void kasitteleKomento(String komento) {

        if (komento.isEmpty()) {
//            System.out.println("Odotat hetken");
        } else {
            char ekaKirjain = komento.charAt(0);
            switch (ekaKirjain) {
                case 'w':
                    if (!tormaako(sankari.getSijainti().getX(), sankari.getSijainti().getY() - 1)) {
                        if (sankari.getSijainti().getY() > 0) {
                            sankari.getSijainti().setY(sankari.getSijainti().getY() - 1);
                        }
                    }
                    break;
                case 'a':
                    if (!tormaako(sankari.getSijainti().getX() - 1, sankari.getSijainti().getY())) {
                        if (sankari.getSijainti().getX() > 0) {
                            sankari.getSijainti().setX(sankari.getSijainti().getX() - 1);
                        }
                    }
                    break;
                case 's':
                    if (!tormaako(sankari.getSijainti().getX(), sankari.getSijainti().getY() + 1)) {
                        if (sankari.getSijainti().getY() < pubi.getKorkeus() - 1) {
                            sankari.getSijainti().setY(sankari.getSijainti().getY() + 1);
                        }
                    }
                    break;
                case 'd':
                    if (!tormaako(sankari.getSijainti().getX() + 1, sankari.getSijainti().getY())) {
                        if (sankari.getSijainti().getX() < pubi.getLeveys() - 1) {
                            sankari.getSijainti().setX(sankari.getSijainti().getX() + 1);
                        }
                    }
                    break;
            }
        }
    }

    public boolean tormaako(int x, int y) {
        if (pubi.annaObjekti(x, y).getEste()) {
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
                    System.out.print(pubi.annaObjekti(j, i).getUlkonako());
                }
            }
            System.out.println("");
        }
    }

    public int arvoX() {
        return luku.nextInt(pubi.getLeveys());
    }

    public int arvoY() {
        return luku.nextInt(pubi.getKorkeus());
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
    
    //MUUTETTAVA - lisättävä satunnaisuus ja lopulta kohteeseen suunnistus
    public void liikutaAsiakkaita() {
        for (Inehmo inehmo : inehmot) {
            if (!inehmo.getSankaruus() && inehmo.getLiikkuvuus()) {
                String suunta = arvoLiikesuunta();
                switch (suunta) {
                    case "w":
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

    public int getAsiakkaita() {
        return this.asiakkaita;
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
}
