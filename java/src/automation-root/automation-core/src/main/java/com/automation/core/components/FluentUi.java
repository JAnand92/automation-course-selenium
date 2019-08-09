package com.automation.core.components;

import com.automation.core.logging.Logger;
import com.automation.core.logging.TraceLogger;
import com.automation.extensions.components.WebDriverExtensions;
import com.automation.extensions.components.WebDriverFactory;
import com.automation.extensions.contracts.DriverParams;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

public class FluentUi extends FluentBase {

    private final WebDriver driver;
    private WebDriverExtensions driverExtensions;

    public FluentUi(String driverParams) throws MalformedURLException {
        this(new WebDriverFactory(driverParams).get());
    }

    public FluentUi(DriverParams driverParams) throws MalformedURLException {
        this(new WebDriverFactory(driverParams).get());
    }

    public FluentUi(WebDriverFactory webDriverFactory) throws MalformedURLException {
        this(webDriverFactory.get());
    }

    public FluentUi(WebDriver driver) {
        this(driver, new TraceLogger());
    }

    public FluentUi(WebDriver driver, Logger logger) {
        super(logger);
        this.driver = driver;
        driverExtensions = new WebDriverExtensions(driver);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverExtensions getDriverExtensions() {
        return driverExtensions;
    }

    @Override
    public <T> T changeContext(Class c, String application)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // navigate
        driver.navigate().to(application);
        driver.manage().window().maximize();

        // factory
        return generateObject(c, null);
    }

    @Override
    public <T> T changeContext(Class c, String application, Logger logger)
            throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // navigation
        driver.navigate().to(application);
        driver.manage().window().maximize();

        // factory
        return generateObject(c, logger);
    }

    @SuppressWarnings("unchecked")
    @Override
    <T> T generateObject(Class c, Logger logger)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // get constructor
        Constructor<T> ctr = logger == null
                ? c.getDeclaredConstructor(WebDriver.class)
                : c.getDeclaredConstructor(WebDriver.class, Logger.class);

        // get arguments
        Object[] args = logger == null ? new Object[]{driver} : new Object[]{driver, logger};

        // return new object instance
        return ctr.newInstance(args);
    }
}