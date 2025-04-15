package com.java6.asm.clothing_store.yc4;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class SeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js;

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
    public void testLogin() {
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1343, 721));
        driver.findElement(By.id("accountDropdown")).click();
        driver.findElement(By.linkText("🔑 Đăng nhập")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).sendKeys("admin@gmail.com");
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).sendKeys("admin");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".btn-primary"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(ExpectedConditions.textToBe(By.id("accountDropdown"), "Nguyễn Văn Admin"));
        }
    }

    @Test
    public void testRegister() {
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1343, 721));
        driver.findElement(By.id("accountDropdown")).click();
        driver.findElement(By.linkText("📝 Đăng ký")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).sendKeys("Trần Văn Test");
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).sendKeys("test05@gmail.com");
        driver.findElement(By.cssSelector(".mb-3:nth-child(3) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(3) > .form-control")).sendKeys("Admin");
        driver.findElement(By.cssSelector(".mb-3:nth-child(4) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(4) > .form-control")).sendKeys("Admin");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert"), "🎉 Đăng ký thành công! Vui lòng đăng nhập."));
        }
    }

    @Test
    public void testCart() {
        driver.get("http://localhost:5173/");
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.findElement(By.id("accountDropdown")).click();
        driver.findElement(By.linkText("🔑 Đăng nhập")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(1) > .form-control")).sendKeys("admin@gmail.com");
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).click();
        driver.findElement(By.cssSelector(".mb-3:nth-child(2) > .form-control")).sendKeys("admin");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".btn-primary"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".col-md-4:nth-child(2) .btn")).click();
        driver.findElement(By.cssSelector(".btn-success")).click();
        driver.findElement(By.cssSelector(".btn-success")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert"), "Sản phẩm đã được thêm vào giỏ hàng!"));
        }
        driver.findElement(By.cssSelector(".position-relative")).click();
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert"), "Đã tăng số lượng sản phẩm."));
        }
        driver.findElement(By.cssSelector(".btn-outline-secondary:nth-child(1)")).click();
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert"), "Đã giảm số lượng sản phẩm."));
        }
        driver.findElement(By.cssSelector(".btn-danger")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".btn-danger"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".alert"), "Đã xóa sản phẩm khỏi giỏ hàng."));
        }
    }
}