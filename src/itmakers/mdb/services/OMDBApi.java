package itmakers.mdb.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class OMDBApi
{
    private String whitespace_chars =  ""+"\\u0009"+"\\u000A"+"\\u000B"+"\\u000C"+"\\u000D"+"\\u0020"+"\\u0085"+"\\u00A0"+"\\u1680"+"\\u180E"+"\\u2000"+"\\u2001"+"\\u2002"+"\\u2003"+"\\u2004"+"\\u2005"+"\\u2006"+"\\u2007"+"\\u2008"
            +"\\u2009"+"\\u200A"+"\\u2028"+"\\u2029"+"\\u202F"+"\\u205F"+"\\u3000";

    public JSONParser parser;
    public String title;

    public OMDBApi(String title, JSONParser.Type t)
    {
        try
        {
            this.title = title;
            String a = "[" + whitespace_chars + "]";
            URLConnection connection = new URL (assembler(title.replaceAll(a, "+"), t)).openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String text = "";
            while ((inputLine = in.readLine()) != null)
                text+=inputLine;
            in.close();
            parser = new JSONParser(text, t);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private String assembler(String title, JSONParser.Type t)
    {
        String OMDBAPI = "http://www.omdbapi.com/";
        return OMDBAPI +"?t="+title+"&type="+t+"&plot=full&r=json";
    }
}
