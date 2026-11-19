.class public Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;
.super Landroid/content/BroadcastReceiver;
.source "ConnectedDeviceSignalController.java"

# interfaces
.implements Lcom/android/systemui/statusbar/policy/BluetoothController$Callback;


# static fields
.field private static final SIGNAL_STRENGTH_ICONS:[I


# instance fields
.field private mBluetoothHeadsetClient:Landroid/bluetooth/BluetoothHeadsetClient;

.field private final mNetworkSignalView:Landroid/widget/ImageView;

.field private final mSignalDrawable:Lcom/android/settingslib/graph/SignalDrawable;

.field private final mSignalsView:Landroid/view/View;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 51
    const/4 v0, 0x6

    new-array v0, v0, [I

    fill-array-data v0, :array_0

    sput-object v0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->SIGNAL_STRENGTH_ICONS:[I

    return-void

    nop

    :array_0
    .array-data 4
        0x0
        0x0
        0x1
        0x2
        0x3
        0x4
    .end array-data
.end method

.method private processActionAgEvent(Landroid/content/Intent;)V
    .locals 6
    .param p1, "intent"    # Landroid/content/Intent;

    .line 165
    const-string v0, "android.bluetooth.headsetclient.extra.NETWORK_STATUS"

    const/4 v1, -0x1

    invoke-virtual {p1, v0, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v0

    .line 167
    .local v0, "networkStatus":I
    if-eq v0, v1, :cond_1

    .line 168
    sget-boolean v2, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v2, :cond_0

    .line 169
    const-string v2, "DeviceSignalCtlr"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "EXTRA_NETWORK_STATUS:  "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 172
    :cond_0
    if-nez v0, :cond_1

    .line 173
    const v2, 0x7f08063a

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->setNetworkSignalIcon(I)V

    .line 177
    :cond_1
    const-string v2, "android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH"

    invoke-virtual {p1, v2, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v2

    .line 179
    .local v2, "signalStrength":I
    if-eq v2, v1, :cond_3

    .line 180
    sget-boolean v3, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v3, :cond_2

    .line 181
    const-string v3, "DeviceSignalCtlr"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "EXTRA_NETWORK_SIGNAL_STRENGTH: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 184
    :cond_2
    sget-object v3, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->SIGNAL_STRENGTH_ICONS:[I

    aget v3, v3, v2

    invoke-direct {p0, v3}, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->setNetworkSignalIcon(I)V

    .line 187
    :cond_3
    const-string v3, "android.bluetooth.headsetclient.extra.NETWORK_ROAMING"

    invoke-virtual {p1, v3, v1}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v3

    .line 189
    .local v3, "roamingStatus":I
    if-eq v3, v1, :cond_4

    .line 190
    sget-boolean v1, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v1, :cond_4

    .line 191
    const-string v1, "DeviceSignalCtlr"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "EXTRA_NETWORK_ROAMING: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v1, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 194
    :cond_4
    return-void
.end method

.method private setNetworkSignalIcon(I)V
    .locals 3
    .param p1, "level"    # I

    .line 198
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mSignalsView:Landroid/view/View;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 200
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mSignalDrawable:Lcom/android/settingslib/graph/SignalDrawable;

    const/4 v2, 0x5

    invoke-static {p1, v2, v1}, Lcom/android/settingslib/graph/SignalDrawable;->getState(IIZ)I

    move-result v2

    invoke-virtual {v0, v2}, Lcom/android/settingslib/graph/SignalDrawable;->setLevel(I)Z

    .line 202
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mNetworkSignalView:Landroid/widget/ImageView;

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 203
    return-void
.end method

.method private updateViewVisibility(Landroid/bluetooth/BluetoothDevice;I)V
    .locals 5
    .param p1, "device"    # Landroid/bluetooth/BluetoothDevice;
    .param p2, "newState"    # I

    .line 206
    const/4 v0, 0x2

    if-ne p2, v0, :cond_6

    .line 207
    sget-boolean v0, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 208
    const-string v0, "DeviceSignalCtlr"

    const-string v1, "Device connected"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 211
    :cond_0
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mBluetoothHeadsetClient:Landroid/bluetooth/BluetoothHeadsetClient;

    if-eqz v0, :cond_5

    if-nez p1, :cond_1

    goto :goto_0

    .line 216
    :cond_1
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mBluetoothHeadsetClient:Landroid/bluetooth/BluetoothHeadsetClient;

    invoke-virtual {v0, p1}, Landroid/bluetooth/BluetoothHeadsetClient;->getCurrentAgEvents(Landroid/bluetooth/BluetoothDevice;)Landroid/os/Bundle;

    move-result-object v0

    .line 217
    .local v0, "featuresBundle":Landroid/os/Bundle;
    if-nez v0, :cond_2

    .line 218
    return-void

    .line 221
    :cond_2
    const-string v1, "android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH"

    const/4 v2, -0x1

    invoke-virtual {v0, v1, v2}, Landroid/os/Bundle;->getInt(Ljava/lang/String;I)I

    move-result v1

    .line 223
    .local v1, "signalStrength":I
    if-eq v1, v2, :cond_4

    .line 224
    sget-boolean v2, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v2, :cond_3

    .line 225
    const-string v2, "DeviceSignalCtlr"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "EXTRA_NETWORK_SIGNAL_STRENGTH: "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 228
    :cond_3
    sget-object v2, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->SIGNAL_STRENGTH_ICONS:[I

    aget v2, v2, v1

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->setNetworkSignalIcon(I)V

    .line 230
    .end local v0    # "featuresBundle":Landroid/os/Bundle;
    .end local v1    # "signalStrength":I
    :cond_4
    goto :goto_1

    .line 212
    :cond_5
    :goto_0
    return-void

    .line 230
    :cond_6
    if-nez p2, :cond_8

    .line 231
    sget-boolean v0, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v0, :cond_7

    .line 232
    const-string v0, "DeviceSignalCtlr"

    const-string v1, "Device disconnected"

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 235
    :cond_7
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mNetworkSignalView:Landroid/widget/ImageView;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 236
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mSignalsView:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 238
    :cond_8
    :goto_1
    return-void
.end method


# virtual methods
.method public onBluetoothDevicesChanged()V
    .locals 0

    .line 115
    return-void
.end method

.method public onBluetoothStateChange(Z)V
    .locals 3
    .param p1, "enabled"    # Z

    .line 119
    sget-boolean v0, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v0, :cond_0

    .line 120
    const-string v0, "DeviceSignalCtlr"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "onBluetoothStateChange(). enabled: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 126
    :cond_0
    if-nez p1, :cond_1

    .line 127
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mNetworkSignalView:Landroid/widget/ImageView;

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 128
    iget-object v0, p0, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->mSignalsView:Landroid/view/View;

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 130
    :cond_1
    return-void
.end method

.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 6
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "intent"    # Landroid/content/Intent;

    .line 134
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object v0

    .line 136
    .local v0, "action":Ljava/lang/String;
    sget-boolean v1, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v1, :cond_0

    .line 137
    const-string v1, "DeviceSignalCtlr"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "onReceive(). action: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 140
    :cond_0
    const-string v1, "android.bluetooth.headsetclient.profile.action.AG_EVENT"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_2

    .line 141
    sget-boolean v1, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v1, :cond_1

    .line 142
    const-string v1, "DeviceSignalCtlr"

    const-string v2, "Received ACTION_AG_EVENT"

    invoke-static {v1, v2}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 145
    :cond_1
    invoke-direct {p0, p2}, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->processActionAgEvent(Landroid/content/Intent;)V

    goto :goto_0

    .line 146
    :cond_2
    const-string v1, "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_4

    .line 147
    const-string v1, "android.bluetooth.profile.extra.STATE"

    const/4 v2, -0x1

    invoke-virtual {p2, v1, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v1

    .line 149
    .local v1, "newState":I
    sget-boolean v3, Lcom/android/systemui/statusbar/phone/StatusBar;->DEBUG:Z

    if-eqz v3, :cond_3

    .line 150
    const-string v3, "android.bluetooth.profile.extra.PREVIOUS_STATE"

    invoke-virtual {p2, v3, v2}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result v2

    .line 151
    .local v2, "oldState":I
    const-string v3, "DeviceSignalCtlr"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "ACTION_CONNECTION_STATE_CHANGED event: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v5, " -> "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v4, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 154
    .end local v2    # "oldState":I
    :cond_3
    const-string v2, "android.bluetooth.device.extra.DEVICE"

    .line 155
    invoke-virtual {p2, v2}, Landroid/content/Intent;->getExtra(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/bluetooth/BluetoothDevice;

    .line 156
    .local v2, "device":Landroid/bluetooth/BluetoothDevice;
    invoke-direct {p0, v2, v1}, Lcom/android/systemui/statusbar/car/ConnectedDeviceSignalController;->updateViewVisibility(Landroid/bluetooth/BluetoothDevice;I)V

    .line 158
    .end local v1    # "newState":I
    .end local v2    # "device":Landroid/bluetooth/BluetoothDevice;
    :cond_4
    :goto_0
    return-void
.end method
