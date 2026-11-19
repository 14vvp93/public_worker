.class public Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;
.super Ljava/lang/Object;
.source "BluetoothDeviceInfo.java"

# interfaces
.implements Landroid/os/Parcelable;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/api/BluetoothDeviceInfo$DeviceStatus;
    }
.end annotation


# static fields
.field public static final CREATOR:Landroid/os/Parcelable$Creator;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/os/Parcelable$Creator<",
            "Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field public mDeviceAddr:Ljava/lang/String;

.field public mDeviceIndex:I

.field public mDeviceName:Ljava/lang/String;

.field public mDeviceStatus:I


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 103
    new-instance v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo$1;

    invoke-direct {v0}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo$1;-><init>()V

    sput-object v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 24
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 11
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 12
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    const/4 v0, -0x1

    .line 25
    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    .line 26
    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    const-string v0, ""

    .line 27
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 28
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>(Landroid/bluetooth/BluetoothDevice;)V
    .locals 1

    .line 31
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 11
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 12
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    if-nez p1, :cond_0

    const/4 p1, -0x1

    .line 33
    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    .line 34
    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    const-string p1, ""

    .line 35
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 36
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    return-void

    .line 39
    :cond_0
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getAliasName()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_1

    .line 40
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getAliasName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    goto :goto_0

    .line 41
    :cond_1
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getName()Ljava/lang/String;

    move-result-object v0

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 42
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getName()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    goto :goto_0

    .line 44
    :cond_2
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getAddress()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 46
    :goto_0
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getAddress()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    .line 47
    invoke-virtual {p1}, Landroid/bluetooth/BluetoothDevice;->getBondState()I

    move-result p1

    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    return-void
.end method

.method private constructor <init>(Landroid/os/Parcel;)V
    .locals 1

    .line 50
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 11
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 12
    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    .line 51
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    .line 52
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    .line 53
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    .line 54
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    return-void
.end method

.method synthetic constructor <init>(Landroid/os/Parcel;Lcom/hcn/bluetooth/api/BluetoothDeviceInfo$1;)V
    .locals 0

    .line 8
    invoke-direct {p0, p1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;-><init>(Landroid/os/Parcel;)V

    return-void
.end method


# virtual methods
.method public describeContents()I
    .locals 1

    const/4 v0, 0x0

    return v0
.end method

.method public getDeviceAddr()Ljava/lang/String;
    .locals 1

    .line 82
    iget-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    return-object v0
.end method

.method public getDeviceIndex()I
    .locals 1

    .line 58
    iget v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    return v0
.end method

.method public getDeviceName()Ljava/lang/String;
    .locals 1

    .line 74
    iget-object v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    return-object v0
.end method

.method public getDeviceStatus()I
    .locals 1

    .line 66
    iget v0, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    return v0
.end method

.method public setDeviceAddr(Ljava/lang/String;)V
    .locals 0

    .line 86
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    return-void
.end method

.method public setDeviceIndex(I)V
    .locals 0

    .line 62
    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    return-void
.end method

.method public setDeviceName(Ljava/lang/String;)V
    .locals 0

    .line 78
    iput-object p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    return-void
.end method

.method public setDeviceStatus(I)V
    .locals 0

    .line 70
    iput p1, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    return-void
.end method

.method public writeToParcel(Landroid/os/Parcel;I)V
    .locals 0

    .line 97
    iget p2, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceIndex:I

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 98
    iget p2, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceStatus:I

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 99
    iget-object p2, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceName:Ljava/lang/String;

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 100
    iget-object p2, p0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->mDeviceAddr:Ljava/lang/String;

    invoke-virtual {p1, p2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return-void
.end method
