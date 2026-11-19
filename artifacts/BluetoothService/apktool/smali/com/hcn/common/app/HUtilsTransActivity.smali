.class public Lcom/hcn/common/app/HUtilsTransActivity;
.super Landroidx/appcompat/app/AppCompatActivity;
.source "HUtilsTransActivity.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;
    }
.end annotation


# static fields
.field private static final CALLBACK_MAP:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Lcom/hcn/common/app/HUtilsTransActivity;",
            "Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;",
            ">;"
        }
    .end annotation
.end field

.field protected static final EXTRA_DELEGATE:Ljava/lang/String; = "extra_delegate"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 28
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .line 25
    invoke-direct {p0}, Landroidx/appcompat/app/AppCompatActivity;-><init>()V

    return-void
.end method

.method public static start(Landroid/app/Activity;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;)V
    .locals 2

    .line 43
    const-class v0, Lcom/hcn/common/app/HUtilsTransActivity;

    const/4 v1, 0x0

    invoke-static {p0, v1, p1, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;Ljava/lang/Class;)V

    return-void
.end method

.method public static start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/app/Activity;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Landroid/content/Intent;",
            ">;",
            "Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;",
            ")V"
        }
    .end annotation

    .line 49
    const-class v0, Lcom/hcn/common/app/HUtilsTransActivity;

    invoke-static {p0, p1, p2, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;Ljava/lang/Class;)V

    return-void
.end method

.method protected static start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;Ljava/lang/Class;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/app/Activity;",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Landroid/content/Intent;",
            ">;",
            "Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;",
            "Ljava/lang/Class<",
            "*>;)V"
        }
    .end annotation

    if-nez p2, :cond_0

    return-void

    .line 60
    :cond_0
    new-instance v0, Landroid/content/Intent;

    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object v1

    invoke-direct {v0, v1, p3}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    const-string p3, "extra_delegate"

    .line 61
    invoke-virtual {v0, p3, p2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;Ljava/io/Serializable;)Landroid/content/Intent;

    if-eqz p1, :cond_1

    .line 63
    invoke-interface {p1, v0}, Lcom/hcn/common/utils/HUtilsEx$Consumer;->accept(Ljava/lang/Object;)V

    :cond_1
    if-nez p0, :cond_2

    const/high16 p0, 0x10000000

    .line 67
    invoke-virtual {v0, p0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 68
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->getApp()Landroid/app/Application;

    move-result-object p0

    invoke-virtual {p0, v0}, Landroid/app/Application;->startActivity(Landroid/content/Intent;)V

    goto :goto_0

    .line 70
    :cond_2
    invoke-virtual {p0, v0}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;)V

    :goto_0
    return-void
.end method

.method public static start(Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;)V
    .locals 2

    .line 33
    const-class v0, Lcom/hcn/common/app/HUtilsTransActivity;

    const/4 v1, 0x0

    invoke-static {v1, v1, p0, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;Ljava/lang/Class;)V

    return-void
.end method

.method public static start(Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
            "Landroid/content/Intent;",
            ">;",
            "Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;",
            ")V"
        }
    .end annotation

    .line 38
    const-class v0, Lcom/hcn/common/app/HUtilsTransActivity;

    const/4 v1, 0x0

    invoke-static {v1, p0, p1, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->start(Landroid/app/Activity;Lcom/hcn/common/utils/HUtilsEx$Consumer;Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;Ljava/lang/Class;)V

    return-void
.end method


# virtual methods
.method public dispatchTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 1

    .line 183
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    .line 185
    invoke-super {p0, p1}, Landroidx/appcompat/app/AppCompatActivity;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1

    .line 188
    :cond_0
    invoke-virtual {v0, p0, p1}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->dispatchTouchEvent(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/view/MotionEvent;)Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 p1, 0x1

    return p1

    .line 192
    :cond_1
    invoke-super {p0, p1}, Landroidx/appcompat/app/AppCompatActivity;->dispatchTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1
.end method

.method protected onActivityResult(IILandroid/content/Intent;)V
    .locals 1

    .line 172
    invoke-super {p0, p1, p2, p3}, Landroidx/appcompat/app/AppCompatActivity;->onActivityResult(IILandroid/content/Intent;)V

    .line 173
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 178
    :cond_0
    invoke-virtual {v0, p0, p1, p2, p3}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onActivityResult(Lcom/hcn/common/app/HUtilsTransActivity;IILandroid/content/Intent;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 2

    const/4 v0, 0x0

    .line 76
    invoke-virtual {p0, v0, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->overridePendingTransition(II)V

    .line 77
    invoke-virtual {p0}, Lcom/hcn/common/app/HUtilsTransActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    const-string v1, "extra_delegate"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getSerializableExtra(Ljava/lang/String;)Ljava/io/Serializable;

    move-result-object v0

    .line 78
    instance-of v1, v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v1, :cond_0

    .line 79
    invoke-super {p0, p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V

    .line 80
    invoke-virtual {p0}, Lcom/hcn/common/app/HUtilsTransActivity;->finish()V

    return-void

    .line 84
    :cond_0
    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    .line 85
    sget-object v1, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v1, p0, v0}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    .line 86
    invoke-virtual {v0, p0, p1}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onCreateBefore(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/os/Bundle;)V

    .line 87
    invoke-super {p0, p1}, Landroidx/appcompat/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V

    .line 88
    invoke-virtual {v0, p0, p1}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onCreated(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/os/Bundle;)V

    return-void
.end method

.method protected onDestroy()V
    .locals 2

    .line 149
    invoke-super {p0}, Landroidx/appcompat/app/AppCompatActivity;->onDestroy()V

    .line 150
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v1, :cond_0

    return-void

    .line 155
    :cond_0
    invoke-virtual {v1, p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onDestroy(Lcom/hcn/common/app/HUtilsTransActivity;)V

    .line 156
    invoke-interface {v0, p0}, Ljava/util/Map;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method

.method protected onPause()V
    .locals 1

    const/4 v0, 0x0

    .line 115
    invoke-virtual {p0, v0, v0}, Lcom/hcn/common/app/HUtilsTransActivity;->overridePendingTransition(II)V

    .line 116
    invoke-super {p0}, Landroidx/appcompat/app/AppCompatActivity;->onPause()V

    .line 117
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 122
    :cond_0
    invoke-virtual {v0, p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onPaused(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method

.method public onRequestPermissionsResult(I[Ljava/lang/String;[I)V
    .locals 1

    .line 161
    invoke-super {p0, p1, p2, p3}, Landroidx/appcompat/app/AppCompatActivity;->onRequestPermissionsResult(I[Ljava/lang/String;[I)V

    .line 162
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 167
    :cond_0
    invoke-virtual {v0, p0, p1, p2, p3}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onRequestPermissionsResult(Lcom/hcn/common/app/HUtilsTransActivity;I[Ljava/lang/String;[I)V

    return-void
.end method

.method protected onResume()V
    .locals 1

    .line 104
    invoke-super {p0}, Landroidx/appcompat/app/AppCompatActivity;->onResume()V

    .line 105
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 110
    :cond_0
    invoke-virtual {v0, p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onResumed(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method

.method protected onSaveInstanceState(Landroid/os/Bundle;)V
    .locals 1

    .line 138
    invoke-super {p0, p1}, Landroidx/appcompat/app/AppCompatActivity;->onSaveInstanceState(Landroid/os/Bundle;)V

    .line 139
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 144
    :cond_0
    invoke-virtual {v0, p0, p1}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onSaveInstanceState(Lcom/hcn/common/app/HUtilsTransActivity;Landroid/os/Bundle;)V

    return-void
.end method

.method protected onStart()V
    .locals 1

    .line 93
    invoke-super {p0}, Landroidx/appcompat/app/AppCompatActivity;->onStart()V

    .line 94
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 99
    :cond_0
    invoke-virtual {v0, p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onStarted(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method

.method protected onStop()V
    .locals 1

    .line 127
    invoke-super {p0}, Landroidx/appcompat/app/AppCompatActivity;->onStop()V

    .line 128
    sget-object v0, Lcom/hcn/common/app/HUtilsTransActivity;->CALLBACK_MAP:Ljava/util/Map;

    invoke-interface {v0, p0}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;

    if-nez v0, :cond_0

    return-void

    .line 133
    :cond_0
    invoke-virtual {v0, p0}, Lcom/hcn/common/app/HUtilsTransActivity$TransActivityDelegate;->onStopped(Lcom/hcn/common/app/HUtilsTransActivity;)V

    return-void
.end method
