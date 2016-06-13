package itmakers.mdb.storage;

import itmakers.mdb.Main;
import itmakers.mdb.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieStorageService implements Serializable
{
    public static List<Movie> movies = new ArrayList<>();

    public static List<Movie> getMovies()
    {
        return movies;
    }

    public static void addMovie(Movie m)
    {
        if (!movies.contains(m))
            movies.add(m);
        updateDB();
    }

    public static void updateDB()
    {

    }

    public static Movie getLastMovie()
    {
        if (movies.size() > 0)
            return movies.get(movies.size()-1);
        return null;
    }

    public static void remove(Movie movie)
    {
        movies.remove(movie);
        Main.controller.removeFromMoviesList(movie);
        updateDB();
    }

    public static void updateList(Movie movie)
    {
        if (!Main.controller.getMovieGraphics().contains(movie.getGraphics()))
            Main.controller.addToMoviesList(movie);
    }

    public static Movie prepareNewMovie()
    {
        Movie m = new Movie();
        addMovie(m);
        return m;
    }
}