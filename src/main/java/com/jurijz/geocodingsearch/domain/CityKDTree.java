package com.jurijz.geocodingsearch.domain;

/**
 * Created by jurijz on 10/26/2018.
 */
public class CityKDTree {

    private CityNode root;

    private double d_min;
    private CityNode nearest_neighbour;
    private int kd_id;
    private int nodeList;

    private final CityNode[] checkedNodes;
    private int checked_nodes;
    private final CityNode[] cityArr;

    private final float[] point_min, point_max;
    private final boolean[] max_boundary, min_boundary;

    private int node_boundary;

    public CityKDTree(int citiesSize) {
        root = null;
        kd_id = 1;
        nodeList = 0;

        cityArr = new CityNode[citiesSize];
        checkedNodes = new CityNode[citiesSize];

        max_boundary = new boolean[2];
        min_boundary = new boolean[2];

        point_min = new float[2];
        point_max = new float[2];
    }

    public void add(float[] lPoint, String cityId) {
        if (nodeList >= 2000000 - 1) {
            return; // can't add more points
        }

        if (root == null) {
            root = new CityNode(lPoint, 0, cityId);
            root.setId(kd_id++);
            cityArr[nodeList++] = root;
        } else {
            CityNode pNode;

            if ((pNode = root.insert(lPoint, cityId)) != null) {
                pNode.setId(kd_id++);
                cityArr[nodeList++] = pNode;
            }
        }
    }

    public CityNode findNearest(float[] lPoint) {
        if (root == null) {
            return null;
        }

        checked_nodes = 0;

        CityNode parent = root.findParent(lPoint);

        nearest_neighbour = parent;

        d_min = root.distance2(lPoint, parent.getPoint());

        if (parent.equal(lPoint, parent.getPoint())) {
            return nearest_neighbour;
        }

        search_parent(parent, lPoint);
        uncheck();

        return nearest_neighbour;
    }


    private void check_subtree(CityNode node, float[] lPoint) {
        if ((node == null) || node.isChecked()) {
            return;
        }

        checkedNodes[checked_nodes++] = node;
        node.setChecked(true);

        set_bounding_cube(node, lPoint);

        int dim = node.getAxis();

        float d = node.getPoint()[dim] - lPoint[dim];

        if (d * d > d_min) {
            if (node.getPoint()[dim] > lPoint[dim]) {
                check_subtree(node.getLeft(), lPoint);
            } else {
                check_subtree(node.getRight(), lPoint);
            }
        } else {
            check_subtree(node.getLeft(), lPoint);
            check_subtree(node.getRight(), lPoint);
        }
    }


    private void set_bounding_cube(CityNode node, float[] lPoint) {
        if (node == null) {
            return;
        }

        int d = 0;
        float dx;

        for (int k = 0; k < 2; k++) {
            dx = node.getPoint()[k] - lPoint[k];

            if (dx > 0) {
                dx *= dx;

                if (!max_boundary[k]) {
                    if (dx > point_max[k]) {
                        point_max[k] = dx;
                    }
                    if (point_max[k] > d_min) {
                        max_boundary[k] = true;
                        node_boundary++;
                    }
                }
            } else {
                dx *= dx;

                if (!min_boundary[k]) {
                    if (dx > point_min[k]) {
                        point_min[k] = dx;
                    }
                    if (point_min[k] > d_min) {
                        min_boundary[k] = true;
                        node_boundary++;
                    }
                }
            }

            d += dx;

            if (d > d_min) {
                return;
            }
        }

        if (d < d_min) {
            d_min = d;
            nearest_neighbour = node;
        }
    }

    private void search_parent(CityNode parent, float[] lPoint) {

        for (int k = 0; k < 2; k++) {
            point_min[k] = point_max[k] = 0;
            max_boundary[k] = min_boundary[k] = false; //
        }

        node_boundary = 0;

        while (parent != null && (node_boundary != 2 * 2)) {
            check_subtree(parent, lPoint);
            parent = parent.getParent();
        }

    }


    private void uncheck() {
        for (int n = 0; n < checked_nodes; n++) {
            checkedNodes[n].setChecked(false);
        }
    }

}