/**
 * Unit-API - Units of Measurement API for Java (http://unitsofmeasurement.org)
 * Copyright (c) 2005-2010, Unit-API contributors, JScience and others
 * All rights reserved.
 *
 * See LICENSE.txt for details.
 */
package org.unitsofmeasurement.test.quantity;

import org.unitsofmeasurement.Measurement;
import org.unitsofmeasurement.quantity.Area;
import org.unitsofmeasurement.test.unit.AreaUnit;
import org.unitsofmeasurement.test.unit.DistanceUnit;
import org.unitsofmeasurement.test.unit.VolumeUnit;
import org.unitsofmeasurement.unit.Unit;


public class AreaQuantity extends TestQuantity<Area> {
    public AreaQuantity() {
    }

    public AreaQuantity(double val, AreaUnit un) {
        units = val;
        unit = un;
        scalar = val * unit.getMultFactor();
    }

    public AreaQuantity add(AreaQuantity d1) {
        AreaQuantity dn = new AreaQuantity();
        Object o = super.add(dn, this, d1, AreaUnit.REF_UNIT);
        return (AreaQuantity) o;
    }

    public AreaQuantity subtract(AreaQuantity d1) {
        AreaQuantity dn = new AreaQuantity();
        Object o = super.subtract(dn, this, d1, AreaUnit.REF_UNIT);
        return (AreaQuantity) o;
    }

    public boolean eq(AreaQuantity d1) {
        return super.eq(d1);
    }

    public boolean ne(AreaQuantity d1) {
        return super.ne(d1);
    }

    public boolean gt(AreaQuantity d1) {
        return super.gt(d1);
    }

    public boolean lt(AreaQuantity d1) {
        return super.lt(d1);
    }

    public boolean ge(AreaQuantity d1) {
        return super.ge(d1);
    }

    public boolean le(AreaQuantity d1) {
        return super.le(d1);
    }

    public AreaQuantity multiply(double v) {
        return new AreaQuantity(units * v, (AreaUnit) unit);
    }

    public AreaQuantity divide(double v) {
        return new AreaQuantity(units / v, (AreaUnit) unit);
    }

    // mixed type operations

    public DistanceQuantity divide(DistanceQuantity d1) {
        AreaQuantity dq0 = convert(AreaUnit.sqmetre);
        DistanceQuantity dq1 = d1.convert(DistanceUnit.m);
        return new DistanceQuantity(dq0.units / dq1.units, DistanceUnit.m);
    }

    public VolumeQuantity multiply(DistanceQuantity d1) {
        AreaQuantity dq0 = convert(AreaUnit.sqmetre);
        DistanceQuantity dq1 = d1.convert(DistanceUnit.m);
        return new VolumeQuantity(dq0.units * dq1.units, VolumeUnit.cumetre);
    }

    public AreaQuantity convert(AreaUnit newUnit) {
        return new AreaQuantity(scalar / newUnit.getMultFactor(), newUnit);
    }

    public String showInUnits(AreaUnit u, int precision) {
        return super.showInUnits(u, precision);
    }

	@Override
	public Measurement<Area, Number> add(Measurement<Area, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<Area, Number> substract(Measurement<Area, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<?, Number> multiply(Measurement<?, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<?, Number> multiply(Number that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<?, Number> divide(Measurement<?, Number> that) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<? extends Measurement<Area, Number>, Number> inverse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Measurement<Area, Number> to(Unit<Area> unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double doubleValue(Unit<Area> unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long longValue(Unit<Area> unit) {
		// TODO Auto-generated method stub
		return 0;
	}

}