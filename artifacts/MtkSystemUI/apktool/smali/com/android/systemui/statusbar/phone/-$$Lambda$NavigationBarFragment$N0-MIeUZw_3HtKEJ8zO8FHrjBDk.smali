.class public final synthetic Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk;
.super Ljava/lang/Object;
.source "lambda"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field private final synthetic f$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

.field private final synthetic f$1:Landroid/graphics/drawable/Drawable;


# direct methods
.method public synthetic constructor <init>(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;Landroid/graphics/drawable/Drawable;)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk;->f$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iput-object p2, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk;->f$1:Landroid/graphics/drawable/Drawable;

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 2

    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk;->f$0:Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/-$$Lambda$NavigationBarFragment$N0-MIeUZw_3HtKEJ8zO8FHrjBDk;->f$1:Landroid/graphics/drawable/Drawable;

    invoke-static {v0, v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->lambda$changeDrawable$3(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;Landroid/graphics/drawable/Drawable;)V

    return-void
.end method
