package xyz.alhdo.tasklist.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @FXML
    private Button taskDeleteBtn;

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
            if (task.getNom()==null){
                taskDeleteBtn.setVisible(false);
            }
//            fieldDateDebut.setValue(fieldDateDebut.getConverter().fromString(task.getDateDebut().toString()));
        }catch (UnsupportedOperationException e){

            e.printStackTrace();


        }

//        userList.getSelectionModel().select(task.getUser().getEmail());
//        userList.getSelectionModel().select(task.getUser().getEmail());
    }

    public void delete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(task!=null){
                try {
                    TaskDao taskDao = (TaskDao)  DaoFactory.getTaskDao();
                    taskDao.delete(task);
                    this.parentDashboard.deleteTask(task);
                    dialogStage.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    private boolean controlForm(){
        boolean isOk = true;
        List<String> errorMessage = new ArrayList<>();
        if(fieldName.getText()==null || fieldName.getText().isEmpty()){
            isOk=false;
            errorMessage.add("Le champs \"nom\" est obligatoire");
        }
        if(fieldDescription.getText()==null || fieldDescription.getText().isEmpty()){
            isOk=false;
            errorMessage.add("Le champs \"description\" est obligatoire");
        }

        if(fieldDateDebut.getValue()==null || fieldDateDebut.getValue().toString().isEmpty()){
            isOk=false;
            errorMessage.add("Le champs \"date debut \" est obligatoire");
        }

        if(fieldDateFin.getValue()==null || fieldDateFin.getValue().toString().isEmpty()){
            isOk=false;
            errorMessage.add("Le champs \"date fin \" est obligatoire");
        }

        return isOk;
    }



    @FXML
    public void cancel(){
            dialogStage.close();
    }

    public void save(){
        if(controlForm()){
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
}
