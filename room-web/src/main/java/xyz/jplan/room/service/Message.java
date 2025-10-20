package xyz.jplan.room.service;

public class Message {

    private final String from;
    private final String content;

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

    @Override
    public String toString() {
	return "{from=" + from + ", content=" + content + "}";
    }

}
