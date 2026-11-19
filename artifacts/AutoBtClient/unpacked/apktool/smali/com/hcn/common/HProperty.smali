.class public Lcom/hcn/common/HProperty;
.super Ljava/lang/Object;
.source "HProperty.java"


# static fields
.field public static D_LOGGABLE:Ljava/lang/String; = "com.hcn.$(module).logd"

.field public static E_LOGGABLE:Ljava/lang/String; = "com.hcn.$(module).loge"

.field public static I_LOGGABLE:Ljava/lang/String; = "com.hcn.$(module).logi"

.field public static V_LOGGABLE:Ljava/lang/String; = "com.hcn.$(module).logv"

.field public static W_LOGGABLE:Ljava/lang/String; = "com.hcn.$(module).logw"


# direct methods
.method static constructor <clinit>()V
    .locals 0

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static init(Ljava/lang/String;)V
    .locals 2

    .line 31
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    .line 35
    sget-object v0, Lcom/hcn/common/HProperty;->V_LOGGABLE:Ljava/lang/String;

    const-string v1, "$(module)"

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/HProperty;->V_LOGGABLE:Ljava/lang/String;

    .line 36
    sget-object v0, Lcom/hcn/common/HProperty;->D_LOGGABLE:Ljava/lang/String;

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/HProperty;->D_LOGGABLE:Ljava/lang/String;

    .line 37
    sget-object v0, Lcom/hcn/common/HProperty;->I_LOGGABLE:Ljava/lang/String;

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/HProperty;->I_LOGGABLE:Ljava/lang/String;

    .line 38
    sget-object v0, Lcom/hcn/common/HProperty;->W_LOGGABLE:Ljava/lang/String;

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/HProperty;->W_LOGGABLE:Ljava/lang/String;

    .line 39
    sget-object v0, Lcom/hcn/common/HProperty;->E_LOGGABLE:Ljava/lang/String;

    invoke-virtual {v0, v1, p0}, Ljava/lang/String;->replace(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;

    move-result-object p0

    sput-object p0, Lcom/hcn/common/HProperty;->E_LOGGABLE:Ljava/lang/String;

    return-void

    .line 32
    :cond_0
    new-instance p0, Ljava/security/InvalidParameterException;

    const-string v0, "HProperty init: \'moduleKey\' is empty!"

    invoke-direct {p0, v0}, Ljava/security/InvalidParameterException;-><init>(Ljava/lang/String;)V

    throw p0
.end method
