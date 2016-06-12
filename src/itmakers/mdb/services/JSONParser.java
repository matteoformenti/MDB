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
        return obj.getString(parameter);
    }
}
