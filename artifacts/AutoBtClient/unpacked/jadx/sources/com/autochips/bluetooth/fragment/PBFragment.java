package com.autochips.bluetooth.fragment;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.OperationCanceledException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.autochips.bluetooth.view.SideBar;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.ConnectionListener;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.bluetooth.service.IPbapCallback;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skinx_night.NightX;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class PBFragment extends SkinCompatFragment implements View.OnClickListener, SideBar.OnTouchingLetterChangedListener {
    private static final String ITEM_PHONEBOOK_LABEL = "item_phonebook_label";
    private static final String ITEM_PHONEBOOK_NAME = "item_phonebook_name";
    private static final String ITEM_PHONEBOOK_NUMBER = "item_phonebook_number";
    private static final String ITEM_PHONEBOOK_PATH = "item_phonebook_path";
    private static final int LOADER_CONTRACT = 1;
    private static final int MSG_DOWNLOAD_FAILED = 2;
    private static final int MSG_DOWNLOAD_FINISH = 1;
    private static final int MSG_DOWNLOAD_START = 0;
    private static final int MSG_UI_HIDE_DOWNLOAD_GROUP = 6;
    private static final int MSG_UI_LOAD_FINISH = 4;
    private static final int MSG_UI_LOAD_START = 3;
    private static final int MSG_UI_PB_ADD = 5;
    private static final String PHONEBOOK_LABEL = "phonebook_label";
    private static final String PHONEBOOK_LABEL_ALT = "phonebook_label_alt";
    public static final String TAG = "PBFragment";
    private LocalBluetoothAdapterManager mAdapterManager;
    private View mClearContacts;
    private TextView mContactCountTextView;
    private Context mContext;
    private LinearLayout mDownloadLayout;
    private TextView mDownloadTextView;
    private EditText mEditSearchText;
    private MainHandler mHandler;
    private LoaderHandler mLoaderHandler;
    private HandlerThread mLoaderThread;
    private PBListAdapter mPBListAdapter;
    private EditText mSearchText;
    private SideBar mSideBar;
    private Button mUpdatePBCtrl = null;
    private AbsListView mPBGridView = null;
    public ArrayList<HashMap<String, String>> mPBList = new ArrayList<>();
    private String mSearchKey = SkinID.SKIN_NONE;
    private CancellationSignal mCancellationSignal = null;
    private BroadcastReceiver pbReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.PBFragment.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (intExtra == 2) {
                    PBFragment.this.startLoadContact(SkinID.SKIN_NONE);
                } else if (intExtra == 0) {
                    PBFragment.this.clearContactList();
                }
            }
        }
    };
    private TextWatcher mSearchEditWatcher = new TextWatcher() { // from class: com.autochips.bluetooth.fragment.PBFragment.3
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            PBFragment.this.mSearchKey = editable.toString().trim();
            PBFragment pBFragment = PBFragment.this;
            pBFragment.startLoadContact(pBFragment.mSearchKey);
        }
    };
    private AdapterView.OnItemClickListener mPhoneBookListClickListener = new AdapterView.OnItemClickListener() { // from class: com.autochips.bluetooth.fragment.PBFragment.4
        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            String str;
            PBFragment.this.mPBListAdapter.setSelect(i);
            PBFragment.this.mPBListAdapter.notifyDataSetChanged();
            if (!TextUtils.isEmpty(PBFragment.this.mPBListAdapter.mSearchLetter) && PBFragment.this.mPBListAdapter.mSearchPBList.size() > 0 && i < PBFragment.this.mPBListAdapter.mSearchPBList.size()) {
                str = (String) ((HashMap) PBFragment.this.mPBListAdapter.mSearchPBList.get(i)).get(PBFragment.ITEM_PHONEBOOK_NUMBER);
            } else {
                str = PBFragment.this.mPBList.get(i).get(PBFragment.ITEM_PHONEBOOK_NUMBER);
            }
            MyApplication.getInstance().getHfpclientManager().dial(str);
        }
    };
    private ConnectionListener mAdapterListener = new ConnectionListener() { // from class: com.autochips.bluetooth.fragment.PBFragment.5
        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceDisconnected() {
        }

        @Override // com.hcn.bluetooth.api.ConnectionListener
        public void onServiceConnected() {
            PBFragment.this.mAdapterManager.registerPbapCallback(PBFragment.this.mIPbapCallback);
            if (PBFragment.this.mAdapterManager.getPbapDownLoadState(2)) {
                PBFragment.this.showPBDownloadGroup();
            }
        }
    };
    IPbapCallback mIPbapCallback = new IPbapCallback.Stub() { // from class: com.autochips.bluetooth.fragment.PBFragment.6
        @Override // com.hcn.bluetooth.service.IPbapCallback
        public void onPbapDownloadStateChanged(int i, int i2) throws RemoteException {
            if (i2 == 2) {
                if (i == 0) {
                    PBFragment.this.mHandler.removeMessages(1);
                    PBFragment.this.mHandler.sendEmptyMessage(1);
                } else {
                    if (i != 1) {
                        PBFragment.this.mHandler.removeMessages(2);
                        Message messageObtainMessage = PBFragment.this.mHandler.obtainMessage(2);
                        messageObtainMessage.arg1 = i;
                        PBFragment.this.mHandler.sendMessage(messageObtainMessage);
                        return;
                    }
                    Log.e(PBFragment.TAG, "Pbap Downloading...");
                    PBFragment.this.mHandler.removeMessages(0);
                    PBFragment.this.mHandler.sendEmptyMessage(0);
                }
            }
        }

        @Override // com.hcn.bluetooth.service.IPbapCallback
        public void onPbapConnectStateChanged(int i) throws RemoteException {
            PBFragment.this.pbapConnectStateChange(i);
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.bt_phonebook;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    @Override // com.autochips.bluetooth.view.SideBar.OnTouchingLetterChangedListener
    public void onTouchingLetterChanged(String str) {
        Log.e(TAG, "onTouchingLetterChanged:" + str);
        PBListAdapter pBListAdapter = this.mPBListAdapter;
        if (pBListAdapter != null) {
            pBListAdapter.setSearchLetter(str);
            this.mPBGridView.setSelection(0);
        }
    }

    private static class MainHandler extends Handler {
        private WeakReference<PBFragment> mWeakReferencePBFragment;

        public MainHandler(PBFragment pBFragment) {
            super(Looper.getMainLooper());
            this.mWeakReferencePBFragment = new WeakReference<>(pBFragment);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            PBFragment pBFragment = this.mWeakReferencePBFragment.get();
            if (pBFragment != null) {
                switch (message.what) {
                    case 0:
                        pBFragment.showPBDownloadGroup();
                        pBFragment.cancelLoadContact();
                        pBFragment.clearContactList();
                        break;
                    case 1:
                        pBFragment.startLoadContact(SkinID.SKIN_NONE);
                        break;
                    case 2:
                        int i = message.arg1;
                        if (i == 3 || i == 2) {
                            Utils.showToast(pBFragment.getContext(), SkinUtils.getString(C0414R.string.str_download_phonebook_fail));
                        }
                        sendEmptyMessage(6);
                        break;
                    case 3:
                        pBFragment.clearContactList();
                        break;
                    case 4:
                        if (message.obj != null) {
                            pBFragment.mPBList.addAll((List) message.obj);
                        }
                        pBFragment.changeDownloadHint(pBFragment.mPBList.size());
                        if (pBFragment.mContactCountTextView != null) {
                            pBFragment.mContactCountTextView.setText(String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.bt_phonebook_search_hint), Integer.valueOf(pBFragment.mPBList.size())));
                        }
                        if (pBFragment.mSearchText != null) {
                            pBFragment.mSearchText.setHint(String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.bt_phonebook_search_hint), Integer.valueOf(pBFragment.mPBList.size())));
                        }
                        pBFragment.mPBListAdapter.notifyDataSetChanged();
                        sendEmptyMessageDelayed(6, 500L);
                        Log.d(PBFragment.TAG, "MSG_UI_LOAD_FINISH size=" + pBFragment.mPBList.size());
                        break;
                    case 5:
                        pBFragment.mPBList.addAll((List) message.obj);
                        pBFragment.changeDownloadHint(pBFragment.mPBList.size());
                        pBFragment.mPBListAdapter.notifyDataSetChanged();
                        break;
                    case 6:
                        pBFragment.hidePBDownloadGroup();
                        break;
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
                    PBFragment.this.loadContact((String) message.obj);
                } catch (Exception unused) {
                    Log.d(PBFragment.TAG, "handleMessage: MSG_PB_SEARCH Exception");
                    PBFragment.this.mHandler.sendEmptyMessage(6);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "PBFragment onAttach");
        this.mHandler = new MainHandler(this);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        Log.d(TAG, "onCreate");
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        getActivity().registerReceiver(this.pbReceiver, intentFilter);
        LocalBluetoothAdapterManager adapterManager = MyApplication.getInstance().getAdapterManager();
        this.mAdapterManager = adapterManager;
        adapterManager.addConnectListener(this.mAdapterListener);
        this.mAdapterManager.registerPbapCallback(this.mIPbapCallback);
        HandlerThread handlerThread = new HandlerThread("loader_thread");
        this.mLoaderThread = handlerThread;
        handlerThread.start();
        this.mLoaderHandler = new LoaderHandler(this.mLoaderThread.getLooper());
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d(TAG, "onCreateView");
        View viewOnCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = SkinUtils.getContext();
        initView(viewOnCreateView);
        initAdapterView();
        return viewOnCreateView;
    }

    private void initAdapterView() {
        PBListAdapter pBListAdapter = new PBListAdapter(this.mContext, this.mPBList, SkinUtils.getId(C0414R.layout.phonebook_listitem), new String[]{ITEM_PHONEBOOK_NAME, ITEM_PHONEBOOK_NUMBER, ITEM_PHONEBOOK_PATH}, new int[]{SkinUtils.getId(C0414R.id.item_phonebook_name), SkinUtils.getId(C0414R.id.item_phonebook_number), SkinUtils.getId(C0414R.id.btn_phonebook_path)});
        this.mPBListAdapter = pBListAdapter;
        this.mPBGridView.setAdapter((ListAdapter) pBListAdapter);
        this.mPBGridView.setOnItemClickListener(this.mPhoneBookListClickListener);
        this.mPBGridView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.autochips.bluetooth.fragment.PBFragment.1
            int first_position = 0;

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i) {
                HashMap<String, String> item;
                Log.d(PBFragment.TAG, "onScrollStateChanged: state=" + i + " pos=" + PBFragment.this.mPBGridView.getFirstVisiblePosition());
                if (i != 0 || (item = PBFragment.this.mPBListAdapter.getItem(this.first_position)) == null) {
                    return;
                }
                String str = item.get(PBFragment.ITEM_PHONEBOOK_LABEL);
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                char cCharAt = str.charAt(0);
                if (cCharAt < 'A' || cCharAt > 'Z') {
                    if (PBFragment.this.mSideBar != null) {
                        PBFragment.this.mSideBar.setChoose(26);
                    }
                } else {
                    int i2 = cCharAt - 'A';
                    if (PBFragment.this.mSideBar != null) {
                        PBFragment.this.mSideBar.setChoose(i2);
                    }
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                this.first_position = i;
            }
        });
    }

    private void initView(View view) {
        if (view == null) {
            Log.e(TAG, "initView roo is null");
            return;
        }
        Button button = (Button) view.findViewById(SkinUtils.getId(C0414R.id.bt_phonebook_syn));
        this.mUpdatePBCtrl = button;
        button.setOnClickListener(this);
        this.mContactCountTextView = (TextView) view.findViewById(SkinUtils.getId(C0414R.id.tv_contact_count));
        View viewFindViewById = view.findViewById(SkinUtils.getId(C0414R.id.btn_contact_clear));
        this.mClearContacts = viewFindViewById;
        if (viewFindViewById != null) {
            if (Build.VERSION.SDK_INT >= 29) {
                this.mClearContacts.setOnClickListener(this);
            } else {
                this.mClearContacts.setVisibility(8);
            }
        }
        EditText editText = (EditText) view.findViewById(SkinUtils.getId(C0414R.id.edit_search_text));
        this.mEditSearchText = editText;
        if (editText != null) {
            editText.clearFocus();
            this.mEditSearchText.setHint(SkinUtils.getString(C0414R.string.bt_phonebook_text_hint));
            this.mEditSearchText.addTextChangedListener(this.mSearchEditWatcher);
        }
        EditText editText2 = (EditText) view.findViewById(SkinUtils.getId(C0414R.id.search_edittext));
        this.mSearchText = editText2;
        if (editText2 != null) {
            editText2.clearFocus();
            this.mSearchText.setHint(String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.bt_phonebook_search_hint), 0));
            this.mSearchText.addTextChangedListener(this.mSearchEditWatcher);
        }
        SideBar sideBar = (SideBar) view.findViewById(SkinUtils.getId(C0414R.id.phonebook_sidebar));
        this.mSideBar = sideBar;
        if (sideBar != null) {
            sideBar.setOnTouchingLetterChangedListener(this);
        }
        this.mDownloadLayout = (LinearLayout) view.findViewById(SkinUtils.getId(C0414R.id.download_phonebook_layout));
        ProgressBar progressBar = (ProgressBar) view.findViewById(SkinUtils.getId(C0414R.id.progressbar_download_phonebook));
        if (progressBar != null) {
            progressBar.setIndeterminate(false);
        }
        this.mDownloadTextView = (TextView) view.findViewById(SkinUtils.getId(C0414R.id.tv_download_phonebook_text));
        this.mPBGridView = (AbsListView) view.findViewById(SkinUtils.getId(C0414R.id.phonebook_listview));
        this.mPBList = new ArrayList<>();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void setUserVisibleHint(boolean z) {
        Log.d(TAG, "setUserVisibleHint:" + z);
        super.setUserVisibleHint(z);
        if (z) {
            LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mAdapterManager;
            if (localBluetoothAdapterManager == null || !localBluetoothAdapterManager.isReady()) {
                return;
            }
            if (this.mAdapterManager.getConnectDevice() != null) {
                if (this.mAdapterManager.getPbapDownLoadState(2)) {
                    showPBDownloadGroup();
                    return;
                }
                hidePBDownloadGroup();
                if (this.mPBList.isEmpty()) {
                    startLoadContact(SkinID.SKIN_NONE);
                    return;
                }
                return;
            }
            clearContactList();
            return;
        }
        hideSoftInput();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        Log.d(TAG, "onHiddenChanged hidden=" + z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (getContext() != null) {
            getContext().unregisterReceiver(this.pbReceiver);
        }
        LocalBluetoothAdapterManager localBluetoothAdapterManager = this.mAdapterManager;
        if (localBluetoothAdapterManager != null) {
            localBluetoothAdapterManager.unregisterPbapCallback(this.mIPbapCallback);
            this.mAdapterManager.removeConnectListener(this.mAdapterListener);
        }
        HandlerThread handlerThread = this.mLoaderThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        super.onDestroy();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
        MainHandler mainHandler = this.mHandler;
        if (mainHandler != null) {
            mainHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int viewId = SkinUtils.getViewId(view);
        if (viewId != C0414R.id.bt_phonebook_syn) {
            if (viewId != C0414R.id.btn_contact_clear) {
                return;
            }
            clearContactList();
        } else {
            hideSoftInput();
            if (this.mAdapterManager.pbapStartDownLoad(2)) {
                return;
            }
            Utils.showToast(getContext(), SkinUtils.getString(C0414R.string.str_download_wait_history_tip));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPBDownloadGroup() {
        this.mHandler.removeMessages(6);
        this.mUpdatePBCtrl.setEnabled(false);
        changeDownloadHint(0);
        this.mDownloadLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hidePBDownloadGroup() {
        this.mDownloadLayout.setVisibility(8);
        this.mUpdatePBCtrl.setEnabled(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeDownloadHint(int i) {
        if (isAdded()) {
            String str = String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.str_download_phonebook), Integer.valueOf(i));
            if (this.mDownloadTextView != null) {
                Log.d(TAG, "changeDownloadHint:" + str);
                this.mDownloadTextView.setText(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pbapConnectStateChange(int i) {
        Log.i(TAG, "pbapConnectStateChange: " + i);
    }

    public void clearContactList() {
        if (!TextUtils.isEmpty(this.mPBListAdapter.mSearchLetter)) {
            this.mPBListAdapter.setSearchLetter(SkinID.SKIN_NONE);
        }
        if (this.mPBList.size() > 0) {
            this.mPBList.clear();
            this.mPBListAdapter.notifyDataSetChanged();
        }
        SideBar sideBar = this.mSideBar;
        if (sideBar != null) {
            sideBar.setChoose(-1);
        }
        TextView textView = this.mContactCountTextView;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.bt_phonebook_search_hint), 0));
        }
        EditText editText = this.mSearchText;
        if (editText != null) {
            editText.setHint(String.format(Locale.getDefault(), SkinUtils.getString(C0414R.string.bt_phonebook_search_hint), 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startLoadContact(String str) {
        Log.d(TAG, "startLoadContact");
        cancelLoadContact();
        clearContactList();
        Message messageObtain = Message.obtain(this.mLoaderHandler, 1);
        if (TextUtils.isEmpty(str)) {
            str = SkinID.SKIN_NONE;
        }
        messageObtain.obj = str;
        this.mLoaderHandler.sendMessage(messageObtain);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelLoadContact() {
        Log.d(TAG, "cancelLoadContact!!!");
        this.mLoaderHandler.removeMessages(1);
        if (this.mCancellationSignal != null) {
            Log.d(TAG, "mCancellationSignal cancel!!!");
            this.mCancellationSignal.cancel();
        }
    }

    public void loadContact(String str) {
        Cursor cursorQuery;
        if (getContext() == null) {
            Log.e(TAG, "getContext()==null!");
            this.mHandler.sendMessage(Message.obtain(this.mHandler, 4));
            return;
        }
        BluetoothDeviceInfo connectDevice = this.mAdapterManager.getConnectDevice();
        if (connectDevice == null) {
            Log.e(TAG, "loadContact deviceInfo==null!");
            this.mHandler.sendMessage(Message.obtain(this.mHandler, 4));
            return;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        String str2 = "display_name";
        String str3 = PHONEBOOK_LABEL;
        if (Utils.isReverseName()) {
            str2 = "display_name_alt";
            str3 = PHONEBOOK_LABEL_ALT;
        }
        String str4 = str3;
        try {
            synchronized (this) {
                CancellationSignal cancellationSignal = this.mCancellationSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    this.mCancellationSignal = null;
                }
                this.mCancellationSignal = new CancellationSignal();
            }
            if (TextUtils.isEmpty(str)) {
                cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{str2, "data1", str4}, "account_name=? AND account_type=? ", new String[]{connectDevice.getDeviceAddr(), Utils.ACCOUNT_TYPE}, "sort_key COLLATE LOCALIZED asc", this.mCancellationSignal);
            } else {
                cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{str2, "data1", str4}, String.format(Locale.ENGLISH, "(%s LIKE ? or %s LIKE ?) AND %s=? AND %s=?", "data1", str2, "account_name", "account_type"), new String[]{"%" + str + "%", "%" + str + "%", connectDevice.getDeviceAddr(), Utils.ACCOUNT_TYPE}, "sort_key COLLATE LOCALIZED asc", this.mCancellationSignal);
            }
            ArrayList arrayList = new ArrayList();
            this.mHandler.sendEmptyMessage(3);
            while (cursorQuery.moveToNext() && !this.mLoaderHandler.hasMessages(1) && !this.mCancellationSignal.isCanceled()) {
                HashMap map = new HashMap();
                String string = cursorQuery.getString(cursorQuery.getColumnIndex(str2));
                String string2 = cursorQuery.getString(cursorQuery.getColumnIndex("data1"));
                String string3 = cursorQuery.getString(cursorQuery.getColumnIndex(str4));
                map.put(ITEM_PHONEBOOK_NAME, string);
                map.put(ITEM_PHONEBOOK_NUMBER, string2);
                map.put(ITEM_PHONEBOOK_PATH, "1");
                map.put(ITEM_PHONEBOOK_LABEL, string3);
                arrayList.add(map);
                if (arrayList.size() >= 10) {
                    Message messageObtain = Message.obtain(this.mHandler, 5);
                    messageObtain.obj = arrayList;
                    this.mHandler.sendMessage(messageObtain);
                    arrayList = new ArrayList();
                }
            }
            cursorQuery.close();
            if (this.mCancellationSignal.isCanceled()) {
                Log.d(TAG, "loadContact: CancelLoader!!");
                this.mHandler.removeMessages(5);
            } else {
                Log.d(TAG, "loadContact finish [list.size ]= " + arrayList.size());
                Message messageObtain2 = Message.obtain(this.mHandler, 4);
                messageObtain2.obj = arrayList;
                this.mHandler.sendMessage(messageObtain2);
            }
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        } catch (OperationCanceledException unused) {
            Log.d(TAG, "loadContact: OperationCanceledException!!!!!!");
            Cursor cursor = null;
            cursor.close();
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        }
    }

    private void hideSoftInput() {
        if (getContext() == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager.isActive()) {
            EditText editText = this.mSearchText;
            if (editText != null) {
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                return;
            }
            EditText editText2 = this.mEditSearchText;
            if (editText2 != null) {
                inputMethodManager.hideSoftInputFromWindow(editText2.getWindowToken(), 0);
            }
        }
    }

    class PBListAdapter extends SimpleAdapter {
        private ArrayList<HashMap<String, String>> mList;
        private String mSearchLetter;
        private ArrayList<HashMap<String, String>> mSearchPBList;
        private int mSelectIdx;

        public PBListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, int i, String[] strArr, int[] iArr) {
            super(context, arrayList, i, strArr, iArr);
            this.mSelectIdx = -1;
            this.mSearchLetter = SkinID.SKIN_NONE;
            this.mSelectIdx = -1;
            this.mList = arrayList;
            this.mSearchPBList = new ArrayList<>();
        }

        public void setSelect(int i) {
            this.mSelectIdx = i;
        }

        public void setSearchLetter(String str) {
            this.mSearchLetter = str != null ? str.toUpperCase() : SkinID.SKIN_NONE;
            this.mSearchPBList.clear();
            if (TextUtils.isEmpty(this.mSearchLetter)) {
                notifyDataSetChanged();
                return;
            }
            if (NightX.DARK_TAG_SEGMENTS_SEPARATOR.equals(this.mSearchLetter)) {
                for (HashMap<String, String> map : this.mList) {
                    String str2 = map.get(PBFragment.ITEM_PHONEBOOK_LABEL);
                    if (TextUtils.isEmpty(str2) || !Character.isLetter(str2.charAt(0))) {
                        this.mSearchPBList.add(map);
                    }
                }
            } else {
                for (HashMap<String, String> map2 : this.mList) {
                    String str3 = map2.get(PBFragment.ITEM_PHONEBOOK_LABEL);
                    if (!TextUtils.isEmpty(str3) && str3.toUpperCase().startsWith(this.mSearchLetter)) {
                        this.mSearchPBList.add(map2);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public int getCount() {
            if (!TextUtils.isEmpty(this.mSearchLetter)) {
                return this.mSearchPBList.size();
            }
            ArrayList<HashMap<String, String>> arrayList = this.mList;
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public HashMap<String, String> getItem(int i) {
            if (!TextUtils.isEmpty(this.mSearchLetter)) {
                if (i < this.mSearchPBList.size()) {
                    return this.mSearchPBList.get(i);
                }
                return null;
            }
            ArrayList<HashMap<String, String>> arrayList = this.mList;
            if (arrayList == null || i >= arrayList.size()) {
                return null;
            }
            return this.mList.get(i);
        }

        public int getSelect() {
            return this.mSelectIdx;
        }

        @Override // android.widget.SimpleAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout;
            ViewHolder viewHolder;
            String str;
            String str2;
            if (view == null) {
                viewHolder = new ViewHolder();
                layout = SkinUtils.getLayout(C0414R.layout.phonebook_listitem);
                viewHolder.nameTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_phonebook_name));
                viewHolder.phoneTextView = (TextView) layout.findViewById(SkinUtils.getId(C0414R.id.item_phonebook_number));
                viewHolder.pathImageView = (ImageView) layout.findViewById(SkinUtils.getId(C0414R.id.btn_phonebook_path));
                layout.setTag(viewHolder);
            } else {
                layout = view;
                viewHolder = (ViewHolder) view.getTag();
            }
            HashMap<String, String> item = getItem(i);
            String str3 = SkinID.SKIN_NONE;
            if (item != null) {
                String string = item.get(PBFragment.ITEM_PHONEBOOK_NAME);
                if (TextUtils.isEmpty(string)) {
                    string = SkinUtils.getString(C0414R.string.phonebook_unknow_name);
                }
                str3 = String.format(Locale.getDefault(), "%d. %s", Integer.valueOf(i + 1), string);
                str = item.get(PBFragment.ITEM_PHONEBOOK_NUMBER);
                str2 = item.get(PBFragment.ITEM_PHONEBOOK_PATH);
            } else {
                str = SkinID.SKIN_NONE;
                str2 = str;
            }
            if (viewHolder.nameTextView != null) {
                viewHolder.nameTextView.setText(str3);
            }
            if (viewHolder.phoneTextView != null) {
                viewHolder.phoneTextView.setText(str);
            }
            if (viewHolder.pathImageView != null) {
                if ("1".equals(str2)) {
                    viewHolder.pathImageView.setImageDrawable(SkinUtils.getDrawable(C0414R.drawable.bt_phonebook_path_sim));
                } else {
                    viewHolder.pathImageView.setImageDrawable(SkinUtils.getDrawable(C0414R.drawable.bt_phonebook_path_phone));
                }
            }
            return layout;
        }

        public final class ViewHolder {
            public TextView nameTextView;
            public ImageView pathImageView;
            public TextView phoneTextView;

            public ViewHolder() {
            }
        }
    }
}
