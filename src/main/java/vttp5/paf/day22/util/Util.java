package vttp5.paf.day22.util;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp5.paf.day22.model.Rsvp;

public class Util {
    
    public static Rsvp toRsvp(SqlRowSet rs)
    {
        Rsvp rsvp = new Rsvp();

        rsvp.setEmail(rs.getString("email"));
        rsvp.setPhone(rs.getString("phone"));
        rsvp.setConfirmationDate(rs.getDate("confirmation_date"));
        rsvp.setComments(rs.getString("comments"));

        return rsvp;
    }

    public static JsonObject toJsonRsvp (Rsvp rsvp)
    {
        JsonObjectBuilder job = Json.createObjectBuilder()
            .add("email", rsvp.getEmail())
            .add("phone", rsvp.getPhone())
            .add("confirmationDate", rsvp.getConfirmationDate().toString())
            .add("comments", rsvp.getComments());

            return job.build();

        // Or

        // JsonObject jRsvp = Json.createObjectBuilder()
        //     .add("email", rsvp.getEmail())
        //     .add("phone", rsvp.getPhone())
        //     .add("date", rsvp.getDate().toString())
        //     .add("comments", rsvp.getComments())
        //     .build();

        //     return jRsvp;
    }
    
}
