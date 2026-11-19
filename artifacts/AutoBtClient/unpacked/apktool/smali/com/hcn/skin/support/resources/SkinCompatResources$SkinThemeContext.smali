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
    .locals 0

    .line 1145
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;

    .line 1146
    invoke-direct {p0}, Landroid/view/ContextThemeWrapper;-><init>()V

    .line 1147
    instance-of p1, p2, Landroid/view/ContextThemeWrapper;

    if-eqz p1, :cond_0

    .line 1148
    check-cast p2, Landroid/view/ContextThemeWrapper;

    invoke-virtual {p2}, Landroid/view/ContextThemeWrapper;->getBaseContext()Landroid/content/Context;

    move-result-object p1

    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->attachBaseContext(Landroid/content/Context;)V

    goto :goto_0

    .line 1150
    :cond_0
    invoke-virtual {p0, p2}, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->attachBaseContext(Landroid/content/Context;)V

    :goto_0
    return-void
.end method


# virtual methods
.method public getPackageName()Ljava/lang/String;
    .locals 1

    .line 1161
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;

    invoke-static {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->access$100(Lcom/hcn/skin/support/resources/SkinCompatResources;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public getResources()Landroid/content/res/Resources;
    .locals 1

    .line 1156
    iget-object v0, p0, Lcom/hcn/skin/support/resources/SkinCompatResources$SkinThemeContext;->this$0:Lcom/hcn/skin/support/resources/SkinCompatResources;

    invoke-static {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->access$000(Lcom/hcn/skin/support/resources/SkinCompatResources;)Landroid/content/res/Resources;

    move-result-object v0

    return-object v0
.end method
