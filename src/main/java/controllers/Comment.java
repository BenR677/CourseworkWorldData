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
    public String addComment (@FormDataParam("title") String title, @FormDataParam("body") String body) {
        System.out.println("Invoked addComment()");
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Comments (Title, Body) VALUES (?, ?)");
            ps.setString(1, title);
            ps.setString(2, body);
            ps.execute();
            return"{\"OK\": \"Added comment.\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable add comment.  Error code xx.\"}";
        }
    }
}


