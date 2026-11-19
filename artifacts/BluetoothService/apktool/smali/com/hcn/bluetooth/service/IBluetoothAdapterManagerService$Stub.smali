.class public abstract Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;
.super Landroid/os/Binder;
.source "IBluetoothAdapterManagerService.java"

# interfaces
.implements Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub$Proxy;
    }
.end annotation


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "com.hcn.bluetooth.service.IBluetoothAdapterManagerService"

.field static final TRANSACTION_connectA2dp:I = 0x10

.field static final TRANSACTION_connectDevice:I = 0xb

.field static final TRANSACTION_connectPbap:I = 0x1d

.field static final TRANSACTION_disConnectPbap:I = 0x1e

.field static final TRANSACTION_disconnectA2dp:I = 0x11

.field static final TRANSACTION_disconnectDevice:I = 0xc

.field static final TRANSACTION_getBTName:I = 0x4

.field static final TRANSACTION_getBTPincode:I = 0x6

.field static final TRANSACTION_getBondedDevices:I = 0x9

.field static final TRANSACTION_getConnectDevice:I = 0xa

.field static final TRANSACTION_getDeviceList:I = 0x8

.field static final TRANSACTION_getPbapDownLoadState:I = 0x20

.field static final TRANSACTION_getThirdPartAPPMode:I = 0x25

.field static final TRANSACTION_isA2dpConnected:I = 0x12

.field static final TRANSACTION_isAutoConnectedNetwork:I = 0x18

.field static final TRANSACTION_isBluetoothAutoAnswer:I = 0x14

.field static final TRANSACTION_isBluetoothAutoConnect:I = 0x16

.field static final TRANSACTION_isBluetoothConnected:I = 0xd

.field static final TRANSACTION_isBluetoothEnable:I = 0x2

.field static final TRANSACTION_isDiscovering:I = 0x1b

.field static final TRANSACTION_isPbapConnected:I = 0x1c

.field static final TRANSACTION_pairDevice:I = 0xe

.field static final TRANSACTION_pbapStartDownLoad:I = 0x1f

.field static final TRANSACTION_registerCallback:I = 0x23

.field static final TRANSACTION_registerPbapCallback:I = 0x21

.field static final TRANSACTION_resetBT:I = 0x3

.field static final TRANSACTION_setAutoConnectedNetwork:I = 0x17

.field static final TRANSACTION_setBTName:I = 0x5

.field static final TRANSACTION_setBTPincode:I = 0x7

.field static final TRANSACTION_setBluetoothAutoAnswer:I = 0x13

.field static final TRANSACTION_setBluetoothAutoConnect:I = 0x15

.field static final TRANSACTION_setBluetoothEnable:I = 0x1

.field static final TRANSACTION_startDiscovery:I = 0x19

.field static final TRANSACTION_stopDiscovery:I = 0x1a

.field static final TRANSACTION_unpairDevice:I = 0xf

.field static final TRANSACTION_unregisterCallback:I = 0x24

.field static final TRANSACTION_unregisterPbapCallback:I = 0x22


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 14
    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    const-string v0, "com.hcn.bluetooth.service.IBluetoothAdapterManagerService"

    .line 15
    invoke-virtual {p0, p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    return-void
.end method

.method public static asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;
    .locals 2

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-string v0, "com.hcn.bluetooth.service.IBluetoothAdapterManagerService"

    .line 26
    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 27
    instance-of v1, v0, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    if-eqz v1, :cond_1

    .line 28
    check-cast v0, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    return-object v0

    .line 30
    :cond_1
    new-instance v0, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub$Proxy;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

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

    const-string v2, "com.hcn.bluetooth.service.IBluetoothAdapterManagerService"

    if-eq p1, v0, :cond_5

    const/4 v0, 0x0

    packed-switch p1, :pswitch_data_0

    .line 367
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result p1

    return p1

    .line 359
    :pswitch_0
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 360
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getThirdPartAPPMode()I

    move-result p1

    .line 361
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 362
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 350
    :pswitch_1
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 352
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IAdapterCallback;

    move-result-object p1

    .line 353
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->unregisterCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 354
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 341
    :pswitch_2
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 343
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IAdapterCallback;

    move-result-object p1

    .line 344
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V

    .line 345
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 332
    :pswitch_3
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 334
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IPbapCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IPbapCallback;

    move-result-object p1

    .line 335
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->unregisterPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 336
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 323
    :pswitch_4
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 325
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object p1

    invoke-static {p1}, Lcom/hcn/bluetooth/service/IPbapCallback$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IPbapCallback;

    move-result-object p1

    .line 326
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V

    .line 327
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 313
    :pswitch_5
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 315
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 316
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getPbapDownLoadState(I)Z

    move-result p1

    .line 317
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 318
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 303
    :pswitch_6
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 305
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 306
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->pbapStartDownLoad(I)Z

    move-result p1

    .line 307
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 308
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 296
    :pswitch_7
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 297
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->disConnectPbap()V

    .line 298
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 289
    :pswitch_8
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 290
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->connectPbap()V

    .line 291
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 281
    :pswitch_9
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 282
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isPbapConnected()Z

    move-result p1

    .line 283
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 284
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 273
    :pswitch_a
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 274
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isDiscovering()Z

    move-result p1

    .line 275
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 276
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 266
    :pswitch_b
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 267
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->stopDiscovery()V

    .line 268
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 259
    :pswitch_c
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 260
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->startDiscovery()V

    .line 261
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 251
    :pswitch_d
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 252
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isAutoConnectedNetwork()Z

    move-result p1

    .line 253
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 254
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 242
    :pswitch_e
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 244
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_0

    move v0, v1

    .line 245
    :cond_0
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setAutoConnectedNetwork(Z)V

    .line 246
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 234
    :pswitch_f
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 235
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isBluetoothAutoConnect()Z

    move-result p1

    .line 236
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 237
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 225
    :pswitch_10
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 227
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_1

    move v0, v1

    .line 228
    :cond_1
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setBluetoothAutoConnect(Z)V

    .line 229
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 217
    :pswitch_11
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 218
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isBluetoothAutoAnswer()Z

    move-result p1

    .line 219
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 220
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 208
    :pswitch_12
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 210
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_2

    move v0, v1

    .line 211
    :cond_2
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setBluetoothAutoAnswer(Z)V

    .line 212
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 198
    :pswitch_13
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 200
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 201
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isA2dpConnected(Ljava/lang/String;)Z

    move-result p1

    .line 202
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 203
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 189
    :pswitch_14
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 191
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 192
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->disconnectA2dp(Ljava/lang/String;)V

    .line 193
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 180
    :pswitch_15
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 182
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 183
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->connectA2dp(Ljava/lang/String;)V

    .line 184
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 171
    :pswitch_16
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 173
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 174
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->unpairDevice(Ljava/lang/String;)V

    .line 175
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 162
    :pswitch_17
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 164
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 165
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->pairDevice(Ljava/lang/String;)V

    .line 166
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 154
    :pswitch_18
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 155
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isBluetoothConnected()Z

    move-result p1

    .line 156
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 157
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 145
    :pswitch_19
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 147
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 148
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->disconnectDevice(Ljava/lang/String;)V

    .line 149
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 136
    :pswitch_1a
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 138
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 139
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->connectDevice(Ljava/lang/String;)V

    .line 140
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 122
    :pswitch_1b
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 123
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object p1

    .line 124
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    if-eqz p1, :cond_3

    .line 126
    invoke-virtual {p3, v1}, Landroid/os/Parcel;->writeInt(I)V

    .line 127
    invoke-virtual {p1, p3, v1}, Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;->writeToParcel(Landroid/os/Parcel;I)V

    goto :goto_0

    .line 130
    :cond_3
    invoke-virtual {p3, v0}, Landroid/os/Parcel;->writeInt(I)V

    :goto_0
    return v1

    .line 114
    :pswitch_1c
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 115
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getBondedDevices()Ljava/util/List;

    move-result-object p1

    .line 116
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 117
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeTypedList(Ljava/util/List;)V

    return v1

    .line 106
    :pswitch_1d
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 107
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getDeviceList()Ljava/util/List;

    move-result-object p1

    .line 108
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 109
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeTypedList(Ljava/util/List;)V

    return v1

    .line 97
    :pswitch_1e
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 99
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 100
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setBTPincode(Ljava/lang/String;)V

    .line 101
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 89
    :pswitch_1f
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 90
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getBTPincode()Ljava/lang/String;

    move-result-object p1

    .line 91
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 92
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v1

    .line 80
    :pswitch_20
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 82
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 83
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setBTName(Ljava/lang/String;)V

    .line 84
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 72
    :pswitch_21
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 73
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->getBTName()Ljava/lang/String;

    move-result-object p1

    .line 74
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 75
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v1

    .line 65
    :pswitch_22
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 66
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->resetBT()V

    .line 67
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 57
    :pswitch_23
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 58
    invoke-virtual {p0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->isBluetoothEnable()Z

    move-result p1

    .line 59
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 60
    invoke-virtual {p3, p1}, Landroid/os/Parcel;->writeInt(I)V

    return v1

    .line 48
    :pswitch_24
    invoke-virtual {p2, v2}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 50
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_4

    move v0, v1

    .line 51
    :cond_4
    invoke-virtual {p0, v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService$Stub;->setBluetoothEnable(Z)V

    .line 52
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v1

    .line 43
    :cond_5
    invoke-virtual {p3, v2}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v1

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_24
        :pswitch_23
        :pswitch_22
        :pswitch_21
        :pswitch_20
        :pswitch_1f
        :pswitch_1e
        :pswitch_1d
        :pswitch_1c
        :pswitch_1b
        :pswitch_1a
        :pswitch_19
        :pswitch_18
        :pswitch_17
        :pswitch_16
        :pswitch_15
        :pswitch_14
        :pswitch_13
        :pswitch_12
        :pswitch_11
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
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
