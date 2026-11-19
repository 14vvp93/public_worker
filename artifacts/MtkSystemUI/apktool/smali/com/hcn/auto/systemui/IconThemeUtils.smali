.class public Lcom/hcn/auto/systemui/IconThemeUtils;
.super Ljava/lang/Object;
.source "IconThemeUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String;

.field private static appInfoList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 24
    const-class v0, Lcom/hcn/auto/systemui/IconThemeUtils;

    invoke-virtual {v0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 23
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getCustomAppIconBitmap(Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 4
    .param p0, "packageName"    # Ljava/lang/String;
    .param p1, "className"    # Ljava/lang/String;

    .line 225
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getCustomAppIconBitmap: packageName = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p0}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " className = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p1}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 226
    invoke-static {p0}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 227
    return-object v1

    .line 230
    :cond_0
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 231
    invoke-static {}, Lcom/hcn/auto/systemui/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 234
    :cond_1
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_2

    .line 235
    return-object v1

    .line 239
    :cond_2
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v0, v2, :cond_5

    .line 240
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 241
    .local v2, "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 244
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 245
    goto :goto_1

    .line 248
    :cond_3
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_4

    .line 250
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->getIconBackgroundName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 239
    .end local v2    # "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    :cond_4
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 254
    .end local v0    # "i":I
    :cond_5
    return-object v1
.end method

.method public static getCustomAppIconBitmap(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 4
    .param p0, "packageName"    # Ljava/lang/String;
    .param p1, "className"    # Ljava/lang/String;
    .param p2, "xmlName"    # Ljava/lang/String;

    .line 258
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getCustomAppIconBitmap: packageName = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p0}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " className = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-static {p1}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v2

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 259
    invoke-static {p0}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 260
    return-object v1

    .line 263
    :cond_0
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 264
    invoke-static {p2}, Lcom/hcn/auto/systemui/IconThemeUtils;->parseIconThemeFromXml(Ljava/lang/String;)Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 267
    :cond_1
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_2

    .line 268
    return-object v1

    .line 272
    :cond_2
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v0, v2, :cond_5

    .line 273
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 274
    .local v2, "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 277
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 278
    goto :goto_1

    .line 281
    :cond_3
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_4

    .line 283
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->getIconBackgroundName(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v3}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getBitmap(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    return-object v1

    .line 272
    .end local v2    # "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    :cond_4
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 287
    .end local v0    # "i":I
    :cond_5
    return-object v1
.end method

.method public static getCustomAppIconName(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 4
    .param p0, "packageName"    # Ljava/lang/String;
    .param p1, "className"    # Ljava/lang/String;

    .line 353
    invoke-static {p0}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 354
    return-object v1

    .line 357
    :cond_0
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 358
    invoke-static {}, Lcom/hcn/auto/systemui/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 361
    :cond_1
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_2

    .line 362
    return-object v1

    .line 366
    :cond_2
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v0, v2, :cond_5

    .line 367
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    .line 368
    .local v2, "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 371
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 372
    goto :goto_1

    .line 375
    :cond_3
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_4

    .line 376
    invoke-static {v2}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v1

    return-object v1

    .line 366
    .end local v2    # "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    :cond_4
    :goto_1
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 380
    .end local v0    # "i":I
    :cond_5
    return-object v1
.end method

.method public static getIconBackgroundName(Ljava/lang/String;)Ljava/lang/String;
    .locals 5
    .param p0, "iconName"    # Ljava/lang/String;

    .line 324
    sget-object v0, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "getIconBackgroundName: iconName = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 325
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    if-nez v0, :cond_0

    .line 326
    return-object p0

    .line 328
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    const-string v1, "support_color_theme"

    invoke-virtual {v0, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInteger(Ljava/lang/String;)I

    move-result v0

    .line 329
    .local v0, "support_color_theme":I
    if-nez v0, :cond_1

    .line 330
    return-object p0

    .line 332
    :cond_1
    invoke-static {}, Lcom/hcn/skin/support/SkinCompatManager;->getInstance()Lcom/hcn/skin/support/SkinCompatManager;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->getContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v1

    const-string v2, "ui_style"

    invoke-static {v1, v2}, Landroid/provider/Settings$System;->getString(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 333
    .local v1, "uiStyle":Ljava/lang/String;
    invoke-static {v1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 334
    const-string v1, ""

    .line 336
    :cond_2
    const-string v2, ""

    invoke-virtual {v2, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    move-object v2, p0

    goto :goto_0

    :cond_3
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "_"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    .line 338
    .local v2, "resName":Ljava/lang/String;
    :goto_0
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v3

    const-string v4, "drawable"

    invoke-virtual {v3, v2, v4}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result v3

    .line 339
    .local v3, "resId":I
    if-lez v3, :cond_4

    .line 340
    return-object v2

    .line 342
    :cond_4
    return-object p0
.end method

.method static isTrimEmpty(Ljava/lang/String;)Z
    .locals 1
    .param p0, "s"    # Ljava/lang/String;

    .line 386
    if-eqz p0, :cond_1

    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->length()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 v0, 0x1

    :goto_1
    return v0
.end method

.method private static parseIconThemeFromXml()Ljava/util/List;
    .locals 14
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation

    .line 29
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const/4 v1, 0x1

    if-nez v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 30
    .local v0, "getCurrentSkin":Z
    :goto_0
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "parseIconThemeFromXml: getCurrentSkin = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 31
    const/4 v2, 0x0

    if-eqz v0, :cond_1

    .line 32
    return-object v2

    .line 34
    :cond_1
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    .line 35
    .local v3, "appInfoList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;>;"
    const/4 v4, 0x0

    .line 39
    .local v4, "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    const-string/jumbo v5, "xt617"

    const-string v6, "persist.sys.skin"

    const-string v7, ""

    invoke-static {v6, v7}, Lcom/android/systemui/util/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_2

    .line 40
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string/jumbo v6, "xt617_icon_themes"

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v5

    .line 40
    .local v5, "xmlParser":Landroid/content/res/XmlResourceParser;
    :goto_1
    goto :goto_2

    .line 41
    .end local v5    # "xmlParser":Landroid/content/res/XmlResourceParser;
    :cond_2
    const-string/jumbo v5, "xt618"

    const-string v6, "persist.sys.skin"

    const-string v7, ""

    invoke-static {v6, v7}, Lcom/android/systemui/util/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_3

    .line 42
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string/jumbo v6, "xt617_icon_themes"

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v5

    goto :goto_1

    .line 43
    :cond_3
    const-string v5, "sa266"

    const-string v6, "persist.sys.skin"

    const-string v7, ""

    invoke-static {v6, v7}, Lcom/android/systemui/util/Utils;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v5, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v5

    if-eqz v5, :cond_4

    .line 44
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string v6, "sa266_icon_themes"

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v5

    goto :goto_1

    .line 46
    :cond_4
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    const-string/jumbo v6, "xt617_icon_themes"

    invoke-virtual {v5, v6}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v5

    .line 49
    .restart local v5    # "xmlParser":Landroid/content/res/XmlResourceParser;
    :goto_2
    :try_start_0
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v6

    .line 52
    .local v6, "event":I
    :goto_3
    if-eq v6, v1, :cond_b

    .line 54
    if-eqz v6, :cond_9

    packed-switch v6, :pswitch_data_0

    goto/16 :goto_8

    .line 74
    :pswitch_0
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v7

    .line 75
    .local v7, "iconName":Ljava/lang/String;
    sget-object v8, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "parseIconThemeFromXml: iconName = "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 76
    if-nez v7, :cond_5

    const-string v8, ""

    goto :goto_4

    :cond_5
    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v8

    :goto_4
    invoke-virtual {v4, v8}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    .line 77
    goto/16 :goto_8

    .line 79
    .end local v7    # "iconName":Ljava/lang/String;
    :pswitch_1
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v7

    const-string v8, "app"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_a

    .line 81
    invoke-virtual {v4}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->getPackageName()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_a

    .line 82
    invoke-interface {v3, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_8

    .line 58
    :pswitch_2
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v7

    const-string v8, "app"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_a

    .line 59
    const-string v7, "packageName"

    invoke-interface {v5, v2, v7}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 60
    .local v7, "packageName":Ljava/lang/String;
    const-string v8, "className"

    invoke-interface {v5, v2, v8}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 61
    .local v8, "className":Ljava/lang/String;
    const-string v9, "hidden"

    invoke-interface {v5, v2, v9}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    .line 63
    .local v9, "hidden":Ljava/lang/String;
    new-instance v10, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    invoke-direct {v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;-><init>()V

    move-object v4, v10

    .line 64
    if-nez v7, :cond_6

    const-string v10, ""

    goto :goto_5

    :cond_6
    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v10

    :goto_5
    invoke-virtual {v4, v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setPackageName(Ljava/lang/String;)V

    .line 65
    if-nez v8, :cond_7

    const-string v10, ""

    goto :goto_6

    :cond_7
    invoke-virtual {v8}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v10

    :goto_6
    invoke-virtual {v4, v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setClassName(Ljava/lang/String;)V

    .line 66
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v10

    .line 67
    .local v10, "iconName":Ljava/lang/String;
    sget-object v11, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "parseIconThemeFromXml: iconName = "

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v12, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 68
    if-nez v10, :cond_8

    const-string v11, ""

    goto :goto_7

    :cond_8
    invoke-virtual {v10}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    :goto_7
    invoke-virtual {v4, v11}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    .line 69
    const-string v11, "true"

    invoke-virtual {v11, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    invoke-virtual {v4, v11}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setHidden(Z)V

    goto :goto_8

    .line 56
    .end local v7    # "packageName":Ljava/lang/String;
    .end local v8    # "className":Ljava/lang/String;
    .end local v9    # "hidden":Ljava/lang/String;
    .end local v10    # "iconName":Ljava/lang/String;
    :cond_9
    nop

    .line 89
    :cond_a
    :goto_8
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v7
    :try_end_0
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v6, v7

    goto/16 :goto_3

    .line 95
    .end local v6    # "event":I
    :catch_0
    move-exception v1

    .line 96
    .local v1, "e":Ljava/lang/Exception;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 96
    .end local v1    # "e":Ljava/lang/Exception;
    goto :goto_a

    .line 93
    :catch_1
    move-exception v1

    .line 94
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 94
    .end local v1    # "e":Ljava/io/IOException;
    goto :goto_9

    .line 91
    :catch_2
    move-exception v1

    .line 92
    .local v1, "e":Lorg/xmlpull/v1/XmlPullParserException;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 97
    .end local v1    # "e":Lorg/xmlpull/v1/XmlPullParserException;
    :cond_b
    :goto_9
    nop

    .line 98
    :goto_a
    return-object v3

    nop

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private static parseIconThemeFromXml(Ljava/lang/String;)Ljava/util/List;
    .locals 14
    .param p0, "xmlName"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation

    .line 102
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    const/4 v1, 0x1

    if-nez v0, :cond_0

    move v0, v1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    .line 103
    .local v0, "getCurrentSkin":Z
    :goto_0
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "parseIconThemeFromXml: getCurrentSkin = "

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 104
    const/4 v2, 0x0

    if-eqz v0, :cond_1

    .line 105
    return-object v2

    .line 107
    :cond_1
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    .line 108
    .local v3, "appInfoList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;>;"
    const/4 v4, 0x0

    .line 110
    .local v4, "appInfo":Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v5

    invoke-virtual {v5, p0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v5

    .line 112
    .local v5, "xmlParser":Landroid/content/res/XmlResourceParser;
    :try_start_0
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v6

    .line 115
    .local v6, "event":I
    :goto_1
    if-eq v6, v1, :cond_8

    .line 117
    if-eqz v6, :cond_6

    packed-switch v6, :pswitch_data_0

    goto/16 :goto_6

    .line 137
    :pswitch_0
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v7

    .line 138
    .local v7, "iconName":Ljava/lang/String;
    sget-object v8, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v10, "parseIconThemeFromXml: second iconName = "

    invoke-virtual {v9, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-static {v8, v9}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 139
    if-nez v7, :cond_2

    const-string v8, ""

    goto :goto_2

    :cond_2
    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v8

    :goto_2
    invoke-virtual {v4, v8}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    .line 140
    goto/16 :goto_6

    .line 142
    .end local v7    # "iconName":Ljava/lang/String;
    :pswitch_1
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v7

    const-string v8, "app"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_7

    .line 144
    invoke-virtual {v4}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->getPackageName()Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Lcom/hcn/auto/systemui/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_7

    .line 145
    invoke-interface {v3, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_6

    .line 121
    :pswitch_2
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v7

    const-string v8, "app"

    invoke-virtual {v7, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_7

    .line 122
    const-string v7, "packageName"

    invoke-interface {v5, v2, v7}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v7

    .line 123
    .local v7, "packageName":Ljava/lang/String;
    const-string v8, "className"

    invoke-interface {v5, v2, v8}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 124
    .local v8, "className":Ljava/lang/String;
    const-string v9, "hidden"

    invoke-interface {v5, v2, v9}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v9

    .line 126
    .local v9, "hidden":Ljava/lang/String;
    new-instance v10, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;

    invoke-direct {v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;-><init>()V

    move-object v4, v10

    .line 127
    if-nez v7, :cond_3

    const-string v10, ""

    goto :goto_3

    :cond_3
    invoke-virtual {v7}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v10

    :goto_3
    invoke-virtual {v4, v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setPackageName(Ljava/lang/String;)V

    .line 128
    if-nez v8, :cond_4

    const-string v10, ""

    goto :goto_4

    :cond_4
    invoke-virtual {v8}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v10

    :goto_4
    invoke-virtual {v4, v10}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setClassName(Ljava/lang/String;)V

    .line 129
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v10

    .line 130
    .local v10, "iconName":Ljava/lang/String;
    sget-object v11, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    new-instance v12, Ljava/lang/StringBuilder;

    invoke-direct {v12}, Ljava/lang/StringBuilder;-><init>()V

    const-string v13, "parseIconThemeFromXml: first iconName = "

    invoke-virtual {v12, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v12, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v12}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v12

    invoke-static {v11, v12}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 131
    if-nez v10, :cond_5

    const-string v11, ""

    goto :goto_5

    :cond_5
    invoke-virtual {v10}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v11

    :goto_5
    invoke-virtual {v4, v11}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    .line 132
    const-string v11, "true"

    invoke-virtual {v11, v9}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v11

    invoke-virtual {v4, v11}, Lcom/hcn/auto/systemui/IconThemeUtils$AppInfo;->setHidden(Z)V

    goto :goto_6

    .line 119
    .end local v7    # "packageName":Ljava/lang/String;
    .end local v8    # "className":Ljava/lang/String;
    .end local v9    # "hidden":Ljava/lang/String;
    .end local v10    # "iconName":Ljava/lang/String;
    :cond_6
    nop

    .line 152
    :cond_7
    :goto_6
    invoke-interface {v5}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v7
    :try_end_0
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v6, v7

    goto/16 :goto_1

    .line 158
    .end local v6    # "event":I
    :catch_0
    move-exception v1

    .line 159
    .local v1, "e":Ljava/lang/Exception;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .end local v1    # "e":Ljava/lang/Exception;
    goto :goto_8

    .line 156
    :catch_1
    move-exception v1

    .line 157
    .local v1, "e":Ljava/io/IOException;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .end local v1    # "e":Ljava/io/IOException;
    goto :goto_7

    .line 154
    :catch_2
    move-exception v1

    .line 155
    .local v1, "e":Lorg/xmlpull/v1/XmlPullParserException;
    sget-object v2, Lcom/hcn/auto/systemui/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v6, ""

    invoke-static {v2, v6, v1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 160
    .end local v1    # "e":Lorg/xmlpull/v1/XmlPullParserException;
    :cond_8
    :goto_7
    nop

    .line 161
    :goto_8
    return-object v3

    nop

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
