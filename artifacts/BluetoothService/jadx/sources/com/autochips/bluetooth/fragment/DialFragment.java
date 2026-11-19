package com.autochips.bluetooth.fragment;

import android.bluetooth.BluetoothHeadsetClientCall;
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
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.autochips.bluetooth.C0414R;
import com.autochips.bluetooth.MyApplication;
import com.autochips.bluetooth.skin.SkinID;
import com.autochips.bluetooth.skin.SkinUtils;
import com.hcn.bluetooth.api.BluetoothDeviceInfo;
import com.hcn.bluetooth.api.LocalBluetoothAdapterManager;
import com.hcn.bluetooth.api.LocalBluetoothHfpclientManager;
import com.hcn.bluetooth.api.Utils;
import com.hcn.skin.support.app.SkinCompatFragment;
import com.hcn.skinx_night.NightX;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DialFragment extends SkinCompatFragment implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener {
    public static final String ACTION_CALL_CHANGED = "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED";
    public static final String EXTRA_CALL = "android.bluetooth.headsetclient.extra.CALL";
    private static final String ITEM_PHONEBOOK_NAME = "item_phonebook_name";
    private static final String ITEM_PHONEBOOK_NUMBER = "item_phonebook_number";
    private static final String ITEM_PHONEBOOK_PATH = "item_phonebook_path";
    private static final int LOAD_CONTACT_ON_CONNECTED_DELAY = 200;
    public static final int MSG_CLEAR_PBLIST = 1;
    public static final int MSG_LOAD_CONTACT = 3;
    public static final int MSG_PB_SEARCH = 4;
    public static final int MSG_UI_PB_SEARCH_ADD = 2;
    private static String SEARCH_SELECTION = String.format(Locale.ENGLISH, "%s LIKE ? AND %s=? AND %s =?", "data1", "account_name", "account_type");
    private static final String TAG = "DialFragment";
    EditText callNumEditText;
    private LocalBluetoothAdapterManager mAdapterManager;
    private contactLoaderHandle mContactLoaderHandle;
    private ListView mListViews;
    private ArrayList<HashMap<String, String>> mPBList;
    private ListAdapter mPBListAdapter;
    private Context mParentContext;
    private View mViewRoot;
    private HandlerThread mcontactLoaderThread;
    private Toast mToast = null;
    private CancellationSignal mSearchCancellationSignal = null;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.autochips.bluetooth.fragment.DialFragment.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(DialFragment.TAG, "action = " + action);
            if (action.equals(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("android.bluetooth.profile.extra.STATE", 0);
                if (intExtra == 2) {
                    DialFragment.this.mContactLoaderHandle.sendEmptyMessageDelayed(3, 200L);
                    return;
                } else {
                    if (intExtra == 0) {
                        DialFragment.this.clearPBList();
                        DialFragment.this.deleteAllDialPadString();
                        return;
                    }
                    return;
                }
            }
            if (action.equals(DialFragment.ACTION_CALL_CHANGED)) {
                int state = ((BluetoothHeadsetClientCall) intent.getParcelableExtra(DialFragment.EXTRA_CALL)).getState();
                Log.d(DialFragment.TAG, "state = " + state);
                if (state != 7 || DialFragment.this.callNumEditText == null) {
                    return;
                }
                DialFragment.this.callNumEditText.setText(SkinID.SKIN_NONE);
            }
        }
    };
    private TextWatcher mSearchTextWatcher = new TextWatcher() { // from class: com.autochips.bluetooth.fragment.DialFragment.2
        String mSearchKey = SkinID.SKIN_NONE;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            String string = DialFragment.this.callNumEditText.getText().toString();
            Log.d(DialFragment.TAG, "afterTextChanged: search=" + string);
            if (string.length() < 2) {
                DialFragment.this.clearPBList();
                return;
            }
            if (string.equals(this.mSearchKey)) {
                return;
            }
            this.mSearchKey = string;
            DialFragment.this.clearPBList();
            Message messageObtainMessage = DialFragment.this.mContactLoaderHandle.obtainMessage(4, this.mSearchKey);
            DialFragment.this.mContactLoaderHandle.removeMessages(4);
            DialFragment.this.mContactLoaderHandle.sendMessage(messageObtainMessage);
            synchronized (DialFragment.this) {
                if (DialFragment.this.mSearchCancellationSignal != null) {
                    DialFragment.this.mSearchCancellationSignal.cancel();
                }
            }
        }
    };
    private Handler mHandler = new Handler() { // from class: com.autochips.bluetooth.fragment.DialFragment.3
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 1) {
                DialFragment.this.clearPBList();
            } else {
                if (i != 2) {
                    return;
                }
                SearchResult searchResult = (SearchResult) message.obj;
                DialFragment.this.mPBList.addAll(searchResult.pbList);
                DialFragment.this.mPBListAdapter.setSearchText(searchResult.searchKey);
                DialFragment.this.mPBListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public int getLayoutRes() {
        return C0414R.layout.bt_call;
    }

    @Override // com.hcn.skin.support.observe.SkinLayoutObserver
    public void onBindViewData() {
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mAdapterManager = MyApplication.getInstance().getAdapterManager();
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mViewRoot = super.onCreateView(layoutInflater, viewGroup, bundle);
        HandlerThread handlerThread = new HandlerThread("contactloader_thread");
        this.mcontactLoaderThread = handlerThread;
        handlerThread.start();
        this.mContactLoaderHandle = new contactLoaderHandle(this.mcontactLoaderThread.getLooper());
        this.mParentContext = SkinUtils.getContext();
        initView();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocalBluetoothAdapterManager.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(ACTION_CALL_CHANGED);
        getActivity().registerReceiver(this.mReceiver, intentFilter);
        return this.mViewRoot;
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDetach() {
        super.onDetach();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        Log.d(TAG, "onKeyDown:keyCode=" + i);
        switch (i) {
            case 7:
                addDialPadInputString("0");
                break;
            case 8:
                addDialPadInputString("1");
                break;
            case 9:
                addDialPadInputString("2");
                break;
            case 10:
                addDialPadInputString("3");
                break;
            case 11:
                addDialPadInputString("4");
                break;
            case 12:
                addDialPadInputString("5");
                break;
            case 13:
                addDialPadInputString("6");
                break;
            case 14:
                addDialPadInputString("7");
                break;
            case 15:
                addDialPadInputString("8");
                break;
            case 16:
                addDialPadInputString("9");
                break;
        }
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        Log.d(TAG, "onKeyUp:keyCode=" + i);
        return true;
    }

    @Override // com.hcn.skin.support.app.SkinCompatFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        int viewId = SkinUtils.getViewId(view);
        if (viewId == C0414R.id.btn_del_num) {
            deleteAllDialPadString();
            return true;
        }
        if (viewId != C0414R.id.btn_call_zero) {
            return false;
        }
        addDialPadInputString("+");
        return true;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int viewId = SkinUtils.getViewId(view);
        if (viewId != C0414R.id.btn_del_num) {
            switch (viewId) {
                case C0414R.id.btn_call /* 2131230812 */:
                    onCallClick();
                    break;
                case C0414R.id.btn_call_asterisk /* 2131230813 */:
                    addDialPadInputString("*");
                    break;
                case C0414R.id.btn_call_eight /* 2131230814 */:
                    addDialPadInputString("8");
                    break;
                case C0414R.id.btn_call_five /* 2131230815 */:
                    addDialPadInputString("5");
                    break;
                case C0414R.id.btn_call_four /* 2131230816 */:
                    addDialPadInputString("4");
                    break;
                default:
                    switch (viewId) {
                        case C0414R.id.btn_call_nine /* 2131230818 */:
                            addDialPadInputString("9");
                            break;
                        case C0414R.id.btn_call_one /* 2131230819 */:
                            addDialPadInputString("1");
                            break;
                        case C0414R.id.btn_call_pound /* 2131230820 */:
                            addDialPadInputString(NightX.DARK_TAG_SEGMENTS_SEPARATOR);
                            break;
                        case C0414R.id.btn_call_seven /* 2131230821 */:
                            addDialPadInputString("7");
                            break;
                        case C0414R.id.btn_call_six /* 2131230822 */:
                            addDialPadInputString("6");
                            break;
                        case C0414R.id.btn_call_three /* 2131230823 */:
                            addDialPadInputString("3");
                            break;
                        case C0414R.id.btn_call_two /* 2131230824 */:
                            addDialPadInputString("2");
                            break;
                        case C0414R.id.btn_call_zero /* 2131230825 */:
                            addDialPadInputString("0");
                            break;
                    }
                    break;
            }
        }
        deleteOneDialPadString();
    }

    private void initView() {
        try {
            this.mListViews = (ListView) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.listview_calls));
            this.mPBList = new ArrayList<>();
            ListAdapter listAdapter = new ListAdapter(this.mParentContext, this.mPBList);
            this.mPBListAdapter = listAdapter;
            this.mListViews.setAdapter((android.widget.ListAdapter) listAdapter);
            this.mListViews.setOnItemClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_zero)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_zero)).setOnLongClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_one)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_two)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_three)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_four)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_five)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_six)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_seven)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_eight)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_nine)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_asterisk)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_call_pound)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_del_num)).setOnClickListener(this);
            this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.btn_del_num)).setOnLongClickListener(this);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        EditText editText = (EditText) this.mViewRoot.findViewById(SkinUtils.getId(C0414R.id.text_call_info));
        this.callNumEditText = editText;
        editText.addTextChangedListener(this.mSearchTextWatcher);
        EditText editText2 = this.callNumEditText;
        if (editText2 != null) {
            editText2.setInputType(0);
            this.callNumEditText.setText(SkinID.SKIN_NONE);
            this.callNumEditText.setFocusable(false);
        }
    }

    private void onCallClick() {
        if (getCallNumber().length() == 0) {
            addDialPadInputString(getLastDialNumber());
        } else {
            call(getCallNumber());
        }
    }

    private void call(String str) {
        if (!this.mAdapterManager.isBluetoothConnected()) {
            Log.e(TAG, "device is not connected!");
        } else if (checkCallNumberVaild()) {
            MyApplication.getInstance().getHfpclientManager().dial(str);
        } else {
            showToast(SkinUtils.getString(C0414R.string.str_call_number_is_empty));
        }
    }

    private String getCallNumber() {
        EditText editText = this.callNumEditText;
        return editText != null ? editText.getText().toString() : SkinID.SKIN_NONE;
    }

    private boolean checkCallNumberVaild() {
        EditText editText = this.callNumEditText;
        return editText != null && editText.getText().length() > 0;
    }

    private void addDialPadInputString(CharSequence charSequence) {
        EditText editText;
        if (charSequence == null || (editText = this.callNumEditText) == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        Editable text = this.callNumEditText.getText();
        if (text.length() > 14) {
            showToast(SkinUtils.getString(C0414R.string.str_call_number_is_too_long));
            return;
        }
        if (selectionStart < 0 || selectionStart > text.length()) {
            text.append(charSequence);
        } else {
            text.insert(selectionStart, charSequence);
        }
        this.callNumEditText.setText(text);
        this.callNumEditText.setSelection(selectionStart + charSequence.length());
    }

    private void deleteOneDialPadString() {
        EditText editText = this.callNumEditText;
        if (editText == null) {
            return;
        }
        int selectionStart = editText.getSelectionStart();
        Editable text = this.callNumEditText.getText();
        if (selectionStart >= 1) {
            int i = selectionStart - 1;
            text.delete(i, selectionStart);
            this.callNumEditText.setText(text);
            this.callNumEditText.setSelection(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteAllDialPadString() {
        EditText editText = this.callNumEditText;
        if (editText == null) {
            return;
        }
        editText.setText(SkinID.SKIN_NONE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearPBList() {
        ArrayList<HashMap<String, String>> arrayList = this.mPBList;
        if (arrayList != null) {
            arrayList.clear();
        }
        ListAdapter listAdapter = this.mPBListAdapter;
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    private final class SearchResult {
        public ArrayList<HashMap<String, String>> pbList;
        public String searchKey;

        private SearchResult() {
        }
    }

    private class contactLoaderHandle extends Handler {
        public contactLoaderHandle(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 4) {
                return;
            }
            try {
                searchContact((String) message.obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void searchContact(String str) throws Exception {
            Cursor cursorQuery;
            BluetoothDeviceInfo connectDevice = DialFragment.this.mAdapterManager.getConnectDevice();
            if (connectDevice == null) {
                Log.e(DialFragment.TAG, "searchContact device is not connected!");
                DialFragment.this.mHandler.sendEmptyMessage(1);
                return;
            }
            Log.d(DialFragment.TAG, "searchContact key : " + str);
            ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
            ContentResolver contentResolver = DialFragment.this.getContext().getContentResolver();
            String str2 = Utils.isReverseName() ? "display_name_alt" : "display_name";
            try {
                try {
                    synchronized (DialFragment.this) {
                        DialFragment.this.mSearchCancellationSignal = new CancellationSignal();
                    }
                    cursorQuery = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{str2, "data1"}, DialFragment.SEARCH_SELECTION, new String[]{"%" + str + "%", connectDevice.getDeviceAddr(), Utils.ACCOUNT_TYPE}, null, DialFragment.this.mSearchCancellationSignal);
                    while (cursorQuery.moveToNext()) {
                        try {
                            String string = cursorQuery.getString(cursorQuery.getColumnIndex("data1"));
                            String string2 = cursorQuery.getString(cursorQuery.getColumnIndex(str2));
                            HashMap<String, String> map = new HashMap<>();
                            map.put(DialFragment.ITEM_PHONEBOOK_NAME, string2);
                            map.put(DialFragment.ITEM_PHONEBOOK_NUMBER, string);
                            map.put(DialFragment.ITEM_PHONEBOOK_PATH, SkinID.SKIN_NONE);
                            arrayList.add(map);
                            if (arrayList.size() >= 10) {
                                SearchResult searchResult = new SearchResult();
                                searchResult.searchKey = str;
                                searchResult.pbList = arrayList;
                                DialFragment.this.mHandler.sendMessage(Message.obtain(DialFragment.this.mHandler, 2, searchResult));
                                arrayList = new ArrayList<>();
                            }
                        } catch (OperationCanceledException e) {
                            e = e;
                            e.printStackTrace();
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            synchronized (DialFragment.this) {
                                DialFragment.this.mSearchCancellationSignal = null;
                            }
                            synchronized (DialFragment.this) {
                                DialFragment.this.mSearchCancellationSignal = null;
                            }
                            return;
                        }
                    }
                    cursorQuery.close();
                    synchronized (DialFragment.this) {
                        DialFragment.this.mSearchCancellationSignal = null;
                    }
                    if (arrayList.size() != 0) {
                        SearchResult searchResult2 = new SearchResult();
                        searchResult2.searchKey = str;
                        searchResult2.pbList = arrayList;
                        DialFragment.this.mHandler.sendMessage(Message.obtain(DialFragment.this.mHandler, 2, searchResult2));
                    }
                } catch (OperationCanceledException e2) {
                    e = e2;
                    cursorQuery = null;
                }
            } catch (Throwable th) {
                synchronized (DialFragment.this) {
                    DialFragment.this.mSearchCancellationSignal = null;
                    throw th;
                }
            }
        }
    }

    class ViewHolder {
        public TextView mContactName;
        public TextView mContactNum;

        public ViewHolder(TextView textView, TextView textView2) {
            this.mContactName = textView;
            this.mContactNum = textView2;
        }
    }

    class ListAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<HashMap<String, String>> mPBDatas;
        private String mSearchText;
        private LayoutInflater mlayoutInflater;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return 0L;
        }

        public ListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
            this.mContext = context;
            this.mPBDatas = arrayList;
            this.mlayoutInflater = LayoutInflater.from(context);
        }

        public void setSearchText(String str) {
            this.mSearchText = str;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mPBDatas.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return this.mPBDatas.get(i);
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.mlayoutInflater.inflate(SkinUtils.getId(C0414R.layout.dial_listitem), viewGroup, false);
                viewHolder = DialFragment.this.new ViewHolder((TextView) view.findViewById(SkinUtils.getId(C0414R.id.dial_name)), (TextView) view.findViewById(SkinUtils.getId(C0414R.id.dial_num)));
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            String str = this.mPBDatas.get(i).get(DialFragment.ITEM_PHONEBOOK_NUMBER);
            if (str.contains(this.mSearchText)) {
                int iIndexOf = str.indexOf(this.mSearchText);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(SkinUtils.getColor(C0414R.color.dial_tv_color)), iIndexOf, this.mSearchText.length() + iIndexOf, 34);
                viewHolder.mContactNum.setText(spannableStringBuilder);
            } else {
                viewHolder.mContactNum.setText(this.mPBDatas.get(i).get(DialFragment.ITEM_PHONEBOOK_NUMBER));
            }
            viewHolder.mContactName.setText(this.mPBDatas.get(i).get(DialFragment.ITEM_PHONEBOOK_NAME));
            return view;
        }
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        call(this.mPBList.get(i).get(ITEM_PHONEBOOK_NUMBER));
    }

    private String getLastDialNumber() {
        LocalBluetoothHfpclientManager hfpclientManager = MyApplication.getInstance().getHfpclientManager();
        return hfpclientManager != null ? hfpclientManager.getLastCall() : SkinID.SKIN_NONE;
    }

    private void showToast(String str) {
        Toast toast = this.mToast;
        if (toast != null) {
            toast.cancel();
        }
        Toast toastMakeText = Toast.makeText(this.mParentContext, str, 0);
        this.mToast = toastMakeText;
        toastMakeText.show();
    }
}
