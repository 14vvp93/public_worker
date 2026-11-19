.class public Lcom/hcn/skin2/enhance/SkinExCompatResources;
.super Lcom/hcn/skin/support/resources/SkinCompatResources;
.source "SkinExCompatResources.java"


# static fields
.field private static final DEBUG:Z

.field private static final TAG:Ljava/lang/String; = "SkinExCompatResources"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 31
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->isAppDebug()Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin2/enhance/SkinExCompatResources;->DEBUG:Z

    return-void
.end method

.method public constructor <init>()V
    .locals 3

    .line 36
    invoke-direct {p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;-><init>()V

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v1, 0x0

    const-string v2, "Constructor."

    aput-object v2, v0, v1

    const-string v1, "SkinExCompatResources"

    .line 37
    invoke-static {v1, v0}, Lcom/hcn/common/misc/LogUtils;->dTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-void
.end method

.method public static asyncLoadSupportEnable()V
    .locals 3

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v1, 0x0

    const-string v2, "asyncLoadSkinEnable."

    aput-object v2, v0, v1

    const-string v1, "SkinExCompatResources"

    .line 48
    invoke-static {v1, v0}, Lcom/hcn/common/misc/LogUtils;->dTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 49
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/skinx_night/NightSkinX;->setSkinCompatResources(Lcom/hcn/skin/share/ISkinCompatResources;)V

    return-void
.end method


# virtual methods
.method public getIntArray(I)[I
    .locals 6

    .line 323
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 326
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 327
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getIntArray(I)[I

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    const/4 v0, 0x1

    new-array v2, v0, [Ljava/lang/Object;

    const-string v3, "[SA1]This resource is not defined in the main program and cannot be replaced through RRO."

    const/4 v4, 0x0

    aput-object v3, v2, v4

    const-string v3, "SkinExCompatResources"

    .line 330
    invoke-static {v3, v2}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 337
    :try_start_1
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 338
    invoke-static {v2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_0

    return-object v1

    .line 345
    :cond_0
    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getIntArray(I)[I

    move-result-object p1
    :try_end_1
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    return-object p1

    :catch_1
    move-exception p1

    .line 347
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x2

    new-array p1, p1, [Ljava/lang/Object;

    aput-object v3, p1, v4

    const-string v2, "The parameters of method \'getString(int)\' are not supported."

    aput-object v2, p1, v0

    .line 348
    invoke-static {p1}, Lcom/hcn/common/misc/LogUtils;->e([Ljava/lang/Object;)V

    return-object v1

    .line 357
    :cond_1
    :try_start_2
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_2

    .line 359
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getIntArray(I)[I

    move-result-object p1

    goto :goto_0

    .line 360
    :cond_2
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 361
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getIntArray(I)[I

    move-result-object p1
    :try_end_2
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_2 .. :try_end_2} :catch_2

    :goto_0
    return-object p1

    :catch_2
    move-exception p1

    .line 363
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    return-object v1
.end method

.method public getString(I)Ljava/lang/String;
    .locals 1

    .line 65
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    .line 66
    invoke-static {p1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 69
    :cond_0
    invoke-interface {p1}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public getString(IZ)Ljava/lang/String;
    .locals 5

    if-nez p2, :cond_0

    .line 86
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    .line 90
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p2

    invoke-static {p2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result p2

    if-eqz p2, :cond_1

    const/4 p1, 0x0

    return-object p1

    .line 95
    :cond_1
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object p2

    invoke-virtual {p2}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result p2

    if-eqz p2, :cond_4

    .line 98
    :try_start_0
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p2

    invoke-virtual {p2, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object p2

    .line 99
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_3

    const-string v0, "string"

    .line 101
    invoke-virtual {p0, p2, v0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-lez v0, :cond_2

    .line 104
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object p2

    .line 105
    invoke-virtual {p2}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object p2

    invoke-virtual {p2}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p2

    invoke-virtual {p2, v0}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :cond_2
    const-string v0, "SkinExCompatResources"

    const/4 v1, 0x1

    new-array v1, v1, [Ljava/lang/Object;

    const/4 v2, 0x0

    .line 108
    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "[S2]This resource["

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v3, "] is not defined in the main program and cannot be replaced through RRO."

    invoke-virtual {p2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    aput-object p2, v1, v2

    invoke-static {v0, v1}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    goto :goto_0

    .line 112
    :cond_3
    new-instance p2, Lcom/hcn/skin2/runtime/IllegalUseException;

    const-string v0, "method \'getString(int, boolean)\' prohibits external calls."

    invoke-direct {p2, v0}, Lcom/hcn/skin2/runtime/IllegalUseException;-><init>(Ljava/lang/String;)V

    throw p2
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    move-exception p2

    .line 116
    invoke-virtual {p2}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    .line 121
    :cond_4
    :goto_0
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p2

    invoke-virtual {p2, p1}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public getString(Ljava/lang/String;)Ljava/lang/String;
    .locals 1

    .line 137
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getText(Ljava/lang/String;)Ljava/lang/CharSequence;

    move-result-object p1

    .line 138
    invoke-static {p1}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 141
    :cond_0
    invoke-interface {p1}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method public getStringArray(I)[Ljava/lang/String;
    .locals 6

    .line 264
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 267
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 268
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    const/4 v0, 0x1

    new-array v2, v0, [Ljava/lang/Object;

    const-string v3, "[SA1]This resource is not defined in the main program and cannot be replaced through RRO."

    const/4 v4, 0x0

    aput-object v3, v2, v4

    const-string v3, "SkinExCompatResources"

    .line 271
    invoke-static {v3, v2}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 278
    :try_start_1
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 279
    invoke-static {v2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_0

    return-object v1

    .line 286
    :cond_0
    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p1
    :try_end_1
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    return-object p1

    :catch_1
    move-exception p1

    .line 288
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x2

    new-array p1, p1, [Ljava/lang/Object;

    aput-object v3, p1, v4

    const-string v2, "The parameters of method \'getString(int)\' are not supported."

    aput-object v2, p1, v0

    .line 289
    invoke-static {p1}, Lcom/hcn/common/misc/LogUtils;->e([Ljava/lang/Object;)V

    return-object v1

    .line 298
    :cond_1
    :try_start_2
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_2

    .line 300
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p1

    goto :goto_0

    .line 301
    :cond_2
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 302
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getStringArray(I)[Ljava/lang/String;

    move-result-object p1
    :try_end_2
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_2 .. :try_end_2} :catch_2

    :goto_0
    return-object p1

    :catch_2
    move-exception p1

    .line 304
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    return-object v1
.end method

.method public getText(I)Ljava/lang/CharSequence;
    .locals 6

    .line 157
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_1

    .line 160
    :try_start_0
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 161
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    const/4 v0, 0x1

    new-array v2, v0, [Ljava/lang/Object;

    const-string v3, "[S1]This resource is not defined in the main program and cannot be replaced through RRO."

    const/4 v4, 0x0

    aput-object v3, v2, v4

    const-string v3, "SkinExCompatResources"

    .line 164
    invoke-static {v3, v2}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 171
    :try_start_1
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v2

    .line 172
    invoke-static {v2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_0

    return-object v1

    .line 179
    :cond_0
    invoke-virtual {v2, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_1
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_1 .. :try_end_1} :catch_1

    return-object p1

    :catch_1
    move-exception p1

    .line 181
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    const/4 p1, 0x2

    new-array p1, p1, [Ljava/lang/Object;

    aput-object v3, p1, v4

    const-string v2, "The parameters of method \'getString(int)\' are not supported."

    aput-object v2, p1, v0

    .line 182
    invoke-static {p1}, Lcom/hcn/common/misc/LogUtils;->e([Ljava/lang/Object;)V

    return-object v1

    .line 191
    :cond_1
    :try_start_2
    invoke-virtual {p0, p1}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResId(I)I

    move-result v0

    if-lez v0, :cond_2

    .line 193
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    goto :goto_0

    .line 194
    :cond_2
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 195
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_2
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_2 .. :try_end_2} :catch_2

    :goto_0
    return-object p1

    :catch_2
    move-exception p1

    .line 197
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    return-object v1
.end method

.method public getText(Ljava/lang/String;)Ljava/lang/CharSequence;
    .locals 6

    .line 206
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result v0

    const/4 v1, 0x0

    const-string v2, "string"

    if-eqz v0, :cond_2

    .line 208
    invoke-virtual {p0, p1, v2}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-lez v0, :cond_0

    .line 211
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object p1

    .line 212
    invoke-virtual {p1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    return-object p1

    :cond_0
    const/4 v0, 0x1

    new-array v3, v0, [Ljava/lang/Object;

    const-string v4, "[S3]This resource is not defined in the main program and cannot be replaced through RRO."

    const/4 v5, 0x0

    aput-object v4, v3, v5

    const-string v4, "SkinExCompatResources"

    .line 215
    invoke-static {v4, v3}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 220
    invoke-virtual {p0, p1, v2}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    if-lez v2, :cond_1

    .line 222
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v2}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    return-object p1

    :cond_1
    new-array v0, v0, [Ljava/lang/Object;

    .line 226
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "[S3]This resource \'"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v2, "\' definition was not found."

    invoke-virtual {p1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    aput-object p1, v0, v5

    invoke-static {v4, v0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-object v1

    .line 233
    :cond_2
    :try_start_0
    invoke-virtual {p0, p1, v2}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    if-lez v0, :cond_3

    .line 235
    invoke-virtual {p0}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1, v0}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1

    return-object p1

    .line 239
    :cond_3
    invoke-virtual {p0, p1, v2}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getAppResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_4

    .line 241
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v0

    .line 242
    invoke-virtual {v0}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getText(I)Ljava/lang/CharSequence;

    move-result-object p1
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    move-exception p1

    .line 245
    invoke-virtual {p1}, Landroid/content/res/Resources$NotFoundException;->printStackTrace()V

    :cond_4
    return-object v1
.end method
