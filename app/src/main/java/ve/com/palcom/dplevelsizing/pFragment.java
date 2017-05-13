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
        return new pFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        View v=inflater.inflate(R.layout.p_atmospheric, container, false);



        // Populating units for pressure and height
        ArrayAdapter<CharSequence> pressureUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.pressureUnit,android.R.layout.simple_spinner_item);
        pressureUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []presSpinnerIds={R.id.pMinPressureUnit,R.id.pMaxPressureUnit};

        for (int presSpinnerId : presSpinnerIds) {
            Spinner pressureSpinner = (Spinner) v.findViewById(presSpinnerId);
            pressureSpinner.setAdapter(pressureUnits);
        }

        // Populating Spinner Length
        ArrayAdapter<CharSequence> LengthUnits=ArrayAdapter.createFromResource(getActivity(),
                R.array.lengthUnit,android.R.layout.simple_spinner_item);
        LengthUnits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        int []spinnerIds={R.id.pMinHeightUnit,R.id.pMaxHeightUnit,R.id.pMaximumLevelHighChamberUnit,
                R.id.pHighChamberToFlangeUnit,R.id.pBottomTankHighChamberFlangeUnit,
                };

        for (int spinnerId : spinnerIds) {
            Spinner out = (Spinner) v.findViewById(spinnerId);

            out.setAdapter(LengthUnits);
        }

        //--------------------------------------------------------------------
        // Populating TextView Results
        int []textViewId={R.id.pMinPressure,R.id.pMaxPressure,
                R.id.pMinHeight,R.id.pMaxHeight};
        for (int aTextViewId : textViewId) {
            TextView output = (TextView) v.findViewById(aTextViewId);
            output.setText("0");

        }
        // --------------------------------------------------------------------

        return v;
    }


    protected int[] getEditTextIds(){

    return new int[]{R.id.pMaximumLevelHighChamber, R.id.pHighChamberToFlange,
            R.id.pBottomTankHighChamberFlange, R.id.pSgFill, R.id.pSgLiquid};
    }

    protected int[] getInSpinner(){

        return new int[]{R.id.pMaximumLevelHighChamberUnit, R.id.pHighChamberToFlangeUnit,
                R.id.pBottomTankHighChamberFlangeUnit};

    }

    protected int[] getOutSpinner(){

        return new int[]{R.id.pMinPressureUnit, R.id.pMaxPressureUnit,
                R.id.pMinHeightUnit, R.id.pMaxHeightUnit};
    }

    protected int[] getOutTextView(){
        return new int[]{R.id.pMinPressure, R.id.pMaxPressure,
                R.id.pMinHeight, R.id.pMaxHeight};
    }

}
