package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Genres;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;

public class FilmEditor extends JFXDialog
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
    private Parent content;
    
    public FilmEditor(Movie m)
    {
        loadUI();
    }

    public FilmEditor(){}

    private void loadUI()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/Dialog.fxml"));
            content = loader.load();
            FilmEditorController c = loader.getController();
            c.init(this);
            JFXDialogLayout layout = new JFXDialogLayout();
            this.setPrefSize(Main.getStage().getScene().getWidth(), Main.getStage().getScene().getHeight());
            layout.setBody(content);
            layout.setPadding(new Insets(-1));
            this.setContent(layout);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
        this.close();
    }
}
