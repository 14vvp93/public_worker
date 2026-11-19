package com.hcn.autocan.hiworld.gm.air;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.hcn.autocan.hiworld.gm.setting.MyContants;

/* JADX INFO: loaded from: classes.dex */
public class AIR implements Parcelable {
    public static final Parcelable.Creator<AIR> CREATOR = new Parcelable.Creator<AIR>() { // from class: com.hcn.autocan.hiworld.gm.air.AIR.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIR createFromParcel(Parcel in) {
            return new AIR(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AIR[] newArray(int size) {
            return new AIR[size];
        }
    };
    private int ACMax;
    private String TAG;
    private int acFlag;
    private int airBag;
    private int aos;
    private int aq;
    private int autoDisFog;
    private int autoDisfog;
    private int autoLevel;
    private int autoUpperVent;
    private int beep;
    private int bigAutoFlag;
    private int chargeLevel;
    private int comfort;
    private int cool;
    private int cycleMode;
    private int dual;
    private int econ;
    private int energyConsume;
    private int fast;
    private int filter;
    private int flow;
    private int follower;
    private int frontSeatBlower;
    private int gCleanStatus;
    private int glassFrontFlag;
    private int heat;
    private int heat_steer;
    private int hiFlag;
    private int info;
    private int innerCycle;
    private int[] invisibleID;
    private int[] invisibleID_REAR;
    private int ion;
    private int leftChairHeat;
    private int leftChairWind;
    private int leftTempLevel;
    private String leftTempNum;
    private float leftTempValue;
    private int lowFlag;
    private int mAutoMode;
    private int mAutoSwing;
    private int mDisplay;
    private int mFrontDisFog;
    private int mLeftMassge;
    private int mLeftWaist;
    private int mLock;
    private int mNanoe;
    private int mRightMassage;
    private int mRightWaist;
    private int manual;
    private int manualStatus;
    private int maxFront;
    private int mirrorBackFlag;
    private int mono;
    private int nomal;
    private int outerCycle;
    private int powerFlag;
    private int ptc;
    private int rear;
    private int rearDisFog;
    private int rear_lock;
    private int refrigeration;
    private int rest;
    private int rightAuto;
    private int rightChairHeat;
    private int rightChairWind;
    private int rightTempLevel;
    private String rightTempNum;
    private float rightTempValue;
    private int smallAutoFlag;
    private int soft;
    private int steerChairHeat;
    private String str_unit;
    private int sync;
    private int temp;
    private int unit;
    private int uvc;
    private int[] visibleID;
    private int[] visibleID_REAR;
    private int winFrontHeatSp;
    private int windIce;
    private int windIntensity;
    private int windMax;
    private int windMode;
    private int windMode_right;
    private int wind_level;
    private int zone;

    public AIR() {
        this.TAG = MyContants.TAG + "AIR";
        this.windMax = 7;
        this.str_unit = " C";
        this.winFrontHeatSp = 0;
        this.heat_steer = 0;
        this.mLock = 0;
        this.rear_lock = 0;
        this.info = -1;
        this.filter = -1;
        this.mNanoe = 0;
        this.mAutoSwing = 0;
    }

    protected AIR(Parcel in) {
        this.TAG = MyContants.TAG + "AIR";
        this.windMax = 7;
        this.str_unit = " C";
        this.winFrontHeatSp = 0;
        this.heat_steer = 0;
        this.mLock = 0;
        this.rear_lock = 0;
        this.info = -1;
        this.filter = -1;
        this.mNanoe = 0;
        this.mAutoSwing = 0;
        this.TAG = in.readString();
        this.powerFlag = in.readInt();
        this.wind_level = in.readInt();
        this.windMode = in.readInt();
        this.windMode_right = in.readInt();
        this.bigAutoFlag = in.readInt();
        this.smallAutoFlag = in.readInt();
        this.acFlag = in.readInt();
        this.lowFlag = in.readInt();
        this.glassFrontFlag = in.readInt();
        this.mirrorBackFlag = in.readInt();
        this.hiFlag = in.readInt();
        this.cycleMode = in.readInt();
        this.windMax = in.readInt();
        this.leftTempNum = in.readString();
        this.rightTempNum = in.readString();
        this.unit = in.readInt();
        this.str_unit = in.readString();
        this.dual = in.readInt();
        this.flow = in.readInt();
        this.mFrontDisFog = in.readInt();
        this.mAutoMode = in.readInt();
        this.rear = in.readInt();
        this.mDisplay = in.readInt();
        this.sync = in.readInt();
        this.ACMax = in.readInt();
        this.invisibleID = in.createIntArray();
        this.visibleID = in.createIntArray();
        this.invisibleID_REAR = in.createIntArray();
        this.visibleID_REAR = in.createIntArray();
        this.maxFront = in.readInt();
        this.steerChairHeat = in.readInt();
        this.leftChairWind = in.readInt();
        this.rightChairWind = in.readInt();
        this.mLeftWaist = in.readInt();
        this.mRightWaist = in.readInt();
        this.mLeftMassge = in.readInt();
        this.mRightMassage = in.readInt();
        this.autoLevel = in.readInt();
        this.windIntensity = in.readInt();
        this.econ = in.readInt();
        this.ion = in.readInt();
        this.innerCycle = in.readInt();
        this.outerCycle = in.readInt();
        this.zone = in.readInt();
        this.winFrontHeatSp = in.readInt();
        this.heat_steer = in.readInt();
        this.mLock = in.readInt();
        this.rear_lock = in.readInt();
        this.info = in.readInt();
        this.filter = in.readInt();
        this.mNanoe = in.readInt();
        this.mAutoSwing = in.readInt();
        this.soft = in.readInt();
        this.nomal = in.readInt();
        this.fast = in.readInt();
        this.follower = in.readInt();
        this.rest = in.readInt();
        this.temp = in.readInt();
        this.aq = in.readInt();
        this.ptc = in.readInt();
        this.uvc = in.readInt();
        this.beep = in.readInt();
        this.heat = in.readInt();
        this.manual = in.readInt();
        this.autoDisfog = in.readInt();
        this.comfort = in.readInt();
        this.energyConsume = in.readInt();
        this.chargeLevel = in.readInt();
        this.cool = in.readInt();
        this.leftTempValue = in.readFloat();
        this.rightTempValue = in.readFloat();
        this.leftTempLevel = in.readInt();
        this.rightTempLevel = in.readInt();
        this.aos = in.readInt();
        this.airBag = in.readInt();
        this.rearDisFog = in.readInt();
        this.autoDisFog = in.readInt();
        this.autoUpperVent = in.readInt();
        this.leftChairHeat = in.readInt();
        this.rightChairHeat = in.readInt();
        this.windIce = in.readInt();
        this.refrigeration = in.readInt();
        this.frontSeatBlower = in.readInt();
        this.manualStatus = in.readInt();
        this.gCleanStatus = in.readInt();
        this.rightAuto = in.readInt();
        this.mono = in.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.TAG);
        dest.writeInt(this.powerFlag);
        dest.writeInt(this.wind_level);
        dest.writeInt(this.windMode);
        dest.writeInt(this.windMode_right);
        dest.writeInt(this.bigAutoFlag);
        dest.writeInt(this.smallAutoFlag);
        dest.writeInt(this.acFlag);
        dest.writeInt(this.lowFlag);
        dest.writeInt(this.glassFrontFlag);
        dest.writeInt(this.mirrorBackFlag);
        dest.writeInt(this.hiFlag);
        dest.writeInt(this.cycleMode);
        dest.writeInt(this.windMax);
        dest.writeString(this.leftTempNum);
        dest.writeString(this.rightTempNum);
        dest.writeInt(this.unit);
        dest.writeString(this.str_unit);
        dest.writeInt(this.dual);
        dest.writeInt(this.flow);
        dest.writeInt(this.mFrontDisFog);
        dest.writeInt(this.mAutoMode);
        dest.writeInt(this.rear);
        dest.writeInt(this.mDisplay);
        dest.writeInt(this.sync);
        dest.writeInt(this.ACMax);
        dest.writeIntArray(this.invisibleID);
        dest.writeIntArray(this.visibleID);
        dest.writeIntArray(this.invisibleID_REAR);
        dest.writeIntArray(this.visibleID_REAR);
        dest.writeInt(this.maxFront);
        dest.writeInt(this.steerChairHeat);
        dest.writeInt(this.leftChairWind);
        dest.writeInt(this.rightChairWind);
        dest.writeInt(this.mLeftWaist);
        dest.writeInt(this.mRightWaist);
        dest.writeInt(this.mLeftMassge);
        dest.writeInt(this.mRightMassage);
        dest.writeInt(this.autoLevel);
        dest.writeInt(this.windIntensity);
        dest.writeInt(this.econ);
        dest.writeInt(this.ion);
        dest.writeInt(this.innerCycle);
        dest.writeInt(this.outerCycle);
        dest.writeInt(this.zone);
        dest.writeInt(this.winFrontHeatSp);
        dest.writeInt(this.heat_steer);
        dest.writeInt(this.mLock);
        dest.writeInt(this.rear_lock);
        dest.writeInt(this.info);
        dest.writeInt(this.filter);
        dest.writeInt(this.mNanoe);
        dest.writeInt(this.mAutoSwing);
        dest.writeInt(this.soft);
        dest.writeInt(this.nomal);
        dest.writeInt(this.fast);
        dest.writeInt(this.follower);
        dest.writeInt(this.rest);
        dest.writeInt(this.temp);
        dest.writeInt(this.aq);
        dest.writeInt(this.ptc);
        dest.writeInt(this.uvc);
        dest.writeInt(this.beep);
        dest.writeInt(this.heat);
        dest.writeInt(this.manual);
        dest.writeInt(this.autoDisfog);
        dest.writeInt(this.comfort);
        dest.writeInt(this.energyConsume);
        dest.writeInt(this.chargeLevel);
        dest.writeInt(this.cool);
        dest.writeFloat(this.leftTempValue);
        dest.writeFloat(this.rightTempValue);
        dest.writeInt(this.leftTempLevel);
        dest.writeInt(this.rightTempLevel);
        dest.writeInt(this.aos);
        dest.writeInt(this.airBag);
        dest.writeInt(this.rearDisFog);
        dest.writeInt(this.autoDisFog);
        dest.writeInt(this.autoUpperVent);
        dest.writeInt(this.leftChairHeat);
        dest.writeInt(this.rightChairHeat);
        dest.writeInt(this.windIce);
        dest.writeInt(this.refrigeration);
        dest.writeInt(this.frontSeatBlower);
        dest.writeInt(this.manualStatus);
        dest.writeInt(this.gCleanStatus);
        dest.writeInt(this.rightAuto);
        dest.writeInt(this.mono);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getPowerFlag() {
        return this.powerFlag;
    }

    public int getWind_level() {
        return this.wind_level;
    }

    public int getWindMode() {
        return this.windMode;
    }

    public int getBigAutoFlag() {
        return this.bigAutoFlag;
    }

    public int getSmallAutoFlag() {
        return this.smallAutoFlag;
    }

    public int getACFlag() {
        return this.acFlag;
    }

    public int getCycleMode() {
        return this.cycleMode;
    }

    public String getLeftTempNum() {
        return getSystemTemp(this.leftTempNum);
    }

    public String getRightTempNum() {
        return getSystemTemp(this.rightTempNum);
    }

    public int getWindLevMax() {
        return this.windMax;
    }

    public int getDual() {
        return this.dual;
    }

    public int getFrontDisFog() {
        return this.mFrontDisFog;
    }

    public int getAutoMode() {
        return this.mAutoMode;
    }

    public int getRear() {
        return this.rear;
    }

    public int getSyncFlag() {
        return this.sync;
    }

    public int getRearDisFog() {
        return this.rearDisFog;
    }

    public int getAutoDisFog() {
        return this.autoDisFog;
    }

    public int getSteerChairHeat() {
        return this.steerChairHeat;
    }

    public int getLeftChairHeat() {
        return this.leftChairHeat;
    }

    public int getRightChairHeat() {
        return this.rightChairHeat;
    }

    public int getACMax() {
        return this.ACMax;
    }

    public int getMaxFront() {
        return this.maxFront;
    }

    public int getLeftChairWind() {
        return this.leftChairWind;
    }

    public int getRightChairWind() {
        return this.rightChairWind;
    }

    public int getAutoLevel() {
        return this.autoLevel;
    }

    public int getWindIntensity() {
        return this.windIntensity;
    }

    public int getEcon() {
        return this.econ;
    }

    public int getRest() {
        return this.rest;
    }

    public int getFollower() {
        return this.follower;
    }

    public int getIon() {
        return this.ion;
    }

    public int getWindModeRight() {
        return this.windMode_right;
    }

    public int getHeatSteer() {
        return this.heat_steer;
    }

    public int getLockRear() {
        return this.mLock;
    }

    public int getFilter() {
        return this.filter;
    }

    public int getNanoe() {
        return this.mNanoe;
    }

    public int getAutoSwing() {
        return this.mAutoSwing;
    }

    public int getZone() {
        return this.zone;
    }

    public int getSoft() {
        return this.soft;
    }

    public int getNomal() {
        return this.nomal;
    }

    public int getFast() {
        return this.fast;
    }

    public int getTemp() {
        return this.temp;
    }

    public int getAirQuality() {
        return this.aq;
    }

    public int getPTC() {
        return this.ptc;
    }

    public int getHeat() {
        return this.heat;
    }

    public int getManual() {
        return this.manual;
    }

    public int getLeftWaist() {
        return this.mLeftWaist;
    }

    public int getRightWaist() {
        return this.mRightWaist;
    }

    public int getLeftMassage() {
        return this.mLeftMassge;
    }

    public int getRightMassage() {
        return this.mRightMassage;
    }

    public static String getSystemTemp(String strTemp) {
        int air_show_flag = MyContants.SYSTEM_TEMP_F;
        if (air_show_flag == 1 && strTemp != null) {
            try {
                if (strTemp.length() != 0 && strTemp.contains("℃")) {
                    int unitIndex = strTemp.indexOf("℃");
                    String strValue = strTemp.substring(0, unitIndex);
                    int tempValue = (int) ((Float.parseFloat(strValue) * 1.8f) + 32.0f);
                    return tempValue + "℉";
                }
            } catch (Exception e) {
                Log.v("Util Error", e.getMessage());
            }
        }
        return strTemp;
    }
}
