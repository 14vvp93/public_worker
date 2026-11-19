.class public Lcom/hcn/common/widget/HTouchUtils;
.super Ljava/lang/Object;
.source "HTouchUtils.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/widget/HTouchUtils$OnTouchUtilsListener;,
        Lcom/hcn/common/widget/HTouchUtils$Direction;
    }
.end annotation


# static fields
.field public static final DOWN:I = 0x8

.field public static final LEFT:I = 0x1

.field public static final RIGHT:I = 0x4

.field public static final UNKNOWN:I = 0x0

.field public static final UP:I = 0x2


# direct methods
.method private constructor <init>()V
    .locals 2

    .line 30
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 31
    new-instance v0, Ljava/lang/UnsupportedOperationException;

    const-string v1, "u can\'t instantiate me..."

    invoke-direct {v0, v1}, Ljava/lang/UnsupportedOperationException;-><init>(Ljava/lang/String;)V

    throw v0
.end method

.method public static setOnTouchListener(Landroid/view/View;Lcom/hcn/common/widget/HTouchUtils$OnTouchUtilsListener;)V
    .locals 0

    if-eqz p0, :cond_1

    if-nez p1, :cond_0

    goto :goto_0

    .line 38
    :cond_0
    invoke-virtual {p0, p1}, Landroid/view/View;->setOnTouchListener(Landroid/view/View$OnTouchListener;)V

    :cond_1
    :goto_0
    return-void
.end method
