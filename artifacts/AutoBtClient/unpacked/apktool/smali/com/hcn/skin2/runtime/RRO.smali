.class public Lcom/hcn/skin2/runtime/RRO;
.super Ljava/lang/Object;
.source "RRO.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;
    }
.end annotation


# static fields
.field public static final ACTION_OVERLAY_CHANGED:Ljava/lang/String; = "android.intent.action.OVERLAY_CHANGED"

.field private static final DESCRIPTOR:Ljava/lang/String; = "android.content.om.IOverlayManager"

.field private static final QUERY_MEDIA_ALL_RRO_INFO:Z = true

.field private static final TAG:Ljava/lang/String; = "RRO"

.field private static final TRANSACTION_getOverlayInfo:I = 0x3

.field private static final TRANSACTION_getOverlayInfosForTarget:I = 0x2

.field private static TRANSACTION_setEnabled:I = 0x4

.field private static sInstance:Lcom/hcn/skin2/runtime/RRO;

.field private static sOverlayManager:Landroid/os/IBinder;


# instance fields
.field private mContextRef:Ljava/lang/ref/Reference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/Reference<",
            "Landroid/content/Context;",
            ">;"
        }
    .end annotation
.end field

.field private mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

.field private mRROChangedReceiver:Landroid/content/BroadcastReceiver;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    .line 105
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 84
    iput-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    .line 90
    iput-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    .line 106
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x21

    if-lt v0, v1, :cond_0

    const/4 v0, 0x5

    .line 107
    sput v0, Lcom/hcn/skin2/runtime/RRO;->TRANSACTION_setEnabled:I

    :cond_0
    return-void
.end method

.method static synthetic access$000(Lcom/hcn/skin2/runtime/RRO;)Ljava/lang/ref/Reference;
    .locals 0

    .line 38
    iget-object p0, p0, Lcom/hcn/skin2/runtime/RRO;->mContextRef:Ljava/lang/ref/Reference;

    return-object p0
.end method

.method static synthetic access$100(Lcom/hcn/skin2/runtime/RRO;Landroid/content/Context;)V
    .locals 0

    .line 38
    invoke-direct {p0, p1}, Lcom/hcn/skin2/runtime/RRO;->updateMediaOverlayInfo(Landroid/content/Context;)V

    return-void
.end method

.method private initMediaOverlayInfo(Landroid/content/Context;)V
    .locals 1

    .line 140
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mContextRef:Ljava/lang/ref/Reference;

    .line 143
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 148
    :cond_0
    invoke-direct {p0, p1}, Lcom/hcn/skin2/runtime/RRO;->updateMediaOverlayInfo(Landroid/content/Context;)V

    .line 151
    invoke-direct {p0}, Lcom/hcn/skin2/runtime/RRO;->registerMediaRROReceiver()V

    return-void
.end method

.method public static initOverlayManager(Landroid/content/Context;)V
    .locals 4

    .line 119
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    const/4 v1, 0x0

    const/4 v2, 0x1

    const-string v3, "RRO"

    if-eqz v0, :cond_0

    new-array p0, v2, [Ljava/lang/Object;

    const-string v0, "Repeated call, initialized."

    aput-object v0, p0, v1

    .line 120
    invoke-static {v3, p0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void

    :cond_0
    const-string v0, "overlay"

    .line 124
    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->getSystemService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    sput-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    .line 125
    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    new-array v0, v2, [Ljava/lang/Object;

    const-string v2, "Get system service \'overlay\' failed."

    aput-object v2, v0, v1

    .line 126
    invoke-static {v3, v0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 130
    :cond_1
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-direct {v0, p0}, Lcom/hcn/skin2/runtime/RRO;->initMediaOverlayInfo(Landroid/content/Context;)V

    return-void
.end method

.method public static instance()Lcom/hcn/skin2/runtime/RRO;
    .locals 1

    .line 98
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sInstance:Lcom/hcn/skin2/runtime/RRO;

    if-nez v0, :cond_0

    .line 99
    new-instance v0, Lcom/hcn/skin2/runtime/RRO;

    invoke-direct {v0}, Lcom/hcn/skin2/runtime/RRO;-><init>()V

    sput-object v0, Lcom/hcn/skin2/runtime/RRO;->sInstance:Lcom/hcn/skin2/runtime/RRO;

    .line 101
    :cond_0
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sInstance:Lcom/hcn/skin2/runtime/RRO;

    return-object v0
.end method

.method private registerMediaRROReceiver()V
    .locals 4

    .line 203
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mContextRef:Ljava/lang/ref/Reference;

    invoke-virtual {v0}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    .line 204
    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v1

    const/4 v2, 0x0

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    .line 205
    invoke-static {v1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 206
    new-instance v1, Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;

    invoke-direct {v1, p0}, Lcom/hcn/skin2/runtime/RRO$MediaRROReceiver;-><init>(Lcom/hcn/skin2/runtime/RRO;)V

    iput-object v1, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    .line 213
    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    const-string v3, "android.intent.action.OVERLAY_CHANGED"

    .line 214
    invoke-virtual {v1, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "package"

    .line 215
    invoke-virtual {v1, v3}, Landroid/content/IntentFilter;->addDataScheme(Ljava/lang/String;)V

    .line 216
    invoke-virtual {v0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3, v2}, Landroid/content/IntentFilter;->addDataSchemeSpecificPart(Ljava/lang/String;I)V

    .line 217
    iget-object v2, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v2, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    return-void

    :cond_0
    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const-string v1, "Function registerMediaRROReceiver called repeatedly!"

    aput-object v1, v0, v2

    const-string v1, "RRO"

    .line 208
    invoke-static {v1, v0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void
.end method

.method public static uninitOverlayManager()V
    .locals 1

    .line 135
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-direct {v0}, Lcom/hcn/skin2/runtime/RRO;->unregisterMediaRROReceiver()V

    return-void
.end method

.method private unregisterMediaRROReceiver()V
    .locals 2

    .line 222
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mContextRef:Ljava/lang/ref/Reference;

    invoke-virtual {v0}, Ljava/lang/ref/Reference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    .line 223
    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v1

    if-nez v1, :cond_1

    iget-object v1, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    .line 224
    invoke-static {v1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    .line 228
    :cond_0
    iget-object v1, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {v0, v1}, Landroid/content/Context;->unregisterReceiver(Landroid/content/BroadcastReceiver;)V

    const/4 v0, 0x0

    .line 229
    iput-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mRROChangedReceiver:Landroid/content/BroadcastReceiver;

    :cond_1
    :goto_0
    return-void
.end method

.method private updateMediaOverlayInfo(Landroid/content/Context;)V
    .locals 3

    .line 162
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ".overlay"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    .line 164
    new-instance v1, Ljava/util/ArrayList;

    .line 165
    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p1

    invoke-static {}, Lcom/hcn/auto_compat/app/CompatUtils;->getCurrentUserID()I

    move-result v2

    invoke-virtual {p0, p1, v2}, Lcom/hcn/skin2/runtime/RRO;->getOverlayInfosForTarget(Ljava/lang/String;I)Ljava/util/ArrayList;

    move-result-object p1

    invoke-direct {v1, p1}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V

    .line 166
    invoke-virtual {v1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :cond_0
    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    .line 167
    instance-of v2, v1, Landroid/content/om/OverlayInfo;

    if-nez v2, :cond_1

    goto :goto_0

    .line 171
    :cond_1
    check-cast v1, Landroid/content/om/OverlayInfo;

    .line 172
    invoke-virtual {v1}, Landroid/content/om/OverlayInfo;->isEnabled()Z

    move-result v2

    if-eqz v2, :cond_0

    .line 173
    iget-object v0, v1, Landroid/content/om/OverlayInfo;->packageName:Ljava/lang/String;

    goto :goto_0

    .line 179
    :cond_2
    invoke-static {}, Lcom/hcn/auto_compat/app/CompatUtils;->getCurrentUserID()I

    move-result p1

    invoke-virtual {p0, v0, p1}, Lcom/hcn/skin2/runtime/RRO;->getOverlayInfo(Ljava/lang/String;I)Lcom/hcn/skin2/runtime/OverlayInfo2;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    const/4 p1, 0x1

    new-array p1, p1, [Ljava/lang/Object;

    const/4 v0, 0x0

    .line 180
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "initMediaOverlayInfo: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object v2, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    aput-object v1, p1, v0

    const-string v0, "RRO"

    invoke-static {v0, p1}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void
.end method


# virtual methods
.method public getOverlayInfo(Ljava/lang/String;I)Lcom/hcn/skin2/runtime/OverlayInfo2;
    .locals 4

    .line 318
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 322
    :cond_0
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 323
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v2

    :try_start_0
    const-string v3, "android.content.om.IOverlayManager"

    .line 326
    invoke-virtual {v0, v3}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 327
    invoke-virtual {v0, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 328
    invoke-virtual {v0, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 329
    sget-object p1, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    const/4 p2, 0x3

    const/4 v3, 0x0

    invoke-interface {p1, p2, v0, v2, v3}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    .line 330
    invoke-virtual {v2}, Landroid/os/Parcel;->readException()V

    .line 331
    invoke-virtual {v2}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_1

    .line 332
    new-instance v1, Lcom/hcn/skin2/runtime/OverlayInfo2;

    invoke-direct {v1, v2}, Lcom/hcn/skin2/runtime/OverlayInfo2;-><init>(Landroid/os/Parcel;)V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 339
    :cond_1
    invoke-virtual {v2}, Landroid/os/Parcel;->recycle()V

    .line 340
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    return-object v1

    :catchall_0
    move-exception p1

    goto :goto_0

    :catch_0
    move-exception p1

    .line 337
    :try_start_1
    new-instance p2, Ljava/lang/RuntimeException;

    invoke-direct {p2, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw p2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 339
    :goto_0
    invoke-virtual {v2}, Landroid/os/Parcel;->recycle()V

    .line 340
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 341
    throw p1
.end method

.method public getOverlayInfosForTarget(Ljava/lang/String;I)Ljava/util/ArrayList;
    .locals 3
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            "I)",
            "Ljava/util/ArrayList<",
            "*>;"
        }
    .end annotation

    .line 281
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 285
    :cond_0
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 286
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v1

    :try_start_0
    const-string v2, "android.content.om.IOverlayManager"

    .line 289
    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 290
    invoke-virtual {v0, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 291
    invoke-virtual {v0, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 292
    sget-object p1, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    const/4 p2, 0x2

    const/4 v2, 0x0

    invoke-interface {p1, p2, v0, v1, v2}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    .line 293
    invoke-virtual {v1}, Landroid/os/Parcel;->readException()V

    .line 294
    invoke-virtual {p0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/Class;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object p1

    .line 295
    invoke-virtual {v1, p1}, Landroid/os/Parcel;->readArrayList(Ljava/lang/ClassLoader;)Ljava/util/ArrayList;

    move-result-object p1
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 299
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 300
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    return-object p1

    :catchall_0
    move-exception p1

    goto :goto_0

    :catch_0
    move-exception p1

    .line 297
    :try_start_1
    new-instance p2, Ljava/lang/RuntimeException;

    invoke-direct {p2, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw p2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 299
    :goto_0
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 300
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 301
    throw p1
.end method

.method public isEnabled()Z
    .locals 1

    .line 185
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return v0

    .line 189
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/OverlayInfo2;->isEnabled()Z

    move-result v0

    return v0
.end method

.method public overlayPackageName()Ljava/lang/String;
    .locals 1

    .line 194
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 198
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin2/runtime/RRO;->mOverlayInfo:Lcom/hcn/skin2/runtime/OverlayInfo2;

    iget-object v0, v0, Lcom/hcn/skin2/runtime/OverlayInfo2;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method public setEnabled(Ljava/lang/String;ZI)Z
    .locals 4

    .line 370
    sget-object v0, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return v1

    .line 374
    :cond_0
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 375
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v2

    :try_start_0
    const-string v3, "android.content.om.IOverlayManager"

    .line 378
    invoke-virtual {v0, v3}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 379
    invoke-virtual {v0, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    const/4 p1, 0x1

    if-eqz p2, :cond_1

    move p2, p1

    goto :goto_0

    :cond_1
    move p2, v1

    .line 380
    :goto_0
    invoke-virtual {v0, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 381
    invoke-virtual {v0, p3}, Landroid/os/Parcel;->writeInt(I)V

    .line 382
    sget-object p2, Lcom/hcn/skin2/runtime/RRO;->sOverlayManager:Landroid/os/IBinder;

    sget p3, Lcom/hcn/skin2/runtime/RRO;->TRANSACTION_setEnabled:I

    invoke-interface {p2, p3, v0, v2, v1}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    .line 383
    invoke-virtual {v2}, Landroid/os/Parcel;->readException()V

    .line 384
    invoke-virtual {v2}, Landroid/os/Parcel;->readInt()I

    move-result p2
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz p2, :cond_2

    move v1, p1

    .line 388
    :cond_2
    invoke-virtual {v2}, Landroid/os/Parcel;->recycle()V

    .line 389
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    return v1

    :catchall_0
    move-exception p1

    goto :goto_1

    :catch_0
    move-exception p1

    .line 386
    :try_start_1
    new-instance p2, Ljava/lang/RuntimeException;

    invoke-direct {p2, p1}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    throw p2
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 388
    :goto_1
    invoke-virtual {v2}, Landroid/os/Parcel;->recycle()V

    .line 389
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 390
    throw p1
.end method
