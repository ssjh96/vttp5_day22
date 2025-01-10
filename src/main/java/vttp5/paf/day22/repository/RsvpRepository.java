package vttp5.paf.day22.repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp5.paf.day22.model.Rsvp;
import vttp5.paf.day22.util.Queries;
import vttp5.paf.day22.util.Util;

@Repository
public class RsvpRepository {
    
    @Autowired
    public JdbcTemplate template;

    public List<Rsvp> getAllRsvp()
    {
        List<Rsvp> allRsvp =  new LinkedList<>();
        
        SqlRowSet rs = template.queryForRowSet(Queries.Q_ALL_RSVP);

        while(rs.next()) 
        {
            allRsvp.add(Util.toRsvp(rs));
        }
        
        return allRsvp;
    }

    public Optional<Rsvp> getRsvpByEmail(String email) // optional is used for a 0 or 1 result, like finding an RSVP by email where email is unique
    {
        String formattedEmail = "%" + email + "%";

        SqlRowSet rs = template.queryForRowSet(Queries.Q_EMAIL_RSVP, formattedEmail); // in this case should not use like, should use "SELECT * FROM rsvp_table WHERE email = ?"

        if(!rs.next())
        {
            return Optional.empty();
        }

        Rsvp rsvp = Util.toRsvp(rs);

        return Optional.of(rsvp);
    }

    public List<Rsvp> getMatchingRsvpByEmail(String email)
    {
        String formattedEmail = "%" + email + "%";
        List<Rsvp> matchingRsvps = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(Queries.Q_EMAIL_RSVP, formattedEmail);

        while(rs.next())
        {
            Rsvp rsvp = Util.toRsvp(rs);
            matchingRsvps.add(rsvp);
        }

        return matchingRsvps;
    }

    public int saveRsvp(Rsvp rsvp)
    {
        int rowsAffected = template.update(Queries.Q_SAVE_RSVP, rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmationDate(), rsvp.getComments());

        return rowsAffected;
        
    }

}
