.class Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
.super Ljava/lang/Object;
.source "Tweener.java"


# static fields
.field private static mCleanupListener:Landroid/animation/Animator$AnimatorListener;

.field private static sTweens:Ljava/util/HashMap;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/HashMap<",
            "Ljava/lang/Object;",
            "Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;",
            ">;"
        }
    .end annotation
.end field


# instance fields
.field animator:Landroid/animation/ObjectAnimator;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 38
    new-instance v0, Ljava/util/HashMap;

    invoke-direct {v0}, Ljava/util/HashMap;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    .line 141
    new-instance v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener$1;

    invoke-direct {v0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener$1;-><init>()V

    sput-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->mCleanupListener:Landroid/animation/Animator$AnimatorListener;

    return-void
.end method

.method public constructor <init>(Landroid/animation/ObjectAnimator;)V
    .locals 0
    .param p1, "anim"    # Landroid/animation/ObjectAnimator;

    .line 40
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 41
    iput-object p1, p0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    .line 42
    return-void
.end method

.method static synthetic access$000(Landroid/animation/Animator;)V
    .locals 0
    .param p0, "x0"    # Landroid/animation/Animator;

    .line 33
    invoke-static {p0}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->remove(Landroid/animation/Animator;)V

    return-void
.end method

.method private static remove(Landroid/animation/Animator;)V
    .locals 3
    .param p0, "animator"    # Landroid/animation/Animator;

    .line 45
    sget-object v0, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v0}, Ljava/util/HashMap;->entrySet()Ljava/util/Set;

    move-result-object v0

    invoke-interface {v0}, Ljava/util/Set;->iterator()Ljava/util/Iterator;

    move-result-object v0

    .line 46
    .local v0, "iter":Ljava/util/Iterator;, "Ljava/util/Iterator<Ljava/util/Map$Entry<Ljava/lang/Object;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;>;>;"
    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    .line 47
    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map$Entry;

    .line 48
    .local v1, "entry":Ljava/util/Map$Entry;, "Ljava/util/Map$Entry<Ljava/lang/Object;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;>;"
    invoke-interface {v1}, Ljava/util/Map$Entry;->getValue()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v2, v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    if-ne v2, p0, :cond_0

    .line 51
    invoke-interface {v0}, Ljava/util/Iterator;->remove()V

    .line 52
    goto :goto_1

    .line 54
    .end local v1    # "entry":Ljava/util/Map$Entry;, "Ljava/util/Map$Entry<Ljava/lang/Object;Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;>;"
    :cond_0
    goto :goto_0

    .line 55
    :cond_1
    :goto_1
    return-void
.end method

.method private static varargs replace(Ljava/util/ArrayList;[Ljava/lang/Object;)V
    .locals 6
    .param p1, "args"    # [Ljava/lang/Object;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList<",
            "Landroid/animation/PropertyValuesHolder;",
            ">;[",
            "Ljava/lang/Object;",
            ")V"
        }
    .end annotation

    .line 165
    .local p0, "props":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/animation/PropertyValuesHolder;>;"
    array-length v0, p1

    const/4 v1, 0x0

    :goto_0
    if-ge v1, v0, :cond_2

    aget-object v2, p1, v1

    .line 166
    .local v2, "killobject":Ljava/lang/Object;
    sget-object v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v3, v2}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 167
    .local v3, "tween":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    if-eqz v3, :cond_1

    .line 168
    iget-object v4, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    invoke-virtual {v4}, Landroid/animation/ObjectAnimator;->cancel()V

    .line 169
    if-eqz p0, :cond_0

    .line 170
    iget-object v4, v3, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    .line 171
    invoke-virtual {p0}, Ljava/util/ArrayList;->size()I

    move-result v5

    new-array v5, v5, [Landroid/animation/PropertyValuesHolder;

    invoke-virtual {p0, v5}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v5

    check-cast v5, [Landroid/animation/PropertyValuesHolder;

    .line 170
    invoke-virtual {v4, v5}, Landroid/animation/ObjectAnimator;->setValues([Landroid/animation/PropertyValuesHolder;)V

    goto :goto_1

    .line 173
    :cond_0
    sget-object v4, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v4, v3}, Ljava/util/HashMap;->remove(Ljava/lang/Object;)Ljava/lang/Object;

    .line 165
    .end local v2    # "killobject":Ljava/lang/Object;
    .end local v3    # "tween":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    :cond_1
    :goto_1
    add-int/lit8 v1, v1, 0x1

    goto :goto_0

    .line 177
    :cond_2
    return-void
.end method

.method public static varargs to(Ljava/lang/Object;J[Ljava/lang/Object;)Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    .locals 16
    .param p0, "object"    # Ljava/lang/Object;
    .param p1, "duration"    # J
    .param p3, "vars"    # [Ljava/lang/Object;

    move-object/from16 v0, p0

    .line 58
    move-object/from16 v1, p3

    const-wide/16 v2, 0x0

    .line 59
    .local v2, "delay":J
    const/4 v4, 0x0

    .line 60
    .local v4, "updateListener":Landroid/animation/ValueAnimator$AnimatorUpdateListener;
    const/4 v5, 0x0

    .line 61
    .local v5, "listener":Landroid/animation/Animator$AnimatorListener;
    const/4 v6, 0x0

    .line 64
    .local v6, "interpolator":Landroid/animation/TimeInterpolator;
    new-instance v7, Ljava/util/ArrayList;

    array-length v8, v1

    const/4 v9, 0x2

    div-int/2addr v8, v9

    invoke-direct {v7, v8}, Ljava/util/ArrayList;-><init>(I)V

    .line 65
    .local v7, "props":Ljava/util/ArrayList;, "Ljava/util/ArrayList<Landroid/animation/PropertyValuesHolder;>;"
    const/4 v8, 0x0

    move-wide v10, v2

    move v2, v8

    .local v2, "i":I
    .local v10, "delay":J
    :goto_0
    array-length v3, v1

    const/4 v12, 0x1

    if-ge v2, v3, :cond_c

    .line 66
    aget-object v3, v1, v2

    instance-of v3, v3, Ljava/lang/String;

    if-eqz v3, :cond_b

    .line 69
    aget-object v3, v1, v2

    check-cast v3, Ljava/lang/String;

    .line 70
    .local v3, "key":Ljava/lang/String;
    add-int/lit8 v13, v2, 0x1

    aget-object v13, v1, v13

    .line 72
    .local v13, "value":Ljava/lang/Object;
    const-string v14, "simultaneousTween"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_0

    .end local v3    # "key":Ljava/lang/String;
    .end local v13    # "value":Ljava/lang/Object;
    goto/16 :goto_3

    .line 74
    .restart local v3    # "key":Ljava/lang/String;
    .restart local v13    # "value":Ljava/lang/Object;
    :cond_0
    const-string v14, "ease"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_1

    .line 75
    move-object v6, v13

    check-cast v6, Landroid/animation/TimeInterpolator;

    goto/16 :goto_3

    .line 76
    :cond_1
    const-string v14, "onUpdate"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-nez v14, :cond_a

    const-string v14, "onUpdateListener"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_2

    goto/16 :goto_2

    .line 78
    :cond_2
    const-string v14, "onComplete"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-nez v14, :cond_9

    const-string v14, "onCompleteListener"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_3

    goto/16 :goto_1

    .line 80
    :cond_3
    const-string v14, "delay"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_4

    .line 81
    move-object v12, v13

    check-cast v12, Ljava/lang/Number;

    invoke-virtual {v12}, Ljava/lang/Number;->longValue()J

    move-result-wide v10

    goto/16 :goto_3

    .line 82
    :cond_4
    const-string v14, "syncWith"

    invoke-virtual {v14, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v14

    if-eqz v14, :cond_5

    .end local v3    # "key":Ljava/lang/String;
    .end local v13    # "value":Ljava/lang/Object;
    goto/16 :goto_3

    .line 84
    .restart local v3    # "key":Ljava/lang/String;
    .restart local v13    # "value":Ljava/lang/Object;
    :cond_5
    instance-of v14, v13, [F

    if-eqz v14, :cond_6

    .line 85
    new-array v14, v9, [F

    move-object v15, v13

    check-cast v15, [F

    aget v15, v15, v8

    aput v15, v14, v8

    move-object v15, v13

    check-cast v15, [F

    aget v15, v15, v12

    aput v15, v14, v12

    invoke-static {v3, v14}, Landroid/animation/PropertyValuesHolder;->ofFloat(Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;

    move-result-object v12

    invoke-virtual {v7, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_3

    .line 87
    :cond_6
    instance-of v14, v13, [I

    if-eqz v14, :cond_7

    .line 88
    new-array v14, v9, [I

    move-object v15, v13

    check-cast v15, [I

    aget v15, v15, v8

    aput v15, v14, v8

    move-object v15, v13

    check-cast v15, [I

    aget v15, v15, v12

    aput v15, v14, v12

    invoke-static {v3, v14}, Landroid/animation/PropertyValuesHolder;->ofInt(Ljava/lang/String;[I)Landroid/animation/PropertyValuesHolder;

    move-result-object v12

    invoke-virtual {v7, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    goto :goto_3

    .line 90
    :cond_7
    instance-of v14, v13, Ljava/lang/Number;

    if-eqz v14, :cond_8

    .line 91
    move-object v14, v13

    check-cast v14, Ljava/lang/Number;

    invoke-virtual {v14}, Ljava/lang/Number;->floatValue()F

    move-result v14

    .line 92
    .local v14, "floatValue":F
    new-array v12, v12, [F

    aput v14, v12, v8

    invoke-static {v3, v12}, Landroid/animation/PropertyValuesHolder;->ofFloat(Ljava/lang/String;[F)Landroid/animation/PropertyValuesHolder;

    move-result-object v12

    invoke-virtual {v7, v12}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 93
    .end local v14    # "floatValue":F
    goto :goto_3

    .line 94
    :cond_8
    new-instance v8, Ljava/lang/IllegalArgumentException;

    new-instance v9, Ljava/lang/StringBuilder;

    invoke-direct {v9}, Ljava/lang/StringBuilder;-><init>()V

    const-string v12, "Bad argument for key \""

    invoke-virtual {v9, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v9, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v12, "\" with value "

    invoke-virtual {v9, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 95
    invoke-virtual {v13}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    move-result-object v12

    invoke-virtual {v9, v12}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v9}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v9

    invoke-direct {v8, v9}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v8

    .line 79
    :cond_9
    :goto_1
    move-object v5, v13

    check-cast v5, Landroid/animation/Animator$AnimatorListener;

    goto :goto_3

    .line 77
    :cond_a
    :goto_2
    move-object v4, v13

    check-cast v4, Landroid/animation/ValueAnimator$AnimatorUpdateListener;

    .line 65
    .end local v3    # "key":Ljava/lang/String;
    .end local v13    # "value":Ljava/lang/Object;
    :goto_3
    add-int/lit8 v2, v2, 0x2

    goto/16 :goto_0

    .line 67
    :cond_b
    new-instance v3, Ljava/lang/IllegalArgumentException;

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    const-string v9, "Key must be a string: "

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    aget-object v9, v1, v2

    invoke-virtual {v8, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v8}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    invoke-direct {v3, v8}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw v3

    .line 100
    .end local v2    # "i":I
    :cond_c
    sget-object v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v2, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    .line 101
    .local v2, "tween":Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;
    const/4 v3, 0x0

    .line 102
    .local v3, "anim":Landroid/animation/ObjectAnimator;
    if-nez v2, :cond_d

    .line 103
    nop

    .line 104
    invoke-virtual {v7}, Ljava/util/ArrayList;->size()I

    move-result v8

    new-array v8, v8, [Landroid/animation/PropertyValuesHolder;

    invoke-virtual {v7, v8}, Ljava/util/ArrayList;->toArray([Ljava/lang/Object;)[Ljava/lang/Object;

    move-result-object v8

    check-cast v8, [Landroid/animation/PropertyValuesHolder;

    .line 103
    invoke-static {v0, v8}, Landroid/animation/ObjectAnimator;->ofPropertyValuesHolder(Ljava/lang/Object;[Landroid/animation/PropertyValuesHolder;)Landroid/animation/ObjectAnimator;

    move-result-object v3

    .line 105
    new-instance v8, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    invoke-direct {v8, v3}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;-><init>(Landroid/animation/ObjectAnimator;)V

    move-object v2, v8

    .line 106
    sget-object v8, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v8, v0, v2}, Ljava/util/HashMap;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    goto :goto_4

    .line 109
    :cond_d
    sget-object v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->sTweens:Ljava/util/HashMap;

    invoke-virtual {v9, v0}, Ljava/util/HashMap;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v9

    check-cast v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;

    iget-object v3, v9, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->animator:Landroid/animation/ObjectAnimator;

    .line 110
    new-array v9, v12, [Ljava/lang/Object;

    aput-object v0, v9, v8

    invoke-static {v7, v9}, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->replace(Ljava/util/ArrayList;[Ljava/lang/Object;)V

    .line 113
    :goto_4
    if-eqz v6, :cond_e

    .line 114
    invoke-virtual {v3, v6}, Landroid/animation/ObjectAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    .line 118
    :cond_e
    invoke-virtual {v3, v10, v11}, Landroid/animation/ObjectAnimator;->setStartDelay(J)V

    .line 119
    move-wide/from16 v8, p1

    invoke-virtual {v3, v8, v9}, Landroid/animation/ObjectAnimator;->setDuration(J)Landroid/animation/ObjectAnimator;

    .line 120
    if-eqz v4, :cond_f

    .line 121
    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->removeAllUpdateListeners()V

    .line 122
    invoke-virtual {v3, v4}, Landroid/animation/ObjectAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    .line 124
    :cond_f
    if-eqz v5, :cond_10

    .line 125
    invoke-virtual {v3}, Landroid/animation/ObjectAnimator;->removeAllListeners()V

    .line 126
    invoke-virtual {v3, v5}, Landroid/animation/ObjectAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 128
    :cond_10
    sget-object v12, Lcom/mediatek/keyguard/PowerOffAlarm/multiwaveview/Tweener;->mCleanupListener:Landroid/animation/Animator$AnimatorListener;

    invoke-virtual {v3, v12}, Landroid/animation/ObjectAnimator;->addListener(Landroid/animation/Animator$AnimatorListener;)V

    .line 130
    return-object v2
.end method
