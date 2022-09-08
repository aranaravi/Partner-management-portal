package io.mosip.test.pmptest.testcase;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
// Generated by Selenium IDE
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.mosip.test.pmptest.utility.BaseClass;
import io.mosip.test.pmptest.utility.Commons;
import io.mosip.test.pmptest.utility.JsonUtil;
import io.mosip.test.pmptest.utility.RegisterBaseClass;
import io.mosip.test.pmptest.utility.RealTimeReport;
import org.testng.annotations.Listeners;

@Listeners(RealTimeReport.class)
public class PartnerRegisterFTMTest extends RegisterBaseClass {
	private static final org.slf4j.Logger logger= org.slf4j.LoggerFactory.getLogger(PartnerRegisterFTMTest.class);
	@Test(groups = {"RFTM"},dataProvider = "data-provider-FTM" , dependsOnGroups = {"UFCC"})
	public void partnerRegisterFTMTest(String cer) throws InterruptedException, AWTException{
		String datetime=Commons.getDateTime();
		String dropdwnVal=cer.substring(0, cer.indexOf("_", 0));
		String orgName=cer.substring(0, cer.length()-4);
		
		Commons.click(driver, By.xpath("//a[contains(text(),'Register')]"));
		Commons.enter(driver, By.id("firstName"), datetime);
		Commons.enter(driver, By.id("lastName"), datetime);
		try {
			Commons.enter(driver, By.id("organizationName"), orgName);
			Select select = new Select(driver.findElement(By.id("user.attributes.partnerType")));
		
		if(dropdwnVal.contains("DEVICE") ||  dropdwnVal.contains("FTM") )
		{	select.selectByValue(dropdwnVal+"_PROVIDER");
		}
		else {
			select.selectByValue(dropdwnVal+"_PARTNER");	
		}
		
		
		Commons.enter(driver, By.id("address"), data);
		Commons.enter(driver, By.id("email"), datetime+"@automationlabs.com");
		Commons.enter(driver, By.id("phoneNumber"), "9178338765");
		Commons.selOption(driver, By.id("user.attributes.langCode"), "English");
		
		
		Commons.enter(driver, By.id("username"),orgName+data);
		Commons.enter(driver, By.id("password"), orgName+data);
		Commons.enter(driver, By.id("password-confirm"),orgName+data);
		
		Commons.click(driver, By.xpath("//input[@type='submit']"));
		
		if(!(dropdwnVal.contains("DEVICE") ||  dropdwnVal.contains("FTM") ))
			{Commons.dropdown(driver, By.id("mat-select-0"),data);
		Commons.click(driver, By.id("applyTxt"));
		Commons.click(driver, By.id("/pmp/resources/policymapping/view"));	
		Commons.click(driver, By.id("/pmp/home"));
		}
		
		Commons.click(driver, By.id("uploadCertificate"));
		
		if(dropdwnVal.contentEquals("CREDENTIAL")) Commons.uploadPartnerCert(driver,By.id("partnerDomain"),"AUTH","\\auth_cert\\",cer);
		else Commons.uploadPartnerCert(driver,By.id("partnerDomain"),dropdwnVal,"\\ftm_cert\\",cer);

			
		Commons.click(driver, By.id("viewCertificate"));
		
		
		String certificate=Commons.getText(driver,By.xpath("//p"));
		logger.info(certificate);
		Commons.click(driver, By.id("confirmmessagepopup"));	
		
		
		switch(dropdwnVal) {
		case "DEVICE":
					Commons.click(driver, By.id("/pmp/resources/devicedetails/view"));
					

					Commons.click(driver, By.id("Create Device"));
					Commons.dropdown(driver, By.id("deviceProviderId"),orgName);
					Commons.dropdown(driver, By.id("deviceTypeCode"),By.id("Face"));
					
					Commons.dropdown(driver, By.id("deviceSubTypeCode"),By.id("Full face"));
					Commons.enter(driver, By.xpath("//input[@id='make']"), data);
					Commons.enter(driver, By.xpath("//input[@id='model']"), data);
					Commons.click(driver, By.xpath("//button[@id='createButton']"));

					Commons.click(driver, By.id("confirmmessagepopup"));
					
					Commons.filter(driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
					Commons.click(driver, By.id("ellipsis-button0"));
					Commons.click(driver, By.id("Edit0"));
					Commons.enter(driver, By.xpath("//input[@id='model']"), data + 1);
					Commons.click(driver, By.xpath("//button[@id='createButton']"));

					Commons.click(driver, By.id("confirmmessagepopup"));
				
// SBI View
					
					Commons.click(driver, By.id("/pmp/resources/sbidetails/view"));
					Commons.click(driver, By.xpath("//button[@id='Create SBI']"));
					
					Commons.dropdown(driver, By.id("providerId"),orgName);
					
					Commons.enter(driver, By.xpath("//input[@id='swVersion']"), data);
					Commons.enter(driver, By.xpath("//input[@id='swBinaryHash']"), data);
					Commons.enter(driver, By.xpath("//input[@id='swCreateDateTime']"), JsonUtil.JsonObjParsing(Commons.getTestData(),"sbivalidDate"));
					Commons.enter(driver, By.xpath("//input[@id='swExpiryDateTime']"), JsonUtil.JsonObjParsing(Commons.getTestData(),"sbiexpiryDate"));
					Commons.click(driver, By.xpath("//button[@id='createButton']"));
					Commons.click(driver, By.xpath("//button[@id='confirmmessagepopup']"));
					
					Commons.filter(driver, By.id("swVersion"),data);
					Commons.click(driver, By.id("ellipsis-button0"));
					Commons.click(driver, By.id("Edit0"));
					Commons.enter(driver, By.xpath("//input[@id='swBinaryHash']"), data+1);

					Commons.click(driver, By.xpath("//button[@id='createButton']"));
					Commons.click(driver, By.xpath("//button[@id='confirmmessagepopup']"));
				
		break;	
		case "FTM":
			Commons.click(driver, By.id("/pmp/resources/ftmdetails/view"));
			

			Commons.click(driver, By.id("Create Device"));
			Commons.dropdown(driver, By.id("ftpProviderId"),orgName);
			Commons.enter(driver, By.xpath("//input[@id='make']"), data);
			Commons.enter(driver, By.xpath("//input[@id='model']"), data);
			Commons.click(driver, By.xpath("//button[@id='createButton']"));

			Commons.click(driver, By.id("confirmmessagepopup"));

		
			Commons.filter(driver, By.id("make"),By.id("partnerOrganizationName"), data,orgName);
			Commons.click(driver, By.id("ellipsis-button0"));
			Commons.click(driver, By.id("Edit0"));
			Commons.enter(driver, By.xpath("//input[@id='model']"), data + 1);
			Commons.click(driver, By.xpath("//button[@id='createButton']"));
			Commons.click(driver, By.xpath("//button[@id='confirmmessagepopup']"));


break;	
		

		case "AUTH":
			
						
			Commons.click(driver, By.id("/pmp/resources/policymapping/view"));	
			Commons.click(driver, By.xpath("//button[@id='Map Policy']"));
			Commons.dropdown(driver, By.id("partnerId"),orgName);
			Commons.dropdown(driver, By.id("policyId"),"AUTH"+data);
			Commons.enter(driver, By.id("requestDetail"), data);
			Commons.click(driver, By.xpath("//button[@id='createButton']"));

			Commons.click(driver, By.xpath("//button[@id='confirmmessagepopup']"));

			
			
				Commons.filter(driver, By.id("requestDetail"),By.id("partnerName"), data,orgName);

break;	

		case "CREDENTIAL":
			
			
			Commons.click(driver, By.id("/pmp/resources/policymapping/view"));	
			Commons.click(driver, By.xpath("//button[@id='Map Policy']"));
			Commons.dropdown(driver, By.id("partnerId"),orgName);
			Commons.dropdown(driver, By.id("policyId"),"DS"+data);
			Commons.enter(driver, By.id("requestDetail"), data);
			Commons.click(driver, By.xpath("//button[@id='createButton']"));

			Commons.click(driver, By.xpath("//button[@id='confirmmessagepopup']"));


			Commons.filter(driver, By.id("requestDetail"),By.id("partnerName"), data,orgName);
			


break;	

		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
