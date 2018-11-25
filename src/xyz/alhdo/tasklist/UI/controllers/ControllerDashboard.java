package xyz.alhdo.tasklist.UI.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
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
import java.util.List;
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

    @FXML
    private TextField searchUser;

    @FXML
    private TextField searchTask;

    @FXML
    private CheckBox checkbox;

    private boolean isSelected = false;
    private Main main;

    private Stage mainStage;

    private List<User> users;

    public ControllerDashboard(){
        taskObservableList = FXCollections.observableArrayList();
        userObservableList = FXCollections.observableArrayList();


        taskObservableList.addAll(DaoFactory.getTaskDao().loadAll());
        UserDao userDao = (UserDao)DaoFactory.getUserDao();
        users = userDao.loadAll();
        userObservableList.addAll(users);

    }

    public void setMainApplication(Main main){
        this.main = main;
    }

    public void setMainStage(Stage stage){
        this.mainStage = stage;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserListViewCell userListViewCell = new UserListViewCell();
        listView.setItems(taskObservableList);
        listView1.setItems(taskObservableList);
        listUser.setItems(userObservableList);



        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Clicked on "+ listView.getSelectionModel().getSelectedItem().getNom());
                showAddTaskDialog(listView.getSelectionModel().getSelectedItem(),"Edit task");
            }
        });

        listUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showAddUserDialog(listUser.getSelectionModel().getSelectedItem(), "Edit user");
            }
        });


        FilteredList<User> filteredData = new FilteredList<>(userObservableList.sorted(), p -> true);
        searchUser.textProperty().addListener((observable, oldValue, newValue)->{

            filteredData.setPredicate(user -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(String.valueOf(user.getNom()).contains(lowerCaseFilter)){
                    return true;
                }else if(user.getPrenom().toLowerCase().contains(lowerCaseFilter)){
                    return  true;
                }else if(user.getEmail().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(user.getTelephone().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(user.getAdresse().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }

                return false;
            });
        });
        SortedList<User> sortedList = new SortedList<>(filteredData);
        listUser.setItems(sortedList);

        FilteredList<Task> filteredTask = new FilteredList<>(taskObservableList.sorted(), p -> true);
        searchTask.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTask.setPredicate(task -> {
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(task.getNom().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if(task.getDescription().contains(lowerCaseFilter)){
                    return true;
                }else if(task.getDateDebut().toString().toLowerCase().contains(newValue)){
                    return true;
                }else if (task.getDateFin().toString().toLowerCase().contains(newValue)){
                    return true;
                }
                return false;
            });
        });
        SortedList<Task> sortedTask = new SortedList<>(filteredTask);
        listView1.setItems(sortedTask);

        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    isSelected = true;
                    filteredTask.setPredicate(task -> {
                        if(task.getUser()!=null){
                            return true;
                        }
                        return false;
                    });
                }else{
                    isSelected = false;
                    filteredTask.setPredicate(task -> {

                        return true;
                    });
                }
            }
        });
        listView.setCellFactory(taskListView -> new TaskListViewCell());
        listView1.setCellFactory(taskListView -> new TaskListViewCell());
        listUser.setCellFactory(userListView -> new UserListViewCell());
    }

    public void refresh(){
//        taskObservableList.removeAll();
//        taskObservableList.addAll(DaoFactory.getTaskDao().loadAll());
        listView.refresh();
        listView1.refresh();

    }

    public void deleteUser(User user){
        userObservableList.remove(user);
    }
    public void deleteTask(Task task){
        taskObservableList.remove(task);
    }
    public void refreshUserList(){
        listUser.refresh();
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnUsers) {
//            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
            pnlCustomer.setVisible(true);
            pnlOverview.setVisible(false);
            pnlOrders.setVisible(false);
            refreshUserList();
        }
//        if (actionEvent.getSource() == btnMenus) {
//            pnlMenus.setStyle("-fx-background-color : #53639F");
//            pnlMenus.toFront();
//        }
        if (actionEvent.getSource() == btnOverview) {
//            pnlOverview.setStyle("-fx-background-color : #02030A");

            pnlCustomer.setVisible(false);
            pnlOverview.setVisible(true);
            pnlOrders.setVisible(false);
        }
        if(actionEvent.getSource()==btnTasks)
        {
//            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();

            pnlCustomer.setVisible(false);
            pnlOverview.setVisible(false);
            pnlOrders.setVisible(true);
            refresh();
        }
    }

    @FXML
    public void close(){
        Alert bye = new Alert(Alert.AlertType.INFORMATION);

        bye.setTitle("Au revoir!");

        bye.setHeaderText("À bientôt ...");

        bye.setContentText("Merci d'avoir utiliser l'application!");

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
