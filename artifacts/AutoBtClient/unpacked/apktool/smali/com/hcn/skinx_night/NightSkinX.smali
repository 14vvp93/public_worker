.class public final Lcom/hcn/skinx_night/NightSkinX;
.super Ljava/lang/Object;
.source "NightSkinX.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skinx_night/NightSkinX$ResTypeName;
    }
.end annotation


# static fields
.field public static final NIGHT_RES_SUFFIX:Ljava/lang/String; = "_night"

.field private static final TAG:Ljava/lang/String; = "NightSkinX"

.field private static mResourcesRef:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/hcn/skin/share/ISkinCompatResources;",
            ">;"
        }
    .end annotation
.end field

.field private static sResInfoCache:Lcom/hcn/skinx_night/ResInfo;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 191
    new-instance v0, Lcom/hcn/skinx_night/ResInfo;

    invoke-direct {v0}, Lcom/hcn/skinx_night/ResInfo;-><init>()V

    sput-object v0, Lcom/hcn/skinx_night/NightSkinX;->sResInfoCache:Lcom/hcn/skinx_night/ResInfo;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 32
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static findNightResInfo(Lcom/hcn/skinx_night/ResInfo;I)Lcom/hcn/skinx_night/ResInfo;
    .locals 3

    .line 116
    invoke-virtual {p0}, Lcom/hcn/skinx_night/ResInfo;->reset()V

    .line 119
    :try_start_0
    sget-object v0, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v0, :cond_0

    const/4 p0, 0x0

    return-object p0

    .line 125
    :cond_0
    invoke-interface {v0}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    .line 128
    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v2

    .line 129
    invoke-virtual {v1, p1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object p1

    .line 132
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v1, "_night"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    .line 133
    iput-object v2, p0, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    .line 134
    iput-object p1, p0, Lcom/hcn/skinx_night/ResInfo;->name:Ljava/lang/String;

    .line 137
    invoke-interface {v0, p1, v2}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResId(Ljava/lang/String;Ljava/lang/String;)I

    move-result p1

    if-lez p1, :cond_1

    .line 139
    iput p1, p0, Lcom/hcn/skinx_night/ResInfo;->id:I
    :try_end_0
    .catch Landroid/content/res/Resources$NotFoundException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 142
    :catch_0
    sget-object p1, Lcom/hcn/skinx_night/NightSkinX;->TAG:Ljava/lang/String;

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/Object;

    const/4 v1, 0x0

    const-string v2, "This is an illegal resource ID."

    aput-object v2, v0, v1

    invoke-static {p1, v0}, Lcom/hcn/common/misc/LogUtils;->eTag(Ljava/lang/String;[Ljava/lang/Object;)V

    :cond_1
    :goto_0
    return-object p0
.end method

.method public static isColor(Ljava/lang/String;)Z
    .locals 1

    const-string v0, "color"

    .line 77
    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method

.method public static isDrawable(Ljava/lang/String;)Z
    .locals 1

    const-string v0, "drawable"

    .line 65
    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_1

    const-string v0, "mipmap"

    .line 66
    invoke-virtual {v0, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    if-eqz p0, :cond_0

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    goto :goto_1

    :cond_1
    :goto_0
    const/4 p0, 0x1

    :goto_1
    return p0
.end method

.method private static isValidResource(Ljava/lang/String;I)Z
    .locals 0

    if-lez p1, :cond_0

    .line 220
    invoke-static {p0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p0

    if-nez p0, :cond_0

    const/4 p0, 0x1

    goto :goto_0

    :cond_0
    const/4 p0, 0x0

    :goto_0
    return p0
.end method

.method private static nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;
    .locals 1

    .line 201
    sget-object v0, Lcom/hcn/skinx_night/NightSkinX;->sResInfoCache:Lcom/hcn/skinx_night/ResInfo;

    invoke-virtual {v0}, Lcom/hcn/skinx_night/ResInfo;->reset()V

    .line 202
    invoke-virtual {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->resType4Attr(I)Ljava/lang/String;

    move-result-object v0

    .line 203
    invoke-virtual {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->nightResId4Attr(I)I

    move-result p0

    .line 204
    invoke-static {v0, p0}, Lcom/hcn/skinx_night/NightSkinX;->isValidResource(Ljava/lang/String;I)Z

    move-result p1

    if-eqz p1, :cond_0

    .line 205
    sget-object p1, Lcom/hcn/skinx_night/NightSkinX;->sResInfoCache:Lcom/hcn/skinx_night/ResInfo;

    iput-object v0, p1, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    .line 206
    sget-object p1, Lcom/hcn/skinx_night/NightSkinX;->sResInfoCache:Lcom/hcn/skinx_night/ResInfo;

    iput p0, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    .line 207
    sget-object p0, Lcom/hcn/skinx_night/NightSkinX;->sResInfoCache:Lcom/hcn/skinx_night/ResInfo;

    return-object p0

    :cond_0
    const/4 p0, 0x0

    return-object p0
.end method

.method public static setSkinCompatResources(Lcom/hcn/skin/share/ISkinCompatResources;)V
    .locals 1

    .line 89
    sget-object v0, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    if-nez v0, :cond_0

    .line 93
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p0}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    sput-object v0, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    return-void

    .line 90
    :cond_0
    new-instance p0, Ljava/lang/RuntimeException;

    const-string v0, "The \'setSkinCompatResources(...)\' method is repeatedly called!"

    invoke-direct {p0, v0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/String;)V

    throw p0
.end method

.method private static updateNightBaseAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 2

    const/4 v0, 0x1

    .line 227
    invoke-static {p1, v0}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v0

    if-eqz v0, :cond_0

    .line 229
    iget v0, v0, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {p0, v0}, Landroid/view/View;->setBackgroundResource(I)V

    .line 232
    :cond_0
    invoke-virtual {p1}, Lcom/hcn/skinx_night/NightAttrs;->widgetType()I

    move-result v0

    const/4 v1, 0x7

    if-eq v0, v1, :cond_1

    const/16 v1, 0x8

    if-eq v0, v1, :cond_1

    goto :goto_0

    .line 236
    :cond_1
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1d

    if-lt v0, v1, :cond_2

    .line 237
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightScrollBarAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    :cond_2
    :goto_0
    return-void
.end method

.method public static updateNightDecoration(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 2

    .line 157
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightBaseAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    .line 159
    invoke-virtual {p1}, Lcom/hcn/skinx_night/NightAttrs;->widgetType()I

    move-result v0

    const/4 v1, 0x7

    if-eq v0, v1, :cond_0

    packed-switch v0, :pswitch_data_0

    goto :goto_0

    .line 172
    :pswitch_0
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightSeekBarAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    goto :goto_0

    .line 168
    :pswitch_1
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightTextAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    goto :goto_0

    .line 162
    :pswitch_2
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightImageAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    goto :goto_0

    .line 175
    :cond_0
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightListViewAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V

    :goto_0
    return-void

    :pswitch_data_0
    .packed-switch 0x20
        :pswitch_2
        :pswitch_2
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_1
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method private static updateNightImageAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 1

    .line 294
    instance-of v0, p0, Landroid/widget/ImageView;

    if-nez v0, :cond_0

    return-void

    .line 298
    :cond_0
    check-cast p0, Landroid/widget/ImageView;

    const/4 v0, 0x4

    .line 301
    invoke-static {p1, v0}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object p1

    if-eqz p1, :cond_1

    .line 303
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {p0, p1}, Landroid/widget/ImageView;->setImageResource(I)V

    :cond_1
    return-void
.end method

.method private static updateNightListViewAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 3

    .line 485
    instance-of v0, p0, Landroid/widget/ListView;

    if-nez v0, :cond_0

    return-void

    .line 489
    :cond_0
    check-cast p0, Landroid/widget/ListView;

    .line 490
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    .line 491
    sget-object v1, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v1, :cond_1

    return-void

    .line 497
    :cond_1
    invoke-interface {v1}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    const/16 v2, 0x100

    .line 500
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object p1

    if-eqz p1, :cond_3

    .line 502
    iget-object v2, p1, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v2}, Lcom/hcn/skinx_night/NightSkinX;->isDrawable(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_2

    .line 503
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    if-eqz p1, :cond_3

    .line 505
    invoke-virtual {p0, p1}, Landroid/widget/ListView;->setDivider(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 507
    :cond_2
    iget-object v2, p1, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v2}, Lcom/hcn/skinx_night/NightSkinX;->isColor(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_3

    .line 508
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result p1

    if-lez p1, :cond_3

    .line 510
    new-instance v0, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v0, p1}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 511
    invoke-virtual {p0, v0}, Landroid/widget/ListView;->setDivider(Landroid/graphics/drawable/Drawable;)V

    :cond_3
    :goto_0
    return-void
.end method

.method private static updateNightScrollBarAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 4

    .line 252
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    .line 253
    sget-object v1, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v1, :cond_0

    return-void

    .line 259
    :cond_0
    invoke-interface {v1}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    const/16 v2, 0x8

    .line 262
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_1

    .line 264
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    .line 265
    invoke-virtual {p0, v2}, Landroid/view/View;->setVerticalScrollbarThumbDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_1
    const/16 v2, 0x10

    .line 269
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 271
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    .line 272
    invoke-virtual {p0, v2}, Landroid/view/View;->setVerticalScrollbarTrackDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_2
    const/16 v2, 0x20

    .line 276
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_3

    .line 278
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    .line 279
    invoke-virtual {p0, v2}, Landroid/view/View;->setHorizontalScrollbarThumbDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_3
    const/16 v2, 0x40

    .line 283
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object p1

    if-eqz p1, :cond_4

    .line 285
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    .line 286
    invoke-virtual {p0, p1}, Landroid/view/View;->setHorizontalScrollbarTrackDrawable(Landroid/graphics/drawable/Drawable;)V

    :cond_4
    return-void
.end method

.method private static updateNightSeekBarAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 5

    .line 431
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    .line 432
    sget-object v1, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v1, :cond_0

    return-void

    .line 438
    :cond_0
    invoke-interface {v1}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    const/16 v2, 0x400

    .line 441
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 443
    instance-of v3, p0, Landroid/widget/ProgressBar;

    if-eqz v3, :cond_2

    .line 444
    move-object v3, p0

    check-cast v3, Landroid/widget/ProgressBar;

    .line 445
    iget-object v4, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v4}, Lcom/hcn/skinx_night/NightSkinX;->isDrawable(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_1

    .line 446
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v4

    invoke-virtual {v1, v2, v4}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    if-eqz v2, :cond_2

    .line 448
    invoke-virtual {v3, v2}, Landroid/widget/ProgressBar;->setProgressDrawableTiled(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0

    .line 450
    :cond_1
    iget-object v4, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v4}, Lcom/hcn/skinx_night/NightSkinX;->isColor(Ljava/lang/String;)Z

    move-result v4

    if-eqz v4, :cond_2

    .line 451
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v4

    invoke-virtual {v1, v2, v4}, Landroid/content/res/Resources;->getColor(ILandroid/content/res/Resources$Theme;)I

    move-result v2

    if-lez v2, :cond_2

    .line 453
    new-instance v4, Landroid/graphics/drawable/ColorDrawable;

    invoke-direct {v4, v2}, Landroid/graphics/drawable/ColorDrawable;-><init>(I)V

    .line 454
    invoke-virtual {v3, v4}, Landroid/widget/ProgressBar;->setProgressDrawableTiled(Landroid/graphics/drawable/Drawable;)V

    .line 460
    :cond_2
    :goto_0
    invoke-virtual {p1}, Lcom/hcn/skinx_night/NightAttrs;->widgetType()I

    move-result v2

    const/16 v3, 0x27

    if-eq v2, v3, :cond_3

    goto :goto_1

    :cond_3
    const/16 v2, 0x200

    .line 463
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object p1

    if-eqz p1, :cond_4

    .line 464
    iget-object v2, p1, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v2}, Lcom/hcn/skinx_night/NightSkinX;->isDrawable(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_4

    .line 465
    instance-of v2, p0, Landroid/widget/SeekBar;

    if-eqz v2, :cond_4

    .line 466
    check-cast p0, Landroid/widget/SeekBar;

    .line 467
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    if-eqz p1, :cond_4

    .line 469
    invoke-virtual {p0, p1}, Landroid/widget/SeekBar;->setThumb(Landroid/graphics/drawable/Drawable;)V

    :cond_4
    :goto_1
    return-void
.end method

.method private static updateNightTextAttrRes(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 4

    .line 312
    instance-of v0, p0, Landroid/widget/TextView;

    if-nez v0, :cond_0

    return-void

    .line 316
    :cond_0
    check-cast p0, Landroid/widget/TextView;

    .line 317
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    .line 318
    sget-object v1, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v1, :cond_1

    return-void

    .line 324
    :cond_1
    invoke-interface {v1}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    const/4 v2, 0x2

    .line 327
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_3

    .line 329
    iget-object v3, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v3}, Lcom/hcn/skinx_night/NightSkinX;->isDrawable(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_2

    .line 330
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    .line 331
    invoke-virtual {v1, v2}, Landroid/content/res/Resources;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v2

    .line 332
    invoke-virtual {p0, v2}, Landroid/widget/TextView;->setTextColor(Landroid/content/res/ColorStateList;)V

    goto :goto_0

    .line 333
    :cond_2
    iget-object v3, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v3}, Lcom/hcn/skinx_night/NightSkinX;->isColor(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_3

    .line 334
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    .line 335
    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object v2

    .line 336
    invoke-virtual {p0, v2}, Landroid/widget/TextView;->setTextColor(Landroid/content/res/ColorStateList;)V

    :cond_3
    :goto_0
    const/16 v2, 0x80

    .line 341
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    if-eqz v2, :cond_5

    .line 343
    iget-object v3, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v3}, Lcom/hcn/skinx_night/NightSkinX;->isDrawable(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_4

    .line 344
    iget v0, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    .line 345
    invoke-virtual {v1, v0}, Landroid/content/res/Resources;->getColorStateList(I)Landroid/content/res/ColorStateList;

    move-result-object v0

    .line 346
    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setTextColor(Landroid/content/res/ColorStateList;)V

    goto :goto_1

    .line 347
    :cond_4
    iget-object v3, v2, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-static {v3}, Lcom/hcn/skinx_night/NightSkinX;->isColor(Ljava/lang/String;)Z

    move-result v3

    if-eqz v3, :cond_5

    .line 348
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    .line 349
    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, v2, v0}, Landroid/content/res/Resources;->getColorStateList(ILandroid/content/res/Resources$Theme;)Landroid/content/res/ColorStateList;

    move-result-object v0

    .line 350
    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setHintTextColor(Landroid/content/res/ColorStateList;)V

    .line 355
    :cond_5
    :goto_1
    invoke-static {p0, p1}, Lcom/hcn/skinx_night/NightSkinX;->updateNightTextDrawableAttrRes(Landroid/widget/TextView;Lcom/hcn/skinx_night/NightAttrs;)V

    return-void
.end method

.method private static updateNightTextDrawableAttrRes(Landroid/widget/TextView;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 9

    .line 362
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v0

    .line 363
    sget-object v1, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/share/ISkinCompatResources;

    if-eqz v1, :cond_0

    return-void

    .line 369
    :cond_0
    invoke-interface {v1}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v1

    const/16 v2, 0x100

    .line 376
    invoke-static {p1, v2}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v2

    const/4 v3, 0x0

    if-eqz v2, :cond_1

    .line 378
    iget v2, v2, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v4

    invoke-virtual {v1, v2, v4}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v2

    goto :goto_0

    :cond_1
    move-object v2, v3

    :goto_0
    const/16 v4, 0x200

    .line 382
    invoke-static {p1, v4}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v4

    if-eqz v4, :cond_2

    .line 384
    iget v4, v4, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v5

    invoke-virtual {v1, v4, v5}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v4

    goto :goto_1

    :cond_2
    move-object v4, v3

    :goto_1
    const/16 v5, 0x400

    .line 388
    invoke-static {p1, v5}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v5

    if-eqz v5, :cond_3

    .line 390
    iget v5, v5, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v6

    invoke-virtual {v1, v5, v6}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v5

    goto :goto_2

    :cond_3
    move-object v5, v3

    :goto_2
    const/16 v6, 0x800

    .line 394
    invoke-static {p1, v6}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v6

    if-eqz v6, :cond_4

    .line 396
    iget v6, v6, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v7

    invoke-virtual {v1, v6, v7}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v6

    goto :goto_3

    :cond_4
    move-object v6, v3

    :goto_3
    const/16 v7, 0x1000

    .line 400
    invoke-static {p1, v7}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v7

    if-eqz v7, :cond_5

    .line 402
    iget v7, v7, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v8

    invoke-virtual {v1, v7, v8}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v7

    goto :goto_4

    :cond_5
    move-object v7, v3

    :goto_4
    const/16 v8, 0x2000

    .line 406
    invoke-static {p1, v8}, Lcom/hcn/skinx_night/NightSkinX;->nightResInfo(Lcom/hcn/skinx_night/NightAttrs;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object p1

    if-eqz p1, :cond_6

    .line 408
    iget p1, p1, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v0}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v0

    invoke-virtual {v1, p1, v0}, Landroid/content/res/Resources;->getDrawable(ILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;

    move-result-object v3

    :cond_6
    if-nez v7, :cond_9

    if-eqz v3, :cond_7

    goto :goto_5

    :cond_7
    if-nez v2, :cond_8

    if-nez v4, :cond_8

    if-nez v5, :cond_8

    if-eqz v6, :cond_a

    .line 421
    :cond_8
    invoke-virtual {p0, v5, v2, v6, v4}, Landroid/widget/TextView;->setCompoundDrawablesWithIntrinsicBounds(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    goto :goto_6

    .line 414
    :cond_9
    :goto_5
    invoke-virtual {p0, v7, v2, v3, v4}, Landroid/widget/TextView;->setCompoundDrawablesRelativeWithIntrinsicBounds(Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;Landroid/graphics/drawable/Drawable;)V

    :cond_a
    :goto_6
    return-void
.end method

.method public static useSkinPackage()Z
    .locals 2

    .line 101
    sget-object v0, Lcom/hcn/skinx_night/NightSkinX;->mResourcesRef:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/skin/share/ISkinCompatResources;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    .line 103
    invoke-interface {v0}, Lcom/hcn/skin/share/ISkinCompatResources;->getSkinResources()Landroid/content/res/Resources;

    move-result-object v0

    if-eqz v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method
