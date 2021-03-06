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
interface RIC_A {}
@nomodel
interface RIC_B {}
@nomodel
interface RIC_C {}

@nomodel
interface RIC_Top<out T> {
    shared formal T val;
    formal shared T get();
}
@nomodel
abstract class RIC_Middle() satisfies RIC_Top<RIC_A> {}
@nomodel
interface RIC_Left satisfies RIC_Top<RIC_B> {}
@nomodel
interface RIC_Right satisfies RIC_Top<RIC_C> {}
@nomodel
class RIC_Bottom_From_Class() extends RIC_Middle() satisfies RIC_Left & RIC_Right {
    shared actual Bottom val { return bottom; }
    shared actual RIC_A & RIC_B & RIC_C get() { return bottom; }
}
@nomodel
class RIC_Bottom_From_Interface() satisfies RIC_Left & RIC_Right {
    shared actual Bottom val { return bottom; }
    shared actual RIC_B & RIC_C get() { return bottom; }
}
