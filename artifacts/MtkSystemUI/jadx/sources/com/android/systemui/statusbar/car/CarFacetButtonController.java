package com.android.systemui.statusbar.car;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Display;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class CarFacetButtonController {
    protected Context mContext;
    protected CarFacetButton mSelectedFacetButton;
    protected HashMap<String, CarFacetButton> mButtonsByCategory = new HashMap<>();
    protected HashMap<String, CarFacetButton> mButtonsByPackage = new HashMap<>();
    protected HashMap<String, CarFacetButton> mButtonsByComponentName = new HashMap<>();

    public CarFacetButtonController(Context context) {
        this.mContext = context;
    }

    public void addFacetButton(CarFacetButton facetButton) {
        String[] categories = facetButton.getCategories();
        for (String str : categories) {
            this.mButtonsByCategory.put(str, facetButton);
        }
        String[] facetPackages = facetButton.getFacetPackages();
        for (String str2 : facetPackages) {
            this.mButtonsByPackage.put(str2, facetButton);
        }
        String[] componentNames = facetButton.getComponentName();
        for (String str3 : componentNames) {
            this.mButtonsByComponentName.put(str3, facetButton);
        }
        this.mSelectedFacetButton = facetButton;
    }

    public void removeAll() {
        this.mButtonsByCategory.clear();
        this.mButtonsByPackage.clear();
        this.mButtonsByComponentName.clear();
        this.mSelectedFacetButton = null;
    }

    public void taskChanged(List<ActivityManager.StackInfo> stackInfoList) {
        String category;
        int displayId = getDisplayId();
        ActivityManager.StackInfo validStackInfo = null;
        for (ActivityManager.StackInfo stackInfo : stackInfoList) {
            if (displayId == -1 || displayId == stackInfo.displayId) {
                if (stackInfo.topActivity != null) {
                    validStackInfo = stackInfo;
                    break;
                }
            }
        }
        if (validStackInfo == null) {
            return;
        }
        if (this.mSelectedFacetButton != null) {
            this.mSelectedFacetButton.setSelected(false);
        }
        String packageName = validStackInfo.topActivity.getPackageName();
        CarFacetButton facetButton = findFacetButtongByComponentName(validStackInfo.topActivity);
        if (facetButton == null) {
            facetButton = this.mButtonsByPackage.get(packageName);
        }
        if (facetButton == null && (category = getPackageCategory(packageName)) != null) {
            facetButton = this.mButtonsByCategory.get(category);
        }
        if (facetButton != null && facetButton.getVisibility() == 0) {
            facetButton.setSelected(true);
            this.mSelectedFacetButton = facetButton;
        }
    }

    private int getDisplayId() {
        Display display;
        if (this.mSelectedFacetButton != null && (display = this.mSelectedFacetButton.getDisplay()) != null) {
            return display.getDisplayId();
        }
        return -1;
    }

    private CarFacetButton findFacetButtongByComponentName(ComponentName componentName) {
        CarFacetButton button = this.mButtonsByComponentName.get(componentName.flattenToShortString());
        return button != null ? button : this.mButtonsByComponentName.get(componentName.flattenToString());
    }

    protected String getPackageCategory(String packageName) {
        PackageManager pm = this.mContext.getPackageManager();
        Set<String> supportedCategories = this.mButtonsByCategory.keySet();
        for (String category : supportedCategories) {
            Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory(category);
            List<ResolveInfo> list = pm.queryIntentActivities(intent, 0);
            if (list.size() > 0) {
                this.mButtonsByPackage.put(packageName, this.mButtonsByCategory.get(category));
                return category;
            }
        }
        return null;
    }
}
