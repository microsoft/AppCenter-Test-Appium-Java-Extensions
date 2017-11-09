package com.microsoft.appcenter.appium;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class AppiumMock {

    private HttpServer server;

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void stop() {
        server.stop(1);
    }

    static class MyHandler implements HttpHandler {

        public static final String SESSION_ID = "c3d24a83-385c-4a32-bea9-40e3ee588ff8";

        public void handle(HttpExchange t) throws IOException {
            String verb = t.getRequestMethod();
            String path = t.getRequestURI().getPath();

            if (path.equals("/session") && verb.equals("POST")) {
                System.out.println("Creating a new session!");
                sendOk(t);
                return;
            }

            if (path.equals("/session/" + SESSION_ID + "/screenshot") && verb.equals("GET")) {
                System.out.println("Taking a screenshot");
                sendScreenshot(t);
                return;
            }

            sendOk(t);
        }

        private void sendScreenshot(HttpExchange t) throws IOException {
            String response = "{\"value\": \"amZsanNmbGtkc2pmbGtzZGpmbGtkc2ZqbGtkc2ZqZGxza2ZqZHNsa2Zq\", \"status\": 0}";
            send(t, response);
        }

        private void sendOk(HttpExchange t) throws IOException {
            String response = "{\"sessionId\": \"" + SESSION_ID + "\", \"value\": {}, \"status\": 0}";
            send(t, response);
        }

        private void send(HttpExchange t, String response) throws IOException {
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
