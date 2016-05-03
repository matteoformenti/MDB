package itmakers.mdb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
    private static Stage mainStage;
    public static MainController controller;

    public static void main(String args[]){launch(args);}

    public static Stage getStage()
    {
        return mainStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/MainLayout.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene mainScene = new Scene(root, 800,600);
        mainStage.initStyle(StageStyle.UNDECORATED);
        mainStage.setScene(mainScene);
        mainStage.setResizable(true);
        mainStage.show();
        controller.init();
    }
}
