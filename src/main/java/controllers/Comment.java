package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("comment/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)

public class Comment {

    @POST
    @Path("add")
    public String addComment(@FormDataParam("newCommentCountryName") String countryName,
                             @FormDataParam("newCommentPostDate") String postDate,
                             @FormDataParam("newCommentHelpfulness") Integer helpfulness,
                             @FormDataParam("newCommentName") String username,
                             @FormDataParam("newCommentTitle") String title,
                             @FormDataParam("newCommentBody") String body,
                             @FormDataParam("StarRating") Integer rating
                             ) {
        System.out.println("Invoked comment.addComment()");
        try {
            PreparedStatement ps = Main.db.prepareStatement(
                    "INSERT INTO Comments (CountryName, Username, Title, Body, StarRating, Helpfulness, PostDate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, countryName);
            ps.setString(2, username);
            ps.setString(3, title);
            ps.setString(4, body);
            ps.setInt(5, rating);
            ps.setInt(6, helpfulness);
            ps.setString(7, postDate);
            ps.execute();
            System.out.println("comment.addComment() Successfully added");
            return "{\"OK\": \"Added comment.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable add comment.  Error code 1.\"}";
        }
    }
    @GET
    @Path("get/{CountryName}")
    public String getComment(@PathParam("CountryName") String CountryName) {
        System.out.println("Invoked comment.getComment() with CountryName " + CountryName);
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, Username, Title, Body, StarRating, Helpfulness, PostDate " +
                    "FROM Comments " +
                    "WHERE CountryName = ?");
            ps.setString(1,CountryName);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("UserID", results.getInt(1));
                row.put("Username", results.getString(2));
                row.put("Title", results.getString(3));
                row.put("Body", results.getString(4));
                row.put("StarRating", results.getInt(5));
                row.put("Helpfulness", results.getInt(6));
                row.put("PostDate", results.getString(7));
                response.add(row);
            }
            System.out.println("comment.getComment() Comments Successfully retrieved");
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get comment.  Error code xx.\"}";
        }
}}



