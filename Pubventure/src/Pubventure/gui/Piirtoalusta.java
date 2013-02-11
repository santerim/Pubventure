package Pubventure.gui;

import Pubventure.Sijainti;
import Pubventure.ihmiset.Inehmo;
import Pubventure.ymparisto.Pubi;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * Luokka piirtää pelikentän tarjoamillaan metodeilla
 */
public class Piirtoalusta {

    private JLabel kentta;
    private JLabel tiedot;
    private JLabel viesti;
    private Pubi pubi;
    private ArrayList<Inehmo> inehmot;
    private StringBuilder sb;
    private Font fontti = new Font("monospaced", Font.PLAIN, 12);
    
    public Piirtoalusta(Pubi pubi, ArrayList<Inehmo> inehmot) {
        //super.setBackground(Color.WHITE);
        this.pubi = pubi;
        this.inehmot = inehmot;
        this.sb = new StringBuilder("");
    }

    public void setPiirtoalusta(JLabel kentta, JLabel tiedot, JLabel viesti) {
        this.kentta = kentta;
        this.tiedot = tiedot;
        this.viesti = viesti;
    }
    
    public void setViestiKentanSisalto(String sisalto) {
        this.viesti.setText(sisalto);
    }
    
    public void setTietoKentanSisalto(String sisalto) {
        this.tiedot.setText(sisalto);
    }
    
    public void piirraAlue() {
        sb.setLength(0);
        sb.append("<html>");
        
        for (int i = 0; i < pubi.getKorkeus(); i++) {
            for (int j = 0; j < pubi.getLeveys(); j++) {
                Sijainti nykSijainti = new Sijainti(j, i);
                if (pubi.tormaako(j, i)) {
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
                        sb.append(pubi.getObjekti(j, i).getUlkonako());
                    }
                } else {
                    sb.append(pubi.getObjekti(j, i).getUlkonako());
                }
                if (j == pubi.getLeveys() - 1) {
                    sb.append("<br>");
                }
            }
        }
        sb.append("</html>");
        this.kentta.setFont(fontti);
        this.kentta.setText(sb.toString());
    }
    
}
