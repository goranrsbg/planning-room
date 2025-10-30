package xyz.jplan.room.service;

public class Message {

    private String from;
    private String content;

    public Message() {
	this("", "");
    }

    public Message(String from, String content) {
	this.from = from;
	this.content = content;
    }

    public String getFrom() {
	return from;
    }

    public String getContent() {
	return content;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public void setContent(String content) {
	this.content = content;
    }

    @Override
    public String toString() {
	return "{from=" + from + ", content=" + content + "}";
    }

}
