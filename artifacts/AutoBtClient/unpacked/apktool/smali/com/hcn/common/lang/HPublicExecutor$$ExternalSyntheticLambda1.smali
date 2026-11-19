.class public final synthetic Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;
.super Ljava/lang/Object;
.source "D8$$SyntheticClass"

# interfaces
.implements Ljava/lang/Runnable;


# instance fields
.field public final synthetic f$0:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

.field public final synthetic f$1:J


# direct methods
.method public synthetic constructor <init>(Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V
    .locals 0

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;->f$0:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    iput-wide p2, p0, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;->f$1:J

    return-void
.end method


# virtual methods
.method public final run()V
    .locals 3

    iget-object v0, p0, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;->f$0:Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;

    iget-wide v1, p0, Lcom/hcn/common/lang/HPublicExecutor$$ExternalSyntheticLambda1;->f$1:J

    invoke-static {v0, v1, v2}, Lcom/hcn/common/lang/HPublicExecutor;->lambda$submitTask$0(Lcom/hcn/common/lang/HTaskRunnable$OnCompletionListener;J)V

    return-void
.end method
