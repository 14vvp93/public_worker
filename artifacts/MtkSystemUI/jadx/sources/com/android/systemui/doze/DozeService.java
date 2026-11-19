package com.android.systemui.doze;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.service.dreams.DreamService;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.DozeServicePlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class DozeService extends DreamService implements DozeMachine.Service, DozeServicePlugin.RequestDoze, PluginListener<DozeServicePlugin> {
    static final boolean DEBUG = Log.isLoggable("DozeService", 3);
    private DozeMachine mDozeMachine;
    private DozeServicePlugin mDozePlugin;

    public DozeService() {
        setDebug(DEBUG);
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public void onCreate() {
        super.onCreate();
        setWindowless(true);
        if (DozeFactory.getHost(this) == null) {
            finish();
        } else {
            ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) this, DozeServicePlugin.class, false);
            this.mDozeMachine = new DozeFactory().assembleMachine(this);
        }
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public void onDestroy() {
        ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
        super.onDestroy();
        this.mDozeMachine = null;
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(DozeServicePlugin plugin, Context pluginContext) {
        this.mDozePlugin = plugin;
        this.mDozePlugin.setDozeRequester(this);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(DozeServicePlugin plugin) {
        if (this.mDozePlugin != null) {
            this.mDozePlugin.onDreamingStopped();
            this.mDozePlugin = null;
        }
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.mDozeMachine.requestState(DozeMachine.State.INITIALIZED);
        startDozing();
        if (this.mDozePlugin != null) {
            this.mDozePlugin.onDreamingStarted();
        }
    }

    @Override // android.service.dreams.DreamService
    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.mDozeMachine.requestState(DozeMachine.State.FINISH);
        if (this.mDozePlugin != null) {
            this.mDozePlugin.onDreamingStopped();
        }
    }

    protected void dumpOnHandler(FileDescriptor fd, PrintWriter pw, String[] args) {
        super.dumpOnHandler(fd, pw, args);
        if (this.mDozeMachine != null) {
            this.mDozeMachine.dump(pw);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Service
    public void requestWakeUp() {
        PowerManager pm = (PowerManager) getSystemService(PowerManager.class);
        pm.wakeUp(SystemClock.uptimeMillis(), "com.android.systemui:NODOZE");
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public void onRequestShowDoze() {
        if (this.mDozeMachine != null) {
            this.mDozeMachine.requestState(DozeMachine.State.DOZE_AOD);
        }
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public void onRequestHideDoze() {
        if (this.mDozeMachine != null) {
            this.mDozeMachine.requestState(DozeMachine.State.DOZE);
        }
    }
}
