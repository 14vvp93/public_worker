package com.android.systemui;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.classifier.FalsingManager;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.NotificationBlockingHelperManager;
import com.android.systemui.statusbar.NotificationEntryManager;
import com.android.systemui.statusbar.NotificationGutsManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLogger;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.ScrimView;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.VisualStabilityManager;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.LockIcon;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.NotificationGroupManager;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimState;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SystemUIFactory {
    static SystemUIFactory mFactory;

    public static SystemUIFactory getInstance() {
        return mFactory;
    }

    public static void createFromConfig(Context context) {
        String clsName = context.getString(R.string.config_systemUIFactoryComponent);
        if (clsName == null || clsName.length() == 0) {
            throw new RuntimeException("No SystemUIFactory component configured");
        }
        try {
            Class<?> cls = context.getClassLoader().loadClass(clsName);
            mFactory = (SystemUIFactory) cls.newInstance();
        } catch (Throwable t) {
            Log.w("SystemUIFactory", "Error creating SystemUIFactory component: " + clsName, t);
            throw new RuntimeException(t);
        }
    }

    public StatusBarKeyguardViewManager createStatusBarKeyguardViewManager(Context context, ViewMediatorCallback viewMediatorCallback, LockPatternUtils lockPatternUtils) {
        return new StatusBarKeyguardViewManager(context, viewMediatorCallback, lockPatternUtils);
    }

    public KeyguardBouncer createKeyguardBouncer(Context context, ViewMediatorCallback callback, LockPatternUtils lockPatternUtils, ViewGroup container, DismissCallbackRegistry dismissCallbackRegistry, KeyguardBouncer.BouncerExpansionCallback expansionCallback) {
        return new KeyguardBouncer(context, callback, lockPatternUtils, container, dismissCallbackRegistry, FalsingManager.getInstance(context), expansionCallback);
    }

    public ScrimController createScrimController(ScrimView scrimBehind, ScrimView scrimInFront, LockscreenWallpaper lockscreenWallpaper, TriConsumer<ScrimState, Float, ColorExtractor.GradientColors> scrimStateListener, Consumer<Integer> scrimVisibleListener, DozeParameters dozeParameters, AlarmManager alarmManager) {
        return new ScrimController(scrimBehind, scrimInFront, scrimStateListener, scrimVisibleListener, dozeParameters, alarmManager);
    }

    public NotificationIconAreaController createNotificationIconAreaController(Context context, StatusBar statusBar) {
        return new NotificationIconAreaController(context, statusBar);
    }

    public KeyguardIndicationController createKeyguardIndicationController(Context context, ViewGroup indicationArea, LockIcon lockIcon) {
        return new KeyguardIndicationController(context, indicationArea, lockIcon);
    }

    public QSTileHost createQSTileHost(Context context, StatusBar statusBar, StatusBarIconController iconController) {
        return new QSTileHost(context, statusBar, iconController);
    }

    public void injectDependencies(ArrayMap<Object, Dependency.DependencyProvider> providers, final Context context) {
        providers.put(NotificationLockscreenUserManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$z1zarcUJ1CMoFj5AQN_-O19KBck
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$0(context);
            }
        });
        providers.put(VisualStabilityManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$rO7kD_xRtn44ztbLT-z5d6D1204
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return new VisualStabilityManager();
            }
        });
        providers.put(NotificationGroupManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$FU21uH-husoEIyZzPMfnsCEG9NU
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return new NotificationGroupManager();
            }
        });
        providers.put(NotificationMediaManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$_ZkpB3cw8KjSnUV2DiHEuXgnP3w
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$1(context);
            }
        });
        providers.put(NotificationGutsManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$VIDF11F4Hm4FO29fDzfbBuWaMOc
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$2(context);
            }
        });
        providers.put(NotificationBlockingHelperManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$SlUzUgfPqIqhXzgg5laArZqKWxA
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$3(context);
            }
        });
        providers.put(NotificationRemoteInputManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$eSzRlAsk6D28lmD2v1NPm3s9RoI
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$4(context);
            }
        });
        providers.put(SmartReplyConstants.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$Lv29Gbe9ThWNLK8qa10kXlPqUkA
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$5(context);
            }
        });
        providers.put(NotificationListener.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$L-4aOdTXuzt8OB3pSNymTUOwMhQ
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$6(context);
            }
        });
        providers.put(NotificationLogger.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$B5VatWTpOGVcdPdOJiZQrQp6aIs
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return new NotificationLogger();
            }
        });
        providers.put(NotificationViewHierarchyManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$MTw0XI2_566RG6bGYjhDG7AgFHQ
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$7(context);
            }
        });
        providers.put(NotificationEntryManager.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$hCjEFu27r-GMURswSL_JwEDKp54
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$8(context);
            }
        });
        providers.put(KeyguardDismissUtil.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$ymijKB1VyNKZxZAbdPMXGO8_FBg
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return new KeyguardDismissUtil();
            }
        });
        providers.put(SmartReplyController.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$d_KgUKmojSRkzZuEYEfWqICK6w4
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$9();
            }
        });
        providers.put(RemoteInputQuickSettingsDisabler.class, new Dependency.DependencyProvider() { // from class: com.android.systemui.-$$Lambda$SystemUIFactory$c6WNy_UO3Uf9By2n25BDlugW55Y
            @Override // com.android.systemui.Dependency.DependencyProvider
            public final Object createDependency() {
                return SystemUIFactory.lambda$injectDependencies$10(context);
            }
        });
    }

    static /* synthetic */ Object lambda$injectDependencies$0(Context context) {
        return new NotificationLockscreenUserManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$1(Context context) {
        return new NotificationMediaManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$2(Context context) {
        return new NotificationGutsManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$3(Context context) {
        return new NotificationBlockingHelperManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$4(Context context) {
        return new NotificationRemoteInputManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$5(Context context) {
        return new SmartReplyConstants((Handler) Dependency.get(Dependency.MAIN_HANDLER), context);
    }

    static /* synthetic */ Object lambda$injectDependencies$6(Context context) {
        return new NotificationListener(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$7(Context context) {
        return new NotificationViewHierarchyManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$8(Context context) {
        return new NotificationEntryManager(context);
    }

    static /* synthetic */ Object lambda$injectDependencies$9() {
        return new SmartReplyController();
    }

    static /* synthetic */ Object lambda$injectDependencies$10(Context context) {
        return new RemoteInputQuickSettingsDisabler(context);
    }
}
