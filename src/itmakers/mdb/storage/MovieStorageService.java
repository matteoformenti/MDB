package itmakers.mdb.storage;

import itmakers.mdb.Main;
import itmakers.mdb.Movie;
import itmakers.mdb.elements.MovieGraphics;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MovieStorageService implements Serializable
{
    private static List<Movie> movies = new ArrayList<>();

    private static boolean MD5Enabled = true;

    public synchronized static List<Movie> getMovies()
    {
        return movies;
    }

    private static void addMovie(Movie m)
    {
        if (!movies.contains(m))
            movies.add(m);
        updateDB();
    }

    private static void updateDB()
    {
        Task saveMoviesTask = new Task()
        {
            @Override
            protected Object call() throws Exception
            {
                getMovies().stream().filter(m -> m.getLocalURL() != null && m.getTitle() != null).forEach(m -> {
                    try
                    {
                        if (Files.notExists(Paths.get(Settings.moviesPostersFolder)))
                            Files.createDirectories(Paths.get(Settings.moviesPostersFolder));
                        if (MD5Enabled)
                        {
                            MessageDigest digest = MessageDigest.getInstance("MD5");
                            digest.reset();
                            digest.update((m.getTitle() + m.getLocalURL()).getBytes());
                            String filename = new BigInteger(1, digest.digest()).toString(16);
                            try
                            {
                                ImageIO.write(SwingFXUtils.fromFXImage(m.getPosterImage(), null), "png", new File(Settings.moviesPostersFolder + "/" + filename + ".png"));
                                m.setImageIdentifier(filename);
                            } catch (Exception s)
                            {
                                s.printStackTrace();
                            }
                        } else
                        {
                            ImageIO.write(SwingFXUtils.fromFXImage(m.getPosterImage(), null), "png", new File(Settings.moviesPostersFolder + "/" + m.getTitle() + ".png"));
                        }
                    } catch (NoSuchAlgorithmException e)
                    {
                        MD5Enabled = false;
                        Main.dialogManager("MD5 isn't supported by your computer, we're going back to a less secure fallback method (this is used to ensure that every movie poster has an unique id, having more movies with the same title can cause problems)");
                    } catch (IOException e)
                    {
                        Main.dialogManager(e.getMessage());
                    }
                });
                try
                {
                    FileOutputStream outputStream = new FileOutputStream(Settings.moviesDbLocation+"-n");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(movies);
                    objectOutputStream.close();
                    outputStream.close();
                    Files.deleteIfExists(Paths.get(Settings.moviesDbLocation));
                    Path fileToMovePath = Paths.get(Settings.moviesDbLocation+"-n");
                    Path targetPath = Paths.get(Settings.moviesDbLocation);
                    Files.move(fileToMovePath, targetPath);


                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        };
        Thread t = new Thread(saveMoviesTask);
        t.start();
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

    public static void loadDB()
    {
        Task loadDB = new Task()
        {
            @Override
            protected Object call() throws Exception
            {
                if (Files.exists(Paths.get(Settings.moviesDbLocation)))
                {
                    FileInputStream in = new FileInputStream(Settings.moviesDbLocation);
                    ObjectInputStream obin = new ObjectInputStream(in);
                    movies = (ArrayList<Movie>) obin.readObject();
                    for (Movie m : getMovies())
                    {
                        File poster = new File(Settings.moviesPostersFolder + "/" + m.getImageIdentifier() + ".png");
                        m.setPosterImage(SwingFXUtils.toFXImage(ImageIO.read(poster), null));
                        m.generateMovieGraphics();
                        Platform.runLater(() -> Main.controller.addToMoviesList(m));
                    }
                    in.close();
                    obin.close();
                }
                return null;
            }
        };
        Thread t = new Thread(loadDB);
        t.start();
    }
}