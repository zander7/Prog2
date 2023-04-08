package com.example.programmering2;

// PROG2 VT2023, Inlämningsuppgift, del 2
// Grupp 057
// Alexander Ellnestam alel6431


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.event.EventHandler;


public class PathFinder extends Application {

    private BorderPane root;
    private Pane pane = new Pane();
    private Stage stage;
    private Scene scene = new Scene(pane);

    public void start(Stage stage) {
        this.stage = stage;
        root = new BorderPane();
        VBox vbox = new VBox();
        root.setTop(vbox);
        root.setStyle("-fx-font-size:16");

        MenuBar menu = new MenuBar();
        vbox.getChildren().add(menu);

        Menu menuFile = new Menu("File");
        menu.getMenus().add(menuFile);
        MenuItem menuNewMap = new MenuItem("New Map");
        menuFile.getItems().add(menuNewMap);
        MenuItem menuOpenFile = new MenuItem("Open");
        menuFile.getItems().add(menuOpenFile);
        MenuItem menuSaveFile = new MenuItem("Save");
        menuFile.getItems().add(menuSaveFile);
        MenuItem menuSaveImage = new MenuItem("Save Image");
        menuFile.getItems().add(menuSaveImage);
        MenuItem menuExit = new MenuItem("Exit");
        menuFile.getItems().add(menuExit);


        FlowPane controls = new FlowPane();
        vbox.getChildren().add(controls);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        controls.setHgap(5);

        Button btnFindPath = new Button("Find Path");
        btnFindPath.setOnAction(new FindPathHandler());
        Button btnShowConnection = new Button("Show Connection");
        btnShowConnection.setOnAction(new ShowConnectionHandler());
        Button btnNewPlace = new Button("New place");
        btnNewPlace.setOnAction(new NewPlaceHandler());
        Button btnChangeConnection = new Button("Change Connection");
        btnChangeConnection.setOnAction(new ChangeConnectionHandler());
        Button btnNewConnection = new Button("New Connection");
        btnNewConnection.setOnAction(new NewConnectionHandler());

        controls.getChildren().addAll(btnFindPath, btnShowConnection, btnNewPlace, btnChangeConnection, btnNewConnection);


        Scene scene = new Scene(root, 500, 300);
        stage.setTitle("PathFinder");
        stage.setScene(scene);
        stage.show();
    }

    private class FindPathHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }

    private class ShowConnectionHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }

    private class NewPlaceHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }

    private class ChangeConnectionHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }

    private class NewConnectionHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }


    }
}

