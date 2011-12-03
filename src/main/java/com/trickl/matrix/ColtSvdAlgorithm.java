package com.trickl.matrix;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class ColtSvdAlgorithm implements SingularValueDecompositionAlgorithm {

   private SingularValueDecomposition svd;
   private boolean transpose = false;

   @Override
   public void calculate(DoubleMatrix2D arg) {
      if (arg.rows() < arg.columns()) {
         // The Colt SVD implementation assumes #rows >= #columns
         transpose = true;
         arg = arg.viewDice();
      }
      svd = new SingularValueDecomposition(arg);
   }

   @Override
   public double[] getSingularValues() {
      if (svd == null) return new double[0];
      return svd.getSingularValues();
   }

   @Override
   public DoubleMatrix2D getU() {
      if (svd == null) return new DenseDoubleMatrix2D(0, 0);
      return transpose ? svd.getV() : svd.getU();
   }

   @Override
   public DoubleMatrix2D getV() {
      if (svd == null) return new DenseDoubleMatrix2D(0, 0);
      return transpose ? svd.getU() : svd.getV();
   }

}
