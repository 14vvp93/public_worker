package com.android.systemui.statusbar.car;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.keyguard.AlphaOptimizedImageButton;
import com.android.systemui.Dependency;
import com.android.systemui.R;

/* JADX INFO: loaded from: classes.dex */
public class CarFacetButton extends LinearLayout {
    private String[] mComponentNames;
    private Context mContext;
    private String[] mFacetCategories;
    private String[] mFacetPackages;
    private AlphaOptimizedImageButton mIcon;
    private int mIconResourceId;
    private AlphaOptimizedImageButton mMoreIcon;
    private boolean mSelected;
    private float mSelectedAlpha;
    private int mSelectedIconResourceId;
    private float mUnselectedAlpha;
    private boolean mUseMoreIcon;

    public CarFacetButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSelected = false;
        this.mUseMoreIcon = true;
        this.mSelectedAlpha = 1.0f;
        this.mUnselectedAlpha = 1.0f;
        this.mContext = context;
        View.inflate(context, R.layout.car_facet_button, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CarFacetButton);
        setupIntents(typedArray);
        setupIcons(typedArray);
        CarFacetButtonController carFacetButtonController = (CarFacetButtonController) Dependency.get(CarFacetButtonController.class);
        carFacetButtonController.addFacetButton(this);
    }

    private void setupIntents(TypedArray typedArray) {
        String intentString = typedArray.getString(3);
        String longPressIntentString = typedArray.getString(4);
        String categoryString = typedArray.getString(0);
        String packageString = typedArray.getString(5);
        String componentNameString = typedArray.getString(1);
        try {
            final Intent intent = Intent.parseUri(intentString, 1);
            intent.putExtra("filter_id", Integer.toString(getId()));
            if (packageString != null) {
                this.mFacetPackages = packageString.split(";");
                intent.putExtra("packages", this.mFacetPackages);
            }
            if (categoryString != null) {
                this.mFacetCategories = categoryString.split(";");
                intent.putExtra("categories", this.mFacetCategories);
            }
            if (componentNameString != null) {
                this.mComponentNames = componentNameString.split(";");
            }
            setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.car.-$$Lambda$CarFacetButton$i_dU0gknjwIWw7BhdaypNSeIb4I
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CarFacetButton.lambda$setupIntents$0(this.f$0, intent, view);
                }
            });
            if (longPressIntentString != null) {
                final Intent longPressIntent = Intent.parseUri(longPressIntentString, 1);
                setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.statusbar.car.-$$Lambda$CarFacetButton$531BxJPdfLOepcWw24yntC-HGBQ
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return CarFacetButton.lambda$setupIntents$1(this.f$0, longPressIntent, view);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to attach intent", e);
        }
    }

    public static /* synthetic */ void lambda$setupIntents$0(CarFacetButton carFacetButton, Intent intent, View v) {
        intent.putExtra("launch_picker", carFacetButton.mSelected);
        carFacetButton.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public static /* synthetic */ boolean lambda$setupIntents$1(CarFacetButton carFacetButton, Intent longPressIntent, View v) {
        carFacetButton.mContext.startActivityAsUser(longPressIntent, UserHandle.CURRENT);
        return true;
    }

    private void setupIcons(TypedArray styledAttributes) {
        this.mSelectedAlpha = styledAttributes.getFloat(6, this.mSelectedAlpha);
        this.mUnselectedAlpha = styledAttributes.getFloat(8, this.mUnselectedAlpha);
        this.mIcon = (AlphaOptimizedImageButton) findViewById(R.id.car_nav_button_icon);
        this.mIcon.setScaleType(ImageView.ScaleType.CENTER);
        this.mIcon.setClickable(false);
        this.mIcon.setAlpha(this.mUnselectedAlpha);
        this.mIconResourceId = styledAttributes.getResourceId(2, 0);
        this.mIcon.setImageResource(this.mIconResourceId);
        this.mSelectedIconResourceId = styledAttributes.getResourceId(7, this.mIconResourceId);
        this.mMoreIcon = (AlphaOptimizedImageButton) findViewById(R.id.car_nav_button_more_icon);
        this.mMoreIcon.setClickable(false);
        this.mMoreIcon.setAlpha(this.mSelectedAlpha);
        this.mMoreIcon.setVisibility(8);
        this.mUseMoreIcon = styledAttributes.getBoolean(9, true);
    }

    public String[] getCategories() {
        if (this.mFacetCategories == null) {
            return new String[0];
        }
        return this.mFacetCategories;
    }

    public String[] getFacetPackages() {
        if (this.mFacetPackages == null) {
            return new String[0];
        }
        return this.mFacetPackages;
    }

    public String[] getComponentName() {
        if (this.mComponentNames == null) {
            return new String[0];
        }
        return this.mComponentNames;
    }

    @Override // android.view.View
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setSelected(selected, selected);
    }

    public void setSelected(boolean selected, boolean showMoreIcon) {
        this.mSelected = selected;
        this.mIcon.setAlpha(this.mSelected ? this.mSelectedAlpha : this.mUnselectedAlpha);
        this.mIcon.setImageResource(this.mSelected ? this.mSelectedIconResourceId : this.mIconResourceId);
        if (this.mUseMoreIcon) {
            this.mMoreIcon.setVisibility(showMoreIcon ? 0 : 8);
        }
    }
}
