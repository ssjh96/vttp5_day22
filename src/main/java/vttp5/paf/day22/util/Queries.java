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

    // insert a new row if email doesn't exist
    // update existing row if email already exist (email is pri key)
    // as new_rsvp (Alias)
    // on duplicate key - special clause that handles conflicts when inserting a row into a table that has unique constratints
    // if primary key alr exists sql will update exisiting row instead of throwing an error. If pri key x exist, sql will insert new row as usual.
    public static final String Q_SAVE_RSVP = 
        """
            insert into rsvp_table
                (email, phone, cfm_date, comments)
            values
                (?, ?, ?, ?)
            as new_rsvp
            on duplicate key update
                phone = new_rsvp.phone,
                cfm_date = new_rsvp.cfm_date,
                comments = new_rsvp.comments
        """;

        // conditional
        // If the comments field is empty, it updates it to "New comment".
        // If the comments field already has a value, it leaves it unchanged.

        // INSERT INTO rsvp_table (email, phone, cfm_date, comments)
        // VALUES ('john.doe@example.com', '98765432', '2024-06-01', 'Looking forward to the event!')
        // ON DUPLICATE KEY UPDATE comments = IF(comments IS NULL, 'New comment', comments);
}
