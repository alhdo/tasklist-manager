package xyz.alhdo.tasklist.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import xyz.alhdo.tasklist.Main;
import xyz.alhdo.tasklist.database.dao.DaoFactory;
import xyz.alhdo.tasklist.database.dao.UserDao;
import xyz.alhdo.tasklist.models.User;

public class UserController {

    private Stage userStage;

    @FXML
    private TextField fieldFirstname;

    @FXML
    private TextField fieldLastname;

    @FXML
    private TextArea fieldAdress;

    @FXML
    private TextField fieldPhone;

    @FXML
    private TextField fieldEmail;

    private Main main;

    private User user;

    private ControllerDashboard parentDashboard;

    public void setMainClass(Main m){
        this.main = m;
        userStage = m.getMainStage();
    }

    public void initialize(){

    }

    public void setParentDashboard(ControllerDashboard dashboard){
        this.parentDashboard = dashboard;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void  setStage(Stage stage){
        this.userStage = stage;
    }

    @FXML
    public void cancel(){
                userStage.close();
    }

    @FXML public void save(){
            if(user==null){
                user = new User();
            }
            user.setPrenom(fieldLastname.getText());
            user.setNom(fieldFirstname.getText());
            user.setTelephone(fieldPhone.getText());
            user.setAdresse(fieldAdress.getText());
            user.setEmail(fieldEmail.getText());
        UserDao userDao = (UserDao) DaoFactory.getUserDao();
        if(userStage.getTitle().startsWith("Create")){
            userDao.create(user);
        }
        userStage.close();
    }
}
