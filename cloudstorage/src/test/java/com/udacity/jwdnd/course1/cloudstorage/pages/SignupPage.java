package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(className = "result")
    private WebElement result;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstNameField(String fname) {
        firstNameField.sendKeys(fname);
    }

    public void setLastNameField(String lname) {
        lastNameField.sendKeys(lname);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void clearUsername() {
        usernameField.clear();
    }

    public void setPasswordField(String password) {
        passwordField.sendKeys(password);
    }

    public String getResult(){
        return result.getText();
    }

    public void submit(){
        usernameField.submit();
    }

}