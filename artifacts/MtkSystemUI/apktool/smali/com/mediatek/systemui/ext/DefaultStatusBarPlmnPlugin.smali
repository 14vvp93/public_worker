.class public Lcom/mediatek/systemui/ext/DefaultStatusBarPlmnPlugin;
.super Landroid/content/ContextWrapper;
.source "DefaultStatusBarPlmnPlugin.java"

# interfaces
.implements Lcom/mediatek/systemui/ext/IStatusBarPlmnPlugin;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;

    .line 22
    invoke-direct {p0, p1}, Landroid/content/ContextWrapper;-><init>(Landroid/content/Context;)V

    .line 23
    return-void
.end method


# virtual methods
.method public addPlmn(Landroid/widget/LinearLayout;Landroid/content/Context;)V
    .locals 0
    .param p1, "statusBarContents"    # Landroid/widget/LinearLayout;
    .param p2, "contx"    # Landroid/content/Context;

    .line 46
    return-void
.end method

.method public customizeCarrierLabel(Landroid/view/ViewGroup;Landroid/view/View;)Landroid/view/View;
    .locals 1
    .param p1, "parentView"    # Landroid/view/ViewGroup;
    .param p2, "orgCarrierLabel"    # Landroid/view/View;

    .line 32
    const/4 v0, 0x0

    return-object v0
.end method

.method public setPlmnVisibility(I)V
    .locals 0
    .param p1, "visibility"    # I

    .line 50
    return-void
.end method

.method public supportCustomizeCarrierLabel()Z
    .locals 1

    .line 27
    const/4 v0, 0x0

    return v0
.end method

.method public updateCarrierLabel(IZZ[Ljava/lang/String;)V
    .locals 0
    .param p1, "slotId"    # I
    .param p2, "isSimInserted"    # Z
    .param p3, "isHasSimService"    # Z
    .param p4, "networkNames"    # [Ljava/lang/String;

    .line 42
    return-void
.end method

.method public updateCarrierLabelVisibility(ZZ)V
    .locals 0
    .param p1, "force"    # Z
    .param p2, "makeVisible"    # Z

    .line 37
    return-void
.end method
