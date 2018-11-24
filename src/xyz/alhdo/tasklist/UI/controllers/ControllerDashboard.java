package xyz.alhdo.tasklist.UI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import xyz.alhdo.tasklist.Main;
import xyz.alhdo.tasklist.UI.view.TaskListViewCell;
import xyz.alhdo.tasklist.UI.view.UserListViewCell;
import xyz.alhdo.tasklist.database.dao.DaoFactory;
import xyz.alhdo.tasklist.database.dao.UserDao;
import xyz.alhdo.tasklist.models.Task;
import xyz.alhdo.tasklist.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDashboard implements Initializable {

    @FXML
    private ListView<Task> listView;

    @FXML
    private ListView<Task> listView1;

    @FXML
    private ListView<User> listUser;


    private ObservableList<Task> taskObservableList;

    private ObservableList<User> userObservableList;

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnTasks;

    @FXML
    private Button btnUsers;



    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    private Main main;

    private Stage mainStage;

    public ControllerDashboard(){
        taskObservableList = FXCollections.observableArrayList();
        userObservableList = FXCollections.observableArrayList();

        taskObservableList.addAll(DaoFactory.getTaskDao().loadAll());
        UserDao userDao = (UserDao)DaoFactory.getUserDao();
        userObservableList.addAll(userDao.loadAll());

    }

    public void setMainApplication(Main main){
        this.main = main;
    }

    public void setMainStage(Stage stage){
        this.mainStage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listView.setItems(taskObservableList);
        listView1.setItems(taskObservableList);
        listUser.setItems(userObservableList);

//        ContextMenu contextMenu = new ContextMenu();
//
//        MenuItem deleteItem = new MenuItem("Delete");
//        contextMenu.getItems().add(deleteItem);
//
//        listView.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, event -> {
//            contextMenu.show(listView, event.getScreenX(), event.getSceneY());
//            event.consume();
//        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked on "+ listView.getSelectionModel().getSelectedItem().getNom());
                showAddTaskDialog(listView.getSelectionModel().getSelectedItem(),"Edit task");
            }
        });
        listView.setCellFactory(taskListView -> new TaskListViewCell());
        listView1.setCellFactory(taskListView -> new TaskListViewCell());
        listUser.setCellFactory(userListView -> new UserListViewCell());
    }

    public void refresh(){
        taskObservableList.removeAll();
        taskObservableList.addAll(DaoFactory.getTaskDao().loadAll());
        userObservableList.removeAll();
        userObservableList.addAll(DaoFactory.getUserDao().loadAll());
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUsers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
//        if (actionEvent.getSource() == btnMenus) {
//            pnlMenus.setStyle("-fx-background-color : #53639F");
//            pnlMenus.toFront();
//        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnTasks)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }

    @FXML
    public void close(){
        Alert bye = new Alert(Alert.AlertType.INFORMATION);

        bye.setTitle("Good bye!");

        bye.setHeaderText("See you soon...");

        bye.setContentText("Thanks for using this program !");

        bye.showAndWait();

        this.main.getMainStage().close();
    }

    @FXML
    public void addTask(){
        showAddTaskDialog(new Task(), "Create a new task");
    }


    @FXML
    public void addUser(){
        showAddUserDialog(new User(), "Create a new user");
    }

    public void showAddTaskDialog(Task task, String title){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.main.getClass().getResource("UI/view/TaskAdd.fxml"));

            AnchorPane pane = (AnchorPane)loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);
            Scene scene = new Scene(pane);
            stage.setScene(scene);


            TaskController taskController = loader.getController();
            taskController.setTask(task);
            taskController.setMainClass(this.main);
            taskController.setStage(stage);
            taskController.setParentDashboard(this);

            stage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showAddUserDialog(User user, String title){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.main.getClass().getResource("UI/view/UserAdd.fxml"));

            AnchorPane pane = (AnchorPane)loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);

            stage.initOwner(mainStage);
            Scene scene = new Scene(pane);

            stage.setScene(scene);


            UserController userController = loader.getController();
            userController.setUser(user);
            userController.setMainClass(this.main);
            userController.setStage(stage);
            userController.setParentDashboard(this);
            stage.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
