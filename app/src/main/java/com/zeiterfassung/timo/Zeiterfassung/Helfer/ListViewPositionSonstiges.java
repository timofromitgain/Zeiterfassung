package com.zeiterfassung.timo.Zeiterfassung.Helfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zeiterfassung.timo.Zeiterfassung.Beans.Position;
import com.zeiterfassung.timo.Zeiterfassung.R;

import java.util.ArrayList;

public class ListViewPositionSonstiges extends ArrayAdapter<Position> {
    ArrayList<Position> listPosition = new ArrayList<Position>();
    private Boolean kundeAnmerkung;

    private String fragment;
    private Context context;
    private int resource;

    public ListViewPositionSonstiges(Context context, int resource, ArrayList<Position> position) {
        super(context, resource, position);
        this.context = context;
        this.resource = resource;
        this.listPosition = position;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, viewGroup, false);
            holder = new ViewHolder();
            holder.tvFahrtfolge = view.findViewById(R.id.posFahrtfolge);
            holder.tvDauer = view.findViewById(R.id.posDauer);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }


        try {
            holder.tvFahrtfolge.setText(listPosition.get(position).getKundeVorher().getFirma() + "  ->  " + listPosition.get(position).getKundeAktuell().getFirma());
            holder.tvDauer.setText(listPosition.get(position).getDauerString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private static class ViewHolder {
        private TextView
                tvFahrtfolge,
                tvDauer;


    }


}