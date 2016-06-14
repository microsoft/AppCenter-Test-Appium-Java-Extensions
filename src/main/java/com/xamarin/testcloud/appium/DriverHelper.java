package com.xamarin.testcloud.appium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DriverCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class DriverHelper {
     static <X> X getScreenshotToWorkspace(Object result, OutputType<X> outputType, Consumer<Path> reporter) {
         if (result instanceof String) {
             String base64EncodedPng = (String) result;
             File file = OutputType.FILE.convertFromBase64Png(base64EncodedPng);
             Path target = copyToPWD(file);

             reporter.accept(target);

             return outputType.convertFromBase64Png(base64EncodedPng);
         } else if (result instanceof byte[]) {
             byte[] buffer = (byte[]) result;
             File file = OutputType.FILE.convertFromPngBytes(buffer);
             Path target = copyToPWD(file);

             reporter.accept(target);

             return outputType.convertFromPngBytes(buffer);
         } else {
             throw new RuntimeException(String.format("Unexpected result for %s command: %s",
                     DriverCommand.SCREENSHOT,
                     result == null ? "null" : result.getClass().getName() + " instance"));
         }
     }


    static String sanitize(String input) {
        return input.replaceAll("[\n\r:=]", " ");
    }

    private static Path copyToPWD(File file) {
        String workspace = System.getenv("PWD");
        Path target = Paths.get(workspace, file.getName());
        try {
            Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return target;
    }
}
