package ve.com.palcom.dplevelsizing;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.math.BigDecimal;
import java.math.MathContext;

import ve.com.palcom.unitconverter.Pressure;
import ve.com.palcom.unitconverter.SignedLength;

/**
 * @author Joalbert Palacios
 * @apiNote Version 1.0
 */

public class dpPresenter
{


    public dpPresenter(View view)
    {
    }

    /** This is update the result from the calculation perform in the dp Level sizing
     * in the UI for pressure output
     * @param input pressure in inches of water, from the dpLevelCalculation object
     * @param output TextView target where the output will be wrote
     * @param unitPressureOutput integer which stated the target pressure unit
     * */
    protected static void textViewPressureUpdate(TextView output, Pressure input,
                                          int unitPressureOutput) {
        BigDecimal out = Pressure.convertPressure(input.getValue(), input.getUnit(),
                unitPressureOutput);

        output.setText(out.round(MathContext.DECIMAL128).toString());
    }

    /**
     * Update TextView in the UI for minimum pressure
     * @param minPressureId TextView Id for the resources from de layout
     * @param unitSelected Spinner Id for the spinner
     * @param view all layout where the textview and spinner are
     * @param levelCalcCase Calculation case with input values already load
     * */
    protected static void minimumPressure(int minPressureId, int unitSelected, View view,
                                          dpLevelCalculation levelCalcCase) {
        Spinner unitOut=(Spinner) view.findViewById(unitSelected);
        int outPos=unitOut.getSelectedItemPosition();

        textViewPressureUpdate((TextView) view.findViewById(minPressureId),
                new Pressure(levelCalcCase.getMinPres(), Pressure.INH2O),
                outPos);
    }
        /*
    * Update TextView in the UI for maximum pressure
     * @param minPressureId TextView Id for the resources from de layout
     * @param unitSelected Spinner Id for the spinner
     * @param view all layout where the textview and spinner are
     * @param levelCalcCase Calculation case with input values already load
**/
    protected static void maximumPressure(int maxPressureId, int unitSelected,
                                          View view, dpLevelCalculation levelCalcCase ) {
        Spinner unitOut=(Spinner) view.findViewById(unitSelected);
        int outPos=unitOut.getSelectedItemPosition();
        textViewPressureUpdate((TextView) view.findViewById(maxPressureId),
                new Pressure(levelCalcCase.getMaxPres(), Pressure.INH2O),
                outPos);
    }

    /** This is update the result from the calculation perform in the dp Level sizing
     * in the UI for level output
     * @param input level from the dpLevelCalculation object
     * @param output TextView target where the output will be wrote
     * @param unitLevelOutput integer which stated the target level unit
     * */
    protected static void textViewLevelUpdate(TextView output, SignedLength input,
                                       int unitLevelOutput) {
        BigDecimal out = SignedLength.signedConvertLength(input.getValue(), input.getUnit(),
                unitLevelOutput);
        output.setText(out.round(MathContext.DECIMAL128).toString());
    }


    /**
* Update TextView in the UI for maximum level
 * @param maxLevelId TextView Id for the resources from de layout
 * @param unitSelected Spinner Id for the spinner
 * @param view all layout where the textview and spinner are
 * @param levelCalcCase Calculation case with input values already load
**/
    protected static void maximumLevel(int maxLevelId, int unitSelected,
                                       View view, dpLevelCalculation levelCalcCase
                                ) {
        Spinner unitOut=(Spinner) view.findViewById(unitSelected);
        int outPos=unitOut.getSelectedItemPosition();

        textViewLevelUpdate((TextView) view.findViewById(maxLevelId),
                new SignedLength(levelCalcCase.getMaxLevel(), SignedLength.INCHES),
                outPos);
    }

    /**
     * Update TextView in the UI for minimum level
     * @param minLevelId TextView Id for the resources from de layout
     * @param unitSelected Spinner Id for the spinner
     * @param view all layout where the textview and spinner are
     * @param levelCalcCase Calculation case with input values already load
     **/
    protected static void minimumLevel(int minLevelId, int unitSelected,
                                View view,dpLevelCalculation levelCalcCase)
    {

        Spinner unitOut=(Spinner) view.findViewById(unitSelected);
        int outPos=unitOut.getSelectedItemPosition();

        textViewLevelUpdate((TextView) view.findViewById(minLevelId),
                new SignedLength(levelCalcCase.getMinLevel(), SignedLength.INCHES),
                outPos);
    }

/** Update whole UI for result section. The order of arrays should be
 * as the following: minimum pressure, max pressure, minimum level
 * and maximum level
 * @param TxtView whole Ids for the textview where result needs to be updated
 * @param UnitSpinner wholes ids for the spinner
 * @param levelCalcCase calculation case with whole inputs got from the UI
 * @param v layout where the result will be loaded.
 * */
    protected static void setResult(int [] TxtView, int [] UnitSpinner, View v, dpLevelCalculation
                             levelCalcCase)
    {

    minimumPressure(TxtView[0],UnitSpinner[0],v,levelCalcCase);
    maximumPressure(TxtView[1],UnitSpinner[1],v,levelCalcCase);
    minimumLevel(TxtView[2],UnitSpinner[2],v,levelCalcCase);
    maximumLevel(TxtView[3],UnitSpinner[3],v,levelCalcCase);
    }
/** Get the values for the edit text given in the ids
 * @param editTextIds array with all ids from the EditText in the ViewGroup
 * @param v View where the EditText are available
 * @param val values will be used as default, if EditText is null, this will be in correspondance
 *            with the order in the editTextIds array
 * @return String array with the whole values available in the ViewGroup
 * */
    protected static String[] getEditText(int [] editTextIds,View v,String[] val){

        String [] editTextValue=new String[editTextIds.length];

        for (int i = 0; i < editTextIds.length; i++) {
            EditText temp = (EditText) v.findViewById(editTextIds[i]);
            editTextValue[i] = (temp.getText().toString().compareTo("")==0)?val[i]:
                    temp.getText().toString();
            temp.setText(editTextValue[i]);
        }
        return editTextValue;
    }

    protected static int [] getSpinnerPosition(int [] SpinnerIds, View v) {

       int [] spinnerValue=new int[SpinnerIds.length];
       for(int i=0;i<SpinnerIds.length;i++)
       {
           Spinner temp=(Spinner) v.findViewById(SpinnerIds[i]);
           spinnerValue[i]=temp.getSelectedItemPosition();
       }

        return spinnerValue;
   }

   /**Load whole the inputs as dpLevelCalculation case depending on the length of array
    * @param editTextIds whole inputs values the order will be as per indicated in the layout
    * @param val values to be loaded as default if information is null
    * @param inSpinnerIds units selected in the spinner for every input in the level case
    * @param view ViewGroup where the inputs are available
    * @return dpLevelCalculation with the case loaded
    * */

    protected static dpLevelCalculation getLevelCalcCase(int [] editTextIds,String[] val,
                                 int[] inSpinnerIds, View view){
        String [] inEditText=getEditText(editTextIds,view,val);
        int [] inSpinner=getSpinnerPosition(inSpinnerIds,view);
        SignedLength highChamberFlange, measureHighChamber,bottomHighChamber;
        dpLevelCalculation levelCalc=new dpLevelCalculation();

        int inputLength=inEditText.length;
        switch (inputLength){
            case 5: // p Atmospheric
                highChamberFlange=new SignedLength(
                        new BigDecimal(inEditText[1]),inSpinner[1]
                );
                measureHighChamber=new SignedLength(
                        new BigDecimal(inEditText[0]),inSpinner[0]
                );
                bottomHighChamber=new SignedLength(
                        new BigDecimal(inEditText[2]),inSpinner[2]
                );
                levelCalc= new dpLevelCalculation.Builder(highChamberFlange,
                        measureHighChamber,bottomHighChamber).
                        setSgFillFluid(Double.valueOf(inEditText[3])).
                        setSgLiquid(Double.valueOf(inEditText[4])).
                        build();
                break;
            case 6: // p Pressurized
                highChamberFlange=new SignedLength(
                        new BigDecimal(inEditText[1]),inSpinner[1]
                );
                measureHighChamber=new SignedLength(
                        new BigDecimal(inEditText[0]),inSpinner[0]
                );
                bottomHighChamber=new SignedLength(
                        new BigDecimal(inEditText[2]),inSpinner[2]
                );
                levelCalc= new dpLevelCalculation.Builder(highChamberFlange,
                        measureHighChamber,bottomHighChamber).
                        setStaticPressure(new Pressure(new BigDecimal(inEditText[3]),inSpinner[3])).
                        setSgFillFluid(Double.valueOf(inEditText[4])).
                        setSgLiquid(Double.valueOf(inEditText[5])).
                        build();

                break;
            case 7: // dp Atmospheric
                double sgFillFluid=Double.valueOf(inEditText[5]);
                if(sgFillFluid==0.0)
                {
                    measureHighChamber=new SignedLength(
                        new BigDecimal(inEditText[0]).add(new BigDecimal(
                                inEditText[1]
                        )), inSpinner[0]);
                }
                else
                    {
                        measureHighChamber=new SignedLength(
                                new BigDecimal(inEditText[0]),
                                inSpinner[0]);
                    }
                highChamberFlange = new SignedLength(
                        new BigDecimal(inEditText[1]), inSpinner[1]
                );

                bottomHighChamber=new SignedLength(
                        new BigDecimal(inEditText[2]),inSpinner[2]
                );

                levelCalc= new dpLevelCalculation.Builder(highChamberFlange,
                        measureHighChamber,bottomHighChamber).
                        setFlangesLowChamber(new SignedLength(new BigDecimal(inSpinner[3]),
                                inSpinner[3])).
                        setBottomTankFlangesLowChamber(new SignedLength(new BigDecimal(inEditText[4]),
                                inSpinner[4])).
                        setSgFillFluid(sgFillFluid).
                        setSgLiquid(Double.valueOf(inEditText[6])).
                        build();
                break;
                /*
            case 8: //dp Pressurized
                highChamberFlange=new SignedLength(
                        new BigDecimal(inEditText[1]),inSpinner[1]
                );
                measureHighChamber=new SignedLength(
                        new BigDecimal(inEditText[0]),inSpinner[0]
                );
                bottomHighChamber=new SignedLength(
                        new BigDecimal(inEditText[2]),inSpinner[2]
                );
                levelCalc= new dpLevelCalculation.Builder(highChamberFlange,
                        measureHighChamber,bottomHighChamber).
                        setFlangesLowChamber(new SignedLength(new BigDecimal(inSpinner[3]),
                                inSpinner[3])).
                        setBottomTankFlangesLowChamber(new SignedLength(new BigDecimal(inEditText[4]),
                                inSpinner[4])).
                        setStaticPressure(new Pressure(new BigDecimal(inEditText[5]),inSpinner[5])).
                        setSgFillFluid(Double.valueOf(inEditText[6])).
                        setSgLiquid(Double.valueOf(inEditText[7])).
                        build();

                break;
                */
        }
    return levelCalc;
    }


}

