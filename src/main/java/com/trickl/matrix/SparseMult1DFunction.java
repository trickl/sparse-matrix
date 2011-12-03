package com.trickl.matrix;

import cern.colt.function.IntIntDoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;

public class SparseMult1DFunction implements IntIntDoubleFunction {

   DoubleMatrix1D B;
   boolean transposeA = false;

   private DoubleMatrix1D C;

   public SparseMult1DFunction(DoubleMatrix1D B, DoubleMatrix1D C) {
      this(B, C, false);
   }

   public SparseMult1DFunction(DoubleMatrix1D B, DoubleMatrix1D C, boolean transposeA) {
      this.B = B;
      this.transposeA = transposeA;
      this.C = C;
   }   

   @Override
   public double apply(int first, int second, double third) {
      if (transposeA) {
         C.setQuick(second, C.getQuick(second) + third * B.getQuick(first));
      }
      else {
         C.setQuick(first, C.getQuick(first) + third * B.getQuick(second));
      }
      return third;
   }
}
