package eg.edu.alexu.csd.oop.draw.cs41.utilities.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Muhammad Salah
 * represents a json array
 */
public class JSONArray {
    /**
     * string to parse
     */
    private String jsonString;
    /**
     * list of json objects
     */
    private List<JSONObject> array;
    
    /**
     * @param jsonString string to parse
     * @throws Exception not json array
     */
    JSONArray(String jsonString) throws Exception{
        if(!jsonString.startsWith("[") && jsonString.endsWith("]")) {
            throw new Exception();
        }
        this.jsonString = jsonString.substring(1, jsonString.length()-1);
        array = new ArrayList<JSONObject>();
    }
    public JSONArray() {
        array = new ArrayList<JSONObject>();
    }
    
    /**
     * @throws Exception
     */
    public void parse() throws Exception {
        char[] jsonchar = jsonString.toCharArray();
        for(int i = 0; i < jsonchar.length; i++) {
            if(jsonchar[i] == '{') {
                StringBuilder string = new StringBuilder();
                string.append(jsonchar[i]);
                i++;
                int temp = 0;
                while(temp != -1) {
                    if(jsonchar[i] == '{') temp ++;
                    if(jsonchar[i] == '}') temp --;
                    string.append(jsonchar[i]);
                    i++;
                }
                string.append(jsonchar[i]);
                JSONObject json = new JSONObject(string.toString());
                json.parse();
                array.add(json);
                continue;
            }
            
        }
    }
    /**
     * @param index
     * @return object at index
     */
    public JSONObject get(int index) {
        return array.get(index);
    }
    /**
     * @return array size
     */
    public int size() {
        return array.size();
    }
    /**
     * @param json object
     */
    public void add(JSONObject j) {
        array.add(j);
    }
    
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append('[');
        for(JSONObject o : array) {
            string.append(o.toString());
            string.append(',');
        }
        string.append(']');
        return string.toString();
    }
}
