package itmakers.mdb.elements;

import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.storage.Settings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

class ActorChooser extends JFXDialog
{
    private Movie m;
    private ListView list = new ListView();

    ActorChooser(Movie m)
    {
        this.m = m;
        list.getStylesheets().add(getClass().getResource("../style.css").toExternalForm());
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
        newActorButton.setOnAction((event ->
        {
            boolean exists = false;
            for (String a : Settings.actors)
                if (a.toLowerCase().equals(searchActorField.getText().toLowerCase()))
                    exists = true;
            if (exists)
                Main.dialogManager("This actor already exists");
            else if (searchActorField.getText().equals(" ") || searchActorField.getText().equals(""))
                Main.dialogManager("The actor name isn't valid!");
            else
            {
                Settings.actors.add(searchActorField.getText());
                searchActorField.setText("");
                reloadList("");
            }
        }));
        newActorBox.getChildren().addAll(searchActorField, newActorButton);
        reloadList("");
        vBox.getChildren().addAll(newActorBox, list);
        layout.setBody(vBox);
        JFXButton closeButton = new JFXButton("Close");
        layout.setActions(closeButton);
        closeButton.setOnAction((event -> this.close()));
        this.setOnDialogClosed((e) ->
        {
            m.getActors().removeAll(m.getActors());
            list.getItems().stream().filter(o -> ((JFXCheckBox) o).isSelected()).forEach(o -> m.getActors().add(((JFXCheckBox) o).getText()));
        });
    }

    private void reloadList(String contains)
    {
        list.getItems().removeAll(list.getItems());
        for (String a : Settings.actors)
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
