package ro.tuc.pt.assig5;
import java.time.LocalDateTime;

public class MonitoredData {
    public String activityLabel;
    public LocalDateTime startTime;
    public LocalDateTime endTime;

   public MonitoredData(LocalDateTime startTime2, LocalDateTime endTime2, String activityLabel2) {
		this. startTime=startTime2;
		this.endTime=endTime2;
		this.activityLabel=activityLabel2;
	}

public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
//    @Override
//    public String toString() {
//        return "Activity{" +
//                "startTime=" + startTime +
//                ", endTime=" + endTime +
//                ", activityLabel='" + activityLabel + '\'' +
//                '}';
//    }
//    
}

