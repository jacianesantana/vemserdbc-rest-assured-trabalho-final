package AutomationPractice.step;


import Itera.util.Browser;
import com.github.javafaker.Faker;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.*;



public class CarrinhoTest extends BaseTeste {

    public void fazerLogin() {
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

    }

    @Test
    public void deveAdicionarItemAoCarrinho() {
        fazerLogin();
        driver.get("https://practice.automationtesting.in/product/selenium-ruby/");
        driver.findElement(By.cssSelector("#product-160 > div.summary.entry-summary > form > button")).click();
        driver.get("https://practice.automationtesting.in/basket/");

        assertEquals(driver.findElement(By.cssSelector("#page-34 > div > div.woocommerce > form > table > tbody > tr.cart_item > td.product-name")).getText(), "Selenium Ruby");
    }

    @Test
    public void deveAumentarQuantidadeDeItensNoCarrinho(){
        deveAdicionarItemAoCarrinho();
        driver.findElement(By.cssSelector("#page-34 > div > div.woocommerce > form > table > tbody > tr.cart_item > td.product-quantity > div > input")).sendKeys(Keys.ARROW_UP);
        driver.findElement(By.cssSelector("#page-34 > div > div.woocommerce > form > table > tbody > tr:nth-child(2) > td > input.button")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        String quantidade = driver.findElement(By.cssSelector("#page-34 > div > div.woocommerce > form > table > tbody > tr.cart_item > td.product-quantity > div > input")).getAttribute("value");

        assertEquals(quantidade, "2");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);



        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div/div/div/div[1]/form/table/tbody/tr[1]/td[1]/a"));
        Actions action = new Actions(driver);
        action.moveToElement(element).click().perform();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

    }
}
