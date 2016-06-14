package com.xamarin.testcloud.appium;

import org.junit.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class EnhancedIOSDriverTest {

    public static final byte[] PNG = OutputType.BYTES.convertFromBase64Png("amZsanNmbGtkc2pmbGtzZGpmbGtkc2ZqbGtkc2ZqZGxza2ZqZHNsa2Zq");
    private static AppiumMock appiumMock;
    private MemoryEventReporter reporter = new MemoryEventReporter();

    @Rule
    public Watcher watcher = new Watcher(reporter);

    private EnhancedIOSDriver driver;

    @BeforeClass
    public static void mockAppium() throws IOException {
        appiumMock = new AppiumMock();
        appiumMock.start();
    }

    @AfterClass
    public static void stopAppium() throws IOException {
        appiumMock.stop();
    }

    @Before
    public void setUp() throws MalformedURLException {
        driver = new EnhancedIOSDriver(new URL("http://localhost:8001"), new DesiredCapabilities(), reporter);
    }

    @After
    public void reset() {
        reporter.clear();
    }

    @Test
    public void testMark() throws Exception {
        driver.label("Lallerkok");
        assertTrue("Expected Lallerkok", reporter.getReports().stream().anyMatch(e -> e.getLabel() != null && e.getLabel().equals("Lallerkok")));
    }

    @Test
    public void testGetScreenshotAs() throws Exception {
        driver.getScreenshotAs(OutputType.BASE64);
        assertTrue("Expected screenshot", reporter.getReports().stream().anyMatch(e -> e.getScreenshotPath() != null && new File(e.getScreenshotPath()).exists()));
    }
}