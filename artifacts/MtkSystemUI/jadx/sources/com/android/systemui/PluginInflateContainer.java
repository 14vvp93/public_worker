package com.android.systemui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.ViewProvider;

/* JADX INFO: loaded from: classes.dex */
public class PluginInflateContainer extends AutoReinflateContainer implements PluginListener<ViewProvider> {
    private Class<?> mClass;
    private View mPluginView;

    public PluginInflateContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PluginInflateContainer);
        String viewType = a.getString(0);
        try {
            this.mClass = Class.forName(viewType);
        } catch (Exception e) {
            Log.d("PluginInflateContainer", "Problem getting class info " + viewType, e);
            this.mClass = null;
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mClass != null) {
            ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener(this, this.mClass);
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mClass != null) {
            ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
        }
    }

    @Override // com.android.systemui.AutoReinflateContainer
    protected void inflateLayoutImpl() {
        if (this.mPluginView != null) {
            addView(this.mPluginView);
        } else {
            super.inflateLayoutImpl();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(ViewProvider plugin, Context context) {
        this.mPluginView = plugin.getView();
        inflateLayout();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(ViewProvider plugin) {
        this.mPluginView = null;
        inflateLayout();
    }
}
