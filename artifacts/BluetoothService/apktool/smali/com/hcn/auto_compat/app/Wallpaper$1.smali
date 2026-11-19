.class Lcom/hcn/auto_compat/app/Wallpaper$1;
.super Ljava/lang/Object;
.source "Wallpaper.java"

# interfaces
.implements Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/auto_compat/app/Wallpaper;->initialize()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/hcn/auto_compat/app/Wallpaper;

.field final synthetic val$taskId:I


# direct methods
.method constructor <init>(Lcom/hcn/auto_compat/app/Wallpaper;I)V
    .locals 0

    .line 144
    iput-object p1, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    iput p2, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->val$taskId:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCompletion(JLjava/lang/Object;)V
    .locals 4

    .line 148
    iget p3, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->val$taskId:I

    int-to-long v0, p3

    cmp-long p1, p1, v0

    if-eqz p1, :cond_0

    return-void

    .line 153
    :cond_0
    iget-object p1, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    const/4 p2, 0x1

    invoke-static {p1, p2}, Lcom/hcn/auto_compat/app/Wallpaper;->access$202(Lcom/hcn/auto_compat/app/Wallpaper;Z)Z

    .line 154
    invoke-static {}, Lcom/hcn/common/utils/HUtilsEx;->isAppDebug()Z

    move-result p1

    if-eqz p1, :cond_1

    .line 155
    iget-object p1, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    invoke-static {p1}, Lcom/hcn/auto_compat/app/Wallpaper;->access$300(Lcom/hcn/auto_compat/app/Wallpaper;)Ljava/util/ArrayList;

    move-result-object p1

    invoke-virtual {p1}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object p1

    :goto_0
    invoke-interface {p1}, Ljava/util/Iterator;->hasNext()Z

    move-result p3

    if-eqz p3, :cond_1

    invoke-interface {p1}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object p3

    check-cast p3, Lcom/hcn/auto_compat/app/Wallpaper$Info;

    new-array v0, p2, [Ljava/lang/Object;

    const/4 v1, 0x0

    .line 156
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, ": "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {p3}, Lcom/hcn/auto_compat/app/Wallpaper$Info;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-virtual {v2, p3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p3

    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    aput-object p3, v0, v1

    const-string p3, "Wallpaper"

    invoke-static {p3, v0}, Lcom/hcn/common/misc/LogUtils;->vTag(Ljava/lang/String;[Ljava/lang/Object;)V

    goto :goto_0

    .line 161
    :cond_1
    iget-object p1, p0, Lcom/hcn/auto_compat/app/Wallpaper$1;->this$0:Lcom/hcn/auto_compat/app/Wallpaper;

    const/4 p2, 0x0

    const-string p3, "Completed"

    invoke-static {p1, p3, p2}, Lcom/hcn/auto_compat/app/Wallpaper;->access$400(Lcom/hcn/auto_compat/app/Wallpaper;Ljava/lang/String;Ljava/lang/Object;)V

    return-void
.end method
