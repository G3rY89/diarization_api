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
 * Euclidean distance implementation
 * <p>
 * Threading : this class is thread safe
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Euclidean_distance">Euclidean distance</a>
 * @author Amaury Crickx
 */
public class EuclideanDistanceCalculator 
        extends DistanceCalculator {

    /**
     * Euclidean distance implementation
     * @param features1 first vector to compare
     * @param features2 second vector to compare
     * @return Euclidean distance between two feature vectors
     */
    public final double getDistance(double[] features1, double[] features2) {
        double distance = positiveInfinityIfEitherOrBothAreNull(features1, features2);
        if (distance < 0) {
            if(features1.length != features2.length) {
                throw new IllegalArgumentException("Both features should have the same length. Received lengths of [" +
                        + features1.length + "] and [" + features2.length + "]");
            }
            distance = 0.0;
            for (int i = 0; i < features1.length; i++) {
                double diff = features1[i] - features2[i];
                distance += diff * diff;
            }
        }
        return distance;
    }

}
