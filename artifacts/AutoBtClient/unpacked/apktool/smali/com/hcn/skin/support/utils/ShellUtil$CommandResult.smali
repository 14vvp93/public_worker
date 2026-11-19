.class public Lcom/hcn/skin/support/utils/ShellUtil$CommandResult;
.super Ljava/lang/Object;
.source "ShellUtil.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin/support/utils/ShellUtil;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "CommandResult"
.end annotation


# instance fields
.field public errorMsg:Ljava/lang/String;

.field public responseMsg:Ljava/lang/String;

.field public result:I


# direct methods
.method public constructor <init>(I)V
    .locals 0

    .line 115
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 116
    iput p1, p0, Lcom/hcn/skin/support/utils/ShellUtil$CommandResult;->result:I

    return-void
.end method

.method public constructor <init>(ILjava/lang/String;Ljava/lang/String;)V
    .locals 0

    .line 119
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 120
    iput p1, p0, Lcom/hcn/skin/support/utils/ShellUtil$CommandResult;->result:I

    .line 121
    iput-object p2, p0, Lcom/hcn/skin/support/utils/ShellUtil$CommandResult;->responseMsg:Ljava/lang/String;

    .line 122
    iput-object p3, p0, Lcom/hcn/skin/support/utils/ShellUtil$CommandResult;->errorMsg:Ljava/lang/String;

    return-void
.end method
