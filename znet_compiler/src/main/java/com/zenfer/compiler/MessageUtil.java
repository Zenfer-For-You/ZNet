package com.zenfer.compiler;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class MessageUtil {

    private Messager mMessager;

    public MessageUtil(Messager messager) {
        mMessager = messager;
    }

    public void setMessager(Messager messager) {
        this.mMessager = messager;
    }

    public void logError(Element element, String str) {
        log(Diagnostic.Kind.ERROR, element, str);
    }

    public void logNote(String str) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, str);
    }

    public void log(Diagnostic.Kind kind, Element element, String str) {
        mMessager.printMessage(kind, str, element);
    }
}
