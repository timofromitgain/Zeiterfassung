package com.zeiterfassung.timo.Zeiterfassung.Helfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zeiterfassung.timo.Zeiterfassung.R;

import java.util.ArrayList;

public class ListViewKundenAdapter extends ArrayAdapter<Kunde> {
    ArrayList<Kunde> listKunde = new ArrayList<Kunde>();
    private Boolean kundeAnmerkung;

    private String fragment;
    private Context context;
    private int resource;
    boolean mapActive;
    private static class ViewHolder {
        private TextView
                tvId,
                tvFirma,
                tvAnsprechpartner,
                tvStrasse,
                tvStadt,
                tvTaetigkeit1,
                tvTaetigkeit2,
                tvTaetigkeit3;

    }


    public ListViewKundenAdapter(Context context, int resource, ArrayList<Kunde> kunde, Boolean mapActive) {
        super(context, resource, kunde);
        this.context = context;
        this.resource = resource;
        this.listKunde = kunde;
        this.fragment = fragment;
        this.mapActive = mapActive;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        String barzahler = context.getString(R.string.barzahler);
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, viewGroup, false);
            holder = new ViewHolder();
            holder.tvId = view.findViewById(R.id.rvid);
            holder.tvFirma = view.findViewById(R.id.rvFirma);
            holder.tvAnsprechpartner = view.findViewById(R.id.rvAnsprechpartner);
            holder.tvStrasse = view.findViewById(R.id.rvStrasse);
            holder.tvStadt = view.findViewById(R.id.rvOrt);
            holder.tvTaetigkeit1 = view.findViewById(R.id.rvTaetigkeit1);
     //       holder.tvTaetigkeit2 = view.findViewById(R.id.rvTaetigkeit2);
       //     holder.tvTaetigkeit3 = view.findViewById(R.id.rvTaetigkeit3);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }


        holder.tvId.setText(String.valueOf(position+1));

        holder.tvStrasse.setText(listKunde.get(position).getStrasse());
        holder.tvStadt.setText(listKunde.get(position).getStadt());
        holder.tvFirma.setText(listKunde.get(position).getFirma());
        holder.tvAnsprechpartner.setText(listKunde.get(position).getAnsprechpartner());
        holder.tvTaetigkeit1.setText(listKunde.get(position).getTaetigkeitString());
     //   holder.tvTaetigkeit2.setText(listKunde.get(position).getTaetigkeit_2());
      //  holder.tvTaetigkeit3.setText(listKunde.get(position).getTaetigkeit_3());

        if (mapActive){
            holder.tvTaetigkeit1.setVisibility(View.GONE);
        }
        return view;
    }


}