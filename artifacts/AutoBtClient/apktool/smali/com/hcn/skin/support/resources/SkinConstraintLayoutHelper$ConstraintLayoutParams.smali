.class public Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;
.super Ljava/lang/Object;
.source "SkinConstraintLayoutHelper.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1
    name = "ConstraintLayoutParams"
.end annotation


# instance fields
.field public id:I

.field public layout:I

.field public mLayoutConstraintMap:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;"
        }
    .end annotation
.end field

.field final synthetic this$0:Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;


# direct methods
.method public constructor <init>(Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;)V
    .locals 0

    .line 228
    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->this$0:Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 p1, -0x1

    .line 229
    iput p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    const/4 p1, 0x0

    .line 230
    iput p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    .line 231
    new-instance p1, Ljava/util/HashMap;

    invoke-direct {p1}, Ljava/util/HashMap;-><init>()V

    iput-object p1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->mLayoutConstraintMap:Ljava/util/Map;

    return-void
.end method


# virtual methods
.method public toString()Ljava/lang/String;
    .locals 2

    .line 235
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "id:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->id:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "  layout:"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget v1, p0, Lcom/hcn/skin/support/resources/SkinConstraintLayoutHelper$ConstraintLayoutParams;->layout:I

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
