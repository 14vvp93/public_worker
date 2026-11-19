.class public Lcom/hcn/skinx_night/NightX;
.super Ljava/lang/Object;
.source "NightX.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skinx_night/NightX$Attribute;,
        Lcom/hcn/skinx_night/NightX$Widget;
    }
.end annotation


# static fields
.field static final synthetic $assertionsDisabled:Z = false

.field public static final ANDROID_RESOURCES:Ljava/lang/String; = "http://schemas.android.com/apk/res/android"

.field public static final DARK_TAG_PREFIX:Ljava/lang/String; = "SkinX#"

.field public static final DARK_TAG_SEGMENTS_SEPARATOR:Ljava/lang/String; = "#"

.field public static final DARK_TAG_SEGMENTS_SIZE:I = 0x3

.field private static final TAG:Ljava/lang/String; = "NightX"

.field public static final TAG_KEY_BASE:I = -0x1fff0000

.field private static final WIDGET_NAMES:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 101
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    sput-object v0, Lcom/hcn/skinx_night/NightX;->WIDGET_NAMES:Landroid/util/SparseArray;

    const/4 v1, 0x0

    const-string v2, "View"

    .line 104
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x1

    const-string v2, "LinearLayout"

    .line 105
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x2

    const-string v2, "FrameLayout"

    .line 106
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x3

    const-string v2, "RelativeLayout"

    .line 107
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x4

    const-string v2, "ConstraintLayout"

    .line 108
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x5

    const-string v2, "GridLayout"

    .line 109
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x6

    const-string v2, "RadioGroup"

    .line 110
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/4 v1, 0x7

    const-string v2, "ListView"

    .line 111
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x8

    const-string v2, "RecyclerView"

    .line 112
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x20

    const-string v2, "ImageView"

    .line 114
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x21

    const-string v2, "ImageButton"

    .line 115
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x22

    const-string v2, "TextView"

    .line 116
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x23

    const-string v2, "Button"

    .line 117
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x24

    const-string v2, "RadioButton"

    .line 118
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x25

    const-string v2, "EditText"

    .line 119
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x26

    const-string v2, "ProgressBar"

    .line 120
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    const/16 v1, 0x27

    const-string v2, "SeekBar"

    .line 121
    invoke-virtual {v0, v1, v2}, Landroid/util/SparseArray;->append(ILjava/lang/Object;)V

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 47
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static buildViewFromNightAttr(Landroid/content/Context;Landroid/util/AttributeSet;Lcom/hcn/skinx_night/NightAttrs;)Landroid/view/View;
    .locals 6

    .line 309
    invoke-static {p2}, Ljava/util/Objects;->isNull(Ljava/lang/Object;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 315
    :cond_0
    sget-object v0, Lcom/hcn/skinx_night/NightX;->TAG:Ljava/lang/String;

    const/4 v2, 0x1

    new-array v2, v2, [Ljava/lang/Object;

    const/4 v3, 0x0

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "buildView: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    .line 316
    invoke-virtual {p2}, Lcom/hcn/skinx_night/NightAttrs;->widgetType()I

    move-result v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, ", "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {p2}, Lcom/hcn/skinx_night/NightAttrs;->attributeMark()I

    move-result v5

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    aput-object v4, v2, v3

    .line 315
    invoke-static {v0, v2}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    .line 319
    invoke-virtual {p2}, Lcom/hcn/skinx_night/NightAttrs;->widgetType()I

    move-result p2

    packed-switch p2, :pswitch_data_0

    packed-switch p2, :pswitch_data_1

    goto/16 :goto_0

    .line 371
    :pswitch_0
    new-instance v1, Landroid/widget/SeekBar;

    invoke-direct {v1, p0, p1}, Landroid/widget/SeekBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto/16 :goto_0

    .line 368
    :pswitch_1
    new-instance v1, Landroid/widget/ProgressBar;

    invoke-direct {v1, p0, p1}, Landroid/widget/ProgressBar;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 365
    :pswitch_2
    new-instance v1, Landroid/widget/EditText;

    invoke-direct {v1, p0, p1}, Landroid/widget/EditText;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 362
    :pswitch_3
    new-instance v1, Landroid/widget/RadioButton;

    invoke-direct {v1, p0, p1}, Landroid/widget/RadioButton;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 359
    :pswitch_4
    new-instance v1, Landroid/widget/Button;

    invoke-direct {v1, p0, p1}, Landroid/widget/Button;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 356
    :pswitch_5
    new-instance v1, Landroid/widget/TextView;

    invoke-direct {v1, p0, p1}, Landroid/widget/TextView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 353
    :pswitch_6
    new-instance v1, Landroid/widget/ImageButton;

    invoke-direct {v1, p0, p1}, Landroid/widget/ImageButton;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 350
    :pswitch_7
    new-instance v1, Landroid/widget/ImageView;

    invoke-direct {v1, p0, p1}, Landroid/widget/ImageView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 346
    :pswitch_8
    new-instance v1, Landroidx/recyclerview/widget/RecyclerView;

    invoke-direct {v1, p0, p1}, Landroidx/recyclerview/widget/RecyclerView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 343
    :pswitch_9
    new-instance v1, Landroid/widget/ListView;

    invoke-direct {v1, p0, p1}, Landroid/widget/ListView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 340
    :pswitch_a
    new-instance v1, Landroid/widget/RadioGroup;

    invoke-direct {v1, p0, p1}, Landroid/widget/RadioGroup;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 337
    :pswitch_b
    new-instance v1, Landroid/widget/GridLayout;

    invoke-direct {v1, p0, p1}, Landroid/widget/GridLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 334
    :pswitch_c
    new-instance v1, Landroidx/constraintlayout/widget/ConstraintLayout;

    invoke-direct {v1, p0, p1}, Landroidx/constraintlayout/widget/ConstraintLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 331
    :pswitch_d
    new-instance v1, Landroid/widget/RelativeLayout;

    invoke-direct {v1, p0, p1}, Landroid/widget/RelativeLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 328
    :pswitch_e
    new-instance v1, Landroid/widget/FrameLayout;

    invoke-direct {v1, p0, p1}, Landroid/widget/FrameLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 325
    :pswitch_f
    new-instance v1, Landroid/widget/LinearLayout;

    invoke-direct {v1, p0, p1}, Landroid/widget/LinearLayout;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 322
    :pswitch_10
    new-instance v1, Landroid/view/View;

    invoke-direct {v1, p0, p1}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    :goto_0
    return-object v1

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_10
        :pswitch_f
        :pswitch_e
        :pswitch_d
        :pswitch_c
        :pswitch_b
        :pswitch_a
        :pswitch_9
        :pswitch_8
    .end packed-switch

    :pswitch_data_1
    .packed-switch 0x20
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public static getViewNightAttrs(Landroid/view/View;)Lcom/hcn/skinx_night/NightAttrs;
    .locals 1

    const/high16 v0, -0x1fff0000

    .line 278
    invoke-virtual {p0, v0}, Landroid/view/View;->getTag(I)Ljava/lang/Object;

    move-result-object p0

    if-eqz p0, :cond_0

    .line 280
    check-cast p0, Lcom/hcn/skinx_night/NightAttrs;

    return-object p0

    :cond_0
    const/4 p0, 0x0

    return-object p0
.end method

.method public static parseAttributeTag(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Lcom/hcn/skinx_night/NightAttrs;
    .locals 8

    const-string p1, "http://schemas.android.com/apk/res/android"

    const-string v0, "tag"

    .line 228
    invoke-interface {p2, p1, v0}, Landroid/util/AttributeSet;->getAttributeValue(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 229
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    return-object v1

    .line 233
    :cond_0
    invoke-static {p1}, Lcom/hcn/skinx_night/NightX;->supportDarkModeTag(Ljava/lang/String;)Z

    move-result v0

    if-nez v0, :cond_1

    return-object v1

    .line 237
    :cond_1
    sget-object v0, Lcom/hcn/skinx_night/NightX;->TAG:Ljava/lang/String;

    const/4 v2, 0x1

    new-array v3, v2, [Ljava/lang/Object;

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "name: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const-string v5, " tag: "

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    const/4 v5, 0x0

    aput-object v4, v3, v5

    invoke-static {v0, v3}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    const-string v3, "#"

    .line 238
    invoke-virtual {p1, v3}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object p1

    .line 239
    array-length v3, p1

    const/4 v4, 0x3

    const-string v6, "dark mode tag format error. (SkinX#01#0001)"

    if-eq v3, v4, :cond_2

    new-array p0, v2, [Ljava/lang/Object;

    aput-object v6, p0, v5

    .line 240
    invoke-static {v0, p0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-object v1

    .line 247
    :cond_2
    :try_start_0
    aget-object v3, p1, v2

    const/16 v4, 0x10

    invoke-static {v3, v4}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result v3

    const/4 v7, 0x2

    .line 248
    aget-object p1, p1, v7

    invoke-static {p1, v4}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;I)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    .line 255
    invoke-static {p0, v3}, Lcom/hcn/skinx_night/NightX;->widgetTypeVerification(Ljava/lang/String;I)Z

    move-result p0

    if-nez p0, :cond_3

    new-array p0, v2, [Ljava/lang/Object;

    const-string p1, "dark mode tag \'widgetType\' does not match component name."

    aput-object p1, p0, v5

    .line 256
    invoke-static {v0, p0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-object v1

    :cond_3
    if-nez p1, :cond_4

    new-array p0, v2, [Ljava/lang/Object;

    const-string p1, "dark mode tag \'attribute\' is an invalid value."

    aput-object p1, p0, v5

    .line 262
    invoke-static {v0, p0}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-object v1

    .line 266
    :cond_4
    new-instance p0, Lcom/hcn/skinx_night/NightAttrs;

    invoke-direct {p0, p2, v3, p1}, Lcom/hcn/skinx_night/NightAttrs;-><init>(Landroid/util/AttributeSet;II)V

    .line 267
    invoke-virtual {p0}, Lcom/hcn/skinx_night/NightAttrs;->isValid()Z

    move-result p1

    if-eqz p1, :cond_5

    move-object v1, p0

    :cond_5
    return-object v1

    .line 250
    :catch_0
    sget-object p0, Lcom/hcn/skinx_night/NightX;->TAG:Ljava/lang/String;

    new-array p1, v2, [Ljava/lang/Object;

    aput-object v6, p1, v5

    invoke-static {p0, p1}, Lcom/hcn/common/misc/LogUtils;->wTag(Ljava/lang/String;[Ljava/lang/Object;)V

    return-object v1
.end method

.method public static setNightAttrsTag(Landroid/view/View;Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 1

    if-eqz p0, :cond_0

    if-eqz p1, :cond_0

    const/high16 v0, -0x1fff0000

    .line 294
    invoke-virtual {p0, v0, p1}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    :cond_0
    return-void
.end method

.method private static supportDarkModeTag(Ljava/lang/String;)Z
    .locals 1

    const-string v0, "SkinX#"

    .line 201
    invoke-virtual {p0, v0}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result p0

    return p0
.end method

.method public static widgetTypeVerification(Ljava/lang/String;I)Z
    .locals 2

    .line 212
    sget-object v0, Lcom/hcn/skinx_night/NightX;->WIDGET_NAMES:Landroid/util/SparseArray;

    const-string v1, "none"

    invoke-virtual {v0, p1, v1}, Landroid/util/SparseArray;->get(ILjava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 213
    invoke-virtual {p0, p1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p0

    return p0
.end method
