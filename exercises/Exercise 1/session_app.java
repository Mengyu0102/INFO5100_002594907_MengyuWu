import java.util.*;

// Base class Student
abstract class Student {
    protected String name;
    protected int id;
    protected List<Integer> quizzes; // 15 quiz scores

    public Student(String name, int id, List<Integer> quizzes) {
        if (quizzes == null || quizzes.size() != 15) {
            throw new IllegalArgumentException("Quizzes list must contain exactly 15 scores.");
        }
        this.name = name;
        this.id = id;
        this.quizzes = new ArrayList<>(quizzes);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getQuizzes() {
        return Collections.unmodifiableList(quizzes);
    }

    public double getAverageQuizScore() {
        double sum = 0;
        for (int s : quizzes) sum += s;
        return sum / quizzes.size();
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d)", name, id);
    }
}

// Part-time student
class PartTime extends Student {
    public PartTime(String name, int id, List<Integer> quizzes) {
        super(name, id, quizzes);
    }
}

// Full-time student (has two exam scores)
class FullTime extends Student {
    private int exam1;
    private int exam2;

    public FullTime(String name, int id, List<Integer> quizzes, int exam1, int exam2) {
        super(name, id, quizzes);
        this.exam1 = exam1;
        this.exam2 = exam2;
    }

    public int getExam1() {
        return exam1;
    }

    public int getExam2() {
        return exam2;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" [Full-Time] Exams: (%d, %d)", exam1, exam2);
    }
}

// Session class holds up to 20 students
class Session {
    private static final int MAX_STUDENTS = 20;
    private List<Student> students;

    public Session() {
        students = new ArrayList<>();
    }

    public boolean addStudent(Student s) {
        if (students.size() >= MAX_STUDENTS) return false;
        students.add(s);
        return true;
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    // Calculate average quiz score per student for the whole class and return a map
    public Map<Student, Double> calculateAverageQuizPerStudent() {
        Map<Student, Double> avgMap = new LinkedHashMap<>();
        for (Student s : students) {
            avgMap.put(s, s.getAverageQuizScore());
        }
        return avgMap;
    }

    // Print list of all quiz scores in ascending order for the session
    public List<Integer> getAllQuizScoresSorted() {
        List<Integer> all = new ArrayList<>();
        for (Student s : students) {
            all.addAll(s.getQuizzes());
        }
        Collections.sort(all);
        return all;
    }

    // Print names of part-time students
    public List<String> getPartTimeStudentNames() {
        List<String> names = new ArrayList<>();
        for (Student s : students) {
            if (s instanceof PartTime) names.add(s.getName());
        }
        return names;
    }

    // Print exam scores of full-time students; maps student -> pair of exams
    public Map<FullTime, int[]> getFullTimeExamScores() {
        Map<FullTime, int[]> map = new LinkedHashMap<>();
        for (Student s : students) {
            if (s instanceof FullTime) {
                FullTime f = (FullTime) s;
                map.put(f, new int[]{f.getExam1(), f.getExam2()});
            }
        }
        return map;
    }

    // Helper: display methods that print to console (main will call these)
    public void printAverageQuizPerStudent() {
        System.out.println("Average quiz score per student:");
        Map<Student, Double> map = calculateAverageQuizPerStudent();
        for (Map.Entry<Student, Double> e : map.entrySet()) {
            System.out.printf("%s -> %.2f\n", e.getKey(), e.getValue());
        }
        System.out.println();
    }

    public void printAllQuizScoresSorted() {
        System.out.println("All quiz scores in ascending order for this session:");
        List<Integer> sorted = getAllQuizScoresSorted();
        System.out.println(sorted);
        System.out.println();
    }

    public void printPartTimeStudentNames() {
        System.out.println("Part-time students:");
        List<String> names = getPartTimeStudentNames();
        for (String n : names) System.out.println(n);
        System.out.println();
    }

    public void printFullTimeExamScores() {
        System.out.println("Full-time students and their exam scores:");
        Map<FullTime, int[]> map = getFullTimeExamScores();
        for (Map.Entry<FullTime, int[]> e : map.entrySet()) {
            FullTime f = e.getKey();
            int[] exams = e.getValue();
            System.out.printf("%s -> Exam1: %d, Exam2: %d\n", f, exams[0], exams[1]);
        }
        System.out.println();
    }
}

// Main application
public class SessionApp {
    public static void main(String[] args) {
        Session session = new Session();
        Random rnd = new Random(42); // fixed seed for reproducible dummy data

        // Create 20 students (some part-time, some full-time) with dummy scores
        for (int i = 1; i <= 20; i++) {
            String name = "Student" + String.format("%02d", i);
            List<Integer> quizzes = new ArrayList<>();
            for (int q = 0; q < 15; q++) {
                quizzes.add(rnd.nextInt(101)); // 0 - 100
            }

            if (i % 3 == 0) { // roughly 1/3 full-time
                int exam1 = rnd.nextInt(101);
                int exam2 = rnd.nextInt(101);
                FullTime ft = new FullTime(name, i, quizzes, exam1, exam2);
                session.addStudent(ft);
            } else {
                PartTime pt = new PartTime(name, i, quizzes);
                session.addStudent(pt);
            }
        }

        // Call all public methods and capture output on console
        session.printAverageQuizPerStudent();
        session.printAllQuizScoresSorted();
        session.printPartTimeStudentNames();
        session.printFullTimeExamScores();

        // Additionally, demonstrate returning structures (optional)
        Map<Student, Double> avgMap = session.calculateAverageQuizPerStudent();
        // Example: print top 3 students by average
        System.out.println("Top 3 students by average quiz score:");
        avgMap.entrySet().stream()
                .sorted(Map.Entry.<Student, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(3)
                .forEach(e -> System.out.printf("%s -> %.2f\n", e.getKey(), e.getValue()));
    }
}
