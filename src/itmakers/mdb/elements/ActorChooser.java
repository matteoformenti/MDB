package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import itmakers.mdb.Movie;
import itmakers.mdb.storage.GeneralStorage;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ActorChooser extends JFXDialog
{
    private Movie m;
    private ListView list = new ListView();

    public ActorChooser(Movie m)
    {
        this.m = m;
        this.setPrefWidth(200);
        this.setPrefHeight(300);
        JFXDialogLayout layout = new JFXDialogLayout();
        this.setContent(layout);
        this.setTransitionType(DialogTransition.CENTER);
        Label title = new Label("Choose actors");
        title.setAlignment(Pos.CENTER);
        layout.setHeading(title);
        VBox vBox = new VBox();
        HBox newActorBox = new HBox();
        JFXTextField searchActorField = new JFXTextField();
        HBox.setHgrow(searchActorField, Priority.ALWAYS);
        searchActorField.setOnKeyReleased((event) -> reloadList(searchActorField.getText()));
        JFXButton newActorButton = new JFXButton("Add new Actor", new MaterialDesignIconView(MaterialDesignIcon.PLUS));
        newActorBox.getChildren().addAll(searchActorField, newActorButton);
        reloadList("");
        vBox.getChildren().addAll(newActorBox, list);
        layout.setBody(vBox);
        JFXButton closeButton = new JFXButton("Close");
        layout.setActions(closeButton);
        closeButton.setOnAction((event -> this.close()));
    }

    public void reloadList(String contains)
    {
        list.getItems().removeAll(list.getItems());
        for (String a : GeneralStorage.actors)
        {
            if (m.getActors().contains(a) && a.toLowerCase().contains(contains.toLowerCase()))
            {
                JFXCheckBox c = new JFXCheckBox(a);
                c.setSelected(true);
                list.getItems().add(c);
            }
            else if (a.toLowerCase().contains(contains.toLowerCase()))
                list.getItems().add(new JFXCheckBox(a));
        }
    }
}
