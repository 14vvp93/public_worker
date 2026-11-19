.class public abstract Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;
.super Landroid/os/Binder;
.source "IBluetoothMusicService.java"

# interfaces
.implements Lcom/hcn/bluetooth/service/IBluetoothMusicService;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/service/IBluetoothMusicService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub$Proxy;
    }
.end annotation


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "com.hcn.bluetooth.service.IBluetoothMusicService"

.field static final TRANSACTION_getConnectDevice:I = 0x4

.field static final TRANSACTION_getID3Info:I = 0xc

.field static final TRANSACTION_isA2dpConnected:I = 0x1

.field static final TRANSACTION_isA2dpPlaying:I = 0x3

.field static final TRANSACTION_isAvrcpConnected:I = 0x2

.field static final TRANSACTION_regMusicClientBinder:I = 0x8

.field static final TRANSACTION_registerBTMusicCallback:I = 0xa

.field static final TRANSACTION_releaseA2dp:I = 0x7

.field static final TRANSACTION_requestA2dp:I = 0x6

.field static final TRANSACTION_send_Avrcp_Cmd:I = 0x5

.field static final TRANSACTION_unRegMusicClientBinder:I = 0x9

.field static final TRANSACTION_unregisterBTMusicCallback:I = 0xb


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 14
    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    const-string v0, "com.hcn.bluetooth.service.IBluetoothMusicService"

    .line 15
    invoke-virtual {p0, p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    return-void
.end method

.method public static asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IBluetoothMusicService;
    .locals 2

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-string v0, "com.hcn.bluetooth.service.IBluetoothMusicService"

    .line 26
    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 27
    instance-of v1, v0, Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v1, :cond_1

    .line 28
    check-cast v0, Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    return-object v0

    .line 30
    :cond_1
    new-instance v0, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub$Proxy;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

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

    const-string v2, "com.hcn.bluetooth.service.IBluetoothMusicService"

    if-eq p1, v0, :cond_1

    packed-switch p1, :pswitch_data_0

    .line 151
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result p1

    return p1

    .line 143
    :pswitch_0
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 144
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->getID3Info()[Ljava/lang/String;

    move-result-object p1

    .line 145
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 146
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeStringArray([Ljava/lang/String;)V

    return v1

    .line 134
    :pswitch_1
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 136
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IMusicCallback;

    move-result-object p1

    .line 137
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->unregisterBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V

    .line 138
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 125
    :pswitch_2
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 127
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IMusicCallback;

    move-result-object p1

    .line 128
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->registerBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V

    .line 129
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 118
    :pswitch_3
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 119
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->unRegMusicClientBinder()V

    .line 120
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 109
    :pswitch_4
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 111
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    .line 112
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->regMusicClientBinder(Landroid/os/IBinder;)V

    .line 113
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 102
    :pswitch_5
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 103
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->releaseA2dp()V

    .line 104
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 95
    :pswitch_6
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 96
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->requestA2dp()V

    .line 97
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 86
    :pswitch_7
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 88
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 89
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->send_Avrcp_Cmd(I)V

    .line 90
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 72
    :pswitch_8
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 73
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    .line 74
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    if-eqz p1, :cond_0

    .line 76
    invoke-virtual {p3, v1}, Landroid/os/Parcel;->writeInt(I)V

    .line 77
    invoke-virtual {p1, p3, v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->writeToParcel(Landroid/os/Parcel;I)V

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    .line 80
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    :goto_0
    return v1

    .line 64
    :pswitch_9
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 65
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->isA2dpPlaying()Z

    move-result p1

    .line 66
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 67
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 56
    :pswitch_a
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 57
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->isAvrcpConnected()Z

    move-result p1

    .line 58
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 59
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 48
    :pswitch_b
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 49
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService$Stub;->isA2dpConnected()Z

    move-result p1

    .line 50
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 51
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 43
    :cond_1
    invoke-virtual {p3, v2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v1

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
