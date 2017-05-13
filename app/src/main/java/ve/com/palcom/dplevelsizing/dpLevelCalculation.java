package ve.com.palcom.dplevelsizing;
import java.math.BigDecimal;


import ve.com.palcom.unitconverter.LengthException;
import ve.com.palcom.unitconverter.Pressure;
import ve.com.palcom.unitconverter.SignedLength;
/**
 * Created by joalbert on 4/14/17.
 */

public class dpLevelCalculation {

    private double SGFillFluidHighChamber=0;
    private double SGFillFluidLowChamber=0;
    private double SGLiquidTank=0;
    private Pressure staticPressureHighChamber;
    private Pressure staticPressureLowChamber;

    // Distance from Low Chamber to Flange
    private SignedLength dFlangesLowChamber;
    // Distance from most higher measure to flanges
    private SignedLength dHighlevelFlangesLowChamber;
    // Distance from bottom tank to flanges
    private SignedLength dBottomTankFlangesLowChamber;

    // Distance from Low Chamber to Flange
    private SignedLength dFlangesHighChamber;
    // Distance from most higher measure to flanges
    private SignedLength dHighlevelFlangesHighChamber;
    // Distance from bottom tank to flanges
    private SignedLength dBottomTankFlangesHighChamber;



    /** Constructor of dP calculation, the function ask for each value required to perform the
     * calculation
     * @param dFlangesLowChamber: weight of liquid due distance from flange to low chamber dP cell
     * @param dHighlevelFlangesLowChamber: weight of liquid due distance from flanges to maximum level to
     *                                   be measured (low chamber)
     * @param dBottomTankFlangesLowChamber: distance from bottom tank to flange where low chamber
     *                                    is connected
     *@param dFlangesHighChamber weight of liquid due distance from flange to high chamber dp cell
     *@param dHighlevelFlangesHighChamber weight of liquid due distance from flange to high chamber dP cell
     * @param dBottomTankFlangesHighChamber distance from bottom tank to flange where high chamber
     *                                    is connected
     * @param SGFillFluidHighChamber Specific gravity for fill fluid for High leg, if any
     * @param SGFillFluidLowChamber Specific gravity for fill fluid for low leg, if any
     * @param SGLiquidTank Specific gravity for liquid to be measured
     * @param staticPressureHighChamber pressure of vessel at high chamber
     * @param staticPressureLowChamber pressure at low chamber
     * *  */
    public dpLevelCalculation(SignedLength dFlangesLowChamber,
                              SignedLength dHighlevelFlangesLowChamber,
                              SignedLength dBottomTankFlangesLowChamber,
                              SignedLength dFlangesHighChamber,
                              SignedLength dHighlevelFlangesHighChamber,
                              SignedLength dBottomTankFlangesHighChamber,
                              Pressure staticPressureHighChamber,
                              Pressure staticPressureLowChamber,
                              double SGFillFluidHighChamber,
                              double SGFillFluidLowChamber,
                              double SGLiquidTank)
    {
        this.dFlangesLowChamber=dFlangesLowChamber;
        this.dHighlevelFlangesLowChamber=dHighlevelFlangesLowChamber;
        this.dBottomTankFlangesLowChamber=dBottomTankFlangesLowChamber;
        this.dFlangesHighChamber=dFlangesHighChamber;
        this.dHighlevelFlangesHighChamber=dHighlevelFlangesHighChamber;
        this.dBottomTankFlangesHighChamber=dBottomTankFlangesHighChamber;
        this.SGFillFluidHighChamber=SGFillFluidHighChamber;
        this.SGFillFluidLowChamber=SGFillFluidLowChamber;
        this.SGLiquidTank=SGLiquidTank;
        this.staticPressureHighChamber=staticPressureHighChamber;
        this.staticPressureLowChamber=staticPressureLowChamber;

    }
    /** Constructor of dP calculation, the function ask for each value required to perform the
     * calculation
     * @param builder: builder class which help to construct a calculation case
     */
    private dpLevelCalculation(Builder builder){
        this.dFlangesLowChamber=builder.FlangesLowChamber;
        this.dHighlevelFlangesLowChamber=builder.HighlevelFlangesLowChamber;
        this.dBottomTankFlangesLowChamber=builder.BottomTankFlangesLowChamber;
        this.dFlangesHighChamber=builder.FlangesHighChamber;
        this.dHighlevelFlangesHighChamber=builder.HighlevelFlangesHighChamber;
        this.dBottomTankFlangesHighChamber=builder.BottomTankFlangesHighChamber;
        this.SGFillFluidHighChamber=builder.SGFillFluidHighChamber;
        this.SGFillFluidLowChamber=builder.SGFillFluidLowChamber;
        this.SGLiquidTank=builder.SGLiquidTank;
        this.staticPressureHighChamber=builder.staticPressureHighChamber;
        this.staticPressureLowChamber=builder.staticPressureLowChamber;

    }


    /** Provide a calculation case null.
     *
     */
    public dpLevelCalculation() {
    }

        /** calculates pressure for leg in inches of water
         * @param FlangesToChamber  weight of liquid due distance from the chamber to the flanges (high)
         * @param SGfill Specific gravity for fill fluid
         * @param FlangesToHighMeasure  weight of liquid due distance from flanges to higher level to be measured
         * @param SGTank Specific gravity for liquid to be measured
         * @param staticPressure static pressure of tank
         * @return pressure in inches of water
         * */
    private static BigDecimal leg(SignedLength FlangesToChamber,SignedLength FlangesToHighMeasure,
                                double SGfill, double SGTank, Pressure staticPressure,
                                  SignedLength FlangesToBottomTank)
    {
        BigDecimal result;

        // Convert whole distance to inches
        FlangesToChamber=new SignedLength(SignedLength.signedConvertLength(
                FlangesToChamber.getValue(),FlangesToChamber.getUnit(), SignedLength.INCHES),
                SignedLength.INCHES);

        FlangesToHighMeasure=new SignedLength(SignedLength.signedConvertLength(
                FlangesToHighMeasure.getValue(),FlangesToHighMeasure.getUnit(),SignedLength.INCHES),
                SignedLength.INCHES);

        FlangesToBottomTank=new SignedLength(SignedLength.signedConvertLength(
                FlangesToBottomTank.getValue(),FlangesToBottomTank.getUnit(),SignedLength.INCHES),
                SignedLength.INCHES);

        // Validate values of level measure is higher than distance from the bottom tank
        if(FlangesToBottomTank.getValue().compareTo(
                FlangesToHighMeasure.addSignedLength(FlangesToBottomTank).getValue())>0)
            {
            FlangesToHighMeasure = new SignedLength(BigDecimal.ZERO, SignedLength.INCHES);
            }
            // Validate if instrument is above flanges and compesating into calculation
        if(FlangesToChamber.getValue().compareTo(BigDecimal.ZERO)<0)
            {
            FlangesToHighMeasure=FlangesToHighMeasure.addSignedLength(FlangesToChamber);
            FlangesToChamber = new SignedLength(BigDecimal.ZERO, SignedLength.INCHES);
            }

        // Calculate weigh of liquid for the leg
        result=FlangesToChamber.getValue().multiply(new BigDecimal(SGfill));

        result=result.add(
                FlangesToHighMeasure.getValue().multiply(new BigDecimal(SGTank))
        );

        result=result.add(
                Pressure.convertPressure(staticPressure.getValue(),staticPressure.getUnit(),
                        Pressure.INH2O)
        );
       return result;
    }

    /**
     * @return calculation of maximum differential pressure required in tank
     */

    public BigDecimal getMaxPres(){
        return getPresAnyLength(dHighlevelFlangesHighChamber,
                dHighlevelFlangesLowChamber);
    }

    /**
     * @return calculation of minimum differential pressure required in tank
     */

    public BigDecimal getMinPres(){
        return getPresAnyLength(new SignedLength(BigDecimal.ZERO,SignedLength.INCHES),
                new SignedLength(BigDecimal.ZERO,SignedLength.INCHES));
    }

    /**
     * @param higherChamberWeightLength weight of liquid from flange of high chamber
     * @param lowerChamberWeightLength weight of liquid from flange of low chamber
     * @return calculation of differential pressure at any length
     */

    public BigDecimal getPresAnyLength(SignedLength higherChamberWeightLength,
                                          SignedLength lowerChamberWeightLength){
        return leg(dFlangesHighChamber,higherChamberWeightLength,
                SGFillFluidHighChamber,SGLiquidTank,staticPressureHighChamber,
                dBottomTankFlangesHighChamber).subtract(
                leg(dFlangesLowChamber,lowerChamberWeightLength,
                        SGFillFluidLowChamber,SGLiquidTank,staticPressureLowChamber,
                        dBottomTankFlangesLowChamber));
    }

    /**@return Maximum level measurement taken from bottom of tank
     *
     *  */
    public BigDecimal getMaxLevel(){
        BigDecimal result,lowerDistance;
        result=SignedLength.signedConvertLength(dHighlevelFlangesHighChamber.getValue(),
                dHighlevelFlangesHighChamber.getUnit(),SignedLength.INCHES);

        result=result.add(
                SignedLength.signedConvertLength(dBottomTankFlangesHighChamber.getValue(),
                dBottomTankFlangesHighChamber.getUnit(),SignedLength.INCHES));

        lowerDistance=SignedLength.signedConvertLength(dHighlevelFlangesLowChamber.getValue(),
                dHighlevelFlangesLowChamber.getUnit(),SignedLength.INCHES);

        lowerDistance=lowerDistance.add(
                SignedLength.signedConvertLength(dBottomTankFlangesLowChamber.getValue(),
                        dBottomTankFlangesLowChamber.getUnit(),SignedLength.INCHES));
        if(lowerDistance.subtract(result).compareTo(BigDecimal.ZERO)<0)return result;
        else return lowerDistance;
    }
    /**@return minimum level measurement possible, where this one is measured from bottom tank
     */
    public BigDecimal getMinLevel(){
        BigDecimal result;
        result=SignedLength.signedConvertLength(dBottomTankFlangesHighChamber.getValue(),
                        dBottomTankFlangesHighChamber.getUnit(),SignedLength.INCHES);
        return result;
    }

    /***
     * @return range of measurement (distance)
     */
    public BigDecimal getLevelMeasurement(){
        return getMaxLevel().subtract(getMinLevel());
    }

    public BigDecimal getLowChamberMax(){
        return SignedLength.signedConvertLength(dBottomTankFlangesLowChamber.getValue(),
                dBottomTankFlangesLowChamber.getUnit(),SignedLength.INCHES);
    }

    public static class Builder{
        // Required parameter
        private double SGFillFluidHighChamber=0;
        private double SGFillFluidLowChamber=0;
        private double SGLiquidTank=0;
        private Pressure staticPressureHighChamber=new Pressure(BigDecimal.ZERO,Pressure.INH2O);
        private Pressure staticPressureLowChamber=new Pressure(BigDecimal.ZERO,Pressure.INH2O);

        // Distance from Low Chamber to Flange
        private SignedLength FlangesLowChamber=new SignedLength(BigDecimal.ZERO,
                SignedLength.INCHES);
        // Distance from most higher measure to flanges
        private SignedLength HighlevelFlangesLowChamber=new SignedLength(BigDecimal.ZERO,
                SignedLength.INCHES);
        // Distance from bottom tank to flanges
        private SignedLength BottomTankFlangesLowChamber=new SignedLength(BigDecimal.ZERO,
                SignedLength.INCHES);

        // Distance from High Chamber to Flange
        private SignedLength FlangesHighChamber;
        // Distance from most higher measure to flanges
        private SignedLength HighlevelFlangesHighChamber;
        // Distance from bottom tank to flanges
        private SignedLength BottomTankFlangesHighChamber;

        public Builder(SignedLength FlangesHighChamber,
                                         SignedLength HighlevelFlangesHighChamber,
                                         SignedLength BottomTankFlangesHighChamber){
            this.FlangesHighChamber=FlangesHighChamber;
            this.BottomTankFlangesHighChamber=BottomTankFlangesHighChamber;
            this.HighlevelFlangesHighChamber=HighlevelFlangesHighChamber;
        }

        public Builder setStaticPressureHighChamber(Pressure staticPressure){
            this.staticPressureHighChamber=staticPressure;
            return this;
        }

        public Builder setStaticPressureLowChamber(Pressure staticPressure){
            this.staticPressureLowChamber=staticPressure;
            return this;
        }


        public Builder setFlangesLowChamber(SignedLength FlangesLowChamber){
            this.FlangesLowChamber=FlangesLowChamber;
            return this;
        }

        public Builder setHighLevelLowChamber(
                SignedLength HighlevelFlangesLowChamber)
        {
            this.HighlevelFlangesLowChamber=HighlevelFlangesLowChamber;
            return this;
        }

        public Builder  setBottomTankFlangesLowChamber(
                SignedLength BottomTankFlangesLowChamber){
            this.BottomTankFlangesLowChamber=BottomTankFlangesLowChamber;
            return this;
        }


        public Builder setSgLiquid(double SGLiquidTank){
            this.SGLiquidTank=SGLiquidTank;
            return this;
        }

        public Builder setSgFillFluidHighChamber(double SGFillFluid){
            this.SGFillFluidHighChamber=SGFillFluid;
            return this;
        }

        public Builder setSgFillFluidLowChamber(double SGFillFluid){
            this.SGFillFluidLowChamber=SGFillFluid;
            return this;
        }


        public dpLevelCalculation build(){
            return new dpLevelCalculation(this);
        }
    }
}
