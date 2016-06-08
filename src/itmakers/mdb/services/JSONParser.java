package itmakers.mdb.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<Field> fields = new ArrayList<>();

    JSONParser(String in, Type r)
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
        List<String> tmp = fields.stream().filter(f -> f.name.equalsIgnoreCase(parameter)).map(f -> f.value).collect(Collectors.toList());
        return tmp;
    }

    private class Field
    {
        private String name;
        private String value;

        Field(String name, String value)
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
