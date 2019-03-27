public class Main {
	public static void main(String[] args) {
			System.out.println(cond_repeat("yes",name()));
		System.out.println(cond_repeat("no","Jane"));
		System.out.println(name1());
	}

	public static String name1() {
		return name();
	}

	public static String name() {
		return "John";
	}

	public static String repeat(String x) {
		return x+x;
	}

	public static String cond_repeat(String c,String x) {
		return "yes".startsWith(c)?c.startsWith("yes")?repeat(x):x:x;
	}

}
