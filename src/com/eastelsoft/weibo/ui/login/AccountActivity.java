package com.eastelsoft.weibo.ui.login;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.db.AccountDBTask;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;
import com.eastelsoft.weibo.ui.main.MainActivity;
import com.eastelsoft.weibo.utils.SettingHelper;
import com.eastelsoft.weibo.utils.Utility;

public class AccountActivity extends AbstractAppActivity {

	private ListView listView;
	private AccountAdapter accountAdapter;
	
	private final int ADD_ACCOUNT_REQUEST_CODE = 0;
	
	private List<AccountBean> accountList = new ArrayList<AccountBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//若存在默认账号，则直接进入
		if (getIntent() != null) {
			jumpToMainActivity();
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountactivity_layout);
		
		getActionBar().setTitle(R.string.app_name);
		
		accountAdapter = new AccountAdapter();
		listView = (ListView)this.findViewById(R.id.listView);
		listView.setAdapter(accountAdapter);
		listView.setOnItemClickListener(new ListViewItemClickListener());
		listView.setOnItemLongClickListener(new ListViewItemLongClickListener());
		
		initData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_menu_accountactivity, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_account:
			final ArrayList<String> valueList = new ArrayList<String>();
			valueList.add(getString(R.string.web_login));
			valueList.add(getString(R.string.app_login));
			valueList.add(getString(R.string.black_login));
			
			new AlertDialog.Builder(this)
			   .setItems(valueList.toArray(new String[0]),new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String val = valueList.get(which);
						if (which == 0) {
							Intent intent = new Intent(AccountActivity.this, WebLoginActivity.class);
							startActivityForResult(intent, ADD_ACCOUNT_REQUEST_CODE);
						}else {
							Toast.makeText(AccountActivity.this, val, Toast.LENGTH_SHORT).show();
						}
					}
			}).show();
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_ACCOUNT_REQUEST_CODE && resultCode == RESULT_OK) {
			initData();
			String expires_in = data.getExtras().getString("expires_in");
			long expiresDays = TimeUnit.SECONDS.toDays(Long.valueOf(expires_in));
			String content = String.format(getString(R.string.token_expires_in_time), String.valueOf(expiresDays));
			AlertDialog.Builder builder = new AlertDialog.Builder(this)
				.setMessage(content)
				.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
			builder.show();
		}
	}
	
	private void jumpToMainActivity() {
		String id = SettingHelper.getDefaultAccountId();
		if (!TextUtils.isEmpty(id)) {
			AccountBean bean = AccountDBTask.getAccount(id);
			if (bean != null) {
				Intent intent = MainActivity.newInstance(bean);
				startActivity(intent);
				finish();
			}
		}
	}
	
	private class ListViewItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = MainActivity.newInstance(accountList.get(position));
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	}
	
	private class ListViewItemLongClickListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
			List<String> items = new ArrayList<String>();
			items.add(getString(R.string.to_top));
			items.add(getString(R.string.delete_account));
			
			AccountBean accountBean = accountList.get(position);
			final String uid = accountBean.getUid();
			
			new AlertDialog.Builder(AccountActivity.this)
				.setItems(items.toArray(new String[0]), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (which == 1) {
							removeData(uid);
						}
					}
				}).show();
			return true;
		}
		
	}
	
	private void initData() {
		Thread thread = new Thread(new DataThread());
		thread.start();
	}
	
	private void removeData(String uid) {
		accountList = AccountDBTask.removeAccount(uid);
		accountAdapter.notifyDataSetChanged();
	}
	
	private class DataThread implements Runnable {
		@Override
		public void run() {
			accountList = AccountDBTask.getAccountList();
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				accountAdapter.notifyDataSetChanged();
				break;
			}
		};
	};
	
	private class AccountAdapter extends BaseAdapter {

		public AccountAdapter() {}
		
		@Override
		public int getCount() {
			return accountList.size();
		}

		@Override
		public Object getItem(int position) {
			return accountList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return Long.valueOf(accountList.get(position).getUid());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater layoutInflater = getLayoutInflater();
			View mView = layoutInflater.inflate(R.layout.accountactivity_listview_item_layout, parent, false);
			
			ImageView avatar = (ImageView)mView.findViewById(R.id.imageview_avatar);
			avatar.setImageResource(R.drawable.account_dark);
			
			TextView account = (TextView)mView.findViewById(R.id.account_name);
			account.setText(accountList.get(position).getUsernick());
			
			TextView expired = (TextView)mView.findViewById(R.id.token_has_expired);
			if (Utility.isTokenValid(accountList.get(position))) {
				expired.setText(getString(R.string.token_not_expired));
				expired.setTextColor(getResources().getColor(R.color.green));
			}else{
				expired.setText(getString(R.string.token_has_expired));
				expired.setTextColor(getResources().getColor(R.color.red));
			}
			
			return mView;
		}
		
	}
}
