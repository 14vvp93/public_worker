.class public Lcom/hcn/skinx_night/NightAttrs;
.super Ljava/lang/Object;
.source "NightAttrs.java"


# instance fields
.field private mAttrDayResIds:Landroid/util/SparseIntArray;

.field private mAttrNightResIds:Landroid/util/SparseIntArray;

.field private mAttrResTypes:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mAttributeMark:I

.field private mNightResIdResolved:Z

.field private mWidgetType:I


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 38
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 21
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    .line 24
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    .line 27
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    .line 30
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    .line 33
    new-instance v1, Landroid/util/SparseArray;

    invoke-direct {v1}, Landroid/util/SparseArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    .line 36
    iput-boolean v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mNightResIdResolved:Z

    return-void
.end method

.method public constructor <init>(Landroid/util/AttributeSet;II)V
    .locals 2

    .line 41
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 21
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    .line 24
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    .line 27
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    .line 30
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    .line 33
    new-instance v1, Landroid/util/SparseArray;

    invoke-direct {v1}, Landroid/util/SparseArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    .line 36
    iput-boolean v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mNightResIdResolved:Z

    .line 42
    iput p2, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    .line 43
    iput p3, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    .line 45
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseDefaultAttrRes(Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Lcom/hcn/skinx_night/NightAttrs;)V
    .locals 2

    .line 49
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 21
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    .line 24
    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    .line 27
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    .line 30
    new-instance v1, Landroid/util/SparseIntArray;

    invoke-direct {v1}, Landroid/util/SparseIntArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    .line 33
    new-instance v1, Landroid/util/SparseArray;

    invoke-direct {v1}, Landroid/util/SparseArray;-><init>()V

    iput-object v1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    .line 36
    iput-boolean v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mNightResIdResolved:Z

    .line 50
    iget v0, p1, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    .line 51
    iget v0, p1, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    iput v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    .line 54
    iget-object v0, p1, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    if-eqz v0, :cond_0

    .line 55
    invoke-virtual {v0}, Landroid/util/SparseIntArray;->clone()Landroid/util/SparseIntArray;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    .line 59
    :cond_0
    iget-object v0, p1, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    if-eqz v0, :cond_1

    .line 60
    invoke-virtual {v0}, Landroid/util/SparseIntArray;->clone()Landroid/util/SparseIntArray;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    .line 64
    :cond_1
    iget-object p1, p1, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    if-eqz p1, :cond_2

    .line 65
    invoke-virtual {p1}, Landroid/util/SparseArray;->clone()Landroid/util/SparseArray;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    :cond_2
    return-void
.end method

.method private parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V
    .locals 2

    .line 200
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    and-int/2addr v0, p2

    if-ne v0, p2, :cond_0

    const/4 v0, 0x0

    const-string v1, "http://schemas.android.com/apk/res/android"

    .line 201
    invoke-interface {p1, v1, p3, v0}, Landroid/util/AttributeSet;->getAttributeResourceValue(Ljava/lang/String;Ljava/lang/String;I)I

    move-result p1

    if-eqz p1, :cond_0

    .line 204
    iget-object p3, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    invoke-virtual {p3, p2, p1}, Landroid/util/SparseIntArray;->put(II)V

    :cond_0
    return-void
.end method

.method private parseBaseAttrRes(Landroid/util/AttributeSet;)V
    .locals 3

    const/4 v0, 0x1

    const-string v1, "background"

    .line 213
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    .line 217
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    const/4 v1, 0x7

    const/16 v2, 0x8

    if-eq v0, v1, :cond_0

    if-eq v0, v2, :cond_0

    goto :goto_0

    :cond_0
    const-string v0, "scrollbarThumbVertical"

    .line 221
    invoke-direct {p0, p1, v2, v0}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x10

    const-string v1, "scrollbarTrackVertical"

    .line 226
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x20

    const-string v1, "scrollbarThumbHorizontal"

    .line 231
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x40

    const-string v1, "scrollbarTrackHorizontal"

    .line 236
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    :goto_0
    return-void
.end method

.method private parseDefaultAttrRes(Landroid/util/AttributeSet;)V
    .locals 1

    .line 159
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    packed-switch v0, :pswitch_data_0

    packed-switch v0, :pswitch_data_1

    goto :goto_0

    .line 182
    :pswitch_0
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseSeekBarAttrRes(Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 178
    :pswitch_1
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseTextAttrRes(Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 172
    :pswitch_2
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseImageAttrRes(Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 185
    :pswitch_3
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseListViewAttrRes(Landroid/util/AttributeSet;)V

    goto :goto_0

    .line 168
    :pswitch_4
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseBaseAttrRes(Landroid/util/AttributeSet;)V

    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_4
        :pswitch_4
        :pswitch_4
        :pswitch_4
        :pswitch_4
        :pswitch_4
        :pswitch_4
        :pswitch_3
        :pswitch_4
    .end packed-switch

    :pswitch_data_1
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

.method private parseImageAttrRes(Landroid/util/AttributeSet;)V
    .locals 2

    .line 249
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseBaseAttrRes(Landroid/util/AttributeSet;)V

    const/4 v0, 0x4

    const-string v1, "src"

    .line 252
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    return-void
.end method

.method private parseListViewAttrRes(Landroid/util/AttributeSet;)V
    .locals 2

    .line 313
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseBaseAttrRes(Landroid/util/AttributeSet;)V

    const/16 v0, 0x100

    const-string v1, "divider"

    .line 316
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    return-void
.end method

.method private parseSeekBarAttrRes(Landroid/util/AttributeSet;)V
    .locals 2

    .line 293
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseBaseAttrRes(Landroid/util/AttributeSet;)V

    const/16 v0, 0x400

    const-string v1, "progressDrawable"

    .line 296
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    .line 300
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    const/16 v1, 0x27

    if-eq v0, v1, :cond_0

    goto :goto_0

    :cond_0
    const/16 v0, 0x200

    const-string v1, "thumb"

    .line 303
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    :goto_0
    return-void
.end method

.method private parseTextAttrRes(Landroid/util/AttributeSet;)V
    .locals 2

    .line 259
    invoke-direct {p0, p1}, Lcom/hcn/skinx_night/NightAttrs;->parseBaseAttrRes(Landroid/util/AttributeSet;)V

    const/4 v0, 0x2

    const-string v1, "textColor"

    .line 262
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x80

    const-string v1, "textColorHint"

    .line 267
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x100

    const-string v1, "drawableTop"

    .line 272
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x200

    const-string v1, "drawableBottom"

    .line 277
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x1000

    const-string v1, "drawableStart"

    .line 282
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    const/16 v0, 0x2000

    const-string v1, "drawableEnd"

    .line 287
    invoke-direct {p0, p1, v0, v1}, Lcom/hcn/skinx_night/NightAttrs;->parseAndroidAttribute(Landroid/util/AttributeSet;ILjava/lang/String;)V

    return-void
.end method


# virtual methods
.method public attributeMark()I
    .locals 1

    .line 90
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    return v0
.end method

.method public isNightValid()Z
    .locals 1

    .line 119
    iget-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v0}, Landroid/util/SparseIntArray;->size()I

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public isValid()Z
    .locals 1

    .line 111
    iget-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v0}, Landroid/util/SparseIntArray;->size()I

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    goto :goto_0

    :cond_0
    const/4 v0, 0x0

    :goto_0
    return v0
.end method

.method public nightResId4Attr(I)I
    .locals 2

    .line 129
    invoke-virtual {p0}, Lcom/hcn/skinx_night/NightAttrs;->isNightValid()Z

    move-result v0

    const/4 v1, 0x0

    if-nez v0, :cond_0

    return v1

    .line 133
    :cond_0
    iget-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v0, p1, v1}, Landroid/util/SparseIntArray;->get(II)I

    move-result p1

    return p1
.end method

.method public nightResIdResolved()Z
    .locals 1

    .line 103
    iget-boolean v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mNightResIdResolved:Z

    return v0
.end method

.method public resType4Attr(I)Ljava/lang/String;
    .locals 2

    .line 151
    invoke-virtual {p0}, Lcom/hcn/skinx_night/NightAttrs;->isNightValid()Z

    move-result v0

    const-string v1, "none"

    if-nez v0, :cond_0

    return-object v1

    .line 155
    :cond_0
    iget-object v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    invoke-virtual {v0, p1, v1}, Landroid/util/SparseArray;->get(ILjava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    return-object p1
.end method

.method public resolveNightResIds()V
    .locals 6

    .line 326
    invoke-virtual {p0}, Lcom/hcn/skinx_night/NightAttrs;->isValid()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    .line 330
    :cond_0
    invoke-virtual {p0}, Lcom/hcn/skinx_night/NightAttrs;->nightResIdResolved()Z

    move-result v0

    if-eqz v0, :cond_1

    return-void

    :cond_1
    const/4 v0, 0x1

    .line 335
    iput-boolean v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mNightResIdResolved:Z

    .line 336
    new-instance v0, Lcom/hcn/skinx_night/ResInfo;

    invoke-direct {v0}, Lcom/hcn/skinx_night/ResInfo;-><init>()V

    const/4 v1, 0x0

    .line 339
    :goto_0
    iget-object v2, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v2}, Landroid/util/SparseIntArray;->size()I

    move-result v2

    if-ge v1, v2, :cond_2

    .line 340
    iget-object v2, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v2, v1}, Landroid/util/SparseIntArray;->keyAt(I)I

    move-result v2

    .line 341
    iget-object v3, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrDayResIds:Landroid/util/SparseIntArray;

    invoke-virtual {v3, v1}, Landroid/util/SparseIntArray;->valueAt(I)I

    move-result v3

    .line 344
    invoke-static {v0, v3}, Lcom/hcn/skinx_night/NightSkinX;->findNightResInfo(Lcom/hcn/skinx_night/ResInfo;I)Lcom/hcn/skinx_night/ResInfo;

    move-result-object v3

    .line 345
    iget-object v4, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrResTypes:Landroid/util/SparseArray;

    iget-object v5, v3, Lcom/hcn/skinx_night/ResInfo;->type:Ljava/lang/String;

    invoke-virtual {v4, v2, v5}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 346
    iget-object v4, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttrNightResIds:Landroid/util/SparseIntArray;

    iget v3, v3, Lcom/hcn/skinx_night/ResInfo;->id:I

    invoke-virtual {v4, v2, v3}, Landroid/util/SparseIntArray;->put(II)V

    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_2
    return-void
.end method

.method public setAttributeMark(I)V
    .locals 0

    .line 74
    iput p1, p0, Lcom/hcn/skinx_night/NightAttrs;->mAttributeMark:I

    return-void
.end method

.method public setWidgetType(I)V
    .locals 0

    .line 70
    iput p1, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    return-void
.end method

.method public widgetType()I
    .locals 1

    .line 82
    iget v0, p0, Lcom/hcn/skinx_night/NightAttrs;->mWidgetType:I

    return v0
.end method
