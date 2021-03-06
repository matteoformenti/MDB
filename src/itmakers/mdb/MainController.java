package itmakers.mdb;

import com.jfoenix.controls.*;
import itmakers.mdb.elements.FilmEditor;
import itmakers.mdb.elements.MovieGraphics;
import itmakers.mdb.storage.MovieStorageService;
import itmakers.mdb.storage.Settings;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static itmakers.mdb.services.JSONParser.Type.movie;

public class MainController
{
    public StackPane mainPane;
    public StackPane mainUI;
    public JFXTabPane tabPane;
    public Tab moviesTab;
    public ScrollPane moviesScrollPane;
    public Tab showTab;
    public ScrollPane tvShowsScrollPane;
    public JFXButton showPopupButton;
    public AnchorPane topBar;
    public JFXPopup popup;
    public JFXListView popupList;

    private JFXDialog infoDialog = new JFXDialog();
    private JFXDialog settingsDialog = new JFXDialog();
    private JFXDialog movieEditorDialog = new JFXDialog();

    private TilePane moviesTilePane = new TilePane();

    void init()
    {
        initSettingsDialog();
        initMovieEditorDialog();
        initPopup();
        moviesScrollPane.setContent(moviesTilePane);
        moviesTilePane.setVgap(20);
        moviesTilePane.setHgap(20);
        moviesTilePane.setPadding(new Insets(20));
        resizeMoviesList();
        mainPane.widthProperty().addListener((e) -> resizeMoviesList());
        mainPane.heightProperty().addListener((e) -> resizeMoviesList());
    }

    public void addToMoviesList(Movie m)
    {
        moviesTilePane.getChildren().add(m.getGraphics());
        resizeMoviesList();
    }

    private void resizeMoviesList()
    {
        moviesTilePane.setPrefWidth(Main.getStage().getWidth());
        moviesTilePane.setPrefHeight(Main.getStage().getHeight());
        moviesTilePane.setPrefColumns(Settings.getMoviesPerRow());
        moviesTilePane.setTileAlignment(Pos.CENTER);
        moviesTilePane.setPrefTileWidth((moviesTilePane.getPrefWidth()-40-(moviesTilePane.getHgap()*(Settings.getMoviesPerRow()+1)))/Settings.getMoviesPerRow());
        moviesTilePane.setPrefTileHeight(moviesTilePane.getPrefTileWidth()*1.5186);
        for (Node g : moviesTilePane.getChildren())
            ((MovieGraphics)g).resizeGraphic(moviesTilePane.getTileWidth(), moviesTilePane.getTileHeight());
    }

    private void initMovieEditorDialog()
    {
        JFXDialogLayout layout = new JFXDialogLayout();
        movieEditorDialog.setContent(layout);
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        layout.setBody(vbox);
        AnchorPane anchorPane = new AnchorPane();
        Label title = new Label("Select movie editor mode");
        title.setAlignment(Pos.CENTER);
        anchorPane.getChildren().add(title);
        AnchorPane.setLeftAnchor(title,0.0);
        AnchorPane.setRightAnchor(title,0.0);
        vbox.getChildren().add(anchorPane);
        vbox.setSpacing(20);
        HBox hBox = new HBox();
        ToggleGroup group = new ToggleGroup();
        JFXRadioButton singleRadio = new JFXRadioButton("Single entry mode");
        singleRadio.setSelected(true);
        JFXRadioButton folderRadio = new JFXRadioButton("Folder entry mode");
        singleRadio.setToggleGroup(group);
        folderRadio.setToggleGroup(group);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(singleRadio, folderRadio);
        vbox.getChildren().add(hBox);
        HBox hBox1 = new HBox();
        group.selectedToggleProperty().addListener((e) -> {
            if (e.toString().contains("Folder entry mode"))
                hBox1.setDisable(false);
            else
                hBox1.setDisable(true);
        });
        Label folderPosition = new Label("No folder selected");
        JFXButton folderButton = new JFXButton("Choose your movies folder");
        HBox.setHgrow(folderButton, Priority.ALWAYS);
        HBox.setHgrow(folderPosition, Priority.ALWAYS);
        hBox1.setDisable(true);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(20);
        hBox1.getChildren().addAll(folderButton, folderPosition);
        folderButton.setRipplerFill(Paint.valueOf("#2196F3"));
        folderButton.setStyle("-fx-background-color: #eeeeee");
        folderPosition.setPadding(new Insets(5,0,0,0));
        vbox.getChildren().add(hBox1);
        folderButton.setOnAction((event ->
        {
            DirectoryChooser chooser = new DirectoryChooser();
            File folder = chooser.showDialog(Main.getStage());
            if (folder!=null)
                folderPosition.setText(folder.getAbsolutePath());
        }));
        movieEditorDialog.setDialogContainer(mainPane);
        movieEditorDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        JFXButton openEditorButton = new JFXButton("Open Editor");
        openEditorButton.setOnAction((event) ->
        {
            if (((JFXRadioButton)group.getSelectedToggle()).getText().equals("Single entry mode"))
            {
                FilmEditor editor = new FilmEditor(null, null);
                movieEditorDialog.close();
            }
            else if (((JFXRadioButton)group.getSelectedToggle()).getText().equals("Folder entry mode") && !folderPosition.getText().equals("No folder selected"))
            {
                FilmEditor editor = new FilmEditor(null, folderPosition.getText());
                movieEditorDialog.close();
            }
            else if (((JFXRadioButton)group.getSelectedToggle()).getText().equals("Folder entry mode") && folderPosition.getText().equals("No folder selected"))
            {
                Main.dialogManager("Please, choose a folder!");
            }
        });
        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.getChildren().add(openEditorButton);
        AnchorPane.setLeftAnchor(openEditorButton,0.0);
        AnchorPane.setRightAnchor(openEditorButton,0.0);
        vbox.getChildren().add(anchorPane1);
    }

    private void initPopup()
    {
        popup.setContent(popupList);
        popup.setSource(showPopupButton);
        popup.setPopupContainer(mainPane);
        popup.setPrefWidth(140);
        popupList.setPrefWidth(140);
        popupList.setFixedCellSize(30);
        popupList.setPadding(new Insets(-1));
        popupList.getStylesheets().add(getClass().getResource("popup.css").toExternalForm());
    }

    private void initSettingsDialog()
    {
    }

    public void showPopup(ActionEvent actionEvent)
    {
        popup.setContent(popupList);
        popup.setSource(showPopupButton);
        popup.setPopupContainer(mainPane);
        popup.setPrefWidth(140);
        popupList.setPrefWidth(140);
        popupList.setFixedCellSize(30);
        popupList.setPadding(new Insets(-1));
        popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, -1, 1);
    }

    public void showSettingsDialog(ActionEvent actionEvent)
    {
    }

    public void addMovie(ActionEvent actionEvent)
    {
        popup.close();
        movieEditorDialog.show();
    }

    public void addTvShow(ActionEvent actionEvent)
    {
        popup.close();
    }

    public void showInfo(ActionEvent actionEvent)
    {
        popup.close();
    }

    public void removeFromMoviesList(Movie movie)
    {
        moviesTilePane.getChildren().remove(movie.getGraphics());
    }

    public ObservableList getMovieGraphics()
    {
        return moviesTilePane.getChildren();
    }

    public void searchMovie(ActionEvent actionEvent)
    {
    }

    public void showMovieFilter(ActionEvent actionEvent)
    {
        List<Movie> movies = new ArrayList();
        Main.controller.getMovieGraphics().stream().forEach(g -> movies.add(((MovieGraphics)g).getMovie()));
        popup.close();
        JFXDialog filterDialog = new JFXDialog();
        JFXDialogLayout layout = new JFXDialogLayout();
        filterDialog.setContent(layout);

        VBox vbox = new VBox();
        layout.setBody(vbox);
        Label title = new Label("Filter movies");
        vbox.getChildren().add(title);

        ToggleGroup group = new ToggleGroup();
        JFXRadioButton byGenreRB = new JFXRadioButton("Filter by genre");
        byGenreRB.setSelected(true);
        JFXRadioButton byLengthRB = new JFXRadioButton("Filter by length");
        JFXRadioButton byRatingRB = new JFXRadioButton("Filter by rating");
        JFXRadioButton byYearRB= new JFXRadioButton("Filter by year");
        FlowPane RBPane = new FlowPane();
        RBPane.setHgap(20);
        RBPane.setVgap(20);
        RBPane.getChildren().addAll(byGenreRB, byLengthRB, byRatingRB, byYearRB);
        group.getToggles().addAll(byGenreRB, byLengthRB, byRatingRB, byYearRB);
        vbox.getChildren().add(RBPane);
        filterDialog.setPrefWidth(mainPane.getWidth()/2);
        mainPane.widthProperty().addListener((e) -> layout.setPrefWidth(mainPane.getWidth()/2));
        RBPane.setPadding(new Insets(10));
        HBox filterPane = new HBox();
        vbox.getChildren().addAll(new Separator(), filterPane, new Separator());

        VBox byGenrePane = new VBox();
        byGenrePane.setPadding(new Insets(10,0,10,0));
        CheckComboBox genreBox = new CheckComboBox();
        HBox a = new HBox();
        a.getChildren().add(genreBox);
        filterPane.widthProperty().addListener(c -> genreBox.setPrefWidth(filterPane.getWidth()));
        Arrays.stream(Genres.values()).forEach(g -> genreBox.getItems().add(g.toString()));
        Label resultsLabel = new Label("Results: "+ MovieStorageService.getMovies().size());
        byGenrePane.getChildren().addAll(a, resultsLabel);
        filterPane.getChildren().add(byGenrePane);
        genreBox.getCheckModel().getCheckedItems().addListener((ListChangeListener) (e) ->
        {
            movies.removeAll(movies);
            if (genreBox.getCheckModel().getCheckedItems().size() == 0)
                MovieStorageService.getMovies().stream().forEach(m -> movies.add(m));
            else
            {
                Main.controller.getMovieGraphics().stream().forEach(g ->
                {
                    Movie movie = ((MovieGraphics)g).getMovie();
                    boolean f = false;
                    for (Object o : genreBox.getCheckModel().getCheckedItems())
                    {
                        for (Genres gen : movie.getGenres())
                        {
                            f = false;
                            if (gen.toString().equalsIgnoreCase(o.toString()))
                            {
                                f = true;
                                break;
                            }
                        }
                    }
                    if (f)
                        movies.add(movie);
                });
            }
            resultsLabel.setText("Results: "+movies.size());
        });

        group.selectedToggleProperty().addListener(e ->
        {
            switch (((JFXRadioButton)group.getSelectedToggle()).getText())
            {
                case "Filter by genre":
                    filterPane.getChildren().removeAll(filterPane.getChildren());
                    filterPane.getChildren().add(byGenrePane);
                    break;
                case "Filter by length":
                    filterPane.getChildren().removeAll(filterPane.getChildren());
                    break;
                case "Filter by rating":
                    filterPane.getChildren().removeAll(filterPane.getChildren());
                    break;
                case "Filter by year":
                    filterPane.getChildren().removeAll(filterPane.getChildren());
                    break;
            }
        }
        );

        JFXButton filterButton = new JFXButton("Filter");
        filterButton.setOnAction(event ->
        {
            Main.controller.getMovieGraphics().removeAll(Main.controller.getMovieGraphics());
            movies.stream().forEach(m -> Main.controller.addToMoviesList(m));
            filterDialog.close();
        });
        JFXButton resetButton = new JFXButton("Reset");
        resetButton.setOnAction(event ->
        {
            Main.controller.getMovieGraphics().removeAll(Main.controller.getMovieGraphics());
            MovieStorageService.getMovies().stream().forEach(m -> Main.controller.addToMoviesList(m));
            filterDialog.close();
        });
        FlowPane actionsPane = new FlowPane();
        AnchorPane a1 = new AnchorPane();
        a1.getChildren().add(actionsPane);
        AnchorPane.setRightAnchor(actionsPane, 5.0);
        actionsPane.getChildren().addAll(resetButton, filterButton);
        layout.setActions(a1);
        filterDialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        filterDialog.show(mainUI);
    }
}
