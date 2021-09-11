package com.example.swuljcityconductor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class ReturnStopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static Context cntxt ;
    static List<String> Stops;
    static int count_stops;
    Button Edit, Add, Delete, Up, Bottom, Back, Done;
    String DialogResult;
    static ReturnStopActivity obj;

    public ReturnStopFragment(Context c, int count_stopsArg, List<String>  StopsArg, ReturnStopActivity Arg) {
        // Required empty public constructor
        cntxt = c;
        count_stops = count_stopsArg;
        Stops = StopsArg;
        obj = Arg;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlusOneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReturnStopFragment newInstance(String param1, String param2) {
        ReturnStopFragment fragment = new ReturnStopFragment(cntxt, count_stops, Stops, obj);
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        //TextView tv= (TextView) view.findViewById(R.id.seat_serial_prefix);
        //tv.setText("yourText");
        Edit = (Button) view.findViewById(R.id.btn_edit) ;
        Add = (Button) view.findViewById(R.id.btn_add) ;
        Delete = (Button) view.findViewById(R.id.btn_delete) ;
        Up = (Button) view.findViewById(R.id.btn_up) ;
        Bottom = (Button) view.findViewById(R.id.btn_bottom) ;
        Back = (Button) view.findViewById(R.id.btn_back) ;
        Done = (Button) view.findViewById(R.id.btn_done) ;
        Up.setVisibility(View.GONE);
        Bottom.setVisibility(View.GONE);
        Back.setVisibility(View.GONE);
        Edit.setVisibility(View.VISIBLE);
        Add.setVisibility(View.VISIBLE);
        Delete.setVisibility(View.VISIBLE);
        RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        // layout params to use when adding each radio button
        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        // add 20 radio buttons to the group
        for (int u = 0; u < count_stops; u++) {
            RadioButton newRadioButton = new RadioButton(cntxt);
            String label = Stops.get(u);
            newRadioButton.setText(label);
            newRadioButton.setId(u);
            radiogroup.addView(newRadioButton, layoutParams);
        }
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();
                DialogResult = Stops.get(a);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(cntxt);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(cntxt);
                input.setText(DialogResult);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DialogResult = input.getText().toString();
                                Stops.set(a, DialogResult);
                                ((RadioButton) radiogroup.getChildAt(a)).setText(DialogResult);
                                //radiogroup.removeViewAt(a);
                                /*RadioButton newRadioButton = new RadioButton(cntxt);
                                String label = DialogResult;
                                newRadioButton.setText(label);
                                newRadioButton.setId(a);
                                radiogroup.addView(newRadioButton,a);
                                radiogroup.removeViewAt(a+1);*/
                            }
                        }).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }

        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Up.setVisibility(View.VISIBLE);
                Bottom.setVisibility(View.VISIBLE);
                Back.setVisibility(View.VISIBLE);
                Add.setVisibility(View.GONE);
                Edit.setVisibility(View.GONE);
                Delete.setVisibility(View.GONE);
            }

        });


        Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();
                DialogResult = Stops.get(a);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(cntxt);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(cntxt);
                input.setText(DialogResult);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DialogResult = input.getText().toString();
                                Stops.add(a, DialogResult);

                                RadioButton newRadioButton = new RadioButton(cntxt);
                                String label = DialogResult;
                                newRadioButton.setText(label);
                                newRadioButton.setId(a);
                                radiogroup.addView(newRadioButton,a);
                                count_stops++;
                                for(int i = a; i< count_stops; i++)
                                {
                                    ((RadioButton) radiogroup.getChildAt(i)).setId(i);
                                }

                            }
                        }).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();

            }

        });

        Bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();
                DialogResult = Stops.get(a);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(cntxt);
                alertDialog.setTitle("PASSWORD");
                alertDialog.setMessage("Enter Password");

                final EditText input = new EditText(cntxt);
                input.setText(DialogResult);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);

                alertDialog.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                DialogResult = input.getText().toString();
                                Stops.add(a + 1 , DialogResult);

                                RadioButton newRadioButton = new RadioButton(cntxt);
                                String label = DialogResult;
                                newRadioButton.setText(label);
                                newRadioButton.setId(a);
                                radiogroup.addView(newRadioButton,a+1);
                                count_stops++;
                                for(int i = a; i< count_stops; i++)
                                {
                                    ((RadioButton) radiogroup.getChildAt(i)).setId(i);
                                }
                            }
                        }).setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }


        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Up.setVisibility(View.GONE);
                Bottom.setVisibility(View.GONE);
                Back.setVisibility(View.GONE);
                Add.setVisibility(View.VISIBLE);
                Edit.setVisibility(View.VISIBLE);
                Delete.setVisibility(View.VISIBLE);
            }

        });


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();
                radiogroup.removeViewAt(a);
                Stops.remove(a);
                count_stops--;
                for(int i = a; i< count_stops; i++)
                {
                    ((RadioButton) radiogroup.getChildAt(i)).setId(i);
                }

            }

        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.changeIntent(count_stops);

            }

        });


        return view;
    }


}