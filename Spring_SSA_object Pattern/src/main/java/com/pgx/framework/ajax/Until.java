package com.pgx.framework.ajax;

import com.google.common.base.Function;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.pgx.framework.core.ElementLocator;
import com.pgx.framework.core.EnvParameters;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 * 
 * Class with Canned Conditions to wait until .Portion of this is already
 * available in the ExpectedConditions in webdriver. This was needed because
 * this wait is will be exposed to the user and we should not expose the web
 * element to the user. Also some of the classes in the canned
 * ExcepectedConditions do not work correctly
 * 
 * 
 * 
 */
public class Until {

    static Logger log = Logger.getLogger(Until.class);

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<List<WebElement>> elementsDisplayed(
            final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator + " is displayed");
        return ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
    }

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<WebElement> elementIsInteractable(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator
                + " becomes interactable");
        return ExpectedConditions.elementToBeClickable(locator);
    }

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<Boolean> desiredElementDisplayed(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return d.findElement(locator).isDisplayed();
                } catch (NoSuchElementException err) {
                    System.out.println(err.getMessage());
                    return true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    return false;
                }
            }
            @Override
            public String toString() {
                return " Until the Web element " + locator.toString()
                        + " is Visible";
            }
        };
    }

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<Boolean> elementIsEnabled(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator + " is enabled");
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return d.findElement(locator).isEnabled();
                } catch (NoSuchElementException err) {
                    System.out.println(err.getMessage());
                    return true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    return false;
                }
            }
            @Override
            public String toString() {
                return " Until the Web element " + locator.toString()
                        + " is Visible";
            }
        };
    }

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<Boolean> elementIsDisplayed(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator + " is displayed");
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return d.findElement(locator).isDisplayed();
                } catch (NoSuchElementException err) {
                    System.out.println(err.getMessage());
                    return true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    return false;
                }
            }
            @Override
            public String toString() {
                return " Until the Web element " + locator.toString()
                        + " is Visible";
            }
        };
    }

    /**
     * 
     * @param elementLocator
     * @return
     */
    public static ExpectedCondition<Boolean> elementIsInvisible(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator + " becomes invisible");
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return !d.findElement(locator).isDisplayed();
                } catch (NoSuchElementException err) {
                    System.out.println(err.getMessage());
                    return true;
                } catch (Exception err) {
                    System.out.println(err.getMessage());
                    return false;
                }
            }
            @Override
            public String toString() {
                return " Until the Web element " + locator.toString()
                        + " is Visible";
            }
        };
    }

    /**
     * wait until a new window opens up
     * 
     * @param numberOfCurrentWindowsOpened
     * @return
     */
    public static ExpectedCondition<Boolean> newWindowOpens(
            final int numberOfCurrentWindowsOpened) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getWindowHandles().size() >= numberOfCurrentWindowsOpened;
            }
        };
    }

    /**
     * wait until a CSS property changes
     * 
     * @param elementLocator
     * @param attribute
     * @param newProperty
     * @return
     */
    public static ExpectedCondition<Boolean> cssPropertyChangesTo(final String elementLocator, final String attribute,final String expectedProperty) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element's " + locator + " css property "
                + attribute + " is changed to " + expectedProperty);
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    WebElement ele = d.findElement(locator);
                    if (ele != null && ele.isDisplayed()) {
                        System.out.println(ele.getCssValue(attribute));
                        return (ele.getCssValue(attribute).trim()
                                .contains(expectedProperty));
                    } else {
                        return false;
                    }
                } catch (StaleElementReferenceException err) {
                    return false;
                }
            }
            @Override
            public String toString() {
                return "Until the css attribute" + expectedProperty
                        + " of element " + locator.toString() + "changes to "
                        + expectedProperty;
            }
        };
    }

    /**
     * wait until an expected text is displayed
     * 
     */
    public static ExpectedCondition<Boolean> textIsDisplayed( final String elementLocator, final String expectedText) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the text " + expectedText
                + " is displayed on the element" + locator);
        return ExpectedConditions.textToBePresentInElement(locator, expectedText);
    }

    /**
     * 
     * @param expectedParam
     * @return
     */
    public static ExpectedCondition<Boolean> urlContainsParam(
            final String expectedParam) {
        log.info("Waiting until the URL contains the string: " + expectedParam);
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(expectedParam);
            }
            @Override
            public String toString() {
                return "Until the URL Contains the param : " + expectedParam;
            }
        };
    }

    public static ExpectedCondition<Boolean> titleIs(final String expectedPageTitle) {
        return ExpectedConditions.titleContains(expectedPageTitle);
    }

    public static ExpectedCondition<Boolean> elementIsVisible(final String elementLocator) {
        final By locator = ElementLocator.getBySelector(elementLocator);
        log.info("Waiting until the element " + locator + " becomes visible");
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                try {
                    return d.findElement(locator).isDisplayed();
                } catch (NoSuchElementException err) {
                    return false;
                } catch (Exception err) {
                    return false;
                }
            }
            @Override
            public String toString() {
                return "Until the element " + locator.toString()
                        + " is Visible";
            }
        };
    }
    public static void explicitWait(WebDriver driver,By by){  
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)  
                 .withTimeout(20, TimeUnit.SECONDS)  
                 .pollingEvery(2, TimeUnit.SECONDS)  
                 .ignoring(NoSuchElementException.class); 

         WebElement element= wait.until(new Function<WebDriver, WebElement>() {  
               public WebElement apply(WebDriver driver) {  
                 return driver.findElement(By.id("foo"));  
                }  
          });
    }
}
