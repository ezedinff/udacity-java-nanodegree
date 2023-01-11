package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private static String fname;
	private static String lname;
	private static String username;
	private static String password;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		fname = "yasmine";
		lname = "moh";
		username = "user1";
		password = "123";
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		this.driver = new ChromeDriver();

		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.setFirstNameField(fname);
		signupPage.setLastNameField(lname);
		signupPage.setUsername(username);
		signupPage.setPasswordField(password);
		signupPage.submit();
		Thread.sleep(1000);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void homePageUnaccessibleTest() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signupLoginLogoutTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");

		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();

		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		homePage.logout();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());


	}

//  CREDENTIAL TESTS

	@Test
	public void addCredTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectCredentialsWindow();
		Credential crd = new Credential(null, "facebook.com", "user1",null,"1234");
		homePage.addCred(crd.getUrl(), crd.getUsername(), crd.getPassword());
		homePage.selectCredentialsWindow();
		Thread.sleep(500);
		ArrayList<Credential> credentials = homePage.getCreds();
		boolean res = homePage.credPresent(crd, credentials);
		Assertions.assertTrue(res);
	}

	@Test
	public void editCredTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectCredentialsWindow();
		Thread.sleep(500);
		Credential oldCred = new Credential(null, "jasminrkrm", "jkrm", null, "abc");
		Credential newCred = new Credential(null, "jasminrkrm", "jasmine", null, "def");
		homePage.addCred(oldCred.getUrl(), oldCred.getUsername(), oldCred.getPassword());
		homePage.logout();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(500);
		homePage.selectCredentialsWindow();
		ArrayList<Credential> credentials = homePage.getCreds();
		Integer id = homePage.getCredId(oldCred.getUrl(), oldCred.getUsername(), oldCred.getPassword(), credentials);
		Thread.sleep(500);
		homePage.updateCred(id,newCred.getUrl(), newCred.getUsername(), newCred.getPassword());
		homePage.selectCredentialsWindow();
		Thread.sleep(500);
		credentials = homePage.getCreds();
		System.out.println(credentials);
		boolean resF = homePage.credPresent(oldCred, credentials);
		boolean resT = homePage.credPresent(newCred, credentials);
		Assertions.assertTrue(resT);
		Assertions.assertTrue(!resF);

	}

	@Test
	public void deleteCredTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectCredentialsWindow();
		Thread.sleep(500);
		Credential oldCred = new Credential(null, "messenger.uk", "moh", null, "876");
		homePage.addCred(oldCred.getUrl(), oldCred.getUsername(), oldCred.getPassword());
		homePage.logout();
		Thread.sleep(500);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(500);
		homePage.selectCredentialsWindow();
		ArrayList<Credential> credentials = homePage.getCreds();
		Integer id = homePage.getCredId(oldCred.getUrl(), oldCred.getUsername(), oldCred.getPassword(), credentials);
		homePage.deleteCred(id);
		homePage.selectCredentialsWindow();
		Thread.sleep(500);
		ArrayList<Credential> credsUpdated = homePage.getCreds();
		boolean res = homePage.credPresent(oldCred, credsUpdated);
		Assertions.assertTrue(!res);
		Assertions.assertTrue(credentials.size()-1 == credsUpdated.size());
	}

	//  NOTE TESTS

	@Test
	public void addNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectNotesWindow();
		Note note = new Note(null, "hi", "hey");
		homePage.addNote(note.getNotetitle(), note.getNotedescription());
		homePage.selectNotesWindow();
		Thread.sleep(500);
		ArrayList<Note> notes = homePage.getNotes();
		System.out.println(notes);
		boolean res = homePage.notePresent(note, notes);
		Assertions.assertTrue(res);
	}

	@Test
	public void editNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectNotesWindow();
		Thread.sleep(500);
		Note oldNote = new Note(null, "groceries", "eggs");
		Note newNote = new Note(null, "cook", "cake");
		homePage.addNote(oldNote.getNotetitle(), oldNote.getNotedescription());
		homePage.logout();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(500);
		homePage.selectNotesWindow();
		ArrayList<Note> notes = homePage.getNotes();
		Integer id = homePage.getNoteId(oldNote.getNotetitle(), oldNote.getNotedescription(), notes);
		Thread.sleep(500);
		homePage.updateNote(id,newNote.getNotetitle(),newNote.getNotedescription());
		homePage.selectNotesWindow();
		Thread.sleep(500);
		notes = homePage.getNotes();
		System.out.println(notes);
		boolean resF = homePage.notePresent(oldNote, notes);
		boolean resT = homePage.notePresent(newNote, notes);
		Assertions.assertTrue(resT);
		Assertions.assertTrue(!resF);

	}

	@Test
	public void deleteNoteTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.selectNotesWindow();
		Thread.sleep(500);
		Note note = new Note(null, "food", "chicken");
		homePage.addNote(note.getNotetitle(), note.getNotedescription());
		homePage.logout();
		Thread.sleep(500);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.setUsername(username);
		loginPage.setPasswordField(password);
		loginPage.submit();
		Thread.sleep(500);
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		Thread.sleep(500);
		homePage.selectNotesWindow();
		ArrayList<Note> notes = homePage.getNotes();
		Integer id = homePage.getNoteId(note.getNotetitle(), note.getNotedescription(), notes);
		homePage.deleteNote(id);
		homePage.selectNotesWindow();
		Thread.sleep(500);
		ArrayList<Note> notesUpdated = homePage.getNotes();
		boolean res = homePage.notePresent(note, notesUpdated);
		Assertions.assertTrue(!res);
		Assertions.assertTrue(notes.size()-1 == notesUpdated.size());
	}

}
