.class public interface abstract Lcom/hcn/autocan/aidl/IAirCtrlServices;
.super Ljava/lang/Object;
.source "IAirCtrlServices.java"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/autocan/aidl/IAirCtrlServices$Stub;
    }
.end annotation


# virtual methods
.method public abstract airCtrl(II)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract unRegisterCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method
