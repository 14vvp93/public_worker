package com.android.systemui.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import com.android.systemui.ConfigurationChangedReceiver;
import com.android.systemui.Dumpable;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class FragmentService implements ConfigurationChangedReceiver, Dumpable {
    private final Context mContext;
    private final ArrayMap<View, FragmentHostState> mHosts = new ArrayMap<>();
    private final Handler mHandler = new Handler();

    public FragmentService(Context context) {
        this.mContext = context;
    }

    public FragmentHostManager getFragmentHostManager(View view) {
        View root = view.getRootView();
        FragmentHostState state = this.mHosts.get(root);
        if (state == null) {
            state = new FragmentHostState(root);
            this.mHosts.put(root, state);
        }
        return state.getFragmentHostManager();
    }

    public void destroyAll() {
        for (FragmentHostState state : this.mHosts.values()) {
            state.mFragmentHostManager.destroy();
        }
    }

    @Override // com.android.systemui.ConfigurationChangedReceiver
    public void onConfigurationChanged(Configuration newConfig) {
        for (FragmentHostState state : this.mHosts.values()) {
            state.sendConfigurationChange(newConfig);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(FileDescriptor fd, PrintWriter pw, String[] args) {
        pw.println("Dumping fragments:");
        for (FragmentHostState state : this.mHosts.values()) {
            state.mFragmentHostManager.getFragmentManager().dump("  ", fd, pw, args);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class FragmentHostState {
        private FragmentHostManager mFragmentHostManager;
        private final View mView;

        public FragmentHostState(View view) {
            this.mView = view;
            this.mFragmentHostManager = new FragmentHostManager(FragmentService.this.mContext, FragmentService.this, this.mView);
        }

        public void sendConfigurationChange(final Configuration newConfig) {
            FragmentService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.fragments.-$$Lambda$FragmentService$FragmentHostState$kEJEvu5Mq9Z5e9srOLcsFn7Glto
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.handleSendConfigurationChange(newConfig);
                }
            });
        }

        public FragmentHostManager getFragmentHostManager() {
            return this.mFragmentHostManager;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleSendConfigurationChange(Configuration newConfig) {
            this.mFragmentHostManager.onConfigurationChanged(newConfig);
        }
    }
}
