package reverso.data.jsonParser;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonArray;

public class JsonParser {

    public static Map<String,String > getContextTranslations(String json){

        Map<String,String> contextTranslations = new HashMap<>();

        JsonObject jsonObject = com.google.gson.JsonParser.parseString(json).getAsJsonObject();


        JsonObject contextResults = jsonObject.getAsJsonObject("contextResults");
        JsonArray results = contextResults.getAsJsonArray("results");
        JsonObject firstElement = results.get(0).getAsJsonObject(); // Первый элемент массива results

        JsonArray sourceExamples = firstElement.getAsJsonArray("sourceExamples");
        JsonArray targetExamples = firstElement.getAsJsonArray("targetExamples");

        for(int i = 0; i<sourceExamples.size(); i++ ){
            contextTranslations.put(sourceExamples.get(i).getAsString(),targetExamples.get(i).getAsString());
        }
        return contextTranslations;
    }
}
