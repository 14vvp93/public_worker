.class public final Lcom/hcn/common/utils/HMessageUtils$UiMessage;
.super Ljava/lang/Object;
.source "HMessageUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/utils/HMessageUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x19
    name = "UiMessage"
.end annotation


# instance fields
.field private mMessage:Landroid/os/Message;


# direct methods
.method static bridge synthetic -$$Nest$msetMessage(Lcom/hcn/common/utils/HMessageUtils$UiMessage;Landroid/os/Message;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->setMessage(Landroid/os/Message;)V

    return-void
.end method

.method private constructor <init>(Landroid/os/Message;)V
    .locals 0

    .line 413
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 414
    iput-object p1, p0, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->mMessage:Landroid/os/Message;

    return-void
.end method

.method synthetic constructor <init>(Landroid/os/Message;Lcom/hcn/common/utils/HMessageUtils$UiMessage-IA;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;-><init>(Landroid/os/Message;)V

    return-void
.end method

.method private setMessage(Landroid/os/Message;)V
    .locals 0

    .line 418
    iput-object p1, p0, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->mMessage:Landroid/os/Message;

    return-void
.end method


# virtual methods
.method public getId()I
    .locals 1

    .line 422
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->mMessage:Landroid/os/Message;

    iget v0, v0, Landroid/os/Message;->what:I

    return v0
.end method

.method public getObject()Ljava/lang/Object;
    .locals 1

    .line 426
    iget-object v0, p0, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->mMessage:Landroid/os/Message;

    iget-object v0, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    return-object v0
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 431
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "{ id="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 432
    invoke-virtual {p0}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->getId()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, ", obj="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 433
    invoke-virtual {p0}, Lcom/hcn/common/utils/HMessageUtils$UiMessage;->getObject()Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, " }"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
