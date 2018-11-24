package xyz.alhdo.tasklist.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import xyz.alhdo.tasklist.Main;
import xyz.alhdo.tasklist.UI.controllers.ControllerDashboard;
import xyz.alhdo.tasklist.database.dao.DaoFactory;
import xyz.alhdo.tasklist.database.dao.TaskDao;
import xyz.alhdo.tasklist.database.dao.UserDao;
import xyz.alhdo.tasklist.models.Task;
import xyz.alhdo.tasklist.models.User;
import xyz.alhdo.tasklist.utils.DateUtil;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TaskController {
    private Stage dialogStage;

    @FXML
    private TextField fieldName;

    @FXML
    private TextArea fieldDescription;

    @FXML
    private DatePicker fieldDateDebut;

    @FXML
    private DatePicker fieldDateFin;


    @FXML
    private ComboBox userList;

    private Main main;

    private Task task;

    private ControllerDashboard parentDashboard;

    public void setMainClass(Main m){
        this.main = m;
        dialogStage = m.getMainStage();
    }
    public void setStage(Stage stage){
        dialogStage = stage;
    }
    public void initialize(){
        List<User> list = DaoFactory.getUserDao().loadAll();
        for (int i = 0; i <list.size() ; i++) {
            userList.getItems().add(list.get(i).getEmail());
        }

    }

    public void setParentDashboard(ControllerDashboard dashboard){
        this.parentDashboard = dashboard;
    }

    public void setTask(Task task){
        this.task = task;
        try {
            fieldName.setText(task.getNom());
            fieldDescription.setText(task.getDescription());
//            fieldDateDebut.setValue(fieldDateDebut.getConverter().fromString(task.getDateDebut().toString()));
        }catch (UnsupportedOperationException e){

            e.printStackTrace();


        }

//        userList.getSelectionModel().select(task.getUser().getEmail());
//        userList.getSelectionModel().select(task.getUser().getEmail());
    }



    @FXML
    public void cancel(){
            dialogStage.close();
    }

    public void save(){
            if(task==null){
                task = new Task();
            }
            task.setNom(fieldName.getText());
            task.setDescription(fieldDescription.getText());
            task.setDateDebut(DateUtil.transformLocalDateToDate(fieldDateDebut.getValue()));
            task.setDateFin(DateUtil.transformLocalDateToDate(fieldDateFin.getValue()));
            UserDao userDao = (UserDao)DaoFactory.getUserDao();
            User user = userDao.findUser(userList.getValue().toString());
            task.setUser(user);

            if(dialogStage.getTitle().startsWith("Create")){
            TaskDao taskDao = (TaskDao)  DaoFactory.getTaskDao();
            taskDao.create(task);
            }
            this.parentDashboard.refresh();
            dialogStage.close();
    }
}
