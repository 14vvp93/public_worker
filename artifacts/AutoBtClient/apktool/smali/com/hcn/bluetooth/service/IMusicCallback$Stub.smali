.class public abstract Lcom/hcn/bluetooth/service/IMusicCallback$Stub;
.super Landroid/os/Binder;
.source "IMusicCallback.java"

# interfaces
.implements Lcom/hcn/bluetooth/service/IMusicCallback;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/bluetooth/service/IMusicCallback;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IMusicCallback$Stub$Proxy;
    }
.end annotation


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "com.hcn.bluetooth.service.IMusicCallback"

.field static final TRANSACTION_onA2dpConnectStateChanged:I = 0x1

.field static final TRANSACTION_onAvrcpConnectStateChanged:I = 0x2

.field static final TRANSACTION_onMetadataChanged:I = 0x3

.field static final TRANSACTION_onPlayStatusChanged:I = 0x4


# direct methods
.method public constructor <init>()V
    .locals 1

    .line 14
    invoke-direct {p0}, Landroid/os/Binder;-><init>()V

    const-string v0, "com.hcn.bluetooth.service.IMusicCallback"

    .line 15
    invoke-virtual {p0, p0, v0}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->attachInterface(Landroid/os/IInterface;Ljava/lang/String;)V

    return-void
.end method

.method public static asInterface(Landroid/os/IBinder;)Lcom/hcn/bluetooth/service/IMusicCallback;
    .locals 2

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    const-string v0, "com.hcn.bluetooth.service.IMusicCallback"

    .line 26
    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 27
    instance-of v1, v0, Lcom/hcn/bluetooth/service/IMusicCallback;

    if-eqz v1, :cond_1

    .line 28
    check-cast v0, Lcom/hcn/bluetooth/service/IMusicCallback;

    return-object v0

    .line 30
    :cond_1
    new-instance v0, Lcom/hcn/bluetooth/service/IMusicCallback$Stub$Proxy;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

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

    const/4 v0, 0x1

    const-string v1, "com.hcn.bluetooth.service.IMusicCallback"

    if-eq p1, v0, :cond_5

    const/4 v2, 0x2

    if-eq p1, v2, :cond_4

    const/4 v2, 0x3

    if-eq p1, v2, :cond_3

    const/4 v2, 0x4

    if-eq p1, v2, :cond_1

    const v2, 0x5f4e5446

    if-eq p1, v2, :cond_0

    .line 90
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result p1

    return p1

    .line 43
    :cond_0
    invoke-virtual {p3, v1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    return v0

    .line 76
    :cond_1
    invoke-virtual {p2, v1}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 78
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_2

    .line 79
    sget-object p1, Lcom/hcn/bluetooth/api/MusicPlayState;->CREATOR:Landroid/os/Parcelable$Creator;

    invoke-interface {p1, p2}, Landroid/os/Parcelable$Creator;->createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/hcn/bluetooth/api/MusicPlayState;

    goto :goto_0

    :cond_2
    const/4 p1, 0x0

    .line 84
    :goto_0
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->onPlayStatusChanged(Lcom/hcn/bluetooth/api/MusicPlayState;)V

    .line 85
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    return v0

    .line 64
    :cond_3
    invoke-virtual {p2, v1}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 66
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p1

    .line 68
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p3

    .line 70
    invoke-virtual {p2}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object p2

    .line 71
    invoke-virtual {p0, p1, p3, p2}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->onMetadataChanged(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    return v0

    .line 56
    :cond_4
    invoke-virtual {p2, v1}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 58
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 59
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->onAvrcpConnectStateChanged(I)V

    return v0

    .line 48
    :cond_5
    invoke-virtual {p2, v1}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 50
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    .line 51
    invoke-virtual {p0, p1}, Lcom/hcn/bluetooth/service/IMusicCallback$Stub;->onA2dpConnectStateChanged(I)V

    return v0
.end method
