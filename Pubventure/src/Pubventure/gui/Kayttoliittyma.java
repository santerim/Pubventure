package Pubventure.gui;

import Pubventure.Logiikka;
import Pubventure.Sijainti;
import Pubventure.enumit.KomentoEnum;
import Pubventure.ihmiset.Inehmo;
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
//    private Piirtoalusta piirtaja;
    private int leveys;
    private int korkeus;
    private Pubi pubi;
    private ArrayList<Inehmo> inehmot;
    private StringBuilder sb;
    private Logiikka logiikka;
    private Font fontti;

    public Kayttoliittyma(Pubi pubi, ArrayList<Inehmo> inehmot, Logiikka log) {
        this.piirtaja = new Piirtaja();
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
        this.logiikka = log;
        this.sb = new StringBuilder();
        
        this.piirtaja.addKeyListener(new NappaimistonKuuntelija(this));
        this.fontti = new Font("monospaced", Font.PLAIN, 12);
        this.piirtaja.kenttaLabel.setFont(fontti);
        this.piirtaja.kenttaLabel.setForeground(Color.WHITE);
        this.piirtaja.tietoLabel.setForeground(Color.WHITE);
        this.piirtaja.viestiLabel.setForeground(Color.WHITE);
    }

    @Override
    public void run() {
        
//        this.piirtaja = new Piirtaja();
//        this.piirtaja.addKeyListener(new NappaimistonKuuntelija(this));
//        this.piirtaja = new JFrame("Pubventure");
////        System.out.println("Pubin leveys ja korkeus: " + this.leveys + " " + this.korkeus);
//        piirtaja.setPreferredSize(new Dimension(500, 480));
////        frame.setPreferredSize(new Dimension(pubi.getLeveys() * 8, pubi.getKorkeus() * 20));
//        piirtaja.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        luoKomponentit(piirtaja.getContentPane());

        piirtaja.pack();
        piirtaja.setVisible(true);
        piirraAlue();
        logiikka.kirjoitaPelaajanTiedot();
    }

    /**
     * Hoitaa pelikentän piirtämisen
     */
    public void piirraAlue() {
        sb.setLength(0);
        sb.append("<html>");
        
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
     * 
     * Välittää näppäinkomentojen syötteen eteenpäin logiikalle
     */
    public void valitaKomento(KomentoEnum komento) {
        logiikka.kasitteleKomento(komento);
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
    
    public void setViestiKentanSisalto(String sisalto) {
        piirtaja.viestiLabel.setText(sisalto);
    }
    
    public void setTietoKentanSisalto(String sisalto) {
        piirtaja.tietoLabel.setText(sisalto);
    }
    
    public void setPeliKentanSisalto(String sisalto) {
        piirtaja.kenttaLabel.setText(sisalto);
    }
    
    //    /**
//     * 
//     * Luo käyttöliittymän komponentit
//     */
//    private void luoKomponentit(Container contentPane) {
//        BorderLayout layout = new BorderLayout();
//        contentPane.setLayout(layout);
//        this.piirtaja = new Piirtoalusta(pubi, inehmot);
////        contentPane.add(piirtoalusta);      
//
//        JLabel kentta = new JLabel();
//        JLabel tiedot = new JLabel();
//        JLabel viesti = new JLabel();
//        
//        contentPane.add(kentta, BorderLayout.CENTER);
//        contentPane.add(tiedot, BorderLayout.EAST);
//        contentPane.add(viesti, BorderLayout.SOUTH);
//
//        this.piirtaja.setPiirtoalusta(kentta, tiedot, viesti);
//        this.piirtaja.piirraAlue();
//
//        logiikka.kirjoitaPelaajanTiedot();
////        tiedot.setText("tieto-label");
//        viesti.setText("viesti-label");
//        
//        this.piirtaja.addKeyListener(new NappaimistonKuuntelija(this));
//    }
    
}
