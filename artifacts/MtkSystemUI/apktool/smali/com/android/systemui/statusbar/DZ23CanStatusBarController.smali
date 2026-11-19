.class public Lcom/android/systemui/statusbar/DZ23CanStatusBarController;
.super Ljava/lang/Object;
.source "DZ23CanStatusBarController.java"


# instance fields
.field private CanMap:Landroid/util/SparseArray;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Landroid/util/SparseArray<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private final gear:[Ljava/lang/String;

.field private mContext:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Landroid/content/Context;",
            ">;"
        }
    .end annotation
.end field

.field private mFragmentView:Landroid/view/View;

.field private mHandler:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Landroid/os/Handler;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 3
    .param p1, "ctx"    # Landroid/content/Context;

    .line 34
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 24
    const-string v0, "N"

    const-string v1, "R"

    const-string v2, "D"

    filled-new-array {v0, v1, v2}, [Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->gear:[Ljava/lang/String;

    .line 35
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    .line 36
    return-void
.end method

.method static synthetic access$000(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)Ljava/lang/ref/WeakReference;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    .line 19
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    return-object v0
.end method

.method static synthetic access$100(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;)[Ljava/lang/String;
    .locals 1
    .param p0, "x0"    # Lcom/android/systemui/statusbar/DZ23CanStatusBarController;

    .line 19
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->gear:[Ljava/lang/String;

    return-object v0
.end method

.method private buildGearObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;
    .locals 2
    .param p1, "tvGear"    # Landroid/widget/TextView;

    .line 128
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    if-nez p1, :cond_0

    goto :goto_0

    .line 129
    :cond_0
    new-instance v0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;

    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/Handler;

    invoke-direct {v0, p0, v1, p1}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$2;-><init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/TextView;)V

    return-object v0

    .line 128
    :cond_1
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private buildNormalObserver(Landroid/widget/ImageView;Ljava/lang/String;)Landroid/database/ContentObserver;
    .locals 2
    .param p1, "imageView"    # Landroid/widget/ImageView;
    .param p2, "target"    # Ljava/lang/String;

    .line 117
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    if-nez p1, :cond_0

    goto :goto_0

    .line 118
    :cond_0
    new-instance v0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;

    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/Handler;

    invoke-direct {v0, p0, v1, p1, p2}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$1;-><init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/ImageView;Ljava/lang/String;)V

    return-object v0

    .line 117
    :cond_1
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private buildSpeedObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;
    .locals 2
    .param p1, "tvSpeed"    # Landroid/widget/TextView;

    .line 140
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    if-eqz v0, :cond_1

    if-nez p1, :cond_0

    goto :goto_0

    .line 141
    :cond_0
    new-instance v0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;

    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/os/Handler;

    invoke-direct {v0, p0, v1, p1}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController$3;-><init>(Lcom/android/systemui/statusbar/DZ23CanStatusBarController;Landroid/os/Handler;Landroid/widget/TextView;)V

    return-object v0

    .line 140
    :cond_1
    :goto_0
    const/4 v0, 0x0

    return-object v0
.end method

.method private regist(Landroid/widget/ImageView;Ljava/lang/String;)V
    .locals 5
    .param p1, "imageView"    # Landroid/widget/ImageView;
    .param p2, "target"    # Ljava/lang/String;

    .line 100
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    .line 101
    invoke-static {p2}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    .line 103
    invoke-direct {p0, p1, p2}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildNormalObserver(Landroid/widget/ImageView;Ljava/lang/String;)Landroid/database/ContentObserver;

    move-result-object v2

    invoke-static {v2}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/database/ContentObserver;

    .line 100
    const/4 v3, 0x1

    const/4 v4, -0x1

    invoke-virtual {v0, v1, v3, v2, v4}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    .line 105
    return-void
.end method


# virtual methods
.method public setNavigationBarFragment(Landroid/view/View;Landroid/os/Handler;)V
    .locals 3
    .param p1, "fragmentView"    # Landroid/view/View;
    .param p2, "handler"    # Landroid/os/Handler;

    .line 39
    iput-object p1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mFragmentView:Landroid/view/View;

    .line 40
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p2}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mHandler:Ljava/lang/ref/WeakReference;

    .line 41
    new-instance v0, Landroid/util/SparseArray;

    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    iput-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    .line 42
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_left_signal"

    const v2, 0x7f0a01da

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 43
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_high_beam"

    const v2, 0x7f0a01d7

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 44
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_front_fog_light"

    const v2, 0x7f0a01d9

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 45
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_rear_fog_light"

    const v2, 0x7f0a01d8

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 46
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_right_signal"

    const v2, 0x7f0a01dc

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 47
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_head_light"

    const v2, 0x7f0a01db

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 48
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_low_beam"

    const v2, 0x7f0a01d6

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 50
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "persist.vendor.cem.cardoor1"

    const v2, 0x7f0a01d0

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 51
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "persist.vendor.cem.cardoor3"

    const v2, 0x7f0a01cf

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 52
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "persist.vendor.cem.cardoor2"

    const v2, 0x7f0a01d2

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 53
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "persist.vendor.cem.cardoor4"

    const v2, 0x7f0a01d1

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 55
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_brake_failure"

    const v2, 0x7f0a01cd

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 56
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_parking_brake"

    const v2, 0x7f0a01dd

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 57
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_eps_alarm"

    const v2, 0x7f0a01d5

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 58
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_steering_fault"

    const v2, 0x7f0a01e4

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 59
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_abs_indication"

    const v2, 0x7f0a01c8

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 61
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_dcdc_fault_indication"

    const v2, 0x7f0a01ce

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 62
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_power_battery_fault"

    const v2, 0x7f0a01e0

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 63
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_low_battery_alarm"

    const v2, 0x7f0a01e1

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 64
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_charging_status_indication"

    const v2, 0x7f0a01df

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 65
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_battery_disconnect"

    const v2, 0x7f0a01c9

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 66
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_battery_temp_abnormal_indication"

    const v2, 0x7f0a01cb

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 67
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_battery_insulation_fault_indication"

    const v2, 0x7f0a01ca

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 68
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_seat_belt"

    const v2, 0x7f0a01cc

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 69
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_power_derate"

    const v2, 0x7f0a01e3

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 70
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_braking_energy_recovery_indication"

    const v2, 0x7f0a01e2

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 71
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_charging_connection"

    const v2, 0x7f0a01de

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 72
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_motor_fault_indication"

    const v2, 0x7f0a01d4

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 73
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    const-string v1, "car_motor_overheat_indication"

    const v2, 0x7f0a01d3

    invoke-virtual {v0, v2, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 74
    return-void
.end method

.method public setUp()V
    .locals 7

    .line 78
    const/4 v0, 0x0

    move v1, v0

    .local v1, "i":I
    :goto_0
    iget-object v2, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    invoke-virtual {v2}, Landroid/util/SparseArray;->size()I

    move-result v2

    const/4 v3, 0x1

    if-ge v1, v2, :cond_3

    .line 79
    iget-object v2, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mFragmentView:Landroid/view/View;

    iget-object v4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->keyAt(I)I

    move-result v4

    invoke-virtual {v2, v4}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageView;

    .line 80
    .local v2, "ivTemp":Landroid/widget/ImageView;
    if-eqz v2, :cond_2

    .line 81
    iget-object v4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v4}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Landroid/content/Context;

    invoke-virtual {v4}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v4

    iget-object v5, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    invoke-virtual {v5, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Ljava/lang/String;

    invoke-static {v4, v5, v0}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v4

    if-ne v4, v3, :cond_0

    goto :goto_1

    :cond_0
    move v3, v0

    .line 82
    .local v3, "isShow":Z
    :goto_1
    if-eqz v3, :cond_1

    move v4, v0

    goto :goto_2

    :cond_1
    const/4 v4, 0x4

    :goto_2
    invoke-virtual {v2, v4}, Landroid/widget/ImageView;->setVisibility(I)V

    .line 83
    iget-object v4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->CanMap:Landroid/util/SparseArray;

    invoke-virtual {v4, v1}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/lang/String;

    invoke-direct {p0, v2, v4}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->regist(Landroid/widget/ImageView;Ljava/lang/String;)V

    .line 78
    .end local v3    # "isShow":Z
    :cond_2
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 86
    .end local v1    # "i":I
    .end local v2    # "ivTemp":Landroid/widget/ImageView;
    :cond_3
    iget-object v1, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mFragmentView:Landroid/view/View;

    const v2, 0x7f0a0430

    invoke-virtual {v1, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iget-object v4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->gear:[Ljava/lang/String;

    iget-object v5, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v5}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Landroid/content/Context;

    invoke-virtual {v5}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v5

    const-string v6, "car_manual"

    invoke-static {v5, v6, v0}, Landroid/provider/Settings$System;->getInt(Landroid/content/ContentResolver;Ljava/lang/String;I)I

    move-result v0

    aget-object v0, v4, v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 87
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "car_manual"

    .line 88
    invoke-static {v1}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    iget-object v4, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mFragmentView:Landroid/view/View;

    .line 90
    invoke-virtual {v4, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildGearObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;

    move-result-object v2

    invoke-static {v2}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/database/ContentObserver;

    .line 87
    const/4 v4, -0x1

    invoke-virtual {v0, v1, v3, v2, v4}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    .line 92
    iget-object v0, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mContext:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/content/Context;

    invoke-virtual {v0}, Landroid/content/Context;->getContentResolver()Landroid/content/ContentResolver;

    move-result-object v0

    const-string v1, "car_speed_value"

    .line 93
    invoke-static {v1}, Landroid/provider/Settings$System;->getUriFor(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    iget-object v2, p0, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->mFragmentView:Landroid/view/View;

    const v5, 0x7f0a0432

    .line 95
    invoke-virtual {v2, v5}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    invoke-direct {p0, v2}, Lcom/android/systemui/statusbar/DZ23CanStatusBarController;->buildSpeedObserver(Landroid/widget/TextView;)Landroid/database/ContentObserver;

    move-result-object v2

    invoke-static {v2}, Ljava/util/Objects;->requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Landroid/database/ContentObserver;

    .line 92
    invoke-virtual {v0, v1, v3, v2, v4}, Landroid/content/ContentResolver;->registerContentObserver(Landroid/net/Uri;ZLandroid/database/ContentObserver;I)V

    .line 97
    return-void
.end method
