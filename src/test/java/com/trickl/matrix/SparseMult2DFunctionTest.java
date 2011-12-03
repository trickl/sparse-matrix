/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trickl.matrix;

import com.trickl.matrix.SparseMult2DFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import com.trickl.matrixunit.MatrixAssert;
import org.junit.Test;

/**
 *
 * @author tgee
 */
public class SparseMult2DFunctionTest {

   private static final double tolerance = 1e-15;

   public SparseMult2DFunctionTest() {
   }

   @Test
   public void testDenseEmpty() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(0, 0);
      DoubleMatrix2D rhs = new DenseDoubleMatrix2D(0, 0);
      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);
   }

   @Test
   public void testDenseMult() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new DenseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testDenseMultTranspose() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new DenseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0, -1},
         {3, 0, 0, 3, -4},
         {-2, 1, 0, 2, 7},
         {-0, 0, 0, 0, 0}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.rows());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, false, true));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, false, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testDenseTransposeMult() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D rhs = new DenseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0},
         {3, 0, 1, 3},
         {-2, 2, 0, 2},
         {1, 2, -7, 4},
         {-3, 2, 3, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.columns(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, true, false));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.columns());
      lhs.zMult(rhs, expectedResult, 1, 0, true, false);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testDenseTranposeMultTranspose() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new DenseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.columns(), rhs.rows());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, true, true));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, true, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseEmpty() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(0, 0);
      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(0, 0);
      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);
   }

   @Test
   public void testSparseMult() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseMultTranspose() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0, -1},
         {3, 0, 0, 3, -4},
         {-2, 1, 0, 2, 7},
         {-1, 0, 0, 0, 5}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.rows());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, false, true));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, false, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseTransposeMult() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0},
         {3, 0, 1, 3},
         {-2, 2, 0, 2},
         {1, 2, -7, 4},
         {-3, 2, 3, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.columns(), rhs.columns());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, true, false));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.columns());
      lhs.zMult(rhs, expectedResult, 1, 0, true, false);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseTranposeMultTranspose() {
      DoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 2, 0, 5},
         {1, 0, 2, 3, 0},
         {0, 1, 1, -2, 1},
         {0, 0, 8, 2, 1}
      });

      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(new double[][] {
         {1, 4, 0, 0},
         {3, 0, 0, 3},
         {-2, 1, 0, 2},
         {-0, 0, 0, 0},
         {-3, 2, 0, 1}
      });

      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.columns(), rhs.rows());
      lhs.forEachNonZero(new SparseMult2DFunction(rhs, result, true, true));

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, true, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }
}
