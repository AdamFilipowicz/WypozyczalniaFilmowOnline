import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/*
 *  Klasa OpcjeS - Opcje sprzedawcy
 *
 *  Klasa pozwala na zmiane hasla, adresu i nazwiska dla zalogowanego konta.
 *  Pozwala dodac i usunac film a takze zmienic jego ilosc, cene oraz opis.
 *  Pozwala wyswietlic wszystkie filmy, wszystkie konta, historie wszystkich wypozyczen,
 *  oraz historie wypozyczen dla podanego klienta.
 *  Pozwala zwrocic wypozyczenie dla danego klienta (zabieg celowy jako potwierdzenie zwrotu od sprzedawcy).
 *  Wyswietla nazwe, nazwisko i adres konta.
 *  Pozwala takze na wylogowanie sie z konta czyli powrot do menu sprzedawcy.
 *
 *  Autor: Adam Filipowicz
 *  Data: 07 stycznia 2018 r.
 */
public class OpcjeS extends javax.swing.JFrame {
    private String nazwaKonta;
    protected static Wypozyczenia wyp;
    protected static MenuSprzedawca sprzedawcaMenu;
    protected static ZmienFilmy zmienFilm;
    protected Wypozyczalnia wypozyczalnia;
    protected static JOptionUserDialog UI = new JOptionUserDialog();
    
    private OpcjeS(){}
    
    public OpcjeS(String nazwaKonta, Wypozyczalnia wypozyczalnia) throws SQLException {
        initComponents();
        this.nazwaKonta = nazwaKonta;
        String nazwisko = wypozyczalnia.getNazwisko(nazwaKonta);
        String adres = wypozyczalnia.getAdres(nazwaKonta);
        String imie = wypozyczalnia.getImie(nazwaKonta);
        nazwaText.setText("Nazwa konta: " + nazwaKonta);
        imieText.setText("Imię: " + imie);
        nazwiskoText.setText("Nazwisko: " + nazwisko);
        adresText.setText("Adres: " + adres);
        this.wypozyczalnia = wypozyczalnia;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        wylogujButton = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        zmienHasloButton = new javax.swing.JButton();
        zmienNazwiskoButton = new javax.swing.JButton();
        zmienAdresButton = new javax.swing.JButton();
        edytujButton = new javax.swing.JButton();
        wypozyczeniaButton = new javax.swing.JButton();
        wyswietlKontaButton = new javax.swing.JButton();
        nazwaText = new javax.swing.JTextField();
        imieText = new javax.swing.JTextField();
        nazwiskoText = new javax.swing.JTextField();
        adresText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Opcje konta");

        wylogujButton.setText("Wyloguj");
        wylogujButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wylogujButtonActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setText("Zalogowano na konto:");

        zmienHasloButton.setText("Zmien haslo");
        zmienHasloButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zmienHasloButtonActionPerformed(evt);
            }
        });

        zmienNazwiskoButton.setText("Zmien nazwisko");
        zmienNazwiskoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zmienNazwiskoButtonActionPerformed(evt);
            }
        });

        zmienAdresButton.setText("Zmien adres");
        zmienAdresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zmienAdresButtonActionPerformed(evt);
            }
        });

        edytujButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        edytujButton.setText("Edytuj filmy");
        edytujButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edytujButtonActionPerformed(evt);
            }
        });

        wypozyczeniaButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        wypozyczeniaButton.setText("Przejrzyj wypozyczenia");
        wypozyczeniaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wypozyczeniaButtonActionPerformed(evt);
            }
        });

        wyswietlKontaButton.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        wyswietlKontaButton.setText("Wyswietl konta");
        wyswietlKontaButton.setToolTipText("");
        wyswietlKontaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wyswietlKontaButtonActionPerformed(evt);
            }
        });

        nazwaText.setEditable(false);
        nazwaText.setText("Nazwa konta:");

        imieText.setEditable(false);
        imieText.setText("Imię:");

        nazwiskoText.setEditable(false);
        nazwiskoText.setText("Nazwisko: ");

        adresText.setEditable(false);
        adresText.setText("Adres:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(adresText, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(wypozyczeniaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(edytujButton, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(zmienAdresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nazwaText, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(nazwiskoText, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(imieText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(19, 19, 19)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(zmienNazwiskoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zmienHasloButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(192, 192, 192)
                                .addComponent(wylogujButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(wyswietlKontaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wylogujButton)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nazwaText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imieText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(nazwiskoText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addComponent(adresText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(zmienHasloButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(zmienNazwiskoButton)
                .addGap(18, 18, 18)
                .addComponent(zmienAdresButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wypozyczeniaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edytujButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wyswietlKontaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metoda po nacisnieciu guzika wylogowuje sie i wraca do menu sprzedawcy.
     * @param evt - nacisniecie guzika powoduje wylogowanie sie i powrot do menu sprzedawcy
     */
    private void wylogujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wylogujButtonActionPerformed
        sprzedawcaMenu = new MenuSprzedawca(wypozyczalnia);
        sprzedawcaMenu.setResizable(false);
        sprzedawcaMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        sprzedawcaMenu.setSize(800,500);
        sprzedawcaMenu.setLocation(500,250);
        sprzedawcaMenu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_wylogujButtonActionPerformed

    /**
     * Metoda po nacisnieciu guzika pozwala zwrocic dane wypozyczenie klienta po podaniu odpowiednich danych.
     * Uwaga! przykladowe daty: 20.5.2017, 30.12.2017.
     * @param evt - nacisniecie guzika powoduje zwrot podanego wypozyczenia
     */
    private void wypozyczeniaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wypozyczeniaButtonActionPerformed
        try {
            wyp = new Wypozyczenia(nazwaKonta, wypozyczalnia);
        } catch (Exception ex) {
            UI.printErrorMessage(ex.getMessage());
            return;
        }
        wyp.setResizable(false);
        wyp.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        wyp.setSize(800,500);
        wyp.setLocation(500,250);
        wyp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_wypozyczeniaButtonActionPerformed
        
    /**
     * Metoda po nacisnieciu guzika pozwala na dodanie filmu.
     * @param evt - nacisniecie guzika powoduje dodanie podanego filmu
     */
    private void edytujButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edytujButtonActionPerformed
        try {
            zmienFilm = new ZmienFilmy(nazwaKonta, wypozyczalnia);
        } catch (Exception ex) {
            UI.printErrorMessage(ex.getMessage());
            return;
        }
        zmienFilm.setResizable(false);
        zmienFilm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        zmienFilm.setSize(800,500);
        zmienFilm.setLocation(500,250);
        zmienFilm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_edytujButtonActionPerformed

    /**
     * Metoda po nacisnieciu guzika wyswietla wszystkie konta.
     * @param evt - nacisniecie guzika powoduje wyswietlenie kont
     */
    private void wyswietlKontaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wyswietlKontaButtonActionPerformed
        UI.printMessage("\nWYSWIETLANIE KONT");
        String lista ="\nLISTA KONT:\n";
        try {
            lista+=wypozyczalnia.listaKont();
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
        UI.printInfoMessage(lista);
    }//GEN-LAST:event_wyswietlKontaButtonActionPerformed

    /**
     * Metoda po nacisnieciu guzika pozwala na zmiane hasla.
     * @param evt - nacisniecie guzika powoduje zmiane hasla
     */
    private void zmienHasloButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zmienHasloButtonActionPerformed
        String noweHaslo;
        String stareHaslo;

        stareHaslo = UI.enterHashString("Podaj stare haslo: ");
        noweHaslo = UI.enterHashString("Podaj nowe haslo: ");
        if (noweHaslo.equals("")){
            UI.printErrorMessage("Haslo nie moze byc puste");
            return;
        }
        try {
            if(wypozyczalnia.sprawdzHaslo(stareHaslo, nazwaKonta)){
                try {
                    wypozyczalnia.setHaslo(stareHaslo, noweHaslo, nazwaKonta);
                } catch (Exception ex) {
                    UI.printErrorMessage(ex.getMessage());
                    return;
                }
                UI.printInfoMessage("\nHaslo zostalo zmienione");
                return;
            }
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
        UI.printErrorMessage("Podano bledne stare haslo!");
    }//GEN-LAST:event_zmienHasloButtonActionPerformed

    /**
     * Metoda po nacisnieciu guzika pozwala na zmiane nazwiska.
     * @param evt - nacisniecie guzika powoduje zmiane nazwiska
     */
    private void zmienNazwiskoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zmienNazwiskoButtonActionPerformed
        String noweNazwisko;

        UI.printMessage("\nZMIANA NAZWISKA WLASCICIELA KONTA");
        noweNazwisko = UI.enterString("Podaj nazwisko: ");
        if (noweNazwisko.equals("")){
            UI.printErrorMessage("Nazwisko nie moze byc puste");
            return;
        }
        UI.printInfoMessage("\nNazwisko zostalo zmienione");
        try {
            wypozyczalnia.setNazwisko(noweNazwisko, nazwaKonta);
            nazwiskoText.setText("Nazwisko: "+wypozyczalnia.getNazwisko(nazwaKonta));
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
    }//GEN-LAST:event_zmienNazwiskoButtonActionPerformed

    /**
     * Metoda po nacisnieciu guzika pozwala na zmiane adresu.
     * @param evt - nacisniecie guzika powoduje zmiane adresu
     */
    private void zmienAdresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zmienAdresButtonActionPerformed
        String noweWojewodztwo, noweMiasto, nowaUlica; 
        int nowyNr1, nowyNr2;

        UI.printMessage("\nZMIANA ADRESU WLASCICIELA KONTA");
        noweWojewodztwo = UI.enterString("Podaj wojewodztwo: ");
        noweMiasto = UI.enterString("Podaj miasto: ");
        nowaUlica = UI.enterString("Podaj ulicę: ");
        nowyNr1 = UI.enterInt("Podaj numer domu: ");
        nowyNr2 = UI.enterInt("Podaj numer mieszkania: ");
        try {
            wypozyczalnia.setAdres(noweWojewodztwo, noweMiasto, nowaUlica, nowyNr1, nowyNr2, nazwaKonta);
            adresText.setText("Adres: "+wypozyczalnia.getAdres(nazwaKonta));
        } catch (Exception e) {
            UI.printErrorMessage(e.getMessage());
            return;
        }
        UI.printInfoMessage("\nAdres zostal zmieniony");
    }//GEN-LAST:event_zmienAdresButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpcjeS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OpcjeS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField adresText;
    private javax.swing.JButton edytujButton;
    private javax.swing.JTextField imieText;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField nazwaText;
    private javax.swing.JTextField nazwiskoText;
    private javax.swing.JButton wylogujButton;
    private javax.swing.JButton wypozyczeniaButton;
    private javax.swing.JButton wyswietlKontaButton;
    private javax.swing.JButton zmienAdresButton;
    private javax.swing.JButton zmienHasloButton;
    private javax.swing.JButton zmienNazwiskoButton;
    // End of variables declaration//GEN-END:variables
}