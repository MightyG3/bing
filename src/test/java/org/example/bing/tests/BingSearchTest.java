package org.example.bing.tests;

import org.example.bing.pages.MainPage;
import org.example.bing.pages.ResultsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BingSearchTest {
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

    @RepeatedTest(5)
    public void searchResultTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        ResultsPage rs = new ResultsPage(driver);
        mp.enterSearchText(input);
        assertEquals(input, rs.getSearchValue(), "Ошибка, не то слово");
    }

    @RepeatedTest(5)
    public void searchFieldTest() {
        String input = "Selenium";
        MainPage mp = new MainPage(driver);
        ResultsPage rs = new ResultsPage(driver);
        mp.enterSearchText(input);
        rs.waitForResults("selenium.dev");
        rs.printResult();
        rs.clickElement(0);
        rs.switchToNewTab();
        rs.CurrentUrl();
    }
}
