package Itera.step;

import com.github.javafaker.Faker;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class TestCrud extends BaseTest {

    Faker fake = new Faker();

    //Definindo atributos do fake
    String fullName = fake.name().fullName();
    String job = fake.job().title();
    String address = fake.address().streetAddress();
    String city = fake.address().city();
    String cellPhone = fake.phoneNumber().cellPhone();
    String email = fake.internet().emailAddress();

    public String buscarEmail(String emailProcurar) {
        driver.findElement(By.id("searching")).sendKeys(emailProcurar);
        driver.findElement(By.cssSelector("body > div > div > form > input.btn.btn-secondary.my-2.my-sm-0")).click();

        String url = driver.getCurrentUrl();
        return url;
    }

    @Test
    public void criarNovoUser() {
        driver.findElement(By.xpath("//a[@href='/Customer/Create']")).click();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("Name")).sendKeys(fullName);
        driver.findElement(By.id("Company")).sendKeys(job);
        driver.findElement(By.id("Address")).sendKeys(address);
        driver.findElement(By.id("City")).sendKeys(city);
        driver.findElement(By.id("Phone")).sendKeys(cellPhone);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.cssSelector("body > div > form > div > div:nth-child(9) > div > input")).click();

        driver.findElement(By.id("searching")).sendKeys(email);
        driver.findElement(By.cssSelector("body > div > div > form > input.btn.btn-secondary.my-2.my-sm-0")).click();

        assertEquals(fullName, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(1)")).getText());
        assertEquals(job, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")).getText());
        assertEquals(address, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(3)")).getText());
        assertEquals(city, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(4)")).getText());
        assertEquals(cellPhone, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(5)")).getText());
        assertEquals(email, driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(6)")).getText());
    }
    @Test
    public void tentarCriarUserComCamposVazio() {
        driver.findElement(By.xpath("//a[@href='/Customer/Create']")).click();
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        driver.findElement(By.id("Name")).sendKeys("");
        driver.findElement(By.id("Company")).sendKeys("");
        driver.findElement(By.id("Address")).sendKeys("");
        driver.findElement(By.id("City")).sendKeys("");
        driver.findElement(By.id("Phone")).sendKeys("");
        driver.findElement(By.id("Email")).sendKeys("");
        driver.findElement(By.cssSelector("body > div > form > div > div:nth-child(9) > div > input")).click();

        //Nao deveria crirar usuário vazios
        assertFalse(driver.getCurrentUrl().equals("https://itera-qa.azurewebsites.net/Dashboard"));
    }

    @Test
    public void deveConsultarUser() {

        criarNovoUser();
        driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(7) > a.btn.btn-outline-info")).click();

        String nameExibido = driver.findElement(By.cssSelector("body > div > div > dl > dd:nth-child(2)")).getText();
        String jobExibido = driver.findElement(By.cssSelector("body > div > div > dl > dd:nth-child(4)")).getText();
        String cityExibido = driver.findElement(By.cssSelector("body > div > div > dl > dd:nth-child(8)")).getText();
        String phoneExibido = driver.findElement(By.cssSelector("body > div > div > dl > dd:nth-child(10)")).getText();
        String emailExibido = driver.findElement(By.cssSelector("body > div > div > dl > dd:nth-child(12)")).getText();

        assertEquals(nameExibido, fullName);
        assertEquals(jobExibido, job);
        assertEquals(cityExibido, city);
        assertEquals(phoneExibido, cellPhone);
        assertEquals(emailExibido, email);
    }
    @Test
    public void consultarUserQueNaoExiste() {
        driver.findElement(By.id("searching")).sendKeys("emailinvalido@emailinvalido.com");
        driver.findElement(By.cssSelector("body > div > div > form > input.btn.btn-secondary.my-2.my-sm-0")).click();

        assertEquals(driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td")).getText(), "No Match");
    }
    @Test
    public void consultarUserPassandoNumeros() {
        driver.findElement(By.id("searching")).sendKeys("999999");
        driver.findElement(By.cssSelector("body > div > div > form > input.btn.btn-secondary.my-2.my-sm-0")).click();

        assertEquals(driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td")).getText(), "No Match");
    }

    @Test
    public void deveEditarUser() {
        deveConsultarUser();
        driver.findElement(By.cssSelector("body > div > p > a.btn.btn-outline-primary")).click();

        String newName = fake.name().fullName();
        String newJob = fake.job().title();
        String newAddress = fake.address().streetAddress();
        String newCity = fake.address().city();
        String newPhone = fake.phoneNumber().cellPhone();
        String newEmail = fake.internet().emailAddress();

        driver.findElement(By.id("Name")).clear();
        driver.findElement(By.id("Company")).clear();
        driver.findElement(By.id("Address")).clear();
        driver.findElement(By.id("City")).clear();
        driver.findElement(By.id("Phone")).clear();
        driver.findElement(By.id("Email")).clear();


        driver.findElement(By.id("Name")).sendKeys(newName);
        driver.findElement(By.id("Company")).sendKeys(newJob);
        driver.findElement(By.id("Address")).sendKeys(newAddress);
        driver.findElement(By.id("City")).sendKeys(newCity);
        driver.findElement(By.id("Phone")).sendKeys(newPhone);
        driver.findElement(By.id("Email")).sendKeys(newEmail);

        driver.findElement(By.cssSelector("body > div > form > div > div:nth-child(10) > div > input")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String navegar = buscarEmail(newEmail);
        driver.get(navegar);


        String nameExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(1)")).getText();
        String jobExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
        String addressExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(3)")).getText();
        String cityExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(4)")).getText();
        String phoneExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(5)")).getText();
        String emailExibido = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(6)")).getText();

        assertEquals(newName, nameExibido);
        assertEquals(newJob, jobExibido);
        assertEquals(newAddress, addressExibido);
        assertEquals(newCity, cityExibido);
        assertEquals(newPhone, phoneExibido);
        assertEquals(newEmail, emailExibido);
    }
    @Test
    public void tentarEditarUserComCampoVazio() {
        deveConsultarUser();
        driver.findElement(By.cssSelector("body > div > p > a.btn.btn-outline-primary")).click();

        driver.findElement(By.id("Name")).clear();
        driver.findElement(By.id("Company")).clear();
        driver.findElement(By.id("Address")).clear();
        driver.findElement(By.id("City")).clear();
        driver.findElement(By.id("Phone")).clear();
        driver.findElement(By.id("Email")).clear();

        driver.findElement(By.cssSelector("body > div > form > div > div:nth-child(10) > div > input")).click();

        //Nao deveria editar user deixando campos vazios
        assertFalse(driver.getCurrentUrl().equals("https://itera-qa.azurewebsites.net/Dashboard"));
    }

    @Test
    public void deveDeletarUser() {
        criarNovoUser();
        String email = driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(6)")).getText();
        driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td:nth-child(7) > a.btn.btn-outline-danger")).click();
        driver.findElement(By.cssSelector("body > div > div > form > div > input")).click();

        driver.findElement(By.id("searching")).sendKeys(email);
        driver.findElement(By.cssSelector("body > div > div > form > input.btn.btn-secondary.my-2.my-sm-0")).click();

        assertEquals(driver.findElement(By.cssSelector("body > div > div > table > tbody > tr:nth-child(2) > td")).getText(), "No Match");
    }

}
