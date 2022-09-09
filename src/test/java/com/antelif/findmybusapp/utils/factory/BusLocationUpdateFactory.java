package com.antelif.findmybusapp.utils.factory;

import com.antelif.findmybusapp.domain.BusLocation;
import com.antelif.findmybusapp.domain.Location;
import java.util.ArrayList;
import java.util.List;

public class BusLocationUpdateFactory {

  public static List<BusLocation> createBusLocations() {

    return new ArrayList<>(
        List.of(
            new BusLocation(
                1151L,
                2484L,
                10389L,
                new Location("37.982891", "23.73476"),
                "Mar  4 2019 10:38:56:000AM"),
            new BusLocation(
                1151L,
                2484L,
                10390L,
                new Location("37.993143", "23.749235"),
                "vMar  4 2019 10:39:06:000AM"),
            new BusLocation(
                821L,
                1804L,
                10007L,
                new Location("37.987158", "23.728757"),
                "Mar  4 2019 10:39:05:000AM"),
            new BusLocation(
                821L,
                1804L,
                10015L,
                new Location("37.985427", "23.75514"),
                "Mar  4 2019 10:39:00:000AM"),
            new BusLocation(
                821L,
                1805L,
                10009L,
                new Location("38.000679", "23.740106"),
                "Mar  4 2019 10:39:18:000AM"),
            new BusLocation(
                821L,
                1805L,
                10011L,
                new Location("37.992304", "23.731425"),
                "Mar  4 2019 10:39:07:000AM"),
            new BusLocation(
                750L,
                2640L,
                79130L,
                new Location("38.047557", "23.734463"),
                "Mar  4 2019 10:39:23:000AM")));
  }
}
