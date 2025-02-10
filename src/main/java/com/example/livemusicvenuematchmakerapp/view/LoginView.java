package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.LoginController;
import com.example.livemusicvenuematchmakerapp.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class LoginView {
    private HBox root;
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
        root = new HBox();
        root.setSpacing(0);

        AnchorPane leftPane = new AnchorPane();
        leftPane.setStyle("-fx-background-color: #cccccc;");

        VBox leftContent = new VBox(20);
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(20));

        Image logoImage = new Image(getClass().getResourceAsStream("/com/example/livemusicvenuematchmakerapp/images/logo2.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setPreserveRatio(true);

        Label appTitle = new Label("Live Music Venue Matchmaker App");
        appTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        leftContent.getChildren().addAll(logoImageView, appTitle);

        leftPane.getChildren().add(leftContent);
        AnchorPane.setTopAnchor(leftContent, 0.0);
        AnchorPane.setBottomAnchor(leftContent, 0.0);
        AnchorPane.setLeftAnchor(leftContent, 0.0);
        AnchorPane.setRightAnchor(leftContent, 0.0);

        BorderPane rightPane = new BorderPane();
        rightPane.setStyle("-fx-background-color: #eeeeee;");

        GridPane loginForm = new GridPane();
        loginForm.setPadding(new Insets(20));
        loginForm.setHgap(10);
        loginForm.setVgap(10);
        loginForm.setAlignment(Pos.CENTER);

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setMaxWidth(Double.MAX_VALUE);

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setMaxWidth(Double.MAX_VALUE);

        Button btnLogin = new Button("Login");
        btnLogin.setMaxWidth(Double.MAX_VALUE);

        Label lblMessage = new Label();

        loginForm.add(lblUsername, 0, 0);
        loginForm.add(txtUsername, 1, 0);
        loginForm.add(lblPassword, 0, 1);
        loginForm.add(txtPassword, 1, 1);
        loginForm.add(btnLogin, 1, 2);
        loginForm.add(lblMessage, 1, 3);

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        loginForm.getColumnConstraints().addAll(col1, col2);

        rightPane.setCenter(loginForm);

        root.getChildren().addAll(leftPane, rightPane);

        leftPane.prefWidthProperty().bind(root.widthProperty().divide(2));
        rightPane.prefWidthProperty().bind(root.widthProperty().divide(2));

        HBox.setHgrow(leftPane, Priority.ALWAYS);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

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
