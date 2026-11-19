.class Lcom/android/systemui/volume/JellyProgressView$2;
.super Ljava/lang/Object;
.source "JellyProgressView.java"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/android/systemui/volume/JellyProgressView;->setupProgressAnimation()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/android/systemui/volume/JellyProgressView;


# direct methods
.method constructor <init>(Lcom/android/systemui/volume/JellyProgressView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/volume/JellyProgressView;

    .line 166
    iput-object p1, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 5
    .param p1, "animation"    # Landroid/animation/ValueAnimator;

    .line 169
    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->getAnimatedFraction()F

    move-result v0

    .line 171
    .local v0, "fraction":F
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    iget-object v2, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-static {v2}, Lcom/android/systemui/volume/JellyProgressView;->access$200(Lcom/android/systemui/volume/JellyProgressView;)F

    move-result v2

    iget-object v3, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-static {v3}, Lcom/android/systemui/volume/JellyProgressView;->access$300(Lcom/android/systemui/volume/JellyProgressView;)F

    move-result v3

    iget-object v4, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-static {v4}, Lcom/android/systemui/volume/JellyProgressView;->access$200(Lcom/android/systemui/volume/JellyProgressView;)F

    move-result v4

    sub-float/2addr v3, v4

    mul-float/2addr v3, v0

    add-float/2addr v2, v3

    invoke-static {v1, v2}, Lcom/android/systemui/volume/JellyProgressView;->access$202(Lcom/android/systemui/volume/JellyProgressView;F)F

    .line 172
    iget-object v1, p0, Lcom/android/systemui/volume/JellyProgressView$2;->this$0:Lcom/android/systemui/volume/JellyProgressView;

    invoke-virtual {v1}, Lcom/android/systemui/volume/JellyProgressView;->invalidate()V

    .line 173
    return-void
.end method
