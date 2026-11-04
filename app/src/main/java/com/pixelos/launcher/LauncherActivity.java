package com.pixelos.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public final class LauncherActivity extends Activity {
    private static final int BACKGROUND = Color.rgb(12, 14, 18);
    private static final int SURFACE = Color.rgb(29, 32, 39);
    private static final int PRIMARY_TEXT = Color.rgb(247, 248, 251);
    private static final int SECONDARY_TEXT = Color.rgb(166, 174, 188);
    private static final int COLUMNS = 4;
    private static final int HOME_APP_LIMIT = 8;

    private final List<ResolveInfo> apps = new ArrayList<>();
    private FrameLayout root;
    private boolean drawerVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BACKGROUND);
        getWindow().setNavigationBarColor(BACKGROUND);
        getWindow().getDecorView().setSystemUiVisibility(0);
        apps.addAll(loadLaunchableApps());
        showHome();
    }

    @Override
    public void onBackPressed() {
        if (drawerVisible) {
            showHome();
        } else {
            super.onBackPressed();
        }
    }

    private void showHome() {
        drawerVisible = false;
        root = new FrameLayout(this);
        root.setBackgroundColor(BACKGROUND);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(26), dp(22), dp(18));
        root.addView(page, new FrameLayout.LayoutParams(-1, -1));

        TextView brand = new TextView(this);
        brand.setText("PixelOS");
        brand.setTextColor(PRIMARY_TEXT);
        brand.setTextSize(30);
        brand.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        page.addView(brand);

        TextView edition = new TextView(this);
        edition.setText("17  ·  Independent Launcher");
        edition.setTextColor(SECONDARY_TEXT);
        edition.setTextSize(12);
        LinearLayout.LayoutParams editionParams = new LinearLayout.LayoutParams(-1, -2);
        editionParams.topMargin = dp(3);
        page.addView(edition, editionParams);

        TextView date = new TextView(this);
        date.setText(DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
        date.setTextColor(SECONDARY_TEXT);
        date.setTextSize(13);
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(-1, -2);
        dateParams.topMargin = dp(28);
        dateParams.bottomMargin = dp(18);
        page.addView(date, dateParams);

        TextView section = new TextView(this);
        section.setText("Favourites");
        section.setTextColor(PRIMARY_TEXT);
        section.setTextSize(18);
        section.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        LinearLayout.LayoutParams sectionParams = new LinearLayout.LayoutParams(-1, -2);
        sectionParams.bottomMargin = dp(12);
        page.addView(section, sectionParams);

        ScrollView homeScroll = new ScrollView(this);
        homeScroll.setFillViewport(false);
        page.addView(homeScroll, new LinearLayout.LayoutParams(-1, 0, 1f));

        GridLayout homeGrid = new GridLayout(this);
        homeGrid.setColumnCount(COLUMNS);
        homeGrid.setUseDefaultMargins(false);
        homeScroll.addView(homeGrid);

        int homeCount = Math.min(HOME_APP_LIMIT, Math.max(0, apps.size() - 4));
        if (homeCount == 0) homeCount = Math.min(HOME_APP_LIMIT, apps.size());
        int start = apps.size() > 4 ? 4 : 0;
        for (int i = 0; i < homeCount; i++) {
            ResolveInfo app = apps.get((start + i) % apps.size());
            homeGrid.addView(createAppCell(app, false));
        }

        if (apps.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No launchable apps found.");
            empty.setTextColor(SECONDARY_TEXT);
            page.addView(empty);
        }

        LinearLayout bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.VERTICAL);
        bottom.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams bottomParams = new LinearLayout.LayoutParams(-1, -2);
        bottomParams.topMargin = dp(12);
        page.addView(bottom, bottomParams);

        LinearLayout dock = new LinearLayout(this);
        dock.setGravity(Gravity.CENTER);
        dock.setOrientation(LinearLayout.HORIZONTAL);
        dock.setPadding(dp(12), dp(9), dp(12), dp(9));
        GradientDrawable dockBackground = new GradientDrawable();
        dockBackground.setColor(Color.rgb(37, 40, 48));
        dockBackground.setCornerRadius(dp(30));
        dock.setBackground(dockBackground);
        int dockCount = Math.min(4, apps.size());
        for (int i = 0; i < dockCount; i++) {
            dock.addView(createDockIcon(apps.get(i)), new LinearLayout.LayoutParams(0, dp(58), 1f));
        }
        LinearLayout.LayoutParams dockParams = new LinearLayout.LayoutParams(-1, -2);
        bottom.addView(dock, dockParams);

        TextView allApps = new TextView(this);
        allApps.setText("All apps  ↑");
        allApps.setTextColor(SECONDARY_TEXT);
        allApps.setTextSize(13);
        allApps.setGravity(Gravity.CENTER);
        allApps.setPadding(dp(16), dp(14), dp(16), dp(4));
        allApps.setClickable(true);
        allApps.setFocusable(true);
        allApps.setOnClickListener(v -> showDrawer());
        bottom.addView(allApps);

        setContentView(root);
    }

    private void showDrawer() {
        drawerVisible = true;
        root = new FrameLayout(this);
        root.setBackgroundColor(BACKGROUND);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(28), dp(20), dp(16));
        root.addView(page, new FrameLayout.LayoutParams(-1, -1));

        LinearLayout toolbar = new LinearLayout(this);
        toolbar.setGravity(Gravity.CENTER_VERTICAL);
        page.addView(toolbar, new LinearLayout.LayoutParams(-1, -2));

        TextView back = new TextView(this);
        back.setText("‹");
        back.setTextColor(PRIMARY_TEXT);
        back.setTextSize(34);
        back.setPadding(dp(4), 0, dp(16), 0);
        back.setOnClickListener(v -> showHome());
        toolbar.addView(back);

        TextView title = new TextView(this);
        title.setText("App Library");
        title.setTextColor(PRIMARY_TEXT);
        title.setTextSize(25);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        toolbar.addView(title);

        TextView count = new TextView(this);
        count.setText(apps.size() + " apps");
        count.setTextColor(SECONDARY_TEXT);
        count.setTextSize(12);
        LinearLayout.LayoutParams countParams = new LinearLayout.LayoutParams(-2, -2);
        countParams.topMargin = dp(8);
        countParams.bottomMargin = dp(16);
        page.addView(count, countParams);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        page.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1f));

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(COLUMNS);
        grid.setUseDefaultMargins(false);
        scroll.addView(grid);
        for (ResolveInfo app : apps) {
            grid.addView(createAppCell(app, true));
        }
        setContentView(root);
    }

    private View createDockIcon(ResolveInfo app) {
        ImageView icon = new ImageView(this);
        icon.setImageDrawable(app.loadIcon(getPackageManager()));
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icon.setPadding(dp(7), dp(5), dp(7), dp(5));
        icon.setContentDescription(app.loadLabel(getPackageManager()));
        icon.setOnClickListener(v -> launchApp(app));
        return icon;
    }

    private View createAppCell(final ResolveInfo app, boolean inDrawer) {
        LinearLayout cell = new LinearLayout(this);
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER_HORIZONTAL);
        cell.setPadding(dp(3), dp(8), dp(3), dp(12));
        cell.setClickable(true);
        cell.setFocusable(true);
        cell.setBackgroundResource(android.R.drawable.list_selector_background);

        ImageView icon = new ImageView(this);
        icon.setImageDrawable(app.loadIcon(getPackageManager()));
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int iconSize = inDrawer ? 52 : 50;
        cell.addView(icon, new LinearLayout.LayoutParams(dp(iconSize), dp(iconSize)));

        TextView label = new TextView(this);
        label.setText(app.loadLabel(getPackageManager()));
        label.setTextColor(PRIMARY_TEXT);
        label.setTextSize(11);
        label.setGravity(Gravity.CENTER);
        label.setMaxLines(2);
        LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(-1, dp(34));
        labelParams.topMargin = dp(6);
        cell.addView(label, labelParams);

        cell.setOnClickListener(v -> launchApp(app));

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        cell.setLayoutParams(params);
        return cell;
    }

    private void launchApp(ResolveInfo app) {
        Intent launch = getPackageManager().getLaunchIntentForPackage(app.activityInfo.packageName);
        if (launch != null) {
            launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launch);
        }
    }

    private List<ResolveInfo> loadLaunchableApps() {
        Intent query = new Intent(Intent.ACTION_MAIN);
        query.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> result =
                new ArrayList<>(getPackageManager().queryIntentActivities(query, 0));
        Collections.sort(result, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo left, ResolveInfo right) {
                return left.loadLabel(getPackageManager()).toString()
                        .compareToIgnoreCase(right.loadLabel(getPackageManager()).toString());
            }
        });
        return result;
    }

    private int dp(float value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
