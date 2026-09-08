package ru.netology.ibank.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");
    private SelenideElement verificationField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public void login(String login, String password, String verificationCode) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        verificationField.should(visible, Duration.ofSeconds(10)).setValue(verificationCode);
        verifyButton.click();
    }
}