package com.example.livemusicvenuematchmakerapp.view;

import com.example.livemusicvenuematchmakerapp.controller.DashboardController;
import com.example.livemusicvenuematchmakerapp.model.User;
import com.example.livemusicvenuematchmakerapp.model.Venue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

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
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");

        Label headerLabel = new Label("LMVM Dashboard");
        headerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #ecf0f1;");
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setMaxWidth(Double.MAX_VALUE);

        Line separator = new Line(0, 0, 150, 0);
        separator.setStroke(Color.web("#ecf0f1"));

        Button btnDashboard = createSidebarButton("Dashboard");
        Button btnEvents = createSidebarButton("Events");
        Button btnVenues = createSidebarButton("Venues");
        Button btnBooking = createSidebarButton("Booking");
        Button btnData = createSidebarButton("Data");
        Button btnAccount = createSidebarButton("Account");
        Button btnReports = createSidebarButton("Reports");
        Button btnLogout = createSidebarButton("Logout");

        btnDashboard.setOnAction(e -> setMainContent(createDashboardOverview()));
        btnEvents.setOnAction(e -> setMainContent(createEventsPanel()));
        btnVenues.setOnAction(e -> setMainContent(createVenuesPanel()));
        btnBooking.setOnAction(e -> setMainContent(createBookingPanel()));
        btnData.setOnAction(e -> setMainContent(createDataPanel()));
        btnAccount.setOnAction(e -> setMainContent(createAccountPanel()));
        btnReports.setOnAction(e -> setMainContent(createReportsPanel()));
        btnLogout.setOnAction(e -> {
            if (onLogout != null) {
                onLogout.handle();
            }
        });

        sidebar.getChildren().addAll(headerLabel, separator,
                btnDashboard, btnEvents, btnVenues, btnBooking, btnData, btnAccount, btnReports,
                btnLogout);
        sidebar.setAlignment(Pos.TOP_CENTER);
        sidebar.setPrefWidth(200);
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
        Label label = new Label("Dashboard Overview: Welcome " + loggedInUser.getFirstName());
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    private Parent createEventsPanel() {
        Label label = new Label("Events Panel: List and details will be here.");
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    private Parent createVenuesPanel() {
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
        TextField txtPrice = new TextField();
        txtPrice.setPromptText("Price / Hour");
        Button btnAddVenue = new Button("Add Venue");
        Label lblAddMsg = new Label();
        addForm.getChildren().addAll(txtName, txtCapacity, txtSuitableFor, txtCategory, txtPrice, btnAddVenue);

        TableView<Venue> tableView = new TableView<>();
        TableColumn<Venue, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Venue, Number> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCapacity()));
        TableColumn<Venue, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        TableColumn<Venue, Number> priceCol = new TableColumn<>("Price / Hour");
        priceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBookingPricePerHour()));
        tableView.getColumns().addAll(nameCol, capacityCol, categoryCol, priceCol);

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

    private Parent createBookingPanel() {
        Label label = new Label("Booking Panel: Hire and cancel bookings here.");
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    private Parent createDataPanel() {
        Label label = new Label("Data Panel: Import CSVs and backup data here.");
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    private Parent createAccountPanel() {
        String panelText;
        if ("manager".equalsIgnoreCase(loggedInUser.getRole())) {
            panelText = "Accounts Panel: Manager account management features.";
        } else {
            panelText = "Profile Panel: Update your account details.";
        }
        Label label = new Label(panelText);
        StackPane pane = new StackPane(label);
        pane.setStyle("-fx-background-color: #ecf0f1;");
        return pane;
    }

    private Parent createReportsPanel() {
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
