package reverso.data.jsonParser;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FromStringToArrayDeserializer extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter jsonWriter, String s) throws IOException {

    }
    public String read(JsonReader reader) throws IOException {
        JsonToken token = reader.peek();
        if (token == JsonToken.BEGIN_ARRAY) {
            reader.beginArray();
            String value = reader.nextString();
            reader.endArray();
            return value;
        } else {
            return reader.nextString();
        }
    }
}
