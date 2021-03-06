package xyz.sonbn.quanlynhansu;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SonBN on 7/13/2016.
 */
public class DisplayAdapter extends ArrayAdapter<NhanSu> {

    private final int THUMBSIZE = 96;

    private static class ViewHolder {
        ImageView imageView;
        TextView nameView, ageView, phoneView;
    }
    public DisplayAdapter(Context context, ArrayList<NhanSu> nhansu) {
        super(context, 0, nhansu);
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
            viewHolder.ageView = (TextView) convertView.findViewById(R.id.ageMainLayout);
            viewHolder.phoneView = (TextView) convertView.findViewById(R.id.phonenumberMainLayout);

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
        viewHolder.ageView.setText(nhanSu.getAge());
        //set phone number text
        viewHolder.phoneView.setText(nhanSu.getPhone());
        // set image icon
        viewHolder.imageView.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(nhanSu.getImage()),
                        THUMBSIZE, THUMBSIZE));

        // Return the completed view to render on screen
        return convertView;
    }
}
