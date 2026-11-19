package com.hcn.skin.support.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import com.autochips.bluetooth.skin.SkinID;
import com.hcn.skin.share.ISkinCompatResources;
import com.hcn.skin.support.SkinCompatManager;
import com.hcn.skin.support.SkinResourcesConfig;
import com.hcn.skin.support.utils.SkinLog;
import com.hcn.skinx_night.NightSkinX;
import java.util.HashMap;
import java.util.Map;
import kotlin.NotImplementedError;

/* JADX INFO: loaded from: classes.dex */
public class SkinCompatResources implements ISkinCompatResources {
    private static final String TAG = "SkinCompatResources";
    private static volatile SkinCompatResources sInstance;
    private Context mSkinContext = null;
    private boolean mSkinInflaterFactorySet = false;
    private Resources mSkinResources = null;
    private String mSkinPkgName = SkinID.SKIN_NONE;

    private boolean isViewIdGenerated(int i) {
        return ((-16777216) & i) == 0 && (i & ViewCompat.MEASURED_SIZE_MASK) != 0;
    }

    public static SkinCompatResources getInstance() {
        if (sInstance == null) {
            synchronized (SkinCompatResources.class) {
                Class<? extends SkinCompatResources> skinResourcesClass = SkinResourcesConfig.getSkinResourcesClass();
                if (skinResourcesClass != null) {
                    try {
                        sInstance = skinResourcesClass.newInstance();
                    } catch (IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                } else if (sInstance == null) {
                    sInstance = new SkinCompatResources();
                }
            }
        }
        return sInstance;
    }

    public void setupSkin(Resources resources, String str) {
        setupSkin(null, resources, str);
    }

    public void setupSkin(Context context, Resources resources, String str) {
        if (resources == null || TextUtils.isEmpty(str)) {
            return;
        }
        SkinLog.m55i(TAG, "setupSkin " + str);
        this.mSkinContext = context;
        this.mSkinResources = resources;
        this.mSkinPkgName = str;
        Context context2 = SkinCompatManager.getInstance().getContext();
        if (this.mSkinContext == null) {
            SkinLog.m53e(TAG, "new SkinThemeContext");
            this.mSkinContext = new SkinThemeContext(context2);
        }
    }

    public void resetSkin() {
        SkinLog.m55i(TAG, "resetSkin");
        this.mSkinContext = null;
        this.mSkinResources = null;
        this.mSkinPkgName = SkinID.SKIN_NONE;
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public Context getSkinContext() {
        return this.mSkinContext;
    }

    public boolean skinInflaterFactorySet() {
        return this.mSkinInflaterFactorySet;
    }

    public void skinInflaterFactorySet(LayoutInflater.Factory2 factory2) {
        if (this.mSkinInflaterFactorySet) {
            Log.v(TAG, "A factory has already been set on skin LayoutInflater!");
        } else {
            this.mSkinInflaterFactorySet = true;
            LayoutInflater.from(this.mSkinContext).setFactory2(factory2);
        }
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public Context getContext() {
        if (this.mSkinResources != null) {
            return this.mSkinContext;
        }
        return SkinCompatManager.getInstance().getContext();
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public Resources getSkinResources() {
        return this.mSkinResources;
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public String getSkinPkgName() {
        return this.mSkinPkgName;
    }

    public void handleView(View view) {
        buildIdRules(view, new SparseIntArray());
    }

    private void buildIdRules(View view, SparseIntArray sparseIntArray) {
        Resources resources;
        try {
            if (view.getId() != -1 && !isViewIdGenerated(view.getId()) && (resources = this.mSkinResources) != null) {
                String resourceEntryName = resources.getResourceEntryName(view.getId());
                Context context = SkinCompatManager.getInstance().getContext();
                int identifier = context.getResources().getIdentifier(resourceEntryName, "id", context.getPackageName());
                SkinLog.m57v(TAG, "----View class:" + view.getClass().getName() + " name:" + resourceEntryName + " id:" + Integer.toHexString(view.getId()) + " mappedId:" + Integer.toHexString(identifier));
                if (identifier > 0) {
                    sparseIntArray.put(view.getId(), identifier);
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                SkinLog.m55i(TAG, view.getId() + "(#" + Integer.toHexString(view.getId()) + ") " + view.toString() + " contain " + childCount + " child");
                for (int i = 0; i < childCount; i++) {
                    buildIdRules(viewGroup.getChildAt(i), sparseIntArray);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void buildInflateRules(View view, SparseIntArray sparseIntArray) {
        int[] rules;
        int i;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null || !(layoutParams instanceof RelativeLayout.LayoutParams) || (rules = ((RelativeLayout.LayoutParams) layoutParams).getRules()) == null) {
            return;
        }
        int length = rules.length;
        SkinLog.m55i(TAG, "Rules length " + length);
        for (int i2 = 0; i2 < length; i2++) {
            if (rules[i2] > 0 && (i = sparseIntArray.get(rules[i2])) > 0) {
                SkinLog.m57v(TAG, "Rules[" + i2 + "]: Mapped from: " + Integer.toHexString(rules[i2]) + "  to  " + Integer.toHexString(i));
                rules[i2] = i;
            }
        }
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public int getSkinResId(Context context, int i) {
        Resources resources;
        try {
            String resourceTypeName = context.getResources().getResourceTypeName(i);
            String resourceEntryName = context.getResources().getResourceEntryName(i);
            if (!TextUtils.isEmpty(resourceEntryName) && (resources = this.mSkinResources) != null) {
                return resources.getIdentifier(resourceEntryName, resourceTypeName, this.mSkinPkgName);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public int getSkinResId(int i) {
        Resources resources;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            String resourceTypeName = context.getResources().getResourceTypeName(i);
            String resourceEntryName = context.getResources().getResourceEntryName(i);
            if (!TextUtils.isEmpty(resourceEntryName) && (resources = this.mSkinResources) != null) {
                return resources.getIdentifier(resourceEntryName, resourceTypeName, this.mSkinPkgName);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public int getSkinResId(int i, String str) {
        Resources resources;
        try {
            String resourceEntryName = SkinCompatManager.getInstance().getContext().getResources().getResourceEntryName(i);
            if (!TextUtils.isEmpty(resourceEntryName) && (resources = this.mSkinResources) != null) {
                return resources.getIdentifier(resourceEntryName, str, this.mSkinPkgName);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    @Override // com.hcn.skin.share.ISkinCompatResources
    public int getSkinResId(String str, String str2) {
        Resources resources;
        try {
            if (!TextUtils.isEmpty(str) && (resources = this.mSkinResources) != null) {
                return resources.getIdentifier(str, str2, this.mSkinPkgName);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public int getIdentifier(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            Resources resources = this.mSkinResources;
            if (resources != null) {
                return resources.getIdentifier(str, str2, this.mSkinPkgName);
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getIdentifier(str, str2, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getTargetResIdStrictly(int i) {
        Resources resources;
        try {
            String resourceName = SkinCompatManager.getInstance().getContext().getResources().getResourceName(i);
            if (!TextUtils.isEmpty(resourceName) && (resources = this.mSkinResources) != null) {
                return resources.getIdentifier(resourceName, null, null);
            }
        } catch (Resources.NotFoundException unused) {
        }
        return 0;
    }

    public int getAppResId(String str, String str2) {
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            if (!TextUtils.isEmpty(str) && resources != null) {
                return resources.getIdentifier(str, str2, packageName);
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    public int getId(int i) {
        int identifier;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            String resourceTypeName = context.getResources().getResourceTypeName(i);
            String resourceEntryName = context.getResources().getResourceEntryName(i);
            Resources resources = this.mSkinResources;
            return (resources == null || (identifier = resources.getIdentifier(resourceEntryName, resourceTypeName, this.mSkinPkgName)) <= 0) ? i : identifier;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getViewId(View view) {
        int id = view.getId();
        if (id <= 0) {
            return id;
        }
        try {
            Resources resources = this.mSkinResources;
            if (resources == null) {
                return id;
            }
            String resourceEntryName = resources.getResourceEntryName(id);
            if (this.mSkinResources.getIdentifier(resourceEntryName, "id", this.mSkinPkgName) <= 0) {
                return id;
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getIdentifier(resourceEntryName, "id", context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }
    }

    public View getLayout(Context context, int i, LayoutInflater.Factory2 factory2) {
        if (this.mSkinResources != null) {
            try {
                int skinResId = getSkinResId(context, i);
                if (skinResId <= 0) {
                    return null;
                }
                SkinLog.m57v(TAG, "getLayout skin " + Integer.toHexString(skinResId) + " " + this.mSkinPkgName);
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mSkinContext);
                if (!this.mSkinInflaterFactorySet && factory2 != null) {
                    this.mSkinInflaterFactorySet = true;
                    layoutInflaterFrom.setFactory2(factory2);
                }
                return layoutInflaterFrom.inflate(skinResId, (ViewGroup) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SkinLog.m53e(TAG, "getLayout mSkinResources == null ");
        return null;
    }

    public View getLayout(int i) {
        return getLayout(i, (ViewGroup) null, (LayoutInflater.Factory2) null);
    }

    public View getLayout(int i, ViewGroup viewGroup, LayoutInflater.Factory2 factory2) {
        int skinResId;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            if (this.mSkinResources != null && (skinResId = getSkinResId(context, i)) > 0) {
                SkinLog.m57v(TAG, "getLayout skin " + Integer.toHexString(skinResId) + " " + this.mSkinPkgName);
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mSkinContext);
                if (!this.mSkinInflaterFactorySet && factory2 != null) {
                    this.mSkinInflaterFactorySet = true;
                    layoutInflaterFrom.setFactory2(factory2);
                }
                layoutInflaterFrom.inflate(skinResId, viewGroup);
            }
            if (i != 0) {
                return LayoutInflater.from(context).inflate(i, viewGroup);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public View getLayout(String str) {
        return getLayout(str, (ViewGroup) null, (LayoutInflater.Factory2) null);
    }

    public View getLayout(String str, ViewGroup viewGroup, LayoutInflater.Factory2 factory2) {
        int identifier;
        try {
            Context context = SkinCompatManager.getInstance().getContext();
            Resources resources = this.mSkinResources;
            if (resources != null && (identifier = resources.getIdentifier(str, "layout", this.mSkinPkgName)) != 0) {
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(this.mSkinContext);
                if (!this.mSkinInflaterFactorySet && factory2 != null) {
                    this.mSkinInflaterFactorySet = true;
                    layoutInflaterFrom.setFactory2(factory2);
                }
                return layoutInflaterFrom.inflate(identifier, viewGroup);
            }
            int identifier2 = context.getResources().getIdentifier(str, "layout", context.getPackageName());
            if (identifier2 != 0) {
                return LayoutInflater.from(context).inflate(identifier2, viewGroup);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void buildLayoutParams(View view, int i) {
        if (this.mSkinResources == null) {
            SkinLog.m53e(TAG, "buildLayoutParams return, mSkinResources is null ");
            return;
        }
        if (i == 0) {
            return;
        }
        try {
            if (view instanceof ConstraintLayout) {
                SkinLog.m51d(TAG, "buildLayoutParams with " + view.getId() + " " + view + " layoutId:" + i);
                SkinConstraintLayoutHelper skinConstraintLayoutHelper = new SkinConstraintLayoutHelper(this.mSkinResources);
                Map<Integer, SkinConstraintLayoutHelper.ConstraintLayoutParams> map = new HashMap<>();
                if (i > 0) {
                    map = skinConstraintLayoutHelper.parserLayout(i);
                }
                int childCount = ((ViewGroup) view).getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = ((ViewGroup) view).getChildAt(i2);
                    if (map.size() > 0 && map.get(Integer.valueOf(childAt.getId())) != null) {
                        buildLayoutParams(childAt, map.get(Integer.valueOf(childAt.getId())).layout);
                        skinConstraintLayoutHelper.makeLayoutParams(childAt, map);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkIfNeedBuildLayoutParams(View view, int i) {
        if (this.mSkinResources != null && (view instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            SkinLog.m55i(TAG, view.getId() + "(#" + Integer.toHexString(view.getId()) + ") " + view.toString() + " contain " + childCount + " child");
            if (childCount <= 0) {
                return;
            }
            View childAt = viewGroup.getChildAt(0);
            if (childAt.getId() != -1) {
                SkinLog.m57v(TAG, "View class:" + childAt.getClass().getName() + " id:" + childAt.getId() + " name:" + childAt.getResources().getResourceEntryName(childAt.getId()));
                if (childAt instanceof ConstraintLayout) {
                    SkinLog.m55i(TAG, "view is ConstraintLayout");
                    buildLayoutParams(childAt, i);
                }
            }
        }
    }

    public CharSequence getText(int i) {
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return this.mSkinResources.getText(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getText(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CharSequence getText(String str) {
        try {
            int skinResId = getSkinResId(str, "string");
            if (skinResId > 0) {
                return this.mSkinResources.getText(skinResId);
            }
            int appResId = getAppResId(str, "string");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getText(appResId);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getString(int i, boolean z) {
        return getString(i);
    }

    public String getString(int i) {
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return this.mSkinResources.getString(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getString(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getString(String str) {
        try {
            int skinResId = getSkinResId(str, "string");
            if (skinResId > 0) {
                return this.mSkinResources.getString(skinResId);
            }
            int appResId = getAppResId(str, "string");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getString(appResId);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getStringArray(int i) {
        throw new NotImplementedError("function 'getStringArray' not implemented!");
    }

    public int[] getIntArray(int i) {
        throw new NotImplementedError("function 'getIntArray' not implemented!");
    }

    public int getColor(int i) {
        try {
            int skinResId = getSkinResId(i, NightSkinX.ResTypeName.COLOR);
            if (skinResId > 0) {
                return this.mSkinResources.getColor(skinResId, this.mSkinContext.getTheme());
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getColor(i, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getColor(String str) {
        try {
            int skinResId = getSkinResId(str, NightSkinX.ResTypeName.COLOR);
            if (skinResId > 0) {
                return this.mSkinResources.getColor(skinResId, this.mSkinContext.getTheme());
            }
            int appResId = getAppResId(str, NightSkinX.ResTypeName.COLOR);
            if (appResId <= 0) {
                return 0;
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getColor(appResId, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ColorStateList getColorStateList(int i) {
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return this.mSkinResources.getColorStateList(skinResId, this.mSkinContext.getTheme());
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getColorStateList(i, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ColorStateList getColorStateList(String str) {
        try {
            int skinResId = getSkinResId(str, NightSkinX.ResTypeName.COLOR);
            if (skinResId > 0) {
                return this.mSkinResources.getColorStateList(skinResId, this.mSkinContext.getTheme());
            }
            int appResId = getAppResId(str, NightSkinX.ResTypeName.COLOR);
            if (appResId <= 0) {
                return null;
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getColorStateList(appResId, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getDrawable(int i) {
        try {
            int skinResId = getSkinResId(i, NightSkinX.ResTypeName.DRAWABLE);
            if (skinResId > 0) {
                return this.mSkinResources.getDrawable(skinResId, this.mSkinContext.getTheme());
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getDrawable(i, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getDrawable(String str) {
        try {
            int skinResId = getSkinResId(str, NightSkinX.ResTypeName.DRAWABLE);
            if (skinResId > 0) {
                return this.mSkinResources.getDrawable(skinResId, this.mSkinContext.getTheme());
            }
            int appResId = getAppResId(str, NightSkinX.ResTypeName.DRAWABLE);
            if (appResId <= 0) {
                return null;
            }
            Context context = SkinCompatManager.getInstance().getContext();
            return context.getResources().getDrawable(appResId, context.getTheme());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap(int i) {
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return BitmapFactory.decodeResource(this.mSkinResources, skinResId);
            }
            return BitmapFactory.decodeResource(SkinCompatManager.getInstance().getContext().getResources(), i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap(String str) {
        try {
            int skinResId = getSkinResId(str, NightSkinX.ResTypeName.DRAWABLE);
            if (skinResId > 0) {
                return BitmapFactory.decodeResource(this.mSkinResources, skinResId);
            }
            int appResId = getAppResId(str, NightSkinX.ResTypeName.DRAWABLE);
            if (appResId > 0) {
                return BitmapFactory.decodeResource(SkinCompatManager.getInstance().getContext().getResources(), appResId);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getInteger(int i) {
        try {
            int skinResId = getSkinResId(i);
            if (skinResId > 0) {
                return this.mSkinResources.getInteger(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getInteger(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getInteger(String str) {
        try {
            int skinResId = getSkinResId(str, "integer");
            if (skinResId > 0) {
                return this.mSkinResources.getInteger(skinResId);
            }
            int appResId = getAppResId(str, "integer");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getInteger(appResId);
            }
            return 0;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimension(int i) {
        try {
            int skinResId = getSkinResId(i, "dimen");
            if (skinResId > 0) {
                return this.mSkinResources.getDimension(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getDimension(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getDimension(String str) {
        try {
            int skinResId = getSkinResId(str, "dimen");
            if (skinResId > 0) {
                return this.mSkinResources.getDimension(skinResId);
            }
            int appResId = getAppResId(str, "dimen");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getDimension(appResId);
            }
            return 0.0f;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public boolean getBoolean(int i) {
        try {
            int skinResId = getSkinResId(i, "bool");
            if (skinResId > 0) {
                return this.mSkinResources.getBoolean(skinResId);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getBoolean(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getBoolean(String str) {
        try {
            int skinResId = getSkinResId(str, "bool");
            if (skinResId > 0) {
                return this.mSkinResources.getBoolean(skinResId);
            }
            int appResId = getAppResId(str, "bool");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getBoolean(appResId);
            }
            return false;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public XmlResourceParser getXml(int i) {
        try {
            int targetResIdStrictly = getTargetResIdStrictly(i);
            if (targetResIdStrictly > 0) {
                return this.mSkinResources.getXml(targetResIdStrictly);
            }
            return SkinCompatManager.getInstance().getContext().getResources().getXml(i);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public XmlResourceParser getXml(String str) {
        SkinLog.m55i(TAG, "getXml name:" + str + " mSkinPkgName:" + this.mSkinPkgName);
        try {
            int skinResId = getSkinResId(str, "xml");
            if (skinResId > 0) {
                return this.mSkinResources.getXml(skinResId);
            }
            int appResId = getAppResId(str, "xml");
            if (appResId > 0) {
                return SkinCompatManager.getInstance().getContext().getResources().getXml(appResId);
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class SkinThemeContext extends ContextThemeWrapper {
        public SkinThemeContext(Context context) {
            if (context instanceof ContextThemeWrapper) {
                attachBaseContext(((ContextThemeWrapper) context).getBaseContext());
            } else {
                attachBaseContext(context);
            }
        }

        @Override // android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
        public Resources getResources() {
            return SkinCompatResources.this.mSkinResources;
        }

        @Override // android.content.ContextWrapper, android.content.Context
        public String getPackageName() {
            return SkinCompatResources.this.mSkinPkgName;
        }
    }
}
