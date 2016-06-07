package itmakers.mdb.services;

import java.util.ArrayList;
import java.util.List;

public class JSONParser
{
    public enum Response
    {
        SEARCH,
        TV_SHOW,
        TITLE
    }

    private Response r;
    private String in;
    private List<Field> fields = new ArrayList<>();

    public JSONParser(String in, Response r)
    {
        String[] tmp = in.split("\"");
        for (int i = 0; i < tmp.length; i++)
        {
            if (!tmp[i].equals(":") && !tmp[i].equals("{") && !tmp[i].equals("}"))
            {
                fields.add(new Field(tmp[i], tmp[i + 2]));
                i+=3;
            }
        }
    }

    public List<String> parse(String parameter)
    {
        List<String> tmp = new ArrayList<>();
        for (Field f:fields)
            if (f.name.equalsIgnoreCase(parameter))
                tmp.add(f.value);
        return tmp;
    }

    private class Field
    {
        private String name;
        private String value;

        public Field(String name, String value)
        {
            this.name = name;
            this.value = value;
        }

        public String getName()
        {
            return name;
        }

        public String getValue()
        {
            return value;
        }
    }
}
