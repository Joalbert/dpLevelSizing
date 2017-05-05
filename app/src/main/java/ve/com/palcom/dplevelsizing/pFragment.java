package ve.com.palcom.dplevelsizing;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link pFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pFragment extends Fragment {

    private View v;


    public pFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment dpPsFragment.
     */
    public static pFragment newInstance()
    {
        pFragment fragment = new pFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        v=inflater.inflate(R.layout.p_atmospheric, container, false);



        // Populating units for pressure and height
        ArrayAdapter<CharSequence> pressureUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.pressureUnit,android.R.layout.simple_spinner_item);
        pressureUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []presSpinnerIds={R.id.pMinPressureUnit,R.id.pMaxPressureUnit};

        for(int i=0;i<presSpinnerIds.length;i++) {
            Spinner pressureSpinner = (Spinner) v.findViewById(presSpinnerIds[i]);
            pressureSpinner.setAdapter(pressureUnits);
        }

        // Populating Spinner Length
        ArrayAdapter<CharSequence> LengthUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.lengthUnit,android.R.layout.simple_spinner_item);
        LengthUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []spinnerIds={R.id.pMinHeightUnit,R.id.pMaxHeightUnit,R.id.pMaximumLevelHighChamberUnit,
                R.id.pHighChamberToFlangeUnit,R.id.pBottomTankHighChamberFlangeUnit,
                };

        for(int i=0;i<spinnerIds.length;i++) {
            Spinner out = (Spinner) v.findViewById(spinnerIds[i]);

            out.setAdapter(LengthUnits);
        }

        //--------------------------------------------------------------------
        // Populating TextView Results
        int []textViewId={R.id.pMinPressure,R.id.pMaxPressure,
                R.id.pMinHeight,R.id.pMaxHeight};
        for (int i=0;i<textViewId.length;i++) {
            TextView output = (TextView) v.findViewById(textViewId[i]);
            output.setText("0");

        }
        // --------------------------------------------------------------------

        return v;
    }


    protected int[] getEditTextIds(){

        int []out={R.id.pMaximumLevelHighChamber, R.id.pHighChamberToFlange,
    R.id.pBottomTankHighChamberFlange, R.id.pSgFill, R.id.pSgLiquid};
    return out;
    }

    protected int[] getInSpinner(){

        int []out={R.id.pMaximumLevelHighChamberUnit, R.id.pHighChamberToFlangeUnit,
                R.id.pBottomTankHighChamberFlangeUnit};
        return out;
    }

    protected int[] getOutSpinner(){

        int []out={R.id.pMinPressureUnit, R.id.pMaxPressureUnit,
                R.id.pMinHeightUnit, R.id.pMaxHeightUnit};
        return out;
    }

    protected int[] getOutTextView(){
        int []out={R.id.pMinPressure, R.id.pMaxPressure,
                R.id.pMinHeight, R.id.pMaxHeight};
        return out;
    }

}
