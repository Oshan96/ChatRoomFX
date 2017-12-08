package lk.ijse.gdse41.publicChatClient.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse41.publicChatClient.connector.ServerConnector;
import lk.ijse.gdse41.publicChatCommon.control.ChatController;
import lk.ijse.gdse41.publicChatCommon.observer.ChatObserver;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by oshan on 19-Nov-17.
 */
public class Login implements Initializable {

    private ChatController controller;


    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Text createNewAccount;

    @FXML
    private void createAccount(MouseEvent evt){
        Stage stage=new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/lk/ijse/gdse41/publicChatClient/ui/fxml/CreateNewAccount.fxml"));
        try {
            Scene scene=new Scene(fxmlLoader.load());
            scene.setFill(Color.TRANSPARENT);
            CreateNewAccount accController=fxmlLoader.getController();
            accController.setController(controller);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            controller= ServerConnector.getServerConnector().getController();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void loginAction(ActionEvent event) {
        String username=txtUsername.getText();
        String password=txtPassword.getText();

        if(!username.equals("")||!password.equals("")){
            try {
                boolean isValid=controller.checkCredentials(username,password);
                if(isValid){
                    if(!controller.isReserved(username)){
                        Stage stage=new Stage();
                        stage.initStyle(StageStyle.TRANSPARENT);
                        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/lk/ijse/gdse41/publicChatClient/ui/fxml/ChatRoom.fxml"));
                        stage.setScene(new Scene(fxmlLoader.load()));
                        stage.getScene().setFill(Color.TRANSPARENT);
                        ChatRoom chatController=fxmlLoader.getController();
                        chatController.setUsername(username);
                        stage.setOnCloseRequest(event1 -> {
                            try {
                                controller.removeChatObserver(chatController.getChatObserver());
                                controller.updateClientList();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        });
                        ((Stage)txtPassword.getScene().getWindow()).close();
                        stage.show();
                    }else{
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Login Error!");
                        alert.setHeaderText("Login Error!");
                        alert.setContentText("You are logged in from another device");
                        alert.getButtonTypes().setAll(ButtonType.OK);
                        alert.showAndWait();
                    }
                }else {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Error!");
                    alert.setHeaderText("Login Erro!");
                    alert.setContentText("Please Enter valid credentials");
                    alert.getButtonTypes().setAll(ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Fill Credentials");
        }
    }


}
