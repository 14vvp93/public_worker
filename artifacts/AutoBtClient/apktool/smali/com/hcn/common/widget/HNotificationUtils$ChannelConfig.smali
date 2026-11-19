.class public Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
.super Ljava/lang/Object;
.source "HNotificationUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/widget/HNotificationUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "ChannelConfig"
.end annotation


# static fields
.field public static final DEFAULT_CHANNEL_CONFIG:Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;


# instance fields
.field private mNotificationChannel:Landroid/app/NotificationChannel;


# direct methods
.method static bridge synthetic -$$Nest$fgetmNotificationChannel(Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;)Landroid/app/NotificationChannel;
    .locals 0

    iget-object p0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    return-object p0
.end method

.method static constructor <clinit>()V
    .locals 4

    .line 174
    new-instance v0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;

    .line 175
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-virtual {v1}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v2

    invoke-virtual {v2}, Landroid/app/Application;->getPackageName()Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x3

    invoke-direct {v0, v1, v2, v3}, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V

    sput-object v0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->DEFAULT_CHANNEL_CONFIG:Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;

    return-void
.end method

.method public constructor <init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V
    .locals 2

    .line 180
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 181
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 182
    new-instance v0, Landroid/app/NotificationChannel;

    invoke-direct {v0, p1, p2, p3}, Landroid/app/NotificationChannel;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V

    iput-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    :cond_0
    return-void
.end method


# virtual methods
.method public getNotificationChannel()Landroid/app/NotificationChannel;
    .locals 1

    .line 187
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    return-object v0
.end method

.method public setBypassDnd(Z)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 197
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 198
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setBypassDnd(Z)V

    :cond_0
    return-object p0
.end method

.method public setDescription(Ljava/lang/String;)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 210
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 211
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setDescription(Ljava/lang/String;)V

    :cond_0
    return-object p0
.end method

.method public setGroup(Ljava/lang/String;)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 229
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 230
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setGroup(Ljava/lang/String;)V

    :cond_0
    return-object p0
.end method

.method public setImportance(I)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 245
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 246
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setImportance(I)V

    :cond_0
    return-object p0
.end method

.method public setLightColor(I)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 259
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 260
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setLightColor(I)V

    :cond_0
    return-object p0
.end method

.method public setLockscreenVisibility(I)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 272
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 273
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setLockscreenVisibility(I)V

    :cond_0
    return-object p0
.end method

.method public setName(Ljava/lang/CharSequence;)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 285
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 286
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setName(Ljava/lang/CharSequence;)V

    :cond_0
    return-object p0
.end method

.method public setShowBadge(Z)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 301
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 302
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setShowBadge(Z)V

    :cond_0
    return-object p0
.end method

.method public setSound(Landroid/net/Uri;Landroid/media/AudioAttributes;)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 316
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 317
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1, p2}, Landroid/app/NotificationChannel;->setSound(Landroid/net/Uri;Landroid/media/AudioAttributes;)V

    :cond_0
    return-object p0
.end method

.method public setVibrationPattern([J)Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;
    .locals 2

    .line 331
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    if-lt v0, v1, :cond_0

    .line 332
    iget-object v0, p0, Lcom/hcn/common/widget/HNotificationUtils$ChannelConfig;->mNotificationChannel:Landroid/app/NotificationChannel;

    invoke-virtual {v0, p1}, Landroid/app/NotificationChannel;->setVibrationPattern([J)V

    :cond_0
    return-object p0
.end method
