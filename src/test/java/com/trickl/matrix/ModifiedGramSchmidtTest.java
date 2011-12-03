/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
