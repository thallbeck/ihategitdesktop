package JsonClasses;

/*
 * Created by timothy.hallbeck on 4/11/2017.
 * Copyright (c) Hallbeck Pepper Company LLC
 * All rights reserved
 */

import Utils.General;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public abstract class JsonData {
    protected JsonObject jsonObject = null;
    protected JsonArray  jsonArray  = null;
    private   String     arrayKey   = "";

    public JsonData() {}
    public JsonData( String jsonResult) {
        loadObject(jsonResult);
    }
    public JsonData( String jsonResult, String arrayKey) {
        loadArray(jsonResult, arrayKey);
    }
    public JsonData( JsonObject object, String arrayKey) {

        this.arrayKey = arrayKey;
        try {
            jsonArray = object.getAsJsonArray(this.arrayKey);
        } catch (ClassCastException e) {
            jsonArray = null;
            General.Debug("jsonArray " + arrayKey + " is empty");
        } finally {
        }

    }
    public JsonData( JsonObject object) {
        jsonObject = object;
    }
    public JsonData( JsonArray array) {
        jsonArray = array;
    }

    public void loadObject(String jsonResult) {
        if (jsonResult.equals("") || jsonResult.equals("{}")) {
            General.Debug("  JsonClasses.JsonData::loadObject()\n  There is no data to load, previous call returned blank.");
            return;
        }
        jsonResult = checkForUntitledArray(jsonResult);
        JsonParser parser = new JsonParser();
        jsonObject = parser.parse(jsonResult).getAsJsonObject();
        checkAllKeys();
    }

    protected void loadNestedObject(String jsonResult, String key) {
        jsonResult = checkForUntitledArray(jsonResult);
        JsonParser parser = new JsonParser();
        jsonObject = parser.parse(jsonResult).getAsJsonObject();
        jsonObject = jsonObject.get(key).getAsJsonObject();
    }

    protected JsonData loadArray( String jsonResult, String arrayKey) {
        this.arrayKey = arrayKey;
        JsonParser parser = new JsonParser();

        if (arrayKey.length() == 0) {
            // Anonymous json array
            jsonArray = parser.parse(jsonResult).getAsJsonArray();
        } else {
            JsonElement element = parser.parse(jsonResult);
            String nestedResult = element.toString();
            JsonObject object = parser.parse(nestedResult).getAsJsonObject();
            jsonArray = object.getAsJsonArray(this.arrayKey);
        }
        checkAllKeys();

        return this;
    }

    protected JsonData loadArray( JsonObject object, String arrayKey) {
        this.arrayKey = arrayKey;
        jsonArray = object.getAsJsonArray(this.arrayKey);
        return this;
    }

    protected JsonData setObject( JsonObject object) {
        jsonObject = object;
        return this;
    }

    protected JsonData setArray( JsonArray array) {
        jsonArray = array;
        return this;
    }

    public String getArrayKey() {
        return arrayKey;
    }

    public JsonArray getBaseArray() {
        return jsonArray;
    }

    public JsonObject getBaseObject() {
        return jsonObject;
    }

    protected String checkForUntitledArray(String jsonResult) {
        // At the very beginning of the string
        if (jsonResult.charAt(0) == '[') {
            jsonResult = jsonResult.replace("[", "");
            jsonResult = jsonResult.replace("]", "");
        }

        // Remove empty arrays in the middle of the string
        jsonResult = jsonResult.replace("[]", "\"\"");

        return jsonResult;
    }

    public String getAsString(int index) {
        return (jsonArray.get(index).toString());
    }

    public String getAsString(String key) {
        Assert.assertTrue(jsonObject != null, "jsonObject has not been initialized in JsonClasses.JsonData");
        JsonElement element = jsonObject.get(key);

        Assert.assertTrue(element != null, "element was not found: " + key);
        if (element.isJsonNull())
            return "";
        return element.getAsString();
    }

    public JsonObject getAsObject(int index) {
        return (jsonArray.get(index).getAsJsonObject());
    }
    protected JsonObject getAsObject(String key) {
        Assert.assertTrue(jsonObject != null, "jsonObject has not been initialized in JsonClasses.JsonData");
        JsonElement element = jsonObject.get(key);

        if (element.isJsonNull())
            return null;
        return element.getAsJsonObject();

    }

    protected JsonArray getAsArray(String key) {
        Assert.assertTrue(jsonObject != null, "jsonObject has not been initialized in JsonClasses.JsonData");
        JsonArray array = jsonObject.get(key).getAsJsonArray();

        return array;
    }

    public String[] getAllKeys() {
        TreeMap<String, String> mylist = new TreeMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entrySet)
            mylist.put(entry.getKey(), "");

        return mylist.keySet().toArray(new String[mylist.size()]);
    }

    abstract public void checkAllKeys();

    public int size() {
        return jsonArray.size();
    }

    @Override
    public String toString() {
        String outputBoth="";

        if (jsonObject != null)
            outputBoth = jsonObject.toString();
        if (jsonArray != null) {
            outputBoth += "\"" + arrayKey + "\":";
            outputBoth += jsonArray.toString();
        }

        return outputBoth;
    }

}
