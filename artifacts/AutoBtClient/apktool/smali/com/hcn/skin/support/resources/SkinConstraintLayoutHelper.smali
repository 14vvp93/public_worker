.class public Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;
.super Ljava/lang/Object;
.source "SkinConstraintLayoutHelper.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
    }
.end annotation


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinConstraintLayoutHelper"


# instance fields
.field private mResources:Landroid/content/res/Resources;


# direct methods
.method public constructor <init>(Landroid/content/res/Resources;)V
    .locals 0

    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 35
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->mResources:Landroid/content/res/Resources;

    return-void
.end method

.method private getFloatValue(Ljava/lang/Object;)F
    .locals 1

    if-eqz p1, :cond_1

    .line 216
    :try_start_0
    instance-of v0, p1, Ljava/lang/Float;

    if-eqz v0, :cond_0

    .line 218
    check-cast p1, Ljava/lang/Float;

    invoke-virtual {p1}, Ljava/lang/Float;->floatValue()F

    move-result p1

    return p1

    .line 220
    :cond_0
    check-cast p1, Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result p1
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 222
    invoke-virtual {p1}, Ljava/lang/NumberFormatException;->printStackTrace()V

    :cond_1
    const/4 p1, 0x0

    return p1
.end method

.method private getIntValue(Ljava/lang/Object;)I
    .locals 1

    if-eqz p1, :cond_1

    .line 201
    :try_start_0
    instance-of v0, p1, Ljava/lang/Integer;

    if-eqz v0, :cond_0

    .line 203
    check-cast p1, Ljava/lang/Integer;

    invoke-virtual {p1}, Ljava/lang/Integer;->intValue()I

    move-result p1

    return p1

    .line 205
    :cond_0
    check-cast p1, Ljava/lang/String;

    invoke-static {p1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result p1
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return p1

    :catch_0
    move-exception p1

    .line 207
    invoke-virtual {p1}, Ljava/lang/NumberFormatException;->printStackTrace()V

    :cond_1
    const/4 p1, -0x1

    return p1
.end method


# virtual methods
.method public makeLayoutParams(Landroid/view/View;Ljava/util/Map;)V
    .locals 4
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/view/View;",
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;",
            ">;)V"
        }
    .end annotation

    .line 151
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-interface {p2, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p2

    check-cast p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    const-string v0, "SkinConstraintLayoutHelper"

    if-nez p2, :cond_0

    .line 153
    new-instance p2, Ljava/lang/StringBuilder;

    invoke-direct {p2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "makeLayoutParams return id:"

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result p1

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    return-void

    .line 157
    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object p1

    check-cast p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;

    if-eqz p1, :cond_2

    .line 162
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintLeft_toLeftOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->leftToLeft:I

    .line 163
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintLeft_toRightOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->leftToRight:I

    .line 164
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintRight_toLeftOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->rightToLeft:I

    .line 165
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintRight_toRightOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->rightToRight:I

    .line 166
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintTop_toTopOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->topToTop:I

    .line 167
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintTop_toBottomOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->topToBottom:I

    .line 168
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintBottom_toTopOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->bottomToTop:I

    .line 169
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintBottom_toBottomOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->bottomToBottom:I

    .line 170
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintStart_toStartOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->startToStart:I

    .line 171
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintStart_toEndOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->startToEnd:I

    .line 172
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintEnd_toStartOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->endToStart:I

    .line 173
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintEnd_toEndOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->endToEnd:I

    .line 174
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintBaseline_toBaselineOf"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->baselineToBaseline:I

    .line 180
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const/high16 v1, 0x3f000000    # 0.5f

    invoke-static {v1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v2

    const-string v3, "layout_constraintHorizontal_bias"

    invoke-interface {v0, v3, v2}, Ljava/util/Map;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getFloatValue(Ljava/lang/Object;)F

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->horizontalBias:F

    .line 181
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    invoke-static {v1}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v1

    const-string v2, "layout_constraintVertical_bias"

    invoke-interface {v0, v2, v1}, Ljava/util/Map;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getFloatValue(Ljava/lang/Object;)F

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->verticalBias:F

    .line 183
    iget-object v0, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v1, "layout_constraintHorizontal_chainStyle"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    .line 185
    invoke-direct {p0, v0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v0

    iput v0, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->horizontalChainStyle:I

    .line 187
    :cond_1
    iget-object p2, p2, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v0, "layout_constraintVertical_chainStyle"

    invoke-interface {p2, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p2

    if-eqz p2, :cond_3

    .line 189
    invoke-direct {p0, p2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result p2

    iput p2, p1, Landroidx/constraintlayout/widget/ConstraintLayout$LayoutParams;->verticalChainStyle:I

    goto :goto_0

    :cond_2
    const-string p1, "ConstraintLayout.LayoutParams is null"

    .line 194
    invoke-static {v0, p1}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    :cond_3
    :goto_0
    return-void
.end method

.method public parserLayout(I)Ljava/util/Map;
    .locals 9
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;",
            ">;"
        }
    .end annotation

    .line 90
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->mResources:Landroid/content/res/Resources;

    if-nez v0, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 94
    :cond_0
    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getLayout(I)Landroid/content/res/XmlResourceParser;

    move-result-object p1

    .line 96
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 98
    :try_start_0
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v1

    :goto_0
    const/4 v2, 0x1

    if-eq v1, v2, :cond_8

    const/4 v3, 0x2

    if-eq v1, v3, :cond_1

    goto :goto_2

    .line 104
    :cond_1
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->getAttributeCount()I

    move-result v1

    .line 106
    new-instance v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    invoke-direct {v3, p0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;-><init>(Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;)V

    const/4 v4, 0x0

    :goto_1
    if-ge v4, v1, :cond_6

    .line 108
    invoke-interface {p1, v4}, Landroid/content/res/XmlResourceParser;->getAttributeName(I)Ljava/lang/String;

    move-result-object v5

    .line 109
    invoke-interface {p1, v4}, Landroid/content/res/XmlResourceParser;->getAttributeValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "id"

    .line 111
    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const-string v8, "@"

    if-eqz v7, :cond_2

    .line 112
    :try_start_1
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_2

    .line 113
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v7

    iput v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    :cond_2
    const-string v7, "layout"

    .line 116
    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_3

    .line 117
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_3

    .line 118
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v7

    iput v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    :cond_3
    const-string v7, "layout_constraint"

    .line 121
    invoke-virtual {v5, v7}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v7

    if-eqz v7, :cond_5

    .line 122
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_4

    .line 123
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v6

    .line 125
    :cond_4
    iget-object v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    invoke-interface {v7, v5, v6}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_5
    add-int/lit8 v4, v4, 0x1

    goto :goto_1

    .line 128
    :cond_6
    iget v1, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    if-lez v1, :cond_7

    .line 130
    iget v1, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-interface {v0, v1, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 140
    :cond_7
    :goto_2
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v1
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 143
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_8
    return-object v0
.end method

.method public parserLayout(Landroid/content/res/XmlResourceParser;)Ljava/util/Map;
    .locals 9
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/content/res/XmlResourceParser;",
            ")",
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;",
            ">;"
        }
    .end annotation

    .line 40
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    .line 42
    :try_start_0
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v1

    :goto_0
    const/4 v2, 0x1

    if-eq v1, v2, :cond_6

    const/4 v3, 0x2

    if-eq v1, v3, :cond_0

    goto :goto_2

    .line 48
    :cond_0
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->getAttributeCount()I

    move-result v1

    .line 50
    new-instance v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    invoke-direct {v3, p0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;-><init>(Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;)V

    const/4 v4, 0x0

    :goto_1
    if-ge v4, v1, :cond_5

    .line 52
    invoke-interface {p1, v4}, Landroid/content/res/XmlResourceParser;->getAttributeName(I)Ljava/lang/String;

    move-result-object v5

    .line 53
    invoke-interface {p1, v4}, Landroid/content/res/XmlResourceParser;->getAttributeValue(I)Ljava/lang/String;

    move-result-object v6

    const-string v7, "id"

    .line 55
    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    const-string v8, "@"

    if-eqz v7, :cond_1

    .line 56
    :try_start_1
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_1

    .line 57
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v7

    iput v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    :cond_1
    const-string v7, "layout"

    .line 60
    invoke-virtual {v7, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v7

    if-eqz v7, :cond_2

    .line 61
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_2

    .line 62
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v7

    invoke-static {v7}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v7

    iput v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    :cond_2
    const-string v7, "layout_constraint"

    .line 65
    invoke-virtual {v5, v7}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v7

    if-eqz v7, :cond_4

    .line 66
    invoke-virtual {v6, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-eqz v7, :cond_3

    .line 67
    invoke-virtual {v6, v2}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v6

    .line 69
    :cond_3
    iget-object v7, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    invoke-interface {v7, v5, v6}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    :cond_4
    add-int/lit8 v4, v4, 0x1

    goto :goto_1

    .line 72
    :cond_5
    iget v1, v3, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    invoke-interface {v0, v1, v3}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 81
    :goto_2
    invoke-interface {p1}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v1
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0

    :catch_0
    move-exception p1

    .line 84
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_6
    return-object v0
.end method
