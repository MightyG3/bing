package org.example.bing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        //ChromeOptions options =new ChromeOptions();
        EdgeOptions options = new EdgeOptions();
        // Fix the issue https://github.com/SeleniumHQ/selenium/issues/11750
        options.addArguments("--remote-allow-origins=*");
        //driver = new ChromeDriver(options);
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("searchTest")
    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(searchBotton);
        searchField.sendKeys(input);
        searchField.submit();
        WebElement searchPageField = driver.findElement(searchBotton);
        assertEquals(input, searchPageField.getAttribute("value"), "Ошибка, не то слово");
    }

    By searchBotton = By.cssSelector("#sb_form_q");

    public void clickElement(List<WebElement> results, int index) {
        results.get(index).click();
    }

    public void switchToNewTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(1));
        }
    }

    @DisplayName("urlTest")
    @Test
    public void site() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(searchBotton);
        searchField.sendKeys(input);
        searchField.submit();
        List<WebElement> links = driver.findElements(By.cssSelector("h2>a[href]"));
        clickElement(links, 0);
        switchToNewTab();
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://www.selenium.dev/"), "Ссылка не совпадает");
    }
}
