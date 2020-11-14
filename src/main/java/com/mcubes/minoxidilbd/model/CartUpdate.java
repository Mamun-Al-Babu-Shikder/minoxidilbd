package com.mcubes.minoxidilbd.model;

import java.util.Arrays;

/**
 * Created by A.A.MAMUN on 10/12/2020.
 */
public class CartUpdate {

    private long[] pid;
    private int[] quantity;

    public CartUpdate() {
    }

    public long[] getPid() {
        return pid;
    }

    public void setPid(long[] pid) {
        this.pid = pid;
    }

    public int[] getQuantity() {
        return quantity;
    }

    public void setQuantity(int[] quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartUpdate{" +
                "pid=" + Arrays.toString(pid) +
                ", quantity=" + Arrays.toString(quantity) +
                '}';
    }
}
