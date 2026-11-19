package com.android.systemui.qs;

import android.content.Context;
import android.os.Handler;
import android.util.ArraySet;
import com.android.systemui.Prefs;
import com.android.systemui.plugins.qs.QSTile;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class QSTileRevealController {
    private final Context mContext;
    private final PagedTileLayout mPagedTileLayout;
    private final QSPanel mQSPanel;
    private final ArraySet<String> mTilesToReveal = new ArraySet<>();
    private final Handler mHandler = new Handler();
    private final Runnable mRevealQsTiles = new AnonymousClass1();

    /* JADX INFO: renamed from: com.android.systemui.qs.QSTileRevealController$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public void run() {
            QSTileRevealController.this.mPagedTileLayout.startTileReveal(QSTileRevealController.this.mTilesToReveal, new Runnable() { // from class: com.android.systemui.qs.-$$Lambda$QSTileRevealController$1$gTMt7U-W3YL6K0ko8X3nSQ3r95I
                @Override // java.lang.Runnable
                public final void run() {
                    QSTileRevealController.AnonymousClass1.lambda$run$0(this.f$0);
                }
            });
        }

        public static /* synthetic */ void lambda$run$0(AnonymousClass1 anonymousClass1) {
            if (QSTileRevealController.this.mQSPanel.isExpanded()) {
                QSTileRevealController.this.addTileSpecsToRevealed(QSTileRevealController.this.mTilesToReveal);
                QSTileRevealController.this.mTilesToReveal.clear();
            }
        }
    }

    QSTileRevealController(Context context, QSPanel qsPanel, PagedTileLayout pagedTileLayout) {
        this.mContext = context;
        this.mQSPanel = qsPanel;
        this.mPagedTileLayout = pagedTileLayout;
    }

    public void setExpansion(float expansion) {
        if (expansion == 1.0f) {
            this.mHandler.postDelayed(this.mRevealQsTiles, 500L);
        } else {
            this.mHandler.removeCallbacks(this.mRevealQsTiles);
        }
    }

    public void updateRevealedTiles(Collection<QSTile> tiles) {
        ArraySet<String> tileSpecs = new ArraySet<>();
        for (QSTile tile : tiles) {
            tileSpecs.add(tile.getTileSpec());
        }
        Set<String> revealedTiles = Prefs.getStringSet(this.mContext, "QsTileSpecsRevealed", Collections.EMPTY_SET);
        if (revealedTiles.isEmpty() || this.mQSPanel.isShowingCustomize()) {
            addTileSpecsToRevealed(tileSpecs);
        } else {
            tileSpecs.removeAll(revealedTiles);
            this.mTilesToReveal.addAll((ArraySet<? extends String>) tileSpecs);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addTileSpecsToRevealed(ArraySet<String> specs) {
        ArraySet<String> revealedTiles = new ArraySet<>(Prefs.getStringSet(this.mContext, "QsTileSpecsRevealed", Collections.EMPTY_SET));
        revealedTiles.addAll((ArraySet<? extends String>) specs);
        Prefs.putStringSet(this.mContext, "QsTileSpecsRevealed", revealedTiles);
    }
}
