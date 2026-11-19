package com.android.systemui.statusbar.tv;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.SystemUI;
import com.android.systemui.statusbar.CommandQueue;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class TvStatusBar extends SystemUI implements CommandQueue.Callbacks {
    private IStatusBarService mBarService;

    @Override // com.android.systemui.SystemUI
    public void start() {
        putComponent(TvStatusBar.class, this);
        CommandQueue commandQueue = (CommandQueue) getComponent(CommandQueue.class);
        commandQueue.addCallbacks(this);
        int[] switches = new int[9];
        ArrayList<IBinder> binders = new ArrayList<>();
        ArrayList<String> iconSlots = new ArrayList<>();
        ArrayList<StatusBarIcon> icons = new ArrayList<>();
        Rect fullscreenStackBounds = new Rect();
        Rect dockedStackBounds = new Rect();
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        try {
            this.mBarService.registerStatusBar(commandQueue, iconSlots, icons, switches, binders, fullscreenStackBounds, dockedStackBounds);
        } catch (RemoteException e) {
        }
    }
}
