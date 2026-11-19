.class public interface abstract Lcom/hcn/autocan/aidl/IAirCallBack;
.super Ljava/lang/Object;
.source "IAirCallBack.java"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/autocan/aidl/IAirCallBack$Stub;
    }
.end annotation


# virtual methods
.method public abstract CallBackEvent(Lcom/hcn/autocan/hiworld/gm/air/AIR;)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method
