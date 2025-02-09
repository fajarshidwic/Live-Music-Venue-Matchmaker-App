package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.DashboardController;
import com.example.livemusicvenuematchmakerapp.model.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardView {
    private BorderPane root;
    private DashboardController controller;
    private User loggedInUser;

    private OnLogout onLogout;

    public interface OnLogout {
        void handle();
    }

    public DashboardView(DashboardController controller, User loggedInUser) {
        this.controller = controller;
        this.loggedInUser = loggedInUser;
        createAndConfigureUI();
    }

    private void createAndConfigureUI() {
        root = new BorderPane();
        root.setPadding(new Insets(10));

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #f0f0f0;");

        Button btnDashboard = new Button("Dashboard");
        Button btnVenues = new Button("Venues");
        Button btnAccounts = new Button("Accounts");
        Button btnLogout = new Button("Logout");

        btnDashboard.setOnAction(e -> {
        });
        btnVenues.setOnAction(e -> {
        });
        btnAccounts.setOnAction(e -> {
        });
        btnLogout.setOnAction(e -> {
            if (onLogout != null) {
                onLogout.handle();
            }
        });

        sidebar.getChildren().addAll(btnDashboard, btnVenues, btnAccounts, btnLogout);
        root.setLeft(sidebar);

        StackPane mainContent = new StackPane();
        Label placeholder = new Label("Main Content Area: Venue details and other features will be shown here.");
        mainContent.getChildren().add(placeholder);
        root.setCenter(mainContent);
    }

    public Parent getView() {
        return root;
    }

    public void setOnLogout(OnLogout callback) {
        this.onLogout = callback;
    }
}
