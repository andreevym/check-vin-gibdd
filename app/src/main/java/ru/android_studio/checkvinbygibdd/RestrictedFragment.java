package ru.android_studio.checkvinbygibdd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RestrictedFragment extends Fragment {

    private static final String ARG_TSMODEL = "tsmodel";
    private static final String ARG_TSYEAR = "tsyear";
    private static final String ARG_DATEOGR = "dateogr";
    private static final String ARG_REGNAME = "regname";
    private static final String ARG_DIVTYPE = "divtype";
    private static final String ARG_OGRKOD = "ogrkod";

    private String tsmodel;
    private String tsyear;
    private String dateogr;
    private String regname;
    private String divtype;
    private String ogrkod;

    private TextView tsmodelTV;
    private TextView tsyearTV;
    private TextView dateogrTV;
    private TextView regnameTV;
    private TextView divtypeTV;
    private TextView ogrkodTV;

    public RestrictedFragment() {
    }

    public static RestrictedFragment newInstance(String tsmodel, String tsyear, String dateogr, String regname, String divtype, String ogrkod) {
        RestrictedFragment fragment = new RestrictedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TSMODEL, tsmodel);
        args.putString(ARG_TSYEAR, tsyear);
        args.putString(ARG_DATEOGR, dateogr);
        args.putString(ARG_REGNAME, regname);
        args.putString(ARG_DIVTYPE, divtype);
        args.putString(ARG_OGRKOD, ogrkod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tsmodel = getArguments().getString(ARG_TSMODEL);
            tsyear = getArguments().getString(ARG_TSYEAR);
            dateogr = getArguments().getString(ARG_DATEOGR);
            regname = getArguments().getString(ARG_REGNAME);
            divtype = getArguments().getString(ARG_DIVTYPE);
            ogrkod = getArguments().getString(ARG_OGRKOD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restricted, container, false);

        tsmodelTV = (TextView) view.findViewById(R.id.tsmodelTV);
        tsyearTV = (TextView) view.findViewById(R.id.tsyearTV);
        dateogrTV = (TextView) view.findViewById(R.id.dateogrTV);
        regnameTV = (TextView) view.findViewById(R.id.regnameTV);
        divtypeTV = (TextView) view.findViewById(R.id.divtypeTV);
        ogrkodTV = (TextView) view.findViewById(R.id.ogrkodTV);

        if (getArguments() != null) {
            tsmodelTV.setText(tsmodel);
            tsyearTV.setText(tsyear);
            dateogrTV.setText(dateogr);
            regnameTV.setText(regname);
            divtypeTV.setText(divtype);
            ogrkodTV.setText(ogrkod);
        }
        return view;
    }
}