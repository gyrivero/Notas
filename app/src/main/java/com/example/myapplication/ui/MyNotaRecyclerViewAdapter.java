package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.myapplication.EditarNotaDialogFragment;
import com.example.myapplication.viewmodel.NuevaNotaDialogViewModel;
import com.example.myapplication.db.entity.NotaEntity;
import com.example.myapplication.R;

import java.util.List;

public class MyNotaRecyclerViewAdapter extends RecyclerView.Adapter<MyNotaRecyclerViewAdapter.ViewHolder> {

    private List<NotaEntity> mValues;
    private Context ctx;
    private NuevaNotaDialogViewModel viewModel;

    public MyNotaRecyclerViewAdapter(List<NotaEntity> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
        viewModel = new ViewModelProvider((AppCompatActivity)ctx).get(NuevaNotaDialogViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_nota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String titulo = holder.mItem.getTitulo();
        String contenido = holder.mItem.getContenido();
        boolean favorita = holder.mItem.getFavorita();
        String color = holder.mItem.getColor();
        int id = holder.mItem.getId();
        holder.tituloTV.setText(holder.mItem.getTitulo());
        holder.contenidoTV.setText(holder.mItem.getContenido());

        holder.cardView.setCardBackgroundColor(getNotaColor(holder));

        holder.tituloTV.setClickable(true);
        holder.tituloTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx,ContenidoNota.class);
                intent.putExtra("titulo",titulo);
                intent.putExtra("contenido",contenido);
                intent.putExtra("id",id);
                ctx.startActivity(intent);
            }
        });

        if (holder.mItem.getFavorita()) {
            holder.favoritaIV.setImageResource(R.drawable.ic_favoritos);
        }

        holder.editarIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("titulo",titulo);
                bundle.putString("contenido",contenido);
                bundle.putString("color",color);
                bundle.putBoolean("favorita",favorita);
                bundle.putInt("id",id);

                FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
                EditarNotaDialogFragment editarNotaDialogFragment = new EditarNotaDialogFragment();
                editarNotaDialogFragment.setArguments(bundle);
                editarNotaDialogFragment.show(fragmentManager,"EditarNotaDialogFragment");
            }
        });

        holder.favoritaIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mItem.getFavorita()) {
                    holder.mItem.setFavorita(false);
                    holder.favoritaIV.setImageResource(R.drawable.ic_star_border_black_24dp);
                }
                else {
                    holder.mItem.setFavorita(true);
                    holder.favoritaIV.setImageResource(R.drawable.ic_favoritos);
                }
                viewModel.updateNota(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNuevasNotas(List<NotaEntity> nuevasNotas) {
        this.mValues = nuevasNotas;
        notifyDataSetChanged();
    }

    public int getNotaColor(ViewHolder holder) {
        switch (holder.mItem.getColor()) {
            case "azul":
                return ctx.getResources().getColor(R.color.azul,null);
            case "rojo":
                return ctx.getResources().getColor(R.color.rojo,null);
            case "verde":
                return ctx.getResources().getColor(R.color.verde,null);
            default:
                return ctx.getResources().getColor(R.color.blanco,null);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tituloTV;
        public final TextView contenidoTV;
        public final ImageView favoritaIV;
        public NotaEntity mItem;
        public final ImageView editarIV;
        public final CardView cardView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tituloTV = view.findViewById(R.id.tituloTV);
            contenidoTV = view.findViewById(R.id.contenidoTV);
            favoritaIV = view.findViewById(R.id.favoritaIV);
            editarIV = view.findViewById(R.id.editarIV);
            cardView = view.findViewById(R.id.notaCV);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + contenidoTV.getText() + "'";
        }
    }
}
