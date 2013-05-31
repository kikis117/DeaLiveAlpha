package com.example.test;

import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public static String EXTRA_MESSAGE = "DEALIVE";
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		/*// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		*/
		actionBar.addTab(actionBar.newTab()
				.setText("Coffee")
				.setTabListener(this));
		
		actionBar.addTab(actionBar.newTab()
				.setText("Food")
				.setTabListener(this));
		

		
		actionBar.addTab(actionBar.newTab()
				.setText("Drink")
				.setTabListener(this));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		
	
		
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		
		 
		public static final String ARG_SECTION_NUMBER = "section_number";
		

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			ListView dummyListView = (ListView) rootView
					.findViewById(R.id.listView1);
			
			final ArrayList<String> allList = new ArrayList<String>();
			ArrayList<String> list = new ArrayList<String>();
			
			
			connectServer server = new connectServer("192.168.0.3");


		    if(server.stopClient())
		    {
		    	
		    	 list.add("Server Connection Failure!");  
		    }
		    else
		    {
		    	
		    	 server.execute();
		    	 ArrayList<String> tmplist = server.sendRequest(getArguments().getInt(ARG_SECTION_NUMBER), false);
		    	 for(String i: tmplist)
		    		     	 allList.add(i);
		    	
		  }
		    
		    	if(allList.size()>1)
		    	{
		    		for(int i=1;i<(allList.size());i+=R.integer.field_number)
		    			list.add(allList.get(i));
		    	}
		    	else
		    		list.add(allList.get(0));
					
			final ArrayAdapter<String> adapter  = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,list);
			
			dummyListView.setAdapter(adapter);
			
			dummyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			    @SuppressLint("NewApi")
				@Override
			      public void onItemClick(AdapterView<?> parent, final View view,
			          int position,  long id) {

			    	final long idf;
			    	idf=id;
			    	view.animate().setDuration(2000).alpha(0)
		            .withEndAction(new Runnable() {
		              @Override
		              public void run() {
		            	ArrayList<String> selection = new ArrayList<String>();
		            	if(allList.size()>7)
		            	{
		            		for(int i=0; i< R.integer.field_number;i++)
		            			selection.add(allList.get( (int) (i+idf)));
		            		
		            	
		            	}
		            	else
		            		selection.add("Server Error!");
		            	view.setAlpha(1);
		            	Intent intent = new Intent(getActivity(), DetailsActivity.class);
		    			intent.putExtra(EXTRA_MESSAGE, selection);
		    			startActivity(intent);
		                
		              }
		            });
			      }

			    });			

			return rootView;
		}
		

	
	}
	
}
