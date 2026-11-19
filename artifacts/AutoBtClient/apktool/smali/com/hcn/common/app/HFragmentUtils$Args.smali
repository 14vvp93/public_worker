.class Lcom/hcn/common/app/HFragmentUtils$Args;
.super Ljava/lang/Object;
.source "HFragmentUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HFragmentUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "Args"
.end annotation


# instance fields
.field final id:I

.field final isAddStack:Z

.field final isHide:Z

.field final tag:Ljava/lang/String;


# direct methods
.method constructor <init>(ILjava/lang/String;ZZ)V
    .locals 0

    .line 1864
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1865
    iput p1, p0, Lcom/hcn/common/app/HFragmentUtils$Args;->id:I

    .line 1866
    iput-object p2, p0, Lcom/hcn/common/app/HFragmentUtils$Args;->tag:Ljava/lang/String;

    .line 1867
    iput-boolean p3, p0, Lcom/hcn/common/app/HFragmentUtils$Args;->isHide:Z

    .line 1868
    iput-boolean p4, p0, Lcom/hcn/common/app/HFragmentUtils$Args;->isAddStack:Z

    return-void
.end method

.method constructor <init>(IZZ)V
    .locals 1

    const/4 v0, 0x0

    .line 1860
    invoke-direct {p0, p1, v0, p2, p3}, Lcom/hcn/common/app/HFragmentUtils$Args;-><init>(ILjava/lang/String;ZZ)V

    return-void
.end method
