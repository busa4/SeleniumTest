package ee.ifjodorov;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {


    String login = "sanshuklde0326.qedemar26@marketo.com";
    String pass = "Adobe@2024";
    String url = "https://app-sjqe.marketo.com/homepage/login";

    private List<WebElement> buttons = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private List<WebElement> navItems = new ArrayList<>();

    @Test
    public void firstTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        login(driver);
        Thread.sleep(18000);
        linkCollector(driver);
        System.out.println("Links count: " + links.size());
        System.out.println("Buttons count: " + buttons.size());
        System.out.println("Nav elements: " + navItems.size());
        ListIterator<WebElement> iterator = buttons.listIterator();
        while (iterator.hasNext()) {
            try {
                WebElement element = iterator.next();
                System.out.println(element.getAccessibleName());
                element.click();
                System.out.println("Was clickable");
            } catch (Exception e){
                e.printStackTrace();
            }

        }
        driver.quit(); // Не забудьте закрыть драйвер после выполнения теста
    }
    private void linkCollector(WebDriver driver) {
        addToList(driver);
        for (int i = 0; i < getFrameCount(driver); i++) {
            driver.switchTo().frame(i);
            linkCollector(driver);
            driver.switchTo().parentFrame();
        }
    }
    private void login(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwd")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
        username.sendKeys(login);
        passwd.sendKeys(pass);
        loginButton.submit();
    }
    private void addToList(WebDriver driver){
        buttons.addAll(driver.findElements(By.tagName("button")));
        links.addAll(getLinksOnFrame(driver));
        navItems.addAll(driver.findElements(By.className("_adobe-rc_rc-nav-item")));
    }
    private List<String> getLinksOnFrame(WebDriver driver){
        List<WebElement> as = driver.findElements(By.tagName("a"));
        List<String> lnks = new ArrayList<>();
        for (WebElement a : as) {
            String href = a.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                lnks.add(href);
            }
        }
        return lnks;
    }
    private int getFrameCount(WebDriver driver){
        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        frames.addAll(driver.findElements(By.tagName("frame")));
//        System.out.println("Найдено фреймов: " + frames.size());
        return frames.size();
    }
}
