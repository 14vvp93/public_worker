.class public Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;
.super Landroid/view/ContextThemeWrapper;
.source "SkinCompatResources.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin/support/resources/SkinCompatResources;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "SkinThemeContext"
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;


# direct methods
.method public constructor <init>(Lcom/hcn/skin/support/resources/SkinCompatResources;Landroid/content/Context;)V
    .locals 1
    .param p1, "this$0"    # Lcom/hcn/skin/support/resources/SkinCompatResources;
    .param p2, "base"    # Landroid/content/Context;

    .line 958
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;

    .line 959
    invoke-direct {p0}, Landroid/view/ContextThemeWrapper;-><init>()V

    .line 960
    instance-of v0, p2, Landroid/view/ContextThemeWrapper;

    if-eqz v0, :cond_0

    .line 961
    move-object v0, p2

    check-cast v0, Landroid/view/ContextThemeWrapper;

    invoke-virtual {v0}, Landroid/view/ContextThemeWrapper;->getBaseContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {p0, v0}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->attachBaseContext(Landroid/content/Context;)V

    goto :goto_0

    .line 963
    :cond_0
    invoke-virtual {p0, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->attachBaseContext(Landroid/content/Context;)V

    .line 965
    :goto_0
    return-void
.end method


# virtual methods
.method public getResources()Landroid/content/res/Resources;
    .locals 1

    .line 969
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;

    invoke-static {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->access$000(Lcom/hcn/skin/support/resources/SkinCompatResources;)Landroid/content/res/Resources;

    move-result-object v0

    return-object v0
.end method
