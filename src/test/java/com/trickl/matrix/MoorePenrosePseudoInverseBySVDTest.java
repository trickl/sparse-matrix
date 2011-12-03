/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trickl.matrix;

import com.trickl.matrix.MoorePenrosePseudoInverseBySVD;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tgee
 */
public class MoorePenrosePseudoInverseBySVDTest {

   public MoorePenrosePseudoInverseBySVDTest() {
   }

   @Test
   public void testInverse() {
      System.out.println("inverse");
      DoubleMatrix2D X = new DenseDoubleMatrix2D(new double[][]{
                 {1, 1, 0},
                 {0, 1, 1}
              });

      MoorePenrosePseudoInverseBySVD inverseAlgorithm = new MoorePenrosePseudoInverseBySVD();
      DoubleMatrix2D expResult = new DenseDoubleMatrix2D(new double[][]{
                 {2. / 3., -1. / 3.},
                 {1. / 3., 1. / 3.},
                 {-1. / 3., 2. / 3.}
              });

      DoubleMatrix2D result = inverseAlgorithm.inverse(X);
      for (int i = 0; i < expResult.rows(); ++i) {
         for (int j = 0; j < expResult.columns(); ++j) {
            assertEquals("Row " + i
                     + ", Column " + j
                     + ", Expected " + expResult.getQuick(i, j)
                     + ", Found " + result.getQuick(i, j),
                     expResult.getQuick(i, j),
                     result.getQuick(i, j), 1e-7);
         }
      }
   }
}
