.class public Lcom/hcn/common/utils/HSpanUtils;
.super Ljava/lang/Object;
.source "HSpanUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;,
        Lcom/hcn/common/utils/HSpanUtils$VerticalAlignSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomQuoteSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomBulletSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomLineHeightSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomTypefaceSpan;,
        Lcom/hcn/common/utils/HSpanUtils$ShaderSpan;,
        Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;,
        Lcom/hcn/common/utils/HSpanUtils$SpaceSpan;,
        Lcom/hcn/common/utils/HSpanUtils$CustomDynamicDrawableSpan;,
        Lcom/hcn/common/utils/HSpanUtils$Align;
    }
.end annotation


# static fields
.field public static final ALIGN_BASELINE:I = 0x1

.field public static final ALIGN_BOTTOM:I = 0x0

.field public static final ALIGN_CENTER:I = 0x2

.field public static final ALIGN_TOP:I = 0x3

.field private static final COLOR_DEFAULT:I = -0x1000001

.field private static final LINE_SEPARATOR:Ljava/lang/String;


# instance fields
.field private alignImage:I

.field private alignLine:I

.field private alignment:Landroid/text/Layout$Alignment;

.field private backgroundColor:I

.field private blurRadius:F

.field private bulletColor:I

.field private bulletGapWidth:I

.field private bulletRadius:I

.field private clickSpan:Landroid/text/style/ClickableSpan;

.field private first:I

.field private flag:I

.field private fontFamily:Ljava/lang/String;

.field private fontSize:I

.field private foregroundColor:I

.field private imageBitmap:Landroid/graphics/Bitmap;

.field private imageDrawable:Landroid/graphics/drawable/Drawable;

.field private imageResourceId:I

.field private imageUri:Landroid/net/Uri;

.field private isBold:Z

.field private isBoldItalic:Z

.field private isCreated:Z

.field private isItalic:Z

.field private isStrikethrough:Z

.field private isSubscript:Z

.field private isSuperscript:Z

.field private isUnderline:Z

.field private lineHeight:I

.field private mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

.field private mText:Ljava/lang/CharSequence;

.field private mTextView:Landroid/widget/TextView;

.field private mType:I

.field private final mTypeCharSequence:I

.field private final mTypeImage:I

.field private final mTypeSpace:I

.field private proportion:F

.field private quoteColor:I

.field private quoteGapWidth:I

.field private rest:I

.field private shader:Landroid/graphics/Shader;

.field private shadowColor:I

.field private shadowDx:F

.field private shadowDy:F

.field private shadowRadius:F

.field private spaceColor:I

.field private spaceSize:I

.field private spans:[Ljava/lang/Object;

.field private stripeWidth:I

.field private style:Landroid/graphics/BlurMaskFilter$Blur;

.field private typeface:Landroid/graphics/Typeface;

.field private url:Ljava/lang/String;

.field private verticalAlign:I

.field private xProportion:F


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "line.separator"

    .line 79
    invoke-static {v0}, Ljava/lang/System;->getProperty(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lcom/hcn/common/utils/HSpanUtils;->LINE_SEPARATOR:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 2

    .line 147
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x0

    .line 138
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTypeCharSequence:I

    const/4 v0, 0x1

    .line 139
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTypeImage:I

    const/4 v0, 0x2

    .line 140
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTypeSpace:I

    .line 148
    new-instance v0, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;-><init>(Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder-IA;)V

    iput-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    const-string v0, ""

    .line 149
    iput-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    const/4 v0, -0x1

    .line 150
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mType:I

    .line 151
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->setDefault()V

    return-void
.end method

.method private constructor <init>(Landroid/widget/TextView;)V
    .locals 0

    .line 143
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;-><init>()V

    .line 144
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->mTextView:Landroid/widget/TextView;

    return-void
.end method

.method private apply(I)V
    .locals 0

    .line 813
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->applyLast()V

    .line 814
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->mType:I

    return-void
.end method

.method private applyLast()V
    .locals 2

    .line 836
    iget-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isCreated:Z

    if-eqz v0, :cond_0

    return-void

    .line 840
    :cond_0
    iget v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mType:I

    if-nez v0, :cond_1

    .line 841
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->updateCharCharSequence()V

    goto :goto_0

    :cond_1
    const/4 v1, 0x1

    if-ne v0, v1, :cond_2

    .line 843
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->updateImage()V

    goto :goto_0

    :cond_2
    const/4 v1, 0x2

    if-ne v0, v1, :cond_3

    .line 845
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->updateSpace()V

    .line 847
    :cond_3
    :goto_0
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->setDefault()V

    return-void
.end method

.method private setDefault()V
    .locals 3

    const/16 v0, 0x21

    .line 155
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    const v0, -0x1000001

    .line 156
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->foregroundColor:I

    .line 157
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->backgroundColor:I

    const/4 v1, -0x1

    .line 158
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->lineHeight:I

    .line 159
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteColor:I

    .line 160
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->first:I

    .line 161
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletColor:I

    .line 162
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->fontSize:I

    const/high16 v0, -0x40800000    # -1.0f

    .line 163
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->proportion:F

    .line 164
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->xProportion:F

    const/4 v2, 0x0

    .line 165
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isStrikethrough:Z

    .line 166
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isUnderline:Z

    .line 167
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isSuperscript:Z

    .line 168
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isSubscript:Z

    .line 169
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isBold:Z

    .line 170
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isItalic:Z

    .line 171
    iput-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isBoldItalic:Z

    const/4 v2, 0x0

    .line 172
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->fontFamily:Ljava/lang/String;

    .line 173
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->typeface:Landroid/graphics/Typeface;

    .line 174
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignment:Landroid/text/Layout$Alignment;

    .line 175
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->verticalAlign:I

    .line 176
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->clickSpan:Landroid/text/style/ClickableSpan;

    .line 177
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->url:Ljava/lang/String;

    .line 178
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->blurRadius:F

    .line 179
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->shader:Landroid/graphics/Shader;

    .line 180
    iput v0, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowRadius:F

    .line 181
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->spans:[Ljava/lang/Object;

    .line 183
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageBitmap:Landroid/graphics/Bitmap;

    .line 184
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageDrawable:Landroid/graphics/drawable/Drawable;

    .line 185
    iput-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageUri:Landroid/net/Uri;

    .line 186
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->imageResourceId:I

    .line 188
    iput v1, p0, Lcom/hcn/common/utils/HSpanUtils;->spaceSize:I

    return-void
.end method

.method private setMovementMethodIfNeed()V
    .locals 2

    .line 564
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTextView:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/widget/TextView;->getMovementMethod()Landroid/text/method/MovementMethod;

    move-result-object v0

    if-nez v0, :cond_0

    .line 565
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTextView:Landroid/widget/TextView;

    invoke-static {}, Landroid/text/method/LinkMovementMethod;->getInstance()Landroid/text/method/MovementMethod;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setMovementMethod(Landroid/text/method/MovementMethod;)V

    :cond_0
    return-void
.end method

.method private updateCharCharSequence()V
    .locals 12

    .line 851
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    invoke-interface {v0}, Ljava/lang/CharSequence;->length()I

    move-result v0

    if-nez v0, :cond_0

    return-void

    .line 855
    :cond_0
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v0}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x2

    const/4 v3, -0x1

    if-nez v0, :cond_1

    .line 856
    iget v4, p0, Lcom/hcn/common/utils/HSpanUtils;->lineHeight:I

    if-eq v4, v3, :cond_1

    .line 857
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-static {v2}, Ljava/lang/Character;->toString(C)Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v0, v4}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->append(Ljava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;

    move-result-object v0

    const-string v4, "\n"

    .line 858
    invoke-virtual {v0, v4}, Landroid/text/SpannableStringBuilder;->append(Ljava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;

    move-result-object v0

    new-instance v4, Landroid/text/style/AbsoluteSizeSpan;

    invoke-direct {v4, v1}, Landroid/text/style/AbsoluteSizeSpan;-><init>(I)V

    const/16 v5, 0x21

    .line 859
    invoke-virtual {v0, v4, v1, v2, v5}, Landroid/text/SpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    move v0, v2

    .line 862
    :cond_1
    iget-object v4, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    invoke-virtual {v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->append(Ljava/lang/CharSequence;)Landroid/text/SpannableStringBuilder;

    .line 863
    iget-object v4, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v4}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v4

    .line 864
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->verticalAlign:I

    if-eq v5, v3, :cond_2

    .line 865
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v6, Lcom/hcn/common/utils/HSpanUtils$VerticalAlignSpan;

    iget v7, p0, Lcom/hcn/common/utils/HSpanUtils;->verticalAlign:I

    invoke-direct {v6, v7}, Lcom/hcn/common/utils/HSpanUtils$VerticalAlignSpan;-><init>(I)V

    iget v7, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v6, v0, v4, v7}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 867
    :cond_2
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->foregroundColor:I

    const v6, -0x1000001

    if-eq v5, v6, :cond_3

    .line 868
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v7, Landroid/text/style/ForegroundColorSpan;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->foregroundColor:I

    invoke-direct {v7, v8}, Landroid/text/style/ForegroundColorSpan;-><init>(I)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v7, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 870
    :cond_3
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->backgroundColor:I

    if-eq v5, v6, :cond_4

    .line 871
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v7, Landroid/text/style/BackgroundColorSpan;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->backgroundColor:I

    invoke-direct {v7, v8}, Landroid/text/style/BackgroundColorSpan;-><init>(I)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v7, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 873
    :cond_4
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->first:I

    if-eq v5, v3, :cond_5

    .line 874
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v7, Landroid/text/style/LeadingMarginSpan$Standard;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->first:I

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->rest:I

    invoke-direct {v7, v8, v9}, Landroid/text/style/LeadingMarginSpan$Standard;-><init>(II)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v7, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 876
    :cond_5
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteColor:I

    const/4 v7, 0x0

    if-eq v5, v6, :cond_6

    .line 877
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v8, Lcom/hcn/common/utils/HSpanUtils$CustomQuoteSpan;

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteColor:I

    iget v10, p0, Lcom/hcn/common/utils/HSpanUtils;->stripeWidth:I

    iget v11, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteGapWidth:I

    invoke-direct {v8, v9, v10, v11, v7}, Lcom/hcn/common/utils/HSpanUtils$CustomQuoteSpan;-><init>(IIILcom/hcn/common/utils/HSpanUtils$CustomQuoteSpan-IA;)V

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v8, v0, v4, v9}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 884
    :cond_6
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletColor:I

    if-eq v5, v6, :cond_7

    .line 885
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v6, Lcom/hcn/common/utils/HSpanUtils$CustomBulletSpan;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletColor:I

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletRadius:I

    iget v10, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletGapWidth:I

    invoke-direct {v6, v8, v9, v10, v7}, Lcom/hcn/common/utils/HSpanUtils$CustomBulletSpan;-><init>(IIILcom/hcn/common/utils/HSpanUtils$CustomBulletSpan-IA;)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v6, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 892
    :cond_7
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->fontSize:I

    if-eq v5, v3, :cond_8

    .line 893
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v6, Landroid/text/style/AbsoluteSizeSpan;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->fontSize:I

    invoke-direct {v6, v8, v1}, Landroid/text/style/AbsoluteSizeSpan;-><init>(IZ)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v6, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 895
    :cond_8
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->proportion:F

    const/high16 v6, -0x40800000    # -1.0f

    cmpl-float v5, v5, v6

    if-eqz v5, :cond_9

    .line 896
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v8, Landroid/text/style/RelativeSizeSpan;

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->proportion:F

    invoke-direct {v8, v9}, Landroid/text/style/RelativeSizeSpan;-><init>(F)V

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v8, v0, v4, v9}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 898
    :cond_9
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->xProportion:F

    cmpl-float v5, v5, v6

    if-eqz v5, :cond_a

    .line 899
    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v8, Landroid/text/style/ScaleXSpan;

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->xProportion:F

    invoke-direct {v8, v9}, Landroid/text/style/ScaleXSpan;-><init>(F)V

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v5, v8, v0, v4, v9}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 901
    :cond_a
    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->lineHeight:I

    if-eq v5, v3, :cond_b

    .line 902
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Lcom/hcn/common/utils/HSpanUtils$CustomLineHeightSpan;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->lineHeight:I

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->alignLine:I

    invoke-direct {v5, v8, v9}, Lcom/hcn/common/utils/HSpanUtils$CustomLineHeightSpan;-><init>(II)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 904
    :cond_b
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isStrikethrough:Z

    if-eqz v3, :cond_c

    .line 905
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/StrikethroughSpan;

    invoke-direct {v5}, Landroid/text/style/StrikethroughSpan;-><init>()V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 907
    :cond_c
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isUnderline:Z

    if-eqz v3, :cond_d

    .line 908
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/UnderlineSpan;

    invoke-direct {v5}, Landroid/text/style/UnderlineSpan;-><init>()V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 910
    :cond_d
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isSuperscript:Z

    if-eqz v3, :cond_e

    .line 911
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/SuperscriptSpan;

    invoke-direct {v5}, Landroid/text/style/SuperscriptSpan;-><init>()V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 913
    :cond_e
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isSubscript:Z

    if-eqz v3, :cond_f

    .line 914
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/SubscriptSpan;

    invoke-direct {v5}, Landroid/text/style/SubscriptSpan;-><init>()V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 916
    :cond_f
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isBold:Z

    if-eqz v3, :cond_10

    .line 917
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/StyleSpan;

    const/4 v8, 0x1

    invoke-direct {v5, v8}, Landroid/text/style/StyleSpan;-><init>(I)V

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v8}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 919
    :cond_10
    iget-boolean v3, p0, Lcom/hcn/common/utils/HSpanUtils;->isItalic:Z

    if-eqz v3, :cond_11

    .line 920
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v5, Landroid/text/style/StyleSpan;

    invoke-direct {v5, v2}, Landroid/text/style/StyleSpan;-><init>(I)V

    iget v2, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v5, v0, v4, v2}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 922
    :cond_11
    iget-boolean v2, p0, Lcom/hcn/common/utils/HSpanUtils;->isBoldItalic:Z

    if-eqz v2, :cond_12

    .line 923
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Landroid/text/style/StyleSpan;

    const/4 v5, 0x3

    invoke-direct {v3, v5}, Landroid/text/style/StyleSpan;-><init>(I)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 925
    :cond_12
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->fontFamily:Ljava/lang/String;

    if-eqz v2, :cond_13

    .line 926
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Landroid/text/style/TypefaceSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->fontFamily:Ljava/lang/String;

    invoke-direct {v3, v5}, Landroid/text/style/TypefaceSpan;-><init>(Ljava/lang/String;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 928
    :cond_13
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->typeface:Landroid/graphics/Typeface;

    if-eqz v2, :cond_14

    .line 929
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Lcom/hcn/common/utils/HSpanUtils$CustomTypefaceSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->typeface:Landroid/graphics/Typeface;

    invoke-direct {v3, v5, v7}, Lcom/hcn/common/utils/HSpanUtils$CustomTypefaceSpan;-><init>(Landroid/graphics/Typeface;Lcom/hcn/common/utils/HSpanUtils$CustomTypefaceSpan-IA;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 931
    :cond_14
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignment:Landroid/text/Layout$Alignment;

    if-eqz v2, :cond_15

    .line 932
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Landroid/text/style/AlignmentSpan$Standard;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->alignment:Landroid/text/Layout$Alignment;

    invoke-direct {v3, v5}, Landroid/text/style/AlignmentSpan$Standard;-><init>(Landroid/text/Layout$Alignment;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 934
    :cond_15
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->clickSpan:Landroid/text/style/ClickableSpan;

    if-eqz v2, :cond_16

    .line 935
    iget-object v3, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v3, v2, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 937
    :cond_16
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->url:Ljava/lang/String;

    if-eqz v2, :cond_17

    .line 938
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Landroid/text/style/URLSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->url:Ljava/lang/String;

    invoke-direct {v3, v5}, Landroid/text/style/URLSpan;-><init>(Ljava/lang/String;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 940
    :cond_17
    iget v2, p0, Lcom/hcn/common/utils/HSpanUtils;->blurRadius:F

    cmpl-float v2, v2, v6

    if-eqz v2, :cond_18

    .line 941
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Landroid/text/style/MaskFilterSpan;

    new-instance v5, Landroid/graphics/BlurMaskFilter;

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->blurRadius:F

    iget-object v9, p0, Lcom/hcn/common/utils/HSpanUtils;->style:Landroid/graphics/BlurMaskFilter$Blur;

    invoke-direct {v5, v8, v9}, Landroid/graphics/BlurMaskFilter;-><init>(FLandroid/graphics/BlurMaskFilter$Blur;)V

    invoke-direct {v3, v5}, Landroid/text/style/MaskFilterSpan;-><init>(Landroid/graphics/MaskFilter;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 948
    :cond_18
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->shader:Landroid/graphics/Shader;

    if-eqz v2, :cond_19

    .line 949
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Lcom/hcn/common/utils/HSpanUtils$ShaderSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->shader:Landroid/graphics/Shader;

    invoke-direct {v3, v5, v7}, Lcom/hcn/common/utils/HSpanUtils$ShaderSpan;-><init>(Landroid/graphics/Shader;Lcom/hcn/common/utils/HSpanUtils$ShaderSpan-IA;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 951
    :cond_19
    iget v2, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowRadius:F

    cmpl-float v2, v2, v6

    if-eqz v2, :cond_1a

    .line 952
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;

    iget v6, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowRadius:F

    iget v7, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowDx:F

    iget v8, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowDy:F

    iget v9, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowColor:I

    const/4 v10, 0x0

    move-object v5, v3

    invoke-direct/range {v5 .. v10}, Lcom/hcn/common/utils/HSpanUtils$ShadowSpan;-><init>(FFFILcom/hcn/common/utils/HSpanUtils$ShadowSpan-IA;)V

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v4, v5}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    .line 959
    :cond_1a
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->spans:[Ljava/lang/Object;

    if-eqz v2, :cond_1b

    .line 960
    array-length v3, v2

    :goto_0
    if-ge v1, v3, :cond_1b

    aget-object v5, v2, v1

    .line 961
    iget-object v6, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    iget v7, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v6, v5, v0, v4, v7}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    :cond_1b
    return-void
.end method

.method private updateImage()V
    .locals 7

    .line 967
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v0}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v0

    const-string v1, "<img>"

    .line 968
    iput-object v1, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    .line 969
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->updateCharCharSequence()V

    .line 970
    iget-object v1, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v1}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v1

    .line 971
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageBitmap:Landroid/graphics/Bitmap;

    const/4 v3, 0x0

    if-eqz v2, :cond_0

    .line 972
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v4, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->imageBitmap:Landroid/graphics/Bitmap;

    iget v6, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    invoke-direct {v4, v5, v6, v3}, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;-><init>(Landroid/graphics/Bitmap;ILcom/hcn/common/utils/HSpanUtils$CustomImageSpan-IA;)V

    iget v3, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v4, v0, v1, v3}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    goto :goto_0

    .line 973
    :cond_0
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageDrawable:Landroid/graphics/drawable/Drawable;

    if-eqz v2, :cond_1

    .line 974
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v4, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->imageDrawable:Landroid/graphics/drawable/Drawable;

    iget v6, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    invoke-direct {v4, v5, v6, v3}, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;-><init>(Landroid/graphics/drawable/Drawable;ILcom/hcn/common/utils/HSpanUtils$CustomImageSpan-IA;)V

    iget v3, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v4, v0, v1, v3}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    goto :goto_0

    .line 975
    :cond_1
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageUri:Landroid/net/Uri;

    if-eqz v2, :cond_2

    .line 976
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v4, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;

    iget-object v5, p0, Lcom/hcn/common/utils/HSpanUtils;->imageUri:Landroid/net/Uri;

    iget v6, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    invoke-direct {v4, v5, v6, v3}, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;-><init>(Landroid/net/Uri;ILcom/hcn/common/utils/HSpanUtils$CustomImageSpan-IA;)V

    iget v3, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v4, v0, v1, v3}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    goto :goto_0

    .line 977
    :cond_2
    iget v2, p0, Lcom/hcn/common/utils/HSpanUtils;->imageResourceId:I

    const/4 v4, -0x1

    if-eq v2, v4, :cond_3

    .line 978
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v4, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->imageResourceId:I

    iget v6, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    invoke-direct {v4, v5, v6, v3}, Lcom/hcn/common/utils/HSpanUtils$CustomImageSpan;-><init>(IILcom/hcn/common/utils/HSpanUtils$CustomImageSpan-IA;)V

    iget v3, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v4, v0, v1, v3}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    :cond_3
    :goto_0
    return-void
.end method

.method private updateSpace()V
    .locals 7

    .line 983
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v0}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v0

    const-string v1, "< >"

    .line 984
    iput-object v1, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    .line 985
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->updateCharCharSequence()V

    .line 986
    iget-object v1, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v1}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->length()I

    move-result v1

    .line 987
    iget-object v2, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    new-instance v3, Lcom/hcn/common/utils/HSpanUtils$SpaceSpan;

    iget v4, p0, Lcom/hcn/common/utils/HSpanUtils;->spaceSize:I

    iget v5, p0, Lcom/hcn/common/utils/HSpanUtils;->spaceColor:I

    const/4 v6, 0x0

    invoke-direct {v3, v4, v5, v6}, Lcom/hcn/common/utils/HSpanUtils$SpaceSpan;-><init>(IILcom/hcn/common/utils/HSpanUtils$SpaceSpan-IA;)V

    iget v4, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    invoke-virtual {v2, v3, v0, v1, v4}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;->setSpan(Ljava/lang/Object;III)V

    return-void
.end method

.method public static with(Landroid/widget/TextView;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    .line 82
    new-instance v0, Lcom/hcn/common/utils/HSpanUtils;

    invoke-direct {v0, p0}, Lcom/hcn/common/utils/HSpanUtils;-><init>(Landroid/widget/TextView;)V

    return-object v0
.end method


# virtual methods
.method public append(Ljava/lang/CharSequence;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 641
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 642
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    return-object p0
.end method

.method public appendImage(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 765
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->appendImage(II)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public appendImage(II)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 782
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 783
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->imageResourceId:I

    .line 784
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    return-object p0
.end method

.method public appendImage(Landroid/graphics/Bitmap;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 675
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->appendImage(Landroid/graphics/Bitmap;I)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public appendImage(Landroid/graphics/Bitmap;I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 692
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 693
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->imageBitmap:Landroid/graphics/Bitmap;

    .line 694
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    return-object p0
.end method

.method public appendImage(Landroid/graphics/drawable/Drawable;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 705
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->appendImage(Landroid/graphics/drawable/Drawable;I)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public appendImage(Landroid/graphics/drawable/Drawable;I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 722
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 723
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->imageDrawable:Landroid/graphics/drawable/Drawable;

    .line 724
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    return-object p0
.end method

.method public appendImage(Landroid/net/Uri;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 735
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->appendImage(Landroid/net/Uri;I)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public appendImage(Landroid/net/Uri;I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 752
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 753
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->imageUri:Landroid/net/Uri;

    .line 754
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignImage:I

    return-object p0
.end method

.method public appendLine()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 652
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 653
    sget-object v0, Lcom/hcn/common/utils/HSpanUtils;->LINE_SEPARATOR:Ljava/lang/String;

    iput-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    return-object p0
.end method

.method public appendLine(Ljava/lang/CharSequence;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 663
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 664
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p1

    sget-object v0, Lcom/hcn/common/utils/HSpanUtils;->LINE_SEPARATOR:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->mText:Ljava/lang/CharSequence;

    return-object p0
.end method

.method public appendSpace(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 795
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->appendSpace(II)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public appendSpace(II)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x2

    .line 806
    invoke-direct {p0, v0}, Lcom/hcn/common/utils/HSpanUtils;->apply(I)V

    .line 807
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->spaceSize:I

    .line 808
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->spaceColor:I

    return-object p0
.end method

.method public create()Landroid/text/SpannableStringBuilder;
    .locals 2

    .line 827
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->applyLast()V

    .line 828
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mTextView:Landroid/widget/TextView;

    if-eqz v0, :cond_0

    .line 829
    iget-object v1, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_0
    const/4 v0, 0x1

    .line 831
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isCreated:Z

    .line 832
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    return-object v0
.end method

.method public get()Landroid/text/SpannableStringBuilder;
    .locals 1

    .line 818
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->mBuilder:Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;

    return-object v0
.end method

.method public setBackgroundColor(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 226
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->backgroundColor:I

    return-object p0
.end method

.method public setBlur(FLandroid/graphics/BlurMaskFilter$Blur;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 584
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->blurRadius:F

    .line 585
    iput-object p2, p0, Lcom/hcn/common/utils/HSpanUtils;->style:Landroid/graphics/BlurMaskFilter$Blur;

    return-object p0
.end method

.method public setBold()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 422
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isBold:Z

    return-object p0
.end method

.method public setBoldItalic()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 442
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isBoldItalic:Z

    return-object p0
.end method

.method public setBullet(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 2

    const/4 v0, 0x0

    const/4 v1, 0x3

    .line 307
    invoke-virtual {p0, v0, v1, p1}, Lcom/hcn/common/utils/HSpanUtils;->setBullet(III)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public setBullet(III)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 321
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletColor:I

    .line 322
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletRadius:I

    .line 323
    iput p3, p0, Lcom/hcn/common/utils/HSpanUtils;->bulletGapWidth:I

    return-object p0
.end method

.method public setClickSpan(IZLandroid/view/View$OnClickListener;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    .line 531
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->setMovementMethodIfNeed()V

    .line 532
    new-instance v0, Lcom/hcn/common/utils/HSpanUtils$1;

    invoke-direct {v0, p0, p1, p2, p3}, Lcom/hcn/common/utils/HSpanUtils$1;-><init>(Lcom/hcn/common/utils/HSpanUtils;IZLandroid/view/View$OnClickListener;)V

    iput-object v0, p0, Lcom/hcn/common/utils/HSpanUtils;->clickSpan:Landroid/text/style/ClickableSpan;

    return-object p0
.end method

.method public setClickSpan(Landroid/text/style/ClickableSpan;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 514
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->setMovementMethodIfNeed()V

    .line 515
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->clickSpan:Landroid/text/style/ClickableSpan;

    return-object p0
.end method

.method public setFlag(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 204
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->flag:I

    return-object p0
.end method

.method public setFontFamily(Ljava/lang/String;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 458
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->fontFamily:Ljava/lang/String;

    return-object p0
.end method

.method public setFontProportion(F)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 361
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->proportion:F

    return-object p0
.end method

.method public setFontSize(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x0

    .line 334
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->setFontSize(IZ)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public setFontSize(IZ)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    if-eqz p2, :cond_0

    .line 346
    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object p2

    invoke-virtual {p2}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p2

    iget p2, p2, Landroid/util/DisplayMetrics;->scaledDensity:F

    int-to-float p1, p1

    mul-float/2addr p1, p2

    const/high16 p2, 0x3f000000    # 0.5f

    add-float/2addr p1, p2

    float-to-int p1, p1

    .line 347
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->fontSize:I

    goto :goto_0

    .line 349
    :cond_0
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->fontSize:I

    :goto_0
    return-object p0
.end method

.method public setFontXProportion(F)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 372
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->xProportion:F

    return-object p0
.end method

.method public setForegroundColor(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 215
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->foregroundColor:I

    return-object p0
.end method

.method public setHorizontalAlign(Landroid/text/Layout$Alignment;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 485
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->alignment:Landroid/text/Layout$Alignment;

    return-object p0
.end method

.method public setItalic()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 432
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isItalic:Z

    return-object p0
.end method

.method public setLeadingMargin(II)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 295
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->first:I

    .line 296
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->rest:I

    return-object p0
.end method

.method public setLineHeight(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x2

    .line 237
    invoke-virtual {p0, p1, v0}, Lcom/hcn/common/utils/HSpanUtils;->setLineHeight(II)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public setLineHeight(II)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 254
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->lineHeight:I

    .line 255
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->alignLine:I

    return-object p0
.end method

.method public setQuoteColor(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x2

    .line 266
    invoke-virtual {p0, p1, v0, v0}, Lcom/hcn/common/utils/HSpanUtils;->setQuoteColor(III)Lcom/hcn/common/utils/HSpanUtils;

    move-result-object p1

    return-object p1
.end method

.method public setQuoteColor(III)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 280
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteColor:I

    .line 281
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->stripeWidth:I

    .line 282
    iput p3, p0, Lcom/hcn/common/utils/HSpanUtils;->quoteGapWidth:I

    return-object p0
.end method

.method public setShader(Landroid/graphics/Shader;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 596
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->shader:Landroid/graphics/Shader;

    return-object p0
.end method

.method public setShadow(FFFI)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 613
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowRadius:F

    .line 614
    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowDx:F

    .line 615
    iput p3, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowDy:F

    .line 616
    iput p4, p0, Lcom/hcn/common/utils/HSpanUtils;->shadowColor:I

    return-object p0
.end method

.method public varargs setSpans([Ljava/lang/Object;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    .line 628
    array-length v0, p1

    if-lez v0, :cond_0

    .line 629
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->spans:[Ljava/lang/Object;

    :cond_0
    return-object p0
.end method

.method public setStrikethrough()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 382
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isStrikethrough:Z

    return-object p0
.end method

.method public setSubscript()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 412
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isSubscript:Z

    return-object p0
.end method

.method public setSuperscript()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 402
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isSuperscript:Z

    return-object p0
.end method

.method public setTypeface(Landroid/graphics/Typeface;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 469
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->typeface:Landroid/graphics/Typeface;

    return-object p0
.end method

.method public setUnderline()Lcom/hcn/common/utils/HSpanUtils;
    .locals 1

    const/4 v0, 0x1

    .line 392
    iput-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils;->isUnderline:Z

    return-object p0
.end method

.method public setUrl(Ljava/lang/String;)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 558
    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils;->setMovementMethodIfNeed()V

    .line 559
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils;->url:Ljava/lang/String;

    return-object p0
.end method

.method public setVerticalAlign(I)Lcom/hcn/common/utils/HSpanUtils;
    .locals 0

    .line 502
    iput p1, p0, Lcom/hcn/common/utils/HSpanUtils;->verticalAlign:I

    return-object p0
.end method
