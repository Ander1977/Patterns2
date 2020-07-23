package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static ru.netology.DataGenerator.RegistrationInfo.generateUserInfo;
import static ru.netology.DataGenerator.RegistrationInfo.setUpUser;

public class ApiTest {

    private void loginPage(String login, String password) {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }

    @Test
    public void shouldUserActive() {
        UserInfo user = generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(user.getLogin(), user.getPassword());
        $(byText("Личный кабинет")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldUserBlocked() {
        UserInfo user = generateUserInfo("ru", true);
        setUpUser(user);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Пользователь заблокирован")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldUsernameNotCorrect() {
        UserInfo user = generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(DataGenerator.RegistrationInfo.userName("ru"), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldPasswordNotCorrect() {
        UserInfo user = generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(user.getLogin(), DataGenerator.RegistrationInfo.userPassword("ru"));
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }
}

