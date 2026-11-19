.class public Lcom/hcn/auto/theme/utils/IconThemeUtils;
.super Ljava/lang/Object;
.source "IconThemeUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String;

.field private static appInfoList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 16
    const-class v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;

    invoke-virtual {v0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 15
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getCustomAppIcon(Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
    .locals 5
    .param p0, "packageName"    # Ljava/lang/String;
    .param p1, "className"    # Ljava/lang/String;

    .line 103
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 105
    :cond_0
    sget-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 106
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 110
    :cond_1
    const/4 v0, 0x0

    move v2, v0

    .local v2, "i":I
    :goto_0
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    if-ge v2, v3, :cond_4

    .line 111
    sget-object v3, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v3, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 112
    .local v3, "appInfo":Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {p0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_3

    .line 115
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_2

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_2

    .line 116
    goto :goto_1

    .line 119
    :cond_2
    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_3

    .line 121
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object v1

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$300(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v1, v4, v0}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getBitmap(Ljava/lang/String;Z)Landroid/graphics/Bitmap;

    move-result-object v0

    return-object v0

    .line 110
    .end local v3    # "appInfo":Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    :cond_3
    :goto_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    .line 125
    .end local v2    # "i":I
    :cond_4
    return-object v1
.end method

.method public static isHidden(Ljava/lang/String;Ljava/lang/String;)Z
    .locals 4
    .param p0, "packageName"    # Ljava/lang/String;
    .param p1, "className"    # Ljava/lang/String;

    .line 74
    invoke-static {p0}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x1

    if-eqz v0, :cond_0

    return v1

    .line 76
    :cond_0
    sget-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    if-nez v0, :cond_1

    .line 77
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->parseIconThemeFromXml()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    .line 81
    :cond_1
    invoke-static {}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isOverseas()Z

    move-result v0

    if-eqz v0, :cond_2

    const-string v0, "com.hcn.autoupdate"

    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 82
    return v1

    .line 86
    :cond_2
    const/4 v0, 0x0

    move v1, v0

    .local v1, "i":I
    :goto_0
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-ge v1, v2, :cond_5

    .line 87
    sget-object v2, Lcom/hcn/auto/theme/utils/IconThemeUtils;->appInfoList:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    .line 88
    .local v2, "appInfo":Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$000(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {p0, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 91
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-static {v3}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v3

    if-nez v3, :cond_3

    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$100(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_3

    .line 92
    goto :goto_1

    .line 95
    :cond_3
    invoke-static {v2}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->access$200(Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;)Z

    move-result v0

    return v0

    .line 86
    .end local v2    # "appInfo":Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    :cond_4
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 99
    .end local v1    # "i":I
    :cond_5
    return v0
.end method

.method static isOverseas()Z
    .locals 1

    .line 168
    invoke-static {}, Landroid/Configures/HConfig;->isOverseaVersion()Z

    move-result v0

    return v0
.end method

.method static isTrimEmpty(Ljava/lang/String;)Z
    .locals 1
    .param p0, "s"    # Ljava/lang/String;

    .line 164
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
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;",
            ">;"
        }
    .end annotation

    .line 21
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 22
    .local v0, "appInfoList":Ljava/util/List;, "Ljava/util/List<Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;>;"
    const/4 v1, 0x0

    .line 24
    .local v1, "appInfo":Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;
    invoke-static {}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getInstance()Lcom/hcn/auto/theme/utils/ThemeUtils;

    move-result-object v2

    const-string v3, "icon_theme"

    invoke-virtual {v2, v3}, Lcom/hcn/auto/theme/utils/ThemeUtils;->getXml(Ljava/lang/String;)Landroid/content/res/XmlResourceParser;

    move-result-object v2

    .line 26
    .local v2, "xmlParser":Landroid/content/res/XmlResourceParser;
    :try_start_0
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v3

    .line 29
    .local v3, "event":I
    :goto_0
    const/4 v4, 0x1

    if-eq v3, v4, :cond_5

    .line 31
    if-eqz v3, :cond_3

    packed-switch v3, :pswitch_data_0

    goto/16 :goto_4

    .line 47
    :pswitch_0
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getText()Ljava/lang/String;

    move-result-object v4

    .line 48
    .local v4, "iconName":Ljava/lang/String;
    if-nez v4, :cond_0

    const-string v5, ""

    goto :goto_1

    :cond_0
    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v5

    :goto_1
    invoke-virtual {v1, v5}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setIconName(Ljava/lang/String;)V

    .line 49
    goto :goto_4

    .line 51
    .end local v4    # "iconName":Ljava/lang/String;
    :pswitch_1
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v4

    const-string v5, "app"

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 53
    invoke-virtual {v1}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->getPackageName()Ljava/lang/String;

    move-result-object v4

    invoke-static {v4}, Lcom/hcn/auto/theme/utils/IconThemeUtils;->isTrimEmpty(Ljava/lang/String;)Z

    move-result v4

    if-nez v4, :cond_4

    .line 54
    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_4

    .line 35
    :pswitch_2
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->getName()Ljava/lang/String;

    move-result-object v4

    const-string v5, "app"

    invoke-virtual {v4, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_4

    .line 36
    const-string v4, "packageName"

    const/4 v5, 0x0

    invoke-interface {v2, v5, v4}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    .line 37
    .local v4, "packageName":Ljava/lang/String;
    const-string v6, "className"

    invoke-interface {v2, v5, v6}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    .line 38
    .local v6, "className":Ljava/lang/String;
    const-string v7, "hidden"

    invoke-interface {v2, v5, v7}, Landroid/content/res/XmlResourceParser;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    .line 40
    .local v5, "hidden":Ljava/lang/String;
    new-instance v7, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;

    invoke-direct {v7}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;-><init>()V

    move-object v1, v7

    .line 41
    if-nez v4, :cond_1

    const-string v7, ""

    goto :goto_2

    :cond_1
    invoke-virtual {v4}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v7

    :goto_2
    invoke-virtual {v1, v7}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setPackageName(Ljava/lang/String;)V

    .line 42
    if-nez v6, :cond_2

    const-string v7, ""

    goto :goto_3

    :cond_2
    invoke-virtual {v6}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v7

    :goto_3
    invoke-virtual {v1, v7}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setClassName(Ljava/lang/String;)V

    .line 43
    const-string v7, "true"

    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    invoke-virtual {v1, v7}, Lcom/hcn/auto/theme/utils/IconThemeUtils$AppInfo;->setHidden(Z)V

    goto :goto_4

    .line 33
    .end local v4    # "packageName":Ljava/lang/String;
    .end local v5    # "hidden":Ljava/lang/String;
    .end local v6    # "className":Ljava/lang/String;
    :cond_3
    nop

    .line 61
    :cond_4
    :goto_4
    invoke-interface {v2}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v4
    :try_end_0
    .catch Lorg/xmlpull/v1/XmlPullParserException; {:try_start_0 .. :try_end_0} :catch_2
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_1
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v3, v4

    goto/16 :goto_0

    .line 67
    .end local v3    # "event":I
    :catch_0
    move-exception v3

    .line 68
    .local v3, "e":Ljava/lang/Exception;
    sget-object v4, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v5, ""

    invoke-static {v4, v5, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .end local v3    # "e":Ljava/lang/Exception;
    goto :goto_6

    .line 65
    :catch_1
    move-exception v3

    .line 66
    .local v3, "e":Ljava/io/IOException;
    sget-object v4, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v5, ""

    invoke-static {v4, v5, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .end local v3    # "e":Ljava/io/IOException;
    goto :goto_5

    .line 63
    :catch_2
    move-exception v3

    .line 64
    .local v3, "e":Lorg/xmlpull/v1/XmlPullParserException;
    sget-object v4, Lcom/hcn/auto/theme/utils/IconThemeUtils;->TAG:Ljava/lang/String;

    const-string v5, ""

    invoke-static {v4, v5, v3}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I

    .line 69
    .end local v3    # "e":Lorg/xmlpull/v1/XmlPullParserException;
    :cond_5
    :goto_5
    nop

    .line 70
    :goto_6
    return-object v0

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
