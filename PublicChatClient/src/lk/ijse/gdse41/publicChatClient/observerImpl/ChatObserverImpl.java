package lk.ijse.gdse41.publicChatClient.observerImpl;

import lk.ijse.gdse41.publicChatCommon.model.User;
import lk.ijse.gdse41.publicChatCommon.observer.ChatObserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by oshan on 19-Nov-17.
 */
public class ChatObserverImpl extends UnicastRemoteObject implements ChatObserver {

    private ChatObserver observer;

    public ChatObserverImpl(ChatObserver observer) throws RemoteException{
        this.observer = observer;
    }

    @Override
    public boolean update(String username,String message) throws RemoteException {
        return observer.update(username,message);
    }

    @Override
    public ArrayList<User> getOnlineUsers() throws RemoteException {
        return observer.getOnlineUsers();
    }

    @Override
    public String getUsername() throws RemoteException {
        return observer.getUsername();
    }

    @Override
    public boolean updateUI(ArrayList<String> clientList) throws RemoteException {
        return observer.updateUI(clientList);
    }

}
