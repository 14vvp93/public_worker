.class public Lcom/autochips/bluetooth/MyApplication;
.super Landroid/app/Application;
.source "MyApplication.java"


# static fields
.field private static final BT_A2DP_ROLE_PROP:Ljava/lang/String; = "persist.bluetooth.a2dp.role"

.field private static final TAG:Ljava/lang/String; = "MyApplication"

.field private static final isHeadrest:Z

.field private static sInstance:Lcom/autochips/bluetooth/MyApplication;


# instance fields
.field private mBinder:Landroid/os/IBinder;

.field private mBluetoothHfpclientManager:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

.field private mLocalBluetoothAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    const-string v0, "ro.headrest.pad"

    const-string v1, ""

    .line 23
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "1"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/autochips/bluetooth/MyApplication;->isHeadrest:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 14
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    const/4 v0, 0x0

    .line 29
    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBinder:Landroid/os/IBinder;

    .line 43
    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mLocalBluetoothAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 44
    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBluetoothHfpclientManager:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    return-void
.end method

.method public static getInstance()Lcom/autochips/bluetooth/MyApplication;
    .locals 1

    .line 19
    sget-object v0, Lcom/autochips/bluetooth/MyApplication;->sInstance:Lcom/autochips/bluetooth/MyApplication;

    return-object v0
.end method

.method private init(Landroid/content/Context;)V
    .locals 1

    .line 32
    iget-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mLocalBluetoothAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    if-nez v0, :cond_0

    .line 33
    invoke-static {}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->getInstance()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mLocalBluetoothAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 34
    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;->init(Landroid/content/Context;)Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    .line 36
    :cond_0
    iget-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBluetoothHfpclientManager:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    if-nez v0, :cond_1

    .line 37
    invoke-static {}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->getInstance()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    move-result-object v0

    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBluetoothHfpclientManager:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    .line 38
    invoke-virtual {v0, p1}, Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;->init(Landroid/content/Context;)V

    .line 40
    :cond_1
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->init(Landroid/app/Application;)Lcom/hcn/auto/theme/utils/ThemeUtils;

    return-void
.end method

.method public static isHeadrest()Z
    .locals 1

    .line 72
    sget-boolean v0, Lcom/autochips/bluetooth/MyApplication;->isHeadrest:Z

    return v0
.end method


# virtual methods
.method public getAdapterManager()Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;
    .locals 1

    .line 47
    iget-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mLocalBluetoothAdapterManager:Lcom/hcn/bluetooth/api/LocalBluetoothAdapterManager;

    return-object v0
.end method

.method public getBinder()Landroid/os/IBinder;
    .locals 1

    .line 26
    iget-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBinder:Landroid/os/IBinder;

    return-object v0
.end method

.method public getHfpclientManager()Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;
    .locals 1

    .line 51
    iget-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBluetoothHfpclientManager:Lcom/hcn/bluetooth/api/LocalBluetoothHfpclientManager;

    return-object v0
.end method

.method public isA2dpSourceRole()Z
    .locals 2

    const-string v0, "persist.bluetooth.a2dp.role"

    const-string v1, "sink"

    .line 76
    invoke-static {v0, v1}, Lcom/hcn/bluetooth/api/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 77
    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    const-string v1, "source"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    return v0

    :cond_0
    const/4 v0, 0x0

    return v0
.end method

.method public onCreate()V
    .locals 1

    .line 57
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 58
    invoke-static {p0}, Lcom/autochips/bluetooth/skin/SkinUtils;->init(Landroid/content/Context;)V

    .line 59
    sput-object p0, Lcom/autochips/bluetooth/MyApplication;->sInstance:Lcom/autochips/bluetooth/MyApplication;

    .line 60
    new-instance v0, Landroid/os/Binder;

    invoke-direct {v0}, Landroid/os/Binder;-><init>()V

    iput-object v0, p0, Lcom/autochips/bluetooth/MyApplication;->mBinder:Landroid/os/IBinder;

    .line 61
    invoke-direct {p0, p0}, Lcom/autochips/bluetooth/MyApplication;->init(Landroid/content/Context;)V

    .line 62
    invoke-static {}, Lcom/autochips/bluetooth/CrashHandler;->getInstance()Lcom/autochips/bluetooth/CrashHandler;

    move-result-object v0

    .line 63
    invoke-virtual {v0, p0}, Lcom/autochips/bluetooth/CrashHandler;->init(Landroid/content/Context;)V

    return-void
.end method

.method public setA2dpSourceRole(Z)V
    .locals 1

    const-string v0, "persist.bluetooth.a2dp.role"

    if-eqz p1, :cond_0

    const-string p1, "source"

    .line 86
    invoke-static {v0, p1}, Lcom/hcn/bluetooth/api/Utils;->setSystemProperty(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    :cond_0
    const-string p1, "sink"

    .line 88
    invoke-static {v0, p1}, Lcom/hcn/bluetooth/api/Utils;->setSystemProperty(Ljava/lang/String;Ljava/lang/String;)V

    :goto_0
    return-void
.end method
