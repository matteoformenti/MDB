package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import itmakers.mdb.Movie;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ActorChooser extends JFXDialog
{
    private static List<String> actors = new ArrayList<>();
    private Movie m;

    public ActorChooser(Movie m)
    {
        this.m = m;
        JFXDialogLayout layout = new JFXDialogLayout();
        this.setContent(layout);
        this.setTransitionType(DialogTransition.CENTER);
        layout.setHeading(new Label("Choose actors"));
        VBox vBox = new VBox();
        JFXTextField searchActorField = new JFXTextField();
        JFXListView list = new JFXListView();
        for (String a : actors)
            list.getChildrenUnmodifiable().add(new JFXCheckBox(a));
        vBox.getChildren().addAll(searchActorField, list);
        HBox actions = new HBox();
        JFXButton saveButton = new JFXButton("Save");
        JFXButton closeButton = new JFXButton("Close");
        closeButton.setOnAction((event -> this.close()));

    }

}
