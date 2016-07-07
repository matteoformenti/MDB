package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Genres;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.services.JSONParser;
import itmakers.mdb.services.OMDBApi;
import itmakers.mdb.storage.Settings;
import itmakers.mdb.storage.MovieStorageService;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import java.io.File;
import java.net.MalformedURLException;
import java.util.List;


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
    private FilmEditor editorManager;
    private boolean useListener = true;
    private boolean isEditingMovie = true;

    void init(FilmEditor ed, Movie m)
    {
        saveAndNextButton.setDisable(true);
        this.movie = m;
        if (movie == null)
        {
            isEditingMovie = false;
            movie = MovieStorageService.prepareNewMovie();
            deleteButton.setDisable(true);
        }
        this.editorManager = ed;
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
                    if (useListener)
                    {
                        movie.getGenres().removeAll(movie.getGenres());
                        genreCheckBox.getCheckModel().getCheckedItems().stream().forEach((c) -> movie.getGenres().add(Genres.valueOf((String) c)));
                    }
                }
        );
    }

    private void loadApi()
    {
        if (api != null)
            if (api.title.equals(titleLabel.getText()))
                return;
        api = new OMDBApi(titleLabel.getText(), JSONParser.Type.movie);
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
        JFXDialog d = new JFXDialog();
        JFXDialogLayout dl = new JFXDialogLayout();
        HBox box = new HBox();
        JFXButton b1 = new JFXButton("Yes, Delete");
        b1.setOnAction(e ->
        {
            MovieStorageService.remove(movie);
            d.close();
            editorManager.close();
        });
        HBox.setHgrow(b1, Priority.ALWAYS);
        JFXButton b2 = new JFXButton("No, Close");
        HBox.setHgrow(b2, Priority.ALWAYS);
        b2.setOnAction(e -> d.close());
        box.getChildren().addAll(b1, b2);
        dl.setActions(box);
        dl.setBody(new Label("Are you sure you want to permanently delete \""+movie.getTitle()+"\"?"));
        d.setContent(dl);
        d.setTransitionType(JFXDialog.DialogTransition.CENTER);
        d.show(Main.controller.mainPane);
    }

    public void saveAndClose(ActionEvent actionEvent)
    {
        if (titleLabel.getText().equals("") || titleLabel.getText().equals(" "))
        {
            Main.dialogManager("Title not valid: unable to create movie");
            return;
        }
        if (!isEditingMovie)
            MovieStorageService.getMovies().stream().forEach(m ->
            {
                if (m.getTitle()!= null)
                if (m.getTitle().equalsIgnoreCase(titleLabel.getText()))
                {
                    Main.dialogManager("This movie is already present in our database");
                    return;
                }
            });
        movie.setTitle(titleLabel.getText());
        movie.setYear(yearLabel.getText());
        movie.setRuntime(runtimeLabel.getText());
        movie.setDirector(directorLabel.getText());
        movie.setPlot(plotArea.getText());
        movie.setPosterImage(posterImageView.getImage());
        movie.setImdbID(imdbID);
        movie.setLocalURL(fileLocationLabel.getText());
        movie.setLocalURLTrailer(trailerField.getText());
        movie.setLocalRating((int) ratingSlider.getValue());
        if (movie.getGraphics() == null)
            generateGraphic(movie);
        else
            movie.getGraphics().reloadGraphics();
        MovieStorageService.updateList(movie);
        resetFields();
        movie = MovieStorageService.prepareNewMovie();
        this.closeDialog(null);
    }

    public void saveAndNext(ActionEvent actionEvent)
    {
        if (titleLabel.getText().equals("") || titleLabel.getText().equals(" "))
        {
            Main.dialogManager("Title not valid: unable to create movie");
            return;
        }
        MovieStorageService.getMovies().stream().forEach(m ->
        {
            if (m.getTitle().equalsIgnoreCase(titleLabel.getText()) || m.getLocalURL().equalsIgnoreCase(fileLocationLabel.getText()))
            {
                Main.dialogManager("Thiis movie is already present in our database");
                return;
            }
        });
        movie.setTitle(titleLabel.getText());
        movie.setYear(yearLabel.getText());
        movie.setRuntime(runtimeLabel.getText());
        movie.setDirector(directorLabel.getText());
        movie.setPlot(plotArea.getText());
        movie.setPosterImage(posterImageView.getImage());
        movie.setImdbID(imdbID);
        movie.setLocalURL(fileLocationLabel.getText());
        movie.setLocalURLTrailer(trailerField.getText());
        movie.setLocalRating((int) ratingSlider.getValue());
        generateGraphic(movie);
        MovieStorageService.updateList(movie);
        resetFields();
        fileLocationLabel.setText(editorManager.getNextMovie().toString());
        if (editorManager.getFilesSize() == 1)
            saveAndNextButton.setDisable(true);
        movie = MovieStorageService.prepareNewMovie();
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
        MovieStorageService.remove(movie);
        editorManager.close();
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

    private void generateGraphic(Movie m)
    {
        m.setGraphics(new MovieGraphics(m));
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
        useListener = false;
        for (Genres g : movie.getGenres())
        {
            int i = 0;
            for (Object o : genreCheckBox.getItems())
            {
                if (o.equals(g.toString()))
                {
                    genreCheckBox.getCheckModel().check(i);
                    break;
                }
                i++;
            }
        }
        useListener=true;
    }
}
