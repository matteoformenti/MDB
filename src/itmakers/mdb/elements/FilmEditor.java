package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.storage.MovieStorageService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmEditor extends JFXDialog
{
    private Movie movie;
    private Files folder;
    private List<Path> files = new ArrayList<>();
    private FilmEditorController c;

    public FilmEditor(Movie m, String folder)
    {
        loadUI();
        this.movie = m;
        if (folder!=null)
        {
            walkDirectory(folder);
            c.filmCounter.setText(files.size()+" movies in the selected folder");
            c.fileLocationLabel.setText(files.get(0).toString());
            c.saveAndNextButton.setDisable(false);
        }
        else
            c.filmCounter.setDisable(true);
    }

    private void walkDirectory(String folder)
    {
        try
        {
            Files.walk(Paths.get(folder), FileVisitOption.FOLLOW_LINKS)
                .parallel()
                .filter((p) -> !p.toFile().isDirectory())
                .forEach((p) -> files.add(p.toAbsolutePath()));
            List<Path> toBeRemoved = new ArrayList<>();
            for (Path p : files)
            {
                toBeRemoved.addAll(MovieStorageService.getMovies().stream().filter(m -> m.getLocalURL().equals(p.toString())).map(m -> p).collect(Collectors.toList()));
                if (Files.probeContentType(p) != null)
                    if (!Files.probeContentType(p).contains("video"))
                        toBeRemoved.add(p);
            }
            toBeRemoved.stream().forEach((p) -> files.remove(p));
        }
        catch (Exception ex)
        {
            Main.dialogManager(ex.getLocalizedMessage());
        }
    }

    private void loadUI()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/Dialog.fxml"));
            Parent content = loader.load();
            c = loader.getController();
            c.init(this, movie);
            JFXDialogLayout layout = new JFXDialogLayout();
            this.setPrefSize(Main.getStage().getScene().getWidth(), Main.getStage().getScene().getHeight());
            layout.setBody(content);
            layout.setPadding(new Insets(-1));
            this.setContent(layout);
            c.setEditor(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
