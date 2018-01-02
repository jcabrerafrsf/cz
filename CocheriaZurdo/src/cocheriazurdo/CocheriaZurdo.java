
package cocheriazurdo;

import acceso.AccesoUsuario;

public class CocheriaZurdo {


    public static void main(String[] args) {
        
        conectar obj=new conectar();
        obj.ConexionMySql();
        
        AccesoUsuario au=new AccesoUsuario();
        au.setLocationRelativeTo(null);        
        au.setVisible(true);
        
    }
    
}
