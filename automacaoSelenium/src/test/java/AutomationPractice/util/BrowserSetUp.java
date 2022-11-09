package AutomationPractice.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class BrowserSetUp {
    public static WebDriver driver;
    public static WebDriverWait wait;



    public void  browserUp(String url) {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 40);
        driver.get(url);

        //Maximizar
        driver.manage().window().maximize();

        //Delay
        driver.manage().timeouts().implicitlyWait(600, TimeUnit.MILLISECONDS);
    }


    public void browserDown() {
//        driver.quit();
    }
}
