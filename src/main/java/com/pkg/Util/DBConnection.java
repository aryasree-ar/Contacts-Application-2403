package com.pkg.Util;
import javax.naming.Context;




import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
	
    public static Connection connect() throws SQLException, NamingException {
    	Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");//env context - used to perform resource lookups in the web apps
        DataSource ds = (DataSource) envContext.lookup("jdbc/MyContactsDB");
        return ds.getConnection();
    }
}
