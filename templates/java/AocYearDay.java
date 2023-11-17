// template AocYear day class
package aoc.year;



class AocYearDay extends AocDay {

    public AocYearDay() {
        super();
    }

    public AocYearDay(String filename) {
        super(filename);
    }

    public void solve() {
        System.out.println("Solving day " + day + " year " + year);
    }

    public static void main(String[] args) {
        AocYearDay day = new AocYearDay();
        day.solve();
    }

}