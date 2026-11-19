.class public final Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;
.super Ljava/lang/Object;
.source "RecordFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x11
    name = "ViewHolder"
.end annotation


# instance fields
.field public imageView:Landroid/widget/ImageView;

.field public nameTextView:Landroid/widget/TextView;

.field public phoneTextView:Landroid/widget/TextView;

.field final synthetic this$1:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

.field public timeTextView:Landroid/widget/TextView;


# direct methods
.method public constructor <init>(Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;)V
    .locals 0

    .line 651
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter$ViewHolder;->this$1:Lcom/autochips/bluetooth/fragment/RecordFragment$HistoryListAdapter;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
