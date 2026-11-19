.class public Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;
.super Ljava/lang/Object;
.source "XmlDeal.java"


# instance fields
.field public appItemList:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>(Ljava/io/InputStream;)V
    .locals 1
    .param p1, "is"    # Ljava/io/InputStream;

    .line 44
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 42
    const/4 v0, 0x0

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    .line 45
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    .line 46
    invoke-direct {p0, p1}, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->ParseXml(Ljava/io/InputStream;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 49
    return-void

    .line 47
    :cond_0
    new-instance v0, Ljava/lang/RuntimeException;

    invoke-direct {v0}, Ljava/lang/RuntimeException;-><init>()V

    throw v0
.end method

.method private ParseXml(Ljava/io/InputStream;)Z
    .locals 19
    .param p1, "is"    # Ljava/io/InputStream;

    move-object/from16 v1, p0

    .line 53
    const/4 v2, 0x0

    .line 54
    .local v2, "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    const/4 v3, 0x0

    .line 55
    .local v3, "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    const/4 v0, 0x0

    move-object v4, v0

    .line 57
    .local v4, "doc":Lorg/w3c/dom/Document;
    :try_start_0
    invoke-static {}, Ljavax/xml/parsers/DocumentBuilderFactory;->newInstance()Ljavax/xml/parsers/DocumentBuilderFactory;

    move-result-object v0
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_4
    .catchall {:try_start_0 .. :try_end_0} :catchall_4

    move-object v2, v0

    .line 58
    :try_start_1
    invoke-virtual {v2}, Ljavax/xml/parsers/DocumentBuilderFactory;->newDocumentBuilder()Ljavax/xml/parsers/DocumentBuilder;

    move-result-object v0
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_3
    .catchall {:try_start_1 .. :try_end_1} :catchall_3

    move-object v3, v0

    .line 60
    move-object/from16 v6, p1

    :try_start_2
    invoke-virtual {v3, v6}, Ljavax/xml/parsers/DocumentBuilder;->parse(Ljava/io/InputStream;)Lorg/w3c/dom/Document;

    move-result-object v0

    move-object v4, v0

    .line 61
    const-string v0, "APPItemList"

    invoke-interface {v4, v0}, Lorg/w3c/dom/Document;->getElementsByTagName(Ljava/lang/String;)Lorg/w3c/dom/NodeList;

    move-result-object v0

    .line 63
    .local v0, "nodeList":Lorg/w3c/dom/NodeList;
    invoke-interface {v0}, Lorg/w3c/dom/NodeList;->getLength()I

    move-result v7

    .line 64
    .local v7, "length":I
    const/4 v8, 0x0

    .local v8, "i":I
    :goto_0
    const/4 v9, 0x1

    if-ge v8, v7, :cond_7

    .line 65
    invoke-interface {v0, v8}, Lorg/w3c/dom/NodeList;->item(I)Lorg/w3c/dom/Node;

    move-result-object v10

    .line 67
    .local v10, "item":Lorg/w3c/dom/Node;
    invoke-interface {v10}, Lorg/w3c/dom/Node;->getFirstChild()Lorg/w3c/dom/Node;

    move-result-object v11

    .local v11, "node":Lorg/w3c/dom/Node;
    :goto_1
    if-eqz v11, :cond_6

    .line 68
    invoke-interface {v11}, Lorg/w3c/dom/Node;->getNodeType()S

    move-result v12

    if-ne v12, v9, :cond_4

    .line 69
    const-string v12, ""

    .line 70
    .local v12, "shortName":Ljava/lang/String;
    const-string v13, ""

    .line 71
    .local v13, "packageName":Ljava/lang/String;
    const-string v14, ""

    .line 72
    .local v14, "className":Ljava/lang/String;
    move-object v15, v14

    move-object v14, v13

    move-object v13, v12

    const/4 v12, 0x0

    .local v12, "j":I
    .local v13, "shortName":Ljava/lang/String;
    .local v14, "packageName":Ljava/lang/String;
    .local v15, "className":Ljava/lang/String;
    :goto_2
    invoke-interface {v11}, Lorg/w3c/dom/Node;->getAttributes()Lorg/w3c/dom/NamedNodeMap;

    move-result-object v9

    invoke-interface {v9}, Lorg/w3c/dom/NamedNodeMap;->getLength()I

    move-result v9

    if-ge v12, v9, :cond_3

    .line 73
    invoke-interface {v11}, Lorg/w3c/dom/Node;->getAttributes()Lorg/w3c/dom/NamedNodeMap;

    move-result-object v9

    invoke-interface {v9, v12}, Lorg/w3c/dom/NamedNodeMap;->item(I)Lorg/w3c/dom/Node;

    move-result-object v9

    invoke-interface {v9}, Lorg/w3c/dom/Node;->getNodeName()Ljava/lang/String;

    move-result-object v9

    .line 74
    .local v9, "attrName":Ljava/lang/String;
    invoke-interface {v11}, Lorg/w3c/dom/Node;->getAttributes()Lorg/w3c/dom/NamedNodeMap;

    move-result-object v5

    invoke-interface {v5, v12}, Lorg/w3c/dom/NamedNodeMap;->item(I)Lorg/w3c/dom/Node;

    move-result-object v5

    invoke-interface {v5}, Lorg/w3c/dom/Node;->getNodeValue()Ljava/lang/String;

    move-result-object v5

    .line 75
    .local v5, "attrValue":Ljava/lang/String;
    move-object/from16 v16, v0

    const-string v0, "XmlDeal"
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    .end local v0    # "nodeList":Lorg/w3c/dom/NodeList;
    .local v16, "nodeList":Lorg/w3c/dom/NodeList;
    move-object/from16 v17, v2

    :try_start_3
    new-instance v2, Ljava/lang/StringBuilder;

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .local v17, "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_0

    move-object/from16 v18, v3

    :try_start_4
    const-string v3, " attrName:"

    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .local v18, "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v9}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v3, " ---attrValue :"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v0, v2}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 76
    const-string v0, "shortName"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    .line 77
    move-object v0, v5

    .line 72
    .end local v13    # "shortName":Ljava/lang/String;
    .local v0, "shortName":Ljava/lang/String;
    move-object v13, v0

    goto :goto_3

    .line 78
    .end local v0    # "shortName":Ljava/lang/String;
    .restart local v13    # "shortName":Ljava/lang/String;
    :cond_0
    const-string v0, "packageName"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_1

    .line 79
    move-object v0, v5

    .line 72
    .end local v14    # "packageName":Ljava/lang/String;
    .local v0, "packageName":Ljava/lang/String;
    move-object v14, v0

    goto :goto_3

    .line 81
    .end local v0    # "packageName":Ljava/lang/String;
    .restart local v14    # "packageName":Ljava/lang/String;
    :cond_1
    const-string v0, "className"

    invoke-virtual {v9, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    .line 82
    move-object v0, v5

    .line 72
    .end local v5    # "attrValue":Ljava/lang/String;
    .end local v9    # "attrName":Ljava/lang/String;
    .end local v15    # "className":Ljava/lang/String;
    .local v0, "className":Ljava/lang/String;
    move-object v15, v0

    .end local v0    # "className":Ljava/lang/String;
    .restart local v15    # "className":Ljava/lang/String;
    :cond_2
    :goto_3
    add-int/lit8 v12, v12, 0x1

    move-object/from16 v0, v16

    move-object/from16 v2, v17

    move-object/from16 v3, v18

    const/4 v9, 0x1

    goto :goto_2

    .line 100
    .end local v7    # "length":I
    .end local v8    # "i":I
    .end local v10    # "item":Lorg/w3c/dom/Node;
    .end local v11    # "node":Lorg/w3c/dom/Node;
    .end local v12    # "j":I
    .end local v13    # "shortName":Ljava/lang/String;
    .end local v14    # "packageName":Ljava/lang/String;
    .end local v15    # "className":Ljava/lang/String;
    .end local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :catchall_0
    move-exception v0

    move-object/from16 v18, v3

    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    goto/16 :goto_7

    .line 95
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :catch_0
    move-exception v0

    move-object/from16 v18, v3

    move-object/from16 v2, v17

    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    goto/16 :goto_6

    .line 86
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .local v0, "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v7    # "length":I
    .restart local v8    # "i":I
    .restart local v10    # "item":Lorg/w3c/dom/Node;
    .restart local v11    # "node":Lorg/w3c/dom/Node;
    .restart local v13    # "shortName":Ljava/lang/String;
    .restart local v14    # "packageName":Ljava/lang/String;
    .restart local v15    # "className":Ljava/lang/String;
    :cond_3
    move-object/from16 v16, v0

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v0    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    const-string v0, ""

    if-eq v13, v0, :cond_5

    const-string v0, ""

    if-eq v14, v0, :cond_5

    const-string v0, ""

    if-eq v15, v0, :cond_5

    .line 87
    new-instance v0, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;

    invoke-direct {v0, v13, v14, v15}, Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;-><init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    .line 88
    .local v0, "appItem":Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;
    iget-object v2, v1, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    invoke-interface {v2, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .end local v0    # "appItem":Lcom/hcn/widgets/dynamicrecyclerview/model/APPItem;
    .end local v13    # "shortName":Ljava/lang/String;
    .end local v14    # "packageName":Ljava/lang/String;
    .end local v15    # "className":Ljava/lang/String;
    goto :goto_4

    .line 67
    .end local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .local v0, "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :cond_4
    move-object/from16 v16, v0

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v0    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :cond_5
    :goto_4
    invoke-interface {v11}, Lorg/w3c/dom/Node;->getNextSibling()Lorg/w3c/dom/Node;

    move-result-object v0
    :try_end_4
    .catch Ljava/lang/Exception; {:try_start_4 .. :try_end_4} :catch_1
    .catchall {:try_start_4 .. :try_end_4} :catchall_1

    move-object v11, v0

    move-object/from16 v0, v16

    move-object/from16 v2, v17

    move-object/from16 v3, v18

    const/4 v9, 0x1

    goto/16 :goto_1

    .line 100
    .end local v7    # "length":I
    .end local v8    # "i":I
    .end local v10    # "item":Lorg/w3c/dom/Node;
    .end local v11    # "node":Lorg/w3c/dom/Node;
    .end local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    :catchall_1
    move-exception v0

    goto/16 :goto_7

    .line 95
    :catch_1
    move-exception v0

    move-object/from16 v2, v17

    move-object/from16 v3, v18

    goto/16 :goto_6

    .line 64
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v0    # "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v7    # "length":I
    .restart local v8    # "i":I
    :cond_6
    move-object/from16 v16, v0

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v0    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    add-int/lit8 v8, v8, 0x1

    goto/16 :goto_0

    .line 100
    .end local v7    # "length":I
    .end local v8    # "i":I
    .end local v16    # "nodeList":Lorg/w3c/dom/NodeList;
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :cond_7
    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    const/4 v0, 0x0

    .line 101
    .end local v4    # "doc":Lorg/w3c/dom/Document;
    .local v0, "doc":Lorg/w3c/dom/Document;
    const/4 v2, 0x0

    .line 102
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .local v2, "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    const/4 v3, 0x0

    .line 103
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .local v3, "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    nop

    .line 105
    iget-object v4, v1, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    if-nez v4, :cond_8

    .line 106
    const/4 v4, 0x0

    return v4

    .line 109
    :cond_8
    const-string v4, "XmlDeal"

    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "The cases count is :"

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v7, v1, Lcom/hcn/widgets/dynamicrecyclerview/utils/XmlDeal;->appItemList:Ljava/util/List;

    invoke-interface {v7}, Ljava/util/List;->size()I

    move-result v7

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    invoke-static {v4, v5}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 110
    const/4 v4, 0x1

    return v4

    .line 100
    .end local v0    # "doc":Lorg/w3c/dom/Document;
    .local v2, "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .local v3, "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v4    # "doc":Lorg/w3c/dom/Document;
    :catchall_2
    move-exception v0

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    goto :goto_7

    .line 95
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :catch_2
    move-exception v0

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    goto :goto_6

    .line 100
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :catchall_3
    move-exception v0

    move-object/from16 v6, p1

    move-object/from16 v17, v2

    move-object/from16 v18, v3

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    goto :goto_7

    .line 95
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    :catch_3
    move-exception v0

    move-object/from16 v6, p1

    move-object/from16 v17, v2

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    goto :goto_6

    .line 100
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    :catchall_4
    move-exception v0

    move-object/from16 v6, p1

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :goto_5
    move-object/from16 v17, v2

    move-object/from16 v18, v3

    goto :goto_7

    .line 95
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :catch_4
    move-exception v0

    move-object/from16 v6, p1

    .line 96
    .local v0, "e":Ljava/lang/Exception;
    :goto_6
    :try_start_5
    const-string v5, "XmlDeal"

    invoke-virtual {v0}, Ljava/lang/Exception;->getMessage()Ljava/lang/String;

    move-result-object v7

    invoke-static {v5, v7}, Landroid/util/Log;->e(Ljava/lang/String;Ljava/lang/String;)I

    .line 97
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V
    :try_end_5
    .catchall {:try_start_5 .. :try_end_5} :catchall_5

    .line 98
    nop

    .line 100
    const/4 v4, 0x0

    .line 101
    const/4 v3, 0x0

    .line 102
    const/4 v2, 0x0

    .line 98
    const/4 v5, 0x0

    return v5

    .line 100
    .end local v0    # "e":Ljava/lang/Exception;
    :catchall_5
    move-exception v0

    goto :goto_5

    .end local v2    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .end local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .restart local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    :goto_7
    const/4 v2, 0x0

    .line 101
    .end local v4    # "doc":Lorg/w3c/dom/Document;
    .local v2, "doc":Lorg/w3c/dom/Document;
    const/4 v3, 0x0

    .line 102
    .end local v18    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    .restart local v3    # "docBuilder":Ljavax/xml/parsers/DocumentBuilder;
    const/4 v4, 0x0

    .line 103
    .end local v17    # "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    .local v4, "docBuilderFactory":Ljavax/xml/parsers/DocumentBuilderFactory;
    throw v0
.end method
