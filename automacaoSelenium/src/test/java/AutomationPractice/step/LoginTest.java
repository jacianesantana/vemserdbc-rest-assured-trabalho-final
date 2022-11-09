package AutomationPractice.step;


import com.github.javafaker.Faker;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.*;

public class LoginTest extends BaseTeste{

    Faker fake = new Faker();

    @Test
    public void logarComUsuarioCadastrado() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String emailLogin = "usuariodeteste@exemplo.com";
        driver.findElement(By.id("username")).sendKeys(emailLogin);
        String senhaLogin = "senhaDificil**888///";
        driver.findElement(By.id("password")).sendKeys(senhaLogin);
        driver.findElement(By.cssSelector("#customer_login > div.u-column1.col-1 > form > p:nth-child(3) > input.woocommerce-Button.button")).click();

        assertThat(driver.findElement(By.cssSelector("#page-36 > div > div.woocommerce > div > p:nth-child(1)")).getText(), containsString("Hello") );
    }

    @Test
    public void tentarLogarComUsuarioNaoCadastrado() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String emailLogin = "usernaocadastrado@email.com";
        driver.findElement(By.id("username")).sendKeys(emailLogin);
        String senhaLogin = "senhaDificil**888///";
        driver.findElement(By.id("password")).sendKeys(senhaLogin);
        driver.findElement(By.cssSelector("#customer_login > div.u-column1.col-1 > form > p:nth-child(3) > input.woocommerce-Button.button")).click();

        assertEquals(driver.getCurrentUrl(), "https://practice.automationtesting.in/my-account/");
        assertThat(driver.findElement(By.cssSelector("#page-36 > div > div.woocommerce > ul > li")).getText(), containsString("Error: A user could not be found with this email address."));
    }

    @Test
    public void tentarLogarSemDigitarCampos() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String emailLogin = "";
        driver.findElement(By.id("username")).sendKeys(emailLogin);
        String senhaLogin = "";
        driver.findElement(By.id("password")).sendKeys(senhaLogin);
        driver.findElement(By.cssSelector("#customer_login > div.u-column1.col-1 > form > p:nth-child(3) > input.woocommerce-Button.button")).click();

        assertEquals(driver.getCurrentUrl(), "https://practice.automationtesting.in/my-account/");
        assertThat(driver.findElement(By.cssSelector("#page-36 > div > div.woocommerce > ul > li")).getText(), containsString("Error: Username is required."));
    }

}
