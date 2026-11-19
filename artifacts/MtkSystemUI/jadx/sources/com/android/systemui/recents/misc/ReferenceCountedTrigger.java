package com.android.systemui.recents.misc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ReferenceCountedTrigger {
    int mCount;
    Runnable mDecrementRunnable;
    Runnable mErrorRunnable;
    ArrayList<Runnable> mFirstIncRunnables;
    Runnable mIncrementRunnable;
    ArrayList<Runnable> mLastDecRunnables;

    public ReferenceCountedTrigger() {
        this(null, null, null);
    }

    public ReferenceCountedTrigger(Runnable firstIncRunnable, Runnable lastDecRunnable, Runnable errorRunanable) {
        this.mFirstIncRunnables = new ArrayList<>();
        this.mLastDecRunnables = new ArrayList<>();
        this.mIncrementRunnable = new Runnable() { // from class: com.android.systemui.recents.misc.ReferenceCountedTrigger.1
            @Override // java.lang.Runnable
            public void run() {
                ReferenceCountedTrigger.this.increment();
            }
        };
        this.mDecrementRunnable = new Runnable() { // from class: com.android.systemui.recents.misc.ReferenceCountedTrigger.2
            @Override // java.lang.Runnable
            public void run() {
                ReferenceCountedTrigger.this.decrement();
            }
        };
        if (firstIncRunnable != null) {
            this.mFirstIncRunnables.add(firstIncRunnable);
        }
        if (lastDecRunnable != null) {
            this.mLastDecRunnables.add(lastDecRunnable);
        }
        this.mErrorRunnable = errorRunanable;
    }

    public void increment() {
        if (this.mCount == 0 && !this.mFirstIncRunnables.isEmpty()) {
            int numRunnables = this.mFirstIncRunnables.size();
            for (int i = 0; i < numRunnables; i++) {
                this.mFirstIncRunnables.get(i).run();
            }
        }
        int numRunnables2 = this.mCount;
        this.mCount = numRunnables2 + 1;
    }

    public void addLastDecrementRunnable(Runnable r) {
        this.mLastDecRunnables.add(r);
    }

    public void decrement() {
        this.mCount--;
        if (this.mCount == 0) {
            flushLastDecrementRunnables();
        } else if (this.mCount < 0) {
            if (this.mErrorRunnable != null) {
                this.mErrorRunnable.run();
                return;
            }
            throw new RuntimeException("Invalid ref count");
        }
    }

    public void flushLastDecrementRunnables() {
        if (!this.mLastDecRunnables.isEmpty()) {
            int numRunnables = this.mLastDecRunnables.size();
            for (int i = 0; i < numRunnables; i++) {
                this.mLastDecRunnables.get(i).run();
            }
        }
        this.mLastDecRunnables.clear();
    }

    public Animator.AnimatorListener decrementOnAnimationEnd() {
        return new AnimatorListenerAdapter() { // from class: com.android.systemui.recents.misc.ReferenceCountedTrigger.3
            private boolean hasEnded;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                if (this.hasEnded) {
                    return;
                }
                ReferenceCountedTrigger.this.decrement();
                this.hasEnded = true;
            }
        };
    }
}
