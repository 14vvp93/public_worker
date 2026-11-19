.class Lcom/hcn/common/lang/HMapUtils$1;
.super Ljava/lang/Object;
.source "HMapUtils.java"

# interfaces
.implements Lcom/hcn/common/lang/HMapUtils$Closure;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/hcn/common/lang/HMapUtils;->transform(Ljava/util/Map;Lcom/hcn/common/lang/HMapUtils$Transformer;)Ljava/util/Map;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lcom/hcn/common/lang/HMapUtils$Closure<",
        "TK1;TV1;>;"
    }
.end annotation


# instance fields
.field final synthetic val$transMap:Ljava/util/Map;

.field final synthetic val$transformer:Lcom/hcn/common/lang/HMapUtils$Transformer;


# direct methods
.method constructor <init>(Lcom/hcn/common/lang/HMapUtils$Transformer;Ljava/util/Map;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    .line 181
    iput-object p1, p0, Lcom/hcn/common/lang/HMapUtils$1;->val$transformer:Lcom/hcn/common/lang/HMapUtils$Transformer;

    iput-object p2, p0, Lcom/hcn/common/lang/HMapUtils$1;->val$transMap:Ljava/util/Map;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public execute(Ljava/lang/Object;Ljava/lang/Object;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(TK1;TV1;)V"
        }
    .end annotation

    .line 184
    iget-object v0, p0, Lcom/hcn/common/lang/HMapUtils$1;->val$transformer:Lcom/hcn/common/lang/HMapUtils$Transformer;

    invoke-interface {v0, p1, p2}, Lcom/hcn/common/lang/HMapUtils$Transformer;->transform(Ljava/lang/Object;Ljava/lang/Object;)Landroid/util/Pair;

    move-result-object p1

    .line 185
    iget-object p2, p0, Lcom/hcn/common/lang/HMapUtils$1;->val$transMap:Ljava/util/Map;

    iget-object v0, p1, Landroid/util/Pair;->first:Ljava/lang/Object;

    iget-object p1, p1, Landroid/util/Pair;->second:Ljava/lang/Object;

    invoke-interface {p2, v0, p1}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    return-void
.end method
