package com.pgx.framework.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

public class CookiesUtilities {
	/**
	 ***************** Creation History ******************
	 *     Method Name		: deleteNamedCookie
	 *     Description              : Deletes the cookie from the page which is passed through as parameter
	 *     Input ParameterList	: @param strCookieName,driver
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static void deleteNamedCookie(WebDriver driver,String strCookieName){
		driver.manage().deleteCookieNamed(strCookieName);
	}
	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: deleteAllCookies
	 *     Description              : Deletes the all cookie from the page
	 *     Input ParameterList	: @param driver
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static void deleteAllCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
	}
	
	/**
	 ***************** Creation History ******************
	 *     Method Name		: addCookie
	 *     Description              : Deletes the all cookie from the page
	 *     Input ParameterList	: @param driver,strCookieValue,strCookieName
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static void addCookie(WebDriver driver,String strCookieName,String strCookieValue){
		Cookie cookie = new Cookie(strCookieName, strCookieValue);
	    driver.manage().addCookie(cookie);
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: getAllCookies
	 *     Description              : gets the all cookies from the page
	 *     Input ParameterList	: @param driver
	 *     Return Type             	: Set
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static Set getAllCookies(WebDriver driver){
		return driver.manage().getCookies();
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: validateCookieNames
	 *     Description              : validates the cookie names passed through 
	 *     Input ParameterList	: @param driver, strCookienames[]
	 *     Return Type             	: boolean
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static boolean validateCookieNames(WebDriver driver,String[] strCookienames){
	    List<String> strActualCookieValues = new ArrayList<String>();
            Set<Cookie> availableCookies = driver.manage().getCookies();
            Iterator<Cookie> iter= availableCookies.iterator();
            while(iter.hasNext()){
                            Cookie C = iter.next();
                            strActualCookieValues.add(C.getName());
            }
	    if(Arrays.equals(strCookienames, strActualCookieValues.toArray()))
	    	return true;
	    else
	    	return false;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: validateCookievalues
	 *     Description              : validates the cookie values passed through 
	 *     Input ParameterList	: @param driver
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static boolean validateCookievalues(WebDriver driver,String[] strCookievalues){
		List<String> strActualCookieValues = new ArrayList<String>();
                Set<Cookie> availableCookies = driver.manage().getCookies();
                Iterator<Cookie> iter= availableCookies.iterator();
                while(iter.hasNext()){
                                Cookie C = iter.next();
                                strActualCookieValues.add(C.getName());
                }
                if(Arrays.equals(strCookievalues, strActualCookieValues.toArray()))
                    return true;
                else
                    return false;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: validateCookie
	 *     Description              : validates the cookie values passed through 
	 *     Input ParameterList	: @param driver,strCookieName,strCookieValue
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static boolean validateCookie(WebDriver driver,String strCookieName,String strCookieValue){
		Cookie cookie = new Cookie(strCookieName, strCookieValue);
		Set<Cookie> cookies = new HashSet<Cookie>();
                cookies.add(cookie);
                if(driver.manage().getCookies().containsAll(cookies))
                    return true;
                else
                    return false;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: validatecontainsCookies
	 *     Description              : validates the cookies passed through are present in the page 
	 *     Input ParameterList	: @param driver,strCookieName[],strCookieValue[]
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static boolean validatecontainsCookies(WebDriver driver,String[] strCookieName,String[] strCookieValue){
		Set<Cookie> cookies = new HashSet<Cookie>();
		for(int i=0;i<strCookieName.length;i++){
			cookies.add(getCookie(strCookieName[i], strCookieValue[i]));
		}
                if(driver.manage().getCookies().contains(cookies))
                    return true;
                else
                    return false;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: validateExaxtCookies
	 *     Description              : validates the cookies passed through are present in the page 
	 *     Input ParameterList	: @param driver,strCookieName[],strCookieValue[]
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static boolean validateExaxtCookies(WebDriver driver,String[] strCookieName,String[] strCookieValue){
		Set<Cookie> cookies = new HashSet<Cookie>();
		for(int i=0;i<strCookieName.length;i++){
			cookies.add(getCookie(strCookieName[i], strCookieValue[i]));
		}
                if(driver.manage().getCookies().equals(cookies))
                    return true;
                else
                    return false;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: getCookie
	 *     Description              : returns the cookie with the given name and value
	 *     Input ParameterList	: @param Cookie
	 *     Return Type             	: void
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static Cookie getCookie(String strCookieName, String strCookieValue){
		Cookie cookie = new Cookie(strCookieName, strCookieValue);
		return cookie;
	}
	/**
	 ***************** Creation History ******************
	 *     Method Name		: getCookieValue
	 *     Description              : returns the cookie value with the given Cookie name
	 *     Input ParameterList	: @param strCookieName,driver
	 *     Return Type             	: String
	 *     Class Name              	: CookiesValidations
	 *     Package Name             : com.pgx.framework.core
	 *     Created By             	: Nainappa Illi
	 *     Created On              	: July 09, 2014
	 ***************** Revision History ******************
	 *     Revised By             	: <Revision Author>
	 *     Revised On              	: <Date>
	 *     Revision Description	: <Multi � Line Entry >
	 ***************** End of Header 
	 */
	public static String getCookieValue(WebDriver driver,String strCookieName){
		String strCookieValue="";
		Set<Cookie> availableCookies = driver.manage().getCookies();
                Iterator<Cookie> iter= availableCookies.iterator();
                while(iter.hasNext()){
                                Cookie C = iter.next();
                                if(C.getName().equals(strCookieName)){
                                        strCookieValue=C.getValue();
                                        break;
                                }
                }
                LoggerUtil.log(strCookieValue);
                return strCookieValue;
	}
}
