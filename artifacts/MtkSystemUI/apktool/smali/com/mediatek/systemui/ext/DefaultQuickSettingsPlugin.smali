.class public Lcom/mediatek/systemui/ext/DefaultQuickSettingsPlugin;
.super Landroid/content/ContextWrapper;
.source "DefaultQuickSettingsPlugin.java"

# interfaces
.implements Lcom/mediatek/systemui/ext/IQuickSettingsPlugin;


# static fields
.field private static final TAG:Ljava/lang/String; = "DefaultQuickSettingsPlugin"


# instance fields
.field protected mContext:Landroid/content/Context;


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;

    .line 24
    invoke-direct {p0, p1}, Landroid/content/ContextWrapper;-><init>(Landroid/content/Context;)V

    .line 25
    iput-object p1, p0, Lcom/mediatek/systemui/ext/DefaultQuickSettingsPlugin;->mContext:Landroid/content/Context;

    .line 26
    return-void
.end method


# virtual methods
.method public addOpTileSpecs(Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .param p1, "defaultTileList"    # Ljava/lang/String;

    .line 81
    return-object p1
.end method

.method public addOpViews(Landroid/view/ViewGroup;)V
    .locals 0
    .param p1, "vg"    # Landroid/view/ViewGroup;

    .line 115
    return-void
.end method

.method public createTile(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
    .locals 1
    .param p1, "host"    # Ljava/lang/Object;
    .param p2, "tileSpec"    # Ljava/lang/String;

    .line 105
    const/4 v0, 0x0

    return-object v0
.end method

.method public customizeAddQSTile(Ljava/lang/Object;)Ljava/lang/Object;
    .locals 1
    .param p1, "qsTile"    # Ljava/lang/Object;

    .line 41
    const/4 v0, 0x0

    return-object v0
.end method

.method public customizeApnSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .param p1, "enable"    # Z
    .param p2, "icon"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    .param p3, "orgLabelStr"    # Ljava/lang/String;

    .line 70
    return-object p3
.end method

.method public customizeDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .param p1, "dataState"    # I
    .param p2, "icon"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    .param p3, "orgLabelStr"    # Ljava/lang/String;

    .line 47
    const-string v0, "DefaultQuickSettingsPlugin"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "customizeDataConnectionTile, icon = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, ", orgLabelStr="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 48
    return-object p3
.end method

.method public customizeDisplayDataUsage(Z)Z
    .locals 3
    .param p1, "isDisplay"    # Z

    .line 30
    const-string v0, "DefaultQuickSettingsPlugin"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "customizeDisplayDataUsage, return isDisplay = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 31
    return p1
.end method

.method public customizeDualSimSettingsTile(ZLcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .param p1, "enable"    # Z
    .param p2, "icon"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;
    .param p3, "labelStr"    # Ljava/lang/String;

    .line 54
    const-string v0, "DefaultQuickSettingsPlugin"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "customizeDualSimSettingsTile, enable = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    const-string v2, " icon="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    const-string v2, " labelStr="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 56
    return-object p3
.end method

.method public customizeQuickSettingsTileOrder(Ljava/lang/String;)Ljava/lang/String;
    .locals 0
    .param p1, "defaultString"    # Ljava/lang/String;

    .line 36
    return-object p1
.end method

.method public customizeSimDataConnectionTile(ILcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;)V
    .locals 3
    .param p1, "state"    # I
    .param p2, "icon"    # Lcom/mediatek/systemui/statusbar/extcb/IconIdWrapper;

    .line 61
    const-string v0, "DefaultQuickSettingsPlugin"

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "customizeSimDataConnectionTile, state = "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string v2, " icon="

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 62
    return-void
.end method

.method public disableDataForOtherSubscriptions()V
    .locals 0

    .line 66
    return-void
.end method

.method public doOperatorSupportTile(Ljava/lang/String;)Z
    .locals 1
    .param p1, "tileSpec"    # Ljava/lang/String;

    .line 92
    const/4 v0, 0x0

    return v0
.end method

.method public getOpViewsHeight()I
    .locals 1

    .line 170
    const/4 v0, 0x0

    return v0
.end method

.method public getPreviousView(Landroid/view/View;)Landroid/view/View;
    .locals 0
    .param p1, "v"    # Landroid/view/View;

    .line 160
    return-object p1
.end method

.method public getTileLabel(Ljava/lang/String;)Ljava/lang/String;
    .locals 1
    .param p1, "tile"    # Ljava/lang/String;

    .line 184
    const-string v0, ""

    return-object v0
.end method

.method public measureOpViews(I)V
    .locals 0
    .param p1, "width"    # I

    .line 151
    return-void
.end method

.method public registerCallbacks()V
    .locals 0

    .line 124
    return-void
.end method

.method public setHostAppInstance(Ljava/lang/Object;)V
    .locals 0
    .param p1, "o"    # Ljava/lang/Object;

    .line 193
    return-void
.end method

.method public setOpViewsLayout(I)V
    .locals 0
    .param p1, "aboveViewHeight"    # I

    .line 180
    return-void
.end method

.method public setViewsVisibility(I)V
    .locals 0
    .param p1, "visibility"    # I

    .line 142
    return-void
.end method

.method public unregisterCallbacks()V
    .locals 0

    .line 133
    return-void
.end method
