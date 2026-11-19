.class Lcom/autochips/bluetooth/fragment/PBFragment$1;
.super Ljava/lang/Object;
.source "PBFragment.java"

# interfaces
.implements Landroid/widget/AbsListView$OnScrollListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;->initAdapterView()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field first_position:I

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 261
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 p1, 0x0

    .line 262
    iput p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->first_position:I

    return-void
.end method


# virtual methods
.method public onScroll(Landroid/widget/AbsListView;III)V
    .locals 0

    .line 294
    iput p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->first_position:I

    return-void
.end method

.method public onScrollStateChanged(Landroid/widget/AbsListView;I)V
    .locals 1

    .line 266
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "onScrollStateChanged: state="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1, p2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    const-string v0, " pos="

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    .line 267
    invoke-static {v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$900(Lcom/autochips/bluetooth/fragment/PBFragment;)Landroid/widget/AbsListView;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/AbsListView;->getFirstVisiblePosition()I

    move-result v0

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    const-string v0, "PBFragment"

    .line 266
    invoke-static {v0, p1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    if-nez p2, :cond_2

    .line 269
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$400(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;

    move-result-object p1

    iget p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->first_position:I

    invoke-virtual {p1, p2}, Lcom/autochips/bluetooth/fragment/PBFragment$PBListAdapter;->getItem(I)Ljava/util/HashMap;

    move-result-object p1

    if-nez p1, :cond_0

    return-void

    :cond_0
    const-string p2, "item_phonebook_label"

    .line 273
    invoke-virtual {p1, p2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Ljava/lang/String;

    .line 274
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p2

    if-nez p2, :cond_2

    const/4 p2, 0x0

    .line 275
    invoke-virtual {p1, p2}, Ljava/lang/String;->charAt(I)C

    move-result p1

    const/16 p2, 0x41

    if-lt p1, p2, :cond_1

    const/16 v0, 0x5a

    if-gt p1, v0, :cond_1

    sub-int/2addr p1, p2

    .line 278
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/view/SideBar;

    move-result-object p2

    if-eqz p2, :cond_2

    .line 279
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/view/SideBar;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/autochips/bluetooth/view/SideBar;->setChoose(I)V

    goto :goto_0

    :cond_1
    const/16 p1, 0x1a

    .line 283
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/view/SideBar;

    move-result-object p2

    if-eqz p2, :cond_2

    .line 284
    iget-object p2, p0, Lcom/autochips/bluetooth/fragment/PBFragment$1;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p2}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1000(Lcom/autochips/bluetooth/fragment/PBFragment;)Lcom/autochips/bluetooth/view/SideBar;

    move-result-object p2

    invoke-virtual {p2, p1}, Lcom/autochips/bluetooth/view/SideBar;->setChoose(I)V

    :cond_2
    :goto_0
    return-void
.end method
