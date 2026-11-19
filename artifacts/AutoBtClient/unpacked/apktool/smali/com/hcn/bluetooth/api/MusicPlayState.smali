.class public final Lcom/hcn/bluetooth/api/MusicPlayState;
.super Ljava/lang/Object;
.source "MusicPlayState.java"

# interfaces
.implements Landroid/os/Parcelable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/hcn/bluetooth/api/MusicPlayState;",
            ">;"
        }
    .end annotation
.end field

.field public static final STATE_BUFFERING:I = 0x6

.field public static final STATE_CONNECTING:I = 0x8

.field public static final STATE_ERROR:I = 0x7

.field public static final STATE_FAST_FORWARDING:I = 0x4

.field public static final STATE_NONE:I = 0x0

.field public static final STATE_PAUSED:I = 0x2

.field public static final STATE_PLAYING:I = 0x3

.field public static final STATE_REWINDING:I = 0x5

.field public static final STATE_SKIPPING_TO_NEXT:I = 0xa

.field public static final STATE_SKIPPING_TO_PREVIOUS:I = 0x9

.field public static final STATE_SKIPPING_TO_QUEUE_ITEM:I = 0xb

.field public static final STATE_STOPPED:I = 0x1

.field private static final TAG:Ljava/lang/String; = "MusicPlayState"


# instance fields
.field private mBufferedPosition:J

.field private mDuration:J

.field private mPosition:J

.field private mSpeed:F

.field private mState:I

.field private mUpdateTime:J


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 198
    new-instance v0, Lcom/hcn/bluetooth/api/MusicPlayState$1;

    invoke-direct {v0}, Lcom/hcn/bluetooth/api/MusicPlayState$1;-><init>()V

    sput-object v0, Lcom/hcn/bluetooth/api/MusicPlayState;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 4

    .line 50
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x1

    .line 51
    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    const-wide/16 v0, 0x0

    .line 52
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    .line 53
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    const/high16 v2, 0x3f800000    # 1.0f

    .line 54
    iput v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    .line 55
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    iput-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    .line 56
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-void
.end method

.method private constructor <init>(Landroid/os/Parcel;)V
    .locals 2

    .line 59
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 60
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    .line 61
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    .line 62
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    .line 63
    invoke-virtual {p1}, Landroid/os/Parcel;->readFloat()F

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    .line 64
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    .line 65
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-void
.end method

.method synthetic constructor <init>(Landroid/os/Parcel;Lcom/hcn/bluetooth/api/MusicPlayState$1;)V
    .locals 0

    .line 26
    invoke-direct {p0, p1}, Lcom/hcn/bluetooth/api/MusicPlayState;-><init>(Landroid/os/Parcel;)V

    return-void
.end method


# virtual methods
.method public describeContents()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public getBufferedPosition()J
    .locals 2

    .line 154
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-wide v0
.end method

.method public getDuration()J
    .locals 2

    .line 142
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    return-wide v0
.end method

.method public getLastPositionUpdateTime()J
    .locals 2

    .line 191
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    return-wide v0
.end method

.method public getPlaybackSpeed()F
    .locals 1

    .line 168
    iget v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    return v0
.end method

.method public getPosition()J
    .locals 2

    .line 134
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    return-wide v0
.end method

.method public getState()I
    .locals 1

    .line 123
    iget v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    return v0
.end method

.method public readFromParcel(Landroid/os/Parcel;)V
    .locals 2

    .line 97
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    .line 98
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    .line 99
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    .line 100
    invoke-virtual {p1}, Landroid/os/Parcel;->readFloat()F

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    .line 101
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    .line 102
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-void
.end method

.method public reset()V
    .locals 4

    const/4 v0, 0x1

    .line 176
    iput v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    const-wide/16 v0, 0x0

    .line 177
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    .line 178
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    const/high16 v2, 0x3f800000    # 1.0f

    .line 179
    iput v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    .line 180
    invoke-static {}, Landroid/os/SystemClock;->elapsedRealtime()J

    move-result-wide v2

    iput-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    .line 181
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-void
.end method

.method public setBufferedPosition(J)V
    .locals 0

    .line 158
    iput-wide p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    return-void
.end method

.method public setDuration(J)V
    .locals 0

    .line 146
    iput-wide p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    return-void
.end method

.method public setLastPositionUpdateTime(J)V
    .locals 0

    .line 195
    iput-wide p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    return-void
.end method

.method public setPlaybackSpeed(F)V
    .locals 0

    .line 172
    iput p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    return-void
.end method

.method public setPosition(J)V
    .locals 0

    .line 138
    iput-wide p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    return-void
.end method

.method public setState(I)V
    .locals 0

    .line 127
    iput p1, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 4

    .line 70
    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "MusicPlayState {"

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    const-string v1, "state="

    .line 71
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", position="

    .line 72
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", duration="

    .line 73
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", buffered position="

    .line 74
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", speed="

    .line 75
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(F)Ljava/lang/StringBuilder;

    const-string v1, ", updated="

    .line 76
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-wide v2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    invoke-virtual {v1, v2, v3}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, "}"

    .line 77
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 78
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 2

    .line 88
    iget p2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mState:I

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 89
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mPosition:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    .line 90
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mDuration:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    .line 91
    iget p2, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mSpeed:F

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeFloat(F)V

    .line 92
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mUpdateTime:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    .line 93
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/MusicPlayState;->mBufferedPosition:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    return-void
.end method
