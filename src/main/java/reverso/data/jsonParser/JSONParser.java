package reverso.data.jsonParser;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class JSONParser {

    public static Map<String,String> getContextTranslations(String json){

        Map<String,String> contextTranslations = new HashMap<>();

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();


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

    public static String getMainTranslation(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray translationsArray = jsonObject.getAsJsonArray("translation");
        if (translationsArray.size() > 0) {
            return translationsArray.get(0).getAsString();
        }
        return null;
    }
}
