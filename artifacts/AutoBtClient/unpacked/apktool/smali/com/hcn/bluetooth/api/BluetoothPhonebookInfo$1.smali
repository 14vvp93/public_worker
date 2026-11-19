.class Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;
.super Ljava/lang/Object;
.source "BluetoothPhonebookInfo.java"

# interfaces
.implements Landroid/os/Parcelable$Creator;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Landroid/os/Parcelable$Creator<",
        "Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;",
        ">;"
    }
.end annotation


# direct methods
.method constructor <init>()V
    .locals 0

    .line 108
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public createFromParcel(Landroid/os/Parcel;)Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    .locals 2

    .line 112
    new-instance v0, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    const/4 v1, 0x0

    invoke-direct {v0, p1, v1}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;-><init>(Landroid/os/Parcel;Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;)V

    return-object v0
.end method

.method public bridge synthetic createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;
    .locals 0

    .line 108
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;->createFromParcel(Landroid/os/Parcel;)Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    move-result-object p1

    return-object p1
.end method

.method public newArray(I)[Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;
    .locals 0

    .line 117
    new-array p1, p1, [Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    return-object p1
.end method

.method public bridge synthetic newArray(I)[Ljava/lang/Object;
    .locals 0

    .line 108
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo$1;->newArray(I)[Lcom/hcn/bluetooth/api/BluetoothPhonebookInfo;

    move-result-object p1

    return-object p1
.end method
