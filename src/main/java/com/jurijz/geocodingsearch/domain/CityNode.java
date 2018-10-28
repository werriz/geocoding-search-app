package com.jurijz.geocodingsearch.domain;

/**
 * Created by jurijz on 10/26/2018.
 */
public class CityNode {

    //x axis - longitude
    //y axis - latitude
    private int axis;
    private float[] point;
    private int id;
    private boolean checked;
    private boolean orientation;

    private CityNode parent;
    private CityNode left;
    private CityNode right;
    private String cityId;

    public CityNode(float[] lPoint, int axis0, String cityId) {
        this(lPoint, axis0);
        this.cityId = cityId;
    }

    public CityNode(float[] lPoint, int axis0) {
        point = new float[2];
        axis = axis0;

        for (int k = 0; k < 2; k++) {
            point[k] = lPoint[k];
        }

        left = right = parent = null;

        checked = false;

        id = 0;
    }

    protected CityNode findParent(float[] lPoint) {

        CityNode parent = null;
        CityNode next = this;
        int split;

        while (next != null) {
            split = next.axis;

            parent = next;

            if (lPoint[split] > next.point[split]) {
                next = next.right;
            } else {
                next = next.left;
            }
        }
        return parent;
    }

    protected CityNode insert(float[] p, String cityId) {
        CityNode parent = findParent(p);

        if (equal(p, parent.point)) {
            return null;
        }

        CityNode newNode = new CityNode(p, parent.axis + 1 < 2 ? parent.axis + 1 : 0, cityId);

        newNode.parent = parent;

        if (p[parent.axis] > parent.point[parent.axis]) {
            parent.right = newNode;
            newNode.orientation = true; //
        } else {
            parent.left = newNode;
            newNode.orientation = false; //
        }

        return newNode;
    }

    protected boolean equal(float[] point1, float[] point2) {
        for (int k = 0; k < 2; k++) {
            if (point1[k] != point2[k]) {
                return false;
            }
        }

        return true;
    }

    protected double distance2(float[] point1, float[] point2) {
        double S = 0;
        for (int k = 0; k < 2; k++) {
            S += (point1[k] - point2[k]) * (point1[k] - point2[k]);
        }
        return S;
    }

    public int getAxis() {
        return axis;
    }

    public void setAxis(int axis) {
        this.axis = axis;
    }

    public float[] getPoint() {
        return point;
    }

    public void setPoint(float[] point) {
        this.point = point;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public CityNode getParent() {
        return parent;
    }

    public void setParent(CityNode parent) {
        this.parent = parent;
    }

    public CityNode getLeft() {
        return left;
    }

    public void setLeft(CityNode left) {
        this.left = left;
    }

    public CityNode getRight() {
        return right;
    }

    public void setRight(CityNode right) {
        this.right = right;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}