package vttp5.paf.day22.util;

public class Queries 
{
    // SQL Commands
    public static final String Q_ALL_RSVP =    
        """
            select * from rsvp_table
        """; // triple quotes for multiple lines?
    
        public static final String Q_EMAIL_RSVP =    
        """
            select * from rsvp_table
            where email like ?
        """;
}
