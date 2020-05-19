package eparon.sideloadlauncher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class ApplicationAdapter extends ArrayAdapter<PackageInfo> {

    private final int mResource;

    public ApplicationAdapter (Context context, int resId, PackageInfo[] items) {
        super(context, R.layout.recycler_item, items);
        mResource = resId;
    }

    @NonNull
    @Override
    public View getView (int position, View convertView, @NonNull ViewGroup parent) {

        View view;

        if (convertView == null)
            view = View.inflate(getContext(), mResource, null);
        else
            view = convertView;

        ImageView packageImage = view.findViewById(R.id.recycler_image);
        PackageInfo packageInfo = getItem(position);

        if (packageInfo != null) {
            if (!packageInfo.getPackageName().equals(getContext().getPackageName())) {
                view.setTag(packageInfo);
                if (packageInfo.getIcon() != null)
                    packageImage.setImageDrawable(packageInfo.getIcon());
            }
        }

        return view;
    }

}
