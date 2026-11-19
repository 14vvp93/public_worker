package com.hcn.skinx_night;

import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;

/* JADX INFO: loaded from: classes.dex */
public class NightAttrs {
    private SparseIntArray mAttrDayResIds;
    private SparseIntArray mAttrNightResIds;
    private SparseArray<String> mAttrResTypes;
    private int mAttributeMark;
    private boolean mNightResIdResolved;
    private int mWidgetType;

    private NightAttrs() {
        this.mWidgetType = 0;
        this.mAttributeMark = 0;
        this.mAttrDayResIds = new SparseIntArray();
        this.mAttrNightResIds = new SparseIntArray();
        this.mAttrResTypes = new SparseArray<>();
        this.mNightResIdResolved = false;
    }

    public NightAttrs(AttributeSet attributeSet, int i, int i2) {
        this.mWidgetType = 0;
        this.mAttributeMark = 0;
        this.mAttrDayResIds = new SparseIntArray();
        this.mAttrNightResIds = new SparseIntArray();
        this.mAttrResTypes = new SparseArray<>();
        this.mNightResIdResolved = false;
        this.mWidgetType = i;
        this.mAttributeMark = i2;
        parseDefaultAttrRes(attributeSet);
    }

    public NightAttrs(NightAttrs nightAttrs) {
        this.mWidgetType = 0;
        this.mAttributeMark = 0;
        this.mAttrDayResIds = new SparseIntArray();
        this.mAttrNightResIds = new SparseIntArray();
        this.mAttrResTypes = new SparseArray<>();
        this.mNightResIdResolved = false;
        this.mWidgetType = nightAttrs.mWidgetType;
        this.mAttributeMark = nightAttrs.mAttributeMark;
        SparseIntArray sparseIntArray = nightAttrs.mAttrDayResIds;
        if (sparseIntArray != null) {
            this.mAttrDayResIds = sparseIntArray.clone();
        }
        SparseIntArray sparseIntArray2 = nightAttrs.mAttrNightResIds;
        if (sparseIntArray2 != null) {
            this.mAttrNightResIds = sparseIntArray2.clone();
        }
        SparseArray<String> sparseArray = nightAttrs.mAttrResTypes;
        if (sparseArray != null) {
            this.mAttrResTypes = sparseArray.clone();
        }
    }

    public void setWidgetType(int i) {
        this.mWidgetType = i;
    }

    public void setAttributeMark(int i) {
        this.mAttributeMark = i;
    }

    public int widgetType() {
        return this.mWidgetType;
    }

    public int attributeMark() {
        return this.mAttributeMark;
    }

    public boolean nightResIdResolved() {
        return this.mNightResIdResolved;
    }

    public boolean isValid() {
        return this.mAttrDayResIds.size() != 0;
    }

    public boolean isNightValid() {
        return this.mAttrNightResIds.size() != 0;
    }

    public int nightResId4Attr(int i) {
        if (isNightValid()) {
            return this.mAttrNightResIds.get(i, 0);
        }
        return 0;
    }

    public String resType4Attr(int i) {
        return !isNightValid() ? "none" : this.mAttrResTypes.get(i, "none");
    }

    private void parseDefaultAttrRes(AttributeSet attributeSet) {
        int i = this.mWidgetType;
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                parseBaseAttrRes(attributeSet);
                break;
            case 7:
                parseListViewAttrRes(attributeSet);
                break;
            default:
                switch (i) {
                    case 32:
                    case 33:
                        parseImageAttrRes(attributeSet);
                        break;
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                        parseTextAttrRes(attributeSet);
                        break;
                    case 38:
                    case 39:
                        parseSeekBarAttrRes(attributeSet);
                        break;
                }
                break;
        }
    }

    private void parseAndroidAttribute(AttributeSet attributeSet, int i, String str) {
        int attributeResourceValue;
        if ((this.mAttributeMark & i) != i || (attributeResourceValue = attributeSet.getAttributeResourceValue(NightX.ANDROID_RESOURCES, str, 0)) == 0) {
            return;
        }
        this.mAttrDayResIds.put(i, attributeResourceValue);
    }

    private void parseBaseAttrRes(AttributeSet attributeSet) {
        parseAndroidAttribute(attributeSet, 1, "background");
        int i = this.mWidgetType;
        if (i == 7 || i == 8) {
            parseAndroidAttribute(attributeSet, 8, "scrollbarThumbVertical");
            parseAndroidAttribute(attributeSet, 16, "scrollbarTrackVertical");
            parseAndroidAttribute(attributeSet, 32, "scrollbarThumbHorizontal");
            parseAndroidAttribute(attributeSet, 64, "scrollbarTrackHorizontal");
        }
    }

    private void parseImageAttrRes(AttributeSet attributeSet) {
        parseBaseAttrRes(attributeSet);
        parseAndroidAttribute(attributeSet, 4, "src");
    }

    private void parseTextAttrRes(AttributeSet attributeSet) {
        parseBaseAttrRes(attributeSet);
        parseAndroidAttribute(attributeSet, 2, "textColor");
        parseAndroidAttribute(attributeSet, 128, "textColorHint");
        parseAndroidAttribute(attributeSet, 256, "drawableTop");
        parseAndroidAttribute(attributeSet, 512, "drawableBottom");
        parseAndroidAttribute(attributeSet, 4096, "drawableStart");
        parseAndroidAttribute(attributeSet, 8192, "drawableEnd");
    }

    private void parseSeekBarAttrRes(AttributeSet attributeSet) {
        parseBaseAttrRes(attributeSet);
        parseAndroidAttribute(attributeSet, 1024, "progressDrawable");
        if (this.mWidgetType != 39) {
            return;
        }
        parseAndroidAttribute(attributeSet, 512, "thumb");
    }

    private void parseListViewAttrRes(AttributeSet attributeSet) {
        parseBaseAttrRes(attributeSet);
        parseAndroidAttribute(attributeSet, 256, "divider");
    }

    public void resolveNightResIds() {
        if (isValid() && !nightResIdResolved()) {
            this.mNightResIdResolved = true;
            ResInfo resInfo = new ResInfo();
            for (int i = 0; i < this.mAttrDayResIds.size(); i++) {
                int iKeyAt = this.mAttrDayResIds.keyAt(i);
                ResInfo resInfoFindNightResInfo = NightSkinX.findNightResInfo(resInfo, this.mAttrDayResIds.valueAt(i));
                this.mAttrResTypes.put(iKeyAt, resInfoFindNightResInfo.type);
                this.mAttrNightResIds.put(iKeyAt, resInfoFindNightResInfo.f90id);
            }
        }
    }
}
