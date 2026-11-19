package com.hcn.skin.support.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.utils.SkinLog;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SkinCompatResources {
    private static volatile SkinCompatResources sInstance;
    private Context mSkinContext = null;
    private Resources mSkinResources = null;
    private String mSkinPkgName = "";

    public static SkinCompatResources getInstance() {
        if (sInstance == null) {
            synchronized (SkinCompatResources.class) {
                if (sInstance == null) {
                    sInstance = new SkinCompatResources();
                }
            }
        }
        return sInstance;
    }

    public void setupSkin(Resources resources, String pkgName) {
        setupSkin(null, resources, pkgName);
    }

    public void setupSkin(Context skinContext, Resources resources, String pkgName) {
        if (resources == null || TextUtils.isEmpty(pkgName)) {
            return;
        }
        SkinLog.i("SkinCompatResources", "setupSkin " + pkgName);
        this.mSkinContext = skinContext;
        this.mSkinResources = resources;
        this.mSkinPkgName = pkgName;
        Context context = SkinCompatManager.getInstance().getContext();
        if (this.mSkinContext == null) {
            SkinLog.e("SkinCompatResources", "new SkinThemeContext");
            this.mSkinContext = new SkinThemeContext(context);
        }
    }

    public void resetSkin() {
        SkinLog.i("SkinCompatResources", "resetSkin");
        this.mSkinContext = null;
        this.mSkinResources = null;
        this.mSkinPkgName = "";
    }

    public Resources getSkinResources() {
        return this.mSkinResources;
    }

    public String getSkinPkgName() {
        return this.mSkinPkgName;
    }

    public void handleView(View target) {
        SparseIntArray array = new SparseIntArray();
        buildIdRules(target, array);
    }

    private boolean isViewIdGenerated(int id) {
        return ((-16777216) & id) == 0 && (16777215 & id) != 0;
    }

    @SuppressLint({"ResourceType"})
    private void buildIdRules(View v, SparseIntArray array) {
        try {
            if (v.getId() != -1 && !isViewIdGenerated(v.getId())) {
                String idName = this.mSkinResources.getResourceEntryName(v.getId());
                Context context = SkinCompatManager.getInstance().getContext();
                int mappedId = context.getResources().getIdentifier(idName, "id", context.getPackageName());
                SkinLog.v("SkinCompatResources", "----View class:" + v.getClass().getName() + " name:" + idName + " id:" + Integer.toHexString(v.getId()) + " mappedId:" + Integer.toHexString(mappedId));
                if (mappedId > 0) {
                    array.put(v.getId(), mappedId);
                }
            }
            if (v instanceof ViewGroup) {
                ViewGroup vp = (ViewGroup) v;
                int childCount = vp.getChildCount();
                SkinLog.i("SkinCompatResources", v.getId() + "(#" + Integer.toHexString(v.getId()) + ") " + v.toString() + " contain " + childCount + " child");
                for (int i = 0; i < childCount; i++) {
                    buildIdRules(vp.getChildAt(i), array);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getSkinResId(int resId) {
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            String type = context.getResources().getResourceTypeName(resId);
            String name = context.getResources().getResourceEntryName(resId);
            if (TextUtils.isEmpty(name) || this.mSkinResources == null) {
                return 0;
            }
            int targetId = this.mSkinResources.getIdentifier(name, type, this.mSkinPkgName);
            return targetId;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getSkinResId(String name, String type) {
        try {
            if (TextUtils.isEmpty(name) || this.mSkinResources == null) {
                return 0;
            }
            int targetId = this.mSkinResources.getIdentifier(name, type, this.mSkinPkgName);
            return targetId;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getAppResId(String name, String type) {
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            if (TextUtils.isEmpty(name) || resources == null) {
                return 0;
            }
            int targetId = resources.getIdentifier(name, type, packageName);
            return targetId;
        } catch (Exception e) {
            return 0;
        }
    }

    public int getId(int resId) {
        int targetId;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            String type = context.getResources().getResourceTypeName(resId);
            String name = context.getResources().getResourceEntryName(resId);
            if (this.mSkinResources != null && (targetId = this.mSkinResources.getIdentifier(name, type, this.mSkinPkgName)) > 0) {
                return targetId;
            }
            return resId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getViewId(View v) {
        int resId = v.getId();
        if (resId <= 0) {
            return resId;
        }
        try {
            if (this.mSkinResources != null) {
                String name = this.mSkinResources.getResourceEntryName(resId);
                String type = this.mSkinResources.getResourceTypeName(resId);
                Context context = SkinCompatManager.getInstance().getContext();
                return context.getResources().getIdentifier(name, type, context.getPackageName());
            }
            return resId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public View getLayout(String name) {
        int targetId;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            if (this.mSkinResources != null && (targetId = this.mSkinResources.getIdentifier(name, "layout", this.mSkinPkgName)) != 0) {
                View layoutView = LayoutInflater.from(this.mSkinContext).inflate(targetId, (ViewGroup) null);
                handleView(layoutView);
                if (layoutView != null && layoutView.getClass().getName().endsWith("ConstraintLayout")) {
                    buildLayoutParams(layoutView, targetId);
                }
                return layoutView;
            }
            int appId = context.getResources().getIdentifier(name, "layout", context.getPackageName());
            if (appId != 0) {
                View layoutView2 = LayoutInflater.from(context).inflate(appId, (ViewGroup) null);
                handleView(layoutView2);
                return layoutView2;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildLayoutParams(View target, int layoutId) {
        if (this.mSkinResources == null) {
            SkinLog.e("SkinCompatResources", "buildLayoutParams return, mSkinResources is null ");
            return;
        }
        if (layoutId == 0) {
            return;
        }
        try {
            if (target instanceof ConstraintLayout) {
                SkinLog.d("SkinCompatResources", "buildLayoutParams with " + target.getId() + " " + target + " layoutId:" + layoutId);
                SkinConstraintLayoutHelper helper = new SkinConstraintLayoutHelper(this.mSkinResources);
                Map<Integer, SkinConstraintLayoutHelper.ConstraintLayoutParams> params = new HashMap<>();
                if (layoutId > 0) {
                    params = helper.parserLayout(layoutId);
                }
                int count = ((ViewGroup) target).getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = ((ViewGroup) target).getChildAt(i);
                    if (params.size() > 0 && params.get(Integer.valueOf(child.getId())) != null) {
                        buildLayoutParams(child, params.get(Integer.valueOf(child.getId())).layout);
                        helper.makeLayoutParams(child, params);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(int resId) {
        try {
            int realId = getSkinResId(resId);
            if (realId > 0) {
                return this.mSkinResources.getString(realId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getString(resId);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getColor(String name) {
        try {
            int realId = getSkinResId(name, "color");
            if (realId > 0) {
                return this.mSkinResources.getColor(realId, this.mSkinContext.getTheme());
            }
            int realId2 = getAppResId(name, "color");
            if (realId2 > 0) {
                Context context = SkinCompatManager.getInstance().getContext();
                return context.getResources().getColor(realId2, context.getTheme());
            }
            return 0;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Drawable getDrawable(int resId) {
        try {
            int realId = getSkinResId(resId);
            if (realId > 0) {
                return this.mSkinResources.getDrawable(realId, this.mSkinContext.getTheme());
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getDrawable(resId, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getDrawable(String name) {
        try {
            int realId = getSkinResId(name, "drawable");
            if (realId > 0) {
                return this.mSkinResources.getDrawable(realId, this.mSkinContext.getTheme());
            }
            int realId2 = getAppResId(name, "drawable");
            if (realId2 > 0) {
                Context context = SkinCompatManager.getInstance().getContext();
                return context.getResources().getDrawable(realId2, context.getTheme());
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap(String name) {
        try {
            int realId = getSkinResId(name, "drawable");
            if (realId > 0) {
                return BitmapFactory.decodeResource(this.mSkinResources, realId);
            }
            int realId2 = getAppResId(name, "drawable");
            if (realId2 > 0) {
                Context context = SkinCompatManager.getInstance().getContext();
                return BitmapFactory.decodeResource(context.getResources(), realId2);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getInteger(String name) {
        try {
            int realId = getSkinResId(name, "integer");
            if (realId > 0) {
                return this.mSkinResources.getInteger(realId);
            }
            int realId2 = getAppResId(name, "integer");
            if (realId2 > 0) {
                Context context = SkinCompatManager.getInstance().getContext();
                return context.getResources().getInteger(realId2);
            }
            return 0;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public XmlResourceParser getXml(String name) {
        SkinLog.i("SkinCompatResources", "getXml name:" + name + " mSkinPkgName:" + this.mSkinPkgName);
        try {
            int realId = getSkinResId(name, "xml");
            if (realId > 0) {
                return this.mSkinResources.getXml(realId);
            }
            int realId2 = getAppResId(name, "xml");
            if (realId2 > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getXml(realId2);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class SkinThemeContext extends ContextThemeWrapper {
        public SkinThemeContext(Context base) {
            if (base instanceof ContextThemeWrapper) {
                attachBaseContext(((ContextThemeWrapper) base).getBaseContext());
            } else {
                attachBaseContext(base);
            }
        }

        @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
        public Resources getResources() {
            return SkinCompatResources.this.mSkinResources;
        }
    }
}
