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
        // Use an HBox as the root container without extra padding so sections reach the window edges.
        root = new HBox();
        root.setSpacing(0);
        // (No padding on the root, so the left side touches the window's very left edge)

        // --------------------------
        // Left Section: Logo & Title
        // --------------------------
        // Use an AnchorPane so the background covers the entire left section.
        AnchorPane leftPane = new AnchorPane();
        leftPane.setStyle("-fx-background-color: #cccccc;"); // Darker grey for left side
        // The leftPane will cover 50% of the width.

        // Create the content for the left section in a VBox.
        VBox leftContent = new VBox(20);
        leftContent.setAlignment(Pos.CENTER);
        leftContent.setPadding(new Insets(20));

        // Load the logo image from resources.
        Image logoImage = new Image(getClass().getResourceAsStream("/com/example/livemusicvenuematchmakerapp/images/logo2.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setPreserveRatio(true);

        // Application title.
        Label appTitle = new Label("Live Music Venue Matchmaker App");
        appTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        leftContent.getChildren().addAll(logoImageView, appTitle);

        // Anchor leftContent to all sides so it fills the leftPane.
        leftPane.getChildren().add(leftContent);
        AnchorPane.setTopAnchor(leftContent, 0.0);
        AnchorPane.setBottomAnchor(leftContent, 0.0);
        AnchorPane.setLeftAnchor(leftContent, 0.0);
        AnchorPane.setRightAnchor(leftContent, 0.0);

        // --------------------------
        // Right Section: Login Form
        // --------------------------
        // Use a BorderPane so that the login form can scale.
        BorderPane rightPane = new BorderPane();
        rightPane.setStyle("-fx-background-color: #eeeeee;"); // Lighter grey for right side

        // Create the login form as a GridPane (mostly preserving your original structure).
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

        // Add components to the GridPane.
        loginForm.add(lblUsername, 0, 0);
        loginForm.add(txtUsername, 1, 0);
        loginForm.add(lblPassword, 0, 1);
        loginForm.add(txtPassword, 1, 1);
        loginForm.add(btnLogin, 1, 2);
        loginForm.add(lblMessage, 1, 3);

        // Make the second column fill available width.
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        loginForm.getColumnConstraints().addAll(col1, col2);

        // Place the login form in the center of the rightPane.
        rightPane.setCenter(loginForm);

        // --------------------------
        // Assemble the Root Layout
        // --------------------------
        // Add leftPane and rightPane to the root HBox.
        root.getChildren().addAll(leftPane, rightPane);

        // Bind each section's width to 50% of the root's width.
        leftPane.prefWidthProperty().bind(root.widthProperty().divide(2));
        rightPane.prefWidthProperty().bind(root.widthProperty().divide(2));

        // Ensure both sections grow to fill the available height.
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
