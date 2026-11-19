package com.android.systemui.statusbar.car;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.car.widget.PagedListView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.users.UserManagerHelper;
import com.android.systemui.R;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class UserGridRecyclerView extends PagedListView implements UserManagerHelper.OnUsersUpdateListener {
    private UserAdapter mAdapter;
    private Context mContext;
    private UserManagerHelper mUserManagerHelper;
    private UserSelectionListener mUserSelectionListener;

    interface UserSelectionListener {
        void onUserSelected(UserRecord userRecord);
    }

    public UserGridRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mUserManagerHelper = new UserManagerHelper(this.mContext);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mUserManagerHelper.registerOnUsersUpdateListener(this);
    }

    @Override // androidx.car.widget.PagedListView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mUserManagerHelper.unregisterOnUsersUpdateListener();
    }

    public void buildAdapter() {
        List<UserRecord> userRecords = createUserRecords(this.mUserManagerHelper.getAllUsers());
        this.mAdapter = new UserAdapter(this.mContext, userRecords);
        super.setAdapter(this.mAdapter);
    }

    private List<UserRecord> createUserRecords(List<UserInfo> userInfoList) {
        List<UserRecord> userRecords = new ArrayList<>();
        for (UserInfo userInfo : userInfoList) {
            if (!userInfo.isGuest()) {
                boolean isForeground = this.mUserManagerHelper.getForegroundUserId() == userInfo.id;
                UserRecord record = new UserRecord(userInfo, false, false, isForeground);
                userRecords.add(record);
            }
        }
        if (!this.mUserManagerHelper.foregroundUserIsGuestUser()) {
            userRecords.add(addGuestUserRecord());
        }
        if (this.mUserManagerHelper.foregroundUserCanAddUsers()) {
            userRecords.add(addUserRecord());
        }
        return userRecords;
    }

    private UserRecord addGuestUserRecord() {
        UserInfo userInfo = new UserInfo();
        userInfo.name = this.mContext.getString(R.string.car_guest);
        return new UserRecord(userInfo, true, false, false);
    }

    private UserRecord addUserRecord() {
        UserInfo userInfo = new UserInfo();
        userInfo.name = this.mContext.getString(R.string.car_add_user);
        return new UserRecord(userInfo, false, true, false);
    }

    public void setUserSelectionListener(UserSelectionListener userSelectionListener) {
        this.mUserSelectionListener = userSelectionListener;
    }

    @Override // com.android.settingslib.users.UserManagerHelper.OnUsersUpdateListener
    public void onUsersUpdate() {
        this.mAdapter.clearUsers();
        this.mAdapter.updateUsers(createUserRecords(this.mUserManagerHelper.getAllUsers()));
        this.mAdapter.notifyDataSetChanged();
    }

    public final class UserAdapter extends RecyclerView.Adapter<UserAdapterViewHolder> implements DialogInterface.OnClickListener {
        private UserRecord mAddUserRecord;
        private View mAddUserView;
        private final Context mContext;
        private AlertDialog mDialog;
        private final String mGuestName;
        private final String mNewUserName;
        private final Resources mRes;
        private List<UserRecord> mUsers;

        public UserAdapter(Context context, List<UserRecord> users) {
            this.mRes = context.getResources();
            this.mContext = context;
            updateUsers(users);
            this.mGuestName = this.mRes.getString(R.string.car_guest);
            this.mNewUserName = this.mRes.getString(R.string.car_new_user);
        }

        public void clearUsers() {
            this.mUsers.clear();
        }

        public void updateUsers(List<UserRecord> users) {
            this.mUsers = users;
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public UserAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(this.mContext).inflate(R.layout.car_fullscreen_user_pod, parent, false);
            view.setAlpha(1.0f);
            view.bringToFront();
            return new UserAdapterViewHolder(view);
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public void onBindViewHolder(final UserAdapterViewHolder holder, int position) {
            final UserRecord userRecord = this.mUsers.get(position);
            RoundedBitmapDrawable circleIcon = RoundedBitmapDrawableFactory.create(this.mRes, getUserRecordIcon(userRecord));
            circleIcon.setCircular(true);
            holder.mUserAvatarImageView.setImageDrawable(circleIcon);
            holder.mUserNameTextView.setText(userRecord.mInfo.name);
            holder.mView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.car.-$$Lambda$UserGridRecyclerView$UserAdapter$n2iLfR_SwwIaOydjDtvjvbVeQ9Y
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    UserGridRecyclerView.UserAdapter.lambda$onBindViewHolder$0(this.f$0, userRecord, holder, view);
                }
            });
        }

        public static /* synthetic */ void lambda$onBindViewHolder$0(UserAdapter userAdapter, UserRecord userRecord, UserAdapterViewHolder holder, View v) {
            if (userRecord == null) {
                return;
            }
            if (userRecord.mIsStartGuestSession) {
                userAdapter.notifyUserSelected(userRecord);
                UserGridRecyclerView.this.mUserManagerHelper.startNewGuestSession(userAdapter.mGuestName);
                return;
            }
            if (userRecord.mIsAddUser) {
                userAdapter.mAddUserView = holder.mView;
                userAdapter.mAddUserView.setEnabled(false);
                String message = userAdapter.mRes.getString(R.string.user_add_user_message_setup).concat(System.getProperty("line.separator")).concat(System.getProperty("line.separator")).concat(userAdapter.mRes.getString(R.string.user_add_user_message_update));
                userAdapter.mAddUserRecord = userRecord;
                userAdapter.mDialog = new AlertDialog.Builder(userAdapter.mContext, 2131886594).setTitle(R.string.user_add_user_title).setMessage(message).setNegativeButton(android.R.string.cancel, userAdapter).setPositiveButton(android.R.string.ok, userAdapter).create();
                SystemUIDialog.applyFlags(userAdapter.mDialog);
                userAdapter.mDialog.show();
                return;
            }
            userAdapter.notifyUserSelected(userRecord);
            UserGridRecyclerView.this.mUserManagerHelper.switchToUser(userRecord.mInfo);
        }

        private void notifyUserSelected(UserRecord userRecord) {
            if (UserGridRecyclerView.this.mUserSelectionListener != null) {
                UserGridRecyclerView.this.mUserSelectionListener.onUserSelected(userRecord);
            }
        }

        private Bitmap getUserRecordIcon(UserRecord userRecord) {
            if (userRecord.mIsStartGuestSession) {
                return UserGridRecyclerView.this.mUserManagerHelper.getGuestDefaultIcon();
            }
            if (!userRecord.mIsAddUser) {
                return UserGridRecyclerView.this.mUserManagerHelper.getUserIcon(userRecord.mInfo);
            }
            return UserIcons.convertToBitmap(this.mContext.getDrawable(R.drawable.car_add_circle_round));
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                notifyUserSelected(this.mAddUserRecord);
                new AddNewUserTask().execute(this.mNewUserName);
            } else if (which == -2 && this.mAddUserView != null) {
                this.mAddUserView.setEnabled(true);
            }
        }

        private class AddNewUserTask extends AsyncTask<String, Void, UserInfo> {
            private AddNewUserTask() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public UserInfo doInBackground(String... userNames) {
                return UserGridRecyclerView.this.mUserManagerHelper.createNewUser(userNames[0]);
            }

            @Override // android.os.AsyncTask
            protected void onPreExecute() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // android.os.AsyncTask
            public void onPostExecute(UserInfo user) {
                if (user != null) {
                    UserGridRecyclerView.this.mUserManagerHelper.switchToUser(user);
                }
            }
        }

        @Override // android.support.v7.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mUsers.size();
        }

        public class UserAdapterViewHolder extends RecyclerView.ViewHolder {
            public ImageView mUserAvatarImageView;
            public TextView mUserNameTextView;
            public View mView;

            public UserAdapterViewHolder(View view) {
                super(view);
                this.mView = view;
                this.mUserAvatarImageView = (ImageView) view.findViewById(R.id.user_avatar);
                this.mUserNameTextView = (TextView) view.findViewById(R.id.user_name);
            }
        }
    }

    public static final class UserRecord {
        public final UserInfo mInfo;
        public final boolean mIsAddUser;
        public final boolean mIsForeground;
        public final boolean mIsStartGuestSession;

        public UserRecord(UserInfo userInfo, boolean isStartGuestSession, boolean isAddUser, boolean isForeground) {
            this.mInfo = userInfo;
            this.mIsStartGuestSession = isStartGuestSession;
            this.mIsAddUser = isAddUser;
            this.mIsForeground = isForeground;
        }
    }
}
