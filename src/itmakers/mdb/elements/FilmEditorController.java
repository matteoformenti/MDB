package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Genres;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.services.JSONParser;
import itmakers.mdb.services.OMDBApi;
import itmakers.mdb.storage.Settings;
import itmakers.mdb.storage.MovieStorageService;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import java.io.File;
import java.net.MalformedURLException;


public class FilmEditorController
{
    public ImageView posterImageView;
    public JFXTextField titleLabel;
    public JFXTextField fileLocationLabel;
    public JFXTextField trailerField;
    public JFXTextField yearLabel;
    public CheckComboBox genreCheckBox;
    public JFXSlider ratingSlider;
    public JFXTextArea plotArea;
    public Label filmCounter;
    public JFXButton closeButton;
    public JFXButton saveAndCloseButton;
    public JFXButton deleteButton;
    public JFXButton saveAndNextButton;
    public VBox posterOptions;
    public StackPane posterPane;
    public JFXTextField runtimeLabel;
    public JFXTextField directorLabel;
    private OMDBApi api;
    private String imdbID;

    private Movie movie;
    private JFXDialog dialog;
    private FilmEditor editorManager;

    private void loadApi()
    {
        if (api != null)
            if (api.title.equals(titleLabel.getText()))
                return;
        api = new OMDBApi(titleLabel.getText(), JSONParser.Type.movie);
    }

    void init(JFXDialog d, Movie m)
    {
        saveAndNextButton.setDisable(true);
        this.movie = m;
        if (movie == null)
        {
            movie = new Movie();
            deleteButton.setDisable(true);
        }
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
        genreCheckBox.getCheckModel().getCheckedItems().addListener((ListChangeListener) (e) ->
        {
            movie.getGenres().removeAll(movie.getGenres());
            genreCheckBox.getCheckModel().getCheckedItems().stream().forEach((c) -> movie.getGenres().add(Genres.valueOf((String) c)));
        }
        );
    }

    void setEditor(FilmEditor editor)
    {
        this.editorManager = editor;
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
            String value = api.parser.parse("Poster");
            if (value==null)
            {
                Main.dialogManager("This film is not present in our database");
                return;
            }
            posterImageView.setImage(new Image(value));
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
        MovieStorageService.remove(movie);
    }

    public void saveAndClose(ActionEvent actionEvent)
    {
        if (titleLabel.getText().equals("") || titleLabel.getText().equals(" "))
        {
            Main.dialogManager("Title not valid: unable to create a new movie");
            return;
        }
        MovieStorageService.addMovie(new Movie());
        Movie tmp = MovieStorageService.getLastMovie();
        tmp.setTitle(titleLabel.getText());
        tmp.setYear(yearLabel.getText());
        tmp.setRuntime(runtimeLabel.getText());
        tmp.setDirector(directorLabel.getText());
        tmp.setPlot(plotArea.getText());
        tmp.setPosterImage(posterImageView.getImage());
        tmp.setImdbID(imdbID);
        tmp.setLocalURL(fileLocationLabel.getText());
        tmp.setLocalURLTrailer(trailerField.getText());
        tmp.setLocalRating((int) ratingSlider.getValue());
        generateGraphic();
        MovieStorageService.updateList(tmp);
        resetFields();
        movie = new Movie();
        this.closeDialog(null);
    }

    public void saveAndNext(ActionEvent actionEvent)
    {
        if (titleLabel.getText().equals("") || titleLabel.getText().equals(" "))
        {
            Main.dialogManager("Title not valid: unable to create a new movie");
            return;
        }
        MovieStorageService.addMovie(new Movie());
        Movie tmp = MovieStorageService.getLastMovie();
        tmp.setTitle(titleLabel.getText());
        tmp.setYear(yearLabel.getText());
        tmp.setRuntime(runtimeLabel.getText());
        tmp.setDirector(directorLabel.getText());
        tmp.setPlot(plotArea.getText());
        tmp.setPosterImage(posterImageView.getImage());
        tmp.setImdbID(imdbID);
        tmp.setLocalURL(fileLocationLabel.getText());
        tmp.setLocalURLTrailer(trailerField.getText());
        tmp.setLocalRating((int) ratingSlider.getValue());
        generateGraphic();
        MovieStorageService.updateList(tmp);
        resetFields();
        fileLocationLabel.setText(editorManager.getNextMovie().toString());
        if (editorManager.getFilesSize() == 1)
            saveAndNextButton.setDisable(true);
        movie = new Movie();
        filmCounter.setText(editorManager.getFilesSize()-1+" more movies in the selected folder");
    }

    private void resetFields()
    {
        posterImageView.setImage(null);
        titleLabel.setText(null);
        yearLabel.setText(null);
        runtimeLabel.setText(null);
        directorLabel.setText(null);
        plotArea.setText(null);
        imdbID = null;
        fileLocationLabel.setText(null);
        trailerField.setText(null);
        ratingSlider.setValue(1);
    }

    public void closeDialog(ActionEvent actionEvent)
    {
        for (Genres s:movie.getGenres())
        {
            System.out.println(s);
        }
        dialog.close();
    }

    public void downloadInfo(ActionEvent actionEvent)
    {
        if (!titleLabel.getText().equals(""))
        {
            loadApi();
            String year = api.parser.parse("Year");
            String runtime = api.parser.parse("Runtime");
            String director = api.parser.parse("Director");
            String plot = api.parser.parse("Plot");
            String imdbid = api.parser.parse("imdbID");
            String title = api.parser.parse("Title");
            String genre = api.parser.parse("Genre");
            String actors = api.parser.parse("Actors");

            if (title==null)
            {
                Main.dialogManager("This film is not present in our database");
                return;
            }
            for (String actor : actors.split(","))
                if (!movie.getActors().contains(actor))
                {
                    movie.getActors().add(actor);
                    Settings.actors.add(actor);
                }
            yearLabel.setText((year!=null)?year:"null");
            runtimeLabel.setText((runtime!=null)?runtime:"null");
            directorLabel.setText((director!=null)?director:"null");
            plotArea.setText((plot!=null)?plot:"null");
            titleLabel.setText((title!=null)?title:"null");
            imdbID = imdbid;
            if (genre!=null)
                for (String g:genre.split(","))
                    genreCheckBox.getItems().stream().filter(o -> ((String) o).replaceAll("_", "-").equalsIgnoreCase(g.replaceAll("\\s+", ""))).forEach(o -> genreCheckBox.getCheckModel().check(o));
        }
    }

    private void generateGraphic()
    {
        MovieStorageService.getLastMovie().setGraphics(new MovieGraphics(MovieStorageService.getLastMovie()));
    }

    void loadMovieData(Movie movie)
    {
        titleLabel.setText(movie.getTitle());
        yearLabel.setText(movie.getYear());
        runtimeLabel.setText(movie.getRuntime());
        directorLabel.setText(movie.getDirector());
        plotArea.setText(movie.getPlot());
        posterImageView.setImage(movie.getPosterImage());
        fileLocationLabel.setText(movie.getLocalURL());
        trailerField.setText(movie.getLocalURLTrailer());
        ratingSlider.setValue(movie.getLocalRating());
    }
}
