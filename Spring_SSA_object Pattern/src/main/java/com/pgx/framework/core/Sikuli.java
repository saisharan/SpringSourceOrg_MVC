package com.pgx.framework.core;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Finder;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.testng.annotations.Test;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;



public class Sikuli {
	public static boolean findScreenshot(String strFilePath){
		boolean blnResults=false;
                Screen screen = new Screen();
		Pattern image = new Pattern(strFilePath);
		try {
			screen.find(image);
                        System.out.println("Image Found");
			blnResults=true;
		} catch (FindFailed e) {
			e.printStackTrace();
                        System.out.println("Image NOT Found");
			blnResults=false;
		}
		return blnResults;
        }
        public static void sikuliClick(String strFilePath){
                Screen screen = new Screen();
		Pattern image = new Pattern(strFilePath);
		try {
			screen.click(image);
		} catch (FindFailed ff) {
			ff.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
        public static void sikuliRightClick(String strFilePath){
                Screen screen = new Screen();
		Pattern image = new Pattern(strFilePath);
		try {
			screen.rightClick(image);
		} catch (FindFailed ff) {
			ff.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
        public static void sikuliSetText(String strFilePath,String strTexttoEnter){
                Screen screen = new Screen();
		Pattern image = new Pattern(strFilePath);
		try {
			screen.type(image, strTexttoEnter);
		} catch (FindFailed ff) {
			ff.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
}
