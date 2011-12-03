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

import com.trickl.matrix.SparseUtils;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import com.trickl.matrixunit.MatrixAssert;
import org.junit.Test;

/**
 *
 * @author tgee
 */
public class SparseUtilsTest {

   private static final double tolerance = 1e-15;

   public SparseUtilsTest() {
   }
   
   @Test
   public void testSparseEmpty() {
      SparseDoubleMatrix2D lhs = new SparseDoubleMatrix2D(0, 0);
      DoubleMatrix2D rhs = new SparseDoubleMatrix2D(0, 0);
      DoubleMatrix2D result = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      SparseUtils.zMult(lhs, rhs, result);

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);
   }

   @Test
   public void testSparseMult() {
      SparseDoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
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
      SparseUtils.zMult(lhs, rhs, result);

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.columns());
      lhs.zMult(rhs, expectedResult);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseMultTranspose() {
      SparseDoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
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
      SparseUtils.zMult(lhs, rhs, result, false, true);

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.rows(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, false, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseTransposeMult() {
      SparseDoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
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
      SparseUtils.zMult(lhs, rhs, result, true, false);

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.columns());
      lhs.zMult(rhs, expectedResult, 1, 0, true, false);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }

   @Test
   public void testSparseTranposeMultTranspose() {
      SparseDoubleMatrix2D lhs = new SparseDoubleMatrix2D(new double[][] {
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
      SparseUtils.zMult(lhs, rhs, result, true, true);

      DoubleMatrix2D expectedResult = new SparseDoubleMatrix2D(lhs.columns(), rhs.rows());
      lhs.zMult(rhs, expectedResult, 1, 0, true, true);

      MatrixAssert.assertEquals(expectedResult, result, tolerance);
   }
}
