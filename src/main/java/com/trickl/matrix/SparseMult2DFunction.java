package com.trickl.matrix;

import cern.colt.function.IntIntDoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import cern.jet.math.Functions;

/**
 * Provides efficient multiplication for general sparse matrices
 */
public class SparseMult2DFunction implements IntIntDoubleFunction {

   DoubleMatrix2D B;
   CompressedSparseRow Bcsr;
   CompressedSparseColumn Bcsc;   
   boolean transposeA = false;
   boolean transposeB = false;
   private DoubleMatrix2D C;

   public SparseMult2DFunction(DoubleMatrix2D B, DoubleMatrix2D C) {
      this(B, C, false, false);
   }

   public SparseMult2DFunction(final DoubleMatrix2D B, DoubleMatrix2D C, boolean transposeA, boolean transposeB) {
      this.B = B;
      this.transposeA = transposeA;
      this.transposeB = transposeB;
      this.C = C;

      if (B instanceof SparseDoubleMatrix2D || C instanceof SparseDoubleMatrix2D) {
         if (transposeB) {
            Bcsc = new CompressedSparseColumnAdapter(B);
         }
         else {
            Bcsr = new CompressedSparseRowAdapter(B);
         }
      }
   }

   @Override
   public double apply(int first, int second, double third) {

      if (transposeA) {
         if (transposeB) {
            if (Bcsc != null) {
               int[] columnPointers = Bcsc.getColumnPointers();
               int[] rowIndices  = Bcsc.getRowIndices();
               double[] data     = Bcsc.getData();
               int column = first;
               for (int k = columnPointers[column]; k < columnPointers[column + 1]; ++k) {
                  int row = rowIndices[k];
                  C.setQuick(second, row, C.getQuick(second, row) + data[k] * third);
               }
            }
            else {
               C.viewRow(second).assign(B.viewColumn(first), Functions.plusMult(third));
            }
         } else {
            if (Bcsr != null) {
               int[] rowPointers = Bcsr.getRowPointers();
               int[] colIndices  = Bcsr.getColumnIndices();
               double[] data     = Bcsr.getData();
               int row = first;
               for (int k = rowPointers[row]; k < rowPointers[row + 1]; ++k) {
                  int column = colIndices[k];
                  C.setQuick(second, column, C.getQuick(second, column) + data[k] * third);
               }
            }
            else {
               C.viewRow(second).assign(B.viewRow(first), Functions.plusMult(third));
            }
         }
      } else {
         if (transposeB) {
            if (Bcsc != null) {
               int[] columnPointers = Bcsc.getColumnPointers();
               int[] rowIndices  = Bcsc.getRowIndices();
               double[] data     = Bcsc.getData();

               int column = second;
               for (int k = columnPointers[column]; k < columnPointers[column + 1]; ++k) {
                  int row = rowIndices[k];
                  C.setQuick(first, row, C.getQuick(first, row) + data[k] * third);
               }
            }
            else {
               C.viewRow(first).assign(B.viewColumn(second), Functions.plusMult(third));
            }
         } else {
            if (Bcsr != null) {
               int[] rowPointers = Bcsr.getRowPointers();
               int[] colIndices  = Bcsr.getColumnIndices();
               double[] data     = Bcsr.getData();
               int row = second;
               for (int k = rowPointers[row]; k < rowPointers[row + 1]; ++k) {
                  int column = colIndices[k];
                  C.setQuick(first, column, C.getQuick(first, column) + data[k] * third);
               }
            }
            else {
               C.viewRow(first).assign(B.viewRow(second), Functions.plusMult(third));
            }
         }
      }
      return third;
   }
}
