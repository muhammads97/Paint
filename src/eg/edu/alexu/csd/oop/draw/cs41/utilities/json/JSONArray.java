package eg.edu.alexu.csd.oop.draw.cs41.utilities.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONArray {
    private String jsonString;
    private List<JSONObject> array;
    
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
    public JSONObject get(int index) {
        return array.get(index);
    }
    public int size() {
        return array.size();
    }
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
