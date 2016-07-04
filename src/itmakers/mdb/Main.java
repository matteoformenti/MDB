package itmakers.mdb;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import itmakers.mdb.storage.MovieStorageService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        mainStage.setScene(mainScene);
        mainStage.setResizable(true);
        mainStage.show();
        controller.init();
        mainStage.setTitle("ITMakers MDB");
        MovieStorageService.loadDB();
        mainStage.setOnCloseRequest(e -> MovieStorageService.saveDB());
    }

    public static JFXDialog dialogManager(String message)
    {
        JFXDialog dialog = new JFXDialog();
        JFXDialogLayout layout = new JFXDialogLayout();
        dialog.setContent(layout);
        Label l = new Label(message);
        l.setWrapText(true);
        layout.setBody(l);
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        Platform.runLater(() -> dialog.show(controller.mainPane));
        return dialog;
    }
}
