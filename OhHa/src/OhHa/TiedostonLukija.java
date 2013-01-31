package OhHa;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TiedostonLukija {

    private File tiedosto;
    private Scanner lukija;
//    private int pubinLeveys;
//    private int pubinKorkeus;
    private String merkkijono = "";
//    private boolean leveysLoydetty = false;

    public TiedostonLukija() {
        this.tiedosto = new File("src/OhHa/pub.txt");
    }

    public String lueTiedosto() {
        StringBuilder sb = new StringBuilder();

        try {
            lukija = new Scanner(tiedosto);
        } catch (FileNotFoundException ex) {
            System.out.println("Tiedoston lukeminen epäonnistui.\n" + ex.getMessage());
        }

        while (lukija.hasNextLine()) {
//            if (!leveysLoydetty) {
//                sb.append(lukija.nextLine());
//                pubinLeveys = sb.length();
//                //System.out.println("Pubin leveys: " + pubinLeveys);
//                sb.append("\n");
//                pubinKorkeus++;
//                leveysLoydetty = true;
//            } else {
                sb.append(lukija.nextLine());
                sb.append("\n");
//                pubinKorkeus++;
//            }

        }
        //System.out.println("Pubin korkeus: " + pubinKorkeus);
        this.merkkijono = sb.toString();
        return sb.toString();
    }

//    public int getPubinLeveys() {
//        return this.pubinLeveys;
//    }
//
//    public int getPubinKorkeus() {
//        return this.pubinKorkeus;
//    }
    
    public String getMerkkijono() {
        return this.merkkijono;
    }
}
