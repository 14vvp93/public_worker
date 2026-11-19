.class Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;
.super Ljava/lang/Object;
.source "IAirCtrlServices.java"

# interfaces
.implements Lcom/hcn/autocan/aidl/IAirCtrlServices;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "Proxy"
.end annotation


# static fields
.field public static sDefaultImpl:Lcom/hcn/autocan/aidl/IAirCtrlServices;


# instance fields
.field private mRemote:Landroid/os/IBinder;


# direct methods
.method constructor <init>(Landroid/os/IBinder;)V
    .locals 0
    .param p1, "remote"    # Landroid/os/IBinder;

    .line 101
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 102
    iput-object p1, p0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->mRemote:Landroid/os/IBinder;

    .line 103
    return-void
.end method


# virtual methods
.method public airCtrl(II)V
    .locals 5
    .param p1, "ctrl_id"    # I
    .param p2, "action"    # I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 114
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 115
    .local v0, "_data":Landroid/os/Parcel;
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v1

    .line 117
    .local v1, "_reply":Landroid/os/Parcel;
    :try_start_0
    const-string v2, "com.hcn.autocan.aidl.IAirCtrlServices"

    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 118
    invoke-virtual {v0, p1}, Landroid/os/Parcel;->writeInt(I)V

    .line 119
    invoke-virtual {v0, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 120
    iget-object v2, p0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->mRemote:Landroid/os/IBinder;

    const/4 v3, 0x1

    const/4 v4, 0x0

    invoke-interface {v2, v3, v0, v1, v4}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result v2

    .line 121
    .local v2, "_status":Z
    if-nez v2, :cond_0

    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    if-eqz v3, :cond_0

    .line 122
    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    invoke-interface {v3, p1, p2}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->airCtrl(II)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 128
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 129
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 123
    return-void

    .line 125
    :cond_0
    :try_start_1
    invoke-virtual {v1}, Landroid/os/Parcel;->readException()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 128
    .end local v2    # "_status":Z
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 129
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 130
    nop

    .line 131
    return-void

    .line 128
    :catchall_0
    move-exception v2

    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 129
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    throw v2
.end method

.method public asBinder()Landroid/os/IBinder;
    .locals 1

    .line 106
    iget-object v0, p0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->mRemote:Landroid/os/IBinder;

    return-object v0
.end method

.method public registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    .locals 5
    .param p1, "callBack"    # Lcom/hcn/autocan/aidl/IAirCallBack;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 134
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 135
    .local v0, "_data":Landroid/os/Parcel;
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v1

    .line 137
    .local v1, "_reply":Landroid/os/Parcel;
    :try_start_0
    const-string v2, "com.hcn.autocan.aidl.IAirCtrlServices"

    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 138
    if-eqz p1, :cond_0

    invoke-interface {p1}, Lcom/hcn/autocan/aidl/IAirCallBack;->asBinder()Landroid/os/IBinder;

    move-result-object v2

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeStrongBinder(Landroid/os/IBinder;)V

    .line 139
    iget-object v2, p0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->mRemote:Landroid/os/IBinder;

    const/4 v3, 0x2

    const/4 v4, 0x0

    invoke-interface {v2, v3, v0, v1, v4}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result v2

    .line 140
    .local v2, "_status":Z
    if-nez v2, :cond_1

    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    if-eqz v3, :cond_1

    .line 141
    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    invoke-interface {v3, p1}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 147
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 148
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 142
    return-void

    .line 144
    :cond_1
    :try_start_1
    invoke-virtual {v1}, Landroid/os/Parcel;->readException()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 147
    .end local v2    # "_status":Z
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 148
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 149
    nop

    .line 150
    return-void

    .line 147
    :catchall_0
    move-exception v2

    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 148
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    throw v2
.end method

.method public unRegisterCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    .locals 5
    .param p1, "callBack"    # Lcom/hcn/autocan/aidl/IAirCallBack;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 153
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 154
    .local v0, "_data":Landroid/os/Parcel;
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v1

    .line 156
    .local v1, "_reply":Landroid/os/Parcel;
    :try_start_0
    const-string v2, "com.hcn.autocan.aidl.IAirCtrlServices"

    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 157
    if-eqz p1, :cond_0

    invoke-interface {p1}, Lcom/hcn/autocan/aidl/IAirCallBack;->asBinder()Landroid/os/IBinder;

    move-result-object v2

    goto :goto_0

    :cond_0
    const/4 v2, 0x0

    :goto_0
    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeStrongBinder(Landroid/os/IBinder;)V

    .line 158
    iget-object v2, p0, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub$Proxy;->mRemote:Landroid/os/IBinder;

    const/4 v3, 0x3

    const/4 v4, 0x0

    invoke-interface {v2, v3, v0, v1, v4}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    move-result v2

    .line 159
    .local v2, "_status":Z
    if-nez v2, :cond_1

    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    if-eqz v3, :cond_1

    .line 160
    invoke-static {}, Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;->getDefaultImpl()Lcom/hcn/autocan/aidl/IAirCtrlServices;

    move-result-object v3

    invoke-interface {v3, p1}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->unRegisterCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 166
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 167
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 161
    return-void

    .line 163
    :cond_1
    :try_start_1
    invoke-virtual {v1}, Landroid/os/Parcel;->readException()V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 166
    .end local v2    # "_status":Z
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 167
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 168
    nop

    .line 169
    return-void

    .line 166
    :catchall_0
    move-exception v2

    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 167
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    throw v2
.end method
