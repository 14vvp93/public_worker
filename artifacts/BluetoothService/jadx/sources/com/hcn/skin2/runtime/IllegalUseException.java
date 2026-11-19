package com.hcn.skin2.runtime;

/* JADX INFO: loaded from: classes.dex */
public class IllegalUseException extends RuntimeException {
    public IllegalUseException(String str) {
        super(str);
    }

    public IllegalUseException(String str, Exception exc) {
        super(str, exc);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return "IllegalUseException, " + super.getMessage();
    }
}
