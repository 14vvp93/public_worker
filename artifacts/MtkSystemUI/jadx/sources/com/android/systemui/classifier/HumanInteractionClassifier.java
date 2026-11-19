package com.android.systemui.classifier;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.SensorEvent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import com.android.systemui.R;
import java.util.ArrayDeque;

/* JADX INFO: loaded from: classes.dex */
public class HumanInteractionClassifier extends Classifier {
    private static HumanInteractionClassifier sInstance = null;
    private final Context mContext;
    private final float mDpi;
    private final GestureClassifier[] mGestureClassifiers;
    private final HistoryEvaluator mHistoryEvaluator;
    private final StrokeClassifier[] mStrokeClassifiers;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final ArrayDeque<MotionEvent> mBufferedEvents = new ArrayDeque<>();
    private boolean mEnableClassifier = false;
    private int mCurrentType = 7;
    protected final ContentObserver mSettingsObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.classifier.HumanInteractionClassifier.1
        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            HumanInteractionClassifier.this.updateConfiguration();
        }
    };

    private HumanInteractionClassifier(Context context) {
        this.mContext = context;
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        this.mDpi = (displayMetrics.xdpi + displayMetrics.ydpi) / 2.0f;
        this.mClassifierData = new ClassifierData(this.mDpi);
        this.mHistoryEvaluator = new HistoryEvaluator();
        this.mStrokeClassifiers = new StrokeClassifier[]{new AnglesClassifier(this.mClassifierData), new SpeedClassifier(this.mClassifierData), new DurationCountClassifier(this.mClassifierData), new EndPointRatioClassifier(this.mClassifierData), new EndPointLengthClassifier(this.mClassifierData), new AccelerationClassifier(this.mClassifierData), new SpeedAnglesClassifier(this.mClassifierData), new LengthCountClassifier(this.mClassifierData), new DirectionClassifier(this.mClassifierData)};
        this.mGestureClassifiers = new GestureClassifier[]{new PointerCountClassifier(this.mClassifierData), new ProximityClassifier(this.mClassifierData)};
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("HIC_enable"), false, this.mSettingsObserver, -1);
        updateConfiguration();
    }

    public static HumanInteractionClassifier getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HumanInteractionClassifier(context);
        }
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConfiguration() {
        this.mEnableClassifier = Settings.Global.getInt(this.mContext.getContentResolver(), "HIC_enable", this.mContext.getResources().getBoolean(R.bool.config_lockscreenAntiFalsingClassifierEnabled) ? 1 : 0) != 0;
    }

    public void setType(int type) {
        this.mCurrentType = type;
    }

    @Override // com.android.systemui.classifier.Classifier
    public void onTouchEvent(MotionEvent event) {
        if (!this.mEnableClassifier) {
            return;
        }
        if (this.mCurrentType == 2) {
            this.mBufferedEvents.add(MotionEvent.obtain(event));
            Point pointEnd = new Point(event.getX() / this.mDpi, event.getY() / this.mDpi);
            while (pointEnd.dist(new Point(this.mBufferedEvents.getFirst().getX() / this.mDpi, this.mBufferedEvents.getFirst().getY() / this.mDpi)) > 0.1f) {
                addTouchEvent(this.mBufferedEvents.getFirst());
                this.mBufferedEvents.remove();
            }
            int action = event.getActionMasked();
            if (action == 1) {
                this.mBufferedEvents.getFirst().setAction(1);
                addTouchEvent(this.mBufferedEvents.getFirst());
                this.mBufferedEvents.clear();
                return;
            }
            return;
        }
        addTouchEvent(event);
    }

    private void addTouchEvent(MotionEvent event) {
        StringBuilder sb;
        this.mClassifierData.update(event);
        for (StrokeClassifier strokeClassifier : this.mStrokeClassifiers) {
            strokeClassifier.onTouchEvent(event);
        }
        for (GestureClassifier gestureClassifier : this.mGestureClassifiers) {
            gestureClassifier.onTouchEvent(event);
        }
        int size = this.mClassifierData.getEndingStrokes().size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            Stroke stroke = this.mClassifierData.getEndingStrokes().get(i);
            sb = FalsingLog.ENABLED ? new StringBuilder("stroke") : null;
            float evaluation = 0.0f;
            for (StrokeClassifier c : this.mStrokeClassifiers) {
                float e = c.getFalseTouchEvaluation(this.mCurrentType, stroke);
                if (FalsingLog.ENABLED) {
                    String tag = c.getTag();
                    sb.append(" ");
                    sb.append(e >= 1.0f ? tag : tag.toLowerCase());
                    sb.append("=");
                    sb.append(e);
                }
                evaluation += e;
            }
            if (FalsingLog.ENABLED) {
                FalsingLog.i(" addTouchEvent", sb.toString());
            }
            this.mHistoryEvaluator.addStroke(evaluation);
            i++;
        }
        int action = event.getActionMasked();
        if (action == 1 || action == 3) {
            float evaluation2 = 0.0f;
            sb = FalsingLog.ENABLED ? new StringBuilder("gesture") : null;
            for (GestureClassifier c2 : this.mGestureClassifiers) {
                float e2 = c2.getFalseTouchEvaluation(this.mCurrentType);
                if (FalsingLog.ENABLED) {
                    String tag2 = c2.getTag();
                    sb.append(" ");
                    sb.append(e2 >= 1.0f ? tag2 : tag2.toLowerCase());
                    sb.append("=");
                    sb.append(e2);
                }
                evaluation2 += e2;
            }
            if (FalsingLog.ENABLED) {
                FalsingLog.i(" addTouchEvent", sb.toString());
            }
            this.mHistoryEvaluator.addGesture(evaluation2);
            setType(7);
        }
        this.mClassifierData.cleanUp(event);
    }

    @Override // com.android.systemui.classifier.Classifier
    public void onSensorChanged(SensorEvent event) {
        for (Classifier c : this.mStrokeClassifiers) {
            c.onSensorChanged(event);
        }
        for (Classifier c2 : this.mGestureClassifiers) {
            c2.onSensorChanged(event);
        }
    }

    public boolean isFalseTouch() {
        if (!this.mEnableClassifier) {
            return false;
        }
        float evaluation = this.mHistoryEvaluator.getEvaluation();
        boolean result = evaluation >= 5.0f;
        if (FalsingLog.ENABLED) {
            StringBuilder sb = new StringBuilder();
            sb.append("eval=");
            sb.append(evaluation);
            sb.append(" result=");
            sb.append(result ? 1 : 0);
            FalsingLog.i("isFalseTouch", sb.toString());
        }
        return result;
    }

    public boolean isEnabled() {
        return this.mEnableClassifier;
    }

    @Override // com.android.systemui.classifier.Classifier
    public String getTag() {
        return "HIC";
    }
}
