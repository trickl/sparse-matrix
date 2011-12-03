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
