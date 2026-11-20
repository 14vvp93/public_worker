package com.hcn.skinx_night;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.common.misc.LogUtils;
import com.hcn.common.utils.HUtilsEx;
import com.hcn.skinx_night.enhance.SkinViewFactory;
import java.util.HashMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NightX {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final String DARK_TAG_PREFIX = "SkinX#";
    public static final String DARK_TAG_SEGMENTS_SEPARATOR = "#";
    public static final int DARK_TAG_SEGMENTS_SIZE = 3;
    private static final String TAG = "NightX";
    public static final int TAG_KEY_BASE = -536805376;
    public static final int TAG_KEY_NIGHT_MODE = -536739840;
    private static final SparseArray<String> WIDGET_NAMES;
    private static final HashMap<Integer, Integer> resourceMap;

    interface Attribute {
        public static final int None = 0;
        public static final int background = 1;
        public static final int divider = 256;
        public static final int drawableBottom = 512;
        public static final int drawableEnd = 8192;
        public static final int drawableLeft = 1024;
        public static final int drawableRight = 2048;
        public static final int drawableStart = 4096;
        public static final int drawableTop = 256;
        public static final int progressDrawable = 1024;
        public static final int scrollbarThumbHorizontal = 32;
        public static final int scrollbarThumbVertical = 8;
        public static final int scrollbarTrackHorizontal = 64;
        public static final int scrollbarTrackVertical = 16;
        public static final int src = 4;
        public static final int textColor = 2;
        public static final int textColorHint = 128;
        public static final int thumb = 512;
    }

    interface Widget {
        public static final int BUTTON = 35;
        public static final int CONSTRAINT_LAYOUT = 4;
        public static final int EDIT_TEXT = 37;
        public static final int FRAME_LAYOUT = 2;
        public static final int GRID_LAYOUT = 5;
        public static final int IMAGE_BUTTON = 33;
        public static final int IMAGE_VIEW = 32;
        public static final int LINEAR_LAYOUT = 1;
        public static final int LIST_VIEW = 7;
        public static final int NONE = -1;
        public static final int PROGRESS_BAR = 38;
        public static final int RADIO_BUTTON = 36;
        public static final int RADIO_GROUP = 6;
        public static final int RECYCLER_VIEW = 8;
        public static final int RELATIVE_LAYOUT = 3;
        public static final int SEEK_BAR = 39;
        public static final int TEXT_VIEW = 34;
        public static final int VIEW = 0;
    }

    static {
        SparseArray<String> sparseArray = new SparseArray<>();
        WIDGET_NAMES = sparseArray;
        resourceMap = new HashMap<>();
        sparseArray.append(0, "View");
        sparseArray.append(1, "LinearLayout");
        sparseArray.append(2, "FrameLayout");
        sparseArray.append(3, "RelativeLayout");
        sparseArray.append(4, "ConstraintLayout");
        sparseArray.append(5, "GridLayout");
        sparseArray.append(6, "RadioGroup");
        sparseArray.append(7, "ListView");
        sparseArray.append(8, "RecyclerView");
        sparseArray.append(32, "ImageView");
        sparseArray.append(33, "ImageButton");
        sparseArray.append(34, "TextView");
        sparseArray.append(35, "Button");
        sparseArray.append(36, "RadioButton");
        sparseArray.append(37, "EditText");
        sparseArray.append(38, "ProgressBar");
        sparseArray.append(39, "SeekBar");
    }

    private static boolean supportDarkModeTag(String str) {
        return str.startsWith(DARK_TAG_PREFIX);
    }

    public static int widgetTypeVerification(String str, int i) {
        String str2 = WIDGET_NAMES.get(i, "none");
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf != -1) {
            str = str.substring(iLastIndexOf + 1);
        }
        if (str.equals(str2)) {
            return 0;
        }
        return (str.equals(new StringBuilder().append(str2).append("Ex").toString()) || str.equals(new StringBuilder().append("H").append(str2).toString())) ? 1 : -1;
    }

    public static NightAttrs parseAttributeTag(String str, Context context, AttributeSet attributeSet) {
        String attributeValue = attributeSet.getAttributeValue(ANDROID_RESOURCES, "tag");
        if (TextUtils.isEmpty(attributeValue) || !supportDarkModeTag(attributeValue)) {
            return null;
        }
        String str2 = TAG;
        LogUtils.vTag(str2, "name: " + str + " tag: " + attributeValue);
        String[] strArrSplit = attributeValue.split(DARK_TAG_SEGMENTS_SEPARATOR);
        if (strArrSplit.length != 3) {
            LogUtils.wTag(str2, "dark mode tag format error. (SkinX#01#0001)");
            return null;
        }
        try {
            int i = Integer.parseInt(strArrSplit[1], 16);
            int i2 = Integer.parseInt(strArrSplit[2], 16);
            int iWidgetTypeVerification = widgetTypeVerification(str, i);
            if (iWidgetTypeVerification == -1) {
                LogUtils.wTag(str2, i + " dark mode tag 'widgetType' does not match component name. " + str);
                return null;
            }
            if (i2 == 0) {
                LogUtils.wTag(str2, "dark mode tag 'attribute' is an invalid value.");
                return null;
            }
            NightAttrs nightAttrs = new NightAttrs(attributeSet, i, i2, iWidgetTypeVerification == 1);
            if (nightAttrs.isValid()) {
                return nightAttrs;
            }
            return null;
        } catch (NumberFormatException unused) {
            LogUtils.wTag(TAG, "dark mode tag format error. (SkinX#01#0001)");
            return null;
        }
    }

    public static NightAttrs getViewNightAttrs(View view) {
        Object tag = view.getTag(TAG_KEY_BASE);
        if (tag != null) {
            return (NightAttrs) tag;
        }
        return null;
    }

    public static void setNightAttrsTag(View view, NightAttrs nightAttrs) {
        if (view == null || nightAttrs == null) {
            return;
        }
        view.setTag(TAG_KEY_BASE, nightAttrs);
    }

    public static View buildViewFromNightAttr(String str, Context context, AttributeSet attributeSet, NightAttrs nightAttrs) {
        if (Objects.isNull(nightAttrs)) {
            return null;
        }
        if (nightAttrs.isDerivedWidget()) {
            return SkinViewFactory.inflateViewByClassName(str, context, attributeSet);
        }
        LogUtils.vTag(TAG, "buildView: " + nightAttrs.widgetType() + ", " + nightAttrs.attributeMark());
        int iWidgetType = nightAttrs.widgetType();
        switch (iWidgetType) {
            case 0:
                return new View(context, attributeSet);
            case 1:
                return new LinearLayout(context, attributeSet);
            case 2:
                return new FrameLayout(context, attributeSet);
            case 3:
                return new RelativeLayout(context, attributeSet);
            case 4:
                return new ConstraintLayout(context, attributeSet);
            case 5:
                return new GridLayout(context, attributeSet);
            case 6:
                return new RadioGroup(context, attributeSet);
            case 7:
                return new ListView(context, attributeSet);
            case 8:
                return new RecyclerView(context, attributeSet);
            default:
                switch (iWidgetType) {
                    case 32:
                        return new ImageView(context, attributeSet);
                    case 33:
                        return new ImageButton(context, attributeSet);
                    case 34:
                        return new TextView(context, attributeSet);
                    case 35:
                        return new Button(context, attributeSet);
                    case 36:
                        return new RadioButton(context, attributeSet);
                    case 37:
                        return new EditText(context, attributeSet);
                    case 38:
                        return new ProgressBar(context, attributeSet);
                    case 39:
                        return new SeekBar(context, attributeSet);
                    default:
                        return null;
                }
        }
    }

    public static boolean isNightMode() {
        return (HUtilsEx.getApp().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public static int xNightId(int i, String str) {
        HashMap<Integer, Integer> map = resourceMap;
        Integer num = map.get(Integer.valueOf(i));
        if (num != null) {
            return num.intValue();
        }
        int skinResId = NightSkinX.getSkinResId(i, str);
        if (skinResId <= 0) {
            return i;
        }
        ResInfo resInfoFindNightResInfo = NightSkinX.findNightResInfo(skinResId, str);
        if (!resInfoFindNightResInfo.isValid()) {
            LogUtils.iTag(TAG, "xNightId resolveNightResIds: failed [resId:" + i + ", type:" + str + "]");
            return skinResId;
        }
        map.put(Integer.valueOf(i), Integer.valueOf(resInfoFindNightResInfo.id));
        return resInfoFindNightResInfo.id;
    }

    public static int xNightSkinId(int i, String str) {
        HashMap<Integer, Integer> map = resourceMap;
        Integer num = map.get(Integer.valueOf(i));
        if (num != null) {
            return num.intValue();
        }
        ResInfo resInfoFindNightResInfo = NightSkinX.findNightResInfo(i, str);
        if (!resInfoFindNightResInfo.isValid()) {
            return i;
        }
        map.put(Integer.valueOf(i), Integer.valueOf(resInfoFindNightResInfo.id));
        return resInfoFindNightResInfo.id;
    }
}
