.class Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;
.super Ljava/lang/Object;
.source "MediatekDigitalClock.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mediatek/keyguard/Clock/MediatekDigitalClock;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x8
    name = "AmPm"
.end annotation


# instance fields
.field private final mAmPm:Landroid/widget/TextView;

.field private final mAmString:Ljava/lang/String;

.field private final mPmString:Ljava/lang/String;


# direct methods
.method constructor <init>(Landroid/view/View;)V
    .locals 3
    .param p1, "parent"    # Landroid/view/View;

    .line 80
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 81
    const v0, 0x7f0a0065

    invoke-virtual {p1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmPm:Landroid/widget/TextView;

    .line 82
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmPm:Landroid/widget/TextView;

    const/4 v1, 0x0

    const/4 v2, 0x4

    invoke-virtual {v0, v1, v2, v1, v1}, Landroid/widget/TextView;->setPadding(IIII)V

    .line 83
    new-instance v0, Ljava/text/DateFormatSymbols;

    invoke-direct {v0}, Ljava/text/DateFormatSymbols;-><init>()V

    invoke-virtual {v0}, Ljava/text/DateFormatSymbols;->getAmPmStrings()[Ljava/lang/String;

    move-result-object v0

    .line 84
    .local v0, "ampm":[Ljava/lang/String;
    aget-object v1, v0, v1

    iput-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmString:Ljava/lang/String;

    .line 85
    const/4 v1, 0x1

    aget-object v1, v0, v1

    iput-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mPmString:Ljava/lang/String;

    .line 86
    return-void
.end method


# virtual methods
.method getAmPmText()Ljava/lang/CharSequence;
    .locals 1

    .line 97
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmPm:Landroid/widget/TextView;

    invoke-virtual {v0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v0

    return-object v0
.end method

.method setIsMorning(Z)V
    .locals 2
    .param p1, "isMorning"    # Z

    .line 93
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmPm:Landroid/widget/TextView;

    if-eqz p1, :cond_0

    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmString:Ljava/lang/String;

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mPmString:Ljava/lang/String;

    :goto_0
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 94
    return-void
.end method

.method setShowAmPm(Z)V
    .locals 2
    .param p1, "show"    # Z

    .line 89
    iget-object v0, p0, Lcom/mediatek/keyguard/Clock/MediatekDigitalClock$AmPm;->mAmPm:Landroid/widget/TextView;

    if-eqz p1, :cond_0

    const/4 v1, 0x0

    goto :goto_0

    :cond_0
    const/16 v1, 0x8

    :goto_0
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 90
    return-void
.end method
