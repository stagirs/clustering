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

import com.github.stagirs.clustering.Cluster;
import com.github.stagirs.common.Store;
import com.github.stagirs.clustering.Clusterable;
import com.github.stagirs.clustering.Clusterer;
import com.github.stagirs.common.StoreIterator;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.set.hash.TIntHashSet;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dmitriy Malakhov
 */
public class ContextClusterer<T extends Clusterable> extends Clusterer<T>  {
    

    public ContextClusterer() {
        //нет непосредственного сравнения векторов друг с другом
        super(null);
    }

    @Override
    public List<? extends Cluster<T>> cluster(Collection<T> points) {
        File dir = new File("ContextClusterer");
        dir.mkdirs();
        File sortContexts = new File(dir, "sortContexts");
        File commonContaxts = new File(dir, "commonContaxts");
        int count = 0;
        if(!sortContexts.exists()){
            count = saveSortContexts(points, new Store(sortContexts));
        }else{
            StoreIterator si = new StoreIterator(sortContexts, Context.class);
            try{
                while(si.hasNext()){
                    si.next();
                    count++;
                }
            }finally{
                si.close();
            }
        }
        StoreIterator si = new StoreIterator(sortContexts, Context.class);
        try{
            saveCommonContexts(si, count, new Store(commonContaxts));
        }finally{
            si.close();
        }
        si = new StoreIterator(commonContaxts, CommonContext.class);
        try{
            return getClusters(si, points);
        }finally{
            si.close();
        }
    }
    
    private List<Cluster> getClusters(StoreIterator<CommonContext> commonContexts, Collection<T> points){
        return null;
    }
    
    private void saveCommonContexts(StoreIterator<Context> sortContexts, int size, Store<Context> store){
        Context[] processed = new Context[size];
        int save = 0;
        for (int i = 0; i < size && sortContexts.hasNext(); i++) {
            Context context = sortContexts.next();
            if(i % 10000 == 0){
                System.out.println(i + " " + save);
            }
            if(context.ids.length == 0){
                continue;
            }
            Context cc = new Context(context.id, new int[]{context.id});
            processed[context.id] = cc;
            for(int id : context.ids){
                if(processed[id] == null || processed[id] == cc){
                    continue;
                }
                cc.add(processed[id].ids);
                if( i > size / 1.5){
                    save++;
                    store.save(processed[id]);
                }
                for (int mainId : processed[id].ids) {
                    processed[mainId] = cc;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if(processed[i] == null){
                continue;
            }
            store.save(processed[i]);
            for (int mainId : processed[i].ids) {
                processed[mainId] = null;
            }
        }
    }
    
    private int saveSortContexts(Collection<T> points, Store<Context> store){
        int contextSize = 0;
        for (T point : points) {
            for (int id : point.getPoint().keys()) {
                contextSize = Math.max(id, contextSize);
            }
        }
        contextSize++;
        Context[] index = new Context[contextSize];
        for (T point : points) {
            int[] ids = point.getPoint().keys();
            Arrays.sort(ids);
            for (int id : ids) {
                if(index[id] == null){
                    index[id] = new Context(id, ids);
                }else{
                    index[id].add(ids);
                }
            }
        }
        for (int i = 0; i < index.length; i++) {
            if(index[i] == null){
                index[i] = new Context(i, new int[0]);
            }
        }
        Context[] sortContexts = new Context[index.length];
        System.arraycopy(index, 0, sortContexts, 0, index.length);
        Arrays.sort(sortContexts);
        for (Context sortContext : sortContexts) {
            store.save(sortContext);
        }
        return sortContexts.length;
    }
}
