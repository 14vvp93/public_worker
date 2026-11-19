.class public Lcom/hcn/common/app/HLanguageUtils;
.super Ljava/lang/Object;
.source "HLanguageUtils.java"


# static fields
.field private static final KEY_LOCALE:Ljava/lang/String; = "KEY_LOCALE"

.field private static final VALUE_FOLLOW_SYSTEM:Ljava/lang/String; = "VALUE_FOLLOW_SYSTEM"


# direct methods
.method static bridge synthetic -$$Nest$smrestart(Z)V
    .locals 0

    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->restart(Z)V

    return-void
.end method

.method private constructor <init>()V
    .locals 2

    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 33
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method static applyLanguage(Landroid/app/Activity;)V
    .locals 2

    .line 242
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object v0

    const-string v1, "KEY_LOCALE"

    invoke-virtual {v0, v1}, Lcom/hcn/common/utils/HSPUtils;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 243
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-eqz v1, :cond_0

    return-void

    :cond_0
    const-string v1, "VALUE_FOLLOW_SYSTEM"

    .line 248
    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_1

    .line 249
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;

    move-result-object v0

    goto :goto_0

    .line 251
    :cond_1
    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->string2Locale(Ljava/lang/String;)Ljava/util/Locale;

    move-result-object v0

    :goto_0
    if-nez v0, :cond_2

    return-void

    .line 258
    :cond_2
    invoke-static {p0, v0}, Lcom/hcn/common/app/HLanguageUtils;->updateConfiguration(Landroid/content/Context;Ljava/util/Locale;)V

    .line 259
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object p0

    invoke-static {p0, v0}, Lcom/hcn/common/app/HLanguageUtils;->updateConfiguration(Landroid/content/Context;Ljava/util/Locale;)V

    return-void
.end method

.method public static applyLanguage(Ljava/util/Locale;)V
    .locals 1

    const/4 v0, 0x0

    .line 58
    invoke-static {p0, v0}, Lcom/hcn/common/app/HLanguageUtils;->applyLanguage(Ljava/util/Locale;Z)V

    return-void
.end method

.method public static applyLanguage(Ljava/util/Locale;Z)V
    .locals 0

    .line 69
    invoke-static {p0, p1}, Lcom/hcn/common/app/HLanguageUtils;->applyLanguageReal(Ljava/util/Locale;Z)V

    return-void
.end method

.method private static applyLanguageReal(Ljava/util/Locale;Z)V
    .locals 4

    const/4 v0, 0x1

    const-string v1, "KEY_LOCALE"

    if-nez p0, :cond_0

    .line 75
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object v2

    const-string v3, "VALUE_FOLLOW_SYSTEM"

    invoke-virtual {v2, v1, v3, v0}, Lcom/hcn/common/utils/HSPUtils;->put(Ljava/lang/String;Ljava/lang/String;Z)V

    goto :goto_0

    .line 77
    :cond_0
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object v2

    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->locale2String(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v1, v3, v0}, Lcom/hcn/common/utils/HSPUtils;->put(Ljava/lang/String;Ljava/lang/String;Z)V

    :goto_0
    if-nez p0, :cond_1

    .line 80
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object p0

    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;

    move-result-object p0

    .line 81
    :cond_1
    new-instance v0, Lcom/hcn/common/app/HLanguageUtils$1;

    invoke-direct {v0, p1}, Lcom/hcn/common/app/HLanguageUtils$1;-><init>(Z)V

    invoke-static {p0, v0}, Lcom/hcn/common/app/HLanguageUtils;->updateAppContextLanguage(Ljava/util/Locale;Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    return-void
.end method

.method public static applySystemLanguage()V
    .locals 1

    const/4 v0, 0x0

    .line 40
    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->applySystemLanguage(Z)V

    return-void
.end method

.method public static applySystemLanguage(Z)V
    .locals 1

    const/4 v0, 0x0

    .line 49
    invoke-static {v0, p0}, Lcom/hcn/common/app/HLanguageUtils;->applyLanguageReal(Ljava/util/Locale;Z)V

    return-void
.end method

.method public static attachBaseContext(Landroid/content/Context;)Landroid/content/Context;
    .locals 4

    .line 218
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object v0

    const-string v1, "KEY_LOCALE"

    invoke-virtual {v0, v1}, Lcom/hcn/common/utils/HSPUtils;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 219
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_3

    const-string v1, "VALUE_FOLLOW_SYSTEM"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    .line 223
    :cond_0
    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->string2Locale(Ljava/lang/String;)Ljava/util/Locale;

    move-result-object v0

    if-nez v0, :cond_1

    return-object p0

    .line 228
    :cond_1
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    .line 229
    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v2

    .line 231
    invoke-static {v2, v0}, Lcom/hcn/common/app/HLanguageUtils;->setLocal(Landroid/content/res/Configuration;Ljava/util/Locale;)V

    .line 233
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v3, 0x11

    if-lt v0, v3, :cond_2

    .line 234
    invoke-virtual {p0, v2}, Landroid/content/Context;->createConfigurationContext(Landroid/content/res/Configuration;)Landroid/content/Context;

    move-result-object p0

    return-object p0

    .line 236
    :cond_2
    invoke-virtual {v1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    invoke-virtual {v1, v2, v0}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    :cond_3
    :goto_0
    return-object p0
.end method

.method public static getAppContextLanguage()Ljava/util/Locale;
    .locals 1

    .line 155
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->getContextLanguage(Landroid/content/Context;)Ljava/util/Locale;

    move-result-object v0

    return-object v0
.end method

.method public static getAppliedLanguage()Ljava/util/Locale;
    .locals 2

    .line 133
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object v0

    const-string v1, "KEY_LOCALE"

    invoke-virtual {v0, v1}, Lcom/hcn/common/utils/HSPUtils;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 134
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v1

    if-nez v1, :cond_1

    const-string v1, "VALUE_FOLLOW_SYSTEM"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    .line 137
    :cond_0
    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->string2Locale(Ljava/lang/String;)Ljava/util/Locale;

    move-result-object v0

    return-object v0

    :cond_1
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method public static getContextLanguage(Landroid/content/Context;)Ljava/util/Locale;
    .locals 0

    .line 146
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object p0

    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;

    move-result-object p0

    return-object p0
.end method

.method private static getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;
    .locals 2

    .line 317
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x18

    if-lt v0, v1, :cond_0

    .line 318
    invoke-virtual {p0}, Landroid/content/res/Configuration;->getLocales()Landroid/os/LocaleList;

    move-result-object p0

    const/4 v0, 0x0

    invoke-virtual {p0, v0}, Landroid/os/LocaleList;->get(I)Ljava/util/Locale;

    move-result-object p0

    return-object p0

    .line 320
    :cond_0
    iget-object p0, p0, Landroid/content/res/Configuration;->locale:Ljava/util/Locale;

    return-object p0
.end method

.method public static getSystemLanguage()Ljava/util/Locale;
    .locals 1

    .line 164
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    invoke-static {v0}, Lcom/hcn/common/app/HLanguageUtils;->getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;

    move-result-object v0

    return-object v0
.end method

.method public static isAppliedLanguage()Z
    .locals 1

    .line 110
    invoke-static {}, Lcom/hcn/common/app/HLanguageUtils;->getAppliedLanguage()Ljava/util/Locale;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public static isAppliedLanguage(Ljava/util/Locale;)Z
    .locals 1

    .line 120
    invoke-static {}, Lcom/hcn/common/app/HLanguageUtils;->getAppliedLanguage()Ljava/util/Locale;

    move-result-object v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return p0

    .line 124
    :cond_0
    invoke-static {p0, v0}, Lcom/hcn/common/app/HLanguageUtils;->isSameLocale(Ljava/util/Locale;Ljava/util/Locale;)Z

    move-result p0

    return p0
.end method

.method private static isRightFormatLocalStr(Ljava/lang/String;)Z
    .locals 7

    .line 298
    invoke-virtual {p0}, Ljava/lang/String;->toCharArray()[C

    move-result-object p0

    .line 300
    array-length v0, p0

    const/4 v1, 0x0

    move v2, v1

    move v3, v2

    :goto_0
    const/4 v4, 0x1

    if-ge v2, v0, :cond_2

    aget-char v5, p0, v2

    const/16 v6, 0x24

    if-ne v5, v6, :cond_1

    if-lt v3, v4, :cond_0

    return v1

    :cond_0
    add-int/lit8 v3, v3, 0x1

    :cond_1
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_2
    if-ne v3, v4, :cond_3

    move v1, v4

    :cond_3
    return v1
.end method

.method private static isSameLocale(Ljava/util/Locale;Ljava/util/Locale;)Z
    .locals 2

    .line 312
    invoke-virtual {p1}, Ljava/util/Locale;->getLanguage()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0}, Ljava/util/Locale;->getLanguage()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/hcn/common/utils/HStringUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 313
    invoke-virtual {p1}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p0}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object p0

    invoke-static {p1, p0}, Lcom/hcn/common/utils/HStringUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static locale2String(Ljava/util/Locale;)Ljava/lang/String;
    .locals 2

    .line 270
    invoke-virtual {p0}, Ljava/util/Locale;->getLanguage()Ljava/lang/String;

    move-result-object v0

    .line 271
    invoke-virtual {p0}, Ljava/util/Locale;->getCountry()Ljava/lang/String;

    move-result-object p0

    .line 272
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "$"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method static pollCheckAppContextLocal(Ljava/util/Locale;ILcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Locale;",
            "I",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)V"
        }
    .end annotation

    .line 181
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Application;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 182
    invoke-virtual {v0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    .line 183
    invoke-static {v1}, Lcom/hcn/common/app/HLanguageUtils;->getLocal(Landroid/content/res/Configuration;)Ljava/util/Locale;

    move-result-object v2

    .line 185
    invoke-static {v1, p0}, Lcom/hcn/common/app/HLanguageUtils;->setLocal(Landroid/content/res/Configuration;Ljava/util/Locale;)V

    .line 187
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v3

    invoke-virtual {v3}, Landroid/app/Application;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    invoke-virtual {v3, v1, v0}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    if-nez p2, :cond_0

    return-void

    .line 193
    :cond_0
    invoke-static {v2, p0}, Lcom/hcn/common/app/HLanguageUtils;->isSameLocale(Ljava/util/Locale;Ljava/util/Locale;)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p0, 0x1

    .line 194
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p0

    invoke-interface {p2, p0}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    goto :goto_0

    :cond_1
    const/16 v0, 0x14

    if-ge p1, v0, :cond_2

    .line 197
    new-instance v0, Lcom/hcn/common/app/HLanguageUtils$2;

    invoke-direct {v0, p0, p1, p2}, Lcom/hcn/common/app/HLanguageUtils$2;-><init>(Ljava/util/Locale;ILcom/hcn/common/utils/HUtilsEx$Consumer;)V

    const-wide/16 p0, 0x10

    invoke-static {v0, p0, p1}, Lcom/hcn/common/lang/HThreadUtils;->runOnUiThreadDelayed(Ljava/lang/Runnable;J)V

    return-void

    :cond_2
    const-string p0, "LanguageUtils"

    const-string p1, "appLocal didn\'t update."

    .line 205
    invoke-static {p0, p1}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    const/4 p0, 0x0

    .line 206
    invoke-static {p0}, Ljava/lang/Boolean;->valueOf(Z)Ljava/lang/Boolean;

    move-result-object p0

    invoke-interface {p2, p0}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    :goto_0
    return-void
.end method

.method private static restart(Z)V
    .locals 1

    if-eqz p0, :cond_0

    .line 96
    invoke-static {}, Lcom/hcn/common/app/HAppUtils;->relaunchApp()V

    goto :goto_1

    .line 98
    :cond_0
    sget-object p0, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->INSTANCE:Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;

    invoke-virtual {p0}, Lcom/hcn/common/app/HUtilsActivityLifecycleImpl;->getActivityList()Ljava/util/List;

    move-result-object p0

    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object p0

    :goto_0
    invoke-interface {p0}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {p0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/Activity;

    .line 99
    invoke-virtual {v0}, Landroid/app/Activity;->recreate()V

    goto :goto_0

    :cond_1
    :goto_1
    return-void
.end method

.method private static setLocal(Landroid/content/res/Configuration;Ljava/util/Locale;)V
    .locals 2

    .line 325
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x11

    if-lt v0, v1, :cond_0

    .line 326
    invoke-virtual {p0, p1}, Landroid/content/res/Configuration;->setLocale(Ljava/util/Locale;)V

    goto :goto_0

    .line 328
    :cond_0
    iput-object p1, p0, Landroid/content/res/Configuration;->locale:Ljava/util/Locale;

    :goto_0
    return-void
.end method

.method private static string2Locale(Ljava/lang/String;)Ljava/util/Locale;
    .locals 3

    .line 276
    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->string2LocaleReal(Ljava/lang/String;)Ljava/util/Locale;

    move-result-object v0

    if-nez v0, :cond_0

    .line 278
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "The string of "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string v1, " is not in the correct format."

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    const-string v1, "LanguageUtils"

    invoke-static {v1, p0}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 279
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getSpUtils4Utils()Lcom/hcn/common/utils/HSPUtils;

    move-result-object p0

    const-string v1, "KEY_LOCALE"

    invoke-virtual {p0, v1}, Lcom/hcn/common/utils/HSPUtils;->remove(Ljava/lang/String;)V

    :cond_0
    return-object v0
.end method

.method private static string2LocaleReal(Ljava/lang/String;)Ljava/util/Locale;
    .locals 4

    .line 285
    invoke-static {p0}, Lcom/hcn/common/app/HLanguageUtils;->isRightFormatLocalStr(Ljava/lang/String;)Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return-object v1

    :cond_0
    :try_start_0
    const-string v0, "$"

    .line 290
    invoke-virtual {p0, v0}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v0

    .line 291
    new-instance v2, Ljava/util/Locale;

    const/4 v3, 0x0

    invoke-virtual {p0, v3, v0}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v3

    add-int/lit8 v0, v0, 0x1

    invoke-virtual {p0, v0}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p0

    invoke-direct {v2, v3, p0}, Ljava/util/Locale;-><init>(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object v2

    :catch_0
    return-object v1
.end method

.method public static updateAppContextLanguage(Ljava/util/Locale;Lcom/hcn/common/utils/HUtilsEx$Consumer;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/Locale;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Ljava/lang/Boolean;",
            ">;)V"
        }
    .end annotation

    const/4 v0, 0x0

    .line 175
    invoke-static {p0, v0, p1}, Lcom/hcn/common/app/HLanguageUtils;->pollCheckAppContextLocal(Ljava/util/Locale;ILcom/hcn/common/utils/HUtilsEx$Consumer;)V

    return-void
.end method

.method private static updateConfiguration(Landroid/content/Context;Ljava/util/Locale;)V
    .locals 1

    .line 263
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    .line 264
    invoke-virtual {p0}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v0

    .line 265
    invoke-static {v0, p1}, Lcom/hcn/common/app/HLanguageUtils;->setLocal(Landroid/content/res/Configuration;Ljava/util/Locale;)V

    .line 266
    invoke-virtual {p0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p1

    invoke-virtual {p0, v0, p1}, Landroid/content/res/Resources;->updateConfiguration(Landroid/content/res/Configuration;Landroid/util/DisplayMetrics;)V

    return-void
.end method
