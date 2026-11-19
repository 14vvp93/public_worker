.class public Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
.super Ljava/lang/Object;
.source "BluetoothPhonebookInfo.java"

# interfaces
.implements Landroid/os/Parcelable;
.implements Ljava/lang/Cloneable;


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;",
            ">;"
        }
    .end annotation
.end field

.field private static final sProtoInfo:Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;


# instance fields
.field public id:J

.field public mTelephone:Ljava/lang/String;

.field public mType:I

.field public mUserName:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 14
    new-instance v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    invoke-direct {v0}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;-><init>()V

    sput-object v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->sProtoInfo:Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    .line 107
    new-instance v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;

    invoke-direct {v0}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;-><init>()V

    sput-object v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 79
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 10
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    .line 11
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    const/4 v0, 0x0

    .line 80
    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    .line 81
    new-instance v0, Ljava/lang/String;

    invoke-direct {v0}, Ljava/lang/String;-><init>()V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    .line 82
    new-instance v0, Ljava/lang/String;

    invoke-direct {v0}, Ljava/lang/String;-><init>()V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    const-wide/16 v0, 0x0

    .line 83
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    return-void
.end method

.method private constructor <init>(Landroid/os/Parcel;)V
    .locals 2

    .line 86
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 10
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    .line 11
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    .line 87
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    .line 88
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    .line 89
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    .line 90
    invoke-virtual {p1}, Landroid/os/Parcel;->readLong()J

    move-result-wide v0

    iput-wide v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    return-void
.end method

.method synthetic constructor <init>(Landroid/os/Parcel;Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;)V
    .locals 0

    .line 8
    invoke-direct {p0, p1}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;-><init>(Landroid/os/Parcel;)V

    return-void
.end method

.method public static declared-synchronized getNewObject()Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    .locals 2

    const-class v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    monitor-enter v0

    .line 17
    :try_start_0
    sget-object v1, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->sProtoInfo:Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    invoke-virtual {v1}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->clone()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 18
    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method


# virtual methods
.method public clone()Ljava/lang/Object;
    .locals 1

    .line 71
    :try_start_0
    invoke-super {p0}, Ljava/lang/Object;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    :try_end_0
    .catch Ljava/lang/CloneNotSupportedException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 73
    invoke-virtual {v0}, Ljava/lang/CloneNotSupportedException;->printStackTrace()V

    const/4 v0, 0x0

    .line 75
    :goto_0
    invoke-virtual {v0}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->reset()Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    return-object v0
.end method

.method public describeContents()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public getId()J
    .locals 2

    .line 53
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    return-wide v0
.end method

.method public getTelephone()Ljava/lang/String;
    .locals 1

    .line 37
    iget-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    return-object v0
.end method

.method public getType()I
    .locals 1

    .line 29
    iget v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    return v0
.end method

.method public getUserName()Ljava/lang/String;
    .locals 1

    .line 45
    iget-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    return-object v0
.end method

.method public reset()Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    .locals 2

    const/4 v0, -0x1

    .line 61
    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    const/4 v0, 0x0

    .line 62
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    .line 63
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    const-wide/16 v0, 0x0

    .line 64
    iput-wide v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    return-object p0
.end method

.method public setId(J)V
    .locals 0

    .line 57
    iput-wide p1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    return-void
.end method

.method public setTelephone(Ljava/lang/String;)V
    .locals 0

    .line 41
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    return-void
.end method

.method public setType(I)V
    .locals 0

    .line 33
    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    return-void
.end method

.method public setUserName(Ljava/lang/String;)V
    .locals 0

    .line 49
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 3

    .line 24
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "id:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-wide v1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    invoke-virtual {v0, v1, v2}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "type: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "telephone: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    const-string v2, "null"

    if-nez v1, :cond_0

    move-object v1, v2

    .line 25
    :cond_0
    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "name: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    if-nez v1, :cond_1

    goto :goto_0

    :cond_1
    move-object v2, v1

    :goto_0
    invoke-virtual {v0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 2

    .line 101
    iget p2, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mType:I

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 102
    iget-object p2, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mTelephone:Ljava/lang/String;

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 103
    iget-object p2, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->mUserName:Ljava/lang/String;

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 104
    iget-wide v0, p0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;->id:J

    invoke-virtual {p1, v0, v1}, Landroid/os/Parcel;->writeLong(J)V

    return-void
.end method
