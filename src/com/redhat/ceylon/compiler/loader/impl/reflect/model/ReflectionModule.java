/*
 * Copyright Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the authors tag. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU General Public License version 2.
 * 
 * This particular file is subject to the "Classpath" exception as provided in the 
 * LICENSE file that accompanied this code.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License,
 * along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.redhat.ceylon.compiler.loader.impl.reflect.model;

import java.net.URLClassLoader;

import com.redhat.ceylon.compiler.loader.AbstractModelLoader;
import com.redhat.ceylon.compiler.loader.model.LazyModule;

public class ReflectionModule extends LazyModule {

    private ReflectionModuleManager modelManager;
    private ClassLoader classLoader;

    public ReflectionModule(ReflectionModuleManager reflectionModuleManager) {
        this.modelManager = reflectionModuleManager;
    }

    @Override
    protected AbstractModelLoader getModelLoader() {
        return modelManager.getModelLoader();
    }

    public void setClassLoader(URLClassLoader cl) {
        if(this.classLoader != null){
            throw new RuntimeException("Module already has a classloader: "+getNameAsString());
        }
        this.classLoader = cl;
    }
    
    public ClassLoader getClassLoader(){
        return classLoader;
    }

}
