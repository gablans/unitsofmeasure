/**
 *  Unit-API - Units of Measurement API for Java
 *  Copyright 2013-2014, Jean-Marie Dautelle, Werner Keil, V2COM and individual
 *  contributors by the @author tag.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.unitsofmeasurement.impl.enums.unit;

import static org.unitsofmeasurement.impl.enums.unit.Constants.DEG;

import org.unitsofmeasurement.impl.enums.quantity.QuantityFactory;
import org.unitsofmeasurement.impl.function.DescriptionSupplier;
import org.unitsofmeasurement.impl.function.DoubleFactorSupplier;

import java.util.HashMap;
import java.util.Map;

import javax.measure.Dimension;
import javax.measure.IncommensurableException;
import javax.measure.Quantity;
import javax.measure.UnconvertibleException;
import javax.measure.Unit;
import javax.measure.function.UnitConverter;
import javax.measure.quantity.Temperature;

/**
 * @author Werner Keil
 * @version 1.4 ($Revision$), $Date$
 */
public enum TemperatureUnit implements Unit<Temperature>, DoubleFactorSupplier,
	DescriptionSupplier {

	/** Kelvin, commonly used in scientific endeavors. */
	KELVIN(1d, 0d, null, 273.15d, 373.15d, "K", 
			"William Thomson, 1st Baron Kelvin"),

	/** Rankine, used in scientific endeavors. */
	RANKINE(5/9, 0d, KELVIN, 491.67d, 671.641d, DEG + "R", 
			"William John Macquorn Rankine"),
			
	/** Celsius, used by most of the world's population. */
	CELSIUS(0d, 273.15d, KELVIN, 0d, 100d, DEG + "C", "Anders Celsius"),

	/** Fahrenheit, commonly used in the United States. */
	FAHRENHEIT(0d, 459.67d, RANKINE, 32d, 212d, DEG + "F", 
				"Daniel Gabriel Fahrenheit");
   
    /** Units by which this temperature scale is expressed. */
    private final String description;
    
    private final double multFactor;
    
    /** Freezing point of water for each temperature scale. */
    private final double freezingPoint;

    /** Boiling point of water for each temperature scale. */
    private final double boilingPoint;

    /** Name of person that this temperature scale is named for. */
    private final String namedFor;
    
    private final TemperatureUnit relativeTo;

//	private static final Double FIVE = new Double("5");
//    private static final Double NINE = new Double("9");
//    private static final Double THIRTY_TWO = new Double("32");
//    private static final Double KELVIN_CELSIUS_DELTA = new Double("273");
    private static final double RANKINE_FAHRENHEIT_DELTA = 459.67d;
    
    /**
     * Constructor for TemperatureUnit that accepts key characteristics of each
     * temperature scale.
     *
     * @param newFreezingPoint Freezing point for this temperature scale.
     * @param newBoilingPoint Boiling point for this temperature scale.
     * @param newUnits Unit symbol for this temperature scale.
     * @param newNamedFor Name of person after which temperature scale was named.
     */
    private TemperatureUnit(
       double newMult,
       double shift,
       final TemperatureUnit rel,
       double newFreezingPoint,
       double newBoilingPoint,
       final String newSymbol,
       final String newNamedFor)
    {
       this.multFactor = newMult;
       this.relativeTo = rel;
       this.freezingPoint = newFreezingPoint;
       this.boilingPoint = newBoilingPoint;
       this.description = newSymbol;
       this.namedFor = newNamedFor;
    }

    public String getSymbol() {
        return description;
    }

    public double getFactor() {
        return multFactor;
    }
    
	@Override
	public String getName() {
		return namedFor;
	}
    
    @Override
	public Unit<Temperature> getSystemUnit() {
		return KELVIN;
    }

    @Override
    public Map<? extends Unit<Temperature>, Integer> getProductUnits() {
        Map<Unit<Temperature>, Integer> prodUnits = new HashMap<Unit<Temperature>, Integer>();
        prodUnits.put(KELVIN, Integer.valueOf(2));
        return prodUnits;
    }

    public static TemperatureUnit getBySymbol(String symbol) {
        if (CELSIUS.name().equals(symbol)) {
            return CELSIUS;
        }
        if (FAHRENHEIT.name().equals(symbol)) {
            return FAHRENHEIT;
        }
        return KELVIN;
    }

    public UnitConverter getConverterTo(Unit<Temperature> that)
            throws UnconvertibleException {
    	if ((this == that) || this.equals(that)) return AbstractConverter.IDENTITY; // Shortcut.
    	Unit<Temperature> thisSystemUnit = this.getSystemUnit();
        Unit<Temperature> thatSystemUnit = that.getSystemUnit();
        if (!thisSystemUnit.equals(thatSystemUnit))
			try {
				return getConverterToAny(that);
			} catch (IncommensurableException e) {
				throw new UnconvertibleException(e);
			}
        return that.getConverterTo(thatSystemUnit);
    }

    public UnitConverter getConverterToAny(Unit<?> that)
            throws IncommensurableException, UnconvertibleException {
        if (!isCompatible(that))
            throw new IncommensurableException(this + " is not compatible with " + that);
        DimensionalModel model = DimensionalModel.getCurrent();
        final Unit thisSystemUnit = this.getSystemUnit();
        return model.getDimensionalTransform(thisSystemUnit.getDimension()); //.concatenate(this.getConverterToSI());
    }

    @Override
    public Unit<Temperature> alternate(String s) {
        return this;
    }

    public Dimension getDimension() {
        return SimpleDimension.INSTANCE;
    }

     public Unit<?> inverse() {
        return this;
    }

    @Override
    public Unit<Temperature> divide(double v) {
        return null;  //To change body of implemented methods use File | Settings | File TemplateBuilder.
    }

    @Override
    public Unit<?> divide(Unit<?> unit) {
        return null;  //To change body of implemented methods use File | Settings | File TemplateBuilder.
    }

    public boolean isCompatible(Unit<?> that) {
        if (that instanceof TemperatureUnit) return true;
        return false;
    }

    @SuppressWarnings({ "unchecked" })
	@Override
    public <T extends Quantity<T>> Unit<T> asType(Class<T> tClass) {
        Unit<T> metricUnit = QuantityFactory.getInstance(tClass).getMetricUnit();
         if ((metricUnit == null) || metricUnit.isCompatible(this))
          return (Unit<T>) this;
           throw new ClassCastException("The unit: " + this //$NON-NLS-1$
               + " is not of parameterized type " + tClass); //$NON-NLS-1$
    }

    public Unit<Temperature> multiply(double factor) {
        return this;
    }

    public Unit<?> multiply(Unit<?> that) {
        return this;
    }

    public Unit<?> pow(int n) {
        return this;
    }

    public Unit<?> root(int n) {
        return this;
    }

    public Unit<Temperature> transform(UnitConverter operation) {
        return this;
    }

    @Override
    public Unit<Temperature> shift(double v) {
        return this;
    }

	public String getDescription() {
		return description;
	}
}