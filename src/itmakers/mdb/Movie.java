package itmakers.mdb;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable
{
    private String title;
    private String year;
    private List<Genres> genres;
    private String runtime;
    private String director;
    private String writer;
    private String plot;
    private String country;
    private String awards;
    private String imdbID;
    private String poster;
    private String imdbRating;

    private String localURL;
    private int localRating;
    private Image posterImage;

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

    public String getWriter()
    {
        return writer;
    }

    public void setWriter(String writer)
    {
        this.writer = writer;
    }

    public String getPlot()
    {
        return plot;
    }

    public void setPlot(String plot)
    {
        this.plot = plot;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getAwards()
    {
        return awards;
    }

    public void setAwards(String awards)
    {
        this.awards = awards;
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

    public String getImdbRating()
    {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating)
    {
        this.imdbRating = imdbRating;
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
}
