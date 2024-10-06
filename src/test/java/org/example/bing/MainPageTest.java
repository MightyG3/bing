package org.example.bing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver.manage().window().maximize();
        driver.get("https://www.bing.com");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @DisplayName("searchTest")
    @RepeatedTest(5)
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(searchButton);
        searchField.sendKeys(input);
        searchField.submit();
        WebElement searchPageField = driver.findElement(searchButton);
        assertEquals(input, searchPageField.getAttribute("value"), "Ошибка, не то слово");
    }

    By searchButton = By.cssSelector("#sb_form_q");
    By seleniumLinkNotAdv = By.cssSelector(":not(.b_adurl) > cite");

    public void clickElement(List<WebElement> results, int index) {
        if (index >= 0 && index < results.size()) {
            results.get(index).click();
        }
    }

    public void switchToNewTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(1));
        }
    }

    @DisplayName("urlTest")
    @RepeatedTest(10)
    public void site() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(searchButton);
        searchField.sendKeys(input);
        searchField.submit();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(seleniumLinkNotAdv, "selenium.dev"),
                ExpectedConditions.elementToBeClickable(seleniumLinkNotAdv)
        ));
        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElements(
                driver.findElements(seleniumLinkNotAdv)));
        for (WebElement link : results) {
            System.out.println("Link text: " + link.getText());
        }
        clickElement(results, 0);
        switchToNewTab();
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);
        assertTrue(currentUrl.contains("https://www.selenium.dev/"), "Ссылка не совпадает");
    }
}
