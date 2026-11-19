.class Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;
.super Ljava/lang/Object;
.source "NavigationBarFragment.java"

# interfaces
.implements Lcom/android/systemui/plugins/VolumeDialogController$Callbacks;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field private mMute_Stream_Music:Z

.field private mVol_Stream_Call:I

.field private mVol_Stream_Music:I

.field private mVol_Stream_Ring:I

.field final synthetic this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;


# direct methods
.method constructor <init>(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)V
    .locals 1
    .param p1, "this$0"    # Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    .line 2028
    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2029
    const/4 v0, -0x1

    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Ring:I

    .line 2030
    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Music:I

    .line 2031
    iput v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Call:I

    .line 2032
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mMute_Stream_Music:Z

    return-void
.end method


# virtual methods
.method public onAccessibilityModeChanged(Ljava/lang/Boolean;)V
    .locals 0
    .param p1, "showA11yStream"    # Ljava/lang/Boolean;

    .line 2056
    return-void
.end method

.method public onConfigurationChanged()V
    .locals 0

    .line 2103
    return-void
.end method

.method public onDismissRequested(I)V
    .locals 0
    .param p1, "reason"    # I

    .line 2041
    return-void
.end method

.method public onLayoutDirectionChanged(I)V
    .locals 0
    .param p1, "layoutDirection"    # I

    .line 2098
    return-void
.end method

.method public onScreenOff()V
    .locals 0

    .line 2046
    return-void
.end method

.method public onShowRequested(I)V
    .locals 0
    .param p1, "reason"    # I

    .line 2036
    return-void
.end method

.method public onShowSafetyWarning(I)V
    .locals 0
    .param p1, "flags"    # I

    .line 2051
    return-void
.end method

.method public onShowSilentHint()V
    .locals 0

    .line 2113
    return-void
.end method

.method public onShowVibrateHint()V
    .locals 0

    .line 2108
    return-void
.end method

.method public onStateChanged(Lcom/android/systemui/plugins/VolumeDialogController$State;)V
    .locals 7
    .param p1, "state"    # Lcom/android/systemui/plugins/VolumeDialogController$State;

    .line 2060
    const/4 v0, 0x0

    .line 2061
    .local v0, "change":Z
    const/4 v1, 0x0

    .local v1, "i":I
    :goto_0
    iget-object v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    invoke-virtual {v2}, Landroid/util/SparseArray;->size()I

    move-result v2

    const/4 v3, 0x2

    if-ge v1, v2, :cond_4

    .line 2062
    iget-object v2, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    invoke-virtual {v2, v1}, Landroid/util/SparseArray;->keyAt(I)I

    move-result v2

    .line 2063
    .local v2, "stream":I
    iget-object v4, p1, Lcom/android/systemui/plugins/VolumeDialogController$State;->states:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;

    .line 2064
    .local v4, "ss":Lcom/android/systemui/plugins/VolumeDialogController$StreamState;
    const/4 v5, 0x3

    if-ne v2, v5, :cond_1

    iget v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Music:I

    iget v6, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    if-ne v5, v6, :cond_0

    iget-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mMute_Stream_Music:Z

    iget-boolean v6, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    if-eq v5, v6, :cond_1

    .line 2066
    :cond_0
    iget v5, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    iput v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Music:I

    .line 2067
    iget-boolean v5, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->muted:Z

    iput-boolean v5, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mMute_Stream_Music:Z

    .line 2068
    const/4 v0, 0x1

    .line 2070
    :cond_1
    if-ne v2, v3, :cond_2

    iget v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Ring:I

    iget v5, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    if-eq v3, v5, :cond_2

    .line 2071
    iget v3, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    iput v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Ring:I

    .line 2072
    const/4 v0, 0x1

    .line 2074
    :cond_2
    if-nez v2, :cond_3

    iget v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Call:I

    iget v5, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    if-eq v3, v5, :cond_3

    .line 2075
    iget v3, v4, Lcom/android/systemui/plugins/VolumeDialogController$StreamState;->level:I

    iput v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Call:I

    .line 2076
    const/4 v0, 0x1

    .line 2061
    .end local v2    # "stream":I
    .end local v4    # "ss":Lcom/android/systemui/plugins/VolumeDialogController$StreamState;
    :cond_3
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 2080
    .end local v1    # "i":I
    :cond_4
    if-eqz v0, :cond_7

    .line 2082
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v1

    if-nez v1, :cond_5

    .line 2083
    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Call:I

    .local v1, "volume_index":I
    :goto_1
    goto :goto_2

    .line 2084
    .end local v1    # "volume_index":I
    :cond_5
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v1

    if-ne v1, v3, :cond_6

    .line 2085
    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Ring:I

    goto :goto_1

    .line 2087
    :cond_6
    iget v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->mVol_Stream_Music:I

    .line 2089
    .restart local v1    # "volume_index":I
    :goto_2
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v2, v2, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->myHandler:Landroid/os/Handler;

    const/4 v3, 0x1

    invoke-virtual {v2, v3}, Landroid/os/Handler;->removeMessages(I)V

    .line 2090
    iget-object v2, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v2, v2, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->myHandler:Landroid/os/Handler;

    iget-object v4, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-static {v4}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->access$800(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)I

    move-result v4

    invoke-virtual {v2, v3, v4, v1}, Landroid/os/Handler;->obtainMessage(III)Landroid/os/Message;

    move-result-object v2

    .line 2091
    .local v2, "msg":Landroid/os/Message;
    iget-object v3, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$5;->this$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v3, v3, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->myHandler:Landroid/os/Handler;

    invoke-virtual {v3, v2}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    .line 2093
    .end local v1    # "volume_index":I
    .end local v2    # "msg":Landroid/os/Message;
    :cond_7
    return-void
.end method
