/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adherente;

import cocheriazurdo.conectar;
import socio.OpcionesSocio;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import socio.ConsultaSociosParaEditar;

/**
 *
 * @author juani
 */
public class ConsultaSociosParaAddAdh extends javax.swing.JFrame {

    private TableRowSorter trsFiltro;
    
    public ConsultaSociosParaAddAdh() {
        initComponents();
        this.setVisible(true);
        setIconImage(new ImageIcon(getClass().getResource("/recursos/logocz.png")).getImage());
        mostrarDatos();
    }

    public void filtro() {
        int columnaABuscar = 0;
        if (comboFiltro.getSelectedItem() == "Apellido") {
            columnaABuscar = 2;
        }
        if (comboFiltro.getSelectedItem() == "Nombre") {
            columnaABuscar = 3;
        }
        if (comboFiltro.getSelectedItem() == "DNI") {
            columnaABuscar = 4;
        }
        if (comboFiltro.getSelectedItem() == "N° de socio") {
            columnaABuscar = 0;
        }
        if (comboFiltro.getSelectedItem() == "Estado") {
            columnaABuscar = 8;
        }
        if (comboFiltro.getSelectedItem() == "N° de zona") {
            columnaABuscar = 1;
        }
        trsFiltro.setRowFilter(RowFilter.regexFilter(txtFiltro.getText(), columnaABuscar));
    }
    
       
    
    void mostrarDatos(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("N° SOCIO");
        modelo.addColumn("N° ZONA");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DNI");
        modelo.addColumn("LOCALIDAD");
        modelo.addColumn("PLAN");
        modelo.addColumn("COBERTURA");
        modelo.addColumn("ESTADO");
        tbsocios.setModel(modelo);
        String datos[] = new String [20];
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM bdcocheriazurdo.socios WHERE 1 ORDER BY 1");
            
            while(rs.next()){
                datos[0] = rs.getString("nro_socio");
                datos[1] = rs.getString("nro_zona");         
                datos[2] = rs.getString("apellido");
                datos[3] = rs.getString("nombre");
                datos[4] = rs.getString("dni");
                //datos[5] = rs.getString("direccion");
                //datos[6] = rs.getString("telefono");
                //datos[7] = rs.getString("sexo");
                datos[5] = rs.getString("localidad");
                datos[6] = rs.getString("plan");
                //datos[10] = rs.getString("codigo_tarifa");         
                //datos[11] = rs.getString("codigo_obrasocial");
                //datos[12] = rs.getString("fecha_nac");
                //datos[13] = rs.getString("fecha_alta");
                datos[7] = rs.getString("fecha_cobertura");
                //datos[15] = rs.getString("edad");
                //datos[16] = rs.getString("nro_adherentes");
                //datos[17] = rs.getString("credito");
                //datos[18] = rs.getString("tipo_socio");
                datos[8] = rs.getString("estado");
                modelo.addRow(datos);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaSociosParaEditar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbsocios = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        comboFiltro = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        btEXIT = new javax.swing.JLabel();
        btMINIMIZAR1 = new javax.swing.JLabel();
        btBACK = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jsocio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(55, 64, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white)));

        tbsocios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbsocios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbsociosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbsocios);

        jButton2.setBackground(new java.awt.Color(153, 255, 153));
        jButton2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jButton2.setText("ACEPTAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        comboFiltro.setBackground(new java.awt.Color(55, 64, 70));
        comboFiltro.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        comboFiltro.setForeground(new java.awt.Color(255, 255, 255));
        comboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apellido", "Nombre", "DNI", "N° de socio", "Estado", "N° de zona" }));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Buscar por:");

        txtFiltro.setBackground(new java.awt.Color(55, 64, 70));
        txtFiltro.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        txtFiltro.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltro.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFiltroKeyTyped(evt);
            }
        });

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

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CONSULTA DE SOCIO PARA AGREGAR ADHERENTE");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/consultauser.png"))); // NOI18N

        jsocio.setBackground(new java.awt.Color(55, 64, 70));
        jsocio.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jsocio.setForeground(new java.awt.Color(255, 255, 255));
        jsocio.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jsocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jsocioKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nro. de socio:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFiltro))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jsocio, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                        .addComponent(btBACK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btMINIMIZAR1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEXIT))
                    .addComponent(jSeparator3))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btEXIT)
                    .addComponent(btMINIMIZAR1)
                    .addComponent(btBACK)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jsocio)
                        .addComponent(jLabel9))
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbsociosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbsociosMouseClicked
        int indice = tbsocios.getSelectedRow();
        indice++;
        jsocio.setText(Integer.toString(indice));
    }//GEN-LAST:event_tbsociosMouseClicked

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
        OpcionesSocio OS = new OpcionesSocio();
        this.dispose();
    }//GEN-LAST:event_btBACKMouseClicked

    private void jsocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jsocioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jsocioKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int nsocio = Integer.parseInt(this.jsocio.getText());
        
        AddAdherente AA = new AddAdherente(nsocio);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ConsultaSociosParaAddAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaSociosParaAddAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaSociosParaAddAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaSociosParaAddAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaSociosParaAddAdh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btBACK;
    private javax.swing.JLabel btEXIT;
    private javax.swing.JLabel btMINIMIZAR1;
    private javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jsocio;
    private javax.swing.JTable tbsocios;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
    conectar cc = new conectar();
    Connection cn = cc.ConexionMySql(); 
    }
