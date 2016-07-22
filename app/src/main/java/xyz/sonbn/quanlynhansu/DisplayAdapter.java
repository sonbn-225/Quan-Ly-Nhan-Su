package xyz.sonbn.quanlynhansu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by SonBN on 7/13/2016.
 */
public class DisplayAdapter extends ArrayAdapter<NhanSu> {

    private Context mContext;

    private static class ViewHolder {
        ImageView imageView;
        TextView nameView, birthdayView, phoneView, emailView;
    }
    public DisplayAdapter(Context context, ArrayList<NhanSu> nhansu) {
        super(context, 0, nhansu);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // view lookup cache stored in tag
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row, parent, false);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.nameMainLayout);
            viewHolder.birthdayView = (TextView) convertView.findViewById(R.id.birthdayMainLayout);
            viewHolder.phoneView = (TextView) convertView.findViewById(R.id.phonenumberMainLayout);
            viewHolder.emailView = (TextView) convertView.findViewById(R.id.emailMainLayout);

            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.list_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Get the data item for this position
        NhanSu nhanSu = getItem(position);
        // set name text
        viewHolder.nameView.setText(nhanSu.getName());
        //set age text
        viewHolder.birthdayView.setText(nhanSu.getBirthday());
        //set phone number text
        if (nhanSu.getPhone().length() == 0) {
            viewHolder.emailView.setText(nhanSu.getEmail());
            viewHolder.phoneView.setVisibility(View.GONE);
        } else {
            viewHolder.phoneView.setText(nhanSu.getPhone());
            viewHolder.emailView.setVisibility(View.GONE);
        }
        // set image icon
        if (nhanSu.getImage() != null){
            Glide.with(mContext).load(nhanSu.getImage()).into(viewHolder.imageView);
        }


        // Return the completed view to render on screen
        return convertView;
    }
}
