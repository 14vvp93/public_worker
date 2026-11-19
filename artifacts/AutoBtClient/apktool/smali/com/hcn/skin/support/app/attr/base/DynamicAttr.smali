.class public Lcom/hcn/skin/support/app/attr/base/DynamicAttr;
.super Ljava/lang/Object;
.source "DynamicAttr.java"


# instance fields
.field public attrName:Ljava/lang/String;

.field public refResId:I


# direct methods
.method public constructor <init>(Ljava/lang/String;I)V
    .locals 0

    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 26
    iput-object p1, p0, Lcom/hcn/skin/support/app/attr/base/DynamicAttr;->attrName:Ljava/lang/String;

    .line 27
    iput p2, p0, Lcom/hcn/skin/support/app/attr/base/DynamicAttr;->refResId:I

    return-void
.end method
