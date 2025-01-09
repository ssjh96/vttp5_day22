package vttp5.paf.day22.repository;

import java.util.LinkedList;
import java.util.List;

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
    
}
