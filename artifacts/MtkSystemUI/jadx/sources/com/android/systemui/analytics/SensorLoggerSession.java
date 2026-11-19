package com.android.systemui.analytics;

import android.hardware.SensorEvent;
import android.os.Build;
import android.view.MotionEvent;
import com.android.systemui.statusbar.phone.nano.TouchAnalyticsProto;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class SensorLoggerSession {
    private long mEndTimestampMillis;
    private final long mStartSystemTimeNanos;
    private final long mStartTimestampMillis;
    private int mTouchAreaHeight;
    private int mTouchAreaWidth;
    private ArrayList<TouchAnalyticsProto.Session.TouchEvent> mMotionEvents = new ArrayList<>();
    private ArrayList<TouchAnalyticsProto.Session.SensorEvent> mSensorEvents = new ArrayList<>();
    private ArrayList<TouchAnalyticsProto.Session.PhoneEvent> mPhoneEvents = new ArrayList<>();
    private int mResult = 2;
    private int mType = 3;

    public SensorLoggerSession(long startTimestampMillis, long startSystemTimeNanos) {
        this.mStartTimestampMillis = startTimestampMillis;
        this.mStartSystemTimeNanos = startSystemTimeNanos;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public void end(long endTimestampMillis, int result) {
        this.mResult = result;
        this.mEndTimestampMillis = endTimestampMillis;
    }

    public void addMotionEvent(MotionEvent motionEvent) {
        TouchAnalyticsProto.Session.TouchEvent event = motionEventToProto(motionEvent);
        this.mMotionEvents.add(event);
    }

    public void addSensorEvent(SensorEvent eventOrig, long systemTimeNanos) {
        TouchAnalyticsProto.Session.SensorEvent event = sensorEventToProto(eventOrig, systemTimeNanos);
        this.mSensorEvents.add(event);
    }

    public void addPhoneEvent(int eventType, long systemTimeNanos) {
        TouchAnalyticsProto.Session.PhoneEvent event = phoneEventToProto(eventType, systemTimeNanos);
        this.mPhoneEvents.add(event);
    }

    public String toString() {
        return "Session{mStartTimestampMillis=" + this.mStartTimestampMillis + ", mStartSystemTimeNanos=" + this.mStartSystemTimeNanos + ", mEndTimestampMillis=" + this.mEndTimestampMillis + ", mResult=" + this.mResult + ", mTouchAreaHeight=" + this.mTouchAreaHeight + ", mTouchAreaWidth=" + this.mTouchAreaWidth + ", mMotionEvents=[size=" + this.mMotionEvents.size() + "], mSensorEvents=[size=" + this.mSensorEvents.size() + "], mPhoneEvents=[size=" + this.mPhoneEvents.size() + "]}";
    }

    public TouchAnalyticsProto.Session toProto() {
        TouchAnalyticsProto.Session proto = new TouchAnalyticsProto.Session();
        proto.setStartTimestampMillis(this.mStartTimestampMillis);
        proto.setDurationMillis(this.mEndTimestampMillis - this.mStartTimestampMillis);
        proto.setBuild(Build.FINGERPRINT);
        proto.setResult(this.mResult);
        proto.setType(this.mType);
        proto.sensorEvents = (TouchAnalyticsProto.Session.SensorEvent[]) this.mSensorEvents.toArray(proto.sensorEvents);
        proto.touchEvents = (TouchAnalyticsProto.Session.TouchEvent[]) this.mMotionEvents.toArray(proto.touchEvents);
        proto.phoneEvents = (TouchAnalyticsProto.Session.PhoneEvent[]) this.mPhoneEvents.toArray(proto.phoneEvents);
        proto.setTouchAreaWidth(this.mTouchAreaWidth);
        proto.setTouchAreaHeight(this.mTouchAreaHeight);
        return proto;
    }

    private TouchAnalyticsProto.Session.PhoneEvent phoneEventToProto(int eventType, long sysTimeNanos) {
        TouchAnalyticsProto.Session.PhoneEvent proto = new TouchAnalyticsProto.Session.PhoneEvent();
        proto.setType(eventType);
        proto.setTimeOffsetNanos(sysTimeNanos - this.mStartSystemTimeNanos);
        return proto;
    }

    private TouchAnalyticsProto.Session.SensorEvent sensorEventToProto(SensorEvent ev, long sysTimeNanos) {
        TouchAnalyticsProto.Session.SensorEvent proto = new TouchAnalyticsProto.Session.SensorEvent();
        proto.setType(ev.sensor.getType());
        proto.setTimeOffsetNanos(sysTimeNanos - this.mStartSystemTimeNanos);
        proto.setTimestamp(ev.timestamp);
        proto.values = (float[]) ev.values.clone();
        return proto;
    }

    private TouchAnalyticsProto.Session.TouchEvent motionEventToProto(MotionEvent ev) {
        int count = ev.getPointerCount();
        TouchAnalyticsProto.Session.TouchEvent proto = new TouchAnalyticsProto.Session.TouchEvent();
        proto.setTimeOffsetNanos(ev.getEventTimeNano() - this.mStartSystemTimeNanos);
        proto.setAction(ev.getActionMasked());
        proto.setActionIndex(ev.getActionIndex());
        proto.pointers = new TouchAnalyticsProto.Session.TouchEvent.Pointer[count];
        for (int i = 0; i < count; i++) {
            TouchAnalyticsProto.Session.TouchEvent.Pointer p = new TouchAnalyticsProto.Session.TouchEvent.Pointer();
            p.setX(ev.getX(i));
            p.setY(ev.getY(i));
            p.setSize(ev.getSize(i));
            p.setPressure(ev.getPressure(i));
            p.setId(ev.getPointerId(i));
            proto.pointers[i] = p;
        }
        return proto;
    }

    public void setTouchArea(int width, int height) {
        this.mTouchAreaWidth = width;
        this.mTouchAreaHeight = height;
    }

    public int getResult() {
        return this.mResult;
    }

    public long getStartTimestampMillis() {
        return this.mStartTimestampMillis;
    }
}
