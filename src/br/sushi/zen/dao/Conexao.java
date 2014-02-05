package br.sushi.zen.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Cesar Schutz
 */
public class Conexao {
    /**
     * Metodo responsavel por conectar ao banco de dados.
     * @return conexão aberta
     * @throws ClassNotFoundException 
     * @throws java.sql.SQLException 
     */
    public static Connection conectar() throws ClassNotFoundException, SQLException {
        Class.forName("org.firebirdsql.jdbc.FBDriver");

        //setando propriedades para que pegue as informaçoes do banco com ascento e caracteres especais e etc
        Properties props = new Properties();
        props.put("user", "SYSDBA");
        props.put("password", "masterkey");
        props.put("charset", "UTF8");
        props.put("lc_ctype", "ISO8859_1");

        Connection con = DriverManager.getConnection("jdbc:firebirdsql:localhost/3050:C:\\DBSUSHIZEN.FDB", props);
        
        return con;
    }
}
