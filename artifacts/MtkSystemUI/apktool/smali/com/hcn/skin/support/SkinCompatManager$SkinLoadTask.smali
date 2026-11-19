.class Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;
.super Landroid/os/AsyncTask;
.source "SkinCompatManager.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/hcn/skin/support/SkinCompatManager;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x2
    name = "SkinLoadTask"
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/os/AsyncTask<",
        "Ljava/lang/String;",
        "Ljava/lang/Void;",
        "Ljava/lang/String;",
        ">;"
    }
.end annotation


# instance fields
.field private final mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

.field final synthetic this$0:Lcom/hcn/skin/support/SkinCompatManager;


# direct methods
.method constructor <init>(Lcom/hcn/skin/support/SkinCompatManager;Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;)V
    .locals 0
    .param p2, "listener"    # Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    .line 151
    iput-object p1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V

    .line 152
    iput-object p2, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    .line 153
    return-void
.end method


# virtual methods
.method protected bridge synthetic doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    .line 148
    check-cast p1, [Ljava/lang/String;

    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->doInBackground([Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method protected varargs doInBackground([Ljava/lang/String;)Ljava/lang/String;
    .locals 3
    .param p1, "params"    # [Ljava/lang/String;

    .line 165
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;

    move-result-object v0

    monitor-enter v0

    .line 166
    :try_start_0
    const-string v1, "SkinCompatManager"

    const-string v2, "load skin ing"

    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 167
    :goto_0
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-static {v1}, Lcom/hcn/skin/support/SkinCompatManager;->access$100(Lcom/hcn/skin/support/SkinCompatManager;)Z

    move-result v1
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    if-eqz v1, :cond_0

    .line 169
    :try_start_1
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-static {v1}, Lcom/hcn/skin/support/SkinCompatManager;->access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->wait()V
    :try_end_1
    .catch Ljava/lang/InterruptedException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_1

    .line 170
    :catch_0
    move-exception v1

    .line 171
    .local v1, "e":Ljava/lang/InterruptedException;
    :try_start_2
    invoke-virtual {v1}, Ljava/lang/InterruptedException;->printStackTrace()V

    .line 172
    .end local v1    # "e":Ljava/lang/InterruptedException;
    :goto_1
    goto :goto_0

    .line 174
    :cond_0
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    const/4 v2, 0x1

    invoke-static {v1, v2}, Lcom/hcn/skin/support/SkinCompatManager;->access$102(Lcom/hcn/skin/support/SkinCompatManager;Z)Z

    .line 175
    monitor-exit v0
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 177
    :try_start_3
    array-length v0, p1

    if-ne v0, v2, :cond_2

    .line 178
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    const/4 v1, 0x0

    aget-object v2, p1, v1

    invoke-virtual {v0, v2}, Lcom/hcn/skin/support/SkinCompatManager;->loadSkinSync(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    .line 179
    .local v0, "skinName":Ljava/lang/String;
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v2

    if-eqz v2, :cond_1

    .line 180
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/resources/SkinCompatResources;->resetSkin()V

    .line 181
    const-string v1, ""

    return-object v1

    .line 183
    :cond_1
    aget-object v1, p1, v1
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_1

    return-object v1

    .line 187
    .end local v0    # "skinName":Ljava/lang/String;
    :cond_2
    goto :goto_2

    .line 185
    :catch_1
    move-exception v0

    .line 186
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 188
    .end local v0    # "e":Ljava/lang/Exception;
    :goto_2
    invoke-static {}, Lcom/hcn/skin/support/resources/SkinCompatResources;->getInstance()Lcom/hcn/skin/support/resources/SkinCompatResources;

    move-result-object v0

    invoke-virtual {v0}, Lcom/hcn/skin/support/resources/SkinCompatResources;->resetSkin()V

    .line 189
    const/4 v0, 0x0

    return-object v0

    .line 175
    :catchall_0
    move-exception v1

    :try_start_4
    monitor-exit v0
    :try_end_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_0

    throw v1
.end method

.method protected bridge synthetic onPostExecute(Ljava/lang/Object;)V
    .locals 0

    .line 148
    check-cast p1, Ljava/lang/String;

    invoke-virtual {p0, p1}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->onPostExecute(Ljava/lang/String;)V

    return-void
.end method

.method protected onPostExecute(Ljava/lang/String;)V
    .locals 4
    .param p1, "skinName"    # Ljava/lang/String;

    .line 194
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-static {v0}, Lcom/hcn/skin/support/SkinCompatManager;->access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;

    move-result-object v0

    monitor-enter v0

    .line 196
    :try_start_0
    const-string v1, "SkinCompatManager"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "load skin result : "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {v1, v2}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 197
    if-eqz p1, :cond_0

    .line 198
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->getInstance()Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    move-result-object v1

    invoke-virtual {v1, p1}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->setSkinName(Ljava/lang/String;)Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->commitEditor()V

    .line 199
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-virtual {v1}, Lcom/hcn/skin/support/SkinCompatManager;->notifySkinChanged()V

    .line 200
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    if-eqz v1, :cond_1

    .line 201
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    invoke-interface {v1}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;->onSuccess()V

    goto :goto_0

    .line 204
    :cond_0
    invoke-static {}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->getInstance()Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    move-result-object v1

    const-string v2, ""

    invoke-virtual {v1, v2}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->setSkinName(Ljava/lang/String;)Lcom/hcn/skin/support/utils/SkinSharedPreferences;

    move-result-object v1

    invoke-virtual {v1}, Lcom/hcn/skin/support/utils/SkinSharedPreferences;->commitEditor()V

    .line 205
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    if-eqz v1, :cond_1

    .line 206
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    const-string/jumbo v2, "\u76ae\u80a4\u8d44\u6e90\u83b7\u53d6\u5931\u8d25"

    invoke-interface {v1, v2}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;->onFailed(Ljava/lang/String;)V

    .line 209
    :cond_1
    :goto_0
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    const/4 v2, 0x0

    invoke-static {v1, v2}, Lcom/hcn/skin/support/SkinCompatManager;->access$102(Lcom/hcn/skin/support/SkinCompatManager;Z)Z

    .line 210
    iget-object v1, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->this$0:Lcom/hcn/skin/support/SkinCompatManager;

    invoke-static {v1}, Lcom/hcn/skin/support/SkinCompatManager;->access$000(Lcom/hcn/skin/support/SkinCompatManager;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->notifyAll()V

    .line 211
    monitor-exit v0

    .line 212
    return-void

    .line 211
    :catchall_0
    move-exception v1

    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    throw v1
.end method

.method protected onPreExecute()V
    .locals 2

    .line 157
    const-string v0, "SkinCompatManager"

    const-string v1, "load skin start"

    invoke-static {v0, v1}, Lcom/hcn/skin/support/utils/SkinLog;->d(Ljava/lang/String;Ljava/lang/String;)V

    .line 158
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    if-eqz v0, :cond_0

    .line 159
    iget-object v0, p0, Lcom/hcn/skin/support/SkinCompatManager$SkinLoadTask;->mListener:Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;

    invoke-interface {v0}, Lcom/hcn/skin/support/SkinCompatManager$SkinLoaderListener;->onStart()V

    .line 161
    :cond_0
    return-void
.end method
