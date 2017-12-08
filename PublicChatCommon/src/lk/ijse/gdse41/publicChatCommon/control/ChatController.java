package lk.ijse.gdse41.publicChatCommon.control;

import lk.ijse.gdse41.publicChatCommon.model.User;
import lk.ijse.gdse41.publicChatCommon.observer.ChatObserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by oshan on 18-Nov-17.
 */
public interface ChatController extends Remote{
    boolean notifyAllClients(String username, String message) throws RemoteException;
    ArrayList<User> getAll() throws RemoteException, SQLException;
    User get(String username) throws RemoteException, SQLException;
    boolean addChatObserver(ChatObserver chatObserver)throws RemoteException;
    boolean removeChatObserver(ChatObserver chatObserver)throws RemoteException;
    boolean isReserved(String username) throws RemoteException;
    boolean checkCredentials(String username, String password) throws RemoteException, SQLException;
    boolean updateClientList() throws RemoteException;
    boolean addNewUser(User user) throws RemoteException, SQLException;
}
