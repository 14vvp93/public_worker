.class public abstract Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;
.super Landroid/os/Binder;
.source "IAdapterCallback.java"

# interfaces
.implements Lcom/hcn/bluetooth/service/IAdapterCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/service/IAdapterCallback;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IAdapterCallback$Stub$Proxy;
    }
.end annotation


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "com.hcn.bluetooth.service.IAdapterCallback"

.field static final TRANSACTION_onBluetoothStateChanged:I = 0x1

.field static final TRANSACTION_onConnectionStateChanged:I = 0x6

.field static final TRANSACTION_onDeviceBondStateChanged:I = 0x5

.field static final TRANSACTION_onDiscoveryDeviceFound:I = 0x3

.field static final TRANSACTION_onDiscoveryDeviceNameChanged:I = 0x4

.field static final TRANSACTION_onDiscoveryStateChanged:I = 0x2


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 14
    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    const-string v0, "com.hcn.bluetooth.service.IAdapterCallback"

    .line 15
    invoke-virtual {p0, p0, v0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    return-void
.end method

.method public static asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IAdapterCallback;
    .locals 2

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-string v0, "com.hcn.bluetooth.service.IAdapterCallback"

    .line 26
    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 27
    instance-of v1, v0, Lcom/hcn/bluetooth/service/IAdapterCallback;

    if-eqz v1, :cond_1

    .line 28
    check-cast v0, Lcom/hcn/bluetooth/service/IAdapterCallback;

    return-object v0

    .line 30
    :cond_1
    new-instance v0, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub$Proxy;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

    return-object v0
.end method


# virtual methods
.method public asBinder()Landroid/os/IBinder;
    .locals 0

    return-object p0
.end method

.method public onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z
    .locals 3
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    const v0, 0x5f4e5446

    const/4 v1, 0x1

    const-string v2, "com.hcn.bluetooth.service.IAdapterCallback"

    if-eq p1, v0, :cond_4

    const/4 v0, 0x0

    packed-switch p1, :pswitch_data_0

    .line 120
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result p1

    return p1

    .line 105
    :pswitch_0
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 107
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_0

    .line 108
    sget-object p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-interface {p1, p2}, Landroid/os/Parcelable$Creator;->createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;

    move-result-object p1

    move-object v0, p1

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 114
    :cond_0
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 115
    invoke-virtual {p0, v0, p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onConnectionStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V

    return v1

    .line 90
    :pswitch_1
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 92
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_1

    .line 93
    sget-object p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-interface {p1, p2}, Landroid/os/Parcelable$Creator;->createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;

    move-result-object p1

    move-object v0, p1

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 99
    :cond_1
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 100
    invoke-virtual {p0, v0, p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onDeviceBondStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V

    return v1

    .line 77
    :pswitch_2
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 79
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_2

    .line 80
    sget-object p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-interface {p1, p2}, Landroid/os/Parcelable$Creator;->createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;

    move-result-object p1

    move-object v0, p1

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 85
    :cond_2
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onDiscoveryDeviceNameChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V

    return v1

    .line 64
    :pswitch_3
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 66
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_3

    .line 67
    sget-object p1, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-interface {p1, p2}, Landroid/os/Parcelable$Creator;->createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;

    move-result-object p1

    move-object v0, p1

    check-cast v0, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    .line 72
    :cond_3
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onDiscoveryDeviceFound(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V

    return v1

    .line 56
    :pswitch_4
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 58
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 59
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onDiscoveryStateChanged(I)V

    return v1

    .line 48
    :pswitch_5
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 50
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 51
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->onBluetoothStateChanged(I)V

    return v1

    .line 43
    :cond_4
    invoke-virtual {p3, v2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v1

    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
