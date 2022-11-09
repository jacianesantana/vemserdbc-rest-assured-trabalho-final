package Itera.step;

import Itera.util.Browser;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;


import java.util.concurrent.TimeUnit;

public class BaseTest extends Browser {

    @Before
    public void abrirNavegador() {
       browserUp("https://itera-qa.azurewebsites.net/");
       driver.findElement(By.xpath("//*[@id=\"navbarColor01\"]/form/ul/li[2]/a")).click();
       driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
       driver.findElement(By.id("Username")).sendKeys("TestUser100");
       driver.findElement(By.id("Password")).sendKeys("123456789");
       driver.findElement(By.xpath("/html/body/div/div[1]/form/table/tbody/tr[7]/td[2]/input[1]")).click();
       driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MICROSECONDS);
    }

    @After
    public void fecharNavegador() {
        browserDown();
    }
}
