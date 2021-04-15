package com.matteo.tonnicchi.utils;

public class Pair<T> {

    private final T left;
    private final T right;

    public Pair(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return this.left;
    }

    public T getRight() {
        return this.right;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
  
        if (!(o instanceof Pair)) {
            return false;
        }
          
        Pair<T> otherPair = (Pair<T>) o;

        return this.left.equals(otherPair.left) && this.right.equals(otherPair.right);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.left == null ? 0 : this.left.hashCode());
        hash = 31 * hash + (this.right == null ? 0 : this.right.hashCode());
        return hash;
    }

}