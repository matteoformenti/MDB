package itmakers.mdb.storage;

import itmakers.mdb.Movie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieStorageService implements Serializable
{
    private List<Movie> movies = new ArrayList<>();

    public List<Movie> getMovies()
    {
        return movies;
    }
}
