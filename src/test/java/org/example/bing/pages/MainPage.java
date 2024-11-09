package org.example.bing.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//https://www.bing.com

public class MainPage {

    @FindBy(css = "#sb_form_q")
    private WebElement searchField;

    public void enterSearchText(String text) {
        searchField.sendKeys(text);
        searchField.submit();
    }

    public String getSearchValue() {
        return searchField.getAttribute("value");
    }

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
