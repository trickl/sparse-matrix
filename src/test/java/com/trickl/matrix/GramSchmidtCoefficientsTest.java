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

import com.trickl.matrix.GramSchmidtCoefficients;
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
public class GramSchmidtCoefficientsTest {

   private static final double tolerance = 1e-12;

   public GramSchmidtCoefficientsTest() {
   }

   @Test
   public void testEmpty() {
      DoubleMatrix2D input = new DenseDoubleMatrix2D(0, 0);
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input);

      assertEquals(0, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is empty
      assertEquals(0, S.rows());
      assertEquals(0, S.columns());
   }

   @Test
   public void testSparseFullRank1x3() {
      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {2, 2, 2}
      });
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input);

      assertEquals(1, orthonormal.getRank());
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
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input);

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
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, input.like(0, input.columns()), DoubleFactory2D.dense.identity(0), 2);

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
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input);

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
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, input.like(0, input.columns()), DoubleFactory2D.dense.identity(0), 3);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D S = orthonormal.getS();

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank()), orthogonalBasisCheck, tolerance);
   }
      
   @Test
   public void testSparseFullRankWithV3x3() {
      double root1_3 = Math.sqrt(1. / 3.);
      DoubleMatrix2D V = new SparseDoubleMatrix2D(new double[][] {
         {root1_3 , root1_3, root1_3},
      });

      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {1, 1, 2},
         {-2, 1, 1}
      });
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, V);

      assertEquals(2, orthonormal.getRank());
      DoubleMatrix2D Sout = orthonormal.getS();

      // Consolidate the output orthogonal basis with the input basis
      assertEquals(Sout.columns(), V.columns());
      DoubleMatrix2D S = Sout.like(Sout.rows() + V.rows(), Sout.columns());
      S.viewPart(0, 0, V.rows(), V.columns()).assign(V);
      S.viewPart(V.rows(), 0, Sout.rows(), V.columns()).assign(Sout);

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank() + V.rows()), orthogonalBasisCheck, tolerance);
   }

      @Test
   public void testSparseMaxRankWithV3x3() {
      double root1_3 = Math.sqrt(1. / 3.);
      DoubleMatrix2D V = new SparseDoubleMatrix2D(new double[][] {
         {root1_3 , root1_3, root1_3},
      });

      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {1, 1, 2},
         {-2, 1, 1}
      });
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, V, DoubleFactory2D.dense.identity(V.rows()), 1);

      assertEquals(1, orthonormal.getRank());
      DoubleMatrix2D Sout = orthonormal.getS();

      // Consolidate the output orthogonal basis with the input basis
      assertEquals(Sout.columns(), V.columns());
      DoubleMatrix2D S = Sout.like(Sout.rows() + V.rows(), Sout.columns());
      S.viewPart(0, 0, V.rows(), V.columns()).assign(V);
      S.viewPart(V.rows(), 0, Sout.rows(), V.columns()).assign(Sout);

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank() + V.rows()), orthogonalBasisCheck, tolerance);
   }


   @Test
   public void testSparseFullRankWithV5x5() {
      double root1_3 = Math.sqrt(1. / 3.);
      double root1_2 = Math.sqrt(1. / 2.);
      DoubleMatrix2D V = new SparseDoubleMatrix2D(new double[][] {
         {root1_3 , root1_3, root1_3, 0, 0},
         {0,       -root1_2, root1_2, 0, 0},
      });

      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, V);

      assertEquals(3, orthonormal.getRank());
      DoubleMatrix2D Sout = orthonormal.getS();

      // Consolidate the output orthogonal basis with the input basis
      assertEquals(Sout.columns(), V.columns());
      DoubleMatrix2D S = Sout.like(Sout.rows() + V.rows(), Sout.columns());
      S.viewPart(0, 0, V.rows(), V.columns()).assign(V);
      S.viewPart(V.rows(), 0, Sout.rows(), V.columns()).assign(Sout);

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank() + V.rows()), orthogonalBasisCheck, tolerance);
   }

   @Test
   public void testSparseMaxRankWithV5x5() {
      double root1_3 = Math.sqrt(1. / 3.);
      double root1_2 = Math.sqrt(1. / 2.);
      DoubleMatrix2D V = new SparseDoubleMatrix2D(new double[][] {
         {root1_3 , root1_3, root1_3, 0, 0},
         {0,       -root1_2, root1_2, 0, 0},
      });

      DoubleMatrix2D input = new SparseDoubleMatrix2D(new double[][] {
         {0, 0, 1, 1, -2},
         {1, 1, 0, 0,  0},
         {1, 1, 0, 0, -1}
      });
      GramSchmidtCoefficients orthonormal = new GramSchmidtCoefficients(input, V, DoubleFactory2D.dense.identity(V.rows()), 2);

      assertEquals(2, orthonormal.getRank());
      DoubleMatrix2D Sout = orthonormal.getS();

      // Consolidate the output orthogonal basis with the input basis
      assertEquals(Sout.columns(), V.columns());
      DoubleMatrix2D S = Sout.like(Sout.rows() + V.rows(), Sout.columns());
      S.viewPart(0, 0, V.rows(), V.columns()).assign(V);
      S.viewPart(V.rows(), 0, Sout.rows(), V.columns()).assign(Sout);

      // Check S is orthonormal
      DoubleMatrix2D orthogonalBasisCheck = S.like(S.rows(), S.rows());
      S.zMult(S, orthogonalBasisCheck, 1, 0, false, true);
      MatrixAssert.assertEquals(DoubleFactory2D.sparse.identity(orthonormal.getRank() + V.rows()), orthogonalBasisCheck, tolerance);
   }
}
