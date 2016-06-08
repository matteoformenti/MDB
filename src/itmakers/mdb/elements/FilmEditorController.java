package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Genres;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.services.JSONParser;
import itmakers.mdb.services.OMDBApi;
import itmakers.mdb.storage.StorageSettings;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class FilmEditorController
{
    public ImageView posterImageView;
    public JFXTextField titleLabel;
    public JFXTextField fileLocationLabel;
    public JFXTextField trailerField;
    public JFXTextField yearLabel;
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
    public JFXTextField runtimeLabel;
    private OMDBApi api;

    private Movie movie;
    private JFXDialog dialog;

    private void loadApi()
    {
        if (api != null)
            if (api.title.equals(titleLabel.getText()))
                return;
        api = new OMDBApi(titleLabel.getText(), JSONParser.Type.movie);
    }

    public void init(JFXDialog d, Movie m)
    {
        this.movie = m;
        this.dialog = d;
        posterPane.setOnMouseEntered((e) ->
        {
            posterOptions.toFront();
            posterOptions.setStyle("-fx-background-color: rgba(41, 41, 41, 0.7);");
        });
        posterPane.setOnMouseExited((e) -> posterOptions.toBack());
        for(Genres g : Genres.values())
            genreCheckBox.getItems().add(g.toString());
        plotArea.setWrapText(true);
        if (movie == null)
            deleteButton.setDisable(true);
    }

    public void removePoster(ActionEvent actionEvent)
    {
        posterImageView.setImage(null);
    }

    public void choosePoster(ActionEvent actionEvent)
    {
        File f = new FileChooser().showOpenDialog(Main.getStage());
        try
        {
            if (f != null)
                posterImageView.setImage(new Image(f.toURI().toURL().toString()));
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    public void downloadPoster(ActionEvent actionEvent)
    {
        if (!titleLabel.getText().equals(""))
        {
            loadApi();
            List<String> values = api.parser.parse("poster");
            if (values.size() < 1)
            {
                System.out.println("error getting poster");
                return;
            }
            posterImageView.setImage(new Image(values.get(0)));
        }
    }

    public void chooseMovieFile(ActionEvent actionEvent)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video file", "*.mp4", "*.avi", "*.wmv", "*.mpg", "*.mpeg", "*.flv", "*.3gp", "*.mkv"));
        File f = chooser.showOpenDialog(Main.getStage());
        if (f != null)
        {
            fileLocationLabel.setText(f.getAbsolutePath());
        }
    }

    public void chooseTrailerFile(ActionEvent actionEvent)
    {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video file", "*.mp4", "*.avi", "*.wmv", "*.mpg", "*.mpeg", "*.flv", "*.3gp", "*.mkv"));
        File f = chooser.showOpenDialog(Main.getStage());
        if (f != null)
        {
            trailerField.setText(f.getAbsolutePath());
        }
    }

    public void chooseActors(ActionEvent actionEvent)
    {
        ActorChooser actorChooser = new ActorChooser(movie);
        actorChooser.show(Main.controller.mainPane);
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

    public void downloadInfo(ActionEvent actionEvent)
    {
        if (!titleLabel.getText().equals(""))
        {
            loadApi();
            List<String> year = api.parser.parse("year");
            List<String> runtime = api.parser.parse("runtime");
            List<String> writer = api.parser.parse("writer");
            List<String> plot = api.parser.parse("plot");
            List<String> imdbid = api.parser.parse("imdbID");
            List<String> title = api.parser.parse("title");
            List<String> genre = api.parser.parse("genre");
            List<String> actors = api.parser.parse("actors");

            if (title.size() < 1)
            {
                Main.dialogManager("This film is not present in our database");
                return;
            }
            yearLabel.setText((year.size()>0)?year.get(0):"null");
            runtimeLabel.setText((runtime.size()>0)?runtime.get(0):"null");
            writerLabel.setText((writer.size()>0)?writer.get(0):"null");
            plotArea.setText((plot.size()>0)?plot.get(0):"null");
            titleLabel.setText((title.size()>0)?title.get(0):"null");
            System.out.println(genre);
            if (genre.size() != 0)
                for (String g:genre.get(0).split(","))
                    genreCheckBox.getItems().stream().filter(o -> ((String) o).replaceAll("_", "-").equalsIgnoreCase(g.replaceAll("\\s+", ""))).forEach(o -> genreCheckBox.getCheckModel().check(o));
        }
    }
}
