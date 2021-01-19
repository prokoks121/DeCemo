package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deizaci.ListaKafica;
import com.example.deizaci.R;
import com.example.deizaci.kafic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;

    private final List<ListaKafica> lista;

    public MyAdapter(Context context, List listItems){
        this.context = context;
        this.lista = listItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        ListaKafica item = lista.get(position);


        String url = "https://api.polovnitelefoni.net/slike/" + item.getSlika();
        holder.name.setText(item.getIme());
        holder.adresa.setText(item.getAdresa());
        holder.vrsta.setText(item.getVrsta());
        Picasso.with(context).load(url).fit().centerInside().into(holder.slika);


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView adresa;
        public TextView vrsta;
        public ImageView slika;
        public RelativeLayout lin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            lin = itemView.findViewById(R.id.list_item1);

            name = itemView.findViewById(R.id.imeKafica);
            adresa = itemView.findViewById(R.id.vrsta);
            vrsta = itemView.findViewById(R.id.adresa);
            slika = itemView.findViewById(R.id.slikaLokalaL);


        }

        @Override
        public void onClick(View v) {

            int postion = getAdapterPosition();
           ListaKafica item = lista.get(postion);

            Intent intent =  new Intent(context, kafic.class);
            intent.putExtra("Kafic", item);
            context.startActivity(intent);
        }

    }


}
