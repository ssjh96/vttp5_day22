package vttp5.paf.day22.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Rsvp> getRsvpByEmail(String email)
    {
        Optional<Rsvp> optRsvp = rsvpRepo.getRsvpByEmail(email);

        return optRsvp;
    }
    
    public JsonArray getMatchingJsonRsvpByEmail(String email)
    {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        List<Rsvp> matchingRsvps = rsvpRepo.getMatchingRsvpByEmail(email);

        for (Rsvp rsvp : matchingRsvps)
        {
            JsonObject jRsvp = Util.toJsonRsvp(rsvp);
            jab.add(jRsvp);
        }

        return jab.build();
    }

    public Boolean saveRsvp(Rsvp rsvp)
    {
        int rowsAffected = rsvpRepo.saveRsvp(rsvp);

        if (rowsAffected > 0)
        {
            return true;
        }
    
        return false;
    }

    public Optional<Integer> countRsvp()
    {
        Optional<Integer> optCount = rsvpRepo.countRsvp();

        return optCount;
    }
}
