
package OhHa.gui;

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
 * Kayttoliittyma-luokka hoitaa swing-ikkunan luomisen ja hallinnoi sen toimintaa.
 */

public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Piirtoalusta piirtoalusta;
    private int leveys;
    private int korkeus;
    private Pubi pubi;
    private ArrayList<Inehmo> inehmot;
    
    public Kayttoliittyma(Pubi pubi, ArrayList<Inehmo> inehmot) {
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
    }
    
    @Override
    public void run() {
        this.frame = new JFrame("Pubventure");
        System.out.println("Pubin leveys ja korkeus: " + this.leveys + " " + this.korkeus);
        frame.setPreferredSize(new Dimension(pubi.getLeveys() * 8, pubi.getKorkeus() * 20));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container contentPane) {
        this.piirtoalusta = new Piirtoalusta(pubi, inehmot);
        contentPane.add(piirtoalusta);
        this.frame.addKeyListener(new NappaimistonKuuntelija());
        
        JLabel kentta = new JLabel();
        JLabel tiedot = new JLabel();
        JLabel viesti = new JLabel();
        
        contentPane.add(kentta, BorderLayout.CENTER);
        contentPane.add(tiedot, BorderLayout.EAST);
        contentPane.add(viesti, BorderLayout.SOUTH);
        
        this.piirtoalusta.setPiirtoalusta(kentta, tiedot);
        this.piirtoalusta.piirraAlue();
    }
    
}
