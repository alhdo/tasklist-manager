package xyz.alhdo.tasklist.UI.view;

import com.jfoenix.controls.JFXBadge;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import xyz.alhdo.tasklist.models.Task;

import java.io.IOException;

public class TaskListViewCell extends ListCell<Task> {

    @FXML
    private Label description;

    @FXML
    private Label title;

    @FXML
    private Label fin;

    @FXML
    private Label debut;

    @FXML
    private Label user;

    @FXML
    private Label statut;

    @FXML
    private AnchorPane itemC;

    private FXMLLoader fxmlLoader;


    @Override
    protected void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("Item.fxml"));
                fxmlLoader.setController(this);

                try{
                    fxmlLoader.load();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            title.setText(item.getNom());
            description.setText(item.getDescription());
            debut.setText(item.getDateDebut().toString());
            fin.setText(item.getDateFin().toString());
            if (item.getUser() == null) {
                user.setText("Pas assignée");
            } else {
                user.setText("En cours");
            }
            itemC.setOnMouseEntered(event -> {
                    itemC.setStyle("-fx-background-color : #3c3d4c");
                });
            itemC.setOnMouseExited(event -> {
                itemC.setStyle("-fx-background-color : #2b2b2b");
                });
            setText(null);
            setGraphic(itemC);
        }
    }
}
