.class Lcom/autochips/bluetooth/fragment/PBFragment$3;
.super Ljava/lang/Object;
.source "PBFragment.java"

# interfaces
.implements Landroid/text/TextWatcher;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/PBFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/PBFragment;


# direct methods
.method constructor <init>(Lcom/autochips/bluetooth/fragment/PBFragment;)V
    .locals 0

    .line 662
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public afterTextChanged(Landroid/text/Editable;)V
    .locals 1

    .line 666
    iget-object v0, p0, Lcom/autochips/bluetooth/fragment/PBFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-virtual {p1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1202(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)Ljava/lang/String;

    .line 667
    iget-object p1, p0, Lcom/autochips/bluetooth/fragment/PBFragment$3;->this$0:Lcom/autochips/bluetooth/fragment/PBFragment;

    invoke-static {p1}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$1200(Lcom/autochips/bluetooth/fragment/PBFragment;)Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lcom/autochips/bluetooth/fragment/PBFragment;->access$200(Lcom/autochips/bluetooth/fragment/PBFragment;Ljava/lang/String;)V

    return-void
.end method

.method public beforeTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    return-void
.end method

.method public onTextChanged(Ljava/lang/CharSequence;III)V
    .locals 0

    return-void
.end method
