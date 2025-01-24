package com.pkg.queryGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.pkg.POJO.DbPojo;
import com.pkg.Util.DBConnection;

public class QueryExecutor {
	public static int queryExecutor(String query,boolean returnGeneratedKey) {
		try(Connection c = DBConnection.connect();
				PreparedStatement p = c.prepareStatement(query, 
			             returnGeneratedKey ? PreparedStatement.RETURN_GENERATED_KEYS : PreparedStatement.NO_GENERATED_KEYS)){

			int rowsAffected = p.executeUpdate();
			if(returnGeneratedKey && rowsAffected>0) {
				try (ResultSet rs = p.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Return the first generated key (typically the primary key)
                    }
                }
			}
			return rowsAffected;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static List<DbPojo> queryExecutor(String query, Class<? extends DbPojo> returnType){
		List<DbPojo> resultSet = new ArrayList<>();
		try(Connection c = DBConnection.connect();
				PreparedStatement p = c.prepareStatement(query)) {
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				DbPojo row = returnType.getDeclaredConstructor().newInstance();
				Field[] fields = row.getClass().getDeclaredFields();
				for(Field f: fields) {
					f.setAccessible(true);
					//System.out.println(f.getName()+" :"+f.get(row));
				}
				for (Field field : fields) {
				
					field.setAccessible(true);
					Object  value = rs.getObject(field.getName());
					
					field.set(row, value);
				}
				//System.out.println("row added");
				resultSet.add(row);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
}
