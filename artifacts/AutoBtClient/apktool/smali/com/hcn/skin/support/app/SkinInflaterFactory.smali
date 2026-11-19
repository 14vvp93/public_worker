.class public Lcom/hcn/skin/support/app/SkinInflaterFactory;
.super Ljava/lang/Object;
.source "SkinInflaterFactory.java"

# interfaces
.implements Landroid/view/LayoutInflater$Factory2;


# static fields
.field private static final TAG:Ljava/lang/String; = "SkinInflaterFactory"


# instance fields
.field private final mContext:Landroid/content/Context;

.field private mSkinItems:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/skin/support/app/SkinItem;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method private constructor <init>(Landroid/content/Context;)V
    .locals 1

    .line 35
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 33
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/skin/support/app/SkinInflaterFactory;->mSkinItems:Ljava/util/List;

    .line 36
    iput-object p1, p0, Lcom/hcn/skin/support/app/SkinInflaterFactory;->mContext:Landroid/content/Context;

    return-void
.end method

.method public static create(Landroid/content/Context;)Lcom/hcn/skin/support/app/SkinInflaterFactory;
    .locals 1

    .line 40
    new-instance v0, Lcom/hcn/skin/support/app/SkinInflaterFactory;

    invoke-direct {v0, p0}, Lcom/hcn/skin/support/app/SkinInflaterFactory;-><init>(Landroid/content/Context;)V

    return-object v0
.end method

.method private parseSkinAttr(Landroid/content/Context;Landroid/util/AttributeSet;Landroid/view/View;)V
    .locals 17

    move-object/from16 v1, p2

    .line 77
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    const/4 v3, 0x0

    move v4, v3

    .line 79
    :goto_0
    invoke-interface/range {p2 .. p2}, Landroid/util/AttributeSet;->getAttributeCount()I

    move-result v0

    if-ge v4, v0, :cond_7

    .line 80
    invoke-interface {v1, v4}, Landroid/util/AttributeSet;->getAttributeName(I)Ljava/lang/String;

    move-result-object v0

    .line 81
    invoke-interface {v1, v4}, Landroid/util/AttributeSet;->getAttributeValue(I)Ljava/lang/String;

    move-result-object v5

    const-string v6, "style"

    .line 85
    invoke-virtual {v6, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    const-string v7, "\n    typeName:"

    const-string v8, "\n    entryName:"

    const-string v9, "\n    attrValue:"

    const-string v10, "\n    attrName:"

    const/4 v11, 0x1

    const-string v12, "SkinInflaterFactory"

    if-eqz v6, :cond_3

    const/4 v6, 0x2

    new-array v6, v6, [I

    .line 86
    fill-array-data v6, :array_0

    .line 87
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getTheme()Landroid/content/res/Resources$Theme;

    move-result-object v13

    invoke-virtual {v13, v1, v6, v3, v3}, Landroid/content/res/Resources$Theme;->obtainStyledAttributes(Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;

    move-result-object v6

    const/4 v13, -0x1

    .line 88
    invoke-virtual {v6, v3, v13}, Landroid/content/res/TypedArray;->getResourceId(II)I

    move-result v14

    .line 89
    invoke-virtual {v6, v11, v13}, Landroid/content/res/TypedArray;->getResourceId(II)I

    move-result v11

    if-eq v14, v13, :cond_1

    .line 91
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v15

    invoke-virtual {v15, v14}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v15

    .line 92
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v14}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v3

    const-string v13, "textColor"

    .line 93
    invoke-static {v13, v14, v15, v3}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->get(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    move-result-object v13

    .line 94
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    move/from16 v16, v4

    const-string v4, "    textColor in style is supported:\n    resource id:"

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v14}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v15}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v12, v1}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    if-eqz v13, :cond_0

    .line 101
    invoke-interface {v2, v13}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    :cond_0
    const/4 v1, -0x1

    goto :goto_1

    :cond_1
    move/from16 v16, v4

    move v1, v13

    :goto_1
    if-eq v11, v1, :cond_2

    .line 105
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1, v11}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v1

    .line 106
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3, v11}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v3

    const-string v4, "background"

    .line 107
    invoke-static {v4, v11, v1, v3}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->get(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    move-result-object v4

    .line 108
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    const-string v14, "    background in style is supported:\n    resource id:"

    invoke-virtual {v13, v14}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v13

    invoke-virtual {v13, v11}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v11

    invoke-virtual {v11, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v10

    invoke-virtual {v10, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v12, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    if-eqz v4, :cond_2

    .line 115
    invoke-interface {v2, v4}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 119
    :cond_2
    invoke-virtual {v6}, Landroid/content/res/TypedArray;->recycle()V

    goto/16 :goto_2

    :cond_3
    move/from16 v16, v4

    .line 124
    invoke-static {v0}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->isSupportedAttr(Ljava/lang/String;)Z

    move-result v1

    const-string v3, "    "

    if-eqz v1, :cond_5

    const-string v1, "@"

    invoke-virtual {v5, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_5

    .line 127
    :try_start_0
    invoke-virtual {v5, v11}, Ljava/lang/String;->substring(I)Ljava/lang/String;

    move-result-object v1

    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    move-result v1

    if-nez v1, :cond_4

    goto :goto_2

    .line 132
    :cond_4
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v4

    invoke-virtual {v4, v1}, Landroid/content/res/Resources;->getResourceEntryName(I)Ljava/lang/String;

    move-result-object v4

    .line 134
    invoke-virtual/range {p1 .. p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    invoke-virtual {v6, v1}, Landroid/content/res/Resources;->getResourceTypeName(I)Ljava/lang/String;

    move-result-object v6

    .line 135
    invoke-static {v0, v1, v4, v6}, Lcom/hcn/skin/support/app/attr/base/AttrFactory;->get(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Lcom/hcn/skin/support/app/attr/base/SkinAttr;

    move-result-object v11

    .line 136
    new-instance v13, Ljava/lang/StringBuilder;

    invoke-direct {v13}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v13, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v13, " is supported:\n    resource id:"

    invoke-virtual {v3, v13}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v12, v0}, Lcom/hcn/skin/support/utils/SkinLog;->v(Ljava/lang/String;Ljava/lang/String;)V

    if-eqz v11, :cond_6

    .line 144
    invoke-interface {v2, v11}, Ljava/util/List;->add(Ljava/lang/Object;)Z
    :try_end_0
    .catch Ljava/lang/NumberFormatException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_2

    :catch_0
    move-exception v0

    .line 147
    invoke-virtual {v0}, Ljava/lang/NumberFormatException;->printStackTrace()V

    goto :goto_2

    .line 150
    :cond_5
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " is not supported!"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v12, v0}, Lcom/hcn/skin/support/utils/SkinLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    :cond_6
    :goto_2
    add-int/lit8 v4, v16, 0x1

    move-object/from16 v1, p2

    const/4 v3, 0x0

    goto/16 :goto_0

    .line 153
    :cond_7
    invoke-static {v2}, Lcom/hcn/skin/support/utils/SkinListUtils;->isEmpty(Ljava/util/List;)Z

    move-result v0

    if-nez v0, :cond_8

    .line 154
    new-instance v0, Lcom/hcn/skin/support/app/SkinItem;

    invoke-direct {v0}, Lcom/hcn/skin/support/app/SkinItem;-><init>()V

    move-object/from16 v1, p3

    .line 155
    iput-object v1, v0, Lcom/hcn/skin/support/app/SkinItem;->view:Landroid/view/View;

    .line 156
    iput-object v2, v0, Lcom/hcn/skin/support/app/SkinItem;->attrs:Ljava/util/List;

    move-object/from16 v1, p0

    .line 158
    iget-object v2, v1, Lcom/hcn/skin/support/app/SkinInflaterFactory;->mSkinItems:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 161
    invoke-virtual {v0}, Lcom/hcn/skin/support/app/SkinItem;->apply()V

    goto :goto_3

    :cond_8
    move-object/from16 v1, p0

    :goto_3
    return-void

    nop

    :array_0
    .array-data 4
        0x1010098
        0x10100d4
    .end array-data
.end method


# virtual methods
.method public applySkin()V
    .locals 3

    .line 168
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinInflaterFactory;->mSkinItems:Ljava/util/List;

    invoke-static {v0}, Lcom/hcn/skin/support/utils/SkinListUtils;->isEmpty(Ljava/util/List;)Z

    move-result v0

    if-eqz v0, :cond_0

    return-void

    .line 172
    :cond_0
    iget-object v0, p0, Lcom/hcn/skin/support/app/SkinInflaterFactory;->mSkinItems:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_2

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/skin/support/app/SkinItem;

    .line 173
    iget-object v2, v1, Lcom/hcn/skin/support/app/SkinItem;->view:Landroid/view/View;

    if-nez v2, :cond_1

    goto :goto_0

    .line 176
    :cond_1
    invoke-virtual {v1}, Lcom/hcn/skin/support/app/SkinItem;->apply()V

    goto :goto_0

    :cond_2
    return-void
.end method

.method public onCreateView(Landroid/view/View;Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;
    .locals 0

    .line 52
    invoke-virtual {p0, p2, p3, p4}, Lcom/hcn/skin/support/app/SkinInflaterFactory;->onCreateView(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method

.method public onCreateView(Ljava/lang/String;Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/view/View;
    .locals 0

    .line 58
    invoke-static {p2, p1, p3}, Lcom/hcn/skin/support/app/SkinViewInflater;->createViewFromTag(Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;

    move-result-object p1

    if-nez p1, :cond_0

    const/4 p1, 0x0

    return-object p1

    .line 64
    :cond_0
    invoke-direct {p0, p2, p3, p1}, Lcom/hcn/skin/support/app/SkinInflaterFactory;->parseSkinAttr(Landroid/content/Context;Landroid/util/AttributeSet;Landroid/view/View;)V

    return-object p1
.end method
