package controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("countryStats")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class GetStats {
    @GET
    @Path("getStats/{CountryID}")
    public String countryStatsgetStats(@PathParam("CountryID") Integer CountryID) {
        System.out.println("Invoked countryStats.getStats()");
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CountryID, NameEnglish, NameNative, Languages, Population FROM CountryStats WHERE CountryID = ?");
            ps.setInt(1,CountryID);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("CountryID", results.getInt(1));
                row.put("NameEnglish", results.getString(2));
                row.put("NameNative", results.getString(3));      //Blob?
                row.put("Languages", results.getString(4));
                row.put("Population", results.getInt(5));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}

