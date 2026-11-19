.class public Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
.super Landroid/view/View;
.source "GlowPadView.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;,
        Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;
    }
.end annotation


# instance fields
.field private mActiveTarget:I

.field private mAllowScaling:Z

.field private mAlwaysTrackFinger:Z

.field private mAnimatingTargets:Z

.field private mBackgroundAnimator:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

.field private mDirectionDescriptions:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mDirectionDescriptionsResourceId:I

.field private mDragging:Z

.field private mFeedbackCount:I

.field private mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

.field private mGlowRadius:F

.field private mGrabbedState:I

.field private mGravity:I

.field private mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

.field private mHorizontalInset:I

.field private mInitialLayout:Z

.field private mInnerRadius:F

.field private mMaxTargetHeight:I

.field private mMaxTargetWidth:I

.field private mNewTargetResources:I

.field private mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

.field private mOuterRadius:F

.field private mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

.field private mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

.field private mPointerId:I

.field private mResetListener:Landroid/animation/Animator$AnimatorListener;

.field private mResetListenerWithPing:Landroid/animation/Animator$AnimatorListener;

.field private mRingScaleFactor:F

.field private mSnapMargin:F

.field private mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

.field private mTargetDescriptions:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private mTargetDescriptionsResourceId:I

.field private mTargetDrawables:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;",
            ">;"
        }
    .end annotation
.end field

.field private mTargetResourceId:I

.field private mTargetUpdateListener:Landroid/animation/Animator$AnimatorListener;

.field private mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

.field private mVerticalInset:I

.field private mVibrationDuration:I

.field private mVibrator:Landroid/os/Vibrator;

.field private mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

.field private mWaveCenterX:F

.field private mWaveCenterY:F


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 214
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 215
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 11
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 218
    invoke-direct {p0, p1, p2}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 104
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    .line 105
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    const/4 v1, 0x0

    invoke-direct {v0, p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    .line 106
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-direct {v0, p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    .line 107
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-direct {v0, p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    .line 115
    const/4 v0, 0x3

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mFeedbackCount:I

    .line 116
    const/4 v2, 0x0

    iput v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrationDuration:I

    .line 118
    const/4 v3, -0x1

    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    .line 124
    const/high16 v3, 0x3f800000    # 1.0f

    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    .line 127
    const/4 v3, 0x0

    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    .line 128
    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    .line 168
    new-instance v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;

    invoke-direct {v4, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$1;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    iput-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mResetListener:Landroid/animation/Animator$AnimatorListener;

    .line 175
    new-instance v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$2;

    invoke-direct {v4, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$2;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    iput-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mResetListenerWithPing:Landroid/animation/Animator$AnimatorListener;

    .line 183
    new-instance v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$3;

    invoke-direct {v4, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$3;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    iput-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    .line 190
    new-instance v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;

    invoke-direct {v4, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$4;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    iput-object v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetUpdateListener:Landroid/animation/Animator$AnimatorListener;

    .line 206
    const/16 v4, 0x30

    iput v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGravity:I

    .line 207
    const/4 v5, 0x1

    iput-boolean v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInitialLayout:Z

    .line 219
    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    .line 221
    .local v6, "res":Landroid/content/res/Resources;
    sget-object v7, Lcom/android/keyguard/R$styleable;->GlowPadView:[I

    invoke-virtual {p1, p2, v7}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object v7

    .line 222
    .local v7, "a":Landroid/content/res/TypedArray;
    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInnerRadius:F

    const/4 v9, 0x7

    invoke-virtual {v7, v9, v8}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v8

    iput v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInnerRadius:F

    .line 223
    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/16 v9, 0x8

    invoke-virtual {v7, v9, v8}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v8

    iput v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    .line 224
    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    const/16 v9, 0xb

    invoke-virtual {v7, v9, v8}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v8

    iput v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    .line 225
    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrationDuration:I

    const/16 v9, 0xe

    invoke-virtual {v7, v9, v8}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v8

    iput v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrationDuration:I

    .line 227
    iget v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mFeedbackCount:I

    const/4 v9, 0x4

    invoke-virtual {v7, v9, v8}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v8

    iput v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mFeedbackCount:I

    .line 229
    invoke-virtual {v7, v5, v2}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    move-result v8

    iput-boolean v8, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAllowScaling:Z

    .line 230
    const/4 v8, 0x6

    invoke-virtual {v7, v8}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v8

    .line 231
    .local v8, "handle":Landroid/util/TypedValue;
    new-instance v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    if-eqz v8, :cond_0

    iget v10, v8, Landroid/util/TypedValue;->resourceId:I

    goto :goto_0

    :cond_0
    move v10, v2

    :goto_0
    invoke-direct {v9, v6, v10, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;-><init>(Landroid/content/res/Resources;II)V

    iput-object v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 232
    iget-object v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    sget-object v10, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v9, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 233
    new-instance v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    const/16 v10, 0x9

    .line 234
    invoke-direct {p0, v7, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getResourceId(Landroid/content/res/TypedArray;I)I

    move-result v10

    invoke-direct {v9, v6, v10, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;-><init>(Landroid/content/res/Resources;II)V

    iput-object v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 236
    const/4 v9, 0x2

    invoke-virtual {v7, v9, v2}, Landroid/content/res/TypedArray;->getBoolean(IZ)Z

    move-result v9

    iput-boolean v9, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAlwaysTrackFinger:Z

    .line 238
    const/16 v9, 0xa

    invoke-direct {p0, v7, v9}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getResourceId(Landroid/content/res/TypedArray;I)I

    move-result v9

    .line 239
    .local v9, "pointId":I
    if-eqz v9, :cond_1

    invoke-virtual {v6, v9}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    nop

    .line 240
    .local v1, "pointDrawable":Landroid/graphics/drawable/Drawable;
    :cond_1
    const/4 v10, 0x5

    invoke-virtual {v7, v10, v3}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v3

    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowRadius:F

    .line 242
    new-instance v3, Landroid/util/TypedValue;

    invoke-direct {v3}, Landroid/util/TypedValue;-><init>()V

    .line 245
    .local v3, "outValue":Landroid/util/TypedValue;
    const/16 v10, 0xd

    invoke-virtual {v7, v10, v3}, Landroid/content/res/TypedArray;->getValue(ILandroid/util/TypedValue;)Z

    move-result v10

    if-eqz v10, :cond_2

    .line 246
    iget v10, v3, Landroid/util/TypedValue;->resourceId:I

    invoke-direct {p0, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->internalSetTargetResources(I)V

    .line 248
    :cond_2
    iget-object v10, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    if-eqz v10, :cond_8

    iget-object v10, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v10}, Ljava/util/ArrayList;->size()I

    move-result v10

    if-eqz v10, :cond_8

    .line 253
    const/16 v10, 0xc

    invoke-virtual {v7, v10, v3}, Landroid/content/res/TypedArray;->getValue(ILandroid/util/TypedValue;)Z

    move-result v10

    if-eqz v10, :cond_4

    .line 254
    iget v10, v3, Landroid/util/TypedValue;->resourceId:I

    .line 255
    .local v10, "resourceId":I
    if-eqz v10, :cond_3

    .line 258
    invoke-virtual {p0, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setTargetDescriptionsResourceId(I)V

    .end local v10    # "resourceId":I
    goto :goto_1

    .line 256
    .restart local v10    # "resourceId":I
    :cond_3
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v2, "Must specify target descriptions"

    invoke-direct {v0, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 262
    .end local v10    # "resourceId":I
    :cond_4
    :goto_1
    invoke-virtual {v7, v0, v3}, Landroid/content/res/TypedArray;->getValue(ILandroid/util/TypedValue;)Z

    move-result v0

    if-eqz v0, :cond_6

    .line 263
    iget v0, v3, Landroid/util/TypedValue;->resourceId:I

    .line 264
    .local v0, "resourceId":I
    if-eqz v0, :cond_5

    .line 267
    invoke-virtual {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setDirectionDescriptionsResourceId(I)V

    .end local v0    # "resourceId":I
    goto :goto_2

    .line 265
    .restart local v0    # "resourceId":I
    :cond_5
    new-instance v2, Ljava/lang/IllegalStateException;

    const-string v4, "Must specify direction descriptions"

    invoke-direct {v2, v4}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v2

    .line 272
    .end local v0    # "resourceId":I
    :cond_6
    :goto_2
    invoke-virtual {v7, v2, v4}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGravity:I

    .line 273
    invoke-virtual {v7}, Landroid/content/res/TypedArray;->recycle()V

    .line 276
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrationDuration:I

    if-lez v0, :cond_7

    move v2, v5

    nop

    :cond_7
    invoke-virtual {p0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setVibrateEnabled(Z)V

    .line 278
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->assignDefaultsIfNeeded()V

    .line 280
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    invoke-direct {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;-><init>(Landroid/graphics/drawable/Drawable;)V

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    .line 281
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInnerRadius:F

    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    invoke-virtual {v0, v2, v4}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->makePointCloud(FF)V

    .line 282
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v0, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowRadius:F

    invoke-virtual {v0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->setRadius(F)V

    .line 283
    return-void

    .line 249
    :cond_8
    new-instance v0, Ljava/lang/IllegalStateException;

    const-string v2, "Must specify at least one target drawable"

    invoke-direct {v0, v2}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method static synthetic access$100(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 61
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    return v0
.end method

.method static synthetic access$200(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)F
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 61
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    return v0
.end method

.method static synthetic access$300(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;IFF)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p1, "x1"    # I
    .param p2, "x2"    # F
    .param p3, "x3"    # F

    .line 61
    invoke-direct {p0, p1, p2, p3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    return-void
.end method

.method static synthetic access$400(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 61
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->dispatchOnFinishFinalAnimation()V

    return-void
.end method

.method static synthetic access$500(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)I
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 61
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mNewTargetResources:I

    return v0
.end method

.method static synthetic access$502(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;I)I
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p1, "x1"    # I

    .line 61
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mNewTargetResources:I

    return p1
.end method

.method static synthetic access$600(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;I)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p1, "x1"    # I

    .line 61
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->internalSetTargetResources(I)V

    return-void
.end method

.method static synthetic access$700(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;ZZ)V
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p1, "x1"    # Z
    .param p2, "x2"    # Z

    .line 61
    invoke-direct {p0, p1, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideTargets(ZZ)V

    return-void
.end method

.method static synthetic access$802(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;Z)Z
    .locals 0
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;
    .param p1, "x1"    # Z

    .line 61
    iput-boolean p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAnimatingTargets:Z

    return p1
.end method

.method static synthetic access$900(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;
    .locals 1
    .param p0, "x0"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;

    .line 61
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    return-object v0
.end method

.method private announceTargets()V
    .locals 7

    .line 1209
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    .line 1210
    .local v0, "utterance":Ljava/lang/StringBuilder;
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    .line 1211
    .local v1, "targetCount":I
    const/4 v2, 0x0

    move v3, v2

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_1

    .line 1212
    invoke-direct {p0, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getTargetDescription(I)Ljava/lang/String;

    move-result-object v4

    .line 1213
    .local v4, "targetDescription":Ljava/lang/String;
    invoke-direct {p0, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getDirectionDescription(I)Ljava/lang/String;

    move-result-object v5

    .line 1214
    .local v5, "directionDescription":Ljava/lang/String;
    invoke-static {v4}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_0

    .line 1215
    invoke-static {v5}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v6

    if-nez v6, :cond_0

    .line 1216
    const/4 v6, 0x1

    new-array v6, v6, [Ljava/lang/Object;

    aput-object v4, v6, v2

    invoke-static {v5, v6}, Ljava/lang/String;->format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v6

    .line 1217
    .local v6, "text":Ljava/lang/String;
    invoke-virtual {v0, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 1211
    .end local v4    # "targetDescription":Ljava/lang/String;
    .end local v5    # "directionDescription":Ljava/lang/String;
    .end local v6    # "text":Ljava/lang/String;
    :cond_0
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1220
    .end local v3    # "i":I
    :cond_1
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->length()I

    move-result v2

    if-lez v2, :cond_2

    .line 1221
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->announceForAccessibility(Ljava/lang/CharSequence;)V

    .line 1223
    :cond_2
    return-void
.end method

.method private assignDefaultsIfNeeded()V
    .locals 4

    .line 1014
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/4 v1, 0x0

    cmpl-float v0, v0, v1

    if-nez v0, :cond_0

    .line 1015
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v0

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v2

    invoke-static {v0, v2}, Ljava/lang/Math;->max(II)I

    move-result v0

    int-to-float v0, v0

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v0, v2

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    .line 1017
    :cond_0
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    cmpl-float v0, v0, v1

    if-nez v0, :cond_1

    .line 1018
    const/4 v0, 0x1

    const/high16 v2, 0x41a00000    # 20.0f

    .line 1019
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v3

    invoke-virtual {v3}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v3

    .line 1018
    invoke-static {v0, v2, v3}, Landroid/util/TypedValue;->applyDimension(IFLandroid/util/DisplayMetrics;)F

    move-result v0

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    .line 1021
    :cond_1
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInnerRadius:F

    cmpl-float v0, v0, v1

    if-nez v0, :cond_2

    .line 1022
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v0

    int-to-float v0, v0

    const/high16 v1, 0x41200000    # 10.0f

    div-float/2addr v0, v1

    iput v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInnerRadius:F

    .line 1024
    :cond_2
    return-void
.end method

.method private computeInsets(II)V
    .locals 5
    .param p1, "dx"    # I
    .param p2, "dy"    # I

    .line 1027
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getLayoutDirection()I

    move-result v0

    .line 1028
    .local v0, "layoutDirection":I
    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGravity:I

    invoke-static {v1, v0}, Landroid/view/Gravity;->getAbsoluteGravity(II)I

    move-result v1

    .line 1030
    .local v1, "absoluteGravity":I
    and-int/lit8 v2, v1, 0x7

    const/4 v3, 0x3

    const/4 v4, 0x0

    if-eq v2, v3, :cond_1

    const/4 v3, 0x5

    if-eq v2, v3, :cond_0

    .line 1039
    div-int/lit8 v2, p1, 0x2

    iput v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHorizontalInset:I

    goto :goto_0

    .line 1035
    :cond_0
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHorizontalInset:I

    .line 1036
    goto :goto_0

    .line 1032
    :cond_1
    iput v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHorizontalInset:I

    .line 1033
    nop

    .line 1042
    :goto_0
    and-int/lit8 v2, v1, 0x70

    const/16 v3, 0x30

    if-eq v2, v3, :cond_3

    const/16 v3, 0x50

    if-eq v2, v3, :cond_2

    .line 1051
    div-int/lit8 v2, p2, 0x2

    iput v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVerticalInset:I

    goto :goto_1

    .line 1047
    :cond_2
    iput p2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVerticalInset:I

    .line 1048
    goto :goto_1

    .line 1044
    :cond_3
    iput v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVerticalInset:I

    .line 1045
    nop

    .line 1054
    :goto_1
    return-void
.end method

.method private computeScaleFactor(IIII)F
    .locals 7
    .param p1, "desiredWidth"    # I
    .param p2, "desiredHeight"    # I
    .param p3, "actualWidth"    # I
    .param p4, "actualHeight"    # I

    .line 1064
    iget-boolean v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAllowScaling:Z

    const/high16 v1, 0x3f800000    # 1.0f

    if-nez v0, :cond_0

    return v1

    .line 1066
    :cond_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getLayoutDirection()I

    move-result v0

    .line 1067
    .local v0, "layoutDirection":I
    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGravity:I

    invoke-static {v2, v0}, Landroid/view/Gravity;->getAbsoluteGravity(II)I

    move-result v2

    .line 1069
    .local v2, "absoluteGravity":I
    const/high16 v3, 0x3f800000    # 1.0f

    .line 1070
    .local v3, "scaleX":F
    const/high16 v4, 0x3f800000    # 1.0f

    .line 1076
    .local v4, "scaleY":F
    and-int/lit8 v5, v2, 0x7

    const/4 v6, 0x3

    if-eq v5, v6, :cond_1

    const/4 v6, 0x5

    if-eq v5, v6, :cond_1

    .line 1082
    if-le p1, p3, :cond_2

    .line 1083
    int-to-float v5, p3

    mul-float/2addr v5, v1

    iget v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    int-to-float v6, v6

    sub-float/2addr v5, v6

    iget v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    sub-int v6, p1, v6

    int-to-float v6, v6

    div-float v3, v5, v6

    goto :goto_0

    .line 1079
    :cond_1
    nop

    .line 1088
    :cond_2
    :goto_0
    and-int/lit8 v5, v2, 0x70

    const/16 v6, 0x30

    if-eq v5, v6, :cond_3

    const/16 v6, 0x50

    if-eq v5, v6, :cond_3

    .line 1094
    if-le p2, p4, :cond_4

    .line 1095
    int-to-float v5, p4

    mul-float/2addr v1, v5

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    int-to-float v5, v5

    sub-float/2addr v1, v5

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    sub-int v5, p2, v5

    int-to-float v5, v5

    div-float v4, v1, v5

    goto :goto_1

    .line 1091
    :cond_3
    nop

    .line 1100
    :cond_4
    :goto_1
    invoke-static {v3, v4}, Ljava/lang/Math;->min(FF)F

    move-result v1

    return v1
.end method

.method private deactivateTargets()V
    .locals 4

    .line 455
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 456
    .local v0, "count":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_0

    .line 457
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 458
    .local v2, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    sget-object v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 456
    .end local v2    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 460
    .end local v1    # "i":I
    :cond_0
    const/4 v1, -0x1

    iput v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    .line 461
    return-void
.end method

.method private dispatchOnFinishFinalAnimation()V
    .locals 1

    .line 475
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    if-eqz v0, :cond_0

    .line 476
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    invoke-interface {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;->onFinishFinalAnimation()V

    .line 478
    :cond_0
    return-void
.end method

.method private dispatchTriggerEvent(I)V
    .locals 1
    .param p1, "whichTarget"    # I

    .line 468
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->vibrate()V

    .line 469
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    if-eqz v0, :cond_0

    .line 470
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    invoke-interface {v0, p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;->onTrigger(Landroid/view/View;I)V

    .line 472
    :cond_0
    return-void
.end method

.method private dist2(FF)F
    .locals 2
    .param p1, "dx"    # F
    .param p2, "dy"    # F

    .line 1192
    mul-float v0, p1, p1

    mul-float v1, p2, p2

    add-float/2addr v0, v1

    return v0
.end method

.method private doFinish()V
    .locals 7

    .line 481
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    .line 482
    .local v0, "activeTarget":I
    const/4 v1, 0x1

    const/4 v2, 0x0

    const/4 v3, -0x1

    if-eq v0, v3, :cond_0

    move v3, v1

    goto :goto_0

    :cond_0
    move v3, v2

    .line 484
    .local v3, "targetHit":Z
    :goto_0
    const/4 v4, 0x0

    const/16 v5, 0xc8

    if-eqz v3, :cond_1

    .line 487
    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->highlightSelected(I)V

    .line 490
    const/16 v1, 0x4b0

    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mResetListener:Landroid/animation/Animator$AnimatorListener;

    invoke-direct {p0, v5, v1, v4, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideGlow(IIFLandroid/animation/Animator$AnimatorListener;)V

    .line 491
    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->dispatchTriggerEvent(I)V

    .line 492
    iget-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAlwaysTrackFinger:Z

    if-nez v1, :cond_2

    .line 494
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->stop()V

    goto :goto_1

    .line 498
    :cond_1
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mResetListenerWithPing:Landroid/animation/Animator$AnimatorListener;

    invoke-direct {p0, v5, v2, v4, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideGlow(IIFLandroid/animation/Animator$AnimatorListener;)V

    .line 499
    invoke-direct {p0, v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideTargets(ZZ)V

    .line 502
    :cond_2
    :goto_1
    invoke-direct {p0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setGrabbedState(I)V

    .line 503
    return-void
.end method

.method private getDirectionDescription(I)Ljava/lang/String;
    .locals 2
    .param p1, "index"    # I

    .line 1238
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1239
    :cond_0
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptionsResourceId:I

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->loadDescriptions(I)Ljava/util/ArrayList;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    .line 1240
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-eq v0, v1, :cond_1

    .line 1241
    const-string v0, "GlowPadView"

    const-string v1, "The number of target drawables must be equal to the number of direction descriptions."

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1243
    const/4 v0, 0x0

    return-object v0

    .line 1246
    :cond_1
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method private getResourceId(Landroid/content/res/TypedArray;I)I
    .locals 2
    .param p1, "a"    # Landroid/content/res/TypedArray;
    .param p2, "id"    # I

    .line 286
    invoke-virtual {p1, p2}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v0

    .line 287
    .local v0, "tv":Landroid/util/TypedValue;
    if-nez v0, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    iget v1, v0, Landroid/util/TypedValue;->resourceId:I

    :goto_0
    return v1
.end method

.method private getRingHeight()F
    .locals 4

    .line 1108
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v1

    int-to-float v1, v1

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v3, v2

    invoke-static {v1, v3}, Ljava/lang/Math;->max(FF)F

    move-result v1

    mul-float/2addr v0, v1

    return v0
.end method

.method private getRingWidth()F
    .locals 4

    .line 1104
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v1

    int-to-float v1, v1

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v3, v2

    invoke-static {v1, v3}, Ljava/lang/Math;->max(FF)F

    move-result v1

    mul-float/2addr v0, v1

    return v0
.end method

.method private getScaledGlowRadiusSquared()F
    .locals 3

    .line 1197
    nop

    .line 1198
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "accessibility"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/accessibility/AccessibilityManager;

    .line 1200
    .local v0, "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    invoke-virtual {v0}, Landroid/view/accessibility/AccessibilityManager;->isEnabled()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 1201
    const v1, 0x3fa66666    # 1.3f

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowRadius:F

    mul-float/2addr v1, v2

    .local v1, "scaledTapRadius":F
    goto :goto_0

    .line 1203
    .end local v1    # "scaledTapRadius":F
    :cond_0
    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowRadius:F

    .line 1205
    .restart local v1    # "scaledTapRadius":F
    :goto_0
    invoke-direct {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->square(F)F

    move-result v2

    return v2
.end method

.method private getTargetDescription(I)Ljava/lang/String;
    .locals 2
    .param p1, "index"    # I

    .line 1226
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 1227
    :cond_0
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptionsResourceId:I

    invoke-direct {p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->loadDescriptions(I)Ljava/util/ArrayList;

    move-result-object v0

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    .line 1228
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-eq v0, v1, :cond_1

    .line 1229
    const-string v0, "GlowPadView"

    const-string v1, "The number of target drawables must be equal to the number of target descriptions."

    invoke-static {v0, v1}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 1231
    const/4 v0, 0x0

    return-object v0

    .line 1234
    :cond_1
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/String;

    return-object v0
.end method

.method private handleCancel(Landroid/view/MotionEvent;)V
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 859
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointerId:I

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->findPointerIndex(I)I

    move-result v0

    .line 860
    .local v0, "actionIndex":I
    const/4 v1, -0x1

    if-ne v0, v1, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    move v1, v0

    :goto_0
    move v0, v1

    .line 861
    const/4 v1, 0x5

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v3

    invoke-direct {p0, v1, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 862
    return-void
.end method

.method private handleDown(Landroid/view/MotionEvent;)V
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 829
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionIndex()I

    move-result v0

    .line 830
    .local v0, "actionIndex":I
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v1

    .line 831
    .local v1, "eventX":F
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v2

    .line 832
    .local v2, "eventY":F
    const/4 v3, 0x1

    invoke-direct {p0, v3, v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 833
    invoke-direct {p0, v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->trySwitchToFirstTouchState(FF)Z

    move-result v3

    if-nez v3, :cond_0

    .line 834
    const/4 v3, 0x0

    iput-boolean v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDragging:Z

    goto :goto_0

    .line 836
    :cond_0
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v3

    iput v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointerId:I

    .line 837
    invoke-direct {p0, v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateGlowPosition(FF)V

    .line 839
    :goto_0
    return-void
.end method

.method private handleMove(Landroid/view/MotionEvent;)V
    .locals 36
    .param p1, "event"    # Landroid/view/MotionEvent;

    move-object/from16 v0, p0

    .line 865
    move-object/from16 v1, p1

    const/4 v2, -0x1

    .line 866
    .local v2, "activeTarget":I
    invoke-virtual/range {p1 .. p1}, Landroid/view/MotionEvent;->getHistorySize()I

    move-result v3

    .line 867
    .local v3, "historySize":I
    iget-object v4, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    .line 868
    .local v4, "targets":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;>;"
    invoke-virtual {v4}, Ljava/util/ArrayList;->size()I

    move-result v5

    .line 869
    .local v5, "ntargets":I
    const/4 v6, 0x0

    .line 870
    .local v6, "x":F
    const/4 v7, 0x0

    .line 871
    .local v7, "y":F
    iget v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointerId:I

    invoke-virtual {v1, v8}, Landroid/view/MotionEvent;->findPointerIndex(I)I

    move-result v8

    .line 873
    .local v8, "actionIndex":I
    const/4 v9, -0x1

    if-ne v8, v9, :cond_0

    .line 874
    return-void

    .line 877
    :cond_0
    move v11, v7

    move v7, v6

    move v6, v2

    const/4 v2, 0x0

    .local v2, "k":I
    .local v6, "activeTarget":I
    .local v7, "x":F
    .local v11, "y":F
    :goto_0
    add-int/lit8 v12, v3, 0x1

    if-ge v2, v12, :cond_b

    .line 878
    if-ge v2, v3, :cond_1

    invoke-virtual {v1, v8, v2}, Landroid/view/MotionEvent;->getHistoricalX(II)F

    move-result v12

    goto :goto_1

    .line 879
    :cond_1
    invoke-virtual {v1, v8}, Landroid/view/MotionEvent;->getX(I)F

    move-result v12

    .line 880
    .local v12, "eventX":F
    :goto_1
    if-ge v2, v3, :cond_2

    invoke-virtual {v1, v8, v2}, Landroid/view/MotionEvent;->getHistoricalY(II)F

    move-result v13

    goto :goto_2

    .line 881
    :cond_2
    invoke-virtual {v1, v8}, Landroid/view/MotionEvent;->getY(I)F

    move-result v13

    .line 883
    .local v13, "eventY":F
    :goto_2
    iget v14, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    sub-float v14, v12, v14

    .line 884
    .local v14, "tx":F
    iget v15, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    sub-float v15, v13, v15

    .line 885
    .local v15, "ty":F
    move/from16 v16, v11

    float-to-double v10, v14

    .end local v11    # "y":F
    .local v16, "y":F
    move/from16 v18, v7

    move/from16 v17, v8

    float-to-double v7, v15

    .end local v7    # "x":F
    .end local v8    # "actionIndex":I
    .local v17, "actionIndex":I
    .local v18, "x":F
    invoke-static {v10, v11, v7, v8}, Ljava/lang/Math;->hypot(DD)D

    move-result-wide v7

    double-to-float v7, v7

    .line 886
    .local v7, "touchRadius":F
    iget v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    cmpl-float v8, v7, v8

    if-lez v8, :cond_3

    iget v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    div-float/2addr v8, v7

    goto :goto_3

    :cond_3
    const/high16 v8, 0x3f800000    # 1.0f

    .line 887
    .local v8, "scale":F
    :goto_3
    mul-float v10, v14, v8

    .line 888
    .local v10, "limitX":F
    mul-float v11, v15, v8

    .line 889
    .local v11, "limitY":F
    neg-float v9, v15

    move/from16 v19, v7

    move/from16 v20, v8

    float-to-double v7, v9

    .end local v7    # "touchRadius":F
    .end local v8    # "scale":F
    .local v19, "touchRadius":F
    .local v20, "scale":F
    move/from16 v21, v2

    float-to-double v1, v14

    .end local v2    # "k":I
    .local v21, "k":I
    invoke-static {v7, v8, v1, v2}, Ljava/lang/Math;->atan2(DD)D

    move-result-wide v1

    .line 891
    .local v1, "angleRad":D
    iget-boolean v7, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDragging:Z

    if-nez v7, :cond_4

    .line 892
    invoke-direct {v0, v12, v13}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->trySwitchToFirstTouchState(FF)Z

    .line 895
    :cond_4
    iget-boolean v7, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDragging:Z

    if-eqz v7, :cond_a

    .line 897
    iget v7, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    iget v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    mul-float/2addr v7, v8

    iget v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mSnapMargin:F

    sub-float/2addr v7, v8

    .line 898
    .local v7, "snapRadius":F
    mul-float v8, v7, v7

    .line 900
    .local v8, "snapDistance2":F
    move v9, v6

    const/4 v6, 0x0

    .local v6, "i":I
    .local v9, "activeTarget":I
    :goto_4
    if-ge v6, v5, :cond_9

    .line 901
    invoke-virtual {v4, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v22

    move/from16 v23, v3

    move-object/from16 v3, v22

    check-cast v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 903
    .local v3, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    .local v23, "historySize":I
    move/from16 v24, v12

    move/from16 v25, v13

    int-to-double v12, v6

    .end local v12    # "eventX":F
    .end local v13    # "eventY":F
    .local v24, "eventX":F
    .local v25, "eventY":F
    const-wide/high16 v26, 0x3fe0000000000000L    # 0.5

    sub-double v12, v12, v26

    const-wide/high16 v28, 0x4000000000000000L    # 2.0

    mul-double v12, v12, v28

    const-wide v30, 0x400921fb54442d18L    # Math.PI

    mul-double v12, v12, v30

    move/from16 v33, v9

    move/from16 v32, v10

    int-to-double v9, v5

    .end local v9    # "activeTarget":I
    .end local v10    # "limitX":F
    .local v32, "limitX":F
    .local v33, "activeTarget":I
    div-double/2addr v12, v9

    .line 904
    .local v12, "targetMinRad":D
    int-to-double v9, v6

    add-double v9, v9, v26

    mul-double v9, v9, v28

    mul-double v9, v9, v30

    move/from16 v35, v6

    move/from16 v34, v7

    int-to-double v6, v5

    .end local v6    # "i":I
    .end local v7    # "snapRadius":F
    .local v34, "snapRadius":F
    .local v35, "i":I
    div-double/2addr v9, v6

    .line 905
    .local v9, "targetMaxRad":D
    invoke-virtual {v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->isEnabled()Z

    move-result v6

    if-eqz v6, :cond_8

    .line 906
    cmpl-double v6, v1, v12

    if-lez v6, :cond_5

    cmpg-double v6, v1, v9

    if-lez v6, :cond_6

    :cond_5
    const-wide v6, 0x401921fb54442d18L    # 6.283185307179586

    add-double v26, v1, v6

    cmpl-double v22, v26, v12

    if-lez v22, :cond_7

    add-double/2addr v6, v1

    cmpg-double v6, v6, v9

    if-gtz v6, :cond_7

    :cond_6
    const/4 v6, 0x1

    goto :goto_5

    :cond_7
    const/4 v6, 0x0

    .line 910
    .local v6, "angleMatches":Z
    :goto_5
    if-eqz v6, :cond_8

    invoke-direct {v0, v14, v15}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->dist2(FF)F

    move-result v7

    cmpl-float v7, v7, v8

    if-lez v7, :cond_8

    .line 911
    move/from16 v3, v35

    .line 900
    .end local v6    # "angleMatches":Z
    .end local v9    # "targetMaxRad":D
    .end local v12    # "targetMinRad":D
    .end local v33    # "activeTarget":I
    .local v3, "activeTarget":I
    move v9, v3

    goto :goto_6

    .end local v3    # "activeTarget":I
    .restart local v33    # "activeTarget":I
    :cond_8
    move/from16 v9, v33

    .end local v33    # "activeTarget":I
    .local v9, "activeTarget":I
    :goto_6
    add-int/lit8 v6, v35, 0x1

    .end local v35    # "i":I
    .local v6, "i":I
    move/from16 v3, v23

    move/from16 v12, v24

    move/from16 v13, v25

    move/from16 v10, v32

    move/from16 v7, v34

    goto :goto_4

    .line 916
    .end local v6    # "i":I
    .end local v8    # "snapDistance2":F
    .end local v23    # "historySize":I
    .end local v24    # "eventX":F
    .end local v25    # "eventY":F
    .end local v32    # "limitX":F
    .end local v34    # "snapRadius":F
    .local v3, "historySize":I
    .restart local v10    # "limitX":F
    .local v12, "eventX":F
    .restart local v13    # "eventY":F
    :cond_9
    move/from16 v23, v3

    move/from16 v33, v9

    move/from16 v32, v10

    move/from16 v24, v12

    move/from16 v25, v13

    move/from16 v6, v33

    .end local v3    # "historySize":I
    .end local v9    # "activeTarget":I
    .end local v10    # "limitX":F
    .end local v12    # "eventX":F
    .end local v13    # "eventY":F
    .restart local v23    # "historySize":I
    .restart local v24    # "eventX":F
    .restart local v25    # "eventY":F
    .restart local v32    # "limitX":F
    .restart local v33    # "activeTarget":I
    goto :goto_7

    .end local v23    # "historySize":I
    .end local v24    # "eventX":F
    .end local v25    # "eventY":F
    .end local v32    # "limitX":F
    .end local v33    # "activeTarget":I
    .restart local v3    # "historySize":I
    .local v6, "activeTarget":I
    .restart local v10    # "limitX":F
    .restart local v12    # "eventX":F
    .restart local v13    # "eventY":F
    :cond_a
    move/from16 v23, v3

    move/from16 v32, v10

    move/from16 v24, v12

    move/from16 v25, v13

    .end local v3    # "historySize":I
    .end local v10    # "limitX":F
    .end local v12    # "eventX":F
    .end local v13    # "eventY":F
    .restart local v23    # "historySize":I
    .restart local v24    # "eventX":F
    .restart local v25    # "eventY":F
    .restart local v32    # "limitX":F
    :goto_7
    move/from16 v7, v32

    .line 917
    .end local v18    # "x":F
    .local v7, "x":F
    nop

    .line 877
    .end local v1    # "angleRad":D
    .end local v14    # "tx":F
    .end local v15    # "ty":F
    .end local v16    # "y":F
    .end local v19    # "touchRadius":F
    .end local v20    # "scale":F
    .end local v24    # "eventX":F
    .end local v25    # "eventY":F
    .end local v32    # "limitX":F
    .local v11, "y":F
    add-int/lit8 v2, v21, 0x1

    .end local v21    # "k":I
    .restart local v2    # "k":I
    move/from16 v8, v17

    move/from16 v3, v23

    move-object/from16 v1, p1

    const/4 v9, -0x1

    goto/16 :goto_0

    .line 920
    .end local v2    # "k":I
    .end local v17    # "actionIndex":I
    .end local v23    # "historySize":I
    .restart local v3    # "historySize":I
    .local v8, "actionIndex":I
    :cond_b
    move/from16 v23, v3

    move/from16 v18, v7

    move/from16 v17, v8

    move/from16 v16, v11

    .end local v3    # "historySize":I
    .end local v7    # "x":F
    .end local v8    # "actionIndex":I
    .end local v11    # "y":F
    .restart local v16    # "y":F
    .restart local v17    # "actionIndex":I
    .restart local v18    # "x":F
    .restart local v23    # "historySize":I
    iget-boolean v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDragging:Z

    if-nez v1, :cond_c

    .line 921
    return-void

    .line 924
    :cond_c
    const/4 v1, -0x1

    if-eq v6, v1, :cond_d

    .line 925
    const/4 v1, 0x4

    move/from16 v11, v16

    move/from16 v7, v18

    invoke-direct {v0, v1, v7, v11}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 926
    .end local v16    # "y":F
    .end local v18    # "x":F
    .restart local v7    # "x":F
    .restart local v11    # "y":F
    invoke-direct {v0, v7, v11}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateGlowPosition(FF)V

    goto :goto_8

    .line 928
    .end local v7    # "x":F
    .end local v11    # "y":F
    .restart local v16    # "y":F
    .restart local v18    # "x":F
    :cond_d
    move/from16 v11, v16

    move/from16 v7, v18

    .end local v16    # "y":F
    .end local v18    # "x":F
    .restart local v7    # "x":F
    .restart local v11    # "y":F
    const/4 v1, 0x3

    invoke-direct {v0, v1, v7, v11}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 929
    invoke-direct {v0, v7, v11}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateGlowPosition(FF)V

    .line 932
    :goto_8
    iget v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    if-eq v1, v6, :cond_f

    .line 934
    iget v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    const/4 v2, -0x1

    if-eq v1, v2, :cond_e

    .line 935
    iget v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    invoke-virtual {v4, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 936
    .local v1, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    sget-object v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v1, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 939
    .end local v1    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    :cond_e
    if-eq v6, v2, :cond_f

    .line 940
    invoke-virtual {v4, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 941
    .restart local v1    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    sget-object v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_FOCUSED:[I

    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 942
    nop

    .line 943
    invoke-virtual/range {p0 .. p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v2

    const-string v3, "accessibility"

    invoke-virtual {v2, v3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/view/accessibility/AccessibilityManager;

    .line 945
    .local v2, "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    invoke-virtual {v2}, Landroid/view/accessibility/AccessibilityManager;->isEnabled()Z

    move-result v3

    if-eqz v3, :cond_f

    .line 946
    invoke-direct {v0, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getTargetDescription(I)Ljava/lang/String;

    move-result-object v3

    .line 947
    .local v3, "targetContentDescription":Ljava/lang/String;
    invoke-virtual {v0, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->announceForAccessibility(Ljava/lang/CharSequence;)V

    .line 951
    .end local v1    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    .end local v2    # "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    .end local v3    # "targetContentDescription":Ljava/lang/String;
    :cond_f
    iput v6, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mActiveTarget:I

    .line 952
    return-void
.end method

.method private handleUp(Landroid/view/MotionEvent;)V
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 843
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionIndex()I

    move-result v0

    .line 844
    .local v0, "actionIndex":I
    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getPointerId(I)I

    move-result v1

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointerId:I

    if-ne v1, v2, :cond_0

    .line 845
    const/4 v1, 0x5

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getX(I)F

    move-result v2

    invoke-virtual {p1, v0}, Landroid/view/MotionEvent;->getY(I)F

    move-result v3

    invoke-direct {p0, v1, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 847
    :cond_0
    return-void
.end method

.method private hideGlow(IIFLandroid/animation/Animator$AnimatorListener;)V
    .locals 8
    .param p1, "duration"    # I
    .param p2, "delay"    # I
    .param p3, "finalAlpha"    # F
    .param p4, "finishListener"    # Landroid/animation/Animator$AnimatorListener;

    .line 442
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->cancel()V

    .line 443
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v1, v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    int-to-long v2, p1

    const/16 v4, 0xe

    new-array v4, v4, [Ljava/lang/Object;

    const-string v5, "ease"

    const/4 v6, 0x0

    aput-object v5, v4, v6

    sget-object v5, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quart;->easeOut:Landroid/animation/TimeInterpolator;

    const/4 v6, 0x1

    aput-object v5, v4, v6

    const-string v5, "delay"

    const/4 v6, 0x2

    aput-object v5, v4, v6

    .line 445
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v6, 0x3

    aput-object v5, v4, v6

    const-string v5, "alpha"

    const/4 v6, 0x4

    aput-object v5, v4, v6

    .line 446
    invoke-static {p3}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    const/4 v6, 0x5

    aput-object v5, v4, v6

    const-string/jumbo v5, "x"

    const/4 v6, 0x6

    aput-object v5, v4, v6

    .line 447
    const/4 v5, 0x0

    invoke-static {v5}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v6

    const/4 v7, 0x7

    aput-object v6, v4, v7

    const-string/jumbo v6, "y"

    const/16 v7, 0x8

    aput-object v6, v4, v7

    .line 448
    invoke-static {v5}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    const/16 v6, 0x9

    aput-object v5, v4, v6

    const-string v5, "onUpdate"

    const/16 v6, 0xa

    aput-object v5, v4, v6

    iget-object v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    const/16 v6, 0xb

    aput-object v5, v4, v6

    const-string v5, "onComplete"

    const/16 v6, 0xc

    aput-object v5, v4, v6

    const/16 v5, 0xd

    aput-object p4, v4, v5

    .line 443
    invoke-static {v1, v2, v3, v4}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 451
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->start()V

    .line 452
    return-void
.end method

.method private hideTargets(ZZ)V
    .locals 23
    .param p1, "animate"    # Z
    .param p2, "expanded"    # Z

    move-object/from16 v0, p0

    move/from16 v1, p1

    .line 520
    iget-object v2, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->cancel()V

    .line 523
    iput-boolean v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAnimatingTargets:Z

    .line 524
    const/16 v2, 0xc8

    const/4 v3, 0x0

    if-eqz v1, :cond_0

    move v4, v2

    goto :goto_0

    :cond_0
    move v4, v3

    .line 525
    .local v4, "duration":I
    :goto_0
    if-eqz v1, :cond_1

    goto :goto_1

    :cond_1
    move v2, v3

    .line 527
    .local v2, "delay":I
    :goto_1
    if-eqz p2, :cond_2

    .line 528
    const/high16 v6, 0x3f800000    # 1.0f

    goto :goto_2

    :cond_2
    const v6, 0x3f4ccccd    # 0.8f

    .line 529
    .local v6, "targetScale":F
    :goto_2
    iget-object v7, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v7

    .line 530
    .local v7, "length":I
    sget-object v8, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeOut:Landroid/animation/TimeInterpolator;

    .line 531
    .local v8, "interpolator":Landroid/animation/TimeInterpolator;
    move v9, v3

    .local v9, "i":I
    :goto_3
    const/4 v15, 0x5

    const/16 v16, 0x4

    const/4 v5, 0x0

    const/16 v18, 0x3

    const/16 v19, 0x2

    const/16 v10, 0xc

    const/16 v20, 0x1

    if-ge v9, v7, :cond_3

    .line 532
    iget-object v11, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v11, v9}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v11

    check-cast v11, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 533
    .local v11, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    sget-object v12, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v11, v12}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 534
    iget-object v12, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    int-to-long v13, v4

    new-array v10, v10, [Ljava/lang/Object;

    const-string v21, "ease"

    aput-object v21, v10, v3

    aput-object v8, v10, v20

    const-string v20, "alpha"

    aput-object v20, v10, v19

    .line 536
    invoke-static {v5}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    aput-object v5, v10, v18

    const-string v5, "scaleX"

    aput-object v5, v10, v16

    .line 537
    invoke-static {v6}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    aput-object v5, v10, v15

    const-string v5, "scaleY"

    const/4 v15, 0x6

    aput-object v5, v10, v15

    .line 538
    invoke-static {v6}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    const/4 v15, 0x7

    aput-object v5, v10, v15

    const-string v5, "delay"

    const/16 v15, 0x8

    aput-object v5, v10, v15

    .line 539
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/16 v15, 0x9

    aput-object v5, v10, v15

    const-string v5, "onUpdate"

    const/16 v15, 0xa

    aput-object v5, v10, v15

    const/16 v5, 0xb

    iget-object v3, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    aput-object v3, v10, v5

    .line 534
    invoke-static {v11, v13, v14, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v3

    invoke-virtual {v12, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 531
    .end local v11    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    add-int/lit8 v9, v9, 0x1

    const/4 v3, 0x0

    goto :goto_3

    .line 543
    .end local v9    # "i":I
    :cond_3
    if-eqz p2, :cond_4

    .line 544
    const/high16 v17, 0x3f800000    # 1.0f

    goto :goto_4

    :cond_4
    const/high16 v3, 0x3f000000    # 0.5f

    move/from16 v17, v3

    :goto_4
    move/from16 v3, v17

    .line 545
    .local v3, "ringScaleTarget":F
    iget v9, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    mul-float/2addr v3, v9

    .line 546
    iget-object v9, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    iget-object v11, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    int-to-long v12, v4

    const/16 v14, 0xe

    new-array v14, v14, [Ljava/lang/Object;

    const-string v17, "ease"

    const/16 v21, 0x0

    aput-object v17, v14, v21

    aput-object v8, v14, v20

    const-string v17, "alpha"

    aput-object v17, v14, v19

    .line 548
    invoke-static {v5}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    aput-object v5, v14, v18

    const-string v5, "scaleX"

    aput-object v5, v14, v16

    .line 549
    invoke-static {v3}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    aput-object v5, v14, v15

    const-string v5, "scaleY"

    const/4 v15, 0x6

    aput-object v5, v14, v15

    .line 550
    invoke-static {v3}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    const/4 v15, 0x7

    aput-object v5, v14, v15

    const-string v5, "delay"

    const/16 v15, 0x8

    aput-object v5, v14, v15

    .line 551
    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/16 v15, 0x9

    aput-object v5, v14, v15

    const-string v5, "onUpdate"

    const/16 v15, 0xa

    aput-object v5, v14, v15

    const/16 v5, 0xb

    iget-object v10, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    aput-object v10, v14, v5

    const-string v5, "onComplete"

    const/16 v10, 0xc

    aput-object v5, v14, v10

    const/16 v5, 0xd

    iget-object v10, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetUpdateListener:Landroid/animation/Animator$AnimatorListener;

    aput-object v10, v14, v5

    .line 546
    invoke-static {v11, v12, v13, v14}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v5

    invoke-virtual {v9, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 555
    iget-object v5, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->start()V

    .line 556
    return-void
.end method

.method private hideUnselected(I)V
    .locals 3
    .param p1, "active"    # I

    .line 512
    const/4 v0, 0x0

    .local v0, "i":I
    :goto_0
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v1}, Ljava/util/ArrayList;->size()I

    move-result v1

    if-ge v0, v1, :cond_1

    .line 513
    if-eq v0, p1, :cond_0

    .line 514
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v1, v0}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setAlpha(F)V

    .line 512
    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 517
    .end local v0    # "i":I
    :cond_1
    return-void
.end method

.method private highlightSelected(I)V
    .locals 2
    .param p1, "activeTarget"    # I

    .line 507
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    sget-object v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_ACTIVE:[I

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 508
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideUnselected(I)V

    .line 509
    return-void
.end method

.method private internalSetTargetResources(I)V
    .locals 7
    .param p1, "resourceId"    # I

    .line 610
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->loadDrawableArray(I)Ljava/util/ArrayList;

    move-result-object v0

    .line 611
    .local v0, "targets":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;>;"
    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    .line 612
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetResourceId:I

    .line 614
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v1

    .line 615
    .local v1, "maxWidth":I
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v2

    .line 616
    .local v2, "maxHeight":I
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v3

    .line 617
    .local v3, "count":I
    const/4 v4, 0x0

    .local v4, "i":I
    :goto_0
    if-ge v4, v3, :cond_0

    .line 618
    invoke-virtual {v0, v4}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 619
    .local v5, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    invoke-virtual {v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v6

    invoke-static {v1, v6}, Ljava/lang/Math;->max(II)I

    move-result v1

    .line 620
    invoke-virtual {v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v6

    invoke-static {v2, v6}, Ljava/lang/Math;->max(II)I

    move-result v2

    .line 617
    .end local v5    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    add-int/lit8 v4, v4, 0x1

    goto :goto_0

    .line 622
    .end local v4    # "i":I
    :cond_0
    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    if-ne v4, v1, :cond_2

    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    if-eq v4, v2, :cond_1

    goto :goto_1

    .line 627
    :cond_1
    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    invoke-direct {p0, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateTargetPositions(FF)V

    .line 628
    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    invoke-direct {p0, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updatePointCloudPosition(FF)V

    goto :goto_2

    .line 623
    :cond_2
    :goto_1
    iput v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    .line 624
    iput v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    .line 625
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->requestLayout()V

    .line 630
    :goto_2
    return-void
.end method

.method private loadDescriptions(I)Ljava/util/ArrayList;
    .locals 5
    .param p1, "resourceId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/ArrayList<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .line 1250
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object v0

    .line 1251
    .local v0, "array":Landroid/content/res/TypedArray;
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->length()I

    move-result v1

    .line 1252
    .local v1, "count":I
    new-instance v2, Ljava/util/ArrayList;

    invoke-direct {v2, v1}, Ljava/util/ArrayList;-><init>(I)V

    .line 1253
    .local v2, "targetContentDescriptions":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Ljava/lang/String;>;"
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_0

    .line 1254
    invoke-virtual {v0, v3}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v4

    .line 1255
    .local v4, "contentDescription":Ljava/lang/String;
    invoke-virtual {v2, v4}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 1253
    .end local v4    # "contentDescription":Ljava/lang/String;
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1257
    .end local v3    # "i":I
    :cond_0
    invoke-virtual {v0}, Landroid/content/res/TypedArray;->recycle()V

    .line 1258
    return-object v2
.end method

.method private loadDrawableArray(I)Ljava/util/ArrayList;
    .locals 10
    .param p1, "resourceId"    # I
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(I)",
            "Ljava/util/ArrayList<",
            "Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;",
            ">;"
        }
    .end annotation

    .line 595
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 596
    .local v0, "res":Landroid/content/res/Resources;
    invoke-virtual {v0, p1}, Landroid/content/res/Resources;->obtainTypedArray(I)Landroid/content/res/TypedArray;

    move-result-object v1

    .line 597
    .local v1, "array":Landroid/content/res/TypedArray;
    invoke-virtual {v1}, Landroid/content/res/TypedArray;->length()I

    move-result v2

    .line 598
    .local v2, "count":I
    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3, v2}, Ljava/util/ArrayList;-><init>(I)V

    .line 599
    .local v3, "drawables":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;>;"
    const/4 v4, 0x0

    move v5, v4

    .local v5, "i":I
    :goto_0
    if-ge v5, v2, :cond_1

    .line 600
    invoke-virtual {v1, v5}, Landroid/content/res/TypedArray;->peekValue(I)Landroid/util/TypedValue;

    move-result-object v6

    .line 601
    .local v6, "value":Landroid/util/TypedValue;
    new-instance v7, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 602
    if-eqz v6, :cond_0

    iget v8, v6, Landroid/util/TypedValue;->resourceId:I

    goto :goto_1

    :cond_0
    move v8, v4

    :goto_1
    const/4 v9, 0x3

    invoke-direct {v7, v0, v8, v9}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;-><init>(Landroid/content/res/Resources;II)V

    .line 603
    .local v7, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    invoke-virtual {v3, v7}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 599
    .end local v6    # "value":Landroid/util/TypedValue;
    .end local v7    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 605
    .end local v5    # "i":I
    :cond_1
    invoke-virtual {v1}, Landroid/content/res/TypedArray;->recycle()V

    .line 606
    return-object v3
.end method

.method private resolveMeasured(II)I
    .locals 4
    .param p1, "measureSpec"    # I
    .param p2, "desired"    # I

    .line 347
    const/4 v0, 0x0

    .line 348
    .local v0, "result":I
    invoke-static {p1}, Landroid/view/View$MeasureSpec;->getSize(I)I

    move-result v1

    .line 349
    .local v1, "specSize":I
    invoke-static {p1}, Landroid/view/View$MeasureSpec;->getMode(I)I

    move-result v2

    const/high16 v3, -0x80000000

    if-eq v2, v3, :cond_1

    if-eqz v2, :cond_0

    .line 358
    move v0, v1

    goto :goto_0

    .line 351
    :cond_0
    move v0, p2

    .line 352
    goto :goto_0

    .line 354
    :cond_1
    invoke-static {v1, p2}, Ljava/lang/Math;->min(II)I

    move-result v0

    .line 355
    nop

    .line 360
    :goto_0
    return v0
.end method

.method private setGrabbedState(I)V
    .locals 2
    .param p1, "newState"    # I

    .line 984
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGrabbedState:I

    if-eq p1, v0, :cond_2

    .line 985
    if-eqz p1, :cond_0

    .line 986
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->vibrate()V

    .line 988
    :cond_0
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGrabbedState:I

    .line 989
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    if-eqz v0, :cond_2

    .line 990
    const/4 v0, 0x1

    if-nez p1, :cond_1

    .line 991
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    invoke-interface {v1, p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;->onReleased(Landroid/view/View;I)V

    goto :goto_0

    .line 993
    :cond_1
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    invoke-interface {v1, p0, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;->onGrabbed(Landroid/view/View;I)V

    .line 995
    :goto_0
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    invoke-interface {v0, p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;->onGrabbedStateChange(Landroid/view/View;I)V

    .line 998
    :cond_2
    return-void
.end method

.method private showGlow(IIFLandroid/animation/Animator$AnimatorListener;)V
    .locals 7
    .param p1, "duration"    # I
    .param p2, "delay"    # I
    .param p3, "finalAlpha"    # F
    .param p4, "finishListener"    # Landroid/animation/Animator$AnimatorListener;

    .line 430
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->cancel()V

    .line 431
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v1, v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    int-to-long v2, p1

    const/16 v4, 0xa

    new-array v4, v4, [Ljava/lang/Object;

    const-string v5, "ease"

    const/4 v6, 0x0

    aput-object v5, v4, v6

    sget-object v5, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeIn:Landroid/animation/TimeInterpolator;

    const/4 v6, 0x1

    aput-object v5, v4, v6

    const-string v5, "delay"

    const/4 v6, 0x2

    aput-object v5, v4, v6

    .line 433
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    const/4 v6, 0x3

    aput-object v5, v4, v6

    const-string v5, "alpha"

    const/4 v6, 0x4

    aput-object v5, v4, v6

    .line 434
    invoke-static {p3}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v5

    const/4 v6, 0x5

    aput-object v5, v4, v6

    const-string v5, "onUpdate"

    const/4 v6, 0x6

    aput-object v5, v4, v6

    iget-object v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    const/4 v6, 0x7

    aput-object v5, v4, v6

    const-string v5, "onComplete"

    const/16 v6, 0x8

    aput-object v5, v4, v6

    const/16 v5, 0x9

    aput-object p4, v4, v5

    .line 431
    invoke-static {v1, v2, v3, v4}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 437
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mGlowAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->start()V

    .line 438
    return-void
.end method

.method private showTargets(Z)V
    .locals 20
    .param p1, "animate"    # Z

    move-object/from16 v0, p0

    .line 559
    move/from16 v1, p1

    iget-object v2, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->stop()V

    .line 560
    iput-boolean v1, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAnimatingTargets:Z

    .line 561
    const/4 v2, 0x0

    if-eqz v1, :cond_0

    const/16 v3, 0x32

    goto :goto_0

    :cond_0
    move v3, v2

    .line 562
    .local v3, "delay":I
    :goto_0
    if-eqz v1, :cond_1

    const/16 v4, 0xc8

    goto :goto_1

    :cond_1
    move v4, v2

    .line 563
    .local v4, "duration":I
    :goto_1
    iget-object v5, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v5}, Ljava/util/ArrayList;->size()I

    move-result v5

    .line 564
    .local v5, "length":I
    move v6, v2

    .local v6, "i":I
    :goto_2
    const/4 v13, 0x5

    const/4 v14, 0x4

    const/4 v15, 0x3

    const/16 v16, 0x2

    const/16 v7, 0xc

    const/16 v17, 0x1

    const/high16 v8, 0x3f800000    # 1.0f

    if-ge v6, v5, :cond_2

    .line 565
    iget-object v9, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v9, v6}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 566
    .local v9, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    sget-object v10, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v9, v10}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 567
    iget-object v10, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    int-to-long v11, v4

    new-array v7, v7, [Ljava/lang/Object;

    const-string v18, "ease"

    aput-object v18, v7, v2

    sget-object v18, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeOut:Landroid/animation/TimeInterpolator;

    aput-object v18, v7, v17

    const-string v17, "alpha"

    aput-object v17, v7, v16

    .line 569
    invoke-static {v8}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v16

    aput-object v16, v7, v15

    const-string v15, "scaleX"

    aput-object v15, v7, v14

    .line 570
    invoke-static {v8}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v14

    aput-object v14, v7, v13

    const-string v13, "scaleY"

    const/4 v14, 0x6

    aput-object v13, v7, v14

    .line 571
    invoke-static {v8}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v8

    const/4 v13, 0x7

    aput-object v8, v7, v13

    const-string v8, "delay"

    const/16 v13, 0x8

    aput-object v8, v7, v13

    .line 572
    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    const/16 v13, 0x9

    aput-object v8, v7, v13

    const-string v8, "onUpdate"

    const/16 v13, 0xa

    aput-object v8, v7, v13

    iget-object v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    const/16 v13, 0xb

    aput-object v8, v7, v13

    .line 567
    invoke-static {v9, v11, v12, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v7

    invoke-virtual {v10, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 564
    .end local v9    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    add-int/lit8 v6, v6, 0x1

    goto :goto_2

    .line 575
    .end local v6    # "i":I
    :cond_2
    iget v6, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    mul-float/2addr v6, v8

    .line 576
    .local v6, "ringScale":F
    iget-object v9, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    iget-object v10, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    int-to-long v11, v4

    const/16 v7, 0xe

    new-array v7, v7, [Ljava/lang/Object;

    const-string v18, "ease"

    aput-object v18, v7, v2

    sget-object v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeOut:Landroid/animation/TimeInterpolator;

    aput-object v2, v7, v17

    const-string v2, "alpha"

    aput-object v2, v7, v16

    .line 578
    invoke-static {v8}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v2

    aput-object v2, v7, v15

    const-string v2, "scaleX"

    aput-object v2, v7, v14

    .line 579
    invoke-static {v6}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v2

    aput-object v2, v7, v13

    const-string v2, "scaleY"

    const/4 v8, 0x6

    aput-object v2, v7, v8

    .line 580
    invoke-static {v6}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v2

    const/4 v8, 0x7

    aput-object v2, v7, v8

    const-string v2, "delay"

    const/16 v8, 0x8

    aput-object v2, v7, v8

    .line 581
    invoke-static {v3}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    const/16 v8, 0x9

    aput-object v2, v7, v8

    const-string v2, "onUpdate"

    const/16 v8, 0xa

    aput-object v2, v7, v8

    iget-object v2, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    const/16 v8, 0xb

    aput-object v2, v7, v8

    const-string v2, "onComplete"

    const/16 v8, 0xc

    aput-object v2, v7, v8

    const/16 v2, 0xd

    iget-object v8, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetUpdateListener:Landroid/animation/Animator$AnimatorListener;

    aput-object v8, v7, v2

    .line 576
    invoke-static {v10, v11, v12, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v2

    invoke-virtual {v9, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 585
    iget-object v2, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->start()V

    .line 586
    return-void
.end method

.method private square(F)F
    .locals 1
    .param p1, "d"    # F

    .line 1188
    mul-float v0, p1, p1

    return v0
.end method

.method private startBackgroundAnimation(IF)V
    .locals 6
    .param p1, "duration"    # I
    .param p2, "alpha"    # F

    .line 768
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getBackground()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 769
    .local v0, "background":Landroid/graphics/drawable/Drawable;
    iget-boolean v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAlwaysTrackFinger:Z

    if-eqz v1, :cond_1

    if-eqz v0, :cond_1

    .line 770
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mBackgroundAnimator:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    if-eqz v1, :cond_0

    .line 771
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mBackgroundAnimator:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v1, v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v1}, Landroid/animation/ObjectAnimator;->cancel()V

    .line 773
    :cond_0
    int-to-long v1, p1

    const/4 v3, 0x6

    new-array v3, v3, [Ljava/lang/Object;

    const/4 v4, 0x0

    const-string v5, "ease"

    aput-object v5, v3, v4

    const/4 v4, 0x1

    sget-object v5, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Cubic;->easeIn:Landroid/animation/TimeInterpolator;

    aput-object v5, v3, v4

    const/4 v4, 0x2

    const-string v5, "alpha"

    aput-object v5, v3, v4

    const/4 v4, 0x3

    const/high16 v5, 0x437f0000    # 255.0f

    mul-float/2addr v5, p2

    float-to-int v5, v5

    .line 775
    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    const/4 v4, 0x4

    const-string v5, "delay"

    aput-object v5, v3, v4

    const/4 v4, 0x5

    const/16 v5, 0x32

    .line 776
    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v5

    aput-object v5, v3, v4

    .line 773
    invoke-static {v0, v1, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v1

    iput-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mBackgroundAnimator:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 777
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mBackgroundAnimator:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v1, v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v1}, Landroid/animation/ObjectAnimator;->start()V

    .line 779
    :cond_1
    return-void
.end method

.method private startWaveAnimation()V
    .locals 7

    .line 733
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->cancel()V

    .line 734
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v0, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    const/high16 v1, 0x3f800000    # 1.0f

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->setAlpha(F)V

    .line 735
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v0, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v1

    int-to-float v1, v1

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v1, v2

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->setRadius(F)V

    .line 736
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v1, v1, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    const/16 v3, 0xa

    new-array v3, v3, [Ljava/lang/Object;

    const-string v4, "ease"

    const/4 v5, 0x0

    aput-object v4, v3, v5

    sget-object v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Ease$Quad;->easeOut:Landroid/animation/TimeInterpolator;

    const/4 v6, 0x1

    aput-object v4, v3, v6

    const-string v4, "delay"

    const/4 v6, 0x2

    aput-object v4, v3, v6

    .line 738
    invoke-static {v5}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v4

    const/4 v5, 0x3

    aput-object v4, v3, v5

    const-string v4, "radius"

    const/4 v5, 0x4

    aput-object v4, v3, v5

    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    mul-float/2addr v2, v4

    .line 739
    invoke-static {v2}, Ljava/lang/Float;->valueOf(F)Ljava/lang/Float;

    move-result-object v2

    const/4 v4, 0x5

    aput-object v2, v3, v4

    const-string v2, "onUpdate"

    const/4 v4, 0x6

    aput-object v2, v3, v4

    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mUpdateListener:Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    const/4 v4, 0x7

    aput-object v2, v3, v4

    const-string v2, "onComplete"

    const/16 v4, 0x8

    aput-object v2, v3, v4

    new-instance v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$5;

    invoke-direct {v2, p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$5;-><init>(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;)V

    const/16 v4, 0x9

    aput-object v2, v3, v4

    .line 736
    const-wide/16 v4, 0x546

    invoke-static {v1, v4, v5, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    move-result-object v1

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->add(Ljava/lang/Object;)Z

    .line 748
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->start()V

    .line 749
    return-void
.end method

.method private stopAndHideWaveAnimation()V
    .locals 2

    .line 728
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->cancel()V

    .line 729
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v0, v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->waveManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$WaveManager;->setAlpha(F)V

    .line 730
    return-void
.end method

.method private switchToState(IFF)V
    .locals 4
    .param p1, "state"    # I
    .param p2, "x"    # F
    .param p3, "y"    # F

    .line 381
    const/high16 v0, 0x3f800000    # 1.0f

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v3, 0x0

    packed-switch p1, :pswitch_data_0

    goto :goto_0

    .line 423
    :pswitch_0
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->doFinish()V

    goto :goto_0

    .line 418
    :pswitch_1
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setAlpha(F)V

    .line 419
    invoke-direct {p0, v3, v3, v2, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->showGlow(IIFLandroid/animation/Animator$AnimatorListener;)V

    .line 420
    goto :goto_0

    .line 413
    :pswitch_2
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setAlpha(F)V

    .line 414
    goto :goto_0

    .line 398
    :pswitch_3
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setAlpha(F)V

    .line 399
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->deactivateTargets()V

    .line 400
    const/4 v1, 0x1

    invoke-direct {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->showTargets(Z)V

    .line 401
    const/16 v2, 0xc8

    invoke-direct {p0, v2, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->startBackgroundAnimation(IF)V

    .line 402
    invoke-direct {p0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setGrabbedState(I)V

    .line 404
    nop

    .line 405
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "accessibility"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/accessibility/AccessibilityManager;

    .line 407
    .local v0, "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    invoke-virtual {v0}, Landroid/view/accessibility/AccessibilityManager;->isEnabled()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 408
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->announceTargets()V

    goto :goto_0

    .line 394
    .end local v0    # "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    :pswitch_4
    invoke-direct {p0, v3, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->startBackgroundAnimation(IF)V

    .line 395
    goto :goto_0

    .line 383
    :pswitch_5
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->deactivateTargets()V

    .line 385
    invoke-direct {p0, v3, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideTargets(ZZ)V

    .line 387
    invoke-direct {p0, v3, v3, v2, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideGlow(IIFLandroid/animation/Animator$AnimatorListener;)V

    .line 388
    invoke-direct {p0, v3, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->startBackgroundAnimation(IF)V

    .line 389
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    sget-object v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->STATE_INACTIVE:[I

    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setState([I)V

    .line 390
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setAlpha(F)V

    .line 391
    nop

    .line 426
    :cond_0
    :goto_0
    return-void

    nop

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method private trySwitchToFirstTouchState(FF)Z
    .locals 4
    .param p1, "x"    # F
    .param p2, "y"    # F

    .line 1001
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    sub-float v0, p1, v0

    .line 1002
    .local v0, "tx":F
    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    sub-float v1, p2, v1

    .line 1003
    .local v1, "ty":F
    iget-boolean v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mAlwaysTrackFinger:Z

    if-nez v2, :cond_1

    invoke-direct {p0, v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->dist2(FF)F

    move-result v2

    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getScaledGlowRadiusSquared()F

    move-result v3

    cmpg-float v2, v2, v3

    if-gtz v2, :cond_0

    goto :goto_0

    .line 1010
    :cond_0
    const/4 v2, 0x0

    return v2

    .line 1005
    :cond_1
    :goto_0
    const/4 v2, 0x2

    invoke-direct {p0, v2, p1, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->switchToState(IFF)V

    .line 1006
    invoke-direct {p0, v0, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateGlowPosition(FF)V

    .line 1007
    const/4 v2, 0x1

    iput-boolean v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDragging:Z

    .line 1008
    return v2
.end method

.method private updateGlowPosition(FF)V
    .locals 4
    .param p1, "x"    # F
    .param p2, "y"    # F

    .line 820
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getX()F

    move-result v0

    sub-float v0, p1, v0

    .line 821
    .local v0, "dx":F
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getY()F

    move-result v1

    sub-float v1, p2, v1

    .line 822
    .local v1, "dy":F
    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    const/high16 v3, 0x3f800000    # 1.0f

    div-float v2, v3, v2

    mul-float/2addr v0, v2

    .line 823
    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    div-float/2addr v3, v2

    mul-float/2addr v1, v3

    .line 824
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v2, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    iget-object v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getX()F

    move-result v3

    add-float/2addr v3, v0

    invoke-virtual {v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->setX(F)V

    .line 825
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget-object v2, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->glowManager:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;

    iget-object v3, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getY()F

    move-result v3

    add-float/2addr v3, v1

    invoke-virtual {v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud$GlowManager;->setY(F)V

    .line 826
    return-void
.end method

.method private updatePointCloudPosition(FF)V
    .locals 1
    .param p1, "centerX"    # F
    .param p2, "centerY"    # F

    .line 1166
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    invoke-virtual {v0, p1, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->setCenter(FF)V

    .line 1167
    return-void
.end method

.method private updateTargetPositions(FF)V
    .locals 10
    .param p1, "centerX"    # F
    .param p2, "centerY"    # F

    .line 1152
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    .line 1153
    .local v0, "targets":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;>;"
    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v1

    .line 1154
    .local v1, "size":I
    int-to-double v2, v1

    const-wide v4, -0x3fe6de04abbbd2e8L    # -6.283185307179586

    div-double/2addr v4, v2

    double-to-float v2, v4

    .line 1155
    .local v2, "alpha":F
    const/4 v3, 0x0

    .local v3, "i":I
    :goto_0
    if-ge v3, v1, :cond_0

    .line 1156
    invoke-virtual {v0, v3}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 1157
    .local v4, "targetIcon":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    int-to-float v5, v3

    mul-float/2addr v5, v2

    .line 1158
    .local v5, "angle":F
    invoke-virtual {v4, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionX(F)V

    .line 1159
    invoke-virtual {v4, p2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionY(F)V

    .line 1160
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getRingWidth()F

    move-result v6

    const/high16 v7, 0x40000000    # 2.0f

    div-float/2addr v6, v7

    float-to-double v8, v5

    invoke-static {v8, v9}, Ljava/lang/Math;->cos(D)D

    move-result-wide v8

    double-to-float v8, v8

    mul-float/2addr v6, v8

    invoke-virtual {v4, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setX(F)V

    .line 1161
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getRingHeight()F

    move-result v6

    div-float/2addr v6, v7

    float-to-double v7, v5

    invoke-static {v7, v8}, Ljava/lang/Math;->sin(D)D

    move-result-wide v7

    double-to-float v7, v7

    mul-float/2addr v6, v7

    invoke-virtual {v4, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setY(F)V

    .line 1155
    .end local v4    # "targetIcon":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    .end local v5    # "angle":F
    add-int/lit8 v3, v3, 0x1

    goto :goto_0

    .line 1163
    .end local v3    # "i":I
    :cond_0
    return-void
.end method

.method private vibrate()V
    .locals 3

    .line 589
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrator:Landroid/os/Vibrator;

    if-eqz v0, :cond_0

    .line 590
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrator:Landroid/os/Vibrator;

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrationDuration:I

    int-to-long v1, v1

    invoke-virtual {v0, v1, v2}, Landroid/os/Vibrator;->vibrate(J)V

    .line 592
    :cond_0
    return-void
.end method


# virtual methods
.method public getResourceIdForTarget(I)I
    .locals 2
    .param p1, "index"    # I

    .line 1262
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 1263
    .local v0, "drawable":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    if-nez v0, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getResourceId()I

    move-result v1

    :goto_0
    return v1
.end method

.method protected getScaledSuggestedMinimumHeight()I
    .locals 4

    .line 341
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v1

    int-to-float v1, v1

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v3, v2

    invoke-static {v1, v3}, Ljava/lang/Math;->max(FF)F

    move-result v1

    mul-float/2addr v0, v1

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    int-to-float v1, v1

    add-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method protected getScaledSuggestedMinimumWidth()I
    .locals 4

    .line 333
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v1

    int-to-float v1, v1

    iget v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v3, 0x40000000    # 2.0f

    mul-float/2addr v3, v2

    invoke-static {v1, v3}, Ljava/lang/Math;->max(FF)F

    move-result v1

    mul-float/2addr v0, v1

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    int-to-float v1, v1

    add-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method protected getSuggestedMinimumHeight()I
    .locals 3

    .line 326
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getHeight()I

    move-result v0

    int-to-float v0, v0

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v2, 0x40000000    # 2.0f

    mul-float/2addr v2, v1

    invoke-static {v0, v2}, Ljava/lang/Math;->max(FF)F

    move-result v0

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    int-to-float v1, v1

    add-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method protected getSuggestedMinimumWidth()I
    .locals 3

    .line 319
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->getWidth()I

    move-result v0

    int-to-float v0, v0

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRadius:F

    const/high16 v2, 0x40000000    # 2.0f

    mul-float/2addr v2, v1

    invoke-static {v0, v2}, Ljava/lang/Math;->max(FF)F

    move-result v0

    iget v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    int-to-float v1, v1

    add-float/2addr v0, v1

    float-to-int v0, v0

    return v0
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 3
    .param p1, "canvas"    # Landroid/graphics/Canvas;

    .line 1171
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    invoke-virtual {v0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->draw(Landroid/graphics/Canvas;)V

    .line 1172
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->draw(Landroid/graphics/Canvas;)V

    .line 1173
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->size()I

    move-result v0

    .line 1174
    .local v0, "ntargets":I
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    if-ge v1, v0, :cond_1

    .line 1175
    iget-object v2, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDrawables:Ljava/util/ArrayList;

    invoke-virtual {v2, v1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    .line 1176
    .local v2, "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    if-eqz v2, :cond_0

    .line 1177
    invoke-virtual {v2, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->draw(Landroid/graphics/Canvas;)V

    .line 1174
    .end local v2    # "target":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;
    :cond_0
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 1180
    .end local v1    # "i":I
    :cond_1
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v1, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->draw(Landroid/graphics/Canvas;)V

    .line 1181
    return-void
.end method

.method public onHoverEvent(Landroid/view/MotionEvent;)Z
    .locals 4
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 956
    nop

    .line 957
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "accessibility"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/accessibility/AccessibilityManager;

    .line 959
    .local v0, "accessibilityManager":Landroid/view/accessibility/AccessibilityManager;
    invoke-virtual {v0}, Landroid/view/accessibility/AccessibilityManager;->isTouchExplorationEnabled()Z

    move-result v1

    const/4 v2, 0x1

    if-eqz v1, :cond_1

    .line 960
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v1

    .line 961
    .local v1, "action":I
    const/4 v3, 0x7

    if-eq v1, v3, :cond_0

    packed-switch v1, :pswitch_data_0

    goto :goto_0

    .line 969
    :pswitch_0
    invoke-virtual {p1, v2}, Landroid/view/MotionEvent;->setAction(I)V

    goto :goto_0

    .line 963
    :pswitch_1
    const/4 v3, 0x0

    invoke-virtual {p1, v3}, Landroid/view/MotionEvent;->setAction(I)V

    .line 964
    goto :goto_0

    .line 966
    :cond_0
    const/4 v3, 0x2

    invoke-virtual {p1, v3}, Landroid/view/MotionEvent;->setAction(I)V

    .line 967
    nop

    .line 972
    :goto_0
    invoke-virtual {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->onTouchEvent(Landroid/view/MotionEvent;)Z

    .line 973
    invoke-virtual {p1, v1}, Landroid/view/MotionEvent;->setAction(I)V

    .line 975
    .end local v1    # "action":I
    :cond_1
    invoke-super {p0, p1}, Landroid/view/View;->onHoverEvent(Landroid/view/MotionEvent;)Z

    .line 976
    return v2

    nop

    :pswitch_data_0
    .packed-switch 0x9
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method protected onLayout(ZIIII)V
    .locals 8
    .param p1, "changed"    # Z
    .param p2, "left"    # I
    .param p3, "top"    # I
    .param p4, "right"    # I
    .param p5, "bottom"    # I

    .line 1113
    invoke-super/range {p0 .. p5}, Landroid/view/View;->onLayout(ZIIII)V

    .line 1114
    sub-int v0, p4, p2

    .line 1115
    .local v0, "width":I
    sub-int v1, p5, p3

    .line 1119
    .local v1, "height":I
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getRingWidth()F

    move-result v2

    .line 1120
    .local v2, "placementWidth":F
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getRingHeight()F

    move-result v3

    .line 1121
    .local v3, "placementHeight":F
    iget v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHorizontalInset:I

    int-to-float v4, v4

    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetWidth:I

    int-to-float v5, v5

    add-float/2addr v5, v2

    const/high16 v6, 0x40000000    # 2.0f

    div-float/2addr v5, v6

    add-float/2addr v4, v5

    .line 1123
    .local v4, "newWaveCenterX":F
    iget v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVerticalInset:I

    int-to-float v5, v5

    iget v7, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mMaxTargetHeight:I

    int-to-float v7, v7

    add-float/2addr v7, v3

    div-float/2addr v7, v6

    add-float/2addr v5, v7

    .line 1126
    .local v5, "newWaveCenterY":F
    iget-boolean v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInitialLayout:Z

    if-eqz v6, :cond_0

    .line 1127
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->stopAndHideWaveAnimation()V

    .line 1128
    const/4 v6, 0x0

    invoke-direct {p0, v6, v6}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->hideTargets(ZZ)V

    .line 1129
    iput-boolean v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mInitialLayout:Z

    .line 1132
    :cond_0
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v6, v4}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionX(F)V

    .line 1133
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOuterRing:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v6, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionY(F)V

    .line 1135
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mPointCloud:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;

    iget v7, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    invoke-virtual {v6, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/PointCloud;->setScale(F)V

    .line 1137
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v6, v4}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionX(F)V

    .line 1138
    iget-object v6, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mHandleDrawable:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;

    invoke-virtual {v6, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/TargetDrawable;->setPositionY(F)V

    .line 1140
    invoke-direct {p0, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateTargetPositions(FF)V

    .line 1141
    invoke-direct {p0, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updatePointCloudPosition(FF)V

    .line 1142
    invoke-direct {p0, v4, v5}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->updateGlowPosition(FF)V

    .line 1144
    iput v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterX:F

    .line 1145
    iput v5, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveCenterY:F

    .line 1148
    return-void
.end method

.method protected onMeasure(II)V
    .locals 8
    .param p1, "widthMeasureSpec"    # I
    .param p2, "heightMeasureSpec"    # I

    .line 365
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getSuggestedMinimumWidth()I

    move-result v0

    .line 366
    .local v0, "minimumWidth":I
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getSuggestedMinimumHeight()I

    move-result v1

    .line 367
    .local v1, "minimumHeight":I
    invoke-direct {p0, p1, v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->resolveMeasured(II)I

    move-result v2

    .line 368
    .local v2, "computedWidth":I
    invoke-direct {p0, p2, v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->resolveMeasured(II)I

    move-result v3

    .line 370
    .local v3, "computedHeight":I
    invoke-direct {p0, v0, v1, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->computeScaleFactor(IIII)F

    move-result v4

    iput v4, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mRingScaleFactor:F

    .line 373
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getScaledSuggestedMinimumWidth()I

    move-result v4

    .line 374
    .local v4, "scaledWidth":I
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getScaledSuggestedMinimumHeight()I

    move-result v5

    .line 376
    .local v5, "scaledHeight":I
    sub-int v6, v2, v4

    sub-int v7, v3, v5

    invoke-direct {p0, v6, v7}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->computeInsets(II)V

    .line 377
    invoke-virtual {p0, v2, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->setMeasuredDimension(II)V

    .line 378
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 3
    .param p1, "event"    # Landroid/view/MotionEvent;

    .line 783
    invoke-virtual {p1}, Landroid/view/MotionEvent;->getActionMasked()I

    move-result v0

    .line 784
    .local v0, "action":I
    const/4 v1, 0x0

    .line 785
    .local v1, "handled":Z
    packed-switch v0, :pswitch_data_0

    :pswitch_0
    goto :goto_0

    .line 810
    :pswitch_1
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleMove(Landroid/view/MotionEvent;)V

    .line 811
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleCancel(Landroid/view/MotionEvent;)V

    .line 812
    const/4 v1, 0x1

    goto :goto_0

    .line 796
    :pswitch_2
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleMove(Landroid/view/MotionEvent;)V

    .line 797
    const/4 v1, 0x1

    .line 798
    goto :goto_0

    .line 803
    :pswitch_3
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleMove(Landroid/view/MotionEvent;)V

    .line 804
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleUp(Landroid/view/MotionEvent;)V

    .line 805
    const/4 v1, 0x1

    .line 806
    goto :goto_0

    .line 789
    :pswitch_4
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleDown(Landroid/view/MotionEvent;)V

    .line 790
    invoke-direct {p0, p1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->handleMove(Landroid/view/MotionEvent;)V

    .line 791
    const/4 v1, 0x1

    .line 792
    nop

    .line 815
    :goto_0
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->invalidate()V

    .line 816
    if-eqz v1, :cond_0

    const/4 v2, 0x1

    goto :goto_1

    :cond_0
    invoke-super {p0, p1}, Landroid/view/View;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result v2

    :goto_1
    return v2

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
        :pswitch_4
        :pswitch_3
    .end packed-switch
.end method

.method public ping()V
    .locals 6

    .line 709
    iget v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mFeedbackCount:I

    if-lez v0, :cond_1

    .line 710
    const/4 v0, 0x1

    .line 711
    .local v0, "doWaveAnimation":Z
    iget-object v1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mWaveAnimations:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;

    .line 714
    .local v1, "waveAnimations":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;
    invoke-virtual {v1}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->size()I

    move-result v2

    if-lez v2, :cond_0

    const/4 v2, 0x0

    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v3, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->isRunning()Z

    move-result v3

    if-eqz v3, :cond_0

    .line 715
    invoke-virtual {v1, v2}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v2, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v2}, Landroid/animation/ObjectAnimator;->getCurrentPlayTime()J

    move-result-wide v2

    .line 716
    .local v2, "t":J
    const-wide/16 v4, 0x2a3

    cmp-long v4, v2, v4

    if-gez v4, :cond_0

    .line 717
    const/4 v0, 0x0

    .line 721
    .end local v2    # "t":J
    :cond_0
    if-eqz v0, :cond_1

    .line 722
    invoke-direct {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->startWaveAnimation()V

    .line 725
    .end local v0    # "doWaveAnimation":Z
    .end local v1    # "waveAnimations":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$AnimationBundle;
    :cond_1
    return-void
.end method

.method public setDirectionDescriptionsResourceId(I)V
    .locals 1
    .param p1, "resourceId"    # I

    .line 676
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptionsResourceId:I

    .line 677
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    .line 678
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mDirectionDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 680
    :cond_0
    return-void
.end method

.method public setOnTriggerListener(Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;)V
    .locals 0
    .param p1, "listener"    # Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    .line 1184
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mOnTriggerListener:Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView$OnTriggerListener;

    .line 1185
    return-void
.end method

.method public setTargetDescriptionsResourceId(I)V
    .locals 1
    .param p1, "resourceId"    # I

    .line 655
    iput p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptionsResourceId:I

    .line 656
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    if-eqz v0, :cond_0

    .line 657
    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mTargetDescriptions:Ljava/util/ArrayList;

    invoke-virtual {v0}, Ljava/util/ArrayList;->clear()V

    .line 659
    :cond_0
    return-void
.end method

.method public setVibrateEnabled(Z)V
    .locals 2
    .param p1, "enabled"    # Z

    .line 697
    if-eqz p1, :cond_0

    iget-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrator:Landroid/os/Vibrator;

    if-nez v0, :cond_0

    .line 698
    invoke-virtual {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string/jumbo v1, "vibrator"

    invoke-virtual {v0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/os/Vibrator;

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrator:Landroid/os/Vibrator;

    goto :goto_0

    .line 700
    :cond_0
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/GlowPadView;->mVibrator:Landroid/os/Vibrator;

    .line 702
    :goto_0
    return-void
.end method
