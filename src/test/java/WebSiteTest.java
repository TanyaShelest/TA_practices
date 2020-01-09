
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class WebSiteTest {

    public static WebDriver driver;
    public HashMap<String, String> vacancies;

    @Before
    public void setUp() throws IOException {

        System.setProperty("webdriver.gecko.driver", "/home/shelest/geckodriver");

        driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://www.google.com");

        System.out.println("Page title is: " + driver.getTitle());

        WebElement element = driver.findElement(By.name("q"));

        element.sendKeys("netcracker open positions");

        element.submit();

        driver.findElement(By.xpath("//h3[text()='Open Positions - Netcracker']")).click();

        driver.findElement(By.xpath("//button[@id='accept-gdrp']")).click();

        driver.findElement(By.xpath("//button[@title='All Departments']")).click();

        driver.findElement(By.xpath("//a[span/text()='IT']")).click();

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/home/shelest/nc_screenshot"));

        driver.findElement(By.xpath("//button[@data-id='jobdept']")).click();

        driver.findElement(By.xpath("//a[span/text()='All Departments']")).click();

        driver.findElement(By.xpath("//button[@title='All Locations']")).click();

        // Get a list of countries
        List<String> keys = new ArrayList<String>();
        List<WebElement> countries = driver.findElements(By.xpath("//li/a/span[@class='text']"));

        for (WebElement e : countries) {
            if (!(e.getText().equals("")) && (!(e.getText().equals("All Locations")))) {
                String key = e.getText();
                keys.add(key);
            }
        }

        driver.findElement(By.xpath("//a[span/text()='All Locations']")).click();

        // Get entries about open positions
        List<WebElement> entr = driver.findElements(By.xpath("//div[@id='positionslist']/div[@data-country]"));
        List<String> entries = new ArrayList<String>();

        for (WebElement e : entr) {
            if (!(e.getText().equals(""))) {
                String entry = e.getText();
                entries.add(entry);
            }
        }

        for (int i = 0; i < entr.size() / 20; i++) {
            WebElement nextPage = driver.findElement(By.xpath("//ul[@class='paging']/li[@class='page-item next']"));
            Actions action = new Actions(driver);
            action.moveToElement(nextPage);
            nextPage.click();
            List<WebElement> temp = driver.findElements(By.xpath("//div[@id='positionslist']/div[@data-country]"));
            for (WebElement e : temp) {
                if (!(e.getText().equals(""))) {
                    String entry = e.getText();
                    entries.add(entry);
                }
            }
        }

        vacancies = new HashMap<String, String>();

        // Put entries to the HashMap
        for (String key : keys) {
            StringBuilder str = new StringBuilder();
            for (String entry : entries) {
                String[] lines = entry.split("\n");
                String[] locations = lines[1].split(";");
                for (int i = 0; i < locations.length; i++) {
                    if (locations[i].contains(key)) {
                        vacancies.put(key, str.append(lines[0] + ", ").toString());

                    }
                }
            }
        }
    }

    @Test
    public void hashMapTest() {

        driver.get("https://www.netcracker.com/careers/open-positions");
        driver.findElement(By.xpath("//button[@title='All Locations']")).click();
        driver.findElement(By.xpath("//a[span/text()='Mexico']")).click();

        List<WebElement> positions = driver.findElements(By.xpath("//div[@class='job result active']/h3"));
        StringBuilder str = new StringBuilder();
        for (WebElement e : positions) {
            str.append(e.getText() + ", ");
        }

        assertEquals(str.toString(), vacancies.get("Mexico"));
    }


    @After
    public void tearDown(){
        driver.quit();
    }
}
    

