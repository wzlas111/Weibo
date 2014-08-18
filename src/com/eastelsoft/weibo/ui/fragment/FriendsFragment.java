package com.eastelsoft.weibo.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.eastelsoft.weibo.GlobalContext;
import com.eastelsoft.weibo.R;
import com.eastelsoft.weibo.bean.AccountBean;
import com.eastelsoft.weibo.bean.GroupBean;
import com.eastelsoft.weibo.bean.TimelineBean;
import com.eastelsoft.weibo.bean.UserBean;
import com.eastelsoft.weibo.callback.GroupDataCallback;
import com.eastelsoft.weibo.dao.HomeDao;
import com.eastelsoft.weibo.dao.task.FriendGroupTask;
import com.eastelsoft.weibo.ui.adapter.FriendsBarAdapter;
import com.eastelsoft.weibo.ui.adapter.ListItemAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.LoaderManager.LoaderCallbacks;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

@SuppressLint("ValidFragment")
public class FriendsFragment extends BaseFragment implements LoaderCallbacks<List<TimelineBean>>{
	
	private FriendsBarAdapter barAdapter;
	private ListItemAdapter listAdapter;
	
	private AccountBean accountBean;
	private UserBean userBean;
	private String token;
	
	private List<TimelineBean> data = new ArrayList<TimelineBean>();
	private String[] groupData;
	
	private int LOADER_ID = 0;
	
	public FriendsFragment() {}
	
	public static FriendsFragment newInstance(AccountBean accountBean, UserBean userBean, String token) {
		FriendsFragment fragment = new FriendsFragment(accountBean, userBean, token);
		fragment.setArguments(new Bundle());
		return fragment;
	}
	
	public FriendsFragment(AccountBean accountBean, UserBean userBean,String token) {
		this.accountBean = accountBean;
		this.userBean = userBean;
		this.token = token;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("account", accountBean);
		outState.putParcelable("user", userBean);
		outState.putString("token", token);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		FriendGroupTask groupTask = new FriendGroupTask(accountBean.getAccess_token(), accountBean.getUid());
		groupTask.execute();
		switch (getCurrentState(savedInstanceState)) {
			case FIRST_TIME_START://加载DB中缓存数据
				
				break;
			case SCREEN_RETATE:
				
				break;
			case DESTROY_AND_CREATE:
				
				break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//下拉自动刷新数据
		//refresh();
		buildAdapter();
		pullToRefreshListView.setOnRefreshListener(refreshListener);
		getLoaderManager().initLoader(LOADER_ID, null, this);
		
		buildActionBar();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.friendsbar_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.write_weibo:
				
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * custom actionbar
	 */
	public void buildActionBar() {
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(R.drawable.ic_menu_home);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		
		List<GroupBean> list = new ArrayList<>();
		if (GlobalContext.getInstance().getGroup() != null) {
			list = GlobalContext.getInstance().getGroup().getLists();
		}
		groupData = buildGroupData(list);
		barAdapter = new FriendsBarAdapter(getActivity(), groupData);
		actionBar.setListNavigationCallbacks(barAdapter, new OnNavigationListener(){
			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
//				Toast.makeText(getActivity(), "selected : "+finalList.get(itemPosition), Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
	}
	
	public void buildAdapter() {
		listAdapter = new ListItemAdapter(this, data);
		getListView().setAdapter(listAdapter);
	}
	
	private String[] buildGroupData(List<GroupBean> groups) {
		List<String> list = new ArrayList<String>();
		
		list.add(getString(R.string.g_all));
		for (GroupBean g : groups) {
			list.add(g.getName());
		}
		System.out.println("group list : "+list.toString());
		return list.toArray(new String[0]);
	}

	@Override
	public Loader<List<TimelineBean>> onCreateLoader(int id, Bundle args) {
		return new TimelineDataLoader(getActivity(), accountBean);
	}

	@Override
	public void onLoadFinished(Loader<List<TimelineBean>> loader,
			List<TimelineBean> data) {
		if (data != null && data.size() > 0) {
			this.data.addAll(data);
			System.out.println("size  : "+data.size());
		}else {
			Toast.makeText(getActivity(), "网络有问题，数据加载失败!", Toast.LENGTH_SHORT).show();
		}
		listAdapter.notifyDataSetChanged();
		pullToRefreshListView.onRefreshComplete();
		
	}

	@Override
	public void onLoaderReset(Loader<List<TimelineBean>> loader) {
		this.data = new ArrayList<>();
		listAdapter.notifyDataSetChanged();
	}
	
	public static class TimelineDataLoader extends AsyncTaskLoader<List<TimelineBean>> {

		private AccountBean accountBean;
		
		public TimelineDataLoader(Context context,AccountBean bean) {
			super(context);
			this.accountBean = bean;
		}
		
		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			forceLoad();
		}

		@Override
		public List<TimelineBean> loadInBackground() {
			System.out.println("loading data");
			String access_token = accountBean.getAccess_token();
			try {
				return new HomeDao(access_token).getBean().getStatuses();
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<TimelineBean>();
			}
		}
		
	}
	
	private OnRefreshListener<ListView> refreshListener = new OnRefreshListener<ListView>() {
		public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			getLoaderManager().restartLoader(LOADER_ID, null, FriendsFragment.this);
		}
	};
	
}
