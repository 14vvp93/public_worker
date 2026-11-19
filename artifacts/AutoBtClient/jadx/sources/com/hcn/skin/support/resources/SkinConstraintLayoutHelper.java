package com.hcn.skin.support.resources;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.hcn.skin.support.utils.SkinLog;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinConstraintLayoutHelper {
    private static final String TAG = "SkinConstraintLayoutHelper";
    private Resources mResources;

    public SkinConstraintLayoutHelper(Resources resources) {
        this.mResources = resources;
    }

    public Map<Integer, ConstraintLayoutParams> parserLayout(XmlResourceParser xmlResourceParser) {
        HashMap map = new HashMap();
        try {
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (eventType == 2) {
                    int attributeCount = xmlResourceParser.getAttributeCount();
                    ConstraintLayoutParams constraintLayoutParams = new ConstraintLayoutParams();
                    for (int i = 0; i < attributeCount; i++) {
                        String attributeName = xmlResourceParser.getAttributeName(i);
                        String attributeValue = xmlResourceParser.getAttributeValue(i);
                        if ("id".equals(attributeName) && attributeValue.startsWith("@")) {
                            constraintLayoutParams.f89id = Integer.parseInt(attributeValue.substring(1));
                        }
                        if ("layout".equals(attributeName) && attributeValue.startsWith("@")) {
                            constraintLayoutParams.layout = Integer.parseInt(attributeValue.substring(1));
                        }
                        if (attributeName.contains("layout_constraint")) {
                            if (attributeValue.startsWith("@")) {
                                attributeValue = attributeValue.substring(1);
                            }
                            constraintLayoutParams.mLayoutConstraintMap.put(attributeName, attributeValue);
                        }
                    }
                    map.put(Integer.valueOf(constraintLayoutParams.f89id), constraintLayoutParams);
                }
                eventType = xmlResourceParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<Integer, ConstraintLayoutParams> parserLayout(int i) {
        Resources resources = this.mResources;
        if (resources == null) {
            return null;
        }
        XmlResourceParser layout = resources.getLayout(i);
        HashMap map = new HashMap();
        try {
            for (int eventType = layout.getEventType(); eventType != 1; eventType = layout.next()) {
                if (eventType == 2) {
                    int attributeCount = layout.getAttributeCount();
                    ConstraintLayoutParams constraintLayoutParams = new ConstraintLayoutParams();
                    for (int i2 = 0; i2 < attributeCount; i2++) {
                        String attributeName = layout.getAttributeName(i2);
                        String attributeValue = layout.getAttributeValue(i2);
                        if ("id".equals(attributeName) && attributeValue.startsWith("@")) {
                            constraintLayoutParams.f89id = Integer.parseInt(attributeValue.substring(1));
                        }
                        if ("layout".equals(attributeName) && attributeValue.startsWith("@")) {
                            constraintLayoutParams.layout = Integer.parseInt(attributeValue.substring(1));
                        }
                        if (attributeName.contains("layout_constraint")) {
                            if (attributeValue.startsWith("@")) {
                                attributeValue = attributeValue.substring(1);
                            }
                            constraintLayoutParams.mLayoutConstraintMap.put(attributeName, attributeValue);
                        }
                    }
                    if (constraintLayoutParams.f89id > 0) {
                        map.put(Integer.valueOf(constraintLayoutParams.f89id), constraintLayoutParams);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void makeLayoutParams(View view, Map<Integer, ConstraintLayoutParams> map) {
        ConstraintLayoutParams constraintLayoutParams = map.get(Integer.valueOf(view.getId()));
        if (constraintLayoutParams == null) {
            SkinLog.m53e(TAG, "makeLayoutParams return id:" + view.getId());
            return;
        }
        ConstraintLayout.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.leftToLeft = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintLeft_toLeftOf"));
            layoutParams.leftToRight = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintLeft_toRightOf"));
            layoutParams.rightToLeft = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintRight_toLeftOf"));
            layoutParams.rightToRight = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintRight_toRightOf"));
            layoutParams.topToTop = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintTop_toTopOf"));
            layoutParams.topToBottom = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintTop_toBottomOf"));
            layoutParams.bottomToTop = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintBottom_toTopOf"));
            layoutParams.bottomToBottom = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintBottom_toBottomOf"));
            layoutParams.startToStart = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintStart_toStartOf"));
            layoutParams.startToEnd = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintStart_toEndOf"));
            layoutParams.endToStart = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintEnd_toStartOf"));
            layoutParams.endToEnd = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintEnd_toEndOf"));
            layoutParams.baselineToBaseline = getIntValue(constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintBaseline_toBaselineOf"));
            layoutParams.horizontalBias = getFloatValue(constraintLayoutParams.mLayoutConstraintMap.getOrDefault("layout_constraintHorizontal_bias", Float.valueOf(0.5f)));
            layoutParams.verticalBias = getFloatValue(constraintLayoutParams.mLayoutConstraintMap.getOrDefault("layout_constraintVertical_bias", Float.valueOf(0.5f)));
            Object obj = constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintHorizontal_chainStyle");
            if (obj != null) {
                layoutParams.horizontalChainStyle = getIntValue(obj);
            }
            Object obj2 = constraintLayoutParams.mLayoutConstraintMap.get("layout_constraintVertical_chainStyle");
            if (obj2 != null) {
                layoutParams.verticalChainStyle = getIntValue(obj2);
                return;
            }
            return;
        }
        SkinLog.m53e(TAG, "ConstraintLayout.LayoutParams is null");
    }

    private int getIntValue(Object obj) {
        if (obj == null) {
            return -1;
        }
        try {
            if (obj instanceof Integer) {
                return ((Integer) obj).intValue();
            }
            return Integer.parseInt((String) obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private float getFloatValue(Object obj) {
        if (obj == null) {
            return 0.0f;
        }
        try {
            if (obj instanceof Float) {
                return ((Float) obj).floatValue();
            }
            return Float.parseFloat((String) obj);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public class ConstraintLayoutParams {

        /* JADX INFO: renamed from: id */
        public int f89id = -1;
        public int layout = 0;
        public Map<String, Object> mLayoutConstraintMap = new HashMap();

        public ConstraintLayoutParams() {
        }

        public String toString() {
            return "id:" + this.f89id + "  layout:" + this.layout;
        }
    }
}
