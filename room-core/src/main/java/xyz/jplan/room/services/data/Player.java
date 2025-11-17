package xyz.jplan.room.services.data;

public class Player {
    private String name;
    private boolean isAnimating;

    public Player() {
	this("", false);
    }

    public Player(String name) {
	this(name, false);
    }

    public Player(String name, boolean isAnimating) {
	this.name = name;
	this.isAnimating = isAnimating;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isAnimating() {
	return isAnimating;
    }

    public void setAnimating(boolean isAnimating) {
	this.isAnimating = isAnimating;
    }

}
