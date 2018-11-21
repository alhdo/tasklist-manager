package xyz.alhdo.tasklist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import xyz.alhdo.tasklist.database.SQLiteJDBCDriverConnection;
import xyz.alhdo.tasklist.database.dao.DAO;
import xyz.alhdo.tasklist.database.dao.DaoFactory;
import xyz.alhdo.tasklist.database.dao.UserDao;
import xyz.alhdo.tasklist.models.User;

import java.io.IOException;

public class Main extends Application {
    private Stage mainStage;

    private AnchorPane mainContainer;

    public static void main(String[] args) {
//        UserDao userDao = new UserDao(SQLiteJDBCDriverConnection.getInstance());
//        User user = new User();
//        user.setNom("Castro");
//        user.setPrenom("Alhdo");
//        user.setAdresse("Ky Tuc Xa my dinh");
//        user.setTelephone("+840934487216");
//        user.setEmail("castroalhdo@gmail.com");
//
//                if(userDao.create(user)){
//                    System.out.println("Saved");
//                }else {
//                    System.out.println("Error");
//                }
            launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            mainStage = primaryStage;
            mainStage.setTitle("Task Management V0.1");
            initializeMainContainer();
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
