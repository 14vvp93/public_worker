.class Lcom/hcn/common/utils/HSpanUtils$1;
.super Landroid/text/style/ClickableSpan;
.source "HSpanUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/utils/HSpanUtils;->setClickSpan(IZLandroid/view/View$OnClickListener;)Lcom/hcn/common/utils/HSpanUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/common/utils/HSpanUtils;

.field final synthetic val$color:I

.field final synthetic val$listener:Landroid/view/View$OnClickListener;

.field final synthetic val$underlineText:Z


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HSpanUtils;IZLandroid/view/View$OnClickListener;)V
    .locals 0

    .line 532
    iput-object p1, p0, Lcom/hcn/common/utils/HSpanUtils$1;->this$0:Lcom/hcn/common/utils/HSpanUtils;

    iput p2, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$color:I

    iput-boolean p3, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$underlineText:Z

    iput-object p4, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$listener:Landroid/view/View$OnClickListener;

    invoke-direct {p0}, Landroid/text/style/ClickableSpan;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    .line 542
    iget-object v0, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$listener:Landroid/view/View$OnClickListener;

    if-eqz v0, :cond_0

    .line 543
    invoke-interface {v0, p1}, Landroid/view/View$OnClickListener;->onClick(Landroid/view/View;)V

    :cond_0
    return-void
.end method

.method public updateDrawState(Landroid/text/TextPaint;)V
    .locals 1

    .line 536
    iget v0, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$color:I

    invoke-virtual {p1, v0}, Landroid/text/TextPaint;->setColor(I)V

    .line 537
    iget-boolean v0, p0, Lcom/hcn/common/utils/HSpanUtils$1;->val$underlineText:Z

    invoke-virtual {p1, v0}, Landroid/text/TextPaint;->setUnderlineText(Z)V

    return-void
.end method
