package ro.tuc.pt.assig5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ro.tuc.pt.assig5.MonitoredData;

public class SmartHouse {

	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void processFile(String fileName, List<MonitoredData> list) {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(line -> processLine(line, list));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void processLine(String line, List<MonitoredData> list) {
		String splits[] = line.split("\t\t");

		if (splits.length == 3) {
			LocalDateTime startTime = LocalDateTime.parse(splits[0], formatter);
			LocalDateTime endTime = LocalDateTime.parse(splits[1], formatter);
			String activityLabel = splits[2].trim();

			MonitoredData activity = new MonitoredData(startTime, endTime, activityLabel);
			list.add(activity);
		}
	}

	private static long numberOfDaysOfMonitoredData(List<MonitoredData> monitoredDataList) {
		// mapeaza datele dupa data(year+month+day of month) si grupeaza folosind
		// collectors
		// rezultatul de mai sus reprezinta o mapa(key, value) unde cheia e stringul cu
		// data de mai sus, si valoarea numarul de evenimente din data respectiva
		// de aici am luat:
		// https://stackoverflow.com/questions/25441088/group-by-counting-in-java-8-stream-api
		return monitoredDataList.stream()
				.map(data -> data.startTime.getYear() + "-" + data.startTime.getMonth() + "-"
						+ data.startTime.getDayOfMonth())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).keySet().size();
	}

	private static Map<String, Long> numberOfAppearencesPerEventType(List<MonitoredData> monitoredDataList) {
		// mapeaza datele dupa activity type si grupeaza folosind
		// collectors
		// rezultatul de mai sus reprezinta o mapa(key, value) unde cheia eactivity
		// typeul, si valoarea numarul de evenimente din data respectiva
		// de aici am luat:
		// https://stackoverflow.com/questions/25441088/group-by-counting-in-java-8-stream-api
		return monitoredDataList.stream().map(data -> data.activityLabel)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private static Map<String, Long> numberOfAppearencesPerEventTypeAndDay(List<MonitoredData> monitoredDataList) {
		// mapeaza datele dupa data si activity type si grupeaza folosind
		// collectors
		// rezultatul de mai sus reprezinta o mapa(key, value) unde cheia e data +
		// activity
		// typeul, si valoarea numarul de evenimente din data respectiva
		// de aici am luat:
		// https://stackoverflow.com/questions/25441088/group-by-counting-in-java-8-stream-api
		return monitoredDataList.stream()
				.map(data -> data.startTime.getYear() + "-" + data.startTime.getMonth() + "-"
						+ data.startTime.getDayOfMonth() + " - " + data.activityLabel)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}

	private static List<ActivityDurationPair> activityDurationMapping(List<MonitoredData> monitoredDataList) {
		// pentru fiecare row ne intereseaza 2 informatii (activityLabel si durata)
		// pentru a scoate ambele informatii am creat o noua clasa, ActivityDurationPair
		// si metoda intoarce o lita de ActivityDurationPair
		return monitoredDataList.stream().map(
				data -> new ActivityDurationPair(data.activityLabel, durationInMinutes(data.startTime, data.endTime)))
				.collect(Collectors.toList());
	}

	private static Map<String, Long> totalDurationMapping(List<MonitoredData> monitoredDataList) {
        // folosim lista de ActivityDuration obtinuta prin metoda activityDurationMapping
		// apoi grupam dupa activityLabel si pe grupare sumam durata in minute a activitatilor
		return activityDurationMapping(monitoredDataList).stream().collect(Collectors
				.groupingBy(data -> data.activityLabel, Collectors.summingLong(data -> data.duration)));
	}
	
	private static List<String> shortActivities(List<MonitoredData> monitoredDataList) {
		Map<String, Long> activitiesThatTookLessThan5Minutes = activityDurationMapping(monitoredDataList).stream().filter(data -> data.duration <= 5 * 60).collect(Collectors
				.groupingBy(data -> data.activityLabel, Collectors.summingLong(data -> data.duration)));
		Map<String, Long> activitiesThatTookMoreThan5Minutes = activityDurationMapping(monitoredDataList).stream().filter(data -> data.duration > 5 * 60).collect(Collectors
				.groupingBy(data -> data.activityLabel, Collectors.summingLong(data -> data.duration)));
		
		List<String> shortActivities = new ArrayList<String>();
		for(String activity: activitiesThatTookLessThan5Minutes.keySet()) {
			Long lessThan5 = activitiesThatTookLessThan5Minutes.get(activity);
			Long moreThan5 = activitiesThatTookMoreThan5Minutes.get(activity);
			// daca toate recordurile pentru activitate au durat mai putin de 5 min
			// adauga la rezultatul final
			if(moreThan5 == null) {
				shortActivities.add(activity);
				continue;
			}
			//daca 90% din durata totala este activ mai mica decat suma duratelor activ mai mici decat 5 min
			long totalDuration = lessThan5 + moreThan5;
			if(0.9 * totalDuration <= lessThan5) {
				shortActivities.add(activity);
			}
		}
		
		return shortActivities;
	}

	private static long durationInMinutes(LocalDateTime start, LocalDateTime end) {
		return start.until(end, ChronoUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		SmartHouse smartHouse = new SmartHouse();

		// String fileName = "Activities.txt"; Which one is good?
		String fileName = "C:\\Users\\alexandra\\Desktop\\Activities.txt";
		//"/Users/mirelafocsa/Downloads/pt2019_30423_dediu_alexandra_assignment_5/assig5/src/main/java/Activities.txt";
		
		List<MonitoredData> activities = new ArrayList<>();
		SmartHouse.processFile(fileName, activities);

		System.out.println("Read: " + activities.size() + " number of activities");
		System.out.println("Number of monitored days: " + numberOfDaysOfMonitoredData(activities));

		System.out.println("\nHow many times each activity has appeared in the entire monitored preriod: ");
		for (String activityType : numberOfAppearencesPerEventType(activities).keySet()) {
			System.out.println(
					"\t" + activityType + ": " + numberOfAppearencesPerEventType(activities).get(activityType));
		}

		System.out.println("\nHow many times each activity has appeared for each day: ");
		for (String dayAndActivityType : numberOfAppearencesPerEventTypeAndDay(activities).keySet()) {
			System.out.println("\t" + dayAndActivityType + ": "
					+ numberOfAppearencesPerEventTypeAndDay(activities).get(dayAndActivityType));
		}

		System.out.println("\nActivity durations");
		for (ActivityDurationPair activityDurationPair : activityDurationMapping(activities)) {
			System.out.println(
					"\t" + activityDurationPair.activityLabel + ": " + activityDurationPair.duration);
		}
		
		System.out.println("\nTotal activity durations");
		for (String activity : totalDurationMapping(activities).keySet()) {
			System.out.println(
					"\t" + activity + ": " + totalDurationMapping(activities).get(activity));
		}
		
		System.out.println("\nActivities that took more less than 5 mins at least 90% of the time");
		for (String activity : shortActivities(activities)) {
			System.out.println(
					"\t" + activity);
		}
	}

	
}
