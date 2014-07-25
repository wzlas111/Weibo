package com.eastelsoft.weibo.ui.login;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.ui.base.AbstractAppActivity;

public class AccountActivity extends AbstractAppActivity {

	private ListView listView;
	private AccountAdapter accountAdapter;
	
	private List<AccountBean> accountList = new ArrayList<AccountBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountactivity_layout);
		
		getActionBar().setTitle(R.string.app_name);
		
		accountAdapter = new AccountAdapter();
		listView = (ListView)this.findViewById(R.id.listView);
		listView.setAdapter(accountAdapter);
		listView.setOnItemClickListener(new ListViewItemClickListener());
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
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String val = valueList.get(which);
								Toast.makeText(AccountActivity.this, val, Toast.LENGTH_SHORT).show();
							}
						}).show();
			break;
		default:
			break;
		}
		return true;
	}
	
	private class ListViewItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
		}
	}
	
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
			
			TextView token = (TextView)mView.findViewById(R.id.token_has_expired);
			token.setVisibility(View.VISIBLE);
			
			return mView;
		}
		
	}
}
