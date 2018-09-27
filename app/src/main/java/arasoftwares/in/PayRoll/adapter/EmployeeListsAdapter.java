package arasoftwares.in.PayRoll.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import arasoftwares.in.PayRoll.R;
import arasoftwares.in.PayRoll.model.EmpList;

public class EmployeeListsAdapter extends ArrayAdapter<EmpList> {

    private LayoutInflater layoutInflater;
    private ArrayList<EmpList> mCustomers;
    ArrayList<EmpList> suggestions;

    public EmployeeListsAdapter(Context context, int textViewResourceId, List<EmpList> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<EmpList>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((EmpList) resultValue).getEmployeeName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                suggestions = new ArrayList<EmpList>();
                for (EmpList customer : mCustomers) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getEmployeeName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<EmpList>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers);
            }
            notifyDataSetChanged();
        }
    };


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.autocomplte_layout, null);
        }

        EmpList customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.empName);
        name.setText(customer.getEmployeeName());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
