.class public Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;
.super Ljava/lang/Object;
.source "SPHelper.java"


# instance fields
.field private editor:Landroid/content/SharedPreferences$Editor;

.field private sharedPreferences:Landroid/content/SharedPreferences;


# direct methods
.method public constructor <init>(Landroid/content/Context;Ljava/lang/String;)V
    .locals 1
    .param p1, "context"    # Landroid/content/Context;
    .param p2, "FILE_NAME"    # Ljava/lang/String;

    .line 13
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 14
    const/4 v0, 0x0

    invoke-virtual {p1, p2, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->sharedPreferences:Landroid/content/SharedPreferences;

    .line 16
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->sharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->edit()Landroid/content/SharedPreferences$Editor;

    move-result-object v0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    .line 17
    return-void
.end method


# virtual methods
.method public apply()V
    .locals 1

    .line 78
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->apply()V

    .line 79
    return-void
.end method

.method public clear()Landroid/content/SharedPreferences$Editor;
    .locals 1

    .line 70
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    invoke-interface {v0}, Landroid/content/SharedPreferences$Editor;->clear()Landroid/content/SharedPreferences$Editor;

    .line 71
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    return-object v0
.end method

.method public getAll()Ljava/util/Map;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "*>;"
        }
    .end annotation

    .line 99
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->sharedPreferences:Landroid/content/SharedPreferences;

    invoke-interface {v0}, Landroid/content/SharedPreferences;->getAll()Ljava/util/Map;

    move-result-object v0

    return-object v0
.end method

.method public put(Ljava/lang/String;Ljava/lang/Object;)Landroid/content/SharedPreferences$Editor;
    .locals 3
    .param p1, "key"    # Ljava/lang/String;
    .param p2, "object"    # Ljava/lang/Object;

    .line 23
    instance-of v0, p2, Ljava/lang/String;

    if-eqz v0, :cond_0

    .line 24
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    move-object v1, p2

    check-cast v1, Ljava/lang/String;

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 25
    :cond_0
    instance-of v0, p2, Ljava/lang/Integer;

    if-eqz v0, :cond_1

    .line 26
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    move-object v1, p2

    check-cast v1, Ljava/lang/Integer;

    invoke-virtual {v1}, Ljava/lang/Integer;->intValue()I

    move-result v1

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences$Editor;->putInt(Ljava/lang/String;I)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 27
    :cond_1
    instance-of v0, p2, Ljava/lang/Boolean;

    if-eqz v0, :cond_2

    .line 28
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    move-object v1, p2

    check-cast v1, Ljava/lang/Boolean;

    invoke-virtual {v1}, Ljava/lang/Boolean;->booleanValue()Z

    move-result v1

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences$Editor;->putBoolean(Ljava/lang/String;Z)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 29
    :cond_2
    instance-of v0, p2, Ljava/lang/Float;

    if-eqz v0, :cond_3

    .line 30
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    move-object v1, p2

    check-cast v1, Ljava/lang/Float;

    invoke-virtual {v1}, Ljava/lang/Float;->floatValue()F

    move-result v1

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences$Editor;->putFloat(Ljava/lang/String;F)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 31
    :cond_3
    instance-of v0, p2, Ljava/lang/Long;

    if-eqz v0, :cond_4

    .line 32
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    move-object v1, p2

    check-cast v1, Ljava/lang/Long;

    invoke-virtual {v1}, Ljava/lang/Long;->longValue()J

    move-result-wide v1

    invoke-interface {v0, p1, v1, v2}, Landroid/content/SharedPreferences$Editor;->putLong(Ljava/lang/String;J)Landroid/content/SharedPreferences$Editor;

    goto :goto_0

    .line 34
    :cond_4
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    invoke-virtual {p2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, p1, v1}, Landroid/content/SharedPreferences$Editor;->putString(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 36
    :goto_0
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    return-object v0
.end method

.method public remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;
    .locals 1
    .param p1, "key"    # Ljava/lang/String;

    .line 62
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    invoke-interface {v0, p1}, Landroid/content/SharedPreferences$Editor;->remove(Ljava/lang/String;)Landroid/content/SharedPreferences$Editor;

    .line 63
    iget-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/SPHelper;->editor:Landroid/content/SharedPreferences$Editor;

    return-object v0
.end method
