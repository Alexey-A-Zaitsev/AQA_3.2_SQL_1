package ru.netology.SQL.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
    private SelenideElement errorMessageCodeField = $("[data-test-id=code] .input__sub");

    public void verificationPageVisibility() {
        codeField.shouldBe(visible);
    }

    public void errorMessageVisibility() {
        errorNotification.shouldBe(visible);
    }

    public void verify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
    }

    public void emptyFieldCode() {
        verifyButton.click();
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void errorMessageCodeFieldVisibility() {
        errorMessageCodeField.shouldBe(visible);
    }

}
