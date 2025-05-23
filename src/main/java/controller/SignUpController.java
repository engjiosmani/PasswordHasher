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
import java.util.regex.Pattern;

public class SignUpController {

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField username;

    @FXML
    private TextField emailAddress;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button signupButton;

    @FXML
    private AnchorPane baseAnchor;

    @FXML
    private TextField confirmPasswordFieldText;

    @FXML
    private TextField passwordFieldText;

    @FXML
    private ImageView eye;

    @FXML
    private ImageView confirmEye;


    DBUserFunctions dbUserFunctions = new DBUserFunctions();
    private boolean check = false;


    @FXML
    private void handleSignIn() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/singin.fxml"));
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
    public void initialize() {
        signupButton.setDefaultButton(true);
    }

    @FXML
    public void unhidePassword() {
        if (password.isVisible()) {
            passwordFieldText.setText(password.getText());
            password.setManaged(false);
            password.setVisible(false);
            passwordFieldText.setManaged(true);
            passwordFieldText.setVisible(true);
            Image image = new Image(getClass().getResource("/images/eye.png").toExternalForm());
            eye.setImage(image);
            passwordFieldText.requestFocus();

            confirmPasswordFieldText.setText(confirmPassword.getText());
            confirmPassword.setManaged(false);
            confirmPassword.setVisible(false);
            confirmPasswordFieldText.setManaged(true);
            confirmPasswordFieldText.setVisible(true);
            confirmEye.setImage(image);
            check = true;

        } else {
            password.setText(passwordFieldText.getText());
            password.setManaged(true);
            password.setVisible(true);
            passwordFieldText.setManaged(false);
            passwordFieldText.setVisible(false);
            Image image = new Image(getClass().getResource("/images/hidden.png").toExternalForm());
            eye.setImage(image);
            password.requestFocus();

            confirmPassword.setText(confirmPasswordFieldText.getText());
            confirmPassword.setManaged(true);
            confirmPassword.setVisible(true);
            confirmPasswordFieldText.setManaged(false);
            confirmPasswordFieldText.setVisible(false);
            confirmEye.setImage(image);
            check = false;

        }
    }

    @FXML
    private void signUp() {
        try {
                password.setText(passwordFieldText.getText());
                confirmPassword.setText(confirmPasswordFieldText.getText());

            if(username.getText()==null||username.getText().equals("")){
                throw new Exception("Field username cannot be empty.");
            }
            if(name.getText()==null||name.getText().equals("")){
                throw new Exception("Field name cannot be empty.");
            }
            if(surname.getText()==null||surname.getText().equals("")){
                throw new Exception("Field surname cannot be empty.");
            }
            if(!isValidEmail(emailAddress.getText().toLowerCase())) {
                throw new Exception("Emails format is wrong.");
            }
            if(doesEmailExist(emailAddress.getText().toLowerCase())) {
                throw new Exception("Account already exists. Please use another email.");
            }
            if(!isValidPassword(password.getText())){
                throw new Exception("Password must contain 8 numbers, letters or capitalised letters.");
            }
            if (!password.getText().equals(confirmPassword.getText())) {
                throw new Exception("Passwords do not match.");
            }
            Users user = initializeUser();
            dbUserFunctions.create(user);
            handleSignIn();
            Session.setExplanation(PasswordHasher.getFullHashingProcessExplanation(password.getText()));
            System.out.println("Signup successful!");

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Signup Failed");
            alert.setHeaderText("Authentication Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    public boolean doesEmailExist(String email) {
return dbUserFunctions.getByEmail(email);
    }

    public static boolean  isValidPassword(String password) {
        if (password == null) return false;

        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        return PASSWORD_PATTERN.matcher(password).matches();
    }
    public static boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    public Users initializeUser(){

       try {
           String rawPassword = password.getText();
           String salt = PasswordHasher.encodeSalt(PasswordHasher.generateSalt());
           String hashedPassword = PasswordHasher.hashPassword(rawPassword, salt);
           return new Users(username.getText(),hashedPassword,salt,name.getText(),surname.getText(),emailAddress.getText().toLowerCase());

       }catch (Exception e){
           e.printStackTrace();
       }
return null;
    }

}
