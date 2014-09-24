package com.pgx.framework.core;
import com.pgx.framework.core.*;
import com.pgx.framework.core.MainReporting;
import java.io.IOException;
import java.util.ArrayList;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

    public class RWDDriverScript extends TestNGBase{
	public static String strpreviousXLPath="";
       /*
        * This method does the comparison of object properties from spreadsheet to the actual application 
        * properties
        */
        public void process(String strSheetName){
	     try{
                    String strXLPath=rWDsheetpath();
                    if(FilenameUtils.getExtension(strXLPath).equalsIgnoreCase("xlsx")){
				return;
                    }
			WebElement wbelm;
                        By locator;
			boolean blnResult=true;int intTilHeight = 0,intTilWidth = 0,intTotalTilHeight=0,intTotalTilWidth=0;
			String[]  arrColumnHeader={ "ElementName",
                                                    "ElementLocator",
                                                    "ExpectedHeight",
                                                    "ActualHeight",
                                                    "ExpectedWidth",
                                                    "ActualWidth",
                                                    "Expectedxlocation",
                                                    "Actualxlocation",
                                                    "Expectedylocation",
                                                    "Actualylocation",
                                                    "ExpectedText",
                                                    "ActualText",
                                                    "ExpectedColor",
                                                    "ActualColor"};
			if(!strpreviousXLPath.equals(strXLPath)){
				GlobalStatic.strFilePath="";
				GlobalStatic.intOutputRowCntr=0;
				GlobalStatic.strReportFolderName="";
			}
			if(GlobalStatic.strFilePath.equals("")){
				GlobalStatic.createExcel(strXLPath,arrColumnHeader, strSheetName);
			}
			else{
				if(!GlobalStatic.strGlobalSheetName.equals(strSheetName)){
					GlobalStatic.createNewSheet(strSheetName, arrColumnHeader);
				}
			}
			String strElementName,strElementLocator,strHeight,strWidth,strxlocation,strylocation,strText,strColor;
			int intRows=GlobalStatic.getColumnValuesFromExcel(strXLPath, strSheetName, "ElementName");
		 	for(int intlpctrl=1;intlpctrl<intRows;intlpctrl++){
		 		ArrayList<String> arrOutputValues = new ArrayList<String>();
		 		GlobalStatic.getDataFromExcelToHashmap(strXLPath, strSheetName, intlpctrl+1);
				strElementName = GlobalStatic.getHashMapValue(strSheetName + "_ElementName");
				strElementLocator = GlobalStatic.getHashMapValue(strSheetName + "_ElementLocator");
				strHeight = GlobalStatic.getHashMapValue(strSheetName + "_Height"); 
				strWidth = GlobalStatic.getHashMapValue(strSheetName + "_Width"); 
				strxlocation= GlobalStatic.getHashMapValue(strSheetName + "_xlocation");
				strylocation= GlobalStatic.getHashMapValue(strSheetName + "_ylocation");
				strText= GlobalStatic.getHashMapValue(strSheetName + "_Text");
				strColor= GlobalStatic.getHashMapValue(strSheetName + "_Color");
				System.out.println(strElementLocator);
				arrOutputValues.add(strElementName);
				arrOutputValues.add(strElementLocator);
                                if(strElementLocator.contains("^")){
                                    for(int i=0;i<strElementLocator.split("\\^").length;i++){
                                        locator= ElementLocator.getBySelector(strElementLocator.split("\\^")[i]);
                                        wbelm=WebUtility.scrndriver.findElement(locator);
                                        intTilHeight=GlobalStatic.getObjectHeight(wbelm);
                                        intTilWidth=GlobalStatic.getObjectWidth(wbelm);
                                        intTotalTilHeight+=intTilHeight;
                                        intTotalTilWidth+=intTilWidth;
                                      }
                                        arrOutputValues.add(strHeight);
                                        if(!strHeight.equals(""))
                                                arrOutputValues.add(compareResults(strHeight, intTotalTilHeight));
                                        else
                                                arrOutputValues.add("");
                                        arrOutputValues.add(strWidth);
                                        if(!strWidth.equals(""))
                                                arrOutputValues.add(compareResults(strWidth,intTotalTilWidth));
                                        else
                                                arrOutputValues.add("");
                                        arrOutputValues.add(strxlocation);
                                        arrOutputValues.add("");
                                        arrOutputValues.add(strylocation);
                                        arrOutputValues.add("");
                                        arrOutputValues.add(strText);
                                        arrOutputValues.add("");
                                        arrOutputValues.add(strColor);
                                        arrOutputValues.add("");
                                }
                                else{
                                        locator= ElementLocator.getBySelector(strElementLocator);
                                        wbelm=WebUtility.scrndriver.findElement(locator);    
                                        arrOutputValues.add(strHeight);
                                        //comparing and adding height of the object to the arraylist to update in excel
                                        if(!strHeight.equals(""))
                                                arrOutputValues.add(compareResults(strHeight, GlobalStatic.getObjectHeight(wbelm)));
                                        else
                                                arrOutputValues.add("");
                                        //comparing and adding width of the object to the arraylist to update in excel
                                        arrOutputValues.add(strWidth);
                                        if(!strWidth.equals(""))
                                                arrOutputValues.add(compareResults(strWidth, GlobalStatic.getObjectWidth(wbelm)));
                                        else
                                                arrOutputValues.add("");
                                        //comparing and adding xaxis of the object to the arraylist to update in excel
                                        arrOutputValues.add(strxlocation);
                                        if(!strxlocation.equals(""))
                                                arrOutputValues.add(compareResults(strxlocation, GlobalStatic.getXAxis(wbelm)));
                                        else
                                                arrOutputValues.add("");
                                        //comparing and adding yaxis of the object to the arraylist to update in excel
                                        arrOutputValues.add(strylocation);
                                        if(!strylocation.equals(""))
                                                arrOutputValues.add(compareResults(strylocation, GlobalStatic.getYAxis(wbelm)));
                                        else
                                                arrOutputValues.add("");
                                        //comparing and adding text of the object to the arraylist to update in excel
                                        arrOutputValues.add(strText);
                                        if(!strText.equals("")){
                                                if(strText.equals(GlobalStatic.getText(wbelm))){
                                                        arrOutputValues.add(GlobalStatic.getText(wbelm));
                                                }
                                                else
                                                        arrOutputValues.add(GlobalStatic.getText(wbelm)+"~");
                                        }
                                        else
                                                arrOutputValues.add("");
                                        //comparing and adding color of the object to the arraylist to update in excel
                                        arrOutputValues.add(strColor);
                                        if(!strColor.equals("")){
                                                if(strColor.equals(GlobalStatic.getObjectHexaColorCode(wbelm)))
                                                        arrOutputValues.add(GlobalStatic.getObjectHexaColorCode(wbelm));
                                                else
                                                        arrOutputValues.add(GlobalStatic.getObjectHexaColorCode(wbelm)+"~");
                                        }
                                        else
                                                arrOutputValues.add("");
                                }
				GlobalStatic.updateExcel(GlobalStatic.strFilePath,arrOutputValues);
				if(arrOutputValues.toString().contains("~")){
					blnResult=false;
				}
		 	}
                        strpreviousXLPath=strXLPath;
			 	if(blnResult){
					MainReporting.reportInformation("All the Actual Properties are as expected. Refer the following spread sheet for more details: "
                                                +GlobalStatic.strFilePath, false);
				}
				else{
					MainReporting.reportWarning("Actual Properties are NOT as expected. Refer the following spread sheet for more details: "
                                                +GlobalStatic.strFilePath, true);
                                        throw new RuntimeException("RWD Verification for "+strSheetName+" is falied");
				}
		   }
                    catch(Exception e){
                            throw new RuntimeException(e.toString().split(":")[1]);
                    }
	}
        
        /*
         * This method compares the results of objects and gives the output based on comparison values
         */
	public static String compareResults(String strExpectedValue, int intActualValue){
		int intExpectedVal;
		intExpectedVal=Integer.parseInt(strExpectedValue);
		if(intExpectedVal==intActualValue){
			return String.valueOf(intActualValue);
		}
		else
			return String.valueOf(intActualValue)+"~";
	}
        
        /*
         * This gives the path of RWD Feed for various resolutions
         */
        public String rWDsheetpath(){
            String strFilepath="";
          switch (TestNGBase.strScreenWidth) {
            case "1272":  strFilepath= EnvParameters.getTestRootDir()+"\\Resources\\RWDFeed\\1272.xls"; break;
            case "960":  strFilepath= EnvParameters.getTestRootDir()+"\\Resources\\RWDFeed\\960.xls"; break;
            case "765":  strFilepath=  EnvParameters.getTestRootDir()+"\\Resources\\RWDFeed\\765.xls";break;
            case "576":  strFilepath= EnvParameters.getTestRootDir()+"\\Resources\\RWDFeed\\576.xls";break;
            case "318":  strFilepath= EnvParameters.getTestRootDir()+"\\Resources\\RWDFeed\\318.xls";break;
            default: throw new RuntimeException("invalid resolution given in the suite file");
          }
          return strFilepath;
        }
}
