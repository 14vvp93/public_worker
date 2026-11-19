.class public Lcom/hcn/skin/support/utils/SkinSharedPreferences;
.super Ljava/lang/Object;
.source "SkinSharedPreferences.java"


# static fields
.field private static sInstance:Lcom/hcn/skin/support/utils/SkinSharedPreferences;


# instance fields
.field private final mContext:Landroid/content/Context;

.field private final mEditor:Landroid/content/SharedPreferences$Editor;

.field private final mSharedPreferences:Landroid/content/SharedPreferences;


# direct methods
.method private constructor <init>(Landroid/content/Context;)V
    .locals 3
    .param p1, "applicationContext"    # Landroid/content/Context;

    .line 37
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 38
    iput-object p1, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mContext:Landroid/content/Context;

    .line 39
    iget-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mContext:Landroid/content/Context;

    const-string v1, "meta-data"

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mSharedPreferences:Landroid/content/SharedPreferences;

    .line 40
    iget-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mSharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mEditor:Landroid/content/SharedPreferences$Editor;

    .line 41
    return-void
.end method

.method public static getInstance()Lcom/hcn/skin/support/utils/SkinSharedPreferences;
    .locals 1

    .line 34
    sget-object v0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->sInstance:Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    return-object v0
.end method

.method public static init(Landroid/content/Context;)V
    .locals 3
    .param p0, "context"    # Landroid/content/Context;

    .line 24
    sget-object v0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->sInstance:Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    if-nez v0, :cond_1

    .line 25
    const-class v0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    monitor-enter v0

    .line 26
    :try_start_0
    sget-object v1, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->sInstance:Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    if-nez v1, :cond_0

    .line 27
    new-instance v1, Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v2

    invoke-direct {v1, v2}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;-><init>(Landroid/content/Context;)V

    sput-object v1, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->sInstance:Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    .line 29
    :cond_0
    monitor-exit v0

    goto :goto_0

    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1

    .line 31
    :cond_1
    :goto_0
    return-void
.end method


# virtual methods
.method public commitEditor()V
    .locals 1

    .line 53
    iget-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mEditor:Landroid/content/SharedPreferences$Editor;

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 54
    return-void
.end method

.method public setSkinName(Ljava/lang/String;)Lcom/hcn/skin/support/utils/SkinSharedPreferences;
    .locals 2
    .param p1, "skinName"    # Ljava/lang/String;

    .line 44
    iget-object v0, p0, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->mEditor:Landroid/content/SharedPreferences$Editor;

    const-string v1, "skin-name"

    invoke-interface {v0, v1, p1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 45
    return-object p0
.end method
