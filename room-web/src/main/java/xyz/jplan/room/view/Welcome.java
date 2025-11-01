package xyz.jplan.room.view;

import java.io.Serial;
import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import xyz.jplan.room.service.InTest;

@Named("welcomeBean")
@ViewScoped
public class Welcome implements Serializable {

    public static final String TITLE = "Main";

    @Serial
    private static final long serialVersionUID = 101L;

    @Inject
    private InTest inTest;

    private String title;

    public void init() {
	title = Constants.TITLE_SEPARATOR + TITLE;
    }

    public String hello() {
	return "HI";
    }

    public String helloFromIn() {
	return inTest.hello();
    }

    public String getTitle() {
	return title;
    }

    public String helloWorld() {
	return "Hi hi";
    }

}
