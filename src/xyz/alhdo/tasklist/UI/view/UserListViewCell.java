package xyz.alhdo.tasklist.UI.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import xyz.alhdo.tasklist.models.User;

import java.io.IOException;

public class UserListViewCell extends ListCell<User> {
    @FXML
    private Label firstname;

    @FXML
    private Label lastname;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private Label adress;

    @FXML
    private HBox itemB;

    private FXMLLoader fxmlLoader;
    @Override
    protected void updateItem(User item, boolean empty) {
        super.updateItem(item, empty);
        if(empty || item == null){
            setText(null);
            setGraphic(null);
        }else {
            if(fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("UserItem.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                firstname.setText(item.getPrenom());
                lastname.setText(item.getNom());
                phone.setText(item.getTelephone());
                email.setText(item.getEmail());

                adress.setText(item.getAdresse());
                itemB.setOnMouseEntered(event -> {
                    itemB.setStyle("-fx-background-color : #0A0E3F");
                });
                itemB.setOnMouseExited(event -> {
                    itemB.setStyle("-fx-background-color : #02030A");
                });
                setText(null);
                setGraphic(itemB);
            }
        }

}
