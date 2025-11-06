package xyz.jplan.room.view;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;

@ViewScoped
public class Welcome implements Serializable {

    private static final long serialVersionUID = 101L;

    private String version;
    private String title;

    public void init() {
	this.version = "0.0.1v";
	this.title = "Home";
    }

    public String getVersion() {
	return this.version;
    }

    public String getTitle() {
	return this.title;
    }
}
