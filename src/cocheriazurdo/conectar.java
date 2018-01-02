package cocheriazurdo;
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;


public class conectar {
    Connection conect=null;
    
    private String bd="bdcocheriazurdo";
    private String url="jdbc:mysql://localhost/"+bd;
    private String user="root";
    private String contraseña="";
    
    public Connection ConexionMySql(){
        
        try{            
            Class.forName("com.mysql.jdbc.Driver");
            conect=DriverManager.getConnection(this.url,this.user,this.contraseña);
  
        }catch(HeadlessException | ClassNotFoundException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error en la conexion a: "+this.bd+" - "+e.getMessage());
        }
        
        return conect;
    }
    
    
    
}
