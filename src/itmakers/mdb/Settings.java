package itmakers.mdb;

import javafx.concurrent.Task;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Settings
{
    private static List<Movie> movies = new ArrayList<>();
    private static List<TVShow> series = new ArrayList<>();
    private static String moviesDB= "mdb.save";
    private static String seriesDB= "sdb.save";
    private static String settingsLocation = "settings.cgf";
    private static int itemsPerLine = 4;

    public static void loadDB()
    {
        Task loader = new Task<Void>()
        {
            @Override
            protected Void call()
            {
                try
                {
                    FileInputStream fileInputStream = new FileInputStream(moviesDB);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    movies = (List<Movie>) objectInputStream.readObject();
                    fileInputStream = new FileInputStream(seriesDB);
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    series = (List<TVShow>) objectInputStream.readObject();
                } catch (Exception e)
                {
                }
                return null;
            }
        };
        new Thread(loader).start();
    }

    public static int getItemsPerLine()
    {
        return itemsPerLine;
    }

    public static void setItemsPerLine(int itemsPerLine)
    {
        Settings.itemsPerLine = itemsPerLine;
    }
}
