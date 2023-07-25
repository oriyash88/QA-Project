//Name: Oriya.

package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Test {
    WebDriver driver;
    String randomEmail;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get("http://automationpractice.com/index.php");
    }


    @org.testng.annotations.Test(priority = 1)  //verify Page
    public void verifyPageTest(){
        String title = driver.getTitle();
        System.out.println("the page title is: " + title);  // Title check
        Assert.assertEquals(title, "My Store");  // TestNG

        boolean flag = driver.findElement(By.xpath("//img[@class='logo img-responsive']")).isDisplayed(); //Logo check
        Assert.assertTrue(flag);  // TestNG
    }


    @org.testng.annotations.Test(priority = 2)
    public void registrationAndBuy1(){

     try {

         register();
         chooseItem();
         checkOutAndPayment();

     }catch (NoSuchElementException e){
         e.printStackTrace();
     }
    }


    public void register(){
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account"); //to the page

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        randomEmail = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)     //  randomEmail+="@gmail.com";
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).append("@gmail.com").toString();

        driver.findElement(By.id("email_create")).sendKeys(randomEmail);
        driver.findElement(By.xpath("//*[@id=\"SubmitCreate\"]/span")).click(); // click on "Create an account"

        //"CREATE AN ACCOUNT" Page
        driver.findElement(By.id("id_gender1")).click(); //Title
        driver.findElement(By.id("customer_firstname")).sendKeys("Oriya"); // First name
        driver.findElement(By.id("customer_lastname")).sendKeys("Sharabi"); // Last name

        driver.findElement(By.id("passwd")).sendKeys("123456"); // Password

        Select day = new Select(driver.findElement(By.id("days"))); // Date
        day.selectByValue("11");
        Select month = new Select(driver.findElement(By.id("months")));  // Date
        month.selectByValue("10");
        Select year = new Select(driver.findElement(By.id("years"))); // Date
        year.selectByValue("1997");

        driver.findElement(By.id("address1")).sendKeys("Herzl"); //Address
        driver.findElement(By.id("city")).sendKeys("Yavne"); //City
        Select state = new Select(driver.findElement(By.id("id_state"))); // State
        state.selectByVisibleText("Texas");
        driver.findElement(By.id("postcode")).sendKeys("00000"); // Zip
        Select country = new Select(driver.findElement(By.id("id_country"))); // Country
        country.selectByVisibleText("United States");

        driver.findElement(By.id("phone_mobile")).sendKeys("0521234567"); // Mobile phone

        driver.findElement(By.xpath("//*[@id=\"submitAccount\"]/span")).click(); // Register

        if (driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=my-account"))
            System.out.println("Register Succeeded");
        else {
            System.out.println("Register failed");
            driver.close();
        }
    }

    public void chooseItem(){

        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[2]/a")).click(); // click on "Dresses"
        driver.findElement(By.xpath("//*[@id=\"list\"]/a/i")).click(); // view "List"
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div/div[3]/div/div[2]/a[1]/span")).click(); // choose item1
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/span/span")).click(); // continue
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[2]/div/div/div[3]/div/div[2]/a[1]/span")).click(); // choose item2
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[2]/div[4]/a/span")).click(); // checkout1
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/p[2]/a[1]/span")).click(); // checkout2

        if(driver.getCurrentUrl().equals("http://automationpractice.com/index.php?controller=order&step=1"))
            System.out.println("Choose item succeeded");
        else {
            System.out.println("Choose item failed");
            driver.close();
        }

    }

    public void checkOutAndPayment(){

        driver.findElement(By.xpath("//*[@id=\"center_column\"]/form/p/button/span")).click(); //checkout
        driver.findElement(By.id("cgv")).click();

        driver.findElement(By.xpath("//*[@id=\"form\"]/p/button/span")).click(); //continue

        driver.findElement(By.xpath("//*[@id=\"HOOK_PAYMENT\"]/div[1]/div/p/a")).click(); //payment

        if(driver.getCurrentUrl().equals("http://automationpractice.com/index.php?fc=module&module=bankwire&controller=payment"))
            System.out.println("Payment succeeded");
        else {
            System.out.println("Payment failed");
            driver.close();
        }

        driver.findElement(By.xpath("//*[@id=\"cart_navigation\"]/button/span")).click(); //confirm

        System.out.println("The website says: " + '"' +  "Your order on My Store is complete." + '"');

    }


    @AfterMethod
    public void tearDown(){
       driver.quit();
    }
}
