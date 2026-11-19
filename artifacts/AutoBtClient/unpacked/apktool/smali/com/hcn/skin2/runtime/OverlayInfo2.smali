.class public Lcom/hcn/skin2/runtime/OverlayInfo2;
.super Ljava/lang/Object;
.source "OverlayInfo2.java"


# static fields
.field public static final STATE_DISABLED:I = 0x2

.field public static final STATE_ENABLED:I = 0x3

.field public static final STATE_ENABLED_STATIC:I = 0x6

.field public static final STATE_MISSING_TARGET:I = 0x0

.field public static final STATE_NO_IDMAP:I = 0x1

.field public static final STATE_OVERLAY_IS_BEING_REPLACED:I = 0x5

.field public static final STATE_OVERLAY_UPGRADING:I = 0x5

.field public static final STATE_TARGET_IS_BEING_REPLACED:I = 0x4
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation
.end field

.field public static final STATE_TARGET_UPGRADING:I = 0x4

.field public static final STATE_UNKNOWN:I = -0x1


# instance fields
.field public final baseCodePath:Ljava/lang/String;

.field public final category:Ljava/lang/String;

.field public final isStatic:Z

.field public final overlayName:Ljava/lang/String;

.field public final packageName:Ljava/lang/String;

.field public final priority:I

.field public final state:I

.field public final targetOverlayableName:Ljava/lang/String;

.field public final targetPackageName:Ljava/lang/String;

.field public final userId:I


# direct methods
.method public constructor <init>(Landroid/os/Parcel;)V
    .locals 3

    .line 124
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 125
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->packageName:Ljava/lang/String;

    .line 128
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const-string v1, "unsupport"

    const/16 v2, 0x21

    if-lt v0, v2, :cond_0

    .line 129
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->overlayName:Ljava/lang/String;

    goto :goto_0

    .line 131
    :cond_0
    iput-object v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->overlayName:Ljava/lang/String;

    .line 134
    :goto_0
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->targetPackageName:Ljava/lang/String;

    .line 137
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1d

    if-lt v0, v2, :cond_1

    .line 138
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->targetOverlayableName:Ljava/lang/String;

    goto :goto_1

    .line 140
    :cond_1
    iput-object v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->targetOverlayableName:Ljava/lang/String;

    .line 144
    :goto_1
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1c

    if-lt v0, v2, :cond_2

    .line 145
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->category:Ljava/lang/String;

    goto :goto_2

    .line 147
    :cond_2
    iput-object v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->category:Ljava/lang/String;

    .line 150
    :goto_2
    invoke-virtual {p1}, Landroid/os/Parcel;->readString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->baseCodePath:Ljava/lang/String;

    .line 151
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    .line 152
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->userId:I

    .line 155
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/4 v1, 0x0

    if-lt v0, v2, :cond_4

    .line 156
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result v0

    iput v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->priority:I

    .line 157
    invoke-virtual {p1}, Landroid/os/Parcel;->readInt()I

    move-result p1

    if-eqz p1, :cond_3

    const/4 v1, 0x1

    :cond_3
    iput-boolean v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->isStatic:Z

    goto :goto_3

    .line 159
    :cond_4
    iput v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->priority:I

    .line 160
    iput-boolean v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->isStatic:Z

    .line 163
    :goto_3
    invoke-direct {p0}, Lcom/hcn/skin2/runtime/OverlayInfo2;->ensureValidState()V

    return-void
.end method

.method private ensureValidState()V
    .locals 3

    .line 216
    iget-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->packageName:Ljava/lang/String;

    if-eqz v0, :cond_2

    .line 220
    iget-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->targetPackageName:Ljava/lang/String;

    if-eqz v0, :cond_1

    .line 224
    iget-object v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->baseCodePath:Ljava/lang/String;

    if-eqz v0, :cond_0

    .line 228
    iget v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    packed-switch v0, :pswitch_data_0

    .line 239
    new-instance v0, Ljava/lang/IllegalArgumentException;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "State "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget v2, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, " is not a valid state"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    :pswitch_0
    return-void

    .line 225
    :cond_0
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "baseCodePath must not be null"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 221
    :cond_1
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "targetPackageName must not be null"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    .line 217
    :cond_2
    new-instance v0, Ljava/lang/IllegalArgumentException;

    const-string v1, "packageName must not be null"

    invoke-direct {v0, v1}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v0

    nop

    :pswitch_data_0
    .packed-switch -0x1
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
        :pswitch_0
    .end packed-switch
.end method

.method public static stateToString(I)Ljava/lang/String;
    .locals 0

    packed-switch p0, :pswitch_data_0

    const-string p0, "<unknown state>"

    return-object p0

    :pswitch_0
    const-string p0, "STATE_ENABLED_STATIC"

    return-object p0

    :pswitch_1
    const-string p0, "STATE_OVERLAY_UPGRADING | STATE_OVERLAY_IS_BEING_REPLACED"

    return-object p0

    :pswitch_2
    const-string p0, "STATE_TARGET_UPGRADING | STATE_TARGET_IS_BEING_REPLACED"

    return-object p0

    :pswitch_3
    const-string p0, "STATE_ENABLED"

    return-object p0

    :pswitch_4
    const-string p0, "STATE_DISABLED"

    return-object p0

    :pswitch_5
    const-string p0, "STATE_NO_IDMAP"

    return-object p0

    :pswitch_6
    const-string p0, "STATE_MISSING_TARGET"

    return-object p0

    :pswitch_7
    const-string p0, "STATE_UNKNOWN"

    return-object p0

    :pswitch_data_0
    .packed-switch -0x1
        :pswitch_7
        :pswitch_6
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method


# virtual methods
.method public isEnabled()Z
    .locals 2

    .line 176
    iget v0, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    const/4 v1, 0x3

    if-eq v0, v1, :cond_0

    const/4 v1, 0x6

    if-eq v0, v1, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    const/4 v0, 0x1

    return v0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 245
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "OverlayInfo { overlay="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->packageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", target="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->targetPackageName:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", state="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " ("

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->state:I

    .line 246
    invoke-static {v1}, Lcom/hcn/skin2/runtime/OverlayInfo2;->stateToString(I)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "), userId="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin2/runtime/OverlayInfo2;->userId:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " }"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
