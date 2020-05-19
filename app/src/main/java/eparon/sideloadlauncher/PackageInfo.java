package eparon.sideloadlauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class PackageInfo {

    private final Drawable mIcon;
    private String mName;
    private final String mPackageName;

    public PackageInfo (PackageManager packageManager, ResolveInfo resolveInfo) {
        mPackageName = resolveInfo.activityInfo.packageName;
        mIcon = resolveInfo.loadIcon(packageManager);
        try {
            mName = resolveInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            mName = mPackageName;
        }
    }

    public String getPackageName () {
        return mPackageName;
    }

    @NonNull
    public String getName () {
        if (mName != null)
            return mName;
        return ("");
    }

    public Drawable getIcon () {
        return mIcon;
    }

    public static List<PackageInfo> loadApplications (Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> intentActivities = packageManager.queryIntentActivities(mainIntent, 0);
        List<PackageInfo> entries = new ArrayList<>();

        for (ResolveInfo resolveInfo : intentActivities)
            if (!context.getPackageName().equals(resolveInfo.activityInfo.packageName))
                entries.add(new PackageInfo(packageManager, resolveInfo));

        Collections.sort(entries, (lhs, rhs) -> lhs.getName().compareToIgnoreCase(rhs.getName()));

        return entries;
    }

}
