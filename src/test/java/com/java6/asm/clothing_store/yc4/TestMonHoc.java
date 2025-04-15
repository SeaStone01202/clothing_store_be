package com.java6.asm.clothing_store.yc4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestMonHoc {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\Users\\haith\\Downloads\\edgedriver_win64\\msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Khởi tạo wait với timeout 10 giây
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testLogin1() {
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().setSize(new Dimension(1262, 666));
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test=\"login-button\"]")).click();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test=\"logout-sidebar-link\"]")));
        logoutBtn.click();
    }

    @Test
    public void testLogin2() {
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().setSize(new Dimension(1262, 666));
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).sendKeys("locked_out_user");
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test=\"login-button\"]")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector("[data-test=\"error\"]"), "Epic sadface: Sorry, this user has been locked out."));
        }
    }

    @Test
    public void testLogin3() {
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().setSize(new Dimension(1262, 666));
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"username\"]")).sendKeys("performance_glitch_user");
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).click();
        driver.findElement(By.cssSelector("[data-test=\"password\"]")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test=\"login-button\"]")).click();
        driver.findElement(By.id("react-burger-menu-btn")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-test=\"logout-sidebar-link\"]")));
        logoutBtn.click();
    }
}
