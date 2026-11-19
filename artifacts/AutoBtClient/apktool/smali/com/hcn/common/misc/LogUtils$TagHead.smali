.class final Lcom/hcn/common/misc/LogUtils$TagHead;
.super Ljava/lang/Object;
.source "LogUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/misc/LogUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "TagHead"
.end annotation


# instance fields
.field consoleHead:[Ljava/lang/String;

.field fileHead:Ljava/lang/String;

.field tag:Ljava/lang/String;


# direct methods
.method constructor <init>(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 964
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 965
    iput-object p1, p0, Lcom/hcn/common/misc/LogUtils$TagHead;->tag:Ljava/lang/String;

    .line 966
    iput-object p2, p0, Lcom/hcn/common/misc/LogUtils$TagHead;->consoleHead:[Ljava/lang/String;

    .line 967
    iput-object p3, p0, Lcom/hcn/common/misc/LogUtils$TagHead;->fileHead:Ljava/lang/String;

    return-void
.end method
