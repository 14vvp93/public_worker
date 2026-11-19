package com.autochips.bluetooth.fragment;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.OperationCanceledException;
import android.os.RemoteException;
import android.provider.CallLog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.service.IPbapCallback;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skin.support.utils.SkinListUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class RecordFragment extends SkinCompatFragment implements View.OnClickListener {
    private static final String[] CALLLOG_PROJECTION = {"number", "date", "type", "name"};
    private static final String ITEM_HISTORY_NAME = "item_history_name";
    private static final String ITEM_HISTORY_NUMBER = "item_history_number";
    private static final String ITEM_HISTORY_TIME = "item_history_time";
    private static final String ITEM_HISTORY_TYPE = "item_history_type";
    private static final int LOADER_HISTORY = 1;
    private static final int MSG_DOWNLOAD_FAILED = 2;
    private static final int MSG_DOWNLOAD_FINISH = 1;
    private static final int MSG_DOWNLOAD_START = 0;
    private static final int MSG_UI_HISTORY_ADD = 5;
    private static final int MSG_UI_LOAD_FINISH = 4;
    private static final int MSG_UI_LOAD_START = 3;
    public static final String TAG = "RecordFragment";
    private LocalBluetoothAdapterManager mAdapterManager;
    private Context mContext;
    private LinearLayout mDownloadLayout;
    private TextView mDownloadTextView;
    private ArrayList<HashMap<String, Object>> mHistoryList;
    private HistoryListAdapter mHistoryListAdapter;
    private LoaderHandler mLoaderHandler;
    private HandlerThread mLoaderThread;
    private MainHandler mMainHandler;
    private RadioButton mMissedCall;
    private AbsListView historyListView = null;
    private int mFocusId = C0414R.id.btn_missed;
    private CancellationSignal mCancellationSignal = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.RecordFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (intExtra == 2) {
                    RecordFragment.this.mFocusId = C0414R.id.btn_missed;
                    if (RecordFragment.this.mMissedCall != null) {
                        RecordFragment.this.mMissedCall.setChecked(true);
                    }
                    RecordFragment.this.startLoadHistoryCallLog(3);
                    return;
                }
                if (intExtra == 0) {
                    RecordFragment.this.stopLoadHistoryCallLog();
                    RecordFragment.this.clearHistoryList();
                }
            }
        }
    };
    private AdapterView.OnItemClickListener mHistoryListClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.RecordFragment.2
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            RecordFragment.this.mHistoryListAdapter.setSelect(i);
            RecordFragment.this.mHistoryListAdapter.notifyDataSetChanged();
            HashMap<String, Object> item = RecordFragment.this.mHistoryListAdapter.getItem(i);
            if (item != null) {
                MyApplication.getInstance().getHfpclientManager().dial((String) item.get(RecordFragment.ITEM_HISTORY_NUMBER));
            }
        }
    };
    private ConnectionListener mAdapterListener = new ConnectionListener() { // from class: com.autochips.bluetooth.fragment.RecordFragment.3
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            RecordFragment.this.mAdapterManager.registerPbapCallback(RecordFragment.this.mIPbapCallback);
            if (RecordFragment.this.mAdapterManager.getPbapDownLoadState(6)) {
                RecordFragment.this.showDownloadGroup();
            }
        }
    };
    IPbapCallback mIPbapCallback = new IPbapCallback.Stub() { // from class: com.autochips.bluetooth.fragment.RecordFragment.4
        @Override // com.hcn.bluetooth.service.IPbapCallback
        public void onPbapConnectStateChanged(int i) throws RemoteException {
        }

        @Override // com.hcn.bluetooth.service.IPbapCallback
        public void onPbapDownloadStateChanged(int i, int i2) throws RemoteException {
            if (i2 == 6) {
                if (i == 0) {
                    if (RecordFragment.this.mMainHandler != null) {
                        RecordFragment.this.mMainHandler.removeMessages(1);
                        RecordFragment.this.mMainHandler.sendEmptyMessage(1);
                        return;
                    }
                    return;
                }
                if (i == 1) {
                    if (RecordFragment.this.mMainHandler != null) {
                        RecordFragment.this.mMainHandler.removeMessages(0);
                        RecordFragment.this.mMainHandler.sendEmptyMessage(0);
                        return;
                    }
                    return;
                }
                RecordFragment.this.mMainHandler.removeMessages(2);
                Message messageObtainMessage = RecordFragment.this.mMainHandler.obtainMessage(2);
                messageObtainMessage.arg1 = i;
                RecordFragment.this.mMainHandler.sendMessage(messageObtainMessage);
            }
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.bt_callhistory;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    private class MainHandler extends Handler {
        private WeakReference<RecordFragment> mWeakRefFragment;

        public MainHandler(RecordFragment recordFragment) {
            super(Looper.getMainLooper());
            this.mWeakRefFragment = new WeakReference<>(recordFragment);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            RecordFragment recordFragment = this.mWeakRefFragment.get();
            if (recordFragment != null) {
                int i = message.what;
                if (i == 0) {
                    recordFragment.showDownloadGroup();
                    recordFragment.stopLoadHistoryCallLog();
                    recordFragment.clearHistoryList();
                } else if (i == 1) {
                    recordFragment.startLoadHistoryCallLog(0);
                } else if (i == 2) {
                    int i2 = message.arg1;
                    if (i2 == 3 || i2 == 2) {
                        Utils.showToast(recordFragment.getContext(), SkinUtils.getString(C0414R.string.str_download_phonebook_fail));
                    }
                    recordFragment.hideDownloadGroup();
                } else if (i == 3) {
                    recordFragment.clearHistoryList();
                } else if (i == 4) {
                    if (message.obj != null) {
                        recordFragment.mHistoryList.addAll((List) message.obj);
                    }
                    recordFragment.mHistoryListAdapter.notifyDataSetChanged();
                    recordFragment.hideDownloadGroup();
                    Log.d(RecordFragment.TAG, "MSG_UI_LOAD_FINISH size=" + recordFragment.mHistoryList.size());
                } else if (i == 5) {
                    recordFragment.mHistoryList.addAll((List) message.obj);
                    recordFragment.mHistoryListAdapter.notifyDataSetChanged();
                    recordFragment.changeDownloadHint(recordFragment.mHistoryList.size());
                    Log.d(RecordFragment.TAG, "MSG_UI_HISTORY_ADD");
                }
            }
            super.handleMessage(message);
        }
    }

    private class LoaderHandler extends Handler {
        public LoaderHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                try {
                    RecordFragment.this.loadHistoryCallLog(message.arg1);
                } catch (Exception unused) {
                    Log.d(RecordFragment.TAG, "handleMessage: MSG_PB_SEARCH Exception");
                    RecordFragment.this.hideDownloadGroup();
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMainHandler = new MainHandler(this);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d(TAG, "RecordFragment onCreate");
        LocalBluetoothAdapterManager adapterManager = MyApplication.getInstance().getAdapterManager();
        this.mAdapterManager = adapterManager;
        adapterManager.addConnectListener(this.mAdapterListener);
        this.mAdapterManager.registerPbapCallback(this.mIPbapCallback);
        HandlerThread handlerThread = new HandlerThread("work_thread");
        this.mLoaderThread = handlerThread;
        handlerThread.start();
        this.mLoaderHandler = new LoaderHandler(this.mLoaderThread.getLooper());
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewOnCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = SkinUtils.getContext();
        initView(viewOnCreateView);
        Log.d(TAG, "onCreateView");
        return viewOnCreateView;
    }

    private void initView(View view) {
        if (view == null) {
            Log.e(TAG, "initView: layout is null");
            return;
        }
        view.findViewById(SkinUtils.getId(C0414R.id.bt_callhistory_syn)).setOnClickListener(this);
        view.findViewById(SkinUtils.getId(C0414R.id.btn_dialed)).setOnClickListener(this);
        view.findViewById(SkinUtils.getId(C0414R.id.btn_received)).setOnClickListener(this);
        RadioButton radioButton = (RadioButton) view.findViewById(SkinUtils.getId(C0414R.id.btn_missed));
        this.mMissedCall = radioButton;
        radioButton.setOnClickListener(this);
        this.mDownloadLayout = (LinearLayout) view.findViewById(SkinUtils.getId(C0414R.id.download_callhistory_layout));
        this.mDownloadTextView = (TextView) view.findViewById(SkinUtils.getId(C0414R.id.tv_download_callhistory_text));
        ((ProgressBar) view.findViewById(SkinUtils.getId(C0414R.id.progressbar_download_callhistory))).setIndeterminate(false);
        this.historyListView = (AbsListView) view.findViewById(SkinUtils.getId(C0414R.id.history_listview));
        this.mHistoryList = new ArrayList<>();
        HistoryListAdapter historyListAdapter = new HistoryListAdapter(this.mContext, this.mHistoryList, SkinUtils.getId(C0414R.layout.history_listitem), new String[]{ITEM_HISTORY_NAME, ITEM_HISTORY_NUMBER, ITEM_HISTORY_TIME, ITEM_HISTORY_TYPE}, new int[]{SkinUtils.getId(C0414R.id.item_history_name), SkinUtils.getId(C0414R.id.item_history_number), SkinUtils.getId(C0414R.id.item_history_time), SkinUtils.getId(C0414R.id.btn_call_history_img)});
        this.mHistoryListAdapter = historyListAdapter;
        this.historyListView.setAdapter((ListAdapter) historyListAdapter);
        this.historyListView.setOnItemClickListener(this.mHistoryListClickListener);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        Log.e(TAG, "setUserVisibleHint:" + z);
        super.setUserVisibleHint(z);
        if (z && this.mAdapterManager.isReady()) {
            if (this.mAdapterManager.getConnectDevice() != null) {
                if (this.mAdapterManager.getPbapDownLoadState(6)) {
                    showDownloadGroup();
                    return;
                }
                hideDownloadGroup();
                if (this.mHistoryList.isEmpty()) {
                    this.mFocusId = C0414R.id.btn_missed;
                    RadioButton radioButton = this.mMissedCall;
                    if (radioButton != null) {
                        radioButton.setChecked(true);
                    }
                    startLoadHistoryCallLog(3);
                    return;
                }
                return;
            }
            clearHistoryList();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(TAG, "RecordFragment onStart");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        if (getContext() != null) {
            getContext().registerReceiver(this.mReceiver, intentFilter);
        }
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "RecordFragment onResume");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        Log.d(TAG, "RecordFragment onPause");
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        Log.d(TAG, "RecordFragment onStop");
        super.onStop();
        if (getContext() != null) {
            getContext().unregisterReceiver(this.mReceiver);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mAdapterManager;
        if (localBluetoothAdapterManager != null) {
            localBluetoothAdapterManager.unregisterPbapCallback(this.mIPbapCallback);
            this.mAdapterManager.removeConnectListener(this.mAdapterListener);
        }
        HandlerThread handlerThread = this.mLoaderThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDownloadGroup() {
        changeDownloadHint(0);
        this.mDownloadLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideDownloadGroup() {
        this.mDownloadLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDownloadHint(int i) {
        if (isAdded()) {
            String str = String.format(SkinUtils.getString(C0414R.string.str_download_history), Integer.valueOf(i));
            if (this.mDownloadTextView != null) {
                Log.d(TAG, "changeDownloadHint:" + str);
                this.mDownloadTextView.setText(str);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Log.d(TAG, "onHiddenChanged hidden=" + z);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (SkinUtils.getViewId(view)) {
            case C0414R.id.bt_callhistory_syn /* 2131230791 */:
                stopLoadHistoryCallLog();
                if (!this.mAdapterManager.pbapStartDownLoad(6)) {
                    Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.str_download_wait_phonebook_tip));
                } else {
                    this.mFocusId = C0414R.id.bt_callhistory_syn;
                }
                break;
            case C0414R.id.btn_dialed /* 2131230842 */:
                this.mFocusId = C0414R.id.btn_dialed;
                clearHistoryList();
                startLoadHistoryCallLog(2);
                Log.d(TAG, "show download btn_dialed");
                break;
            case C0414R.id.btn_missed /* 2131230849 */:
                this.mFocusId = C0414R.id.btn_missed;
                clearHistoryList();
                startLoadHistoryCallLog(3);
                Log.d(TAG, "show download btn_missed");
                break;
            case C0414R.id.btn_received /* 2131230865 */:
                this.mFocusId = C0414R.id.btn_received;
                clearHistoryList();
                startLoadHistoryCallLog(1);
                Log.d(TAG, "show download btn_received");
                break;
        }
    }

    public void clearHistoryList() {
        ArrayList<HashMap<String, Object>> arrayList = this.mHistoryList;
        if (arrayList != null && arrayList.size() > 0) {
            this.mHistoryList.clear();
            this.mHistoryListAdapter.notifyDataSetChanged();
        }
    }

    public void stopLoadHistoryCallLog() {
        Log.d(TAG, "stopLoadHistoryCallLog");
        this.mLoaderHandler.removeMessages(1);
        if (this.mCancellationSignal != null) {
            Log.d(TAG, "stopLoadHistoryCallLog mCancellationSignal");
            this.mCancellationSignal.cancel();
        }
    }

    public void startLoadHistoryCallLog(int i) {
        Message messageObtain = Message.obtain(this.mLoaderHandler, 1);
        messageObtain.arg1 = i;
        stopLoadHistoryCallLog();
        this.mLoaderHandler.sendMessage(messageObtain);
    }

    public void loadHistoryCallLog(int i) {
        Cursor cursorQuery;
        if (getContext() == null) {
            this.mMainHandler.sendMessage(Message.obtain(this.mMainHandler, 4));
            return;
        }
        BluetoothDeviceInfo connectDevice = this.mAdapterManager.getConnectDevice();
        if (connectDevice == null) {
            Log.e(TAG, "loadHistoryCallLog: deviceInfo==null!");
            this.mMainHandler.sendMessage(Message.obtain(this.mMainHandler, 4));
            return;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        Log.d(TAG, "loadHistoryCallLog: callType=" + i);
        try {
            synchronized (this) {
                CancellationSignal cancellationSignal = this.mCancellationSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    this.mCancellationSignal = null;
                }
                this.mCancellationSignal = new CancellationSignal();
            }
            Account account = new Account(connectDevice.getDeviceAddr(), Utils.ACCOUNT_TYPE);
            Log.d(TAG, "loadHistoryCallLog: name = " + account.name + " hashCode = " + account.hashCode());
            if (i == 0) {
                cursorQuery = contentResolver.query(CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, "subscription_id=? or subscription_id=?", new String[]{String.valueOf(account.hashCode()), account.name}, "date desc", this.mCancellationSignal);
            } else {
                cursorQuery = contentResolver.query(CallLog.Calls.CONTENT_URI, CALLLOG_PROJECTION, "type=? AND (subscription_id=? or subscription_id=?)", new String[]{String.valueOf(i), String.valueOf(account.hashCode()), account.name}, "date desc", this.mCancellationSignal);
            }
            ArrayList arrayList = new ArrayList();
            this.mMainHandler.removeMessages(5);
            this.mMainHandler.sendEmptyMessage(3);
            Log.d(TAG, "loadHistoryCallLog: send MSG_UI_LOAD_START");
            for (int i2 = 1; cursorQuery.moveToNext() && !this.mLoaderHandler.hasMessages(i2) && !this.mCancellationSignal.isCanceled(); i2 = 1) {
                long j = cursorQuery.getLong(cursorQuery.getColumnIndex("date"));
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("number"));
                int i3 = cursorQuery.getInt(cursorQuery.getColumnIndex("type"));
                String strSubstring = SkinID.SKIN_NONE;
                if (string.length() > 15) {
                    String strSubstring2 = string.substring(12);
                    string = string.substring(0, 12);
                    strSubstring = strSubstring2;
                } else if (string.length() > 14) {
                    strSubstring = string.substring(10);
                    string = string.substring(0, 10);
                }
                if (!SkinID.SKIN_NONE.equals(strSubstring)) {
                    string = new StringBuffer().append(string).append(SkinListUtils.DEFAULT_JOIN_SEPARATOR).append(strSubstring).toString();
                }
                String contactNameByNumber = Utils.getContactNameByNumber(getContext(), string, connectDevice.getDeviceAddr());
                if (TextUtils.isEmpty(contactNameByNumber)) {
                    contactNameByNumber = SkinID.SKIN_NONE;
                }
                HashMap map = new HashMap();
                map.put(ITEM_HISTORY_NAME, contactNameByNumber);
                map.put(ITEM_HISTORY_NUMBER, string);
                map.put(ITEM_HISTORY_TIME, Long.valueOf(j));
                map.put(ITEM_HISTORY_TYPE, Integer.valueOf(i3));
                arrayList.add(map);
                if (arrayList.size() >= 10) {
                    Message messageObtain = Message.obtain(this.mMainHandler, 5);
                    messageObtain.obj = arrayList;
                    this.mMainHandler.sendMessage(messageObtain);
                    arrayList = new ArrayList();
                }
            }
            cursorQuery.close();
            if (this.mCancellationSignal.isCanceled()) {
                this.mMainHandler.removeMessages(5);
                Log.d(TAG, "loadHistoryCallLog: finish cancel!!");
            } else {
                Message messageObtain2 = Message.obtain(this.mMainHandler, 4);
                messageObtain2.obj = arrayList;
                this.mMainHandler.sendMessage(messageObtain2);
                Log.d(TAG, "loadHistoryCallLog: send MSG_UI_LOAD_FINISH");
            }
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        } catch (OperationCanceledException unused) {
            synchronized (this) {
                this.mCancellationSignal = null;
                Log.e(TAG, "loadHistoryCallLog: OperationCanceledException!");
            }
        }
    }

    class HistoryListAdapter extends SimpleAdapter {
        private ArrayList<HashMap<String, Object>> mList;
        private int mSelectIdx;

        public HistoryListAdapter(Context context, ArrayList<HashMap<String, Object>> arrayList, int i, String[] strArr, int[] iArr) {
            super(context, arrayList, i, strArr, iArr);
            this.mSelectIdx = -1;
            this.mSelectIdx = -1;
            this.mList = arrayList;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public HashMap<String, Object> getItem(int i) {
            ArrayList<HashMap<String, Object>> arrayList = this.mList;
            if (arrayList != null) {
                return arrayList.get(i);
            }
            return null;
        }

        public void setSelect(int i) {
            this.mSelectIdx = i;
        }

        public int getSelect() {
            return this.mSelectIdx;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            String string;
            String str;
            String str2;
            if (view == null) {
                ViewHolder viewHolder2 = new ViewHolder();
                View layout = SkinUtils.getLayout(C0414R.layout.history_listitem);
                viewHolder2.nameTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_history_name));
                viewHolder2.phoneTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_history_number));
                viewHolder2.timeTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_history_time));
                viewHolder2.imageView = (ImageView) layout.findViewById(SkinUtils.getId(C0414R.id.btn_call_history_img));
                layout.setTag(viewHolder2);
                viewHolder = viewHolder2;
                view = layout;
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            int iIntValue = -1;
            int size = this.mList.size();
            String dateToString = SkinID.SKIN_NONE;
            if (i < size) {
                try {
                    string = (String) this.mList.get(i).get(RecordFragment.ITEM_HISTORY_NAME);
                    try {
                        if (TextUtils.isEmpty(string)) {
                            string = SkinUtils.getString(C0414R.string.phonebook_unknow_name);
                        }
                        string = String.format(Locale.getDefault(), "%d. %s", Integer.valueOf(i + 1), string);
                        str = (String) this.mList.get(i).get(RecordFragment.ITEM_HISTORY_NUMBER);
                        try {
                            iIntValue = ((Integer) this.mList.get(i).get(RecordFragment.ITEM_HISTORY_TYPE)).intValue();
                            dateToString = Utils.getDateToString(((Long) this.mList.get(i).get(RecordFragment.ITEM_HISTORY_TIME)).longValue());
                        } catch (Exception unused) {
                            Log.d(RecordFragment.TAG, "getView: Exception!!!!!");
                        }
                    } catch (Exception unused2) {
                        str = SkinID.SKIN_NONE;
                    }
                } catch (Exception unused3) {
                    string = SkinID.SKIN_NONE;
                    str = string;
                }
                str2 = dateToString;
                dateToString = string;
            } else {
                str2 = SkinID.SKIN_NONE;
                str = str2;
            }
            viewHolder.nameTextView.setText(dateToString);
            viewHolder.phoneTextView.setText(str);
            viewHolder.timeTextView.setText(str2);
            if (iIntValue == 1) {
                viewHolder.imageView.setImageDrawable(SkinUtils.getDrawable(C0414R.drawable.icon_incoming_call));
            } else if (iIntValue == 2) {
                viewHolder.imageView.setImageDrawable(SkinUtils.getDrawable(C0414R.drawable.icon_outgoing_call));
            } else {
                viewHolder.imageView.setImageResource(SkinUtils.getId(C0414R.drawable.icon_missed_call));
            }
            return view;
        }

        public final class ViewHolder {
            public ImageView imageView;
            public TextView nameTextView;
            public TextView phoneTextView;
            public TextView timeTextView;

            public ViewHolder() {
            }
        }
    }
}
