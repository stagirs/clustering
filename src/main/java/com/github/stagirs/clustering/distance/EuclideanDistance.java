/*
 * Copyright 2016 Dmitriy Malakhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.stagirs.clustering.distance;

import gnu.trove.map.hash.TIntDoubleHashMap;
import org.apache.commons.math3.util.FastMath;

/**
 * Calculates the L<sub>2</sub> (Euclidean) distance between two points.
 * @author Dmitriy Malakhov
 */
public class EuclideanDistance implements DistanceMeasure {

    /** Serializable version identifier. */
    private static final long serialVersionUID = 1717556319784040040L;


    @Override
    public double compute(TIntDoubleHashMap a, TIntDoubleHashMap b) {
        double sum = 0;
        for (int index : a.keys()) {
            if(b.contains(index)){
                sum += FastMath.pow(a.get(index) - b.get(index), 2);
            }else{
                sum += FastMath.pow(a.get(index), 2);
            }
        }
        for (int index : b.keys()) {
            if(!a.contains(index)){
                sum += FastMath.pow(b.get(index), 2);
            }
        }
        return FastMath.sqrt(sum);
    }

}
