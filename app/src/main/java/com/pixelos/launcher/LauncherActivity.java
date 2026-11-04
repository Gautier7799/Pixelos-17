package com.pixelos.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class LauncherActivity extends Activity {
    private static final int BACKGROUND = Color.rgb(18, 20, 25);
    private static final int PRIMARY_TEXT = Color.rgb(245, 246, 250);
    private static final int SECONDARY_TEXT = Color.rgb(174, 180, 192);
    private static final int COLUMNS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BACKGROUND);
        getWindow().setNavigationBarColor(BACKGROUND);
        getWindow().getDecorView().setSystemUiVisibility(0);
        renderLauncher();
    }

    private void renderLauncher() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BACKGROUND);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(30), dp(20), dp(28));
        scroll.addView(page);

        TextView title = new TextView(this);
        title.setText("Your apps");
        title.setTextColor(PRIMARY_TEXT);
        title.setTextSize(28);
        title.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        page.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("PixelOS 17 · Foundation build");
        subtitle.setTextColor(SECONDARY_TEXT);
        subtitle.setTextSize(13);
        LinearLayout.LayoutParams subtitleParams =
                new LinearLayout.LayoutParams(-1, -2);
        subtitleParams.topMargin = dp(6);
        subtitleParams.bottomMargin = dp(24);
        page.addView(subtitle, subtitleParams);

        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(COLUMNS);
        grid.setUseDefaultMargins(false);
        page.addView(grid, new LinearLayout.LayoutParams(-1, -2));

        List<ResolveInfo> apps = loadLaunchableApps();
        for (ResolveInfo app : apps) {
            grid.addView(createAppCell(app));
        }

        if (apps.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("No launchable apps found.");
            empty.setTextColor(SECONDARY_TEXT);
            page.addView(empty);
        }

        setContentView(scroll);
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
                        .compareToIgnoreCase(
                                right.loadLabel(getPackageManager()).toString());
            }
        });
        return result;
    }

    private View createAppCell(final ResolveInfo app) {
        LinearLayout cell = new LinearLayout(this);
        cell.setOrientation(LinearLayout.VERTICAL);
        cell.setGravity(Gravity.CENTER_HORIZONTAL);
        cell.setPadding(dp(3), dp(8), dp(3), dp(12));
        cell.setClickable(true);
        cell.setFocusable(true);
        cell.setBackgroundResource(android.R.drawable.list_selector_background);

        ImageView icon = new ImageView(this);
        Drawable originalIcon = app.loadIcon(getPackageManager());
        icon.setImageDrawable(originalIcon);
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams iconParams =
                new LinearLayout.LayoutParams(dp(52), dp(52));
        cell.addView(icon, iconParams);

        TextView label = new TextView(this);
        label.setText(app.loadLabel(getPackageManager()));
        label.setTextColor(PRIMARY_TEXT);
        label.setTextSize(11);
        label.setGravity(Gravity.CENTER);
        label.setMaxLines(2);
        LinearLayout.LayoutParams labelParams =
                new LinearLayout.LayoutParams(-1, dp(34));
        labelParams.topMargin = dp(6);
        cell.addView(label, labelParams);

        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packageName = app.activityInfo.packageName;
                Intent launch = getPackageManager().getLaunchIntentForPackage(packageName);
                if (launch != null) {
                    launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(launch);
                }
            }
        });

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        cell.setLayoutParams(params);
        return cell;
    }

    private int dp(float value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
