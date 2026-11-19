.class public Lcom/hcn/common/app/HAppUtils$AppInfo;
.super Ljava/lang/Object;
.source "HAppUtils.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/common/app/HAppUtils;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x9
    name = "AppInfo"
.end annotation


# instance fields
.field private icon:Landroid/graphics/drawable/Drawable;

.field private isSystem:Z

.field private minSdkVersion:I

.field private name:Ljava/lang/String;

.field private packageName:Ljava/lang/String;

.field private packagePath:Ljava/lang/String;

.field private targetSdkVersion:I

.field private versionCode:I

.field private versionName:Ljava/lang/String;


# direct methods
.method public constructor <init>(Ljava/lang/String;Ljava/lang/String;Landroid/graphics/drawable/Drawable;Ljava/lang/String;Ljava/lang/String;IIIZ)V
    .locals 0

    .line 1118
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 1119
    invoke-virtual {p0, p2}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setName(Ljava/lang/String;)V

    .line 1120
    invoke-virtual {p0, p3}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setIcon(Landroid/graphics/drawable/Drawable;)V

    .line 1121
    invoke-virtual {p0, p1}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setPackageName(Ljava/lang/String;)V

    .line 1122
    invoke-virtual {p0, p4}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setPackagePath(Ljava/lang/String;)V

    .line 1123
    invoke-virtual {p0, p5}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setVersionName(Ljava/lang/String;)V

    .line 1124
    invoke-virtual {p0, p6}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setVersionCode(I)V

    .line 1125
    invoke-virtual {p0, p7}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setMinSdkVersion(I)V

    .line 1126
    invoke-virtual {p0, p8}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setTargetSdkVersion(I)V

    .line 1127
    invoke-virtual {p0, p9}, Lcom/hcn/common/app/HAppUtils$AppInfo;->setSystem(Z)V

    return-void
.end method


# virtual methods
.method public getIcon()Landroid/graphics/drawable/Drawable;
    .locals 1

    .line 1047
    iget-object v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->icon:Landroid/graphics/drawable/Drawable;

    return-object v0
.end method

.method public getMinSdkVersion()I
    .locals 1

    .line 1103
    iget v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->minSdkVersion:I

    return v0
.end method

.method public getName()Ljava/lang/String;
    .locals 1

    .line 1071
    iget-object v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->name:Ljava/lang/String;

    return-object v0
.end method

.method public getPackageName()Ljava/lang/String;
    .locals 1

    .line 1063
    iget-object v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->packageName:Ljava/lang/String;

    return-object v0
.end method

.method public getPackagePath()Ljava/lang/String;
    .locals 1

    .line 1079
    iget-object v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->packagePath:Ljava/lang/String;

    return-object v0
.end method

.method public getTargetSdkVersion()I
    .locals 1

    .line 1111
    iget v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->targetSdkVersion:I

    return v0
.end method

.method public getVersionCode()I
    .locals 1

    .line 1087
    iget v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->versionCode:I

    return v0
.end method

.method public getVersionName()Ljava/lang/String;
    .locals 1

    .line 1095
    iget-object v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->versionName:Ljava/lang/String;

    return-object v0
.end method

.method public isSystem()Z
    .locals 1

    .line 1055
    iget-boolean v0, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->isSystem:Z

    return v0
.end method

.method public setIcon(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    .line 1051
    iput-object p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->icon:Landroid/graphics/drawable/Drawable;

    return-void
.end method

.method public setMinSdkVersion(I)V
    .locals 0

    .line 1107
    iput p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->minSdkVersion:I

    return-void
.end method

.method public setName(Ljava/lang/String;)V
    .locals 0

    .line 1075
    iput-object p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->name:Ljava/lang/String;

    return-void
.end method

.method public setPackageName(Ljava/lang/String;)V
    .locals 0

    .line 1067
    iput-object p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->packageName:Ljava/lang/String;

    return-void
.end method

.method public setPackagePath(Ljava/lang/String;)V
    .locals 0

    .line 1083
    iput-object p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->packagePath:Ljava/lang/String;

    return-void
.end method

.method public setSystem(Z)V
    .locals 0

    .line 1059
    iput-boolean p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->isSystem:Z

    return-void
.end method

.method public setTargetSdkVersion(I)V
    .locals 0

    .line 1115
    iput p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->targetSdkVersion:I

    return-void
.end method

.method public setVersionCode(I)V
    .locals 0

    .line 1091
    iput p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->versionCode:I

    return-void
.end method

.method public setVersionName(Ljava/lang/String;)V
    .locals 0

    .line 1099
    iput-object p1, p0, Lcom/hcn/common/app/HAppUtils$AppInfo;->versionName:Ljava/lang/String;

    return-void
.end method

.method public toString()Ljava/lang/String;
    .locals 2

    .line 1133
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "{\n    pkg name: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1134
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app icon: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1135
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getIcon()Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app name: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1136
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app path: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1137
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getPackagePath()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app v name: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1138
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getVersionName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app v code: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1139
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getVersionCode()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app v min: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1140
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getMinSdkVersion()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    app v target: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1141
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->getTargetSdkVersion()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n    is system: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    .line 1142
    invoke-virtual {p0}, Lcom/hcn/common/app/HAppUtils$AppInfo;->isSystem()Z

    move-result v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Z)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n}"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method
