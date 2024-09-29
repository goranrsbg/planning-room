package xyz.jplan.room.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import xyz.jplan.room.service.InTest;
import xyz.jplan.room.services.TestService;

import java.io.Serial;
import java.io.Serializable;


@Named("welcomeBean")
@ViewScoped
public class Welcome implements Serializable {

    public static final String TITLE = "Main";

    @Serial
    private static final long serialVersionUID = 101L;

    @Inject
    private TestService testService;

    @Inject
    private InTest inTest;

    private String title;

    public void init() {
        title = Constants.TITLE_SEPARATOR + TITLE;
    }

    public String hello() {
        return testService.hello();
    }

    public String helloFromIn() {
        return inTest.hello();
    }

    public String getTitle() {
        return title;
    }

}
