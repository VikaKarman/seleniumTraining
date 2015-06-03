package com.training.JenkinsTest.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.ElementScrollBehavior;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebDriverManager {
    public static final String DOWNLOAD_DIRECTORY = System.getProperty("user.dir");
    public static final int DEFAULT_TIMEOUT_SEC = 30;
    public static final int SHORT_TIMEOUT_SEC = 10;
    
    private static WebDriver getWebDriver(Browser browser, Language language) {
        switch (browser) {
            case CHROME:
//                OSUtils.killProcess("chromedriver.exe");
                /**
                 * need to set system property with path of chromedriver to make it working
                 * https://code.google.com/p/selenium/wiki/ChromeDriver#Getting_Started
                 */
                String chromePath = "src/main/resources/chromedriver.exe";
                System.setProperty("webdriver.chrome.driver", chromePath);

                // http://stackoverflow.com/questions/13886430/passing-options-to-chrome-driver-selenium
                System.setProperty("webdriver.chrome.logfile", "NUL");
                ChromeOptions options = new ChromeOptions();
                // http://peter.sh/experiments/chromium-command-line-switches/
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.prompt_for_download", "false");
                prefs.put("download.default_directory", DOWNLOAD_DIRECTORY);
                prefs.put("savefile.default_directory", DOWNLOAD_DIRECTORY);
                prefs.put("safebrowsing.enabled", "true");
                options.setExperimentalOption("prefs", prefs);
                // http://stackoverflow.com/questions/18645205/set-chromes-language-using-selenium-chromedriver
                options.addArguments("--disable-logging", String.format("--lang=%s", language));
                return new ChromeDriver(options);
            case FIREFOX:
            /*
             * https://code.google.com/p/selenium/issues/detail?id=5050
			 * The issue is that firefox waits for the Dashboard page to load while there is some
			 * jquery which is continuously checks for updates - this makes firefox unresponsive
			 */
                DesiredCapabilities firefoxCaps = DesiredCapabilities.firefox();
                //List of constants: https://dvcs.w3.org/hg/webdriver/raw-file/tip/webdriver-spec.html#page-load-strategies-1/>
                firefoxCaps.setCapability(CapabilityType.PAGE_LOADING_STRATEGY, "none");
                // https://code.google.com/p/selenium/wiki/DesiredCapabilities#Read-write_capabilities
                firefoxCaps.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("intl.accept_languages", language.toString());
                profile.setPreference("browser.download.folderList", 2); // 0 - Desktop, 1 - System default, 2 - custom
                profile.setPreference("browser.download.dir", DOWNLOAD_DIRECTORY);
                profile.setPreference("browser.helperApps.alwaysAsk.force", false);
                profile.setPreference("browser.download.manager.showWhenStarting", false);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/x-msdos-program"); // *.exe
                FirefoxBinary binary = new FirefoxBinary();
                return new FirefoxDriver(binary, profile, firefoxCaps);
            case IE:
                // TODO: Use x64 version of driver when running on x64 Windows
//                OSUtils.killProcess("IEDriverServer.exe");
//                OSUtils.killProcess("iexplore.exe");
                String pathToIEDriver = "src/main/resources/IEDriverServer.exe";
                System.setProperty("webdriver.ie.driver", pathToIEDriver);

                // http://stackoverflow.com/questions/7413966/delete-cookies-in-webdriver
                DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
                caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
                // This code is commented out since IEDriver v2.43 crashes with this flag set up
                // https://code.google.com/p/selenium/wiki/DesiredCapabilities#Read-write_capabilities
//                caps.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, ElementScrollBehavior.BOTTOM);
                WebDriver wd = new InternetExplorerDriver(caps);
                wd.manage().deleteAllCookies();
                return wd;
            default:
                return new HtmlUnitDriver(true);
        }
    }
    
    public static WebDriver getDriver() {
        Browser browser = Browser.getCurrentBrowser();
        Language language = Language.getCurrentLanguage();
        Logger log = Logger.getLogger(WebDriverManager.class);
        log.debug("Going to start Browser: " + browser);
        WebDriver wd = getWebDriver(browser, language);

        wd.manage().timeouts().pageLoadTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);
        wd.manage().timeouts().setScriptTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS); //for async JavaScript
        wd.manage().window().maximize();
        return wd;
    }

}
