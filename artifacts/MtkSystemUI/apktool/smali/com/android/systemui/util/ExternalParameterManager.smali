.class public Lcom/android/systemui/util/ExternalParameterManager;
.super Ljava/lang/Object;
.source "ExternalParameterManager.java"


# static fields
.field public static final ETHEME_GOD:I

.field public static ETHEME_GOD_400:I

.field public static final ETHEME_SUB:I

.field public static ETHEME_SUB_16:I

.field public static ETHEME_SUB_22:I

.field public static ETHEME_SUB_37:I

.field public static ETHEME_SUB_65:I


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 13
    const/16 v0, 0x190

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_GOD_400:I

    .line 14
    const/16 v0, 0x10

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_16:I

    .line 15
    const/16 v0, 0x16

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_22:I

    .line 16
    const/16 v0, 0x25

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_37:I

    .line 18
    const/16 v0, 0x41

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB_65:I

    .line 22
    const-string v0, "persist.sys.etheme_god"

    .line 23
    const/4 v1, 0x0

    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v2

    .line 22
    invoke-static {v0, v2}, Lcom/android/systemui/util/ExternalParameterManager;->parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_GOD:I

    .line 24
    const-string v0, "persist.sys.etheme_sub"

    .line 25
    invoke-static {v1}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    move-result-object v1

    .line 24
    invoke-static {v0, v1}, Lcom/android/systemui/util/ExternalParameterManager;->parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    sput v0, Lcom/android/systemui/util/ExternalParameterManager;->ETHEME_SUB:I

    return-void
.end method

.method public static getASystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 6
    .param p0, "property"    # Ljava/lang/String;
    .param p1, "defaultValue"    # Ljava/lang/String;

    .line 38
    :try_start_0
    const-string v0, "android.os.SystemProperties"

    invoke-static {v0}, Ljava/lang/Class;->forName(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object v0

    .line 39
    .local v0, "clazz":Ljava/lang/Class;
    const-string v1, "get"

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Class;

    const-class v4, Ljava/lang/String;

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-virtual {v0, v1, v3}, Ljava/lang/Class;->getDeclaredMethod(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;

    move-result-object v1

    .line 40
    .local v1, "getter":Ljava/lang/reflect/Method;
    const/4 v3, 0x0

    new-array v2, v2, [Ljava/lang/Object;

    aput-object p0, v2, v5

    invoke-virtual {v1, v3, v2}, Ljava/lang/reflect/Method;->invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/lang/String;

    .line 41
    .local v2, "value":Ljava/lang/String;
    invoke-static {v2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    if-nez v3, :cond_0

    .line 42
    return-object v2

    .line 46
    .end local v0    # "clazz":Ljava/lang/Class;
    .end local v1    # "getter":Ljava/lang/reflect/Method;
    .end local v2    # "value":Ljava/lang/String;
    :cond_0
    goto :goto_0

    .line 44
    :catch_0
    move-exception v0

    .line 45
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 47
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_0
    return-object p1
.end method

.method private static parseIntProperty(Ljava/lang/String;Ljava/lang/String;)I
    .locals 2
    .param p0, "propName"    # Ljava/lang/String;
    .param p1, "defValue"    # Ljava/lang/String;

    .line 62
    invoke-static {p0, p1}, Lcom/android/systemui/util/ExternalParameterManager;->getASystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 65
    .local v0, "propValue":Ljava/lang/String;
    :try_start_0
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_0

    .line 66
    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return v1

    .line 70
    :cond_0
    goto :goto_0

    .line 68
    :catch_0
    move-exception v1

    .line 69
    .local v1, "e":Ljava/lang/NumberFormatException;
    invoke-virtual {v1}, Ljava/lang/NumberFormatException;->printStackTrace()V

    .line 71
    .end local v1    # "e":Ljava/lang/NumberFormatException;
    :goto_0
    const/4 v1, 0x0

    return v1
.end method
