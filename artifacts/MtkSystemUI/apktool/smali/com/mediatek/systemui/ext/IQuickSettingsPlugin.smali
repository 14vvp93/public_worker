.class public interface abstract Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;
.super Ljava/lang/Object;
.source "IQuickSettingsPlugin.java"


# virtual methods
.method public abstract addOpTileSpecs(Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract addOpViews(Landroid/view/ViewGroup;)V
.end method

.method public abstract createTile(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
.end method

.method public abstract customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;
.end method

.method public abstract customizeApnSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract customizeDisplayDataUsage(Z)Z
.end method

.method public abstract customizeDualSimSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract customizeQuickSettingsTileOrder(Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract customizeSimDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)V
.end method

.method public abstract disableDataForOtherSubscriptions()V
.end method

.method public abstract doOperatorSupportTile(Ljava/lang/String;)Z
.end method

.method public abstract getOpViewsHeight()I
.end method

.method public abstract getPreviousView(Landroid/view/View;)Landroid/view/View;
.end method

.method public abstract getTileLabel(Ljava/lang/String;)Ljava/lang/String;
.end method

.method public abstract measureOpViews(I)V
.end method

.method public abstract registerCallbacks()V
.end method

.method public abstract setHostAppInstance(Ljava/lang/Object;)V
.end method

.method public abstract setOpViewsLayout(I)V
.end method

.method public abstract setViewsVisibility(I)V
.end method

.method public abstract unregisterCallbacks()V
.end method
