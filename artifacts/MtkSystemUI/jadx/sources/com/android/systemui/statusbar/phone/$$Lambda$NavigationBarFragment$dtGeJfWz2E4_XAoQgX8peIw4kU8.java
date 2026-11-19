package com.android.systemui.statusbar.phone;

import android.view.View;

/* JADX INFO: renamed from: com.android.systemui.statusbar.phone.-$$Lambda$NavigationBarFragment$dtGeJfWz2E4_XAoQgX8peIw4kU8, reason: invalid class name */
/* JADX INFO: compiled from: lambda */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$NavigationBarFragment$dtGeJfWz2E4_XAoQgX8peIw4kU8 implements View.OnLongClickListener {
    private final /* synthetic */ NavigationBarFragment f$0;

    public /* synthetic */ $$Lambda$NavigationBarFragment$dtGeJfWz2E4_XAoQgX8peIw4kU8(NavigationBarFragment navigationBarFragment) {
        this.f$0 = navigationBarFragment;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        return this.f$0.onLongPressBackRecents(view);
    }
}
