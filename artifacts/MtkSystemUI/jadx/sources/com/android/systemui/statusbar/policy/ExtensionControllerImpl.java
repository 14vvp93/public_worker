package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.leak.LeakDetector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionControllerImpl implements ExtensionController {
    private final Context mDefaultContext;

    /* JADX INFO: Access modifiers changed from: private */
    interface Item<T> extends Producer<T> {
        int sortOrder();
    }

    private interface Producer<T> {
        void destroy();

        T get();
    }

    public ExtensionControllerImpl(Context context) {
        this.mDefaultContext = context;
    }

    @Override // com.android.systemui.statusbar.policy.ExtensionController
    public <T> ExtensionBuilder<T> newExtension(Class<T> cls) {
        return new ExtensionBuilder<>();
    }

    private class ExtensionBuilder<T> implements ExtensionController.ExtensionBuilder<T> {
        private ExtensionImpl<T> mExtension;

        private ExtensionBuilder() {
            this.mExtension = new ExtensionImpl<>();
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder<T> withTunerFactory(ExtensionController.TunerFactory<T> factory) {
            this.mExtension.addTunerFactory(factory, factory.keys());
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public <P extends T> ExtensionController.ExtensionBuilder<T> withPlugin(Class<P> cls) {
            return withPlugin(cls, PluginManager.getAction(cls));
        }

        public <P extends T> ExtensionController.ExtensionBuilder<T> withPlugin(Class<P> cls, String action) {
            return withPlugin(cls, action, null);
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public <P> ExtensionController.ExtensionBuilder<T> withPlugin(Class<P> cls, String action, ExtensionController.PluginConverter<T, P> converter) {
            this.mExtension.addPlugin(action, cls, converter);
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder<T> withDefault(Supplier<T> def) {
            this.mExtension.addDefault(def);
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder<T> withFeature(String feature, Supplier<T> supplier) {
            this.mExtension.addFeature(feature, supplier);
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.ExtensionBuilder<T> withCallback(Consumer<T> callback) {
            ((ExtensionImpl) this.mExtension).mCallbacks.add(callback);
            return this;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.ExtensionBuilder
        public ExtensionController.Extension build() {
            Collections.sort(((ExtensionImpl) this.mExtension).mProducers, Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.statusbar.policy.-$$Lambda$LO8p3lRLZXpohPDzojcJ_BVuMnk
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((ExtensionControllerImpl.Item) obj).sortOrder();
                }
            }));
            this.mExtension.notifyChanged();
            return this.mExtension;
        }
    }

    private class ExtensionImpl<T> implements ExtensionController.Extension<T> {
        private final ArrayList<Consumer<T>> mCallbacks;
        private T mItem;
        private Context mPluginContext;
        private final ArrayList<Item<T>> mProducers;

        private ExtensionImpl() {
            this.mProducers = new ArrayList<>();
            this.mCallbacks = new ArrayList<>();
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public void addCallback(Consumer<T> callback) {
            this.mCallbacks.add(callback);
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public T get() {
            return this.mItem;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public Context getContext() {
            return this.mPluginContext != null ? this.mPluginContext : ExtensionControllerImpl.this.mDefaultContext;
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public void destroy() {
            for (int i = 0; i < this.mProducers.size(); i++) {
                this.mProducers.get(i).destroy();
            }
        }

        @Override // com.android.systemui.statusbar.policy.ExtensionController.Extension
        public void clearItem(boolean isDestroyed) {
            if (isDestroyed && this.mItem != null) {
                ((LeakDetector) Dependency.get(LeakDetector.class)).trackGarbage(this.mItem);
            }
            this.mItem = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyChanged() {
            if (this.mItem != null) {
                ((LeakDetector) Dependency.get(LeakDetector.class)).trackGarbage(this.mItem);
            }
            this.mItem = null;
            for (int i = 0; i < this.mProducers.size(); i++) {
                T item = this.mProducers.get(i).get();
                if (item != null) {
                    this.mItem = item;
                    break;
                }
            }
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                this.mCallbacks.get(i2).accept(this.mItem);
            }
        }

        public void addDefault(Supplier<T> def) {
            this.mProducers.add(new Default(def));
        }

        public <P> void addPlugin(String action, Class<P> cls, ExtensionController.PluginConverter<T, P> converter) {
            this.mProducers.add(new PluginItem(action, cls, converter));
        }

        public void addTunerFactory(ExtensionController.TunerFactory<T> factory, String[] keys) {
            this.mProducers.add(new TunerItem(factory, keys));
        }

        public void addFeature(String feature, Supplier<T> mode) {
            this.mProducers.add(new FeatureItem(feature, mode));
        }

        private class PluginItem<P extends Plugin> implements PluginListener<P>, Item<T> {
            private final ExtensionController.PluginConverter<T, P> mConverter;
            private T mItem;

            public PluginItem(String action, Class<P> cls, ExtensionController.PluginConverter<T, P> converter) {
                this.mConverter = converter;
                ((PluginManager) Dependency.get(PluginManager.class)).addPluginListener(action, (PluginListener) this, (Class<?>) cls);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginConnected(P p, Context pluginContext) {
                ExtensionImpl.this.mPluginContext = pluginContext;
                if (this.mConverter != null) {
                    this.mItem = this.mConverter.getInterfaceFromPlugin(p);
                } else {
                    this.mItem = p;
                }
                ExtensionImpl.this.notifyChanged();
            }

            @Override // com.android.systemui.plugins.PluginListener
            public void onPluginDisconnected(P plugin) {
                ExtensionImpl.this.mPluginContext = null;
                this.mItem = null;
                ExtensionImpl.this.notifyChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public T get() {
                return this.mItem;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public void destroy() {
                ((PluginManager) Dependency.get(PluginManager.class)).removePluginListener(this);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 0;
            }
        }

        private class TunerItem<T> implements Item<T>, TunerService.Tunable {
            private final ExtensionController.TunerFactory<T> mFactory;
            private T mItem;
            private final ArrayMap<String, String> mSettings = new ArrayMap<>();

            public TunerItem(ExtensionController.TunerFactory<T> factory, String... setting) {
                this.mFactory = factory;
                ((TunerService) Dependency.get(TunerService.class)).addTunable(this, setting);
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public T get() {
                return this.mItem;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public void destroy() {
                ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
            }

            @Override // com.android.systemui.tuner.TunerService.Tunable
            public void onTuningChanged(String key, String newValue) {
                this.mSettings.put(key, newValue);
                this.mItem = this.mFactory.create(this.mSettings);
                ExtensionImpl.this.notifyChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 1;
            }
        }

        private class FeatureItem<T> implements Item<T> {
            private final String mFeature;
            private final Supplier<T> mSupplier;

            public FeatureItem(String feature, Supplier<T> supplier) {
                this.mSupplier = supplier;
                this.mFeature = feature;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public T get() {
                if (ExtensionControllerImpl.this.mDefaultContext.getPackageManager().hasSystemFeature(this.mFeature)) {
                    return this.mSupplier.get();
                }
                return null;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public void destroy() {
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 2;
            }
        }

        private class Default<T> implements Item<T> {
            private final Supplier<T> mSupplier;

            public Default(Supplier<T> supplier) {
                this.mSupplier = supplier;
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public T get() {
                return this.mSupplier.get();
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Producer
            public void destroy() {
            }

            @Override // com.android.systemui.statusbar.policy.ExtensionControllerImpl.Item
            public int sortOrder() {
                return 4;
            }
        }
    }
}
