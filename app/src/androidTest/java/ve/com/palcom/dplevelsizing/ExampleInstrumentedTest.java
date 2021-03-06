package ve.com.palcom.dplevelsizing;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.MathContext;

import ve.com.palcom.unitconverter.Pressure;
import ve.com.palcom.unitconverter.SignedLength;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;



/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    private int [] inputTextViews ={
            R.id.dpHighChamberToFlange,
            R.id.dpMaximumLevelHighChamber,
            R.id.dpBottomTankHighChamberFlange,
            R.id.dpLowChamberToFlange,
            R.id.dpBottomTankLowChamberFlange,
            R.id.dpSgFill, R.id.dpSgLiquid
            };

    private int [] inputScrollViews={
            R.id.dpHighChamberToFlangeUnit,
            R.id.dpMaximumLevelHighChamberUnit,
            R.id.dpBottomTankHighChamberFlangeUnit,
            R.id.dpLowChamberToFlangeUnit,
            R.id.dpBottomTankLowChamberFlangeUnit,
            R.id.dpMinPressureUnit,
            R.id.dpMaxPressureUnit,
            R.id.dpMinHeightUnit,
            R.id.dpMaxHeightUnit
    };



    private int [] outputViews = {R.id.dpMinPressure,R.id.dpMaxPressure,R.id.dpMinHeight,
            R.id.dpMaxHeight};


    private int [] inputTextViewsP ={
            R.id.pHighChamberToFlange,
            R.id.pMaximumLevelHighChamber,
            R.id.pBottomTankHighChamberFlange,
            R.id.pSgFill, R.id.pSgLiquid
    };

    private int [] inputScrollViewsP={
            R.id.pHighChamberToFlangeUnit,R.id.pMaximumLevelHighChamberUnit,
            R.id.pBottomTankHighChamberFlangeUnit,
            R.id.pMinPressureUnit,R.id.pMaxPressureUnit,R.id.pMinHeightUnit,
            R.id.pMaxHeightUnit
    };


    private int [] outputViewsP = {R.id.pMinPressure,R.id.pMaxPressure,R.id.pMinHeight,
            R.id.pMaxHeight};


    private int [] inputTextViewsPps ={
            R.id.ppsHighChamberToFlange,
            R.id.ppsMaximumLevelHighChamber,
            R.id.ppsBottomTankHighChamberFlange,
            R.id.ppsPressureTank,
            R.id.ppsSgFill, R.id.ppsSgLiquid
    };

    private int [] inputScrollViewsPps={
            R.id.ppsHighChamberToFlangeUnit,R.id.ppsMaximumLevelHighChamberUnit,
            R.id.ppsBottomTankHighChamberFlangeUnit,R.id.ppsPressureTankUnit,
            R.id.ppsMinPressureUnit,R.id.ppsMaxPressureUnit,R.id.ppsMinHeightUnit,
            R.id.ppsMaxHeightUnit
    };


    private int [] outputViewsPps = {R.id.ppsMinPressure,R.id.ppsMaxPressure,R.id.ppsMinHeight,
            R.id.ppsMaxHeight};


    private String [] tabs={"Two Legs","One Leg - Pressurized Tank","One Leg - Atmospheric Tank"};

    private final int DP_MODE=0;
    private final int P_PS_MODE=1;
    private final int P_MODE=2;

    @Rule
    public ActivityTestRule<dpActivity> mActivity = new ActivityTestRule<>(
            dpActivity.class);


    public static boolean check(BigDecimal expected,BigDecimal tested)
    {
        double aceptedDev=0.00001;
        double result;
        result=tested.subtract(expected).doubleValue();
        return (result>aceptedDev||result<(-1*aceptedDev));
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("ve.com.palcom.dplevelsizing", appContext.getPackageName());
    }
    @Test
    public void levelTestPressurizedTank(){

        Pressure P1=new Pressure(new BigDecimal(15.0),Pressure.PSI);
        SignedLength lowChamber=new SignedLength(new BigDecimal(114.0), SignedLength.INCHES);
        SignedLength highChamber=new SignedLength(BigDecimal.ZERO,SignedLength.INCHES);
        SignedLength lowFlange=new SignedLength(BigDecimal.ZERO, SignedLength.INCHES);
        SignedLength highFlange=new SignedLength(new BigDecimal(114.0),SignedLength.INCHES);
        SignedLength bottomLowFlange=new SignedLength(new BigDecimal(120.0),SignedLength.INCHES);
        SignedLength bottomHighFlange=new SignedLength(new BigDecimal(6.0),SignedLength.INCHES);
        double sgfill=0.934; // Silicone
        double sgWater=0.893; //
        dpLevelCalculation dp= new dpLevelCalculation(
                lowChamber,lowFlange,bottomLowFlange,highChamber,highFlange,
                bottomHighFlange,P1,P1,sgfill,sgfill,sgWater);
        BigDecimal dpValue=dp.getMaxPres();
        if(check(new BigDecimal(-4.674),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));

        dpValue=dp.getMinPres();
        if(check(new BigDecimal(-106.476),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));

        dpValue=dp.getPresAnyLength(new SignedLength(new BigDecimal(106.0),SignedLength.INCHES),
                new SignedLength(BigDecimal.ZERO,SignedLength.INCHES));
        if(check(new BigDecimal(-11.818),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));

        dpValue=dp.getPresAnyLength(new SignedLength(new BigDecimal(6.0),SignedLength.INCHES),
                new SignedLength(BigDecimal.ZERO,SignedLength.INCHES));
        if(check(new BigDecimal(-101.118),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));


    }

    @Test
    public void levelTestOneLeg(){

        Pressure P1=new Pressure(new BigDecimal(0.0),Pressure.PSI);
        SignedLength lowChamber=new SignedLength(BigDecimal.ZERO, SignedLength.INCHES);
        SignedLength highChamber=new SignedLength(BigDecimal.ZERO,SignedLength.INCHES);
        SignedLength lowFlange=new SignedLength(BigDecimal.ZERO, SignedLength.INCHES);
        SignedLength highFlange=new SignedLength(new BigDecimal(324.0),SignedLength.INCHES);
        SignedLength bottomLowFlange=new SignedLength(BigDecimal.ZERO,SignedLength.INCHES);
        SignedLength bottomHighFlange=new SignedLength(new BigDecimal(8.0),SignedLength.INCHES);
        double sgfill=0.0; // Silicone
        double sgWater=0.997; //
        dpLevelCalculation dp= new dpLevelCalculation(
                lowChamber,lowFlange,bottomLowFlange,highChamber,highFlange,
                bottomHighFlange,P1,P1,sgfill,sgfill,sgWater);
        BigDecimal dpValue=dp.getMaxPres();
        if(check(new BigDecimal(323.028),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));


        dpValue=dp.getPresAnyLength(new SignedLength(new BigDecimal(320.0),SignedLength.INCHES),
                new SignedLength(BigDecimal.ZERO,SignedLength.INCHES));
        if(check(new BigDecimal(319.04),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));

        dpValue=dp.getPresAnyLength(new SignedLength(new BigDecimal(28.0),SignedLength.INCHES),
                new SignedLength(BigDecimal.ZERO,SignedLength.INCHES));
        if(check(new BigDecimal(27.916),dpValue)) throw new AssertionError("Level Measured: "+
                String.valueOf(dpValue.doubleValue()));


    }

    String getText(final int id) {
        final String[] stringHolder = { null };
        Espresso.onView(withId(id)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    public void calculation(String[] inputValues, int[]inputSpinnerValues,
                            String [] outputValues, int mode)
    {
        int [] inputScrollViews=this.inputScrollViews;
        int []inputTextViews=this.inputTextViews;
        int [] outputViews=this.outputViews;

        switch(mode)
        {
            case P_MODE: inputScrollViews=this.inputScrollViewsP;
                inputTextViews=this.inputTextViewsP;
                outputViews=this.outputViewsP;
                break;

            case P_PS_MODE: inputScrollViews=this.inputScrollViewsPps;
                inputTextViews=this.inputTextViewsPps;
                outputViews=this.outputViewsPps;
                break;
        }

        // Selecting Tab
        Espresso.onView((ViewMatchers.withText(tabs[mode]))).
                perform(ViewActions.click());



        // Selecting whole Spinners Values

        for(int i=0;i<inputSpinnerValues.length;i++) {
            Espresso.onView((ViewMatchers.withId(inputScrollViews[i]))).
                    perform(scrollTo());
            Espresso.onView(ViewMatchers.withId(inputScrollViews[i])).
                    perform(ViewActions.click());
            Espresso.onData(is(instanceOf(String.class))).
                    atPosition(inputSpinnerValues[i]).
                    perform(ViewActions.click());
        }

        // Texting whole Text Values
        for(int i=0;i<inputValues.length;i++)
        {
            Espresso.onView((ViewMatchers.withId(inputTextViews[i]))).
                    perform(scrollTo());
            Espresso.onView(ViewMatchers.withId(inputTextViews[i])).
                    perform(ViewActions.typeText(inputValues[i]), closeSoftKeyboard());
        }

        // Performing calculation
        Espresso.onView(ViewMatchers.withId(R.id.submit)).
                    perform(ViewActions.click());

        // Checking Result
        for(int i=0;i<outputViews.length;i++) {
            BigDecimal expected=new BigDecimal(outputValues[i]);
            expected=expected.divide(BigDecimal.ONE,MathContext.DECIMAL128);
            BigDecimal result=new BigDecimal(getText(outputViews[i]));

            if(check(expected,result)) throw new AssertionError("Expected Value: "
            +expected.toString()+" instead output is "+result.toString());
        }

    }
        @Test
    public void levelUITest()
    {

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Low Chamber to flange
        4: from bottom tank to low chamber's flange
        5: SG for fill fluid
        6: SG liquid
        */
        String [] inputTextValues={ "0.0","324.0","8.0","0.0","0.0",
                "0.0","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Low Chamber to flange unit
        4: from bottom tank to low chamber's flange unit
        5: minimum pressure unit output
        6: maximum pressure unit output
        7: minimum height unit output
        8: maximum height unit output
        */


        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"0.0","323.028","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,DP_MODE);
    }

    @Test
    public void levelTwoLegsUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Low Chamber to flange
        4: from bottom tank to low chamber's flange
        5: SG for fill fluid
        6: SG liquid
        */
        String [] inputTextValues={ "0.0","114.0","6.0","114.0","120.0",
                "0.934","0.893"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Low Chamber to flange unit
        4: from bottom tank to low chamber's flange unit
        5: minimum pressure unit output
        6: maximum pressure unit output
        7: minimum height unit output
        8: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"-106.476","-4.674","6","120"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,DP_MODE);
    }

    @Test
    public void levelTwoLegsWithoutFillFluidUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Low Chamber to flange
        4: from bottom tank to low chamber's flange
        5: SG for fill fluid
        6: SG liquid
        */
        String [] inputTextValues={ "1.0","90","15.0","91.0","105.0",
                "0.0","0.618"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Low Chamber to flange unit
        4: from bottom tank to low chamber's flange unit
        5: minimum pressure unit output
        6: maximum pressure unit output
        7: minimum height unit output
        8: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"0.618","56.238","15","105"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,DP_MODE);
    }


    @Test
    public void levelOneLegUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: SG for fill fluid
        4: SG liquid
        */
        String [] inputTextValues={ "0.0","324.0","8.0","0.0","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: minimum pressure unit output
        4: maximum pressure unit output
        5: minimum height unit output
        6: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"0.0","323.028","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_MODE);
    }

    @Test
    public void levelOneLegFillFluidUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: SG for fill fluid
        4: SG liquid
        */
        String [] inputTextValues={ "1.0","324.0","8.0","0.934","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: minimum pressure unit output
        4: maximum pressure unit output
        5: minimum height unit output
        6: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"0.934","323.962","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_MODE);
    }

    @Test
    public void levelOneLegLiquidWithFlangeDistanceUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: SG for fill fluid
        4: SG liquid
        */
        String [] inputTextValues={ "1.0","324.0","8.0","0.0","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: minimum pressure unit output
        4: maximum pressure unit output
        5: minimum height unit output
        6: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"0.997","324.025","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_MODE);
    }


    @Test
    public void levelOneLegPsUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Pressure at tank
        4: SG for fill fluid
        5: SG liquid
        */
        String [] inputTextValues={ "0.0","324.0","8.0","2","0.0","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Pressure at tank
        4: minimum pressure unit output
        5: maximum pressure unit output
        6: minimum height unit output
        7: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"2.0","325.028","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_PS_MODE);
    }

    @Test
    public void levelOneLegFillFluidPsUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Pressure at tank
        4: SG for fill fluid
        5: SG liquid
        */
        String [] inputTextValues={ "1.0","324.0","8.0","2.0","0.934","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Pressure at tank
        4: minimum pressure unit output
        5: maximum pressure unit output
        6: minimum height unit output
        7: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"2.934","325.962","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_PS_MODE);
    }

    @Test
    public void levelOneLegLiquidWithFlangeDistancePsUI(){

        /* the order is given as follows
        0: HighChamber to flange
        1: high measure to high chamber's flange
        2: from bottom tank to high chamber's flange
        3: Pressure at tank
        4: SG for fill fluid
        5: SG liquid
        */
        String [] inputTextValues={ "1.0","324.0","8.0","2.0","0.0","0.997"};

        /* the order is given as follows
        0: HighChamber to flange unit
        1: high measure to high chamber's flange unit
        2: from bottom tank to high chamber's flange unit
        3: Pressure at tank
        4: minimum pressure unit output
        5: maximum pressure unit output
        6: minimum height unit output
        7: maximum height unit output
        */

        int [] inputSpinnerValues={SignedLength.INCHES,
                SignedLength.INCHES,
                SignedLength.INCHES,
                Pressure.INH2O,
                Pressure.INH2O,
                Pressure.INH2O,
                SignedLength.INCHES,
                SignedLength.INCHES};
        /* the order is given as follows:
        0: minimum pressure value output
        1: maximum pressure value output
        2: minimum height value output
        3: maximum height value output
         323.028*/
        String [] outputValues={"2.997","326.025","8","332"};

        calculation(inputTextValues, inputSpinnerValues,outputValues,P_PS_MODE);
    }

}

