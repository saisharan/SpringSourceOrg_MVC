package com.pgx.framework.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import com.pgx.framework.ajax.Until;
import com.pgx.framework.exception.BrowserBotException;
import com.google.common.base.Function;

/**
 * A class representing a bot. Bot is an automation agent that executes commends
 * on the calling functions behalf. This method is advantageous while working on
 * a project with multiple developers, This brings in discipline while coding
 * 
 * Note : This is based on the automation framework developed in COS support
 * Automation (This can be considered as a fork) . However a lot of new
 * functionality has been added atop of this to enable this working in ajax rich
 * applications and reduce the code verbose
 * 
 * 
 */
public class WebDriverBase {

    protected WebDriver driver;
    private static final int TIMEOUT_IN_SECONDS = 120;
    private static final int POLL_INTERVAL = 500;
    private static final Logger LOGGER = Logger.getLogger(WebDriverBase.class);
    private static ChromeDriverService service = null;
    protected Properties settingProps;
    Logger log = Logger.getLogger(WebDriverBase.class);
    public String url;

    /**
     * 
     * A generic method wait is used to make the web driver to wait until a
     * specific event has occurred or until the timeout. It uses FluentWait to
     * wait for the particular condition. Each wait instance defines the maximum
     * amount of time to wait for a particular condition, as well as the
     * frequency with which to check the condition. Also, the user may configure
     * the wait to ignore specific types of exceptions while waiting, such as
     * NoSuchElementExceptions when searching for an element on the page.
     * 
     * This is an internal method
     * 
     * @param <U>
     * @param condition
     * @return
     */

    private <U> U wait_internal(ExpectedCondition<U> condition) {
        FluentWait<WebDriver> wait =new FluentWait<WebDriver>(this.driver).ignoring(
                        RuntimeException.class).withTimeout(TIMEOUT_IN_SECONDS,
                        TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL,
                        TimeUnit.MILLISECONDS);
        try {
            return wait.until(condition);
        } catch (TimeoutException err) {
            String errMessage = "Bot encountered a timeout while waiting for a condition,  "
                            + err.getLocalizedMessage();
            throw new BrowserBotException(errMessage);
        }
    }

    @SuppressWarnings("unused")
    private <U> U wait_internal(ExpectedCondition<U> condition,int timeoutInSeconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(
                        RuntimeException.class).withTimeout(timeoutInSeconds,
                        TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL,
                        TimeUnit.MILLISECONDS);
        try {
            return wait.until(condition);
        } catch (TimeoutException err) {
            String errMessage = "Bot encountered a timeout while waiting for a condition,  "
                            + err.getLocalizedMessage();
            throw new BrowserBotException(errMessage);
        }
    }

    public void init() {

    }

    /**
     * This is the exposed function to wait Until for a certain criteria
     * 
     * @param condition
     * @return
     */
    public Boolean wait(ExpectedCondition<Boolean> condition) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(this.driver).ignoring(
                        RuntimeException.class).withTimeout(TIMEOUT_IN_SECONDS,
                        TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL,
                        TimeUnit.MILLISECONDS);
        try {
            return wait.until(condition);
        } catch (TimeoutException err) {
            String errMessage ="Bot encountered a timeout while waiting for a condition, " + err.getLocalizedMessage();
            LOGGER.warn(errMessage);
            return false;
        } catch (Exception err) {
            String errMessage ="Bot encountered a timeout while waiting for a condition,  " + err.getLocalizedMessage();
            LOGGER.warn(errMessage);
            return false;
        }
    }

    /**
     * This is the exposed function to wait Until for a certain criteria until
     * the timeout
     * 
     * @param condition
     * @return
     */
    public Boolean wait(ExpectedCondition<Boolean> condition,int timeoutInSeconds) {
        FluentWait<WebDriver> wait =new FluentWait<WebDriver>(this.driver).ignoring(
                        RuntimeException.class).withTimeout(timeoutInSeconds,
                        TimeUnit.SECONDS).pollingEvery(POLL_INTERVAL,
                        TimeUnit.MILLISECONDS);
        try {
            return wait.until(condition);
        } catch (TimeoutException err) {
            String errMessage ="Bot encountered a timeout while waiting for a condition,  " + err.getLocalizedMessage();
            LOGGER.warn(errMessage);
            return false;
        } catch (Exception err) {
            String errMessage ="Bot encountered a timeout while waiting for a condition,  " + err.getLocalizedMessage();
            LOGGER.warn(errMessage);
            return false;
        }

    }

    /**
     * Method to making the driver to wait implicitly
     **/
    protected void implicitWait(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * clicks on the given web element which uses click method to click.
     * findElement method is used to locate the web element with the given
     * selector which will return the first matching element on the current
     * page.
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase clickOnElement(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        WebElement element = driver.findElement(locator);
        if (element.isDisplayed()) {
            element.click();
            log.info("Clicked on element: " + locator);
        }
        return this;
    }
     protected WebDriverBase actionClickOnElement(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        WebElement element = driver.findElement(locator);
        if (element.isDisplayed()) {
            Actions builder = new Actions(driver);
            builder.moveToElement(element).click(element);
            builder.perform();
        }
        log.info("Clicked on element: " + locator);
        return this;
    }
     protected WebDriverBase javaScriptClickOnElement(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        WebElement element = driver.findElement(locator);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].checked = true;", element);
        log.info("Clicked on element: " + locator);
        return this;
    }
     protected WebDriverBase sikuliClick(String imgFilePath) {
       Sikuli.sikuliClick(imgFilePath);
       return this;
    }
    /**
     * Method to click on the object using java script executor
     * 
     * returns web driver base
     */
    protected WebDriverBase clickOnElementJavavScript(String elementLocator)  {
        By locator = ElementLocator.getBySelector(elementLocator);
        WebElement element = driver.findElement(locator);
        if (element.isDisplayed()) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
               if(!EnvParameters.webBrowserType.contains("safari"))
                     jsExecutor.executeScript("arguments[0].click();",element);
               else
                   element.click();
               try{
                 Thread.sleep(10000);
               }
               catch(Exception e){
                   //gulp the exception
               }
               log.info("Clicked on element: " + locator);
        }
        return this;
    }

    /**
     * Method to return the current window handle
     * 
     * @return the current window handle
     */
    protected String getWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * clicks on the given web element which uses click method to click.
     * findElement method is used to locate the web element with the given
     * selector which will return the first matching element on the current
     * page. Suppose, if the element text is passed as an input instead of the
     * web element for this function then it will find the web element which has
     * this inner text and will click on that
     * 
     * @param elementLocatorthe key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @param elementText the text of the element
     * @return returns the BrowserBot instance
     **/
    protected WebDriverBase clickOnElementWithText(String elementLocator,String elementText) {
        By locator = ElementLocator.getBySelector(elementLocator);
        List<WebElement> elementList = driver.findElements(locator);
        int index = 0;
        for (WebElement element : elementList) {
            if (element.getText().trim().equalsIgnoreCase(elementText)&& element.isDisplayed()) {
                element.click();
                index++;
                break;
            }
        }
        if (index == elementList.size()) {
            throw new RuntimeException("Could not locate any element described by the locator "
                            + elementLocator.toString() + " with text "+ elementText);
        }
        return this;
    }

    /**
     * simulates typing into an element, which may set its value.
     * 
     * @param elementLocator is the key associated to Locator(LocatorType;LocatorValue)
     *  that specifies the selector
     * @param text the keys to send to the element
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase type(String elementLocator, String text) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Entering " + text + " into the " + locator + " text field");
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
        return this;
    }
    protected WebDriverBase specialHandlingType(String elementLocator, String text) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Entering " + text + " into the " + locator + " text field");
        WebElement element = driver.findElement(locator);
        element.clear();
        element.click();
        element.sendKeys(Keys.CONTROL,Keys.HOME);
        element.sendKeys("");
        element.sendKeys(text);
        //JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        //jsExecutor.executeScript("arguments[0].value=arguments[1];",element,text);
        return this;
    }

    /**
     * simulates typing into an element, which may set its value.
     * 
     * @param elementLocator is the key associated to Locator(LocatorType;LocatorValue)
     * that specifies the selector
     * @param text the keys to send to the element
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase type(String elementLocator, String text, Keys keys) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Entering " + text + " into the " + locator
                + " text field and pressing " + keys);
        WebElement element = driver.findElement(locator);
        element.sendKeys(text);
        element.sendKeys(keys);
        return this;
    }

    /**
     * navigates to a particular page with the given url. It uses driver get
     * method to load a new web page in the current browser window with the
     * given url
     * 
     * @param URL the URL where the browser has to navigate
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase goToUrl(String url) {
        log.info("Loading the URL:" + url);
        this.driver.get(url);
        return this;

    }

    /**
     * Retrieves the specified css attribute for a given web element.
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue) that
     *  specifies the by selector
     * @param attribute the attribute for the given element
     * @return String the attribute pertaining to the element
     */
    protected String getCssAttribute(String elementLocator, String attribute) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Getting CSS value of " + attribute + " from the locator " + locator);
        return driver.findElement(locator).getCssValue(attribute);
    }

    /**
     * Retrieves the specified attribute for a given web element
     * 
     * @param elementLocatorthe key associated to Locator(LocatorType;LocatorValue) that
     * specifies the by selector
     * @param attribute the attribute for the given element
     * @return String the attribute pertaining to the element
     */
    protected String getAttribute(String elementLocator, String attribute) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Getting CSS attribute of " + attribute + " from the locator "+ locator);
        return driver.findElement(locator).getAttribute(attribute);
    }

    /**
     * Submits the form via the specified element. If this current element is a
     * form, or an element within a form, then this will be submitted to the
     * remote server. If this causes the current page to change, then this
     * method will block until the new page is loaded
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the by selector
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase submit(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Submitting the Form");
        driver.findElement(locator).submit();
        return this;
    }

    /**
     * Retrieves the number of windows that is currently opened. It uses size
     * method of driver getWindowHandles method to get the count of the browser windows
     * @return int the count of browser windows
     */
    protected int getNumberOfOpenWindows() {
        return driver.getWindowHandles().size();
    }

    /**
     * navigate is an abstraction allowing the driver to access the browser's
     * history and to navigate to a given URL. navigateBack is used to navigate
     * to the previous page. It uses back method of driver navigate method for
     * this purpose
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase navigateBack() {
        driver.navigate().back();
        log.info("Navigating to the previous page");
        return this;
    }

    /**
     * navigate is an abstraction allowing the driver to access the browser's
     * history and to navigate to a given URL. navigateForward is used to
     * navigate to the next page. It uses forward method of driver navigate for
     * this purpose
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase navigateForward() {
        driver.navigate().forward();
        log.info("Navigating to the next page");
        return this;
    }

    /**
     * selectDropDown is used to select an option from the given drop down web
     * element. It will set the value based on the visible text provided
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue)
     * @param visibleText the option to select
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase selectValueFromDropDown(String elementLocator,String visibleText) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Selecting " + visibleText + " from the DropDown");
        WebElement dropDownElement = driver.findElement(locator);
        Select dropDownSelect = new Select(dropDownElement);
        dropDownSelect.selectByVisibleText(visibleText);
        return this;
    }
    /*
     * This method is to select value from dropdown
     */
    protected WebDriverBase selectValueFromDropDown(String elementLocator, int index) {
        By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Selecting " + index + " from the DropDown");
        WebElement dropDownElement = driver.findElement(locator);
        Select dropDownSelect = new Select(dropDownElement);
        dropDownSelect.selectByIndex(index);
        return this;
    }

    /**
     * method to perform the drag and drop action
     * 
     * @param fromElementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @param toElementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase dragAndDrop(String fromElementLocator,String toElementLocator) {
        By fromLocator = ElementLocator.getBySelector(fromElementLocator);
        By toLocator = ElementLocator.getBySelector(toElementLocator);
        Actions action = new Actions(this.driver);
        action.dragAndDrop(driver.findElement(fromLocator),driver.findElement(toLocator)).build().perform();
        return this;
    }

    /**
     * Forces the refresh operation. It will load the driver instance in the
     * Actions class and then will perform the refresh operation
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase forceRefresh() {
        Actions action = new Actions(this.driver);
        log.info("Forcefully refreshing the page");
        action.keyDown(Keys.CONTROL).sendKeys(Keys.F5).keyUp(Keys.CONTROL).perform();
        return this;
    }

    /**
     * Method to perform the hover over action on the given element
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase hoverOver(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        @SuppressWarnings("unused")
        Actions action = new Actions(this.driver);
        log.info("Hovering over the mouse on the element " + locator);
        WebElement element = driver.findElement(locator);
        action.moveToElement(element).build().perform();
        return this;
    }

    /**
     * Method to press the key. It uses sendKeys method to send the keys to the
     * given web element
     * 
     * @param elementLocator the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @param key the keys to send to the element
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase pressKey(String elementLocator, Keys key) {
        By locator = ElementLocator.getBySelector(elementLocator);
        driver.findElement(locator).sendKeys(key);
        return this;
    }

    /**
     * A helper method to see if the element is enabled . An Element might be
     * present in a hTML page However it might not be enabled We use this method
     * to check and see if the element is enabled or not
     * 
     * @param elementSelector the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the selector
     * @return returns true if the element is enabled
     */

    protected Boolean isElementEnabled(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        return driver.findElement(locator).isEnabled();
    }

    /**
     * switches the focus to the window based on the title. It will get all the
     * window handles using driver getWindowHandles method then focus to the
     * corresponding window with the given title
     * 
     * @param titleOfNewWindow the string that specifies the title of the window
     * @return returns the BrowserBot instance
     */

    protected WebDriverBase switchToWindowByTitle(String titleOfNewWindow) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().contains(titleOfNewWindow)) {
                break;
            }
        }
        return this;
    }

    /**
     * Returns focus to the main browser window by using defaultContent method
     * of the driver switchTo method
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase switchToDefaultContent() {
        driver.switchTo().defaultContent();
        return this;
    }

    /**
     * Refreshes the page
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase refreshPage() {
        driver.navigate().refresh();
        return this;
    }

    /**
     * Performs double click on an element. Action and Actions classes in
     * interactions are used
     * 
     * @param elementSelector
     *            the key associated to Locator(LocatorType;LocatorValue) thatspecifies the selector
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase doubleClick(String elementLocator) {
        By locator = ElementLocator.getBySelector(elementLocator);
        Actions builder = new Actions(driver);
        WebElement element = driver.findElement(locator);
        Action hoverOverRegistrar = (Action) builder.doubleClick(element);
        hoverOverRegistrar.perform();
        return this;
    }

    /**
     * Deletes all the cookies. It uses deleteAllCookies of driver manage method
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase clearCookies() {
        this.driver.manage().deleteAllCookies();
        return this;
    }

    /**
     * Returns the displayed element from a list of similarly named elements by
     * getting the element locator as the input value
     * 
     * @param elementLocator
     *            the key associated to Locator(LocatorType;LocatorValue) that specifies the selector
     * @return returns the displayed element
     */
    @SuppressWarnings("unused")
    protected WebElement getDisplayedElement(String elementLocator) {
        log.info("Finding the displayed Element for the locator provided--"+ elementLocator);
        By locator = ElementLocator.getBySelector(elementLocator);
        List<WebElement> elementList = findElements(locator);
        for (WebElement element : elementList) {
            if (element.isDisplayed())
                return element;
        }
        throw new BrowserBotException("Element not found--" + elementLocator+ " displayed");
    }


    /**
     * Gets a string representing the current URL of the page which is currently
     * loaded in the browser
     * 
     * @return String the current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Retrieves the size for the specified element.
     * 
     * @param propKey
     *            the key associated to Locator(LocatorType;LocatorValue) that specifies the by selector
     * @return int the size for the element
     */
    protected int getListSize(String propKey) {
        By locator = ElementLocator.getBySelector(propKey);
        return findElements(locator).size();
    }

    /**
     * Finds all the Web Elements that matches the given by selector
     * 
     * @param by
     *            By object to identify the element(s)
     * @return List<WebElement> the list of web elements that is found
     * @throws SupportException
     *             if no element is found
     */
    private List<WebElement> findElements(By by) throws BrowserBotException {
        try {
            return driver.findElements(by);
        } catch (NoSuchElementException e) {
            String msg = "Element could not be located " + by.toString();
            log.info(msg);
            throw new BrowserBotException(msg);
        }
    }

    /**
     * Closes the current pop up window
     * 
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase closePopupWindow() {
        driver.close();
        for (String name : driver.getWindowHandles()) {
            driver.switchTo().window(name);
            log.info("popup window closed : " + name);
            break;
        }
        return this;
    }

    /**
     * Closes the given pop up window without switching to any other window
     * 
     * @param windowId
     *            the window id that should be closed
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase closePopupWindow(String windowID) {
        driver.switchTo().window(windowID).close();
        return this;
    }

    /**
     * Closes the given popup window and switches to random window
     * 
     * @param windowID
     *            the window id that should be closed
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase closepopUpAndSwitchtoParent(String windowID) {
        closePopupWindow(windowID);
        for (String name : driver.getWindowHandles()) {
            driver.switchTo().window(name);
            break;
        }
        return this;
    }

    /**
     * Determines if a specific element is present
     * 
     * @param propKey
     *            the key associated to Locator(LocatorType;LocatorValue) that specifies the by selector
     * @return boolean true/false if element is found/not found
     */
    protected boolean isElementPresent(String propKey) {
        By locator = ElementLocator.getBySelector(propKey);
        log.debug("Checking the presence of the Element: " + propKey + " : "+ propKey);
        return isElementPresent(locator);
    }

    /**
     * Determines if a specific element is visible
     * @param propKeyx the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the by selector
     * @return boolean true/false if element is found/not found
     */
    protected boolean isElementVisible(String propKey) {
        By locator = ElementLocator.getBySelector(propKey);
        log.debug("Checking the presence of the Visble: " + propKey + " : "+ propKey);
        return isElementVisible(locator);
    }

    /**
     * Determines if a specific element is visible
     * @param propKey the key associated to Locator(LocatorType;LocatorValue) that
     * specifies the by selector
     * @return boolean true/false if element is found/not found
     */
    protected boolean isElementChecked(String propKey) {
        By locator = ElementLocator.getBySelector(propKey);
        log.debug("Checking the presence of the Visble: " + propKey + " : " + propKey);
        return isElementChecked(locator);
    }

    /**
     * Determines if an element is present with the given by selector
     * 
     * @param by
     *            By locator to find the element
     * @return boolean true/false if element is found/not found
     */
    private boolean isElementPresent(By by) {
        try {
            WebElement element = driver.findElement(by);
            if (element != null) {
                log.debug("Element is present: " + by.toString());
                return true;
            }
            log.warn("Element is NOT present: " + by.toString());
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Determines if an element is present with the given by selector
     * 
     * @param by
     *            By locator to find the element
     * @return boolean true/false if element is found/not found
     */
    private boolean isElementChecked(By by) {
        try {
            WebElement element = driver.findElement(by);
            if (element.isSelected()) {
                log.debug("Element is checked: " + by.toString());
                return true;
            }
            log.warn("Element is NOT checked: " + by.toString());
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Determines if an element is visible with the given by selector
     * 
     * @param by
     *            By locator to find the element
     * @return boolean true/false if element is found/not found
     */
    private boolean isElementVisible(By by) {
        try {
            WebElement element = driver.findElement(by);
            if (element.isDisplayed()) {
                log.debug("Element is present: " + by.toString());
                return true;
            }
            log.warn("Element is NOT present: " + by.toString());
            return false;
        } catch (NoSuchElementException e) {
            return false;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            return false;
        }
    }

    /**
     * Method to switch to a parent window. It will get all the window handles
     * and will iterate over the handles and then focus the particular window
     * checking that window is not the parent window
     * 
     * @param parentWindowId
     *            the id of the parent window
     * @return returns the BrowserBot instance
     * 
     */
    protected WebDriverBase switchToParentWindow(String parentWindowId) {
        String windowId = "";
        Set<String> set = driver.getWindowHandles();
        log.info("Number of windows opened: " + set.size());
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            windowId = (String) iterator.next();
            if (windowId.equals(parentWindowId)) {
                log.info("Switching to the window: " + parentWindowId);
                driver.switchTo().window(parentWindowId);
            }
            log.info("windowId" + windowId);
        }
        return this;
    }

    /**
     * Method for selecting multiple options from the list box. It uses Actions
     * class for keyDown and keyUp methods
     * 
     * @param listBoxLocator
     *            the locator of the list box
     * @param indexes
     *            the indexes of all the items
     * @return returns the BrowserBot instance
     */
    protected WebDriverBase selectMultipleListItems(String listBoxLocator,int[] indexes) {
        By locator = ElementLocator.getBySelector(listBoxLocator);
        Actions action = new Actions(this.driver);
        WebElement listItem = driver.findElement(locator);
        List<WebElement> listOptions =listItem.findElements(By.tagName("option"));
        action.keyDown(Keys.CONTROL).perform();
        for (int i : indexes) {
            listOptions.get(i).click();
        }
        action.keyUp(Keys.CONTROL).perform();
        return this;
    }

    /**
     * Switch to the pop up window by getting parent window as the input value
     * It gets the window handles and compares it with parent window to select
     * the new pop up window other than parent window
     * 
     * @param ID
     *            of the parent window
     * @return the instance of the BrowserBot class
     */
    protected WebDriverBase switchToPopUpWindow(String parentWindowId) {
        String windowId = "";
        Set<String> set = driver.getWindowHandles();
        log.info("Number of windows opened: " + set.size());
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            windowId = (String) iterator.next();
            if (!windowId.equals(parentWindowId)) {
                log.info("Switching to the window: " + parentWindowId);
                driver.switchTo().window(windowId);
            }
            LOGGER.info("Popup windowId" + windowId);
        }
        return this;
    }

    /**
     * Method for switching to the frame of the given frame id
     * 
     * @param frameId
     * @return instance of the BrowserBot class
     */
    protected WebDriverBase switchToFrame(int frameId) {
        log.info("Switching to the frame: " + frameId);
        driver.switchTo().frame(frameId);
        return this;
    }

    protected WebDriverBase switchToFrame(String frameName) {
        log.info("Switching to the frame: " + frameName);
        driver.switchTo().frame(frameName);
        return this;
    }

    /**
     * gets the visible inner text of the specified web element, including
     * sub-elements,without any leading or trailing whitespace
     * 
     * @param elementLocator
     *            the key associated to Locator(LocatorType;LocatorValue) that specifies the selector
     * @return the text of the given element
     */
    protected String getText(String elementLocator) {
        String strvalue="";
        try{
            By locator = ElementLocator.getBySelector(elementLocator);
            System.err.println("Getting text from the element");
            log.info("Getting the text of the element: " + locator);
            System.err.println(this.driver.findElement(locator).getText());
            strvalue=this.driver.findElement(locator).getText();
        }
        catch(Exception e ){
            System.err.println("Exception:"+e);
        }
        return strvalue;
    }

    /**
     * Returns the text from the alert window
     * 
     * @return the text of the alert
     */
    protected String getAlertText() {
        log.info("Getting the Alert text");
        return driver.switchTo().alert().getText();

    }

    /**
     * Accepts the alert window
     */
    protected void acceptAlert() {
        log.info("Confirming the operation");
        driver.switchTo().alert().accept();
    }

    /**
     * Dismisses the alert window
     */
    protected void dismissAlert() {
        log.info("Cancelling the operation");
        driver.switchTo().alert().dismiss();
    }

    /**
     * Executes the JavaScript of the given script using the object of
     * JavaScriptExecutor and returns the resulting text The executeScript
     * method of JavaScriptExecutor is used
     * 
     * @param Javascript
     *            that is to be executed
     * @return text
     */
    protected String executeJavaScript(String script) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        String text = (String) jsExecutor.executeScript(script);
        return text;
    }

    /**
     * Gets the title of the current window.
     * 
     * @return String the title of the window
     */
    protected String getTitle() {
        log.info("Getting the title of the page");
        return driver.getTitle();
    }

    /**
     * This is a helper function/class to determine if a page is loaded
     * properly, We use the document.readystate to determine if the page is
     * loaded correctly
     * 
     * This prevents the webdriver from doing automation when a page "component"
     * is loaded partially.Without this the automation will be unreliable. This
     * is analogous to the selenium.waitforPageToLoad, Except that this prints a
     * warning message and tries to continue with automation when the timeout
     * expires
     * 
     * @return Boolean value indicating if the page has loaded or not
     */
    protected Boolean isPageLoaded() {
        final String javaScriptFunction = "function f(){return document.readyState;} return f();";

        Function<WebDriver, Boolean> condition =
                new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver d) {
                        String result =(String)((JavascriptExecutor) d).executeScript(javaScriptFunction);
                        LOGGER.debug("The page is in " + result + " state");
                        if (result.equalsIgnoreCase("complete"))
                            return true;
                            return false;
                        }
                };
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(2,TimeUnit.SECONDS).withTimeout(180, TimeUnit.SECONDS).ignoring(RuntimeException.class);
        try {
            return wait.until(condition);
        }
        catch (TimeoutException err) {
            LOGGER.warn("THE PAGE IS NOT LOADED EVEN AFTER THE TIMEOUT, OF 180 SECONDS"
                    + "THIS COULD MEAN THAT THE AUTOMATION MIGHT FAIL OR BE UNRELIABLE");
            return false;
        }
    }

    /**
     * Quits the web driver object and closes the every associated window
     */
    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
            // Stop the chrome driver service if needed
            if (service != null) {
                service.stop();
            }
            LOGGER.debug("The web driver is quit successfully");
        }
    }

    /**
     * Returns the windowset containing all the opened window handles
     * 
     * @return The set of windows in the windowhandle list
     */
    protected Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    /**
     * Gets the current window title
     * 
     * @return the current window title
     */
    protected String getWindowTitle() {
        return driver.getTitle();
    }

    /**
     * Switches to window having the specified element
     * 
     * @param elementLocator the locator for the element
     * @return True if switch is performed. False if no window with the expected element is found
     */
    protected boolean switchToWindowWithElement(String elementLocator) {
        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
            if (isElementPresent(elementLocator))
                return true;
        }
        return false;
    }

    /**
     * Switches to window having the specified url part
     * 
     * @param URLPart
     *            the part of url to match
     * @return True if switch is performed. False if no window with the expected url part is found
     */
    protected boolean switchToWindowWithURLPart(String URLPart) {
        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
            if (getCurrentUrl().contains(URLPart))
                return true;
        }
        return false;
    }

    /**
     * 
     * Get the text of similarly named elements
     * 
     * @param elementLocator
     * @return
     */
    protected List<String> getTextOfSimilarElements(String elementLocator) {
        List<WebElement> elementList = wait_internal(Until.elementsDisplayed(elementLocator));
        List<String> elementTextList = new ArrayList<String>();
        LOGGER.debug("There are " + elementList.size()+ " Similar elements(Element with locator " + elementLocator+ " )");
        for (WebElement anElement : elementList) {
            elementTextList.add(anElement.getText());
        }
        return elementTextList;
    }

    protected String getPageSource() {
        return this.getPageSource();
    }

    /**
     * Clear the specified text box.
     * 
     * @param propKey
     *            the key associated to Locator(LocatorType;LocatorValue) that specifies the by selector
     * @return the instance of the BrowserBot class
     */
    protected WebDriverBase clearTextBox(String elementLocator) {
        wait_internal(Until.elementIsInteractable(elementLocator)).clear();
        return this;
    }

    /**
     * Method for capturing the screen
     * 
     * @return File
     */
    protected File captureScreen() {
        File srcFile = null;
        // If we run on standalone mode , Grab the screenshot
        try {
            srcFile =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        }
        // we run grid , Use a augmenter to capture the screenshot. This is a
        // bit unstable , That is why the Augmenter is placed last
        catch (ClassCastException err) {
            srcFile =((TakesScreenshot) new Augmenter().augment(driver)) .getScreenshotAs(OutputType.FILE);
        } catch (Exception err) {
            LOGGER.error("There was an error while capturing the screenshot ..");
            LOGGER.error(err.getMessage());
        }
        return srcFile;
    }
    public void pageLoadCheck(){
        if(!EnvParameters.webBrowserType.toLowerCase().contains("safari")){
            if(!EnvParameters.pageLoadTime.equals(""))
               driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(EnvParameters.pageLoadTime), TimeUnit.SECONDS);
            else
               driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        }
    }
}
