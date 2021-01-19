package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deizaci.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.ListaSvirki;

public class HorizontalniAdapter extends RecyclerView.Adapter<HorizontalniAdapter.ViewHolder>  {

    private Context context;

    public List<ListaSvirki> getLista() {
        return lista;
    }

    public void setLista(List<ListaSvirki> lista) {
        this.lista = lista;
    }

    private List<ListaSvirki> lista;

    public HorizontalniAdapter(Context context, List listItems){
        this.context = context;
        this.lista = listItems;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalna_lista, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalniAdapter.ViewHolder holder, int position) {

        ListaSvirki item = lista.get(position);
        String url = "https://api.polovnitelefoni.net/slike/" + item.getSlika();

        holder.imeLokala.setText(item.getImeLokala());
        holder.koPeva.setText(item.getKoPeva());
        holder.datum.setText(item.getDatum());

        Picasso.with(context).load(url).fit().centerInside().into(holder.slika);


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView imeLokala;
        public TextView koPeva;
        public TextView datum;
        public ImageView slika;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imeLokala = (TextView) itemView.findViewById(R.id.imeKafica);
            koPeva = (TextView) itemView.findViewById(R.id.imePevaca);
            datum = (TextView) itemView.findViewById(R.id.datum);
            slika = (ImageView) itemView.findViewById(R.id.slikaLokala);


        }

        @Override
        public void onClick(View v) {

            int postion = getAdapterPosition();
            ListaSvirki item = lista.get(postion);


        }

    }


}
