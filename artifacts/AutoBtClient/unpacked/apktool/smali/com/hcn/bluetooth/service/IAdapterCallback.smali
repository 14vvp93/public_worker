.class public interface abstract Lcom/hcn/bluetooth/service/IAdapterCallback;
.super Ljava/lang/Object;
.source "IAdapterCallback.java"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/bluetooth/service/IAdapterCallback$Stub;
    }
.end annotation


# virtual methods
.method public abstract onBluetoothStateChanged(I)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onConnectionStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onDeviceBondStateChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;I)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onDiscoveryDeviceFound(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onDiscoveryDeviceNameChanged(Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method

.method public abstract onDiscoveryStateChanged(I)V
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation
.end method
