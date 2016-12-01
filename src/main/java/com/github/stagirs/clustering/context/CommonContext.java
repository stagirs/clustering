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
public class CommonContext extends Context{
    int[] mainIds;

    public CommonContext() {
    }
    

    public CommonContext(int id, int[] ids) {
        super(id, ids);
        mainIds = new int[1];
        mainIds[0] = id;
    }

    void addMain(int[] ids){
        this.mainIds = or(this.mainIds, ids);
    }
    
    public int[] getMainIds() {
        return mainIds;
    }

    public void setMainIds(int[] mainIds) {
        this.mainIds = mainIds;
    }
    
    
}
