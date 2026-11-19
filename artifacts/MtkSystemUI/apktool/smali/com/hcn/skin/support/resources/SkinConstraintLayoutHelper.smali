.class public Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;
.super Ljava/lang/Object;
.source "SkinConstraintLayoutHelper.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
    }
.end annotation


# instance fields
.field private mResources:Landroid/content/res/Resources;


# direct methods
.method public constructor <init>(Landroid/content/res/Resources;)V
    .locals 0
    .param p1, "resources"    # Landroid/content/res/Resources;

    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 35
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->mResources:Landroid/content/res/Resources;

    .line 36
    return-void
.end method

.method private getFloatValue(Ljava/lang/Object;)F
    .locals 1
    .param p1, "obj"    # Ljava/lang/Object;

    .line 214
    if-eqz p1, :cond_1

    .line 216
    :try_start_0
    instance-of v0, p1, Ljava/lang/Float;

    if-eqz v0, :cond_0

    .line 218
    move-object v0, p1

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    return v0

    .line 220
    :cond_0
    move-object v0, p1

    check-cast v0, Ljava/lang/String;

    invoke-static {v0}, Ljava/lang/Float;->parseFloat(Ljava/lang/String;)F

    move-result v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    .line 221
    :catch_0
    move-exception v0

    .line 222
    .local v0, "e":Ljava/lang/NumberFormatException;
    invoke-virtual {v0}, Ljava/lang/NumberFormatException;->printStackTrace()V

    .line 225
    .end local v0    # "e":Ljava/lang/NumberFormatException;
    :cond_1
    const/4 v0, 0x0

    return v0
.end method

.method private getIntValue(Ljava/lang/Object;)I
    .locals 1
    .param p1, "obj"    # Ljava/lang/Object;

    .line 199
    if-eqz p1, :cond_1

    .line 201
    :try_start_0
    instance-of v0, p1, Ljava/lang/Integer;

    if-eqz v0, :cond_0

    .line 203
    move-object v0, p1

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    return v0

    .line 205
    :cond_0
    move-object v0, p1

    check-cast v0, Ljava/lang/String;

    invoke-static {v0}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v0
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    return v0

    .line 206
    :catch_0
    move-exception v0

    .line 207
    .local v0, "e":Ljava/lang/NumberFormatException;
    invoke-virtual {v0}, Ljava/lang/NumberFormatException;->printStackTrace()V

    .line 210
    .end local v0    # "e":Ljava/lang/NumberFormatException;
    :cond_1
    const/4 v0, -0x1

    return v0
.end method


# virtual methods
.method public makeLayoutParams(Landroid/view/View;Ljava/util/Map;)V
    .locals 6
    .param p1, "target"    # Landroid/view/View;
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
    .local p2, "rules":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/Integer;Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;>;"
    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-interface {p2, v0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    .line 152
    .local v0, "config":Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
    if-nez v0, :cond_0

    .line 153
    const-string v1, "SkinConstraintLayoutHelper"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "makeLayoutParams return id:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Landroid/view/View;->getId()I

    move-result v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 154
    return-void

    .line 157
    :cond_0
    invoke-virtual {p1}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    check-cast v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;

    .line 158
    .local v1, "lp":Landroid/support/constraint/ConstraintLayout$LayoutParams;
    if-eqz v1, :cond_3

    .line 162
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintLeft_toLeftOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->leftToLeft:I

    .line 163
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintLeft_toRightOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->leftToRight:I

    .line 164
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintRight_toLeftOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->rightToLeft:I

    .line 165
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintRight_toRightOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->rightToRight:I

    .line 166
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintTop_toTopOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->topToTop:I

    .line 167
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintTop_toBottomOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->topToBottom:I

    .line 168
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintBottom_toTopOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->bottomToTop:I

    .line 169
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintBottom_toBottomOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->bottomToBottom:I

    .line 170
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintStart_toStartOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->startToStart:I

    .line 171
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintStart_toEndOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->startToEnd:I

    .line 172
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintEnd_toStartOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->endToStart:I

    .line 173
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintEnd_toEndOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->endToEnd:I

    .line 174
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintBaseline_toBaselineOf"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->baselineToBaseline:I

    .line 180
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintHorizontal_bias"

    const/high16 v4, 0x3f000000    # 0.5f

    invoke-static {v4}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    invoke-interface {v2, v3, v5}, Ljava/util/Map;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getFloatValue(Ljava/lang/Object;)F

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->horizontalBias:F

    .line 181
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintVertical_bias"

    invoke-static {v4}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v4

    invoke-interface {v2, v3, v4}, Ljava/util/Map;->getOrDefault(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getFloatValue(Ljava/lang/Object;)F

    move-result v2

    iput v2, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->verticalBias:F

    .line 183
    iget-object v2, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v3, "layout_constraintHorizontal_chainStyle"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    .line 184
    .local v2, "horizontal_chainStyle":Ljava/lang/Object;
    if-eqz v2, :cond_1

    .line 185
    invoke-direct {p0, v2}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v3

    iput v3, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->horizontalChainStyle:I

    .line 187
    :cond_1
    iget-object v3, v0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    const-string v4, "layout_constraintVertical_chainStyle"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    .line 188
    .local v3, "vertical_chainStyle":Ljava/lang/Object;
    if-eqz v3, :cond_2

    .line 189
    invoke-direct {p0, v3}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->getIntValue(Ljava/lang/Object;)I

    move-result v4

    iput v4, v1, Landroid/support/constraint/ConstraintLayout$LayoutParams;->verticalChainStyle:I

    .line 193
    .end local v2    # "horizontal_chainStyle":Ljava/lang/Object;
    .end local v3    # "vertical_chainStyle":Ljava/lang/Object;
    :cond_2
    goto :goto_0

    .line 194
    :cond_3
    const-string v2, "SkinConstraintLayoutHelper"

    const-string v3, "ConstraintLayout.LayoutParams is null"

    invoke-static {v2, v3}, Lcom/hcn/skin/support/utils/SkinLog;->e(Ljava/lang/String;Ljava/lang/String;)V

    .line 196
    :goto_0
    return-void
.end method

.method public parserLayout(I)Ljava/util/Map;
    .locals 10
    .param p1, "layoutId"    # I
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

    .line 91
    const/4 v0, 0x0

    return-object v0

    .line 94
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;->mResources:Landroid/content/res/Resources;

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->getLayout(I)Landroid/content/res/XmlResourceParser;

    move-result-object v0

    .line 96
    .local v0, "xmlParser":Landroid/content/res/XmlResourceParser;
    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    .line 98
    .local v1, "list":Ljava/util/Map;, "Ljava/util/Map<Ljava/lang/Integer;Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;>;"
    :try_start_0
    invoke-interface {v0}, Landroid/content/res/XmlResourceParser;->getEventType()I

    move-result v2

    .line 99
    .local v2, "event":I
    :goto_0
    const/4 v3, 0x1

    if-eq v2, v3, :cond_8

    .line 100
    if-eqz v2, :cond_6

    packed-switch v2, :pswitch_data_0

    goto/16 :goto_2

    .line 134
    :pswitch_0
    goto/16 :goto_2

    .line 136
    :pswitch_1
    goto/16 :goto_2

    .line 104
    :pswitch_2
    invoke-interface {v0}, Landroid/content/res/XmlResourceParser;->getAttributeCount()I

    move-result v4

    .line 106
    .local v4, "size":I
    new-instance v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;

    invoke-direct {v5, p0}, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;-><init>(Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;)V

    .line 107
    .local v5, "config":Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
    const/4 v6, 0x0

    .local v6, "i":I
    :goto_1
    if-ge v6, v4, :cond_5

    .line 108
    invoke-interface {v0, v6}, Landroid/content/res/XmlResourceParser;->getAttributeName(I)Ljava/lang/String;

    move-result-object v7

    .line 109
    .local v7, "attributeName":Ljava/lang/String;
    invoke-interface {v0, v6}, Landroid/content/res/XmlResourceParser;->getAttributeValue(I)Ljava/lang/String;

    move-result-object v8

    .line 111
    .local v8, "attributeValue":Ljava/lang/String;
    const-string v9, "id"

    invoke-virtual {v9, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_1

    .line 112
    const-string v9, "@"

    invoke-virtual {v8, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_1

    .line 113
    invoke-virtual {v8, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v9

    iput v9, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    .line 116
    :cond_1
    const-string v9, "layout"

    invoke-virtual {v9, v7}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v9

    if-eqz v9, :cond_2

    .line 117
    const-string v9, "@"

    invoke-virtual {v8, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_2

    .line 118
    invoke-virtual {v8, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v9

    invoke-static {v9}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v9

    iput v9, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    .line 121
    :cond_2
    const-string v9, "layout_constraint"

    invoke-virtual {v7, v9}, Ljava/lang/String;->contains(Ljava/lang/CharSequence;)Z

    move-result v9

    if-eqz v9, :cond_4

    .line 122
    const-string v9, "@"

    invoke-virtual {v8, v9}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v9

    if-eqz v9, :cond_3

    .line 123
    invoke-virtual {v8, v3}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v9

    move-object v8, v9

    .line 125
    :cond_3
    iget-object v9, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    invoke-interface {v9, v7, v8}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 107
    .end local v7    # "attributeName":Ljava/lang/String;
    .end local v8    # "attributeValue":Ljava/lang/String;
    :cond_4
    add-int/lit8 v6, v6, 0x1

    goto :goto_1

    .line 128
    .end local v6    # "i":I
    :cond_5
    iget v3, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    if-lez v3, :cond_7

    .line 130
    iget v3, v5, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-interface {v1, v3, v5}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_2

    .line 102
    .end local v4    # "size":I
    .end local v5    # "config":Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
    :cond_6
    nop

    .line 140
    :cond_7
    :goto_2
    invoke-interface {v0}, Landroid/content/res/XmlResourceParser;->next()I

    move-result v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    move v2, v3

    goto/16 :goto_0

    .line 144
    .end local v2    # "event":I
    :cond_8
    goto :goto_3

    .line 142
    :catch_0
    move-exception v2

    .line 143
    .local v2, "e":Ljava/lang/Exception;
    invoke-virtual {v2}, Ljava/lang/Exception;->printStackTrace()V

    .line 146
    .end local v2    # "e":Ljava/lang/Exception;
    :goto_3
    return-object v1

    :pswitch_data_0
    .packed-switch 0x2
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
