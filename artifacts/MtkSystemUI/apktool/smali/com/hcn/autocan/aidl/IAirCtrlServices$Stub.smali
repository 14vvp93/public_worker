.class public abstract Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;
.super Landroid/os/Binder;
.source "IAirCtrlServices.java"

# interfaces
.implements Lcom/hcn/autocan/aidl/IAirCtrlServices;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/autocan/aidl/IAirCtrlServices;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x409
    name = "Stub"
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;
    }
.end annotation


# direct methods
.method public static asInterface(Landroid/os/IBinder;)Lcom/hcn/autocan/aidl/IAirCtrlServices;
    .locals 2
    .param p0, "obj"    # Landroid/os/IBinder;

    .line 39
    if-nez p0, :cond_0

    .line 40
    const/4 v0, 0x0

    return-object v0

    .line 42
    :cond_0
    const-string v0, "com.hcn.autocan.aidl.IAirCtrlServices"

    invoke-interface {p0, v0}, Landroid/os/IBinder;->queryLocalInterface(Ljava/lang/String;)Landroid/os/IInterface;

    move-result-object v0

    .line 43
    .local v0, "iin":Landroid/os/IInterface;
    if-eqz v0, :cond_1

    instance-of v1, v0, Lcom/hcn/autocan/aidl/IAirCtrlServices;

    if-eqz v1, :cond_1

    .line 44
    move-object v1, v0

    check-cast v1, Lcom/hcn/autocan/aidl/IAirCtrlServices;

    return-object v1

    .line 46
    :cond_1
    new-instance v1, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;

    invoke-direct {v1, p0}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;-><init>(Landroid/os/IBinder;)V

    return-object v1
.end method

.method public static getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;
    .locals 1

    .line 183
    sget-object v0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->sDefaultImpl:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    return-object v0
.end method


# virtual methods
.method public onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z
    .locals 4
    .param p1, "code"    # I
    .param p2, "data"    # Landroid/os/Parcel;
    .param p3, "reply"    # Landroid/os/Parcel;
    .param p4, "flags"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 54
    const-string v0, "com.hcn.autocan.aidl.IAirCtrlServices"

    .line 55
    .local v0, "descriptor":Ljava/lang/String;
    const v1, 0x5f4e5446

    const/4 v2, 0x1

    if-eq p1, v1, :cond_0

    packed-switch p1, :pswitch_data_0

    .line 93
    invoke-super {p0, p1, p2, p3, p4}, Landroid/os/Binder;->onTransact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result v1

    return v1

    .line 84
    :pswitch_0
    invoke-virtual {p2, v0}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 86
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object v1

    invoke-static {v1}, Lcom/hcn/autocan/aidl/IAirCallBack$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/autocan/aidl/IAirCallBack;

    move-result-object v1

    .line 87
    .local v1, "_arg0":Lcom/hcn/autocan/aidl/IAirCallBack;
    invoke-virtual {p0, v1}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->unRegisterCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V

    .line 88
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 89
    return v2

    .line 75
    .end local v1    # "_arg0":Lcom/hcn/autocan/aidl/IAirCallBack;
    :pswitch_1
    invoke-virtual {p2, v0}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 77
    invoke-virtual {p2}, Landroid/os/Parcel;->readStrongBinder()Landroid/os/IBinder;

    move-result-object v1

    invoke-static {v1}, Lcom/hcn/autocan/aidl/IAirCallBack$Stub;->asInterface(Landroid/os/IBinder;)Lcom/hcn/autocan/aidl/IAirCallBack;

    move-result-object v1

    .line 78
    .restart local v1    # "_arg0":Lcom/hcn/autocan/aidl/IAirCallBack;
    invoke-virtual {p0, v1}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V

    .line 79
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 80
    return v2

    .line 64
    .end local v1    # "_arg0":Lcom/hcn/autocan/aidl/IAirCallBack;
    :pswitch_2
    invoke-virtual {p2, v0}, Landroid/os/Parcel;->enforceInterface(Ljava/lang/String;)V

    .line 66
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result v1

    .line 68
    .local v1, "_arg0":I
    invoke-virtual {p2}, Landroid/os/Parcel;->readInt()I

    move-result v3

    .line 69
    .local v3, "_arg1":I
    invoke-virtual {p0, v1, v3}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->airCtrl(II)V

    .line 70
    invoke-virtual {p3}, Landroid/os/Parcel;->writeNoException()V

    .line 71
    return v2

    .line 59
    .end local v1    # "_arg0":I
    .end local v3    # "_arg1":I
    :cond_0
    invoke-virtual {p3, v0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 60
    return v2

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
