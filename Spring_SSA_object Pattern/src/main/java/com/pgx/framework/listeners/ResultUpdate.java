package com.pgx.framework.listeners;


import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/*
 * This class is to generate results xml to update in testlink
 */
public class ResultUpdate {
	public static String strRootFolderPath="",XMLFolderName="";
	public static boolean createInitXML()  {
		String strReportFilePath = "";
		String strTestIterationErrMsg="";
		try {
				String xmlFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				//xmlFile = xmlFile + "\n<Report LastXMLNum=\""+ String.valueOf(GlobalStaticInfo.intTestCurrentXMLNumber[intTestInstance])+" XMLFileName=\"" +getCurrentReportFileName(intTestInstance)+ "\">" + "\n</Report>";
				xmlFile = xmlFile + "<!--  TestLink - www.teamst.org - xml to allow results import -->";
				xmlFile = xmlFile + "\n<results>" + "\n</results>";
				SAXBuilder builder = new SAXBuilder();
				Document doc = builder.build(new StringReader(xmlFile));
				strReportFilePath = getCurrentReportFilePath();
				File newReportFile = new File(strReportFilePath);
				if (newReportFile.exists()) {
					newReportFile.delete();
					Writer writer = new FileWriter(new File(strReportFilePath));
					XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
					xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
					xmlOutput.output(doc, writer);
					return true;
				}
				else {
					Writer writer = new FileWriter(new File(strReportFilePath));
					XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
					xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
					xmlOutput.output(doc, writer);
					return true;
				}
		} catch (Exception e) {
			if (e.getMessage().contains("Access is denied")) {
				strTestIterationErrMsg= "Access denied while creating XML file at " + strReportFilePath + " please check.";
				System.out.println(strTestIterationErrMsg);
				//MainReporting.reportError(strTestIterationErrMsg, false);
			}
			e.printStackTrace();
			return false;
		}
	}
	public static boolean addCommonNodes(String strProjectName,String strProjectPrefix,String strTestPlanName,String strBuildName){
		try {
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strRootFolderPath);
			if (!xmlFile.exists()) {
				createInitXML();
			}
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
			// Create a new testproject node
			Element childTestCase1 = new Element("testproject");
			// Add the attribute to the child
			childTestCase1.setAttribute("name", strProjectName);
			childTestCase1.setAttribute("prefix", strProjectPrefix);
			// Adding number tag having id attribute to root element
			rootNode.addContent(childTestCase1);
			
			// Create a new testplan node
			Element childTestCase2 = new Element("testplan");
			// Add the attribute to the child
			childTestCase2.setAttribute("name", strTestPlanName);
			// Adding number tag having id attribute to root element
			rootNode.addContent(childTestCase2);
			
			// Create a new build node
			Element childTestCase3 = new Element("build");
		   // Add the attribute to the child
			childTestCase3.setAttribute("name", strBuildName);
			// Adding number tag having id attribute to root element
			rootNode.addContent(childTestCase3);
			
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmlOutput.output(doc, new FileWriter(strRootFolderPath));
			return true;
	} catch (Exception e) {
		e.printStackTrace();
		return false;
	}
	}
	public static boolean addTestCaseNode(String strExternalID) {
		try {
			String strReportFilePath = getCurrentReportFilePath();
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
			// Create a new testcase node
			Element childTestCase3 = new Element("testcase");
		    // Add the attribute to the child
		    childTestCase3.setAttribute("external_id", strExternalID);
			// Adding number tag having id attribute to root element
			rootNode.addContent(childTestCase3);
						
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmlOutput.output(doc, new FileWriter(strRootFolderPath));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static boolean addTestResultsNode(String strExternal_ID,String strResult,String strNotes,String strTester,String strTimeStamp,String strBug_id) {
		String strReportFilePath = getCurrentReportFilePath();
		String strTestIterationErrMsg="";
		try {
			addTestCaseNode(strExternal_ID);
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File(strReportFilePath);
			Document doc = (Document) builder.build(xmlFile);
			List lstTestCase1 = doc.getRootElement().getChildren("testproject");
			List lstTestCase2 = doc.getRootElement().getChildren("testplan");
			List lstTestCase3 = doc.getRootElement().getChildren("build");
			List lstTestCase = doc.getRootElement().getChildren("testcase");
			
			Element currentTestCase = (Element) lstTestCase.get(lstTestCase.size() - 1);
			
			// Create a new result node
			Element childTestResult = new Element("result");
			childTestResult.setText(strResult);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestResult);
			
			// Create a new notes node
			Element childTestnotes = new Element("notes");
			childTestnotes.setText(strNotes);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestnotes);
			
			// Create a new tester node
			Element childTesttester = new Element("tester");
			childTesttester.setText(strTester);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTesttester);
			// Create a new TestFlow node
			/*String strComment="<!--  if not present now() will be used -->";
			//Element childTestComment = new Element(strComment);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(strComment);*/

			// Create a new timestamp node
			Element childTestTimeStamp = new Element("timestamp");
			childTestTimeStamp.setText(strTimeStamp);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestTimeStamp);
			
			// Create a new bug_id node
			Element childTestBugID = new Element("bug_id");
			childTestBugID.setText(strBug_id);
			// Adding number tag having id attribute to root element
			currentTestCase.addContent(childTestBugID);
			
			XMLOutputter xmlOutput = new XMLOutputter(Format.getRawFormat());
			xmlOutput.setFormat(Format.getPrettyFormat().setEncoding("UTF-8"));
			xmlOutput.output(doc, new FileWriter(strReportFilePath));
			return true;
		} catch (Exception e) {
			if (e.getMessage().toLowerCase().contains("filenotfoundexception")) {
				strTestIterationErrMsg= "File not found exception " + strReportFilePath + " please check.";
				System.out.println(strTestIterationErrMsg);
			}
			return false;
		}
	}
	public static String getCurrentReportFilePath() {
		try{
			String strXMLPath="";
			   String fileName = System.getProperty("user.dir");
		       File file = new File(fileName + "\\Resources");
			   strRootFolderPath=file.getCanonicalPath();
			createFolder(strRootFolderPath);
			strRootFolderPath = strRootFolderPath + "\\"+ "Results.xml";
		}
		catch(Exception e){
			System.out.println("Exception Occured while fetching the cononical path: "+e.getMessage());
		}
		return strRootFolderPath;
	}
	
	public static void createFolder(String strDirectoy) {
		File file=new File(strDirectoy);
		if(!file.exists()){
			(new File(strDirectoy)).mkdirs();
	       }
		else{
			System.out.println("Already folder is existed");
		}
	}
	public static String getCurrentDateTime(String strFormat) {
		java.util.Date currentDate = new java.util.Date();
		SimpleDateFormat newFormat = new SimpleDateFormat(strFormat);
		XMLFolderName= newFormat.format(currentDate);
		return XMLFolderName;
	}
}

