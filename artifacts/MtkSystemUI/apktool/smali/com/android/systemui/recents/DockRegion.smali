.class Lcom/android/systemui/recents/DockRegion;
.super Ljava/lang/Object;
.source "RecentsConfiguration.java"


# static fields
.field public static PHONE_LANDSCAPE:[Lcom/android/systemui/recents/views/DockState;

.field public static PHONE_PORTRAIT:[Lcom/android/systemui/recents/views/DockState;

.field public static TABLET_LANDSCAPE:[Lcom/android/systemui/recents/views/DockState;

.field public static TABLET_PORTRAIT:[Lcom/android/systemui/recents/views/DockState;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    .line 37
    const/4 v0, 0x1

    new-array v1, v0, [Lcom/android/systemui/recents/views/DockState;

    sget-object v2, Lcom/android/systemui/recents/views/DockState;->LEFT:Lcom/android/systemui/recents/views/DockState;

    const/4 v3, 0x0

    aput-object v2, v1, v3

    sput-object v1, Lcom/android/systemui/recents/DockRegion;->PHONE_LANDSCAPE:[Lcom/android/systemui/recents/views/DockState;

    .line 41
    new-array v1, v0, [Lcom/android/systemui/recents/views/DockState;

    sget-object v2, Lcom/android/systemui/recents/views/DockState;->TOP:Lcom/android/systemui/recents/views/DockState;

    aput-object v2, v1, v3

    sput-object v1, Lcom/android/systemui/recents/DockRegion;->PHONE_PORTRAIT:[Lcom/android/systemui/recents/views/DockState;

    .line 47
    new-array v0, v0, [Lcom/android/systemui/recents/views/DockState;

    sget-object v1, Lcom/android/systemui/recents/views/DockState;->LEFT:Lcom/android/systemui/recents/views/DockState;

    aput-object v1, v0, v3

    sput-object v0, Lcom/android/systemui/recents/DockRegion;->TABLET_LANDSCAPE:[Lcom/android/systemui/recents/views/DockState;

    .line 51
    sget-object v0, Lcom/android/systemui/recents/DockRegion;->PHONE_PORTRAIT:[Lcom/android/systemui/recents/views/DockState;

    sput-object v0, Lcom/android/systemui/recents/DockRegion;->TABLET_PORTRAIT:[Lcom/android/systemui/recents/views/DockState;

    return-void
.end method
