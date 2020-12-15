package controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("userComment/")
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
public class PostComment {
    @POST
    @Path("postComment/")

    public String userCommentpostComment(@FormDataParam("UComm_NameEntryBox") String Title, @FormDataParam("UComm_BodyEntryBox") String Body) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Comments (Title, Body) VALUES (?, ?)");
            ps.setString(1, Title);
            ps.setString(2, Body);
            ps.execute();
            return"{\\\"OK\\\": \\\"Added food.\\\"}";
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"Error\": \"Unable to list items.  Error code xx.\"}";
        }
    }
}


