package itmakers.mdb.storage;

import itmakers.mdb.Main;
import itmakers.mdb.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieStorageService implements Serializable
{
    private static List<Movie> movies = new ArrayList<>();

    public static List<Movie> getMovies()
    {
        return movies;
    }

    public static void addMovie(Movie m)
    {
        movies.add(m);
        saveDB();
    }

    private static void saveDB()
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
        saveDB();
    }

    public static void updateList(Movie movie)
    {
        Main.controller.addToMoviesList(movie);
    }
}
