package xyz.alhdo.tasklist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xyz.alhdo.tasklist.UI.controllers.ControllerDashboard;

import java.io.IOException;

public class Main extends Application {
    private Stage mainStage;

    private double x, y;

    private AnchorPane mainContainer;

    public static void main(String[] args) {
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UI/view/MainContainer.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        //set stage borderless
        //primaryStage.initStyle(StageStyle.UNDECORATED);

        //drag it here
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
//        root.setOnMouseDragged(event -> {
//
//            primaryStage.setX(event.getScreenX() - x);
//            primaryStage.setY(event.getScreenY() - y);
//
//        });
        mainStage = primaryStage;
        ControllerDashboard controllerDashboard = loader.getController();
        controllerDashboard.setMainApplication(this);
        controllerDashboard.setMainStage(mainStage);
        primaryStage.show();
    }

    public Stage getMainStage(){
        return mainStage;
    }

    private void initializeMainContainer(){
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Main.class.getResource("UI/view/MainContainer.fxml"));

        try {
            mainContainer = (AnchorPane) loader.load();
            Scene scene = new Scene(mainContainer);
            mainStage.setScene(scene);

            mainStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
