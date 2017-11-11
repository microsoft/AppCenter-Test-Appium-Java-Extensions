package com.microsoft.appcenter.appium;

import java.io.IOException;

import static com.microsoft.appcenter.appium.EventType.*;

class StdOutEventReporter extends EventReporter {

    @Override
    protected void commit(Event event) throws IOException {
        switch (event.getType()) {
            case started:
                System.out.println(String.format("%s: %s (%s)", event.getType().name(), event.getTestName(), event.getClassName()));
                break;
            case label:
                System.out.println(String.format("%s - %s", event.getLabel(), event.getScreenshotPath()));
                break;
            case screenshot:
                System.out.println(String.format("Took screenshot - %s", event.getScreenshotPath()));
                break;
            default:
                System.out.println(event.getType().name());
                break;
        }
    }

}
