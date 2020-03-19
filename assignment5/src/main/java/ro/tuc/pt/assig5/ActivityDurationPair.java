package ro.tuc.pt.assig5;

public class ActivityDurationPair {

	String activityLabel;
	public Long duration;

	public ActivityDurationPair(String activityLabel, Long duration) {

		this.activityLabel = activityLabel;
		this.duration = duration;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

}
