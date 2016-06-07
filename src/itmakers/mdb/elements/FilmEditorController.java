package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Genres;
import itmakers.mdb.Movie;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;


public class FilmEditorController
{
    public ImageView posterImageView;
    public JFXTextField titleLabel;
    public JFXTextField fileLocationLabel;
    public JFXTextField trailerField;
    public JFXTextField yearLabel;
    public JFXTextField RuntimeLabel;
    public CheckComboBox genreCheckBox;
    public JFXSlider ratingSlider;
    public JFXTextField writerLabel;
    public JFXTextArea plotArea;
    public Label filmCounter;
    public JFXButton closeButton;
    public JFXButton saveAndCloseButton;
    public JFXButton deleteButton;
    public JFXButton saveAndNextButton;
    public VBox posterOptions;
    public StackPane posterPane;

    private Movie movie;
    private JFXDialog dialog;

    public void init(JFXDialog d)
    {
        this.dialog = d;
        posterPane.setOnMouseEntered((e) ->
        {
            posterOptions.toFront();
            posterOptions.setStyle("-fx-background-color: rgba(41, 41, 41, 0.7);");
            System.out.println("in");
        });
        posterPane.setOnMouseExited((e) ->
        {
            posterOptions.toBack();
            System.out.println("Out");
        });
        for(Genres g : Genres.values())
            genreCheckBox.getItems().add(g.toString());
    }

    public void removePoster(ActionEvent actionEvent)
    {
    }

    public void choosePoster(ActionEvent actionEvent)
    {
    }

    public void downloadPoster(ActionEvent actionEvent)
    {
    }

    public void chooseMovieFile(ActionEvent actionEvent)
    {

    }

    public void chooseTrailerFile(ActionEvent actionEvent)
    {
    }

    public void chooseActors(ActionEvent actionEvent)
    {
    }

    public void deleteMovie(ActionEvent actionEvent)
    {
    }

    public void saveAndClose(ActionEvent actionEvent)
    {
    }

    public void saveAndNext(ActionEvent actionEvent)
    {
    }

    public void closeDialog(ActionEvent actionEvent)
    {
        dialog.close();
    }
}
