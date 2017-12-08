package lk.ijse.gdse41.publicChatServer.main;

import lk.ijse.gdse41.publicChatServer.control.ChatControllerImpl;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 * Created by oshan on 18-Nov-17.
 */
public class ServerMain {

    public static void main(String[] args) {
        Properties properties=new Properties();
        try {
            InputStream input = new FileInputStream(new File("dbSettings/settings.properties"));
            System.out.println(System.getProperty("user.dir"));
            properties.load(input);

            System.setProperty("java.rmi.server.hostname",properties.getProperty("ip"));

            Registry registry= LocateRegistry.createRegistry(Integer.parseInt(properties.getProperty("registryPort")));
            registry.rebind("ChatServer",new ChatControllerImpl());

            System.out.println("Server Started...");
            System.out.println(properties.getProperty("ip"));
            System.out.println(properties.getProperty("registryPort"));

            String[] ar={"cmd","/c","start","powershell.exe","-command","Read-Host"," Server Started","Press Enter to Exit......"};

            Process process=Runtime.getRuntime().exec(ar);
            InputStream stderr = process.getErrorStream ();

            BufferedReader reader = new BufferedReader (new InputStreamReader(stderr));

            reader.readLine();
            System.out.println("Exiting...");
            reader.close();
            process.destroy();
//            System.exit(0);

            Runtime.getRuntime().exit(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
