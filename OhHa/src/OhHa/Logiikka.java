package OhHa;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import OhHa.ihmiset.Sankari;
import OhHa.ihmiset.Tarjoilija;
import OhHa.ymparisto.Pubi;
import OhHa.ymparisto.Pubiobjekti;
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
        System.out.println("Pubin leveys: " + pubi.getLeveys());
        System.out.println("Pubin korkeus: " + pubi.getKorkeus());
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

            if (siirtoja == 0 && asiakkaita > 0) {
                System.out.println("\nHÄVISIT");
                break;
            } else if (asiakkaita == 0) {
                System.out.println("\nVOITIT");
                break;
            }
        }

    }

    //POISTUMASSA
//    public void kasitteleSyote(String syote) {
//        
//               
//        //pilkotaan syöte taulukkoon
//        String[] komennot = new String[syote.length()];
//        for (int i = 0; i < syote.length(); i++) {
//            komennot[i] = Character.toString(syote.charAt(i));
//        }
//        //suoritetaan komennot ja jokaisen jälkeen tutkitaan osuiko,
//        //minkä jälkeen liikutetaan asiakkaita (jos sallittu) ja vähennetään
//        //yhdellä jäljellä olevia siirtovuoroja
//        for (String string : komennot) {
//            kasitteleKomento(string);
////            osuiko();
//            if (asiakkaatLiikkuvat) {
//                liikutaAsiakkaita();
////                osuiko();
//            }
//        }
//        this.siirtoja--;
//    }
    public void luoOlennot() {
        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        this.sankari = new Sankari(13, 1, "@", "sankari", true);
        this.inehmot.add(sankari);

        //luodaan tarjoilija
        this.inehmot.add(new Tarjoilija(5, 1, "t", "tarjoilija", false));

        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla
        // tai seinissä yms
        int asiakkaitaJaljella = this.asiakkaita;

        while (asiakkaitaJaljella > 0) {
            int uusiX = this.arvoX();
            int uusiY = this.arvoY();

            if (!tormaako(uusiX, uusiY)) {
                inehmot.add(new Asiakas(uusiX, uusiY, "a", "asiakas", true));
                asiakkaitaJaljella--;
            }

//            if (!pubi.annaObjekti(uusiX, uusiY).getEste()) {
//                boolean toinenInehmoSamassaPaikassa = false;
//                for (Inehmo inehmo : inehmot) {
//                    if (inehmo.x == uusiX && inehmo.y == uusiY) {
//                        toinenInehmoSamassaPaikassa = true;
//                    }
//                }
//                if (!toinenInehmoSamassaPaikassa) {
//                    inehmot.add(new Asiakas(uusiX, uusiY, "a", "asiakas", true));
//                    asiakkaitaJaljella--;
//                }
//            }
        }
        //DEBUG-tulostus:
        for (Inehmo inehmo : inehmot) {
            System.out.println(inehmo.tyyppi + " " + inehmo.x + " " + inehmo.y);
        }
    }

    public void kasitteleKomento(String komento) {

        if (komento.isEmpty()) {
//            System.out.println("Odotat hetken");
        } else {
            char ekaKirjain = komento.charAt(0);
            switch (ekaKirjain) {
                case 'w':
                    if (!tormaako(sankari.x, sankari.y - 1)) {
                        if (sankari.getY() > 0) {
                            sankari.setY(sankari.getY() - 1);
                        }
                    }
                    break;
                case 'a':
                    if (!tormaako(sankari.x - 1, sankari.y)) {
                        if (sankari.getX() > 0) {
                            sankari.setX(sankari.getX() - 1);
                        }
                    }
                    break;
                case 's':
                    if (!tormaako(sankari.x, sankari.y + 1)) {
                        if (sankari.getY() < pubi.getKorkeus() - 1) {
                            sankari.setY(sankari.getY() + 1);
                        }
                    }
                    break;
                case 'd':
                    if (!tormaako(sankari.x + 1, sankari.y)) {
                        if (sankari.getX() < pubi.getLeveys() - 1) {
                            sankari.setX(sankari.getX() + 1);
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
            if (inehmo.getX() == x && inehmo.getY() == y) {
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
                    if (inehmo.x == j && inehmo.y == i) {
                        System.out.print(inehmo.ulkomuoto);
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
    //MUUTETTAVA!
    public void liikutaAsiakkaita() {
        for (Inehmo inehmo : inehmot) {
            if (!inehmo.getSankaruus() && inehmo.getLiikkuva()) {
                String suunta = arvoLiikesuunta();
                switch (suunta) {
                    case "w":
                        if (inehmo.getY() > 0 && tormaako(inehmo.getX(), inehmo.getY() - 1) == false) {
                            inehmo.setY(inehmo.getY() - 1);
                        }
                        break;
                    case "a":
                        if (inehmo.getX() > 0 && tormaako(inehmo.getX() - 1, inehmo.getY()) == false) {
                            inehmo.setX(inehmo.getX() - 1);
                        }
                        break;
                    case "s":
                        if (inehmo.getY() < pubi.getKorkeus() - 1 && tormaako(inehmo.getX(), inehmo.getY() + 1) == false) {
                            inehmo.setY(inehmo.getY() + 1);
                        }
                        break;
                    case "d":
                        if (inehmo.getX() < pubi.getLeveys() - 1 && tormaako(inehmo.getX() + 1, inehmo.getY()) == false) {
                            inehmo.setX(inehmo.getX() + 1);
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
