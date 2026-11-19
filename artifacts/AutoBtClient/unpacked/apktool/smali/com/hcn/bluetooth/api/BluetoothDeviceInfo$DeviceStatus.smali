.class public final Lcom/hcn/bluetooth/api/BluetoothDeviceInfo$DeviceStatus;
.super Ljava/lang/Object;
.source "BluetoothDeviceInfo.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "DeviceStatus"
.end annotation


# static fields
.field public static final DEVICE_STATUS_CONNECTED:I = 0x1

.field public static final DEVICE_STATUS_CONNECTEDMEDIA:I = 0x3

.field public static final DEVICE_STATUS_CONNECTEDPHONE:I = 0x2

.field public static final DEVICE_STATUS_CONNECTING:I = 0x4

.field public static final DEVICE_STATUS_DISCONNECTING:I = 0x5

.field public static final DEVICE_STATUS_PAIRED:I = 0x0

.field public static final DEVICE_STATUS_UNKOWN:I = -0x1


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 14
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
