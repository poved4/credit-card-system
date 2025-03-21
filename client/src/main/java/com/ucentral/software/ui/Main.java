package com.ucentral.software.ui;

import com.ucentral.software.service.ManagerService;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Main extends javax.swing.JFrame {

  private PanelDashboard panelDashboard;
  private PanelLogin panelLogin;

  private final ManagerService service;

  public Main(ManagerService service) {
    this.service = service;
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated
  // Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    content = new javax.swing.JPanel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
    setPreferredSize(new java.awt.Dimension(600, 400));
    setResizable(false);
    setSize(new java.awt.Dimension(600, 300));

    content.setBackground(new java.awt.Color(255, 255, 255));
    content.setPreferredSize(new java.awt.Dimension(650, 400));

    javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
    content.setLayout(contentLayout);
    contentLayout.setHorizontalGroup(
        contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE));
    contentLayout.setVerticalGroup(
        contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, 600,
                javax.swing.GroupLayout.PREFERRED_SIZE));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

    pack();
    setLocationRelativeTo(null);
  }// </editor-fold>//GEN-END:initComponents

  public void init() {
    panelDashboard = new PanelDashboard(this, service);
    panelLogin = new PanelLogin(this, service);
    this.setVisible(true);
    showLoginPanel();
  }

  private void showPanel(JPanel panel) {
    panel.setSize(600, 400);
    panel.setLocation(0, 0);
    content.removeAll();
    content.add(panel, BorderLayout.CENTER);
    content.revalidate();
    content.repaint();
  }

  public void showLoginPanel() {
    showPanel(panelLogin);
    panelLogin.init();
  }

  public void showDashboardPanel() {
    showPanel(panelDashboard);
    panelDashboard.init();
    service.account();
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel content;
  // End of variables declaration//GEN-END:variables

}
