package com.android.systemui.plugins;

import android.util.ArrayMap;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.annotations.Requirements;
import com.android.systemui.plugins.annotations.Requires;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public class VersionInfo {
    private Class<?> mDefault;
    private final ArrayMap<Class<?>, Version> mVersions = new ArrayMap<>();

    public boolean hasVersionInfo() {
        return !this.mVersions.isEmpty();
    }

    public int getDefaultVersion() {
        return this.mVersions.get(this.mDefault).mVersion;
    }

    public VersionInfo addClass(Class<?> cls) {
        if (this.mDefault == null) {
            this.mDefault = cls;
        }
        addClass(cls, false);
        return this;
    }

    private void addClass(Class<?> cls, boolean required) {
        if (this.mVersions.containsKey(cls)) {
            return;
        }
        ProvidesInterface provider = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (provider != null) {
            this.mVersions.put(cls, new Version(provider.version(), true));
        }
        Requires requires = (Requires) cls.getDeclaredAnnotation(Requires.class);
        if (requires != null) {
            this.mVersions.put(requires.target(), new Version(requires.version(), required));
        }
        Requirements requirements = (Requirements) cls.getDeclaredAnnotation(Requirements.class);
        if (requirements != null) {
            for (Requires r : requirements.value()) {
                this.mVersions.put(r.target(), new Version(r.version(), required));
            }
        }
        DependsOn depends = (DependsOn) cls.getDeclaredAnnotation(DependsOn.class);
        if (depends != null) {
            addClass(depends.target(), true);
        }
        Dependencies dependencies = (Dependencies) cls.getDeclaredAnnotation(Dependencies.class);
        if (dependencies != null) {
            for (DependsOn d : dependencies.value()) {
                addClass(d.target(), true);
            }
        }
    }

    public void checkVersion(VersionInfo plugin) throws InvalidVersionException {
        final ArrayMap<Class<?>, Version> versions = new ArrayMap<>(this.mVersions);
        plugin.mVersions.forEach(new BiConsumer() { // from class: com.android.systemui.plugins.-$$Lambda$VersionInfo$36RaSV8LaXl8e0tazdPbdpQ95k8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                VersionInfo.lambda$checkVersion$0(this.f$0, versions, (Class) obj, (VersionInfo.Version) obj2);
            }
        });
        versions.forEach(new BiConsumer() { // from class: com.android.systemui.plugins.-$$Lambda$VersionInfo$OmM59IDVY01yoqUNn9tP6VG6ndM
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                VersionInfo.lambda$checkVersion$1((Class) obj, (VersionInfo.Version) obj2);
            }
        });
    }

    public static /* synthetic */ void lambda$checkVersion$0(VersionInfo versionInfo, ArrayMap versions, Class aClass, Version version) {
        Version v = (Version) versions.remove(aClass);
        if (v == null) {
            v = versionInfo.createVersion(aClass);
        }
        if (v == null) {
            throw new InvalidVersionException(aClass.getSimpleName() + " does not provide an interface", false);
        }
        if (v.mVersion != version.mVersion) {
            throw new InvalidVersionException(aClass, v.mVersion < version.mVersion, v.mVersion, version.mVersion);
        }
    }

    static /* synthetic */ void lambda$checkVersion$1(Class aClass, Version version) {
        if (version.mRequired) {
            throw new InvalidVersionException("Missing required dependency " + aClass.getSimpleName(), false);
        }
    }

    private Version createVersion(Class<?> cls) {
        ProvidesInterface provider = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (provider != null) {
            return new Version(provider.version(), false);
        }
        return null;
    }

    public <T> boolean hasClass(Class<T> cls) {
        return this.mVersions.containsKey(cls);
    }

    public static class InvalidVersionException extends RuntimeException {
        private final boolean mTooNew;

        public InvalidVersionException(String str, boolean tooNew) {
            super(str);
            this.mTooNew = tooNew;
        }

        public InvalidVersionException(Class<?> cls, boolean tooNew, int expected, int actual) {
            super(cls.getSimpleName() + " expected version " + expected + " but had " + actual);
            this.mTooNew = tooNew;
        }

        public boolean isTooNew() {
            return this.mTooNew;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Version {
        private final boolean mRequired;
        private final int mVersion;

        public Version(int version, boolean required) {
            this.mVersion = version;
            this.mRequired = required;
        }
    }
}
