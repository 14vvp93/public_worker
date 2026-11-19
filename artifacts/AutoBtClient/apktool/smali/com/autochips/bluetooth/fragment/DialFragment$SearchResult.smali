.class final Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;
.super Ljava/lang/Object;
.source "DialFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/autochips/bluetooth/fragment/DialFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x12
    name = "SearchResult"
.end annotation


# instance fields
.field public pbList:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList<",
            "Ljava/util/HashMap<",
            "Ljava/lang/String;",
            "Ljava/lang/String;",
            ">;>;"
        }
    .end annotation
.end field

.field public searchKey:Ljava/lang/String;

.field final synthetic this$0:Lcom/autochips/bluetooth/fragment/DialFragment;


# direct methods
.method private constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V
    .locals 0

    .line 457
    iput-object p1, p0, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;->this$0:Lcom/autochips/bluetooth/fragment/DialFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/autochips/bluetooth/fragment/DialFragment;Lcom/autochips/bluetooth/fragment/DialFragment$1;)V
    .locals 0

    .line 457
    invoke-direct {p0, p1}, Lcom/autochips/bluetooth/fragment/DialFragment$SearchResult;-><init>(Lcom/autochips/bluetooth/fragment/DialFragment;)V

    return-void
.end method
