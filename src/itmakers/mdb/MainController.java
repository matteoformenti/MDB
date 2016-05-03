package itmakers.mdb;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import com.sun.media.jfxmedia.logging.Logger;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import itmakers.mdb.elements.VideoGraphics;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Screen;

public class MainController
{

    public AnchorPane topBar;
    public JFXButton showMenu;
    public JFXTabPane primaryTabPane;
    public Tab baseTab;
    public JFXTabPane secondaryTabPane;
    public Tab moviesTab;
    public Tab tvSeriesTab;
    public Tab detailsTab;
    public AnchorPane imagePane;
    public Tab playerTab;
    public JFXButton closeButton;
    public JFXButton maximizeButton;
    public JFXButton iconifyButton;
    public ScrollPane moviesScrollPane;
    public MaterialDesignIconView sizeControlIcon;
    public TilePane moviesTile;

    public boolean maximized = false;
    public BorderPane MainPane;

    public void init()
    {
        showMenu.setOnMouseClicked((event) -> System.out.println("showing menu"));
        closeButton.setOnMouseClicked((event) -> Main.getStage().close());
        iconifyButton.setOnMouseClicked((event) -> Main.getStage().setIconified(true));
        maximizeButton.setOnMouseClicked((event) -> {if (!maximized) maximize(); else  restore();});
        moviesScrollPane.setPrefWidth(Main.getStage().getWidth());
        moviesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        for (int i = 0; i < 50; i++) moviesTile.getChildren().add(new VideoGraphics(null));
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
}
