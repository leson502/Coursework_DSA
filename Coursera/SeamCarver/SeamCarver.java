import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {

    private Picture pic;

    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException();
        this.pic = new Picture(picture);
    }


    public Picture picture() {
        return new Picture(pic);
    }

    public int width() {
        return pic.width();
    }

    public int height() {
        return pic.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= pic.width() || y < 0 || y >= pic.height())
            throw new IllegalArgumentException("invalid coordinate");
        if (x == 0 || x == pic.width() - 1 || y == 0 || y == pic.height() - 1)
            return 1000;
        Color pixel1 = pic.get(x - 1, y);
        Color pixel2 = pic.get(x + 1, y);
        double deltaX2 = Math.pow(pixel1.getBlue() - pixel2.getBlue(), 2)
                + Math.pow(pixel1.getGreen() - pixel2.getGreen(), 2)
                + Math.pow(pixel1.getRed() - pixel2.getRed(), 2);
        pixel1 = pic.get(x, y - 1);
        pixel2 = pic.get(x, y + 1);
        double deltaY2 = Math.pow(pixel1.getBlue() - pixel2.getBlue(), 2)
                + Math.pow(pixel1.getGreen() - pixel2.getGreen(), 2)
                + Math.pow(pixel1.getRed() - pixel2.getRed(), 2);
        return Math.sqrt(deltaX2 + deltaY2);
    }

    public int[] findHorizontalSeam() {
        pic = transpose();
        int[] result = findVerticalSeam();
        pic = transpose();
        return result;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[pic.height()];
        double[][] dp = new double[pic.height()][pic.width()];
        int[][] trace = new int[pic.height()][pic.width()];

        for (int i = 0; i < pic.width(); i++) {
            dp[0][i] = 1000;
        }

        if (pic.height() > 0)
            for (int i = 1; i < pic.height(); i++) {
                for (int j = 0; j < pic.width(); j++) {
                    dp[i][j] = Double.MAX_VALUE;
                }
                for (int j = 0; j < pic.width(); j++) {
                    double e = energy(j, i);
                    for (int k = -1; k <= 1; k++) {
                        if (0 > j + k || j + k >= pic.width())
                            continue;
                        if (dp[i][j] > dp[i - 1][j + k] + e) {
                            dp[i][j] = dp[i - 1][j + k] + e;
                            trace[i][j] = j + k;
                        }
                    }
                }
            }

        double min = Double.MAX_VALUE;
        int currTrace = 0;
        for (int i = 0; i < pic.width(); i++) {
            if (min > dp[pic.height() - 1][i]) {
                min = dp[pic.height() - 1][i];
                currTrace = i;
            }
        }

        int i = pic.height() - 1;
        while (i >= 0) {
            seam[i] = currTrace;
            currTrace = trace[i][currTrace];
            i--;
        }

        return seam;
    }

    public void removeHorizontalSeam(int[] seam) {
        pic = transpose();
        removeVerticalSeam(seam);
        pic = transpose();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("seam can't be null");
        if (seam.length != pic.height())
            throw new IllegalArgumentException("seam length and height is differ");
        for (int i = 0; i < seam.length; i++) {
            if (0 > seam[i] && seam[i] > pic.width())
                throw new IllegalArgumentException();
            if (i > 0 && Math.abs(seam[i] - seam[i - 1]) > 1)
                throw new IllegalArgumentException();
        }
        Picture p = new Picture(pic.width() - 1, pic.height());

        for (int i = 0; i < pic.height(); i++) {
            for (int j = 0; j < pic.width(); j++) {
                if (j < seam[i])
                    p.set(j, i, pic.get(j, i));
                else if (j > seam[i])
                    p.set(j - 1, i, pic.get(j, i));
            }
        }

        pic = p;
    }

    private Picture transpose() {
        Picture p = new Picture(pic.height(), pic.width());
        for (int i = 0; i < p.width(); i++) {
            for (int j = 0; j < p.height(); j++) {
                p.set(i, j, pic.get(j, i));
            }
        }
        return p;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}