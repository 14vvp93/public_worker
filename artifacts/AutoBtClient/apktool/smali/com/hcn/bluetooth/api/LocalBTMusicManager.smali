.class public Lcom/hcn/bluetooth/api/LocalBTMusicManager;
.super Ljava/lang/Object;
.source "LocalBTMusicManager.java"


# static fields
.field public static final ACTION_CONNECTION_STATE_CHANGED:Ljava/lang/String; = "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED"

.field public static final ACTION_TRACK_EVENT:Ljava/lang/String; = "android.bluetooth.avrcp-controller.profile.action.TRACK_EVENT"

.field public static final CMD_AVRCP_NEXT:I = 0x4

.field public static final CMD_AVRCP_PAUSE:I = 0x1

.field public static final CMD_AVRCP_PLAY:I = 0x0

.field public static final CMD_AVRCP_PLAY_PAUSE:I = 0x2

.field public static final CMD_AVRCP_PREV:I = 0x5

.field public static final CMD_AVRCP_STOP:I = 0x3

.field public static final EXTRA_METADATA:Ljava/lang/String; = "android.bluetooth.avrcp-controller.profile.extra.METADATA"

.field public static final EXTRA_PLAYBACK:Ljava/lang/String; = "android.bluetooth.avrcp-controller.profile.extra.PLAYBACK"

.field public static final TAG:Ljava/lang/String; = "LocalBluetoothMusicManager"

.field private static sInstance:Lcom/hcn/bluetooth/api/LocalBTMusicManager;


# instance fields
.field private isBound:Z

.field private mConnection:Landroid/content/ServiceConnection;

.field private mContext:Landroid/content/Context;

.field private mListeners:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/bluetooth/api/ConnectionListener;",
            ">;"
        }
    .end annotation
.end field

.field private mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 65
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 54
    iput-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    const/4 v0, 0x0

    .line 55
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    .line 56
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    .line 111
    new-instance v0, Lcom/hcn/bluetooth/api/LocalBTMusicManager$1;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager$1;-><init>(Lcom/hcn/bluetooth/api/LocalBTMusicManager;)V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mConnection:Landroid/content/ServiceConnection;

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "LocalBluetoothMusicManager Create"

    .line 66
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$002(Lcom/hcn/bluetooth/api/LocalBTMusicManager;Z)Z
    .locals 0

    .line 17
    iput-boolean p1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    return p1
.end method

.method static synthetic access$102(Lcom/hcn/bluetooth/api/LocalBTMusicManager;Lcom/hcn/bluetooth/service/IBluetoothMusicService;)Lcom/hcn/bluetooth/service/IBluetoothMusicService;
    .locals 0

    .line 17
    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    return-object p1
.end method

.method public static getInstance()Lcom/hcn/bluetooth/api/LocalBTMusicManager;
    .locals 1

    .line 59
    sget-object v0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    if-nez v0, :cond_0

    .line 60
    new-instance v0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    invoke-direct {v0}, Lcom/hcn/bluetooth/api/LocalBTMusicManager;-><init>()V

    sput-object v0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    .line 62
    :cond_0
    sget-object v0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    return-object v0
.end method


# virtual methods
.method public addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V
    .locals 1

    if-eqz p1, :cond_0

    .line 90
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    .line 91
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public callConnectListener(Z)V
    .locals 2

    const/4 v0, 0x0

    .line 102
    :goto_0
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    if-ge v0, v1, :cond_1

    if-eqz p1, :cond_0

    .line 104
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-interface {v1}, Lcom/hcn/bluetooth/api/ConnectionListener;->onServiceConnected()V

    goto :goto_1

    .line 106
    :cond_0
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-interface {v1}, Lcom/hcn/bluetooth/api/ConnectionListener;->onServiceDisconnected()V

    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method public getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;
    .locals 1

    .line 170
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 172
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :catch_0
    move-exception v0

    .line 175
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public getID3Info()[Ljava/lang/String;
    .locals 1

    .line 272
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 273
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v0, :cond_0

    .line 275
    :try_start_0
    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->getID3Info()[Ljava/lang/String;

    move-result-object v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return-object v0

    :catch_0
    move-exception v0

    .line 278
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public init(Landroid/content/Context;)Lcom/hcn/bluetooth/api/LocalBTMusicManager;
    .locals 3

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "init"

    .line 70
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 71
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    .line 72
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mContext:Landroid/content/Context;

    .line 74
    :cond_0
    iget-boolean p1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-nez p1, :cond_1

    .line 75
    new-instance p1, Landroid/content/Intent;

    const-string v0, "com.hcn.bluetooth.service.BluetoothMusicService"

    invoke-direct {p1, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v1, "com.hcn.bluetoothservice"

    .line 77
    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 79
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mConnection:Landroid/content/ServiceConnection;

    const/4 v2, 0x1

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 81
    :cond_1
    sget-object p1, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBTMusicManager;

    return-object p1
.end method

.method public isA2dpConnected()Z
    .locals 2

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "isA2dpConnected"

    .line 131
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 132
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 134
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->isA2dpConnected()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 137
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isA2dpPlaying()Z
    .locals 2

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "isA2dpPlaying"

    .line 157
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 158
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 160
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->isA2dpPlaying()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 163
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isAvrcpConnected()Z
    .locals 2

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "isAvrcpConnected"

    .line 144
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 145
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 147
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->isAvrcpConnected()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 150
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isReady()Z
    .locals 1

    .line 85
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    return v0
.end method

.method public regMusicClientBinder(Landroid/os/IBinder;)V
    .locals 1

    .line 218
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 219
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v0, :cond_0

    .line 221
    :try_start_0
    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->regMusicClientBinder(Landroid/os/IBinder;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 224
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public registerBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V
    .locals 1

    .line 245
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 246
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v0, :cond_0

    .line 248
    :try_start_0
    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->registerBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 251
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public releaseA2dp()V
    .locals 2

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "releaseA2dp"

    .line 206
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 207
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 209
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->releaseA2dp()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 212
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V
    .locals 1

    if-eqz p1, :cond_0

    .line 97
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public requestA2dp()V
    .locals 2

    const-string v0, "LocalBluetoothMusicManager"

    const-string v1, "requestA2dp"

    .line 194
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 195
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 197
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->requestA2dp()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 200
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public send_Avrcp_Cmd(I)V
    .locals 2

    .line 182
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "send_Avrcp_Cmd cmd="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "LocalBluetoothMusicManager"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 183
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 185
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->send_Avrcp_Cmd(I)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 188
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public unRegMusicClientBinder()V
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/os/RemoteException;
        }
    .end annotation

    .line 232
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 233
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v0, :cond_0

    .line 235
    :try_start_0
    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->unRegMusicClientBinder()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 238
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public unRegisterBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V
    .locals 1

    .line 259
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 260
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBTMusicManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothMusicService;

    if-eqz v0, :cond_0

    .line 262
    :try_start_0
    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothMusicService;->unregisterBTMusicCallback(Lcom/hcn/bluetooth/service/IMusicCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 265
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method
