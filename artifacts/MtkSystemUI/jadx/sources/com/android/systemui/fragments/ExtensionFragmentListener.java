package com.android.systemui.fragments;

import android.app.Fragment;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.FragmentBase;
import com.android.systemui.statusbar.policy.ExtensionController;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionFragmentListener<T extends FragmentBase> implements Consumer<T> {
    private final ExtensionController.Extension<T> mExtension;
    private final FragmentHostManager mFragmentHostManager;
    private final int mId;
    private String mOldClass;
    private final String mTag;

    private ExtensionFragmentListener(View view, String str, int i, ExtensionController.Extension<T> extension) {
        this.mTag = str;
        this.mFragmentHostManager = FragmentHostManager.get(view);
        this.mExtension = extension;
        this.mId = i;
        this.mFragmentHostManager.getFragmentManager().beginTransaction().replace(i, (Fragment) this.mExtension.get(), this.mTag).commit();
        this.mExtension.clearItem(false);
    }

    @Override // java.util.function.Consumer
    public void accept(T extension) {
        try {
            Fragment.class.cast(extension);
            this.mFragmentHostManager.getExtensionManager().setCurrentExtension(this.mId, this.mTag, this.mOldClass, extension.getClass().getName(), this.mExtension.getContext());
            this.mOldClass = extension.getClass().getName();
        } catch (ClassCastException e) {
            Log.e("ExtensionFragmentListener", extension.getClass().getName() + " must be a Fragment", e);
        }
        this.mExtension.clearItem(true);
    }

    public static <T> void attachExtensonToFragment(View view, String tag, int id, ExtensionController.Extension<T> extension) {
        extension.addCallback(new ExtensionFragmentListener(view, tag, id, extension));
    }
}
