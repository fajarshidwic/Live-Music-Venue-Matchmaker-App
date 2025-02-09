package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.LoginController;
import com.example.livemusicvenuematchmakerapp.model.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class LoginView {
    private GridPane root;
    private LoginController loginController;

    private OnLoginSuccess onLoginSuccess;

    public interface OnLoginSuccess {
        void handle(User user);
    }

    public LoginView() {
        loginController = new LoginController();
        createAndConfigureUI();
    }

    private void createAndConfigureUI() {
        root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        Button btnLogin = new Button("Login");
        Label lblMessage = new Label();

        root.add(lblUsername, 0, 0);
        root.add(txtUsername, 1, 0);
        root.add(lblPassword, 0, 1);
        root.add(txtPassword, 1, 1);
        root.add(btnLogin, 1, 2);
        root.add(lblMessage, 1, 3);

        btnLogin.setOnAction(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            User user = loginController.login(username, password);
            if (user != null) {
                if (onLoginSuccess != null) {
                    onLoginSuccess.handle(user);
                }
            } else {
                lblMessage.setText("Invalid username or password!");
            }
        });
    }

    public Parent getView() {
        return root;
    }

    public void setOnLoginSuccess(OnLoginSuccess callback) {
        this.onLoginSuccess = callback;
    }
}
