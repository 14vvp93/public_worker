package com.hcn.bluetooth.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;

/* JADX INFO: loaded from: classes.dex */
public final class MusicPlayState implements Parcelable {
    public static final Parcelable.Creator<MusicPlayState> CREATOR = new Parcelable.Creator<MusicPlayState>() { // from class: com.hcn.bluetooth.api.MusicPlayState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicPlayState createFromParcel(Parcel parcel) {
            return new MusicPlayState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MusicPlayState[] newArray(int i) {
            return new MusicPlayState[i];
        }
    };
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    private static final String TAG = "MusicPlayState";
    private long mBufferedPosition;
    private long mDuration;
    private long mPosition;
    private float mSpeed;
    private int mState;
    private long mUpdateTime;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MusicPlayState() {
        this.mState = 1;
        this.mPosition = 0L;
        this.mDuration = 0L;
        this.mSpeed = 1.0f;
        this.mUpdateTime = SystemClock.elapsedRealtime();
        this.mBufferedPosition = 0L;
    }

    private MusicPlayState(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mDuration = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("MusicPlayState {");
        sb.append("state=").append(this.mState);
        sb.append(", position=").append(this.mPosition);
        sb.append(", duration=").append(this.mDuration);
        sb.append(", buffered position=").append(this.mBufferedPosition);
        sb.append(", speed=").append(this.mSpeed);
        sb.append(", updated=").append(this.mUpdateTime);
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mPosition);
        parcel.writeLong(this.mDuration);
        parcel.writeFloat(this.mSpeed);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeLong(this.mBufferedPosition);
    }

    public void readFromParcel(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mDuration = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public long getPosition() {
        return this.mPosition;
    }

    public void setPosition(long j) {
        this.mPosition = j;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public void setDuration(long j) {
        this.mDuration = j;
    }

    public long getBufferedPosition() {
        return this.mBufferedPosition;
    }

    public void setBufferedPosition(long j) {
        this.mBufferedPosition = j;
    }

    public float getPlaybackSpeed() {
        return this.mSpeed;
    }

    public void setPlaybackSpeed(float f) {
        this.mSpeed = f;
    }

    public void reset() {
        this.mState = 1;
        this.mPosition = 0L;
        this.mDuration = 0L;
        this.mSpeed = 1.0f;
        this.mUpdateTime = SystemClock.elapsedRealtime();
        this.mBufferedPosition = 0L;
    }

    public long getLastPositionUpdateTime() {
        return this.mUpdateTime;
    }

    public void setLastPositionUpdateTime(long j) {
        this.mUpdateTime = j;
    }
}
