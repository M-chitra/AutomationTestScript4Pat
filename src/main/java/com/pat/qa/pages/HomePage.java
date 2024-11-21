package com.pat.qa.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.pat.qa.base.TestBase;

public class HomePage extends TestBase {

	@FindBy(xpath = "//button[@class='margin-right']")
	WebElement searchOption;

	@FindBy(xpath = "//button[text()='I have read and agree to the terms']")
	WebElement agreeButton;

	@FindBy(xpath = "//div[@class='fixedContainer flex']")
	WebElement getAllTableValues;

	@FindBy(tagName = "tr")
	WebElement tableRow;

	@FindBy(tagName = "td")
	WebElement tableColumn;

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String validateHomePageTittle() {
		return driver.getTitle();
	}

	public void clickSearchOption() {
		searchOption.click();
	}

	public void acceptDisclimer() {
		agreeButton.click();
	}

	public void differentiateDates() throws InterruptedException {

		WebElement table = driver.findElement(By.xpath("//div[@class='fixedContainer flex']"));

		List<WebElement> rows = table.findElements(By.tagName("tr"));

		System.out.println(rows.size());

		for (int i = 1; i < rows.size(); i++) {
			WebElement row = rows.get(i);
			List<WebElement> columns = row.findElements(By.tagName("td"));

			String participant = columns.get(0).getText();
			String inn = columns.get(1).getText();
			String patents = columns.get(2).getText();

			Assert.assertNotNull(participant, "Participant is null in row " + i);
			Assert.assertNotNull(inn, "INN is null in row " + i);
			Assert.assertNotNull(patents, "Patents are null in row " + i);

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
				WebElement card = driver
						.findElement(By.xpath("//ul[@class='results flex space-between']//li[1]//table"));

				List<WebElement> cardrows = card.findElements(By.tagName("tr"));

				System.out.println(cardrows.size());

				try {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					WebElement publicationDate = driver.findElement(By.xpath(
							"//ul[@class='results flex space-between']//li[1]//table//td//b[text()='Publication date']//following::td[1]"));

					String publicationDateText = publicationDate.getText();
					System.out.println("Publication date:" + publicationDateText);
					Date PD = sdf.parse(publicationDateText);

					WebElement filingDate = driver.findElement(By.xpath(
							"//ul[@class='results flex space-between']//li[1]//table//td//b[text()='Filing date']//following::td[1]"));
					String filingDateText = filingDate.getText();
					System.out.println("Filing date:" + filingDateText);

					Date FD = sdf.parse(filingDateText);

					if (PD.before(FD)) {
						System.out.println("The Publication date is before Filing date.");
					} else if (PD.after(FD)) {
						System.out.println("The Publication date is after Filing date.");
					} else {
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
