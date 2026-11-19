.class public Lcom/hcn/auto/systemui/RecyclerViewDialog;
.super Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
.source "RecyclerViewDialog.java"


# instance fields
.field private mWindowHeight:I

.field private mWindowWidth:I


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;

    .line 14
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;-><init>(Landroid/content/Context;)V

    .line 15
    return-void
.end method


# virtual methods
.method public setDialogHeight(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "height"    # I

    .line 33
    iput p1, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowHeight:I

    .line 34
    return-object p0
.end method

.method public setDialogWidth(I)Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 0
    .param p1, "width"    # I

    .line 27
    iput p1, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowWidth:I

    .line 28
    return-object p0
.end method

.method public show()Lcom/hcn/widgets/dynamicrecyclerview/RecyclerViewDialog;
    .locals 3

    .line 19
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/RecyclerViewDialog;->getDialog()Landroid/app/AlertDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog;->getWindow()Landroid/view/Window;

    move-result-object v0

    iget v1, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowWidth:I

    iget v2, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowHeight:I

    invoke-virtual {v0, v1, v2}, Landroid/view/Window;->setLayout(II)V

    .line 20
    const-string v0, "RecyclerViewDialog"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "show: mWindowWidth = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowWidth:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " mWindowHeight = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget v2, p0, Lcom/hcn/auto/systemui/RecyclerViewDialog;->mWindowHeight:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 21
    invoke-virtual {p0}, Lcom/hcn/auto/systemui/RecyclerViewDialog;->getDialog()Landroid/app/AlertDialog;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/AlertDialog;->show()V

    .line 22
    return-object p0
.end method
