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

import com.trickl.matrix.ModifiedGramSchmidt;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import com.trickl.matrixunit.MatrixAssert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tgee
 */
public class ModifiedGramSchmidtTest {

   private static final double tolerance = 1e-14;

   public ModifiedGramSchmidtTest() {
   }

   @Test
   public void testEmpty() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(0, 0);
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input);

      assertEquals(0, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is empty
      assertEquals(0, S.rows());
      assertEquals(0, S.columns());
   }

   @Test
   public void testDenseFullRank3x3() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(new double[][] {
         {2, 2, 2},
         {1, 1, 2},
         {-2, 1, 1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);      
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testDenseMaxRank3x3() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(new double[][] {
         {2, 2, 2},
         {1, 1, 2},
         {-2, 1, 1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input, 2);

      assertEquals(2, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testDenseFullRank5x5() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(new double[][] {
         {2, 2, 2, 0,  0},
         {1, 0, 2, 0,  0},
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input);

      assertEquals(5, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testDenseMaxRank5x5() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(new double[][] {
         {2, 2, 2, 0,  0},
         {1, 0, 2, 0,  0},
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input, 3);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testSparseFullRank3x3() {
      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {2, 2, 2},
         {1, 1, 2},
         {-2, 1, 1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testSparseMaxRank3x3() {
      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {2, 2, 2},
         {1, 1, 2},
         {-2, 1, 1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input, 2);

      assertEquals(2, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testSparseFullRank5x5() {
      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {2, 2, 2, 0,  0},
         {1, 0, 2, 0,  0},
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input);

      assertEquals(5, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testSparseMaxRank5x5() {
      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {2, 2, 2, 0,  0},
         {1, 0, 2, 0,  0},
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      ModifiedGramSchmidt orthonormal = new ModifiedGramSchmidt(input, 3);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }
}
