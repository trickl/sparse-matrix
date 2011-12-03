package com.trickl.matrix;

public interface CompressedSparseRow {
   int[] getRowPointers();
   int[] getColumnIndices();
   double[] getData();
}
