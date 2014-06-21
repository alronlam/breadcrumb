package headingdetermination;

import java.util.ArrayList;

import data.SensorEntry;

public interface HeadingDeterminer {
	public double getHeading(ArrayList<SensorEntry> batch);
}
