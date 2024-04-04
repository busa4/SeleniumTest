package ee.ifjodorov;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class GoogleSignUpAutomation {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://accounts.google.com/signup");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.findElement(By.id("firstName")).sendKeys("Ivan");
            driver.findElement(By.id("lastName")).sendKeys("Fjodorov");
            driver.findElement(By.xpath("//button[contains(.,'Next')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("day")));
            driver.findElement(By.xpath("//button[contains(.,'Dismiss')]")).click();
            driver.findElement(By.id("day")).sendKeys("01");
            driver.findElement(By.id("year")).sendKeys("2001");
            WebElement genderDropdown = driver.findElement(By.id("gender"));
            Select genderSelect = new Select(genderDropdown);
            genderSelect.selectByValue("1");
            WebElement monthDropdown = driver.findElement(By.id("month"));
            Select monthSelect = new Select(monthDropdown);
            monthSelect.selectByValue("7");
            driver.findElement(By.xpath("//button[contains(.,'Next')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("selectionc4")));
            Thread.sleep(1000);
            driver.findElement(By.xpath("//div[@role='radio' and @aria-labelledby='selectionc4']")).click();
            driver.findElement(By.xpath("//input[@aria-label='Create a Gmail address']")).sendKeys("ivanfjodorov" + ((Math.random() * (1000 - 1)) + 1));
            driver.findElement(By.xpath("//button[contains(.,'Next')]")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("passwd")));
            driver.findElement(By.xpath("//input[@name='Passwd']")).sendKeys("qwerty123Qwerty!");
            driver.findElement(By.xpath("//input[@name='PasswdAgain']")).sendKeys("qwerty123Qwerty!");


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }
}
