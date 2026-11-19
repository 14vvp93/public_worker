.class public Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;
.super Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;
.source "AppIconItemDecoration.java"


# static fields
.field private static final ATTRS:[I


# instance fields
.field private mBounds:Landroid/graphics/Rect;

.field private mContext:Landroid/content/Context;

.field private mDivider:Landroid/graphics/drawable/Drawable;

.field private mDividerSpacing:I

.field private mFooterNoShowSize:I

.field private mHeaderNoShowSize:I

.field private mLeftTopPadding:I

.field private mOrientation:I

.field private mPaint:Landroid/graphics/Paint;

.field private mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

.field private mRightBottomPadding:I


# direct methods
.method static constructor <clinit>()V
    .locals 3

    .line 34
    const/4 v0, 0x1

    new-array v0, v0, [I

    const/4 v1, 0x0

    const v2, 0x1010214

    aput v2, v0, v1

    sput-object v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->ATTRS:[I

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;III)V
    .locals 2
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "orientation"    # I
    .param p3, "headerNoShowSize"    # I
    .param p4, "footerNoShowSize"    # I

    .line 85
    invoke-direct {p0}, Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;-><init>()V

    .line 30
    new-instance v0, Landroid/graphics/Rect;

    invoke-direct {v0}, Landroid/graphics/Rect;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mBounds:Landroid/graphics/Rect;

    .line 38
    const/4 v0, 0x0

    iput v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mHeaderNoShowSize:I

    .line 42
    const/4 v1, 0x1

    iput v1, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mFooterNoShowSize:I

    .line 86
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mContext:Landroid/content/Context;

    .line 87
    iput p3, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mHeaderNoShowSize:I

    .line 88
    iput p4, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mFooterNoShowSize:I

    .line 89
    invoke-virtual {p0, p2}, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->setOrientation(I)Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;

    .line 90
    sget-object v1, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->ATTRS:[I

    invoke-virtual {p1, v1}, Landroid/content/Context;->obtainStyledAttributes([I)Landroid/content/res/TypedArray;

    move-result-object v1

    .line 91
    .local v1, "a":Landroid/content/res/TypedArray;
    invoke-virtual {v1, v0}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    .line 92
    invoke-virtual {v1}, Landroid/content/res/TypedArray;->recycle()V

    .line 93
    return-void
.end method

.method private drawHorizontal(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 22
    .param p1, "canvas"    # Landroid/graphics/Canvas;
    .param p2, "parent"    # Landroidx/recyclerview/widget/RecyclerView;
    .param p3, "state"    # Landroidx/recyclerview/widget/RecyclerView$State;

    move-object/from16 v0, p0

    move-object/from16 v7, p1

    move-object/from16 v8, p2

    .line 187
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->save()I

    .line 191
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getClipToPadding()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 192
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingTop()I

    move-result v1

    .line 193
    .local v1, "top":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getHeight()I

    move-result v2

    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingBottom()I

    move-result v3

    sub-int/2addr v2, v3

    .line 194
    .local v2, "bottom":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingLeft()I

    move-result v3

    .line 195
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getWidth()I

    move-result v4

    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingRight()I

    move-result v5

    sub-int/2addr v4, v5

    .line 194
    invoke-virtual {v7, v3, v1, v4, v2}, Landroid/graphics/Canvas;->clipRect(IIII)Z

    .line 198
    .end local v1    # "top":I
    .local v9, "top":I
    :goto_0
    move v9, v1

    goto :goto_1

    .line 197
    .end local v2    # "bottom":I
    .end local v9    # "top":I
    :cond_0
    const/4 v1, 0x0

    .line 198
    .restart local v1    # "top":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getHeight()I

    move-result v2

    goto :goto_0

    .end local v1    # "top":I
    .restart local v2    # "bottom":I
    .restart local v9    # "top":I
    :goto_1
    move v10, v2

    .line 201
    .end local v2    # "bottom":I
    .local v10, "bottom":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getChildCount()I

    move-result v11

    .line 202
    .local v11, "childCount":I
    invoke-virtual/range {p3 .. p3}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    move-result v1

    add-int/lit8 v12, v1, -0x1

    .line 203
    .local v12, "lastPosition":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_2
    move v13, v1

    .end local v1    # "i":I
    .local v13, "i":I
    if-ge v13, v11, :cond_4

    .line 204
    invoke-virtual {v8, v13}, Landroidx/recyclerview/widget/RecyclerView;->getChildAt(I)Landroid/view/View;

    move-result-object v14

    .line 205
    .local v14, "child":Landroid/view/View;
    invoke-virtual {v8, v14}, Landroidx/recyclerview/widget/RecyclerView;->getChildAdapterPosition(Landroid/view/View;)I

    move-result v15

    .line 208
    .local v15, "childRealPosition":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mHeaderNoShowSize:I

    if-ge v15, v1, :cond_1

    .line 209
    nop

    .line 203
    move/from16 v18, v9

    goto/16 :goto_3

    .line 212
    :cond_1
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mFooterNoShowSize:I

    sub-int v1, v12, v1

    if-gt v15, v1, :cond_3

    .line 213
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    if-eqz v1, :cond_2

    .line 214
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v8, v14, v1}, Landroidx/recyclerview/widget/RecyclerView;->getDecoratedBoundsWithMargins(Landroid/view/View;Landroid/graphics/Rect;)V

    .line 215
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mBounds:Landroid/graphics/Rect;

    iget v1, v1, Landroid/graphics/Rect;->right:I

    invoke-virtual {v14}, Landroid/view/View;->getTranslationX()F

    move-result v2

    invoke-static {v2}, Ljava/lang/Math;->round(F)I

    move-result v2

    add-int/2addr v1, v2

    .line 216
    .local v1, "right":I
    iget-object v2, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    sub-int v2, v1, v2

    .line 217
    .local v2, "left":I
    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v3, v2, v9, v1, v10}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 218
    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v3, v7}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 221
    .end local v1    # "right":I
    .end local v2    # "left":I
    :cond_2
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    if-eqz v1, :cond_3

    .line 222
    invoke-virtual {v14}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    move-object v6, v1

    check-cast v6, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 223
    .local v6, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    invoke-virtual {v14}, Landroid/view/View;->getRight()I

    move-result v1

    iget v2, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->rightMargin:I

    add-int v5, v1, v2

    .line 224
    .local v5, "left1":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDividerSpacing:I

    add-int v4, v5, v1

    .line 225
    .local v4, "right1":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mLeftTopPadding:I

    add-int v3, v9, v1

    .line 226
    .local v3, "top1":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mRightBottomPadding:I

    sub-int v2, v10, v1

    .line 227
    .local v2, "bottom1":I
    int-to-float v1, v5

    move/from16 v16, v5

    int-to-float v5, v3

    .end local v5    # "left1":I
    .local v16, "left1":I
    move-object/from16 v17, v6

    int-to-float v6, v4

    .end local v6    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .local v17, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    int-to-float v8, v2

    move/from16 v18, v9

    iget-object v9, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    .end local v9    # "top":I
    .local v18, "top":I
    move/from16 v19, v1

    move-object v1, v7

    move/from16 v20, v2

    move/from16 v2, v19

    .end local v2    # "bottom1":I
    .local v20, "bottom1":I
    move/from16 v19, v3

    move v3, v5

    .end local v3    # "top1":I
    .local v19, "top1":I
    move/from16 v21, v4

    move v4, v6

    .end local v4    # "right1":I
    .local v21, "right1":I
    move v5, v8

    move-object/from16 v8, v17

    move-object v6, v9

    .end local v17    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .local v8, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    invoke-virtual/range {v1 .. v6}, Landroid/graphics/Canvas;->drawRect(FFFFLandroid/graphics/Paint;)V

    .end local v8    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .end local v14    # "child":Landroid/view/View;
    .end local v15    # "childRealPosition":I
    .end local v16    # "left1":I
    .end local v19    # "top1":I
    .end local v20    # "bottom1":I
    .end local v21    # "right1":I
    goto :goto_3

    .line 203
    .end local v18    # "top":I
    .restart local v9    # "top":I
    :cond_3
    move/from16 v18, v9

    .end local v9    # "top":I
    .restart local v18    # "top":I
    :goto_3
    add-int/lit8 v1, v13, 0x1

    .end local v13    # "i":I
    .local v1, "i":I
    move/from16 v9, v18

    move-object/from16 v8, p2

    goto/16 :goto_2

    .line 231
    .end local v1    # "i":I
    .end local v18    # "top":I
    .restart local v9    # "top":I
    :cond_4
    move/from16 v18, v9

    .end local v9    # "top":I
    .restart local v18    # "top":I
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->restore()V

    .line 232
    return-void
.end method

.method private drawVertical(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 22
    .param p1, "canvas"    # Landroid/graphics/Canvas;
    .param p2, "parent"    # Landroidx/recyclerview/widget/RecyclerView;
    .param p3, "state"    # Landroidx/recyclerview/widget/RecyclerView$State;

    move-object/from16 v0, p0

    move-object/from16 v7, p1

    move-object/from16 v8, p2

    .line 140
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->save()I

    .line 144
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getClipToPadding()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 145
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingLeft()I

    move-result v1

    .line 146
    .local v1, "left":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getWidth()I

    move-result v2

    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingRight()I

    move-result v3

    sub-int/2addr v2, v3

    .line 147
    .local v2, "right":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingTop()I

    move-result v3

    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getHeight()I

    move-result v4

    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getPaddingBottom()I

    move-result v5

    sub-int/2addr v4, v5

    invoke-virtual {v7, v1, v3, v2, v4}, Landroid/graphics/Canvas;->clipRect(IIII)Z

    .line 150
    .end local v1    # "left":I
    .local v9, "left":I
    :goto_0
    move v9, v1

    goto :goto_1

    .line 149
    .end local v2    # "right":I
    .end local v9    # "left":I
    :cond_0
    const/4 v1, 0x0

    .line 150
    .restart local v1    # "left":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getWidth()I

    move-result v2

    goto :goto_0

    .end local v1    # "left":I
    .restart local v2    # "right":I
    .restart local v9    # "left":I
    :goto_1
    move v10, v2

    .line 153
    .end local v2    # "right":I
    .local v10, "right":I
    invoke-virtual/range {p2 .. p2}, Landroidx/recyclerview/widget/RecyclerView;->getChildCount()I

    move-result v11

    .line 154
    .local v11, "childCount":I
    invoke-virtual/range {p3 .. p3}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    move-result v1

    add-int/lit8 v12, v1, -0x1

    .line 155
    .local v12, "lastPosition":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_2
    move v13, v1

    .end local v1    # "i":I
    .local v13, "i":I
    if-ge v13, v11, :cond_4

    .line 156
    invoke-virtual {v8, v13}, Landroidx/recyclerview/widget/RecyclerView;->getChildAt(I)Landroid/view/View;

    move-result-object v14

    .line 157
    .local v14, "child":Landroid/view/View;
    invoke-virtual {v8, v14}, Landroidx/recyclerview/widget/RecyclerView;->getChildAdapterPosition(Landroid/view/View;)I

    move-result v15

    .line 160
    .local v15, "childRealPosition":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mHeaderNoShowSize:I

    if-ge v15, v1, :cond_1

    .line 161
    nop

    .line 155
    move/from16 v18, v9

    goto/16 :goto_3

    .line 164
    :cond_1
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mFooterNoShowSize:I

    sub-int v1, v12, v1

    if-gt v15, v1, :cond_3

    .line 165
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    if-eqz v1, :cond_2

    .line 166
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mBounds:Landroid/graphics/Rect;

    invoke-virtual {v8, v14, v1}, Landroidx/recyclerview/widget/RecyclerView;->getDecoratedBoundsWithMargins(Landroid/view/View;Landroid/graphics/Rect;)V

    .line 167
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mBounds:Landroid/graphics/Rect;

    iget v1, v1, Landroid/graphics/Rect;->bottom:I

    invoke-virtual {v14}, Landroid/view/View;->getTranslationY()F

    move-result v2

    invoke-static {v2}, Ljava/lang/Math;->round(F)I

    move-result v2

    add-int/2addr v1, v2

    .line 168
    .local v1, "bottom":I
    iget-object v2, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v2

    sub-int v2, v1, v2

    .line 169
    .local v2, "top":I
    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v3, v9, v2, v10, v1}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 170
    iget-object v3, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v3, v7}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 173
    .end local v1    # "bottom":I
    .end local v2    # "top":I
    :cond_2
    iget-object v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    if-eqz v1, :cond_3

    .line 174
    invoke-virtual {v14}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    move-object v6, v1

    check-cast v6, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;

    .line 175
    .local v6, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mLeftTopPadding:I

    add-int v5, v9, v1

    .line 176
    .local v5, "left1":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mRightBottomPadding:I

    sub-int v4, v10, v1

    .line 177
    .local v4, "right1":I
    invoke-virtual {v14}, Landroid/view/View;->getBottom()I

    move-result v1

    iget v2, v6, Landroidx/recyclerview/widget/RecyclerView$LayoutParams;->bottomMargin:I

    add-int v3, v1, v2

    .line 178
    .local v3, "top1":I
    iget v1, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDividerSpacing:I

    add-int v2, v3, v1

    .line 179
    .local v2, "bottom1":I
    int-to-float v1, v5

    move/from16 v16, v5

    int-to-float v5, v3

    .end local v5    # "left1":I
    .local v16, "left1":I
    move-object/from16 v17, v6

    int-to-float v6, v4

    .end local v6    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .local v17, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    int-to-float v8, v2

    move/from16 v18, v9

    iget-object v9, v0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    .end local v9    # "left":I
    .local v18, "left":I
    move/from16 v19, v1

    move-object v1, v7

    move/from16 v20, v2

    move/from16 v2, v19

    .end local v2    # "bottom1":I
    .local v20, "bottom1":I
    move/from16 v19, v3

    move v3, v5

    .end local v3    # "top1":I
    .local v19, "top1":I
    move/from16 v21, v4

    move v4, v6

    .end local v4    # "right1":I
    .local v21, "right1":I
    move v5, v8

    move-object/from16 v8, v17

    move-object v6, v9

    .end local v17    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .local v8, "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    invoke-virtual/range {v1 .. v6}, Landroid/graphics/Canvas;->drawRect(FFFFLandroid/graphics/Paint;)V

    .end local v8    # "params":Landroidx/recyclerview/widget/RecyclerView$LayoutParams;
    .end local v14    # "child":Landroid/view/View;
    .end local v15    # "childRealPosition":I
    .end local v16    # "left1":I
    .end local v19    # "top1":I
    .end local v20    # "bottom1":I
    .end local v21    # "right1":I
    goto :goto_3

    .line 155
    .end local v18    # "left":I
    .restart local v9    # "left":I
    :cond_3
    move/from16 v18, v9

    .end local v9    # "left":I
    .restart local v18    # "left":I
    :goto_3
    add-int/lit8 v1, v13, 0x1

    .end local v13    # "i":I
    .local v1, "i":I
    move/from16 v9, v18

    move-object/from16 v8, p2

    goto/16 :goto_2

    .line 183
    .end local v1    # "i":I
    .end local v18    # "left":I
    .restart local v9    # "left":I
    :cond_4
    move/from16 v18, v9

    .end local v9    # "left":I
    .restart local v18    # "left":I
    invoke-virtual/range {p1 .. p1}, Landroid/graphics/Canvas;->restore()V

    .line 184
    return-void
.end method


# virtual methods
.method public getItemOffsets(Landroid/graphics/Rect;Landroid/view/View;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 6
    .param p1, "outRect"    # Landroid/graphics/Rect;
    .param p2, "view"    # Landroid/view/View;
    .param p3, "parent"    # Landroidx/recyclerview/widget/RecyclerView;
    .param p4, "state"    # Landroidx/recyclerview/widget/RecyclerView$State;

    .line 236
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    const/4 v1, 0x0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    if-nez v0, :cond_0

    .line 237
    invoke-virtual {p1, v1, v1, v1, v1}, Landroid/graphics/Rect;->set(IIII)V

    .line 238
    return-void

    .line 241
    :cond_0
    invoke-virtual {p4}, Landroidx/recyclerview/widget/RecyclerView$State;->getItemCount()I

    move-result v0

    const/4 v2, 0x1

    sub-int/2addr v0, v2

    .line 242
    .local v0, "lastPosition":I
    invoke-virtual {p3, p2}, Landroidx/recyclerview/widget/RecyclerView;->getChildAdapterPosition(Landroid/view/View;)I

    move-result v3

    .line 244
    .local v3, "position":I
    iget-object v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    if-nez v4, :cond_1

    .line 245
    iput-object p3, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mRecyclerView:Landroidx/recyclerview/widget/RecyclerView;

    .line 248
    :cond_1
    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mHeaderNoShowSize:I

    if-gt v4, v3, :cond_2

    iget v4, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mFooterNoShowSize:I

    sub-int v4, v0, v4

    if-gt v3, v4, :cond_2

    move v4, v2

    goto :goto_0

    :cond_2
    move v4, v1

    .line 250
    .local v4, "isShowDivider":Z
    :goto_0
    iget v5, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mOrientation:I

    if-ne v5, v2, :cond_5

    .line 251
    if-eqz v4, :cond_4

    .line 252
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    if-eqz v2, :cond_3

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v2

    goto :goto_1

    :cond_3
    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDividerSpacing:I

    :goto_1
    invoke-virtual {p1, v1, v1, v1, v2}, Landroid/graphics/Rect;->set(IIII)V

    goto :goto_3

    .line 254
    :cond_4
    invoke-virtual {p1, v1, v1, v1, v1}, Landroid/graphics/Rect;->set(IIII)V

    goto :goto_3

    .line 257
    :cond_5
    if-eqz v4, :cond_7

    .line 258
    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    if-eqz v2, :cond_6

    iget-object v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v2

    goto :goto_2

    :cond_6
    iget v2, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDividerSpacing:I

    :goto_2
    invoke-virtual {p1, v1, v1, v2, v1}, Landroid/graphics/Rect;->set(IIII)V

    goto :goto_3

    .line 260
    :cond_7
    invoke-virtual {p1, v1, v1, v1, v1}, Landroid/graphics/Rect;->set(IIII)V

    .line 263
    :goto_3
    return-void
.end method

.method public onDraw(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V
    .locals 2
    .param p1, "canvas"    # Landroid/graphics/Canvas;
    .param p2, "parent"    # Landroidx/recyclerview/widget/RecyclerView;
    .param p3, "state"    # Landroidx/recyclerview/widget/RecyclerView$State;

    .line 129
    invoke-virtual {p2}, Landroidx/recyclerview/widget/RecyclerView;->getLayoutManager()Landroidx/recyclerview/widget/RecyclerView$LayoutManager;

    move-result-object v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mPaint:Landroid/graphics/Paint;

    if-nez v0, :cond_0

    goto :goto_1

    .line 132
    :cond_0
    iget v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mOrientation:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    .line 133
    invoke-direct {p0, p1, p2, p3}, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->drawVertical(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V

    goto :goto_0

    .line 135
    :cond_1
    invoke-direct {p0, p1, p2, p3}, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->drawHorizontal(Landroid/graphics/Canvas;Landroidx/recyclerview/widget/RecyclerView;Landroidx/recyclerview/widget/RecyclerView$State;)V

    .line 137
    :goto_0
    return-void

    .line 130
    :cond_2
    :goto_1
    return-void
.end method

.method public setDrawable(Landroid/graphics/drawable/Drawable;)Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;
    .locals 2
    .param p1, "drawable"    # Landroid/graphics/drawable/Drawable;

    .line 115
    if-eqz p1, :cond_0

    .line 118
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mDivider:Landroid/graphics/drawable/Drawable;

    .line 119
    return-object p0

    .line 116
    :cond_0
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "drawable-mdpi cannot be null."

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public setOrientation(I)Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;
    .locals 2
    .param p1, "orientation"    # I

    .line 102
    if-eqz p1, :cond_1

    const/4 v0, 0x1

    if-ne p1, v0, :cond_0

    goto :goto_0

    .line 103
    :cond_0
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "Invalid orientation. It should be either HORIZONTAL or VERTICAL"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 105
    :cond_1
    :goto_0
    iput p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/decoration/AppIconItemDecoration;->mOrientation:I

    .line 106
    return-object p0
.end method
