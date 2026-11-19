package com.android.systemui.qs;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.BrightnessController;
import com.android.systemui.settings.ToggleSlider;
import com.android.systemui.settings.ToggleSliderView;
import com.android.systemui.settings.VolumeController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ExternalParameterManager;
import com.mediatek.systemui.ext.IQuickSettingsPlugin;
import com.mediatek.systemui.ext.OpSystemUICustomizationFactoryBase;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: classes.dex */
public class QSPanel extends LinearLayout implements QSHost.Callback, BrightnessMirrorController.BrightnessMirrorListener, TunerService.Tunable {
    private BrightnessController mBrightnessController;
    private BrightnessMirrorController mBrightnessMirrorController;
    protected final View mBrightnessView;
    private QSDetail.Callback mCallback;
    protected final Context mContext;
    private QSCustomizer mCustomizePanel;
    private Record mDetailRecord;
    private View mDivider;
    protected boolean mExpanded;
    protected QSSecurityFooter mFooter;
    private PageIndicator mFooterPageIndicator;
    private boolean mGridContentVisible;
    private final H mHandler;
    protected QSTileHost mHost;
    protected boolean mListening;
    private final MetricsLogger mMetricsLogger;
    private PageIndicator mPanelPageIndicator;
    private final QSTileRevealController mQsTileRevealController;
    private IQuickSettingsPlugin mQuickSettingsExt;
    protected final ArrayList<TileRecord> mRecords;
    protected QSTileLayout mTileLayout;
    private VolumeController mVolumeController;
    protected final View mVolumeView;

    public static final class TileRecord extends Record {
        public QSTile.Callback callback;
        public boolean scanState;
        public QSTile tile;
        public QSTileView tileView;
    }

    public QSPanel(Context context) {
        this(context, null);
    }

    public QSPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRecords = new ArrayList<>();
        this.mHandler = new H();
        this.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        this.mGridContentVisible = true;
        this.mQuickSettingsExt = null;
        this.mContext = context;
        setOrientation(1);
        this.mBrightnessView = LayoutInflater.from(this.mContext).inflate(R.layout.quick_settings_brightness_dialog, (ViewGroup) this, false);
        if (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || (ExternalParameterManager.ETHEME_SUB_16 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_22 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_37 != ExternalParameterManager.ETHEME_SUB && ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB)) {
            addView(this.mBrightnessView);
        }
        this.mVolumeView = LayoutInflater.from(this.mContext).inflate(R.layout.quick_settings_volume_dialog, (ViewGroup) this, false);
        if (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB) {
            addView(this.mVolumeView);
        }
        this.mQuickSettingsExt = OpSystemUICustomizationFactoryBase.getOpFactory(context).makeQuickSettings(context);
        if (this.mQuickSettingsExt != null) {
            this.mQuickSettingsExt.addOpViews(this);
        }
        this.mTileLayout = (QSTileLayout) LayoutInflater.from(this.mContext).inflate(R.layout.qs_paged_tile_layout, (ViewGroup) this, false);
        this.mTileLayout.setListening(this.mListening);
        addView((View) this.mTileLayout);
        if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB) {
            addView(this.mVolumeView);
        }
        if (ExternalParameterManager.ETHEME_GOD_400 == ExternalParameterManager.ETHEME_GOD && (ExternalParameterManager.ETHEME_SUB_16 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_22 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_37 == ExternalParameterManager.ETHEME_SUB || ExternalParameterManager.ETHEME_SUB_65 == ExternalParameterManager.ETHEME_SUB)) {
            addView(this.mBrightnessView);
        }
        this.mPanelPageIndicator = (PageIndicator) LayoutInflater.from(context).inflate(R.layout.qs_page_indicator, (ViewGroup) this, false);
        addView(this.mPanelPageIndicator);
        ((PagedTileLayout) this.mTileLayout).setPageIndicator(this.mPanelPageIndicator);
        this.mQsTileRevealController = new QSTileRevealController(this.mContext, this, (PagedTileLayout) this.mTileLayout);
        addDivider();
        this.mFooter = new QSSecurityFooter(this, context);
        addView(this.mFooter.getView());
        updateResources();
        if (ExternalParameterManager.ETHEME_GOD_400 != ExternalParameterManager.ETHEME_GOD || ExternalParameterManager.ETHEME_SUB_65 != ExternalParameterManager.ETHEME_SUB) {
            this.mBrightnessController = new BrightnessController(getContext(), (ImageView) findViewById(R.id.brightness_icon), (ToggleSlider) findViewById(R.id.brightness_slider));
            this.mVolumeController = new VolumeController(getContext(), (ImageView) findViewById(R.id.volume_icon), (ToggleSlider) findViewById(R.id.volume_slider));
        } else {
            this.mBrightnessController = new BrightnessController(getContext(), (ImageView) findViewById(R.id.brightness_icon), (ToggleSlider) findViewById(R.id.brightness_slider), (TextView) findViewById(R.id.brightness_text), true);
            this.mVolumeController = new VolumeController(getContext(), (ImageView) findViewById(R.id.volume_icon), (ToggleSlider) findViewById(R.id.volume_slider), (TextView) findViewById(R.id.volume_text), true);
        }
    }

    protected void addDivider() {
        this.mDivider = LayoutInflater.from(this.mContext).inflate(R.layout.qs_divider, (ViewGroup) this, false);
        this.mDivider.setBackgroundColor(Utils.applyAlpha(this.mDivider.getAlpha(), QSTileImpl.getColorForState(this.mContext, 2)));
        addView(this.mDivider);
    }

    public View getDivider() {
        return this.mDivider;
    }

    public View getPageIndicator() {
        return this.mPanelPageIndicator;
    }

    public QSTileRevealController getQsTileRevealController() {
        return this.mQsTileRevealController;
    }

    public boolean isShowingCustomize() {
        return this.mCustomizePanel != null && this.mCustomizePanel.isCustomizing();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TunerService tunerService = (TunerService) Dependency.get(TunerService.class);
        tunerService.addTunable(this, "qs_show_brightness");
        if (this.mHost != null) {
            setTiles(this.mHost.getTiles());
        }
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.addCallback((BrightnessMirrorController.BrightnessMirrorListener) this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        if (this.mHost != null) {
            this.mHost.removeCallback(this);
        }
        for (TileRecord record : this.mRecords) {
            record.tile.removeCallbacks();
        }
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.removeCallback((BrightnessMirrorController.BrightnessMirrorListener) this);
        }
        super.onDetachedFromWindow();
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public void onTilesChanged() {
        setTiles(this.mHost.getTiles());
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public void onTuningChanged(String key, String newValue) {
        if ("qs_show_brightness".equals(key)) {
            updateViewVisibilityForTuningValue(this.mBrightnessView, newValue);
        }
    }

    private void updateViewVisibilityForTuningValue(View view, String newValue) {
        view.setVisibility((newValue == null || Integer.parseInt(newValue) != 0) ? 0 : 8);
    }

    public void openDetails(String subPanel) {
        QSTile tile = getTile(subPanel);
        showDetailAdapter(true, tile.getDetailAdapter(), new int[]{getWidth() / 2, 0});
    }

    private QSTile getTile(String subPanel) {
        for (int i = 0; i < this.mRecords.size(); i++) {
            if (subPanel.equals(this.mRecords.get(i).tile.getTileSpec())) {
                return this.mRecords.get(i).tile;
            }
        }
        return this.mHost.createTile(subPanel);
    }

    public void setBrightnessMirror(BrightnessMirrorController c) {
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.removeCallback((BrightnessMirrorController.BrightnessMirrorListener) this);
        }
        this.mBrightnessMirrorController = c;
        if (this.mBrightnessMirrorController != null) {
            this.mBrightnessMirrorController.addCallback((BrightnessMirrorController.BrightnessMirrorListener) this);
        }
        updateBrightnessMirror();
    }

    @Override // com.android.systemui.statusbar.policy.BrightnessMirrorController.BrightnessMirrorListener
    public void onBrightnessMirrorReinflated(View brightnessMirror) {
        updateBrightnessMirror();
    }

    View getBrightnessView() {
        return this.mBrightnessView;
    }

    View getVolumeView() {
        return this.mVolumeView;
    }

    public void setCallback(QSDetail.Callback callback) {
        this.mCallback = callback;
    }

    public void setHost(QSTileHost host, QSCustomizer customizer) {
        this.mHost = host;
        this.mHost.addCallback(this);
        setTiles(this.mHost.getTiles());
        this.mFooter.setHostEnvironment(host);
        this.mCustomizePanel = customizer;
        if (this.mCustomizePanel != null) {
            this.mCustomizePanel.setHost(this.mHost);
        }
        this.mQuickSettingsExt.setHostAppInstance(host);
    }

    public void setFooterPageIndicator(PageIndicator pageIndicator) {
        if (this.mTileLayout instanceof PagedTileLayout) {
            this.mFooterPageIndicator = pageIndicator;
            updatePageIndicator();
        }
    }

    private void updatePageIndicator() {
        if (this.mTileLayout instanceof PagedTileLayout) {
            boolean shouldUseFooterPageIndicator = getResources().getConfiguration().orientation == 2 && this.mFooterPageIndicator != null;
            this.mPanelPageIndicator.setVisibility(8);
            if (this.mFooterPageIndicator != null) {
                this.mFooterPageIndicator.setVisibility(8);
            }
            ((PagedTileLayout) this.mTileLayout).setPageIndicator(shouldUseFooterPageIndicator ? this.mFooterPageIndicator : this.mPanelPageIndicator);
        }
    }

    public QSTileHost getHost() {
        return this.mHost;
    }

    public void updateResources() {
        Resources res = this.mContext.getResources();
        setPadding(0, res.getDimensionPixelSize(R.dimen.qs_panel_padding_top), 0, res.getDimensionPixelSize(R.dimen.qs_panel_padding_bottom));
        updatePageIndicator();
        for (TileRecord r : this.mRecords) {
            r.tile.clearState();
        }
        if (this.mListening) {
            refreshAllTiles();
        }
        if (this.mTileLayout != null) {
            this.mTileLayout.updateResources();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mFooter.onConfigurationChanged();
        updateBrightnessMirror();
    }

    public void updateBrightnessMirror() {
        if (this.mBrightnessMirrorController != null) {
            ToggleSliderView brightnessSlider = (ToggleSliderView) findViewById(R.id.brightness_slider);
            ToggleSliderView mirrorSlider = (ToggleSliderView) this.mBrightnessMirrorController.getMirror().findViewById(R.id.brightness_slider);
            brightnessSlider.setMirror(mirrorSlider);
            brightnessSlider.setMirrorController(this.mBrightnessMirrorController);
        }
    }

    public void setExpanded(boolean expanded) {
        if (this.mExpanded == expanded) {
            return;
        }
        this.mExpanded = expanded;
        if (!this.mExpanded && (this.mTileLayout instanceof PagedTileLayout)) {
            ((PagedTileLayout) this.mTileLayout).setCurrentItem(0, false);
        }
        this.mMetricsLogger.visibility(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBar, this.mExpanded);
        if (!this.mExpanded) {
            closeDetail();
        } else {
            logTiles();
        }
    }

    public void setPageListener(PagedTileLayout.PageListener pageListener) {
        if (this.mTileLayout instanceof PagedTileLayout) {
            ((PagedTileLayout) this.mTileLayout).setPageListener(pageListener);
        }
    }

    public boolean isExpanded() {
        return this.mExpanded;
    }

    public void setListening(boolean listening) {
        if (this.mListening == listening) {
            return;
        }
        this.mListening = listening;
        if (this.mTileLayout != null) {
            this.mTileLayout.setListening(listening);
        }
        this.mFooter.setListening(this.mListening);
        if (this.mListening) {
            refreshAllTiles();
        }
        if (this.mBrightnessView.getVisibility() == 0) {
            if (listening) {
                this.mBrightnessController.registerCallbacks();
            } else {
                this.mBrightnessController.unregisterCallbacks();
            }
        }
        if (this.mVolumeView.getVisibility() == 0 && listening) {
            this.mVolumeController.registerCallbacks();
        }
        if (this.mQuickSettingsExt != null) {
            if (listening) {
                this.mQuickSettingsExt.registerCallbacks();
            } else {
                this.mQuickSettingsExt.unregisterCallbacks();
            }
        }
    }

    public void refreshAllTiles() {
        this.mBrightnessController.checkRestrictionAndSetEnabled();
        for (TileRecord r : this.mRecords) {
            r.tile.refreshState();
        }
        this.mFooter.refreshState();
    }

    public void showDetailAdapter(boolean show, DetailAdapter adapter, int[] locationInWindow) {
        int xInWindow = locationInWindow[0];
        int yInWindow = locationInWindow[1];
        ((View) getParent()).getLocationInWindow(locationInWindow);
        Record r = new Record();
        r.detailAdapter = adapter;
        r.x = xInWindow - locationInWindow[0];
        r.y = yInWindow - locationInWindow[1];
        locationInWindow[0] = xInWindow;
        locationInWindow[1] = yInWindow;
        showDetail(show, r);
    }

    protected void showDetail(boolean z, Record record) {
        this.mHandler.obtainMessage(1, z ? 1 : 0, 0, record).sendToTarget();
    }

    public void setTiles(Collection<QSTile> tiles) {
        setTiles(tiles, false);
    }

    public void setTiles(Collection<QSTile> tiles, boolean collapsedView) {
        if (!collapsedView) {
            this.mQsTileRevealController.updateRevealedTiles(tiles);
        }
        for (TileRecord record : this.mRecords) {
            this.mTileLayout.removeTile(record);
            record.tile.removeCallback(record.callback);
        }
        this.mRecords.clear();
        for (QSTile tile : tiles) {
            addTile(tile, collapsedView);
        }
    }

    protected void drawTile(TileRecord r, QSTile.State state) {
        r.tileView.onStateChanged(state);
    }

    protected QSTileView createTileView(QSTile tile, boolean collapsedView) {
        return this.mHost.createTileView(tile, collapsedView);
    }

    protected boolean shouldShowDetail() {
        return this.mExpanded;
    }

    protected TileRecord addTile(QSTile tile, boolean collapsedView) {
        final TileRecord r = new TileRecord();
        r.tile = tile;
        r.tileView = createTileView(tile, collapsedView);
        QSTile.Callback callback = new QSTile.Callback() { // from class: com.android.systemui.qs.QSPanel.1
            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onStateChanged(QSTile.State state) {
                QSPanel.this.drawTile(r, state);
            }

            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onShowDetail(boolean show) {
                if (QSPanel.this.shouldShowDetail()) {
                    QSPanel.this.showDetail(show, r);
                }
            }

            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onToggleStateChanged(boolean state) {
                if (QSPanel.this.mDetailRecord == r) {
                    QSPanel.this.fireToggleStateChanged(state);
                }
            }

            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onScanStateChanged(boolean state) {
                r.scanState = state;
                if (QSPanel.this.mDetailRecord == r) {
                    QSPanel.this.fireScanStateChanged(r.scanState);
                }
            }

            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onAnnouncementRequested(CharSequence announcement) {
                if (announcement != null) {
                    QSPanel.this.mHandler.obtainMessage(3, announcement).sendToTarget();
                }
            }
        };
        r.tile.addCallback(callback);
        r.callback = callback;
        r.tileView.init(r.tile);
        r.tile.refreshState();
        this.mRecords.add(r);
        if (this.mTileLayout != null) {
            this.mTileLayout.addTile(r);
        }
        return r;
    }

    public void showEdit(final View v) {
        v.post(new Runnable() { // from class: com.android.systemui.qs.QSPanel.2
            @Override // java.lang.Runnable
            public void run() {
                if (QSPanel.this.mCustomizePanel != null && !QSPanel.this.mCustomizePanel.isCustomizing()) {
                    int[] loc = new int[2];
                    v.getLocationInWindow(loc);
                    int x = loc[0] + (v.getWidth() / 2);
                    int y = loc[1] + (v.getHeight() / 2);
                    QSPanel.this.mCustomizePanel.show(x, y);
                }
            }
        });
    }

    public void closeDetail() {
        if (this.mCustomizePanel != null && this.mCustomizePanel.isShown()) {
            this.mCustomizePanel.hide(this.mCustomizePanel.getWidth() / 2, this.mCustomizePanel.getHeight() / 2);
        } else {
            showDetail(false, this.mDetailRecord);
        }
    }

    protected void handleShowDetail(Record r, boolean show) {
        if (r instanceof TileRecord) {
            handleShowDetailTile((TileRecord) r, show);
            return;
        }
        int x = 0;
        int y = 0;
        if (r != null) {
            x = r.x;
            y = r.y;
        }
        handleShowDetailImpl(r, show, x, y);
    }

    private void handleShowDetailTile(TileRecord r, boolean show) {
        if ((this.mDetailRecord != null) == show && this.mDetailRecord == r) {
            return;
        }
        if (show) {
            r.detailAdapter = r.tile.getDetailAdapter();
            if (r.detailAdapter == null) {
                return;
            }
        }
        r.tile.setDetailListening(show);
        int x = r.tileView.getLeft() + (r.tileView.getWidth() / 2);
        int y = r.tileView.getDetailY() + this.mTileLayout.getOffsetTop(r) + getTop();
        handleShowDetailImpl(r, show, x, y);
    }

    private void handleShowDetailImpl(Record r, boolean show, int x, int y) {
        setDetailRecord(show ? r : null);
        fireShowingDetail(show ? r.detailAdapter : null, x, y);
    }

    protected void setDetailRecord(Record r) {
        if (r == this.mDetailRecord) {
            return;
        }
        this.mDetailRecord = r;
        boolean scanState = (this.mDetailRecord instanceof TileRecord) && ((TileRecord) this.mDetailRecord).scanState;
        fireScanStateChanged(scanState);
    }

    void setGridContentVisibility(boolean visible) {
        int newVis = visible ? 0 : 4;
        setVisibility(newVis);
        if (this.mQuickSettingsExt != null) {
            this.mQuickSettingsExt.setViewsVisibility(newVis);
        }
        if (this.mGridContentVisible != visible) {
            this.mMetricsLogger.visibility(com.android.systemui.plugins.R.styleable.AppCompatTheme_windowActionBar, newVis);
        }
        this.mGridContentVisible = visible;
    }

    private void logTiles() {
        for (int i = 0; i < this.mRecords.size(); i++) {
            QSTile tile = this.mRecords.get(i).tile;
            this.mMetricsLogger.write(tile.populate(new LogMaker(tile.getMetricsCategory()).setType(1)));
        }
    }

    private void fireShowingDetail(DetailAdapter detail, int x, int y) {
        if (this.mCallback != null) {
            this.mCallback.onShowingDetail(detail, x, y);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireToggleStateChanged(boolean state) {
        if (this.mCallback != null) {
            this.mCallback.onToggleStateChanged(state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fireScanStateChanged(boolean state) {
        if (this.mCallback != null) {
            this.mCallback.onScanStateChanged(state);
        }
    }

    public void clickTile(ComponentName tile) {
        String spec = CustomTile.toSpec(tile);
        int N = this.mRecords.size();
        for (int i = 0; i < N; i++) {
            if (this.mRecords.get(i).tile.getTileSpec().equals(spec)) {
                this.mRecords.get(i).tile.click();
                return;
            }
        }
    }

    QSTileLayout getTileLayout() {
        return this.mTileLayout;
    }

    QSTileView getTileView(QSTile tile) {
        for (TileRecord r : this.mRecords) {
            if (r.tile == tile) {
                return r.tileView;
            }
        }
        return null;
    }

    public QSSecurityFooter getFooter() {
        return this.mFooter;
    }

    public void showDeviceMonitoringDialog() {
        this.mFooter.showDeviceMonitoringDialog();
    }

    public void setMargins(int sideMargins) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != this.mTileLayout) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.leftMargin = sideMargins;
                lp.rightMargin = sideMargins;
            }
        }
    }

    private class H extends Handler {
        private H() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                QSPanel.this.handleShowDetail((Record) msg.obj, msg.arg1 != 0);
            } else if (msg.what == 3) {
                QSPanel.this.announceForAccessibility((CharSequence) msg.obj);
            }
        }
    }

    protected static class Record {
        DetailAdapter detailAdapter;
        int x;
        int y;

        protected Record() {
        }
    }

    public interface QSTileLayout {
        void addTile(TileRecord tileRecord);

        int getOffsetTop(TileRecord tileRecord);

        void removeTile(TileRecord tileRecord);

        void setListening(boolean z);

        boolean updateResources();

        default void setExpansion(float expansion) {
        }
    }
}
