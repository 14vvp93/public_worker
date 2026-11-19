.class public Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;
.super Ljava/lang/Object;
.source "SkinExInflaterFactory2.java"

# interfaces
.implements Landroid/view/LayoutInflater$Factory2;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$SInstanceHolder;
    }
.end annotation


# static fields
.field private static final ENHANCE_TEXTVIEW_PROP:Ljava/lang/String; = "persist.sys.skinx_enhance_textview"

.field private static final SUPPORT_ENHANCE_TEXTVIEW:Z

.field private static final TAG:Ljava/lang/String; = "SkinExInflaterFactory2"

.field private static final mConstructorArgs:[Ljava/lang/Object;

.field private static mOverlayCallback:Lcom/hcn/skin2/enhance/IOverlayCallback;

.field private static final sClassPrefixList:[Ljava/lang/String;

.field private static final sConstructorMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/reflect/Constructor<",
            "+",
            "Landroid/view/View;",
            ">;>;"
        }
    .end annotation
.end field

.field private static final sConstructorSignature:[Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "[",
            "Ljava/lang/Class<",
            "*>;"
        }
    .end annotation
.end field

.field private static final sTextViewClassNameList:[Ljava/lang/String;


# instance fields
.field private final sTextViewAttrMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Lcom/hcn/skin2/base/TextViewAttr;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 6

    const/4 v0, 0x2

    new-array v1, v0, [Ljava/lang/Object;

    .line 33
    sput-object v1, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    new-array v0, v0, [Ljava/lang/Class;

    .line 34
    const-class v1, Landroid/content/Context;

    const/4 v2, 0x0

    aput-object v1, v0, v2

    const-class v1, Landroid/util/AttributeSet;

    const/4 v3, 0x1

    aput-object v1, v0, v3

    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sConstructorSignature:[Ljava/lang/Class;

    .line 37
    new-instance v0, Landroid/util/ArrayMap;

    invoke-direct {v0}, Landroid/util/ArrayMap;-><init>()V

    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sConstructorMap:Ljava/util/Map;

    const-string v0, "android.widget."

    const-string v1, "android.view."

    .line 46
    filled-new-array {v0, v1}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sClassPrefixList:[Ljava/lang/String;

    const-string v0, "TextView"

    const-string v1, "Button"

    const-string v4, "RadioButton"

    const-string v5, "CheckBox"

    .line 52
    filled-new-array {v0, v1, v4, v5}, [Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sTextViewClassNameList:[Ljava/lang/String;

    const-string v0, "persist.sys.skinx_enhance_textview"

    .line 68
    invoke-static {v0, v2}, Lcom/hcn/common/utils/HUtilsEx;->getSystemProperty(Ljava/lang/String;I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/Integer;->equals(Ljava/lang/Object;)Z

    move-result v0

    sput-boolean v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->SUPPORT_ENHANCE_TEXTVIEW:Z

    const/4 v0, 0x0

    .line 71
    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mOverlayCallback:Lcom/hcn/skin2/enhance/IOverlayCallback;

    return-void
.end method

.method private constructor <init>()V
    .locals 1

    .line 123
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 43
    new-instance v0, Landroid/util/ArrayMap;

    invoke-direct {v0}, Landroid/util/ArrayMap;-><init>()V

    iput-object v0, p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sTextViewAttrMap:Ljava/util/Map;

    return-void
.end method

.method synthetic constructor <init>(Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$1;)V
    .locals 0

    .line 29
    invoke-direct {p0}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;-><init>()V

    return-void
.end method

.method private static buildView(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;
    .locals 6
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Landroid/view/InflateException;
        }
    .end annotation

    .line 215
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sConstructorMap:Ljava/util/Map;

    invoke-interface {v0, p1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/lang/reflect/Constructor;

    .line 216
    invoke-static {p2}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_0

    move-object p2, p1

    goto :goto_0

    :cond_0
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v2, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    :goto_0
    const/4 v2, 0x0

    const/4 v3, 0x1

    if-nez v1, :cond_1

    const/4 v1, 0x2

    :try_start_0
    new-array v1, v1, [Ljava/lang/Object;

    .line 219
    sget-object v4, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->TAG:Ljava/lang/String;

    aput-object v4, v1, v2

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "loadClass: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    aput-object v4, v1, v3

    invoke-static {v1}, Lcom/hcn/common/misc/LogUtils;->v([Ljava/lang/Object;)V

    .line 222
    invoke-virtual {p0}, Landroid/content/Context;->getClassLoader()Ljava/lang/ClassLoader;

    move-result-object p0

    .line 223
    invoke-virtual {p0, p2}, Ljava/lang/ClassLoader;->loadClass(Ljava/lang/String;)Ljava/lang/Class;

    move-result-object p0

    const-class v1, Landroid/view/View;

    invoke-virtual {p0, v1}, Ljava/lang/Class;->asSubclass(Ljava/lang/Class;)Ljava/lang/Class;

    move-result-object p0

    .line 224
    sget-object v1, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sConstructorSignature:[Ljava/lang/Class;

    invoke-virtual {p0, v1}, Ljava/lang/Class;->getConstructor([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;

    move-result-object v1

    .line 225
    invoke-interface {v0, p1, v1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 228
    :cond_1
    invoke-virtual {v1, v3}, Ljava/lang/reflect/Constructor;->setAccessible(Z)V

    .line 229
    sget-object p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    invoke-virtual {v1, p0}, Ljava/lang/reflect/Constructor;->newInstance([Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Landroid/view/View;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    return-object p0

    :catch_0
    new-array p0, v3, [Ljava/lang/Object;

    .line 231
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "loadClass "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string p2, " Fail!"

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    aput-object p1, p0, v2

    invoke-static {p0}, Lcom/hcn/common/misc/LogUtils;->v([Ljava/lang/Object;)V

    const/4 p0, 0x0

    return-object p0
.end method

.method public static buildViewFromName(Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;
    .locals 6

    const/4 v0, 0x1

    const/4 v1, 0x0

    const/4 v2, 0x0

    .line 189
    :try_start_0
    sget-object v3, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object p0, v3, v1

    .line 190
    aput-object p2, v3, v0

    const/4 p2, -0x1

    const/16 v4, 0x2e

    .line 193
    invoke-virtual {p1, v4}, Ljava/lang/String;->indexOf(I)I

    move-result v4

    if-ne p2, v4, :cond_2

    .line 194
    sget-object p2, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sClassPrefixList:[Ljava/lang/String;

    array-length v3, p2

    move v4, v1

    :goto_0
    if-ge v4, v3, :cond_1

    aget-object v5, p2, v4

    .line 195
    invoke-static {p0, p1, v5}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->buildView(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;

    move-result-object v5
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v5, :cond_0

    .line 207
    sget-object p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object v2, p0, v1

    .line 208
    aput-object v2, p0, v0

    return-object v5

    :cond_0
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 207
    :cond_1
    sget-object p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object v2, p0, v1

    .line 208
    aput-object v2, p0, v0

    goto :goto_1

    .line 201
    :cond_2
    :try_start_1
    invoke-static {p0, p1, v2}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->buildView(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/view/View;

    move-result-object p0
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 207
    aput-object v2, v3, v1

    .line 208
    aput-object v2, v3, v0

    return-object p0

    :catchall_0
    move-exception p0

    goto :goto_2

    :catch_0
    move-exception p0

    .line 204
    :try_start_2
    sget-object p2, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->TAG:Ljava/lang/String;

    new-array v3, v0, [Ljava/lang/Object;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "buildViewFromName: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v4, ", "

    invoke-virtual {p1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object p0

    invoke-virtual {p1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    aput-object p0, v3, v1

    invoke-static {p2, v3}, Lcom/hcn/common/misc/LogUtils;->dTag(Ljava/lang/String;[Ljava/lang/Object;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 207
    sget-object p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object v2, p0, v1

    .line 208
    aput-object v2, p0, v0

    :goto_1
    return-object v2

    .line 207
    :goto_2
    sget-object p1, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object v2, p1, v1

    .line 208
    aput-object v2, p1, v0

    .line 209
    throw p0
.end method

.method public static instance()Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;
    .locals 1

    .line 132
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$SInstanceHolder;->S_INSTANCE:Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;

    return-object v0
.end method

.method private parseTextAttrRid(Landroid/util/AttributeSet;)I
    .locals 6

    .line 238
    invoke-interface {p1}, Landroid/util/AttributeSet;->getAttributeCount()I

    move-result v0

    const/4 v1, 0x0

    move v2, v1

    :goto_0
    if-ge v2, v0, :cond_1

    .line 240
    invoke-interface {p1, v2}, Landroid/util/AttributeSet;->getAttributeName(I)Ljava/lang/String;

    move-result-object v3

    .line 241
    invoke-interface {p1, v2}, Landroid/util/AttributeSet;->getAttributeValue(I)Ljava/lang/String;

    move-result-object v4

    const-string v5, "text"

    .line 243
    invoke-virtual {v5, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    const-string v3, "@"

    invoke-virtual {v4, v3}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_0

    const/4 p1, 0x1

    .line 246
    :try_start_0
    invoke-virtual {v4, p1}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    move-exception p1

    .line 248
    invoke-virtual {p1}, Ljava/lang/NumberFormatException;->printStackTrace()V

    :goto_1
    return v1

    :cond_0
    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    return v1
.end method

.method public static setOverlayCallback(Lcom/hcn/skin2/enhance/IOverlayCallback;)V
    .locals 0

    .line 79
    sput-object p0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mOverlayCallback:Lcom/hcn/skin2/enhance/IOverlayCallback;

    return-void
.end method

.method private static tryCheckCustomizeTextView(Ljava/lang/String;)Z
    .locals 1

    .line 120
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mOverlayCallback:Lcom/hcn/skin2/enhance/IOverlayCallback;

    if-eqz v0, :cond_0

    invoke-interface {v0, p0}, Lcom/hcn/skin2/enhance/IOverlayCallback;->isSupportRROView(Ljava/lang/String;)Z

    move-result p0

    if-eqz p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static tryCheckEnhanceTextView(Ljava/lang/String;)Z
    .locals 2

    .line 105
    sget-boolean v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->SUPPORT_ENHANCE_TEXTVIEW:Z

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    :cond_0
    const-string v0, "CheckedTextView"

    .line 109
    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "com.hcn.media_view.HTextView"

    .line 110
    invoke-virtual {p0, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_2

    :cond_1
    const/4 v1, 0x1

    :cond_2
    return v1
.end method

.method private static tryCheckGeneralTextView(Ljava/lang/String;)Z
    .locals 5

    .line 89
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->sTextViewClassNameList:[Ljava/lang/String;

    array-length v1, v0

    const/4 v2, 0x0

    move v3, v2

    :goto_0
    if-ge v3, v1, :cond_1

    aget-object v4, v0, v3

    .line 90
    invoke-virtual {v4, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_0

    const/4 p0, 0x1

    return p0

    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    :cond_1
    return v2
.end method


# virtual methods
.method public onCreateView(Landroid/view/View;Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;
    .locals 3

    .line 141
    sget-object p1, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->TAG:Ljava/lang/String;

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v1, 0x0

    const-string v2, "onCreateView(4 parameter)"

    aput-object v2, v0, v1

    invoke-static {p1, v0}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 142
    invoke-virtual {p0, p2, p3, p4}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->onCreateView(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method

.method public onCreateView(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;
    .locals 5

    .line 150
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->TAG:Ljava/lang/String;

    const/4 v1, 0x1

    new-array v2, v1, [Ljava/lang/Object;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onCreateView("

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ", ...)"

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    const/4 v4, 0x0

    aput-object v3, v2, v4

    invoke-static {v0, v2}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 152
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mConstructorArgs:[Ljava/lang/Object;

    aput-object p2, v0, v4

    .line 153
    aput-object p3, v0, v1

    .line 157
    sget-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->mOverlayCallback:Lcom/hcn/skin2/enhance/IOverlayCallback;

    if-eqz v0, :cond_0

    .line 158
    invoke-interface {v0, p1, p2, p3}, Lcom/hcn/skin2/enhance/IOverlayCallback;->tryReplaceAndCreateView(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;

    move-result-object v0

    if-eqz v0, :cond_0

    return-object v0

    .line 165
    :cond_0
    invoke-static {p1}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->tryCheckGeneralTextView(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 166
    invoke-static {p1}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->tryCheckCustomizeTextView(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_2

    .line 167
    invoke-static {p1}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->tryCheckEnhanceTextView(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    goto :goto_0

    :cond_1
    const/4 p1, 0x0

    return-object p1

    .line 168
    :cond_2
    :goto_0
    invoke-static {p2, p1, p3}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->buildViewFromName(Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;

    move-result-object p1

    .line 169
    instance-of p2, p1, Landroid/widget/TextView;

    if-eqz p2, :cond_3

    .line 170
    move-object p2, p1

    check-cast p2, Landroid/widget/TextView;

    .line 171
    invoke-static {}, Lcom/hcn/skin2/runtime/RRO;->instance()Lcom/hcn/skin2/runtime/RRO;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin2/runtime/RRO;->isEnabled()Z

    move-result v0

    if-eqz v0, :cond_3

    .line 172
    invoke-direct {p0, p3}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;->parseTextAttrRid(Landroid/util/AttributeSet;)I

    move-result p3

    if-eqz p3, :cond_3

    .line 175
    invoke-static {}, Lcom/hcn/skin2/enhance/SkinExCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    .line 176
    invoke-virtual {v0, p3, v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getString(IZ)Ljava/lang/String;

    move-result-object p3

    .line 174
    invoke-virtual {p2, p3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_3
    return-object p1
.end method
