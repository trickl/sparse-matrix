/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trickl.matrix;

import com.trickl.matrix.CompressedSparseColumnAdapter;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import org.junit.Test;
import static org.junit.Assert.*;

public class CompressedSparseColumnAdapterTest {

   private static final double tolerance = 1e-15;

   public CompressedSparseColumnAdapterTest() {
   }

   @Test
   public void testEmpty() {
      SparseDoubleMatrix2D input = new SparseDoubleMatrix2D(0, 0);
      CompressedSparseColumnAdapter csr = new CompressedSparseColumnAdapter(input);

      assertArrayEquals(new double[] {}, csr.getData(), tolerance);
      assertArrayEquals(new int[] {}, csr.getRowIndices());
      assertArrayEquals(new int[] {0}, csr.getColumnPointers());
   }

   // See http://web.eecs.utk.edu/~dongarra/etemplates/node374.html
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
      CompressedSparseColumnAdapter csr = new CompressedSparseColumnAdapter(input);

      assertArrayEquals(new double[] {10, 3, 3, 9, 7, 8, 4, 8, 8, 7, 7, 9, -2, 5, 9, 2, 3, 13, -1}, csr.getData(), tolerance);
      assertArrayEquals(new int[] {0, 1, 3, 1, 2, 4, 5, 2, 3, 2, 3, 4, 0, 3, 4, 5, 1, 4, 5}, csr.getRowIndices());
      assertArrayEquals(new int[] {0, 3, 7, 9, 12, 16, 19}, csr.getColumnPointers());
   }
}
