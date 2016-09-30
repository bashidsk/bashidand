package com.eruvaka.pm;

 
import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


 
public class ViewpagerActivity extends ActionBarActivity {
	 android.support.v7.app.ActionBar bar;
	 ImageButton leftNav,rightNav;
	 SharedPreferences ApplicationDetails;
	 SharedPreferences.Editor ApplicationDetailsEdit;
	 ArrayList<HashMap<String, String>> schedule_list = new ArrayList<HashMap<String, String>>();
	 AlertDialog.Builder build;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.viewpager); 
	   	  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.signinupshape));
	   	  bar=getSupportActionBar();
	  	  bar.setHomeButtonEnabled(true);
	      bar.setDisplayHomeAsUpEnabled(true);
	      bar.setIcon(android.R.color.transparent);
	      ApplicationDetails=getApplicationContext().getSharedPreferences("com.pm",MODE_PRIVATE);
	       build = new AlertDialog.Builder(ViewpagerActivity.this);
	      try{
          	Intent i = getIntent();
  		    Bundle extras = i.getExtras();
  			String feederSno = extras.getString("feederSno");
  			//String feederId = extras.getString("feederId");
  			String feederName = extras.getString("feederName");
  			Bundle bundle = new Bundle();
  			bundle.putString("feederSno",feederSno);
  		    ApplicationData.add_feeder_Sno(feederSno);
            ApplicationData.add_feeder_Name(feederName);
            bar.setTitle("Add Schedule");
  	      }catch(Exception e){
  	    	  e.printStackTrace();
  	      }
			 
	      	final ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
	        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
	        
	        pager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					// TODO Auto-generated method stub
				 	if(position==0){
						  bar.setTitle("Add Schedule");
			    	  	 leftNav.setVisibility(8);
						 rightNav.setVisibility(0);
						 
					}else if(position==1){
					      bar.setTitle("History");
			    	 	 leftNav.setVisibility(0);
						 rightNav.setVisibility(0);
						 
					}else if(position==2){
						 bar.setTitle("Settings");
						 leftNav.setVisibility(0);
						 rightNav.setVisibility(8);
										 
					}
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int position) {
					// TODO Auto-generated method stub
					
				}
			});
	        leftNav = (ImageButton)findViewById(R.id.left_nav);
	        rightNav = (ImageButton)findViewById(R.id.right_nav);
	        leftNav.setVisibility(8);
	      
	        leftNav.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                int tab = pager.getCurrentItem();
	                 if (tab > 0) {
	                    tab--;
	                    pager.setCurrentItem(tab);
	                    //leftNav.setVisibility(0);
	                  } else if (tab == 0) {
	                	pager.setCurrentItem(tab);
	                	//leftNav.setVisibility(8);
	                }
	            }
	        });

	        // Images right navigatin
	        rightNav.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                int tab = pager.getCurrentItem();
	               
	               // leftNav.setVisibility(0);
	                tab++;
	                pager.setCurrentItem(tab);
	              
	            	  
	            }
	        });

	    }
	 
	 private class MyPagerAdapter extends FragmentPagerAdapter {

	        public MyPagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int pos) {
	        	 switch (pos) {
	             case 0:
	            	 FeedEditFragment tab1 = new FeedEditFragment();
	                 return tab1;
	             case 1:
	            	 HistoryFragment tab2 = new HistoryFragment();
	                 return tab2;
	             case 2:
	            	 SettingsFragment tab3 = new SettingsFragment();
	                 return tab3;
	             default:
	                 return null;
	                
	         }
	        }

	        @Override
	        public int getCount() {
	            return 3;
	        }       
	    }
	 // Returns the page title for the top indicator
   
     public CharSequence getPageTitle(int pos) {
    	
    	 switch(pos){
    	 case 0: bar.setTitle("Add Schedule");
    	 case 1: bar.setTitle("History");
    	 case 2: bar.setTitle("Settings");
    	 }
         return "Page " + pos;
     }
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			try{
			 MenuItem menuItem = menu.findItem(R.id.login_in1);
			   String user_name = ApplicationDetails.getString("FirstName", null);
		       menuItem.setTitle("User : "+user_name);
			}catch(Exception e){
				e.printStackTrace();
			}
			return true;
		}
	 @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			  switch (item.getItemId()) {
			  case R.id.action_settings1:
					 build.setTitle("Logout");
				   	 build.setPositiveButton("OK",new DialogInterface.OnClickListener() {
		              @Override
						public void onClick(DialogInterface dialog, int id) {
		             	 dialog.cancel();
		             	 signoutdialog();
		              }
		              });
		         		build.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialog,int which) {

								dialog.cancel();
							}
						});
		      		build.show();
					return true;
			  case R.id.login_in1: 
						Intent user_intent = new Intent(ViewpagerActivity.this,UserProfileActivity.class);
						user_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(user_intent);
						return true;
				 
			  case android.R.id.home:
		            // app icon in action bar clicked; go home
				  try{
		        	Intent intent = new Intent(ViewpagerActivity.this, MainActivity.class);
		            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		            startActivity(intent);
		             finish();
					  
		             ArrayList<HashMap<String, String>> empty_array=new ArrayList<HashMap<String,String>>();
		             ApplicationData.add_remove_list(empty_array);
		             onBackPressed();
				  }catch (Exception e) {
					// TODO: handle exception
					  e.printStackTrace();
				  }
		            return true;
			 	    default:
					break;
			       
			           }
			return super.onOptionsItemSelected(item);
		}
		 
		private void signoutdialog() {
			
			                 try{
		    		            	ApplicationDetailsEdit = ApplicationDetails.edit();
					            	ApplicationDetailsEdit.putBoolean("isLogged", false);
					                ApplicationDetailsEdit.commit();
					               	Application app = getApplication();					
									Intent loginintent = new Intent(app,LoginActivity.class);
									loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(loginintent);
									finish();	
											    	   
				    	   }catch(Exception e){
				    		   e.printStackTrace();
				    	   }
		}
		 
		@Override
	    public void onBackPressed() {
	        // Write your code here
	        super.onBackPressed();
	        try{
	        	 ArrayList<HashMap<String, String>> empty_array=new ArrayList<HashMap<String,String>>();
	             ApplicationData.add_remove_list(empty_array);
	            
	        	Intent intent = new Intent(ViewpagerActivity.this, MainActivity.class);
	            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            startActivity(intent);
	             finish();
	             
	           
			  }catch (Exception e) {
				// TODO: handle exception
				  e.printStackTrace();
			  }
	    }

}
