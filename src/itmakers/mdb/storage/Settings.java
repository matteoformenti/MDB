package itmakers.mdb.storage;

import java.util.ArrayList;
import java.util.List;

public class Settings
{
    public static String moviesDbLocation = "mdb.db";
    public static String showsDbLocation = "sdb.db";
    public static String settingsLocation = "settings.config";
    public static String moviesPostersFolder = "mp";
    public static String showsPostersFolder = "sp";
    public static String tempFolder = "tmp";

    public static List<String> actors = new ArrayList<>();
    private static int moviesPerRow = 6;

    public static int getMoviesPerRow()
    {
        return moviesPerRow;
    }

    public static void setMoviesPerRow(int moviesPerRow)
    {
        Settings.moviesPerRow = moviesPerRow;
    }
}
