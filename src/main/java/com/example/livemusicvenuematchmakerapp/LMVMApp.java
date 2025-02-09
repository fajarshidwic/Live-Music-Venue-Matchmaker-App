package com.example.livemusicvenuematchmakerapp;

import com.example.livemusicvenuematchmakerapp.controller.DashboardController;
import com.example.livemusicvenuematchmakerapp.model.User;
import com.example.livemusicvenuematchmakerapp.view.DashboardView;
import com.example.livemusicvenuematchmakerapp.view.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LMVMApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        DBUtil.initializeDatabase();
        showLoginView();

        primaryStage.setTitle("LMVMApp");
        primaryStage.show();
    }

    private void showLoginView() {
        LoginView loginView = new LoginView();
        loginView.setOnLoginSuccess((User user) -> {
            showDashboardView(user);
        });
        Scene scene = new Scene(loginView.getView(), 800, 600);
        primaryStage.setScene(scene);
    }

    private void showDashboardView(User user) {
        DashboardController dashboardController = new DashboardController();
        DashboardView dashboardView = new DashboardView(dashboardController, user);
        dashboardView.setOnLogout(() -> {
            showLoginView();
        });
        Scene scene = new Scene(dashboardView.getView(), 800, 600);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
