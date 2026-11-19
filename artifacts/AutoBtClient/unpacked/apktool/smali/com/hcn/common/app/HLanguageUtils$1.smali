.class Lcom/hcn/common/app/HLanguageUtils$1;
.super Ljava/lang/Object;
.source "HLanguageUtils.java"

# interfaces
.implements Lcom/hcn/common/utils/HUtilsEx$Consumer;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/app/HLanguageUtils;->applyLanguageReal(Ljava/util/Locale;Z)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lcom/hcn/common/utils/HUtilsEx$Consumer<",
        "Ljava/lang/Boolean;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic val$isRelaunchApp:Z


# direct methods
.method constructor <init>(Z)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 81
    iput-boolean p1, p0, Lcom/hcn/common/app/HLanguageUtils$1;->val$isRelaunchApp:Z

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public accept(Ljava/lang/Boolean;)V
    .locals 0

    .line 84
    invoke-virtual {p1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result p1

    if-eqz p1, :cond_0

    .line 85
    iget-boolean p1, p0, Lcom/hcn/common/app/HLanguageUtils$1;->val$isRelaunchApp:Z

    invoke-static {p1}, Lcom/hcn/common/app/HLanguageUtils;->-$$Nest$smrestart(Z)V

    goto :goto_0

    .line 88
    :cond_0
    invoke-static {}, Lcom/hcn/common/app/HAppUtils;->relaunchApp()V

    :goto_0
    return-void
.end method

.method public bridge synthetic accept(Ljava/lang/Object;)V
    .locals 0

    .line 81
    check-cast p1, Ljava/lang/Boolean;

    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HLanguageUtils$1;->accept(Ljava/lang/Boolean;)V

    return-void
.end method
