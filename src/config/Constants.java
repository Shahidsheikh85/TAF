package config;

public class Constants {
	
	public static final String URL =  "https://www.chemischestoffengoedgeregeld.nl/"; //http://reach.rivm.localhost/";
	public static final String Admin = "http://webcmsl02-ext-t.rivm.nl/REACH/user";
	public static final String Path_TestData = ".//src//dataEngine//DataEngine_REACH.xlsx";
	public static final String Path_OR = ".//src//config//OR.txt";
	public static final String Path_TestDataCopy = ".//src//dataEngine//";
	public static final String File_TestData = "DataEngine_REACH.xlsx";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	public static final String Path_ScreenShot = ".//src//Screenshots//";
	public static final String Path_Report = ".//src//Reports//";
	public static final String Path_ScreenShots = "./src/Screenshots/";
	
	//Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_PageObject =4 ;
	public static final int Col_ActionKeyword =5 ;
	public static final int Col_RunMode =2 ;
	public static final int Col_Result =3 ;
	public static final int Col_DataSet =6 ;
	public static final int Col_TestStepResult =7 ;
	public static final int Col_Description =2 ;

	
	
		
	// Data Engine Excel sheets
	public static final String Sheet_TestSteps = "Test Steps";
	public static final String Sheet_TestCases = "Test Cases";
	
	// Test Data
	public static final String UserName = "bigman1";
	public static final String Password = "bigman1";
	
}

