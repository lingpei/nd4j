package org.nd4j.linalg.jcublas.buffer;

import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.jcublas.JCublas;
import jcuda.runtime.JCuda;
import jcuda.runtime.cudaMemcpyKind;
import org.nd4j.linalg.api.buffer.DataBuffer;

/**
 * Cuda double  buffer
 *
 * @author Adam Gibson
 */
public class CudaDoubleDataBuffer extends BaseCudaDataBuffer {


    /**
     * Base constructor
     *
     * @param length      the length of the buffer
     */
    public CudaDoubleDataBuffer(int length) {
        super(length, Sizeof.DOUBLE);
    }

    public CudaDoubleDataBuffer(double[] data) {
        this(data.length);
        setData(data);
    }


    @Override
    public void setData(int[] data) {

    }

    @Override
    public void setData(float[] data) {

    }

    @Override
    public void setData(double[] data) {

        if(data.length != length)
            throw new IllegalArgumentException("Unable to set vector, must be of length " + length() + " but found length " + data.length);

        if(pointer() == null)
            alloc();

        JCublas.cublasSetVector(
                length,
                elementSize,
                Pointer.to(data),
                1,
                pointer(),
                1);

    }

    @Override
    public byte[] asBytes() {
        return new byte[0];
    }

    @Override
    public int dataType() {
        return DataBuffer.DOUBLE;
    }

    @Override
    public float[] asFloat() {
        return new float[0];
    }

    @Override
    public double[] asDouble() {
        double[] ret = new double[length];
        Pointer p = Pointer.to(ret);
        JCublas.cublasGetVector(
                length,
                elementSize(),
                pointer(),
                1,
                p,
                1);
        return ret;
    }

    @Override
    public int[] asInt() {
        return new int[0];
    }


    @Override
    public double getDouble(int i) {
        double[] d = new double[1];
        Pointer p = Pointer.to(d);
        get(i,p);
        return d[0];
    }

    @Override
    public float getFloat(int i) {
        return (float) getDouble(i);
    }

    @Override
    public Number getNumber(int i) {
        return getDouble(i);
    }



    @Override
    public void put(int i, float element) {
      put(i,(double)element);
    }

    @Override
    public void put(int i, double element) {
        double[] d = new double[]{element};
        Pointer p = Pointer.to(d);
        set(i,p);

    }

    @Override
    public void put(int i, int element) {
        put(i,(double) element);
    }




    @Override
    public int getInt(int ix) {
        return (int) getDouble(ix);
    }

    @Override
    public DataBuffer dup() {
        CudaDoubleDataBuffer buffer = new CudaDoubleDataBuffer(length);
        copyTo(buffer);
        return buffer;
    }

    @Override
    public void flush() {

    }


}