package com.rsking175453.com.sgh_try1.old;

        import android.content.Context;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.rsking175453.com.sgh_try1.Adapter;
        import com.rsking175453.com.sgh_try1.R;
        import com.rsking175453.com.sgh_try1.fragmentCollection.linearListDataAdapter;
        import com.rsking175453.com.sgh_try1.models.SectionDataModel;
        import com.rsking175453.com.sgh_try1.models.SingleItemModel;

        import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    RecyclerView r1;
    ArrayList<SingleItemModel> sampleData;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard.OnFragmentInteractionListener mListener;

    public tryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard newInstance(String param1, String param2) {
        com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard fragment = new com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);
        r1 = (RecyclerView) view.findViewById(R.id.userRecycleView);
        r1.setHasFixedSize(true);
        createDummyData();
        linearListDataAdapter adapter = new linearListDataAdapter(getActivity(),sampleData);
        r1.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        r1.setAdapter(adapter);



        return view;
    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);

        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }

    public void createDummyData() {

        ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
        for (int j = 0; j <= 5; j++) {
            //singleItem.add(new SingleItemModel("a","b","c","c","e","f"));
        }


        sampleData = singleItem;

    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
