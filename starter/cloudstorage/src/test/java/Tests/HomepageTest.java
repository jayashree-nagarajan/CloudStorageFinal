package Tests;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class HomepageTest {
    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    private WebElement addNoteButton;
    private WebElement noteTitle;
    private WebElement noteDescription;

    @FindBy(id = "note-submit")
    private WebElement submitNoteButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;
    private WebElement addCredentialButton;
    private WebElement credentialURL;
    private WebElement credentialUsername;
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement submitCredentialButton;

    private WebDriverWait wait;
    private WebDriver webDriver;


    public HomepageTest(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
        PageFactory.initElements(webDriver, this);
    }



    public void ifNoteDeleted() throws InterruptedException {
        Thread.sleep(1000);
        this.noteTab.click();

        List rows = webDriver.findElements(By.xpath("//*[@id='userTable']/tbody/tr"));
        Assertions.assertEquals(0, rows.size());
    }

    public void ifNoteEdited() throws InterruptedException {
        Thread.sleep(1000);
        this.noteTab.click();
        //Thread.sleep(1000);
        this.wait.until(ExpectedConditions.visibilityOf
                (this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
        noteTitle = this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"));
        noteDescription = this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]"));

        Assertions.assertEquals("Edited", noteTitle.getText());
        Assertions.assertEquals("This is edited test note", noteDescription.getText());
    }

    public void ifNoteVisible() throws InterruptedException {
        //Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement notesTab = webDriver.findElement(By.id("nav-notes-tab"));
        notesTab.click();


        this.wait.until(ExpectedConditions.visibilityOf
                (this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
        noteTitle = this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"));
        noteDescription = this.webDriver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]"));

        Assertions.assertEquals("Test Note", noteTitle.getText());
        Assertions.assertEquals("Thiz is a test note", noteDescription.getText());

    }



    public void ifCredentialVisible() throws InterruptedException {
        //Thread.sleep(1000);
        //this.credentialTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
        WebElement credentialTab=webDriver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        wait.until(ExpectedConditions.visibilityOf
                (webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
        credentialURL = webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"));
        credentialUsername =webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]"));
        credentialPassword =webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));

        Assertions.assertEquals("User Name", credentialUsername.getText());
        Assertions.assertEquals("www.aaa.aa", credentialURL.getText());
        Assertions.assertNotEquals("password", credentialPassword.getText());


    }


    public void ifCredentialEdited() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
        WebElement credentialTab=webDriver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();

        this.wait.until(ExpectedConditions.visibilityOf
                (this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
        credentialURL = this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"));
        credentialUsername = this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]"));
        credentialPassword = this.webDriver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[3]"));

        Assertions.assertEquals("Edited Name", credentialUsername.getText());
        Assertions.assertEquals("www.bbb.bb", credentialURL.getText());
    }



    public void ifCredentialDeleted() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-credentials-tab")));
        WebElement credentialTab=webDriver.findElement(By.id("nav-credentials-tab"));
        credentialTab.click();


        List rows = webDriver.findElements(By.xpath("//*[@id='credentialTable']/tbody/tr"));
        Assertions.assertEquals(0, rows.size());

    }
}
