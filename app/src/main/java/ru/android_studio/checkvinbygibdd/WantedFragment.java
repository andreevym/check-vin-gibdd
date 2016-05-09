package ru.android_studio.checkvinbygibdd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WantedFragment extends Fragment {

    private static final String ARG_MODEL = "model";
    private static final String ARG_GODVYP = "godVyp";
    private static final String ARG_DATAPU = "dataPu";
    private static final String ARG_REGZN = "regZn";
    private static final String ARG_KUZOV = "kuzov";
    private static final String ARG_SHASSI = "shassi";
    private static final String ARG_REGINIC = "regInic";
    private static final String ARG_DATAOPER = "dataOper";

    private String model;
    private String godVyp;
    private String dataPu;
    private String regZn;
    private String kuzov;
    private String shassi;
    private String regInic;
    private String dataOper;

    private TextView modelTV;
    private TextView godVypTV;
    private TextView dataPuTV;
    private TextView regZnTV;
    private TextView kuzovTV;
    private TextView shassiTV;
    private TextView regInicTV;
    private TextView dataOperTV;

    public WantedFragment() {
    }

    public static WantedFragment newInstance(String model, String godVyp, String dataPu, String regZn, String kuzov, String shassi, String regInic, String dataOper) {
        WantedFragment fragment = new WantedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MODEL, model);
        args.putString(ARG_GODVYP, godVyp);
        args.putString(ARG_DATAPU, dataPu);
        args.putString(ARG_REGZN, regZn);
        args.putString(ARG_KUZOV, kuzov);
        args.putString(ARG_SHASSI, shassi);
        args.putString(ARG_REGINIC, regInic);
        args.putString(ARG_DATAOPER, dataOper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            model = getArguments().getString(ARG_MODEL);
            godVyp = getArguments().getString(ARG_GODVYP);
            dataPu = getArguments().getString(ARG_DATAPU);
            regZn = getArguments().getString(ARG_REGZN);
            kuzov = getArguments().getString(ARG_KUZOV);
            shassi = getArguments().getString(ARG_SHASSI);
            regInic = getArguments().getString(ARG_REGINIC);
            dataOper = getArguments().getString(ARG_DATAOPER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wanted, container, false);

        modelTV = (TextView) view.findViewById(R.id.modelTV);
        godVypTV = (TextView) view.findViewById(R.id.godVypTV);
        dataPuTV = (TextView) view.findViewById(R.id.dataPuTV);
        regZnTV = (TextView) view.findViewById(R.id.regZnTV);
        kuzovTV = (TextView) view.findViewById(R.id.kuzovTV);
        shassiTV = (TextView) view.findViewById(R.id.shassiTV);
        regInicTV = (TextView) view.findViewById(R.id.regInicTV);
        dataOperTV = (TextView) view.findViewById(R.id.dataOperTV);

        if (getArguments() != null) {
            modelTV.setText(model);
            godVypTV.setText(godVyp);
            dataPuTV.setText(dataPu);
            regZnTV.setText(regZn);
            kuzovTV.setText(kuzov);
            shassiTV.setText(shassi);
            regInicTV.setText(regInic);
            dataOperTV.setText(dataOper);
        }
        return view;
    }
}