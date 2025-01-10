package vttp5.paf.day22.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp5.paf.day22.model.Rsvp;
import vttp5.paf.day22.service.RsvpService;

@Controller
@RequestMapping("/web")
public class RsvpController {

    @Autowired
    private RsvpService rsvpService;

     @GetMapping(path = "/email")
    public String getRsvpByEmailForWeb(@RequestParam("q") String email, Model model) {

        Optional<Rsvp> optRsvp = rsvpService.getRsvpByEmail(email);

        if (optRsvp.isEmpty()) {
            model.addAttribute("errorMsg", "RSVP not found for email: " + email);
            return "error";
        }

        Rsvp rsvp = optRsvp.get();

        model.addAttribute("rsvp", rsvp);
        return "success";
    }
    
}
