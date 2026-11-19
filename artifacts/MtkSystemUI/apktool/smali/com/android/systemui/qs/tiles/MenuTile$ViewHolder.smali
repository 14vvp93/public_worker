.class Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;
.super Ljava/lang/Object;
.source "MenuTile.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/qs/tiles/MenuTile;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "ViewHolder"
.end annotation


# instance fields
.field public mMenuName:Landroid/widget/TextView;

.field final synthetic this$0:Lcom/android/systemui/qs/tiles/MenuTile;


# direct methods
.method public constructor <init>(Lcom/android/systemui/qs/tiles/MenuTile;Landroid/widget/TextView;)V
    .locals 0
    .param p1, "this$0"    # Lcom/android/systemui/qs/tiles/MenuTile;
    .param p2, "menuName"    # Landroid/widget/TextView;

    .line 165
    iput-object p1, p0, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;->this$0:Lcom/android/systemui/qs/tiles/MenuTile;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 166
    iput-object p2, p0, Lcom/android/systemui/qs/tiles/MenuTile$ViewHolder;->mMenuName:Landroid/widget/TextView;

    .line 167
    return-void
.end method
