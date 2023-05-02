package Utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class JSONUtils {

    //convert JSON into List of Objects
    static public <T> List<T> convertFromJsonToList(String json, TypeReference<List<T>> var) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, var);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Generic Type Safe Method – convert JSON into Object
    static public <T> T covertFromJsonToObject(String json, Class<T> var){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, var);//Convert Json into object of Specific Type
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //convert Object into JSON
    public static String covertFromObjectToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
