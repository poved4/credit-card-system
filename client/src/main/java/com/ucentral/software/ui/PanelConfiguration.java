package com.ucentral.software.ui;

import com.ucentral.software.configuration.Connection;
import com.ucentral.software.service.ManagerService;

public class PanelConfiguration extends javax.swing.JPanel {

    private final ManagerService service;

    /**
     * Creates new form Configuration
     */
    public PanelConfiguration(ManagerService service) {
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
        titleConfigurationLabel = new javax.swing.JLabel();
        hostLabel = new javax.swing.JLabel();
        hostField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        timeOutLabel = new javax.swing.JLabel();
        timeOutField = new javax.swing.JTextField();
        retriesLabel = new javax.swing.JLabel();
        retriesField = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(600, 400));

        titleConfigurationLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleConfigurationLabel.setForeground(new java.awt.Color(22, 22, 22));
        titleConfigurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleConfigurationLabel.setText("configuracion del servidor");

        hostLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        hostLabel.setForeground(new java.awt.Color(22, 22, 22));
        hostLabel.setText("host");

        hostField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        hostField.setForeground(new java.awt.Color(22, 22, 22));

        portLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        portLabel.setForeground(new java.awt.Color(22, 22, 22));
        portLabel.setText("port");

        portField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        portField.setForeground(new java.awt.Color(22, 22, 22));

        timeOutLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        timeOutLabel.setForeground(new java.awt.Color(22, 22, 22));
        timeOutLabel.setText("time out");

        timeOutField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        timeOutField.setForeground(new java.awt.Color(22, 22, 22));

        retriesLabel.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        retriesLabel.setForeground(new java.awt.Color(22, 22, 22));
        retriesLabel.setText("retries");

        retriesField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        retriesField.setForeground(new java.awt.Color(22, 22, 22));

        connectButton.setText("conectarse");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("desconectarse");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleConfigurationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(timeOutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeOutField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(connectButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(retriesField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(retriesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(titleConfigurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(hostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hostField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(timeOutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeOutField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(retriesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(retriesField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(112, Short.MAX_VALUE))
        );

        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 400));
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        connect();
    }//GEN-LAST:event_connectButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        disconnect();
    }//GEN-LAST:event_disconnectButtonActionPerformed

    public void init() {

        if (!hostField.isEnabled()) {
            enableFields(Boolean.FALSE);
            connectButton.setEnabled(Boolean.FALSE);
            disconnectButton.setEnabled(Boolean.TRUE);
            return;
        }
        
        loadFields();
        enableFields(Boolean.TRUE);
        connectButton.setEnabled(Boolean.TRUE);
        disconnectButton.setEnabled(Boolean.FALSE);

    }

    private void enableFields(Boolean enabled) {
        hostField.setEnabled(enabled);
        portField.setEnabled(enabled);
        timeOutField.setEnabled(enabled);
        retriesField.setEnabled(enabled);
    }

    private void loadFields() {
        timeOutField.setText(String.valueOf(Connection.TIME_OUT));
        retriesField.setText(String.valueOf(Connection.RETIES));
        portField.setText(String.valueOf(Connection.PORT));
        hostField.setText(Connection.HOST);
    }

    private void saveFields() {
        Connection.TIME_OUT = Long.valueOf(timeOutField.getText());
        Connection.RETIES = Short.valueOf(retriesField.getText());
        Connection.PORT = Integer.valueOf(portField.getText());
        Connection.HOST = hostField.getText();
    }

    private void connect() {
        
        saveFields();
        service.connect();
        enableFields(Boolean.FALSE);
        connectButton.setEnabled(Boolean.FALSE);
        disconnectButton.setEnabled(Boolean.TRUE);

    }

    private void disconnect() {

        service.disconnect();
        enableFields(Boolean.TRUE);
        connectButton.setEnabled(Boolean.TRUE);
        disconnectButton.setEnabled(Boolean.FALSE);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JTextField hostField;
    private javax.swing.JLabel hostLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField retriesField;
    private javax.swing.JLabel retriesLabel;
    private javax.swing.JTextField timeOutField;
    private javax.swing.JLabel timeOutLabel;
    private javax.swing.JLabel titleConfigurationLabel;
    // End of variables declaration//GEN-END:variables

}
