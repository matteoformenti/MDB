package itmakers.mdb.storage;

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

    public static void saveDB()
    {

    }
}
