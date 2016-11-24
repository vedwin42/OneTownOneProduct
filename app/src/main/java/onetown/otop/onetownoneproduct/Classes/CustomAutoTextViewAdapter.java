package onetown.otop.onetownoneproduct.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;


public class CustomAutoTextViewAdapter extends ArrayAdapter<LocationsData> {

    Context ctx;
    int resource, textViewResourceId;
    ArrayList<LocationsData> list, tempItems, suggestions;

    public CustomAutoTextViewAdapter(Context context, int resource, int textViewResourceId, ArrayList<LocationsData> datas) {
        super(context, resource, textViewResourceId, datas);
        this.ctx = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        list = datas;
        tempItems = new ArrayList<LocationsData>(list);
        suggestions = new ArrayList<LocationsData>();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public LocationsData getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.row_layout, parent, false);
        }
        LocationsData locationsData = getItem(position);
        if (locationsData != null) {
            TextView tv = (TextView) v.findViewById(R.id.autocomplete_result);
            if (tv != null) {
                tv.setText(locationsData.locationName);
            }
        }

        return v;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                String str = ((LocationsData) resultValue).locationName;
                return str;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (LocationsData locationsData : tempItems) {
                        if (locationsData.locationName.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            suggestions.add(locationsData);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<LocationsData> filterList = (ArrayList<LocationsData>) results.values;
                if (results != null && results.count > 0) {
                    clear();

                    for (LocationsData data : filterList) {
                        add(data);
                        notifyDataSetChanged();
                    }
                }
            }

        };
    }
}
