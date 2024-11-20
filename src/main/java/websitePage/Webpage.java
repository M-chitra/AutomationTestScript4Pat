package websitePage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class Webpage {

	
	public static void main(String[] args) throws InterruptedException {

		
		System.setProperty("webdriver.chrome.driver","C:/Users/chitra/Downloads/chromedriver-win32/chromedriver-win32/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://patinformed.wipo.int/");
		driver.manage().window().maximize();
		System.out.println(driver.getTitle());
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@class='margin-right']")).click();
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='I have read and agree to the terms']")).click();
		 
		WebElement table = driver.findElement(By.xpath("//div[@class='fixedContainer flex']"));
		
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		
		System.out.println(rows.size());
		
		for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> columns = row.findElements(By.tagName("td"));
            
            
            String participant = columns.get(0).getText();
            String inn = columns.get(1).getText();   
            String patents = columns.get(2).getText();
            
            System.out.println("Row " + i + ": PARTICIPANT=" + participant + ", INN=" + inn + ", PATENTS=" + patents);
            
            if (i == 1) {
            
            WebElement patentsColumn = columns.get(2);
           
    		List<WebElement> patentItems = patentsColumn.findElements(By.tagName("li"));
    		
    		if (!patentItems.isEmpty()) {
    		   
    		    WebElement firstPatentItem = patentItems.get(0);
    		    
    		   
    		    String firstPatentText = firstPatentItem.getText();
    		    
    	
    		    System.out.println("First Patent Text (from first row): " + firstPatentText);
    		    
    		    
                if (patentItems.contains(firstPatentItem)) {
                    System.out.println("Clicking on first patent: " + firstPatentText);
                    firstPatentItem.click(); // Click the first patent item
                } else {
                    System.out.println("First patent item is not present in the list.");
                }
            } else {
                System.out.println("No patents found in the first row.");
            }
    		
    		Thread.sleep(2000);
    		WebElement card = driver.findElement(By.xpath("//ul[@class='results flex space-between']//li[1]//table"));
    		
    		List<WebElement> cardrows = card.findElements(By.tagName("tr"));
            
            System.out.println(cardrows.size());
            
			/*
			 * for (int j = 0; j < cardrows.size(); j++) { WebElement cardrow =
			 * cardrows.get(j); List<WebElement> cardcolumns =
			 * cardrow.findElements(By.tagName("td"));
			 * 
			 * String Test1 = cardcolumns.get(0).getText(); String Test2 =
			 * cardcolumns.get(1).getText();
			 * 
			 * System.out.println( Test1 + ","+ Test2); }
			 */
            
            try {
            
            	
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            WebElement publicationDate = driver.findElement(By.xpath("//ul[@class='results flex space-between']//li[1]//table//td//b[text()='Publication date']//following::td[1]"));
           
            String publicationDateText = publicationDate.getText();
            System.out.println("Publication date:" + publicationDateText);
            Date PD = sdf.parse(publicationDateText);
            
            WebElement filingDate = driver.findElement(By.xpath("//ul[@class='results flex space-between']//li[1]//table//td//b[text()='Filing date']//following::td[1]"));
            String filingDateText = filingDate.getText();      
            System.out.println("Filing date:" + filingDateText);
            
            
            Date FD = sdf.parse(filingDateText);
            
            
            if (PD.before(FD)) {
                System.out.println("The Publication date is before Filing date.");
            } else if(PD.after(FD)) {
                System.out.println("The Publication date is after Filing date.");
            }else {
                System.out.println("Both dates are the same");
            }
           
            if (publicationDateText.isEmpty()) {
                System.out.println("Publication date is missing.");
            }
            if (filingDateText.isEmpty()) {
                System.out.println("Filing date is missing.");
            }
            
            } catch (Exception e) {
                System.out.println("Element not found: " + e.getMessage());
            }
    		break;
            }
       
            
		}	
		
	}

}
