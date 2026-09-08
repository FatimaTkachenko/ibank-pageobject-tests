package ru.netology.ibank.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.ibank.page.DashboardPage;
import ru.netology.ibank.page.LoginPage;
import ru.netology.ibank.page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyTest {
    private static final String CARD1 = "5559 0000 0000 0001";
    private static final String CARD2 = "5559 0000 0000 0002";
    private static final int INITIAL_BALANCE = 10000;

    @BeforeEach
    public void setUp() {
        Configuration.headless = true;
        Configuration.browserCapabilities = new ChromeOptions()
                .addArguments("--no-sandbox", "--disable-dev-shm-usage");
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        loginPage.login("vasya", "qwerty123", "12345");
    }

    @Test
    public void shouldTransferMoneyBetweenCards() {
        DashboardPage dashboardPage = new DashboardPage();
        int balance1Initial = parseBalance(dashboardPage.getCardBalance(CARD1));
        int balance2Initial = parseBalance(dashboardPage.getCardBalance(CARD2));
        assertEquals(INITIAL_BALANCE, balance1Initial);
        assertEquals(INITIAL_BALANCE, balance2Initial);

        int transferAmount = 5000;
        dashboardPage.clickReplenish(CARD1);
        TransferPage transferPage = new TransferPage();
        transferPage.transfer(String.valueOf(transferAmount), CARD2);

        dashboardPage = new DashboardPage();
        int balance1After = parseBalance(dashboardPage.getCardBalance(CARD1));
        int balance2After = parseBalance(dashboardPage.getCardBalance(CARD2));

        assertEquals(balance1Initial + transferAmount, balance1After);
        assertEquals(balance2Initial - transferAmount, balance2After);
    }

    private int parseBalance(String balanceText) {
        return Integer.parseInt(balanceText.replaceAll("[^0-9]", ""));
    }
}