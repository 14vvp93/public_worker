.class public Lcom/hcn/auto_compat/app/NotificationUtils;
.super Ljava/lang/Object;
.source "NotificationUtils.java"


# static fields
.field private static final DESCRIPTOR:Ljava/lang/String; = "android.app.INotificationManager"

.field private static final TAG:Ljava/lang/String; = "NotificationUtils"

.field private static final TRANSACTION_cancelNotificationWithTag:I = 0x7

.field private static sService:Landroid/os/IBinder;


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 18
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static cancelAsPackage(Ljava/lang/String;Ljava/lang/String;II)V
    .locals 3

    .line 64
    sget-object v0, Lcom/hcn/auto_compat/app/NotificationUtils;->sService:Landroid/os/IBinder;

    if-nez v0, :cond_0

    .line 65
    invoke-static {}, Lcom/hcn/auto_compat/app/NotificationUtils;->init()V

    .line 68
    :cond_0
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v0

    .line 69
    invoke-static {}, Landroid/os/Parcel;->obtain()Landroid/os/Parcel;

    move-result-object v1

    :try_start_0
    const-string v2, "android.app.INotificationManager"

    .line 72
    invoke-virtual {v0, v2}, Landroid/os/Parcel;->writeInterfaceToken(Ljava/lang/String;)V

    .line 73
    invoke-virtual {v0, p0}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 74
    invoke-virtual {v0, p1}, Landroid/os/Parcel;->writeString(Ljava/lang/String;)V

    .line 75
    invoke-virtual {v0, p2}, Landroid/os/Parcel;->writeInt(I)V

    .line 76
    invoke-virtual {v0, p3}, Landroid/os/Parcel;->writeInt(I)V

    .line 77
    sget-object p0, Lcom/hcn/auto_compat/app/NotificationUtils;->sService:Landroid/os/IBinder;

    const/4 p1, 0x7

    const/4 p2, 0x0

    invoke-interface {p0, p1, v0, v1, p2}, Landroid/os/IBinder;->transact(ILandroid/os/Parcel;Landroid/os/Parcel;I)Z

    .line 78
    invoke-virtual {v1}, Landroid/os/Parcel;->readException()V
    :try_end_0
    .catch Landroid/os/RemoteException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    goto :goto_0

    :catchall_0
    move-exception p0

    .line 81
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 82
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    .line 83
    throw p0

    .line 81
    :catch_0
    :goto_0
    invoke-virtual {v1}, Landroid/os/Parcel;->recycle()V

    .line 82
    invoke-virtual {v0}, Landroid/os/Parcel;->recycle()V

    return-void
.end method

.method public static getIdentifier(Landroid/os/UserHandle;)I
    .locals 6

    .line 94
    invoke-virtual {p0}, Landroid/os/UserHandle;->hashCode()I

    move-result v0

    const/4 v1, 0x0

    :try_start_0
    const-string v2, "android.os.UserHandle"

    .line 97
    invoke-static {v2}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v2

    const-string v3, "getIdentifier"

    new-array v4, v1, [Ljava/lang/Class;

    .line 98
    invoke-virtual {v2, v3, v4}, Ljava/lang/Class;->getMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v2

    new-array v3, v1, [Ljava/lang/Object;

    .line 99
    invoke-virtual {v2, p0, v3}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Ljava/lang/Integer;

    invoke-virtual {p0}, Ljava/lang/Integer;->intValue()I

    move-result v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    move-exception p0

    .line 101
    sget-object v2, Lcom/hcn/auto_compat/app/NotificationUtils;->TAG:Ljava/lang/String;

    const/4 v3, 0x1

    new-array v3, v3, [Ljava/lang/Object;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "getIdentifier: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {p0}, Ljava/lang/Exception;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v3, v1

    invoke-static {v2, v3}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    :goto_0
    return v0
.end method

.method public static init()V
    .locals 4

    .line 39
    sget-object v0, Lcom/hcn/auto_compat/app/NotificationUtils;->sService:Landroid/os/IBinder;

    const/4 v1, 0x0

    const/4 v2, 0x1

    if-eqz v0, :cond_0

    .line 40
    sget-object v0, Lcom/hcn/auto_compat/app/NotificationUtils;->TAG:Ljava/lang/String;

    new-array v2, v2, [Ljava/lang/Object;

    const-string v3, "Repeated call, initialized."

    aput-object v3, v2, v1

    invoke-static {v0, v2}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void

    :cond_0
    const-string v0, "notification"

    .line 44
    invoke-static {v0}, Lcom/hcn/common/utils/HUtilsEx;->getSystemService(Ljava/lang/String;)Landroid/os/IBinder;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto_compat/app/NotificationUtils;->sService:Landroid/os/IBinder;

    .line 45
    invoke-static {v0}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 46
    sget-object v0, Lcom/hcn/auto_compat/app/NotificationUtils;->TAG:Ljava/lang/String;

    new-array v2, v2, [Ljava/lang/Object;

    const-string v3, "Get system service \'notification\' failed."

    aput-object v3, v2, v1

    invoke-static {v0, v2}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_1
    return-void
.end method
