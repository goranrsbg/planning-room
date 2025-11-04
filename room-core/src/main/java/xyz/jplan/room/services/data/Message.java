package xyz.jplan.room.services.data;

public class Message {

    private String action;
    private String data;

    public Message() {
	this("", "");
    }

    public Message(String action, String data) {
	this.action = action;
	this.data = data;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public Message withData(String data) {
	this.data = data;
	return this;
    }

    public Message withAction(String action) {
	this.action = action;
	return this;
    }

    @Override
    public String toString() {
	return "{action=" + action + ", data=" + data + "}";
    }

}
