package Pubventure.gui;

import Pubventure.Logiikka;
import Pubventure.enumit.KomentoEnum;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * Luokka hoitaa näppäinkomentojen sieppaamisen ja välittää ne
 * käyttöliittymä-luokalle
 */
public class NappaimistonKuuntelija implements KeyListener {

    Kayttoliittyma kl;
    private boolean odotetaanKomentoa;

    public NappaimistonKuuntelija(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            kl.valitaKomento(KomentoEnum.POHJOINEN);
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            kl.valitaKomento(KomentoEnum.ITA);
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            kl.valitaKomento(KomentoEnum.ETELA);
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            kl.valitaKomento(KomentoEnum.LANSI);
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8) {
            kl.valitaKomento(KomentoEnum.POHJOINEN);
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) {
            kl.valitaKomento(KomentoEnum.ITA);
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) {
            kl.valitaKomento(KomentoEnum.ETELA);
        } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) {
            kl.valitaKomento(KomentoEnum.LANSI);
        } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
            kl.valitaKomento(KomentoEnum.ODOTUS);
        } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            if (odotetaanKomentoa == false) {
                kl.valitaKomento(KomentoEnum.TEEJOTAIN);
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    public void setOdotetaanKomentoa(boolean juuTaiEi) {
        this.odotetaanKomentoa = juuTaiEi;
    }

    public boolean getOdotetaanKomentoa() {
        return this.odotetaanKomentoa;
    }
}
