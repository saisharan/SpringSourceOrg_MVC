package com.pgx.framework.utility;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.SkipException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pgx.framework.core.EnvParameters;

import ch.randelshofer.screenrecorder.ScreenRecorder;



public class WebUtil {

    /**
     * This method will capture the screen shot if there is a failure in tests
     * 
     * @param ImageFileName
     */
    public static final int DEFAULT_FILE_SIZE_KB = 1;
    /*
     * This takes the screenshot of the browser
     */
    public static void takeScreenShot(String ImageFileName) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Rectangle screenRect = new Rectangle(screenSize);
        // create screen shot
        BufferedImage image = robot.createScreenCapture(screenRect);
        // save captured image to PNG file
        try {
            int count = 0;
            String currentImageFilePath = null;
            File file;
            do {
                currentImageFilePath =
                        ImageFileName + ((count == 0) ? "" : count) + ".png";
                file = new File(currentImageFilePath);
                count++;
            } while (file.exists());
            ImageIO.write(image, "png", new File(currentImageFilePath));

            Reporter.log("Final Screenshot:<br><a href='file:///"
                    + currentImageFilePath
                    + "' target='new'> <img src='file:///"
                    + currentImageFilePath
                    + "' width='300px' height='200px' /></a><br> ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * This method is used capture videos
     */
    public static void captureVideo(String videofilename,ScreenRecorder recorder) {
        try {
            String currentVideoFilePath = videofilename;
            if (EnvParameters.getRunMode().equals("maven"))
            if (recorder != null)
                  recorder.stop();
        } catch (Exception e) {
               e.printStackTrace();
               System.err.println("Error in onTestFailure" + e.getMessage());
        }
   }
    /*
     * This method clears the video folder on test pass
     */
    public static void clearVideoFolder() {
    	 String userHomePath = System.getProperty("user.home");
	        File userHome = new File(userHomePath);
	        // uses the corect path separator for the OS
	        File videos = new File(userHome, "Videos");
	        File[] files=videos.listFiles();
	        for (File file:files)
	        	if (file.getName().contains("ScreenRecording"))
	        		file.delete();
    }

    /*
     * This method creates the spread sheet for output results
     */
    @SuppressWarnings("deprecation")
    public void createResultExcel(String FileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(FileName);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("ExecutionResult");
            HSSFRow row1 = worksheet.createRow((short) 0);
            HSSFCell cellA1 = row1.createCell((short) 0);
            cellA1.setCellValue("TestCase");
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
            cellA1.setCellStyle(cellStyle);
            HSSFCell cellB1 = row1.createCell((short) 1);
            cellB1.setCellValue("Group");
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
            cellB1.setCellStyle(cellStyle);
            HSSFCell cellc1 = row1.createCell((short) 2);
            cellc1.setCellValue("ExecutionDate");
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
            cellc1.setCellStyle(cellStyle);
            HSSFCell celle1 = row1.createCell((short) 3);
            celle1.setCellValue("Result");
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);
            celle1.setCellStyle(cellStyle);
            HSSFCell cellf1 = row1.createCell((short) 4);
            cellf1.setCellValue("Verification");
            cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellf1.setCellStyle(cellStyle);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     * Generates output excel sheet for results
     */
    @SuppressWarnings("deprecation")
	public static void generateExcel(File xmlDocument) {
        try {// Creating a Workbook
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet spreadSheet = wb.createSheet("spreadSheet");
            spreadSheet.setColumnWidth((short) 0, (short) (256 * 25));
            spreadSheet.setColumnWidth((short) 1, (short) (256 * 25));
            // Parsing XML Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlDocument);
            NodeList nodeList = document.getElementsByTagName("test-method");
            // Creating Rows
            HSSFRow headerrow = spreadSheet.createRow(0);
            HSSFCell cell = headerrow.createCell(0);
            cell.setCellValue("Test Method");
            cell = headerrow.createCell(1);
            cell.setCellValue("Result");
            HSSFCellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            for(int i=0,j=1; i<nodeList.getLength(); i++){
                String attribute=nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
                if (!(attribute.equals("setUp") || attribute.equals("testSetup") || attribute.equals("postTestCase") || attribute.equals("tearDown") || attribute.equals("reportGeneration")))
                {
                    HSSFRow row = spreadSheet.createRow(j);
                    j++;
                    cell = row.createCell(0);
                    cell.setCellValue(nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue());
                    cell = row.createCell(1);
                    cell.setCellValue(nodeList.item(i).getAttributes().getNamedItem("status").getNodeValue());
                }
             }   
            // Outputting to Excel spreadsheet
            FileOutputStream output = new FileOutputStream(new File("Result.xls"));
            wb.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
           System.out.println("IOException " + e.getMessage());
        } catch (ParserConfigurationException e) {
           System.out .println("ParserConfigurationException " + e.getMessage());
        } catch (SAXException e) {
           System.out.println("SAXException " + e.getMessage());
        }
     }

    @SuppressWarnings("rawtypes")
    public void writeResultToExcel(String testname, String Group,List verifications, String FileName) {
        // get the file name of the result excel file
        // Get the Last row number of the excel file
        // Write testcase name
        // write test group name if its set
        // Write the execution date and time
        // Write the result as pass if it passed in Green color
        // write the result as fail if its failed in red Color
    }

    /**
     * Description : This Method will highlight the elements in red in the web
     * page
     * 
     * @param driver
     * @param element
     * @throws Exception
     */
    public void highlightElement(WebDriver driver, WebElement element)
            throws Exception {
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "color: red; border: 3px solid red;");
            Thread.sleep(100);
            js.executeScript(
                    "arguments[0].setAttribute('style', arguments[1]);",
                    element, "");
        }
    }

    /*
     * Returns a random File name without any extension, 1-25 characters
     * */
    public static String randomFileName() {
        return RandomStringUtils.randomAlphanumeric(1 + new Random().nextInt(25));
    }
    
    /*
     * Create a file with random content, fileSizeInKB is optional, default = 1
     */
    public static void createFile(String path, String fileName,Integer... fileSizeInKB) {
        if (new File(path + File.separator + fileName).exists()) {
            FileUtils.deleteQuietly(new File(path + File.separator + fileName));
        }
        StringBuffer sb;
        int size =(fileSizeInKB.length == 0) ? DEFAULT_FILE_SIZE_KB * 1024: fileSizeInKB[0] * 1024;
        File fileNameWithPath = new File(path + File.separator + fileName);
        try {
            if (!new File(path).isDirectory())
                FileUtils.forceMkdir(new File(path));
            if (size <= 1024 * 100) {
                sb = new StringBuffer();
                while (sb.length() <= size) {
                    sb.append(RandomStringUtils.randomAscii(2 + new Random().nextInt(20))+ "\n");
                }
                FileUtils.writeStringToFile(fileNameWithPath, sb.toString());
            } else {
                int chunks = size / (1024 * 100);
                for (int i = 0; i < chunks; i++) {
                    sb = new StringBuffer();
                    while (sb.length() <= 1024 * 100) {
                        sb.append(RandomStringUtils.randomAscii(10 + new Random().nextInt(100))+ "\n");
                    }
                    FileUtils.writeStringToFile(fileNameWithPath,sb.toString(), true);
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating folder : " + path+ ", message : " + e.getMessage());
            e.printStackTrace();
            throw new SkipException("Skip test, folder creation failed");
        }
    }

    /*
     * Make a copy of an existing file.
     */
    public static void copyFile(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * The method creates multiple files with random names.
     */
    public static Set<String> createMultipleFiles(String path,
            String extension, int numberOfFiles, Integer... fileSizeInKB) {
        return createMultipleFiles("file", path, extension, numberOfFiles,
                fileSizeInKB);
    }

    /*
     * This method creates multiple files
     */
    public static Set<String> createMultipleFiles(String filename, String path,
            String extension, int numberOfFiles, Integer... fileSizeInKB) {
        Set<String> fileNames = new HashSet<String>();
        for (int i = 0; i < numberOfFiles; i++) {
            String fileName = filename + i + "." + extension;
            createFile(path, fileName, fileSizeInKB);
            fileNames.add(fileName);
        }
        return fileNames;
    }

    /*
     * Creates nested folders : path - folder name, depth - level of nested
     * folders. files - Count of files in each folder
     */
    public static void createNestedFolders(String path, int depth, int files) {
        try {
            if (!new File(path).isDirectory())
                FileUtils.forceMkdir(new File(path));
            String nextLevelPath = path;
            for (int i = 0; i < depth; i++) {
                nextLevelPath = path + File.separator + "folder_" + (i + 1);
                FileUtils.forceMkdir(new File(nextLevelPath));
                nextLevelPath = path + File.separator + "folder_" + (i + 1);
                path = nextLevelPath;
                for (int j = 0; j < files; j++) {
                    createFile(path, randomFileName() + ".txt");
                }
            }
        } catch (IOException e) {
            System.out.println("Error creating folder : " + path + ", message : " + e.getMessage());
            e.printStackTrace();
            throw new SkipException("Skip test, folder creation failed");
        }
    }

    // Creates nested folders, with a default depth = 5 and # of files = 5
    public static void createNestedFolders(String path) {
        createNestedFolders(path, 5, 5);
    }

    // Compares 2 files in the given path, returns true if the files are same.
    public static boolean compareFiles(String file1, String file2) {
        boolean result = false;
        try {
            Checksum csum1 = FileUtils.checksum(new File(file1), new CRC32());
            Checksum csum2 = FileUtils.checksum(new File(file2), new CRC32());
            result = csum1.getValue() == csum2.getValue() ? true : false;
        } catch (Exception e) {
            System.out.println("Error comparing files : " + file1 + " and "+ file2);
            e.printStackTrace();
        }
        System.out.println("Comparing files : " + file1 + " and " + file2 + ", result : " + result);
        return result;
    }

    // Use to create a file by passing the string content
    public static void createFile(String path, String fileName, String content,boolean append) {
        File fileNameWithPath = new File(path + File.separator + fileName);
        try {
            if (fileNameWithPath.exists()) {
                fileNameWithPath.delete();
            }
            FileUtils.writeStringToFile(fileNameWithPath, content, append);
        } catch (IOException e) {
            System.out.println("Error creating folder : " + path + ", message : " + e.getMessage());
            e.printStackTrace();
            throw new SkipException("Skip test, folder creation failed");
        }
    }

    /**
     * Captures screenshot of the screen under the folder path stored in
     * screenshotBaseFolder variable
     * 
     * @param fileName
     */
    public static void captureScreen(String folderPath, String fileName) {
       
        if (!new File(folderPath).isDirectory())
            try {
                FileUtils.forceMkdir(new File(folderPath));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        String filePath = folderPath + File.separator + fileName + ".jpg";
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRectangle = new Rectangle(screenSize);
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRectangle);
            ImageIO.write(image, "jpg", new File(filePath));
        } catch (Exception e) {
            System.out.println("Exception thrown while capturing screenshot '"
                    + filePath + "' : " + e.toString());
        }
    }

    /*
     * This method deletes the given directory
     */
    public static void deleteDirectory(String folderPath) {
        try {
            FileUtils.deleteDirectory(new File(folderPath));
        } catch (IOException e) {
            System.out.println("Exeception deleting folder : " + folderPath);
            e.printStackTrace();
        }
    }
    
    /*
     * This method reads the data from the file
     */
    public static String readFile(File filePath) {
        try {
            String fileContents = FileUtils.readFileToString(filePath);
            return fileContents;
        } catch (IOException exception) {
            System.out.println("Exeception reading file : " + filePath);
            exception.printStackTrace();
        }
        return null;
    }
    
    /*
     * This method gives the current date in MM/dd/yyyy format
     */
    public static String getCurrentDate(String format) {
        DateFormat dateFormat;
        if (format.equals("dd/MM/yyyy"))
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        else if (format.equals("MM/dd/yyyy"))
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        else
            dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // default format
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /*
     * This method is used to give the date with the given no.of days back 
     */
    public static String changeDate(int daysBack) {
        // Format supported by Last Modified Date Filter
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysBack);
        return dateFormat.format(cal.getTime());
    }
    
    /*
     * This method returns the last modified date in dd/MM/yyyy format 
     */
    public static void changeLastModifiedDate(String fileName, String newLastModifiedDate) {
        try {
            // Format supported by Last Modified Date Filter
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = sdf.parse(newLastModifiedDate);
            File file = new File(fileName);
            file.setLastModified(newDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
