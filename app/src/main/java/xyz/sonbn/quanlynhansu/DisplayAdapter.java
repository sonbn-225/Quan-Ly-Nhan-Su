package xyz.sonbn.quanlynhansu;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SonBN on 7/13/2016.
 */
public class DisplayAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final int[] id;
    private final String[] name;
    private final String[] age;
    private final String[] phonenumber;
    private final Integer imageId;
    public DisplayAdapter(Activity context, int[] id,
                          String[] name, String[] age, String[] phonenumber, int imageId) {
        super(context, R.layout.list_row, name);
        this.context = context;
        this.id = id;
        this.name = name;
        this.age = age;
        this.phonenumber = phonenumber;
        this.imageId = imageId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row, null, true);
        TextView nameView = (TextView) rowView.findViewById(R.id.nameMainLayout);
        TextView ageView = (TextView) rowView.findViewById(R.id.ageMainLayout);
        TextView phonenumberView = (TextView) rowView.findViewById(R.id.phonenumberMainLayout);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image);



        nameView.setText(name[position]);
        ageView.setText(age[position]);
        phonenumberView.setText(phonenumber[position]);

        imageView.setImageResource(imageId);
        return rowView;
    }
}
