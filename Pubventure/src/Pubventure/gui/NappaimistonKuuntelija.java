
package Pubventure.gui;

import Pubventure.Logiikka;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Luokka hoitaa näppäinkomentojen sieppaamisen ja välittää ne
 * käyttöliittymä-luokalle
 */

public class NappaimistonKuuntelija implements KeyListener {
    
    Kayttoliittyma kl;
    
    public NappaimistonKuuntelija(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_W) {
            kl.liikeKomento("w");
        } else if (ke.getKeyCode() == KeyEvent.VK_D) {
            kl.liikeKomento("d");
        } else if (ke.getKeyCode() == KeyEvent.VK_S) {
            kl.liikeKomento("s");
        } else if (ke.getKeyCode() == KeyEvent.VK_A) {
            kl.liikeKomento("a");
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            kl.liikeKomento("");
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
    
}
