package com.android.systemui.statusbar.car.hvac;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class TemperatureTextView extends TextView implements TemperatureView {
    private final int mAreaId;
    private final int mPropertyId;
    private final String mTempFormat;

    public TemperatureTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TemperatureView);
        this.mAreaId = typedArray.getInt(0, -1);
        this.mPropertyId = typedArray.getInt(1, -1);
        String format = typedArray.getString(2);
        this.mTempFormat = format == null ? "%.1f°" : format;
        HvacController hvacController = (HvacController) Dependency.get(HvacController.class);
        hvacController.addHvacTextView(this);
    }

    @Override // com.android.systemui.statusbar.car.hvac.TemperatureView
    public void setTemp(float temp) {
        if (Float.isNaN(temp)) {
            setText("--");
        } else {
            setText(String.format(this.mTempFormat, Float.valueOf(temp)));
        }
    }

    @Override // com.android.systemui.statusbar.car.hvac.TemperatureView
    public int getPropertyId() {
        return this.mPropertyId;
    }

    @Override // com.android.systemui.statusbar.car.hvac.TemperatureView
    public int getAreaId() {
        return this.mAreaId;
    }
}
