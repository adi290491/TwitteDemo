package com.example.twitterdemo.beans;

/**
 * Created by aditya.sawant on 13-11-2016.
 */
public class Message
{
   // private Sender sender;

    private String id;

    private String text;

    private String recipient_screen_name;

    private String sender_id;

    private String sender_screen_name;

    private String recipient_id;

    private String created_at;

    private String id_str;

  //  private Entities entities;

    //private Recipient recipient;

   /* public Sender getSender ()
    {
        return sender;
    }

    public void setSender (Sender sender)
    {
        this.sender = sender;
    }*/

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getRecipient_screen_name ()
    {
        return recipient_screen_name;
    }

    public void setRecipient_screen_name (String recipient_screen_name)
    {
        this.recipient_screen_name = recipient_screen_name;
    }

    public String getSender_id ()
    {
        return sender_id;
    }

    public void setSender_id (String sender_id)
    {
        this.sender_id = sender_id;
    }

    public String getSender_screen_name ()
    {
        return sender_screen_name;
    }

    public void setSender_screen_name (String sender_screen_name)
    {
        this.sender_screen_name = sender_screen_name;
    }

    public String getRecipient_id ()
    {
        return recipient_id;
    }

    public void setRecipient_id (String recipient_id)
    {
        this.recipient_id = recipient_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getId_str ()
    {
        return id_str;
    }

    public void setId_str (String id_str)
    {
        this.id_str = id_str;
    }

   /* public Entities getEntities ()
    {
        return entities;
    }

    public void setEntities (Entities entities)
    {
        this.entities = entities;
    }

    public Recipient getRecipient ()
    {
        return recipient;
    }

    public void setRecipient (Recipient recipient)
    {
        this.recipient = recipient;
    }
*/
    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", text = "+text+", recipient_screen_name = "+recipient_screen_name+", sender_id = "+sender_id+", sender_screen_name = "+sender_screen_name+", recipient_id = "+recipient_id+", created_at = "+created_at+", id_str = "+id_str+"]";
    }
}