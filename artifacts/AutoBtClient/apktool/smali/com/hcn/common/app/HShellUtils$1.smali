.class Lcom/hcn/common/app/HShellUtils$1;
.super Lcom/hcn/common/utils/HUtilsEx$Task;
.source "HShellUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HShellUtils;->execCmdAsync([Ljava/lang/String;ZZLcom/hcn/common/utils/HUtilsEx$Consumer;)Lcom/hcn/common/utils/HUtilsEx$Task;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Lcom/hcn/common/utils/HUtilsEx$Task<",
        "Lcom/hcn/common/app/HShellUtils$CommandResult;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic val$commands:[Ljava/lang/String;

.field final synthetic val$isNeedResultMsg:Z

.field final synthetic val$isRooted:Z


# direct methods
.method constructor <init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;[Ljava/lang/String;ZZ)V
    .locals 0

    .line 114
    iput-object p2, p0, Lcom/hcn/common/app/HShellUtils$1;->val$commands:[Ljava/lang/String;

    iput-boolean p3, p0, Lcom/hcn/common/app/HShellUtils$1;->val$isRooted:Z

    iput-boolean p4, p0, Lcom/hcn/common/app/HShellUtils$1;->val$isNeedResultMsg:Z

    invoke-direct {p0, p1}, Lcom/hcn/common/utils/HUtilsEx$Task;-><init>(Lcom/hcn/common/utils/HUtilsEx$Consumer;)V

    return-void
.end method


# virtual methods
.method public doInBackground()Lcom/hcn/common/app/HShellUtils$CommandResult;
    .locals 3

    .line 117
    iget-object v0, p0, Lcom/hcn/common/app/HShellUtils$1;->val$commands:[Ljava/lang/String;

    iget-boolean v1, p0, Lcom/hcn/common/app/HShellUtils$1;->val$isRooted:Z

    iget-boolean v2, p0, Lcom/hcn/common/app/HShellUtils$1;->val$isNeedResultMsg:Z

    invoke-static {v0, v1, v2}, Lcom/hcn/common/app/HShellUtils;->execCmd([Ljava/lang/String;ZZ)Lcom/hcn/common/app/HShellUtils$CommandResult;

    move-result-object v0

    return-object v0
.end method

.method public bridge synthetic doInBackground()Ljava/lang/Object;
    .locals 1
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Throwable;
        }
    .end annotation

    .line 114
    invoke-virtual {p0}, Lcom/hcn/common/app/HShellUtils$1;->doInBackground()Lcom/hcn/common/app/HShellUtils$CommandResult;

    move-result-object v0

    return-object v0
.end method
