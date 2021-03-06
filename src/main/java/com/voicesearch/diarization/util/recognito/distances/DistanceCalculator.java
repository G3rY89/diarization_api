/*
 * (C) Copyright 2014 Amaury Crickx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.voicesearch.diarization.util.recognito.distances;

/**
 * Abstract base class for distance calculators
 * 
 * @author Amaury Crickx
 */
public abstract class DistanceCalculator {

    public abstract double getDistance(double[] features1, double[] features2);
    
    /**
     * Nullity check of parameters
     * @param features1 the first features vector
     * @param features2 the secund features vector
     * @return Double.POSITIVE_INFINITY in case either or both of the vectors are null, a negative number otherwise
     */
    protected double positiveInfinityIfEitherOrBothAreNull(double[] features1, double[] features2) {
        if(features1 == null || features2 == null) {
            return Double.POSITIVE_INFINITY;
        } else {
            return -1.0d;
        }
    }
}
