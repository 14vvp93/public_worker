package androidx.car.widget;

import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public interface IAlphaJumpAdapter {

    public interface Bucket {
        int getIndex();

        CharSequence getLabel();

        boolean isEmpty();
    }

    Collection<Bucket> getAlphaJumpBuckets();

    void onAlphaJumpEnter();

    void onAlphaJumpLeave(Bucket bucket);
}
