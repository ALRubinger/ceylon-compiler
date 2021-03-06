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

@nomodel
interface Bug260_Interface {
    // we will narrow those to Nothing
    shared formal Integer? attr;
    shared formal Integer? attrGetter;
    shared formal Integer? m();
    // we will narrow those to Bottom
    shared formal Bug260_Interface2 attr2;
    shared formal Bug260_Interface2 m2();
    // we will narrow those to Bug260_Interface2&Bug260_Interface3
    shared formal Bug260_Interface2 attr3;
    shared formal Bug260_Interface2 m3();
}

@nomodel
interface Bug260_Interface2 {
}

@nomodel
interface Bug260_Interface3 {
}

@nomodel
class Bug260_Intersection() satisfies Bug260_Interface2 & Bug260_Interface3 {
}

@nomodel
class Bug260() satisfies Bug260_Interface & Bug260_Interface2 {
    // narrow to Nothing
    shared actual Nothing attr = null;
    shared actual Nothing attrGetter { return null; }
    
    shared actual Nothing m(){
        return null;
    }

    // narrow to Bottom
    shared actual Bottom attr2 = bottom;
    shared actual Bottom m2() {
        return bottom;
    }
    
    // narrow to Bug260_Interface2&Bug260_Interface3
    shared actual Bug260_Interface2&Bug260_Interface3 attr3 = Bug260_Intersection();
    shared actual Bug260_Interface2&Bug260_Interface3 m3() {
        return Bug260_Intersection();
    }
}
