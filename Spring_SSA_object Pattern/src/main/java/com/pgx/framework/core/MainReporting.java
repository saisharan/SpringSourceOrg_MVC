package com.pgx.framework.core;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import com.pgx.framework.listeners.WebTestListeners;

public class MainReporting extends TestNGBase {
	//variables required
	public static String strReportFolderName = "";
	public static String strReportFilePath = "";
	public static int intTestCurrentXMLNumber;
	public static String strRootFolderPath="";
	public static String strReportFolderPath="";
	public static String strTestIterationErrMsg="";
	public static boolean blnTestReportMethodFlag;
	public static boolean blnReportRowIDFlag;
	public static String strTestRowNumber;
	public static String strImagePath;
	public static String strWebPagePath;
	public static String strScreenRecorderPath;
	public static String strScreenRecorderName;
	
	public static String getCurrentDateTime(String strFormat) {
		java.util.Date currentDate = new java.util.Date();
		SimpleDateFormat newFormat = new SimpleDateFormat(strFormat);
		return newFormat.format(currentDate);
	}

	public static String limitChars(String strValueToBeLimited, int intNoChar) {
		String strLimitedValue = "";
		if (strValueToBeLimited.length() < intNoChar) {
			strLimitedValue = strValueToBeLimited;
		}
		else {
			strLimitedValue = strValueToBeLimited.substring(0, intNoChar - 1);
		}
		return strLimitedValue;
	}
	@SuppressWarnings("unused")
	public static String getCurrentReportFolderName() {
		if (strReportFolderName == "") {
				//strReportFolderName = getCurrentDateTime("ddMMMyy_hhmmss")+"_"+GlobalStaticInfo.strIpFolderName+"_"+GlobalStaticInfo.strGlobalState+"_Rw-"+limitChars(GlobalStaticInfo.strRunRows,20);
			try{
				DateFormat dtYearFormat = new SimpleDateFormat("yyyy");
				DateFormat dtMonthFormat = new SimpleDateFormat("M");
				String strCurrYear = dtYearFormat.format(new Date());
				String strCurrMonth = dtMonthFormat.format(new Date());
				int intYear = Integer.parseInt(strCurrYear);
				if(EnvParameters.propValue("testtype").toLowerCase().equals("smoke"))
					strReportFolderPath = "C:\\Selenium Execution Reports\\SmokeTest Results\\" + strCurrYear + "\\" + theMonth(Integer.parseInt(strCurrMonth)) + "\\" + System.getProperty("user.name") + "\\";
				else
					strReportFolderPath = "C:\\Selenium Execution Reports\\Regression Test Results\\" + strCurrYear + "\\" + theMonth(Integer.parseInt(strCurrMonth)) + "\\" + System.getProperty("user.name") + "\\";
			}
			catch(Exception e){
				
			}
			strReportFolderName = getCurrentDateTime("ddMMMyy-hhmmss") + "-"+ "ITJumpStart";
			//createFolder(strReportFolderPath + strReportFolderName);
			return strReportFolderName;
		}
		else {
			return strReportFolderName;
		}
	}
	public static String theMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month-1];
	}

	public static String getCurrentReportFileName(int intTestInstance) {
		String reportFilename = System.getProperty("user.name") + "-" + getCurrentReportFolderName().split("_1")[0];
		return reportFilename;
	}

	public static String getCurrentReportFilePath() {
		strReportFilePath="";
		//String strBrowsername="Firefox";
		String strTempFolderName=getCurrentReportFolderName();
		strReportFilePath = strReportFolderPath + strTempFolderName;
		strReportFilePath = strReportFilePath + "-" + EnvParameters.webBrowserType.replace("*", "") ;
		createFolder(strReportFilePath);
		strImagePath=strReportFilePath;
		strReportFilePath = strReportFilePath + "\\"+ "TestReport" +"_Pg" + intTestCurrentXMLNumber+ ".xml";
		return strReportFilePath;
	}

	public static void createFolder(String strDirectoy) {
		boolean success = (new File(strDirectoy)).mkdirs();
		if (success) {
		}
	}

	public static boolean createSupportFiles() {
		try {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean copyDirectory(File sourceLocation, File targetLocation) {
		try {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean createInitXML()  {
		strReportFilePath = "";
		try {
			if (createSupportFiles()) {
				intTestCurrentXMLNumber = 1;
				   String fileName = System.getProperty("user.dir");
				   strRootFolderPath="\\\\bataan\\IT\\QA\\Selenium-cog-resources\\reporting-xslt";
				String xmlFile = "<?xml version=\"1.0\" encoding=\"us-ascii\"?>\n";
				xmlFile = xmlFile + "<?xml-stylesheet href='" + strRootFolderPath + "\\Report11.xsl' type=\'text/xsl\'?>\n";
				xmlFile = xmlFile + "\n<Report LastXMLNum=\"" + String.valueOf(intTestCurrentXMLNumber) + "\">" + "\n</Report>";

				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(new StringReader(xmlFile));
				strReportFilePath = getCurrentReportFilePath();
				File newReportFile = new File(strReportFilePath);
				if (newReportFile.exists()) {
					return true;
				}
				else {
					Writer writer = new FileWriter(new File(strReportFilePath));
					XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
					xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
					xmlOutput.output(doc, writer);
					return true;
				}
			}
			else {
				return false;
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Access is denied")) {
				strTestIterationErrMsg= "Access denied while creating XML file at " + strReportFilePath + " please check.";
				MainReporting.reportError(strTestIterationErrMsg, false);
			}
			e.printStackTrace();
			return false;
		}
	}

	public static boolean writeEnvDetailsToXMLReport()  {
		strReportFilePath = getCurrentReportFilePath();
		try {
			String strPrevXMLName = null, strNextXMLName = null, rws = null;
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			Element rootElement = doc.getRootElement();

			// Add the attribute to the root node
			rootElement.setAttribute("Env", EnvParameters.propValue("environment"));
			rootElement.setAttribute("RunFlow", EnvParameters.propValue("suitname"));
			rootElement.setAttribute("BuildVersion", EnvParameters.propValue("buildname"));
			rootElement.setAttribute("XMLNum", String.valueOf(intTestCurrentXMLNumber));
			rootElement.setAttribute("DateTime", getCurrentDateTime("dd-MMM-yyyy hh:mm:ss.SSS"));
			if (intTestCurrentXMLNumber== 1) {
				strPrevXMLName = "";
			}
			else {
				//rws = getCurrentRowsValue(intTestInstance, GlobalStaticInfo.intTestCurrentXMLNumber[intTestInstance] - 1);
				//strPrevXMLName = getCurrentReportFileName(intTestInstance) + "_Rw_" + rws + "_Pg";
			}
			rootElement.setAttribute("PrevXMLFileName", strPrevXMLName);
			try {
				//rws = getCurrentRowsValue(intTestInstance, intTestCurrentXMLNumber + 1);
				//strNextXMLName = getCurrentReportFileName(intTestInstance) + "_Rw_" + rws + "_Pg";
			} catch (Exception ee) {
				strNextXMLName = "";
			}
			rootElement.setAttribute("NextXMLFileName", "");
			rootElement.setAttribute("InputFilePath", "NA");
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("filenotfoundexception")) {
				strTestIterationErrMsg = "File not found exception " + strReportFilePath + " please check.";
				MainReporting.reportError( strTestIterationErrMsg, false);
			}
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static void writeLastXMLValue()  {
		String strReportFile = "";
		try {
			strReportFolderPath = strReportFolderPath + getCurrentReportFolderName();
			//strReportFolderPath = strReportFolderPath + "\\Int" + ("1") + "_Rw_" + limitChars(GlobalStaticInfo.strSplittedRows[], 20);

			File folder = new File(strReportFolderPath);
			File[] listOfFiles = folder.listFiles();

			for (int intLoop = 0; intLoop < listOfFiles.length; intLoop++) {
				if (listOfFiles[intLoop].isFile()) {
					strReportFile = listOfFiles[intLoop].getName();
					if (strReportFile.endsWith(".xml") || strReportFile.endsWith(".XML")) {
						strReportFile = listOfFiles[intLoop].getAbsolutePath();
						SAXBuilder builder = new SAXBuilder();
						File xmlFile = new File(strReportFile);
						Document doc = (Document) builder.build(xmlFile);
						Element rootElement = doc.getRootElement();

						XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
						xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
						xmlOutput.output(doc, new FileWriter(strReportFile));
					}
				}
			}
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("filenotfoundexception")) {
				//GlobalStaticInfo.strTestIterationErrMsg[intTestInstance] = "File not found exception " + strReportFile + " please check.";
				//MainReporting.reportError(intTestInstance, GlobalStaticInfo.strTestIterationErrMsg[intTestInstance], false);
			}
			System.out.println(e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addTestCaseNode(String strTestCaseID) {
		try {
			blnReportRowIDFlag = true;
			strTestRowNumber="1";
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			if (!xmlFile.exists()) {
				createInitXML();
			}
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
			// Create a new Test Suite node
			Element childTestCase = new Element("TestCase");

			// Add the attribute to the child
			List lstTestCase = rootNode.getChildren("TestCase");

			int newTestCaseID = lstTestCase.size() + 1;
			childTestCase.setAttribute("ID", String.valueOf(newTestCaseID));
			childTestCase.setAttribute("Desc", strTestCaseID);
			childTestCase.setAttribute("TCStatus", "1");

			childTestCase.setAttribute("Row", strTestRowNumber);

			// Adding number tag having id attribute to root element
			rootNode.addContent(childTestCase);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static void reportSkipped(String strDescription) {
		try {
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			// Create a new TestFlow node
			Element childTestFlow = new Element("TestFlow");

			// Add the attribute to the child
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			int newTestFlowID = lstTestFlow.size() + 1;
			childTestFlow.setAttribute("ID", String.valueOf(newTestFlowID));
			childTestFlow.setAttribute("Time", String.valueOf(new Date()));
			childTestFlow.setAttribute("Desc", strDescription);

			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestFlow);
			currentTestCase.setAttribute("TCStatus", "7");

			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private static void reportSpecialError(String strDescription) {
		boolean blnTestFlow = true, blnTestActivity = true;
		List lstTestActivity;
		Element currentTestFlow, currentTestActivity;
		try {
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			try {
				currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
				blnTestFlow = true;
			} catch (Exception e) {
				blnTestFlow = false;
			}

			if (!blnTestFlow) {
				// TestFlow node not found, create a new TestFlow node
				Element childTestFlow = new Element("TestFlow");
				// Add the attribute to the child
				int newTestFlowID = lstTestFlow.size() + 1;
				childTestFlow.setAttribute("ID", String.valueOf(newTestFlowID));
				childTestFlow.setAttribute("Desc", strDescription);
				// Adding number tag having id attribute to root element
				currentTestCase.addContent(childTestFlow);
			}
			lstTestFlow = currentTestCase.getChildren("TestFlow");
			currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			lstTestActivity = currentTestFlow.getChildren("TestActivity");
			try {
				currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
				blnTestActivity = true;
			} catch (Exception e) {
				blnTestActivity = false;
			}
			if (!blnTestActivity) {
				currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
				lstTestActivity = currentTestFlow.getChildren("TestActivity");
				// Create a new TestActivity node
				Element childTestActivity = new Element("TestActivity");
				// Add the attribute to the child
				int newTestActivityID = lstTestActivity.size() + 1;
				childTestActivity.setAttribute("ID", String.valueOf(newTestActivityID));
				childTestActivity.setAttribute("Desc", strDescription);
				currentTestFlow.addContent(childTestActivity);
			}
			lstTestActivity = currentTestFlow.getChildren("TestActivity");
			currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
			// Create a new TestMethod node
			Element childTestMethod = new Element("TestMethod");
			// Add the attribute to the child
			List lstTestMethod = currentTestActivity.getChildren("TestMethod");
			int newTestMethodID = lstTestMethod.size() + 1;
			childTestMethod.setAttribute("ID", String.valueOf(newTestMethodID));
			childTestMethod.setAttribute("Desc", strDescription);
			currentTestActivity.addContent(childTestMethod);
			currentTestCase.setAttribute("TCStatus", "0");
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private static void reportSpecialErrorOriginal(int intTestInstance, String strDescription) {
		boolean blnTestFlow = true, blnTestActivity = true;
		List lstTestActivity;
		Element currentTestFlow;
		try {
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			try {
				currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
				blnTestFlow = true;
			} catch (Exception e) {
				blnTestFlow = false;
			}

			if (!blnTestFlow) {
				// TestFlow node not found, create a new TestFlow node
				Element childTestFlow = new Element("TestFlow");
				// Add the attribute to the child
				int newTestFlowID = lstTestFlow.size() + 1;
				childTestFlow.setAttribute("ID", String.valueOf(newTestFlowID));
				childTestFlow.setAttribute("Desc", strDescription);

				// Adding number tag having id attribute to root element
				currentTestCase.addContent(childTestFlow);
			}
			else {
				try {
					currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
					lstTestActivity = currentTestFlow.getChildren("TestActivity");
					Element currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
					blnTestActivity = true;
					// Create a new TestMethod node
					Element childTestMethod = new Element("TestMethod");
					// Add the attribute to the child
					List lstTestMethod = currentTestActivity.getChildren("TestMethod");
					int newTestMethodID = lstTestMethod.size() + 1;
					childTestMethod.setAttribute("ID", String.valueOf(newTestMethodID));
					childTestMethod.setAttribute("Desc", strDescription);
					currentTestActivity.addContent(childTestMethod);
				} catch (Exception e) {
					blnTestActivity = false;
				}
				if (!blnTestActivity) {
					currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
					lstTestActivity = currentTestFlow.getChildren("TestActivity");
					// Create a new TestActivity node
					Element childTestActivity = new Element("TestActivity");
					// Add the attribute to the child
					int newTestActivityID = lstTestActivity.size() + 1;
					childTestActivity.setAttribute("ID", String.valueOf(newTestActivityID));
					childTestActivity.setAttribute("Desc", strDescription);
					currentTestFlow.addContent(childTestActivity);
				}
			}
			currentTestCase.setAttribute("TCStatus", "0");

			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addTestFlowNode(String strDescription) {
		strReportFilePath = getCurrentReportFilePath();
		try {
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			// Create a new TestFlow node
			Element childTestFlow = new Element("TestFlow");

			// Add the attribute to the child
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			int newTestFlowID = lstTestFlow.size() + 1;
			childTestFlow.setAttribute("ID", String.valueOf(newTestFlowID));
			childTestFlow.setAttribute("Desc", strDescription);

			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestFlow);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("filenotfoundexception")) {
				strTestIterationErrMsg= "File not found exception " + strReportFilePath + " please check.";
				MainReporting.reportError( strTestIterationErrMsg, false);
			}
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addTestActivityNode( String strDescription) {
		try {
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			Element currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			// Create a new TestActivity node
			Element childTestActivity = new Element("TestActivity");

			// Add the attribute to the child
			List lstTestActivity = currentTestFlow.getChildren("TestActivity");
			int newTestActivityID = lstTestActivity.size() + 1;
			childTestActivity.setAttribute("ID", String.valueOf(newTestActivityID));
			childTestActivity.setAttribute("Desc", strDescription);

			currentTestFlow.addContent(childTestActivity);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addTestMethodNode(String strDescription) {
		try {
			blnTestReportMethodFlag= true;
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			Element currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			List lstTestActivity = currentTestFlow.getChildren("TestActivity");
			Element currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
			// Create a new TestMethod node
			Element childTestMethod = new Element("TestMethod");

			// Add the attribute to the child
			List lstTestMethod = currentTestActivity.getChildren("TestMethod");
			int newTestMethodID = lstTestMethod.size() + 1;
			childTestMethod.setAttribute("ID", String.valueOf(newTestMethodID));
			childTestMethod.setAttribute("Desc", strDescription);

			// Adding number tag having id attribute to root element
			currentTestActivity.addContent(childTestMethod);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addTestSubMethodNode(String strDescription) {
		try {
			blnTestReportMethodFlag = false;
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			Element currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			List lstTestActivity = currentTestFlow.getChildren("TestActivity");
			Element currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
			List lstTestMethod = currentTestActivity.getChildren("TestMethod");
			Element currentTestMethod = (Element) lstTestMethod.get(lstTestMethod.size() - 1);
			// Create a new TestMethod node
			Element childTestSubMethod = new Element("TestSubMethod");
			// Add the attribute to the child
			List lstTestSubMethod = currentTestMethod.getChildren("TestSubMethod");
			int newTestSubMethodID = lstTestSubMethod.size() + 1;
			childTestSubMethod.setAttribute("ID", String.valueOf(newTestSubMethodID));
			childTestSubMethod.setAttribute("Desc", strDescription);
			// Adding number tag having id attribute to root element
			currentTestMethod.addContent(childTestSubMethod);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addResultNode( String strReportType, String strDescription, int intStatus) {
		try {
			strReportFilePath = getCurrentReportFilePath();
			strTestRowNumber="1";
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			Element currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			List lstTestActivity = currentTestFlow.getChildren("TestActivity");
			Element currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
			List lstTestMethod = currentTestActivity.getChildren("TestMethod");
			Element currentTestMethod = (Element) lstTestMethod.get(lstTestMethod.size() - 1);
			if (strReportType.equalsIgnoreCase("method")) {
				// Create a new MethodResult node
				Element childMethodResult = new Element("MethodResult");
				// Add the attribute to the child
				childMethodResult.setAttribute("Status", String.valueOf(intStatus));
				childMethodResult.setAttribute("Time", String.valueOf(new Date()));
				// - Giving ID for the first failed node
				if (blnReportRowIDFlag && (intStatus % 10 == 0)) {
					childMethodResult.setAttribute("ID", "fail_" + strTestRowNumber);
					blnReportRowIDFlag = false;
				}
				childMethodResult.setText(handleSpecialCharactersForXML(strDescription));
				// Adding number tag having id attribute to root element
				currentTestMethod.addContent(childMethodResult);
			}
			else {
				List lstTestSubMethod = currentTestMethod.getChildren("TestSubMethod");
				Element currentTestSubMethod = (Element) lstTestSubMethod.get(lstTestSubMethod.size() - 1);
				// Create a new SubMethodResult node
				Element childSubMethodResult = new Element("SubMethodResult");
				// Add the attribute to the child
				childSubMethodResult.setAttribute("Status", String.valueOf(intStatus));
				childSubMethodResult.setAttribute("Time", String.valueOf(new Date()));
				// - Giving ID for the first failed node
				if (blnReportRowIDFlag && (intStatus % 10 == 0)) {
					childSubMethodResult.setAttribute("ID", "fail_" + strTestRowNumber);
					blnReportRowIDFlag = false;
				}
				childSubMethodResult.setText(handleSpecialCharactersForXML(strDescription));
				// Adding number tag having id attribute to root element
				currentTestSubMethod.addContent(childSubMethodResult);
			}
			// Update overall result for the TestCase node
			if ((intStatus == 0) || (intStatus == 20)) {
				if (!currentTestCase.getAttributeValue("TCStatus").equals("10")) {
					currentTestCase.setAttribute("TCStatus", "0");
				}
				else {
					currentTestCase.setAttribute("TCStatus", "10");
				}
			}
			else {
				if ((!currentTestCase.getAttributeValue("TCStatus").equals("0")) && (!currentTestCase.getAttributeValue("TCStatus").equals("10"))) {
					if (!currentTestCase.getAttributeValue("TCStatus").equals("11")) {
						currentTestCase.setAttribute("TCStatus", "1");
					}
					else {
						currentTestCase.setAttribute("TCStatus", "11");
					}
				}
			}
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (IndexOutOfBoundsException e) {
			MainReporting.reportSpecialError( "Exception: '" + e.getMessage()+ "'. Message: '" + strDescription + "'");
			return false;
		} catch (Exception e) {
			if (intStatus == 0 || intStatus == 10 || intStatus == 20)
				MainReporting.reportError( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 1 || intStatus == 11 || intStatus == 21)
				MainReporting.reportPass( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 4)
				MainReporting.reportInformation( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 2)
				MainReporting.reportWarning( strDescription + " : " + e.getMessage(), false);
			return false;
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean addImagePathNode(String strReportType, String strDescription, String strImagePath, int intStatus) {
		try {
			strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase = doc.getRootElement().getChildren("TestCase");
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			List lstTestFlow = currentTestCase.getChildren("TestFlow");
			Element currentTestFlow = (Element) lstTestFlow.get(lstTestFlow.size() - 1);
			List lstTestActivity = currentTestFlow.getChildren("TestActivity");
			Element currentTestActivity = (Element) lstTestActivity.get(lstTestActivity.size() - 1);
			List lstTestMethod = currentTestActivity.getChildren("TestMethod");
			Element currentTestMethod = (Element) lstTestMethod.get(lstTestMethod.size() - 1);
			if (strReportType.equalsIgnoreCase("method")) {
				// Create a new MethodResult node
				Element childMethodResult = new Element("MethodResult");
				// Add the attribute to the child
				childMethodResult.setAttribute("Status", String.valueOf(intStatus));
				childMethodResult.setAttribute("Time", String.valueOf(new Date()));
				childMethodResult.setAttribute("ImagePath", strImagePath);
				// - Giving ID for the first failed node
				if (blnReportRowIDFlag && (intStatus % 10 == 0)) {
					childMethodResult.setAttribute("ID", "fail_" + strTestRowNumber);
					childMethodResult.setAttribute("WebPagePath", strWebPagePath);//for the webPage link
					childMethodResult.setAttribute("VideoRecording", strScreenRecorderName);//for the webPage link
					blnReportRowIDFlag = false;
				}
				childMethodResult.setText(handleSpecialCharactersForXML(strDescription));
				// Adding number tag having id attribute to root element
				currentTestMethod.addContent(childMethodResult);
			}
			else {
				List lstTestSubMethod = currentTestMethod.getChildren("TestSubMethod");
				Element currentTestSubMethod = (Element) lstTestSubMethod.get(lstTestSubMethod.size() - 1);
				// Create a new SubMethodResult node
				Element childSubMethodResult = new Element("SubMethodResult");
				// Add the attribute to the child
				childSubMethodResult.setAttribute("Status", String.valueOf(intStatus));
				childSubMethodResult.setAttribute("Time", String.valueOf(new Date()));
				childSubMethodResult.setAttribute("ImagePath",strImagePath);
				// - Giving ID for the first failed node
				if ((blnReportRowIDFlag) && (intStatus % 10 == 0)) {
					childSubMethodResult.setAttribute("ID", "fail_" + strTestRowNumber);
					childSubMethodResult.setAttribute("WebPagePath", strWebPagePath);//for the webPage link
					childSubMethodResult.setAttribute("VideoRecording", strScreenRecorderName);//for the webPage link
					blnReportRowIDFlag= false;
				}
				childSubMethodResult.setText(handleSpecialCharactersForXML(strDescription));
				// Adding number tag having id attribute to root element
				currentTestSubMethod.addContent(childSubMethodResult);
			}
			// Update overall result for the TestCase node
			if ((intStatus == 0) || (intStatus == 20)) {
				currentTestCase.setAttribute("TCStatus", "10");
			}
			else {
				if ((!currentTestCase.getAttributeValue("TCStatus").equals("0")) && (!currentTestCase.getAttributeValue("TCStatus").equals("10"))) {
					currentTestCase.setAttribute("TCStatus", "11");
				}
				else {
					currentTestCase.setAttribute("TCStatus", "10");
				}
			}
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("US-ASCII"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (IndexOutOfBoundsException e) {
			MainReporting.reportSpecialError( strDescription);
			return false;
		} catch (Exception e) {
			if (intStatus == 0 || intStatus == 10 || intStatus == 20)
				MainReporting.reportError( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 1 || intStatus == 11 || intStatus == 21)
				MainReporting.reportPass( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 4)
				MainReporting.reportInformation( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 2)
				MainReporting.reportWarning( strDescription + " : " + e.getMessage(), false);
			return false;
		}
	}

	public static boolean reportPass(String strDescription, boolean imgCapture) {
		String strReportType;
		if (blnTestReportMethodFlag) {
			strReportType = "method";
		}
		else {
			strReportType = "submethod";
		}
		if (imgCapture) {
			if (captureScreenShot(strReportType, strDescription, 21)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (addResultNode( strReportType, strDescription, 1)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static boolean reportError(String strDescription, boolean imgCapture) {
		String strReportType;
		if (blnTestReportMethodFlag) {
			strReportType = "method";
		}
		else {
			strReportType = "submethod";
		}
		if (imgCapture) {
			if (captureScreenShot(strReportType, strDescription, 20)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (addResultNode( strReportType, strDescription, 0)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static boolean reportWarning(String strDescription, boolean imgCapture) {
		String strReportType;
		if (blnTestReportMethodFlag) {
			strReportType = "method";
		}
		else {
			strReportType = "submethod";
		}
		if (imgCapture) {
			if (captureScreenShot(strReportType, strDescription, 2)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (addResultNode( strReportType, strDescription, 2)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static boolean reportInformation(String strDescription, boolean imgCapture) {
		String strReportType;
		if (blnTestReportMethodFlag) {
			strReportType = "method";
		}
		else {
			strReportType = "submethod";
		}
		if (imgCapture) {
			if (captureScreenShot( strReportType, strDescription, 4)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if (addResultNode( strReportType, strDescription, 4)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public static boolean captureScreenShot(String strReportType, String strDescription, int intStatus) {
        WebDriverBase wbdriver=new WebDriverBase();
		strWebPagePath= "";
		File scrFile;
		BufferedWriter writer = null;
		try {
			String strImageName, strWebPageName;
			//For selenium execution reports
			strImagePath= strImagePath + "\\Snap";
			strWebPagePath= strImagePath;
			strScreenRecorderPath=strImagePath;
			createFolder(strImagePath);
			strImageName = getCurrentDateTime("ddMMMyy_hhmmss") + ".png";
			//capturing and copying the screenshot
			strImagePath= strImagePath+ "\\" + strImageName;
			scrFile = ((TakesScreenshot)WebUtility.scrndriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(strImagePath));
			//capturing and copying the webPage
			if (blnReportRowIDFlag) {
				strWebPageName = strImageName.replace(".png", ".html");
				strWebPagePath = strWebPagePath+ "\\" + strWebPageName;
				scrFile = new File(strWebPagePath);
				writer = new BufferedWriter(new FileWriter(scrFile));
				writer.write(WebUtility.scrndriver.getPageSource());
				writer.close();
			}
			if (blnReportRowIDFlag) {
				strScreenRecorderName =WebTestListeners.testResultVideos+"\\"+WebTestListeners.sFilename+".avi";
			}
			Thread.sleep(1000);
			String strRelativeImgPath = "Snap\\" + strImageName;
			if (addImagePathNode(strReportType, strDescription, strRelativeImgPath, intStatus)) {
				return true;
			}
			else {
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			MainReporting.reportSpecialError( strDescription);
			return false;
		} catch (ClassCastException cce) {
			try {
                            //if (!GlobalStaticInfo.strTestEnvironment.toLowerCase().contains("dev"))
                            //FileUtils.copyFile(scrFile, new File(strImagePath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (intStatus == 0 || intStatus == 10 || intStatus == 20)
				MainReporting.reportError( strDescription + " : " + cce.getMessage(), false);
			else if (intStatus == 1 || intStatus == 11 || intStatus == 21)
				MainReporting.reportPass( strDescription + " : " + cce.getMessage(), false);
			else if (intStatus == 4)
				MainReporting.reportInformation( strDescription + " : " + cce.getMessage(), false);
			else if (intStatus == 2)
				MainReporting.reportWarning( strDescription + " : " + cce.getMessage(), false);
			return true;
		} catch (IOException ioe) {
			if (intStatus == 0 || intStatus == 10 || intStatus == 20)
				MainReporting.reportError( strDescription + " : " + ioe.getMessage(), false);
			else if (intStatus == 1 || intStatus == 11 || intStatus == 21)
				MainReporting.reportPass( strDescription + " : " + ioe.getMessage(), false);
			else if (intStatus == 4)
				MainReporting.reportInformation( strDescription + " : " + ioe.getMessage(), false);
			else if (intStatus == 2)
				MainReporting.reportWarning( strDescription + " : " + ioe.getMessage(), false);
			return false;
		} catch (Exception e) {
			if (intStatus == 0 || intStatus == 10 || intStatus == 20)
				MainReporting.reportError( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 1 || intStatus == 11 || intStatus == 21)
				MainReporting.reportPass( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 4)
				MainReporting.reportInformation( strDescription + " : " + e.getMessage(), false);
			else if (intStatus == 2)
				MainReporting.reportWarning( strDescription + " : " + e.getMessage(), false);
			return false;
		}
	}

	public static String handleSpecialCharactersForXML(String strDescription) throws FileNotFoundException, InterruptedException, IOException {
		try {
			/*
			 * String[] specialChar = { "<", ">", "�", "�", "�", "�", "�", "�",
			 * "�", "�", "�", "�", "�� ", "�", "�", "�", "�", "�", "�", "�",
			 * "�"};
			 * 
			 * String[] entityNumber = { "&#60;", "&#62;", "&#162;", "&#163;",
			 * "&#165;", "&#8364;", "&#167;", "&#169;", "&#174;", "&#8482;",
			 * "&#8212;", "&#8211;", " ", "&#8217;", "&#8226;", "&#8221;",
			 * "&#8220;", "&#160;", "&#8230;", "&#194;", "&#226;"}; for (int i =
			 * 0; i < specialChar.length; i++) { strDescription =
			 * strDescription.replaceAll(specialChar[i], entityNumber[i]); }
			 */
		} catch (Exception e) {
		}
		return strDescription;
	}
}
