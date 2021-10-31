package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_Textbox_Textarea {
    WebDriver driver;
    JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");
	String loginPageUrl,userID, userPassword;
	String customerName, gender, dateOfBirth, addressInput, addressOutput, city, state, pinNo, PhoneNo, email, password;
	String customerID;
	String editCustomerName, editGender, editDateOfBirth, editAddressInput, editAddressOutput, editCity, editState, editPinNo, editPhoneNo, editEmail, editPassword;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		//ép kiểu tường minh
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		driver.get("https://demo.guru99.com/v4/");
		
		customerName= "Thao Tran";
		gender= "male";
		dateOfBirth= "1999-08-26";
		addressInput= "25 Phan Chau Trinh \n Hai Chau Da Nang Vietnam";
		addressOutput= "25 Phan Chau Trinh Hai Chau Da Nang Vietnam";
		city = "Da Nang";
		state = "Da Nang" ;
		pinNo = "234567" ;
		PhoneNo = "0987654565"; 
		email = "thaotran" +getRandomNumber() + "@gmail.com";
		password = "123456";
		
		//Edit value
		/*
		 * editCustomerName= "Ty Nguyen"; editGender= "male"; editDateOfBirth=
		 * "1999-05-25";
		 */
		editAddressInput= "30 Trung Nu Vuong \n Hai Chau Da Nang Vietnam";
		editAddressOutput= "30 Trung Nu Vuong Hai Chau Da Nang Vietnam";
		editCity = "Da Nang";
		editState = "Da Nang" ;
		editPinNo = "234578" ;
		editPhoneNo = "0387654565"; 
		editEmail = "tynguyen" +getRandomNumber() + "@gmail.com";
	}

	@Test
	public void TC_01_Register() {
		loginPageUrl = driver.getCurrentUrl();
		
		driver.findElement(By.xpath("//a[text()='here']")).click();
		
		driver.findElement(By.name("emailid")).sendKeys("thaotran" +getRandomNumber() + "@gmail.com");
		
		driver.findElement(By.name("btnLogin")).click();
		
		userID = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		userPassword = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
			
	}

	@Test
	public void TC_02_Login() {
		driver.get(loginPageUrl);
		
		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		
		driver.findElement(By.name("btnLogin")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(), "Welcome To Manager's Page of Guru99 Bank");
		Assert.assertEquals(driver.findElement(By.cssSelector("tr.heading3>td")).getText(), "Manger Id : " +userID);
		
	}

	@Test
	public void TC_03_Add_New_Customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		
		driver.findElement(By.name("name")).sendKeys(customerName);
		
		WebElement dobTextbox = driver.findElement(By.name("dob"));
		jsExecutor.executeScript("arguments[0].removeAttribute('type')", dobTextbox);
		sleepInSecond(3);
		dobTextbox.sendKeys(dateOfBirth);
		
		driver.findElement(By.name("addr")).sendKeys(addressInput);
		driver.findElement(By.name("city")).sendKeys(city);
		driver.findElement(By.name("state")).sendKeys(state);
		driver.findElement(By.name("pinno")).sendKeys(pinNo);
		driver.findElement(By.name("telephoneno")).sendKeys(PhoneNo);
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("sub")).click();
		
		Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(), "Customer Registered Successfully!!!");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pinNo);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), PhoneNo);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);
		
		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		

		
	}

	@Test
	public void TC_04_Edit_Customer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();
		
		
		//verify dữ liệu cũ
		Assert.assertEquals(driver.findElement(By.name("name")).getAttribute("value"), customerName);
		Assert.assertEquals(driver.findElement(By.name("gender")).getAttribute("value"), gender);
		Assert.assertEquals(driver.findElement(By.name("dob")).getAttribute("value"), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.name("addr")).getAttribute("value"), addressInput);
		Assert.assertEquals(driver.findElement(By.name("city")).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(By.name("state")).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(By.name("pinno")).getAttribute("value"), pinNo);
		Assert.assertEquals(driver.findElement(By.name("telephoneno")).getAttribute("value"), PhoneNo);
		Assert.assertEquals(driver.findElement(By.name("emailid")).getAttribute("value"), email);
		
		//edit dữ liệu
		/*
		 * driver.findElement(By.name("name")).clear();
		 * driver.findElement(By.name("name")).sendKeys(editCustomerName);
		 * 
		 * WebElement dobTextbox = driver.findElement(By.name("dob"));
		 * jsExecutor.executeScript("arguments[4].removeAttribute('disable')",
		 * dobTextbox); sleepInSecond(3); driver.findElement(By.name("dob")).clear();
		 * dobTextbox.sendKeys(editDateOfBirth);
		 */
		
		driver.findElement(By.name("addr")).clear();
		driver.findElement(By.name("addr")).sendKeys(editAddressInput);
		
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys(editCity);
		
		driver.findElement(By.name("state")).clear();
		driver.findElement(By.name("state")).sendKeys(editState);
		
		driver.findElement(By.name("pinno")).clear();
		driver.findElement(By.name("pinno")).sendKeys(editPinNo);
		
		driver.findElement(By.name("telephoneno")).clear();
		driver.findElement(By.name("telephoneno")).sendKeys(editPhoneNo);
		
		driver.findElement(By.name("emailid")).clear();
		driver.findElement(By.name("emailid")).sendKeys(editEmail);
		
		driver.findElement(By.name("sub")).click();
		
		//verify data after editting
		Assert.assertEquals(driver.findElement(By.cssSelector("p.heading3")).getText(), "Customer details updated Successfully!!!");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), customerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), gender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editAddressOutput);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editPinNo);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editPhoneNo);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editEmail);
		
	}

	@Test
	public void TC_05() {
	
	}

	@Test
	public void TC_06() {

	}
	
	public int getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9999);
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
