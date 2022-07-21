/*
 *  Copyright (c) 2021, 2022, Oracle and/or its affiliates. All rights reserved.
 *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *  This code is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License version 2 only, as
 *  published by the Free Software Foundation.
 *
 *  This code is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 *  version 2 for more details (a copy is included in the LICENSE file that
 *  accompanied this code).
 *
 *  You should have received a copy of the GNU General Public License version
 *  2 along with this work; if not, write to the Free Software Foundation,
 *  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 *  or visit www.oracle.com if you need additional information or have any
 *  questions.
 */

/*
 * @test
 * @enablePreview
 * @requires ((os.arch == "amd64" | os.arch == "x86_64") & sun.arch.data.model == "64") | os.arch == "aarch64"
 * @run testng/othervm
 *     --enable-native-access=ALL-UNNAMED
 *     TestNULLAddress
 */

import org.testng.annotations.Test;

import java.lang.foreign.Addressable;
import java.lang.foreign.Linker;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemoryAddress;
import java.lang.invoke.MethodHandle;

public class TestNULLAddress {

    static final Linker LINKER = Linker.nativeLinker();

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLLinking() {
        LINKER.downcallHandle(
                MemoryAddress.NULL,
                FunctionDescriptor.ofVoid());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLVirtual() throws Throwable {
        MethodHandle mh = LINKER.downcallHandle(
                FunctionDescriptor.ofVoid());
        mh.invokeExact((Addressable)MemoryAddress.NULL);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLgetString() {
        MemoryAddress.NULL.getUtf8String(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNULLsetString() {
        MemoryAddress.NULL.setUtf8String(0, "hello");
    }
}
