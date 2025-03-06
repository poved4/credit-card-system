package com.ucentral.software.ui;

import com.ucentral.software.service.ManagerService;

public class PanelLogin extends javax.swing.JPanel {

    private final Main container;
    private final ManagerService service;

    /**
     * Creates new form login
     */
    public PanelLogin(Main container, ManagerService service) {
        this.container = container;
        this.service = service;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        ccLabel = new javax.swing.JLabel();
        ccField = new javax.swing.JTextField();
        pinLabel = new javax.swing.JLabel();
        pinField = new javax.swing.JTextField();
        logInButton = new javax.swing.JButton();
        msgLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 400));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(600, 400));

        ccLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        ccLabel.setForeground(new java.awt.Color(22, 22, 22));
        ccLabel.setText("CC");

        ccField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ccField.setForeground(new java.awt.Color(22, 22, 22));

        pinLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        pinLabel.setForeground(new java.awt.Color(22, 22, 22));
        pinLabel.setText("PIN");

        pinField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pinField.setForeground(new java.awt.Color(22, 22, 22));

        logInButton.setBackground(new java.awt.Color(0, 204, 204));
        logInButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        logInButton.setForeground(new java.awt.Color(255, 255, 255));
        logInButton.setText("logIn");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });

        msgLabel.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        msgLabel.setForeground(new java.awt.Color(153, 0, 0));
        msgLabel.setText("hola esto es una prueba");

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap(260, Short.MAX_VALUE)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(ccField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ccLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pinField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(ccLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ccField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pinField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(msgLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logInButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(113, Short.MAX_VALUE))
        );

        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 610, 400));
    }// </editor-fold>//GEN-END:initComponents

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed

        String cc = ccField.getText();
        String pin = pinField.getText();

        if (cc.isBlank() || pin.isBlank()) {
            msgLabel.setText("the fields cannot be empty");
            return;
        }

        service.connect();
        String response = service.logIn(cc, pin);

        if (response.startsWith("ERROR. ")) {
            msgLabel.setText(response.replace("ERROR. ", ""));
            return;
        }

        container.showDashboardPanel();
    }//GEN-LAST:event_logInButtonActionPerformed

    public void init() {
        ccField.setText("");
        pinField.setText("");
        msgLabel.setText("  ");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JTextField ccField;
    private javax.swing.JLabel ccLabel;
    private javax.swing.JButton logInButton;
    private javax.swing.JLabel msgLabel;
    private javax.swing.JTextField pinField;
    private javax.swing.JLabel pinLabel;
    // End of variables declaration//GEN-END:variables

}
