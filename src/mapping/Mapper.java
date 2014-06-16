package mapping;

import android.app.Activity;
import data.BatchProcessingResults;

public interface Mapper {
	public void plot(Activity mainActivity, BatchProcessingResults results);
}
