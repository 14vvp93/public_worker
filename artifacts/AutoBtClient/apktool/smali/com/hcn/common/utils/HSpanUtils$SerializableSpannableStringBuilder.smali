.class Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;
.super Landroid/text/SpannableStringBuilder;
.source "HSpanUtils.java"

# interfaces
.implements Ljava/io/Serializable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HSpanUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "SerializableSpannableStringBuilder"
.end annotation


# static fields
.field private static final serialVersionUID:J = 0x442249c430a62a3bL


# direct methods
.method private constructor <init>()V
    .locals 0

    .line 1428
    invoke-direct {p0}, Landroid/text/SpannableStringBuilder;-><init>()V

    return-void
.end method

.method synthetic constructor <init>(Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder-IA;)V
    .locals 0

    invoke-direct {p0}, Lcom/hcn/common/utils/HSpanUtils$SerializableSpannableStringBuilder;-><init>()V

    return-void
.end method
