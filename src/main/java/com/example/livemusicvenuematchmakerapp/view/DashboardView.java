package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.DashboardController;
import com.example.livemusicvenuematchmakerapp.model.User;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class DashboardView {
    private BorderPane root;
    private DashboardController controller;
    private User loggedInUser;

    private OnLogout onLogout;

    private Pane mainContentPane = new StackPane();

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
            setMainContent(createDashboardOverview());
        });
        btnVenues.setOnAction(e -> {
            setMainContent(createVenuesPanel());
        });
        btnAccounts.setOnAction(e -> {
            setMainContent(createAccountsPanel());
        });
        btnLogout.setOnAction(e -> {
            if (onLogout != null) {
                onLogout.handle();
            }
        });

        sidebar.getChildren().addAll(btnDashboard, btnVenues, btnAccounts, btnLogout);
        root.setLeft(sidebar);

        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(createDashboardOverview());
        root.setCenter(mainContentPane);
    }

    private void setMainContent(Parent content) {
        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(content);
    }

    private Parent createDashboardOverview() {
        Label label = new Label("Dashboard Overview: Welcome " + loggedInUser.getFirstName());
        StackPane pane = new StackPane(label);
        return pane;
    }

    private Parent createVenuesPanel() {
        Label label = new Label("Venues Panel: Here you can manage venues.");
        StackPane pane = new StackPane(label);
        return pane;
    }

    private Parent createAccountsPanel() {
        String panelText;
        if ("manager".equalsIgnoreCase(loggedInUser.getRole())) {
            panelText = "Accounts Panel: Manager account management features.";
        } else {
            panelText = "Profile Panel: Update your account details.";
        }
        Label label = new Label(panelText);
        StackPane pane = new StackPane(label);
        return pane;
    }

    public Parent getView() {
        return root;
    }

    public void setOnLogout(OnLogout callback) {
        this.onLogout = callback;
    }
}
