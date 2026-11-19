.class public Lcom/android/systemui/statusbar/policy/BatteryVoltageTopWindow;
.super Lcom/android/systemui/statusbar/policy/TopWindow;
.source "BatteryVoltageTopWindow.java"


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .line 16
    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/android/systemui/statusbar/policy/TopWindow;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    .line 17
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;

    .line 12
    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/android/systemui/statusbar/policy/TopWindow;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 13
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "attrs"    # Landroid/util/AttributeSet;
    .param p3, "defStyle"    # I

    .line 20
    invoke-direct {p0, p1, p2, p3}, Lcom/android/systemui/statusbar/policy/TopWindow;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 21
    return-void
.end method


# virtual methods
.method public onDarkChanged(Landroid/graphics/Rect;FI)V
    .locals 4
    .param p1, "area"    # Landroid/graphics/Rect;
    .param p2, "darkIntensity"    # F
    .param p3, "tint"    # I

    .line 25
    invoke-static {p1, p0, p3}, Lcom/android/systemui/statusbar/policy/DarkIconDispatcher;->getTint(Landroid/graphics/Rect;Landroid/view/View;I)I

    move-result v0

    .line 26
    .local v0, "color":I
    invoke-virtual {p0, v0}, Lcom/android/systemui/statusbar/policy/BatteryVoltageTopWindow;->setTextColor(I)V

    .line 27
    invoke-virtual {p0}, Lcom/android/systemui/statusbar/policy/BatteryVoltageTopWindow;->getCompoundDrawables()[Landroid/graphics/drawable/Drawable;

    move-result-object v1

    .line 28
    .local v1, "bg":[Landroid/graphics/drawable/Drawable;
    const/4 v2, 0x0

    aget-object v2, v1, v2

    .line 29
    .local v2, "drawableLeft":Landroid/graphics/drawable/Drawable;
    instance-of v3, v2, Landroid/graphics/drawable/VectorDrawable;

    if-eqz v3, :cond_0

    .line 30
    move-object v3, v2

    check-cast v3, Landroid/graphics/drawable/VectorDrawable;

    .line 31
    .local v3, "vd":Landroid/graphics/drawable/VectorDrawable;
    invoke-virtual {v3, v0}, Landroid/graphics/drawable/VectorDrawable;->setTint(I)V

    .line 33
    .end local v3    # "vd":Landroid/graphics/drawable/VectorDrawable;
    :cond_0
    return-void
.end method
