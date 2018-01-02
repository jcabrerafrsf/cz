/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adherente;

import cocheriazurdo.Fecha_Hora;
import cocheriazurdo.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import socio.EditSocio;

/**
 *
 * @author juani
 */
public class EditAdh extends javax.swing.JFrame {

    int numeroadh=0;
    
    public EditAdh() {
        initComponents();
        this.setVisible(true);
        setIconImage(new ImageIcon(getClass().getResource("/recursos/logocz.png")).getImage());
        this.setLocationRelativeTo(null);
    }
    
    public EditAdh(int nroadh) {
        initComponents();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/recursos/logocz.png")).getImage());
        String cadena = "";
        numeroadh = nroadh;
        cadena = String.valueOf(numeroadh);
        jnroadh.setText(cadena);
        
    }

    
    
    public void fechaSistema(){
        
        Fecha_Hora fechaSys = new Fecha_Hora();
        this.jfechaalta.setText(fechaSys.obtenerFechaSystem());
        Date fecha = null;
        Date fecha2 = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        
        try {
        fecha = formatoDelTexto.parse(jfechanac.getText());
        fecha2 = formatoDelTexto.parse(jfechaalta.getText());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }   
        
        if (fecha.after(fecha2)){
            JOptionPane.showMessageDialog(null,"Se colocó una fecha de nacimiento incorrecta");
            jfechanac.setText(null);
            jfechaalta.setText(null);
            return ;
        }
        
        //seteo la edad y la fecha de cobertura
        
        this.jedad.setText(fechaSys.getEdad(fecha));
        this.jfechacobertura.setText(fechaSys.getFechaCobertura(fecha,fecha2));
    }
    
    
    public String getSexo(){
        int varsexo = this.jsexo.getSelectedIndex();
        if(varsexo==0){
            return "MASCULINO";
        }
        else{
            if(varsexo==1){
                return "FEMENINO";
            }
            else{
               if(varsexo==2){
                return "INDEFINIDO";
                }
                else{
                return "NO ESPECIFICADO";
                } 
            }
        }       
    }
    
    
    public String setSexo(){
        int varsexo = this.jsexo.getSelectedIndex();
        if(varsexo==0){
            return "MASCULINO";
        }
        else{
            if(varsexo==1){
                return "FEMENINO";
            }
            else{
               if(varsexo==2){
                return "INDEFINIDO";
                }
                else{
                return "NO ESPECIFICADO";
                } 
            }
        }       
    }    
    
    //LUEGO DE MODIFICAR LOS TEXTFIELD, HACE UN UPDATE
    public void actualizarDatos(){

       try{
           
        int numerodeadh = Integer.parseInt(this.jnroadh.getText());
        int numerodesocio = Integer.parseInt(this.jnrosocio.getText());
           
        PreparedStatement pst = cn.prepareStatement("UPDATE adherentes SET nro_adherente=?, nro_socio=?, apellido=?, nombre=?, dni=?, direccion=?, telefono=?, sexo=?, fecha_nac=?, fecha_alta=?, fecha_cobertura=?, estado=? WHERE nro_adherente="+numerodeadh);

        
        pst.setInt(1,numerodeadh);
        pst.setInt(2, numerodesocio);
        pst.setString(3, this.japellido.getText().toUpperCase());
        pst.setString(4, this.jnombre.getText().toUpperCase());
        pst.setString(5, this.jdni.getText().toUpperCase());
        pst.setString(6, this.jdireccion.getText().toUpperCase());
        pst.setString(7, this.jtelefono.getText().toUpperCase());
        pst.setString(8, getSexo());
        pst.setString(9, this.jfechanac.getText().toUpperCase());
        pst.setString(10, this.jfechaalta.getText().toUpperCase());
        pst.setString(11, this.jfechacobertura.getText().toUpperCase());
        pst.setString(12, this.jedad.getText().toUpperCase());

        pst.executeUpdate();
        pst.close();
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error en la conexion - "+e.getMessage());
        }
       
    }
    
    //SETEA LOS VALORES EN LOS TEXTFIELD
    
    public void cargaDatos(){

       try{
        
        int numerodeadh = Integer.parseInt(this.jnroadh.getText());
        
        //VER SI EL NRO DE SOCIO EXISTE
        
            Statement st1 = cn.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM bdcocheriazurdo.adherentes WHERE nro_adherente="+numerodeadh);
            int existesocio=0;
            
            while(rs1.next()){
                existesocio = rs1.getInt("nro_adherente");
            }
            rs1.close();
            
            if(existesocio==0){
                JOptionPane.showMessageDialog(null,"El N° de Adherente no existe en la Base de Datos. Consulte desde la opcion 'Consultar'");
                EditAdh EUS = new EditAdh();
                this.dispose();           
                EUS.setVisible(true);
            }
            else{
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM bdcocheriazurdo.adherentes WHERE nro_adherente="+numerodeadh);

                String datos[] = new String [20];        
                while(rs.next()){
                        datos[0] = rs.getString("nro_adherente");
                        datos[1] = rs.getString("nro_socio");         
                        datos[2] = rs.getString("apellido");
                        datos[3] = rs.getString("nombre");
                        datos[4] = rs.getString("dni");
                        datos[5] = rs.getString("direccion");
                        datos[6] = rs.getString("telefono");
                        datos[7] = rs.getString("sexo");
                        datos[8] = rs.getString("fecha_nac");
                        datos[9] = rs.getString("fecha_alta");
                        datos[10] = rs.getString("fecha_cobertura");
                        datos[11] = rs.getString("estado");
                }
                rs.close();   
                
                jfechacobertura.setText(datos[10]);
                japellido.setText(datos[2]);
                jnombre.setText(datos[3]);
                jdni.setText(datos[4]);
                jnrosocio.setText(datos[1]);
                if(datos[7]=="MASCULINO"){
                   jsexo.setSelectedItem("M");
                }
                else{
                    if(datos[7]=="FEMENINO"){
                      jsexo.setSelectedItem("F"); 
                    }
                    else{
                        jsexo.setSelectedItem("I");
                    }
                }
                
                jfechanac.setText(datos[8]);
                
                jfechaalta.setText(datos[9]);
                jdireccion.setText(datos[5]);
                jtelefono.setText(datos[6]);
                jedad.setText(datos[15]);
            }

        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error en la conexion - "+e.getMessage());
        }
       
    }
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btEXIT = new javax.swing.JLabel();
        btMINIMIZAR = new javax.swing.JLabel();
        btBACK = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jnroadh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jnombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        japellido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jdni = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jsexo = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jfechanac = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jdireccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jtelefono = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jedad = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jfechaalta = new javax.swing.JFormattedTextField();
        jLabel21 = new javax.swing.JLabel();
        jfechacobertura = new javax.swing.JFormattedTextField();
        jSeparator7 = new javax.swing.JSeparator();
        btmodificar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jnrosocio = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(55, 64, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white)));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/edituser.png"))); // NOI18N

        btEXIT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/close-circular-button-of-a-cross (1).png"))); // NOI18N
        btEXIT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btEXITMouseClicked(evt);
            }
        });

        btMINIMIZAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/minus-sign-in-a-circle (1).png"))); // NOI18N
        btMINIMIZAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btMINIMIZARMouseClicked(evt);
            }
        });

        btBACK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/back-arrow-circular-symbol (1).png"))); // NOI18N
        btBACK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btBACKMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 240, 240));
        jLabel2.setText("MODIFICAR ADHERENTE");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("N° de adherente:");

        jnroadh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jnroadh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jnroadhFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jnroadhFocusLost(evt);
            }
        });
        jnroadh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jnroadhActionPerformed(evt);
            }
        });
        jnroadh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jnroadhKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("Consultar Adherentes:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(240, 240, 240));
        jLabel5.setText("NOMBRE:");

        jnombre.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jnombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jnombreFocusGained(evt);
            }
        });
        jnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jnombreKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("APELLIDO:");

        japellido.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        japellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                japellidoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setText("DNI:");

        jdni.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jdni.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jdni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jdniKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(240, 240, 240));
        jLabel3.setText("SEXO: ");

        jsexo.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jsexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F", "I" }));

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(240, 240, 240));
        jLabel9.setText("FECHA DE NACIMIENTO:");

        jfechanac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jfechanac.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jfechanac.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jfechanacFocusLost(evt);
            }
        });
        jfechanac.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jfechanacKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(240, 240, 240));
        jLabel11.setText("DIRECCION:");

        jdireccion.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jdireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jdireccionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(240, 240, 240));
        jLabel12.setText("TELEFONO:");

        jtelefono.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jtelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtelefonoKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(240, 240, 240));
        jLabel18.setText("EDAD:");

        jedad.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(240, 240, 240));
        jLabel20.setText("FECHA ALTA:");

        jfechaalta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jfechaalta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(240, 240, 240));
        jLabel21.setText("FECHA COBERTURA:");

        jfechacobertura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));
        jfechacobertura.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        btmodificar.setBackground(new java.awt.Color(255, 153, 51));
        btmodificar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btmodificar.setText("MODIFICAR");
        btmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmodificarActionPerformed(evt);
            }
        });
        btmodificar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btmodificarKeyPressed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/busqueda24.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setText("N° de socio:");

        jnrosocio.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jnrosocio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jnrosocioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jnrosocioFocusLost(evt);
            }
        });
        jnrosocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jnrosocioActionPerformed(evt);
            }
        });
        jnrosocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jnrosocioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator5)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jnroadh, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btBACK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btMINIMIZAR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEXIT))
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btmodificar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jfechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jfechacobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(160, 160, 160))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(japellido, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdni, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jsexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jfechanac, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jnrosocio, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btBACK)
                    .addComponent(btMINIMIZAR)
                    .addComponent(btEXIT)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(19, 19, 19)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jnroadh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jnrosocio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(japellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jsexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jfechanac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jtelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jfechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jfechacobertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btmodificar)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ConsultaAdhParaEditar CS = new ConsultaAdhParaEditar();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btmodificarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btmodificarKeyPressed

        actualizarDatos();
    }//GEN-LAST:event_btmodificarKeyPressed

    private void btmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmodificarActionPerformed
        actualizarDatos();

        int numerodeadh1 = Integer.parseInt(this.jnroadh.getText());

        JOptionPane.showMessageDialog(this, "El socio "+jnombre.getText()+" "+japellido.getText()+" fue actualizado con éxito. Nro. de adherente: "+ numerodeadh1);

        OpcionesAdherente OS = new OpcionesAdherente();
        this.dispose();
        OS.setVisible(true);
    }//GEN-LAST:event_btmodificarActionPerformed

    private void jtelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtelefonoKeyTyped
        /* if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
            int cont = jtelefono.getText().length();
            if (cont>=10){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jtelefonoKeyTyped

    private void jdireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdireccionKeyTyped
        /* if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || ((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
            int cont = jdireccion.getText().length();
            if (cont>=30){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jdireccionKeyTyped

    private void jfechanacKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jfechanacKeyTyped
        /* if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SLASH)){
            int cont = jfechanac.getText().length();
            if (evt.getKeyChar() == KeyEvent.VK_SLASH){
                if (cont>=3)
                cont=5;
            }
            if (cont>=10){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jfechanacKeyTyped

    private void jfechanacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfechanacFocusLost
        fechaSistema();
    }//GEN-LAST:event_jfechanacFocusLost

    private void jdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdniKeyTyped
        /*if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
            int cont = jdni.getText().length();
            if (cont>=8){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jdniKeyTyped

    private void japellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_japellidoKeyTyped
        /* if(((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
            int cont = japellido.getText().length();
            if (cont>=30){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_japellidoKeyTyped

    private void jnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnombreKeyTyped
        /*if(((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
            int cont = jnombre.getText().length();
            if (cont>=30){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jnombreKeyTyped

    private void jnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnombreFocusGained

    }//GEN-LAST:event_jnombreFocusGained

    private void jnroadhKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnroadhKeyTyped
        /*        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
            int cont = jdni.getText().length();
            if (cont>=8){
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)
                cont--;
                else
                evt.consume();
            }
            else{
                if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE){
                    if (cont>0)
                    cont--;
                }
                else
                cont++;
            }
        }
        else
        evt.consume();*/
    }//GEN-LAST:event_jnroadhKeyTyped

    private void jnroadhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jnroadhActionPerformed

    }//GEN-LAST:event_jnroadhActionPerformed

    private void jnroadhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnroadhFocusLost
        if(jnroadh.getText().isEmpty()){
            jnroadh.requestFocus();
        }
        else{
            cargaDatos();
        }
    }//GEN-LAST:event_jnroadhFocusLost

    private void jnroadhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnroadhFocusGained
        if(jnroadh.getText().isEmpty()){
            jnroadh.requestFocus();
        }
        else{
            cargaDatos();
        }
    }//GEN-LAST:event_jnroadhFocusGained

    private void btBACKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btBACKMouseClicked
        if (jnroadh.getText().isEmpty() && japellido.getText().isEmpty() && jdni.getText().isEmpty() && jtelefono.getText().isEmpty() &&
            jdireccion.getText().isEmpty() && jfechanac.getText().isEmpty()
        ){
            OpcionesAdherente OS = new OpcionesAdherente();
            this.dispose();
            OS.setVisible(true);
        }else{
            int seleccion = JOptionPane.showConfirmDialog(null, "Existen campos con datos cargados. Desea perder los datos y regresar?", "Confirmar regreso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(seleccion == 0){
                OpcionesAdherente OS = new OpcionesAdherente();
                this.dispose();
                OS.setVisible(true);
            }
            else{

            }

        }
    }//GEN-LAST:event_btBACKMouseClicked

    private void btMINIMIZARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMINIMIZARMouseClicked
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btMINIMIZARMouseClicked

    private void btEXITMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEXITMouseClicked
        if (jnroadh.getText().isEmpty() && japellido.getText().isEmpty() && jdni.getText().isEmpty() && jtelefono.getText().isEmpty() &&
            jdireccion.getText().isEmpty() && jfechanac.getText().isEmpty()
        ){
            System.exit(0);
        }else{
            int seleccion = JOptionPane.showConfirmDialog(null, "Existen campos con datos cargados. Desea perder los datos y regresar?", "Confirmar regreso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if(seleccion == 0){
                System.exit(0);
            }
            else{

            }

        }
    }//GEN-LAST:event_btEXITMouseClicked

    private void jnrosocioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnrosocioFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jnrosocioFocusGained

    private void jnrosocioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnrosocioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jnrosocioFocusLost

    private void jnrosocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jnrosocioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jnrosocioActionPerformed

    private void jnrosocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnrosocioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jnrosocioKeyTyped

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
            java.util.logging.Logger.getLogger(EditAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditAdh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditAdh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btBACK;
    private javax.swing.JLabel btEXIT;
    private javax.swing.JLabel btMINIMIZAR;
    private javax.swing.JButton btmodificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTextField japellido;
    private javax.swing.JTextField jdireccion;
    private javax.swing.JFormattedTextField jdni;
    private javax.swing.JTextField jedad;
    private javax.swing.JFormattedTextField jfechaalta;
    private javax.swing.JFormattedTextField jfechacobertura;
    private javax.swing.JFormattedTextField jfechanac;
    private javax.swing.JTextField jnombre;
    private javax.swing.JTextField jnroadh;
    private javax.swing.JTextField jnrosocio;
    private javax.swing.JComboBox<String> jsexo;
    private javax.swing.JTextField jtelefono;
    // End of variables declaration//GEN-END:variables
    conectar cc = new conectar();
    Connection cn = cc.ConexionMySql();
}
