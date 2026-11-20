package com.hcn.skin2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.hcn.common.misc.LogUtils;
import com.hcn.skinx_night.IUiModeDecoration;
import com.hcn.skinx_night.NightModeManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class BaseRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IUiModeDecoration {
    public static final String TAG = "BaseRvAdapter";
    protected Context mContext;
    protected boolean mIsResume = false;
    private final int mListFileType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FileType {
        public static final int FILE_MUSIC = 0;
        public static final int FILE_PHOTO = 2;
        public static final int FILE_VIDEO = 1;
        public static final int NONE = -1;
    }

    public int getItemCount() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override // com.hcn.skinx_night.IUiModeDecoration
    public void onUpdateUiModeView(boolean z) {
        LogUtils.v(TAG, z + " onUpdateUiModeView: " + getClass().getSimpleName());
    }

    public BaseRvAdapter(Context context, int i) {
        this.mContext = context;
        this.mListFileType = i;
        NightModeManager.register(this);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        NightModeManager.unregister(this);
    }

    protected int listFileType() {
        return this.mListFileType;
    }

    protected boolean isListFileType(int i) {
        return this.mListFileType == i;
    }

    public boolean isResume() {
        return this.mIsResume;
    }

    public void onResume() {
        this.mIsResume = true;
    }

    public void onPause() {
        this.mIsResume = false;
    }

    public void onDestroy() {
        this.mIsResume = false;
    }

    protected static class ViewHolderEx extends RecyclerView.ViewHolder {
        protected Object mTag;

        public ViewHolderEx(View view, Object obj) {
            super(view);
            this.mTag = obj;
        }

        public Object getTag() {
            return this.mTag;
        }

        public void setTag(Object obj) {
            this.mTag = obj;
        }
    }
}
