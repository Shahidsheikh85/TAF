package executionEngine;

import java.io.File;
import java.io.FileInputStream;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtilis;
import utility.Log;
//import utility.SendMail;


public class ExecuteTestScript {
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];

	public static int iTestStep;
	public static File targetFile;
	public static int iTestLastStep;
	public static String sTestScenario;
	public static String sTestScenaroID;
	public static String sTestCaseID;
	public static String sDescription;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static int count;
	public static String img;


	public ExecuteTestScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}

	public static void main (String[] args) throws Exception {
		ExcelUtilis.duplicateExcelFile(Constants.Path_TestDataCopy); 
		ExcelUtilis.setExcelFile(Constants.Path_TestData);
		DOMConfigurator.configure("log4j.xml");

		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		DateFormat format = new SimpleDateFormat("ddMMYY_HHmm");
		extent  = new ExtentReports(Constants.Path_Report+ "RIVM_Rapport_" + format.format(new Date()) + ".html", false, DisplayOrder.OLDEST_FIRST);
		ExecuteTestScript startEngine = new ExecuteTestScript();
		startEngine.execute_TestCase();
	}

	private void execute_TestCase() throws Exception {

		int iTotalTestCases = ExcelUtilis.getRowCount(Constants.Sheet_TestCases);
		for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
			bResult = true;
			sTestCaseID = ExcelUtilis.getCellData(iTestcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases); 
			sTestScenario = ExcelUtilis.getCellData(iTestcase, Constants.Col_TestScenarioID, Constants.Sheet_TestCases); 
			sRunMode = ExcelUtilis.getCellData(iTestcase, Constants.Col_RunMode,Constants.Sheet_TestCases);
			if (sRunMode.equals("Yes")){
				Log.startTestCase(sTestCaseID);
				test = extent.startTest(sTestScenario, sTestScenario);
				System.out.println("Executing " + sTestCaseID);
				iTestStep = ExcelUtilis.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
				iTestLastStep = ExcelUtilis.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
				test.log(LogStatus.INFO, "Begin Test");
				bResult=true;
				for (;iTestStep<iTestLastStep;iTestStep++){
					sActionKeyword = ExcelUtilis.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
					sPageObject = ExcelUtilis.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
					sData = ExcelUtilis.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
					sDescription = ExcelUtilis.getCellData(iTestStep, Constants.Col_Description, Constants.Sheet_TestSteps);
					execute_Actions();
					System.out.println("Executing row " + iTestStep);
					if(bResult==false){
						ExcelUtilis.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
						Log.endTestCase(sTestCaseID);
						extent.endTest(test);
						extent.flush();
						System.out.println("Teststep " + sDescription + " executing went wrong");
						break;
					}						
				}
				if(bResult==true){
					ExcelUtilis.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.Col_Result,Constants.Sheet_TestCases);
					test.log(LogStatus.PASS, "Testscenario '" + sTestScenario + " 'succesfully executed");
					Log.endTestCase(sTestCaseID);	
					extent.endTest(test);
					System.out.println("Teststep " + sTestCaseID + " successfully executed");
					extent.flush();

				}					
			}
		}
		//SendMail.execute(Constants.Path_Report);
	}	

	private static void execute_Actions() throws Exception {

		for(int i=0;i<method.length;i++){

			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==true){
					if(sDescription.equals("")){
						ExcelUtilis.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);	
						//String img = test.addScreenCapture(Constants.Path_ScreenShot + iTestStep + ".png" );
						//test.log(LogStatus.INFO, "Screenshot below : " + test.addScreenCapture(Constants.Path_ScreenShot + iTestStep +".jpeg"));  

						break;		
					}else{
						ExcelUtilis.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);	
						test.log(LogStatus.PASS, sDescription + " " + sData);
						if(sData.equals("screenShot")){
							test.log(LogStatus.INFO, "Schermafbeelding : " + test.addScreenCapture(ActionKeywords.takeScreenshot().getAbsolutePath()));
						}
						break;
					}
				}else{

					if(sTestCaseID.equals("Controleren_Files")){
						ExcelUtilis.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
						//sTestScenario = ExcelUtilis.getCellData(iTestStep, Constants.Col_TestScenarioID, Constants.Sheet_TestSteps);
						test.log(LogStatus.FAIL, "FAIL" , sDescription);
						test.log(LogStatus.INFO, "Schermafbeelding : " + test.addScreenCapture(ActionKeywords.takeScreenshot().getAbsolutePath()));
						continue;
					}else{
						ExcelUtilis.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
						test.log(LogStatus.FAIL, "FAIL" , sDescription);
						test.log(LogStatus.INFO, "Schermafbeelding : " + test.addScreenCapture(ActionKeywords.takeScreenshot().getAbsolutePath()));
						ActionKeywords.closeBrowser("","");
						break;	
					}	
				}
			}
		}
	}
}