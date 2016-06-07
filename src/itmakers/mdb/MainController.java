package itmakers.mdb;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import itmakers.mdb.elements.FilmEditor;
import itmakers.mdb.elements.VideoGraphics;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class MainController
{
    public MaterialDesignIconView sizeControlIcon;
    public AnchorPane topBar;
    public JFXDialog settingsDialog = new JFXDialog();
    public TilePane moviesTile;
    
    public boolean maximized = false;
    public StackPane mainPane;
    public JFXTabPane tabPane;
    public JFXDialogLayout dialogLayout;

    public void init()
    {
        initSettingsDialog();
    }

    public LinearGradient blueGradient()
    {
        Stop[] stops = new Stop[] { new Stop(0, Color.valueOf("3F51B5")), new Stop(0.5, Color.valueOf("#2196F3")), new Stop(1, Color.valueOf("#03A9F4"))};
        return new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
    }

    public void maximize()
    {
        Main.getStage().setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        Main.getStage().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        Main.getStage().setX(0);
        Main.getStage().setY(0);
        maximized = true;
        sizeControlIcon.setIcon(MaterialDesignIcon.WINDOW_RESTORE);
    }

    public void restore()
    {
        Main.getStage().setWidth(800);
        Main.getStage().setHeight(600);
        Main.getStage().centerOnScreen();
        maximized = false;
        sizeControlIcon.setIcon(MaterialDesignIcon.WINDOW_MAXIMIZE);
    }

    private void resizeMoviesList()
    {
        moviesTile.setPrefWidth(Main.getStage().getWidth());
        moviesTile.setPrefHeight(Main.getStage().getHeight());
        moviesTile.setPrefColumns(Settings.getItemsPerLine());
        moviesTile.setTileAlignment(Pos.CENTER);
        moviesTile.setPrefTileWidth((moviesTile.getPrefWidth()-(moviesTile.getHgap()*(Settings.getItemsPerLine()+1)))/Settings.getItemsPerLine());
        moviesTile.setPrefTileHeight(moviesTile.getPrefTileWidth()*1.5186);
    }

    private void initSettingsDialog()
    {
        AnchorPane pane = new AnchorPane();
        dialogLayout.setBody(pane);
        dialogLayout.setHeading(new Label("Settings"));
    }

    public void showOptions(ActionEvent actionEvent)
    {
//        settingsDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
//        settingsDialog.show(mainPane);
        FilmEditor editor = new FilmEditor(null);
        editor.setTransitionType(JFXDialog.DialogTransition.CENTER);
        editor.show(mainPane);
    }

    public void close(ActionEvent actionEvent)
    {
        Main.getStage().close();
    }

    public void iconfy(ActionEvent actionEvent)
    {
        Main.getStage().setIconified(true);
    }

    public void resize(ActionEvent actionEvent)
    {
        if (maximized)
            restore();
        else
            maximize();
    }
}
