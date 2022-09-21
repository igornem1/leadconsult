package eu.leadconsult.interview.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.leadconsult.interview.task.data.model.base.BaseEntity;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static byte[] toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException ie) {
            throw new RuntimeException(ie);
        }
    }

    public static <T extends BaseEntity> T toObject(JSONObject jsonObj, Class<T> clazz)
            throws JsonMappingException, JsonProcessingException {
        return mapper.readValue(jsonObj.toString(), clazz);
    }

    public static <T extends BaseEntity> List<T> toList(JSONArray jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(mapper.readValue(jsonArray.get(i).toString(), clazz));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

}
