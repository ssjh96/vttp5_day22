package vttp5.paf.day22.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp5.paf.day22.service.RsvpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class RsvpRestController {
    
    @Autowired
    private RsvpService rsvpService;

    @GetMapping(path = "/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRsvp() {
    
        // Headers (Spring boot actually handles this automatically)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");

        return ResponseEntity.status(200).headers(headers).body(rsvpService.getAllJsonArrayRsvp().toString());
    }
    


}
