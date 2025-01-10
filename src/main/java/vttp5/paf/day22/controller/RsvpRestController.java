package vttp5.paf.day22.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp5.paf.day22.model.Rsvp;
import vttp5.paf.day22.service.RsvpService;
import vttp5.paf.day22.util.Util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class RsvpRestController {
    
    @Autowired
    private RsvpService rsvpService;

    // Client makes GET request > /api/rsvps
    
    // Request from client:
    // GET /api/rsvps
    // Accept: application/json
    
    // Server runs getAllRsvp() method
    // Svc Layer: call rsvpService.getAllJsonArrayRsvp(), which gets the list of RSVPs from the database and converts it to a JSON array
    // Response: The server sends back a 200 OK status with the JSON array as the response body

    // Response from server:
    // HTTP/1.1 200 OK
    // Content-Type: application/json



    // produces tells the server what kind of response format to return to the client
    // produces tells SB - The server - will return the response in JSON format with a Content-Type: application/json header.
    @GetMapping(path = "/rsvps", produces = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<String> getAllRsvp() {
    
        // Headers (Spring boot auto sets the correct response headers based on produces attribute in getmapping) 
        // for Client
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        // Accept = Specifies/tells the server the response format the client expects (e.g., JSON). 
        // Content-Type = Specifies the format of the request body (used for POST/PUT).

        // Client (Postman/browser/frontend app) is telling SB server that it wants the response in JSON format.
        // Server should respond with a Content-Type: application/json header in its re sponse.

        return ResponseEntity.status(200).headers(headers).body(rsvpService.getAllJsonArrayRsvp().toString());

        //without headers
        // return ResponseEntity.ok()
        //     .contentType(MediaType.APPLICATION_JSON)
        //     .body(rsvpService.getAllJsonArrayRsvp().toString());

    }

    // Query for 1 specific email 
    @GetMapping(path = "/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRsvpByEmail(@RequestParam ("q") String email) 
    {
        // produces specified the content-type, dont need header

        Optional<Rsvp> optRsvp = rsvpService.getRsvpByEmail(email);

        if (optRsvp.isEmpty())
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "RSVP not found for: " + email)
                                .build();
            
            return ResponseEntity.status(404)
                                //.contentType(MediaType.APPLICATION_JSON)
                                .body(jResponse.toString());
        }

        Rsvp rsvp = optRsvp.get();
        JsonObject jRsvp = Util.toJsonRsvp(rsvp);

        return ResponseEntity.status(200) // 200 is also ok()
                                //.contentType(MediaType.APPLICATION_JSON)
                                .body(jRsvp.toString());
        
    }

    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getMatchingRsvpByEmail(@RequestParam ("q") String email) {
        
        JsonArray jMatchingRsvps = rsvpService.getMatchingJsonRsvpByEmail(email);

        if(jMatchingRsvps.isEmpty())
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "no matching RSVPs for: " + email)
                                .build();
            
            return ResponseEntity.status(404)
                                .body(jResponse.toString());
        }

        return ResponseEntity.status(200).body(jMatchingRsvps.toString());
    }
    
    

    // If wanna use the same url, use conditional logic
    // @GetMapping(path = "/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<String> getRsvp(@RequestParam (value = "q", required = false) String email) 
    // {
    //     // If the 'q' parameter is provided, search by email
    //     if (email != null) 
    //     {
    //         Optional<Rsvp> optRsvp = rsvpService.getRsvpByEmail(email);

    //         if (optRsvp.isEmpty())
    //         {
    //             JsonObject jResponse = Json.createObjectBuilder()
    //                                 .add("errorMsg", "RSVP not found for: " + email)
    //                                 .build();
                
    //             return ResponseEntity.status(404)
    //                                 .contentType(MediaType.APPLICATION_JSON)
    //                                 .body(jResponse.toString());
    //         }

    //         Rsvp rsvp = optRsvp.get();
    //         JsonObject jRsvp = Util.toJsonRsvp(rsvp);

    //         return ResponseEntity.status(200) // 200 is also ok()
    //                                 .contentType(MediaType.APPLICATION_JSON)
    //                                 .body(jRsvp.toString());
    //     }

    //      // If 'q' is not provided, return all RSVPs
    //      return ResponseEntity.status(200).body(rsvpService.getAllJsonArrayRsvp().toString());
    // }
    
    



    // For Post request
    // Clieant sends request > /api/rsvp > Header CT: a/j, A: a/j, Body: JSON obj w RSVP details
    // Server process request > parse JSON body, adds RSVP to DB, sends a response > Header: CT: a/j, Body: confirmation message

    
    // Without MultiValueMap
    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrUpdateRsvp(@RequestParam ("email") String email,
                                                @RequestParam("phone") String phone,
                                                @RequestParam("cfm_date_str") String cfm_date_str,
                                                @RequestParam("comments")String comments) 
    {
        Date cfm_date;

        try
        {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           cfm_date  = sdf.parse(cfm_date_str);
        }
        catch (ParseException e)
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "Unable to parse date: " + cfm_date_str)
                                .build();
            
            return ResponseEntity.status(400) // 400 bad request
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jResponse.toString());

            // return ResponseEntity.status(400)
            //     .body("{\"error\": \"Invalid date format. Please use yyyy-MM-dd.\"}");
        }

        Rsvp rsvp = new Rsvp(email, phone, cfm_date, comments);

        if (!rsvpService.saveRsvp(rsvp))
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "Unable to save/update RSVP.")
                                .add("email", email)
                                .add("phone", phone)
                                .add("cfm_date", cfm_date_str)
                                .add("comments", comments)
                                .build();
            
            return ResponseEntity.status(500) // 500 internal server error
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jResponse.toString());
        }

        JsonObject jResponse = Json.createObjectBuilder()
                                .add("successMsg", "Successfully save/update RSVP.")
                                .add("email", email)
                                .add("phone", phone)
                                .add("cfm_date", cfm_date_str)
                                .add("comments", comments)
                                .build();

        return ResponseEntity.status(201)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jResponse.toString());
    }
    

    // With MultiValueMap
    @PostMapping(path = "/rsvp2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrUpdateRsvp(@RequestBody MultiValueMap<String, String> formData)
    {
        // .get() returns a list of values: .get(email) > ["x@email.com", "y@email.com"]
        String email = formData.getFirst("email"); // return first value
        String phone = formData.getFirst("phone");
        String cfm_date_str = formData.getFirst("cfm_date_str");
        String comments = formData.getFirst("comments");

        Date cfm_date;

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // Makes it match the pattern strictly
            cfm_date = sdf.parse(cfm_date_str);
        }
        catch (ParseException e)
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "Unable to parse date: " + cfm_date_str)
                                .build();
            
            return ResponseEntity.status(400) // 400 bad request
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jResponse.toString());
        }

        Rsvp rsvp = new Rsvp(email, phone, cfm_date, comments);

        if (!rsvpService.saveRsvp(rsvp))
        {
            JsonObject jResponse = Json.createObjectBuilder()
                                .add("errorMsg", "Unable to save/update RSVP.")
                                .add("email", email)
                                .add("phone", phone)
                                .add("cfm_date", cfm_date_str)
                                .add("comments", comments)
                                .build();
            
            return ResponseEntity.status(500) // 500 internal server error
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(jResponse.toString());
        }

        JsonObject jResponse = Json.createObjectBuilder()
                                .add("successMsg", "Successfully saved/updated RSVP.")
                                .add("email", email)
                                .add("phone", phone)
                                .add("cfm_date", cfm_date_str)
                                .add("comments", comments)
                                .build();

        return ResponseEntity.status(201)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jResponse.toString());
    } 


}
