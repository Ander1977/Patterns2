package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.RegistrationInfo.*;

public class ApiTest {

    private void loginPage(String login, String password) {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }

    @Test
    public void shouldLoginWithValidActiveUser() {
        UserInfo user = generateValidUserInfo("en", false);
        loginPage(user.getLogin(), user.getPassword());
        $(byText("Личный кабинет")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfNotRegisteredUser() {
        UserInfo user = generateUserInfo("en", false);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfUserBlocked() {
        UserInfo user = generateValidUserInfo("en", true);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Пользователь заблокирован")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfInvalidLogin() {
        UserInfo user = generateInvalidLoginUserInfo("en", false);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @Test
    public void shouldGetErrorIfInvalidPassword() {
        UserInfo user = generateInvalidPasswordUserInfo("en", false);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }
}

