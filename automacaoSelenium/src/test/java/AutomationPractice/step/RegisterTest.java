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

public class RegisterTest extends BaseTeste{

    Faker fake = new Faker();

    @Test
    public void deveCriarNovoUsuario() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String novoEmail = fake.internet().emailAddress();
        driver.findElement(By.id("reg_email")).sendKeys(novoEmail);
        String novaSenha = "senhaDificil**888///";
        driver.findElement(By.id("reg_password")).sendKeys(novaSenha);
        driver.findElement(By.cssSelector("#customer_login > div.u-column2.col-2 > form > p.woocomerce-FormRow.form-row > input.woocommerce-Button.button")).click();

        assertThat(driver.findElement(By.cssSelector("#page-36 > div > div.woocommerce > div > p:nth-child(1)")).getText(), containsString("Hello") );
    }

    @Test
    public void tentarCriarNovoUsuarioSemEmail() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String novoEmail = "";
        driver.findElement(By.id("reg_email")).sendKeys(novoEmail);
        String novaSenha = "senhaDificil**888///";
        driver.findElement(By.id("reg_password")).sendKeys(novaSenha);
        driver.findElement(By.cssSelector("#customer_login > div.u-column2.col-2 > form > p.woocomerce-FormRow.form-row > input.woocommerce-Button.button")).click();

        assertEquals(driver.getCurrentUrl(), "https://practice.automationtesting.in/my-account/");
    }

    @Test public void tentarCriarNovoUsuarioSemSenha() {
        driver.findElement(By.cssSelector("#menu-item-50 > a")).click();
        driver.manage().timeouts().implicitlyWait(200, TimeUnit.MILLISECONDS);
        String url = driver.getCurrentUrl();
        String urlAnuncio = "https://practice.automationtesting.in/#google_vignette";

        //Verifica se foi redirecionado para a pagina do anuncio
        if (url.equals(urlAnuncio)) {
            driver.get("https://practice.automationtesting.in/my-account/");
        }

        String novoEmail = fake.internet().emailAddress();
        driver.findElement(By.id("reg_email")).sendKeys(novoEmail);
        String novaSenha = "";
        driver.findElement(By.id("reg_password")).sendKeys(novaSenha);
        driver.findElement(By.cssSelector("#customer_login > div.u-column2.col-2 > form > p.woocomerce-FormRow.form-row > input.woocommerce-Button.button")).click();

        assertEquals(driver.getCurrentUrl(), "https://practice.automationtesting.in/my-account/");
    }
}
