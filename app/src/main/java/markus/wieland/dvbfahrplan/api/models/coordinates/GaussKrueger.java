package markus.wieland.dvbfahrplan.api.models.coordinates;


import java.util.Optional;

public class GaussKrueger {

    private GaussKrueger() {
    }

    /*
     Copyright (c) 2006, HELMUT H. HEIMEIER
     Permission is hereby granted, free of charge, to any person obtaining a
     copy of this software and associated documentation files (the "Software"),
     to deal in the Software without restriction, including without limitation
     the rights to use, copy, modify, merge, publish, distribute, sublicense,
     and/or sell copies of the Software, and to permit persons to whom the
     Software is furnished to do so, subject to the following conditions:
     The above copyright notice and this permission notice shall be included
     in all copies or substantial portions of the Software.
     */

    public static Optional<WGSCoordinate> gk2wgs(GKCoordinate gk) {
        Optional<GKCoordinate> pot = gk2pot(gk);
        return pot.map(GaussKrueger::pot2wgs);
    }

    public static Optional<GKCoordinate> wgs2gk(WGSCoordinate wgs) {
        Optional<GKCoordinate> pot = wgs2pot(wgs);
        if (!pot.isPresent()) {
            return Optional.empty();
        }
        return pot2gk(pot.get());
    }

    public static Optional<GKCoordinate> gk2pot(GKCoordinate coordinate) {
        final Double rw = coordinate.getX();
        final Double hw = coordinate.getY();

        final double a = 6377397.155;
        final double f = 3.342773154e-3;
        final double pi = Math.PI;

        final double c = a / (1 - f);

        final double ex2 = (2 * f - f * f) / ((1 - f) * (1 - f));
        final double ex4 = ex2 * ex2;
        final double ex6 = ex4 * ex2;
        final double ex8 = ex4 * ex4;

        final double e0 = c * (pi / 180) * (1 - 3 * ex2 / 4 + 45 * ex4 / 64 - 175 * ex6 / 256 + 11025 * ex8 / 16384);
        final double f2 = (180 / pi) * (3 * ex2 / 8 - 3 * ex4 / 16 + 213 * ex6 / 2048 - 255 * ex8 / 4096);
        final double f4 = (180 / pi) * (21 * ex4 / 256 - 21 * ex6 / 256 + 533 * ex8 / 8192);
        final double f6 = (180 / pi) * (151 * ex6 / 6144 - 453 * ex8 / 12288);

        final double sigma = hw / e0;
        final double sigmr = sigma * pi / 180;
        final double bf = sigma + f2 * Math.sin(2 * sigmr) + f4 * Math.sin(4 * sigmr) + f6 * Math.sin(6 * sigmr);

        final double br = bf * pi / 180;
        final double tan1 = Math.tan(br);
        final double tan2 = tan1 * tan1;
        final double tan4 = tan2 * tan2;

        final double cos1 = Math.cos(br);
        final double cos2 = cos1 * cos1;

        final double etasq = ex2 * cos2;

        final double nd = c / Math.sqrt(1 + etasq);
        final double nd2 = nd * nd;
        final double nd4 = nd2 * nd2;
        final double nd6 = nd4 * nd2;
        final double nd3 = nd2 * nd;
        final double nd5 = nd4 * nd;

        final double kzNR = rw / 1e6;
        final int kzR = (int) kzNR;
        final double lh = (double) kzR * 3;
        final double dy = rw - ((double) kzR * 1e6 + 500000);
        final double dy2 = dy * dy;
        final double dy4 = dy2 * dy2;
        final double dy3 = dy2 * dy;
        final double dy5 = dy4 * dy;
        final double dy6 = dy3 * dy3;

        final double b2 = -tan1 * (1 + etasq) / (2 * nd2);
        final double b4 = tan1 * (5 + 3 * tan2 + 6 * etasq * (1 - tan2)) / (24 * nd4);
        final double b6 = -tan1 * (61 + 90 * tan2 + 45 * tan4) / (720 * nd6);

        final double l1 = 1 / (nd * cos1);
        final double l3 = -(1 + 2 * tan2 + etasq) / (6 * nd3 * cos1);
        final double l5 = (5 + 28 * tan2 + 24 * tan4) / (120 * nd5 * cos1);

        // Geographischer Breite bp und Länge lp als Funktion von Rechts- und Hochwert
        final double bp = bf + (180 / pi) * (b2 * dy2 + b4 * dy4 + b6 * dy6);
        final double lp = lh + (180 / pi) * (l1 * dy + l3 * dy3 + l5 * dy5);

        if (lp < 5 || lp > 16 || bp < 46 || bp > 56) {
            // RW und/oder HW ungültig für das deutsche Gauss-Krüger-System
            return Optional.empty();
        }

        return Optional.of(new GKCoordinate(lp, bp));
    }

    /// Die Funktion verschiebt das Kartenbezugssystem (map datum) vom in
    /// Deutschland gebräuchlichen Potsdam-Datum zum WGS84 (World Geodetic
    /// System 84) Datum. Geographische Länge lp und Breite bp gemessen in
    /// grad auf dem Bessel-Ellipsoid müssen gegeben sein.
    /// Ausgegeben werden geographische Länge lw und
    /// Breite bw (in grad) auf dem WGS84-Ellipsoid.
    /// Bei der Transformation werden die Ellipsoidachsen parallel
    /// verschoben um dx = 587 m, dy = 16 m und dz = 393 m.
    public static WGSCoordinate pot2wgs(GKCoordinate pot) {
        final Double lp = pot.getX();
        final Double bp = pot.getY();

        final double a = 6378137.000 - 739.845;
        final double fq = 3.35281066e-3 - 1.003748e-05;

        final double f = 3.35281066e-3;

        final double dx = 587.0;
        final double dy = 16.0;
        final double dz = 393.0;

        final double e2q = (2 * fq - fq * fq);
        final double e2 = (2 * f - f * f);

        final double pi = Math.PI;
        final double b1 = bp * (pi / 180);
        final double l1 = lp * (pi / 180);

        final double nd = a / Math.sqrt(1 - e2q * Math.sin(b1) * Math.sin(b1));

        final double xp = nd * Math.cos(b1) * Math.cos(l1);
        final double yp = nd * Math.cos(b1) * Math.sin(l1);
        final double zp = (1 - e2q) * nd * Math.sin(b1);

        final double x = xp + dx;
        final double y = yp + dy;
        final double z = zp + dz;

        final double rb = Math.sqrt(x * x + y * y);
        final double b2 = (180 / pi) * Math.atan((z / rb) / (1 - e2));

        double l2 = 0.0;
        double someOtherValueIdkIAmJustRefactoring = (180 / pi) * Math.atan(y / x);

        if (x > 0) {
            l2 = someOtherValueIdkIAmJustRefactoring;
        }
        if (x < 0 && y > 0) {
            l2 = someOtherValueIdkIAmJustRefactoring + 180;
        }
        if (x < 0 && y < 0) {
            l2 = someOtherValueIdkIAmJustRefactoring - 180;
        }

        return new WGSCoordinate(b2, l2);
    }

    public static Optional<GKCoordinate> wgs2pot(WGSCoordinate wgs) {
        final double lw = wgs.getLongitude();
        final double bw = wgs.getLatitude();

        final double a = 6378137.000;
        final double fq = 3.35281066e-3;

        final double f = fq - 1.003748e-5;

        final double dx = -587.0;
        final double dy = -16.0;
        final double dz = -393.0;

        final double e2q = (2 * fq - fq * fq);
        final double e2 = (2 * f - f * f);

        final double pi = Math.PI;
        final double b1 = bw * (pi / 180);
        final double l1 = lw * (pi / 180);

        final double nd = a / Math.sqrt(1 - e2q * Math.sin(b1) * Math.sin(b1));

        final double xw = nd * Math.cos(b1) * Math.cos(l1);
        final double yw = nd * Math.cos(b1) * Math.sin(l1);
        final double zw = (1 - e2q) * nd * Math.sin(b1);

        final double x = xw + dx;
        final double y = yw + dy;
        final double z = zw + dz;

        final double rb = Math.sqrt(x * x + y * y);
        final double b2 = (180 / pi) * Math.atan((z / rb) / (1 - e2));

        double l2 = 0.0;
        double someOtherValueIdkIAmJustRefactoring = (180 / pi) * Math.atan(y / x);
        if (x > 0) {
            l2 = someOtherValueIdkIAmJustRefactoring;
        }
        if (x < 0 && y > 0) {
            l2 = someOtherValueIdkIAmJustRefactoring + 180;
        }
        if (x < 0 && y < 0) {
            l2 = someOtherValueIdkIAmJustRefactoring - 180;
        }

        if (l2 < 5 || l2 > 16 || b2 < 46 || b2 > 56) {
            return Optional.empty();
        }

        return Optional.of(new GKCoordinate(l2, b2));
    }

    public static Optional<GKCoordinate> pot2gk(GKCoordinate pot) {
        final double lp = pot.getX();
        final double bp = pot.getY();

        if (bp < 46 || bp > 56 || lp < 5 || lp > 16) {
            return Optional.empty();
        }

        final double a = 6377397.155;
        final double f = 3.342773154e-3;
        final double pi = Math.PI;


        final double c = a / (1 - f);

        final double ex2 = (2 * f - f * f) / ((1 - f) * (1 - f));
        final double ex4 = ex2 * ex2;
        final double ex6 = ex4 * ex2;
        final double ex8 = ex4 * ex4;

        final double e0 = c * (pi / 180) * (1 - 3 * ex2 / 4 + 45 * ex4 / 64 - 175 * ex6 / 256 + 11025 * ex8 / 16384);
        final double e2 = c * (-3 * ex2 / 8 + 15 * ex4 / 32 - 525 * ex6 / 1024 + 2205 * ex8 / 4096);
        final double e4 = c * (15 * ex4 / 256 - 105 * ex6 / 1024 + 2205 * ex8 / 16384);
        final double e6 = c * (-35 * ex6 / 3072 + 315 * ex8 / 12288);

        final double br = bp * pi / 180;

        final double tan1 = Math.tan(br);
        final double tan2 = tan1 * tan1;
        final double tan4 = tan2 * tan2;

        final double cos1 = Math.cos(br);
        final double cos2 = cos1 * cos1;
        final double cos4 = cos2 * cos2;
        final double cos3 = cos2 * cos1;
        final double cos5 = cos4 * cos1;

        final double etasq = ex2 * cos2;

        final double nd = c / Math.sqrt(1 + etasq);

        final double g = e0 * bp + e2 * Math.sin(2 * br) + e4 * Math.sin(4 * br) + e6 * Math.sin(6 * br);

        final double kzNR = (lp + 1.5) / 3;
        final int kzR = (int) kzNR;
        final double lh = (double) kzR * 3;
        final double dl = (lp - lh) * pi / 180;
        final double dl2 = dl * dl;
        final double dl4 = dl2 * dl2;
        final double dl3 = dl2 * dl;
        final double dl5 = dl4 * dl;

        double hw = (g + nd * cos2 * tan1 * dl2 / 2 + nd * cos4 * tan1 * (5 - tan2 + 9 * etasq) * dl4 / 24);
        double rw = (nd * cos1 * dl + nd * cos3 * (1 - tan2 + etasq) * dl3 / 6 + nd * cos5 * (5 - 18 * tan2 + tan4) * dl5 / 120 + (double) kzR * 1e6 + 500000);

        int hwR = (int) hw;
        double hwRR = (double) hwR;
        double nk = hw - hwRR;
        hwR = (int) hw;
        hwRR = (double) hwR;

        if (nk < 0.5) {
            hw = hwRR;
        } else {
            hw = hwRR + 1;
        }

        int rwR = (int) rw;

        if (nk < 0.5) {
            rw = (double) rwR;
        } else {
            rw = (double) rwR + 1;
        }

        return Optional.of(new GKCoordinate(rw, hw));
    }

}

