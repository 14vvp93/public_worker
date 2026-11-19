package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.android.systemui.statusbar.phone.ExpandableIndicator;
import com.android.systemui.statusbar.policy.ExtensionController;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class LockscreenFragment extends PreferenceFragment {
    private Handler mHandler;
    private final ArrayList<TunerService.Tunable> mTunables = new ArrayList<>();
    private TunerService mTunerService;

    @Override // android.support.v14.preference.PreferenceFragment
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        this.mTunerService = (TunerService) Dependency.get(TunerService.class);
        this.mHandler = new Handler();
        addPreferencesFromResource(R.xml.lockscreen_settings);
        setupGroup("sysui_keyguard_left", "sysui_keyguard_left_unlock");
        setupGroup("sysui_keyguard_right", "sysui_keyguard_right_unlock");
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$Lo7jOQgOiEZ4M1LxVUxyoD69g0s
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.mTunerService.removeTunable((TunerService.Tunable) obj);
            }
        });
    }

    private void setupGroup(String buttonSetting, String unlockKey) {
        final Preference shortcut = findPreference(buttonSetting);
        final SwitchPreference unlock = (SwitchPreference) findPreference(unlockKey);
        addTunable(new TunerService.Tunable() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$0AVJL9CTzprG2BMD2je5SHaUt-w
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                LockscreenFragment.lambda$setupGroup$1(this.f$0, unlock, shortcut, str, str2);
            }
        }, buttonSetting);
    }

    public static /* synthetic */ void lambda$setupGroup$1(LockscreenFragment lockscreenFragment, SwitchPreference unlock, Preference shortcut, String k, String v) {
        boolean visible = !TextUtils.isEmpty(v);
        unlock.setVisible(visible);
        lockscreenFragment.setSummary(shortcut, v);
    }

    private void setSummary(Preference shortcut, String value) {
        if (value == null) {
            shortcut.setSummary(R.string.lockscreen_none);
            return;
        }
        if (value.contains("::")) {
            ShortcutParser.Shortcut info = getShortcutInfo(getContext(), value);
            shortcut.setSummary(info != null ? info.label : null);
        } else if (value.contains("/")) {
            ActivityInfo info2 = getActivityinfo(getContext(), value);
            shortcut.setSummary(info2 != null ? info2.loadLabel(getContext().getPackageManager()) : null);
        } else {
            shortcut.setSummary(R.string.lockscreen_none);
        }
    }

    private void addTunable(TunerService.Tunable t, String... keys) {
        this.mTunables.add(t);
        this.mTunerService.addTunable(t, keys);
    }

    public static ActivityInfo getActivityinfo(Context context, String value) {
        ComponentName component = ComponentName.unflattenFromString(value);
        try {
            return context.getPackageManager().getActivityInfo(component, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static ShortcutParser.Shortcut getShortcutInfo(Context context, String value) {
        return ShortcutParser.Shortcut.create(context, value);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public final ExpandableIndicator expand;
        public final ImageView icon;
        public final TextView title;

        public Holder(View itemView) {
            super(itemView);
            this.icon = (ImageView) itemView.findViewById(android.R.id.icon);
            this.title = (TextView) itemView.findViewById(android.R.id.title);
            this.expand = (ExpandableIndicator) itemView.findViewById(R.id.expand);
        }
    }

    private static class StaticShortcut extends Item {
        private final Context mContext;
        private final ShortcutParser.Shortcut mShortcut;

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public Drawable getDrawable() {
            return this.mShortcut.icon.loadDrawable(this.mContext);
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public String getLabel() {
            return this.mShortcut.label;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public Boolean getExpando() {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class App extends Item {
        private final ArrayList<Item> mChildren;
        private final Context mContext;
        private boolean mExpanded;
        private final LauncherActivityInfo mInfo;

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public Drawable getDrawable() {
            return this.mInfo.getBadgedIcon(this.mContext.getResources().getConfiguration().densityDpi);
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public String getLabel() {
            return this.mInfo.getLabel().toString();
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public Boolean getExpando() {
            if (this.mChildren.size() != 0) {
                return Boolean.valueOf(this.mExpanded);
            }
            return null;
        }

        @Override // com.android.systemui.tuner.LockscreenFragment.Item
        public void toggleExpando(final Adapter adapter) {
            this.mExpanded = !this.mExpanded;
            if (this.mExpanded) {
                this.mChildren.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$App$ETExpSuIeTllbJ9AB_3DTGOAJgk
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        adapter.addItem(this.f$0, (LockscreenFragment.Item) obj);
                    }
                });
            } else {
                this.mChildren.forEach(new Consumer() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$App$KymmDZF-Q8mj0Qr5uc4akrkgskU
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        adapter.remItem((LockscreenFragment.Item) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Item {
        public abstract Drawable getDrawable();

        public abstract Boolean getExpando();

        public abstract String getLabel();

        private Item() {
        }

        public void toggleExpando(Adapter adapter) {
        }
    }

    public static class Adapter extends RecyclerView.Adapter<Holder> {
        private final Consumer<Item> mCallback;
        private ArrayList<Item> mItems;

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tuner_shortcut_item, parent, false));
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(final Holder holder, int position) {
            Item item = this.mItems.get(position);
            holder.icon.setImageDrawable(item.getDrawable());
            holder.title.setText(item.getLabel());
            holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$Adapter$VuIE2eL9-LHOyBflZw_Px7xwF04
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LockscreenFragment.Adapter adapter = this.f$0;
                    adapter.mCallback.accept(adapter.mItems.get(holder.getAdapterPosition()));
                }
            });
            Boolean expando = item.getExpando();
            if (expando != null) {
                holder.expand.setVisibility(0);
                holder.expand.setExpanded(expando.booleanValue());
                holder.expand.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.-$$Lambda$LockscreenFragment$Adapter$fS6IuUEavDgpMOkDZLNh46UcUNQ
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        LockscreenFragment.Adapter adapter = this.f$0;
                        adapter.mItems.get(holder.getAdapterPosition()).toggleExpando(adapter);
                    }
                });
                return;
            }
            holder.expand.setVisibility(8);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mItems.size();
        }

        public void remItem(Item item) {
            int index = this.mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(index);
        }

        public void addItem(Item parent, Item child) {
            int index = this.mItems.indexOf(parent);
            this.mItems.add(index + 1, child);
            notifyItemInserted(index + 1);
        }
    }

    public static class LockButtonFactory implements ExtensionController.TunerFactory<IntentButtonProvider.IntentButton> {
        private final Context mContext;
        private final String mKey;

        @Override // com.android.systemui.statusbar.policy.ExtensionController.TunerFactory
        public /* bridge */ /* synthetic */ IntentButtonProvider.IntentButton create(Map map) {
            return create((Map<String, String>) map);
        }

        public LockButtonFactory(Context context, String key) {
            this.mContext = context;
            this.mKey = key;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.TunerFactory
        public String[] keys() {
            return new String[]{this.mKey};
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.systemui.statusbar.policy.ExtensionController.TunerFactory
        public IntentButtonProvider.IntentButton create(Map<String, String> settings) {
            ActivityInfo info;
            String buttonStr = settings.get(this.mKey);
            if (!TextUtils.isEmpty(buttonStr)) {
                if (buttonStr.contains("::")) {
                    ShortcutParser.Shortcut shortcut = LockscreenFragment.getShortcutInfo(this.mContext, buttonStr);
                    if (shortcut != null) {
                        return new ShortcutButton(this.mContext, shortcut);
                    }
                    return null;
                }
                if (buttonStr.contains("/") && (info = LockscreenFragment.getActivityinfo(this.mContext, buttonStr)) != null) {
                    return new ActivityButton(this.mContext, info);
                }
                return null;
            }
            return null;
        }
    }

    private static class ShortcutButton implements IntentButtonProvider.IntentButton {
        private final IntentButtonProvider.IntentButton.IconState mIconState = new IntentButtonProvider.IntentButton.IconState();
        private final ShortcutParser.Shortcut mShortcut;

        public ShortcutButton(Context context, ShortcutParser.Shortcut shortcut) {
            this.mShortcut = shortcut;
            this.mIconState.isVisible = true;
            this.mIconState.drawable = shortcut.icon.loadDrawable(context).mutate();
            this.mIconState.contentDescription = this.mShortcut.label;
            int size = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
            this.mIconState.drawable = new ScalingDrawableWrapper(this.mIconState.drawable, size / this.mIconState.drawable.getIntrinsicWidth());
            this.mIconState.tint = false;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public Intent getIntent() {
            return this.mShortcut.intent;
        }
    }

    private static class ActivityButton implements IntentButtonProvider.IntentButton {
        private final IntentButtonProvider.IntentButton.IconState mIconState = new IntentButtonProvider.IntentButton.IconState();
        private final Intent mIntent;

        public ActivityButton(Context context, ActivityInfo info) {
            this.mIntent = new Intent().setComponent(new ComponentName(info.packageName, info.name));
            this.mIconState.isVisible = true;
            this.mIconState.drawable = info.loadIcon(context.getPackageManager()).mutate();
            this.mIconState.contentDescription = info.loadLabel(context.getPackageManager());
            int size = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
            this.mIconState.drawable = new ScalingDrawableWrapper(this.mIconState.drawable, size / this.mIconState.drawable.getIntrinsicWidth());
            this.mIconState.tint = false;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public IntentButtonProvider.IntentButton.IconState getIcon() {
            return this.mIconState;
        }

        @Override // com.android.systemui.plugins.IntentButtonProvider.IntentButton
        public Intent getIntent() {
            return this.mIntent;
        }
    }
}
