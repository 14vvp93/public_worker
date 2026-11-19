package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.systemui.Dependency;
import com.android.systemui.OverviewProxyService;
import com.android.systemui.R;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.statusbar.phone.NavBarButtonProvider;
import com.android.systemui.statusbar.policy.KeyButtonView;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NavigationBarInflaterView extends FrameLayout implements PluginListener<NavBarButtonProvider>, TunerService.Tunable {
    private boolean isRot0Landscape;
    private boolean mAlternativeOrder;
    private SparseArray<ButtonDispatcher> mButtonDispatchers;
    private String mCurrentLayout;
    private final Display mDisplay;
    protected LayoutInflater mLandscapeInflater;
    private View mLastLandscape;
    private View mLastPortrait;
    protected LayoutInflater mLayoutInflater;
    private OverviewProxyService mOverviewProxyService;
    private final List<NavBarButtonProvider> mPlugins;
    protected FrameLayout mRot0;
    protected FrameLayout mRot90;
    private boolean mUsingCustomLayout;

    public NavigationBarInflaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPlugins = new ArrayList();
        createInflaters();
        this.mDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Display.Mode displayMode = this.mDisplay.getMode();
        this.isRot0Landscape = displayMode.getPhysicalWidth() > displayMode.getPhysicalHeight();
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
    }

    private void createInflaters() {
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        Configuration landscape = new Configuration();
        landscape.setTo(this.mContext.getResources().getConfiguration());
        landscape.orientation = 2;
        this.mLandscapeInflater = LayoutInflater.from(this.mContext.createConfigurationContext(landscape));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflateChildren();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    private void inflateChildren() {
        removeAllViews();
        this.mRot0 = (FrameLayout) this.mLayoutInflater.inflate(R.layout.navigation_layout, (ViewGroup) this, false);
        this.mRot0.setId(R.id.rot0);
        addView(this.mRot0);
        this.mRot90 = (FrameLayout) this.mLayoutInflater.inflate(R.layout.navigation_layout_rot90, (ViewGroup) this, false);
        this.mRot90.setId(R.id.rot90);
        addView(this.mRot90);
        updateAlternativeOrder();
    }

    protected String getDefaultLayout() {
        int defaultResource;
        if (this.mOverviewProxyService.shouldShowSwipeUpUI()) {
            defaultResource = R.string.config_navBarLayoutQuickstep;
        } else {
            defaultResource = R.string.config_navBarLayout;
        }
        return this.mContext.getString(defaultResource);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "sysui_nav_bar", "sysui_nav_bar_left", "sysui_nav_bar_right");
        ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener((PluginListener) this, NavBarButtonProvider.class, true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
        super.onDetachedFromWindow();
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("sysui_nav_bar".equals(key)) {
            if (!Objects.equals(this.mCurrentLayout, newValue)) {
                this.mUsingCustomLayout = newValue != null;
                clearViews();
                inflateLayout(newValue);
                return;
            }
            return;
        }
        if ("sysui_nav_bar_left".equals(key) || "sysui_nav_bar_right".equals(key)) {
            clearViews();
            inflateLayout(this.mCurrentLayout);
        }
    }

    public void onLikelyDefaultLayoutChange() {
        if (this.mUsingCustomLayout) {
            return;
        }
        String newValue = getDefaultLayout();
        if (!Objects.equals(this.mCurrentLayout, newValue)) {
            clearViews();
            inflateLayout(newValue);
        }
    }

    public void updateButtonDispatchersCurrentView() {
        if (this.mButtonDispatchers != null) {
            int rotation = this.mDisplay.getRotation();
            boolean portrait = rotation == 0 || rotation == 2;
            View view = portrait ? this.mRot0 : this.mRot90;
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                ButtonDispatcher dispatcher = this.mButtonDispatchers.valueAt(i);
                dispatcher.setCurrentView(view);
            }
        }
    }

    public void setAlternativeOrder(boolean alternativeOrder) {
        if (alternativeOrder != this.mAlternativeOrder) {
            this.mAlternativeOrder = alternativeOrder;
            updateAlternativeOrder();
        }
    }

    private void updateAlternativeOrder() {
        updateAlternativeOrder(this.mRot0.findViewById(R.id.ends_group));
        updateAlternativeOrder(this.mRot0.findViewById(R.id.center_group));
        updateAlternativeOrder(this.mRot90.findViewById(R.id.ends_group));
        updateAlternativeOrder(this.mRot90.findViewById(R.id.center_group));
    }

    private void updateAlternativeOrder(View v) {
        if (v instanceof ReverseLinearLayout) {
            ((ReverseLinearLayout) v).setAlternativeOrder(this.mAlternativeOrder);
        }
    }

    protected void inflateLayout(String newLayout) {
        this.mCurrentLayout = newLayout;
        if (newLayout == null) {
            newLayout = getDefaultLayout();
        }
        String[] sets = newLayout.split(";", 3);
        if (sets.length != 3) {
            Log.d("NavBarInflater", "Invalid layout.");
            sets = getDefaultLayout().split(";", 3);
        }
        String[] start = sets[0].split(",");
        String[] center = sets[1].split(",");
        String[] end = sets[2].split(",");
        inflateButtons(start, (ViewGroup) this.mRot0.findViewById(R.id.ends_group), this.isRot0Landscape, true);
        inflateButtons(start, (ViewGroup) this.mRot90.findViewById(R.id.ends_group), !this.isRot0Landscape, true);
        inflateButtons(center, (ViewGroup) this.mRot0.findViewById(R.id.center_group), this.isRot0Landscape, false);
        inflateButtons(center, (ViewGroup) this.mRot90.findViewById(R.id.center_group), !this.isRot0Landscape, false);
        addGravitySpacer((LinearLayout) this.mRot0.findViewById(R.id.ends_group));
        addGravitySpacer((LinearLayout) this.mRot90.findViewById(R.id.ends_group));
        inflateButtons(end, (ViewGroup) this.mRot0.findViewById(R.id.ends_group), this.isRot0Landscape, false);
        inflateButtons(end, (ViewGroup) this.mRot90.findViewById(R.id.ends_group), true ^ this.isRot0Landscape, false);
        updateButtonDispatchersCurrentView();
    }

    private void addGravitySpacer(LinearLayout layout) {
        layout.addView(new Space(this.mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
    }

    private void inflateButtons(String[] buttons, ViewGroup parent, boolean landscape, boolean start) {
        for (String str : buttons) {
            inflateButton(str, parent, landscape, start);
        }
    }

    protected View inflateButton(String buttonSpec, ViewGroup parent, boolean landscape, boolean start) {
        LayoutInflater inflater = landscape ? this.mLandscapeInflater : this.mLayoutInflater;
        View v = createView(buttonSpec, parent, inflater);
        if (v == null) {
            return null;
        }
        View v2 = applySize(v, buttonSpec, landscape, start);
        parent.addView(v2);
        addToDispatchers(v2);
        View lastView = landscape ? this.mLastLandscape : this.mLastPortrait;
        View accessibilityView = v2;
        if (v2 instanceof ReverseLinearLayout.ReverseRelativeLayout) {
            accessibilityView = ((ReverseLinearLayout.ReverseRelativeLayout) v2).getChildAt(0);
        }
        if (lastView != null) {
            accessibilityView.setAccessibilityTraversalAfter(lastView.getId());
        }
        if (landscape) {
            this.mLastLandscape = accessibilityView;
        } else {
            this.mLastPortrait = accessibilityView;
        }
        return v2;
    }

    private View applySize(View v, String buttonSpec, boolean landscape, boolean start) {
        int gravity;
        String sizeStr = extractSize(buttonSpec);
        if (sizeStr == null) {
            return v;
        }
        if (sizeStr.contains("W")) {
            float weight = Float.parseFloat(sizeStr.substring(0, sizeStr.indexOf("W")));
            ReverseLinearLayout.ReverseRelativeLayout frame = new ReverseLinearLayout.ReverseRelativeLayout(this.mContext);
            FrameLayout.LayoutParams childParams = new FrameLayout.LayoutParams(v.getLayoutParams());
            if (landscape) {
                gravity = start ? 48 : 80;
            } else {
                gravity = start ? 8388611 : 8388613;
            }
            if (sizeStr.endsWith("WC")) {
                gravity = 17;
            }
            frame.setDefaultGravity(gravity);
            frame.setGravity(gravity);
            frame.addView(v, childParams);
            frame.setLayoutParams(new LinearLayout.LayoutParams(0, -1, weight));
            frame.setClipChildren(false);
            frame.setClipToPadding(false);
            return frame;
        }
        float size = Float.parseFloat(sizeStr);
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = (int) (params.width * size);
        return v;
    }

    private View createView(String buttonSpec, ViewGroup parent, LayoutInflater inflater) {
        View v = null;
        String button = extractButton(buttonSpec);
        if ("left".equals(button)) {
            String s = ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_nav_bar_left", "space");
            button = extractButton(s);
        } else if ("right".equals(button)) {
            String s2 = ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_nav_bar_right", "menu_ime");
            button = extractButton(s2);
        }
        for (NavBarButtonProvider provider : this.mPlugins) {
            v = provider.createView(buttonSpec, parent);
            if (v != null) {
                return v;
            }
        }
        if ("home".equals(button)) {
            View v2 = inflater.inflate(R.layout.home, parent, false);
            return v2;
        }
        if ("back".equals(button)) {
            View v3 = inflater.inflate(R.layout.back, parent, false);
            return v3;
        }
        if ("recent".equals(button)) {
            View v4 = inflater.inflate(R.layout.recent_apps, parent, false);
            return v4;
        }
        if ("menu_ime".equals(button)) {
            View v5 = inflater.inflate(R.layout.menu_ime, parent, false);
            return v5;
        }
        if ("space".equals(button)) {
            View v6 = inflater.inflate(R.layout.nav_key_space, parent, false);
            return v6;
        }
        if ("clipboard".equals(button)) {
            View v7 = inflater.inflate(R.layout.clipboard, parent, false);
            return v7;
        }
        if ("contextual".equals(button)) {
            View v8 = inflater.inflate(R.layout.contextual, parent, false);
            return v8;
        }
        if (button.startsWith("key")) {
            String uri = extractImage(button);
            int code = extractKeycode(button);
            View v9 = inflater.inflate(R.layout.custom_key, parent, false);
            ((KeyButtonView) v9).setCode(code);
            if (uri != null) {
                if (uri.contains(":")) {
                    ((KeyButtonView) v9).loadAsync(Icon.createWithContentUri(uri));
                    return v9;
                }
                if (uri.contains("/")) {
                    int index = uri.indexOf(47);
                    String pkg = uri.substring(0, index);
                    int id = Integer.parseInt(uri.substring(index + 1));
                    ((KeyButtonView) v9).loadAsync(Icon.createWithResource(pkg, id));
                    return v9;
                }
                return v9;
            }
            return v9;
        }
        return v;
    }

    public static String extractImage(String buttonSpec) {
        if (!buttonSpec.contains(":")) {
            return null;
        }
        int start = buttonSpec.indexOf(":");
        String subStr = buttonSpec.substring(start + 1, buttonSpec.indexOf(")"));
        return subStr;
    }

    public static int extractKeycode(String buttonSpec) {
        if (!buttonSpec.contains("(")) {
            return 1;
        }
        int start = buttonSpec.indexOf("(");
        String subStr = buttonSpec.substring(start + 1, buttonSpec.indexOf(":"));
        return Integer.parseInt(subStr);
    }

    public static String extractSize(String buttonSpec) {
        if (!buttonSpec.contains("[")) {
            return null;
        }
        int sizeStart = buttonSpec.indexOf("[");
        return buttonSpec.substring(sizeStart + 1, buttonSpec.indexOf("]"));
    }

    public static String extractButton(String buttonSpec) {
        if (!buttonSpec.contains("[")) {
            return buttonSpec;
        }
        return buttonSpec.substring(0, buttonSpec.indexOf("["));
    }

    private void addToDispatchers(View v) {
        if (this.mButtonDispatchers != null) {
            int indexOfKey = this.mButtonDispatchers.indexOfKey(v.getId());
            if (indexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(indexOfKey).addView(v);
            }
            if (v instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) v;
                int N = viewGroup.getChildCount();
                for (int i = 0; i < N; i++) {
                    addToDispatchers(viewGroup.getChildAt(i));
                }
            }
        }
    }

    private void clearViews() {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                this.mButtonDispatchers.valueAt(i).clear();
            }
        }
        clearAllChildren((ViewGroup) this.mRot0.findViewById(R.id.nav_buttons));
        clearAllChildren((ViewGroup) this.mRot90.findViewById(R.id.nav_buttons));
    }

    private void clearAllChildren(ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            ((ViewGroup) group.getChildAt(i)).removeAllViews();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(NavBarButtonProvider plugin, Context context) {
        this.mPlugins.add(plugin);
        clearViews();
        inflateLayout(this.mCurrentLayout);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(NavBarButtonProvider plugin) {
        this.mPlugins.remove(plugin);
        clearViews();
        inflateLayout(this.mCurrentLayout);
    }
}
