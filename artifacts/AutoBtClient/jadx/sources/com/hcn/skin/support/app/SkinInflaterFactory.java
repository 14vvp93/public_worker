package com.hcn.skin.support.app;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.hcn.skin.support.app.attr.base.AttrFactory;
import com.hcn.skin.support.app.attr.base.SkinAttr;
import com.hcn.skin.support.utils.SkinListUtils;
import com.hcn.skin.support.utils.SkinLog;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SkinInflaterFactory implements LayoutInflater.Factory2 {
    private static final String TAG = "SkinInflaterFactory";
    private final Context mContext;
    private List<SkinItem> mSkinItems = new ArrayList();

    private SkinInflaterFactory(Context context) {
        this.mContext = context;
    }

    public static SkinInflaterFactory create(Context context) {
        return new SkinInflaterFactory(context);
    }

    @Override // android.view.LayoutInflater.Factory2
    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return onCreateView(str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        View viewCreateViewFromTag = SkinViewInflater.createViewFromTag(context, str, attributeSet);
        if (viewCreateViewFromTag == null) {
            return null;
        }
        parseSkinAttr(context, attributeSet, viewCreateViewFromTag);
        return viewCreateViewFromTag;
    }

    private void parseSkinAttr(Context context, AttributeSet attributeSet, View view) {
        int i;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i3 < attributeSet.getAttributeCount()) {
            String attributeName = attributeSet.getAttributeName(i3);
            String attributeValue = attributeSet.getAttributeValue(i3);
            if ("style".equals(attributeName)) {
                TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, new int[]{R.attr.textColor, R.attr.background}, i2, i2);
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(i2, -1);
                int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, -1);
                if (resourceId != -1) {
                    String resourceEntryName = context.getResources().getResourceEntryName(resourceId);
                    String resourceTypeName = context.getResources().getResourceTypeName(resourceId);
                    SkinAttr skinAttr = AttrFactory.get("textColor", resourceId, resourceEntryName, resourceTypeName);
                    SkinLog.m57v(TAG, "    textColor in style is supported:\n    resource id:" + resourceId + "\n    attrName:" + attributeName + "\n    attrValue:" + attributeValue + "\n    entryName:" + resourceEntryName + "\n    typeName:" + resourceTypeName);
                    if (skinAttr != null) {
                        arrayList.add(skinAttr);
                    }
                    i = -1;
                } else {
                    i = -1;
                }
                if (resourceId2 != i) {
                    String resourceEntryName2 = context.getResources().getResourceEntryName(resourceId2);
                    String resourceTypeName2 = context.getResources().getResourceTypeName(resourceId2);
                    SkinAttr skinAttr2 = AttrFactory.get("background", resourceId2, resourceEntryName2, resourceTypeName2);
                    SkinLog.m57v(TAG, "    background in style is supported:\n    resource id:" + resourceId2 + "\n    attrName:" + attributeName + "\n    attrValue:" + attributeValue + "\n    entryName:" + resourceEntryName2 + "\n    typeName:" + resourceTypeName2);
                    if (skinAttr2 != null) {
                        arrayList.add(skinAttr2);
                    }
                }
                typedArrayObtainStyledAttributes.recycle();
            } else {
                i3 = i3;
                if (AttrFactory.isSupportedAttr(attributeName) && attributeValue.startsWith("@")) {
                    try {
                        int i4 = Integer.parseInt(attributeValue.substring(1));
                        if (i4 != 0) {
                            String resourceEntryName3 = context.getResources().getResourceEntryName(i4);
                            String resourceTypeName3 = context.getResources().getResourceTypeName(i4);
                            SkinAttr skinAttr3 = AttrFactory.get(attributeName, i4, resourceEntryName3, resourceTypeName3);
                            SkinLog.m57v(TAG, "    " + attributeName + " is supported:\n    resource id:" + i4 + "\n    attrName:" + attributeName + "\n    attrValue:" + attributeValue + "\n    entryName:" + resourceEntryName3 + "\n    typeName:" + resourceTypeName3);
                            if (skinAttr3 != null) {
                                arrayList.add(skinAttr3);
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    SkinLog.m55i(TAG, "    " + attributeName + " is not supported!");
                }
            }
            i3++;
            i2 = 0;
        }
        if (SkinListUtils.isEmpty(arrayList)) {
            return;
        }
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        skinItem.attrs = arrayList;
        this.mSkinItems.add(skinItem);
        skinItem.apply();
    }

    public void applySkin() {
        if (SkinListUtils.isEmpty(this.mSkinItems)) {
            return;
        }
        for (SkinItem skinItem : this.mSkinItems) {
            if (skinItem.view != null) {
                skinItem.apply();
            }
        }
    }
}
