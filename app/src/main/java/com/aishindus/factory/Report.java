package com.aishindus.factory;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import static com.aishindus.factory.R.id.container;
import static com.aishindus.factory.R.id.toolbar;

public class Report extends AppCompatActivity implements ValidationResponse {

    Toolbar mToolbar;
    private ObjectAnimator animator;
    private PopupWindow mPopup;
    private static String resultString;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    int position;
    private CoordinatorLayout corCoordinatorLayout;
    String query = "SELECT * FROM Factory order by style";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        corCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = sdf.format(date);
        String today1 = sdf1.format(date);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        switch (position) {
            case 0:
                query = "SELECT * FROM Factory where Date='"+today1+"' order by style;";
                toolbar.setTitle(Html.fromHtml("<small>Date: " + today + "</small>"));
                break;
            case 1:
                String style = intent.getStringExtra("element");
                query = "select * from search_by_style where style='" + style + "';";
                toolbar.setTitle(Html.fromHtml("<small>Style#: " + style + "</small>"));
                break;
            case 2:
                style = intent.getStringExtra("element");
                query = "SELECT * FROM Factory where Date='" + style + "' order by style";
                toolbar.setTitle(Html.fromHtml("<small>Date: " + style + "</small>"));
                break;
        }

        Get_Result conn = new Get_Result(this);
        conn.delegate = Report.this;
        showProgress(true);
        conn.execute(URLS.Query_URL,query);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


/*        po_num.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                *//*if(style.getVisibility() == View.VISIBLE) {
                    style.startAnimation(AnimationUtils.loadAnimation(Report.this, android.R.anim.fade_out));
                    style.setVisibility(View.INVISIBLE);
                } else {
                    style.startAnimation(AnimationUtils.loadAnimation(Report.this, android.R.anim.fade_in));
                    style.setVisibility(View.VISIBLE);
                }

                TableRow color = (TableRow) findViewById(R.id.color);
                TextView et = (TextView) color.getChildAt(0);
                if(et.getLayoutParams().height==0) {
                    final TableRow.LayoutParams lparams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT); // Width , height
                    et.setLayoutParams(lparams);
                }
                else
                {
                    final TableRow.LayoutParams lparams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 0); // Width , height
                    et.setLayoutParams(lparams);
                }*//*
                return false;
            }
        });*/

/*        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                toolbar.setElevation(40);
                if (scrollY <= 0)
                    toolbar.setElevation(0);
            }
        });*/
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_grid_view; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_bar_report, menu);

        /*final String num = "2810";
        MenuItem searchItem = menu_grid_view.findItem(R.id.action_search);
        final SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(Report.this,"Submitted",Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals(num)) {
                    EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                    searchEditText.setTextColor(Color.RED);
                }
                else{
                    EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
                    searchEditText.setTextColor(Color.BLACK);
                }
                return false;
            }
        });*/

        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                View item = mToolbar.findViewById(R.id.action_refresh);
                if (item != null) {
                    mToolbar.removeOnLayoutChangeListener(this);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            animator = ObjectAnimator
                                    .ofFloat(v, "rotation", v.getRotation() + 360);
                            animator.setRepeatCount(ValueAnimator.INFINITE);
                            animator.setRepeatMode(ValueAnimator.RESTART);
                            animator.setDuration(1000);
                            animator.start();
                            Get_Result conn = new Get_Result(Report.this);
                            conn.delegate = Report.this;
                            showProgress(true);
                            conn.execute(URLS.Query_URL,query);
                        }
                    });
                }
            }
        });
        return true;
    }

/*    @Override
    public void response(final boolean result, String[] s) {

        int textBackground1 = ContextCompat.getColor(Report.this, R.color.textBackground1);
        int textBackground2 = ContextCompat.getColor(Report.this, R.color.textBackground2);
        int colors[] = new int[]{textBackground2, textBackground1};
        TableRow tr;
        TextView style_text = (TextView) findViewById(R.id.style_text);
        TextView style_date = (TextView) findViewById(R.id.style_num);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String today = sdf.format(date);

        if (result) {
            Log.e("Rsult",""+result);
            if (scrollable_table.getChildCount() > 0) scrollable_table.removeAllViews();
            style_text.setText("Style# : " + s[0].substring(0, s[0].indexOf("#")));
            style_date.setText("Date : " + today);
            for (int k = 0; k < s.length; k++) {
                for (int i = 0, j = 1, index = 1; i < table.getChildCount(); i++, index++) {
                    if (k == 0)
                        tr = new TableRow(this);
                    else
                        tr = (TableRow) scrollable_table.getChildAt(i);
                    String[] content = s[k].split("#");
                    TextView label = new TextView(this);
                    label.setText(content[index]);
                    label.setId(View.generateViewId());
                    label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    label.setPadding(pixelAsDp(10), pixelAsDp(10), pixelAsDp(10), pixelAsDp(10));
                    label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    label.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                    label.setBackgroundColor(colors[j % 2]);
                    label.setGravity(Gravity.CENTER);
                    label.setTextColor(Color.WHITE);
                    LinearLayout Ll = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(0, 0, 1, 0);
                    Ll.addView(label, params);
                    tr.addView((View) Ll);
                    if (k == 0)
                        scrollable_table.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                    j++;
                }
            }
        }
        scrollView.scrollTo(0, 0);
        horizontalScrollView.scrollTo(0, 0);
        mPopup.dismiss();
        if (animator != null) animator.end();
    }*/

    public void showProgress(final boolean show) {
        if (show) {
            final View popupView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_progress, null);
            Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;
            int height = size.y;
            mPopup = new PopupWindow(popupView, width, height);
            mPopup.setFocusable(true);
            mPopup.setBackgroundDrawable(new BitmapDrawable(getResources(), ""));
            popupView.post(new Runnable() {
                public void run() {
                    mPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }
            });
        } else {
            if (mPopup != null)
                mPopup.dismiss();

        }
    }

    @Override
    public void response(boolean result, String s) {
        showProgress(false);
        if (animator != null) animator.end();

        if (result) {
            Report.resultString = s;
            JSONObject jObject = null;
            try {
                jObject = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TabHandler.noOfTabs = jObject.length();
            Iterator<?> keys = jObject.keys();
            String key = null;
            int i = 0;
            TabHandler.tabNames = new String[jObject.length()];
            while (keys.hasNext()) {
                key = (String) keys.next();
                TabHandler.tabNames[i++] = key;
            }
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        } else {
            String value = (s.equals("None"))?"No Result !":s;
            Snackbar.make(corCoordinatorLayout, Html.fromHtml("<b>"+value+"</b>"), Snackbar.LENGTH_INDEFINITE).show();
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        static int sectionNumber;

        public PlaceholderFragment() {
        }

        View loadData(LayoutInflater inflater, ViewGroup container, int sectionNumber) {
            View inflatedView = inflater.inflate(R.layout.report, container, false);
            TableLayout table = (TableLayout) inflatedView.findViewById(R.id.tableLayout1);
            TableLayout scrollable_table = (TableLayout) inflatedView.findViewById(R.id.scrollable_part);
            int textBackground1 = ContextCompat.getColor(getContext(), R.color.textBackground1);
            int textBackground2 = ContextCompat.getColor(getContext(), R.color.textBackground2);
            int colors[] = new int[]{textBackground2, textBackground1};
            TableRow tr;

            JSONObject jObject = null;
            try {
                jObject = new JSONObject(Report.resultString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (scrollable_table.getChildCount() > 0) scrollable_table.removeAllViews();
            int k = 0;
            Iterator<?> keys = jObject.keys();
            String key = null;
            while (k < sectionNumber) {
                key = (String) keys.next();
                k++;
            }
            JSONArray jsonArray, temp = null;
            try {
                jsonArray = jObject.getJSONArray(key);
                for (int i = 0; i < jsonArray.length(); i++) {
                    temp = jsonArray.getJSONArray(i);
                    for (int index = 0, j = 1; index < table.getChildCount(); index++) {
                        if (i == 0)
                            tr = new TableRow(getContext());
                        else
                            tr = (TableRow) scrollable_table.getChildAt(index);
                        TextView label = new TextView(getContext());
                        String value = temp.getString(index);
                        label.setText(value.equals("null") ? "-" : value);
                        Log.e("" + index + " " + table.getChildCount(), temp.getString(index));
                        label.setId(View.generateViewId());
                        label.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        label.setPadding(pixelAsDp(10), pixelAsDp(10), pixelAsDp(10), pixelAsDp(10));
                        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                        label.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                        label.setBackgroundColor(colors[j % 2]);
                        label.setGravity(Gravity.CENTER);
                        label.setTextColor(Color.WHITE);
                        LinearLayout Ll = new LinearLayout(getContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 0, 1, 0);
                        Ll.addView(label, params);
                        tr.addView((View) Ll);
                        if (i == 0)
                            scrollable_table.addView(tr, new TableLayout.LayoutParams(
                                    TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                        j++;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return inflatedView;
        }

        int pixelAsDp(int value) {
            float scale = getResources().getDisplayMetrics().density;
            int dp = (int) (value * scale + 0.5f);
            return dp;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment.sectionNumber = sectionNumber;
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = loadData(inflater, container, getArguments().getInt(ARG_SECTION_NUMBER));
            return rootView;
        }
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
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return TabHandler.noOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position <= TabHandler.noOfTabs)
                return TabHandler.tabNames[position];
            return null;
        }
    }
}
