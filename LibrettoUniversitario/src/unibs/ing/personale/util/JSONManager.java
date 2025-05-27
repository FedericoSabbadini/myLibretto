package unibs.ing.personale.util;

import unibs.ing.personale.Corso;
import unibs.ing.personale.Libretto;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JSONManager {
    
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting() // JSON formattato e leggibile
            .registerTypeAdapter(Libretto.class, new LibrettoAdapter())
            .create();
    
    /**
     * Salva un oggetto in formato JSON
     */
    public static boolean salvaJSON(Object oggetto, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(oggetto, writer);
            return true;
        } catch (IOException e) {
            System.err.println("Errore nel salvataggio JSON: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Carica un oggetto da file JSON
     */
    public static <T> T caricaJSON(File file, Class<T> clazz) {
        if (!file.exists()) {
            return null;
        }
        
        try {
            String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
            return gson.fromJson(json, clazz);
        } catch (IOException e) {
            System.err.println("Errore nel caricamento JSON: " + e.getMessage());
            return null;
        } catch (JsonParseException e) {
            System.err.println("Errore nel parsing JSON: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Verifica se un file JSON esiste ed è valido
     */
    public static boolean esisteJSON(File file) {
        return file.exists() && file.length() > 0;
    }
    
    /**
     * Adapter personalizzato per la classe Libretto
     * Gestisce correttamente la serializzazione/deserializzazione
     */
    private static class LibrettoAdapter implements JsonSerializer<Libretto>, JsonDeserializer<Libretto> {
        
        @Override
        public JsonElement serialize(Libretto libretto, Type type, JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("nome", libretto.getNome());
            jsonObject.addProperty("numeroLodi", libretto.getNumeroLodi());
            jsonObject.add("libretto", context.serialize(libretto.getLibretto()));
            return jsonObject;
        }
        
        @Override
        public Libretto deserialize(JsonElement json, Type type, JsonDeserializationContext context) 
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            
            String nome = jsonObject.get("nome").getAsString();
            Libretto libretto = new Libretto(nome);
            
            // Deserializza la HashMap dei corsi
            if (jsonObject.has("libretto")) {
                Type mapType = new com.google.gson.reflect.TypeToken<HashMap<String, Corso>>(){}.getType();
                HashMap<String, Corso> corsiMap = context.deserialize(jsonObject.get("libretto"), mapType);
                libretto.libretto = corsiMap;
            }
            
            return libretto;
        }
    }
}