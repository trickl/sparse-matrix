/*
 * This file is part of the Trickl Open Source Libraries.
 *
 * Trickl Open Source Libraries - http://open.trickl.com/
 *
 * Copyright (C) 2011 Tim Gee.
 *
 * Trickl Open Source Libraries are free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Trickl Open Source Libraries are distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this project.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.trickl.matrix;

import com.trickl.matrix.CompressedSparseRowAdapter;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompressedSparseRowAdapterTest {

   private static final double tolerance = 1e-15;

   public CompressedSparseRowAdapterTest() {
   }

   @Test
   public void testEmpty() {
      SparseDoubleMatrix2D input = new SparseDoubleMatrix2D(0, 0);
      CompressedSparseRowAdapter csr = new CompressedSparseRowAdapter(input);

      assertArrayEquals(new double[] {}, csr.getData(), tolerance);
      assertArrayEquals(new int[] {}, csr.getColumnIndices());
      assertArrayEquals(new int[] {0}, csr.getRowPointers());
   }

   // See http://web.eecs.utk.edu/~dongarra/etemplates/node373.html
   // Note we use zero based indexes
   @Test
   public void testExample1() {
      SparseDoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {10, 0, 0, 0, -2, 0},
         {3,  9, 0, 0, 0,  3},
         {0,  7, 8, 7, 0,  0},
         {3,  0, 8, 7, 5,  0},
         {0,  8, 0, 9, 9, 13},
         {0,  4, 0, 0, 2, -1}
      });
      CompressedSparseRowAdapter csr = new CompressedSparseRowAdapter(input);

      assertArrayEquals(new double[] {10, -2, 3, 9, 3, 7, 8, 7, 3, 8, 7, 5, 8, 9, 9, 13, 4, 2, -1}, csr.getData(), tolerance);
      assertArrayEquals(new int[] {0, 4, 0, 1, 5, 1, 2, 3, 0, 2, 3, 4, 1, 3, 4, 5, 1, 4, 5}, csr.getColumnIndices());
      assertArrayEquals(new int[] {0, 2, 5, 8, 12, 16, 19}, csr.getRowPointers());  
   }
}
