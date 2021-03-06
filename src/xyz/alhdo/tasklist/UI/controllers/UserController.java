package xyz.alhdo.tasklist.UI.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import xyz.alhdo.tasklist.Main;
import xyz.alhdo.tasklist.database.dao.DaoFactory;
import xyz.alhdo.tasklist.database.dao.UserDao;
import xyz.alhdo.tasklist.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @FXML
    private Button userDeleteBtn;

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
        try {
            fieldFirstname.setText(user.getPrenom());
            fieldLastname.setText(user.getNom());
            fieldAdress.setText(user.getAdresse());
            fieldPhone.setText(user.getTelephone());
            fieldEmail.setText(user.getEmail());
            if(user.getNom()==null){
                userDeleteBtn.setVisible(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void  setStage(Stage stage){
        this.userStage = stage;
    }

    @FXML
    public void cancel(){
                userStage.close();
    }

    @FXML
    public void delete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(user!=null){
                try {
                    UserDao userDao = (UserDao) DaoFactory.getUserDao();
                    userDao.delete(user);
                    this.parentDashboard.deleteUser(user);
                    this.parentDashboard.refreshUserList();
                    userStage.close();
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
        if(fieldFirstname.getText()==null || fieldFirstname.getText().isEmpty()){
            isOk = false;
            errorMessage.add("Le champs Prenom est obligatoire");
        }
        if(fieldLastname.getText()==null || fieldLastname.getText().isEmpty()){
            isOk = false;
            errorMessage.add("Le champs Nom est obligatoire");
        }
        if(fieldEmail.getText()==null || fieldEmail.getText().isEmpty()){
            isOk = false;
            errorMessage.add("Le champs email est obligatoire");
        }
        if(!isOk){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur !");
            StringBuilder stringBuilder = new StringBuilder();
            errorMessage.stream().forEach((x) -> stringBuilder.append("\n" + x));
            error.setHeaderText(stringBuilder.toString());
            error.showAndWait();
        }
        return isOk;
    }

    @FXML public void save(){
        if(controlForm()){
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
            }else{
                userDao.update(user);
            }
            this.parentDashboard.refreshUserList();
            userStage.close();
        }

    }
}
