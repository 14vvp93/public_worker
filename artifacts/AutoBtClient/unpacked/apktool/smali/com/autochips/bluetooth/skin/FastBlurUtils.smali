.class public Lcom/autochips/bluetooth/skin/FastBlurUtils;
.super Ljava/lang/Object;
.source "FastBlurUtils.java"


# static fields
.field private static final BITMAP_ZOOM_IN_SCALE:F = 4.0f

.field private static final BITMAP_ZOOM_OUT_SCALE:F = 0.25f

.field private static final BLUR_RADIUS:I = 0x7


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 19
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static fastBlurBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
    .locals 37

    move/from16 v0, p1

    .line 161
    invoke-virtual/range {p0 .. p0}, Landroid/graphics/Bitmap;->getConfig()Landroid/graphics/Bitmap$Config;

    move-result-object v1

    const/4 v2, 0x1

    move-object/from16 v3, p0

    invoke-virtual {v3, v1, v2}, Landroid/graphics/Bitmap;->copy(Landroid/graphics/Bitmap$Config;Z)Landroid/graphics/Bitmap;

    move-result-object v1

    if-ge v0, v2, :cond_0

    const/4 v0, 0x0

    return-object v0

    .line 167
    :cond_0
    invoke-virtual {v1}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v11

    .line 168
    invoke-virtual {v1}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v12

    mul-int v13, v11, v12

    .line 170
    new-array v14, v13, [I

    const/4 v5, 0x0

    const/4 v7, 0x0

    const/4 v8, 0x0

    move-object v3, v1

    move-object v4, v14

    move v6, v11

    move v9, v11

    move v10, v12

    .line 171
    invoke-virtual/range {v3 .. v10}, Landroid/graphics/Bitmap;->getPixels([IIIIIII)V

    add-int/lit8 v3, v11, -0x1

    add-int/lit8 v4, v12, -0x1

    add-int v5, v0, v0

    add-int/2addr v5, v2

    .line 178
    new-array v6, v13, [I

    .line 179
    new-array v7, v13, [I

    .line 180
    new-array v8, v13, [I

    .line 182
    invoke-static {v11, v12}, Ljava/lang/Math;->max(II)I

    move-result v9

    new-array v9, v9, [I

    add-int/lit8 v10, v5, 0x1

    shr-int/2addr v10, v2

    mul-int/2addr v10, v10

    mul-int/lit16 v13, v10, 0x100

    .line 186
    new-array v15, v13, [I

    const/4 v2, 0x0

    :goto_0
    if-ge v2, v13, :cond_1

    .line 188
    div-int v17, v2, v10

    aput v17, v15, v2

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_1
    const/4 v2, 0x3

    const/4 v10, 0x2

    new-array v13, v10, [I

    const/16 v16, 0x1

    aput v2, v13, v16

    const/4 v2, 0x0

    aput v5, v13, v2

    .line 193
    const-class v2, I

    invoke-static {v2, v13}, Ljava/lang/reflect/Array;->newInstance(Ljava/lang/Class;[I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, [[I

    add-int/lit8 v13, v0, 0x1

    const/4 v10, 0x0

    const/16 v18, 0x0

    const/16 v19, 0x0

    :goto_1
    if-ge v10, v12, :cond_6

    move-object/from16 v20, v1

    neg-int v1, v0

    move/from16 v29, v12

    const/16 v21, 0x0

    const/16 v22, 0x0

    const/16 v23, 0x0

    const/16 v24, 0x0

    const/16 v25, 0x0

    const/16 v26, 0x0

    const/16 v27, 0x0

    const/16 v28, 0x0

    move v12, v1

    const/4 v1, 0x0

    :goto_2
    const v30, 0xff00

    const/high16 v31, 0xff0000

    if-gt v12, v0, :cond_3

    move/from16 v32, v4

    move-object/from16 v33, v9

    const/4 v4, 0x0

    .line 205
    invoke-static {v12, v4}, Ljava/lang/Math;->max(II)I

    move-result v9

    invoke-static {v3, v9}, Ljava/lang/Math;->min(II)I

    move-result v9

    add-int v9, v18, v9

    aget v9, v14, v9

    add-int v34, v12, v0

    .line 206
    aget-object v34, v2, v34

    and-int v31, v9, v31

    shr-int/lit8 v31, v31, 0x10

    .line 207
    aput v31, v34, v4

    and-int v30, v9, v30

    shr-int/lit8 v30, v30, 0x8

    const/16 v16, 0x1

    .line 208
    aput v30, v34, v16

    and-int/lit16 v9, v9, 0xff

    const/16 v17, 0x2

    .line 209
    aput v9, v34, v17

    .line 210
    invoke-static {v12}, Ljava/lang/Math;->abs(I)I

    move-result v9

    sub-int v9, v13, v9

    .line 211
    aget v30, v34, v4

    mul-int v30, v30, v9

    add-int v1, v1, v30

    .line 212
    aget v30, v34, v16

    mul-int v30, v30, v9

    add-int v21, v21, v30

    .line 213
    aget v30, v34, v17

    mul-int v30, v30, v9

    add-int v22, v22, v30

    if-lez v12, :cond_2

    .line 215
    aget v9, v34, v4

    add-int v26, v26, v9

    .line 216
    aget v9, v34, v16

    add-int v27, v27, v9

    .line 217
    aget v9, v34, v17

    add-int v28, v28, v9

    goto :goto_3

    .line 219
    :cond_2
    aget v9, v34, v4

    add-int v23, v23, v9

    .line 220
    aget v4, v34, v16

    add-int v24, v24, v4

    .line 221
    aget v4, v34, v17

    add-int v25, v25, v4

    :goto_3
    add-int/lit8 v12, v12, 0x1

    move/from16 v4, v32

    move-object/from16 v9, v33

    goto :goto_2

    :cond_3
    move/from16 v32, v4

    move-object/from16 v33, v9

    move v9, v0

    move v4, v1

    const/4 v1, 0x0

    :goto_4
    if-ge v1, v11, :cond_5

    .line 228
    aget v12, v15, v4

    aput v12, v6, v18

    .line 229
    aget v12, v15, v21

    aput v12, v7, v18

    .line 230
    aget v12, v15, v22

    aput v12, v8, v18

    sub-int v4, v4, v23

    sub-int v21, v21, v24

    sub-int v22, v22, v25

    sub-int v12, v9, v0

    add-int/2addr v12, v5

    .line 237
    rem-int/2addr v12, v5

    aget-object v12, v2, v12

    const/16 v34, 0x0

    .line 239
    aget v35, v12, v34

    sub-int v23, v23, v35

    const/16 v16, 0x1

    .line 240
    aget v34, v12, v16

    sub-int v24, v24, v34

    const/16 v17, 0x2

    .line 241
    aget v34, v12, v17

    sub-int v25, v25, v34

    if-nez v10, :cond_4

    add-int v34, v1, v0

    move-object/from16 v35, v15

    add-int/lit8 v15, v34, 0x1

    .line 244
    invoke-static {v15, v3}, Ljava/lang/Math;->min(II)I

    move-result v15

    aput v15, v33, v1

    goto :goto_5

    :cond_4
    move-object/from16 v35, v15

    .line 246
    :goto_5
    aget v15, v33, v1

    add-int v15, v19, v15

    aget v15, v14, v15

    and-int v34, v15, v31

    shr-int/lit8 v34, v34, 0x10

    const/16 v36, 0x0

    .line 248
    aput v34, v12, v36

    and-int v34, v15, v30

    shr-int/lit8 v34, v34, 0x8

    const/16 v16, 0x1

    .line 249
    aput v34, v12, v16

    and-int/lit16 v15, v15, 0xff

    const/16 v17, 0x2

    .line 250
    aput v15, v12, v17

    .line 252
    aget v15, v12, v36

    add-int v26, v26, v15

    .line 253
    aget v15, v12, v16

    add-int v27, v27, v15

    .line 254
    aget v12, v12, v17

    add-int v28, v28, v12

    add-int v4, v4, v26

    add-int v21, v21, v27

    add-int v22, v22, v28

    add-int/lit8 v9, v9, 0x1

    .line 260
    rem-int/2addr v9, v5

    .line 261
    rem-int v12, v9, v5

    aget-object v12, v2, v12

    const/4 v15, 0x0

    .line 263
    aget v34, v12, v15

    add-int v23, v23, v34

    const/16 v16, 0x1

    .line 264
    aget v34, v12, v16

    add-int v24, v24, v34

    const/16 v17, 0x2

    .line 265
    aget v34, v12, v17

    add-int v25, v25, v34

    .line 267
    aget v34, v12, v15

    sub-int v26, v26, v34

    .line 268
    aget v15, v12, v16

    sub-int v27, v27, v15

    .line 269
    aget v12, v12, v17

    sub-int v28, v28, v12

    add-int/lit8 v18, v18, 0x1

    add-int/lit8 v1, v1, 0x1

    move-object/from16 v15, v35

    goto/16 :goto_4

    :cond_5
    move-object/from16 v35, v15

    add-int v19, v19, v11

    add-int/lit8 v10, v10, 0x1

    move-object/from16 v1, v20

    move/from16 v12, v29

    move/from16 v4, v32

    move-object/from16 v9, v33

    goto/16 :goto_1

    :cond_6
    move-object/from16 v20, v1

    move/from16 v32, v4

    move-object/from16 v33, v9

    move/from16 v29, v12

    move-object/from16 v35, v15

    const/4 v1, 0x0

    :goto_6
    if-ge v1, v11, :cond_c

    neg-int v3, v0

    mul-int v4, v3, v11

    move/from16 v22, v5

    move-object/from16 v23, v14

    const/4 v9, 0x0

    const/4 v10, 0x0

    const/4 v12, 0x0

    const/4 v15, 0x0

    const/16 v18, 0x0

    const/16 v19, 0x0

    const/16 v21, 0x0

    move v5, v3

    move v14, v4

    const/4 v3, 0x0

    const/4 v4, 0x0

    :goto_7
    if-gt v5, v0, :cond_9

    move/from16 v24, v11

    const/4 v11, 0x0

    .line 279
    invoke-static {v11, v14}, Ljava/lang/Math;->max(II)I

    move-result v25

    add-int v25, v25, v1

    add-int v26, v5, v0

    .line 281
    aget-object v26, v2, v26

    .line 283
    aget v27, v6, v25

    aput v27, v26, v11

    .line 284
    aget v11, v7, v25

    const/16 v16, 0x1

    aput v11, v26, v16

    .line 285
    aget v11, v8, v25

    const/16 v17, 0x2

    aput v11, v26, v17

    .line 287
    invoke-static {v5}, Ljava/lang/Math;->abs(I)I

    move-result v11

    sub-int v11, v13, v11

    .line 289
    aget v27, v6, v25

    mul-int v27, v27, v11

    add-int v3, v3, v27

    .line 290
    aget v27, v7, v25

    mul-int v27, v27, v11

    add-int v4, v4, v27

    .line 291
    aget v25, v8, v25

    mul-int v25, v25, v11

    add-int v9, v9, v25

    if-lez v5, :cond_7

    const/4 v11, 0x0

    .line 294
    aget v25, v26, v11

    add-int v18, v18, v25

    const/16 v16, 0x1

    .line 295
    aget v25, v26, v16

    add-int v19, v19, v25

    const/16 v17, 0x2

    .line 296
    aget v25, v26, v17

    add-int v21, v21, v25

    goto :goto_8

    :cond_7
    const/4 v11, 0x0

    const/16 v16, 0x1

    const/16 v17, 0x2

    .line 298
    aget v25, v26, v11

    add-int v10, v10, v25

    .line 299
    aget v11, v26, v16

    add-int/2addr v12, v11

    .line 300
    aget v11, v26, v17

    add-int/2addr v15, v11

    :goto_8
    move/from16 v11, v32

    if-ge v5, v11, :cond_8

    add-int v14, v14, v24

    :cond_8
    add-int/lit8 v5, v5, 0x1

    move/from16 v32, v11

    move/from16 v11, v24

    goto :goto_7

    :cond_9
    move/from16 v24, v11

    move/from16 v11, v32

    move/from16 v26, v0

    move/from16 v25, v1

    move v5, v4

    move/from16 v14, v29

    move v4, v3

    const/4 v3, 0x0

    :goto_9
    if-ge v3, v14, :cond_b

    const/high16 v27, -0x1000000

    .line 310
    aget v28, v23, v25

    and-int v27, v28, v27

    aget v28, v35, v4

    shl-int/lit8 v28, v28, 0x10

    or-int v27, v27, v28

    aget v28, v35, v5

    shl-int/lit8 v28, v28, 0x8

    or-int v27, v27, v28

    aget v28, v35, v9

    or-int v27, v27, v28

    aput v27, v23, v25

    sub-int/2addr v4, v10

    sub-int/2addr v5, v12

    sub-int/2addr v9, v15

    sub-int v27, v26, v0

    add-int v27, v27, v22

    .line 317
    rem-int v27, v27, v22

    aget-object v27, v2, v27

    const/16 v28, 0x0

    .line 319
    aget v29, v27, v28

    sub-int v10, v10, v29

    const/16 v16, 0x1

    .line 320
    aget v28, v27, v16

    sub-int v12, v12, v28

    const/16 v17, 0x2

    .line 321
    aget v28, v27, v17

    sub-int v15, v15, v28

    if-nez v1, :cond_a

    add-int v0, v3, v13

    .line 324
    invoke-static {v0, v11}, Ljava/lang/Math;->min(II)I

    move-result v0

    mul-int v0, v0, v24

    aput v0, v33, v3

    .line 326
    :cond_a
    aget v0, v33, v3

    add-int/2addr v0, v1

    .line 328
    aget v28, v6, v0

    const/16 v29, 0x0

    aput v28, v27, v29

    .line 329
    aget v28, v7, v0

    const/16 v16, 0x1

    aput v28, v27, v16

    .line 330
    aget v0, v8, v0

    const/16 v17, 0x2

    aput v0, v27, v17

    .line 332
    aget v0, v27, v29

    add-int v18, v18, v0

    .line 333
    aget v0, v27, v16

    add-int v19, v19, v0

    .line 334
    aget v0, v27, v17

    add-int v21, v21, v0

    add-int v4, v4, v18

    add-int v5, v5, v19

    add-int v9, v9, v21

    add-int/lit8 v26, v26, 0x1

    .line 340
    rem-int v26, v26, v22

    .line 341
    aget-object v0, v2, v26

    const/16 v27, 0x0

    .line 343
    aget v28, v0, v27

    add-int v10, v10, v28

    const/16 v16, 0x1

    .line 344
    aget v28, v0, v16

    add-int v12, v12, v28

    const/16 v17, 0x2

    .line 345
    aget v28, v0, v17

    add-int v15, v15, v28

    .line 347
    aget v28, v0, v27

    sub-int v18, v18, v28

    .line 348
    aget v28, v0, v16

    sub-int v19, v19, v28

    .line 349
    aget v0, v0, v17

    sub-int v21, v21, v0

    add-int v25, v25, v24

    add-int/lit8 v3, v3, 0x1

    move/from16 v0, p1

    goto/16 :goto_9

    :cond_b
    const/16 v16, 0x1

    const/16 v17, 0x2

    const/16 v27, 0x0

    add-int/lit8 v1, v1, 0x1

    move/from16 v0, p1

    move/from16 v32, v11

    move/from16 v29, v14

    move/from16 v5, v22

    move-object/from16 v14, v23

    move/from16 v11, v24

    goto/16 :goto_6

    :cond_c
    move/from16 v24, v11

    move-object/from16 v23, v14

    move/from16 v14, v29

    const/4 v5, 0x0

    const/4 v7, 0x0

    const/4 v8, 0x0

    move-object/from16 v3, v20

    move-object/from16 v4, v23

    move/from16 v6, v24

    move/from16 v9, v24

    move v10, v14

    .line 355
    invoke-virtual/range {v3 .. v10}, Landroid/graphics/Bitmap;->setPixels([IIIIIII)V

    return-object v20
.end method

.method public static gaussianBlur(Landroid/content/Context;Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
    .locals 3

    .line 139
    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x11

    if-ge v0, v1, :cond_0

    const/4 p0, 0x0

    return-object p0

    .line 142
    :cond_0
    invoke-static {p0}, Landroid/renderscript/RenderScript;->create(Landroid/content/Context;)Landroid/renderscript/RenderScript;

    move-result-object p0

    .line 143
    invoke-static {p0, p1}, Landroid/renderscript/Allocation;->createFromBitmap(Landroid/renderscript/RenderScript;Landroid/graphics/Bitmap;)Landroid/renderscript/Allocation;

    move-result-object v0

    .line 144
    invoke-virtual {v0}, Landroid/renderscript/Allocation;->getType()Landroid/renderscript/Type;

    move-result-object v1

    invoke-static {p0, v1}, Landroid/renderscript/Allocation;->createTyped(Landroid/renderscript/RenderScript;Landroid/renderscript/Type;)Landroid/renderscript/Allocation;

    move-result-object v1

    .line 145
    invoke-static {p0}, Landroid/renderscript/Element;->U8_4(Landroid/renderscript/RenderScript;)Landroid/renderscript/Element;

    move-result-object v2

    invoke-static {p0, v2}, Landroid/renderscript/ScriptIntrinsicBlur;->create(Landroid/renderscript/RenderScript;Landroid/renderscript/Element;)Landroid/renderscript/ScriptIntrinsicBlur;

    move-result-object p0

    int-to-float p2, p2

    .line 146
    invoke-virtual {p0, p2}, Landroid/renderscript/ScriptIntrinsicBlur;->setRadius(F)V

    .line 147
    invoke-virtual {p0, v0}, Landroid/renderscript/ScriptIntrinsicBlur;->setInput(Landroid/renderscript/Allocation;)V

    .line 148
    invoke-virtual {p0, v1}, Landroid/renderscript/ScriptIntrinsicBlur;->forEach(Landroid/renderscript/Allocation;)V

    .line 149
    invoke-virtual {v1, p1}, Landroid/renderscript/Allocation;->copyTo(Landroid/graphics/Bitmap;)V

    return-object p1
.end method

.method public static getBlurBackgroundDrawer(Landroid/app/Activity;)Landroid/graphics/Bitmap;
    .locals 5

    .line 34
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    .line 35
    invoke-static {p0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->takeScreenShot(Landroid/app/Activity;)Landroid/graphics/Bitmap;

    move-result-object v2

    .line 36
    invoke-static {p0, v2}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->startBlurBackground(Landroid/content/Context;Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object p0

    .line 37
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "=====blur time===:"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v3

    sub-long/2addr v3, v0

    invoke-virtual {v2, v3, v4}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "FastBlurUtility"

    invoke-static {v1, v0}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    return-object p0
.end method

.method public static startBlurBackground(Landroid/content/Context;Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 1

    .line 101
    invoke-static {p1}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->zoomOut(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object p1

    const/4 v0, 0x7

    invoke-static {p0, p1, v0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->gaussianBlur(Landroid/content/Context;Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object p0

    .line 102
    invoke-static {p0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->zoomIn(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method

.method public static startBlurBackground(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 1

    .line 89
    invoke-static {p0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->zoomOut(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object p0

    const/4 v0, 0x7

    invoke-static {p0, v0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->fastBlurBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object p0

    .line 90
    invoke-static {p0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->zoomIn(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method

.method public static startBlurBitmap(Landroid/content/Context;Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 1

    const/4 v0, 0x7

    .line 79
    invoke-static {p0, p1, v0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->gaussianBlur(Landroid/content/Context;Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method

.method public static startBlurBitmap(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 1

    const/4 v0, 0x7

    .line 67
    invoke-static {p0, v0}, Lcom/autochips/bluetooth/skin/FastBlurUtils;->fastBlurBitmap(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method

.method public static takeScreenShot(Landroid/app/Activity;)Landroid/graphics/Bitmap;
    .locals 4

    .line 48
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/Window;->getDecorView()Landroid/view/View;

    move-result-object v0

    const/4 v1, 0x1

    .line 49
    invoke-virtual {v0, v1}, Landroid/view/View;->setDrawingCacheEnabled(Z)V

    .line 50
    invoke-virtual {v0}, Landroid/view/View;->buildDrawingCache()V

    .line 51
    invoke-virtual {v0}, Landroid/view/View;->getDrawingCache()Landroid/graphics/Bitmap;

    move-result-object v1

    .line 53
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v2

    invoke-virtual {v2}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v2

    iget v2, v2, Landroid/util/DisplayMetrics;->widthPixels:I

    .line 54
    invoke-virtual {p0}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object p0

    invoke-virtual {p0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p0

    iget p0, p0, Landroid/util/DisplayMetrics;->heightPixels:I

    const/4 v3, 0x0

    .line 55
    invoke-static {v1, v3, v3, v2, p0}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;

    move-result-object p0

    .line 56
    invoke-virtual {v0}, Landroid/view/View;->destroyDrawingCache()V

    return-object p0
.end method

.method public static zoomIn(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 7

    .line 112
    new-instance v5, Landroid/graphics/Matrix;

    invoke-direct {v5}, Landroid/graphics/Matrix;-><init>()V

    const/high16 v0, 0x40800000    # 4.0f

    .line 113
    invoke-virtual {v5, v0, v0}, Landroid/graphics/Matrix;->postScale(FF)Z

    .line 114
    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v6, 0x1

    move-object v0, p0

    invoke-static/range {v0 .. v6}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method

.method public static zoomOut(Landroid/graphics/Bitmap;)Landroid/graphics/Bitmap;
    .locals 7

    .line 124
    new-instance v5, Landroid/graphics/Matrix;

    invoke-direct {v5}, Landroid/graphics/Matrix;-><init>()V

    const/high16 v0, 0x3e800000    # 0.25f

    .line 125
    invoke-virtual {v5, v0, v0}, Landroid/graphics/Matrix;->postScale(FF)Z

    .line 126
    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v3

    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v4

    const/4 v1, 0x0

    const/4 v2, 0x0

    const/4 v6, 0x1

    move-object v0, p0

    invoke-static/range {v0 .. v6}, Landroid/graphics/Bitmap;->createBitmap(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;

    move-result-object p0

    return-object p0
.end method
