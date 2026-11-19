.class public Lcom/hcn/autocan/aidl/AirCtrlManager;
.super Ljava/lang/Object;
.source "AirCtrlManager.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;
    }
.end annotation


# static fields
.field private static instance:Lcom/hcn/autocan/aidl/AirCtrlManager;


# instance fields
.field private TAG:Ljava/lang/String;

.field private airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

.field callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

.field private list_call_back:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/hcn/autocan/aidl/IAirCallBack;",
            ">;"
        }
    .end annotation
.end field

.field private mContext:Landroid/content/Context;

.field private mReceiver:Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;

.field private serviceConnection:Landroid/content/ServiceConnection;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 20
    new-instance v0, Lcom/hcn/autocan/aidl/AirCtrlManager;

    invoke-direct {v0}, Lcom/hcn/autocan/aidl/AirCtrlManager;-><init>()V

    sput-object v0, Lcom/hcn/autocan/aidl/AirCtrlManager;->instance:Lcom/hcn/autocan/aidl/AirCtrlManager;

    return-void
.end method

.method private constructor <init>()V
    .locals 1

    .line 24
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 19
    const-string v0, "AirCtrlManager_"

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    .line 23
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mReceiver:Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;

    .line 66
    new-instance v0, Lcom/hcn/autocan/aidl/AirCtrlManager$1;

    invoke-direct {v0, p0}, Lcom/hcn/autocan/aidl/AirCtrlManager$1;-><init>(Lcom/hcn/autocan/aidl/AirCtrlManager;)V

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->serviceConnection:Landroid/content/ServiceConnection;

    .line 90
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->list_call_back:Ljava/util/ArrayList;

    .line 133
    new-instance v0, Lcom/hcn/autocan/aidl/AirCtrlManager$2;

    invoke-direct {v0, p0}, Lcom/hcn/autocan/aidl/AirCtrlManager$2;-><init>(Lcom/hcn/autocan/aidl/AirCtrlManager;)V

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    .line 26
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    return-object v0
.end method

.method static synthetic access$100(Lcom/hcn/autocan/aidl/AirCtrlManager;)Lcom/hcn/autocan/aidl/IAirCtrlServices;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    return-object v0
.end method

.method static synthetic access$102(Lcom/hcn/autocan/aidl/AirCtrlManager;Lcom/hcn/autocan/aidl/IAirCtrlServices;)Lcom/hcn/autocan/aidl/IAirCtrlServices;
    .locals 0
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;
    .param p1, "x1"    # Lcom/hcn/autocan/aidl/IAirCtrlServices;

    .line 18
    iput-object p1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    return-object p1
.end method

.method static synthetic access$200(Lcom/hcn/autocan/aidl/AirCtrlManager;)Ljava/util/ArrayList;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->list_call_back:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$300(Lcom/hcn/autocan/aidl/AirCtrlManager;)V
    .locals 0
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    invoke-direct {p0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->bindServices()V

    return-void
.end method

.method static synthetic access$400(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/Context;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    return-object v0
.end method

.method static synthetic access$500(Lcom/hcn/autocan/aidl/AirCtrlManager;)Landroid/content/ServiceConnection;
    .locals 1
    .param p0, "x0"    # Lcom/hcn/autocan/aidl/AirCtrlManager;

    .line 18
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->serviceConnection:Landroid/content/ServiceConnection;

    return-object v0
.end method

.method private bindServices()V
    .locals 5

    .line 147
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    if-nez v0, :cond_0

    .line 148
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    const-string v1, "context is null"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 149
    return-void

    .line 151
    :cond_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    .line 152
    .local v0, "intent":Landroid/content/Intent;
    new-instance v1, Landroid/content/ComponentName;

    const-string v2, "com.hcn.autocan"

    const-string v3, "com.hcn.autocan.service.CanService"

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    .line 154
    .local v1, "mAudioComponent":Landroid/content/ComponentName;
    invoke-virtual {v0, v1}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;

    .line 155
    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    iget-object v3, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->serviceConnection:Landroid/content/ServiceConnection;

    const/4 v4, 0x1

    invoke-virtual {v2, v0, v3, v4}, Landroid/content/Context;->bindService(Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z

    .line 156
    return-void
.end method

.method public static getInstance()Lcom/hcn/autocan/aidl/AirCtrlManager;
    .locals 1

    .line 29
    sget-object v0, Lcom/hcn/autocan/aidl/AirCtrlManager;->instance:Lcom/hcn/autocan/aidl/AirCtrlManager;

    return-object v0
.end method


# virtual methods
.method public airCtrl(II)V
    .locals 2
    .param p1, "ctrl_id"    # I
    .param p2, "action"    # I

    .line 49
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    if-nez v0, :cond_1

    .line 50
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    const-string v1, "airCtrlServices is null"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 51
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    if-eqz v0, :cond_0

    .line 52
    invoke-direct {p0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->bindServices()V

    .line 54
    :cond_0
    return-void

    .line 57
    :cond_1
    :try_start_0
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    invoke-interface {v0, p1, p2}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->airCtrl(II)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 62
    goto :goto_0

    .line 58
    :catch_0
    move-exception v0

    .line 60
    .local v0, "e":Landroid/os/RemoteException;
    invoke-direct {p0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->bindServices()V

    .line 61
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    .line 63
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    return-void
.end method

.method public init(Landroid/content/Context;)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;

    .line 32
    iput-object p1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    .line 33
    invoke-direct {p0}, Lcom/hcn/autocan/aidl/AirCtrlManager;->bindServices()V

    .line 34
    new-instance v0, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;

    invoke-direct {v0, p0}, Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;-><init>(Lcom/hcn/autocan/aidl/AirCtrlManager;)V

    iput-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mReceiver:Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;

    .line 35
    new-instance v0, Landroid/content/IntentFilter;

    invoke-direct {v0}, Landroid/content/IntentFilter;-><init>()V

    .line 36
    .local v0, "filter":Landroid/content/IntentFilter;
    sget-object v1, Lcom/hcn/util/HCNConstants;->ACTION_INIT_CAN:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 37
    sget-object v1, Lcom/hcn/util/HCNConstants;->ACTION_UNBIND_CAN:Ljava/lang/String;

    invoke-virtual {v0, v1}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 38
    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mContext:Landroid/content/Context;

    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->mReceiver:Lcom/hcn/autocan/aidl/AirCtrlManager$InitBroadcastReceiver;

    invoke-virtual {v1, v2, v0}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    .line 39
    return-void
.end method

.method public registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    .locals 3
    .param p1, "callBack"    # Lcom/hcn/autocan/aidl/IAirCallBack;

    .line 92
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "registerCallBack "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 93
    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->list_call_back:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->contains(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 94
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->list_call_back:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 96
    :cond_0
    return-void
.end method

.method public registerCallback()V
    .locals 3

    .line 110
    :try_start_0
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "register callBack "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 111
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    if-eqz v0, :cond_0

    .line 112
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    invoke-interface {v0, v1}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->registerCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 116
    :cond_0
    goto :goto_0

    .line 114
    :catch_0
    move-exception v0

    .line 115
    .local v0, "e":Landroid/os/RemoteException;
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    .line 118
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    return-void
.end method

.method public unRegisterCallBack()V
    .locals 3

    .line 120
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    if-nez v0, :cond_0

    .line 121
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    const-string v1, "unRegisterCallBack is null can not be remove"

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 122
    return-void

    .line 125
    :cond_0
    :try_start_0
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "unregister callBack "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v2, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 126
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    if-eqz v0, :cond_1

    .line 127
    iget-object v0, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->airCtrlServices:Lcom/hcn/autocan/aidl/IAirCtrlServices;

    iget-object v1, p0, Lcom/hcn/autocan/aidl/AirCtrlManager;->callBack:Lcom/hcn/autocan/aidl/IAirCallBack;

    invoke-interface {v0, v1}, Lcom/hcn/autocan/aidl/IAirCtrlServices;->unRegisterCallBack(Lcom/hcn/autocan/aidl/IAirCallBack;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0

    .line 131
    :cond_1
    goto :goto_0

    .line 129
    :catch_0
    move-exception v0

    .line 130
    .local v0, "e":Landroid/os/RemoteException;
    invoke-virtual {v0}, Landroid/os/RemoteException;->printStackTrace()V

    .line 132
    .end local v0    # "e":Landroid/os/RemoteException;
    :goto_0
    return-void
.end method
