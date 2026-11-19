.class public final Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;
.super Ljava/lang/Object;
.source "Alarm.java"

# interfaces
.implements Landroid/os/Parcelable;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;
    }
.end annotation


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field alert:Landroid/net/Uri;

.field daysOfWeek:Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

.field enabled:Z

.field hour:I

.field id:I

.field label:Ljava/lang/String;

.field minutes:I

.field silent:Z

.field time:J

.field vibrate:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 41
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$1;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$1;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 231
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 232
    const/4 v0, -0x1

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    .line 233
    const/4 v0, 0x0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->hour:I

    .line 234
    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->minutes:I

    .line 235
    const/4 v1, 0x1

    iput-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->vibrate:Z

    .line 236
    new-instance v1, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    invoke-direct {v1, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;-><init>(I)V

    iput-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->daysOfWeek:Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    .line 237
    const-string v0, ""

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->label:Ljava/lang/String;

    .line 238
    const/4 v0, 0x4

    invoke-static {v0}, Landroid/media/RingtoneManager;->getDefaultUri(I)Landroid/net/Uri;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->alert:Landroid/net/Uri;

    .line 239
    return-void
.end method

.method public constructor <init>(Landroid/os/Parcel;)V
    .locals 5
    .param p1, "p"    # Landroid/os/Parcel;

    .line 217
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 218
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    .line 219
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-ne v0, v2, :cond_0

    move v0, v2

    goto :goto_0

    :cond_0
    move v0, v1

    :goto_0
    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->enabled:Z

    .line 220
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->hour:I

    .line 221
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->minutes:I

    .line 222
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v3

    invoke-direct {v0, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;-><init>(I)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->daysOfWeek:Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    .line 223
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v3

    iput-wide v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->time:J

    .line 224
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    if-ne v0, v2, :cond_1

    move v0, v2

    goto :goto_1

    :cond_1
    move v0, v1

    :goto_1
    iput-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->vibrate:Z

    .line 225
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->label:Ljava/lang/String;

    .line 226
    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->readParcelable(Ljava/lang/ClassLoader;)Landroid/os/Parcelable;

    move-result-object v0

    check-cast v0, Landroid/net/Uri;

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->alert:Landroid/net/Uri;

    .line 227
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    if-ne v0, v2, :cond_2

    move v1, v2

    nop

    :cond_2
    iput-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->silent:Z

    .line 228
    return-void
.end method


# virtual methods
.method public describeContents()I
    .locals 1

    .line 53
    const/4 v0, 0x0

    return v0
.end method

.method public equals(Ljava/lang/Object;)Z
    .locals 4
    .param p1, "o"    # Ljava/lang/Object;

    .line 255
    instance-of v0, p1, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    .line 256
    :cond_0
    move-object v0, p1

    check-cast v0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;

    .line 257
    .local v0, "other":Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;
    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    iget v3, v0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    if-ne v2, v3, :cond_1

    const/4 v1, 0x1

    nop

    :cond_1
    return v1
.end method

.method public hashCode()I
    .locals 1

    .line 250
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    return v0
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 176
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "Alarm{alert="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->alert:Landroid/net/Uri;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", id="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", enabled="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->enabled:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", hour="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->hour:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", minutes="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->minutes:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v1, ", daysOfWeek="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->daysOfWeek:Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v1, ", time="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-wide v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->time:J

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    const-string v1, ", vibrate="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->vibrate:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v1, ", label=\'"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->label:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const/16 v1, 0x27

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    const-string v1, ", silent="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->silent:Z

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const/16 v1, 0x7d

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 2
    .param p1, "p"    # Landroid/os/Parcel;
    .param p2, "flags"    # I

    .line 57
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->id:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 58
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->enabled:Z

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 59
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->hour:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 60
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->minutes:I

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 61
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->daysOfWeek:Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm$DaysOfWeek;->getCoded()I

    move-result v0

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 62
    iget-wide v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->time:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    .line 63
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->vibrate:Z

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 64
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->label:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 65
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->alert:Landroid/net/Uri;

    invoke-virtual {p1, v0, p2}, Landroid/os/Parcel;->writeParcelable(Landroid/os/Parcelable;I)V

    .line 66
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/Alarm;->silent:Z

    invoke-virtual {p1, v0}, Landroid/os/Parcel;->writeInt(I)V

    .line 67
    return-void
.end method
