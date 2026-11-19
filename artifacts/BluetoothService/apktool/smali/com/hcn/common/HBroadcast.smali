.class public Lcom/hcn/common/HBroadcast;
.super Landroid/content/BroadcastReceiver;
.source "HBroadcast.java"


# static fields
.field public static DEBUG_CONFIG_ACTION:Ljava/lang/String; = "com.hcn.$(module).config.action"

.field public static final DEBUG_CONFIG_EXTRA_INFO_LOGGER:Ljava/lang/String; = "Logger"

.field public static final DEBUG_CONFIG_EXTRA_KEY:Ljava/lang/String; = "config.extra.key"


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 15
    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method

.method public static init(Ljava/lang/String;)V
    .locals 2

    .line 37
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 41
    sget-object v0, Lcom/hcn/common/HBroadcast;->DEBUG_CONFIG_ACTION:Ljava/lang/String;

    const-string v1, "$(module)"

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p0

    sput-object p0, Lcom/hcn/common/HBroadcast;->DEBUG_CONFIG_ACTION:Ljava/lang/String;

    return-void

    .line 38
    :cond_0
    new-instance p0, Ljava/security/InvalidParameterException;

    const-string v0, "HBroadcast init: \'moduleKey\' is empty!"

    invoke-direct {p0, v0}, Ljava/security/InvalidParameterException;-><init>(Ljava/lang/String;)V

    throw p0
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    if-nez p2, :cond_0

    return-void

    .line 50
    :cond_0
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    .line 51
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_1

    return-void

    .line 55
    :cond_1
    sget-object v0, Lcom/hcn/common/HBroadcast;->DEBUG_CONFIG_ACTION:Ljava/lang/String;

    invoke-virtual {v0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    const-string p1, "config.extra.key"

    .line 56
    invoke-virtual {p2, p1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 57
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-eqz p2, :cond_2

    return-void

    :cond_2
    const-string p2, "Logger"

    .line 61
    invoke-virtual {p2, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_3

    const/4 p1, 0x0

    .line 62
    invoke-static {p1}, Lcom/hcn/common/HConfig;->loggerConfig(Z)V

    :cond_3
    return-void
.end method
