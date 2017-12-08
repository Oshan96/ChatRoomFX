package lk.ijse.gdse41.publicChatCommon.observer;

import lk.ijse.gdse41.publicChatCommon.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by oshan on 18-Nov-17.
 */
public interface ChatObserver extends Remote{
    boolean update(String username,String message) throws RemoteException;
    ArrayList<User> getOnlineUsers() throws RemoteException;
    String getUsername() throws RemoteException;
    boolean updateUI(ArrayList<String> clientList) throws RemoteException;
}
