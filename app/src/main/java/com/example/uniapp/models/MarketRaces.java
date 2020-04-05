package com.example.uniapp.models;

import android.util.Log;

import com.example.uniapp.models.database.dao.race.Race;
import com.example.uniapp.views.comparators.RaceDateComparator;
import com.example.uniapp.views.comparators.TimeComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MarketRaces {
    private List<Race> allTimes;

    public MarketRaces() { }

    /*public static ArrayList<Race> initAllTimes() {
        ArrayList<Race> allTimes = new ArrayList<Race>();
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:25.58", 1063, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "09/11/2019", "PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:25.22", 1088, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "freestyle", "00:24.93", 1108, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/05/2017", "MONTIGNY-LES-CORMEILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:26.37", 1010, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "03/12/2016", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.09", 964, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/11/2016", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.27", 952, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:26.98", 994, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.30", 973, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.41", 966, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:27.87", 936, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.23", 851, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/12/2014", "FOSSES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.80", 816, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:29.54", 832, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:31.84", 698, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:32.52", 661, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/10/2012", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:33.75", 596, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "23/05/2010", "MASSY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:41.29", 272, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/02/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "freestyle", "00:44.23", 180, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:55.66", 1067, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/11/2019", "PONTOISE", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:56.41", 1042, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 100, 25, "freestyle", "00:56.66", 1034, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/05/2017", "MONTIGNY-LES-CORMEILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:57.37", 1012, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "04/12/2016", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.95", 931, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/11/2016", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.37", 949, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "05/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.63", 941, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.09", 985, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/04/2016", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:59.26", 979, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "00:58.15", 1015, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/12/2015", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:00.26", 948, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:00.02", 955, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:01.06", 923, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:04.35", 824, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "23/11/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:05.99", 777, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "21/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:07.22", 743, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/06/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:05.09", 803, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "09/03/2014", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:08.96", 696, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:10.60", 653, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/10/2013", "SAINT-LEU-LA-FORET", "FRANCE", "DEP", 100, 25, "freestyle", "01:11.16", 638, ""));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/04/2013", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:12.01", 617, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "02/02/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:13.88", 570, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:14.79", 549, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "25/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:14.32", 560, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/11/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:12.44", 606, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:17.14", 494, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/04/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:15.73", 527, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "21/01/2012", "BEZONS", "FRANCE", "DEP", 100, 25, "freestyle", "01:17.40", 488, ""));
        allTimes.add(new Race(UUID.randomUUID().toString(), "04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:19.96", 433, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "15/05/2011", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:23.59", 360, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "27/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:26.28", 310, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/01/2011", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "freestyle", "01:24.73", 338, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "02:28.27", 692, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/06/2010", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:26.38", 134, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:33.12", 97, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/02/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:38.18", 74, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/12/2009", "EZANVILLE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "freestyle", "03:57.69", 13, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "05:17.83", 650, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "05:59.73", 408, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "05/12/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "freestyle", "07:03.98", 147, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/02/2013", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "11:45.45", 508, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/01/2012", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "12:54.87", 334, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "05/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 800, 25, "freestyle", "13:12.94", 295, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/11/2016", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:31.85", 896, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/12/2015", "BOBIGNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:33.47", 891, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:31.96", 976, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/02/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:35.31", 793, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/12/2014", "FOSSES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:34.87", 816, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "23/11/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:34.84", 818, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "21/06/2014", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:35.85", 765, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2013", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:36.99", 709, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/10/2013", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:36.27", 744, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "25/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:39.19", 605, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:40.90", 531, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "31/03/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:39.77", 579, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:40.34", 554, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "23/01/2011", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:45.41", 357, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/04/2010", "ÉRAGNY-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "backstroke", "00:52.40", 156, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/11/2016", "GARGES-LES-GONESSE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:10.34", 835, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:11.22", 814, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/04/2016", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:12.95", 831, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/02/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:13.37", 821, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/11/2015", "TAVERNY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:10.30", 899, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:12.43", 844, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "09/03/2014", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:20.24", 658, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/11/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:17.90", 711, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/11/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:18.40", 700, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/04/2013", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:22.55", 607, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "03/02/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:21.81", 623, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:24.34", 569, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:22.07", 618, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/05/2012", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:27.73", 501, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/11/2010", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "backstroke", "01:45.98", 208, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/11/2017", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:37.34", 749, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "05/11/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:33.02", 799, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:40.45", 772, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/11/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:42.67", 746, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:47.26", 692, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "09/11/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "02:52.47", 634, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/11/2011", "EAUBONNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "backstroke", "03:19.88", 369, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:48.35", 404, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:49.95", 353, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "26/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "00:55.76", 198, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "01:06.13", 31, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/12/2009", "ÉZANVILLE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "breaststroke", "01:03.72", 57, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/10/2012", "SAINT-LEU-LA-FOReT", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "breaststroke", "01:47.07", 334, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/10/2019", "CERGY-PONTOISE", "FRANCE", "AS HERBLAY NATATION", 50, 25, "butterfly", "00:29.36", 937, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "19/06/2016", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:30.44", 955, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:33.08", 815, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:39.94", 502, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "31/03/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:41.78", 431, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "21/01/2012", "BEZONS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:43.42", 372, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:41.35", 447, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:47.08", 257, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/06/2010", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:54.89", 82, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "30/05/2010", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 25, "butterfly", "00:50.89", 159, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "14/10/2012", "SAINT-LEU-LA-FORET", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "butterfly", "01:35.51", 327, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/12/2015", "HERBLAY", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:14.01", 835, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "27/01/2013", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:24.21", 583, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/04/2010", "ÉRAGNY-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 25, "IM", "01:54.28", 103, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "17/01/2016", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "02:45.15", 711, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "18/01/2015", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "02:58.10", 562, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/11/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:05.66", 483, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "16/06/2012", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:13.30", 410, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/05/2012", "FRANCONVILLE-LA-GARENNE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:14.59", 398, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "04/12/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:21.83", 335, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "15/05/2011", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:29.57", 273, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "05/12/2010", "BEAUMONT-SUR-OISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 25, "IM", "03:47.18", 157, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "24/02/2013", "SOISY-SOUS-MONTMORENCY", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "06:31.11", 459, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/03/2012", "CERGY-PONTOISE", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:04.85", 310, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "26/03/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:31.18", 214, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/02/2011", "ARGENTEUIL", "FRANCE", "ETOILE SAINT-LEU NATATION", 400, 25, "IM", "07:41.49", 181, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "29/02/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 50, 50, "freestyle", "00:26.75", 986, "NAT"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.27", 889, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/06/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:27.30", 973, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "21/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:27.07", 988, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/06/2015", "PARIS", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.39", 903, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:28.43", 900, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:30.93", 750, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:31.84", 698, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:33.49", 610, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "freestyle", "00:35.82", 495, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/03/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:58.47", 977, "NAT"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "26/01/2020", "SAINT-GERMAIN-EN-LAYE", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:57.79", 998, "NAT"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/12/2019", "SARCELLES", "FRANCE", "AS HERBLAY NATATION", 100, 50, "freestyle", "00:57.61", 1004, "NAT"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/07/2017", "PARIS", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:01.14", 895, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.00", 929, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/06/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.79", 931, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:00.38", 944, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:04.51", 820, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:11.63", 626, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:12.79", 597, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:18.74", 459, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "07/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "freestyle", "01:17.27", 491, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "12/03/2017", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:33.84", 794, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "22/05/2016", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:33.68", 880, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:34.12", 856, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "10/05/2015", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:34.37", 842, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:36.50", 733, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:37.23", 697, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:37.24", 697, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "backstroke", "00:41.29", 514, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:44.82", 528, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:45.98", 485, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "breaststroke", "00:45.80", 492, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 100, 50, "breaststroke", "01:57.57", 199, "REG"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "01/03/2020", "LILLE", "FRANCE", "AS HERBLAY NATATION", 50, 50, "butterfly", "00:30.59", 864, "NAT"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "13/04/2013", "COLOMBES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "butterfly", "00:39.10", 536, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 50, 50, "butterfly", "00:43.99", 353, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "11/05/2014", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:02.66", 514, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "02/06/2013", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:01.22", 529, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "06/05/2012", "SARCELLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:25.65", 304, "DEP"));
        allTimes.add(new Race(UUID.randomUUID().toString(), "08/04/2012", "VERSAILLES", "FRANCE", "ETOILE SAINT-LEU NATATION", 200, 50, "IM", "03:27.36", 290, "REG"));

        return allTimes;
    }*/

    /*public static List<Race> getRacesByPoolSizeDistanceRaceSwimRace(List<Race> allTimes, int poolSize, int distance, String swim) {
        List<Race> result = new ArrayList<>();
        Race tmp;
        for (int i = 0; i < allTimes.size(); i++) {
            tmp = allTimes.get(i);
            if (tmp.getPoolSize() == poolSize && tmp.getDistanceRace() == distance && tmp.getSwim().equals(swim))
                result.add(tmp);
        }

        sortRacesByDate(allTimes);
        return result;
    }*/

    public static Race getBestTime(List<Race> allTimes, int position) {
        Race bestTime;

        if (allTimes != null && allTimes.size() != 0 && (position - 1) >= 0 && allTimes.size() > (position - 1)) {
            Collections.sort(allTimes, new TimeComparator());
            bestTime = allTimes.get(position - 1);
            Collections.sort(allTimes, new RaceDateComparator());
        }
        else if (allTimes != null && allTimes.size() != 0) {
            Collections.sort(allTimes, new TimeComparator());
            bestTime = allTimes.get(0);
            Collections.sort(allTimes, new RaceDateComparator());
        } else {
            bestTime = new Race();
            bestTime.setTime(0.0f);
        }
        return bestTime;
    }

    public static void sortRacesByDate(List<Race> allTimes) {
        RaceDateComparator raceDateComparator = new RaceDateComparator();
        Collections.sort(allTimes, raceDateComparator);
    }
}
