.class public Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;
.super Ljava/lang/Object;
.source "APPItem.java"


# instance fields
.field public className:Ljava/lang/String;

.field public packageName:Ljava/lang/String;

.field public shortName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .param p1, "shortName"    # Ljava/lang/String;
    .param p2, "packageName"    # Ljava/lang/String;
    .param p3, "className"    # Ljava/lang/String;

    .line 8
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 9
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->shortName:Ljava/lang/String;

    .line 10
    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->packageName:Ljava/lang/String;

    .line 11
    iput-object p3, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;->className:Ljava/lang/String;

    .line 12
    return-void
.end method
