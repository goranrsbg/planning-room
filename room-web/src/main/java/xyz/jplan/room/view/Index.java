package xyz.jplan.room.view;

import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import xyz.jplan.room.services.TestService;

import java.io.Serializable;

@ViewScoped
public class Index implements Serializable {

    private static final long serialVersionUID = 101L;

    @EJB
    private TestService testService;

    public String hello() {
        return testService.hello();
    }
}
