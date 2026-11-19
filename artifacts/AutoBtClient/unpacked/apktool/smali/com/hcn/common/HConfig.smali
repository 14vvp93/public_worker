.class public Lcom/hcn/common/HConfig;
.super Ljava/lang/Object;
.source "HConfig.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/HConfig$HLogAdapter;
    }
.end annotation


# static fields
.field private static D_LOGGABLE:Z = false

.field private static E_LOGGABLE:Z = false

.field public static final INTENT_REASON_EXTRA_KEY:Ljava/lang/String; = "intent_reason"

.field private static I_LOGGABLE:Z = false

.field public static final REASON_ACTIVITY_CREATE:Ljava/lang/String; = "activity_created"

.field public static final REASON_APPLICATION_CREATE:Ljava/lang/String; = "application_created"

.field public static final REASON_FRAGMENT_CREATE:Ljava/lang/String; = "fragment_created"

.field public static final START_REASON_EXTRA_KEY:Ljava/lang/String; = "start_reason"

.field private static V_LOGGABLE:Z = false

.field private static W_LOGGABLE:Z = false

.field private static sConfigBroadcastReceiver:Landroid/content/BroadcastReceiver;

.field private static sIsDebug:Z


# direct methods
.method static bridge synthetic -$$Nest$sfgetD_LOGGABLE()Z
    .locals 1

    sget-boolean v0, Lcom/hcn/common/HConfig;->D_LOGGABLE:Z

    return v0
.end method

.method static bridge synthetic -$$Nest$sfgetE_LOGGABLE()Z
    .locals 1

    sget-boolean v0, Lcom/hcn/common/HConfig;->E_LOGGABLE:Z

    return v0
.end method

.method static bridge synthetic -$$Nest$sfgetI_LOGGABLE()Z
    .locals 1

    sget-boolean v0, Lcom/hcn/common/HConfig;->I_LOGGABLE:Z

    return v0
.end method

.method static bridge synthetic -$$Nest$sfgetV_LOGGABLE()Z
    .locals 1

    sget-boolean v0, Lcom/hcn/common/HConfig;->V_LOGGABLE:Z

    return v0
.end method

.method static bridge synthetic -$$Nest$sfgetW_LOGGABLE()Z
    .locals 1

    sget-boolean v0, Lcom/hcn/common/HConfig;->W_LOGGABLE:Z

    return v0
.end method

.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 22
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static initConfig(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    .line 76
    invoke-static {p0}, Lcom/hcn/common/utils/HUtilsEx;->isAppDebug(Landroid/content/Context;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->sIsDebug:Z

    .line 78
    invoke-static {p1}, Lcom/hcn/common/HProperty;->init(Ljava/lang/String;)V

    .line 79
    invoke-static {p1}, Lcom/hcn/common/HBroadcast;->init(Ljava/lang/String;)V

    const/4 p1, 0x1

    .line 81
    invoke-static {p1}, Lcom/hcn/common/HConfig;->loggerConfig(Z)V

    .line 82
    invoke-static {p0, p2}, Lcom/hcn/common/HConfig;->initDebugConfig(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method

.method public static initDebugConfig(Landroid/content/Context;Ljava/lang/String;)V
    .locals 2

    .line 123
    invoke-static {}, Lcom/orhanobut/logger/PrettyFormatStrategy;->newBuilder()Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object v0

    const/4 v1, 0x0

    .line 124
    invoke-virtual {v0, v1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->showThreadInfo(Z)Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object v0

    .line 125
    invoke-virtual {v0, v1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->methodCount(I)Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object v0

    const/4 v1, 0x5

    .line 126
    invoke-virtual {v0, v1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->methodOffset(I)Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object v0

    new-instance v1, Lcom/orhanobut/logger/LogcatLogStrategy;

    invoke-direct {v1}, Lcom/orhanobut/logger/LogcatLogStrategy;-><init>()V

    .line 127
    invoke-virtual {v0, v1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->logStrategy(Lcom/orhanobut/logger/LogStrategy;)Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object v0

    .line 128
    invoke-virtual {v0, p1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->tag(Ljava/lang/String;)Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;

    move-result-object p1

    invoke-virtual {p1}, Lcom/orhanobut/logger/PrettyFormatStrategy$Builder;->build()Lcom/orhanobut/logger/PrettyFormatStrategy;

    move-result-object p1

    .line 130
    new-instance v0, Lcom/hcn/common/HConfig$HLogAdapter;

    invoke-direct {v0, p1}, Lcom/hcn/common/HConfig$HLogAdapter;-><init>(Lcom/orhanobut/logger/FormatStrategy;)V

    .line 131
    invoke-static {v0}, Lcom/orhanobut/logger/Logger;->addLogAdapter(Lcom/orhanobut/logger/LogAdapter;)V

    if-eqz p0, :cond_1

    .line 134
    sget-object p1, Lcom/hcn/common/HConfig;->sConfigBroadcastReceiver:Landroid/content/BroadcastReceiver;

    if-nez p1, :cond_0

    .line 135
    new-instance p1, Lcom/hcn/common/HBroadcast;

    invoke-direct {p1}, Lcom/hcn/common/HBroadcast;-><init>()V

    sput-object p1, Lcom/hcn/common/HConfig;->sConfigBroadcastReceiver:Landroid/content/BroadcastReceiver;

    .line 138
    :cond_0
    new-instance p1, Landroid/content/IntentFilter;

    invoke-direct {p1}, Landroid/content/IntentFilter;-><init>()V

    .line 139
    sget-object v0, Lcom/hcn/common/HBroadcast;->DEBUG_CONFIG_ACTION:Ljava/lang/String;

    invoke-virtual {p1, v0}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    .line 140
    sget-object v0, Lcom/hcn/common/HConfig;->sConfigBroadcastReceiver:Landroid/content/BroadcastReceiver;

    invoke-virtual {p0, v0, p1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    :cond_1
    return-void
.end method

.method public static isDebug()Z
    .locals 1

    .line 59
    sget-boolean v0, Lcom/hcn/common/HConfig;->sIsDebug:Z

    return v0
.end method

.method public static loggerConfig(Z)V
    .locals 3

    .line 101
    sget-object v0, Lcom/hcn/common/HProperty;->V_LOGGABLE:Ljava/lang/String;

    const-string v1, "0"

    .line 102
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v2, "1"

    .line 101
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->V_LOGGABLE:Z

    .line 103
    sget-object v0, Lcom/hcn/common/HProperty;->D_LOGGABLE:Ljava/lang/String;

    .line 104
    invoke-static {v0, v2}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 103
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->D_LOGGABLE:Z

    .line 105
    sget-object v0, Lcom/hcn/common/HProperty;->I_LOGGABLE:Ljava/lang/String;

    .line 106
    invoke-static {v0, v1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 105
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->I_LOGGABLE:Z

    .line 107
    sget-object v0, Lcom/hcn/common/HProperty;->W_LOGGABLE:Ljava/lang/String;

    .line 108
    invoke-static {v0, v2}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 107
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->W_LOGGABLE:Z

    .line 109
    sget-object v0, Lcom/hcn/common/HProperty;->E_LOGGABLE:Ljava/lang/String;

    .line 110
    invoke-static {v0, v2}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 109
    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/common/HConfig;->E_LOGGABLE:Z

    if-eqz p0, :cond_0

    .line 112
    invoke-static {}, Lcom/hcn/common/HConfig;->isDebug()Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    .line 113
    sput-boolean p0, Lcom/hcn/common/HConfig;->V_LOGGABLE:Z

    :cond_0
    return-void
.end method
