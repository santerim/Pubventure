package Pubventure.gui;

import Pubventure.enumit.KomentoEnum;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * Luokka hoitaa näppäinkomentojen sieppaamisen ja välittää ne
 * käyttöliittymä-luokalle.
 * Oleellinen toiminto on myös komentojen odotustilannetta määrittävien
 * boolean-muuttujien päivittäminen.
 */
public class NappaimistonKuuntelija implements KeyListener {

    Kayttoliittyma kl;
    private boolean odotetaanKomentoa;
    private boolean odotetaanSuuntaKomentoa;

    public NappaimistonKuuntelija(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (!odotetaanKomentoa) {
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
                kl.valitaKomento(KomentoEnum.TEEJOTAIN);
                setOdotetaanKomentoa(true);
            }
        }
        if (odotetaanKomentoa && !odotetaanSuuntaKomentoa) {
            if (ke.getKeyCode() == KeyEvent.VK_O) {
                kl.valitaKomento(KomentoEnum.OSTA);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_A) {
                kl.valitaKomento(KomentoEnum.ANNA);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_L) {
                kl.valitaKomento(KomentoEnum.LYO);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_K) {
                kl.valitaKomento(KomentoEnum.KUSE);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_P) {
                kl.valitaKomento(KomentoEnum.PUHU);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_J) {
                kl.valitaKomento(KomentoEnum.JUO);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_T) {
                kl.valitaKomento(KomentoEnum.TUTKI);
                setOdotetaanKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                kl.valitaKomento(KomentoEnum.PERU);
                setOdotetaanKomentoa(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    public void setOdotetaanKomentoa(boolean trueTaiFalse) {
        this.odotetaanKomentoa = trueTaiFalse;
    }
    
    public void setOdotetaanSuuntaKomentoa(boolean trueTaiFalse) {
        this.odotetaanSuuntaKomentoa = trueTaiFalse;
    }

    public boolean getOdotetaanKomentoa() {
        return this.odotetaanKomentoa;
    }
    
    public boolean getOdotetaanSuuntaKomentoa() {
        return this.odotetaanSuuntaKomentoa;
    }
}
