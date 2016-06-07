package itmakers.mdb.elements;


import itmakers.mdb.Main;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class VideoGraphics extends AnchorPane
{
    private VideoData videoData;
    private String image = Main.class.getResource("img/iron-man-3-poster1.jpg").toExternalForm();
    private GaussianBlur blur = new GaussianBlur();
    private DropShadow shadow = new DropShadow();

    public VideoGraphics(VideoData video)
    {
        this.videoData = video;
        this.setPrefWidth(200);
        this.setPrefHeight(200);
        this.setOnMouseEntered((event) -> mouseOver());
        this.setOnMouseExited((event) -> mouseAway());
        shadow.setRadius(6.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.05, 0.05, 0.05));
        this.setEffect(shadow);
        mouseAway();
        blur.setRadius(5);
    }

    public void mouseOver()
    {
        this.setEffect(blur);
    }

    public void mouseAway()
    {
        this.setEffect(null);
        this.setEffect(shadow);
        this.setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: no-repeat;"+ "-fx-background-size:cover, auto");
    }
}
