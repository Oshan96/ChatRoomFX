package lk.ijse.gdse41.publicChatClient.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.gdse41.publicChatClient.ui.controller.ChatRoom;

/**
 * Created by oshan on 19-Nov-17.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

//        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("../ui/fxml/ChatRoom.fxml"));
//
//        primaryStage.setScene(new Scene(fxmlLoader.load()));
//        ChatRoom chatRoom=fxmlLoader.getController();
//        chatRoom.setUsername("user 1");
//        primaryStage.show();
//
//        FXMLLoader fxmlLoader1=new FXMLLoader(getClass().getResource("../ui/fxml/ChatRoom.fxml"));
//        Stage stage=new Stage();
//        stage.setScene(new Scene(fxmlLoader1.load()));
//
//        ChatRoom chatRoom1=fxmlLoader.getController();
//        chatRoom1.setUsername("user 2");
//
//        stage.show();

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root=FXMLLoader.load(getClass().getResource("/lk/ijse/gdse41/publicChatClient/ui/fxml/Login.fxml"));
        Scene scene=new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
