package com.example.timo.Zeiterfassung.Helfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.timo.Zeiterfassung.Beans.Position;
import com.example.timo.Zeiterfassung.R;

import java.util.ArrayList;

public class ListViewPositionAdapter extends ArrayAdapter<Position> {
    ArrayList<Position> listPosition = new ArrayList<Position>();
    LayoutInflater inflater;
    private Boolean kundeAnmerkung;
    private String fragment;
    private Context context;
    private int resource;

    public ListViewPositionAdapter(Context context, int resource, ArrayList<Position> position) {
        super(context, resource, position);
        Log.d("changeevent", "ListViewPositionAdapter call ");
        String gg = "ff";
        Log.d("eventhandler","listpo");
        this.context = context;
        this.resource = resource;
        this.listPosition = position;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.d("changeevent", "ListViewPositionAdapterView call ");
        ViewHolder holder;
        String barzahler = context.getString(R.string.barzahler);
        if (view == null) {
            inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, viewGroup, false);
            holder = new ViewHolder();
            holder.tvId = view.findViewById(R.id.posId);
            holder.tvFirma = view.findViewById(R.id.posFirma);
            holder.tvAnsprechpartner = view.findViewById(R.id.posAnsprechpartner);
            holder.tvStrasse = view.findViewById(R.id.posStrasse);
            holder.tvStadt = view.findViewById(R.id.posOrt);
            holder.tvTaetigkeit = view.findViewById(R.id.posTaetigkeit);
            holder.tvDauer = view.findViewById(R.id.posDauer);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

        if (listPosition.get(position).getKunde() != null) {

            if (listPosition.get(position).getNamePosition().equals("AKTUELL")) {
                holder.tvDauer.setText(listPosition.get(position).getDauerStringSeit());
            } else {
                holder.tvDauer.setText(listPosition.get(position).getDauerString());
            }

            if (listPosition.get(position).getKunde().getFirma().equals("Firma")) {
                holder.tvId.setVisibility(View.GONE);
                holder.tvStrasse.setVisibility(View.GONE);
                holder.tvStadt.setVisibility(View.GONE);
                holder.tvAnsprechpartner.setVisibility(View.GONE);
                holder.tvTaetigkeit.setVisibility(View.GONE);
                holder.tvFirma.setText(listPosition.get(position).getKunde().getFirma());
            } else {

                //VISIBLE
                holder.tvId.setVisibility(View.GONE);
                holder.tvStrasse.setVisibility(View.GONE);
                holder.tvStadt.setVisibility(View.GONE);
                holder.tvAnsprechpartner.setVisibility(View.GONE);
             //   holder.tvTaetigkeit.setVisibility(View.GONE);

                holder.tvId.setText(String.valueOf(position + 1));
                holder.tvStrasse.setText(listPosition.get(position).getKunde().getStrasse());
                holder.tvStadt.setText(listPosition.get(position).getKunde().getStadt());
                holder.tvFirma.setText(listPosition.get(position).getKunde().getFirma());
                holder.tvAnsprechpartner.setText(listPosition.get(position).getKunde().getAnsprechpartner());
                if (listPosition.get(position).getKunde().getAuswahlTaetigkeit() == null) {

                   holder.tvTaetigkeit.setText(listPosition.get(position).getKunde().getListAuftrag().get(0));
                } else {
                    holder.tvTaetigkeit.setText(listPosition.get(position).getKunde().getAuswahlTaetigkeit());
                }
            }
        } else {


            String firmaVorher = "", firmaAktuell = "", firmaDummy = "";
            try {
                firmaVorher = listPosition.get(position).getKundeVorher().getFirma();

            } catch (Exception e) {
           /*     Kunde kundeDummy = new Kunde();
                kundeDummy.setFirma("?");
                listPosition.get(position).setKundeVorher(kundeDummy);*/
                firmaVorher = "?";
                e.printStackTrace();
            }


            try {
                firmaAktuell = listPosition.get(position).getKundeAktuell().getFirma();

            } catch (Exception e) {
           /*     Kunde kundeDummy = new Kunde();
                kundeDummy.setFirma("?");
                listPosition.get(position).setKundeAktuell(kundeDummy);*/
                firmaAktuell = "?";
                e.printStackTrace();
            }
          /*
            if (firmaVorher == null) {
                    firmaDummy = "? -> ?";
                } else if (firmaAktuell == null) {
                    firmaDummy = firmaAktuell + " -> " + "?";
                }
                */
            firmaDummy = "Fahrtzeit";
            if (listPosition.get(position).getNamePosition().equals("AKTUELL")) {


                holder.tvFirma.setText(firmaDummy);
                holder.tvDauer.setText(listPosition.get(position).getDauerStringSeit());
            } else {
                holder.tvFirma.setText(firmaDummy);
                holder.tvDauer.setText(listPosition.get(position).getDauerString());
            }

            holder.tvId.setVisibility(View.GONE);
            holder.tvStrasse.setVisibility(View.GONE);
            holder.tvStadt.setVisibility(View.GONE);
            holder.tvAnsprechpartner.setVisibility(View.GONE);
            holder.tvTaetigkeit.setVisibility(View.GONE);


        }


        return view;
    }

    private static class ViewHolder {
        private TextView
                tvId,
                tvFirma,
                tvAnsprechpartner,
                tvStrasse,
                tvStadt,
                tvTaetigkeit,
                tvDauer,
                tvBeschreibung,
                tvTest;


    }


}