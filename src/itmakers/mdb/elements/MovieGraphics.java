package itmakers.mdb.elements;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import itmakers.mdb.Movie;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

//// TODO: 04-Jul-16 fix remove behaviour
//// TODO: 04-Jul-16 add onclick behaviour

public class MovieGraphics extends StackPane
{
    private GaussianBlur blur = new GaussianBlur();
    private DropShadow shadow = new DropShadow();
    private Movie movie;
    private VBox vBox = new VBox();
    private ImageView imageView = new ImageView();

    private List<Node> nodes = new ArrayList<>();

    public MovieGraphics(Movie m)
    {
        movie = m;
        initVBox();
        this.getChildren().addAll(imageView, vBox);
        this.setOnMouseEntered((event) -> mouseOver());
        this.setOnMouseExited((event) -> mouseAway());
        shadow.setRadius(6.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.05, 0.05, 0.05));
        imageView.setEffect(shadow);
        mouseAway();
        blur.setRadius(2);
        imageView.setImage(movie.getPosterImage());
        imageView.setFitWidth(260);
        imageView.setFitHeight(380);
    }

    private void initVBox()
    {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        vBox.getChildren().add(hBox);
        JFXButton modifyButton = new JFXButton();
        modifyButton.setRipplerFill(Paint.valueOf("#2196F3"));
        MaterialDesignIconView modifyIcon = new MaterialDesignIconView(MaterialDesignIcon.PENCIL);
        modifyIcon.setSize("20");
        modifyIcon.glyphSizeProperty().bind(this.widthProperty().divide(10));
        modifyIcon.setFill(Paint.valueOf("#ffffff"));
        modifyButton.setGraphic(modifyIcon);
        JFXButton deleteButton = new JFXButton();
        deleteButton.setRipplerFill(Paint.valueOf("#2196F3"));
        MaterialDesignIconView deleteIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE);
        deleteIcon.setFill(Paint.valueOf("#ffffff"));
        deleteIcon.setSize("20");
        deleteIcon.glyphSizeProperty().bind(this.widthProperty().divide(10));
        deleteButton.setGraphic(deleteIcon);
        modifyButton.setOnAction((e) -> new FilmEditor(movie, null));

        //Title and other things//
        Label titleLabel = new Label(movie.getTitle());
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(0,10,0,10));
        vBox.getChildren().add(titleLabel);
        titleLabel.setTextFill(Color.valueOf("#fefefe"));

        Label genres = new Label();
        movie.getGenres().stream().forEach(g -> genres.setText(g.toString()+", "+genres.getText()));
        genres.setWrapText(true);
        genres.setAlignment(Pos.CENTER);
        genres.setPadding(new Insets(0,0,0,10));
        vBox.getChildren().add(genres);
        genres.setTextFill(Color.valueOf("#fefefe"));

        hBox.getChildren().addAll(modifyButton, deleteButton);
        resizeGraphic(this.getWidth(), this.getHeight());
        titleLabel.setFont(new Font(this.getWidth()/10));
        this.widthProperty().addListener(e -> {
            titleLabel.setFont(new Font(this.getWidth() / 10));
            genres.setFont(new Font(this.getWidth() / 15));
        });
    }

    private void mouseOver()
    {
        shadow.setInput(blur);
        vBox.toFront();
        vBox.setStyle("-fx-background-color: rgba(41, 41, 41, 0.7);");
    }

    private void mouseAway()
    {
        shadow.setInput(null);
        imageView.setEffect(shadow);
        vBox.toBack();
    }

    public void resizeGraphic(double tileWidth, double tileHeight)
    {
        imageView.setFitHeight(tileHeight);
        imageView.setFitWidth(tileWidth);
        nodes.stream().forEach(n -> setPrefWidth(tileWidth));
    }

    void reloadGraphics()
    {
        imageView.setImage(movie.getPosterImage());
    }
}
