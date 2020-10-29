package me.mrdev.mojang;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Base64;

public class API {

    private static Requests requests = new Requests();

    public static String getMojangStatus() {
        return requests.GetRequest("https://status.mojang.com/check");
    }

    public static String getUUID(String username) {
        return requests.GetRequest("https://api.mojang.com/users/profiles/minecraft/" + username);
    }

    public static String getNameHistoryByUsername(String username) {
        String uuid = getUUID(username);
        JsonObject object = new Gson().fromJson(uuid , JsonObject.class);
        return requests.GetRequest("https://api.mojang.com/user/profiles/" + object.get("id").getAsString() + "/names");
    }

    public static String getNameHistory(String uuid) {
        return requests.GetRequest("https://api.mojang.com/user/profiles/" + uuid + "/names");
    }

    public static String getUUIDs(String... usernames) {
        return requests.PostRequest("https://api.mojang.com/profiles/minecraft" , usernames);
    }

    public static String getTexturesByUsername(String username) {
        String uuid = getUUID(username);
        JsonObject object = new Gson().fromJson(uuid , JsonObject.class);
        return requests.GetRequest("https://sessionserver.mojang.com/session/minecraft/profile/" + object.get("id").getAsString());
    }

    public static String getTextures(String uuid) {
        return requests.GetRequest("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
    }

    public static String getSkinURLByUsername(String username) {
        String textures = getTexturesByUsername(username);
        JsonObject object = new Gson().fromJson(textures , JsonObject.class);
        JsonArray array = object.getAsJsonArray("properties");
        return new String(Base64.getDecoder().decode(array.get(0).getAsJsonObject().get("value").getAsString()));
    }

    public static String getSkinURL(String uuid) {
        String textures = getTextures(uuid);
        JsonObject object = new Gson().fromJson(textures , JsonObject.class);
        JsonArray array = object.getAsJsonArray("properties");
        return new String(Base64.getDecoder().decode(array.get(0).getAsJsonObject().get("value").getAsString()));
    }


}
