package com.android.systemui;

import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class SystemBars extends SystemUI {
    private SystemUI mStatusBar;

    @Override // com.android.systemui.SystemUI
    public void start() {
        createStatusBarFromConfig();
    }

    @Override // com.android.systemui.SystemUI
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        if (this.mStatusBar != null) {
            this.mStatusBar.dump(fd, pw, args);
        }
    }

    private void createStatusBarFromConfig() {
        String clsName = this.mContext.getString(R.string.config_statusBarComponent);
        if (clsName == null || clsName.length() == 0) {
            throw andLog("No status bar component configured", null);
        }
        try {
            Class<?> cls = this.mContext.getClassLoader().loadClass(clsName);
            try {
                this.mStatusBar = (SystemUI) cls.newInstance();
                this.mStatusBar.mContext = this.mContext;
                this.mStatusBar.mComponents = this.mComponents;
                this.mStatusBar.start();
            } catch (Throwable t) {
                throw andLog("Error creating status bar component: " + clsName, t);
            }
        } catch (Throwable t2) {
            throw andLog("Error loading status bar component: " + clsName, t2);
        }
    }

    private RuntimeException andLog(String msg, Throwable t) {
        Log.w("SystemBars", msg, t);
        throw new RuntimeException(msg, t);
    }
}
