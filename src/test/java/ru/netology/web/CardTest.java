package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUaAll() {
        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSendingForm() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Шатунова Иванна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79168580322");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendingLatinSymbols() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivanna");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79168580322");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendEmptyName() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79168580322");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendSpaceInName() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79168580322");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendEmptyPhone() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Шатунова Иванна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendIncompletePhone() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Шатунова Иванна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7916858032");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendPhoneMoreThanEleven() {
        driver.get("http://0.0.0.0:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Шатунова Иванна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79168580322222");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("[type=button]")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span.input__sub")).getText().trim();
        assertEquals(expected, actual);
    }


}





