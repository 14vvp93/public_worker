.class public final Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;
.super Ljava/lang/Object;
.source "LocalBluetoothHfpclientManager.java"


# static fields
.field public static final TAG:Ljava/lang/String; = "LocalBluetoothHfpclientManager"

.field private static sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;


# instance fields
.field private isBound:Z

.field private mConnection:Landroid/content/ServiceConnection;

.field private mContext:Landroid/content/Context;

.field private mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 30
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 19
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    const/4 v1, 0x0

    .line 20
    iput-boolean v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    .line 21
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    .line 68
    new-instance v0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager$1;-><init>(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;)V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mConnection:Landroid/content/ServiceConnection;

    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "LocalBluetoothHfpclientManager Create"

    .line 31
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$002(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Z)Z
    .locals 0

    .line 13
    iput-boolean p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    return p1
.end method

.method static synthetic access$102(Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;)Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;
    .locals 0

    .line 13
    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    return-object p1
.end method

.method public static declared-synchronized getInstance()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;
    .locals 2

    const-class v0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    monitor-enter v0

    .line 24
    :try_start_0
    sget-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    if-nez v1, :cond_0

    .line 25
    new-instance v1, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    invoke-direct {v1}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;-><init>()V

    sput-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    .line 27
    :cond_0
    sget-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    monitor-exit v0

    return-object v1

    :catchall_0
    move-exception v1

    monitor-exit v0

    throw v1
.end method


# virtual methods
.method public declared-synchronized acceptCall(I)V
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "pickup"

    .line 125
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 126
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 128
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->acceptCall(I)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 131
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 134
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public deinit()V
    .locals 2

    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "deinit"

    .line 58
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 59
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 60
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mConnection:Landroid/content/ServiceConnection;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unbindService(Landroid/content/ServiceConnection;)V

    :cond_0
    const/4 v0, 0x0

    .line 62
    iput-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    const/4 v0, 0x0

    .line 63
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    .line 64
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    .line 65
    sput-object v0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    return-void
.end method

.method public declared-synchronized dial(Ljava/lang/String;)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    .line 99
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "dial:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " isBound:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-boolean v2, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 100
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 102
    :try_start_1
    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->dial(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 105
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    goto :goto_0

    :cond_0
    const-string p1, "LocalBluetoothHfpclientManager"

    const-string v0, "mService == null"

    .line 108
    invoke-static {p1, v0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 110
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized getLastCall()Ljava/lang/String;
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "getLastCall"

    .line 161
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 162
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 164
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->getLastCall()Ljava/lang/String;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 167
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const-string v0, ""
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 170
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized hangup()V
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "hangup"

    .line 137
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 138
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 140
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->hangup()V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 143
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 146
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public init(Landroid/content/Context;)V
    .locals 3

    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "init"

    .line 35
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    if-eqz p1, :cond_1

    .line 40
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    if-eqz v1, :cond_0

    .line 41
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "mContext is not null "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 43
    :cond_0
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    .line 46
    new-instance p1, Landroid/content/Intent;

    const-string v0, "com.hcn.bluetooth.service.BluetoothHfpclientService"

    invoke-direct {p1, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v1, "com.hcn.bluetoothservice"

    .line 48
    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 50
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mConnection:Landroid/content/ServiceConnection;

    const/4 v2, 0x1

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    return-void

    .line 37
    :cond_1
    new-instance p1, Ljava/lang/IllegalArgumentException;

    const-string v0, "LocalBluetoothHfpclientManagerinit(Context context): conext is null"

    invoke-direct {p1, v0}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p1
.end method

.method public declared-synchronized isAudioConnected()Z
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "isAudioConnected"

    .line 86
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 87
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 89
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->isAudioConnected()Z

    move-result v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return v0

    :catch_0
    move-exception v0

    .line 92
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 95
    monitor-exit p0

    return v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public isReady()Z
    .locals 1

    .line 54
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z

    return v0
.end method

.method public declared-synchronized sendDTMF(B)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    .line 113
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "sendDTMF:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 114
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 116
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->sendDTMF(B)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 119
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 122
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized switchAudio()V
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothHfpclientManager"

    const-string v1, "switchAudio"

    .line 149
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 150
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 152
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothHfpclientService;->switchAudio()V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 155
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 158
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method
