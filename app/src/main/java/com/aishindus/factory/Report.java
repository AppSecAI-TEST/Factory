package com.aishindus.factory;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Report extends AppCompatActivity implements ValidationResponse {
    TableRow style, po_num;
    TableLayout table, scrollable_table;
    SessionManager session;
    ScrollView scrollView;
    Toolbar toolbar, mToolbar;
    HorizontalScrollView horizontalScrollView;
    ObjectAnimator animator;
    private PopupWindow mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        session = new SessionManager(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.my_head);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // style = (TableRow) findViewById(R.id.style);
        po_num = (TableRow) findViewById(R.id.po_no);
        table = (TableLayout) findViewById(R.id.tableLayout1);
        scrollable_table = (TableLayout) findViewById(R.id.scrollable_part);

        Get_Result conn1 = new Get_Result(this);
        conn1.delegate = Report.this;
        showProgress(true);
        conn1.execute();


        po_num.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /*if(style.getVisibility() == View.VISIBLE) {
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
                }*/
                return false;
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();
                toolbar.setElevation(40);
                if (scrollY <= 0)
                    toolbar.setElevation(0);
            }
        });

        /*LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        Snackbar snackbar = Snackbar
                .make(parent, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

        snackbar.show();*/



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

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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
                            conn.execute();
                        }
                    });
                }
            }
        });
        return true;
    }

    @Override
    public void response(final boolean result, String[] s) {

        int textBackground1 = ContextCompat.getColor(Report.this, R.color.textBackground1);
        int textBackground2 = ContextCompat.getColor(Report.this, R.color.textBackground2);
        int colors[] = new int[]{textBackground2, textBackground1};
        TableRow tr;
        TextView style_text = (TextView) findViewById(R.id.style_text);
        TextView style_date = (TextView) findViewById(R.id.style_num);

        if (result) {
            if (scrollable_table.getChildCount() > 0) scrollable_table.removeAllViews();
            style_text.setText("Style# : " + s[0].substring(0, s[0].indexOf("#")));
            style_date.setText("Date : " + "26-02-2017");
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
    }

    int pixelAsDp(int value) {
        float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (value * scale + 0.5f);
        return dp;
    }

    public void showProgress(final boolean show) {
        if (show) {
            final View popupView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_progress, null);
            Point size = new Point();
            this.getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;
            int height = size.y;
            mPopup = new PopupWindow(popupView, width, height);
            mPopup.setFocusable(true);
            popupView.post(new Runnable() {
                public void run() {
                    mPopup.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                }
            });
        } else {
            mPopup.dismiss();
        }
    }
}
