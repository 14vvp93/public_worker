.class public interface abstract Lcom/hcn/bluetooth/service/IPbapCallback;
.super Ljava/lang/Object;
.source "IPbapCallback.java"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IPbapCallback$Stub;
    }
.end annotation


# virtual methods
.method public abstract onPbapConnectStateChanged(I)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onPbapDownloadStateChanged(II)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method
