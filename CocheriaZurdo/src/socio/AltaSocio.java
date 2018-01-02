
package socio;

import adherente.AltaAdherente;
import cocheriazurdo.Fecha_Hora;
import cocheriazurdo.conectar;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.*;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.logging.*;
import javax.swing.ImageIcon;
import tarifa.ConsTarParaAltaSocio;

public class AltaSocio extends javax.swing.JFrame {

    
    public AltaSocio() {
        initComponents(); 
        this.setVisible(true);
        setIconImage(new ImageIcon(getClass().getResource("/recursos/logocz.png")).getImage());
        setLocationRelativeTo(null);
        cargarTarifas();
        this.jnumeroadherentes.setText("0");
    }
    
    public void setPrecio(){
        
        String datos[] = new String [1];
        String nombreTarifa = jcodigotarifa.getSelectedItem().toString();
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT precio FROM bdcocheriazurdo.tarifas WHERE nombre="+"'"+nombreTarifa+"'");
            //si hay dos, HOUSTON WE ARE PROBLEM
            while(rs.next()){
                datos[0] = rs.getString("precio");        
            }
            
            jprecio.setText(datos[0]);
            
            

        } catch (SQLException ex) {
            Logger.getLogger(ConsultaSocios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void cargarTarifas(){
        
        jcodigotarifa.getSelectedIndex();
        
        String datos[] = new String [1];
        jcodigotarifa.addItem("Seleccionar item");
        
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre FROM bdcocheriazurdo.tarifas WHERE estado='ALTA'");
            
            while(rs.next()){
                datos[0] = rs.getString("nombre");        
                jcodigotarifa.addItem(datos[0]);
            }
            
            jcodigotarifa.removeItem("");

        } catch (SQLException ex) {
            Logger.getLogger(ConsultaSocios.class.getName()).log(Level.SEVERE, null, ex);
        }     
        
        
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
    
    public void cargaDatos(){
       
       int cantsocios=0;
       //CONSIGO EL NRO_SOCIO MAS GRANDE ASI LUEGO INCREMENTO
       try{
        PreparedStatement pst = cn.prepareStatement("INSERT INTO bdcocheriazurdo.socios VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(nro_socio) FROM bdcocheriazurdo.socios");
            rs.last();
            cantsocios = rs.getInt("MAX(nro_socio)");
            }catch (SQLException ex) {
            Logger.getLogger(ConsultaSocios.class.getName()).log(Level.SEVERE, null, ex);
        }
        pst.setInt(1,cantsocios+1);
        pst.setInt(2, this.jzona.getSelectedIndex()+1);
        pst.setString(3, this.jnombre.getText().toUpperCase());
        pst.setString(4, this.japellido.getText().toUpperCase());
        pst.setString(5, this.jdni.getText().toUpperCase());
        pst.setString(6, this.jdireccion.getText().toUpperCase());
        pst.setString(7, this.jtelefono.getText().toUpperCase());
        pst.setString(8, getSexo());
        pst.setString(9, getLocalidad());
        pst.setString(10, getPlan());
        pst.setString(11, this.jcodigotarifa.getSelectedItem().toString().toUpperCase());
        pst.setString(12, this.jcodigoobrasocial.getText().toUpperCase());
        pst.setString(13, this.jfechanac.getText().toUpperCase());
        pst.setString(14, this.jfechaalta.getText().toUpperCase());
        pst.setString(15, this.jfechacobertura.getText().toUpperCase());
        pst.setString(16, this.jedad.getText().toUpperCase());
        pst.setString(17, this.jnumeroadherentes.getText().toUpperCase());
        pst.setDouble(18,0);
        pst.setString(19, "ACTIVO");
        pst.setString(20, "ALTA");

        pst.executeUpdate();
        
        JOptionPane.showMessageDialog(null,"Socio dado de alta con éxito"); 

        int tieneadherentes = Integer.parseInt(this.jnumeroadherentes.getText());
                
        if(tieneadherentes == 0){
            AltaSocioExito AUE = new AltaSocioExito();
            this.dispose();
        }
        else{
            if(tieneadherentes == 1){
                JOptionPane.showMessageDialog(null,"A continuación cargará los adherentes"); 
                AltaAdherente AH = new AltaAdherente(1, cantsocios+1);
                this.dispose();
            }
            else{
                JOptionPane.showMessageDialog(null,"A continuación cargará los adherentes"); 
                AltaAdherente AH = new AltaAdherente(tieneadherentes, cantsocios+1);
                this.dispose();
            }
        }
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error en la conexion o dni duplicado - "+e.getMessage());
        }
       
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
    
    public String getLocalidad(){
        int varlocalidad = this.jlocalidad.getSelectedIndex();
        switch (varlocalidad){
            case 0: return "BOVRIL";
            case 1: return "SAUCE DE LUNA";
            case 2: return "ALCARAZ";
            case 3: return "COLONIA AVIGDOR";
            case 4: return "MOJONES NORTE";
            case 5: return "MOJONES SUR";
            case 6: return "CONSCRIPTO BERNARDI";
            case 7: return "SIR LEONARD";
            default: return "NO ESPECIFICADO";
        }
    }
    
    public String getPlan(){
        int varplan = this.jplan.getSelectedIndex();
        switch (varplan){
            case 0: return "INDIVIDUAL";
            case 1: return "TITULAR Y ADHERENTE";
            case 2: return "FAMILIAR";
            case 3: return"FAMILIAR CON IOSPER";
            case 4: return "JUBILADOS";
            default: return "NO ESPECIFICADO";
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jzona = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jnombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        japellido = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jdni = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jsexo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jfechanac = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jdireccion = new javax.swing.JTextField();
        jlocalidad = new javax.swing.JComboBox<>();
        jplan = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jprecio = new javax.swing.JTextField();
        jcodigoobrasocial = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jedad = new javax.swing.JTextField();
        jfechaalta = new javax.swing.JFormattedTextField();
        jfechacobertura = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btcargar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jnumeroadherentes = new javax.swing.JTextField();
        jtelefono = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btEXIT = new javax.swing.JLabel();
        btMINIMIZAR = new javax.swing.JLabel();
        btBACK = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jcodigotarifa = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INGRESO DE NUEVO SOCIO");
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(55, 64, 70));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black, java.awt.Color.black), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white)));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 240, 240));
        jLabel1.setText("ALTA DE NUEVO SOCIO TITULAR");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(240, 240, 240));
        jLabel4.setText("ZONA:");

        jzona.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jzona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 - OFICINA BOVRIL", "2 - BOVRIL 2", "3 - BOVRIL 3", "4 - BOVRIL 4", "5 - BOVRIL 5", "6 - ALCARAZ", "7 - SIR LEONARD", "8 - MOJONES", "9 - AVIGDOR", "10 - SAUCE DE LUNA", "11 - CONSCRIPTO BERNARDI", "12 - SOCIO NUEVO" }));
        jzona.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(240, 240, 240));
        jLabel6.setText("APELLIDO:");

        japellido.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        japellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                japellidoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(240, 240, 240));
        jLabel7.setText("DNI:");

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

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(240, 240, 240));
        jLabel8.setText("FECHA DE NACIMIENTO:");

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

        jlocalidad.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jlocalidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BOVRIL", "SAUCE DE LUNA", "ALCARAZ", "COLONIA AVIGDOR", "MOJONES NORTE", "MOJONES SUR", "CONSCRIPTO BERNARDI", "SIR LEONARD" }));

        jplan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jplan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INDIVIDUAL", "TITULAR Y ADHERENTE", "FAMILIAR", "FAMILIAR CON OBRA SOCIAL", "JUBILADOS" }));
        jplan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jplanItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(240, 240, 240));
        jLabel10.setText("LOCALIDAD:");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(240, 240, 240));
        jLabel12.setText("TELEFONO:");

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(240, 240, 240));
        jLabel13.setText("PLAN:");

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(240, 240, 240));
        jLabel14.setText("TARIFA:");

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(240, 240, 240));
        jLabel15.setText("OBRA SOCIAL:");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(240, 240, 240));
        jLabel9.setText("NUMERO DE ADHERENTES:");

        jprecio.setEditable(false);
        jprecio.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jprecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jprecioActionPerformed(evt);
            }
        });

        jcodigoobrasocial.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jcodigoobrasocial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jcodigoobrasocialKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(240, 240, 240));
        jLabel18.setText("EDAD:");

        jedad.setEditable(false);
        jedad.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jfechaalta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jfechaalta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jfechacobertura.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.MEDIUM))));
        jfechacobertura.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(240, 240, 240));
        jLabel16.setText("FECHA ALTA:");

        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(240, 240, 240));
        jLabel17.setText("FECHA COBERTURA:");

        btcargar.setBackground(new java.awt.Color(153, 255, 153));
        btcargar.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        btcargar.setText("CARGAR");
        btcargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcargarActionPerformed(evt);
            }
        });
        btcargar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btcargarKeyPressed(evt);
            }
        });

        jnumeroadherentes.setEditable(false);
        jnumeroadherentes.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jnumeroadherentes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jnumeroadherentesKeyTyped(evt);
            }
        });

        jtelefono.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jtelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtelefonoKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(240, 240, 240));
        jLabel19.setText("PRECIO:");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/altauser.png"))); // NOI18N

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

        jcodigotarifa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jcodigotarifa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcodigotarifaItemStateChanged(evt);
            }
        });
        jcodigotarifa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jcodigotarifaFocusLost(evt);
            }
        });
        jcodigotarifa.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jcodigotarifaCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jzona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addComponent(jSeparator5)
                            .addComponent(jSeparator6)
                            .addComponent(jSeparator7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(japellido, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jdni, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jsexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
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
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jplan, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jnumeroadherentes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcodigotarifa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcodigoobrasocial, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(112, 112, 112)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator9))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jfechacobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 120, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btcargar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btBACK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btMINIMIZAR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btEXIT))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator8)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btBACK)
                        .addComponent(btEXIT)
                        .addComponent(btMINIMIZAR))
                    .addComponent(jLabel20)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jzona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(japellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jdni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jsexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jfechanac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jdireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jtelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jlocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jplan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jnumeroadherentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jcodigoobrasocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jcodigotarifa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jfechaalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jfechacobertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btcargar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btcargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcargarActionPerformed
        
        if (!(jnombre.getText().isEmpty() || japellido.getText().isEmpty() || jdni.getText().isEmpty() || jtelefono.getText().isEmpty() ||
            jdireccion.getText().isEmpty() || jfechanac.getText().isEmpty() || jcodigoobrasocial.getText().isEmpty())){
            
            if(jcodigotarifa.getSelectedItem()!="Seleccionar item"){
                cargaDatos();
            }
            else{
                JOptionPane.showMessageDialog(null,"Se debe especificar una tarifa");
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Se deben cargar todos los datos");
        }
        
        
    }//GEN-LAST:event_btcargarActionPerformed

    private void btcargarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btcargarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER){
            cargaDatos();
        }
    }//GEN-LAST:event_btcargarKeyPressed

    private void jplanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jplanItemStateChanged
        switch(this.jplan.getSelectedItem().toString()){
            case "INDIVIDUAL": 
                this.jnumeroadherentes.setText("0");
                this.jnumeroadherentes.setEditable(false);
                break;
            
            case "TITULAR Y ADHERENTE":
                this.jnumeroadherentes.setText("1");
                this.jnumeroadherentes.setEditable(false);
                break;
                
            default: 
                this.jnumeroadherentes.setEditable(true);
                this.jnumeroadherentes.setText("2");
        }
    }//GEN-LAST:event_jplanItemStateChanged

    private void jprecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jprecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jprecioActionPerformed

    private void jnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnombreKeyTyped
        if(((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
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
            evt.consume(); 
    }//GEN-LAST:event_jnombreKeyTyped

    private void jdniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdniKeyTyped
        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
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
            evt.consume();     
    }//GEN-LAST:event_jdniKeyTyped

    private void japellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_japellidoKeyTyped
        if(((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
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
            evt.consume(); 
    }//GEN-LAST:event_japellidoKeyTyped

    private void jtelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtelefonoKeyTyped
        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
            int cont = jtelefono.getText().length();
            if (cont>=12){
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
            evt.consume(); 
    }//GEN-LAST:event_jtelefonoKeyTyped

    private void jnumeroadherentesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnumeroadherentesKeyTyped
        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
            int cont = jnumeroadherentes.getText().length();
            if (cont>=1){
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
            evt.consume();
    }//GEN-LAST:event_jnumeroadherentesKeyTyped

    private void jcodigoobrasocialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcodigoobrasocialKeyTyped
        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || ((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
            int cont = jcodigoobrasocial.getText().length();
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
            evt.consume();
    }//GEN-LAST:event_jcodigoobrasocialKeyTyped

    private void jdireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jdireccionKeyTyped
/*        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || ((evt.getKeyChar()>='A') && (evt.getKeyChar()<='Z')) || ((evt.getKeyChar()>='a') && (evt.getKeyChar()<='z')) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SPACE)){
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
        if(((evt.getKeyChar()>=48) && (evt.getKeyChar()<=57)) || (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE) || (evt.getKeyChar() == KeyEvent.VK_SLASH)){
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
            evt.consume();
    }//GEN-LAST:event_jfechanacKeyTyped

    private void jnombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jnombreFocusGained
    }//GEN-LAST:event_jnombreFocusGained

    private void btBACKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btBACKMouseClicked
       if (jnombre.getText().isEmpty() && japellido.getText().isEmpty() && jdni.getText().isEmpty() && jtelefono.getText().isEmpty() &&
            jdireccion.getText().isEmpty() && jfechanac.getText().isEmpty() && jcodigoobrasocial.getText().isEmpty()
            ){
                OpcionesSocio OS = new OpcionesSocio();
                this.dispose();
        }else{
            int seleccion = JOptionPane.showConfirmDialog(null, "Existen campos con datos cargados. Desea perder los datos y regresar?", "Confirmar regreso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            if(seleccion == 0){
               OpcionesSocio OS = new OpcionesSocio();
                this.dispose(); 
            }
            else{
                
            }
            
        }
    }//GEN-LAST:event_btBACKMouseClicked

    private void btMINIMIZARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMINIMIZARMouseClicked
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_btMINIMIZARMouseClicked

    private void btEXITMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btEXITMouseClicked
        if (jnombre.getText().isEmpty() && japellido.getText().isEmpty() && jdni.getText().isEmpty() && jtelefono.getText().isEmpty() &&
            jdireccion.getText().isEmpty() && jfechanac.getText().isEmpty() && jcodigoobrasocial.getText().isEmpty()
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

    private void jfechanacFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jfechanacFocusLost
        if(this.jfechanac.getText().isEmpty()){ 
        }else{
            fechaSistema();
        }        
    }//GEN-LAST:event_jfechanacFocusLost

    private void jcodigotarifaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jcodigotarifaFocusLost
        setPrecio();
    }//GEN-LAST:event_jcodigotarifaFocusLost

    private void jcodigotarifaCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jcodigotarifaCaretPositionChanged
        setPrecio();
    }//GEN-LAST:event_jcodigotarifaCaretPositionChanged

    private void jcodigotarifaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcodigotarifaItemStateChanged
        setPrecio();
    }//GEN-LAST:event_jcodigotarifaItemStateChanged
    
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
            java.util.logging.Logger.getLogger(AltaSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AltaSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AltaSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AltaSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AltaSocio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btBACK;
    private javax.swing.JLabel btEXIT;
    private javax.swing.JLabel btMINIMIZAR;
    private javax.swing.JButton btcargar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTextField japellido;
    private javax.swing.JTextField jcodigoobrasocial;
    private javax.swing.JComboBox<String> jcodigotarifa;
    private javax.swing.JTextField jdireccion;
    private javax.swing.JFormattedTextField jdni;
    private javax.swing.JTextField jedad;
    private javax.swing.JFormattedTextField jfechaalta;
    private javax.swing.JFormattedTextField jfechacobertura;
    private javax.swing.JFormattedTextField jfechanac;
    private javax.swing.JComboBox<String> jlocalidad;
    private javax.swing.JTextField jnombre;
    private javax.swing.JTextField jnumeroadherentes;
    private javax.swing.JComboBox<String> jplan;
    private javax.swing.JTextField jprecio;
    private javax.swing.JComboBox<String> jsexo;
    private javax.swing.JTextField jtelefono;
    private javax.swing.JComboBox<String> jzona;
    // End of variables declaration//GEN-END:variables

    conectar cc = new conectar();
    Connection cn = cc.ConexionMySql();
 
}
