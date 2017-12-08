package lk.ijse.gdse41.publicChatClient.ui.controller;

import com.jfoenix.controls.JFXDrawer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lk.ijse.gdse41.publicChatClient.connector.ServerConnector;
import lk.ijse.gdse41.publicChatClient.observerImpl.ChatObserverImpl;
import lk.ijse.gdse41.publicChatCommon.control.ChatController;
import lk.ijse.gdse41.publicChatCommon.model.User;
import lk.ijse.gdse41.publicChatCommon.observer.ChatObserver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by oshan on 19-Nov-17.
 */
public class ChatRoom implements Initializable,ChatObserver {

    private ChatObserver chatObserver;
    private ChatController controller;
    private String username="user";
    private double xOffset;
    private double yOffset;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane titleBar;

    @FXML
    private AnchorPane detailPane;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private TextArea txtMsg;

    @FXML
    private VBox chatBox;

    @FXML
    private Button btnSend;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextFlow emojiList;

    @FXML
    private Button btnEmoji;
    @FXML
    private JFXDrawer drawerPane;

    @FXML
    private ScrollPane clientListScroll;

    @FXML
    private VBox clientListBox;
    @FXML
    private Button btnClose;



    public void setUsername(String username){
        this.username=username;
        System.out.println(this.username);
        try {
            controller.updateClientList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        System.out.println(username);
        for(Node text : emojiList.getChildren()){
            text.setOnMouseClicked(event -> {
                txtMsg.setText(txtMsg.getText()+" "+((Text)text).getText());
                emojiList.setVisible(false);
            });
        }

        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        try {
            chatObserver=new ChatObserverImpl(this);
            controller= ServerConnector.getServerConnector().getController();
            controller.addChatObserver(chatObserver);
//            controller.updateClientList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        System.out.println();
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            titleBar.getScene().getWindow().setX(event.getScreenX() - xOffset);
            titleBar.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    void emojiAction(ActionEvent event) {
        if(emojiList.isVisible()){

            emojiList.setVisible(false);
        }else {
            emojiList.setVisible(true);
        }
    }

    @FXML
    void sendAction(ActionEvent event) {
        try {
            if(txtMsg.getText().trim().equals(""))return;
            controller.notifyAllClients(username,txtMsg.getText().trim());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        txtMsg.setText("");
        txtMsg.requestFocus();
    }

    @Override
    public boolean update(String username,String message) throws RemoteException {
        Text text=new Text(message);

        text.setFill(Color.WHITE);
        text.getStyleClass().add("message");
        TextFlow tempFlow=new TextFlow();
        if(!this.username.equals(username)){
            Text txtName=new Text(username + "\n");
            txtName.getStyleClass().add("txtName");
            tempFlow.getChildren().add(txtName);
        }

        tempFlow.getChildren().add(text);
        tempFlow.setMaxWidth(200);

        TextFlow flow=new TextFlow(tempFlow);

        HBox hbox=new HBox(12);

        Circle img =new Circle(32,32,16);
        try{
            System.out.println(username);
            String path= new File(String.format("resources/user-images/%s.png", username)).toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        }catch (Exception ex){
            String path= new File("resources/user-images/user.png").toURI().toString();
            img.setFill(new ImagePattern(new Image(path)));
        }

        img.getStyleClass().add("imageView");

        if(!this.username.equals(username)){

            tempFlow.getStyleClass().add("tempFlowFlipped");
            flow.getStyleClass().add("textFlowFlipped");
            chatBox.setAlignment(Pos.TOP_LEFT);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().add(img);
            hbox.getChildren().add(flow);

        }else{
            text.setFill(Color.WHITE);
            tempFlow.getStyleClass().add("tempFlow");
            flow.getStyleClass().add("textFlow");
            hbox.setAlignment(Pos.BOTTOM_RIGHT);
            hbox.getChildren().add(flow);
            hbox.getChildren().add(img);
        }

        hbox.getStyleClass().add("hbox");
        Platform.runLater(() -> chatBox.getChildren().addAll(hbox));

        return true;

    }

    @Override
    public ArrayList<User> getOnlineUsers() throws RemoteException {
        return null;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public boolean updateUI(ArrayList<String> clientList) throws RemoteException {
        Platform.runLater(() -> clientListBox.getChildren().clear());
        for(String client : clientList){
            if(client.equals(this.username)) continue;
//            containerPane.getStyleClass().add("online-user-container");
            HBox container=new HBox() ;
            container.setAlignment(Pos.CENTER_LEFT);
            container.setSpacing(10);
            container.setPrefWidth(clientListBox.getPrefWidth());
            container.setPadding(new Insets(3));
            container.getStyleClass().add("online-user-container");
            Circle img =new Circle(30,30,15);
            try{
                String path= new File(String.format("resources/user-images/%s.png", client)).toURI().toString();
                img.setFill(new ImagePattern(new Image(path)));

            }catch (Exception ex){
                String path= new File("resources/user-images/user.png").toURI().toString();
                img.setFill(new ImagePattern(new Image(path)));
            }
            container.getChildren().add(img);

            VBox userDetailContainer=new VBox();
            userDetailContainer.setPrefWidth(clientListBox.getPrefWidth()/1.7);
            Label lblUsername=new Label(client);
            lblUsername.getStyleClass().add("online-label");
            userDetailContainer.getChildren().add(lblUsername);
            User user=null;
            try {
                user=controller.get(client);
                Label lblName=new Label(user.getName());
                lblName.getStyleClass().add("online-label-details");
                userDetailContainer.getChildren().add(lblName);
            } catch (SQLException e) {
                e.printStackTrace();
            }catch (NullPointerException ex){
                System.out.println("user is null");
            }
            container.getChildren().add(userDetailContainer);

            Label settings = new Label("...");
            settings.getStyleClass().add("online-settings");
            settings.setTextAlignment(TextAlignment.CENTER);
            container.getChildren().add(settings);
            System.out.println(container.getChildren().size());
            Platform.runLater(() -> clientListBox.getChildren().add(container));

        }
        return true;
    }

    @FXML
    void closeAction(ActionEvent event) {
        try {
            controller.removeChatObserver(chatObserver);
            controller.updateClientList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exit(0);
        ((Stage)btnClose.getScene().getWindow()).close();
    }

    public ChatObserver getChatObserver() {
        return chatObserver;
    }
}
