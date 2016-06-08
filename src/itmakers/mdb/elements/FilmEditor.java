package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

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
            Parent content = loader.load();
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
}
