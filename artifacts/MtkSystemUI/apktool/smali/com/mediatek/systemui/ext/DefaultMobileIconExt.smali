.class public Lcom/mediatek/systemui/ext/DefaultMobileIconExt;
.super Ljava/lang/Object;
.source "DefaultMobileIconExt.java"

# interfaces
.implements Lcom/mediatek/systemui/ext/IMobileIconExt;


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public customizeMobileNetCondition(I)I
    .locals 0
    .param p1, "netCondition"    # I

    .line 14
    return p1
.end method

.method public customizeWifiNetCondition(I)I
    .locals 0
    .param p1, "netCondition"    # I

    .line 10
    return p1
.end method
