package ee.ifjodorov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SiteCrawlerTest {

    private WebDriver driver;
    private Set<String> visitedUrls = new HashSet<>();

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void crawlSite() {
        String baseUrl = "https://app-sjqe.marketo.com/homepage/login";
        crawl(baseUrl);
    }

    private void crawl(String url) {
        if (!visitedUrls.contains(url)) {
            visitedUrls.add(url);
            driver.get(url);

            List<WebElement> elements = driver.findElements(By.xpath("//*"));
            for (WebElement element : elements) {
                if (!element.getText().isEmpty()) {
                    System.out.println("Text: " + element.getText());
                }
                if ("button".equals(element.getTagName()) || ("input".equals(element.getTagName()) && "button".equals(element.getAttribute("type")))) {
                    System.out.println("Button: " + element.getText() + ", " + element.getAttribute("outerHTML"));
                }
            }

            List<WebElement> links = driver.findElements(By.tagName("a"));
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                if (href != null && !href.isEmpty() && !visitedUrls.contains(href)) {
                    crawl(href); // Рекурсивный вызов для обхода сайта
                }
            }
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
