package androidx.lifecycle;

/* JADX INFO: loaded from: classes.dex */
public abstract class LiveData<T> {
    private static final Object NOT_SET = new Object();

    class LifecycleBoundObserver extends LiveData<T>.ObserverWrapper implements GenericLifecycleObserver {
    }

    private abstract class ObserverWrapper {
    }
}
