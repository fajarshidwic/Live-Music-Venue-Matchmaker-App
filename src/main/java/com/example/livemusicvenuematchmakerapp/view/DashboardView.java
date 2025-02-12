package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.DashboardController;
import com.example.livemusicvenuematchmakerapp.model.User;
import com.example.livemusicvenuematchmakerapp.model.Venue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

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
        root.setPadding(new Insets(0, 10, 0, 0));

        VBox sidebar = new VBox();
        sidebar.setPrefWidth(200);
        sidebar.prefHeightProperty().bind(root.heightProperty());

        Image logoImage = new Image(getClass().getResourceAsStream("/com/example/livemusicvenuematchmakerapp/images/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(150);
        logoImageView.setPreserveRatio(true);
        StackPane logoContainer = new StackPane(logoImageView);
        logoContainer.setPrefHeight(100); // Upper half fixed height
        logoContainer.setStyle("-fx-background-color: white;");

        VBox navContainer = new VBox(10);
        navContainer.setPadding(new Insets(20));
        String navColor = "manager".equalsIgnoreCase(loggedInUser.getRole()) ? "#e74c3c" : "#2c3e50";
        navContainer.setStyle("-fx-background-color: " + navColor + ";");
        navContainer.setAlignment(Pos.TOP_CENTER);

        Line separator = new Line(0, 0, 150, 0);
        separator.setStroke(Color.web("#ecf0f1"));

        Button btnDashboard = createSidebarButton("Dashboard");
        Button btnEvents = createSidebarButton("Events");
        Button btnVenues = createSidebarButton("Venues");
        Button btnRequests = createSidebarButton("Requests");
        Button btnBooking = createSidebarButton("Booking");
        Button btnOrders = createSidebarButton("Orders");
        Button btnClients = createSidebarButton("Clients");
        Button btnData = createSidebarButton("Data");
        Button btnAccount = createSidebarButton("Account");
        Button btnReports = createSidebarButton("Reports");
        Button btnLogout = createSidebarButton("Logout");

        btnDashboard.setOnAction(e -> setMainContent(createDashboardOverview()));
        btnEvents.setOnAction(e -> setMainContent(createEventPage()));
        btnVenues.setOnAction(e -> setMainContent(createVenuePage()));
        btnRequests.setOnAction(e -> setMainContent(createRequestPage()));
        btnBooking.setOnAction(e -> setMainContent(createBookingPage()));
        btnOrders.setOnAction(e -> setMainContent(createOrderPage()));
        btnClients.setOnAction(e -> setMainContent(createClientPage()));
        btnData.setOnAction(e -> setMainContent(createDataPage()));
        btnAccount.setOnAction(e -> setMainContent(createAccountPage()));
        btnReports.setOnAction(e -> setMainContent(createReportPage()));
        btnLogout.setOnAction(e -> {
            if (onLogout != null) {
                onLogout.handle();
            }
        });

        navContainer.getChildren().addAll(separator, btnDashboard, btnEvents, btnVenues, btnRequests,
                btnBooking, btnOrders, btnClients, btnData, btnAccount, btnReports, btnLogout);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        navContainer.getChildren().add(spacer);
        VBox.setVgrow(navContainer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logoContainer, navContainer);
        sidebar.setAlignment(Pos.TOP_CENTER);
        root.setLeft(sidebar);

        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(createDashboardOverview());
        root.setCenter(mainContentPane);
    }

    private Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #ecf0f1; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8 12 8 12;"
        );
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #34495e; " +
                        "-fx-text-fill: #ecf0f1; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8 12 8 12;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #ecf0f1; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8 12 8 12;"
        ));
        return button;
    }

    private void setMainContent(Parent content) {
        mainContentPane.getChildren().clear();
        mainContentPane.getChildren().add(content);
    }

    private Parent createDashboardOverview() {
        VBox dashboardOverview = new VBox(20);
        dashboardOverview.setPadding(new Insets(20));

        HBox autoMatchPane = new HBox();
        autoMatchPane.setAlignment(Pos.CENTER);
        Button autoMatchButton = new Button("Auto Match");
        autoMatchButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20 10 20;");
        autoMatchButton.setOnAction(e -> {
            controller.autoMatch();
        });
        autoMatchPane.getChildren().add(autoMatchButton);

        HBox chartsPane = new HBox(20);
        chartsPane.setAlignment(Pos.CENTER);
        chartsPane.setPrefHeight(200);

        double income = 0.0;
        double commission = 0.0;

        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
        if (income == 0 && commission == 0) {
            chartData.add(new PieChart.Data("No Data", 1));
        } else {
            chartData.add(new PieChart.Data("Commission", commission));
            chartData.add(new PieChart.Data("Income", income));
        }

        PieChart commissionChart = new PieChart(chartData);
        commissionChart.setTitle("Commission & Income Preview");
        commissionChart.prefWidthProperty().bind(chartsPane.widthProperty().divide(2).subtract(20));
        commissionChart.prefHeightProperty().bind(chartsPane.heightProperty());

        chartsPane.getChildren().add(commissionChart);

        GridPane tablesGrid = new GridPane();
        tablesGrid.setHgap(20);
        tablesGrid.setVgap(20);
        tablesGrid.setPadding(new Insets(20));

        VBox venuePreview = createTablePreview(
                "Venues Preview",
                new String[][] { {"Name", "name"}, {"Capacity", "capacity"}, {"Category", "category"} },
                FXCollections.observableArrayList(controller.getAllVenues())
        );
        VBox requestPreview = createTablePreview(
                "Requests Preview",
                new String[][] { {"Client", "client"}, {"Title", "title"}, {"Date", "date"} },
                FXCollections.observableArrayList(controller.getAllRequests())
        );
        VBox eventPreview = createTablePreview(
                "Events Preview",
                new String[][] { {"Event ID", "eventId"}, {"Title", "title"}, {"Date", "date"} },
                FXCollections.observableArrayList(controller.getAllEvents())
        );
        VBox bookingPreview = createTablePreview(
                "Bookings Preview",
                new String[][] { {"Booking ID", "bookingId"}, {"Event ID", "eventId"}, {"Venue Name", "venueName"} },
                FXCollections.observableArrayList(controller.getAllBookings())
        );
        VBox orderPreview = createTablePreview(
                "Orders Preview",
                new String[][] { {"Order ID", "orderId"}, {"Booking ID", "bookingId"}, {"Commission", "commission"} },
                FXCollections.observableArrayList(controller.getAllOrders())
        );
        VBox clientPreview = createTablePreview(
                "Clients Preview",
                new String[][] { {"Client Name", "clientName"} },
                FXCollections.observableArrayList(controller.getAllClients())
        );

        tablesGrid.add(venuePreview, 0, 0);
        tablesGrid.add(requestPreview, 1, 0);
        tablesGrid.add(eventPreview, 0, 1);
        tablesGrid.add(bookingPreview, 1, 1);
        tablesGrid.add(orderPreview, 0, 2);
        tablesGrid.add(clientPreview, 1, 2);

        for (Node node : tablesGrid.getChildren()) {
            GridPane.setHgrow(node, Priority.ALWAYS);
            GridPane.setVgrow(node, Priority.ALWAYS);
        }

        dashboardOverview.getChildren().addAll(autoMatchPane, chartsPane, tablesGrid);
        VBox.setVgrow(tablesGrid, Priority.ALWAYS);

        return dashboardOverview;
    }


    private VBox createTablePreview(String headerText, String[][] columns, ObservableList<?> data) {
        VBox previewContainer = new VBox(5);
        previewContainer.setPadding(new Insets(10));
        previewContainer.setStyle("-fx-background-color: #ffffff; -fx-border-color: #dcdcdc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label header = new Label(headerText);
        header.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TableView table = new TableView();
        for (String[] colDef : columns) {
            String displayHeader = colDef[0];
            String propertyKey = colDef[1];
            TableColumn column = new TableColumn(displayHeader);
            column.setCellValueFactory(new PropertyValueFactory<>(propertyKey));
            table.getColumns().add(column);
        }
        table.setItems(data);
        VBox.setVgrow(table, Priority.ALWAYS);

        previewContainer.getChildren().addAll(header, table);
        return previewContainer;
    }


    private Parent createEventPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        Label header = new Label("Events");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView table = new TableView();
        TableColumn eventIdCol = new TableColumn("Event ID");
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn artistCol = new TableColumn("Main Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("mainArtist"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn venueCol = new TableColumn("Venue");
        venueCol.setCellValueFactory(new PropertyValueFactory<>("venue"));
        table.getColumns().addAll(eventIdCol, titleCol, artistCol, dateCol, timeCol, venueCol);
        table.setItems(FXCollections.observableArrayList(controller.getAllEvents()));

        VBox.setVgrow(table, Priority.ALWAYS);
        container.getChildren().addAll(header, table);
        return container;
    }

    private Parent createVenuePage() {
        VBox venuePanel = new VBox(10);
        venuePanel.setPadding(new Insets(10));

        Label title = new Label("Manage Venues");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox addForm = new HBox(10);
        TextField txtName = new TextField();
        txtName.setPromptText("Name");
        TextField txtCapacity = new TextField();
        txtCapacity.setPromptText("Capacity");
        TextField txtSuitableFor = new TextField();
        txtSuitableFor.setPromptText("Suitable For");
        TextField txtCategory = new TextField();
        txtCategory.setPromptText("Category");
        TextField txtVenueType = new TextField();
        txtVenueType.setPromptText("Venue Type");
        TextField txtPrice = new TextField();
        txtPrice.setPromptText("Price / Hour");
        Button btnAddVenue = new Button("Add Venue");
        Label lblAddMsg = new Label();
        addForm.getChildren().addAll(txtName, txtCapacity, txtSuitableFor, txtCategory, txtVenueType, txtPrice, btnAddVenue);

        TableView<Venue> tableView = new TableView<>();
        TableColumn<Venue, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Venue, Number> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()));
        TableColumn<Venue, String> suitableForCol = new TableColumn<>("Suitable For");
        suitableForCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSuitableFor()));
        TableColumn<Venue, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        TableColumn<Venue, String> venueTypeCol = new TableColumn<>("Venue Type");
        venueTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVenueType()));
        TableColumn<Venue, Number> priceCol = new TableColumn<>("Price / Hour");
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBookingPricePerHour()));
        tableView.getColumns().addAll(nameCol, capacityCol, suitableForCol, categoryCol, venueTypeCol, priceCol);

        HBox searchPanel = new HBox(10);
        TextField txtSearch = new TextField();
        txtSearch.setPromptText("Search by name");
        Button btnSearch = new Button("Search");
        Label lblSearchResult = new Label();
        searchPanel.getChildren().addAll(txtSearch, btnSearch, lblSearchResult);

        btnAddVenue.setOnAction(e -> {
            try {
                int capacity = Integer.parseInt(txtCapacity.getText());
                double price = Double.parseDouble(txtPrice.getText());
                Venue venue = new Venue();
                venue.setName(txtName.getText());
                venue.setCapacity(capacity);
                venue.setSuitableFor(txtSuitableFor.getText());
                venue.setCategory(txtCategory.getText());
                venue.setVenueType(txtVenueType.getText());
                venue.setBookingPricePerHour(price);
                boolean success = controller.addVenue(venue);
                if(success) {
                    lblAddMsg.setText("Venue added successfully!");
                    tableView.setItems(FXCollections.observableArrayList(controller.getAllVenues()));
                } else {
                    lblAddMsg.setText("Failed to add venue.");
                }
            } catch(NumberFormatException ex) {
                lblAddMsg.setText("Invalid numeric input.");
            }
        });

        btnSearch.setOnAction(e -> {
            String searchName = txtSearch.getText();
            Venue venue = controller.searchVenueByName(searchName);
            if (venue != null) {
                lblSearchResult.setText("Found: " + venue.getName());
            } else {
                lblSearchResult.setText("Venue not found.");
            }
        });

        tableView.setItems(FXCollections.observableArrayList(controller.getAllVenues()));

        venuePanel.getChildren().addAll(title, addForm, lblAddMsg, searchPanel, tableView);
        return venuePanel;
    }


    private Parent createRequestPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        Label header = new Label("Requests");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView table = new TableView();
        TableColumn clientCol = new TableColumn("Client");
        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableColumn artistCol = new TableColumn("Artist");
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        TableColumn audienceCol = new TableColumn("Target Audience");
        audienceCol.setCellValueFactory(new PropertyValueFactory<>("targetAudience"));
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn categoryCol = new TableColumn("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        table.getColumns().addAll(clientCol, titleCol, artistCol, dateCol, timeCol, durationCol, audienceCol, typeCol, categoryCol);
        table.setItems(FXCollections.observableArrayList(controller.getAllRequests()));

        VBox.setVgrow(table, Priority.ALWAYS);
        container.getChildren().addAll(header, table);
        return container;
    }

    private Parent createBookingPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        Label header = new Label("Bookings");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView table = new TableView();
        TableColumn bookingIdCol = new TableColumn("Booking ID");
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        TableColumn eventIdCol = new TableColumn("Event ID");
        eventIdCol.setCellValueFactory(new PropertyValueFactory<>("eventId"));
        TableColumn venueCol = new TableColumn("Venue Name");
        venueCol.setCellValueFactory(new PropertyValueFactory<>("venueName"));
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn timeCol = new TableColumn("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        TableColumn durationCol = new TableColumn("Duration");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        table.getColumns().addAll(bookingIdCol, eventIdCol, venueCol, dateCol, timeCol, durationCol);
        table.setItems(FXCollections.observableArrayList(controller.getAllBookings()));

        VBox.setVgrow(table, Priority.ALWAYS);
        container.getChildren().addAll(header, table);
        return container;
    }

    private Parent createOrderPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        Label header = new Label("Orders");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView table = new TableView();
        TableColumn orderIdCol = new TableColumn("Order ID");
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn bookingIdCol = new TableColumn("Booking ID");
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        TableColumn commissionCol = new TableColumn("Commission");
        commissionCol.setCellValueFactory(new PropertyValueFactory<>("commission"));
        TableColumn totalCol = new TableColumn("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        table.getColumns().addAll(orderIdCol, bookingIdCol, commissionCol, totalCol);
        table.setItems(FXCollections.observableArrayList(controller.getAllOrders()));

        VBox.setVgrow(table, Priority.ALWAYS);
        container.getChildren().addAll(header, table);
        return container;
    }

    private Parent createClientPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        Label header = new Label("Clients");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView table = new TableView();
        TableColumn clientNameCol = new TableColumn("Client Name");
        clientNameCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        table.getColumns().addAll(clientNameCol);
        table.setItems(FXCollections.observableArrayList(controller.getAllClients()));

        VBox.setVgrow(table, Priority.ALWAYS);
        container.getChildren().addAll(header, table);
        return container;
    }

    private Parent createDataPage() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);

        Label header = new Label("Data Import & Backup");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button importVenuesButton = new Button("Import Venues CSV");
        Button importRequestsButton = new Button("Import Requests CSV");

        Label statusLabel = new Label();

        importVenuesButton.setOnAction(e -> {
            try {
                com.example.livemusicvenuematchmakerapp.util.CSVImporter.importVenues("src/main/resources/com/example/livemusicvenuematchmakerapp/csv/venues.csv");
                statusLabel.setText("Venues imported successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Failed to import venues.");
            }
        });

        importRequestsButton.setOnAction(e -> {
            try {
                com.example.livemusicvenuematchmakerapp.util.CSVImporter.importRequests("src/main/resources/com/example/livemusicvenuematchmakerapp/csv/requests.csv");
                statusLabel.setText("Requests imported successfully.");
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Failed to import requests.");
            }
        });

        container.getChildren().addAll(header, importVenuesButton, importRequestsButton, statusLabel);
        return container;
    }


    private Parent createAccountPage() {
        if ("manager".equalsIgnoreCase(loggedInUser.getRole())) {
            TabPane tabPane = new TabPane();

            Tab createUserTab = new Tab("Create User");
            VBox createUserPanel = new VBox(10);
            createUserPanel.setPadding(new Insets(10));
            Label createUserTitle = new Label("Create New User Account");
            createUserTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            TextField txtUsername = new TextField();
            txtUsername.setPromptText("Username");
            PasswordField txtPassword = new PasswordField();
            txtPassword.setPromptText("Password");
            TextField txtFirstName = new TextField();
            txtFirstName.setPromptText("First Name");
            TextField txtLastName = new TextField();
            txtLastName.setPromptText("Last Name");

            ComboBox<String> cmbRole = new ComboBox<>();
            cmbRole.getItems().addAll("Staff", "Manager");
            cmbRole.setValue("Staff");

            TextField txtSecretPin = new TextField();
            txtSecretPin.setPromptText("Secret Auth Pin (for Manager role)");
            txtSecretPin.setVisible(false);
            cmbRole.setOnAction(e -> {
                if ("Manager".equalsIgnoreCase(cmbRole.getValue())) {
                    txtSecretPin.setVisible(true);
                } else {
                    txtSecretPin.setVisible(false);
                }
            });

            Button btnCreateUser = new Button("Create User");
            Label lblCreateUserMsg = new Label();

            btnCreateUser.setOnAction(e -> {
                if ("Manager".equalsIgnoreCase(cmbRole.getValue())) {
                    if (!"909".equals(txtSecretPin.getText())) {
                        lblCreateUserMsg.setText("Invalid secret pin for manager creation!");
                        return;
                    }
                }
                User newUser = new User();
                newUser.setUsername(txtUsername.getText());
                newUser.setPassword(txtPassword.getText());
                newUser.setFirstName(txtFirstName.getText());
                newUser.setLastName(txtLastName.getText());
                newUser.setRole(cmbRole.getValue().toLowerCase());
                boolean success = controller.createStaffAccount(newUser);
                if (success) {
                    lblCreateUserMsg.setText("User account created successfully!");
                } else {
                    lblCreateUserMsg.setText("Failed to create user account.");
                }
            });

            createUserPanel.getChildren().addAll(createUserTitle, txtUsername, txtPassword, txtFirstName, txtLastName, cmbRole, txtSecretPin, btnCreateUser, lblCreateUserMsg);
            createUserTab.setContent(createUserPanel);

            Tab manageUsersTab = new Tab("Manage Users");
            VBox manageUserPanel = new VBox(10);
            manageUserPanel.setPadding(new Insets(10));
            Label manageUserTitle = new Label("Manage Existing User Accounts");
            manageUserTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            TableView<User> userTable = new TableView<>();
            TableColumn<User, String> usernameCol = new TableColumn<>("Username");
            usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
            firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
            TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
            lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
            TableColumn<User, String> roleCol = new TableColumn<>("Role");
            roleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
            userTable.getColumns().addAll(usernameCol, firstNameCol, lastNameCol, roleCol);
            userTable.setItems(FXCollections.observableArrayList(controller.getAllUsers()));

            HBox userActions = new HBox(10);
            Button btnDeleteUser = new Button("Delete User");
            Button btnUpgradeUser = new Button("Upgrade to Manager");
            Button btnUpdateUser = new Button("Update User");
            Label lblManageMsg = new Label();
            userActions.getChildren().addAll(btnDeleteUser, btnUpgradeUser, btnUpdateUser);

            btnDeleteUser.setOnAction(e -> {
                User selectedUser = userTable.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    boolean success = controller.deleteUser(selectedUser.getUsername());
                    if (success) {
                        lblManageMsg.setText("User deleted successfully.");
                        userTable.setItems(FXCollections.observableArrayList(controller.getAllUsers()));
                    } else {
                        lblManageMsg.setText("Failed to delete user.");
                    }
                }
            });

            btnUpgradeUser.setOnAction(e -> {
                User selectedUser = userTable.getSelectionModel().getSelectedItem();
                if (selectedUser != null && "staff".equalsIgnoreCase(selectedUser.getRole())) {
                    boolean success = controller.upgradeUser(selectedUser.getUsername());
                    if (success) {
                        lblManageMsg.setText("User upgraded to Manager.");
                        userTable.setItems(FXCollections.observableArrayList(controller.getAllUsers()));
                    } else {
                        lblManageMsg.setText("Failed to upgrade user.");
                    }
                } else {
                    lblManageMsg.setText("Select a staff user to upgrade.");
                }
            });

            btnUpdateUser.setOnAction(e -> {
                User selectedUser = userTable.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    // For demonstration, update the first name.
                    selectedUser.setFirstName(selectedUser.getFirstName() + " Updated");
                    boolean success = controller.updateUser(selectedUser);
                    if (success) {
                        lblManageMsg.setText("User updated successfully.");
                        userTable.setItems(FXCollections.observableArrayList(controller.getAllUsers()));
                    } else {
                        lblManageMsg.setText("Failed to update user.");
                    }
                }
            });

            manageUserPanel.getChildren().addAll(manageUserTitle, userTable, userActions, lblManageMsg);
            manageUsersTab.setContent(manageUserPanel);

            tabPane.getTabs().addAll(createUserTab, manageUsersTab);
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
            return tabPane;
        } else {
            VBox profilePanel = new VBox(10);
            profilePanel.setPadding(new Insets(10));
            Label title = new Label("Update Profile");
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            PasswordField txtPassword = new PasswordField();
            txtPassword.setPromptText("New Password");
            TextField txtFirstName = new TextField();
            txtFirstName.setPromptText("First Name");
            TextField txtLastName = new TextField();
            txtLastName.setPromptText("Last Name");
            Button btnUpdateProfile = new Button("Update Profile");
            Label lblUpdateMsg = new Label();

            btnUpdateProfile.setOnAction(e -> {
                User updatedUser = new User();
                updatedUser.setUsername(loggedInUser.getUsername());
                updatedUser.setPassword(txtPassword.getText());
                updatedUser.setFirstName(txtFirstName.getText());
                updatedUser.setLastName(txtLastName.getText());
                boolean success = controller.updateProfile(updatedUser);
                if (success) {
                    lblUpdateMsg.setText("Profile updated successfully!");
                } else {
                    lblUpdateMsg.setText("Failed to update profile.");
                }
            });

            profilePanel.getChildren().addAll(title, txtPassword, txtFirstName, txtLastName, btnUpdateProfile, lblUpdateMsg);
            return profilePanel;
        }
    }

    private Parent createReportPage() {
        Label label = new Label("Reports Panel: View summary, pie and bar charts here.");
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    public Parent getView() {
        return root;
    }

    public void setOnLogout(OnLogout callback) {
        this.onLogout = callback;
    }
}
