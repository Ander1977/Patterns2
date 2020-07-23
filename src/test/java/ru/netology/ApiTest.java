package ru.netology;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class ApiTest {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .addFilter(new ResponseLoggingFilter())
            .log(LogDetail.ALL)
            .build();

    private void setUpUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private void loginPage(String login, String password) {
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(login);
        $("[data-test-id=password] input").setValue(password);
        $("[data-test-id=action-login]").click();
    }

    @org.junit.jupiter.api.Test
    public void shouldUserActive() {
        UserInfo user = DataGenerator.RegistrationInfo.generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(user.getLogin(), user.getPassword());
        $(byText("Личный кабинет")).waitUntil(visible, 15000);
    }

    @org.junit.jupiter.api.Test
    public void shouldUserBlocked() {
        UserInfo user = DataGenerator.RegistrationInfo.generateUserInfo("ru", true);
        setUpUser(user);
        loginPage(user.getLogin(), user.getPassword());
        $(withText("Пользователь заблокирован")).waitUntil(visible, 15000);
    }

    @org.junit.jupiter.api.Test
    public void shouldUsernameNotCorrect() {
        UserInfo user = DataGenerator.RegistrationInfo.generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(DataGenerator.RegistrationInfo.userName("ru"), user.getPassword());
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }

    @org.junit.jupiter.api.Test
    public void shouldPasswordNotCorrect() {
        UserInfo user = DataGenerator.RegistrationInfo.generateUserInfo("ru", false);
        setUpUser(user);
        loginPage(user.getLogin(), DataGenerator.RegistrationInfo.userPassword("ru"));
        $(withText("Неверно указан логин или пароль")).waitUntil(visible, 15000);
    }
}

