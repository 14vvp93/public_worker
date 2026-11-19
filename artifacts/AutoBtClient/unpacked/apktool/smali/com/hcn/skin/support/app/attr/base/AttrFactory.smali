.class public Lcom/hcn/skin/support/app/attr/base/AttrFactory;
.super Ljava/lang/Object;
.source "AttrFactory.java"


# static fields
.field private static sSupportAttr:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Lcom/hcn/skin/support/app/attr/base/SkinAttr;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 25
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    .line 28
    new-instance v1, Lcom/hcn/skin/support/app/attr/BackgroundAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/BackgroundAttr;-><init>()V

    const-string v2, "background"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 29
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/TextColorAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/TextColorAttr;-><init>()V

    const-string v2, "textColor"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 30
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/TextAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/TextAttr;-><init>()V

    const-string v2, "text"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 31
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/ListSelectorAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/ListSelectorAttr;-><init>()V

    const-string v2, "listSelector"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 32
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/DividerAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/DividerAttr;-><init>()V

    const-string v2, "divider"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 33
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/ImageViewSrcAttr;-><init>()V

    const-string v2, "src"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 34
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/ThumbAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/ThumbAttr;-><init>()V

    const-string v2, "thumb"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 35
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/ProgressDrawableAttr;-><init>()V

    const-string v2, "progressDrawable"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 36
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/ButtonAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/ButtonAttr;-><init>()V

    const-string v2, "button"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 37
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    new-instance v1, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;

    invoke-direct {v1}, Lcom/hcn/skin/support/app/attr/PopupBackgroundAttr;-><init>()V

    const-string v2, "popupBackground"

    invoke-virtual {v0, v2, v1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 38
    invoke-static {}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->makeDrawableAttr()V

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 23
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static addSupportAttr(Ljava/lang/String;Lcom/hcn/skin/support/app/attr/base/SkinAttr;)V
    .locals 1

    .line 78
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    invoke-virtual {v0, p0, p1}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method public static get(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Lcom/hcn/skin/support/app/attr/base/SkinAttr;
    .locals 1

    .line 49
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    invoke-virtual {v0, p0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    invoke-virtual {v0}, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->clone()Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    move-result-object v0

    if-nez v0, :cond_0

    const/4 p0, 0x0

    return-object p0

    .line 53
    :cond_0
    iput-object p0, v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrName:Ljava/lang/String;

    .line 54
    iput p1, v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueRefId:I

    .line 55
    iput-object p2, v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueRefName:Ljava/lang/String;

    .line 56
    iput-object p3, v0, Lcom/hcn/skin/support/app/attr/base/SkinAttr;->attrValueTypeName:Ljava/lang/String;

    return-object v0
.end method

.method public static isSupportedAttr(Ljava/lang/String;)Z
    .locals 1

    .line 68
    sget-object v0, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    invoke-virtual {v0, p0}, Ljava/util/HashMap;->containsKey(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method private static makeDrawableAttr()V
    .locals 6

    const-string v0, "drawableTop"

    const-string v1, "drawableBottom"

    const-string v2, "drawableLeft"

    const-string v3, "drawableRight"

    const-string v4, "drawableStart"

    const-string v5, "drawableEnd"

    .line 42
    filled-new-array/range {v0 .. v5}, [Ljava/lang/String;

    move-result-object v0

    const/4 v1, 0x0

    :goto_0
    const/4 v2, 0x6

    if-ge v1, v2, :cond_0

    .line 44
    sget-object v2, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->sSupportAttr:Ljava/util/HashMap;

    aget-object v3, v0, v1

    new-instance v4, Lcom/hcn/skin/support/app/attr/DrawableAttr;

    aget-object v5, v0, v1

    invoke-direct {v4, v5}, Lcom/hcn/skin/support/app/attr/DrawableAttr;-><init>(Ljava/lang/String;)V

    invoke-virtual {v2, v3, v4}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_0
    return-void
.end method
