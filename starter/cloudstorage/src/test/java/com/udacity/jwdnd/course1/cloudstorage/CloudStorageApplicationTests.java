package com.udacity.jwdnd.course1.cloudstorage;

import Tests.HomepageTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}
	@FindBy(id = "nav-notes-tab")
	private WebElement noteTab;
	private WebElement addNoteButton;
	private WebElement noteTitle;
	private WebElement noteDescription;

	@FindBy(id = "note-submit")
	private WebElement submitNoteButton;

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You have successfully signed up! Please login"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}



	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("multipartFile")));
		WebElement fileSelectButton = driver.findElement(By.id("multipartFile"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testLoginFlow(){


		doMockSignUp("LoginFlow","Test","LF","123");
		doLogIn("LF", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		driver.get("http://localhost:" + this.port + "/home");

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testNoteCreation() throws InterruptedException {
		doMockSignUp("Notes","Test","NTE","123");
		doLogIn("NTE", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);



		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createNote("Test Note", "Thiz is a test note");
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		homepageTest.ifNoteVisible();

		deleteNote();
	}

	@Test
	public void testNoteEdit() throws InterruptedException {
		doMockSignUp("NotesEdit","Test","NE","123");
		doLogIn("NE", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createNote("Test Note", "Thiz is a test note");
		//Assertions.assertEquals("Result", driver.getTitle());
		//Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		editNote("Edited", "This is edited test note");
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		homepageTest.ifNoteEdited();

		deleteNote();
	}

	@Test
	public void testNoteDelete() throws InterruptedException{
		doMockSignUp("NotesDelete","Test","NT","123");
		doLogIn("NT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createNote("Test Note", "Thiz is a test note");
		//Assertions.assertEquals("Result", driver.getTitle());
		//Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		deleteNote();
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		homepageTest.ifNoteDeleted();
	}

	@Test
	public void testCredentialCreation() throws InterruptedException {
		doMockSignUp("Credential","Test","CT","123");
		doLogIn("CT", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);



		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createCredential("www.aaa.aa","User Name", "password");
		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		homepageTest.ifCredentialVisible();

		deleteCredential();
	}

	@Test
	public void testCredentialDelete() throws InterruptedException {
		doMockSignUp("Credential","Delete","CD","123");
		doLogIn("CD", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);



		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createCredential("www.aaa.aa","User Name", "password");
		//Assertions.assertEquals("Result", driver.getTitle());
		//Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		deleteCredential();

		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");


		homepageTest.ifCredentialDeleted();


	}

	@Test
	public void testCredentialEdit() throws InterruptedException {
		doMockSignUp("Credential","Edit","CE","123");
		doLogIn("CE", "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);



		HomepageTest homepageTest = new HomepageTest(driver,webDriverWait );
		createCredential("www.aaa.aa","User Name", "password");
		//Assertions.assertEquals("Result", driver.getTitle());
		//Assertions.assertEquals("Success", driver.findElement(By.id("success")).getText());

		driver.get("http://localhost:" + this.port + "/home");

		editCredential("www.bbb.bb","Edited Name","pass");

		driver.get("http://localhost:" + this.port + "/home");

		homepageTest.ifCredentialEdited();

		deleteCredential();
	}

	public void createNote(String noteTitle, String noteDescription) throws InterruptedException {
		//Thread.sleep(1000);


		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		noteTab=driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddNewNote")));
		addNoteButton = driver.findElement(By.id("btnAddNewNote"));
		addNoteButton.click();

		WebElement notesTitle = driver.findElement(By.id("note-title"));
		webDriverWait.until(ExpectedConditions.visibilityOf(notesTitle));
		notesTitle.clear();
		notesTitle.sendKeys(noteTitle);



		WebElement notesDescription = driver.findElement(By.id("note-description"));
		notesDescription.clear();
		notesDescription.sendKeys(noteDescription);


		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-submit")));
		submitNoteButton=driver.findElement(By.id("note-submit"));
		submitNoteButton.click();
	}

	public void editNote(String noteTitle, String noteDescription) throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		noteTab=driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		WebElement editButton = driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[1]/button"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		editButton.click();

		WebElement notesTitle = driver.findElement(By.id("note-title"));
		webDriverWait.until(ExpectedConditions.visibilityOf(notesTitle));
		notesTitle.clear();
		notesTitle.sendKeys(noteTitle);

		WebElement notesDescription = driver.findElement(By.id("note-description"));
		notesDescription.clear();
		notesDescription.sendKeys(noteDescription);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("note-submit")));
		submitNoteButton=driver.findElement(By.id("note-submit"));
		submitNoteButton.click();
	}

	public void deleteNote() throws InterruptedException {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-notes-tab")));
		noteTab=driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();
		WebElement deleteButton = this.driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[1]/a"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		deleteButton.click();
	}

	public void createCredential(String url, String username, String password) throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		WebElement credentialTab=driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("btnAddNewCredential")));
		WebElement addCredentialButton = driver.findElement(By.id("btnAddNewCredential"));
		addCredentialButton.click();

		WebElement credentialURL = driver.findElement(By.id("credential-url"));
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
		credentialURL.sendKeys(url);

		WebElement credentialUsername = driver.findElement(By.id("credential-userName"));
		credentialUsername.sendKeys(username);

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-Submit")));
		WebElement submitCredentialButton = driver.findElement(By.id("credential-Submit"));
		submitCredentialButton.click();


	}

	public void deleteCredential() throws InterruptedException {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		WebElement credentialTab=driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();

		WebElement deleteButton = this.driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/a"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
		deleteButton.click();

	}

	public void editCredential(String url, String username, String password) throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
		WebElement credentialTab=driver.findElement(By.id("nav-credentials-tab"));
		credentialTab.click();

		WebElement editButton = driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/button"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editButton));
		editButton.click();

		WebElement credentialURL = driver.findElement(By.id("credential-url"));
		webDriverWait.until(ExpectedConditions.visibilityOf(credentialURL));
		credentialURL.clear();
		credentialURL.sendKeys(url);

		WebElement credentialUsername = driver.findElement(By.id("credential-userName"));
		credentialUsername.clear();
		credentialUsername.sendKeys(username);

		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		Assertions.assertEquals("password", credentialPassword.getAttribute("value"));
		credentialPassword.clear();
		credentialPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("credential-Submit")));
		WebElement submitCredentialButton = driver.findElement(By.id("credential-Submit"));
		submitCredentialButton.click();


	}


	}
