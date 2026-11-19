.class public Lcom/android/systemui/statusbar/policy/WifiIcons;
.super Ljava/lang/Object;
.source "WifiIcons.java"


# static fields
.field public static final QS_WIFI_SIGNAL_STRENGTH:[[I

.field static final WIFI_LEVEL_COUNT:I

.field static final WIFI_SIGNAL_STRENGTH:[[I

.field static final WIFI_SIGNAL_STRENGTH_INOUT:[[I


# direct methods
.method static constructor <clinit>()V
    .locals 6

    .line 22
    const/4 v0, 0x2

    new-array v1, v0, [[I

    const/4 v2, 0x5

    new-array v3, v2, [I

    fill-array-data v3, :array_0

    const/4 v4, 0x0

    aput-object v3, v1, v4

    new-array v3, v2, [I

    fill-array-data v3, :array_1

    const/4 v5, 0x1

    aput-object v3, v1, v5

    sput-object v1, Lcom/android/systemui/statusbar/policy/WifiIcons;->WIFI_SIGNAL_STRENGTH:[[I

    .line 35
    new-array v1, v0, [[I

    new-array v3, v2, [I

    fill-array-data v3, :array_2

    aput-object v3, v1, v4

    new-array v3, v2, [I

    fill-array-data v3, :array_3

    aput-object v3, v1, v5

    sput-object v1, Lcom/android/systemui/statusbar/policy/WifiIcons;->QS_WIFI_SIGNAL_STRENGTH:[[I

    .line 51
    sget-object v1, Lcom/android/systemui/statusbar/policy/WifiIcons;->WIFI_SIGNAL_STRENGTH:[[I

    aget-object v1, v1, v4

    array-length v1, v1

    sput v1, Lcom/android/systemui/statusbar/policy/WifiIcons;->WIFI_LEVEL_COUNT:I

    .line 54
    new-array v1, v2, [[I

    const/4 v2, 0x4

    new-array v3, v2, [I

    fill-array-data v3, :array_4

    aput-object v3, v1, v4

    new-array v3, v2, [I

    fill-array-data v3, :array_5

    aput-object v3, v1, v5

    new-array v3, v2, [I

    fill-array-data v3, :array_6

    aput-object v3, v1, v0

    new-array v0, v2, [I

    fill-array-data v0, :array_7

    const/4 v3, 0x3

    aput-object v0, v1, v3

    new-array v0, v2, [I

    fill-array-data v0, :array_8

    aput-object v0, v1, v2

    sput-object v1, Lcom/android/systemui/statusbar/policy/WifiIcons;->WIFI_SIGNAL_STRENGTH_INOUT:[[I

    return-void

    nop

    :array_0
    .array-data 4
        0x7f080647
        0x7f080649
        0x7f08064e
        0x7f080653
        0x7f080658
    .end array-data

    :array_1
    .array-data 4
        0x7f080648
        0x7f08064a
        0x7f08064f
        0x7f080654
        0x7f080659
    .end array-data

    :array_2
    .array-data 4
        0x7f0803bc
        0x7f0803bd
        0x7f0803be
        0x7f0803bf
        0x7f0803c0
    .end array-data

    :array_3
    .array-data 4
        0x7f0803c4
        0x7f0803c5
        0x7f0803c6
        0x7f0803c7
        0x7f0803c8
    .end array-data

    :array_4
    .array-data 4
        0x7f080648
        0x7f080648
        0x7f080648
        0x7f080648
    .end array-data

    :array_5
    .array-data 4
        0x7f08064a
        0x7f08064b
        0x7f08064d
        0x7f08064c
    .end array-data

    :array_6
    .array-data 4
        0x7f08064f
        0x7f080650
        0x7f080652
        0x7f080651
    .end array-data

    :array_7
    .array-data 4
        0x7f080654
        0x7f080655
        0x7f080657
        0x7f080656
    .end array-data

    :array_8
    .array-data 4
        0x7f080659
        0x7f08065a
        0x7f08065c
        0x7f08065b
    .end array-data
.end method
