package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;

/* JADX INFO: loaded from: classes.dex */
public class SliceBroadcastRelayHandler extends SystemUI {
    private final ArrayMap<Uri, BroadcastRelay> mRelays = new ArrayMap<>();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.SliceBroadcastRelayHandler.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SliceBroadcastRelayHandler.this.handleIntent(intent);
        }
    };

    @Override // com.android.systemui.SystemUI
    public void start() {
        IntentFilter filter = new IntentFilter("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        filter.addAction("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
        this.mContext.registerReceiver(this.mReceiver, filter);
    }

    @VisibleForTesting
    void handleIntent(Intent intent) {
        if ("com.android.settingslib.action.REGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
            Uri uri = (Uri) intent.getParcelableExtra("uri");
            ComponentName receiverClass = (ComponentName) intent.getParcelableExtra("receiver");
            IntentFilter filter = (IntentFilter) intent.getParcelableExtra("filter");
            getOrCreateRelay(uri).register(this.mContext, receiverClass, filter);
            return;
        }
        if ("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
            Uri uri2 = (Uri) intent.getParcelableExtra("uri");
            getAndRemoveRelay(uri2).unregister(this.mContext);
        }
    }

    private BroadcastRelay getOrCreateRelay(Uri uri) {
        BroadcastRelay ret = this.mRelays.get(uri);
        if (ret == null) {
            BroadcastRelay ret2 = new BroadcastRelay(uri);
            this.mRelays.put(uri, ret2);
            return ret2;
        }
        return ret;
    }

    private BroadcastRelay getAndRemoveRelay(Uri uri) {
        return this.mRelays.remove(uri);
    }

    private static class BroadcastRelay extends BroadcastReceiver {
        private final ArraySet<ComponentName> mReceivers = new ArraySet<>();
        private final Uri mUri;
        private final UserHandle mUserId;

        public BroadcastRelay(Uri uri) {
            this.mUserId = new UserHandle(ContentProvider.getUserIdFromUri(uri));
            this.mUri = uri;
        }

        public void register(Context context, ComponentName receiver, IntentFilter filter) {
            this.mReceivers.add(receiver);
            context.registerReceiver(this, filter);
        }

        public void unregister(Context context) {
            context.unregisterReceiver(this);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.addFlags(268435456);
            for (ComponentName receiver : this.mReceivers) {
                intent.setComponent(receiver);
                intent.putExtra("uri", this.mUri.toString());
                context.sendBroadcastAsUser(intent, this.mUserId);
            }
        }
    }
}
