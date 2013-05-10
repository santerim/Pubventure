
package pubventure.gui;

/**
 *
 * @author Santeri
 */
public class Piirtaja extends javax.swing.JFrame {

    public Piirtaja() {
        initComponents();
    }

                       
    private void initComponents() {

        kenttaPaneeli = new javax.swing.JPanel();
        kenttaLabel = new javax.swing.JLabel();
        tietoPaneeli = new javax.swing.JPanel();
        tietoLabel = new javax.swing.JLabel();
        viestiPaneeli = new javax.swing.JPanel();
        viestiLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pubventure 1.0 - santerim@cs.helsinki.fi");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        kenttaPaneeli.setBackground(new java.awt.Color(0, 0, 0));

        kenttaLabel.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N

        javax.swing.GroupLayout kenttaPaneeliLayout = new javax.swing.GroupLayout(kenttaPaneeli);
        kenttaPaneeli.setLayout(kenttaPaneeliLayout);
        kenttaPaneeliLayout.setHorizontalGroup(
            kenttaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kenttaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 480, Short.MAX_VALUE)
        );
        kenttaPaneeliLayout.setVerticalGroup(
            kenttaPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(kenttaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, Short.MAX_VALUE)
        );

        tietoPaneeli.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout tietoPaneeliLayout = new javax.swing.GroupLayout(tietoPaneeli);
        tietoPaneeli.setLayout(tietoPaneeliLayout);
        tietoPaneeliLayout.setHorizontalGroup(
            tietoPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tietoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        tietoPaneeliLayout.setVerticalGroup(
            tietoPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tietoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        viestiPaneeli.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout viestiPaneeliLayout = new javax.swing.GroupLayout(viestiPaneeli);
        viestiPaneeli.setLayout(viestiPaneeliLayout);
        viestiPaneeliLayout.setHorizontalGroup(
            viestiPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viestiLabel, javax.swing.GroupLayout.Alignment.LEADING,
            javax.swing.GroupLayout.PREFERRED_SIZE,
            500,
            Short.MAX_VALUE)
        );
        viestiPaneeliLayout.setVerticalGroup(
            viestiPaneeliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(viestiLabel, javax.swing.GroupLayout.Alignment.LEADING,
            javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(kenttaPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tietoPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(viestiPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kenttaPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tietoPaneeli, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viestiPaneeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    
    
    // Variables declaration - do not modify                     
    public javax.swing.JLabel kenttaLabel;
    public javax.swing.JPanel kenttaPaneeli;
    public javax.swing.JLabel tietoLabel;
    public javax.swing.JPanel tietoPaneeli;
    public javax.swing.JLabel viestiLabel;
    public javax.swing.JPanel viestiPaneeli;
}
