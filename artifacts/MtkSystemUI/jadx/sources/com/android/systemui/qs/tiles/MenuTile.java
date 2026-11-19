package com.android.systemui.qs.tiles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MenuTile extends QSTileImpl<QSTile.BooleanState> {

    @SuppressLint({"HandlerLeak"})
    Handler mHandler;
    private ListAdapter mMenuListAdapter;

    public MenuTile(QSHost host) {
        super(host);
        this.mHandler = new Handler() { // from class: com.android.systemui.qs.tiles.MenuTile.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    MenuTile.this.launchMenuTask();
                }
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean listening) {
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    protected void handleClick() {
        this.mHost.collapsePanels();
        Message msg = this.mHandler.obtainMessage(1);
        this.mHandler.sendMessageDelayed(msg, 1500L);
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_menu_label);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState state, Object arg) {
        state.value = true;
        state.icon = QSTileImpl.ResourceIcon.get(R.drawable.icon_menu);
        state.label = this.mContext.getString(R.string.quick_settings_menu_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchMenuTask() {
        final SystemUIDialog dialog = new SystemUIDialog(this.mContext, 5);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View contentView = View.inflate(this.mContext, R.layout.qs_menu_tile_dialog, null);
        ListView listView = (ListView) contentView.findViewById(R.id.menu_listview);
        String namesString = this.mContext.getString(R.string.quick_settings_menu_names);
        String packagesString = this.mContext.getString(R.string.quick_settings_menu_pkgs);
        ArrayList<String> menuNameList = new ArrayList<>(Arrays.asList(namesString.split(",")));
        final ArrayList<String> packageNameList = new ArrayList<>(Arrays.asList(packagesString.split(",")));
        this.mMenuListAdapter = new ListAdapter(this.mContext, menuNameList, packageNameList);
        listView.setAdapter((android.widget.ListAdapter) this.mMenuListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.qs.tiles.-$$Lambda$MenuTile$EOqaa8gbLHpBzcXkwgEXN39ezPo
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                MenuTile.lambda$launchMenuTask$0(this.f$0, packageNameList, dialog, adapterView, view, i, j);
            }
        });
        dialog.setView(contentView);
        dialog.setCancelable(true);
        dialog.show();
    }

    public static /* synthetic */ void lambda$launchMenuTask$0(MenuTile menuTile, ArrayList packageNameList, SystemUIDialog dialog, AdapterView parent, View view, int position, long id) {
        String packageName = (String) packageNameList.get(position);
        if (1 == position) {
            menuTile.openAppManagement();
        } else {
            menuTile.launchApp(packageName);
        }
        dialog.dismiss();
    }

    private void openAppManagement() {
        try {
            Intent intent = new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS");
            intent.addFlags(268468224);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_menu_message), 1).show();
        }
    }

    private void launchApp(String packageName) {
        try {
            Intent intent = this.mContext.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_menu_message), 1).show();
            } else {
                intent.addFlags(268468224);
                this.mContext.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.quick_settings_menu_message), 1).show();
        }
    }

    class ViewHolder {
        public TextView mMenuName;

        public ViewHolder(TextView menuName) {
            this.mMenuName = menuName;
        }
    }

    class ListAdapter extends BaseAdapter {
        private Context mContext;
        private ArrayList<String> menuNameDatas;
        private LayoutInflater mlayoutInflater;
        private ArrayList<String> packageNameDatas;

        public ListAdapter(Context context, ArrayList<String> mDatas, ArrayList<String> pDatas) {
            this.mContext = context;
            this.packageNameDatas = pDatas;
            this.menuNameDatas = mDatas;
            this.mlayoutInflater = LayoutInflater.from(this.mContext);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.packageNameDatas.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return this.packageNameDatas.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int arg0) {
            return 0L;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mViewHolder;
            if (convertView == null) {
                convertView = this.mlayoutInflater.inflate(R.layout.menu_listitem, parent, false);
                TextView menuName = (TextView) convertView.findViewById(R.id.menu_name);
                mViewHolder = MenuTile.this.new ViewHolder(menuName);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.mMenuName.setText(this.menuNameDatas.get(position));
            return convertView;
        }
    }
}
