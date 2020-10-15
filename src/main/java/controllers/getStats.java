package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("countryStats")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class getStats {
    @GET
    @Path("getStats")
    public String countryStatsgetStats() {
        System.out.println("Invoked countryStats.getStats()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CountryID, NameEnglish, NameNative, Languages, Population FROM CountryStats");
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("CountryID", results.getInt(1));
                row.put("NameEnglish", results.getString(2));
                row.put("NameNative", results.getInt(3));      //Blob?
                row.put("Languages", results.getString(5));
                row.put("Population", results.getInt(6));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}

