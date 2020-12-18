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
    public String addComment(@FormDataParam("newCommentTitle") String title,
                             @FormDataParam("newCommentBody") String body,
                             @FormDataParam("StarRating") Integer StarRating,
                             @FormDataParam("newCommentCountryID") Integer CountryID,
                             @FormDataParam("newCommentPostDate") String postDate) {
        System.out.println("Invoked comment.addComment()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Comments (CountryID, Title, Body, StarRating, PostDate) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, CountryID);
            ps.setString(2, title);
            ps.setString(3, body);
            ps.setInt(4, StarRating);
            ps.setString(5, postDate);
            ps.execute();
            return "{\"OK\": \"Added comment.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable add comment.  Error code xx.\"}";
        }
    }
    @GET
    @Path("get/{CountryID}")
    public String getComment(@PathParam("CountryID") Integer CountryID) {
        System.out.println("Invoked comment.getComment() with CountryID " + CountryID);
        JSONArray response = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT CommentID, CountryID, UserID, Title, Body, StarRating, Helpfulness, PostDate FROM Comments WHERE CountryID = ?");
            ps.setInt(1,CountryID);
            ResultSet results = ps.executeQuery();
            while (results.next()==true) {
                JSONObject row = new JSONObject();
                row.put("CommentID", results.getInt(1));
                row.put("CountryID", results.getInt(2));
                row.put("UserID", results.getInt(3));
                row.put("Title", results.getString(4));
                row.put("Body", results.getString(5));
                row.put("StarRating", results.getInt(6));
                row.put("Helpfulness", results.getInt(7));
                row.put("PostDate", results.getString(8));
                response.add(row);
            }
            return response.toString();
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to get comment.  Error code xx.\"}";
        }
}}



