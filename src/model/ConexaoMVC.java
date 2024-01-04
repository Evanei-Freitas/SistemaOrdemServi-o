

package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author william
 */
public class ConexaoMVC {
    
// Criação do Metodo de Conexao em MVC
    public Connection getConnection(){
        Connection conn = null;
        try {
           Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbinfox", "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return conn;
    }
    
    
    public class ExceptionDAO extends Exception{
        public ExceptionDAO(String mensagem){
            super(mensagem);
        }
    }
    
    
} 
