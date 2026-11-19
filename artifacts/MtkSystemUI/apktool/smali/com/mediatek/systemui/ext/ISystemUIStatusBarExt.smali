.class public interface abstract Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt;
.super Ljava/lang/Object;
.source "ISystemUIStatusBarExt.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt$StatusBarCallback;
    }
.end annotation


# virtual methods
.method public abstract SetHostViewInvisible(Landroid/widget/ImageView;)V
.end method

.method public abstract addCallback(Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt$StatusBarCallback;)V
.end method

.method public abstract addCustomizedView(ILandroid/content/Context;Landroid/view/ViewGroup;)V
.end method

.method public abstract addSignalClusterCustomizedView(Landroid/content/Context;Landroid/view/ViewGroup;I)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract addSlot([Ljava/lang/String;)[Ljava/lang/String;
.end method

.method public abstract checkIfSlotIdChanged(II)Z
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract disableHostFunction()Z
.end method

.method public abstract getClockAmPmStyle(I)I
.end method

.method public abstract getCommonSignalIconId(I)I
.end method

.method public abstract getCustomizeCsState(Landroid/telephony/ServiceState;I)I
.end method

.method public abstract getCustomizeSignalStrengthIcon(IILandroid/telephony/SignalStrength;ILandroid/telephony/ServiceState;)I
.end method

.method public abstract getCustomizeSignalStrengthLevel(ILandroid/telephony/SignalStrength;Landroid/telephony/ServiceState;)I
.end method

.method public abstract getDataTypeIcon(IIIILandroid/telephony/ServiceState;)I
.end method

.method public abstract getNetworkTypeIcon(IIILandroid/telephony/ServiceState;)I
.end method

.method public abstract getServiceStateForCustomizedView(I)V
.end method

.method public abstract handleCallStateChanged(IILjava/lang/String;Landroid/telephony/ServiceState;)Z
.end method

.method public abstract isDataDisabled(IZ)V
.end method

.method public abstract isInCsCall()Z
.end method

.method public abstract needShowRoamingIcons(Z)Z
.end method

.method public abstract needShowWfcIcon()Z
.end method

.method public abstract registerOpStateListener()V
.end method

.method public abstract setCustomizedAirplaneView(Landroid/view/View;Z)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract setCustomizedDataTypeView(IIZZ)V
.end method

.method public abstract setCustomizedMobileTypeView(ILandroid/widget/ImageView;)V
.end method

.method public abstract setCustomizedNetworkTypeView(IILandroid/widget/ImageView;)V
.end method

.method public abstract setCustomizedNoSimView(Landroid/widget/ImageView;)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract setCustomizedNoSimView(Z)V
.end method

.method public abstract setCustomizedNoSimsVisible(Z)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract setCustomizedPlmnTextTint(I)V
.end method

.method public abstract setCustomizedSignalStrengthView(IILandroid/widget/ImageView;)V
.end method

.method public abstract setCustomizedView(I)V
.end method

.method public abstract setCustomizedVolteView(ILandroid/widget/ImageView;)V
.end method

.method public abstract setDisVolteView(IILandroid/widget/ImageView;)V
.end method

.method public abstract setIconTint(IF)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract setIconTint(Landroid/content/res/ColorStateList;)V
.end method

.method public abstract setImsRegInfo(IIZZ)V
.end method

.method public abstract setImsSlotId(I)V
.end method

.method public abstract setNoSimIconTint(ILandroid/widget/ImageView;)V
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end method

.method public abstract setSimInserted(IZ)V
.end method

.method public abstract updateSignalStrengthWifiOnlyMode(Landroid/telephony/ServiceState;Z)Z
.end method
