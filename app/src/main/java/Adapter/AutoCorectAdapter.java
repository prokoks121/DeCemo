package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.deizaci.ListaKafica;
import com.example.deizaci.R;
import com.example.deizaci.kafic;

import java.util.ArrayList;
import java.util.List;

public class AutoCorectAdapter extends ArrayAdapter<ListaKafica>  {
private List<ListaKafica> listaKafica ;
private Context context;
    public AutoCorectAdapter(@NonNull Context context,  @NonNull List<ListaKafica> objects) {
        super(context, 0, objects);
        this.context = context;
        listaKafica = new ArrayList<>(objects);
    }



    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.auto_correct, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.text_view_name);
        ListaKafica countryItem = getItem(position);

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context, kafic.class);
                intent.putExtra("Kafic", (Parcelable) countryItem);
                context.startActivity(intent);
            }
        });
        if (countryItem != null) {
            textViewName.setText(countryItem.getIme());
        }
        return convertView;


    }






    private Filter filter = new Filter() {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ListaKafica> sugg = new ArrayList<>();




            if (constraint == null || constraint.length() == 0) {
                sugg.addAll(listaKafica);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ListaKafica item : listaKafica) {
                    if (item.getIme().toLowerCase().contains(filterPattern)) {
                        sugg.add(item);
                    }
                }
            }
            results.values = sugg;
            results.count = sugg.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ListaKafica) resultValue).getIme();

        }
    };


}
