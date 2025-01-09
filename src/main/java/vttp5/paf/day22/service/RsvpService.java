package vttp5.paf.day22.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp5.paf.day22.model.Rsvp;
import vttp5.paf.day22.repository.RsvpRepository;
import vttp5.paf.day22.util.Util;

@Service
public class RsvpService {

    @Autowired
    private RsvpRepository rsvpRepo;

    public List<Rsvp> allRsvp ()
    {
        return rsvpRepo.getAllRsvp();
    }


    
    public JsonArray getAllJsonArrayRsvp()
    {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        // List<Rsvp> allRsvp = rsvpRepo.getAllRsvp();
        List<Rsvp> allRsvp = allRsvp();

        for (Rsvp rsvp : allRsvp)
        {
            JsonObject jRsvp = Util.toJsonRsvp(rsvp);
            jab.add(jRsvp);
        }

        return jab.build();
    }
    
}
