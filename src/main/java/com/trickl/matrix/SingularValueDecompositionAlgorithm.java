package com.trickl.matrix;

import cern.colt.matrix.DoubleMatrix2D;

public interface SingularValueDecompositionAlgorithm {
   public void calculate(DoubleMatrix2D arg);
   double[] getSingularValues();
   DoubleMatrix2D getU();
   DoubleMatrix2D getV();
}
