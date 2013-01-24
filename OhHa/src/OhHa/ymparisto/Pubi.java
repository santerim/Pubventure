package OhHa.ymparisto;

import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import OhHa.ihmiset.Sankari;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Pubi {

    private int leveys;
    private int korkeus;
    private int asiakkaita;
    private int siirtoja;
    private boolean asiakkaatLiikkuvat;
    private Scanner lukija = new Scanner(System.in);
    private ArrayList<Inehmo> inehmot = new ArrayList<>();
    private Inehmo sankari;
    private Random luku = new Random();

    public Pubi(int leveys, int korkeus, int asiakkaita, int siirtoja, boolean asiakkaatLiikkuvat) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.asiakkaita = asiakkaita;
        this.siirtoja = siirtoja;
        this.asiakkaatLiikkuvat = asiakkaatLiikkuvat;
    }

    public void run() {
        luoOlennot();

//        testiä
//        Asiakas jantteri = new Asiakas(2, 2, "@", "S", true);
//        jantteri.setX(5);

        while (true) {
            System.out.println("Siirtoja: " + siirtoja);
            System.out.println("Asiakkaita: " + asiakkaita);
            System.out.println("");
            tulostaOlennot();
            System.out.println("");
            tulostaLuola();
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
        //minkä jälkeen liikutetaan asiakkaita (jos sallittu) ja vähennetään
        //yhdellä jäljellä olevia siirtovuoroja
        for (String string : komennot) {
            siirraSankaria(string);
            osuiko();
            if (asiakkaatLiikkuvat) {
                liikutaAsiakkaita();
                osuiko();
            }
        }
        this.siirtoja--;
    }

    public void siirraSankaria(String suunta) {
        switch (suunta) {
            case "w":
                if (!tormaako(sankari.x, sankari.y - 1)) {
                    if (sankari.getY() > 0) {
                        sankari.setY(sankari.getY() - 1);
                    }
                }

                break;
            case "a":
                if (!tormaako(sankari.x - 1, sankari.y)) {
                    if (sankari.getX() > 0) {
                    sankari.setX(sankari.getX() - 1);
                }
                }
                
                break;
            case "s":
                if (!tormaako(sankari.x, sankari.y + 1)) {
                    if (sankari.getY() < korkeus - 1) {
                        sankari.setY(sankari.getY() + 1);
                    }
                }

                break;
            case "d":
                if (!tormaako(sankari.x + 1, sankari.y)) {
                    if (sankari.getX() < leveys - 1) {
                    sankari.setX(sankari.getX() + 1);
                }
                }
                
                break;
        }
    }

    //KESKEN!
    public void luoOlennot() {
        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        this.sankari = new Sankari(1, 1, "@", "S", true);
        this.inehmot.add(sankari);
        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla


        //debug-jantteri
        Asiakas jantteri = new Asiakas(1, 0, "a", "A", true);
        inehmot.add(jantteri);

        for (int i = 0; i < asiakkaita; i++) {
            Asiakas uusi = new Asiakas(arvoX(), arvoY(), "a", "A", true);
            while (true) {
                if (inehmot.contains(uusi)) {
                    uusi.setSijainti(arvoX(), arvoY());
                } else {
                    inehmot.add(uusi);
                    break;
                }
            }
        }
    }

    public void tulostaOlennot() {
        for (Inehmo inehmo : inehmot) {
            System.out.println(inehmo.getUlkomuoto() + " "
                    + inehmo.getX() + " " + inehmo.getY());
        }
    }

    public void tulostaLuola() {
        for (int i = 0; i < korkeus; i++) {
            piirraRivi(i);
        }
    }

    public void piirraRivi(int y) {
        // tutkitaan onko koordinaateissa ketään. Jos on, tulostetaan ulkomuoto
        // ja jos ei, tulostetaan piste
        for (int i = 0; i < leveys; i++) {
            String tuloste = ".";
            for (Inehmo inehmo : inehmot) {
                if (inehmo.getX() == i && inehmo.getY() == y) {
                    tuloste = inehmo.getUlkomuoto();
                }
            }
            System.out.print(tuloste);
        }
        System.out.println("");
    }

    public int arvoX() {
        return luku.nextInt(leveys);
    }

    public int arvoY() {
        return luku.nextInt(korkeus);
    }

    // MUUTETTAVA!
    public void osuiko() {
        for (int i = 1; i < inehmot.size(); i++) {
            if (sankari.equals(inehmot.get(i))) {
                inehmot.remove(i);
                asiakkaita--;
            }
        }
    }

    public boolean tormaako(int x, int y) {
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getX() == x && inehmo.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void liikutaAsiakkaita() {
        for (Inehmo inehmo : inehmot) {
            if (!inehmo.getSankaruus()) {
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
                        if (inehmo.getY() < korkeus - 1 && tormaako(inehmo.getX(), inehmo.getY() + 1) == false) {
                            inehmo.setY(inehmo.getY() + 1);
                        }
                        break;
                    case "d":
                        if (inehmo.getX() < leveys - 1 && tormaako(inehmo.getX() + 1, inehmo.getY()) == false) {
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

    public int getLeveys() {
        return this.leveys;
    }

    public int getKorkeus() {
        return this.korkeus;
    }

    public int getAsiakkaita() {
        return this.asiakkaita;
    }

    public boolean getAsiakkaatLiikkuvat() {
        return this.asiakkaatLiikkuvat;
    }
}
