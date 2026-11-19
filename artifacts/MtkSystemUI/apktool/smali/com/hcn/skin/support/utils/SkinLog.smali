.class public Lcom/hcn/skin/support/utils/SkinLog;
.super Ljava/lang/Object;
.source "SkinLog.java"


# static fields
.field private static D_LOGGABLE:Z

.field private static E_LOGGABLE:Z

.field private static I_LOGGABLE:Z

.field private static V_LOGGABLE:Z

.field private static W_LOGGABLE:Z


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 33
    const/4 v0, 0x0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->V_LOGGABLE:Z

    .line 34
    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->D_LOGGABLE:Z

    .line 35
    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->I_LOGGABLE:Z

    .line 36
    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->W_LOGGABLE:Z

    .line 37
    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->E_LOGGABLE:Z

    return-void
.end method

.method public static d(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p0, "subtag"    # Ljava/lang/String;
    .param p1, "msg"    # Ljava/lang/String;

    .line 120
    sget-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->D_LOGGABLE:Z

    if-eqz v0, :cond_0

    .line 121
    const-string v0, "skin-support"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ": "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 124
    :cond_0
    return-void
.end method

.method public static e(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p0, "subtag"    # Ljava/lang/String;
    .param p1, "msg"    # Ljava/lang/String;

    .line 159
    sget-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->E_LOGGABLE:Z

    if-eqz v0, :cond_0

    .line 160
    const-string v0, "skin-support"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ": "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 163
    :cond_0
    return-void
.end method

.method public static i(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p0, "subtag"    # Ljava/lang/String;
    .param p1, "msg"    # Ljava/lang/String;

    .line 133
    sget-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->I_LOGGABLE:Z

    if-eqz v0, :cond_0

    .line 134
    const-string v0, "skin-support"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ": "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 137
    :cond_0
    return-void
.end method

.method public static init()V
    .locals 0

    .line 97
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinLog;->loggerConfig()V

    .line 98
    return-void
.end method

.method public static loggerConfig()V
    .locals 3

    .line 43
    const-string v0, "1"

    const-string v1, "persist.sys.skin-support.logv"

    const-string v2, "0"

    .line 44
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinSystemUtils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 43
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->V_LOGGABLE:Z

    .line 45
    const-string v0, "1"

    const-string v1, "persist.sys.skin-support.logd"

    const-string v2, "0"

    .line 46
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinSystemUtils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 45
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->D_LOGGABLE:Z

    .line 47
    const-string v0, "1"

    const-string v1, "persist.sys.skin-support.logi"

    const-string v2, "0"

    .line 48
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinSystemUtils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 47
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->I_LOGGABLE:Z

    .line 49
    const-string v0, "1"

    const-string v1, "persist.sys.skin-support.logw"

    const-string v2, "1"

    .line 50
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinSystemUtils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 49
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->W_LOGGABLE:Z

    .line 51
    const-string v0, "1"

    const-string v1, "persist.sys.skin-support.loge"

    const-string v2, "1"

    .line 52
    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinSystemUtils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 51
    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->E_LOGGABLE:Z

    .line 53
    return-void
.end method

.method public static v(Ljava/lang/String;Ljava/lang/String;)V
    .locals 3
    .param p0, "subtag"    # Ljava/lang/String;
    .param p1, "msg"    # Ljava/lang/String;

    .line 107
    sget-boolean v0, Lcom/hcn/skin/support/utils/SkinLog;->V_LOGGABLE:Z

    if-eqz v0, :cond_0

    .line 108
    const-string v0, "skin-support"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ": "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->v(Ljava/lang/String;Ljava/lang/String;)I

    .line 111
    :cond_0
    return-void
.end method
