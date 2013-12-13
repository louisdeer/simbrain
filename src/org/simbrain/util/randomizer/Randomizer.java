/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.util.randomizer;

import java.util.Random;

/**
 * <b>Randomizer</b> produces numbers drawn from a probability distribution
 * according to a set of user-specified parameters.
 */
public class Randomizer {

    /** Labels used by combo boxes. */
    private static String[] functionList = { "Uniform", "Gaussian" };

    /** A uniform distribution. */
    public static final int UNIFORM = 0;

    /** A Gaussian distribution. */
    public static final int GAUSSIAN = 1;

    /** Which distribution is currently chosen. */
    private int distributionIndex = 0;

    /** Upper bound of the random distribution. */
    private double upperBound = 1;

    /** Lower bound of the random distribution. */
    private double lowerBound = -1;

    /** Mean value for Gaussian distribution. */
    private double mean = .5;

    /** Standard deviation for Gaussian distribution. */
    private double standardDeviation = .5;

    /**
     * Whether the Gaussian distribution should be clipped at upper / lower
     * bound values.
     */
    private boolean clipping = false;

    /** Object which produces random values. */
    private Random randomGenerator = new Random();

    /**
     * Default constructor.
     */
    public Randomizer() {
    }

    /**
     * Copy constructor.
     *
     * @param dup the <code>RandomSource</code> to duplicate.
     * @return the duplicated <code>RandomSource</code> object.
     */
    public Randomizer(final Randomizer dup) {
        setLowerBound(getLowerBound());
        setUpperBound(getUpperBound());
        setMean(getMean());
        setStandardDeviation(getStandardDeviation());
        setClipping(getClipping());
    }

    /**
     * Creates a "mirrored" copy of this randomizer, wherein the mirror's lower
     * bound is set to the additive inverse of the original's upper bound and
     * likewise the mirror's upper bound is set to the additive inverse of the
     * original's lower bound. The mean of the mirror is the additive inverse of
     * the mean of the original. The standard deviation and clipping are
     * directly copied.
     *
     * @return a mirrored copy of this randomizer
     */
    public Randomizer mirrorCopy() {
        Randomizer mirror = new Randomizer();
        mirror.setDistributionIndex(this.getDistributionIndex());
        mirror.setLowerBound(-this.getUpperBound());
        mirror.setUpperBound(-this.getLowerBound());
        mirror.setMean(-this.getMean());
        mirror.setStandardDeviation(this.getStandardDeviation());
        mirror.setClipping(this.getClipping());
        return mirror;
    }

    /**
     * Returns a random number.
     *
     * @return the next random number
     */
    public double getRandom() {
        if (getDistributionIndex() == UNIFORM) {
            return ((upperBound - lowerBound) * Math.random()) + lowerBound;
        } else {
            double val = randomGenerator.nextGaussian();
            val = (val * standardDeviation) + mean;

            if (clipping) {
                val = clip(val);
            }

            return val;
        }
    }

    /**
     * Clip <code>val</code> to upper and lower bounds.
     *
     * @param val the value to clip
     * @return the clipped value
     */
    private double clip(final double val) {
        double ret = val;

        if (ret > upperBound) {
            ret = upperBound;
        } else if (ret < lowerBound) {
            ret = lowerBound;
        }

        return ret;
    }

    /**
     * @return Returns the mean.
     */
    public double getMean() {
        return mean;
    }

    /**
     * @param mean The mean to set.
     */
    public void setMean(final double mean) {
        this.mean = mean;
    }

    /**
     * @return Returns the standardDeviation.
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }

    /**
     * @param standardDeviation The standardDeviation to set.
     */
    public void setStandardDeviation(final double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    /**
     * @return Returns the clipping.
     */
    public boolean getClipping() {
        return clipping;
    }

    /**
     * @param clipping The useBounds to set.
     */
    public void setClipping(final boolean clipping) {
        this.clipping = clipping;
    }

    /**
     * @return Returns the functionList.
     */
    public static String[] getFunctionList() {
        return functionList;
    }

    /**
     * @return Returns the distributionIndex.
     */
    public int getDistributionIndex() {
        return distributionIndex;
    }

    /**
     * Returns the string name of the distribution.
     *
     * @return the distribution name.
     */
    public String getDistributionName() {
        if (distributionIndex == 1) {
            return "Gaussian";
        } else {
            return "Uniform";
        }
    }

    /**
     * @param distributionIndex The distributionIndex to set.
     */
    public void setDistributionIndex(final int distributionIndex) {
        this.distributionIndex = distributionIndex;
    }

    /**
     * @return Returns the lowerBound.
     */
    public double getLowerBound() {
        return lowerBound;
    }

    /**
     * @param lowerBound The lowerBound to set.
     */
    public void setLowerBound(final double lowerBound) {
        this.lowerBound = lowerBound;
    }

    /**
     * @return Returns the upperBound.
     */
    public double getUpperBound() {
        return upperBound;
    }

    /**
     * @param upperBound The upperBound to set.
     */
    public void setUpperBound(final double upperBound) {
        this.upperBound = upperBound;
    }
}
