
package Pubventure.gui;

import Pubventure.Logiikka;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Luokka hoitaa näppäinkomentojen sieppaamisen
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
        if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            kl.komento("w");
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            kl.komento("d");
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            kl.komento("s");
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            kl.komento("a");
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD5) {
            kl.komento("");
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
    
}
