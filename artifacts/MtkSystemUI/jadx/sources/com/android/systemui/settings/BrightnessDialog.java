package com.android.systemui.settings;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import com.android.internal.logging.MetricsLogger;

/* JADX INFO: loaded from: classes.dex */
public class BrightnessDialog extends Activity {
    private BrightnessController mBrightnessController;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(48);
        window.clearFlags(2);
        window.requestFeature(1);
        ContextThemeWrapper themedContext = new ContextThemeWrapper(this, R.style.TextAppearance.Leanback.FormWizard);
        View v = LayoutInflater.from(themedContext).inflate(com.android.systemui.R.layout.quick_settings_brightness_dialog, (ViewGroup) null);
        setContentView(v);
        ImageView icon = (ImageView) findViewById(com.android.systemui.R.id.brightness_icon);
        ToggleSliderView slider = (ToggleSliderView) findViewById(com.android.systemui.R.id.brightness_slider);
        this.mBrightnessController = new BrightnessController(this, icon, slider);
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.mBrightnessController.registerCallbacks();
        MetricsLogger.visible(this, 220);
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        MetricsLogger.hidden(this, 220);
        this.mBrightnessController.unregisterCallbacks();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 25 || keyCode == 24 || keyCode == 164) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
