.class Lcom/android/systemui/statusbar/phone/NavigationBarFragment$MyHandler;
.super Landroid/os/Handler;
.source "NavigationBarFragment.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/android/systemui/statusbar/phone/NavigationBarFragment;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0xa
    name = "MyHandler"
.end annotation


# instance fields
.field private mWeakReference:Ljava/lang/ref/WeakReference;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/ref/WeakReference<",
            "Lcom/android/systemui/statusbar/phone/NavigationBarFragment;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Lcom/android/systemui/statusbar/phone/NavigationBarFragment;)V
    .locals 1
    .param p1, "navigationBarFragment"    # Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    .line 2001
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    .line 2002
    new-instance v0, Ljava/lang/ref/WeakReference;

    invoke-direct {v0, p1}, Ljava/lang/ref/WeakReference;-><init>(Ljava/lang/Object;)V

    iput-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$MyHandler;->mWeakReference:Ljava/lang/ref/WeakReference;

    .line 2003
    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5
    .param p1, "msg"    # Landroid/os/Message;

    .line 2007
    iget-object v0, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$MyHandler;->mWeakReference:Ljava/lang/ref/WeakReference;

    invoke-virtual {v0}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    .line 2008
    .local v0, "navigationBarFragment":Lcom/android/systemui/statusbar/phone/NavigationBarFragment;
    if-eqz v0, :cond_0

    .line 2009
    iget v1, p1, Landroid/os/Message;->what:I

    const/16 v2, 0x64

    if-ne v1, v2, :cond_0

    .line 2010
    const-string v1, "NavigationBar"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "changeBottomBgByThemePicker---handler"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, "persist.sys.skin"

    const-string v4, "com.hcn.auto.theme.p1"

    invoke-static {v3, v4}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->getSystemProperty(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 2011
    iget-object v1, p0, Lcom/android/systemui/statusbar/phone/NavigationBarFragment$MyHandler;->mWeakReference:Ljava/lang/ref/WeakReference;

    invoke-virtual {v1}, Ljava/lang/ref/WeakReference;->get()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;

    invoke-virtual {v1}, Lcom/android/systemui/statusbar/phone/NavigationBarFragment;->changeBottomBgByThemePicker()V

    .line 2014
    :cond_0
    return-void
.end method
