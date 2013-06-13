
package pubventure.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import pubventure.enumit.KomentoEnum;

/**
 *
 * @author Santeri
 *
 * Luokka hoitaa näppäinkomentojen sieppaamisen ja välittää ne
 * käyttöliittymä-luokalle. Oleellinen toiminto on myös komentojen
 * odotustilannetta määrittävän boolean-muuttujan päivittäminen.
 */
public class NappaimistonKuuntelija implements KeyListener {

    Kayttoliittyma kl;
    KomentoEnum komento;
    private boolean odotetaanSuuntaKomentoa;
    private boolean odotaKyllaVaiEiKysymysta;
    private boolean loppu;

    public NappaimistonKuuntelija(Kayttoliittyma kl) {
        this.kl = kl;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        /**
         * Jos peli on loppu..
         */
        if (loppu) {
            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                kl.getPiirtaja().dispose();
            }
        }
        
        /**
         * jos ollaan ns. normaalitilassa, kuunnellaan tavanomaisia
         * näppäinkomentoja
         */
        if (!odotetaanSuuntaKomentoa 
                && !odotaKyllaVaiEiKysymysta
                && !loppu) {            
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
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD9) {
                kl.valitaKomento(KomentoEnum.KOILLINEN);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                kl.valitaKomento(KomentoEnum.KAAKKO);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                kl.valitaKomento(KomentoEnum.LOUNAS);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD7) {
                kl.valitaKomento(KomentoEnum.LUODE);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                kl.valitaKomento(KomentoEnum.ITA);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                kl.valitaKomento(KomentoEnum.ETELA);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                kl.valitaKomento(KomentoEnum.LANSI);
            } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                kl.valitaKomento(KomentoEnum.ODOTUS);
            } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                kl.valitaKomento(KomentoEnum.OHJE);
            } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                kl.valitaKomento(KomentoEnum.PERU);
            }
            // a-star- ja dijkstratestaus
            else if (ke.getKeyCode() == KeyEvent.VK_A) {
                kl.valitaKomento(KomentoEnum.ASTAR);
            }
            else if (ke.getKeyCode() == KeyEvent.VK_C) {
                kl.valitaKomento(KomentoEnum.PYYHI);
            }
            else if (ke.getKeyCode() == KeyEvent.VK_Q) {
                kl.valitaKomento(KomentoEnum.TIEDOT);
            }
            else if (ke.getKeyCode() == KeyEvent.VK_D) {
                kl.valitaKomento(KomentoEnum.DIJKSTRA);
            }

            /**
             * Mikäli annettu komento on kaksivaiheinen, rajoitetaan sallittuja
             * näppäinkomentoja ja otetaan annettu komento muuttujaan talteen
             * odottamaan suuntakomentoa.
             * Logiikalle välitetään SUUNTA, joka
             * aiheuttaa suunnanantokehotuksen tulostamisen.
             * 
             */
            if (ke.getKeyCode() == KeyEvent.VK_O) {
                komento = KomentoEnum.OSTA;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_U) {
                komento = KomentoEnum.PUMMI;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_L) {
                komento = KomentoEnum.LYO;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_K) {
                komento = KomentoEnum.KUSE;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_P) {
                komento = KomentoEnum.PUHU;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_J) {
                komento = KomentoEnum.JUO;
                kl.valitaKomento(KomentoEnum.JUO);
            } else if (ke.getKeyCode() == KeyEvent.VK_T) {
                komento = KomentoEnum.TUTKI;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_V) {
                komento = KomentoEnum.VONKAA;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            } else if (ke.getKeyCode() == KeyEvent.VK_M) {
                komento = KomentoEnum.MAALI;
                setOdotetaanSuuntaKomentoa(true);
                kl.valitaKomento(KomentoEnum.SUUNTA);
            }


            //            else if (ke.getKeyCode() == KeyEvent.VK_A) {
//                komento = KomentoEnum.ANNA;
//                setOdotetaanSuuntaKomentoa(true);
//                kl.valitaKomento(KomentoEnum.SUUNTA);
//            } 
        }
        /**
         * Välitetään annettu suunta ja edellisessä vaiheessa saatu komento
         * eteenpäin. Muutetaan muuttujan arvoa niin, että näppäinkomentojen
         * vastaanottaminen toimii taas normaalisti.
         */
        if (odotetaanSuuntaKomentoa 
                && !odotaKyllaVaiEiKysymysta
                && !loppu) {
            if (ke.getKeyCode() == KeyEvent.VK_UP) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.POHJOINEN);
            } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.ITA);
            } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.ETELA);
            } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.LANSI);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD8) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.POHJOINEN);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD6) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.ITA);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD2) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.ETELA);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD4) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.LANSI);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD9) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.KOILLINEN);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD3) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.KAAKKO);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD1) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.LOUNAS);
            } else if (ke.getKeyCode() == KeyEvent.VK_NUMPAD7) {
                setOdotetaanSuuntaKomentoa(false);
                kl.valitaKaksivaiheinenKomento(komento, KomentoEnum.LUODE);
            } else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                setOdotetaanSuuntaKomentoa(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                setOdotetaanSuuntaKomentoa(false);
            }
        }
        
        if (odotaKyllaVaiEiKysymysta && !loppu) {
            if (ke.getKeyCode() == KeyEvent.VK_K) {
                kl.loppu();
            } else if (ke.getKeyCode() == KeyEvent.VK_E) {
                setOdotetaanKyllaVaiEiKysymysta(false);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    public void setOdotetaanSuuntaKomentoa(boolean totuusarvo) {
        this.odotetaanSuuntaKomentoa = totuusarvo;
    }
    
    public void setOdotetaanKyllaVaiEiKysymysta(boolean totuusarvo) {
        this.odotaKyllaVaiEiKysymysta = totuusarvo;
    }

    public boolean getOdotetaanSuuntaKomentoa() {
        return this.odotetaanSuuntaKomentoa;
    }
    
    public boolean getOdotetaanKyllaVaiEiKysymysta() {
        return this.odotaKyllaVaiEiKysymysta;
    }
    
    public void setLoppu(boolean totuusarvo) {
        this.loppu = totuusarvo;
    }
}