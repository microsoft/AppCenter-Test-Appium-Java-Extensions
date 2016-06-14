package com.xamarin.testcloud.appium;

import java.io.IOException;
import java.util.ArrayList;

public class MemoryEventReporter extends EventReporter {

    ArrayList<Event> events = new ArrayList<Event>();

    public ArrayList<Event> getReports() { return events; }

    public String[] getIds() {
        return events.stream().map(Event::getId).toArray(String[]::new);
    }

    public boolean hasId(String id) {
        return events.stream().anyMatch(report -> report.getId().equals(id));
    }

    public void clear() {
        events.clear();
    }


    @Override
    protected void commit(Event event) throws IOException {
        events.add(event);
    }
}
