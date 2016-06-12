package itmakers.mdb;

import itmakers.mdb.elements.MovieGraphics;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable
{
    private String title;
    private String year;
    private String runtime;
    private List<Genres> genres = new ArrayList<>();
    private String director;
    private List<String> actors = new ArrayList<>();
    private String plot;
    private String imdbID;
    private String poster;
    private String localURLTrailer;
    private String localURL;
    private int localRating;
    private Image posterImage;
    private MovieGraphics graphics;

    public String getLocalURLTrailer()
    {
        return localURLTrailer;
    }

    public void setLocalURLTrailer(String localURLTrailer)
    {
        this.localURLTrailer = localURLTrailer;
    }

    public MovieGraphics getGraphics()
    {
        return graphics;
    }

    public void setGraphics(MovieGraphics graphics)
    {
        this.graphics = graphics;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public List<Genres> getGenres()
    {
        return genres;
    }

    public void setGenres(List<Genres> genres)
    {
        this.genres = genres;
    }

    public String getRuntime()
    {
        return runtime;
    }

    public void setRuntime(String runtime)
    {
        this.runtime = runtime;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public String getPlot()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public String getImdbID()
    {
        return imdbID;
    }

    public void setImdbID(String imdbID)
    {
        this.imdbID = imdbID;
    }

    public String getPoster()
    {
        return poster;
    }

    public void setPoster(String poster)
    {
        this.poster = poster;
    }

    public String getLocalURL()
    {
        return localURL;
    }

    public void setLocalURL(String localURL)
    {
        this.localURL = localURL;
    }

    public int getLocalRating()
    {
        return localRating;
    }

    public void setLocalRating(int localRating)
    {
        this.localRating = localRating;
    }

    public Image getPosterImage()
    {
        return posterImage;
    }

    public void setPosterImage(Image posterImage)
    {
        this.posterImage = posterImage;
    }

    public List<String> getActors()
    {
        return actors;
    }

    public void setActors(List<String> actors)
    {
        this.actors = actors;
    }
}
