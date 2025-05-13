package stepDefinitions;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import propconfig.ConfigReader;

public class LoginStep {
	
	public static ExtentReports extent;
    public static ExtentTest test;
	
	
	
    WebDriver driver;
	ConfigReader config = Hooks.config; // shared configuration
	String url = config.getProperty("appURL");

	    @Given("Open the browser")
	    public void open_the_browser() {
	    	
	        String browser = config.getProperty("browser").toLowerCase();
	        switch (browser) {
	            case "chrome":
	                WebDriverManager.chromedriver().setup();
	                driver = new ChromeDriver();
	                break;
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                driver = new FirefoxDriver();
	                break;
	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                driver = new EdgeDriver();
	                break;
	        }

	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    }  
	     

	    @When("Enter the URL {string}")
	    public void enter_the_url(String ur) {
	    
	        String url = config.getProperty("appURL");
	        driver.get(url);

	        try {
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	            WebElement overlay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fc-dialog-scrollable-content")));
	            WebElement closeBtn = driver.findElement(By.className("fc-button-label"));
	            closeBtn.click();
	            wait.until(ExpectedConditions.invisibilityOf(overlay));
	        } catch (TimeoutException | NoSuchElementException e) {
	            // proceed
	        }

	        Hooks.test = Hooks.extent.createTest("Login Test - Page Load");
	        Hooks.test.pass("User landed on login page"); 
	        
	        
	    }
	    	
	    @And("Click on My Account Menu")
	    public void click_on_my_account_menu() {
	        WebElement myAccount = driver.findElement(By.linkText("My Account"));
	        myAccount.click();
	    }

	    @When("Enter valid credentials {string} and password {string}")
	    public void enter_valid_credentials_and_password(String username, String password) {
	        driver.findElement(By.id("username")).clear();
	        driver.findElement(By.id("password")).clear();
	        driver.findElement(By.id("username")).sendKeys(username);
	        driver.findElement(By.id("password")).sendKeys(password);
	    }


	 
	    @When("Enter invalid credentials {string} and password {string}")
	    public void enter_invalid_credentials_and_password(String username, String password) {
	        driver.findElement(By.id("username")).clear();
	        driver.findElement(By.id("password")).clear();
	        driver.findElement(By.id("username")).sendKeys(username);
	        driver.findElement(By.id("password")).sendKeys(password);
	        driver.findElement(By.name("login")).click();
	    }
	    
	    @And("Enter the following credentials:")
	    public void enter_the_following_credentials(DataTable table) throws InterruptedException {
	        List<List<String>> credentials = table.asLists(String.class);

	        for (int i = 1; i < credentials.size(); i++) {
	            String username = credentials.get(i).get(0);
	            String password = credentials.get(i).get(1);

	            driver.findElement(By.id("username")).clear();
	            driver.findElement(By.id("password")).clear();
	            driver.findElement(By.id("username")).sendKeys(username);
	            driver.findElement(By.id("password")).sendKeys(password);
	            driver.findElement(By.name("login")).click();

	            Thread.sleep(7000); // Replace with WebDriverWait for better practice

	            if (driver.getPageSource().contains("Logout")) {
	                System.out.println("Login successful for: " + username);
	                //driver.findElement(By.linkText("Logout")).click();
	            } else {
	                System.out.println("Login failed for: " + username);
	            }
	            driver.findElement(By.linkText("Logout")).click();
	            // Go back to login page for the next iteration
	            driver.navigate().to("http://practice.automationtesting.in/my-account/");
	        }
	    }

	    
	   /*@And("Enter the following credentials:")
	    public void enter_the_following_credentials(DataTable dataTable) throws InterruptedException {
	        List<List<String>> credentials = dataTable.asLists(String.class);
	        
	        // Skip header row (index 0) and iterate from second row
	        for (int i = 1; i < credentials.size(); i++) {
	            String username = credentials.get(i).get(0);
	            String password = credentials.get(i).get(1);

	            driver.findElement(By.id("username")).clear();
	            driver.findElement(By.id("password")).clear();
	            driver.findElement(By.id("username")).sendKeys(username);
	            driver.findElement(By.id("password")).sendKeys(password);
	            driver.findElement(By.name("login")).click();
	            
	            Thread.sleep(5000);
	        }  
	    }*/

	    @And("Click on login button")
	    public void click_on_login_button() throws InterruptedException {
	        driver.findElement(By.name("login")).click();
	        Thread.sleep(7000);
	    }
	   /* @Then ("I click on logout")
	    public void I_click_on_logout () {
	    
	    driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
	    	
	    }*/

	    @Then("User must successfully login to the web page")
	    public void user_must_successfully_login_to_the_web_page() {
	    String capText= driver.findElement(By.xpath("//*[@id='page-36']/div/div[1]/div/p[1]/strong")).getText();
	  	  Assert.assertTrue(capText.contains("pavanoltraining"));
	  	  //Assert.assertEquals(true,capText.contains("pavanoltraining"));
	  	  System.out.println("capture text is :"+capText);
	    
	    }
	    
	    @After
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit(); // cleaner than close()
	        }
	    }

	    @Then("User must not login succesfully")
	    public void user_must_not_login_succesfully() {
	    	
	    	
	    	String txt=driver.findElement(By.xpath("//*[@id='page-36']/div/div[1]/ul/li/strong")).getText();
	    	Assert.assertTrue(txt.contains("Error"));
	        
	    }
	    
	    
	      
	}   
	    
	    
	    
	    
	    
	 
