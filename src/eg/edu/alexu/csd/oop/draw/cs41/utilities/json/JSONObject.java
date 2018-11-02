package eg.edu.alexu.csd.oop.draw.cs41.utilities.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JSONObject {
    private String jsonString;
    Map<String, Object> json;
    
    JSONObject(String jsonString) throws Exception{
        if(!jsonString.startsWith("{") && jsonString.endsWith("}")) {
            throw new Exception();
        }
        this.jsonString = jsonString.substring(1, jsonString.length()-1);
        json = new HashMap<String, Object>();
    }
    public JSONObject() {
        json = new HashMap<String, Object>();
    }
    public void parse() throws Exception {
        char[] jsonchar = jsonString.toCharArray();
        for(int i = 0; i < jsonchar.length; i++) {
            if(jsonchar[i] == '\'') {
                StringBuilder key = new StringBuilder();
                i++;
                while(jsonchar[i] != '\'') {
                    key.append(jsonchar[i]);
                    i++;
                }
                i += 4;
                StringBuilder value = new StringBuilder();
                if(jsonchar[i] == '\'') {
                    i++;
                    while(jsonchar[i] != '\'') {
                        value.append(jsonchar[i]);
                        i++;
                    }
                    //value.append(jsonchar[i]);
                    json.put(key.toString(), value.toString());
                    continue;
                }
                if(jsonchar[i] == '{') {
                    value.append(jsonchar[i]);
                    i++;
                    int temp = 0;
                    while(temp != -1) {
                        if(jsonchar[i] == '{') temp ++;
                        if(jsonchar[i] == '}') temp --;
                        value.append(jsonchar[i]);
                        i++;
                    }
                    value.append(jsonchar[i]);
                    JSONObject v = new JSONObject(value.toString());
                    v.parse();
                    json.put(key.toString(), v);
                    continue;
                }
                if(jsonchar[i] == '[') {
                    value.append(jsonchar[i]);
                    i++;
                    int temp = 0;
                    while(jsonchar[i] != ']') {
                        if(jsonchar[i] == '[') temp ++;
                        if(jsonchar[i] == ']') temp --;
                        value.append(jsonchar[i]);
                        i++;
                    }
                    value.append(jsonchar[i]);
                    JSONArray v = new JSONArray(value.toString());
                    v.parse();
                    json.put(key.toString(), v);
                    continue;
                }
            }
            
        }
    }
    public Object get(String key) {
        return json.get(key);
    }
    public boolean hasKey(String key) {
        return json.containsKey(key);
    }
    public Set<String> keySet(){
        return json.keySet();
    }
    public void put(String key, String value) {
        json.put(key, '\'' + value + '\'');
    }
    public void put(String key, JSONObject j) {
        json.put(key, j);
    }
    public void put(String key, JSONArray j) {
        json.put(key, j);
    }
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append('{');
        for(String key : json.keySet()) {
            string.append('\'');
            string.append(key);
            string.append('\'');
            string.append(" : ");
            Object o = json.get(key);
            if(o.getClass().equals(JSONObject.class)) {
                string.append(((JSONObject)o).toString());
            } else if(o.getClass().equals(JSONArray.class)) {
                string.append(((JSONArray)o).toString());
            } else if(o.getClass().equals(String.class)) {
                string.append(((String)o));
            }
            string.append(',');
        }
        string.append('}');
        return string.toString();
    }
}
