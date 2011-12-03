package com.trickl.matrix;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;

public class MoorePenrosePseudoInverseBySVD implements MoorePenrosePseudoInverseAlgorithm {

   /**
   * The difference between 1 and the smallest exactly representable number
   * greater than one. Gives an upper bound on the relative error due to
   * rounding of floating point numbers.
   */
   public static double EPSILON = 1e-12;

   private SingularValueDecompositionAlgorithm svdAlgorithm = new ColtSvdAlgorithm();

   /*
    * Modified version of the original implementation by Kim van der Linde.
    * http://cio.nist.gov/esd/emaildir/lists/jama/msg01302.html
    */
   @Override
   public DoubleMatrix2D inverse(DoubleMatrix2D X) {
      svdAlgorithm.calculate(X);
      double[] singularValues = svdAlgorithm.getSingularValues();
      double tol = Math.max(X.columns(), X.rows()) * singularValues[0] * EPSILON;
      double[] singularValueReciprocals = new double[singularValues.length];
      for (int i = 0; i < singularValues.length; i++) {
         singularValueReciprocals[i] = Math.abs(singularValues[i]) < tol ? 0 : (1.0 / singularValues[i]);
      }

      DoubleMatrix2D U = svdAlgorithm.getU();
      DoubleMatrix2D V = svdAlgorithm.getV();
      int min = Math.min(X.columns(), U.columns());
      double[][] inverse = new double[X.columns()][X.rows()];

      for (int i = 0; i < X.columns(); i++) {
         for (int j = 0; j < X.rows(); j++) {
            for (int k = 0; k < min; k++) {
               inverse[i][j] += V.getQuick(i, k) * singularValueReciprocals[k] * U.getQuick(j, k);
            }
         }
      }
      
      return new DenseDoubleMatrix2D(inverse);
   }

   public SingularValueDecompositionAlgorithm getSvdAlgorithm() {
      return svdAlgorithm;
   }

   public void setSvdAlgorithm(SingularValueDecompositionAlgorithm svdAlgorithm) {
      this.svdAlgorithm = svdAlgorithm;
   }
}
