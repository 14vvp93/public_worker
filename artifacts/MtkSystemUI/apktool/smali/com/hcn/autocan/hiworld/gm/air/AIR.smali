.class public Lcom/hcn/autocan/hiworld/gm/air/AIR;
.super Ljava/lang/Object;
.source "AIR.java"

# interfaces
.implements Landroid/os/Parcelable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/hcn/autocan/hiworld/gm/air/AIR;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field private ACMax:I

.field private TAG:Ljava/lang/String;

.field private acFlag:I

.field private airBag:I

.field private aos:I

.field private aq:I

.field private autoDisFog:I

.field private autoDisfog:I

.field private autoLevel:I

.field private autoUpperVent:I

.field private beep:I

.field private bigAutoFlag:I

.field private chargeLevel:I

.field private comfort:I

.field private cool:I

.field private cycleMode:I

.field private dual:I

.field private econ:I

.field private energyConsume:I

.field private fast:I

.field private filter:I

.field private flow:I

.field private follower:I

.field private frontSeatBlower:I

.field private gCleanStatus:I

.field private glassFrontFlag:I

.field private heat:I

.field private heat_steer:I

.field private hiFlag:I

.field private info:I

.field private innerCycle:I

.field private invisibleID:[I

.field private invisibleID_REAR:[I

.field private ion:I

.field private leftChairHeat:I

.field private leftChairWind:I

.field private leftTempLevel:I

.field private leftTempNum:Ljava/lang/String;

.field private leftTempValue:F

.field private lowFlag:I

.field private mAutoMode:I

.field private mAutoSwing:I

.field private mDisplay:I

.field private mFrontDisFog:I

.field private mLeftMassge:I

.field private mLeftWaist:I

.field private mLock:I

.field private mNanoe:I

.field private mRightMassage:I

.field private mRightWaist:I

.field private manual:I

.field private manualStatus:I

.field private maxFront:I

.field private mirrorBackFlag:I

.field private mono:I

.field private nomal:I

.field private outerCycle:I

.field private powerFlag:I

.field private ptc:I

.field private rear:I

.field private rearDisFog:I

.field private rear_lock:I

.field private refrigeration:I

.field private rest:I

.field private rightAuto:I

.field private rightChairHeat:I

.field private rightChairWind:I

.field private rightTempLevel:I

.field private rightTempNum:Ljava/lang/String;

.field private rightTempValue:F

.field private smallAutoFlag:I

.field private soft:I

.field private steerChairHeat:I

.field private str_unit:Ljava/lang/String;

.field private sync:I

.field private temp:I

.field private unit:I

.field private uvc:I

.field private visibleID:[I

.field private visibleID_REAR:[I

.field private winFrontHeatSp:I

.field private windIce:I

.field private windIntensity:I

.field private windMax:I

.field private windMode:I

.field private windMode_right:I

.field private wind_level:I

.field private zone:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 291
    new-instance v0, Lcom/hcn/autocan/hiworld/gm/air/AIR$1;

    invoke-direct {v0}, Lcom/hcn/autocan/hiworld/gm/air/AIR$1;-><init>()V

    sput-object v0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 100
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->TAG:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "AIR"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->TAG:Ljava/lang/String;

    .line 29
    const/4 v0, 0x7

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMax:I

    .line 33
    const-string v0, " C"

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->str_unit:Ljava/lang/String;

    .line 61
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->winFrontHeatSp:I

    .line 62
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat_steer:I

    .line 63
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLock:I

    .line 64
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear_lock:I

    .line 65
    const/4 v1, -0x1

    iput v1, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->info:I

    .line 66
    iput v1, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->filter:I

    .line 67
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mNanoe:I

    .line 68
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoSwing:I

    .line 102
    return-void
.end method

.method protected constructor <init>(Landroid/os/Parcel;)V
    .locals 2
    .param p1, "in"    # Landroid/os/Parcel;

    .line 103
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 16
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->TAG:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "AIR"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->TAG:Ljava/lang/String;

    .line 29
    const/4 v0, 0x7

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMax:I

    .line 33
    const-string v0, " C"

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->str_unit:Ljava/lang/String;

    .line 61
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->winFrontHeatSp:I

    .line 62
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat_steer:I

    .line 63
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLock:I

    .line 64
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear_lock:I

    .line 65
    const/4 v1, -0x1

    iput v1, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->info:I

    .line 66
    iput v1, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->filter:I

    .line 67
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mNanoe:I

    .line 68
    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoSwing:I

    .line 104
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->TAG:Ljava/lang/String;

    .line 105
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->powerFlag:I

    .line 106
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->wind_level:I

    .line 107
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode:I

    .line 108
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode_right:I

    .line 109
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->bigAutoFlag:I

    .line 110
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->smallAutoFlag:I

    .line 111
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->acFlag:I

    .line 112
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->lowFlag:I

    .line 113
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->glassFrontFlag:I

    .line 114
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mirrorBackFlag:I

    .line 115
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->hiFlag:I

    .line 116
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->cycleMode:I

    .line 117
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMax:I

    .line 118
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempNum:Ljava/lang/String;

    .line 119
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempNum:Ljava/lang/String;

    .line 120
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->unit:I

    .line 121
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->str_unit:Ljava/lang/String;

    .line 122
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->dual:I

    .line 123
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->flow:I

    .line 124
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mFrontDisFog:I

    .line 125
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoMode:I

    .line 126
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear:I

    .line 127
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mDisplay:I

    .line 128
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->sync:I

    .line 129
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ACMax:I

    .line 130
    invoke-virtual {p1}, Landroid/os/Parcel;->createIntArray()[I

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->invisibleID:[I

    .line 131
    invoke-virtual {p1}, Landroid/os/Parcel;->createIntArray()[I

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->visibleID:[I

    .line 132
    invoke-virtual {p1}, Landroid/os/Parcel;->createIntArray()[I

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->invisibleID_REAR:[I

    .line 133
    invoke-virtual {p1}, Landroid/os/Parcel;->createIntArray()[I

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->visibleID_REAR:[I

    .line 134
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->maxFront:I

    .line 135
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->steerChairHeat:I

    .line 136
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairWind:I

    .line 137
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairWind:I

    .line 138
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftWaist:I

    .line 139
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightWaist:I

    .line 140
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftMassge:I

    .line 141
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightMassage:I

    .line 142
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoLevel:I

    .line 143
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windIntensity:I

    .line 144
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->econ:I

    .line 145
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ion:I

    .line 146
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->innerCycle:I

    .line 147
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->outerCycle:I

    .line 148
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->zone:I

    .line 149
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->winFrontHeatSp:I

    .line 150
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat_steer:I

    .line 151
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLock:I

    .line 152
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear_lock:I

    .line 153
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->info:I

    .line 154
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->filter:I

    .line 155
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mNanoe:I

    .line 156
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoSwing:I

    .line 157
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->soft:I

    .line 158
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->nomal:I

    .line 159
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->fast:I

    .line 160
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->follower:I

    .line 161
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rest:I

    .line 162
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->temp:I

    .line 163
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->aq:I

    .line 164
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ptc:I

    .line 165
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->uvc:I

    .line 166
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->beep:I

    .line 167
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat:I

    .line 168
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->manual:I

    .line 169
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoDisfog:I

    .line 170
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->comfort:I

    .line 171
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->energyConsume:I

    .line 172
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->chargeLevel:I

    .line 173
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->cool:I

    .line 174
    invoke-virtual {p1}, Landroid/os/Parcel;->readFloat()F

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempValue:F

    .line 175
    invoke-virtual {p1}, Landroid/os/Parcel;->readFloat()F

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempValue:F

    .line 176
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempLevel:I

    .line 177
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempLevel:I

    .line 178
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->aos:I

    .line 179
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->airBag:I

    .line 180
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rearDisFog:I

    .line 181
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoDisFog:I

    .line 182
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoUpperVent:I

    .line 183
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairHeat:I

    .line 184
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairHeat:I

    .line 185
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windIce:I

    .line 186
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->refrigeration:I

    .line 187
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->frontSeatBlower:I

    .line 188
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->manualStatus:I

    .line 189
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->gCleanStatus:I

    .line 190
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightAuto:I

    .line 191
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mono:I

    .line 192
    return-void
.end method

.method public static getSystemTemp(Ljava/lang/String;)Ljava/lang/String;
    .locals 6
    .param p0, "strTemp"    # Ljava/lang/String;

    .line 1258
    sget v0, Lcom/hcn/autocan/hiworld/gm/setting/MyContants;->SYSTEM_TEMP_F:I

    .line 1260
    .local v0, "air_show_flag":I
    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    .line 1261
    if-eqz p0, :cond_0

    :try_start_0
    invoke-virtual {p0}, Ljava/lang/String;->length()I

    move-result v1

    if-eqz v1, :cond_0

    .line 1262
    const-string/jumbo v1, "\u2103"

    invoke-virtual {p0, v1}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 1263
    const-string/jumbo v1, "\u2103"

    invoke-virtual {p0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v1

    .line 1264
    .local v1, "unitIndex":I
    const/4 v2, 0x0

    invoke-virtual {p0, v2, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v2

    .line 1265
    .local v2, "strValue":Ljava/lang/String;
    invoke-static {v2}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result v3

    const v4, 0x3fe66666    # 1.8f

    mul-float/2addr v3, v4

    const/high16 v4, 0x42000000    # 32.0f

    add-float/2addr v3, v4

    float-to-int v3, v3

    .line 1266
    .local v3, "tempValue":I
    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string/jumbo v5, "\u2109"

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object v4

    .line 1272
    .end local v1    # "unitIndex":I
    .end local v2    # "strValue":Ljava/lang/String;
    .end local v3    # "tempValue":I
    :catch_0
    move-exception v1

    .line 1273
    .local v1, "e":Ljava/lang/Exception;
    const-string v2, "Util Error"

    invoke-virtual {v1}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 1273
    .end local v1    # "e":Ljava/lang/Exception;
    goto :goto_0

    .line 1274
    :cond_0
    nop

    .line 1275
    :goto_0
    return-object p0

    .line 1270
    :cond_1
    return-object p0
.end method


# virtual methods
.method public describeContents()I
    .locals 1

    .line 288
    const/4 v0, 0x0

    return v0
.end method

.method public getACFlag()I
    .locals 1

    .line 428
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->acFlag:I

    return v0
.end method

.method public getACMax()I
    .locals 1

    .line 657
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ACMax:I

    return v0
.end method

.method public getAirQuality()I
    .locals 1

    .line 1002
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->aq:I

    return v0
.end method

.method public getAutoDisFog()I
    .locals 1

    .line 596
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoDisFog:I

    return v0
.end method

.method public getAutoLevel()I
    .locals 1

    .line 712
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoLevel:I

    return v0
.end method

.method public getAutoMode()I
    .locals 1

    .line 526
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoMode:I

    return v0
.end method

.method public getAutoSwing()I
    .locals 1

    .line 922
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoSwing:I

    return v0
.end method

.method public getBigAutoFlag()I
    .locals 1

    .line 420
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->bigAutoFlag:I

    return v0
.end method

.method public getCycleMode()I
    .locals 1

    .line 446
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->cycleMode:I

    return v0
.end method

.method public getDual()I
    .locals 1

    .line 490
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->dual:I

    return v0
.end method

.method public getEcon()I
    .locals 1

    .line 736
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->econ:I

    return v0
.end method

.method public getFast()I
    .locals 1

    .line 985
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->fast:I

    return v0
.end method

.method public getFilter()I
    .locals 1

    .line 898
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->filter:I

    return v0
.end method

.method public getFollower()I
    .locals 1

    .line 756
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->follower:I

    return v0
.end method

.method public getFrontDisFog()I
    .locals 1

    .line 514
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mFrontDisFog:I

    return v0
.end method

.method public getHeat()I
    .locals 1

    .line 1083
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat:I

    return v0
.end method

.method public getHeatSteer()I
    .locals 1

    .line 850
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat_steer:I

    return v0
.end method

.method public getIon()I
    .locals 1

    .line 790
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ion:I

    return v0
.end method

.method public getLeftChairHeat()I
    .locals 1

    .line 631
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairHeat:I

    return v0
.end method

.method public getLeftChairWind()I
    .locals 1

    .line 688
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairWind:I

    return v0
.end method

.method public getLeftMassage()I
    .locals 1

    .line 1143
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftMassge:I

    return v0
.end method

.method public getLeftTempNum()Ljava/lang/String;
    .locals 1

    .line 450
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempNum:Ljava/lang/String;

    invoke-static {v0}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSystemTemp(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public getLeftWaist()I
    .locals 1

    .line 1123
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftWaist:I

    return v0
.end method

.method public getLockRear()I
    .locals 1

    .line 862
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLock:I

    return v0
.end method

.method public getManual()I
    .locals 1

    .line 1113
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->manual:I

    return v0
.end method

.method public getMaxFront()I
    .locals 1

    .line 676
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->maxFront:I

    return v0
.end method

.method public getNanoe()I
    .locals 1

    .line 910
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mNanoe:I

    return v0
.end method

.method public getNomal()I
    .locals 1

    .line 979
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->nomal:I

    return v0
.end method

.method public getPTC()I
    .locals 1

    .line 1029
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ptc:I

    return v0
.end method

.method public getPowerFlag()I
    .locals 1

    .line 408
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->powerFlag:I

    return v0
.end method

.method public getRear()I
    .locals 1

    .line 544
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear:I

    return v0
.end method

.method public getRearDisFog()I
    .locals 1

    .line 582
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rearDisFog:I

    return v0
.end method

.method public getRest()I
    .locals 1

    .line 746
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rest:I

    return v0
.end method

.method public getRightChairHeat()I
    .locals 1

    .line 645
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairHeat:I

    return v0
.end method

.method public getRightChairWind()I
    .locals 1

    .line 700
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairWind:I

    return v0
.end method

.method public getRightMassage()I
    .locals 1

    .line 1153
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightMassage:I

    return v0
.end method

.method public getRightTempNum()Ljava/lang/String;
    .locals 1

    .line 454
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempNum:Ljava/lang/String;

    invoke-static {v0}, Lcom/hcn/autocan/hiworld/gm/air/AIR;->getSystemTemp(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public getRightWaist()I
    .locals 1

    .line 1133
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightWaist:I

    return v0
.end method

.method public getSmallAutoFlag()I
    .locals 1

    .line 424
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->smallAutoFlag:I

    return v0
.end method

.method public getSoft()I
    .locals 1

    .line 973
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->soft:I

    return v0
.end method

.method public getSteerChairHeat()I
    .locals 1

    .line 617
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->steerChairHeat:I

    return v0
.end method

.method public getSyncFlag()I
    .locals 1

    .line 568
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->sync:I

    return v0
.end method

.method public getTemp()I
    .locals 1

    .line 990
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->temp:I

    return v0
.end method

.method public getWindIntensity()I
    .locals 1

    .line 724
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windIntensity:I

    return v0
.end method

.method public getWindLevMax()I
    .locals 1

    .line 478
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMax:I

    return v0
.end method

.method public getWindMode()I
    .locals 1

    .line 416
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode:I

    return v0
.end method

.method public getWindModeRight()I
    .locals 1

    .line 796
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode_right:I

    return v0
.end method

.method public getWind_level()I
    .locals 1

    .line 412
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->wind_level:I

    return v0
.end method

.method public getZone()I
    .locals 1

    .line 934
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->zone:I

    return v0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 1
    .param p1, "dest"    # Landroid/os/Parcel;
    .param p2, "flags"    # I

    .line 196
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->TAG:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 197
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->powerFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 198
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->wind_level:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 199
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 200
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMode_right:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 201
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->bigAutoFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 202
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->smallAutoFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 203
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->acFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 204
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->lowFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 205
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->glassFrontFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 206
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mirrorBackFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 207
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->hiFlag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 208
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->cycleMode:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 209
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windMax:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 210
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempNum:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 211
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempNum:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 212
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->unit:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 213
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->str_unit:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 214
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->dual:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 215
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->flow:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 216
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mFrontDisFog:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 217
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoMode:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 218
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 219
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mDisplay:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 220
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->sync:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 221
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ACMax:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 222
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->invisibleID:[I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeIntArray([I)V

    .line 223
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->visibleID:[I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeIntArray([I)V

    .line 224
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->invisibleID_REAR:[I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeIntArray([I)V

    .line 225
    iget-object v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->visibleID_REAR:[I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeIntArray([I)V

    .line 226
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->maxFront:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 227
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->steerChairHeat:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 228
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairWind:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 229
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairWind:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 230
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftWaist:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 231
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightWaist:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 232
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLeftMassge:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 233
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mRightMassage:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 234
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoLevel:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 235
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windIntensity:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 236
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->econ:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 237
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ion:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 238
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->innerCycle:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 239
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->outerCycle:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 240
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->zone:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 241
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->winFrontHeatSp:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 242
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat_steer:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 243
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mLock:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 244
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rear_lock:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 245
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->info:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 246
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->filter:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 247
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mNanoe:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 248
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mAutoSwing:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 249
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->soft:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 250
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->nomal:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 251
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->fast:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 252
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->follower:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 253
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rest:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 254
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->temp:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 255
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->aq:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 256
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->ptc:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 257
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->uvc:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 258
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->beep:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 259
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->heat:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 260
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->manual:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 261
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoDisfog:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 262
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->comfort:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 263
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->energyConsume:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 264
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->chargeLevel:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 265
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->cool:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 266
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempValue:F

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeFloat(F)V

    .line 267
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempValue:F

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeFloat(F)V

    .line 268
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftTempLevel:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 269
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightTempLevel:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 270
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->aos:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 271
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->airBag:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 272
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rearDisFog:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 273
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoDisFog:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 274
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->autoUpperVent:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 275
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->leftChairHeat:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 276
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightChairHeat:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 277
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->windIce:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 278
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->refrigeration:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 279
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->frontSeatBlower:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 280
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->manualStatus:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 281
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->gCleanStatus:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 282
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->rightAuto:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 283
    iget v0, p0, Lcom/hcn/autocan/hiworld/gm/air/AIR;->mono:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 284
    return-void
.end method
