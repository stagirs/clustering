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
 * Calculates the Canberra distance between two points.
 * @author Dmitriy Malakhov
 */
public class CanberraDistance implements DistanceMeasure {

    /** Serializable version identifier. */
    private static final long serialVersionUID = -6972277381587032228L;

    /** {@inheritDoc} */
    @Override
    public double compute(TIntDoubleHashMap a, TIntDoubleHashMap b) {
        double sum = 0;
        for (int index : a.keys()) {
            if(b.contains(index)){
                double ai = a.get(index);
                double bi = b.get(index);
                final double num = FastMath.abs(ai - bi);
                final double denom = FastMath.abs(ai) + FastMath.abs(bi);
                sum += num == 0.0 && denom == 0.0 ? 0.0 : num / denom;
            }else{
                sum += 1;
            }
        }
        for (int index : b.keys()) {
            if(!a.contains(index)){
                sum += 1;
            }
        }
        return sum;
    }
}
