package com.pkg.Util;
import javax.naming.Context;



import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
    public static Connection connect() throws SQLException, NamingException {
//        try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/MyContacts", "root", "root");
    	Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup("java:/comp/env");//env context - used to perform resource lookups in the web apps
        DataSource ds = (DataSource) envContext.lookup("jdbc/MyContactsDB");
        return ds.getConnection();
    }
}
