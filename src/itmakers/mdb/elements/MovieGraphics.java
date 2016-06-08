package itmakers.mdb.elements;


import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MovieGraphics extends StackPane
{
    private String image = Main.class.getResource("img/iron-man-3-poster1.jpg").toExternalForm();
    private GaussianBlur blur = new GaussianBlur();
    private DropShadow shadow = new DropShadow();
    private Movie movie;
    private VBox vBox = new VBox();


    public MovieGraphics(Movie m)
    {
        movie = m;
        ImageView imageView = new ImageView();
        this.getChildren().addAll(imageView, vBox);
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
        blur.setRadius(2);
        imageView.setImage(new Image(image));
        imageView.setFitWidth(260);
        imageView.setFitHeight(380);
    }

    public void mouseOver()
    {
        shadow.setInput(blur);
        vBox.toFront();
        vBox.setStyle("-fx-background-color: rgba(41, 41, 41, 0.7);");
    }

    public void mouseAway()
    {
        shadow.setInput(null);
        this.setEffect(null);
        this.setEffect(shadow);
        vBox.toBack();
    }
}
