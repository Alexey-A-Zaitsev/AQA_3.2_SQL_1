package ru.netology.SQL.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.SQL.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorMessageLoginField = $("[data-test-id=login] .input__sub");
    private SelenideElement errorMessagePasswordField = $("[data-test-id=password] .input__sub");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void emptyLogin () {
        passwordField.setValue(DataHelper.generateRandomPassword());
        loginButton.click();
    }

    public void emptyPassword () {
        loginField.setValue(DataHelper.generateRandomLogin());
        loginButton.click();
    }

    public void emptyFields() {
        loginButton.click();
    }

    public void loginPageVisible() {
        loginField.shouldBe(visible);
        passwordField.shouldBe(visible);
    }

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }

    public void verifyErrorMessageLoginField() {
        errorMessageLoginField.shouldBe(visible);
    }

    public void verifyErrorMessagePasswordField() {
        errorMessagePasswordField.shouldBe(visible);
    }

    public void cleanPasswordField() {
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

    public void setLoginField(DataHelper.AuthLogin login) {
        loginField.setValue(login.getLogin());
    }

}
