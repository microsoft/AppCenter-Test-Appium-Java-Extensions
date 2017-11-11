package com.microsoft.appcenter.appium;

import java.io.IOException;
import java.util.ArrayList;

public class MemoryEventReporter extends EventReporter {

    ArrayList<Event> events = new ArrayList<>();

    public ArrayList<Event> getReports() { return events; }

    public String[] getIds() {
        return java8.util.stream.StreamSupport.stream(events).map(Event::getId).toArray(String[]::new);
    }

    public boolean hasId(String id) {
        return java8.util.stream.StreamSupport.stream(events).anyMatch(report -> report.getId().equals(id));
    }

    public void clear() {
        events.clear();
    }


    @Override
    protected void commit(Event event) throws IOException {
        events.add(event);
    }
}
