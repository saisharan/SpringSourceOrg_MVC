package com.pgx.framework.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import java.io.FileInputStream;
import java.util.Properties;


public class DBManipulation {
    private static final String PROP_FILE = "SQLDump.properties";
	/**
	 ***************** Creation History ******************
	 *     Method Name		: executeAndStoreDBData
	 *     Description              : Execute query and store values in global variables
	 *     Input ParameterList	: @param strQueryFilepath
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static void executeAndStoreDBData(String strQueryFilepath) throws FileNotFoundException, IOException, ClassNotFoundException {
		String  strServerName;
		String   strResult,  strQuery;
		Connection objSQLConnection;
		try {
			strQuery = readTextFile(new File(strQueryFilepath));
			strServerName = "SQL_AuditPro";
			objSQLConnection = DBManipulation.establishConnection(strServerName);
			strResult = DBManipulation.executeSQLDBQuery(objSQLConnection, strQuery);

			if (strResult.equals("")) {
				//Given query didn't fetch any values
			}
			String[] strArrTempRes = strResult.split("~");
			String[] strArrFinRes = strArrTempRes[0].split(",");
		} catch (Exception e) {
			//gulp the exception
		}
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: readTextFile
	 *     Description              : To read a text file
	 *     Input ParameterList	: @param aFile
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static String readTextFile(File aFile) {
		StringBuilder contents = new StringBuilder();
		String s = null;
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
				s = contents.toString();
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return s;
	}
        /**
	 ***************** Creation History ******************
	 *     Method Name		: getPropertyValue
	 *     Description              : Fetches the queries from sqldump.
	 *     Input ParameterList	: @param String - propertyname
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Anbarasan
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
        public static String getPropertyValue(String propertyname) {
        String testRoot;
        String Propertyvalue = null;
        Properties properties = new Properties();
        testRoot = System.getProperty("user.dir")+File.separator+"Resources";
            try {
                FileInputStream in =
                        new FileInputStream(testRoot + File.separator + PROP_FILE);
                properties.load(in);
                in.close();
                Propertyvalue = properties.getProperty(propertyname);
            } catch (IOException e) {
                System.err.println("Failed to read from " + testRoot
                        + File.separator + PROP_FILE + " file.");
            }
            return Propertyvalue;
        }

	/**
	 ***************** Creation History ******************
	 *     Method Name              : executeSQLDBQuery
	 *     Description              : executes DBQuery and returns a string value
	 *     Input ParameterList	: @param objConnection - Connection Object
	 *     	Input ParameterList	: @param strQueryFilePath - .sql file path with extension
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static String executeSQLDBQuery(Connection objConnection, String strQuery) {
		Statement objSQLStatement;
		String strPlaceHolder = ",";
		String strResultContent = "";
		ResultSet objSQLResultSet;
		ResultSetMetaData objSQLRSMetaData;
		int intColumnCount;
		try {
			objSQLStatement = objConnection.createStatement();
			objSQLResultSet = objSQLStatement.executeQuery(strQuery);
			objSQLRSMetaData = objSQLResultSet.getMetaData();
			intColumnCount = objSQLRSMetaData.getColumnCount();
			while (objSQLResultSet.next()) {
				for (int intLpCnt = 1; intLpCnt <= intColumnCount; intLpCnt++) {
					strResultContent = strResultContent + strPlaceHolder + objSQLResultSet.getString(intLpCnt);
					strPlaceHolder = ",";
				}
				strResultContent = strResultContent + "~";
				strPlaceHolder = "";
			}
			if (!strResultContent.equalsIgnoreCase(""))
				strResultContent = strResultContent.substring(1, strResultContent.length() - 1);
			objSQLResultSet.close();
			closeConnection(objSQLResultSet, objSQLStatement, objConnection);
			return strResultContent;
		} catch (Exception ex) {
			return "";
		}
	}
	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: executeSQLDBQuery
	 *     Description              : executes DBQuery and returns a string value
	 *     Input ParameterList	: @param objConnection - Connection Object
	 *     	Input ParameterList	: @param strQueryFilePath - .sql file path with extension
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static String executeSQLDBQueryFile(String strServerName, String strQueryFilePath) throws UnknownHostException {
			Connection objSQLConnection;
			String strResultSet, strQuery,strModifiedQuery;
			objSQLConnection = DBManipulation.establishConnection(strServerName);
			strQuery = readTextFile(new File(strQueryFilePath));
			strResultSet = DBManipulation.executeSQLDBQuery(objSQLConnection, strQuery);
			return strResultSet;
	}
	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: executeSQLDBQuery
	 *     Description              : executes DBQuery and returns a string value
	 *     Input ParameterList	: @param objConnection - Connection Object
	 *     	Input ParameterList	: @param strQueryFilePath - .sql file path with extension
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static int executeSQLDBStoredProcedure(Connection objConnection, String strQuery) throws SQLException {
		try {
			Thread.sleep(1000);
			CallableStatement cs = objConnection.prepareCall(strQuery);
			System.out.println("strQuery" + strQuery);
			int intExecutionStatus = cs.executeUpdate();
			return intExecutionStatus;
		} catch (Exception e) {
			return -1;
		}
	}

	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: establishConnection
	 *     Description              : establishes a connection between specified db server (sql)
	 *     Input ParameterList	: @param objConnection - Connection Object
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static Connection establishConnection(String strConnectionInfo)  {
		Connection objConnection = null;
		String strDriverInfo,strUserName = null,strPassword = null;
		try {
			strDriverInfo = "com.mysql.jdbc.Driver";
			Class.forName(strDriverInfo);
			objConnection = DriverManager.getConnection(strConnectionInfo);
			return objConnection;
		} catch (Exception objException) {
			//gulp the exception
		}
		return objConnection;
	}
	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: establishConnection
	 *     Description              : establishes a connection between specified db server (sql)
	 *     Input ParameterList	: @param objConnection - Connection Object
	 *     Return Type             	: void
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 07, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static void closeConnection(ResultSet objSQLResultSet, Statement objSQLStatement, Connection objSQLConnection) {
		try {
			if (objSQLResultSet != null)
				objSQLResultSet.close();
		} catch (Exception e) {
		}
		try {
			if (objSQLStatement != null)
				objSQLStatement.close();
		} catch (Exception e) {
		}
		try {
			if (objSQLConnection != null)
				objSQLConnection.close();
		} catch (Exception e) {
		}
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: replaceValueForVariableInSQL
	 *     Description              : establishes a connection between specified db server (sql)
	 *     Input ParameterList	: @param strSQLQuery - Connection Object
	 *     Return Type             	: String
	 *     Class Name              	: DBManipulation
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
        public static String replaceValueForVariableInSQL(String strSQLQuery,String strReplace) throws UnknownHostException {
            if (StringUtils.containsIgnoreCase(strSQLQuery, "<temp_value>")) {
                        String strRowNumber = null;
                        strSQLQuery = strSQLQuery.replaceAll("<temp_value>",strReplace);
                }
                return strSQLQuery;
        }
}

