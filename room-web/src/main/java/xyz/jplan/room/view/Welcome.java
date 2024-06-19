package xyz.jplan.room.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import xyz.jplan.room.services.TestService;

import java.io.Serial;
import java.io.Serializable;

@ViewScoped
public class Welcome implements Serializable {

    @Serial
    private static final long serialVersionUID = 101L;

    @Inject
    TestService testService = new TestService();

    public String getHello() {
        return testService.hello();
    }
}
