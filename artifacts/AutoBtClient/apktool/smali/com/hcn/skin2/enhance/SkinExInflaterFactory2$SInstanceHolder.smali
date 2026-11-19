.class final Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$SInstanceHolder;
.super Ljava/lang/Object;
.source "SkinExInflaterFactory2.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "SInstanceHolder"
.end annotation


# static fields
.field static final S_INSTANCE:Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 128
    new-instance v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;-><init>(Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$1;)V

    sput-object v0, Lcom/hcn/skin2/enhance/SkinExInflaterFactory2$SInstanceHolder;->S_INSTANCE:Lcom/hcn/skin2/enhance/SkinExInflaterFactory2;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 126
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method
