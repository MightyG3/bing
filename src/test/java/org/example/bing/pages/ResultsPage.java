package org.example.bing.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

//https://www.selenium.dev/
public class ResultsPage {
    final WebDriver driver;
    final WebDriverWait wait;
    @FindBy(css = "#sb_form_q")
    private WebElement searchField;

    @FindBy(css = ":not(.b_adurl) > cite")
    private List<WebElement> listResultLinks;

    public void clickElement(int index) {
        if (index >= 0 && index < listResultLinks.size()) {
            listResultLinks.get(index).click();
            System.out.println("Нажатие на результат под номером " + index);
        }
    }

    public String getSearchValue() {
        return searchField.getAttribute("value");
    }

    public void switchToNewTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() > 1) {
            driver.switchTo().window(tabs.get(1));
        }
    }

    public void waitForResults(String expectedText) {
        wait.until(ExpectedConditions.and(
                ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(":not(.b_adurl) > cite"), expectedText),
                ExpectedConditions.elementToBeClickable(By.cssSelector(":not(.b_adurl) > cite"))
        ));
        wait.until(ExpectedConditions.visibilityOfAllElements(listResultLinks));
    }

    public void printResult() {
        int index = 0;
        for (WebElement link : listResultLinks) {
            System.out.println("Ссылка " + index + " = " + link.getText());
            index++;
        }
    }

    public void CurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);
        assertTrue(currentUrl.contains("https://www.selenium.dev/"), "Ссылка не совпадает");
    }


    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

}
