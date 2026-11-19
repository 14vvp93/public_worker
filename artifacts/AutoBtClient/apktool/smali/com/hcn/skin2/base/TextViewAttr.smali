.class public Lcom/hcn/skin2/base/TextViewAttr;
.super Ljava/lang/Object;
.source "TextViewAttr.java"


# instance fields
.field public final rro_string_id:I

.field public final view:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Landroid/widget/TextView;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 20
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 21
    new-instance v0, Ljava/lang/ref/WeakReference;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/hcn/skin2/base/TextViewAttr;->view:Ljava/lang/ref/WeakReference;

    const/4 v0, 0x0

    .line 22
    iput v0, p0, Lcom/hcn/skin2/base/TextViewAttr;->rro_string_id:I

    return-void
.end method

.method public constructor <init>(Landroid/widget/TextView;I)V
    .locals 1

    .line 25
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 26
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/hcn/skin2/base/TextViewAttr;->view:Ljava/lang/ref/WeakReference;

    .line 27
    iput p2, p0, Lcom/hcn/skin2/base/TextViewAttr;->rro_string_id:I

    return-void
.end method
