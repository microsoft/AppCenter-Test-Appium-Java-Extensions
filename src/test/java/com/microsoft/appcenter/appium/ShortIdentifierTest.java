package com.microsoft.appcenter.appium;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ShortIdentifierTest {

    @Test
    public void ShortInputsContainTheOriginalString() {
        String value = new ShortIdentifier("1234567890ABCDEF").value();
        assertTrue("contains 1234567890ABCDEF", value.contains("1234567890ABCDEF"));
    }

    @Test
    public void TwoAlmostSimilarInputsReturnDifferentShortIds() {
        String value1 = new ShortIdentifier("1234567890ABCDEF").value();
        String value2 = new ShortIdentifier("1234567890ABCDEG").value();
        assertNotEquals(value1, value2);
    }

    @Test
    public void InvalidFileSystemCharsAreStripped() throws IOException {
        String value = new ShortIdentifier(":/?a$!\"#€€#€!").value();
        System.out.println(value);
        File.createTempFile(value, ".txt");
        assertEquals("-a-4devdukkiyfu", value);
    }

    @Test
    public void InvalidFileSystemCharsAreStrippedButFilesAreStillUnqiue() throws IOException {
        String value1 = new ShortIdentifier(":/?$!\"#€!").value();
        String value2 = new ShortIdentifier(":/?$!\"#€").value();
        System.out.println(value1);
        System.out.println(value2);
        assertNotEquals(value1, value2);
    }

    @Test
    public void LongPartsAreTruncated() throws IOException {
        String repeated = new String(new char[4000]).replace("\0", "X");
        String value = new ShortIdentifier(repeated).value();
        System.out.println(value);
        assertEquals(63, value.length());
    }

    @Test
    public void LongInputsWithManyPartsAreTruncated() throws IOException {
        String repeated = new String(new char[2000]).replace("\0", "X:");
        String value = new ShortIdentifier(repeated).value();
        System.out.println(value);
        assertEquals(1024, value.length());
    }

    @Test(expected = IllegalArgumentException.class)
    public void EmptyInputsAreNotAccepted() {
        new ShortIdentifier("").value();
    }

}