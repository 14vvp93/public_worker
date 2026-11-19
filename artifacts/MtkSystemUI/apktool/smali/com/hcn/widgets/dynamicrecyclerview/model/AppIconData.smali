.class public Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;
.super Ljava/lang/Object;
.source "AppIconData.java"


# instance fields
.field private mAppIcon:Landroid/graphics/drawable/Drawable;

.field private mAppName:Ljava/lang/String;

.field private mComponentName:Landroid/content/ComponentName;


# direct methods
.method public constructor <init>(Ljava/lang/String;Landroid/content/ComponentName;Landroid/graphics/drawable/Drawable;)V
    .locals 0
    .param p1, "appName"    # Ljava/lang/String;
    .param p2, "componentName"    # Landroid/content/ComponentName;
    .param p3, "appIcon"    # Landroid/graphics/drawable/Drawable;

    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 12
    iput-object p1, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mAppName:Ljava/lang/String;

    .line 13
    iput-object p2, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mComponentName:Landroid/content/ComponentName;

    .line 14
    iput-object p3, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mAppIcon:Landroid/graphics/drawable/Drawable;

    .line 15
    return-void
.end method


# virtual methods
.method public getAppIcon()Landroid/graphics/drawable/Drawable;
    .locals 1

    .line 40
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mAppIcon:Landroid/graphics/drawable/Drawable;

    return-object v0
.end method

.method public getAppName()Ljava/lang/String;
    .locals 1

    .line 24
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mAppName:Ljava/lang/String;

    return-object v0
.end method

.method public getComponentName()Landroid/content/ComponentName;
    .locals 1

    .line 32
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/model/AppIconData;->mComponentName:Landroid/content/ComponentName;

    return-object v0
.end method
