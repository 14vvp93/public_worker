package com.hcn.widgets.dynamicrecyclerview.utils;

import android.util.Log;
import com.hcn.widgets.dynamicrecyclerview.model.APPItem;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes.dex */
public class XmlDeal {
    public List<APPItem> appItemList;

    public XmlDeal(InputStream is) {
        this.appItemList = null;
        this.appItemList = new ArrayList();
        if (!ParseXml(is)) {
            throw new RuntimeException();
        }
    }

    private boolean ParseXml(InputStream is) throws Exception {
        NodeList nodeList;
        DocumentBuilderFactory docBuilderFactory;
        DocumentBuilder docBuilder;
        DocumentBuilderFactory docBuilderFactory2 = null;
        DocumentBuilder docBuilder2 = null;
        try {
            docBuilderFactory2 = DocumentBuilderFactory.newInstance();
            try {
                docBuilder2 = docBuilderFactory2.newDocumentBuilder();
                try {
                    Document doc = docBuilder2.parse(is);
                    NodeList nodeList2 = doc.getElementsByTagName("APPItemList");
                    int length = nodeList2.getLength();
                    int i = 0;
                    while (true) {
                        short s = 1;
                        if (i >= length) {
                            break;
                        }
                        Node item = nodeList2.item(i);
                        Node node = item.getFirstChild();
                        while (node != null) {
                            if (node.getNodeType() == s) {
                                String className = "";
                                String className2 = "";
                                String shortName = "";
                                int j = 0;
                                while (j < node.getAttributes().getLength()) {
                                    String attrName = node.getAttributes().item(j).getNodeName();
                                    String attrValue = node.getAttributes().item(j).getNodeValue();
                                    NodeList nodeList3 = nodeList2;
                                    DocumentBuilderFactory docBuilderFactory3 = docBuilderFactory2;
                                    try {
                                        StringBuilder sb = new StringBuilder();
                                        DocumentBuilder docBuilder3 = docBuilder2;
                                        try {
                                            sb.append(" attrName:");
                                            sb.append(attrName);
                                            sb.append(" ---attrValue :");
                                            sb.append(attrValue);
                                            Log.i("XmlDeal", sb.toString());
                                            if (attrName.equals("shortName")) {
                                                shortName = attrValue;
                                            } else if (attrName.equals("packageName")) {
                                                className2 = attrValue;
                                            } else if (attrName.equals("className")) {
                                                className = attrValue;
                                            }
                                            j++;
                                            nodeList2 = nodeList3;
                                            docBuilderFactory2 = docBuilderFactory3;
                                            docBuilder2 = docBuilder3;
                                        } catch (Exception e) {
                                            e = e;
                                            docBuilderFactory2 = docBuilderFactory3;
                                            docBuilder2 = docBuilder3;
                                        } catch (Throwable th) {
                                            e = th;
                                            throw e;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        docBuilderFactory2 = docBuilderFactory3;
                                    } catch (Throwable th2) {
                                        e = th2;
                                    }
                                }
                                nodeList = nodeList2;
                                docBuilderFactory = docBuilderFactory2;
                                docBuilder = docBuilder2;
                                if (shortName != "" && className2 != "" && className != "") {
                                    APPItem appItem = new APPItem(shortName, className2, className);
                                    this.appItemList.add(appItem);
                                }
                            } else {
                                nodeList = nodeList2;
                                docBuilderFactory = docBuilderFactory2;
                                docBuilder = docBuilder2;
                            }
                            node = node.getNextSibling();
                            nodeList2 = nodeList;
                            docBuilderFactory2 = docBuilderFactory;
                            docBuilder2 = docBuilder;
                            s = 1;
                        }
                        i++;
                        try {
                            Log.e("XmlDeal", e.getMessage());
                            e.printStackTrace();
                            return false;
                        } catch (Throwable th3) {
                            e = th3;
                            throw e;
                        }
                    }
                    if (this.appItemList.size() == 0) {
                        return false;
                    }
                    Log.i("XmlDeal", "The cases count is :" + this.appItemList.size());
                    return true;
                } catch (Exception e3) {
                    e = e3;
                } catch (Throwable th4) {
                    e = th4;
                }
            } catch (Exception e4) {
                e = e4;
            } catch (Throwable th5) {
                e = th5;
            }
        } catch (Exception e5) {
            e = e5;
        } catch (Throwable th6) {
            e = th6;
            throw e;
        }
    }
}
