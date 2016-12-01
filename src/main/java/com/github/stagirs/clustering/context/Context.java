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
package com.github.stagirs.clustering.context;


/**
 *
 * @author Dmitriy Malakhov
 */
public class Context implements Comparable<Context>{
    int id;
    int[] ids;
    int count = 1;
    int rankContext = 0;
    int rankFreq = 0;
    double rank = 0;

    public Context() {
    }
        
    
    public Context(int id, int[] ids) {
        this.id = id;
        this.ids = new int[ids.length];
        System.arraycopy(ids, 0, this.ids, 0, ids.length);
    }
    /**
     * 
     * @param ids должен быть отсортирован
     */
    void add(int[] ids){
        this.ids = or(this.ids, ids);
        count++;
    }
    
    static int[] or(int[] a, int[] b){
        int[] r = new int[a.length + b.length];
        int i = 0, j = 0, index = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                r[index++] = a[i++];
                continue;
            }
            if (a[i] > b[j]) {
                r[index++] = b[j++];
                continue;
            }
            r[index++] = a[i];
            i++;
            j++;
        }
        while (i < a.length) {
            r[index++] = a[i++];
        }

        while (j < b.length) {
            r[index++] = b[j++];
        }
        if(a.length == index){
            return a;
        }
        if(r.length == index){
            return r;
        }
        int[] nr = new int[index];
        System.arraycopy(r, 0, nr, 0, index);
        return nr;
    }

    @Override
    public int compareTo(Context o) {
        return (int) Math.ceil(rank - o.rank);
    }

    public int getId() {
        return id;
    }

    public int[] getIds() {
        return ids;
    }


    public void setIds(int[] ids) {
        this.ids = ids;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRankContext(int rankContext) {
        this.rankContext = rankContext;
        if(rankContext != 0){
            this.rank = ((double) rankFreq) / rankContext;
        }
    }

    public void setRankFreq(int rankFreq) {
        this.rankFreq = rankFreq;
        if(rankContext != 0){
            this.rank = ((double) rankFreq) / rankContext;
        }
    }

    
    
}
