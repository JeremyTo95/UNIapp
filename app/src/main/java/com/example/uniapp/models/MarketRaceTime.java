package com.example.uniapp.models;

import java.util.ArrayList;
import java.util.List;

public class MarketRaceTime {
    List<RaceTime> allTimes;

    public MarketRaceTime() { }

    public static ArrayList<RaceTime> initAllTime() {
        ArrayList<RaceTime> allTimes = new ArrayList<RaceTime>();
        allTimes.add(new RaceTime("08/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:25.58", 21, "REG", 1063, ""));
        allTimes.add(new RaceTime("09/11/2019", "PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:25.22", 21, "REG", 1088, ""));
        allTimes.add(new RaceTime("12/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:24.93", 21, "REG", 1108, ""));
        allTimes.add(new RaceTime("14/05/2017", "MONTIGNY-LES-CORMEILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:26.37", 18, "DEP", 1010, ""));
        allTimes.add(new RaceTime("03/12/2016", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.09", 18, "REG", 964, ""));
        allTimes.add(new RaceTime("12/11/2016", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.27", 18, "DEP", 952, ""));
        allTimes.add(new RaceTime("19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:26.98", 17, "DEP", 994, ""));
        allTimes.add(new RaceTime("07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.30", 17, "DEP", 973, ""));
        allTimes.add(new RaceTime("13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.41", 17, "DEP", 966, ""));
        allTimes.add(new RaceTime("22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.87", 17, "DEP", 936, ""));
        allTimes.add(new RaceTime("08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.23", 16, "DEP", 851, ""));
        allTimes.add(new RaceTime("07/12/2014", "FOSSES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.80", 16, "DEP", 816, ""));
        allTimes.add(new RaceTime("22/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.54", 15, "DEP", 832, ""));
        allTimes.add(new RaceTime("24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:31.84", 15, "DEP", 698, ""));
        allTimes.add(new RaceTime("24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:32.52", 14, "DEP", 661, ""));
        allTimes.add(new RaceTime("14/10/2012", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:33.75", 14, "DEP", 596, ""));
        allTimes.add(new RaceTime("23/05/2010", "MASSY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:41.29", 11, "DEP", 272, ""));
        allTimes.add(new RaceTime("07/02/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:44.23", 11, "DEP", 180, ""));
        allTimes.add(new RaceTime("08/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:55.66", 21, "REG", 1067, ""));
        allTimes.add(new RaceTime("10/11/2019", "PONTOISE", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:56.41", 21, "REG", 1042, ""));
        allTimes.add(new RaceTime("13/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:56.66", 21, "REG", 1034, ""));
        allTimes.add(new RaceTime("14/05/2017", "MONTIGNY-LES-CORMEILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:57.37", 18, "DEP", 1012, ""));
        allTimes.add(new RaceTime("04/12/2016", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.95", 18, "REG", 931, ""));
        allTimes.add(new RaceTime("13/11/2016", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.37", 18, "DEP", 949, ""));
        allTimes.add(new RaceTime("05/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.63", 18, "REG", 941, ""));
        allTimes.add(new RaceTime("19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.09", 17, "DEP", 985, ""));
        allTimes.add(new RaceTime("10/04/2016", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.26", 17, "DEP", 979, ""));
        allTimes.add(new RaceTime("07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:58.15", 17, "DEP", 1015, ""));
        allTimes.add(new RaceTime("06/12/2015", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:00.26", 17, "REG", 948, ""));
        allTimes.add(new RaceTime("22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:00.02", 17, "DEP", 955, ""));
        allTimes.add(new RaceTime("08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:01.06", 17, "REG", 923, ""));
        allTimes.add(new RaceTime("08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:04.35", 16, "DEP", 824, ""));
        allTimes.add(new RaceTime("23/11/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:05.99", 16, "DEP", 777, ""));
        allTimes.add(new RaceTime("21/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:07.22", 15, "DEP", 743, ""));
        allTimes.add(new RaceTime("08/06/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:05.09", 15, "DEP", 803, ""));
        allTimes.add(new RaceTime("09/03/2014", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:08.96", 15, "DEP", 696, ""));
        allTimes.add(new RaceTime("24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:10.60", 15, "DEP", 653, ""));
        allTimes.add(new RaceTime("13/10/2013", "SAINT-LEU-LA-FORET", "FRANCE", "DEP", 100, 25, "freestyle", "01:11.16", 15, "", 638, ""));
        allTimes.add(new RaceTime("14/04/2013", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:12.01", 14, "DEP", 617, ""));
        allTimes.add(new RaceTime("02/02/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:13.88", 14, "DEP", 570, ""));
        allTimes.add(new RaceTime("27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:14.79", 14, "DEP", 549, ""));
        allTimes.add(new RaceTime("25/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:14.32", 14, "DEP", 560, ""));
        allTimes.add(new RaceTime("11/11/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:12.44", 14, "REG", 606, ""));
        allTimes.add(new RaceTime("17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:17.14", 13, "DEP", 494, ""));
        allTimes.add(new RaceTime("01/04/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:15.73", 13, "DEP", 527, ""));
        allTimes.add(new RaceTime("21/01/2012", "BEZONS", "FRANCE", "DEP", 100, 25, "freestyle", "01:17.40", 13, "", 488, ""));
        allTimes.add(new RaceTime("04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:19.96", 13, "DEP", 433, ""));
        allTimes.add(new RaceTime("15/05/2011", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:23.59", 12, "DEP", 360, ""));
        allTimes.add(new RaceTime("27/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:26.28", 12, "DEP", 310, ""));
        allTimes.add(new RaceTime("22/01/2011", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:24.73", 12, "DEP", 338, ""));
        allTimes.add(new RaceTime("18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "02:28.27", 16, "DEP", 692, ""));
        allTimes.add(new RaceTime("13/06/2010", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:26.38", 11, "DEP", 134, ""));
        allTimes.add(new RaceTime("30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:33.12", 11, "DEP", 97, ""));
        allTimes.add(new RaceTime("07/02/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:38.18", 11, "DEP", 74, ""));
        allTimes.add(new RaceTime("13/12/2009", "EZANVILLE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:57.69", 11, "DEP", 13, ""));
        allTimes.add(new RaceTime("17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "05:17.83", 17, "DEP", 650, ""));
        allTimes.add(new RaceTime("17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "05:59.73", 13, "DEP", 408, ""));
        allTimes.add(new RaceTime("05/12/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "07:03.98", 12, "DEP", 147, ""));
        allTimes.add(new RaceTime("24/02/2013", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "11:45.45", 14, "DEP", 508, ""));
        allTimes.add(new RaceTime("22/01/2012", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "12:54.87", 13, "DEP", 334, ""));
        allTimes.add(new RaceTime("05/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "13:12.94", 12, "DEP", 295, ""));
        allTimes.add(new RaceTime("13/11/2016", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:31.85", 18, "DEP", 896, ""));
        allTimes.add(new RaceTime("06/12/2015", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:33.47", 17, "REG", 891, ""));
        allTimes.add(new RaceTime("22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:31.96", 17, "DEP", 976, ""));
        allTimes.add(new RaceTime("08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:35.31", 16, "DEP", 793, ""));
        allTimes.add(new RaceTime("07/12/2014", "FOSSES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:34.87", 16, "DEP", 816, ""));
        allTimes.add(new RaceTime("23/11/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:34.84", 16, "DEP", 818, ""));
        allTimes.add(new RaceTime("21/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:35.85", 15, "DEP", 765, ""));
        allTimes.add(new RaceTime("24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:36.99", 15, "DEP", 709, ""));
        allTimes.add(new RaceTime("13/10/2013", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:36.27", 15, "DEP", 744, ""));
        allTimes.add(new RaceTime("25/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:39.19", 14, "DEP", 605, ""));
        allTimes.add(new RaceTime("16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:40.90", 13, "DEP", 531, ""));
        allTimes.add(new RaceTime("31/03/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:39.77", 13, "DEP", 579, ""));
        allTimes.add(new RaceTime("10/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:40.34", 13, "DEP", 554, ""));
        allTimes.add(new RaceTime("23/01/2011", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:45.41", 12, "DEP", 357, ""));
        allTimes.add(new RaceTime("11/04/2010", "ÉRAGNY-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:52.40", 11, "DEP", 156, ""));
        allTimes.add(new RaceTime("12/11/2016", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:10.34", 18, "DEP", 835, ""));
        allTimes.add(new RaceTime("06/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:11.22", 18, "REG", 814, ""));
        allTimes.add(new RaceTime("10/04/2016", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:12.95", 17, "DEP", 831, ""));
        allTimes.add(new RaceTime("07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:13.37", 17, "DEP", 821, ""));
        allTimes.add(new RaceTime("22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:10.30", 17, "DEP", 899, ""));
        allTimes.add(new RaceTime("08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:12.43", 17, "REG", 844, ""));
        allTimes.add(new RaceTime("09/03/2014", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:20.24", 15, "DEP", 658, ""));
        allTimes.add(new RaceTime("10/11/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:17.90", 15, "REG", 711, ""));
        allTimes.add(new RaceTime("10/11/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:18.40", 15, "REG", 700, ""));
        allTimes.add(new RaceTime("14/04/2013", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:22.55", 14, "DEP", 607, ""));
        allTimes.add(new RaceTime("03/02/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:21.81", 14, "DEP", 623, ""));
        allTimes.add(new RaceTime("27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:24.34", 14, "DEP", 569, ""));
        allTimes.add(new RaceTime("24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:22.07", 14, "DEP", 618, ""));
        allTimes.add(new RaceTime("13/05/2012", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:27.73", 13, "DEP", 501, ""));
        allTimes.add(new RaceTime("14/11/2010", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:45.98", 12, "REG", 208, ""));
        allTimes.add(new RaceTime("11/11/2017", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:37.34", 19, "REG", 749, ""));
        allTimes.add(new RaceTime("05/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:33.02", 18, "REG", 799, ""));
        allTimes.add(new RaceTime("17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:40.45", 17, "DEP", 772, ""));
        allTimes.add(new RaceTime("08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:42.67", 17, "REG", 746, ""));
        allTimes.add(new RaceTime("18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:47.26", 16, "DEP", 692, ""));
        allTimes.add(new RaceTime("09/11/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:52.47", 16, "REG", 634, ""));
        allTimes.add(new RaceTime("06/11/2011", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "03:19.88", 13, "REG", 369, ""));
        allTimes.add(new RaceTime("16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:48.35", 13, "DEP", 404, ""));
        allTimes.add(new RaceTime("10/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:49.95", 13, "DEP", 353, ""));
        allTimes.add(new RaceTime("26/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:55.76", 12, "DEP", 198, ""));
        allTimes.add(new RaceTime("30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "01:06.13", 11, "DEP", 31, ""));
        allTimes.add(new RaceTime("13/12/2009", "ÉZANVILLE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "01:03.72", 11, "DEP", 57, ""));
        allTimes.add(new RaceTime("14/10/2012", "SAINT-LEU-LA-FOReT", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "breaststroke", "01:47.07", 14, "DEP", 334, ""));
        allTimes.add(new RaceTime("13/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "butterfly", "00:29.36", 21, "REG", 937, ""));
        allTimes.add(new RaceTime("19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:30.44", 17, "DEP", 955, ""));
        allTimes.add(new RaceTime("13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:33.08", 17, "DEP", 815, ""));
        allTimes.add(new RaceTime("17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:39.94", 13, "DEP", 502, ""));
        allTimes.add(new RaceTime("31/03/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:41.78", 13, "DEP", 431, ""));
        allTimes.add(new RaceTime("21/01/2012", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:43.42", 13, "DEP", 372, ""));
        allTimes.add(new RaceTime("04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:41.35", 13, "DEP", 447, ""));
        allTimes.add(new RaceTime("06/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:47.08", 12, "DEP", 257, ""));
        allTimes.add(new RaceTime("13/06/2010", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:54.89", 11, "DEP", 82, ""));
        allTimes.add(new RaceTime("30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:50.89", 11, "DEP", 159, ""));
        allTimes.add(new RaceTime("14/10/2012", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "butterfly", "01:35.51", 14, "DEP", 327, ""));
        allTimes.add(new RaceTime("13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:14.01", 17, "DEP", 835, ""));
        allTimes.add(new RaceTime("27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:24.21", 14, "DEP", 583, ""));
        allTimes.add(new RaceTime("11/04/2010", "ÉRAGNY-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:54.28", 11, "DEP", 103, ""));
        allTimes.add(new RaceTime("17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "02:45.15", 17, "DEP", 711, ""));
        allTimes.add(new RaceTime("18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "02:58.10", 16, "DEP", 562, ""));
        allTimes.add(new RaceTime("24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:05.66", 14, "DEP", 483, ""));
        allTimes.add(new RaceTime("16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:13.30", 13, "DEP", 410, ""));
        allTimes.add(new RaceTime("13/05/2012", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:14.59", 13, "DEP", 398, ""));
        allTimes.add(new RaceTime("04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:21.83", 13, "DEP", 335, ""));
        allTimes.add(new RaceTime("15/05/2011", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:29.57", 12, "DEP", 273, ""));
        allTimes.add(new RaceTime("05/12/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:47.18", 12, "DEP", 157, ""));
        allTimes.add(new RaceTime("24/02/2013", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "06:31.11", 14, "DEP", 459, ""));
        allTimes.add(new RaceTime("11/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:04.85", 13, "DEP", 310, ""));
        allTimes.add(new RaceTime("26/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:31.18", 12, "DEP", 214, ""));
        allTimes.add(new RaceTime("06/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:41.49", 12, "DEP", 181, ""));
        allTimes.add(new RaceTime("29/02/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 50, 50, "freestyle", "00:26.75", 21, "NAT", 986, ""));
        allTimes.add(new RaceTime("11/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.27", 18, "DEP", 889, ""));
        allTimes.add(new RaceTime("11/06/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:27.30", 17, "REG", 973, ""));
        allTimes.add(new RaceTime("21/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:27.07", 17, "DEP", 988, ""));
        allTimes.add(new RaceTime("13/06/2015", "PARIS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.39", 16, "REG", 903, ""));
        allTimes.add(new RaceTime("10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.43", 16, "DEP", 900, ""));
        allTimes.add(new RaceTime("11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:30.93", 15, "DEP", 750, ""));
        allTimes.add(new RaceTime("02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:31.84", 14, "DEP", 698, ""));
        allTimes.add(new RaceTime("13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:33.49", 14, "DEP", 610, ""));
        allTimes.add(new RaceTime("06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:35.82", 13, "DEP", 495, ""));
        allTimes.add(new RaceTime("01/03/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:58.47", 21, "NAT", 977, ""));
        allTimes.add(new RaceTime("26/01/2020", "SAINT-GERMAIN-EN-LAYE", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:57.79", 21, "NAT", 998, ""));
        allTimes.add(new RaceTime("22/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:57.61", 21, "NAT", 1004, ""));
        allTimes.add(new RaceTime("01/07/2017", "PARIS", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:01.14", 18, "REG", 895, ""));
        allTimes.add(new RaceTime("12/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.00", 18, "DEP", 929, ""));
        allTimes.add(new RaceTime("12/06/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.79", 17, "REG", 931, ""));
        allTimes.add(new RaceTime("22/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.38", 17, "DEP", 944, ""));
        allTimes.add(new RaceTime("10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:04.51", 16, "DEP", 820, ""));
        allTimes.add(new RaceTime("11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:11.63", 15, "DEP", 626, ""));
        allTimes.add(new RaceTime("02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:12.79", 14, "DEP", 597, ""));
        allTimes.add(new RaceTime("06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:18.74", 13, "DEP", 459, ""));
        allTimes.add(new RaceTime("07/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:17.27", 13, "REG", 491, ""));
        allTimes.add(new RaceTime("12/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:33.84", 18, "DEP", 794, ""));
        allTimes.add(new RaceTime("22/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:33.68", 17, "DEP", 880, ""));
        allTimes.add(new RaceTime("10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:34.12", 16, "DEP", 856, ""));
        allTimes.add(new RaceTime("10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:34.37", 16, "DEP", 842, ""));
        allTimes.add(new RaceTime("11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:36.50", 15, "DEP", 733, ""));
        allTimes.add(new RaceTime("11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:37.23", 15, "DEP", 697, ""));
        allTimes.add(new RaceTime("02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:37.24", 14, "DEP", 697, ""));
        allTimes.add(new RaceTime("13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:41.29", 14, "DEP", 514, ""));
        allTimes.add(new RaceTime("01/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:44.82", 14, "DEP", 528, ""));
        allTimes.add(new RaceTime("01/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:45.98", 14, "DEP", 485, ""));
        allTimes.add(new RaceTime("13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:45.80", 14, "DEP", 492, ""));
        allTimes.add(new RaceTime("08/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "breaststroke", "01:57.57", 13, "REG", 199, ""));
        allTimes.add(new RaceTime("01/03/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 50, 50, "butterfly", "00:30.59", 21, "NAT", 864, ""));
        allTimes.add(new RaceTime("13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "butterfly", "00:39.10", 14, "DEP", 536, ""));
        allTimes.add(new RaceTime("06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "butterfly", "00:43.99", 13, "DEP", 353, ""));
        allTimes.add(new RaceTime("11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:02.66", 15, "DEP", 514, ""));
        allTimes.add(new RaceTime("02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:01.22", 14, "DEP", 529, ""));
        allTimes.add(new RaceTime("06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:25.65", 13, "DEP", 304, ""));
        allTimes.add(new RaceTime("08/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:27.36", 13, "REG", 290, ""));

        return allTimes;
    }

    public static List<RaceTime> getButterflyRaces(List<RaceTime> allTimes) {
        List<RaceTime> butterflyTimes = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getSwim().equals("butterfly")) butterflyTimes.add(allTimes.get(i));

        return butterflyTimes;
    }

    public static List<RaceTime> getBackstrokeRaces(List<RaceTime> allTimes) {
        List<RaceTime> backstrokeTimes = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getSwim().equals("backstroke")) backstrokeTimes.add(allTimes.get(i));

        return backstrokeTimes;
    }

    public static List<RaceTime> getBreaststrokeRaces(List<RaceTime> allTimes) {
        List<RaceTime> breaststrokeTimes = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getSwim().equals("breaststroke")) breaststrokeTimes.add(allTimes.get(i));

        return breaststrokeTimes;
    }

    public static List<RaceTime> getFreestyleRaces(List<RaceTime> allTimes) {
        List<RaceTime> freestyleTimes = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getSwim().equals("freestyle")) freestyleTimes.add(allTimes.get(i));

        return freestyleTimes;
    }

    public static List<RaceTime> getIMRaces(List<RaceTime> allTimes) {
        List<RaceTime> imTimes = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getSwim().equals("IM")) imTimes.add(allTimes.get(i));

        return imTimes;
    }

    public static List<RaceTime> get25poolSizeRaces(List<RaceTime> allTimes) {
        List<RaceTime> races25 = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getPoolSize() == 25) races25.add(allTimes.get(i));

        return races25;
    }

    public static List<RaceTime> get50poolSizeRaces(List<RaceTime> allTimes) {
        List<RaceTime> races50 = new ArrayList<RaceTime>();
        for (int i = 0; i < allTimes.size(); i++)
            if (allTimes.get(i).getPoolSize() == 50) races50.add(allTimes.get(i));

        return races50;
    }

    public static List<RaceTime> getRaceTimeByPoolSizeAndSwim(List<RaceTime> allTimes, int poolSize, String swim) {
        List<RaceTime> result = new ArrayList<>();
        RaceTime tmp;
        for (int i = 0; i < allTimes.size(); i++) {
            tmp = allTimes.get(i);
            if (tmp.getPoolSize() == poolSize && tmp.getSwim().equals(swim))
                result.add(tmp);
        }

        return result;
    }

    public static List<RaceTime> getRacesByPoolSizeDistanceRaceSwimRace(List<RaceTime> allTimes, int poolSize, int distance, String swim) {
        List<RaceTime> result = new ArrayList<>();
        RaceTime tmp;
        for (int i = 0; i < allTimes.size(); i++) {
            tmp = allTimes.get(i);
            if (tmp.getPoolSize() == poolSize && tmp.getDistanceRace() == distance && tmp.getSwim().equals(swim))
                result.add(tmp);
        }

        return result;
    }

    public static float fetchTimeToFloat(String time) {
        float myTime = 0.0f;
        myTime += Float.parseFloat(String.valueOf(time.charAt(0) )) * 10 * 60;
        myTime += Float.parseFloat(String.valueOf(time.charAt(1))) * 60;
        myTime += Float.parseFloat(String.valueOf(time.charAt(3))) * 10;
        myTime += Float.parseFloat(String.valueOf(time.charAt(4)));
        myTime += Float.parseFloat(String.valueOf(time.charAt(6))) / 10;
        myTime += Float.parseFloat(String.valueOf(time.charAt(7))) / 100;
        return myTime;
    }

    public static int convertTimeToPointFFN(String time) {
        return 0;
    }
}
