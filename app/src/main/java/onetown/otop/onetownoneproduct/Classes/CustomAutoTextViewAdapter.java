package onetown.otop.onetownoneproduct.Classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import onetown.otop.onetownoneproduct.Objects.LocationsData;
import onetown.otop.onetownoneproduct.R;

/**
 * Created by root on 11/21/16.
 */

public class CustomAutoTextViewAdapter extends ArrayAdapter<LocationsData> {

    Context ctx;
    int resource,textViewResourceId;
    ArrayList<LocationsData> list,tempItems,suggestions;

    public CustomAutoTextViewAdapter(Context context, int resource, int textViewResourceId, ArrayList<LocationsData> datas) {
        super(context, resource,textViewResourceId,datas);
        this.ctx=context;
        this.resource=resource;
        this.textViewResourceId=textViewResourceId;
        list=datas;
        tempItems= new ArrayList<LocationsData>(list);
        suggestions= new ArrayList<LocationsData>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= convertView;
        if (v== null) {
            LayoutInflater layoutInflater= (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v= layoutInflater.inflate(R.layout.row_layout,parent,false);
        }
        LocationsData locationsData= list.get(position);
            if (locationsData != null) {
                TextView tv= (TextView)v.findViewById(R.id.autocomplete_result);
                if (tv != null) {
                    tv.setText(locationsData.getLocationName());
                }
            }

        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter= new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str= ((LocationsData)resultValue).getLocationName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           if (constraint != null) {
               suggestions.clear();
               for (LocationsData locationsData:tempItems) {
                   if (locationsData.getLocationName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                       suggestions.add(locationsData);
                   }
               }
               FilterResults filterResults= new FilterResults();
               filterResults.values= suggestions;
               filterResults.count= suggestions.size();
               return filterResults;
           }else {
               return new FilterResults();
           }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayList<LocationsData> filterList= (ArrayList<LocationsData>)results.values;
            if (results != null && results.count > 0) {
                clear();

                for (LocationsData data:filterList) {
                    add(data);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
