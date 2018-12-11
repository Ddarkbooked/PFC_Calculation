package com.donaldark.pfccalculation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class ResultFragment extends Fragment {

    private static final String TAG = "ResultFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    View v;
    private RecyclerView myRecyclerView;
    private List<ResultData> firstLine;
    private MyRecyclerViewAdapter recyclerViewAdapter;



    MyRecyclerViewAdapter adapter;




    String firstText;
    private TextView tv_FirstText;


    public ResultFragment() {
    }

    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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

        firstLine = new ArrayList<>();


        Log.d(TAG, "onCreate: "+FirebaseAuth.getInstance().getCurrentUser().getUid());

        db.collection("Results").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        ResultData resultData = new ResultData(documentSnapshot.getString("firstLine"));
                        //firstLine.add(resultData);
                        Log.d(TAG, "onComplete: "+documentSnapshot.getString("firstLine") + " " + firstLine.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for (QueryDocumentSnapshot querySnapshot: task.getResult()) {
//                            ResultData resultData = new ResultData(querySnapshot.getString("firstLine"));
//                            firstLine.add(resultData);
//                        }
//                        adapter = new MyRecyclerViewAdapter(getContext(), firstLine);
//                        myRecyclerView.setAdapter(adapter);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error getting document", e);
//                    }
//                });

//        firstLine.add(new ResultData("1465", "Ваши данные: 167 см, 56 кг, 22"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void notifyRecyclerView(List<ResultData> resultDataList) {
        recyclerViewAdapter.setData(resultDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_result, null, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerViewAdapter = new MyRecyclerViewAdapter(getContext(), firstLine);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        firstLine = new ArrayList<>();


        Log.d(TAG, "onCreate: "+FirebaseAuth.getInstance().getCurrentUser().getUid());

        db.collection("Results").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        ResultData resultData = new ResultData(documentSnapshot.getString("firstLine"));
                        firstLine.add(resultData);
                        Log.d(TAG, "onComplete: "+documentSnapshot.getString("firstLine") + " " + firstLine.toString());
                        notifyRecyclerView(firstLine);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                });



//        db.collection("Results").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for (DocumentChange dc:queryDocumentSnapshots.getDocumentChanges()) {
//                    if (dc.getType() == DocumentChange.Type.ADDED) {
//                        String first;
//                        first = dc.getDocument().getString("firstLine");
//                        Log.d(TAG, "onEvent1: " + first);
//                    }
//                }
//            }
//        });








//        DocumentReference docRef = db.collection("Results").document("tJWpAHlF4pWrTr7hxUSu");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                    } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//
//                }
//            }
//        });


        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
