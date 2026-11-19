package com.hcn.skin.support.resources;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import com.hcn.skin.support.utils.SkinLog;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinConstraintLayoutHelper {
    private Resources mResources;

    public SkinConstraintLayoutHelper(Resources resources) {
        this.mResources = resources;
    }

    public Map<Integer, ConstraintLayoutParams> parserLayout(int layoutId) {
        if (this.mResources == null) {
            return null;
        }
        XmlResourceParser xmlParser = this.mResources.getLayout(layoutId);
        Map<Integer, ConstraintLayoutParams> list = new HashMap<>();
        try {
            for (int event = xmlParser.getEventType(); event != 1; event = xmlParser.next()) {
                if (event != 0) {
                    switch (event) {
                        case 2:
                            int size = xmlParser.getAttributeCount();
                            ConstraintLayoutParams config = new ConstraintLayoutParams();
                            for (int i = 0; i < size; i++) {
                                String attributeName = xmlParser.getAttributeName(i);
                                String attributeValue = xmlParser.getAttributeValue(i);
                                if ("id".equals(attributeName) && attributeValue.startsWith("@")) {
                                    config.id = Integer.parseInt(attributeValue.substring(1));
                                }
                                if ("layout".equals(attributeName) && attributeValue.startsWith("@")) {
                                    config.layout = Integer.parseInt(attributeValue.substring(1));
                                }
                                if (attributeName.contains("layout_constraint")) {
                                    if (attributeValue.startsWith("@")) {
                                        attributeValue = attributeValue.substring(1);
                                    }
                                    config.mLayoutConstraintMap.put(attributeName, attributeValue);
                                }
                            }
                            if (config.id > 0) {
                                list.put(Integer.valueOf(config.id), config);
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void makeLayoutParams(View target, Map<Integer, ConstraintLayoutParams> rules) {
        ConstraintLayoutParams config = rules.get(Integer.valueOf(target.getId()));
        if (config == null) {
            SkinLog.e("SkinConstraintLayoutHelper", "makeLayoutParams return id:" + target.getId());
            return;
        }
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) target.getLayoutParams();
        if (lp != null) {
            lp.leftToLeft = getIntValue(config.mLayoutConstraintMap.get("layout_constraintLeft_toLeftOf"));
            lp.leftToRight = getIntValue(config.mLayoutConstraintMap.get("layout_constraintLeft_toRightOf"));
            lp.rightToLeft = getIntValue(config.mLayoutConstraintMap.get("layout_constraintRight_toLeftOf"));
            lp.rightToRight = getIntValue(config.mLayoutConstraintMap.get("layout_constraintRight_toRightOf"));
            lp.topToTop = getIntValue(config.mLayoutConstraintMap.get("layout_constraintTop_toTopOf"));
            lp.topToBottom = getIntValue(config.mLayoutConstraintMap.get("layout_constraintTop_toBottomOf"));
            lp.bottomToTop = getIntValue(config.mLayoutConstraintMap.get("layout_constraintBottom_toTopOf"));
            lp.bottomToBottom = getIntValue(config.mLayoutConstraintMap.get("layout_constraintBottom_toBottomOf"));
            lp.startToStart = getIntValue(config.mLayoutConstraintMap.get("layout_constraintStart_toStartOf"));
            lp.startToEnd = getIntValue(config.mLayoutConstraintMap.get("layout_constraintStart_toEndOf"));
            lp.endToStart = getIntValue(config.mLayoutConstraintMap.get("layout_constraintEnd_toStartOf"));
            lp.endToEnd = getIntValue(config.mLayoutConstraintMap.get("layout_constraintEnd_toEndOf"));
            lp.baselineToBaseline = getIntValue(config.mLayoutConstraintMap.get("layout_constraintBaseline_toBaselineOf"));
            lp.horizontalBias = getFloatValue(config.mLayoutConstraintMap.getOrDefault("layout_constraintHorizontal_bias", Float.valueOf(0.5f)));
            lp.verticalBias = getFloatValue(config.mLayoutConstraintMap.getOrDefault("layout_constraintVertical_bias", Float.valueOf(0.5f)));
            Object horizontal_chainStyle = config.mLayoutConstraintMap.get("layout_constraintHorizontal_chainStyle");
            if (horizontal_chainStyle != null) {
                lp.horizontalChainStyle = getIntValue(horizontal_chainStyle);
            }
            Object vertical_chainStyle = config.mLayoutConstraintMap.get("layout_constraintVertical_chainStyle");
            if (vertical_chainStyle != null) {
                lp.verticalChainStyle = getIntValue(vertical_chainStyle);
                return;
            }
            return;
        }
        SkinLog.e("SkinConstraintLayoutHelper", "ConstraintLayout.LayoutParams is null");
    }

    private int getIntValue(Object obj) {
        if (obj != null) {
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
        return -1;
    }

    private float getFloatValue(Object obj) {
        if (obj != null) {
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
        return 0.0f;
    }

    public class ConstraintLayoutParams {
        public int id = -1;
        public int layout = 0;
        public Map<String, Object> mLayoutConstraintMap = new HashMap();

        public ConstraintLayoutParams() {
        }

        public String toString() {
            return "id:" + this.id + "  layout:" + this.layout;
        }
    }
}
