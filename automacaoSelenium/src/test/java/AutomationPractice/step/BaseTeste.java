package AutomationPractice.step;

import AutomationPractice.util.BrowserSetUp;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;


public class BaseTeste extends BrowserSetUp {

    @Before
    public void abrirNavegador() {

        browserUp("https://practice.automationtesting.in/");
    }

    @After
    public void fecharNavegador() {
        browserDown();
    }
}
