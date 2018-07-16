package com.microsoft.appcenter.appium;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DriverCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

class DriverHelper {
    static <X> X getScreenshotToWorkspace(Object result, OutputType<X> outputType, java8.util.function.Consumer<Path> reporter) {
        if (result instanceof String) {
            String base64EncodedPng = (String) result;
            File file = OutputType.FILE.convertFromBase64Png(base64EncodedPng);
            Path target = copyToWorkspace(file);

            reporter.accept(target);

            return outputType.convertFromBase64Png(base64EncodedPng);
        } else if (result instanceof byte[]) {
            byte[] buffer = (byte[]) result;
            File file = OutputType.FILE.convertFromPngBytes(buffer);
            Path target = copyToWorkspace(file);

            reporter.accept(target);

            return outputType.convertFromPngBytes(buffer);
        } else {
            throw new RuntimeException(String.format("Unexpected result for %s command: %s",
                    DriverCommand.SCREENSHOT,
                    result == null ? "null" : result.getClass().getName() + " instance"));
        }
    }


    static String sanitize(String input) {
        if (null == input) {
            return null;
        }
        return input.replaceAll("[\n\r:=]", " ");
    }

    static boolean pathIsWritable(String workspace) {
        if (workspace != null && !Objects.equals(workspace, "")) {
            Path path = Paths.get(workspace);
            if (Files.exists(path)) {
                return Files.isWritable(path);
            } else {
                try {
                    Files.createDirectory(path);
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return false;
    }

    private static String workspace;

    static {
        workspace = System.getenv("SCREENSHOT_PATH");
        if (!pathIsWritable(workspace)) {
            workspace = System.getenv("PWD");
            if (!pathIsWritable(workspace)) {
                try {
                    workspace = Files.createTempDirectory("appium").toAbsolutePath().toString();
                } catch (IOException e) {
                    throw new RuntimeException("Could not create writable folder for screenshots", e);
                }
            }
        }
    }

    private static Path copyToWorkspace(File file) {
        Path target = Paths.get(workspace, file.getName());
        try {
            Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write screenshot to disk", e);
        }
        return target;
    }
}
