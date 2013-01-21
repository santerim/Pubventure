

package OhHa.ymparisto;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Pubi {

    private int leveys;
    private int korkeus;
    private int asiakkaita;
    private int siirtoja;
    private boolean hirviotLiikkuvat;
    private Scanner lukija = new Scanner(System.in);
    private ArrayList<Inehmo> henkilot = new ArrayList<Inehmo>();
    private Inehmo sankari;
    private Random luku = new Random();

    public Pubi(int leveys, int korkeus, int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.asiakkaita = asiakkaita;
        this.siirtoja = siirtoja;
        this.hirviotLiikkuvat = asiakkaatLiikkuvat;
    }

    public void run() {
        //luoOlennot();

//        testiä
//        Asiakas jantteri = new Asiakas(2, 2, "@", "S", true);
//        jantteri.setX(5);
        
        while (true) {
            System.out.println(siirtoja);
            System.out.println("");
            //tulostaOlennot();
            System.out.println("");
            //tulostaLuola();
            System.out.println("");
            kasitteleSyote(lukija.nextLine());

            if (siirtoja == 0 && asiakkaita > 0) {
                System.out.println("\nHÄVISIT");
                break;
            } else if (asiakkaita == 0) {
                System.out.println("\nVOITIT");
                break;
            }
        }

    }

    public void kasitteleSyote(String syote) {
        //pilkotaan syöte taulukkoon
        String[] komennot = new String[syote.length()];
        for (int i = 0; i < syote.length(); i++) {
            komennot[i] = Character.toString(syote.charAt(i));
        }
        //suoritetaan komennot ja jokaisen jälkeen tutkitaan osuiko,
        //minkä jälkeen liikutetaan hirviöitä (jos sallittu) ja vähennetään
        //yhdellä jäljellä olevia siirtovuoroja
        for (String string : komennot) {
            siirraSankaria(string);
            //osuiko();
            if (hirviotLiikkuvat) {
                //liikutaAsiakkaita();
                //osuiko();
            }
        }
        this.siirtoja--;
    }

    public void siirraSankaria(String suunta) {
        if (suunta.equals("w")) {
            if (sankari.getY() > 0) {
                sankari.setY(sankari.getY() - 1);
            }
        } else if (suunta.equals("a")) {
            if (sankari.getX() > 0) {
                sankari.setX(sankari.getX() - 1);
            }
        } else if (suunta.equals("s")) {
            if (sankari.getY() < korkeus - 1) {
                sankari.setY(sankari.getY() + 1);
            }
        } else if (suunta.equals("d")) {
            if (sankari.getX() < leveys - 1) {
                sankari.setX(sankari.getX() + 1);
            }
        }
    }

//    public void luoOlennot() {
//        // luodaan sankari ja laitetaan hänet olennot-listan alkuun
//        this.sankari = new Henkilo(0, 0, true, "@");
//        this.henkilot.add(sankari);
//        // luodaan hirviöt niin ettei niitä ole samoilla paikoilla
//        for (int i = 0; i < asiakkaita; i++) {
//            Henkilo uusi = new Henkilo(arvoX(), arvoY(), false, "h");
//            while (true) {
//                if (henkilot.contains(uusi)) {
//                    uusi.asetaSijainti(arvoX(), arvoY());
//                } else {
//                    henkilot.add(uusi);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void tulostaOlennot() {
//        for (Henkilo henkilo : henkilot) {
//            System.out.println(henkilo.getUlkomuoto() + " "
//                    + henkilo.getX() + " " + henkilo.getY());
//        }
//    }
//
//    public void tulostaLuola() {
//        for (int i = 0; i < korkeus; i++) {
//            piirraRivi(i);
//        }
//    }
//
//    public void piirraRivi(int y) {
//        // tutkitaan onko koordinaateissa ketään. Jos on, tulostetaan ulkomuoto
//        // ja jos ei, tulostetaan piste
//        for (int i = 0; i < leveys; i++) {
//            String tuloste = ".";
//            for (Henkilo olento : henkilot) {
//                if (olento.getX() == i && olento.getY() == y) {
//                    tuloste = olento.getUlkomuoto();
//                }
//            }
//            System.out.print(tuloste);
//        }
//        System.out.println("");
//    }
//
//    public int arvoX() {
//        return luku.nextInt(leveys);
//    }
//
//    public int arvoY() {
//        return luku.nextInt(korkeus);
//    }
//
//    public void osuiko() {
//        for (int i = 1; i < henkilot.size(); i++) {
//            if (sankari.equals(henkilot.get(i))) {
//                henkilot.remove(i);
//                asiakkaita--;
//            }
//        }
//    }
//
//    public boolean tormaako(int x, int y) {
//        for (Henkilo henkilo : henkilot) {
//            if (henkilo.getX() == x && henkilo.getY() == y) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void liikutaAsiakkaita() {
//        for (Henkilo henkilo : henkilot) {
//            if (!henkilo.onkoSankari()) {
//                String suunta = arvoLiikesuunta();
//                if (suunta.equals("w")) {
//                    if (henkilo.getY() > 0 && tormaako(henkilo.getX(), henkilo.getY() - 1) == false) {
//                        henkilo.setY(henkilo.getY() - 1);
//                    }
//                } else if (suunta.equals("a")) {
//                    if (henkilo.getX() > 0 && tormaako(henkilo.getX() - 1, henkilo.getY()) == false) {
//                        henkilo.setX(henkilo.getX() - 1);
//                    }
//                } else if (suunta.equals("s")) {
//                    if (henkilo.getY() < korkeus - 1 && tormaako(henkilo.getX(), henkilo.getY() + 1) == false) {
//                        henkilo.setY(henkilo.getY() + 1);
//                    }
//                } else if (suunta.equals("d")) {
//                    if (henkilo.getX() < leveys - 1 && tormaako(henkilo.getX() + 1, henkilo.getY()) == false) {
//                        henkilo.setX(henkilo.getX() + 1);
//                    }
//                }
//            }
//        }
//    }

    public String arvoLiikesuunta() {
        String[] suunnat = {"w", "a", "s", "d"};
        int satunnainen = luku.nextInt(4);
        return suunnat[satunnainen];
    }
}
