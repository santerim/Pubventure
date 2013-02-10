package OhHa.gui;

import OhHa.Logiikka;
import OhHa.ihmiset.Inehmo;
import OhHa.ymparisto.Pubi;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * Kayttoliittyma-luokka hoitaa swing-ikkunan luomisen ja hallinnoi sen
 * toimintaa.
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Piirtoalusta piirtoalusta;
    private int leveys;
    private int korkeus;
    private Pubi pubi;
    private ArrayList<Inehmo> inehmot;
    private Logiikka logiikka;

    public Kayttoliittyma(Pubi pubi, ArrayList<Inehmo> inehmot, Logiikka log) {
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
        this.logiikka = log;
    }

    @Override
    public void run() {
        this.frame = new JFrame("Pubventure");
//        System.out.println("Pubin leveys ja korkeus: " + this.leveys + " " + this.korkeus);
        frame.setPreferredSize(new Dimension(500, 480));
//        frame.setPreferredSize(new Dimension(pubi.getLeveys() * 8, pubi.getKorkeus() * 20));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    //debug-metodi
    public void komento(String komento) {
        logiikka.kasitteleKomento(komento);
    }

    private void luoKomponentit(Container contentPane) {
        BorderLayout layout = new BorderLayout();
        contentPane.setLayout(layout);
        this.piirtoalusta = new Piirtoalusta(pubi, inehmot);
//        contentPane.add(piirtoalusta);      

        JLabel kentta = new JLabel();
        JLabel tiedot = new JLabel();
        JLabel viesti = new JLabel();

        contentPane.add(kentta, BorderLayout.CENTER);
        contentPane.add(tiedot, BorderLayout.EAST);
        contentPane.add(viesti, BorderLayout.SOUTH);

        this.piirtoalusta.setPiirtoalusta(kentta, tiedot, viesti);
        this.piirtoalusta.piirraAlue();

        piirtoalusta.setViestiKentanSisalto("tieto-label");
//        tiedot.setText("tieto-label");
        viesti.setText("viesti-label");
        
        this.frame.addKeyListener(new NappaimistonKuuntelija(this));
    }
    
    public Piirtoalusta getPiirtoalusta() {
        return this.piirtoalusta;
    }
}
