.class public Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
.super Ljava/lang/Object;
.source "LocalBluetoothAdapterManager.java"


# static fields
.field public static final ACTION_CONNECTION_STATE_CHANGED:Ljava/lang/String; = "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED"

.field public static final PARAM_DOWNLOAD_ALL:I = 0x1

.field public static final PARAM_DOWNLOAD_MCH:I = 0x3

.field public static final PARAM_DOWNLOAD_MIOCH:I = 0x6

.field public static final PARAM_DOWNLOAD_OCH:I = 0x5

.field public static final PARAM_DOWNLOAD_PB:I = 0x2

.field public static final STATE_DOWNLOADED:I = 0x0

.field public static final STATE_DOWNLOADING:I = 0x1

.field public static final STATE_FAILED_NO_CONNECT:I = 0x2

.field public static final STATE_FAILED_UNKNOWN:I = 0x3

.field public static final TAG:Ljava/lang/String; = "LocalBluetoothAdapterManager"

.field public static final THIRD_PART_NORMAL_MODE:I = 0x0

.field public static final THIRD_PART_ZJ_CARPLAY_MODE:I = 0x1

.field private static sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;


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

.field private mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 62
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 51
    iput-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    const/4 v0, 0x0

    .line 52
    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    .line 53
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    .line 108
    new-instance v0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;

    invoke-direct {v0, p0}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager$1;-><init>(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;)V

    iput-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mConnection:Landroid/content/ServiceConnection;

    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "LocalBluetoothAdapterManager Create"

    .line 63
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-void
.end method

.method static synthetic access$002(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Z)Z
    .locals 0

    .line 18
    iput-boolean p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    return p1
.end method

.method static synthetic access$102(Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;)Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;
    .locals 0

    .line 18
    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    return-object p1
.end method

.method public static declared-synchronized getInstance()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 2

    const-class v0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    monitor-enter v0

    .line 56
    :try_start_0
    sget-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v1, :cond_0

    .line 57
    new-instance v1, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    invoke-direct {v1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;-><init>()V

    sput-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 59
    :cond_0
    sget-object v1, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
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
.method public addConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V
    .locals 1

    if-eqz p1, :cond_0

    .line 87
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    .line 88
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public callConnectListener(Z)V
    .locals 2

    const/4 v0, 0x0

    .line 99
    :goto_0
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    if-ge v0, v1, :cond_1

    if-eqz p1, :cond_0

    .line 101
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/bluetooth/api/ConnectionListener;

    invoke-interface {v1}, Lcom/hcn/bluetooth/api/ConnectionListener;->onServiceConnected()V

    goto :goto_1

    .line 103
    :cond_0
    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

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

.method public connectA2dp(Ljava/lang/String;)V
    .locals 1

    .line 428
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 430
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->connectA2dp(Ljava/lang/String;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 433
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public declared-synchronized connectDevice(Ljava/lang/String;)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    .line 179
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "connectDevice:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 180
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 182
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->connectDevice(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 185
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 188
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public connectPbap()V
    .locals 1

    .line 474
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 476
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->connectPbap()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 479
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public disConnectPbap()V
    .locals 1

    .line 485
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 487
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->disConnectPbap()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 490
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public disconnectA2dp(Ljava/lang/String;)V
    .locals 1

    .line 439
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 441
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->disconnectA2dp(Ljava/lang/String;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 444
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public declared-synchronized disconnectDevice(Ljava/lang/String;)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    .line 191
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "disconnectDevice:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 192
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 194
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->disconnectDevice(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 197
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 200
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized getBTName()Ljava/lang/String;
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "getBTName"

    .line 253
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 254
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 256
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getBTName()Ljava/lang/String;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 259
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 262
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized getBTPincode()Ljava/lang/String;
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "getBTName"

    .line 278
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 279
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 281
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getBTPincode()Ljava/lang/String;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 284
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 287
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized getBondedDevices()Ljava/util/List;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;",
            ">;"
        }
    .end annotation

    monitor-enter p0

    .line 142
    :try_start_0
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 144
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getBondedDevices()Ljava/util/List;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 147
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 150
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "getConnectDevice"

    .line 154
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 155
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 157
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getConnectDevice()Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 160
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 163
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized getDeviceList()Ljava/util/List;
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/bluetooth/api/BluetoothDeviceInfo;",
            ">;"
        }
    .end annotation

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "getDeviceList"

    .line 129
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 130
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 132
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getDeviceList()Ljava/util/List;

    move-result-object v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return-object v0

    :catch_0
    move-exception v0

    .line 135
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 138
    monitor-exit p0

    return-object v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public getPbapDownLoadState(I)Z
    .locals 1

    .line 509
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 511
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getPbapDownLoadState(I)Z

    move-result p1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 514
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public getThirdPartAPPMode()I
    .locals 1

    .line 565
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 567
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->getThirdPartAPPMode()I

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 569
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public init(Landroid/content/Context;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 3

    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "init"

    .line 67
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 68
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    .line 69
    invoke-virtual {p1}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mContext:Landroid/content/Context;

    .line 71
    :cond_0
    iget-boolean p1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-nez p1, :cond_1

    .line 72
    new-instance p1, Landroid/content/Intent;

    const-string v0, "com.hcn.bluetooth.service.BluetoothAdapterManagerService"

    invoke-direct {p1, v0}, Landroid/content/Intent;-><init>(Ljava/lang/String;)V

    const-string v1, "com.hcn.bluetoothservice"

    .line 74
    invoke-virtual {p1, v1, v0}, Landroid/content/Intent;->setClassName(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;

    .line 76
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mContext:Landroid/content/Context;

    iget-object v1, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mConnection:Landroid/content/ServiceConnection;

    const/4 v2, 0x1

    invoke-virtual {v0, p1, v1, v2}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 78
    :cond_1
    sget-object p1, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->sInstance:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object p1
.end method

.method public isA2dpConnected(Ljava/lang/String;)Z
    .locals 1

    .line 450
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 452
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isA2dpConnected(Ljava/lang/String;)Z

    move-result p1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 455
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public isAutoConnectedNetwork()Z
    .locals 1

    .line 411
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 413
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isAutoConnectedNetwork()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 416
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isBluetoothAutoAnswer()Z
    .locals 2

    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "isBluetoothAutoAnswer"

    .line 302
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 303
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 305
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isBluetoothAutoAnswer()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 308
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isBluetoothAutoConnect()Z
    .locals 2

    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "isBluetoothAutoConnect"

    .line 327
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 328
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 330
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isBluetoothAutoConnect()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 333
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public declared-synchronized isBluetoothConnected()Z
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "isBluetoothConnected"

    .line 228
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 229
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 231
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isBluetoothConnected()Z

    move-result v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return v0

    :catch_0
    move-exception v0

    .line 234
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 237
    monitor-exit p0

    return v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public declared-synchronized isBluetoothEnable()Z
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "isBluetoothEnable"

    .line 215
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 216
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 218
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isBluetoothEnable()Z

    move-result v0
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    monitor-exit p0

    return v0

    :catch_0
    move-exception v0

    .line 221
    :try_start_2
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    :cond_0
    const/4 v0, 0x0

    .line 224
    monitor-exit p0

    return v0

    :catchall_0
    move-exception v0

    monitor-exit p0

    throw v0
.end method

.method public isDiscovering()Z
    .locals 1

    .line 388
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 390
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isDiscovering()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 393
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isPbapConnected()Z
    .locals 1

    .line 462
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 464
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->isPbapConnected()Z

    move-result v0
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    :catch_0
    move-exception v0

    .line 467
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public isReady()Z
    .locals 1

    .line 82
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    return v0
.end method

.method public declared-synchronized pairDevice(Ljava/lang/String;)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    .line 167
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "pairDevice:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 168
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 170
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->pairDevice(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 173
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 176
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public pbapStartDownLoad(I)Z
    .locals 1

    .line 496
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 498
    :try_start_0
    invoke-static {}, Lcom/hcn/bluetooth/api/Utils;->updateReverseName()V

    .line 499
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->pbapStartDownLoad(I)Z

    move-result p1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 502
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    const/4 p1, 0x0

    return p1
.end method

.method public registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V
    .locals 1

    .line 543
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 545
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->registerCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 548
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V
    .locals 1

    .line 521
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 523
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->registerPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 526
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public removeConnectListener(Lcom/hcn/bluetooth/api/ConnectionListener;)V
    .locals 1

    if-eqz p1, :cond_0

    .line 94
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mListeners:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->remove(Ljava/lang/Object;)Z

    :cond_0
    return-void
.end method

.method public resetBT()V
    .locals 1

    .line 352
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 354
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->resetBT()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 357
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public setAutoConnectedNetwork(Z)V
    .locals 1

    .line 400
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 402
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setAutoConnectedNetwork(Z)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 405
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public declared-synchronized setBTName(Ljava/lang/String;)V
    .locals 2

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    const-string v1, "setBTName"

    .line 266
    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 267
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 269
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setBTName(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 272
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 275
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public declared-synchronized setBTPincode(Ljava/lang/String;)V
    .locals 1

    monitor-enter p0

    .line 291
    :try_start_0
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 293
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setBTPincode(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 296
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 299
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public setBluetoothAutoAnswer(Z)V
    .locals 2

    .line 315
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setBluetoothAutoAnswer:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "LocalBluetoothAdapterManager"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 316
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 318
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setBluetoothAutoAnswer(Z)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 321
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public setBluetoothAutoConnect(Z)V
    .locals 2

    .line 340
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "setBluetoothAutoConnect:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "LocalBluetoothAdapterManager"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 341
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 343
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setBluetoothAutoConnect(Z)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 346
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public declared-synchronized setBluetoothEnable(Z)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    .line 241
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "setBluetoothEnable:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 242
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 244
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->setBluetoothEnable(Z)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 247
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 250
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public startDiscovery()V
    .locals 1

    .line 364
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 366
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->startDiscovery()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 369
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public stopDiscovery()V
    .locals 1

    .line 376
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 378
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->stopDiscovery()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    .line 381
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public declared-synchronized unpairDevice(Ljava/lang/String;)V
    .locals 3

    monitor-enter p0

    :try_start_0
    const-string v0, "LocalBluetoothAdapterManager"

    .line 203
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "unpairDevice:"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 204
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v0, :cond_0

    .line 206
    :try_start_1
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->unpairDevice(Ljava/lang/String;)V
    :try_end_1
    .catch Landroid/os/RemoteException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 209
    :try_start_2
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 212
    :cond_0
    :goto_0
    monitor-exit p0

    return-void

    :catchall_0
    move-exception p1

    monitor-exit p0

    throw p1
.end method

.method public unregisterCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V
    .locals 1

    .line 554
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 556
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->unregisterCallback(Lcom/hcn/bluetooth/service/IAdapterCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 559
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method

.method public unregisterPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V
    .locals 1

    .line 532
    iget-boolean v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->isBound:Z

    if-eqz v0, :cond_0

    .line 534
    :try_start_0
    iget-object v0, p0, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->mService:Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;

    invoke-interface {v0, p1}, Lcom/hcn/bluetooth/service/IBluetoothAdapterManagerService;->unregisterPbapCallback(Lcom/hcn/bluetooth/service/IPbapCallback;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 537
    invoke-virtual {p1}, Landroid/os/RemoteException;->printStackTrace()V

    :cond_0
    :goto_0
    return-void
.end method
