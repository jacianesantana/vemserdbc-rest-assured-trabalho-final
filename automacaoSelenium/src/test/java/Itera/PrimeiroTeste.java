package Itera;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PrimeiroTeste {
    public static WebDriver driver;
    public static WebDriverWait wait;
    Faker fake = new Faker();


    @Before
    public void abrirNavegador() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 40);
        driver.get("https://itera-qa.azurewebsites.net/UserRegister/NewUser");

        //Maximizar
        driver.manage().window().maximize();

        //Delay
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
    }

    @After
    public void fecharNavegador() {
//        driver.quit();
    }
    @Test
    public void validarLoginComUsernameSenhaValidos() {


        driver.findElement(By.cssSelector("[id=\"Username\"]")).sendKeys("teste123");
        driver.findElement(By.cssSelector("[id=\"Password\"]")).sendKeys("12345");
        driver.findElement(By.cssSelector("[name=\"Username\"]")).click();

        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);

    }
    
}
