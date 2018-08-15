package com.rsking175453.com.sgh_try1.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.fragmentCollection.playStoreTime;


public class dialogTime extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sort_dialog, container, false);

        final Spinner taluka = (Spinner)view.findViewById(R.id.talukaSpinner2);
        Spinner district = (Spinner) view.findViewById(R.id.districtSpinner);
        Button cancel =(Button) view.findViewById(R.id.cancelLink);
        Button go =(Button) view.findViewById(R.id.goLink);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String talukaSelted = (String) taluka.getSelectedItem();
                dismiss();
                Log.v("dialog","clicked");
                playStoreTime fragment2 = new playStoreTime();
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                Bundle b = new Bundle();
                b.putString("taluka",talukaSelted);
                fragment2.setArguments(b);
                transaction2.replace(R.id.frameLayout, fragment2);
                transaction2.commit();

            }
        });

//        String[] districts = { "India", "USA", "China", "Japan", "Other",  };
//        final String[][] talukas = { {"name1","name2"}, {"USA","name2","name2"}, {"China","name2"},{"Japan","name2","name2"}, {"Other","name2"}};

        String[] districts={"Ahmedabad","Amreli","Ananad","Aravalli","Banaskantha","Bharuch","Bhavnagar","Botad","Chhota Udaipur","Dahod","Dang","Devbhoomi Dwarka","Gandhinagar","Gir Somnath","Jamnagar","Junagadh","Kutch","Kheda",
                "Mahisagar","Mehsana","Morbi","Narmada","Navsari","Panchmahal","Patan","Porbanadar","Rajkot","Sabarkantha","Surat","Surendranagar","Tapi","Vadodara","Valsad"};
        final String[][] talukas= {{"Ahmedabad","Bavla","Daskroi","Detroj-Rampura","Dhandhuka","Dholera","Dholka","Mandal","Sanand","Viramgam"},{"Amreli","Babra","Bagasara","Dhari","Jafrabad","Khambha","Kunkavav","Vadia","Lathi","Lilia","Rajula","Savarkundla"},{"Anand","Anklav","Borsad","Khambhat","Petlad","Sojitra","Tarapur","Umreth"},{"Bayad","Bhiloda","Dhansura","Malpur","Meghraj","Modasa"},{"Amirgadh","Bhabhar","Danta","Dantiwada","Deesa","Deodar","Dhanera","Kankrej","Lakhani","Palanpur","Suigam","Tharad","Vadgam","Vav"},{"Amirgadh","Bhabhar","Danta","Dantiwada","Deesa","Deodar","Dhanera","Kankrej","Lakhani","Palanpur","Suigam","Tharad","Vadgam","Vav"},{"Bhavnagar","Gariadhar","Ghogha","Jesar","Mahuva","Palitana","Sihor","Talaja","Umrala","Vallabhipur"},{"Botad","Barwala","Gadhada","Ranpur"},{"Chhota Udaipur","Bodeli","Jetpur pavi","Kavant","Nasvadi","Sankheda"},{"Dahod","Devgadh baria","Dhanpur","Fatepura","Garbada","Limkheda","Sanjeli","Jhalod"},{

                "Ahwa",
                "Subir",
                "Waghai"},{"Bhanvad",
                "Kalyanpur",
                "Khambhalia",
                "Okhamandal"},{


                "Gandhinagar",
                "Dehgam",
                "Kalol",
                "Mansa"

        },{


                "Gir-Gadhada",
                "Kodinar",
                "Sutrapada",
                "Talala",
                "Una",
                "Patan-Veraval"},{


                "Jamnagar",
                "Dhrol",
                "Jamjodhpur",
                "Jodiya",
                "Kalavad",
                "Lalpur"},{


                "Junagadh City",
                "Bhesana",
                "Junagadh Rural",
                "Keshod",
                "Malia",
                "Manavadar",
                "Mangrol",
                "Mendarda",
                "Vanthali",
                "Visavadar"},{

                "Abdasa",
                "Anjar",
                "Bhachau",
                "Bhuj",
                "Gandhidham",
                "Lakhpat",
                "Mandvi",
                "Mundra",
                "Nakhatrana",
                "Rapar"},{
                "Kheda",
                "Galteshwar",
                "Kapadvanj",
                "Kathlal",
                "Mahudha",
                "Matar",
                "Mehmedabad",
                "Nadiad",
                "Thasra",
                "Vaso"
        },{

                "Balasinor",
                "Kadana",
                "Khanpur",
                "Lunawada",
                "Santrampur",
                "Virpur"},{


                "Mehsana",
                "Becharaji",
                "Jotana",
                "Kadi",
                "Kheralu",
                "Satlasana",
                "Unjha",
                "Vadnagar",
                "Vijapur",
                "Visnagar",
                "Gojariya"},{


                "Halvad",
                "Maliya",
                "Morbi",
                "Tankara",
                "Wankaner"

        },{

                "Dediapada",
                "Garudeshwar",
                "Nandod",
                "Sagbara",
                "Tilakwada"

        },{"Navsari","Vansda","Chikhli","Gandevi","Jalalpore","Khergam"},{"Ghoghamba","Godhra","Halol","Jambughoda","Kalol","Morwa Hadaf","Shehera"},{"Patan","Chanasma","Harij","Radhanpur","Sami","Sankheswar","Santalpur","Sarasvati","Sidhpur"},{"Porbandar","Kutiyana","Ranavav"},{"Rajkot","Dhoraji","Gondal","Jamkandorna","Jasdan","Jetpur","Kotada Sangani","Lodhika","Paddhari","Upleta","Vinchchiya"},{"Himatnagar","Idar","Khedbrahma","Poshina","Prantij","Talod","Vadali","Vijaynagar"},{"Surat","Bardoli","Choryasi","Kamrej","Mahuva","Mandvi","Mangrol","Olpad","Palsana","Umarpada"},{"Chotila","Chuda","Dasada","Dhrangadhra","Lakhtar","Limbdi","Muli","Sayla","Thangadh","Wadhwan"},{"Nizar","Songadh","Uchchhal","Valod","Vyara","Kukurmunda"},{"Vadodara","Dabhoi","Desar","Karjan","Padra","Savli","Sinor","Vaghodia"},{"Valsad","Dharampur","Kaprada","Pardi","Umbergaon","Vapi"}};



        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, districts);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(adapter2);
        district.setEnabled(true);
        district.setSelection(0);

        district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = adapterView.getItemAtPosition(i).toString();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, talukas[((int) l)]);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                taluka.setAdapter(adapter);
                taluka.setEnabled(true);
                taluka.setSelection(0);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, talukas[0]);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taluka.setAdapter(adapter);
        taluka.setEnabled(true);
        taluka.setSelection(0);




        return view;
    }
@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    }
