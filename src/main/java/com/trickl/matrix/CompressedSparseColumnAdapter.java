package com.trickl.matrix;

import cern.colt.function.IntIntDoubleFunction;
import cern.colt.matrix.DoubleMatrix2D;
import java.util.Arrays;

public class CompressedSparseColumnAdapter implements CompressedSparseColumn {

   private int[] rowIndices;
   private int[] columnPointers;   
   private double[] data;

   public CompressedSparseColumnAdapter(final DoubleMatrix2D mat) {
      // First get all the non-zero values in row major order
      // SparseDoubleMatrix is hash based, so we need to sort accordingly
      final int[] i = new int[1];
      i[0] = 0;
      final int[] columnMajorIndex = new int[mat.cardinality()];
      columnPointers = new int[mat.columns() + 1];
      rowIndices = new int[mat.cardinality()];
      data = new double[mat.cardinality()];
      mat.forEachNonZero(new IntIntDoubleFunction() {

         @Override
         public double apply(int first, int second, double value) {
            columnMajorIndex[i[0]] = second * mat.rows() + first;
            i[0] += 1;
            return value;
         }
      });
      Arrays.sort(columnMajorIndex);

      // Convert to CSR format
      int lastColumn = -1;
      for (int j = 0; j < columnMajorIndex.length; ++j) {
         int index = columnMajorIndex[j];
         int column = index / mat.rows();
         int row = index % mat.rows();
         while (column != lastColumn) {
            columnPointers[++lastColumn] = j;
         }
         data[j] = mat.getQuick(row, column);
         rowIndices[j] = row;
      }
      while (lastColumn < mat.columns()) {
         columnPointers[++lastColumn] = columnMajorIndex.length;
      }
   }
     
   @Override
   public int[] getRowIndices() {
      return rowIndices;
   }

   @Override
   public int[] getColumnPointers() {
      return columnPointers;
   }

   @Override
   public double[] getData() {
      return data;
   }
}
