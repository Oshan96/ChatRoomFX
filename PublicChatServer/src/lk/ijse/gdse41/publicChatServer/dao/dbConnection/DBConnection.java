package lk.ijse.gdse41.publicChatServer.dao.dbConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by oshan on 18-Nov-17.
 */
public class DBConnection {
    public static Connection getConnection() throws ClassNotFoundException,SQLException{
        Properties dbPro=new Properties();
//        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties");
        try {
            InputStream input = new FileInputStream(new File("dbSettings/settings.properties"));
            dbPro.load(input);
            System.out.println("Pass : "+dbPro.getProperty("password"));
            String setDB=String.format("jdbc:mysql://%s:%s/%s", dbPro.getProperty("ip"),dbPro.getProperty("port"),dbPro.getProperty("database"));
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(setDB,dbPro.getProperty("username"),dbPro.getProperty("password"));
            return connection;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
