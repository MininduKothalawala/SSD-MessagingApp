package messagingapp.ssd.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("Message")
public class Message {

    @Id
    private String Id;
    private String Content;
    private String Sender;
    private String AudienceType;
    private String[] Receivers;
    private LocalDateTime SentTime;

    public Message() {
    }

    public Message(String  id, String content, String sender, String audienceType, String[] receivers, LocalDateTime sentTime) {
        Id = id;
        Content = content;
        Sender = sender;
        AudienceType = audienceType;
        Receivers = receivers;
        SentTime = sentTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getAudienceType() {
        return AudienceType;
    }

    public void setAudienceType(String audienceType) {
        AudienceType = audienceType;
    }

    public String[] getReceivers() {
        return Receivers;
    }

    public void setReceivers(String[] receivers) {
        Receivers = receivers;
    }

    public LocalDateTime getSentTime() {
        return SentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        SentTime = sentTime;
    }
}
