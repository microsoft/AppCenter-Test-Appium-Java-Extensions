package com.xamarin.testcloud.appium;

import java.io.IOException;

class StdOutEventReporter extends EventReporter {

    @Override
    protected void commit(Event event) throws IOException {
        String output = String.format("%s - %s", event.getLabel(), event.getScreenshotPath());
        System.out.println(output);
    }

}
