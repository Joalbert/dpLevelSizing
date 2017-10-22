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
 * Use the {@link dpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dpFragment extends Fragment {



    public dpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment dpPsFragment.
     */
    public static dpFragment newInstance()
    {
        return new dpFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        View v=inflater.inflate(R.layout.dp_atmospheric, container, false);



        // Populating units for pressure and height
        ArrayAdapter<CharSequence> pressureUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.pressureUnit,android.R.layout.simple_spinner_item);
        pressureUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []presSpinnerIds={R.id.dpMinPressureUnit,R.id.dpMaxPressureUnit};

        for (int presSpinnerId : presSpinnerIds) {
            Spinner pressureSpinner = (Spinner) v.findViewById(presSpinnerId);
            pressureSpinner.setAdapter(pressureUnits);
        }

        // Populating Spinner Length
        ArrayAdapter<CharSequence> LengthUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.lengthUnit,android.R.layout.simple_spinner_item);
        LengthUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []spinnerIds={R.id.dpMinHeightUnit,R.id.dpMaxHeightUnit,R.id.dpMaximumLevelHighChamberUnit,
                R.id.dpHighChamberToFlangeUnit,R.id.dpBottomTankHighChamberFlangeUnit,
                R.id.dpLowChamberToFlangeUnit,
                R.id.dpBottomTankLowChamberFlangeUnit};

        for (int spinnerId : spinnerIds) {
            Spinner out = (Spinner) v.findViewById(spinnerId);

            out.setAdapter(LengthUnits);
        }

        //--------------------------------------------------------------------
        // Populating TextView Results
        int []textViewId={R.id.dpMinPressure,R.id.dpMaxPressure,
                R.id.dpMinHeight,R.id.dpMaxHeight};
        for (int aTextViewId : textViewId) {
            TextView output = (TextView) v.findViewById(aTextViewId);
            output.setText("0");

        }
        // --------------------------------------------------------------------

        return v;
    }

    public int[] getEditTextIds(){

        return new int[]{R.id.dpMaximumLevelHighChamber, R.id.dpHighChamberToFlange,
    R.id.dpBottomTankHighChamberFlange, R.id.dpLowChamberToFlange,
            R.id.dpBottomTankLowChamberFlange,
        R.id.dpSgFill, R.id.dpSgLiquid};
    }

    protected int[] getInSpinner(){

        return new int []{R.id.dpMaximumLevelHighChamberUnit, R.id.dpHighChamberToFlangeUnit,
                R.id.dpBottomTankHighChamberFlangeUnit, R.id.dpLowChamberToFlangeUnit,
                R.id.dpBottomTankLowChamberFlangeUnit};

    }

    protected int[] getOutSpinner(){

        return new int []{R.id.dpMinPressureUnit, R.id.dpMaxPressureUnit,
                R.id.dpMinHeightUnit, R.id.dpMaxHeightUnit};
    }

    protected int[] getOutTextView(){
        return new int []{R.id.dpMinPressure, R.id.dpMaxPressure,
                R.id.dpMinHeight, R.id.dpMaxHeight};
    }

}
