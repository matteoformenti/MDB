package itmakers.mdb.services;

import org.json.JSONObject;

public class JSONParser
{
    public enum Type
    {
        movie,
        series,
        episode
    }

    private Type r;
    private String in;
    private JSONObject obj;

    JSONParser(String in, Type r)
    {
        obj = new JSONObject(in);
    }

    public String parse(String parameter)
    {
        try
        {
            return obj.getString(parameter);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
