package lk.ijse.gdse41.publicChatClient.ui.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.gdse41.publicChatCommon.control.ChatController;
import lk.ijse.gdse41.publicChatCommon.model.User;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateNewAccount implements Initializable{
    private ChatController controller;
    private boolean isFieldEmpty=false;
    private String password;
    private File file;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtName;

    @FXML
    private DatePicker dtPkr;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtPath;

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnCreate;


    public void setController(ChatController controller){
        this.controller=controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbGender.getItems().addAll("Male","Female");
    }

    @FXML
    public void btnBrowseAction(ActionEvent actionEvent){
        file=showOpenFile();
        if(file!=null){
            txtPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void createAccount(ActionEvent event){
        User user = new User();
        String username,name,dob,gender,address,nic,contact,pic;
        pic = txtPath.getText();
        try {
            username = txtUsername.getText();
            name = txtName.getText();
            dob = dtPkr.getValue().toString();
            gender = cmbGender.getValue();
            address = txtAddress.getText();
            nic = txtNic.getText();
            contact = txtContact.getText();

        }catch (NullPointerException ex){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create New Account");
            alert.setHeaderText("Create New Account");
            alert.setContentText("Please Fill All Fields");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        ArrayList<String> list = new ArrayList<>();
        list.add(username);
        list.add(name);
        list.add(dob);
        list.add(gender);
        list.add(address);
        list.add(nic);
        list.add(contact);

        isFieldEmpty=isEmpty(list);
        if(isFieldEmpty||!nic.matches("[0-9]{9}[Vv]")||!contact.matches("[0-9]{10}")){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create New Account");
            alert.setHeaderText("Create New Account");
            alert.setContentText("Please Ender Valid Details");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
            return;
        }

        user.setUsername(username);
        user.setName(name);
        user.setAddress(address);
        user.setDob(dob);
        user.setGender(gender);
        user.setNic(nic);
        user.setContact(contact);
        ///////////////////////////////////////
        showConfirmation();
        if(password.equals("")){
            System.out.println("please set password!");
            return;
        }
        user.setPassword(password);

        file=new File(pic);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(new Image("file:"+file.getAbsolutePath()), null),
                    "png", new File("resources/user-images/"+username+".png"));

        } catch (Exception e) {

        }
        try {
            boolean val =controller.addNewUser(user);
            if(val){
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Create New Account");
                alert.setHeaderText("Create New Account");
                alert.setContentText("Account Created!");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Create New Account");
                alert.setHeaderText("Create New Account");
                alert.setContentText("Failed to create the account!");
                alert.getButtonTypes().setAll(ButtonType.OK);
                alert.showAndWait();
            }
            ((Stage)txtAddress.getScene().getWindow()).close();
        } catch (RemoteException | SQLException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Create New Account");
            alert.setHeaderText("Create New Account");
            alert.setContentText("Failed to create the account!");
            alert.getButtonTypes().setAll(ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void showConfirmation(){
        Stage stage=new Stage();
        VBox box=new VBox();

        box.setId("root");
        box.setSpacing(10);
        box.setPadding(new Insets(8));

        HBox titleBox=new HBox();
        titleBox.setPadding(new Insets(8));
        titleBox.setPrefWidth(box.getPrefWidth());
        titleBox.setAlignment(Pos.CENTER);
        Label title=new Label("Set Password");
        title.setId("title");
        titleBox.getChildren().add(title);

        box.getChildren().add(titleBox);

        HBox containerBox=new HBox();
        containerBox.setPrefWidth(box.getPrefWidth());

        VBox lblBox=new VBox();
        lblBox.setSpacing(8);
        lblBox.setAlignment(Pos.CENTER_LEFT);
//        lblBox.setPrefWidth(box.getPrefWidth()/3);

        Label lblPass=new Label("Password");
        lblPass.getStyleClass().add("label-info");
        Label lblConPass=new Label("Confirm Password");
        lblConPass.getStyleClass().add("label-info");

        lblBox.getChildren().addAll(lblPass,lblConPass);

        containerBox.getChildren().add(lblBox);

        VBox txtBox=new VBox();
        txtBox.setSpacing(8);

        PasswordField txtPass=new PasswordField();
        txtPass.setPrefWidth(100);

        PasswordField txtConPass=new PasswordField();
        txtPass.setPrefWidth(100);

        txtBox.setPadding(new Insets(8));

        txtBox.getChildren().addAll(txtPass,txtConPass);

        containerBox.getChildren().add(txtBox);

        box.getChildren().add(containerBox);

        Button button=new Button("Confirm");
        button.setId("btnCreate");

        button.setOnAction(e->{
            if(!txtPass.getText().equals(txtConPass.getText())){
                System.out.println("Password mismatch!");
            }else{
                password=txtPass.getText();
                System.out.println("Success!");
                ((Stage)txtPass.getScene().getWindow()).close();
            }
        });

        HBox buttonBox=new HBox();
        buttonBox.setPadding(new Insets(10));
        buttonBox.setPrefWidth(box.getPrefWidth());
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().add(button);

        box.getChildren().add(buttonBox);

        Scene scene=new Scene(box);
        scene.getStylesheets().add(getClass().getResource("/lk/ijse/gdse41/publicChatClient/ui/util/css/Login.css").toExternalForm());
        stage.setScene(scene);
        stage.showAndWait();
    }

    private File showOpenFile(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Student Photo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg"));
        return fileChooser.showOpenDialog(txtUsername.getScene().getWindow());
    }

    private boolean isEmpty(ArrayList<String> list){
        for(String s : list){
            if(s.equals(""))return true;
        }
        return false;
    }

    @FXML
    private void intRestriction(KeyEvent key){
        if(!"0123456789".contains(key.getCharacter())&&(key.getCode()!= KeyCode.BACK_SPACE)){
            key.consume();
        }
    }

    @FXML
    private void namingFieldsRestriction(KeyEvent event){
        if(!event.getCharacter().matches("[a-z]|[A-Z]+")&&(event.getCode()!= KeyCode.BACK_SPACE)&&!(event.getCharacter().matches("\\s"))){
            event.consume();
        }

    }

}
