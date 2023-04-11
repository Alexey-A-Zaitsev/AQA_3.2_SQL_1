package ru.netology.SQL.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.SQL.data.DataHelper;
import ru.netology.SQL.data.SQLHelper;
import ru.netology.SQL.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @AfterAll
    static void databaseCleanup() {
        SQLHelper.clearDatabase();
    }


    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    @DisplayName("should get an error message if the login invalid")
    void shouldErrorMessageIfLoginInvalid() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authLogin = DataHelper.generateRandomLogin();
        var authPass = DataHelper.getAuthPassword().getPassword();
        loginPage.inputInLoginField(authLogin);
        loginPage.inputInPasswordField(authPass);
        loginPage.emptyFields(); // используем для клика по кнопке
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("should get an error message if the password invalid")
    void shouldErrorMessageIfPasswordInvalid() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authLogin = DataHelper.getAuthLogin().getLogin();
        var authPass = DataHelper.generateRandomPassword();
        loginPage.inputInLoginField(authLogin);
        loginPage.inputInPasswordField(authPass);
        loginPage.emptyFields(); // используем для клика по кнопке
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("should get an error message if the user is not in the database")
    void shouldErrorMessageIfUserExist() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("should get an error message if the field Login empty")
    void shouldErrorMessageIfLoginEmpty() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomPassword();
        loginPage.emptyLogin(authInfo);
        loginPage.verifyErrorMessageLoginField();
    }

    @Test
    @DisplayName("should get an error message if the field Password empty")
    void shouldErrorMessageIfPasswordEmpty() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomLogin();
        loginPage.emptyPassword(authInfo);
        loginPage.verifyErrorMessagePasswordField();
    }

    @Test
    @DisplayName("should get an error messages if the all fields empty")
    void shouldErrorMessageIfFieldsEmpty() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.emptyFields();
        loginPage.verifyErrorMessageLoginField();
        loginPage.verifyErrorMessagePasswordField();
    }

    @Test
    @DisplayName("should get an error message if verification code is random")
    void shouldErrorMessageIfRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        var verificationCode = DataHelper.generateRandomCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.errorMessageVisibility();
    }

    @Test
    @DisplayName("should get an error message if \"verification code\" field is empty")
    void shouldErrorMessageIfFieldCodeEmpty() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verificationPageVisibility();
        verificationPage.emptyFieldCode();
        verificationPage.errorMessageCodeFieldVisibility();
    }

    @Test
    @DisplayName("should give an error message if you enter the wrong password three times")
    void shouldErrorMessageIfPasswordEnteredIncorrectlyThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var loginInfo = DataHelper.getAuthLogin();
        var password1 = DataHelper.generateRandomPassword();
        var password2 = DataHelper.generateRandomPassword();
        var password3 = DataHelper.generateRandomPassword();
        loginPage.setLoginField(loginInfo);
        loginPage.emptyLogin(password1);
        loginPage.cleanPasswordField();
        loginPage.emptyLogin(password2);
        loginPage.cleanPasswordField();
        loginPage.emptyLogin(password3);
        loginPage.userBlockMessage();
    }

}
