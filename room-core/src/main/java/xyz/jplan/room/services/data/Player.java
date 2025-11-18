package xyz.jplan.room.services.data;

public class Player {
    private String name;
    private boolean isAnimating;
    private boolean hasVoted;

    public Player() {
	this("", false, false);
    }

    public Player(String name) {
	this(name, false, false);
    }

    public Player(String name, boolean isAnimating, boolean hasVoted) {
	this.name = name;
	this.isAnimating = isAnimating;
	this.hasVoted = hasVoted;
    }

    public Player withHasVoted(boolean hasVoted) {
	this.hasVoted = hasVoted;
	return this;
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

    public boolean isHasVoted() {
	return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
	this.hasVoted = hasVoted;
    }

}
