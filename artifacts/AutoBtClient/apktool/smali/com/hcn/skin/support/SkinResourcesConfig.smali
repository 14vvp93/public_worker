.class public Lcom/hcn/skin/support/SkinResourcesConfig;
.super Ljava/lang/Object;
.source "SkinResourcesConfig.java"


# static fields
.field private static sSkinResourcesClass:Ljava/lang/Class;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/lang/Class<",
            "+",
            "Lcom/hcn/skin/support/resources/SkinCompatResources;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 9
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static getSkinResourcesClass()Ljava/lang/Class;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/lang/Class<",
            "+",
            "Lcom/hcn/skin/support/resources/SkinCompatResources;",
            ">;"
        }
    .end annotation

    .line 21
    sget-object v0, Lcom/hcn/skin/support/SkinResourcesConfig;->sSkinResourcesClass:Ljava/lang/Class;

    return-object v0
.end method

.method public static setSkinResourcesClass(Ljava/lang/Class;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class<",
            "+",
            "Lcom/hcn/skin/support/resources/SkinCompatResources;",
            ">;)V"
        }
    .end annotation

    .line 17
    sput-object p0, Lcom/hcn/skin/support/SkinResourcesConfig;->sSkinResourcesClass:Ljava/lang/Class;

    return-void
.end method
