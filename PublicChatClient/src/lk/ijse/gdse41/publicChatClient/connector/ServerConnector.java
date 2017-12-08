package lk.ijse.gdse41.publicChatClient.connector;

import lk.ijse.gdse41.publicChatCommon.control.ChatController;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

/**
 * Created by oshan on 19-Nov-17.
 */
public class ServerConnector {
    private static ServerConnector serverConnector;
    private ChatController controller;

    private ServerConnector() throws IOException, NotBoundException {
        Properties properties=new Properties();
//        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties");
        InputStream input = new FileInputStream(new File("dbSettings/settings.properties"));

        properties.load(input);
//        System.setProperty("java.rmi.server.hostname",properties.getProperty("ip"));
        System.out.println(properties.getProperty("server-ip"));
        controller= (ChatController) Naming.lookup(String.format("rmi://%s:5050/ChatServer", properties.getProperty("server-ip")));

    }

    public static ServerConnector getServerConnector() throws IOException, NotBoundException {
       if(serverConnector==null){
           serverConnector=new ServerConnector();
       }
       return serverConnector;
    }

    public ChatController getController(){
        return controller;
    }
}
