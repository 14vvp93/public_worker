.class Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$4;
.super Ljava/lang/Object;
.source "CheckBoxRvDialog.java"

# interfaces
.implements Lcom/hcn/widgets/dynamicrecyclerview/AppRecyclerView$AppIconLoader;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;->getIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;


# direct methods
.method constructor <init>(Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;)V
    .locals 0
    .param p1, "this$0"    # Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    .line 586
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog$4;->this$0:Lcom/hcn/widgets/dynamicrecyclerview/CheckBoxRvDialog;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public loadIcon(Landroid/content/pm/LauncherActivityInfo;)Landroid/graphics/drawable/Drawable;
    .locals 1
    .param p1, "info"    # Landroid/content/pm/LauncherActivityInfo;

    .line 589
    const/4 v0, 0x0

    invoke-virtual {p1, v0}, Landroid/content/pm/LauncherActivityInfo;->getIcon(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    return-object v0
.end method
