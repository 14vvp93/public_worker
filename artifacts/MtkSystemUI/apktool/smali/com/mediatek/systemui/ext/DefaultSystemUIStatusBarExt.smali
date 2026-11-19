.class public Lcom/mediatek/systemui/ext/DefaultSystemUIStatusBarExt;
.super Ljava/lang/Object;
.source "DefaultSystemUIStatusBarExt.java"

# interfaces
.implements Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;

    .line 22
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 23
    return-void
.end method


# virtual methods
.method public SetHostViewInvisible(Landroid/widget/ImageView;)V
    .locals 0
    .param p1, "view"    # Landroid/widget/ImageView;

    .line 114
    return-void
.end method

.method public addCallback(Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt$StatusBarCallback;)V
    .locals 0
    .param p1, "cb"    # Lcom/mediatek/systemui/ext/ISystemUIStatusBarExt$StatusBarCallback;

    .line 206
    return-void
.end method

.method public addCustomizedView(ILandroid/content/Context;Landroid/view/ViewGroup;)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "context"    # Landroid/content/Context;
    .param p3, "root"    # Landroid/view/ViewGroup;

    .line 81
    return-void
.end method

.method public addSignalClusterCustomizedView(Landroid/content/Context;Landroid/view/ViewGroup;I)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "root"    # Landroid/view/ViewGroup;
    .param p3, "index"    # I

    .line 85
    return-void
.end method

.method public addSlot([Ljava/lang/String;)[Ljava/lang/String;
    .locals 0
    .param p1, "slots"    # [Ljava/lang/String;

    .line 210
    return-object p1
.end method

.method public checkIfSlotIdChanged(II)Z
    .locals 1
    .param p1, "subId"    # I
    .param p2, "slotId"    # I

    .line 32
    const/4 v0, 0x0

    return v0
.end method

.method public disableHostFunction()Z
    .locals 1

    .line 27
    const/4 v0, 0x0

    return v0
.end method

.method public getClockAmPmStyle(I)I
    .locals 0
    .param p1, "defaultAmPmStyle"    # I

    .line 196
    return p1
.end method

.method public getCommonSignalIconId(I)I
    .locals 0
    .param p1, "mobileSignalIconId"    # I

    .line 109
    return p1
.end method

.method public getCustomizeCsState(Landroid/telephony/ServiceState;I)I
    .locals 0
    .param p1, "serviceState"    # Landroid/telephony/ServiceState;
    .param p2, "state"    # I

    .line 45
    return p2
.end method

.method public getCustomizeSignalStrengthIcon(IILandroid/telephony/SignalStrength;ILandroid/telephony/ServiceState;)I
    .locals 0
    .param p1, "subId"    # I
    .param p2, "iconId"    # I
    .param p3, "signalStrength"    # Landroid/telephony/SignalStrength;
    .param p4, "networkType"    # I
    .param p5, "serviceState"    # Landroid/telephony/ServiceState;

    .line 69
    return p2
.end method

.method public getCustomizeSignalStrengthLevel(ILandroid/telephony/SignalStrength;Landroid/telephony/ServiceState;)I
    .locals 0
    .param p1, "signalLevel"    # I
    .param p2, "signalStrength"    # Landroid/telephony/SignalStrength;
    .param p3, "serviceState"    # Landroid/telephony/ServiceState;

    .line 75
    return p1
.end method

.method public getDataTypeIcon(IIIILandroid/telephony/ServiceState;)I
    .locals 0
    .param p1, "subId"    # I
    .param p2, "iconId"    # I
    .param p3, "dataType"    # I
    .param p4, "dataState"    # I
    .param p5, "serviceState"    # Landroid/telephony/ServiceState;

    .line 62
    return p2
.end method

.method public getNetworkTypeIcon(IIILandroid/telephony/ServiceState;)I
    .locals 0
    .param p1, "subId"    # I
    .param p2, "iconId"    # I
    .param p3, "networkType"    # I
    .param p4, "serviceState"    # Landroid/telephony/ServiceState;

    .line 56
    return p2
.end method

.method public getServiceStateForCustomizedView(I)V
    .locals 0
    .param p1, "subId"    # I

    .line 41
    return-void
.end method

.method public handleCallStateChanged(IILjava/lang/String;Landroid/telephony/ServiceState;)Z
    .locals 1
    .param p1, "subId"    # I
    .param p2, "state"    # I
    .param p3, "incomingNumber"    # Ljava/lang/String;
    .param p4, "serviceState"    # Landroid/telephony/ServiceState;

    .line 186
    const/4 v0, 0x0

    return v0
.end method

.method public isDataDisabled(IZ)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "dataDisabled"    # Z

    .line 37
    return-void
.end method

.method public isInCsCall()Z
    .locals 1

    .line 50
    const/4 v0, 0x0

    return v0
.end method

.method public needShowRoamingIcons(Z)Z
    .locals 1
    .param p1, "isInRoaming"    # Z

    .line 191
    const/4 v0, 0x1

    return v0
.end method

.method public needShowWfcIcon()Z
    .locals 1

    .line 201
    const/4 v0, 0x1

    return v0
.end method

.method public registerOpStateListener()V
    .locals 0

    .line 157
    return-void
.end method

.method public setCustomizedAirplaneView(Landroid/view/View;Z)V
    .locals 0
    .param p1, "noSimView"    # Landroid/view/View;
    .param p2, "airplaneMode"    # Z

    .line 143
    return-void
.end method

.method public setCustomizedDataTypeView(IIZZ)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "dataTypeId"    # I
    .param p3, "dataIn"    # Z
    .param p4, "dataOut"    # Z

    .line 95
    return-void
.end method

.method public setCustomizedMobileTypeView(ILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "mobileTypeView"    # Landroid/widget/ImageView;

    .line 100
    return-void
.end method

.method public setCustomizedNetworkTypeView(IILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "networkTypeId"    # I
    .param p3, "networkTypeView"    # Landroid/widget/ImageView;

    .line 90
    return-void
.end method

.method public setCustomizedNoSimView(Landroid/widget/ImageView;)V
    .locals 0
    .param p1, "noSimView"    # Landroid/widget/ImageView;

    .line 135
    return-void
.end method

.method public setCustomizedNoSimView(Z)V
    .locals 0
    .param p1, "noSimsVisible"    # Z

    .line 131
    return-void
.end method

.method public setCustomizedNoSimsVisible(Z)V
    .locals 0
    .param p1, "noSimsVisible"    # Z

    .line 147
    return-void
.end method

.method public setCustomizedPlmnTextTint(I)V
    .locals 0
    .param p1, "tint"    # I

    .line 181
    return-void
.end method

.method public setCustomizedSignalStrengthView(IILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "signalStrengthId"    # I
    .param p3, "signalStrengthView"    # Landroid/widget/ImageView;

    .line 105
    return-void
.end method

.method public setCustomizedView(I)V
    .locals 0
    .param p1, "subId"    # I

    .line 127
    return-void
.end method

.method public setCustomizedVolteView(ILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "iconId"    # I
    .param p2, "volteView"    # Landroid/widget/ImageView;

    .line 139
    return-void
.end method

.method public setDisVolteView(IILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "iconId"    # I
    .param p3, "volteView"    # Landroid/widget/ImageView;

    .line 123
    return-void
.end method

.method public setIconTint(IF)V
    .locals 0
    .param p1, "tint"    # I
    .param p2, "darkIntensity"    # F

    .line 161
    return-void
.end method

.method public setIconTint(Landroid/content/res/ColorStateList;)V
    .locals 0
    .param p1, "tint"    # Landroid/content/res/ColorStateList;

    .line 165
    return-void
.end method

.method public setImsRegInfo(IIZZ)V
    .locals 0
    .param p1, "subId"    # I
    .param p2, "imsRegState"    # I
    .param p3, "isOverwfc"    # Z
    .param p4, "isImsOverVoice"    # Z

    .line 119
    return-void
.end method

.method public setImsSlotId(I)V
    .locals 0
    .param p1, "slotId"    # I

    .line 177
    return-void
.end method

.method public setNoSimIconTint(ILandroid/widget/ImageView;)V
    .locals 0
    .param p1, "tint"    # I
    .param p2, "nosim"    # Landroid/widget/ImageView;

    .line 169
    return-void
.end method

.method public setSimInserted(IZ)V
    .locals 0
    .param p1, "slotId"    # I
    .param p2, "insert"    # Z

    .line 173
    return-void
.end method

.method public updateSignalStrengthWifiOnlyMode(Landroid/telephony/ServiceState;Z)Z
    .locals 0
    .param p1, "serviceState"    # Landroid/telephony/ServiceState;
    .param p2, "connected"    # Z

    .line 152
    return p2
.end method
