package com.microsoft.appcenter.appium.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

public class GenericsRewrite {
    private static String GenericdDecl = "<TT;>";
    public static void main(String[] args) {
        ClassPool pool = ClassPool.getDefault();

        for (String clazz: args) {
            try {
                CtClass cc = pool.get(clazz);
                final String genericSignature = cc.getGenericSignature();
                // Rewrite once
                if(!genericSignature.contains(GenericdDecl)) {
                    final int start = genericSignature.lastIndexOf('<');
                    final int stop = genericSignature.lastIndexOf('>');
                    if (start == -1 | stop == -1 || stop < start) {
                        throw new RuntimeException(String.format("Unable to find existing generics in signature %s, %d, %d", genericSignature, start, stop));
                    }
                    final String newSignature = genericSignature.substring(0, start) + GenericdDecl +
                            genericSignature.substring(stop + 1, genericSignature.length());
                    cc.setGenericSignature(newSignature);
                    cc.writeFile();
                }
            } catch (NotFoundException e) {
                throw new RuntimeException(String.format("Unable to find class: %s", clazz), e);
            } catch (CannotCompileException e) {
                throw new RuntimeException(String.format("Unable to compile to new class: %s", clazz), e);
            } catch (IOException e) {
                throw new RuntimeException(String.format("Unable to write new class: %s", clazz), e);
            }
        }
    }
}
