//PROG2 VT2023, Inlämningsuppgift, del 2
//Grupp 057
//Alexander Ellnestam alel6431
//Malika Taverdieva mata6399

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

public class PathFinder extends Application {

    private BorderPane root;
    private VBox center;
    private HBox controls;
    private MenuBar menu;
    private Pane mapContainer;
    private ImageView mapView;
    private Scene scene;
    private Stage stage;
    private Button newPlaceBtn;

    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        root = new BorderPane();
        center = new VBox();
        root.setCenter(center);

        menu = new MenuBar();
        menu.setId("menu");
        center.getChildren().add(menu);

        Menu fileMenu = new Menu("File");
        fileMenu.setId("menuFile");
        menu.getMenus().add(fileMenu);
        MenuItem menuNewMap = new MenuItem("New Map");
        menuNewMap.setId("menuNewMap");
        fileMenu.getItems().add(menuNewMap);
        menuNewMap.setOnAction(new NewMapHandler());
        MenuItem menuOpenFile = new MenuItem("Open");
        menuOpenFile.setId("menuOpenFile");
        fileMenu.getItems().add(menuOpenFile);
        menuOpenFile.setOnAction(new OpenHandler());
        MenuItem menuSaveFile = new MenuItem("Save");
        menuSaveFile.setId("menuSaveFile");
        fileMenu.getItems().add(menuSaveFile);
        //menuSaveFile.setOnAction(new SaveHandler());
        MenuItem menuSaveImage = new MenuItem("Save Image");
        menuSaveImage.setId("menuSaveImage");
        fileMenu.getItems().add(menuSaveImage);
        menuSaveImage.setOnAction(new SaveImageHandler());
        MenuItem menuExit = new MenuItem("Exit");
        menuExit.setId("menuExit");
        fileMenu.getItems().add(menuExit);
        menuExit.setOnAction(new ExitHandler());

        controls = new HBox();
        center.getChildren().add(controls);
        controls.setSpacing(10);
        controls.setPadding(new Insets(20));
        controls.setAlignment(Pos.TOP_CENTER);

        Button findPathBtn = new Button("Find Path");
        findPathBtn.setId("btnFindPath");
        Button showConnectionBtn = new Button("Show Connection");
        showConnectionBtn.setId("btnShowConnection");
        newPlaceBtn = new Button("New Place");
        newPlaceBtn.setId("btnNewPlace");
        newPlaceBtn.setOnMouseClicked(new NewPlaceHandler());
        Button newConnectionBtn = new Button("New Connection");
        newConnectionBtn.setId("btnNewConnection");
        Button changeConnectionBtn = new Button("Change Connection");
        changeConnectionBtn.setId("btnChangeConnection");
        controls.getChildren().addAll(findPathBtn, showConnectionBtn, newPlaceBtn, newConnectionBtn, changeConnectionBtn);

        scene = new Scene(root, 580, 90);
        primaryStage.setTitle("PathFinder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class NewMapHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Image map = new Image("file:europa.gif");
            mapView = new ImageView(map);
            mapContainer = new Pane();
            mapContainer.getChildren().add(mapView);
            center.getChildren().add(mapContainer);

            stage.setWidth(map.getWidth());
            stage.setHeight(map.getHeight() + menu.getHeight() + controls.getHeight());
        }
    }

    class OpenHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileReader fileReader = new FileReader("europa.graph");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No file found!");
                alert.showAndWait();
            }
        }
    }

/*    class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {

            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Can't open file!");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IO-error " + e.getMessage());
                alert.showAndWait();
            }
        }
    }*/

    class SaveImageHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            try {
                WritableImage map = root.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(map, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "IO-error" + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    class ExitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Unsaved changes");
            alert.setContentText("Exit anyway?");
            Optional<ButtonType> response = alert.showAndWait();
            if (response.isPresent() && response.get().equals(ButtonType.CANCEL)) {
                event.consume();
            } else if (response.isPresent() && response.get().equals(ButtonType.OK)) {
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }
        }
    }

    class NewPlaceHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
                mapView.setCursor(Cursor.CROSSHAIR);
                newPlaceBtn.setDisable(true);
                mapView.setPickOnBounds(true);
                mapView.setOnMouseClicked(new ClickHandler());
            }
    }

    class ClickHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Dialog msgBox = new TextInputDialog();
            msgBox.setTitle("Name");
            msgBox.setHeaderText("Name of place:");
            Optional<String> input = msgBox.showAndWait();
            String entered = "";

            if (input.isPresent()) {
                entered = input.get();
            }

            double x = event.getX();
            double y = event.getY();
            Circle mark = new Circle(x, y, 5, Color.BLUE);
            mapContainer.getChildren().add(mark);

            Label place = new Label(entered);
            place.setTranslateX(x);
            place.setTranslateY(y);
            mapContainer.getChildren().add(place);

            mapView.setCursor(Cursor.DEFAULT);
            newPlaceBtn.setDisable(false);
        }
    }
}
