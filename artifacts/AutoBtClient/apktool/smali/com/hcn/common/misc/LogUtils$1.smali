.class Lcom/hcn/common/misc/LogUtils$1;
.super Ljava/lang/Object;
.source "LogUtils.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/misc/LogUtils;->log(ILjava/lang/String;[Ljava/lang/Object;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic val$body:Ljava/lang/String;

.field final synthetic val$tagHead:Lcom/hcn/common/misc/LogUtils$TagHead;

.field final synthetic val$type_low:I


# direct methods
.method constructor <init>(ILcom/hcn/common/misc/LogUtils$TagHead;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 236
    iput p1, p0, Lcom/hcn/common/misc/LogUtils$1;->val$type_low:I

    iput-object p2, p0, Lcom/hcn/common/misc/LogUtils$1;->val$tagHead:Lcom/hcn/common/misc/LogUtils$TagHead;

    iput-object p3, p0, Lcom/hcn/common/misc/LogUtils$1;->val$body:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    .line 239
    iget v0, p0, Lcom/hcn/common/misc/LogUtils$1;->val$type_low:I

    iget-object v1, p0, Lcom/hcn/common/misc/LogUtils$1;->val$tagHead:Lcom/hcn/common/misc/LogUtils$TagHead;

    iget-object v1, v1, Lcom/hcn/common/misc/LogUtils$TagHead;->tag:Ljava/lang/String;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v3, p0, Lcom/hcn/common/misc/LogUtils$1;->val$tagHead:Lcom/hcn/common/misc/LogUtils$TagHead;

    iget-object v3, v3, Lcom/hcn/common/misc/LogUtils$TagHead;->fileHead:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    iget-object v3, p0, Lcom/hcn/common/misc/LogUtils$1;->val$body:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v1, v2}, Lcom/hcn/common/misc/LogUtils;->-$$Nest$smprint2File(ILjava/lang/String;Ljava/lang/String;)V

    return-void
.end method
