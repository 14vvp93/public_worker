package com.android.settingslib.core.lifecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.android.settingslib.core.lifecycle.events.OnAttach;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnCreateOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnOptionsItemSelected;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnPrepareOptionsMenu;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Lifecycle extends LifecycleRegistry {
    private final List<LifecycleObserver> mObservers;
    private final LifecycleProxy mProxy;

    public Lifecycle(LifecycleOwner provider) {
        super(provider);
        this.mObservers = new ArrayList();
        this.mProxy = new LifecycleProxy();
        addObserver(this.mProxy);
    }

    @Override // android.arch.lifecycle.LifecycleRegistry, android.arch.lifecycle.Lifecycle
    public void addObserver(android.arch.lifecycle.LifecycleObserver observer) {
        ThreadUtils.ensureMainThread();
        super.addObserver(observer);
        if (observer instanceof LifecycleObserver) {
            this.mObservers.add((LifecycleObserver) observer);
        }
    }

    @Override // android.arch.lifecycle.LifecycleRegistry, android.arch.lifecycle.Lifecycle
    public void removeObserver(android.arch.lifecycle.LifecycleObserver observer) {
        ThreadUtils.ensureMainThread();
        super.removeObserver(observer);
        if (observer instanceof LifecycleObserver) {
            this.mObservers.remove(observer);
        }
    }

    public void onAttach(Context context) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnAttach) {
                ((OnAttach) observer).onAttach(context);
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnCreate) {
                ((OnCreate) observer).onCreate(savedInstanceState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStart() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnStart) {
                ((OnStart) observer).onStart();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onResume() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnResume) {
                ((OnResume) observer).onResume();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPause() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnPause) {
                ((OnPause) observer).onPause();
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnSaveInstanceState) {
                ((OnSaveInstanceState) observer).onSaveInstanceState(outState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStop() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnStop) {
                ((OnStop) observer).onStop();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onDestroy() {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnDestroy) {
                ((OnDestroy) observer).onDestroy();
            }
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnCreateOptionsMenu) {
                ((OnCreateOptionsMenu) observer).onCreateOptionsMenu(menu, inflater);
            }
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if (observer instanceof OnPrepareOptionsMenu) {
                ((OnPrepareOptionsMenu) observer).onPrepareOptionsMenu(menu);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int size = this.mObservers.size();
        for (int i = 0; i < size; i++) {
            LifecycleObserver observer = this.mObservers.get(i);
            if ((observer instanceof OnOptionsItemSelected) && ((OnOptionsItemSelected) observer).onOptionsItemSelected(menuItem)) {
                return true;
            }
        }
        return false;
    }

    private class LifecycleProxy implements android.arch.lifecycle.LifecycleObserver {
        private LifecycleProxy() {
        }

        @OnLifecycleEvent(android.arch.lifecycle.Lifecycle.Event.ON_ANY)
        public void onLifecycleEvent(LifecycleOwner owner, android.arch.lifecycle.Lifecycle.Event event) {
            switch (event) {
                case ON_START:
                    Lifecycle.this.onStart();
                    break;
                case ON_RESUME:
                    Lifecycle.this.onResume();
                    break;
                case ON_PAUSE:
                    Lifecycle.this.onPause();
                    break;
                case ON_STOP:
                    Lifecycle.this.onStop();
                    break;
                case ON_DESTROY:
                    Lifecycle.this.onDestroy();
                    break;
                case ON_ANY:
                    Log.wtf("LifecycleObserver", "Should not receive an 'ANY' event!");
                    break;
            }
        }
    }
}
