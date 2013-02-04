
package OhHa.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;


public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    private Piirtoalusta piirtoalusta;
    
    public Kayttoliittyma() {
        
    }
    
    @Override
    public void run() {
        this.frame = new JFrame("Pubventure");
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        luoKomponentit(frame.getContentPane());
        
        frame.pack();
        frame.setVisible(true);
    }

    private void luoKomponentit(Container contentPane) {
        this.piirtoalusta = new Piirtoalusta();
        contentPane.add(piirtoalusta);
        this.frame.addKeyListener(new NappaimistonKuuntelija());
        
        JLabel l = new JLabel();
        l.setOpaque(true);
        l.setBackground(Color.red);
        l.setPreferredSize(new Dimension(15, 15));
        
        JLabel l2 = new JLabel();
        l2.setOpaque(true);
        l2.setBackground(Color.yellow);
        l2.setPreferredSize(new Dimension(15, 15));
        
        JLabel l3 = new JLabel();
        l3.setOpaque(true);
        l3.setBackground(Color.BLACK);
        l3.setPreferredSize(new Dimension(15, 15));
        
        frame.getContentPane().add(l, BorderLayout.WEST);
        frame.getContentPane().add(l2, BorderLayout.SOUTH);
        frame.getContentPane().add(l3, BorderLayout.PAGE_START);
    }
    
}
