package DataHandeler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.todo.rahle.todo_app.R;

import java.util.ArrayList;

/**
 * Created by rahle on 2016-12-23.
 * The list adapter for the list in the mainactivity.
 */

public class ListAdapter extends ArrayAdapter<String> {
    // the current activity context
    private final Activity context;
    // the items to present
    private final ArrayList<String> itemname;
    // the dates to present
    private final ArrayList<String> dates;

    // Setup the list adapter.
    public ListAdapter(Activity context, ArrayList<String> itemname, ArrayList<String> dates) {
        super(context, R.layout.my_list, itemname);

        this.context = context;
        this.itemname = itemname;
        this.dates = dates;
    }

    // this happens for every single listitem. Get the text and the date for every item.
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
        TextView txtDate = (TextView) rowView.findViewById(R.id.dateTxt);

        if(dates != null){
            txtDate.setText(dates.get(position));
        }

        txtTitle.setText(itemname.get(position));
        return rowView;

    }
}
