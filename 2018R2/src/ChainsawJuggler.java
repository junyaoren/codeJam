import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class ChainsawJuggler {
  int R;
  int B;
  private Map<Integer, int[]> sets;
  static Map<Status, Integer> dp;
  private int res;

  private ChainsawJuggler(int r, int b) {
    B = b;
    R = r;
    sets = new HashMap<>();
    int idxCounter = 0;
    for (int i = 0; i <= B; i++) {
      for (int j = 0; j <= R; j++) {
        sets.put(idxCounter, new int[]{i, j});
        idxCounter++;
      }
    }
    if (dp == null) dp = new HashMap<>();
    /*
    for (int k = 0; k < sets.size(); k++) {
      for (int i = 0; i < B; i++) {
        for (int j = 0; j < R; j++) {
          dp.put(new Status(k, i, j), 0);
        }
      }
    }
    */
    res = 0;
  }

  void solve(){
    res = solve(sets.size() - 1, new Status(B, R, B, R)) - 1;
  }

  int solve(int setIdx, Status status) {
    int optimal;
    if (status.b == 0 && status.r == 0) optimal = 1;
    else if (status.vB == 0 && status.vR == 0) optimal = 1;
    else if (dp.containsKey(status)) optimal = dp.get(status);
    else {
      if (status.b - sets.get(setIdx)[0] >= 0 && status.r - sets.get(setIdx)[1] >= 0)
        optimal = Math.max(solve(setIdx - 1, new Status(sets.get(setIdx - 1)[0], sets.get(setIdx - 1)[1], status.b, status.r)),
                           1 + solve(setIdx - 1, new Status(sets.get(setIdx - 1)[0], sets.get(setIdx - 1)[1], status.b - sets.get(setIdx)[0], status.r - sets.get(setIdx)[1])));
      else
        optimal = solve(setIdx - 1, new Status(sets.get(setIdx - 1)[0], sets.get(setIdx - 1)[1], status.b, status.r));
      
    }
    dp.put(status, optimal);
    return optimal;
  }

  void printDP() {
    for (Status s : dp.keySet()) {
        System.out.println(s.toString() + " , optimal: " + dp.get(s));
    }
  }



  class Status {
    int vB;
    int vR;
    int b;
    int r;
    Status (int _vB, int _vR, int _b, int _r) {
      vB = _vB;
      vR = _vR;
      b = _b;
      r = _r;
    }
    public int hashCode() {
      int hash = 7;
      hash = 31 * hash + vB;
      hash = 31 * hash + vR;
      hash = 31 * hash + b;
      hash = 31 * hash + r;
      return hash;
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (this.getClass() != o.getClass()) return false;
      Status s = (Status) o;
      return this.vB == s.vB && this.vR == s.vR && this.r == s.r && this.b == s.b;
    }

    public String toString() {
        return "Status: vB = " + vB + ", vR: " + vR + " , b = " + b + " , r = " + r;
    }
  }

  public static void main(String[] args) {
    Jren.p(Integer.hashCode(100));
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int T = in.nextInt();
    for (int t = 1; t <= T; t++) {
      //if (ChainsawJuggler.dp != null) System.out.println(ChainsawJuggler.dp.size());
      long start = System.nanoTime();
      int R = in.nextInt();
      int B = in.nextInt();
      ChainsawJuggler cj = new ChainsawJuggler(R, B);
      cj.solve();
      long end = System.nanoTime();
      System.out.println("Case #" + t +  ": " + cj.res );
      System.out.println("time: " + (end - start) / 1000000);
      //System.out.println("Case: #" + t + " (B: " + B + ", R: " + R + ")" + ": " + cj.res );
      //cj.printDP();
    }

  }
}