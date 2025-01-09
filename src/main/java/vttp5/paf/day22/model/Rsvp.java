package vttp5.paf.day22.model;

import java.util.Date;

public class Rsvp 
{
    // @Email(message = "Please enter a valid email")
    private String email;

    // @Pattern( regexp = "(8|9)[0-9]{7}", message = "Phone number must start with 8 or 9 follow by 7 digits")
    private String phone;

    // @Future
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String comments;

    public Rsvp() {
    }

    public Rsvp(String email, String phone, Date date, String comments) {
        this.email = email;
        this.phone = phone;
        this.date = date;
        this.comments = comments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }    
}
