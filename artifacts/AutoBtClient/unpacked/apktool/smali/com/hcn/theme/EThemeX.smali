.class public Lcom/hcn/theme/EThemeX;
.super Ljava/lang/Object;
.source "EThemeX.java"


# static fields
.field public static final E_THEME_GOD:I

.field public static final E_THEME_SUB:I


# direct methods
.method static constructor <clinit>()V
    .locals 3

    const/4 v0, 0x0

    .line 15
    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    const-string v2, "persist.sys.etheme_god"

    .line 14
    invoke-static {v2, v1}, Lcom/hcn/theme/EThemeX;->parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    sput v1, Lcom/hcn/theme/EThemeX;->E_THEME_GOD:I

    .line 17
    invoke-static {v0}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v0

    const-string v1, "persist.sys.etheme_sub"

    .line 16
    invoke-static {v1, v0}, Lcom/hcn/theme/EThemeX;->parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    sput v0, Lcom/hcn/theme/EThemeX;->E_THEME_SUB:I

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 12
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static eThemeWeight()I
    .locals 4

    .line 77
    sget v0, Lcom/hcn/theme/EThemeX;->E_THEME_GOD:I

    sget v1, Lcom/hcn/theme/EThemeX;->E_THEME_SUB:I

    add-int v2, v0, v1

    const/4 v3, 0x0

    .line 78
    invoke-static {v3}, Lcom/hcn/theme/EThemeX;->isThemeSub(I)Z

    move-result v3

    if-nez v3, :cond_0

    mul-int/lit16 v0, v0, 0x3e8

    add-int v2, v0, v1

    :cond_0
    return v2
.end method

.method public static isThemeGod(I)Z
    .locals 1

    .line 47
    sget v0, Lcom/hcn/theme/EThemeX;->E_THEME_GOD:I

    if-ne v0, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static isThemeSub(I)Z
    .locals 1

    .line 58
    sget v0, Lcom/hcn/theme/EThemeX;->E_THEME_SUB:I

    if-ne v0, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method public static isThemeX(I)Z
    .locals 1

    .line 67
    invoke-static {}, Lcom/hcn/theme/EThemeX;->eThemeWeight()I

    move-result v0

    if-ne v0, p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I
    .locals 0

    .line 27
    invoke-static {p0, p1}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p0

    .line 30
    :try_start_0
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-nez p1, :cond_0

    .line 31
    invoke-virtual {p0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p0

    invoke-static {p0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return p0

    :catch_0
    move-exception p0

    .line 34
    invoke-virtual {p0}, Ljava/lang/NumberFormatException;->printStackTrace()V

    :cond_0
    const/4 p0, 0x0

    return p0
.end method
