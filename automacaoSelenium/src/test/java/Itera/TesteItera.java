package Itera;

import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static org.testng.AssertJUnit.*;

public class TesteItera {
    public static WebDriver driver;
    public static WebDriverWait wait;
    Faker fake = new Faker();


    @Before
    public void abrirNavegador() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 40);
        driver.get("https://itera-qa.azurewebsites.net/home/automation");

        //Maximizar
        driver.manage().window().maximize();

        //Delay
        driver.manage().timeouts().implicitlyWait(6000, TimeUnit.MILLISECONDS);
    }

    @After
    public void fecharNavegador() {
        driver.quit();
    }
    @Test
    public void clicarSubmitComCamposValidos() {

        String nome = fake.name().fullName();
        driver.findElement(By.cssSelector("[id=\"name\"]")).sendKeys(nome);
        String numeroTelefone = fake.phoneNumber().cellPhone();
        driver.findElement(By.cssSelector("[id=\"phone\"]")).sendKeys(numeroTelefone);
        String email = fake.internet().emailAddress();
        driver.findElement(By.cssSelector("[id=\"email\"]")).sendKeys(email);
        String senha = fake.internet().password();
        driver.findElement(By.cssSelector("[id=\"password\"]")).sendKeys(senha);
        String endereco = fake.address().fullAddress();
        driver.findElement(By.cssSelector("[id=\"address\"]")).sendKeys(endereco);
        driver.findElement(By.cssSelector("[name=\"submit\"]")).click();


        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);


        String nomeEsperado = driver.findElement(By.cssSelector("[id=\"name\"]")).getAttribute("value");
        String numeroTelefoneEsperado = driver.findElement(By.cssSelector("[id=\"phone\"]")).getAttribute("value");
        String emailEsperado = driver.findElement(By.cssSelector("[id=\"email\"]")).getAttribute("value");
        String senhaEsperada = driver.findElement(By.cssSelector("[id=\"password\"]")).getAttribute("value");
        String enderecoEsperado = driver.findElement(By.cssSelector("[id=\"address\"]")).getAttribute("value");

        assertEquals(nome, nomeEsperado);
        assertEquals(numeroTelefone, numeroTelefoneEsperado);
        assertEquals(email, emailEsperado);
        assertEquals(senha, senhaEsperada);
        assertEquals(endereco, enderecoEsperado);
    }


    @Test
    public void clicarSubmitComEmailInvalido() {


        String nome = fake.name().fullName();
        driver.findElement(By.cssSelector("[id=\"name\"]")).sendKeys(nome);
        String numeroTelefone = fake.phoneNumber().cellPhone();
        driver.findElement(By.cssSelector("[id=\"phone\"]")).sendKeys(numeroTelefone);
        String email = "123456789";
        driver.findElement(By.cssSelector("[id=\"email\"]")).sendKeys(email);
        String senha = fake.internet().password();
        driver.findElement(By.cssSelector("[id=\"password\"]")).sendKeys(senha);
        String endereco = fake.address().fullAddress();
        driver.findElement(By.cssSelector("[id=\"address\"]")).sendKeys(endereco);
        driver.findElement(By.cssSelector("[name=\"submit\"]")).click();


        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        assertEquals("email inválido", driver.findElement(By.id("email")).getAttribute("value"));

    }

    @Test
    public void clicarSubmitComTelefoneInvalido(){

        String nome = fake.name().fullName();
        driver.findElement(By.cssSelector("[id=\"name\"]")).sendKeys(nome);
        String numeroTelefone = "-50";
        driver.findElement(By.cssSelector("[id=\"phone\"]")).sendKeys(numeroTelefone);
        String email = "123456789";
        driver.findElement(By.cssSelector("[id=\"email\"]")).sendKeys(email);
        String senha = fake.internet().password();
        driver.findElement(By.cssSelector("[id=\"password\"]")).sendKeys(senha);
        String endereco = fake.address().fullAddress();
        driver.findElement(By.cssSelector("[id=\"address\"]")).sendKeys(endereco);
        driver.findElement(By.cssSelector("[name=\"submit\"]")).click();


        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        assertEquals("Telefone inválido", driver.findElement(By.id("phone")).getAttribute("value"));

    }

    @Test
    public void selecionarRadioButtonFemaleCorretamente() {
        driver.findElement(By.id("female")).click();

        assertTrue(driver.findElement(By.id("female")).isSelected());
    }

    @Test
    public void selecionarRadioButtonMaleCorretamente() {
        driver.findElement(By.id("male")).click();

        assertTrue(driver.findElement(By.id("male")).isSelected());
    }
    @Test
    public void tentarSelecionarRadioButtonDesativado() {
        driver.findElement(By.id("other")).click();

        assertFalse(driver.findElement(By.id("other")).isSelected());
    }

    @Test
    public void selecionarTodosOsDiasDaSemana() {
        driver.findElement(By.id("monday")).click();
        driver.findElement(By.id("tuesday")).click();
        driver.findElement(By.id("wednesday")).click();
        driver.findElement(By.id("thursday")).click();
        driver.findElement(By.id("friday")).click();
        driver.findElement(By.id("saturday")).click();
        driver.findElement(By.id("sunday")).click();

        assertTrue(driver.findElement(By.id("monday")).isSelected());
        assertTrue(driver.findElement(By.id("tuesday")).isSelected());
        assertTrue(driver.findElement(By.id("wednesday")).isSelected());
        assertTrue(driver.findElement(By.id("thursday")).isSelected());
        assertTrue(driver.findElement(By.id("friday")).isSelected());
        assertTrue(driver.findElement(By.id("saturday")).isSelected());
        assertTrue(driver.findElement(By.id("sunday")).isSelected());
    }

    @Test
    public void desselecionarTodosOsDiasDaSemana() {
        //Selecionando todos os dias
        driver.findElement(By.id("monday")).click();
        driver.findElement(By.id("tuesday")).click();
        driver.findElement(By.id("wednesday")).click();
        driver.findElement(By.id("thursday")).click();
        driver.findElement(By.id("friday")).click();
        driver.findElement(By.id("saturday")).click();
        driver.findElement(By.id("sunday")).click();

        //Desselecionando todos os dias
        driver.findElement(By.id("monday")).click();
        driver.findElement(By.id("tuesday")).click();
        driver.findElement(By.id("wednesday")).click();
        driver.findElement(By.id("thursday")).click();
        driver.findElement(By.id("friday")).click();
        driver.findElement(By.id("saturday")).click();
        driver.findElement(By.id("sunday")).click();


        assertFalse(driver.findElement(By.id("monday")).isSelected());
        assertFalse(driver.findElement(By.id("tuesday")).isSelected());
        assertFalse(driver.findElement(By.id("wednesday")).isSelected());
        assertFalse(driver.findElement(By.id("thursday")).isSelected());
        assertFalse(driver.findElement(By.id("friday")).isSelected());
        assertFalse(driver.findElement(By.id("saturday")).isSelected());
        assertFalse(driver.findElement(By.id("sunday")).isSelected());
    }

    @Test
    public void selecionarUmaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Norway");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Norway");
    }
    @Test
    public void selecionarTerceiraOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Turkey");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Turkey");
    }
    @Test
    public void selecionarQuartaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Greece");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Greece");
    }
    @Test
    public void selecionarQuintaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Italy");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Italy");
    }@Test
    public void selecionarSextaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Malta");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Malta");
    }
    @Test
    public void selecionarSetimaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Sweden");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Sweden");
    }
    @Test
    public void selecionarOitavaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Denmark");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Denmark");
    }

    @Test
    public void selecionarNonaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Finland");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Finland");
    }

    @Test
    public void selecionarNenhumaOpcaoDoDropdown() {

        Select paises = new Select(driver.findElement(By.className("custom-select")));
        paises.selectByVisibleText("Select option");
        String paisSelecionado = paises.getFirstSelectedOption().getText();

        assertEquals(paisSelecionado, "Select option");
    }

    @Test
    public void selecionarApenasUmaOpcaoDoRadioButtonDeAnos(){



        driver.findElement(By.cssSelector("label[for='1year']")).click();

        assertFalse(driver.findElement(By.id("2years")).isSelected());
        assertFalse(driver.findElement(By.id("3years")).isSelected());
        assertFalse(driver.findElement(By.id("4years")).isSelected());
        assertTrue(driver.findElement(By.id("1year")).isSelected());
    }

    @Test
    public void selecionarVariasOpcoesDeFrameworks() {
        driver.findElement(By.cssSelector("label[for='selenium']")).click();
        driver.findElement(By.cssSelector("label[for='cucumber']")).click();
        driver.findElement(By.cssSelector("label[for='mabl']")).click();

        assertTrue(driver.findElement(By.id("selenium")).isSelected());
        assertTrue(driver.findElement(By.id("cucumber")).isSelected());
        assertTrue(driver.findElement(By.id("mabl")).isSelected());

    }

}
