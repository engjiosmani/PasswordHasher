package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.DBUserFunctions;
import utils.PasswordHasher;
import utils.Session;
import utils.Users;

import java.io.IOException;

import static controller.SignUpController.isValidEmail;
import static controller.SignUpController.isValidPassword;



public class SignInController{

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    TextField passwordFieldText;

    @FXML
    Button loginButton;

    @FXML
    ImageView eye;

    @FXML
    private AnchorPane baseAnchor;

    @FXML
    Label stringShower;


    DBUserFunctions dbUserFunctions = new DBUserFunctions();
    private boolean check = false;


    @FXML
    private void handleSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) baseAnchor.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void signIn() {
        try {
            if(password.getText()==null){
                password.setText(passwordFieldText.getText());
            }

            if (!isValidEmail(email.getText().toLowerCase())) {
                throw new Exception("Emails format is wrong.");
            }
            if (!isValidPassword(password.getText())) {
                throw new Exception("Password must contain 8 numbers, letters or capitalised letters.");
            }

            if(dbUserFunctions.getUserByEmail(email.getText().toLowerCase())!=null){
                Users user =dbUserFunctions.getUserByEmail(email.getText().toLowerCase());
                String salt = user.getSalt();
                String passwordGenerated = PasswordHasher.hashPassword(password.getText(), salt);
                if (passwordGenerated.equals(user.getPasswordHash())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText("You have been loged in");
                    alert.showAndWait();

                }
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Authentication Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void initialize() {
        loginButton.setDefaultButton(true);
        javafx.application.Platform.runLater(() -> {
            stringShower.setText(Session.getExplanation());
        });

    }