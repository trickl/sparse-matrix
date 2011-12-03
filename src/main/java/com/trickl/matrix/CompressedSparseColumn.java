package com.trickl.matrix;

public interface CompressedSparseColumn {
   int[] getColumnPointers();
   int[] getRowIndices();
   double[] getData();
}
