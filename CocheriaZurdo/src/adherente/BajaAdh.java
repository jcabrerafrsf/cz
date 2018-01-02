/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adherente;

import cocheriazurdo.PantallaPrincipal;
import cocheriazurdo.conectar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import socio.ConsultaSocios;
import tarifa.Tarifas;

/**
 *
 * @author juani
 */
public class BajaAdh extends javax.swing.JFrame {

    private TableRowSorter trsFiltro;
    
    public BajaAdh() {
        initComponents();
        this.setVisible(true);
        setIconImage(new ImageIcon(getClass().getResource("/recursos/logocz.png")).getImage());
        setLocationRelativeTo(null);
        mostrarDatos();
    }

    void mostrarDatos(){
        
        DefaultTableModel modelo = new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
        }
        };
        modelo.addColumn("N° ADHERENTE");
        modelo.addColumn("N° SOCIO");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DNI");
        modelo.addColumn("COBERTURA");
        modelo.addColumn("ESTADO");
        tbsocios.setModel(modelo);
        String datos[] = new String [9];
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nro_adherente, nro_socio, apellido, nombre, dni, fecha_cobertura, estado FROM bdcocheriazurdo.adherentes WHERE estado!='BAJA'");
            
            while(rs.next()){
                datos[0] = rs.getString("nro_adherente");
                datos[1] = rs.getString("nro_socio");         
                datos[2] = rs.getString("apellido");
                datos[3] = rs.getString("nombre");
                datos[4] = rs.getString("dni");
                datos[7] = rs.getString("fecha_cobertura");
                datos[8] = rs.getString("estado");
                modelo.addRow(datos);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultaSocios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void filtro() {
        int columnaABuscar = 0;
        if (comboFiltro.getSelectedItem() == "Apellido") {
            columnaABuscar = 1;
        }
        if (comboFiltro.getSelectedItem() == "Nombre") {
            columnaABuscar = 2;
        }
        if (comboFiltro.getSelectedItem() == "DNI") {
            columnaABuscar = 3;
        }
        if (comboFiltro.getSelectedItem() == "N° de Socio") {
            columnaABuscar = 0;
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbsocios = new javax.swing.JTable();
        txtFiltro = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        comboFiltro = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jadherente = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btEXIT = new javax.swing.JLabel();
        btMINIMIZAR1 = new javax.swing.JLabel();
        btBACK = new javax.swing.JLabel();
        jCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(55, 64, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white)));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BAJA DE ADHERENTE");

        tbsocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbsocios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbsocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbsociosMouseClicked(evt);
            }
        });
        tbsocios.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tbsociosInputMethodTextChanged(evt);
            }
        });
        tbsocios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbsociosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbsocios);

        txtFiltro.setBackground(new java.awt.Color(55, 64, 70));
        txtFiltro.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        txtFiltro.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltro.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Buscar por:");

        comboFiltro.setBackground(new java.awt.Color(55, 64, 70));
        comboFiltro.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        comboFiltro.setForeground(new java.awt.Color(255, 255, 255));
        comboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apellido", "Nombre", "DNI", "N° de Socio" }));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingrese el N° de Adherente a dar de baja:");

        jadherente.setBackground(new java.awt.Color(55, 64, 70));
        jadherente.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jadherente.setForeground(new java.awt.Color(255, 255, 255));
        jadherente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jadherenteActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 153, 153));
        jButton2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jButton2.setText("DAR DE BAJA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/bajauser.png"))); // NOI18N

        btEXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/close-circular-button-of-a-cross (1).png"))); // NOI18N
        btEXIT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btEXITMouseClicked(evt);
            }
        });

        btMINIMIZAR1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/minus-sign-in-a-circle (1).png"))); // NOI18N
        btMINIMIZAR1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btMINIMIZAR1MouseClicked(evt);
            }
        });

        btBACK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/back-arrow-circular-symbol (1).png"))); // NOI18N
        btBACK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btBACKMouseClicked(evt);
            }
        });

        jCancelar.setBackground(new java.awt.Color(255, 153, 153));
        jCancelar.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jCancelar.setText("CANCELAR");
        jCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFiltro))
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jadherente, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btBACK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btMINIMIZAR1)
                        .addGap(6, 6, 6)
                        .addComponent(btEXIT)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel2))
                    .addComponent(btEXIT)
                    .addComponent(btBACK)
                    .addComponent(btMINIMIZAR1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jadherente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbsociosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbsociosMouseClicked
        int indice = tbsocios.getSelectedRow();
        indice++;
        jadherente.setText(Integer.toString(indice));
    }//GEN-LAST:event_tbsociosMouseClicked

    private void tbsociosInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tbsociosInputMethodTextChanged
        int indice = tbsocios.getSelectedRow();
        indice++;
        jadherente.setText(Integer.toString(indice));
    }//GEN-LAST:event_tbsociosInputMethodTextChanged

    private void tbsociosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbsociosKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_UP){
            int indice = tbsocios.getSelectedRow();
            if (indice==0)
            return ;
            else
            indice--;
            jadherente.setText(Integer.toString(indice));
            System.out.println(indice);
        }else{
            int indice = tbsocios.getSelectedRow(), cantsocios=0;
            if (evt.getKeyChar() == KeyEvent.VK_DOWN){
                try{
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT MAX(nro_socio) FROM bdcocheriazurdo.socios");
                    rs.last();
                    cantsocios = rs.getInt("MAX(nro_socio)");
                }catch (SQLException ex) {
                    ex.getMessage();
                }
                if (indice==cantsocios)
                return ;
                else
                indice++;
                jadherente.setText(Integer.toString(indice));
                System.out.println(indice);
            }
        }
    }//GEN-LAST:event_tbsociosKeyPressed

    private void txtFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroKeyTyped
        txtFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                String cadena = (txtFiltro.getText().toUpperCase());
                txtFiltro.setText(cadena);
                repaint();
                filtro();
            }
        });
        trsFiltro = new TableRowSorter(tbsocios.getModel());
        tbsocios.setRowSorter(trsFiltro);
    }//GEN-LAST:event_txtFiltroKeyTyped

    private void jadherenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jadherenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jadherenteActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jadherente.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Olvidó de colocar un N° de socio");
            jadherente.requestFocus();
        }
        else{

            try{
                DefaultTableModel modelo = new DefaultTableModel();
                tbsocios.setModel(modelo);

                String datos[] = new String [1];

                int numerodeadh = Integer.parseInt(this.jadherente.getText());
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT estado FROM bdcocheriazurdo.adherentes WHERE nro_adherente="+numerodeadh);

                while(rs.next()){
                    datos[0] = rs.getString("estado");
                }

                if(datos[0].equals("BAJA")){
                    JOptionPane.showMessageDialog(null, "El adherente ya está dado de baja");
                    
                    BajaAdh BU2 = new BajaAdh();
                    this.dispose();
                    
                }else{
                    
                    PreparedStatement pst = cn.prepareStatement("UPDATE adherentes SET estado='BAJA' WHERE nro_adherente=?;");
                    pst.setString(1, jadherente.getText());
                    pst.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Adherente dado de baja con éxito!");
                    
                    OpcionesAdherente OA = new OpcionesAdherente();
                    this.dispose();
                    
                }

            }catch(NumberFormatException | SQLException e){
                System.out.println(e.getMessage());
            }

        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void btEXITMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEXITMouseClicked
        int seleccion = JOptionPane.showConfirmDialog(null, "Realmente desea salir del sistema?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(seleccion==0){
            System.exit(0);
        }else{
        }
    }//GEN-LAST:event_btEXITMouseClicked

    private void btMINIMIZAR1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMINIMIZAR1MouseClicked
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btMINIMIZAR1MouseClicked

    private void btBACKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btBACKMouseClicked
        OpcionesAdherente OS = new OpcionesAdherente();
        this.dispose();
        OS.setVisible(true);

    }//GEN-LAST:event_btBACKMouseClicked

    private void jCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelarActionPerformed
        int seleccion = JOptionPane.showConfirmDialog(null, "Realmente desea volver atrás?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(seleccion==0){
            OpcionesAdherente OS = new OpcionesAdherente();
            this.dispose();
            OS.setVisible(true);
        }else{
        } 
    }//GEN-LAST:event_jCancelarActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BajaAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BajaAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BajaAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BajaAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BajaAdh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btBACK;
    private javax.swing.JLabel btEXIT;
    private javax.swing.JLabel btMINIMIZAR1;
    private javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jadherente;
    private javax.swing.JTable tbsocios;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
    conectar cc = new conectar();
    Connection cn = cc.ConexionMySql();
}
