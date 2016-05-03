package itmakers.mdb.elements;


import javafx.scene.layout.Pane;

public class VideoGraphics extends Pane
{
    private VideoData videoData;

    public VideoGraphics(VideoData video)
    {
        this.videoData = video;
        this.setStyle("-fx-background-color: #99cc00");
        this.setPrefWidth(200);
        this.setPrefHeight(200);
        this.setOnMouseEntered((event) -> setStyle("-fx-background-color: #99ee22"));
        this.setOnMouseExited((event) -> setStyle("-fx-background-color: #99cc00"));
    }

    public void resize()
    {
    }
}
